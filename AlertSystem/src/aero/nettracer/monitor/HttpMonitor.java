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

	private static final long SECONDS_TILL_ALERT = 20000;
	static Logger logger = Logger.getLogger(HttpMonitor.class);

	public static void main(String[] args) {

		// Perform request on URL
		ArrayList<String[]> urls = new ArrayList<String[]>();

		// addTestUrl(urls, "JetBlue",
		// "https://jetblue.nettracer.aero/tracer/logon.do",
		// "companyCode=B6&username=ntadmin&password=Ladendead51!", "Pawob");
		// addTestUrl(urls, "Azul",
		// "https://azul.nettracer.aero/tracer/logon.do",
		// "companyCode=AD&username=ntadmin&password=Ladendead51!", "Incoming");
		// addTestUrl(urls, "Spirit",
		// "https://spirit.nettracer.aero/tracer/logon.do",
		// "companyCode=NK&username=ntadmin&password=Ladendead51!", "Incoming");
		// addTestUrl(urls, "WestJet",
		// "https://westjet.nettracer.aero/tracer/logon.do",
		// "companyCode=WS&username=ntadmin&password=Ladendead51!", "PIR");

		addTestUrl(urls, "JetBlue",
				"https://jetblue.nettracer.aero/tracer/logoff.do", "", "Owens");
		addTestUrl(urls, "Azul",
				"https://azul.nettracer.aero/tracer/logoff.do", "", "Owens");
		addTestUrl(urls, "Spirit",
				"https://spirit.nettracer.aero/tracer/logoff.do", "", "Owens");
		addTestUrl(urls, "WestJet",
				"https://westjet.nettracer.aero/tracer/logoff.do", "", "Owens");
		addTestUrl(urls, "US Air Paxview",
				"https://usairways.nettracer.aero/pax-view/claim/login.do", "",
				"baggage");
		addTestUrl(urls, "Avis",
				"https://avis.nettracer.aero/abtracer/logoff.do", "",
				"NetTracer");
		
		
		addTestUrl(urls, "B6 Testing", "https://testing.nettracer.aero/jetblue/logoff.do", "", "Owens");
		addTestUrl(urls, "Avis Testing", "https://testing.nettracer.aero/avis/logoff.do", "", "NetTracer");
		addTestUrl(urls, "WestJet Testing", "https://testing.nettracer.aero/westjet/logoff.do", "",	"Owens");
		addTestUrl(urls, "B6 Training", "https://training.nettracer.aero/jetblue/logoff.do", "", "Owens");
		addTestUrl(urls, "Avis Training", "https://training.nettracer.aero/avis/logoff.do", "", "NetTracer");
		addTestUrl(urls, "WestJet Training", "https://training.nettracer.aero/westjet/logoff.do", "",	"Owens");		
		
		while (true) {
			Calendar sTime = new GregorianCalendar();
			for (String[] url : urls) {

				HttpMonitorWorkerThread wt = null;
				
				wt = createHttpMonitorWorkerThread(url);
				sleep(SECONDS_TILL_ALERT);

				if (wt.isFinished() && wt.isPassingTests()
						&& wt.getElapsedTimeInMillis() < SECONDS_TILL_ALERT) {
					logger.info("  Test successful...");
					SimpleJdbcLogger.log(wt.getLabel(), wt.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
				} else {
					logger.info("  Test failure; " + wt.printFullStatus());
					try {
						SimpleJdbcLogger.log(wt.getLabel(), wt.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, true);
						sendEmail(wt.printFullStatus());
						
					} catch (AddressException e) {
						e.printStackTrace();
						SimpleJdbcLogger.log(wt.getLabel(), wt.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
					} catch (EmailException e) {
						e.printStackTrace();
						SimpleJdbcLogger.log(wt.getLabel(), wt.getUrl(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
					}
				}
			}

			try {
				// Sleep 5 minutes 
				Calendar stTime = new GregorianCalendar();
				long elapse = stTime.getTimeInMillis() - sTime.getTimeInMillis();
				long remaining = 10 * 60 * 1000 - elapse;
				logger.info("Sleeping for " + (remaining/1000)+ " seconds...");
				Thread.sleep(remaining);
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
			String[] url) {
		
		HttpMonitorWorkerThread wt;
		wt = new HttpMonitorWorkerThread(
				url[0], url[1], url[2], url[3]);
		Thread t = new Thread(wt);
		t.start();
		return wt;
	}

	private static void addTestUrl(ArrayList<String[]> urls, String a,
			String b, String c, String d) {
		String[] x = { a, b, c, d };
		urls.add(x);
	}

	public static void sendEmail(String msg) throws EmailException, AddressException {
		Properties properties = new Properties();
		try {
			properties.load(HttpMonitor.class.getResourceAsStream("/email.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String host = properties.getProperty("host");
		String from = properties.getProperty("from");
		String to = properties.getProperty("support_email");
//		String to = properties.getProperty("support_sms");
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
