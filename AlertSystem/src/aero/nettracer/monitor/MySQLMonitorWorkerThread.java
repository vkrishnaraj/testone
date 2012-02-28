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
	static Logger logger = Logger.getLogger(HttpMonitorWorkerThread.class);
	
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
					  
					  if((rs.getInt("Seconds_Behind_Master")>600) || nulltrue)
					  {
						  sb.append("Failure: Seconds_Behind_Master is: "+rs.getString("Seconds_Behind_Master")+" ");
						  fail=true;
					  }
				  }
				  if(!fail)
				  {
					  sb.append("Success ");
				  }
				  sb.append("Finished.");
				  result = sb.toString();
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

	/**
	 * Reads data from the data reader and posts it to a server via POST
	 * request. data - The data you want to send endpoint - The server's address
	 * output - writes the server's response to output
	 * 
	 * @throws Exception
	 */
//	private static void postData(Reader data, URL endpoint, Writer output) throws Exception {
//		Connection urlc = null;
//		try {
//			urlc = (Connection) endpoint.openConnection();
//			try {
//				urlc.setRequestMethod("POST");
//			} catch (ProtocolException e) {
//				throw new Exception(
//						"Shouldn't happen: HttpURLConnection doesn't support POST??",
//						e);
//			}
//			urlc.setDoOutput(true);
//			urlc.setDoInput(true);
//			urlc.setUseCaches(false);
//			urlc.setAllowUserInteraction(false);
//			urlc.setRequestProperty("Content-type", "text/xml; charset="
//					+ "UTF-8");
//
//			OutputStream out = urlc.getOutputStream();
//
//			try {
//				Writer writer = new OutputStreamWriter(out, "UTF-8");
//				pipe(data, writer);
//				writer.close();
//			} catch (IOException e) {
//				throw new Exception("IOException while posting data", e);
//			} finally {
//				if (out != null)
//					out.close();
//			}
//
//			InputStream in = urlc.getInputStream();
//			try {
//				Reader reader = new InputStreamReader(in);
//				pipe(reader, output);
//				reader.close();
//			} catch (IOException e) {
//				throw new Exception("IOException while reading response", e);
//			} finally {
//				if (in != null)
//					in.close();
//			}
//
//		} catch (IOException e) {
//			throw new Exception("Connection error (is server running at "
//					+ endpoint + " ?): " + e);
//		} finally {
//			if (urlc != null)
//				urlc.disconnect();
//		}
//	}

	/**
	 * Pipes everything from the reader to the writer via a buffer
	 */
//	private static void pipe(Reader reader, Writer writer) throws IOException {
//		char[] buf = new char[1024];
//		int read = 0;
//		while ((read = reader.read(buf)) >= 0) {
//			writer.write(buf, 0, read);
//		}
//		writer.flush();
//	}

	public String printFullStatus() {
		return "isFinished: " + isFinished() + "; isPassingTests: " + isPassingTests() + "; Elapsed Time: " + getElapsedTimeInMillis() + "; \nURL: " + monDB.getConnectionURL() +"\n  Response String: " + responseString;
	}

}
