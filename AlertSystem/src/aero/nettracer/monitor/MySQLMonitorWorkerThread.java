package aero.nettracer.monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

// TODO:
//  2. Open connection
//  3. Show Slave Status
//  4. Check fields
//  5. Delay outgoing message for alerts.

public class MySQLMonitorWorkerThread implements Runnable {

	private MonitorDB monDB;
	
	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}
	
	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getStopTime() {
		return stopTime;
	}

	public void setStopTime(Calendar stopTime) {
		this.stopTime = stopTime;
	}

	private 	String responseString = null;
	private Calendar startTime = null;
	private Calendar stopTime = null;
	static Logger logger = Logger.getLogger(MySQLMonitorWorkerThread.class);
	
	public MySQLMonitorWorkerThread() {
		
	}
	
	public MySQLMonitorWorkerThread(MonitorDB monDB) {
		this.monDB = monDB;

	}
	
	public boolean isFinished()
	{
		return stopTime != null;
	}
	
	public long getElapsedTimeInMillis() throws NullPointerException {
		Calendar x = ((stopTime != null) ? stopTime : new GregorianCalendar());
		return x.getTimeInMillis() - startTime.getTimeInMillis();
	}
	
	public boolean isPassingTests() {
		if (responseString == null) {
			return false;
		}
		
		return responseString.contains("Success");
	}
	
	@Override
	public void run() {
		logger.info("Performing MySQL test for: " + monDB.getDBName());
		startTime = new GregorianCalendar();
		try {
			this.responseString = sendGetRequest(monDB.getConnectionURL());
			
		} catch (Exception e) {
			this.responseString = e.getMessage();
		}
		stopTime = new GregorianCalendar();
		logger.info("  Elapsed time: " + getElapsedTimeInMillis());
	}

	
	/**
	 * Sends an HTTP GET request to a url
	 * 
	 * @param endpoint
	 *            - The URL of the server. (Example:
	 *            " http://www.yahoo.com/search")
	 * @param requestParameters
	 *            - all the request parameters (Example:
	 *            "param1=val1&param2=val2"). Note: This method will add the
	 *            question mark (?) to the request - DO NOT add it yourself
	 * @return - The response from the end point
	 */
	private static String sendGetRequest(String endpoint) { //String requestParameters
		String result = null;
		
//		if (endpoint.startsWith("http://")) {
		if(true) {
			// Send a GET request to the servlet
			try {
				StringBuffer sb = new StringBuffer();
				 String connectionURL = "jdbc:mysql://10.16.247.2:3306/";

				  // declare a connection by using Connection interface 
				  Connection connection = null;
				  
			      // declare object of Statement interface that uses for executing sql statements.
				  Statement statement = null;

	            // declare a resultset that uses as a table for output data from the table.
				  ResultSet rs = null;
				 // int updateQuery = 0;

				  // Load JBBC driver "com.mysql.jdbc.Driver".
				  Class.forName("com.mysql.jdbc.Driver").newInstance();

				  /* Create a connection by using getConnection()
		            method that takes parameters of string type 
		            connection url, user name and password to 
		            connect to database. */
				  connection = DriverManager.getConnection
				            (connectionURL, "nettracer", "nettracer");

		        /* createStatement() is used for create 
		            statement object that is used for sending sql 
		            statements to the specified database. */
				  statement = connection.createStatement();
				  String QueryString="";
				  
				  // sql query to retrieve slave status  
				  QueryString = "Show Slave Status";
				  rs = statement.executeQuery(QueryString);
				  boolean fail=false;
				  while (rs.next()) {
					  System.out.println(""+rs.getInt("Seconds_Behind_Master"));
					  System.out.println(rs.getString("Seconds_Behind_Master"));
					  boolean nulltrue=false;
					  if(rs.getString("Seconds_Behind_Master")==null || rs.getString("Seconds_Behind_Master").equalsIgnoreCase("null"))
					  {
						  nulltrue=true;
					  }
					  
					  if((rs.getInt("Seconds_Behind_Master")>1800) || nulltrue)
					  {		String error="No Error Listed.";
						  if(!rs.getString("Last_Error").equals("") && !rs.getString("Last_Error").equals(null))
						  {
							  error=rs.getString("Last_Error");
						  }
						  sb.append("Failure: Seconds_Behind_Master is: "+rs.getString("Seconds_Behind_Master")+" Last_Errno is: "+rs.getString("Last_Errno")+" Last_Error is: "+error+" ");
						  fail=true;
					  }
				  }
				  if(!fail)
				  {
					  sb.append("Success ");
				  }
				  sb.append("Finished.");
				  result = sb.toString();
				  if(result==null)
				  {
					  result="null";
				  }
				  // close all the connections.
				  rs.close();
				  statement.close();
				  connection.close();
				  } 
			catch (Exception ex) {
				ex.printStackTrace();
				  System.out.println("Unable to connect to Database.");
			}
				
		}
		return result;
	}



	public String printFullStatus() {
		return "isFinished: " + isFinished() + "; isPassingTests: " + isPassingTests() + "; Elapsed Time: " + getElapsedTimeInMillis() + "; \nURL: " + monDB.getConnectionURL() +"\n  Response String: " + responseString;
	}

}
