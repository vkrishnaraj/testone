package aero.nettracer.lf.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.lf.LFLog;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class LFLogUtil {
	
	private static Logger logger = Logger.getLogger(LFLogUtil.class);
	
	public final static String EVENT_LOGIN = "LOGIN";
	public final static String EVENT_CREATE = "CREATE";
	public final static String EVENT_MODIFY = "MODIFY";
	public final static String EVENT_CLOSE = "CLOSE";
	public final static String EVENT_ALL = "ALL";
	
	public static void writeLog(String agent, String station, String event, int lost_id, int found_id) {
		LFLog log = new LFLog();
		log.setStamp(new Date()); //(TracerDateTime.getGMTDate());
		log.setAgent(agent);
		log.setStationcode(station);
		log.setEvent(event);
		log.setLflost_id(lost_id);
		log.setLffound_id(found_id);
		
		Session sess = null;
		Transaction t;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.save(log);
			t.commit();
		} catch (Exception e) {
			logger.error("unable to write to LFLog.");
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
	
	public static List<LFLog> searchLog(StatReportDTO srDTO, Agent user) {
		String s_date = srDTO.getStarttime();
		String e_date = srDTO.getEndtime();
		String agent = srDTO.getAgent();
		String station = srDTO.getStationCode();
		String event = srDTO.getEvent();
		return searchLog(s_date, e_date, agent, station, event, user);
	}
	
	@SuppressWarnings("unchecked")
	public static List<LFLog> searchLog(String s_date, String e_date, String agent, String station, String event, Agent user) {
		String q_sdate = "";
		String q_edate = "";
		String q_agent = "";
		String q_station = "";
		String q_event = "";
		if (s_date != null && s_date.length() > 0) {
			q_sdate = " and log.stamp > :sdate ";
		}
		if (e_date != null && e_date.length() > 0) {
			q_edate = " and log.stamp < :edate ";
		}
		if (agent != null && agent.length() > 0) {
			q_agent = " and log.agent = :agent ";
		}
		if (station != null && station.length() > 0) {
			q_station = " and log.stationcode = :station ";
		}
		if (event != null && event.length() > 0 && !event.equals(EVENT_ALL)) {
			q_event = " and log.event = :event ";
		}
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess
			.createQuery("select log from com.bagnet.nettracer.tracing.db.lf.LFLog log"
					+ " where 1=1 " + q_sdate + q_edate + q_agent + q_station + q_event);
			if (q_sdate.length() > 0) {
				Date s_dateDate = DateUtils.convertToDate(s_date, user.getDateformat().getFormat(), null);
				q.setDate("sdate", s_dateDate);
			}
			if (q_edate.length() > 0) {
				Date e_dateDate = DateUtils.convertToDate(e_date, user.getDateformat().getFormat(), null);
				Calendar cal = Calendar.getInstance();
				cal.setTime(e_dateDate);
				cal.add(Calendar.DATE, 1);
				e_dateDate = cal.getTime();
				q.setDate("edate", e_dateDate);
			}
			if (q_agent.length() > 0) {
				q.setString("agent", agent);
			}
			if (q_station.length() > 0) {
				q.setString("station", station);
			}
			if (q_event.length() > 0) {
				q.setString("event", event);
			}
			List<LFLog> toReturn = q.list();
			return toReturn;
		} catch (Exception e) {
			logger.error("search of LFLog failed.");
			e.printStackTrace();
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		
		return null;
	}

}
