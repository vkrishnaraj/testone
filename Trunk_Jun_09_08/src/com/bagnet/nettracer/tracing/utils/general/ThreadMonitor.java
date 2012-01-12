package com.bagnet.nettracer.tracing.utils.general;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.tracing.utils.TracerProperties;



public class ThreadMonitor implements Runnable{
	private static final Logger logger = Logger.getLogger(ThreadMonitor.class);
	
	private static Vector<ThreadContainer> container;
	private static final long TIMEOUT = 120000;
	private static final long POLL_INTERVAL = 30000;
	
	public ThreadMonitor(Vector<ThreadContainer> container){
		this.container = container;
	}
	
	@Override
	public void run() {
		while(true){
			try{
				Thread.sleep(POLL_INTERVAL);
				for(ThreadContainer tc:container){
					Date now = new Date();
					if(!tc.isDead() && (tc.isWaiting() == false || !tc.getThread().isAlive())){
						if(!tc.getThread().isAlive() || now.getTime() - tc.getStartTime().getTime() > TIMEOUT){
							try{
								logger.error("LFC Connection Thread: " + tc.getId() + "appears to have failed");
								sendAlertEmail("Thread: " + tc.getId() + "appears to have failed");
								tc.setDead(true);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public boolean sendAlertEmail(String message){
			try {
				HtmlEmail he = new HtmlEmail();
				String from = "support@nettracer.aero";
				String host =  TracerProperties.get("smtp.host");
				int port =  new Integer(TracerProperties.get("smtp.port"));
				String to = TracerProperties.get("smtp.support.email.address");
				
				he.setHostName(host);
				he.setSmtpPort(port);

				he.setFrom(from);
				ArrayList al = new ArrayList();
				al.add(new InternetAddress(to));
				he.setTo(al);
				
				he.setSubject("LFC Connection Thread alert");

				he.setHtmlMsg(message);
				he.send();
				
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
	}
	
	public static void main(String [] args){
		ThreadMonitor t = new ThreadMonitor(null);
		t.sendAlertEmail("Test");
	}
}
