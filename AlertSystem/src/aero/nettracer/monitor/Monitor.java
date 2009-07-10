package aero.nettracer.monitor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;

import com.bagnet.nettracer.email.HtmlEmail;

public class Monitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Properties properties = new Properties();			
		try {
      properties.load(Monitor.class.getResourceAsStream("/email.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
		
		String datePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
		Pattern succePatt = Pattern.compile(datePattern, Pattern.CASE_INSENSITIVE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long FIFTEEN_MINUTES = 15*60*1000;

		
		String command = properties.getProperty("command");
		String file = properties.getProperty("file");
			
		do {
	    try {
		    Process child = Runtime.getRuntime().exec(command);
		    child.waitFor();
		    System.out.println(child.exitValue());
	    } catch (IOException e1) {
		    e1.printStackTrace();
	    } catch (InterruptedException e) {
		    e.printStackTrace();
	    }
	    
	    String finalTime = null;
	    StringBuilder allContents = new StringBuilder();
	    try {
		    FileInputStream fstream = new FileInputStream(file);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;

		    while ((strLine = br.readLine()) != null) {
			    allContents.append(strLine + "\n");
			    Matcher succeMat = succePatt.matcher(strLine);
			    if (succeMat.find()) {
				    finalTime = succeMat.group(0);
			    }
		    }
		    in.close();
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
	    Date then = null;
	    Date now = new Date();
	    long difference = 0;
	    try {
		    then = sdf.parse(finalTime);
	    } catch (Exception e) {
		    e.printStackTrace();
	    }
	    
	    if (then != null) {
		    difference = now.getTime() - then.getTime();
	    }
	    if (then == null || difference >= FIFTEEN_MINUTES) {

		    try {

			    String host = properties.getProperty("host");
			    String from = properties.getProperty("from");
			    String to = properties.getProperty("support_email");
			    String subject = properties.getProperty("monitor_name")
			        + " CRON NOT RESPONSIVE IN LAST 15 MINUTES";
			    int port = Integer.parseInt(properties.getProperty("port"));

			    String message = "It has been " + difference / 1000 / 60
			        + " minutes since last log entry. \nCurrent Server Time: "
			        + new Date() + "\n\nFile contents: " + allContents.toString();

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
		    } catch (Exception e) {
			    e.printStackTrace();
		    }
		    try {
	        Thread.sleep(15*60*1000);
        } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    } else {
	    	try {
	        Thread.sleep(60*60*1000);
        } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	    }
	    
    } while (true);
	}

}
