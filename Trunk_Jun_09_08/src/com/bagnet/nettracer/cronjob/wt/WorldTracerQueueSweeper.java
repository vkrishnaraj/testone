package com.bagnet.nettracer.cronjob.wt;

import java.util.List;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.bmo.WTQueueBmo;
import com.bagnet.nettracer.cronjob.bmo.WT_FWD_LogBmo;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WorldTracerConnectionException;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerService;

public class WorldTracerQueueSweeper {
	
	private static final Logger logger = Logger.getLogger(WorldTracerQueueSweeper.class);
	
	//Spring injected
	private WTQueueBmo wtqBmo;
	private String companyCode;
	private int wtHitDelay;
	private int wtEndDelay;
	private WorldTracerService wtService;
	
	//not spring injected
	private IncidentBMO iBmo;
	private OhdBMO ohdBmo;

	private WT_FWD_LogBmo wfwdBmo;
	
	public WorldTracerQueueSweeper() {
		iBmo = new IncidentBMO();
		ohdBmo = new OhdBMO();
	}
	
	public void processWtQueue() {
		Company_Specific_Variable csv = AdminUtils.getCompVariable(companyCode);
		if (csv == null || csv.getWt_enabled() != 1 || csv.getWt_write_enabled() != 1) {
			return;
		}
		//TODO logging
		Agent ogadmin = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		//proess the queued tasks
		List<WT_Queue> qTasks = wtqBmo.getPendingTasksByCompany(companyCode);
		for (WT_Queue queue : qTasks) {
			if(WT_Queue.INCIDENT_TYPE.equalsIgnoreCase(queue.getType())) {
				String wtId = null;
				Incident incident = iBmo.findIncidentByID(queue.getType_id());
				try {
					wtId = wtService.insertIncident(incident);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("created wt ahl: " + wtId);
				queue.setQueue_status(-1);
				wtqBmo.updateQueue(queue);
				incident.setWt_id(wtId);
				MessageUtils.sendmessage(incident.getStationcreated(), "WT AHL Created", incident.getAgent(),
						String.format("NetTracer Lost/Delay: %s -> WorldTracer AHL: %s", incident.getIncident_ID(), wtId),
						incident.getIncident_ID(), "");
				iBmo.updateIncidentNoAudit(incident);
			}
			else if (WT_Queue.OHD_TYPE.equalsIgnoreCase(queue.getType())) {
				String wtId = null;
				OHD ohd = ohdBmo.findOHDByID(queue.getType_id());
				try {
					wtId = wtService.insertOhd(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("created wt ohd: " + wtId);
				queue.setQueue_status(-1);
				wtqBmo.updateQueue(queue);
				ohd.setWt_id(wtId);
				MessageUtils.sendmessage(ohd.getFoundAtStation(), "WT OHD Created", ohd.getAgent(),
						String.format("NetTracer OHD: %s  -> WorldTracer OHD: %s", ohd.getOHD_ID(), wtId),
						"", ohd.getOHD_ID());
				ohdBmo.insertOHD(ohd, ogadmin);
			}
			else if (WT_Queue.CLOSE_INCIDENT_TYPE.equalsIgnoreCase(queue.getType())) {
				Incident incident = iBmo.findIncidentByID(queue.getType_id());
				String result = "";
				try {
					result = wtService.closeIncident(incident);
				} catch  ( WorldTracerException ex) {
					logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("closed wt ahl: " + result);
				queue.setQueue_status(-1);
				wtqBmo.updateQueue(queue);
			}
			else if (WT_Queue.CLOSE_OHD_TYPE.equalsIgnoreCase(queue.getType())) {
				String wtId = null;
				OHD ohd = ohdBmo.findOHDByID(queue.getType_id());
				try {
					wtId = wtService.closeOHD(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to close wt ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setQueue_status(queue.getQueue_status() + 1);
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("closed wt ahl: " + wtId);
				queue.setQueue_status(-1);
				wtqBmo.updateQueue(queue);
			}
			try {
				Thread.sleep(wtHitDelay * 1000);
			} catch (InterruptedException e) {
				logger.warn("sleep was interrupted", e);
			}
		}
		processForwards(companyCode, ogadmin);
		try {
			Thread.sleep(wtEndDelay * 1000);
		} catch (InterruptedException e) {
			logger.warn("sleep was interrupted", e);
		}
	}
	
	
	public void processForwards(String companyCode, Agent ogadmin) {
		//proess the queued tasks
		List<WT_FWD_Log> qTasks = wfwdBmo.getPendingFwdsByCompany(companyCode);
		for (WT_FWD_Log fwd : qTasks) {
			try {
				//wtService.sendFwd(fwd);
			} catch (Throwable e) {
				logger.warn("unable to send fwd: " + fwd.getWt_fwd_log_id() + " - " + e.getMessage());
				fwd.setFwd_status(fwd.getFwd_status() + 1);
				wfwdBmo.updateFwd(fwd);
				continue;
			}
			logger.info("forwarded " + fwd.getWt_fwd_log_id() + " with bagtag " + fwd.getBagtag());
			fwd.setFwd_status(-1);
			wfwdBmo.updateFwd(fwd);
		}
		
	}

	
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	public void setWtHitDelay(int delay) {
		this.wtHitDelay = delay;
	}

	public void setWtqBmo(WTQueueBmo wtqBmo) {
		this.wtqBmo = wtqBmo;
	}

	public void setWtEndDelay(int wtEndDelay) {
		this.wtEndDelay = wtEndDelay;
	}


	public void setIncidentBmo(IncidentBMO bmo) {
		iBmo = bmo;
	}


	public void setWtService(WorldTracerService wtService) {
		this.wtService = wtService;
	}


	public void setOhdBmo(OhdBMO ohdBmo) {
		this.ohdBmo = ohdBmo;
	}

}
