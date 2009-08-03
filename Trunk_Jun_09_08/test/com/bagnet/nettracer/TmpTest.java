package com.bagnet.nettracer;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.db.Company;

public class TmpTest {
	
	private static String companyCode = "US";

	@Test
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
		
				List<Object[]> list = (List<Object[]>) query.list();
				
				output.append("<b>WorldTracer Transactions: " + d1.toString() + " to " + d2.toString() + "</b><br />");
				output.append("<table>");
				for (Object[] x: list) {
					output.append("<tr>");
					output.append("<td>" + x[0] + "</td>");
					output.append("<td>" + x[1] + "</td>");
					output.append("<td>" + x[2] + "</td>");
					output.append("</tr>");
				}
				output.append("</table><br /><br />");
			}
			System.out.println(output.toString());
			try {
				Company company = CompanyBMO.getCompany(companyCode);
				HtmlEmail he = new HtmlEmail();
				System.out.println(company.getVariable().getEmail_host());
				he.setHostName(company.getVariable().getEmail_host());
				he.setSmtpPort(company.getVariable().getEmail_port());
				he.setFrom("support@nettracer.aero");
				ArrayList toList = new ArrayList();
				toList.add(new InternetAddress("bsmith@nettracer.aero"));
				he.setTo(toList);
				he.setSubject("Daily WT Status Report: " + companyCode);
				he.setHtmlMsg(output.toString());
				he.setCharset("UTF-8");
				he.send();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Errors...");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			sess.close();
		}
	}	
}
