package com.bagnet.nettracer.cronjob.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.actions.BillingAction;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.dto.BillingDTO;
import com.bagnet.nettracer.tracing.forms.BillingForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class CronUtils {
	
	private static Logger logger = Logger.getLogger(CronUtils.class);
	private static String companyCode = null;
	
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
	
	public void emailPreviousMonthsBillingReport() {
		Agent user = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		System.out.println(user.getUsername());
		BillingForm form = new BillingForm();

		// Determine Dates
		Calendar a = new GregorianCalendar();
		System.out.println("Current date: " + a.getTime());
		
		int previousMonth = a.get(Calendar.MONTH) - 1;
		a.set(Calendar.MONTH, previousMonth);
		a.set(Calendar.DAY_OF_MONTH, a.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		String starttime = new SimpleDateFormat(TracingConstants.DISPLAY_DATEFORMAT).format(a.getTime());
		
		a.set(Calendar.DAY_OF_MONTH, a.getActualMaximum(Calendar.DAY_OF_MONTH));
		String endtime = new SimpleDateFormat(TracingConstants.DISPLAY_DATEFORMAT).format(a.getTime());
		
		System.out.println("Starttime: " + starttime);
		System.out.println("Endtime: " + endtime);
		
		// Generate The Report
		form.setCompanycode_ID(companyCode);
		form.setStarttime(starttime);
		form.setEndtime(endtime);
		form.setItemType_ID(0);
		
		//String output = BillingAction.createReport("", form, user, null);
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = BillingAction.generateQuery(form, sess, user);
		
		if (q == null) {
			System.out.println("Exception...");
			return;
		}
		
		List results = q.list();
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

		System.out.println(mb.toString());
		
		
		
		try {
			HtmlEmail he = new HtmlEmail();
			System.out.println(company.getVariable().getEmail_host());
			he.setHostName(company.getVariable().getEmail_host());
			he.setSmtpPort(company.getVariable().getEmail_port());
			he.setFrom("support@nettracer.aero");
			ArrayList toList = new ArrayList();
			toList.add(new InternetAddress("support@nettracer.aero"));
			he.setTo(toList);
			he.setSubject(messageHeader);
			he.setHtmlMsg(mb.toString());
			he.setCharset("UTF-8");
			he.send();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errors...");
		}
	}
}
