package com.bagnet.nettracer.cronjob.wt;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bagnet.nettracer.cronjob.ErrorHandler;
import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.cronjob.bmo.WT_ActionFileBmo;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.SpecialFlagBMO;
import com.bagnet.nettracer.tracing.bmo.exception.StaleStateException;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateBdo;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqEraseActionFile;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestPxf;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.wt.WorldTracerAlreadyClosedException;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerLoggedOutException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.connector.WorldTracerConnectionException;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;
import com.bagnet.nettracer.wt.svc.DefaultWorldTracerService;
import com.bagnet.nettracer.wt.svc.RuleMapper;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class WorldTracerQueueWorker implements Runnable {

	private static final Logger logger = Logger.getLogger(WorldTracerQueueWorker.class);
	
	
	private WorldTracerService wtService;
	private WTQueueBmo wtqBmo;
	private Agent defaultWtAgent;
	private WT_ActionFileBmo wafBmo;
	ConcurrentLinkedQueue<Long> qqueue;

	@SuppressWarnings("unused")
	private RuleMapper wtRuleMap;

	private ErrorHandler errorHandler;

	public void setWtRuleMap(RuleMapper wtRuleMap) {
		this.wtRuleMap = wtRuleMap;
	}
	
	public WorldTracerQueueWorker(WorldTracerService wtService, String companyCode, WTQueueBmo qBmo, Agent defAgent, WT_ActionFileBmo afBmo,
			ConcurrentLinkedQueue<Long> qq, RuleMapper wtRuleMap, ErrorHandler errorHandler) {
		// DO NOT USE PROVIDED WT SERVICE
		//this.wtService = wtService;
		DefaultWorldTracerService dwts = (DefaultWorldTracerService) wtService;
		dwts.setWtCompanyCode(defAgent.getCompanycode_ID());
		
		WorldTracerWebService connector = WorldTracerWebService.getInstance();
		dwts.setWtConnector(connector);
		
		
		this.wtService = dwts;
		this.wtqBmo = qBmo;
		this.defaultWtAgent = defAgent;
		this.wafBmo = afBmo;
		this.qqueue = qq;
		this.wtRuleMap = wtRuleMap;
		this.errorHandler = errorHandler;

	}

	public void handleWorldTracerLoggedOutException(WorldTracerLoggedOutException ex){
		errorHandler.sendEmail("Unable to Login", ex, false, false);
		logger.warn("weren't able to login so sleeping for 5 minutes");
		try {
			Thread.sleep(5 * 60 * 1000);
		} catch (InterruptedException e) {
			//continue
		}
	}
	
	/**
	 * Retry unsuccessful transactions up to the MAX_ATTEMPTS before marking the transaction as failed.
	 * 
	 * TODO - verify with product owner MAX_ATTEMPTS is current hardcoded to 1, thus we will always set 
	 * status to FAIL on the first attempt.
	 * 
	 * @param queue
	 */
	private void updateWtQueueAttemptStatus(WorldTracerQueue queue){
		queue.setAttempts(queue.getAttempts() + 1);
		if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
			queue.setStatus(WtqStatus.FAIL);
		}
		wtqBmo.updateQueue(queue);
	}
	
	/**
	 * Creates an AHL.  If successful, updates incident with WT AHL ID.  If unable to insert,
	 * adds remark to incident with reason for failure.
	 * 
	 * If incident is already closed, cancel the transaction.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	@SuppressWarnings("static-access")
	public void createAhl(WtqCreateAhl queue, WebServiceDto dto) throws CaptchaException{
		IncidentBMO iBmo = new IncidentBMO();
		String wtId = null;
		Incident incident = ((WtqIncidentAction) queue).getIncident();
		String error = null;
		boolean success = false;
		if (incident == null || TracingConstants.MBR_STATUS_CLOSED == incident.getStatus().getStatus_ID()) {
			queue.setStatus(WtqStatus.CANCELED);
			wtqBmo.updateQueue(queue);
			return;
		}
		try {
			wtId = wtService.insertIncident(queue.getAgent(), incident, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		} catch (CaptchaException e) {
			logger.warn("unable to export incident; captcha exception: " + incident.getIncident_ID() + " - " + e.getMessage(), e);						
			throw new CaptchaException();
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();

		} catch (Throwable ex) {
			logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}

		if(success){
			logger.info("created wt ahl: " + wtId);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			incident.setWtFile(new WorldTracerFile(wtId));
			try {
				iBmo.updateIncidentNoAudit(false,true,incident);
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (StaleStateException e) {
				//loupas - should never reach this since the update is ignoring the stale state
				e.printStackTrace();
				try {
					incident.setLastupdated(iBmo.getIncidentByID(incident.getIncident_ID(), null).getLastupdated());
					iBmo.updateIncidentNoAudit(false,true,incident);
				} catch (HibernateException e1) {
					e1.printStackTrace();
				} catch (StaleStateException e1) {
					e1.printStackTrace();
				}
			}
		} else if(queue.getStatus().equals(WtqStatus.FAIL)) {
			//only write remark if queue status is failed
			iBmo.insertRemark(error, incident.getIncident_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Creates a mass set of temporary WT onhands good for 24 hours.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void qoh(WtqQoh queue, WebServiceDto dto) throws CaptchaException{
		String response = null;
		OhdBMO ohdBmo = new OhdBMO();
		try {
			response = wtService.insertQoh(queue.getAgent(), ((WtqQoh) queue), dto);
			Collection<OHD> tags = ((WtqQoh) queue).getOhdTags();
			for (OHD ohd: tags) {
				
				ohd.setTagSentToWt(true);
				ohd.setTagSentToWtStationId(ohd.getHoldingStation().getStation_ID());
				ohdBmo.updateOhdNoAudit(ohd);
			}
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (WorldTracerException ex) { 
			logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			return;
		} catch (CaptchaException ex) {
			logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);						
			throw new CaptchaException();
			
		} 
		catch (Throwable ex) {
			logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		logger.info("created wt qoh: " + response);
		queue.setStatus(WtqStatus.SUCCESS);
		wtqBmo.updateQueue(queue);
	}
	
	/**
	 * Creates a WT OHD.  If successful, updates NT OHD with WT OHD ID.  If unable to insert,
	 * adds remark to NT OHD with reason for failure.
	 * 
	 * If NT OHD is already closed, cancel the transaction.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void createOhd(WtqOhdAction queue, WebServiceDto dto) throws CaptchaException{
		OhdBMO ohdBmo = new OhdBMO();
		String wtId = null;
		boolean success = false;
		String error = null;
		OHD ohd = ((WtqOhdAction) queue).getOhd();
		if (ohd == null || TracingConstants.OHD_STATUS_CLOSED == ohd.getStatus().getStatus_ID()) {
			queue.setStatus(WtqStatus.CANCELED);
			wtqBmo.updateQueue(queue);
			return;
		}
		try {
			wtId = wtService.insertOhd(queue.getAgent(), ohd, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to export ohd; captcha exception: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);						
			throw new CaptchaException();
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}  
		catch (Throwable ex) {
			logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		
		if(success){
			logger.info("created wt ohd: " + wtId);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			ohd.setWtFile(new WorldTracerFile(wtId));
			ohdBmo.updateOhdNoAudit(ohd);
		} else if (queue.getStatus().equals(WtqStatus.FAIL)){
			//only write remark if queue status is failed
			ohdBmo.insertRemark(error, ohd.getOHD_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Closes WT AHL.  If successful, update incident WT_STATUS to closed.
	 * If failed to close, add incident remark stating reason for failure.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void closeAhl(WtqCloseAhl queue, WebServiceDto dto) throws CaptchaException{
		IncidentBMO iBmo = new IncidentBMO();
		Incident incident = ((WtqIncidentAction) queue).getIncident();
		String result = "";
		boolean success = false;
		String error = null;
		try {
			result = wtService.closeIncident(queue.getAgent(), incident, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to close incident; captcha exception: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("closed wt ahl: " + result);
			incident.getWtFile().setWt_status(WTStatus.CLOSED);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			try {
				iBmo.updateIncidentNoAudit(false, true, incident);
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (StaleStateException e) {
				//loupas - should never reach this since the update is ignoring the stale state
				e.printStackTrace();
			}
		} else if(queue.getStatus().equals(WtqStatus.FAIL)) {
			//only write remark if queue status is failed
			iBmo.insertRemark(error, incident.getIncident_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Closes WT OHD.  If successful, update NT OHD WT_STATUS to closed.
	 * If failed to close, add NT OHD remark stating reason for failure.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void closeOhd(WtqCloseOhd queue, WebServiceDto dto) throws CaptchaException{
		OhdBMO ohdBmo = new OhdBMO();
		String wtId = null;
		OHD ohd = ((WtqOhdAction) queue).getOhd();
		boolean success = false;
		String error = null;
		try {
			wtId = wtService.closeOHD(queue.getAgent(), ohd, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to export ohd; captcha exception: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to close wt ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("closed wt ahl: " + wtId);
			ohd.getWtFile().setWt_status(WTStatus.CLOSED);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			ohdBmo.updateOhdNoAudit(ohd);
		} else if (queue.getStatus().equals(WtqStatus.FAIL)){
			//only write remark if queue status is failed
			ohdBmo.insertRemark(error, ohd.getOHD_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Suspends a WT AHL.  If successful, update incident WT_STATUS to SUSPENDED.
	 * If failed, add remark to incident stating reason for failure.
	 * 
	 * If unable to load incident, mark queue status as FAIL
	 * If incident is already closed, cancel the transaction
	 * If property TRACING_STATUS_BLOCK_WT is enabled and the incident's tracing status is set to TRACING,
	 * set queue status to LOCKED
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void suspendAhl(WtqSuspendAhl queue, WebServiceDto dto) throws CaptchaException{
		IncidentBMO iBmo = new IncidentBMO();
		String wtId = null;
		Incident incident = ((WtqIncidentAction) queue).getIncident();
		boolean success = false;
		String error = null;
		
		if(incident == null){
			queue.setStatus(WtqStatus.FAIL);
			wtqBmo.updateQueue(queue);
			return;
		}
		if(PropertyBMO.isTrue(PropertyBMO.TRACING_STATUS_BLOCK_WT) 
				&& incident.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING){
			queue.setStatus(WtqStatus.LOCKED);
			wtqBmo.updateQueue(queue);
			return;
		}
		if(incident != null && TracingConstants.MBR_STATUS_CLOSED == incident.getStatus().getStatus_ID()){
			queue.setStatus(WtqStatus.CANCELED);
			wtqBmo.updateQueue(queue);
			return;
		}

		try {
			wtId = wtService.suspendIncident(queue.getAgent(), incident, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to supsend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("suspended wt ahl: " + wtId);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			incident.getWtFile().setWt_status(WTStatus.SUSPENDED);
			try {
				iBmo.updateIncidentNoAudit(false,true,incident);
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (StaleStateException e) {
				//should never reach here
				e.printStackTrace();
			}
		} else if(queue.getStatus().equals(WtqStatus.FAIL)) {
			//only write remark if queue status is failed
			iBmo.insertRemark(error, incident.getIncident_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Reinstates the WT AHL.  If successful, update incident WT_STATUS to ACTIVE
	 * If failed, add remark to incident stating reason for failure
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void reinstateAhl(WtqReinstateAhl queue, WebServiceDto dto) throws CaptchaException{
		IncidentBMO iBmo = new IncidentBMO();
		String wtId = null;
		Incident incident = ((WtqIncidentAction) queue).getIncident();
		boolean success = false;
		String error = null;
		
		try {
			wtId = wtService.reinstateIncident(queue.getAgent(), incident, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);				
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("reinstated wt ahl: " + wtId);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			incident.getWtFile().setWt_status(WTStatus.ACTIVE);
			try {
				iBmo.updateIncidentNoAudit(false, true,incident);
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (StaleStateException e) {
				//loupas - should never reach this since the update is ignoring the stale state
				e.printStackTrace();
			}
		} else if(queue.getStatus().equals(WtqStatus.FAIL)) {
			//only write remark if queue status is failed
			iBmo.insertRemark(error, incident.getIncident_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Suspends a WT OHD.  If successful, update NT OHD WT_STATUS to SUSPENDED.
	 * If failed, add remark to NT OHD stating reason for failure.
	 * 
	 * If NT OHD is already closed, cancel the transaction
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void suspendOhd(WtqSuspendOhd queue, WebServiceDto dto) throws CaptchaException{
		OhdBMO ohdBmo = new OhdBMO();
		String wtId = null;
		OHD ohd = ((WtqOhdAction) queue).getOhd();
		boolean success = false;
		String error = null;

		if (ohd == null || TracingConstants.OHD_STATUS_CLOSED == ohd.getStatus().getStatus_ID()) {
			queue.setStatus(WtqStatus.CANCELED);
			wtqBmo.updateQueue(queue);
			return;
		}
		try {
			wtId = wtService.suspendOhd(queue.getAgent(), ohd, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to supsend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("suspended wt ahl: " + wtId);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			ohd.getWtFile().setWt_status(WTStatus.SUSPENDED);
			ohdBmo.updateOhdNoAudit(ohd);
		} else if (queue.getStatus().equals(WtqStatus.FAIL)){
			//only write remark if queue status is failed
			ohdBmo.insertRemark(error, ohd.getOHD_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Reinstates the WT OHD.  If successful, update NT OHD WT_STATUS to ACTIVE
	 * If failed, add remark to NT OHD stating reason for failure
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void reinstateOhd(WtqReinstateOhd queue, WebServiceDto dto) throws CaptchaException{
		OhdBMO ohdBmo = new OhdBMO();
		String wtId = null;
		OHD ohd = ((WtqOhdAction) queue).getOhd();
		boolean success = false;
		String error = null;
		try {
			wtId = wtService.reinstateOhd(queue.getAgent(), ohd, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);				
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("suspended wt ahl: " + wtId);
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
			ohd.getWtFile().setWt_status(WTStatus.ACTIVE);
			ohdBmo.updateOhdNoAudit(ohd);
		} else if (queue.getStatus().equals(WtqStatus.FAIL)){
			//only write remark if queue status is failed
			ohdBmo.insertRemark(error, ohd.getOHD_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Forwards a message in WT
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void fwdGeneral(WtqFwdGeneral queue, WebServiceDto dto) throws CaptchaException{
		try {
			@SuppressWarnings("unused")
			String result = wtService.sendFwdMsg(queue.getAgent() == null ? defaultWtAgent : queue.getAgent(), (WtqFwdGeneral) queue, dto);
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to send fwd", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to send fwd", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		} catch (CaptchaException ex) {
			logger.warn("unable to send fwd", ex);						
			throw new CaptchaException();
		} 
		catch (Throwable ex) {
			logger.warn("unable to send fwd:", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		logger.info("send fwd Message");
		queue.setStatus(WtqStatus.SUCCESS);
		wtqBmo.updateQueue(queue);
	}
	
	/**
	 * Forward the WT OHD.  If successful, update NT OHD Status to closed.
	 * If failed, add remark to NT OHD stating reason for failure.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void fwdOhd(WtqFwdOhd queue, WebServiceDto dto) throws CaptchaException{
		OhdBMO ohdBmo = new OhdBMO();
		OHD ohd = ((WtqFwdOhd) queue).getOhd();
		boolean success = false;
		String error = null;

		try {
			@SuppressWarnings("unused")
			String result = wtService.forwardOhd(queue.getAgent(), (WtqFwdOhd) queue, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch(CaptchaException ex) {
			logger.warn("unable to fwd ohd; captcha exception", ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to fwd ohd", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to fwd ohd", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (Throwable ex) {
			logger.warn("unable to fwd ohd", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("forwarded ohd: " + ((WtqFwdOhd) queue).getOhd().getOHD_ID());
			queue.setStatus(WtqStatus.SUCCESS);
			if(ohd.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED) {
				Status s = new Status();
				s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
				ohd.setStatus(s);
				ohdBmo.updateOhdNoAudit(ohd);
			}
			wtqBmo.updateQueue(queue);
		} else if (queue.getStatus().equals(WtqStatus.FAIL)){
			//only write remark if queue status is failed
			ohdBmo.insertRemark(error, ohd.getOHD_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}

	}
	
	/**
	 * Request quick onhand.
	 * 
	 * TODO - need product owner to verify functionality.
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void requestQoh(WtqRequestQoh queue, WebServiceDto dto) throws CaptchaException{
		try {
			WtqRequestQoh wtq = (WtqRequestQoh) queue;
			// this one is a little complicated. gotta have an ahl and
			// an ohd
			// then we need to map them up...
			@SuppressWarnings("unused")
			String result = wtService.requestQoh(queue.getAgent(), wtq, dto);
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to request qoh", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to request qoh", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		} catch (CaptchaException ex) {
			logger.warn("unable to request qoh", ex);						
			throw new CaptchaException();
		} 
		catch (Throwable ex) {
			logger.warn("unable to request qoh", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		logger.info("requested qoh: " + ((WtqRequestQoh) queue).getBagTagNumber());
		queue.setStatus(WtqStatus.SUCCESS);
		wtqBmo.updateQueue(queue);
	}
	
	/**
	 * Request an OHD from WT.  
	 * 
	 * TODO - need product owner to verify functionality
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void requestOhd(WtqRequestOhd queue, WebServiceDto dto) throws CaptchaException{
		@SuppressWarnings("unused")
		OHD ohd = null;
		try {
			WtqRequestOhd wtq = (WtqRequestOhd) queue;
			// this one is a little complicated. gotta have an ahl and
			// an ohd
			// then we need to map them up...
			ohd = WorldTracerUtils.findOHDByWTID(wtq.getWt_ohd());
			/*
			if(ohd == null) {
				// need to import
				ohd = wtService.getOhdforOhd(wtq.getWt_ohd(), WTStatus.ACTIVE, null);
				ohdBmo.insertOHD(ohd, defaultWtAgent);
			}
			*/
			@SuppressWarnings("unused")
			String result = wtService.requestOhd(queue.getAgent(), wtq, dto);
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to request ohd", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to request ohd", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		} catch (CaptchaException ex) {
			logger.warn("unable to request ohd", ex);						
			throw new CaptchaException();
		} 
		catch (Throwable ex) {
			logger.warn("unable to request ohd", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		logger.info("requested ohd: " + ((WtqRequestOhd) queue).getWt_ohd());
		queue.setStatus(WtqStatus.SUCCESS);
		wtqBmo.updateQueue(queue);
	}
	

	/**
	 * Amends WT AHL.  If successful, no update needed for incident.
	 * If failed, add remark to incident stating reason for failure.
	 * 
	 * If incident is null, set queue status to FAIL.
	 * 
	 * if property TRACING_STATUS_BLOCK_WT is enabled and the incident tracing status is TRACING,
	 * update queue status to LOCKED
	 * 
	 * If AHL is already closed, cancel the transaction
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void amendAhl(WtqAmendAhl queue, WebServiceDto dto) throws CaptchaException{
		IncidentBMO iBmo = new IncidentBMO();
		Incident incident = ((WtqIncidentAction) queue).getIncident();
		boolean success = false;
		String error = null;

		if (incident == null) {
			queue.setStatus(WtqStatus.FAIL);
			wtqBmo.updateQueue(queue);
			return;
		} 
		if(PropertyBMO.isTrue(PropertyBMO.TRACING_STATUS_BLOCK_WT) 
				&& incident.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING) {
			queue.setStatus(WtqStatus.LOCKED);
			wtqBmo.updateQueue(queue);
			return;
		}

		try {
			@SuppressWarnings("unused")
			String result = wtService.amendAhl(queue.getAgent(), incident, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to amend ahl", ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerAlreadyClosedException ex) {
			queue.setStatus(WtqStatus.CANCELED);
			incident.getWtFile().setWt_status(WTStatus.CLOSED);
			wtqBmo.updateQueue(queue);
			try {
				iBmo.updateIncidentNoAudit(false, true,incident);
			} catch (HibernateException e) {
				e.printStackTrace();
			} catch (StaleStateException e) {
				//loupas - should never reach this since the update is ignoring the stale state
				e.printStackTrace();
			}
			return;
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to amend ahl", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to amend ahl", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (Throwable ex) {
			logger.warn("unable to amend ahl", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success) {
			logger.info("amended AHL: " + ((WtqAmendAhl) queue).getIncident().getIncident_ID());
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
		} else if(queue.getStatus().equals(WtqStatus.FAIL)) {
			//only write remark if queue status is failed
			iBmo.insertRemark(error, incident.getIncident_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Amends a WT OHD.  IF successful, no updated needed for NT OHD.
	 * If failed, add remark to NT OHD stating reason for failure
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void amendOhd(WtqAmendOhd queue, WebServiceDto dto) throws CaptchaException{
		OhdBMO ohdBmo = new OhdBMO();
		OHD ohd = null;
		boolean success = false;
		String error = null;
		
		try {
			ohd = ((WtqOhdAction) queue).getOhd();
			@SuppressWarnings("unused")
			String result = wtService.amendOhd(queue.getAgent(), ohd, dto);
			success = true;
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to amend ohd", ex);					
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to amend ohd", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to amend ohd", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		} 
		catch (Throwable ex) {
			logger.warn("unable to amend ohd", ex);
			updateWtQueueAttemptStatus(queue);
			error = ex.getMessage();
		}
		if(success){
			logger.info("amended ohd: " + ((WtqAmendOhd) queue).getOhd().getOHD_ID());
			queue.setStatus(WtqStatus.SUCCESS);
			wtqBmo.updateQueue(queue);
		} else if (queue.getStatus().equals(WtqStatus.FAIL)){
			//only write remark if queue status is failed
			ohdBmo.insertRemark(error, ohd.getOHD_ID(), null, TracingConstants.REMARK_REGULAR, false);
		}
	}
	
	/**
	 * Creates a WT BDO.
	 * 
	 * @param queue
	 * @param dto
	 */
	public void createBdo(WtqCreateBdo queue, WebServiceDto dto){
		BDO bdo = null;
		try {
			bdo = ((WtqCreateBdo) queue).getBdo();
			if(bdo == null) {
				throw new WorldTracerException("Cannot export BDO, invalid bdo referenced in queue");
			}
			@SuppressWarnings("unused")
			String result = wtService.insertBdo(queue.getAgent(), bdo, dto);
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (WorldTracerException ex) {
			logger.warn("unable to insert bdo", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to insert bdo", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		} catch (CaptchaException ex) {
			logger.warn("unable to insert bdo", ex);						
			try {
				throw new CaptchaException();
			} catch (CaptchaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (Throwable ex) {
			logger.warn("unable to insert bdo", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		logger.info("sent bdo to worldtracer: " + bdo.getBDO_ID());
		queue.setStatus(WtqStatus.SUCCESS);
		wtqBmo.updateQueue(queue);
	}
	
	
	
	/**
	 * Erases the Action file
	 * 
	 * TODO - refactor me
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void eraseActionFile(WtqEraseActionFile queue, WebServiceDto dto) throws CaptchaException{
		WtqEraseActionFile eraseTask = (WtqEraseActionFile) queue;
		Worldtracer_Actionfiles waf;
		try {
			waf = new Worldtracer_Actionfiles(eraseTask.getAf_id());
			waf.setAction_file_text(wafBmo.findTextForAf(waf));
			if(waf.getAction_file_text() == null) {
				logger.warn(String.format("tried to delete action file %s but not found in db %s %s %s %s %d %d",
						eraseTask.getAf_id(), waf.getAirline(), waf.getStation(), waf.getAction_file_type()
								.name(), waf.getSeq(), waf.getDay(), waf.getItem_number()));
				eraseTask.setStatus(WtqStatus.FAIL);
			}
			else {
				wtService.eraseActionFile(queue.getAgent(), waf, dto);
				wafBmo.deleteActionFile(waf);
				eraseTask.setStatus(WtqStatus.SUCCESS);
			}
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		} catch (CaptchaException ex) {
			logger.warn("unable to delete action file for eraseTask task:", ex);						
			throw new CaptchaException();
		} 
		catch (Exception e) {
			logger.error("unable to delete action file for eraseTask task: " + eraseTask.getWt_queue_id(), e);
		} finally {
			try {
				if (eraseTask != null) {
					//TODO review this, this is the only method and has an additional FAIL criteria other than MAX_ATTTEMPTS
					eraseTask.setAttempts(eraseTask.getAttempts() + 1);
					if (eraseTask.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS
							&& !eraseTask.getStatus().equals(WtqStatus.SUCCESS)) {
						eraseTask.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(eraseTask);
				}
			}
			catch (Exception e) {
				logger.error("unable to update queue task", e);
			}
		}
	}
	
	/**
	 * Request WT PXF
	 * 
	 * @param queue
	 * @param dto
	 * @throws CaptchaException
	 */
	public void requestPxf(WtqRequestPxf queue, WebServiceDto dto) throws CaptchaException{
		String result = "";
		try {
			WtqRequestPxf wtq = (WtqRequestPxf) queue;
			// we need to map them up...
			result = wtService.sendPxf(queue.getAgent(), wtq, dto);
		}
		catch(WorldTracerLoggedOutException ex) {
			handleWorldTracerLoggedOutException(ex);
			return;
		}
		catch (CaptchaException ex) {
			logger.warn("unable to send pxf", ex);						
			throw new CaptchaException();
		} 
		catch (WorldTracerException ex) {
			logger.warn("unable to send pxf", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		catch (WorldTracerConnectionException ex) {
			logger.warn("unable to send pxf", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		} 
		catch (Throwable ex) {
			logger.warn("unable to send pxf", ex);
			updateWtQueueAttemptStatus(queue);
			return;
		}
		logger.info("pxf sent !" + result);
		queue.setStatus(WtqStatus.SUCCESS);
		wtqBmo.updateQueue(queue);
	}
	

	public void run() {
		try {
			wtService.getWtConnector().initialize();
			
			WebServiceDto dto = new WebServiceDto();
			dto.setCronUser(true);
			dto.setUseAvailableConnectionsIfAvailable(true);

			Long queue_id;
			
			while ((queue_id = qqueue.poll()) != null) {
				WorldTracerQueue queue = wtqBmo.findById(queue_id);
				if(queue == null) {
					logger.warn("unable to find queue entry for id in list: " + queue_id);
					continue;
				}
				if(queue instanceof WtqCreateAhl) {
					createAhl((WtqCreateAhl)queue, dto);
				} 
				else if(queue instanceof WtqQoh) {
					qoh((WtqQoh)queue, dto);
				}
				else if(queue instanceof WtqCreateOhd) {
					createOhd((WtqCreateOhd)queue, dto);
				}
				else if(queue instanceof WtqCloseAhl) {
					closeAhl((WtqCloseAhl)queue, dto);
				}
				else if(queue instanceof WtqCloseOhd) {
					closeOhd((WtqCloseOhd)queue, dto);
				}
				else if(queue instanceof WtqSuspendAhl) {
					suspendAhl((WtqSuspendAhl)queue, dto);
				}
				else if(queue instanceof WtqReinstateAhl) {
					reinstateAhl((WtqReinstateAhl)queue, dto);
				}
				else if(queue instanceof WtqSuspendOhd) {
					suspendOhd((WtqSuspendOhd)queue, dto);
				}
				else if(queue instanceof WtqReinstateOhd) {
					reinstateOhd((WtqReinstateOhd)queue, dto);
				}
				else if(queue instanceof WtqFwdGeneral) {
					fwdGeneral((WtqFwdGeneral)queue, dto);
				}
				else if(queue instanceof WtqFwdOhd) {
					fwdOhd((WtqFwdOhd)queue, dto);
				}
				else if(queue instanceof WtqRequestQoh) {
					requestQoh((WtqRequestQoh)queue, dto);
				}
				else if(queue instanceof WtqRequestOhd) {
					requestOhd((WtqRequestOhd)queue, dto);
				}
				else if(queue instanceof WtqAmendAhl) {
					amendAhl((WtqAmendAhl)queue, dto);
				}
				else if(queue instanceof WtqAmendOhd) {
					amendOhd((WtqAmendOhd)queue, dto);
				}
				else if(queue instanceof WtqCreateBdo) {
					createBdo((WtqCreateBdo)queue, dto);
				}
				else if(queue instanceof WtqEraseActionFile) {
					eraseActionFile((WtqEraseActionFile)queue, dto);
				}
				else if(queue instanceof WtqRequestPxf) {
					requestPxf((WtqRequestPxf)queue, dto);
				}
			}
		} catch (CaptchaException e) {
			// ABORT
			logger.error("Received Captcha Exception; will notify user of error", e);
			SpecialFlagBMO.setSpecialFlagResetTimestampByCron("captcha");
		} finally {
			WorldTracerConnector conn = wtService.getWtConnector();
			conn.logout();
		}
	}
}
