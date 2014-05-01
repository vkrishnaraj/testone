/*
 * Created on Aug 30, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

/**
 * @author Ankur Gupta
 * 
 */
@SuppressWarnings("deprecation")
public class MatchUtils {
	private static Logger logger = Logger.getLogger(MatchUtils.class);

	/**
	 * 
	 * @param match
	 */
	public static void updateMatch(Match match) {
		Session sess = null;
		Transaction t = null;
		if (match == null) return;
		try {
			sess = HibernateWrapper.getSession().openSession();

			t = sess.beginTransaction();
			sess.saveOrUpdate(match);
			t.commit();

		} catch (Exception e) {
			logger.error("unable to update match: " + e);
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
	
	public static int getMatchRowCount(boolean isactive, Station station, String[] status,
			String incident_id, String ohd_id) {
		return getMatchRowCount(isactive, station, status, incident_id, ohd_id, false);
	}

	public static int getMatchRowCount(boolean isactive, Station station, String[] status,
			String incident_id, String ohd_id, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}

			int station_id = station.getStation_ID();
			double min_percent = station.getCompany().getVariable().getMin_match_percent();

			String percentq = "";
			if (!isactive) percentq = " and match.match_percent >= :match_percent";

			String statusq = "";
			if (status == null) {
				statusq = " and match.status.status_ID = :status1";

			} else {
				statusq = " and ( ";
				boolean hasstatus = false;

				for (int i = 0; i < status.length; i++) {
					if (status[i] != null && !status[i].equals("")) {
						if (statusq.length() > 8) statusq += " or ";
						statusq += "match.status.status_ID = :status" + i;
						hasstatus = true;
					}
				}

				statusq += " )";

				if (!hasstatus) statusq = "";
			}

			String stationq = "";
			if (incident_id != null && incident_id.length() > 0) stationq = " and match.mbr.incident_ID like :incident_id ";
			if (ohd_id != null && ohd_id.length() > 0) stationq += " and match.ohd.OHD_ID like :ohd_id";
			else stationq += " and match.mbr.stationassigned.station_ID = :station_id";

			String sql = "select count(match.match_id) from "
					+ "com.bagnet.nettracer.tracing.db.Match match where 1=1 " + percentq + statusq
					+ stationq;

			Query q = sess.createQuery(sql);
			if (!isactive) q.setDouble("match_percent", min_percent);
			if (status == null) {
				q.setInteger("status1", TracingConstants.MATCH_STATUS_OPEN);

			} else {

				for (int i = 0; i < status.length; i++) {
					if (status[i] != null && !status[i].equals("")) {
						q.setInteger("status" + i, Integer.parseInt(status[i]));
					}
				}
			}

			if (incident_id != null && incident_id.length() > 0) q.setString("incident_id", incident_id);
			if (ohd_id != null && ohd_id.length() > 0) q.setString("ohd_id", ohd_id);
			else q.setInteger("station_id", station_id);

			@SuppressWarnings("rawtypes")
			List list = q.list();

			// set list to be list of topflight objects
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
	
	@SuppressWarnings("rawtypes")
	public static List getMatches(boolean isactive, Station station, String[] status,
			String incident_id, String ohd_id, int rowsperpage, int currpage, String sort) {
		return getMatches(isactive, station, status, incident_id, ohd_id, rowsperpage, currpage, sort, false);
	}
	

	/**
	 * get passive tracing matches
	 * 
	 * @param station_id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getMatches(boolean isactive, Station station, String[] status,
			String incident_id, String ohd_id, int rowsperpage, int currpage, String sort, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			int station_id = station.getStation_ID();
			double min_percent = station.getCompany().getVariable().getMin_match_percent();

			String percentq = "";
			if (!isactive) percentq = " and match.match_percent >= :match_percent";

			String statusq = "";
			if (status == null) {
				statusq = " and match.status.status_ID = :status1";

			} else {
				statusq = " and ( ";
				boolean hasstatus = false;

				for (int i = 0; i < status.length; i++) {
					if (status[i] != null && !status[i].equals("")) {
						if (statusq.length() > 8) statusq += " or ";
						statusq += "match.status.status_ID = :status" + i;
						hasstatus = true;
					}
				}

				statusq += " )";

				if (!hasstatus) statusq = "";
			}

			String stationq = "";
			if (incident_id != null && incident_id.length() > 0) stationq = " and match.mbr.incident_ID like :incident_id ";
			if (ohd_id != null && ohd_id.length() > 0) stationq += " and match.ohd.OHD_ID like :ohd_id";
			else stationq += " and match.mbr.stationassigned.station_ID = :station_id";

			String sortq = "";
			if (sort != null) {
				if (sort.equals("mbr")) sortq = " order by match.mbr.incident_ID asc";
				if (sort.equals("ohd")) sortq = " order by match.ohd.OHD_ID asc";
				if (sort.equals("date")) sortq = " order by match.match_made_on desc";
			}

			if (sort == null || sort.equals("")) {
				sortq = " order by match.match_percent desc";
			}

			String sql = "from " + "com.bagnet.nettracer.tracing.db.Match match where 1=1 " + percentq
					+ statusq + stationq + sortq;

			Query q = sess.createQuery(sql);

			int startnum = currpage * rowsperpage;
			q.setFirstResult(startnum);
			q.setMaxResults(rowsperpage);

			if (!isactive) q.setDouble("match_percent", min_percent);
			if (status == null) {
				q.setInteger("status1", TracingConstants.MATCH_STATUS_OPEN);

			} else {

				for (int i = 0; i < status.length; i++) {
					if (status[i] != null && !status[i].equals("")) {
						q.setInteger("status" + i, Integer.parseInt(status[i]));
					}
				}
			}

			if (incident_id != null && incident_id.length() > 0) q.setString("incident_id", incident_id);
			if (ohd_id != null && ohd_id.length() > 0) q.setString("ohd_id", ohd_id);
			else q.setInteger("station_id", station_id);

			List list = q.list();

			return list;
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

	public static Match getMatchWithID(String match_ID) {
		return getMatchWithID(match_ID, false);
	}

	public static Match getMatchWithID(String match_ID, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			Criteria cri = sess.createCriteria(Match.class).add(Expression.eq("match_id", new Integer(match_ID)));
			@SuppressWarnings("rawtypes")
			List temp = cri.list();
			if (temp == null || temp.size() == 0) return null;
			return (Match) temp.get(0);
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

	/**
	 * this method is to give all the matches associated with an incident id and
	 * ohd_id
	 * 
	 * @param Incident_ID
	 * @param OHD_ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getMatchWithMBROHD(String Incident_ID, String OHD_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Match.class);
			cri.createCriteria("mbr").add(Expression.eq("mbr_Number", Incident_ID));
			cri.createCriteria("ohd").add(Expression.eq("ohd_id", OHD_ID));

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

	@SuppressWarnings("rawtypes")
	public static List getMatchWithOHD(String OHD_ID) {
		return getMatchWithOHD(OHD_ID, false);
	}
	/**
	 * this method is to give all the matches associated with an ohd_id
	 * 
	 * @param OHD_ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getMatchWithOHD(String OHD_ID, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			Criteria cri = sess.createCriteria(Match.class);
			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", OHD_ID));
			cri.addOrder(Order.asc("match_id"));
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

	
	@SuppressWarnings("rawtypes")
	public static List getMatchWithMBR(String Incident_ID) {
		return getMatchWithMBR(Incident_ID, false);
	}
	/**
	 * this method is to give all the matches associated with an ohd_id
	 * 
	 * @param OHD_ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List getMatchWithMBR(String Incident_ID, boolean dirtyRead) {
		Session sess = null;
		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			Criteria cri = sess.createCriteria(Match.class);
			cri.createCriteria("mbr").add(Expression.eq("incident_ID", Incident_ID));
			cri.addOrder(Order.asc("match_id"));
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

	/**
	 * ohd matched, change status to matched, and close other history with the
	 * same ohd_id
	 * 
	 * @param ohd_ID
	 */
	private static String matchedOHD(int match_ID, OHD ohd) {
		String ohd_id = ohd.getOHD_ID();
		Session sess = null;
		String returnVal = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			Criteria cri = sess.createCriteria(Match.class);

			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));

			@SuppressWarnings("rawtypes")
			List al = cri.list();
			Match match = null;
			Status status_obj = null;
			for (int i = 0; i < al.size(); i++) {
				match = (Match) al.get(i);
				if (match.getMatch_id() == match_ID) {
					status_obj = new Status();
					status_obj.setStatus_ID(TracingConstants.MATCH_STATUS_MATCHED);
					match.setStatus(status_obj);
					returnVal = match.getMbr().getIncident_ID();
				} else {
					status_obj = new Status();
					status_obj.setStatus_ID(TracingConstants.MATCH_STATUS_CLOSED);
					match.setStatus(status_obj);
				}
				//update match
				t = sess.beginTransaction();
				sess.saveOrUpdate(match);
				t.commit();
			}

		} catch (Exception e) {
			logger.error("unable to update match result: " + e);
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
				}
			}
		}
		return returnVal;
	}

	/**
	 * reopen all the matches associated with the report or ohd
	 * 
	 * @param incident_id
	 * @param ohd_id
	 */
	public static void openMatches(String incident_id, String ohd_id) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			Criteria cri = null;
			@SuppressWarnings("rawtypes")
			List al = null;
			Match match = null;

			Status status = null;

			// open all ohd
			if (ohd_id != null) {
				cri = sess.createCriteria(Match.class);
				cri.add(Expression
						.eq("status.status_ID", new Integer(TracingConstants.MATCH_STATUS_CLOSED)));

				cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
				al = cri.list();

				for (int i = 0; i < al.size(); i++) {
					match = (Match) al.get(i);
					status = new Status();
					status.setStatus_ID(TracingConstants.MATCH_STATUS_OPEN);
					match.setStatus(status);
					//update match
					t = sess.beginTransaction();
					sess.saveOrUpdate(match);
					t.commit();
				}
			}

			// open all mbr
			if (incident_id != null) {
				cri = sess.createCriteria(Match.class);
				cri.add(Expression
						.eq("status.status_ID", new Integer(TracingConstants.MATCH_STATUS_CLOSED)));
				cri.createCriteria("mbr").add(Expression.eq("incident_ID", incident_id));
				al = cri.list();
				for (int i = 0; i < al.size(); i++) {
					match = (Match) al.get(i);
					status = new Status();
					status.setStatus_ID(TracingConstants.MATCH_STATUS_OPEN);
					match.setStatus(status);
					//update match
					t = sess.beginTransaction();
					sess.saveOrUpdate(match);
					t.commit();
				}
			}

		} catch (Exception e) {
			logger.error("unable to update match result: " + e);
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * close all open matches with that incident id or ohd_id
	 * 
	 * @param incident_id
	 * @param ohd_id
	 */
	public static void closeMatches(String incident_id, String ohd_id) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			Criteria cri = null;
			@SuppressWarnings("rawtypes")
			List al = null;
			Match match = null;
			Status status = null;

			// close all ohd
			if (ohd_id != null) {
				cri = sess.createCriteria(Match.class);
				cri.add(Expression.eq("status.status_ID", new Integer(TracingConstants.MATCH_STATUS_OPEN)));
				cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));
				al = cri.list();
				for (int i = 0; i < al.size(); i++) {
					match = (Match) al.get(i);
					status = new Status();
					status.setStatus_ID(TracingConstants.MATCH_STATUS_CLOSED);
					match.setStatus(status);
					//update match
					t = sess.beginTransaction();
					sess.saveOrUpdate(match);
					t.commit();
				}
			}

			// close all mbr
			if (incident_id != null) {
				cri = sess.createCriteria(Match.class);
				cri.add(Expression.eq("status.status_ID", new Integer(TracingConstants.MATCH_STATUS_OPEN)));
				cri.createCriteria("mbr").add(Expression.eq("incident_ID", incident_id));
				al = cri.list();
				for (int i = 0; i < al.size(); i++) {
					match = (Match) al.get(i);
					status = new Status();
					status.setStatus_ID(TracingConstants.MATCH_STATUS_CLOSED);
					match.setStatus(status);
					//update match
					t = sess.beginTransaction();
					sess.saveOrUpdate(match);
					t.commit();
				}
			}

		} catch (Exception e) {
			logger.error("unable to update match result: " + e);
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private static void unmatchOHD(String ohd_id) {
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			Criteria cri = sess.createCriteria(Match.class);

			// get all unrejected matches
			cri.add(Expression.not(Expression.eq("status.status_ID", new Integer(
					TracingConstants.MATCH_STATUS_REJECTED))));

			cri.createCriteria("ohd").add(Expression.eq("OHD_ID", ohd_id));

			Status status = null;
			@SuppressWarnings("rawtypes")
			List al = cri.list();
			Match match = null;
			for (int i = 0; i < al.size(); i++) {
				match = (Match) al.get(i);
				status = new Status();
				status.setStatus_ID(TracingConstants.MATCH_STATUS_OPEN);
				match.setStatus(status);
				//update match to status open
				t = sess.beginTransaction();
				sess.saveOrUpdate(match);
				t.commit();
			}

		} catch (Exception e) {
			logger.error("unable to update match result: " + e);
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static void matchToOhd(int matchId, OHD ohd, Agent agent) {
		MatchUtils.matchedOHD(matchId, ohd);
	}

	public static void unmatchTheOHD(String ohd_id, Agent agent) {
		unmatchOHD(ohd_id);
	}
}