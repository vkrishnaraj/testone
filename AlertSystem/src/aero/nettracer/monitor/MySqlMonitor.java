package aero.nettracer.monitor;
/**
 * Execute sql queries with java application.
 */

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.email.EmailException;
import com.bagnet.nettracer.email.HtmlEmail;

public class MySqlMonitor {

	static Logger logger = Logger.getLogger(MySqlMonitor.class);
	private static final int PRODUCTION_TIMEOUT = 20000;
	private static final int PRODUCTION_RETRY = 20000;
	
  public static void main(String[] args) {

	  ArrayList<MonitorDB> DBs = new ArrayList<MonitorDB>();
		DBs.add(new MonitorDB("Avis", "jdbc:mysql://localhost:3306/ab_local"));
		DBs.add(new MonitorDB("JetBlue", "jdbc:mysql://localhost:3306/b6_local"));
		int nullCount=0;
		MonitorDB MDB= new MonitorDB("Main", "jdbc:mysql://10.16.247.2:3306");
		boolean InARow=false;
		
		while (true) {
			Calendar sTime = new GregorianCalendar();
			//for (MonitorDB DB : DBs) {

				MySQLMonitorWorkerThread wt = null;
				
				wt = createMySQLMonitorWorkerThread(MDB);
				sleep(PRODUCTION_TIMEOUT);

				if (wt.isFinished() && wt.isPassingTests()
						&& wt.getElapsedTimeInMillis() < PRODUCTION_TIMEOUT) {
					logger.info("  Test successful...");
					SimpleJdbcLogger.log(MDB.getDBName(), MDB.getConnectionURL(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
				} else {
					logger.info("  Test failure; " + wt.printFullStatus());
					try {
						SimpleJdbcLogger.log(MDB.getDBName(), MDB.getConnectionURL(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, true);
						if(wt.getResponseString().toLowerCase().contains("null"))
						{
							nullCount++;
							if(nullCount==2)
							{	sendEmail(wt.printFullStatus(), MDB.isProduction());
								nullCount=0;
								InARow=false;
							}
						}
						else 
						{	
							sendEmail(wt.printFullStatus(), MDB.isProduction());
						}
						
					} catch (AddressException e) {
						e.printStackTrace();
						SimpleJdbcLogger.log(MDB.getDBName(), MDB.getConnectionURL(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
					} catch (EmailException e) {
						e.printStackTrace();
						SimpleJdbcLogger.log(MDB.getDBName(), MDB.getConnectionURL(), wt.getStartTime(), wt.getStopTime(), wt.isFinished()? wt.getElapsedTimeInMillis() : 0, false);
					}
				}
				
				if(nullCount==1 && !InARow)
				{
					InARow=true;
				}
				else if(nullCount==1 && InARow)
				{
					nullCount=0;
					InARow=false;
				}
					
			

			try {
				// Sleep 5 minutes 
				Calendar stTime = new GregorianCalendar();
				long elapse = stTime.getTimeInMillis() - sTime.getTimeInMillis();
				long remaining = 5 * 60 * 1000 - elapse;
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

	private static MySQLMonitorWorkerThread createMySQLMonitorWorkerThread(
			MonitorDB monDB) {
		
		MySQLMonitorWorkerThread wt;
		wt = new MySQLMonitorWorkerThread(monDB);
		Thread t = new Thread(wt);
		t.start();
		return wt;
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