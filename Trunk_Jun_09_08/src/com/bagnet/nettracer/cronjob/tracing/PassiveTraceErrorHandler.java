package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.Date;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.HtmlEmail;

public class PassiveTraceErrorHandler {
	
	public static Logger logger = Logger.getLogger(PassiveTraceErrorHandler.class);

	private String emailHost = "localhost";
	private String emailFrom = "donotreply@nettracer.aero";
	private String emailTo = "bsmith@nettracer.aero";
	private int emailPort = 25;
	private String instanceName = null;
	private volatile ArrayList<Date> dateList = new ArrayList<Date>();
	
	
	public PassiveTraceErrorHandler(String emailHost, String emailFrom, String emailTo, int emailPort, String instanceName) {
		this.emailHost = emailHost;
		this.emailFrom = emailFrom;
		//this.emailTo = emailTo;
		this.emailPort = emailPort;
		this.instanceName = instanceName;

	}
	
	public void sendEmail(String message, Exception e, boolean fatal, boolean hibernateError) {
		StringBuffer sb = new StringBuffer();
		sb.append(message + "\n\n");
		java.io.StringWriter out = new java.io.StringWriter();
        e.printStackTrace(new java.io.PrintWriter(out));
        String stackTrace = out.toString();
        sb.append(stackTrace);
		sendEmail(sb.toString(), fatal, hibernateError);
	}

	public void sendEmail(String message, boolean fatal,  boolean hibernateError) {
		try {
			HtmlEmail he = new HtmlEmail();
			he.setHostName(emailHost);
			he.setSmtpPort(emailPort);
			he.setFrom(emailFrom);

			ArrayList al = new ArrayList();
			al.add(new InternetAddress(emailTo));
			/*
			if (!emailTo.equals("support@nettracer.aero")) {
				al.add(new InternetAddress("support@nettracer.aero"));
			}
			*/
			he.setTo(al);
			he.setHtmlMsg(message);
			
			if (fatal) {
				he.setSubject("Fatal Passive Tracing Error: " + instanceName);
				he.send();
			} else 	if (hibernateError) {
				synchronized (dateList) {
					dateList.add(new Date());
					if (dateList.size() > 5) {
						dateList.clear();
						he.setSubject("Multiple Hibernate Errors: " + instanceName);
						he.send();
						// Sleep for 5 minutes
						Thread.sleep(300*1000);
					}
				}
			} else {
				he.setSubject("Passive Tracing Thread Error: " + instanceName);
				he.send();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Unable to send error via email due to: " + e);
			//return new ActionMessage("error.unable_to_send_mail");
		}
	}

	// Getters and setters
	
	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public int getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(int emailPort) {
		this.emailPort = emailPort;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

}
