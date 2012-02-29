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
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

// TODO:
//  2. Ping public site
//  3. Session management
//  4. Status page
//  5. Success email message; delay outgoing message for alerts.
//  6. Be able to add urls through file
//  7. Change rate of test once failure detected.

public class HttpMonitorWorkerThread implements Runnable {

	private MonitorUrl monUrl;

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
	
	public HttpMonitorWorkerThread() {
		
	}
	
	public HttpMonitorWorkerThread(MonitorUrl monUrl) {
		this.monUrl = monUrl;

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
		return responseString.contains(monUrl.getTest());  
	}
	
	@Override
	public void run() {
		logger.info("Performing HttP test for: " + monUrl.getTitle());
		startTime = new GregorianCalendar();
		try {
			responseString = sendGetRequest(monUrl.getUrl(), monUrl.getParamString());
		} catch (Exception e) {
			responseString = e.getMessage();
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
	private static String sendGetRequest(String endpoint,
			String requestParameters) {
		String result = null;
//		if (endpoint.startsWith("http://")) {
		if(true) {
			// Send a GET request to the servlet
			try {

				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length() > 0) {
					urlStr += "?" + requestParameters;
				}
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection();
				conn.setConnectTimeout(120000);
				conn.setReadTimeout(120000);
//				logger.info("Connection timeout: " + conn.getConnectTimeout());
//				logger.info("Read timeout: " + conn.getReadTimeout());

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}
				rd.close();
				result = sb.toString();
				
			} catch (Exception e) {
				e.printStackTrace();
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
	private static void postData(Reader data, URL endpoint, Writer output)
			throws Exception {
		HttpURLConnection urlc = null;
		try {
			urlc = (HttpURLConnection) endpoint.openConnection();
			try {
				urlc.setRequestMethod("POST");
			} catch (ProtocolException e) {
				throw new Exception(
						"Shouldn't happen: HttpURLConnection doesn't support POST??",
						e);
			}
			urlc.setDoOutput(true);
			urlc.setDoInput(true);
			urlc.setUseCaches(false);
			urlc.setAllowUserInteraction(false);
			urlc.setRequestProperty("Content-type", "text/xml; charset="
					+ "UTF-8");

			OutputStream out = urlc.getOutputStream();

			try {
				Writer writer = new OutputStreamWriter(out, "UTF-8");
				pipe(data, writer);
				writer.close();
			} catch (IOException e) {
				throw new Exception("IOException while posting data", e);
			} finally {
				if (out != null)
					out.close();
			}

			InputStream in = urlc.getInputStream();
			try {
				Reader reader = new InputStreamReader(in);
				pipe(reader, output);
				reader.close();
			} catch (IOException e) {
				throw new Exception("IOException while reading response", e);
			} finally {
				if (in != null)
					in.close();
			}

		} catch (IOException e) {
			throw new Exception("Connection error (is server running at "
					+ endpoint + " ?): " + e);
		} finally {
			if (urlc != null)
				urlc.disconnect();
		}
	}

	/**
	 * Pipes everything from the reader to the writer via a buffer
	 */
	private static void pipe(Reader reader, Writer writer) throws IOException {
		char[] buf = new char[1024];
		int read = 0;
		while ((read = reader.read(buf)) >= 0) {
			writer.write(buf, 0, read);
		}
		writer.flush();
	}

	public String printFullStatus() {
		return "isFinished: " + isFinished() + "; isPassingTests: " + isPassingTests() + "; Elapsed Time: " + getElapsedTimeInMillis() + "; \nURL: " + monUrl.getUrl() +"\n  Response String: " + responseString;
	}

}
