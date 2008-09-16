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
import com.bagnet.nettracer.tracing.db.WorldTracerFile;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqAmendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCloseOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqCreateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqIncidentAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqOhdAction;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqReinstateOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendAhl;
import com.bagnet.nettracer.tracing.db.wtq.WtqSuspendOhd;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.MessageUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.WorldTracerConnectionException;
import com.bagnet.nettracer.wt.svc.WorldTracerService;

public class WorldTracerQueueSweeper {
	
	private static final Logger logger = Logger.getLogger(WorldTracerQueueSweeper.class);
	
	private static final int MAX_ATTEMPTS = 10;
	
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
		List<WorldTracerQueue> qTasks = null;
		try {
			qTasks = wtqBmo.findAllPendingTasks(companyCode);
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (WorldTracerQueue queue : qTasks) {
			if(queue instanceof WtqCreateAhl) {
				String wtId = null;
				Incident incident = ((WtqIncidentAction)queue).getIncident();
				try {
					wtId = wtService.insertIncident(incident);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to export incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("created wt ahl: " + wtId);
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
				incident.setWtFile(new WorldTracerFile(wtId));
				MessageUtils.sendmessage(incident.getStationcreated(), "WT AHL Created", incident.getAgent(),
						String.format("NetTracer Lost/Delay: %s -> WorldTracer AHL: %s", incident.getIncident_ID(), wtId),
						incident.getIncident_ID(), "");
				iBmo.updateIncidentNoAudit(incident);
			}
			else if (queue instanceof WtqCreateOhd) {
				String wtId = null;
				OHD ohd = ((WtqOhdAction)queue).getOhd();
				try {
					wtId = wtService.insertOhd(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to export ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("created wt ohd: " + wtId);
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
				ohd.setWtFile(new WorldTracerFile(wtId));
				MessageUtils.sendmessage(ohd.getFoundAtStation(), "WT OHD Created", ohd.getAgent(),
						String.format("NetTracer OHD: %s  -> WorldTracer OHD: %s", ohd.getOHD_ID(), wtId),
						"", ohd.getOHD_ID());
				ohdBmo.insertOHD(ohd, ogadmin);
			}
			else if (queue instanceof WtqCloseAhl) {
				Incident incident = ((WtqIncidentAction)queue).getIncident();
				String result = "";
				try {
					result = wtService.closeIncident(incident);
				} catch  ( WorldTracerException ex) {
					logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					logger.warn("unable to close incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("closed wt ahl: " + result);
				incident.getWtFile().setWt_status(WTStatus.CLOSED);
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
				iBmo.updateIncidentNoAudit(incident);
			}
			else if (queue instanceof WtqCloseOhd) {
				String wtId = null;
				OHD ohd = ((WtqOhdAction)queue).getOhd();
				try {
					wtId = wtService.closeOHD(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to close wt ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					logger.warn("unable to close ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
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
			else if (queue instanceof WtqSuspendAhl) {
				String wtId = null;
				Incident incident = ((WtqIncidentAction)queue).getIncident();
				try {
					wtId = wtService.suspendIncident(incident);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to supsend incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to suspend incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("suspended wt ahl: " + wtId);
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
				incident.getWtFile().setWt_status(WTStatus.SUSPENDED);
				iBmo.updateIncidentNoAudit(incident);
			}
			else if (queue instanceof WtqReinstateAhl) {
				String wtId = null;
				Incident incident = ((WtqIncidentAction)queue).getIncident();
				try {
					wtId = wtService.reinstateIncident(incident);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to reinstate incident: " + incident.getIncident_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("reinstated wt ahl: " + wtId);
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
				incident.getWtFile().setWt_status(WTStatus.ACTIVE);
				iBmo.updateIncidentNoAudit(incident);
			}
			else if (queue instanceof WtqSuspendOhd) {
				String wtId = null;
				OHD ohd = ((WtqOhdAction)queue).getOhd();
				try {
					wtId = wtService.suspendOhd(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to supsend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to suspend ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
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
			}
			else if (queue instanceof WtqReinstateOhd) {
				String wtId = null;
				OHD ohd = ((WtqOhdAction)queue).getOhd();
				try {
					wtId = wtService.reinstateOhd(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to reinstate ohd: " + ohd.getOHD_ID() + " - " + ex.getMessage());
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
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
			else if (queue instanceof WtqFwdGeneral) {
				try {
					String result = wtService.sendFwdMsg((WtqFwdGeneral)queue);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to send fwd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to send fwd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to send fwd:", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("send fwd Message");
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
			}
			else if (queue instanceof WtqFwdOhd) {
				try {
					String result = wtService.forwardOhd((WtqFwdOhd) queue);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to fwd ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to fwd ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to fwd ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("forwarded ohd: " + ((WtqFwdOhd)queue).getOhd().getOHD_ID());
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
			}
			else if (queue instanceof WtqRequestOhd) {
				OHD ohd = null;
				try {
					WtqRequestOhd wtq = (WtqRequestOhd) queue;
					//this one is  a little complicated.  gotta have an ahl and an ohd
					//then we need to map them up...
					ohd = WorldTracerUtils.findOHDByWTID(wtq.getWt_ohd());
					if(ohd == null) {
						//need to import
						ohd = wtService.getOhdforOhd(wtq.getWt_ohd(), WTStatus.ACTIVE);
						ohdBmo.insertOHD(ohd, ogadmin);
					}
					String result = wtService.requestOhd(wtq);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to request ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to request ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to request ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("requested ohd: " + ((WtqFwdOhd)queue).getOhd().getOHD_ID());
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
			}
			else if (queue instanceof WtqAmendAhl) {
				try {
					Incident incident = ((WtqIncidentAction)queue).getIncident();
					String result = wtService.amendAhl(incident);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to amend ahl", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to amend ahl", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to amend ahl", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("forwarded ohd: " + ((WtqFwdOhd)queue).getOhd().getOHD_ID());
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
			}
			else if (queue instanceof WtqAmendOhd) {
				try {
					OHD ohd = ((WtqOhdAction)queue).getOhd();
					String result = wtService.amendOhd(ohd);
				}
				catch  ( WorldTracerException ex) {
					//TODO
					logger.warn("unable to amend ohd", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch(WorldTracerConnectionException ex) {
					//TODO
					logger.warn("unable to amend ahl", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				} catch (Throwable ex) {
					//TODO
					logger.warn("unable to amend ahl", ex);
					queue.setAttempts(queue.getAttempts() + 1);
					if(queue.getAttempts() >= MAX_ATTEMPTS) {
						queue.setStatus(WtqStatus.FAIL);
					}
					wtqBmo.updateQueue(queue);
					continue;
				}
				logger.info("forwarded ohd: " + ((WtqFwdOhd)queue).getOhd().getOHD_ID());
				queue.setStatus(WtqStatus.SUCCESS);
				wtqBmo.updateQueue(queue);
			}
			try {
				Thread.sleep(wtHitDelay * 1000);
			} catch (InterruptedException e) {
				logger.warn("sleep was interrupted", e);
			}
		}
		
		try {
			Thread.sleep(wtEndDelay * 1000);
		} catch (InterruptedException e) {
			logger.warn("sleep was interrupted", e);
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
