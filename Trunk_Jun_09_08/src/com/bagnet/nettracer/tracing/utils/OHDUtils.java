/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.forms.ViewCreatedRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewIncomingRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewMassOnHandsForm;
import com.bagnet.nettracer.tracing.forms.ViewRequestForm;
import com.bagnet.nettracer.tracing.forms.ViewTemporaryOnHandsForm;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class OHDUtils {

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
				} else {
					if (sort.equalsIgnoreCase("incident_num")) {
						sql += " order by request.incident_ID asc ";
					}
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
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
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

	public static List getOHDRequests(String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
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

	public static List getRequests(String ohd_id, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("status").add(
					Expression.eq("status_ID", new Integer(TracingConstants.OHD_STATUS_OPEN)));
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
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

	public static List getIncidentRequests(String incident_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class).add(
					Expression.eq("incident_ID", incident_id));
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

	public static List getCreatedRequests(Agent user, int rowsperpage, int currpage,
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
				cri.createCriteria("status").add(Expression.eq("status_ID", new Integer(status)));
			}

			if (s_time != null && !s_time.equals("")) {
				if (e_time != null && !e_time.equals("")) {
					if (s_time.equals(e_time)) cri.add(Expression.eq("requestTime", s_time));
					else cri.add(Expression.between("requestTime", s_time, e_time));
				} else {
					cri.add(Expression.ge("requestTime", s_time));
				}
			}
			if (ohd_num != null && ohd_num.length() > 0) {
				cri.createCriteria("ohd").add(Expression.like("OHD_ID", ohd_num));
			}
			if (incident_ID != null && incident_ID.length() > 0) {
				cri.add(Expression.like("incident_ID", incident_ID));
			}
			cri.createCriteria("requestForStation").add(
					Expression.eq("station_ID", new Integer(agent_station.getStation_ID())));
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

	public static List getCreatedRequests(int station_id, int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("requestForStation").add(
					Expression.eq("station_ID", new Integer(station_id)));
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
	
	public static List getCreatedRequestsForOHD(int station_id, String OHD_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHDRequest.class);
			cri.createCriteria("requestForStation").add(
					Expression.eq("station_ID", new Integer(station_id)));
			
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID",OHD_ID));
			
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
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(request.ohd_request_id) from "
					+ "com.bagnet.nettracer.tracing.db.OHDRequest request where 1=1 ";
			sql += " and request.requestForStation.station_ID = :station_ID";
			sql += " and request.status.status_ID = :openStatus";

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("openStatus", TracingConstants.OHD_STATUS_OPEN);
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

	public static List getBagsToLZed(int station_id, int rowsperpage, int currpage) {
		Session sess = null;
		Station s = StationBMO.getStation("" + station_id);
		if (s.getCompany().getVariable().getOhd_to_lz_days() <= 0) {
			return new ArrayList();
		}

		try {
			sess = HibernateWrapper.getSession().openSession();
			long tillNow = (TracerDateTime.getGMTDate()).getTime();
			long xDaysAgo = tillNow
					- (1000 * 60 * 60 * 24 * s.getCompany().getVariable().getOhd_to_lz_days());
			Date xDaysPrior = new Date(xDaysAgo);
			Criteria cri = sess.createCriteria(OHD.class).add(Expression.lt("founddate", xDaysPrior));
			cri.createCriteria("holdingStation")
					.add(Expression.eq("station_ID", new Integer(station_id)));
			cri.createCriteria("status").add(
					Expression.eq("status_ID", new Integer(TracingConstants.OHD_STATUS_OPEN)));
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

			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(ohd.OHD_ID) from "
					+ "com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ";
			sql += " and ohd.holdingStation.station_ID = :station_ID";
			sql += " and ohd.status.status_ID = :status_ID";
			sql += " and ohd.founddate < :xDaysprior";

			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.OHD_STATUS_OPEN);
			q.setDate("xDaysprior", xDaysPrior);
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

	/**
	 * update ohd_log table to set all status to received for this ohd id
	 * @param ohd_id
	 */
	public static void setLogReceived(String ohd_id) {
		try {
		Session sess = HibernateWrapper.getSession().openSession();

		Connection conn = sess.connection();
		Statement stmt = conn.createStatement();
		
		String sql = "update ohd_log set log_status = " +
			TracingConstants.LOG_RECEIVED + " where ohd_id = '" + ohd_id + "'";
		stmt.execute(sql);
		stmt.close();
		sess.close();
		} catch (Exception e) {
			e.printStackTrace();
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
	public static List getIncomingBags(int station_id, ViewIncomingRequestForm form, String sort,
			int rowsperpage, int currpage) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select log from com.bagnet.nettracer.tracing.db.OHD_Log log where 1=1 ";
			sql += " and log.destStationCode = :station_ID";
			sql += " and (log.ohd.status.status_ID = :status_ID or log.ohd.status.status_ID = :status_ID2)";
			sql += " and log.log_status <> " + TracingConstants.LOG_RECEIVED;

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
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(log.OHDLog_ID) from "
					+ "com.bagnet.nettracer.tracing.db.OHD_Log log where 1=1 ";
			sql += " and log.destStationCode = :station_ID";
			sql += " and (log.ohd.status.status_ID = :status_ID or log.ohd.status.status_ID = :status_ID2)";
			sql += " and log.log_status <> " + TracingConstants.LOG_RECEIVED;

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
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			String sql = "select count(incident.incident_ID) from "
					+ "com.bagnet.nettracer.tracing.db.Incident incident where 1=1 ";
			sql += " and incident.stationassigned.station_ID = :station_ID";
			sql += " and (incident.status.status_ID = :status_ID or incident.status.status_ID = :status_ID2 or incident.status.status_ID = :status_ID3)";


			Query q = sess.createQuery(sql);
			q.setInteger("station_ID", station_id);
			q.setInteger("status_ID", TracingConstants.MBR_STATUS_OPEN);
			q.setInteger("status_ID2", TracingConstants.MBR_STATUS_PENDING);
			q.setInteger("status_ID3", TracingConstants.MBR_STATUS_TEMP);


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
	
	public static String getStatusDesc(int status_id) {
		String ret = "";

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Status.class).add(
					Expression.eq("status_ID", new Integer(status_id)));
			List retList = cri.list();
			if (retList != null && retList.size() > 0) {
				Status s = (Status) retList.get(0);
				ret = s.getDescription();
			}
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
		return ret;
	}

	public static OHDRequest getRequest(String request_id) {
		OHDRequest ret = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria criteria = sess.createCriteria(OHDRequest.class).add(
					Expression.eq("ohd_request_id", new Integer(request_id)));
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

	public static List getOHDs(Agent user, String sort, ViewTemporaryOnHandsForm form,
			String status_ID, String station_id, int rowsperpage, int currpage, boolean isCount) {
		OHD ret = null;
		Session sess = null;
		try {
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			sess = HibernateWrapper.getSession().openSession();
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
			String dateq = "";
			
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

	public static List getOHDsByType(Agent user, String sort, String ohd_type,
			ViewMassOnHandsForm form, String station_id, int rowsperpage, int currpage, boolean isCount) {
		OHD ret = null;
		Session sess = null;
		try {
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (isCount) sql
					.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1");
			else sql
					.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ");

			sql.append(" and ohd.holdingStation.station_ID = :stationID ");
			sql.append(" and ohd.ohd_type = :type ");

			if (form.getOhd_num() != null && form.getOhd_num().length() > 0) {
				sql.append(" and ohd.OHD_ID like :ohd_id ");
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
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
			criteria.add(Expression.eq("OHD_ID", ohd_ID));
			List results = criteria.list();
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

	public static List getOhdStatusList(String locale) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Status.class).add(Expression.eq("locale", locale)).add(
					Expression.eq("table_ID", new Integer(TracingConstants.TABLE_ON_HAND)));
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

	public static String getMBRReportNum(String ohd_id, String destCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_Log.class);
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
			List forwards = cri.list();
			if (forwards.size() <= 0) return "";

			OHD_Log log = (OHD_Log) forwards.get(0);

			int request_id = log.getOhd_request_id();

			if (request_id <= 0) return "";

			//So request exists...Find out the request based on its it.

			OHDRequest req = OHDUtils.getRequest("" + request_id);

			if (req == null) return "";
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
					Expression.eq("OHDLog_ID", new Integer(log_id)));
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

	public static List getForwardMessages(String ohd_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(OHD_Log.class);
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
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
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
			cri.addOrder(Order.desc("OHDLog_ID"));
			List result = cri.list();
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
			cri.createCriteria("agent").add(Expression.eq("agent_ID", new Integer(agent_id)));
			List results = cri.list();
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
	
	public static OHD getExistingOnhandWithin24HoursAtStation(String bagTagNumber, Station foundAtStation) {

		List<OHD> list = null;
		Session sess = null;
		try {
			
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			GregorianCalendar nowDate = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
			nowDate.add(GregorianCalendar.DATE, -1);
			
			sql.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd where 1=1 ");
			sql.append(" and ohd.holdingStation.station_ID = :stationID ");
			sql.append(" and ohd.founddate >= :foundDate ");
			sql.append(" and ohd.claimnum = :claimnum ");
			sql.append(" and ohd.status.status_ID <> :closedStatus ");
			
			
			Query q = sess.createQuery(sql.toString());

			q.setInteger("stationID", foundAtStation.getStation_ID());
			q.setCalendar("foundDate", nowDate);
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
	
	
	
}