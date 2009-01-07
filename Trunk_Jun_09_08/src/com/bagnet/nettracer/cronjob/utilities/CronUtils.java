package com.bagnet.nettracer.cronjob.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class CronUtils {
	
	private static Logger logger = Logger.getLogger(CronUtils.class);
	
	/**
	 * Cron process to close old mass on-hands that:
	 * 1.  Are open, mass onhands(check)
	 * 2.  Founddate <= today - 1 day (check)
	 * 3.  Are in their create station (check)
	 * 4.  Have no outstanding requests for them (check)
	 * 5.  No confirmed matches (check)
	 *   
	 * The process also closes match-results to the on-hand. (check)
	 */
	public void closeUSAirOldMassOhdsInSQLServer() {
		
		// FIRST HALF
		
		Session writeSession = HibernateWrapper.getSession().openSession();
		Session readSess = HibernateWrapper.getDirtySession().openSession();
		
		
		usAirTwentyFourHourBusinessRules(writeSession, readSess);
		usAirFortyEightHourBusinessRules(writeSession, readSess);
		
		// SECOND HALF
		readSess.close();
		writeSession.close();
	}

	private void usAirTwentyFourHourBusinessRules(Session writeSession,
			Session readSess) {
		String sql = "SELECT OHD_ID FROM OHD WHERE STATUS_ID = :openStatus " +
				"AND found_station_ID = holding_station_ID " +
				"AND ohd_type = :ohdType " +
				"AND (founddate < :24HourFoundDate OR( founddate = :24HourFoundDate AND foundtime<= :24HourFoundTime )) " +
				"AND ohd_id NOT IN (select distinct ohd_id from match_history where status_ID = :matchConfirmed)" +
				"AND ohd_id NOT IN (select distinct ohd_id from ohd_request where " +
					"status_ID != :requestClosed and status_ID != :requestDenied)";
		
		
		
		SQLQuery query = readSess.createSQLQuery(sql);
		
		query.setInteger("openStatus", TracingConstants.OHD_STATUS_OPEN);
		query.setInteger("ohdType", TracingConstants.MASS_OHD_TYPE);
		query.setInteger("requestClosed", TracingConstants.OHD_STATUS_CLOSED);
		query.setInteger("requestDenied", TracingConstants.OHD_REQUEST_STATUS_DENIED);
		query.setInteger("matchConfirmed", TracingConstants.MATCH_STATUS_MATCHED);
				
		Calendar hours24 = new GregorianCalendar();
		hours24.add(Calendar.DAY_OF_MONTH, -1);

		Date hours24Date = new Date(hours24.getTime().getTime() + (hours24.getTime().getTimezoneOffset() * 1000 * 60));
		
		query.setDate("24HourFoundDate", hours24Date);
		query.setTime("24HourFoundTime", hours24Date);

		query.addScalar("OHD_ID", Hibernate.STRING);
		
		List<String> list = (List<String>) query.list();
		logger.info("OHDs to close: " + list.size());
		
		Agent ogadmin = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		Status closedStatus = new Status();
		closedStatus.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		
		for (String ohdId: list) {
			try {
				OHD ohd = OhdBMO.getOHDByID(ohdId, writeSession);
				ohd.setStatus(closedStatus);
				OhdBMO.updateOHD(ohd, ogadmin, writeSession);
				writeSession.evict(ohd);
				logger.info("Closed: " + ohd.getOHD_ID());
			} catch (Exception e) {
				logger.error("closeUSAirOldMassOhdsInSQLServer: Exception encountered", e);
			}
		}
	}
	
	private void usAirFortyEightHourBusinessRules(Session writeSession,
			Session readSess) {
		String sql = "SELECT OHD_ID FROM OHD WHERE STATUS_ID != :closedStatus " +
				"AND ohd_type = :ohdType " +
				"AND (founddate < :48HourFoundDate OR( founddate = :48HourFoundDate AND foundtime<= :48HourFoundTime )) ";
		
		SQLQuery query = readSess.createSQLQuery(sql);
		
		query.setInteger("closedStatus", TracingConstants.OHD_STATUS_CLOSED);
		query.setInteger("ohdType", TracingConstants.MASS_OHD_TYPE);
				
		Calendar hours48 = new GregorianCalendar();
		hours48.add(Calendar.DAY_OF_MONTH, -2);

		Date hours48Date = new Date(hours48.getTime().getTime() + (hours48.getTime().getTimezoneOffset() * 1000 * 60));
		
		query.setDate("48HourFoundDate", hours48Date);
		query.setTime("48HourFoundTime", hours48Date);

		query.addScalar("OHD_ID", Hibernate.STRING);
		
		List<String> list = (List<String>) query.list();
		logger.info("OHDs to close: " + list.size());
		
		Agent ogadmin = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		Status closedStatus = new Status();
		closedStatus.setStatus_ID(TracingConstants.OHD_STATUS_CLOSED);
		
		for (String ohdId: list) {
			try {
				OHD ohd = OhdBMO.getOHDByID(ohdId, writeSession);
				ohd.setStatus(closedStatus);
				OhdBMO.updateOHD(ohd, ogadmin, writeSession);
				writeSession.evict(ohd);
				logger.info("Closed: " + ohd.getOHD_ID());
			} catch (Exception e) {
				logger.error("closeUSAirOldMassOhdsInSQLServer: Exception encountered", e);
			}
		}
	}
}
