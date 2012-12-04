package aero.nettracer.monitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import org.apache.log4j.Logger;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.bagnet.nettracer.email.EmailException;
import com.bagnet.nettracer.email.HtmlEmail;

public class HttpMonitor {

//	private static final long SECONDS_TILL_ALERT = 15000;
	private static final int PRODUCTION_TIMEOUT = 20000;
	private static final int PRODUCTION_RETRY = 20000;
	
	static Logger logger = Logger.getLogger(HttpMonitor.class);

	public static void main(String[] args) {


		ArrayList<MonitorUrl> urls = new ArrayList<MonitorUrl>();
		urls.add(new MonitorUrl("JetBlue",	"https://jetblue.nettracer.aero/tracer/logoff.do", "", "Owens", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("Azul", "https://azul.nettracer.aero/tracer/logoff.do", "", "Owens", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("Spirit", "https://spirit.nettracer.aero/tracer/logoff.do", "", "Owens", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("WestJet",	"https://westjet.nettracer.aero/tracer/logoff.do", "", "Owens", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("US Air Paxview","https://usairways.nettracer.aero/pax-view/claim/login.do", "","baggage", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("Avis","https://avis.nettracer.aero/abtracer/logoff.do", "", "NetTracer", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("WebJet", "https://live.nettracer.aero/webjet/logoff.do", "", "NetTracer", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("United", "https://live.nettracer.aero/united/logoff.do", "", "NetTracer", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("American", "https://live.nettracer.aero/american/logoff.do", "", "NetTracer", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("Delta", "https://live.nettracer.aero/delta/logoff.do", "", "NetTracer", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("LostAndFound", "https://live.lostandfound.aero/lostandfound/logoff.do", "", "NetTracer", true, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("LostAndFound Paxview", "https://live.lostandfound.aero/client/southwest/landing.do","","Southwest",true,PRODUCTION_TIMEOUT, PRODUCTION_RETRY));	
		
		urls.add(new MonitorUrl("B6 Testing", "https://testing.nettracer.aero/jetblue/logoff.do", "", "Owens", false, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("Avis Testing", "https://testing.nettracer.aero/avis/logoff.do", "", "NetTracer", false, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("WestJet Testing", "https://testing.nettracer.aero/westjet/logoff.do", "",	"Owens", false, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("B6 Training", "https://training.nettracer.aero/jetblue/logoff.do", "", "Owens", false, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("Avis Training", "https://training.nettracer.aero/avis/logoff.do", "", "NetTracer", false, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		urls.add(new MonitorUrl("WestJet Training", "https://training.nettracer.aero/westjet/logoff.do", "",	"Owens", false, PRODUCTION_TIMEOUT, PRODUCTION_RETRY));
		
		
		while (true) {
			Calendar sTime = new GregorianCalendar();
			for (MonitorUrl url : urls) {

				HttpMonitorWorkerThread wt = null;
				
				wt = createHttpMonitorWorkerThread(url);
				sleep(PRODUCTION_TIMEOUT);

				if (wt.isFinished() && wt.isPassingTests()
						&& wt.getElapsedTimeInMillis() < PRODUCTION_TIMEOUT) {
					logger.info("  Test successful...");
					SimpleJdbcLogger.log(url.getTitle(), url.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
				} else {
					logger.info("  Test failure; " + wt.printFullStatus());
					try {
						SimpleJdbcLogger.log(url.getTitle(), url.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, true);
						
						sendEmail(wt.printFullStatus(), url.isProduction());
						
					} catch (AddressException e) {
						e.printStackTrace();
						SimpleJdbcLogger.log(url.getTitle(), url.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
					} catch (EmailException e) {
						e.printStackTrace();
						SimpleJdbcLogger.log(url.getTitle(), url.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
					}
				}
			}

			try {
				// Sleep 5 minutes 
				Calendar stTime = new GregorianCalendar();
				long elapse = stTime.getTimeInMillis() - sTime.getTimeInMillis();
				long remaining = 5 * 60 * 1000 - elapse;
				logger.info("Sleeping for " + (remaining/1000)+ " seconds...");
				if(remaining>0){
					Thread.sleep(remaining);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void sleep(long wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static HttpMonitorWorkerThread createHttpMonitorWorkerThread(
			MonitorUrl monUrl) {
		
		HttpMonitorWorkerThread wt;
		wt = new HttpMonitorWorkerThread(monUrl);
		Thread t = new Thread(wt);
		t.start();
		return wt;
	}

	private static void addTestUrl(ArrayList<String[]> urls, String a,
			String b, String c, String d) {
		String[] x = { a, b, c, d };
		urls.add(x);
	}

	public static void sendEmail(String msg, boolean isProduction) throws EmailException, AddressException {
		Properties properties = new Properties();
		try {
			properties.load(HttpMonitor.class.getResourceAsStream("/email.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String host = properties.getProperty("host");
		String from = properties.getProperty("from");
		String to = properties.getProperty("support_email");
		if (!isProduction) {
			to = properties.getProperty("notice_email");
		}

		String subject = properties.getProperty("monitor_name");
		int port = Integer.parseInt(properties.getProperty("port"));

		String message = msg;

		System.out.println(message);

		HtmlEmail he = new HtmlEmail();
		he.setHostName(host);
		he.setSmtpPort(port);
		he.setFrom(from);

		ArrayList<InternetAddress> al = new ArrayList<InternetAddress>();
		al.add(new InternetAddress(to));
		he.setTo(al);
		he.setHtmlMsg(message);

		he.setSubject(subject);
		he.send();

	}

}
