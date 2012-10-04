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

	public void run() {

		try {
			
			wtService.getWtConnector().initialize();
			IncidentBMO iBmo = new IncidentBMO();
			OhdBMO ohdBmo = new OhdBMO();
			
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
					String wtId = null;
					Incident incident = ((WtqIncidentAction) queue).getIncident();
					if (incident != null && TracingConstants.MBR_STATUS_CLOSED != incident.getStatus().getStatus_ID()) {
						try {
							wtId = wtService.insertIncident(incident, dto);
						}
						catch(WorldTracerLoggedOutException ex) {
							errorHandler.sendEmail("Unable to Login", ex, false, false);
							logger.warn("weren't able to login so sleeping for 5 minutes");
							Thread.sleep(5 * 60 * 1000);
							continue;
						}
						catch (WorldTracerException ex) {
	
							logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						catch (WorldTracerConnectionException ex) {
	
							logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						} catch (CaptchaException e) {
							logger.warn("unable to export incident; captcha exception: " + incident.getIncident_ID() + " - " + e.getMessage(), e);						
							throw new CaptchaException();
						} catch (Throwable ex) {
							
							logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						logger.info("created wt ahl: " + wtId);
						queue.setStatus(WtqStatus.SUCCESS);
						wtqBmo.updateQueue(queue);
						incident.setWtFile(new WorldTracerFile(wtId));
						try {
							iBmo.updateIncidentNoAudit(false,incident);
						} catch (HibernateException e) {
							e.printStackTrace();
						} catch (StaleStateException e) {
							//loupas - should never reach this since the update is ignoring the stale state
							e.printStackTrace();
						}
						
					} else {
						queue.setStatus(WtqStatus.CANCELED);
						wtqBmo.updateQueue(queue);
					}
				} 
				// TODO: BEGIN
				else if(queue instanceof WtqQoh) {
					String response = null;
					try {
						response = wtService.insertQoh(((WtqQoh) queue), dto);
						Collection<OHD> tags = ((WtqQoh) queue).getOhdTags();
						for (OHD ohd: tags) {
							
							ohd.setTagSentToWt(true);
							ohd.setTagSentToWtStationId(ohd.getHoldingStation().getStation_ID());
							ohdBmo.updateOhdNoAudit(ohd);
						}
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						 
						logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {

						logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);						
						throw new CaptchaException();
						
					} 
					catch (Throwable ex) {
						logger.warn("unable to export ohd tag list for queue ID: " + queue.getWt_queue_id() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("created wt qoh: " + response);
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);

				}
				// TODO: END
				else if(queue instanceof WtqCreateOhd) {
					String wtId = null;
					OHD ohd = ((WtqOhdAction) queue).getOhd();
					if (ohd != null && TracingConstants.OHD_STATUS_CLOSED != ohd.getStatus().getStatus_ID()) {
						try {
							wtId = wtService.insertOhd(ohd, dto);
						}
						catch(WorldTracerLoggedOutException ex) {
							errorHandler.sendEmail("Unable to Login", ex, false, false);
							logger.warn("weren't able to login so sleeping for 5 minutes");
							Thread.sleep(5 * 60 * 1000);
							continue;
						}
						catch (WorldTracerException ex) {
							 
							logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						catch (WorldTracerConnectionException ex) {
	
							logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						} catch (CaptchaException ex) {
							logger.warn("unable to export ohd; captcha exception: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);						
							throw new CaptchaException();
							
						} 
						catch (Throwable ex) {
							logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						logger.info("created wt ohd: " + wtId);
						queue.setStatus(WtqStatus.SUCCESS);
						wtqBmo.updateQueue(queue);
						ohd.setWtFile(new WorldTracerFile(wtId));
						ohdBmo.updateOhdNoAudit(ohd);
					} else {
						queue.setStatus(WtqStatus.CANCELED);
						wtqBmo.updateQueue(queue);
					}
				}
				else if(queue instanceof WtqCloseAhl) {
					Incident incident = ((WtqIncidentAction) queue).getIncident();
					String result = "";
					try {
						result = wtService.closeIncident(incident, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to close incident; captcha exception: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("closed wt ahl: " + result);
					incident.getWtFile().setWt_status(WTStatus.CLOSED);
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
					try {
						iBmo.updateIncidentNoAudit(false, incident);
					} catch (HibernateException e) {
						e.printStackTrace();
					} catch (StaleStateException e) {
						//loupas - should never reach this since the update is ignoring the stale state
						e.printStackTrace();
					}
				}
				else if(queue instanceof WtqCloseOhd) {
					String wtId = null;
					OHD ohd = ((WtqOhdAction) queue).getOhd();
					try {
						wtId = wtService.closeOHD(ohd, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {

						logger.warn("unable to close wt ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to export ohd; captcha exception: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("closed wt ahl: " + wtId);
					ohd.getWtFile().setWt_status(WTStatus.CLOSED);
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
					ohdBmo.updateOhdNoAudit(ohd);
				}
				else if(queue instanceof WtqSuspendAhl) {
					String wtId = null;
					Incident incident = ((WtqIncidentAction) queue).getIncident();
					if (incident != null && incident.getTracingStatus() != TracingConstants.INCIDENT_TRACING_STATUS_TRACING
							&& TracingConstants.MBR_STATUS_CLOSED != incident.getStatus().getStatus_ID()) {
						try {
							wtId = wtService.suspendIncident(incident, dto);
						}
						catch(WorldTracerLoggedOutException ex) {
							errorHandler.sendEmail("Unable to Login", ex, false, false);
							logger.warn("weren't able to login so sleeping for 5 minutes");
							Thread.sleep(5 * 60 * 1000);
							continue;
						}
						catch (WorldTracerException ex) {
	
							logger.warn("unable to supsend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						catch (WorldTracerConnectionException ex) {
	
							logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						} catch (CaptchaException ex) {
							logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);						
							throw new CaptchaException();
						} 
						catch (Throwable ex) {
	
							logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						logger.info("suspended wt ahl: " + wtId);
						queue.setStatus(WtqStatus.SUCCESS);
						wtqBmo.updateQueue(queue);
						incident.getWtFile().setWt_status(WTStatus.SUSPENDED);
						try {
							iBmo.updateIncidentNoAudit(false,incident);
						} catch (HibernateException e) {
							e.printStackTrace();
						} catch (StaleStateException e) {
							//should never reach here
							e.printStackTrace();
						}
					} else {
						if (incident == null) {
							queue.setStatus(WtqStatus.FAIL);
						} else if (incident.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING){
							queue.setStatus(WtqStatus.LOCKED);
						} else {
							queue.setStatus(WtqStatus.CANCELED);
						}
						wtqBmo.updateQueue(queue);
					}
				}
				else if(queue instanceof WtqReinstateAhl) {
					String wtId = null;
					Incident incident = ((WtqIncidentAction) queue).getIncident();
					try {
						wtId = wtService.reinstateIncident(incident, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {

						logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {

						logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);				
						throw new CaptchaException();
					} 
					catch (Throwable ex) {

						logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("reinstated wt ahl: " + wtId);
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
					incident.getWtFile().setWt_status(WTStatus.ACTIVE);
					try {
						iBmo.updateIncidentNoAudit(false, incident);
					} catch (HibernateException e) {
						e.printStackTrace();
					} catch (StaleStateException e) {
						//loupas - should never reach this since the update is ignoring the stale state
						e.printStackTrace();
					}
				}
				else if(queue instanceof WtqSuspendOhd) {
					String wtId = null;
					OHD ohd = ((WtqOhdAction) queue).getOhd();
					if (ohd != null && TracingConstants.OHD_STATUS_CLOSED != ohd.getStatus().getStatus_ID()) {
						try {
							wtId = wtService.suspendOhd(ohd, dto);
						}
						catch(WorldTracerLoggedOutException ex) {
							errorHandler.sendEmail("Unable to Login", ex, false, false);
							logger.warn("weren't able to login so sleeping for 5 minutes");
							Thread.sleep(5 * 60 * 1000);
							continue;
						}
						catch (WorldTracerException ex) {
	
							logger.warn("unable to supsend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
	
							continue;
						}
						catch (WorldTracerConnectionException ex) {
	
							logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						} catch (CaptchaException ex) {
							logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);						
							throw new CaptchaException();
						} 
						catch (Throwable ex) {
	
							logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
							queue.setAttempts(queue.getAttempts() + 1);
							if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
								queue.setStatus(WtqStatus.FAIL);
							}
							wtqBmo.updateQueue(queue);
							continue;
						}
						logger.info("suspended wt ahl: " + wtId);
						queue.setStatus(WtqStatus.SUCCESS);
						wtqBmo.updateQueue(queue);
						ohd.getWtFile().setWt_status(WTStatus.SUSPENDED);
						ohdBmo.updateOhdNoAudit(ohd);
					} else {
						queue.setStatus(WtqStatus.CANCELED);
						wtqBmo.updateQueue(queue);
					}
				}
				else if(queue instanceof WtqReinstateOhd) {
					String wtId = null;
					OHD ohd = ((WtqOhdAction) queue).getOhd();
					try {
						wtId = wtService.reinstateOhd(ohd, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);				
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage(), ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("suspended wt ahl: " + wtId);
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
					ohd.getWtFile().setWt_status(WTStatus.ACTIVE);
					ohdBmo.updateOhdNoAudit(ohd);

				}
				else if(queue instanceof WtqFwdGeneral) {
					try {
						String result = wtService.sendFwdMsg((WtqFwdGeneral) queue, queue.getAgent() == null ? defaultWtAgent : queue.getAgent(), dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						logger.warn("unable to send fwd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to send fwd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to send fwd", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to send fwd:", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("send fwd Message");
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
				}
				else if(queue instanceof WtqFwdOhd) {
					try {
						String result = wtService.forwardOhd((WtqFwdOhd) queue, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						logger.warn("unable to fwd ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to fwd ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to fwd ohd; captcha exception", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to fwd ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("forwarded ohd: " + ((WtqFwdOhd) queue).getOhd().getOHD_ID());
					queue.setStatus(WtqStatus.SUCCESS);
					OHD ohd = ((WtqFwdOhd) queue).getOhd();
					if(ohd.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED) {
						Status s = new Status();
						s.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
						ohd.setStatus(s);
						ohdBmo.updateOhdNoAudit(ohd);
					}
					wtqBmo.updateQueue(queue);
					// need to close the OHD if it has been forwarded.

				}
				else if(queue instanceof WtqRequestQoh) {
					try {
						WtqRequestQoh wtq = (WtqRequestQoh) queue;
						// this one is a little complicated. gotta have an ahl and
						// an ohd
						// then we need to map them up...
						String result = wtService.requestQoh(wtq, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						logger.warn("unable to request qoh", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to request qoh", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to request qoh", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to request qoh", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("requested qoh: " + ((WtqRequestQoh) queue).getBagTagNumber());
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
				}
				else if(queue instanceof WtqRequestOhd) {
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
						String result = wtService.requestOhd(wtq, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						logger.warn("unable to request ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						logger.warn("unable to request ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to request ohd", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						logger.warn("unable to request ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("requested ohd: " + ((WtqRequestOhd) queue).getWt_ohd());
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
				}
				else if(queue instanceof WtqAmendAhl) {
					Incident incident = null;
					try {
						incident = ((WtqIncidentAction) queue).getIncident();
						if (incident != null && incident.getTracingStatus() != TracingConstants.INCIDENT_TRACING_STATUS_TRACING) {
							String result = wtService.amendAhl(incident, dto);
						}
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerAlreadyClosedException ex) {
						queue.setStatus(WtqStatus.CANCELED);
						incident.getWtFile().setWt_status(WTStatus.CLOSED);
						wtqBmo.updateQueue(queue);
						try {
							iBmo.updateIncidentNoAudit(false, incident);
						} catch (HibernateException e) {
							e.printStackTrace();
						} catch (StaleStateException e) {
							//loupas - should never reach this since the update is ignoring the stale state
							e.printStackTrace();
						}
						continue;
					}
					catch (WorldTracerException ex) {
						
						logger.warn("unable to amend ahl", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						
						logger.warn("unable to amend ahl", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to amend ahl", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						
						logger.warn("unable to amend ahl", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					if (incident == null) {
						queue.setStatus(WtqStatus.FAIL);
					} else if (incident.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING) {
						queue.setStatus(WtqStatus.LOCKED);
					} else {
						logger.info("amended AHL: " + ((WtqAmendAhl) queue).getIncident().getIncident_ID());
						queue.setStatus(WtqStatus.SUCCESS);
					}
					wtqBmo.updateQueue(queue);
				}
				else if(queue instanceof WtqAmendOhd) {
					OHD ohd = null;
					try {
						ohd = ((WtqOhdAction) queue).getOhd();
						String result = wtService.amendOhd(ohd, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						
						logger.warn("unable to amend ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						
						logger.warn("unable to amend ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to amend ohd", ex);					
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						
						logger.warn("unable to amend ohd", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("amended ohd: " + ((WtqAmendOhd) queue).getOhd().getOHD_ID());
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
				}
				else if(queue instanceof WtqCreateBdo) {
					BDO bdo;
					try {
						bdo = ((WtqCreateBdo) queue).getBdo();
						if(bdo == null) {
							throw new WorldTracerException("Cannot export BDO, invalid bdo referenced in queue");
						}
						String result = wtService.insertBdo(bdo, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						
						logger.warn("unable to insert bdo", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						
						logger.warn("unable to insert bdo", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to insert bdo", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						
						logger.warn("unable to insert bdo", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("sent bdo to worldtracer: " + bdo.getBDO_ID());
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
				}
				else if(queue instanceof WtqEraseActionFile) {
					//TODO loupas update me
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
							wtService.eraseActionFile(waf, dto);
							wafBmo.deleteActionFile(waf);
							eraseTask.setStatus(WtqStatus.SUCCESS);
						}
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to delete action file for eraseTask task:", ex);						
						throw new CaptchaException();
					} 
					catch (Exception e) {
						logger.error("unable to delete action file for eraseTask task: " + eraseTask.getWt_queue_id(), e);
					} finally {
						try {
							if (eraseTask != null) {
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
				else if(queue instanceof WtqRequestPxf) {
					String result = "";
					try {
						WtqRequestPxf wtq = (WtqRequestPxf) queue;
						// we need to map them up...
						result = wtService.sendPxf(wtq, dto);
					}
					catch(WorldTracerLoggedOutException ex) {
						errorHandler.sendEmail("Unable to Login", ex, false, false);
						logger.warn("weren't able to login so sleeping for 5 minutes");
						Thread.sleep(5 * 60 * 1000);
						continue;
					}
					catch (WorldTracerException ex) {
						
						logger.warn("unable to send pxf", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					catch (WorldTracerConnectionException ex) {
						
						logger.warn("unable to send pxf", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					} catch (CaptchaException ex) {
						logger.warn("unable to send pxf", ex);						
						throw new CaptchaException();
					} 
					catch (Throwable ex) {
						
						logger.warn("unable to send pxf", ex);
						queue.setAttempts(queue.getAttempts() + 1);
						if(queue.getAttempts() >= WorldTracerQueueSweeper.MAX_ATTEMPTS) {
							queue.setStatus(WtqStatus.FAIL);
						}
						wtqBmo.updateQueue(queue);
						continue;
					}
					logger.info("pxf sent !" + result);
					queue.setStatus(WtqStatus.SUCCESS);
					wtqBmo.updateQueue(queue);
				}
			}
		} catch (InterruptedException e) {
			logger.error("Queue Worker Failure: ", e);
			throw new RuntimeException(e);
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
