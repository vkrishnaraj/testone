package com.bagnet.nettracer.cronjob;

import java.util.ArrayList;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class BaseErrorHandler implements ErrorHandler {

	private static final Logger logger = Logger.getLogger(BaseErrorHandler.class);
	private String emailHost = "localhost";
	private String emailFrom = "donotreply@nettracer.aero";
	private String emailTo = "support@nettracer.aero";
	private int emailPort = 25;
	private String instanceName = null;
	private String processName;
	private int hibernateErrorThreshold = 10;
	private int fatalErrorThreshold = 1;
	private int defaultErrorThreshold = 1;
	
	private volatile Integer hibernateCount = 0;
	private volatile Integer fatalCount = 0;
	private volatile Integer defaultCount = 0;
	
	public BaseErrorHandler(String companyCode) {
		super();
		Company_Specific_Variable var = AdminUtils.getCompVariable(companyCode);
		this.emailHost = var.getEmail_host();
		this.emailPort = var.getEmail_port();
	}
	
	public BaseErrorHandler(String emailHost, String emailFrom, String emailTo, int emailPort, String instanceName) {
		this.setEmailHost(emailHost);
		this.setEmailFrom(emailFrom);
		//this.emailTo = emailTo;
		this.setEmailPort(emailPort);
		this.setInstanceName(instanceName);
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

	public void sendEmail(String message, boolean fatal, boolean hibernateError) {
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
				synchronized(fatalCount) {
					fatalCount++;
					if(fatalCount >= fatalErrorThreshold) {
						fatalCount = 0;
						he.setSubject(String.format("%d Fatal %s Error(s): %s", fatalErrorThreshold, processName, instanceName));
						he.send();
					}
				}
				
			} else if (hibernateError){
				synchronized (hibernateCount) {
					hibernateCount++;
					if (hibernateCount > hibernateErrorThreshold) {
						hibernateCount = 0;
						he.setSubject(String.format("%d Hibernate Error(s) in %s: %s", hibernateErrorThreshold, processName, instanceName));
						he.send();
					}
				}
			} else {
				synchronized(defaultCount) {
					defaultCount++;
					if(defaultCount > defaultErrorThreshold) {
						defaultCount = 0;
						he.setSubject(String.format("%d Error(s) in %s: %s", defaultErrorThreshold, processName, instanceName));
						he.send();
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal("Unable to send error via email due to: " + e);
			//return new ActionMessage("error.unable_to_send_mail");
		}
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailPort(int emailPort) {
		this.emailPort = emailPort;
	}

	public int getEmailPort() {
		return emailPort;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getEmailTo() {
		return emailTo;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public int getHibernateErrorThreshold() {
		return hibernateErrorThreshold;
	}

	public void setHibernateErrorThreshold(int hibernateErrorThreshold) {
		this.hibernateErrorThreshold = hibernateErrorThreshold;
	}

	public int getFatalErrorThreshold() {
		return fatalErrorThreshold;
	}

	public void setFatalErrorThreshold(int fatalErrorThreshold) {
		this.fatalErrorThreshold = fatalErrorThreshold;
	}

	public int getDefaultErrorThreshold() {
		return defaultErrorThreshold;
	}

	public void setDefaultErrorThreshold(int defaultErrorThreshold) {
		this.defaultErrorThreshold = defaultErrorThreshold;
	}

}
