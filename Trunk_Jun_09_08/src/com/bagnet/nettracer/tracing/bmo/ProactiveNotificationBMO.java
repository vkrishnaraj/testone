package com.bagnet.nettracer.tracing.bmo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.dto.PcnSearchDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;

public class ProactiveNotificationBMO {
	private static Logger logger = Logger.getLogger(ProactiveNotificationBMO.class);
	
	public static int getCount(PcnSearchDTO dto, Agent user) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Criteria cri = sess.createCriteria(ProactiveNotification.class);
			
			if (dto.getDestinationStation() != 0) {
				cri.add(Expression.eq("destinationStation.station_ID", dto.getDestinationStation()));
			}
			
			if (dto.getStatus_ID() != 0) {
				cri.add(Expression.eq("status.status_ID", dto.getStatus_ID()));
			}
			
			if (dto.getMissedFlightAirline() != null && dto.getMissedFlightAirline().trim().length() > 0) {
				cri.add(Expression.eq("missedFlightAirline", dto.getMissedFlightAirline()));
			}

			if (dto.getMissedFlightNumber() != null && dto.getMissedFlightNumber().trim().length() > 0) {
				String mfn = dto.getMissedFlightNumber();
				if (mfn.length() <= 3 && !mfn.contains("%")) {
					mfn = "%" + mfn;
				}
				cri.add(Expression.like("missedFlightNumber", mfn));
			}

			if (dto.getMissedFlightDate() != null && dto.getMissedFlightDate().trim().length() > 0) {
				Date start = DateUtils.convertToDate(dto.getMissedFlightDate().trim(), user.getDateformat().getFormat(), user
				.getCurrentlocale());
		
				Date end = new Date(start.getTime() + 24*60*60*1000);
				
				cri.add(Expression.eq("missedFlightDate", start));
			}
			
			
			cri.setProjection(Projections.rowCount());
			return (Integer) cri.uniqueResult();

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static List<ProactiveNotification> get(PcnSearchDTO dto, Agent user) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Criteria cri = sess.createCriteria(ProactiveNotification.class);
			
			if (dto.getDestinationStation() != 0) {
				cri.add(Expression.eq("destinationStation.station_ID", dto.getDestinationStation()));
			}
			
			if (dto.getStatus_ID() != 0) {
				cri.add(Expression.eq("status.status_ID", dto.getStatus_ID()));
			}
			
			if (dto.getMissedFlightAirline() != null && dto.getMissedFlightAirline().trim().length() > 0) {
				cri.add(Expression.eq("missedFlightAirline", dto.getMissedFlightAirline()));
			}

			if (dto.getMissedFlightNumber() != null && dto.getMissedFlightNumber().trim().length() > 0) {
				String mfn = dto.getMissedFlightNumber();
				if (mfn.length() <= 3 && !mfn.contains("%")) {
					mfn = "%" + mfn;
				}
				cri.add(Expression.like("missedFlightNumber", mfn));
			}

			if (dto.getMissedFlightDate() != null && dto.getMissedFlightDate().trim().length() > 0) {
				Date start = DateUtils.convertToDate(dto.getMissedFlightDate().trim(), user.getDateformat().getFormat(), user.getCurrentlocale());
				Date end = new Date();
				end.setTime(start.getTime() + 24*60*60*1000);
				cri.add(Expression.ge("missedFlightDate", start));
				cri.add(Expression.lt("missedFlightDate", end));
			}
			
			return cri.list();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void receivedForward(OHD_Log ohdLog, Session sess) {
		
	}
	
	public static void checkClosePcn(ProactiveNotification pcn, Session sess, OHD_Log ohdLog) {
		boolean close = true;
		for (OHD_Log log: pcn.getOhd_logs()) {
			if (log.getOhd().getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED &&
					log.getLog_status() == TracingConstants.LOG_NOT_RECEIVED && 
					(log == null || ohdLog.getOHDLog_ID() != log.getOHDLog_ID())) {
				close = false;
				break;
			}
		}
		
		if (close) {
			Status closed = new Status();
			closed.setStatus_ID(ProactiveNotification.STATUS_CLOSED);
			pcn.setStatus(closed);
			sess.update(pcn);
		}
	}
	
	public static void closedOhd(OHD ohd) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			Criteria cri = sess.createCriteria(OHD_Log.class);
			cri.add(Expression.eq("ohd.OHD_ID", ohd.getOHD_ID()));
			List<OHD_Log> list = (List<OHD_Log>)cri.list();
			
			// For each OHD_Log, check PCN
			for (OHD_Log log: list) {
				if (log.getPcn() != null) {
					boolean close = true;
					ProactiveNotification pcn = log.getPcn();
					
					for (OHD_Log olog: pcn.getOhd_logs()) {
						if (olog.getOhd().getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED &&
								olog.getLog_status() == TracingConstants.LOG_NOT_RECEIVED) {
							close = false;
							break;
						}
					}

					if (close) {
						t = sess.beginTransaction();
						Status closed = new Status();
						closed.setStatus_ID(ProactiveNotification.STATUS_CLOSED);
						pcn.setStatus(closed);
						sess.update(pcn);
						t.commit();
					}
				}
			}
		} catch (Exception e) {
			
			logger.error("Exception encountered loading & closing PNC", e);
			
			if (t != null) {
				t.rollback();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
	
	
	public static void closePcns(String[] items) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			for (String item: items) {
				ProactiveNotification pcn = (ProactiveNotification) sess.load(ProactiveNotification.class, Long.parseLong(item.trim()));
				Status closed = new Status();
				closed.setStatus_ID(ProactiveNotification.STATUS_CLOSED);
				pcn.setStatus(closed);
				t = sess.beginTransaction();
				sess.update(pcn);
				t.commit();
			}
		} catch (Exception e) {
			
			logger.error("Exception encountered loading & closing PNC", e);
			
			if (t != null) {
				t.rollback();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
	
	public static void printPcns(String[] items, String address) {
		Session sess = null;
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			for (String item: items) {
				ProactiveNotification pcn = (ProactiveNotification) sess.load(ProactiveNotification.class, Long.parseLong(item.trim()));
				
	  		try {
	  			SpringUtils.getClientEventHandler().printPcn(address, pcn);
	  		} catch (Exception e) {
	  			logger.error("Error performing PCN lookup...");
	  			e.printStackTrace();
	  		}
			}
		} catch (Exception e) {
			logger.error("Exception encountered loading & printing PNC", e);

		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
	
	
	public static ProactiveNotification get(long id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			return (ProactiveNotification) sess.load(ProactiveNotification.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void setIncidentId(long pcn_id, Incident incident) {
		Session sess = null;
		Transaction t = null;
		
		if (incident == null || incident.getIncident_ID() == null || incident.getIncident_ID().length() < 13) {
			return;
		}
		
		try {
			sess = HibernateWrapper.getSession().openSession();
			ProactiveNotification pcn = (ProactiveNotification) sess.load(ProactiveNotification.class, pcn_id);
			pcn.setIncident(incident);
			
			Status closed = new Status();
			closed.setStatus_ID(ProactiveNotification.STATUS_CLOSED);
			pcn.setStatus(closed);
			t = sess.beginTransaction();
			sess.update(pcn);
			t.commit();
			
		} catch (Exception e) {
			logger.error("Exception encountered loading & updating PNC", e);

			if (t != null) {
				t.rollback();
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
}
