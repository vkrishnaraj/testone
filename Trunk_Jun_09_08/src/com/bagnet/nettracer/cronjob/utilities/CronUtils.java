package com.bagnet.nettracer.cronjob.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.StandardBasicTypes;

import com.bagnet.nettracer.cronjob.ErrorHandler;
import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.integrations.events.ClientEventHandler;
import com.bagnet.nettracer.tracing.actions.BillingAction;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;
import com.bagnet.nettracer.tracing.dto.BillingDTO;
import com.bagnet.nettracer.tracing.forms.BillingForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagDropUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.SmsEmailService;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.wt.bmo.WtTransactionBmo;

public class CronUtils {
	
	private static Logger logger = Logger.getLogger(CronUtils.class);
	private String companyCode = null;
	
	private ErrorHandler errHandler;
	private WtTransactionBmo wtxBmo;
	
	public CronUtils(String companyCode) {
		this.companyCode = companyCode;
	}
	
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
		
		Session writeSession = null;
		Session readSess = null;
		
		try {
			writeSession = HibernateWrapper.getSession().openSession();
			readSess = HibernateWrapper.getDirtySession().openSession();
			usAirTwentyFourHourBusinessRules(writeSession, readSess);
			usAirFortyEightHourBusinessRules(writeSession, readSess);
		} finally {
		
			// SECOND HALF
			readSess.close();
			writeSession.close();
		}
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

		query.addScalar("OHD_ID", StandardBasicTypes.STRING);
		
		@SuppressWarnings("unchecked")
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

		query.addScalar("OHD_ID", StandardBasicTypes.STRING);
		
		@SuppressWarnings("unchecked")
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
	
	public void emailPreviousMonthsBillingReport() {
		Agent user = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		
		BillingForm form = new BillingForm();

		// Determine Dates
		Calendar a = new GregorianCalendar();
		logger.info("Current date: " + a.getTime());
		
		int previousMonth = a.get(Calendar.MONTH) - 1;
		a.set(Calendar.MONTH, previousMonth);
		a.set(Calendar.DAY_OF_MONTH, a.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		String starttime = new SimpleDateFormat(TracingConstants.DISPLAY_DATEFORMAT).format(a.getTime());
		
		a.set(Calendar.DAY_OF_MONTH, a.getActualMaximum(Calendar.DAY_OF_MONTH));
		String endtime = new SimpleDateFormat(TracingConstants.DISPLAY_DATEFORMAT).format(a.getTime());
		
		logger.info("Starttime: " + starttime);
		logger.info("Endtime: " + endtime);
		
		// Generate The Report
		form.setCompanycode_ID(companyCode);
		form.setStarttime(starttime);
		form.setEndtime(endtime);
		form.setItemType_ID(0);
		
		//String output = BillingAction.createReport("", form, user, null);
		Session sess = null;
		List results = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = BillingAction.generateQuery(form, sess, user);
			
			if (q == null) {
				logger.info("Exception...");
				return;
			}
			
			results = q.list();
		} finally {
			if (sess != null && sess.isOpen()) sess.close();
		}
		ArrayList<BillingDTO> reportList = new ArrayList<BillingDTO>();
		Company company = CompanyBMO.getCompany(companyCode);
		String companyName = company.getCompanydesc();
		String messageHeader = "Automated Monthly Billing Report for: " + companyName;
		
		StringBuilder mb = new StringBuilder();
		mb.append("<strong>Automated Monthly Billing Report for: " + companyName + "</strong><p />");
		
		mb.append("<table border=\"1\" width=\"350\">");
		mb.append("<tr><th colspan=\"2\"  bgcolor=\"lightgrey\"><strong>Billing Criteria</strong></th></tr>");
		mb.append("<tr><td width=\"200\">Company:</td><td>" + companyName +"</td></tr>");
		mb.append("<tr><td width=\"200\">Start Date:</td><td>" + starttime +"</td></tr>");
		mb.append("<tr><td width=\"200\">End Date:</td><td>" + endtime +"</td></tr>");
		mb.append("</table>");
		
		
		long sum = 0;

		mb.append("<p /><table border=\"1\" width=\"350\">");
		mb.append("<tr><th colspan=\"2\" bgcolor=\"lightgrey\"><strong>Incidents Created</strong></th></tr>");

		for (int i = 0; i < results.size(); i++) {
			Object[] b = (Object[]) results.get(i);

			mb.append("<tr><td width=\"200\">" + IncidentUtils.retrieveItemTypeWithId(((Integer) b[1]).intValue(),
					user.getCurrentlocale()).getDescription() + ": " + "</td><td>" + ((Long) b[2]).longValue() +"</td></tr>");
			
			sum += ((Long) b[2]).longValue();
		}
		
		mb.append("<tr bgcolor=\"lightgreen\"><td width=\"200\"><strong>Sum Total:</strong></td><td><strong>" + sum +"</strong></td></tr>");
		mb.append("</table>");

		logger.info(mb.toString());
		
		
		
		try {
			HtmlEmail he = new HtmlEmail();
			logger.info(company.getVariable().getEmail_host());
			he.setHostName(company.getVariable().getEmail_host());
			he.setSmtpPort(company.getVariable().getEmail_port());
			he.setFrom("support@nettracer.aero");
			ArrayList <InternetAddress>toList = new ArrayList<InternetAddress>();
			toList.add(new InternetAddress("support@nettracer.aero"));
			he.setTo(toList);
			he.setSubject(messageHeader);
			he.setHtmlMsg(mb.toString());
			he.setCharset("UTF-8");
			he.send();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Errors...");
		}
	}
	
	public void expireGlobalLocks() {
		Session writeSession = null;
		Date now = new Date();
		Transaction tx = null;
		try {
			writeSession = HibernateWrapper.getSession().openSession();
			tx = writeSession.beginTransaction();
			tx.begin();
			Query q = writeSession
					.createQuery("delete Lock lock where lock.expirationDate < :now");
			q.setTimestamp("now", now);
			q.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Throwable e1) {
					//pass
				}
			}
		} finally {
			if (writeSession != null) writeSession.close();
		}
	}
	
	public void checkWorldTracer() {
		List foo = wtxBmo.countTransactionResults(12);
	}

	//setters used by Spring to inject props
	public void setErrHandler(ErrorHandler errHandler) {
		this.errHandler = errHandler;
	}

	public void setWtxBmo(WtTransactionBmo wtxBmo) {
		this.wtxBmo = wtxBmo;
	}
	
	public void hourlySqlServerStatusCheck() {
		ArrayList<String> alerts = new ArrayList<String>();
		
		int pendingSize = checkPendingSize();
		if (pendingSize > 100) {
			alerts.add("Pending Size: " + pendingSize);
		}
		
		int lastSequentialFailures = checkSQLServerSequentialFailures();
		int percentFailure = checkSQLServerPercentageFailures();
		if (lastSequentialFailures > 20) {
			alerts.add("Failures (max 50): " + lastSequentialFailures);
		}
		if (percentFailure > 24){
			alerts.add("Total Failures out of last 50 transactions: " + percentFailure);
		}
		
		if (alerts.size() > 0) {
			StringBuffer output = new StringBuffer();
			for (String alert: alerts) {
				output.append(alert + "\n");
			}
			
			try {
				Company company = CompanyBMO.getCompany(companyCode);
				HtmlEmail he = new HtmlEmail();			
				he.setHostName(company.getVariable().getEmail_host());
				he.setSmtpPort(company.getVariable().getEmail_port());
				he.setFrom("support@nettracer.aero");
				ArrayList<InternetAddress> toList = new ArrayList<InternetAddress>();
				String toString = PropertyBMO.getValue("ALERT_SMS_EMAILS");
				toList.add(new InternetAddress("support@nettracer.aero"));
				if (toString != null){
					for (String sms: toString.split(",")) {
						toList.add(new InternetAddress(sms));
					}
				}
				
				he.setTo(toList);
				he.setSubject("Alert: " + companyCode);
				he.setHtmlMsg(output.toString());
				logger.info(output.toString());
				he.setCharset("UTF-8");
				he.send();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	protected int checkSQLServerPercentageFailures(){	
		String sql = "SELECT count(wtq_status) FROM " +
				"(SELECT top 50 wtq_status FROM wt_queue ORDER BY CREATEDATE DESC) count_table " +
				"WHERE wtq_status = 'FAIL'";
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery query = sess.createSQLQuery(sql);
			@SuppressWarnings("unchecked")
			List<Integer> list = (List<Integer>) query.list();
			return ((Integer)list.get(0)).intValue();
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	protected int checkSQLServerSequentialFailures() {
		
		String sql = "SELECT top 50 wtq_status FROM wt_queue " +
		"ORDER BY CREATEDATE DESC";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			SQLQuery query = sess.createSQLQuery(sql);
			query.addScalar("wtq_status", StandardBasicTypes.STRING);
			
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) query.list();
			
			int sequentialFails = 0;
			for (String str: list) {
				if (str.equals(WorldTracerQueue.WtqStatus.SUCCESS.name())) {
					sequentialFails = 0;
					break;
				}  else if (str.equals(WorldTracerQueue.WtqStatus.FAIL.name())) {
					sequentialFails++;
				}
			}
			
			return sequentialFails;
		} finally {
			if (sess != null) sess.close();
		}
	}

	protected int checkPendingSize() {
		String sql = "SELECT count(*) as icount FROM wt_queue " +
		"WHERE wtq_status = 'PENDING'";
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			SQLQuery query = sess.createSQLQuery(sql);
			@SuppressWarnings("unchecked")
			List<Integer> list = (List<Integer>) query.list();
			return ((Integer)list.get(0)).intValue();
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	public void emailWtTransactions() {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		Calendar day = new GregorianCalendar();
		StringBuilder output = new StringBuilder();
			
		try{
			for (int i =0; i < 3; ++i) {
				String sql = "select wtq_action, wtq_status, count(*) as count from wt_queue " +
						" where createdate between :date1 and :date2" +
						" group by wtq_action, wtq_status" +
						" order by wtq_action";
				
				SQLQuery query = sess.createSQLQuery(sql);
				
				Date d2 = new Date(day.getTime().getTime());
				day.add(Calendar.DAY_OF_MONTH, -1);
				Date d1 = new Date(day.getTime().getTime());
		
				query.setDate("date1", d1);
				query.setDate("date2", d2);
		
				@SuppressWarnings("unchecked")
				List<Object[]> list = (List<Object[]>) query.list();
				
				output.append("<b>WorldTracer Transactions: " + d1.toString() + " to " + d2.toString() + "</b><br />");
				output.append("<table>");
				for (Object[] x: list) {
					if (((String)(x[1])).contains("PENDING")) {
						output.append("<tr bgcolor=\"lightred\">");
					} else {
						output.append("<tr>");
					}
					output.append("<td>" + x[0] + "</td>");
					output.append("<td>" + x[1] + "</td>");
					output.append("<td>" + x[2] + "</td>");
					output.append("</tr>");
				}
				output.append("</table><br /><br />");
			}
			logger.info(output.toString());
			try {
				Company company = CompanyBMO.getCompany(companyCode);
				HtmlEmail he = new HtmlEmail();
				logger.info(company.getVariable().getEmail_host());
				he.setHostName(company.getVariable().getEmail_host());
				he.setSmtpPort(company.getVariable().getEmail_port());
				he.setFrom("support@nettracer.aero");
				ArrayList<InternetAddress> toList = new ArrayList<InternetAddress>();
				toList.add(new InternetAddress("support@nettracer.aero"));
				he.setTo(toList);
				he.setSubject("Daily WT Status Report: " + companyCode);
				he.setHtmlMsg(output.toString());
				he.setCharset("UTF-8");
				he.send();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("Errors...");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sess.close();
		}
	}
	
	
	public void closeAirTranOldMassOhdsInSQLServer() {
		
		// FIRST HALF	
		Session writeSession = null;
		Session readSess = null;
		try {
			writeSession = HibernateWrapper.getSession().openSession();
			readSess = HibernateWrapper.getDirtySession().openSession();
			airtranTwentyFourHourBusinessRules(writeSession, readSess);
		} finally {
			// SECOND HALF
			if (readSess != null) readSess.close();
			if (writeSession != null) writeSession.close();
		}
	}

	private void airtranTwentyFourHourBusinessRules(Session writeSession,
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

		query.addScalar("OHD_ID", StandardBasicTypes.STRING);
		
		@SuppressWarnings("unchecked")
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
	
	public void sendUSFilesToCRM() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
	
			String query = "select i.incident_ID from Incident i left outer join i.crmFile c" + " where i.crmFile.incident is null and i.createdate >= :startDate "
					+ " and ((i.createdate < :incCutoff) or (i.createdate = :incCutoff and i.createtime <= :incTimeCutoff)) " 
					+ " and i.status.status_ID = :openStatus order by i.createdate asc, i.createtime asc";
	
			Query q = sess.createQuery(query);
			
			Date startDate = DateUtils.convertToGMTDate(PropertyBMO.getValue("us.crm.start.date"), TracingConstants.DB_DATEFORMAT);
	//		logger.info("**Start Date: " + startDate);
	
			Calendar c = new GregorianCalendar();
			c.setTime(TracerDateTime.getGMTDate());
			c.add(Calendar.HOUR, (0 - 96));
			Date incCutoff = c.getTime();
	
	
			q.setDate("startDate", startDate);
			q.setDate("incCutoff", incCutoff);
			q.setTime("incTimeCutoff", incCutoff);
			q.setInteger("openStatus", TracingConstants.MBR_STATUS_OPEN);
	
			@SuppressWarnings("unchecked")
			List<String> results = (List<String>) q.list();
	
			logger.info("Total files ready to send to CRM: " + results.size());
			int countUpdated = 0;
	
			for (String o : results) {
				ClientEventHandler h = new com.bagnet.clients.us.ClientEventHandlerImpl();
				h.sendCrm(o, sess);
				countUpdated += 1;
			}
				
			logger.info("Total files sent to CRM: " + countUpdated);
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	
	public void process24HoursEmails() {
		try {
			Session session = null;
			
			Transaction t = null;
			try {

				session = HibernateWrapper.getSession().openSession();
				// String query =
				String query = "select * from incident ";
				query += "where status_ID = 12 ";
				query += "and itemtype_ID = 1 ";
				query += "and Incident_ID not in (select distinct incident_id from bdo where incident_ID IS NOT NULL) ";
				query += "and Incident_ID not in (select distinct incident_id from z_us_24hrs_email where incident_ID IS NOT NULL) ";

				String timeQuery_1 = "";
				String timeQuery_2 = "";

				timeQuery_1 += "and createdate >= ? ";
				timeQuery_1 += "and createtime >= ? ";
				timeQuery_1 += "and createdate <= ? ";
				timeQuery_1 += "and createtime <= ? ";

				timeQuery_2 += "and ";
				timeQuery_2 += "((createdate = ? and createtime >= ?) "; // ie.
				// 2010-05-24
				timeQuery_2 += "or (createdate = ? and createtime <= ?)) "; // ie.
				// 2010-05-25
				// next
				// day

				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");

				// test case one
				// Date myChosenDate =
				// (Date)datetimeformatter.parse("2010.02.03 00:30:00");

				// test case two
				// Date myChosenDate =
				// (Date)datetimeformatter.parse("2010.02.03 15:23:47");

				// test case three
				// Date myChosenDate =
				// (Date)datetimeformatter.parse("2010.01.11 00:40:10");

				// test case four "2010.01.31 23:55:56"
//				Date myChosenDate = (Date) datetimeformatter.parse("2010.02.01 23:55:56");

				Calendar endCal = Calendar.getInstance();
				Calendar startCal = Calendar.getInstance();

//				endCal.setTime(myChosenDate);
//				startCal.setTime(myChosenDate);

				// figure out whether the two times fall on the same day or two
				// consecutive days
				String myTime = timeformat.format(startCal.getTime());
				if ((myTime.compareTo("00:00:00") >= 0) && (myTime.compareTo("01:00:00") <= 0)) {
					logger.info(" our time is in that critical spot...");
					query += timeQuery_2;
				} else {
					logger.info(" our time is NOT in that critical spot...");
					query += timeQuery_1;
				}

				endCal.add(Calendar.HOUR, -24); // Subtracting 24 hour to
												// current
				// date time

				String newEndDate = dateformat.format(endCal.getTime());
				String newEndTime = timeformat.format(endCal.getTime());

				startCal.add(Calendar.HOUR, -25); // Subtracting 25 hour to
													// current
				// date time

				String newStartDate = dateformat.format(startCal.getTime());
				String newStartTime = timeformat.format(startCal.getTime());

				SQLQuery hibQuery = session.createSQLQuery(query).addEntity("incident", Incident.class);

				hibQuery.setString(0, newStartDate);
				hibQuery.setString(1, newStartTime);
				hibQuery.setString(2, newEndDate);
				hibQuery.setString(3, newEndTime);

				logger.info("the entire sql is:" + hibQuery.toString());
				logger.info("  Date range:" + newStartDate + " " + newStartTime + " " + newEndDate + " "+ newEndTime);

				// execute query, and return
				List myList = hibQuery.list();

				@SuppressWarnings("unchecked")
				Iterator<Incident> itr = myList.iterator();
				while (itr.hasNext()) {
					Incident myItem = (Incident) itr.next();

					String myIncidentId = myItem.getIncident_ID();
					logger.info("  Processing 24 hour emails incident: " + myIncidentId);

					Incident myIncident = IncidentBMO.getIncidentByID(myIncidentId, session);

					if (myIncident == null) {
						// logger.info("incident is null, try another one.");
					} else {
						SmsEmailService smsEmailService = new SmsEmailService();
						smsEmailService.send24HoursMessage(myIncident);
					}

					String insertQuery = "INSERT INTO z_us_24hrs_email (incident_ID) VALUES('" + myIncidentId + "')";

					SQLQuery hibInsertQuery = session.createSQLQuery(insertQuery);
					// hibInsertQuery.setString(0, myIncidentId);
					t = session.beginTransaction();
					int numOfRowInserted = hibInsertQuery.executeUpdate();
					t.commit();
					if (numOfRowInserted > 0) {
						logger.info("    New email sent for incident: " + myIncidentId);
					}

				}

			} catch (HibernateException e) {
				e.printStackTrace();
				if (t != null) {
					t.rollback();
				}
			} finally {
				if (session != null) {
					session.close();
				}
			}

		} catch (Exception e) {
			logger.error("Error processing 24 hour emails: ", e);
		}
	}
	
	/**
	 * Updates flight information for SWA BagDrop for all stations for current day and previous day
	 */
	public void bagDropFlightInfo(){
		logger.info("Updating bagdrop flight information for " + companyCode);
		String admin = "ntadmin";                                                               
		Agent agent = AdminUtils.getAgentBasedOnUsername(admin, companyCode);
		Calendar today = GregorianCalendar.getInstance();
		Calendar yesturday = GregorianCalendar.getInstance();
		yesturday.add(Calendar.DATE, -1);
		try{
			BagDropUtils.refreshFlightInfo(agent, yesturday.getTime());
			BagDropUtils.refreshFlightInfo(agent, today.getTime());
		} catch (Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
