package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.cronjob.BaseErrorHandler;
import com.bagnet.nettracer.cronjob.ErrorHandler;
import com.bagnet.nettracer.email.HtmlEmail;

public class PassiveTraceErrorHandler extends BaseErrorHandler {
	
	public static Logger logger = Logger.getLogger(PassiveTraceErrorHandler.class);

	private volatile ArrayList<Date> dateList = new ArrayList<Date>();
	
	
	public PassiveTraceErrorHandler(String emailHost, String emailFrom, String emailTo, int emailPort, String instanceName) {
		super(emailHost, emailFrom, emailTo, emailPort, instanceName);
	}

	@Override
	public void sendEmail(String message, boolean fatal,  boolean hibernateError) {
		try {
			HtmlEmail he = new HtmlEmail();
			he.setHostName(getEmailHost());
			he.setSmtpPort(getEmailPort());
			he.setFrom(getEmailFrom());

			ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
			al.add(new InternetAddress(getEmailTo()));
			/*
			 * TODO : Update
			if (!emailTo.equals("support@nettracer.aero")) {
				al.add(new InternetAddress("support@nettracer.aero"));
			}
			*/
			he.setTo(al);
			he.setHtmlMsg(message);
			
			if (fatal) {
				he.setSubject("Fatal Passive Tracing Error: " + getInstanceName());
				he.send();
			} else 	if (hibernateError) {
				synchronized (dateList) {
					dateList.add(new Date());
					if (dateList.size() > 5) {
						dateList.clear();
						he.setSubject("Multiple Hibernate Errors: " + getInstanceName());
						he.send();
						// Sleep for 5 minutes
						Thread.sleep(300*1000);
					}
				}
			} else {
				he.setSubject("Passive Tracing Thread Error: " + getInstanceName());
				he.send();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Unable to send error via email due to: " + e);
			//return new ActionMessage("error.unable_to_send_mail");
		}
	}

}
