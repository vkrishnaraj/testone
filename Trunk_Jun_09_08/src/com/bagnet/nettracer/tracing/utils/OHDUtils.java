/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.ProactiveNotificationBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.ProactiveNotification;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.forms.ViewCreatedRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewIncomingRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewMassOnHandsForm;
import com.bagnet.nettracer.tracing.forms.ViewRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewTemporaryOnHandsForm;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class OHDUtils {
	private static Logger logger = Logger.getLogger(OHDUtils.class);

	@SuppressWarnings("rawtypes")
	public static List getRequests(int station_id, ViewRequestForm form, String sort,
			int rowsperpage, int currpage) {
		Session sess = null;
		try {
	
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select request from "
					+ "com.bagnet.nettracer.tracing.db.OHDRequest request where 1=1 ";
			sql += " and request.ohd.holdingStation.station_ID = :station_ID";
			sql += " and request.status.status_ID = :status_ID";
	
			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql += " and request.ohd.OHD_ID like :ohdnum";
			}
	
			if (form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				sql += " and request.incident_ID like :incident";
			}
	
			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase("ohd_num")) {
					sql += " order by request.ohd.OHD_ID asc ";
				} else if (sort.equalsIgnoreCase("incident_num")) {
					sql += " order by request.incident_ID asc ";
				} else if (sort.equalsIgnoreCase("bagtag")) {
					sql += " order by request.ohd.claimnum asc ";
				}
			} else {
				sql += " order by request.requestTime desc ";
			}
	
			Query q = sess.createQuery(sql);
	
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
	
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.OHD_STATUS_OPEN);
	
			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				q.setString("ohdnum", form.getOhd_num());
			}
			if (form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				q.setString("incident", form.getIncident_num());
			}
			return q.list();
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
	
	public static int getRequestsCount(int station_id, ViewRequestForm form) {
		return getRequestsCount(station_id, form, false);
	}

	public static int getRequestsCount(int station_id, ViewRequestForm form, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(request.ohd_request_id) from "
					+ "com.bagnet.nettracer.tracing.db.OHDRequest request where 1=1 ";
			sql += " and request.ohd.holdingStation.station_ID = :station_ID";
			sql += " and request.status.status_ID = :status_ID";
	
			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql += " and request.ohd.OHD_ID like :ohdnum";
			}
	
			if (form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				sql += " and request.incident_ID like :incident";
			}
	
			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.OHD_STATUS_OPEN);
	
			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				q.setString("ohdnum", form.getOhd_num());
			}
	
			if (form.getIncident_num() != null && form.getIncident_num().length() > 0) {
				q.setString("incident", form.getIncident_num());
			}
	
			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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

	@SuppressWarnings("unchecked")
	public static List<OHDRequest> getOHDRequests(String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("ohd").add(Restrictions.eq("OHD_ID", ohd_id));
			cri.addOrder(Order.asc("ohd_request_id"));
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

	@SuppressWarnings("unchecked")
	public static List<OHDRequest> getRequests(String ohd_id, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("status").add(
					Restrictions.eq("status_ID", new Integer(TracingConstants.OHD_STATUS_OPEN)));
			cri.createCriteria("ohd").add(Restrictions.eq("OHD_ID", ohd_id));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
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

	@SuppressWarnings("unchecked")
	public static List<OHDRequest> getIncidentRequests(String incident_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class).add(
					Restrictions.eq("incident_ID", incident_id));
			cri.addOrder(Order.asc("ohd_request_id"));
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

	@SuppressWarnings("unchecked")
	public static List<OHDRequest> getCreatedRequests(Agent user, int rowsperpage, int currpage,
			ViewCreatedRequestForm form, Station agent_station) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);

			String status = form.getStatus();
			String s_time = form.getS_time();
			String e_time = form.getE_time();
			String ohd_num = form.getOhd_num();
			String incident_ID = form.getIncident_ID();

			if (status != null && !status.equals("") && !status.equals("-1")) {
				cri.createCriteria("status").add(Restrictions.eq("status_ID", new Integer(status)));
			}

			if (s_time != null && !s_time.equals("")) {
				if (e_time != null && !e_time.equals("")) {
					if (s_time.equals(e_time)) cri.add(Restrictions.eq("requestTime", s_time));
					else cri.add(Restrictions.between("requestTime", s_time, e_time));
				} else {
					cri.add(Restrictions.ge("requestTime", s_time));
				}
			}
			if (ohd_num != null && ohd_num.length() > 0) {
				cri.createCriteria("ohd").add(Restrictions.like("OHD_ID", ohd_num));
			}
			if (incident_ID != null && incident_ID.length() > 0) {
				cri.add(Restrictions.like("incident_ID", incident_ID));
			}
			cri.createCriteria("requestForStation").add(
					Restrictions.eq("station_ID", new Integer(agent_station.getStation_ID())));
			cri.addOrder(Order.desc("requestTime"));
			cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
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

	@SuppressWarnings("unchecked")
	public static List<OHDRequest> getCreatedRequests(int station_id, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("requestForStation").add(
					Restrictions.eq("station_ID", new Integer(station_id)));
			cri.addOrder(Order.desc("requestTime"));
			cri.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
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
	
	@SuppressWarnings("unchecked")
	public static List<OHDRequest> getCreatedRequestsForOHD(int station_id, String OHD_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("requestForStation").add(
					Restrictions.eq("station_ID", new Integer(station_id)));
			
			cri.createCriteria("ohd").add(Restrictions.eq("OHD_ID",OHD_ID));
			
			cri.addOrder(Order.desc("requestTime"));

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

	public static int getCreatedRequestsCount(int station_id) {
		return getCreatedRequestsCount(station_id, false);
	}
	
	
	public static int getCreatedRequestsCount(int station_id, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(request.ohd_request_id) from "
					+ "com.bagnet.nettracer.tracing.db.OHDRequest request where 1=1 ";
			sql += " and request.requestForStation.station_ID = :station_ID";
			sql += " and request.status.status_ID = :openStatus";

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("openStatus", TracingConstants.OHD_STATUS_OPEN);
			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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

	public static int getCreatedRequestsCount(Agent user, ViewCreatedRequestForm form,
			Station agent_station) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();

			String status = form.getStatus();
			String s_time = form.getS_time();
			String e_time = form.getE_time();
			String ohd_num = form.getOhd_num();
			String incident_ID = form.getIncident_ID();

			String sql = "select count(request.ohd_request_id) from "
					+ "com.bagnet.nettracer.tracing.db.OHDRequest request where 1=1 ";
			sql += " and request.requestForStation.station_ID = :station_ID";

			if (status != null && !status.equals("") && !status.equals("-1")) {
				sql += " and request.status.status_ID = :status_ID";
			}
			if (s_time != null && !s_time.equals("")) {
				if (e_time != null && !e_time.equals("")) {
					if (s_time.equals(e_time)) {
						sql += " and request.requestTime = :s_time";
					} else {
						sql += " and request.requestTime >= :s_time and request.requestTime <= :e_time";
					}
				} else {
					sql += " and request.requestTime >= :s_time";
				}
			}
			if (ohd_num != null && ohd_num.length() > 0) {
				sql += " and request.ohd.OHD_ID like :ohd_num";
			}
			if (incident_ID != null && incident_ID.length() > 0) {
				sql += " and request.incident_ID like :incident_ID";
			}

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", agent_station.getStation_ID());

			if (status != null && !status.equals("") && !status.equals("-1")) {
				q.setInteger("status_ID", Integer.parseInt(status));
			}

			if (s_time != null && !s_time.equals("")) {
				if (e_time != null && !e_time.equals("")) {
					if (s_time.equals(e_time)) {
						q.setDate("s_time", DateUtils.convertToDate(s_time, user.getDateformat().getFormat(),
								user.getCurrentlocale()));
					} else {
						q.setDate("s_time", DateUtils.convertToDate(s_time, user.getDateformat().getFormat(),
								user.getCurrentlocale()));
						q.setDate("e_time", DateUtils.convertToDate(e_time, user.getDateformat().getFormat(),
								user.getCurrentlocale()));
					}
				} else {
					q.setDate("s_time", DateUtils.convertToDate(s_time, user.getDateformat().getFormat(),
							user.getCurrentlocale()));
				}
			}
			if (ohd_num != null && ohd_num.length() > 0) {
				q.setString("ohd_num", ohd_num);
			}
			if (incident_ID != null && incident_ID.length() > 0) {
				q.setString("incident_ID", incident_ID);
			}
			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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

	@SuppressWarnings("unchecked")
	public static List<OHD> getBagsToLZed(int station_id, int rowsperpage, int currpage) {
		Session sess = null;
		Station s = StationBMO.getStation("" + station_id);
		if (s.getCompany().getVariable().getOhd_to_lz_days() <= 0) {
			return new ArrayList<OHD>();
		}

		try {
			sess = HibernateWrapper.getSession().openSession();
			long tillNow = (TracerDateTime.getGMTDate()).getTime();
			long xDaysAgo = tillNow
					- (1000 * 60 * 60 * 24 * s.getCompany().getVariable().getOhd_to_lz_days());
			Date xDaysPrior = new Date(xDaysAgo);
			Criteria cri = sess.createCriteria(OHD.class).add(Restrictions.lt("founddate", xDaysPrior));
			cri.createCriteria("holdingStation")
					.add(Restrictions.eq("station_ID", new Integer(station_id)));
			cri.createCriteria("status").add(
					Restrictions.eq("status_ID", new Integer(TracingConstants.OHD_STATUS_OPEN)));
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				cri.setFirstResult(startnum);
				cri.setMaxResults(rowsperpage);
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

	public static int getBagsToLZedCount(int station_id) {
		return getBagsToLZedCount(station_id, false);
	}
	
	public static int getBagsToLZedCount(int station_id, boolean dirtyRead) {
		Session sess = null;

		Station s = StationBMO.getStation("" + station_id);
		if (s.getCompany().getVariable().getOhd_to_lz_days() <= 0) {
			return 0;
		}

		try {
			long tillNow = (TracerDateTime.getGMTDate()).getTime();

			//find station by id
			long xDaysAgo = tillNow
					- (1000 * 60 * 60 * 24 * s.getCompany().getVariable().getOhd_to_lz_days());
			Date xDaysPrior = new Date(xDaysAgo);

			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(ohd.OHD_ID) from "
					+ "com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ";
			sql += " and ohd.holdingStation.station_ID = :station_ID";
			sql += " and ohd.status.status_ID = :status_ID";
			sql += " and ohd.founddate < :xDaysprior";

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.OHD_STATUS_OPEN);
			q.setDate("xDaysprior", xDaysPrior);
			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
	
	
	

	public static void cancelForward(String ohd_id, Agent a) {
		Session sess = HibernateWrapper.getSession().openSession();
		
		Status openStatus = new Status();
		openStatus.setStatus_ID(TracingConstants.OHD_STATUS_OPEN);		
		
		Transaction t = null;
		try {
			
			// GET ID TO BE UPDATED
			Query q = sess.createQuery("select distinct OHDLog_ID from OHD_Log where ohd.OHD_ID = :ohd_ID and log_status = :status");
			q.setString("ohd_ID", ohd_id);
			q.setInteger("status", TracingConstants.LOG_NOT_RECEIVED);		
			@SuppressWarnings("unchecked")
			List<Integer> logList = (List<Integer>) q.list();
					
			t = sess.beginTransaction();
					
			for (Integer logId: logList) {

				OHD_Log log = (OHD_Log) sess.load(OHD_Log.class, logId);
				log.setLog_status(TracingConstants.LOG_CANCELLED);
				sess.update(log);
				
				// CLOSE PCN  -- MUST OCCUR AFTER UPDATING LOG
				if (log.getPcn() != null) {
					ProactiveNotification pcn = log.getPcn();
					ProactiveNotificationBMO.checkClosePcn(pcn, sess, null);
				}
				
				// UPDATE REQUEST IF NECESSARY
				if (log.getOhd_request_id() != 0) {
					OHDRequest req = (OHDRequest) sess.load(OHDRequest.class, log.getOhd_request_id());
					req.setStatus(openStatus);
					sess.update(req);
				}
			
			}
			
			t.commit();
			sess.close();
			sess = null;
			
			OHD ohd = OhdBMO.getOHDByID(ohd_id, null);
			ohd.setStatus(openStatus);
			
			Remark r = new Remark();
			
			MessageResources messages = MessageResources
			.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			
			r.setAgent(a);
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime
					.getGMTDate()));
			r.setRemarktext(messages.getMessage(new Locale(a.getCurrentlocale()),
					"bagForwardCancelMessage")
					+ " "
					+ a.getStation().getCompany().getCompanyCode_ID()
					+ messages.getMessage(new Locale(a.getCurrentlocale()), "aposS")
					+ " "
					+ a.getStation().getStationcode() + " station.");
			r.setRemarktype(TracingConstants.REMARK_REGULAR);
			r.setOhd(ohd);
			
			if (ohd.getRemarks() == null) 
				ohd.setRemarks(new HashSet<Remark>());
			ohd.getRemarks().add(r);
			
			OhdBMO ohdBmo = new OhdBMO();
			ohdBmo.insertOHD(ohd, a);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (Exception ex) {
				// Fails
				ex.printStackTrace();
			}
			return;
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
	
	

	/**
	 * update ohd_log table to set all status to received for this ohd id
	 * @param ohd_id
	 */
	public static void setLogReceived(OHD ohd) {
		String ohd_id = ohd.getOHD_ID();
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			OHD_Log log = getLastLog(ohd_id);
			t = sess.beginTransaction();
			
			if (log.getPcn() != null) {
				ProactiveNotificationBMO.checkClosePcn(log.getPcn(), sess, log);
				
			}

//			Connection conn = sess.connection();
//			Statement stmt = conn.createStatement();

			String sql = "update ohd_log set log_status = " + TracingConstants.LOG_RECEIVED + " where ohd_id = '" + ohd_id
					+ "' and log_status = " + TracingConstants.LOG_NOT_RECEIVED;
			

			SQLQuery q = sess.createSQLQuery(sql);
			
			q.executeUpdate();
			
//			stmt.execute(sql);
			t.commit();

//			stmt.close();
			
		} catch (Exception e) {
			if (t!= null) {
				t.rollback();
			}
			e.printStackTrace();
		} finally {
			sess.close();
		}
	}
	
	
	/**
	 * Incoming bags
	 * 
	 * @param station_id
	 * @param rowsperpage
	 * @param currpage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<OHD_Log> getIncomingBags(int station_id, ViewIncomingRequestForm form, String sort,
			int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select log from com.bagnet.nettracer.tracing.db.OHD_Log log where 1=1 ";
			sql += " and log.destStationCode = :station_ID";
			sql += " and (log.ohd.status.status_ID = :status_ID or log.ohd.status.status_ID = :status_ID2)";
			sql += " and log.log_status <> " + TracingConstants.LOG_RECEIVED;
			sql += " and log.log_status <> " + TracingConstants.LOG_CANCELLED;

			if (form.getExpedite() != null && form.getExpedite().length() > 0) {
				sql += " and log.expeditenum like :expeditenum";
			}

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql += " and log.ohd.OHD_ID like :ohd_id";
			}

			if (form.getBag_tag() != null && form.getBag_tag().length() > 0) {
				sql += " and log.ohd.claimnum like :claimnum";
			}

			if (sort != null && sort.length() > 0) {
				if (sort.equalsIgnoreCase("ohd")) {
					sql += " order by log.ohd.OHD_ID  asc ";
				} else {
					if (sort.equalsIgnoreCase("expedite")) {
						sql += " order by log.expeditenum asc ";
					} else {
						if (sort.equalsIgnoreCase("bagtag")) {
							sql += " order by log.ohd.claimnum asc ";
						}
					}
				}
			} else {
				sql += " order by log.forward_time desc ";
			}

			Query q = sess.createQuery(sql);

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.OHD_STATUS_IN_TRANSIT);
			q.setInteger("status_ID2", TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT);

			if (form.getExpedite() != null && form.getExpedite().length() > 0) {
				q.setString("expeditenum", form.getExpedite());
			}

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				q.setString("ohd_id", form.getOhd_num());
			}

			if (form.getBag_tag() != null && form.getBag_tag().length() > 0) {
				q.setString("claimnum", form.getBag_tag());
			}
			return q.list();
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
	
	public static int getIncomingBagsCount(int station_id, ViewIncomingRequestForm form) {
		return getIncomingBagsCount(station_id, form, false);
	}

	public static int getIncomingBagsCount(int station_id, ViewIncomingRequestForm form, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(log.OHDLog_ID) from "
					+ "com.bagnet.nettracer.tracing.db.OHD_Log log where 1=1 ";
			sql += " and log.destStationCode = :station_ID";
			sql += " and (log.ohd.status.status_ID = :status_ID or log.ohd.status.status_ID = :status_ID2)";
			sql += " and log.log_status <> " + TracingConstants.LOG_RECEIVED;
			sql += " and log.log_status <> " + TracingConstants.LOG_CANCELLED;

			if (form.getExpedite() != null && form.getExpedite().length() > 0) {
				sql += " and log.expeditenum like :expeditenum";
			}

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql += " and log.ohd.OHD_ID like :ohd_id";
			}

			if (form.getBag_tag() != null && form.getBag_tag().length() > 0) {
				sql += " and log.ohd.claimnum like :claimnum";
			}

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.OHD_STATUS_IN_TRANSIT);
			q.setInteger("status_ID2", TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT);

			if (form.getExpedite() != null && form.getExpedite().length() > 0) {
				q.setString("expeditenum", form.getExpedite());
			}

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				q.setString("ohd_id", form.getOhd_num());
			}

			if (form.getBag_tag() != null && form.getBag_tag().length() > 0) {
				q.setString("claimnum", form.getBag_tag());
			}

			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
	
	public static int getIncomingIncidentsCount(int station_id, SearchIncidentForm form) {
		return getIncomingIncidentsCount(station_id, form, false);
	}

	public static int getIncomingIncidentsCount(int station_id, SearchIncidentForm form, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(incident.incident_ID) from "
					+ "com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ";
			sql += " and incident.stationassigned.station_ID = :station_ID";
			sql += " and (incident.status.status_ID = :status_ID or incident.status.status_ID = :status_ID2 or incident.status.status_ID = :status_ID3)";


			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.MBR_STATUS_OPEN);
			q.setInteger("status_ID2", TracingConstants.MBR_STATUS_PENDING);
			q.setInteger("status_ID3", TracingConstants.MBR_STATUS_TEMP);


			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
	
	//delayed: incidentTypeId=1; pilferage: incidentTypeId=2; damaged: incidentTypeId=3; 
	public static int getIncomingIncidentCount(int station_id, int incidentTypeId, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(incident.incident_ID) from "
					+ "com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ";
			sql += " and incident.itemtype.itemType_ID = :itemType_ID";
			sql += " and incident.stationassigned.station_ID = :station_ID";
			sql += " and (incident.status.status_ID = :status_ID or incident.status.status_ID = :status_ID2 or incident.status.status_ID = :status_ID3)";


			Query q = sess.createQuery(sql);
			q.setInteger("itemType_ID", incidentTypeId);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.MBR_STATUS_OPEN);
			q.setInteger("status_ID2", TracingConstants.MBR_STATUS_PENDING);
			q.setInteger("status_ID3", TracingConstants.MBR_STATUS_TEMP);


			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
	
	//within last 24 hours
	public static int getIncomingIncidentInLast24HoursCount(int station_id, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			String sql = "select count(incident.incident_ID)";
			sql += " from com.bagnet.nettracer.tracing.db.Incident incident ";
			sql += " join incident.incidentControl incidentcontrol";
			sql += " where 1=1 ";
			sql += " and incidentcontrol.assignedDate >= :startassigneddate";
			sql += " and incident.stationassigned.station_ID = :station_ID";
			sql += " and (incident.status.status_ID = :status_ID or incident.status.status_ID = :status_ID2 or incident.status.status_ID = :status_ID3)";


			Query q = sess.createQuery(sql);
			
			int numberOfHoursBack = 24;
			try {
				numberOfHoursBack = PropertyBMO.getValueAsInt(PropertyBMO.PROPERTY_NT_COMPANY_TIME_RANGE_WITHIN_LAST);
			} catch (Exception e) {
				logger.error("unable to retrieve value from properties table.");
			}
			Date myHoursBackFromNowDate = TracerDateTime.getGMTDate();
			myHoursBackFromNowDate.setTime(myHoursBackFromNowDate.getTime() - 60*60*1000*numberOfHoursBack); 
			q.setTimestamp("startassigneddate", myHoursBackFromNowDate);
			
			
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.MBR_STATUS_OPEN);
			q.setInteger("status_ID2", TracingConstants.MBR_STATUS_PENDING);
			q.setInteger("status_ID3", TracingConstants.MBR_STATUS_TEMP);


			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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
	

	public static OHDRequest getRequest(String request_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria criteria = sess.createCriteria(OHDRequest.class).add(
					Restrictions.eq("ohd_request_id", new Integer(request_id)));
			return (OHDRequest) criteria.list().get(0);
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

	@SuppressWarnings("rawtypes")
	public static List getOHDs(Agent user, String sort, ViewTemporaryOnHandsForm form,
			String status_ID, String station_id, int rowsperpage, int currpage, boolean isCount) {
		return getOHDs(user, sort, form, status_ID, station_id, rowsperpage, currpage, isCount, false);
	}
	
	@SuppressWarnings("rawtypes")
	public static List getOHDs(Agent user, String sort, ViewTemporaryOnHandsForm form,
			String status_ID, String station_id, int rowsperpage, int currpage, boolean isCount, boolean dirtyRead) {
		Session sess = null;
		try {
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			StringBuffer sql = new StringBuffer(512);

			if (isCount) sql
					.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1");
			else sql
					.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ");

			sql.append(" and ohd.holdingStation.station_ID = :stationID ");
			sql.append(" and ohd.status.status_ID = :status_ID ");

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql.append(" and ohd.OHD_ID like :ohd_id ");
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			
			if (sdate != null) {
				if (edate != null && sdate != edate) {
					sql.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
						+ " or (ohd.founddate= :enddate1 and ohd.foundtime <= :starttime)"
						+ " or (ohd.founddate > :startdate and ohd.founddate <= :enddate))");

				} else {
					sql.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
							+ " or (ohd.founddate= :startdate1 and ohd.foundtime <= :starttime))");
				}
			}
			


			if (!isCount) {
				if (sort != null && sort.length() > 0) {
					if (sort.equalsIgnoreCase("ohd")) {
						sql.append(" order by ohd.OHD_ID  asc ");
					} else {
						if (sort.equalsIgnoreCase("createdate")) {
							sql.append("  order by ohd.founddate desc ");
						}
					}
				} else {
					sql.append("  order by ohd.founddate desc ");
				}
			}
			
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("stationID", Integer.parseInt(station_id));
			q.setInteger("status_ID", Integer.parseInt(status_ID));

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);
					
				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				q.setString("ohd_id", form.getOhd_num());
			}

			return q.list();
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

	@SuppressWarnings("rawtypes")
	public static List getOHDsByTypeStatus(Agent user, String sort, String ohd_type,
			ViewMassOnHandsForm form, String station_id, int rowsperpage, int currpage, boolean isCount) {
		return getOHDsByTypeStatus(user, sort, ohd_type, form, station_id, rowsperpage, currpage, isCount, false);
	}
	@SuppressWarnings("rawtypes")
	public static List getOHDsByTypeStatus(Agent user, String sort, String ohd_type,
			ViewMassOnHandsForm form, String station_id, int rowsperpage, int currpage, boolean isCount, boolean dirtyRead) {
		Session sess = null;
		try {
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			StringBuffer sql = new StringBuffer(512);

			if (isCount) sql
					.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1");
			else sql
					.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ");

			sql.append(" and ohd.holdingStation.station_ID = :stationID ");
			sql.append(" and ohd.ohd_type = :type ");
			sql.append(" and ohd.status.status_ID != :status ");

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql.append(" and ohd.OHD_ID like :ohd_id ");
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(form.getS_time(),form.getE_time(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			
			if (sdate != null) {
				if (edate != null && sdate != edate) {
					sql.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
						+ " or (ohd.founddate= :enddate1 and ohd.foundtime <= :starttime)"
						+ " or (ohd.founddate > :startdate and ohd.founddate <= :enddate))");

				} else {
					sql.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
							+ " or (ohd.founddate= :startdate1 and ohd.foundtime <= :starttime))");
				}
			}
			
			if (!isCount) {
				if (sort != null && sort.length() > 0) {
					if (sort.equalsIgnoreCase("ohd")) {
						sql.append(" order by ohd.OHD_ID  asc ");
					} else {
						if (sort.equalsIgnoreCase("createdate")) {
							sql.append("  order by ohd.founddate desc ");
						}
					}
				} else {
					sql.append("  order by ohd.founddate desc ");
				}
			}

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setInteger("stationID", Integer.parseInt(station_id));
			q.setInteger("type", Integer.parseInt(ohd_type));
			q.setInteger("status", TracingConstants.OHD_STATUS_CLOSED);

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);
					
				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				q.setString("ohd_id", form.getOhd_num());
			}

			return q.list();
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

	public static OHD getOHD(String ohd_ID) {
		OHD ret = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria criteria = sess.createCriteria(OHD.class);
			criteria.add(Restrictions.eq("OHD_ID", ohd_ID));
			@SuppressWarnings("unchecked")
			List<OHD> results = criteria.list();
			if (results == null || results.size() < 1) {
				return null;
			} else {
				ret = (OHD) results.get(0);
			}
			return ret;
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

	
	public static String getMBRReportNum(OHD ohd, String destCode) {
		if (ohd.getMatched_incident() != null) {
			return ohd.getMatched_incident();
		} else {
			return getMBRReportNum(ohd.getOHD_ID() , destCode);
		}
	}

	public static String getMBRReportNum(String ohd_id, String destCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_Log.class);
			cri.createCriteria("ohd").add(Restrictions.eq("OHD_ID", ohd_id));
			@SuppressWarnings("unchecked")
			List<OHD_Log> forwards = cri.list();
			if (forwards.size() <= 0) return "";

			OHD_Log log = (OHD_Log) forwards.get(0);

			int request_id = log.getOhd_request_id();

			if (request_id <= 0) return "";

			//So request exists...Find out the request based on its it.

			OHDRequest req = OHDUtils.getRequest("" + request_id);

			if (req == null || req.getIncident_ID() == null) return "";
			else return req.getIncident_ID();

		} catch (Exception e) {
			e.printStackTrace();
			return "";
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

	public static OHD_Log getForwardLog(String log_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_Log.class).add(
					Restrictions.eq("OHDLog_ID", new Integer(log_id)));
			return (OHD_Log) cri.list().get(0);
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

	@SuppressWarnings("unchecked")
	public static List<OHD_Log> getForwardMessages(String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_Log.class);
			cri.createCriteria("ohd").add(Restrictions.eq("OHD_ID", ohd_id));
			cri.addOrder(Order.asc("OHDLog_ID"));
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
	
	public static OHD_Log getLastLog(String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_Log.class);
			cri.createCriteria("ohd").add(Restrictions.eq("OHD_ID", ohd_id));
			cri.addOrder(Order.desc("OHDLog_ID"));
			@SuppressWarnings("unchecked")
			List<OHD_Log> result = cri.list();
			System.out.println("!!!!!!!LOG LIST!!!! "+result);
			if (result != null && result.size() > 0)
				return (OHD_Log)result.get(0);
			
			return null;
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

	public static boolean checkCreatedByAgent(String agent_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD.class);
			cri.createCriteria("agent").add(Restrictions.eq("agent_ID", new Integer(agent_id)));
			@SuppressWarnings("unchecked")
			List<OHD> results = cri.list();
			if (results != null && results.size() > 0) return true;
			else return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
	
	/**
	 * this method is used by admin to check if onhand has been held at a station, so this station can't be deleted
	 * @param station_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getHoldingStationList(int station_id, boolean onlyOpenOhds) {
		Session sess = null;
		try {
			String query = "select ohd.OHD_ID from com.bagnet.nettracer.tracing.db.OHD ohd where "
					+ "ohd.holdingStation.station_ID = :station_id";
			// Search for only open incidents for a station
			if (onlyOpenOhds) {
				query = query + " and ohd.status.status_ID <> :status_ID";
			}
			
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("station_id", station_id);
			
			if (onlyOpenOhds) {
				q.setInteger("status_ID", TracingConstants.OHD_STATUS_CLOSED);
			}
			
			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (sess != null)
					sess.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** 
	 * this method is used by admin to check if onhand has been forwarded to a station, so this station can't be deleted
	 * @param station_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getDestStationList(int station_id, boolean onlyOpenOhds) {
		Session sess = null;
		try {
			String query = "select log.destStationCode from com.bagnet.nettracer.tracing.db.OHD_Log log where "
					+ "log.destStationCode = :station_id";
			if (onlyOpenOhds) {
				query += " and log.log_status = :log_status";
				query += " and (log.ohd.status.status_ID = :ohd_status1 or log.ohd.status.status_ID = :ohd_status2)";
			}
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("station_id", station_id);
			
			if (onlyOpenOhds) {
				q.setInteger("log_status", TracingConstants.LOG_NOT_RECEIVED);
				q.setInteger("ohd_status1", TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT);
				q.setInteger("ohd_status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
			}
			
			return q.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (sess != null)
					sess.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static OHD getExistingOnhandWithin24HoursAtStation(String bagTagNumber, Station foundAtStation) {

		List<OHD> list = null;
		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			// GMT Minus 24 hours
			Date dateCutoff = new Date(TracerDateTime.getGMTDate().getTime() - 1*24*60*60*1000);
			
			sql.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ");
			sql.append(" and ohd.holdingStation.station_ID = :stationID ");
			sql.append(" and (ohd.founddate > :foundDate OR (ohd.founddate = :foundDate AND ohd.foundtime >= :foundTime))");
			sql.append(" and ohd.claimnum = :claimnum ");
			sql.append(" and ohd.status.status_ID <> :closedStatus ");
			
			
			Query q = sess.createQuery(sql.toString());

			q.setInteger("stationID", foundAtStation.getStation_ID());
			q.setDate("foundDate", dateCutoff);
			q.setTime("foundTime", dateCutoff);
			q.setString("claimnum", bagTagNumber);
			q.setInteger("closedStatus", TracingConstants.OHD_STATUS_CLOSED);

			list = q.list();

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
		if (list.size() < 1)
			return null;
		else if (list.size() == 1)
			return list.get(0);
		else
			return null;
	}

	public static void cancelRequest(String request_ID, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		
		Status cancelledStatus = new Status();
		cancelledStatus.setStatus_ID(TracingConstants.OHD_REQUEST_CANCELLED);
		
		Transaction t = null;
		try {
			t = sess.beginTransaction();	
			OHDRequest request = (OHDRequest) sess.load(OHDRequest.class, Integer.parseInt(request_ID));
			request.setStatus(cancelledStatus);
			sess.save(request);
			
			OHD ohd = request.getOhd();
			
			Remark r = new Remark();
			MessageResources messages = MessageResources
			.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			
			r.setAgent(user);
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime
					.getGMTDate()));
			r.setRemarktext(messages.getMessage(new Locale(user.getCurrentlocale()),
					"bagRequestCancelMessage")
					+ " "
					+ user.getStation().getCompany().getCompanyCode_ID()
					+ messages.getMessage(new Locale(user.getCurrentlocale()), "aposS")
					+ " "
					+ user.getStation().getStationcode() + " station.");
			r.setRemarktype(TracingConstants.REMARK_REGULAR);
			r.setOhd(ohd);
			
			if (ohd.getRemarks() == null) 
				ohd.setRemarks(new HashSet<Remark>());
			ohd.getRemarks().add(r);

			sess.update(ohd);
			t.commit();
			if (user.getStation().getCompany().getVariable().getAudit_ohd() == 1) {
				Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(ohd, user);
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

			sess.close();
			sess = null;
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (Exception ex) {
				// Fails
				ex.printStackTrace();
			}
			return;
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
	
	@SuppressWarnings("unchecked")
	public static OHD getBagTagNumberIncomingToStation(String bagTagNumber, Station foundAtStation) {

		OHD result = null;
		
		List<OHD_Log> list = null;
		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			
		
			
			sql.append("select log from com.bagnet.nettracer.tracing.db.OHD_Log log");
			sql.append(" where 1=1");
			sql.append(" and log.destStationCode = :station_ID");
			sql.append(" and (log.ohd.claimnum = :claimnum1");
			sql.append(" or log.ohd.claimnum = :claimnum2)");
			sql.append(" and log.log_status = :status");
			
			Query q = sess.createQuery(sql.toString());

			q.setInteger("station_ID", foundAtStation.getStation_ID());
			q.setString("claimnum1", bagTagNumber);
			
			String twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagTagNumber);
			
			q.setString("claimnum2", twoCharBagTag);
			q.setInteger("status", TracingConstants.LOG_NOT_RECEIVED);
			
			list = q.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if (list != null) {
			if (list.size() == 1) {
				result = list.get(0).getOhd();
			}
		}
		return result;
	}
	
	//manage fault dispute
	public static int getDisputeCount(boolean dirtyRead, int lzId, int stationId) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			
			String sql = "select count(dispute.dispute_res_id)";
			sql += " from com.bagnet.nettracer.tracing.db.dr.Dispute dispute where 1=1";
			sql += " and dispute.status = :status";
			sql += " and (dispute.incident.faultstation.station_ID = :station or dispute.incident.faultstation.lz_ID = :lz)";
			
			Query q = sess.createQuery(sql);
			q.setInteger("status", TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN);

			q.setParameter("station", stationId);
			q.setParameter("lz", lzId);
			
			@SuppressWarnings("rawtypes")
			List list = q.list();
			if (list.size() > 0) {
				return ((Long) list.get(0)).intValue();
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
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

	public static OnHandForm cloneOnHand(String ohd_id, Agent user, OnHandForm form) {
		if(ohd_id==null){
			return null;
		}
		
		OHD o=getOHD(ohd_id);
		if(o!=null){
//			BeanUtils.copyProperties(o, co);
//			co.setOHD_ID(null);
			Set<OHD_Passenger> plist=(Set<OHD_Passenger>)o.getPassengers();
			List<OHD_Passenger> nplist=new ArrayList<OHD_Passenger>();
			int i=0;
			for(OHD_Passenger p:plist){
				OHD_Passenger np=form.getPassenger(i);
				//OHD_Passenger np=co.getPassenger();
				np.setFirstname(p.getFirstname());
				np.setLastname(p.getLastname());
				np.setMiddlename(p.getMiddlename());
				@SuppressWarnings("unchecked")
				Set<OHD_Address> oalist=(Set<OHD_Address>)p.getAddresses();
				int j=0;
				for(OHD_Address oa:oalist){
					OHD_Address na=np.getAddress(j);
					na.setAddress1(oa.getAddress1());
					na.setAddress2(oa.getAddress2());
					na.setCity(oa.getCity());
					na.setState_ID(oa.getState_ID());
					na.setProvince(oa.getProvince());
					na.setZip(oa.getZip());
					na.setCountrycode_ID(oa.getCountrycode_ID());
					na.setHomephone(oa.getHomephone());
					na.setWorkphone(oa.getWorkphone());
					na.setMobile(oa.getMobile());
					na.setPager(oa.getPager());
					na.setAltphone(oa.getAltphone());
					na.setEmail(oa.getEmail());
					na.setOhd_passenger(np);
					np.addAddress(na);
					j++;
				}
				
				np.setIsprimary(p.getIsprimary());
				nplist.add(np);
				i++;
			}
			form.setPassengerList(nplist);
			
			form.setFirstname(o.getFirstname());
			form.setLastname(o.getLastname());
			form.setMiddlename(o.getMiddlename());
			form.setPnr(o.getRecord_locator());
			form.setStorage_location(o.getStorage_location());
			if(o.getMembership()!=null){
				form.setCompanycode_ID(o.getMembership().getCompanycode_ID());
				form.setMembershipnum(o.getMembership().getMembershipnum());
				form.setMembershipstatus(o.getMembership().getMembershipstatus());
			}
			
			return form;
		}
		
		
		return null;
	}

	/**
	 * Method to fill the form with existing data from the database for the
	 * purposes of checking for corresponding status and remark requirements
	 * 
	 * @param ohd_id - id of ohd to pull to get currently existing information from
	 * @param theform - the form to be populated
	 */
	public static void populateFormWithExistingData(String ohd_id, OnHandForm theform) {
		OHD ohd=OHDUtils.getOHD(ohd_id);
		populateFormWithExistingData(ohd,theform);
	}

	/**
	 * Method to fill the form with existing data from the database for the
	 * purposes of checking for corresponding status and remark requirements
	 * 
	 * @param ohd - ohd to get currently existing information from
	 * @param theform - the form to be populated
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void populateFormWithExistingData(OHD ohd, OnHandForm theform) {
		if(ohd!=null){
			if(ohd.getMatched_incident()!=null && !ohd.getMatched_incident().isEmpty()){
				Incident inc=IncidentBMO.getIncidentByID(ohd.getMatched_incident(), null);
				if(inc!=null){
					if(inc.getItemlist()!=null){
						theform.setExistMatchedItemlist(new ArrayList(inc.getItemlist()));
					}
					
					if(inc.getItemtype()!=null){
						theform.setExistMatchedItemType(inc.getItemtype().getItemType_ID());
					}
				}
			}
			if(ohd.getDisposal_status()!=null){
				theform.setExistDisposalStatus(ohd.getDisposal_status());
			} else {
				theform.setExistDisposalStatus(new Status());
			}

			if(ohd.getRemarks()!=null){
				theform.setExistRemarkSize(ohd.getRemarks().size());
			} else {
				theform.setExistRemarkSize(1);
			}
			
		}
		
	}
}