package com.bagnet.nettracer.tracing.bmo;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ForwardNotice;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;

public class ForwardNoticeBMO {
	
	private static Logger logger = Logger.getLogger(ForwardNoticeBMO.class);

	public static void createForwardNotice(OHD_Log log, List<String> stations, Agent user) {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			for (String stationCode : stations) {
				Station  station= null;
				try {
					station = StationBMO.getStationByCode(stationCode, user.getStation().getCompany().getCompanyCode_ID());
				} catch (Exception e) {
					logger.error("Station does not exist and cannot be notified: ", e);
				}
				if (station != null) {
					createForwardNotices(log, station, sess);
				}
			}
		} finally {
			sess.close();
		}
	}

	private static void createForwardNotices(OHD_Log log, Station station, Session sess) {
		ForwardNotice notice = new ForwardNotice();
		notice.setForward(log);
		notice.setStation(station);
		HibernateUtils.save(notice, sess);
	}
	
	public static void closeForwardNotice(String[] noticeIds) {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			for (String noticeId : noticeIds) {
				ForwardNotice notice = (ForwardNotice) sess.load(ForwardNotice.class, Long.parseLong(noticeId));
				closeForwardNotices(notice, sess);
			}
		} finally {
			sess.close();
		}
	}

	public static void closeForwardNotices(ForwardNotice notice, Session sess) {
		notice.setStatus(ForwardNotice.CLOSED_STATUS);
		HibernateUtils.save(notice, sess);
	}

	public static long getForwardsForStationCount(Station station, int status) {

		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Criteria cri = sess.createCriteria(ForwardNotice.class).add(
					Expression.eq("station.station_ID", station.getStation_ID()));
			if (status != -1) {
				cri.add(Expression.eq("status", status));
			}
			cri.setProjection(Projections.rowCount());
			return (Long) cri.uniqueResult();

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

	@SuppressWarnings("unchecked")
	public static List<ForwardNotice> getForwardsForStation(Station station, int rowsperpage, int currpage, int status) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(ForwardNotice.class);
			cri.add(Expression.eq("station.station_ID", station.getStation_ID()));
			
			if (status != -1) {
				cri.add(Expression.eq("status", status));
			}
			//cri.addOrder(Order.asc("forward.forward_time"));
			
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
}
