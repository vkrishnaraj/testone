package com.bagnet.nettracer.tracing.utils.general;

import java.util.ArrayList;

import javax.mail.internet.InternetAddress;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

public class AlertEmail {

	public static String FROM = "support@nettracer.aero";
	public static String HOST = TracerProperties.get(TracerProperties.get("wt.company.code"),"smtp.host");
	public static int PORT = new Integer(TracerProperties.get(TracerProperties.get("wt.company.code"),"smtp.port"));
	public static String TO = TracerProperties.get(TracerProperties.get("wt.company.code"),"smtp.support.email.address");

	public static boolean sendAlertEmail(String subject, String message){
		return sendAlertEmail(subject, message, FROM, TO, HOST, PORT);
	}

	public static boolean sendAlertEmail(String subject, String message, String from, String to, String host, int port){
		try {
			HtmlEmail he = new HtmlEmail();

			he.setHostName(host);
			he.setSmtpPort(port);

			he.setFrom(from);
			ArrayList al = new ArrayList();
			al.add(new InternetAddress(to));
			he.setTo(al);

			he.setSubject(subject);

			he.setHtmlMsg(message);
			he.send();

		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String [] args){
		sendAlertEmail("test","test");
	}
}
