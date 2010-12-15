package aero.nettracer.serviceprovider.wt_1_0.services.ishares.connection;


import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.WorldTracerISharesAccount;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerConnection;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WtHttpClient;

public class ISharesHttpClient extends WtHttpClient implements
		WorldTracerConnection {
	private static Logger logger = Logger.getLogger(ISharesHttpClient.class);

	private Calendar lastUsed = new GregorianCalendar();
	
	private WorldTracerISharesAccount account;
	private String token = null;
	Pattern tokenPattern = Pattern.compile("<input type=hidden name=Token value=(.*)>");

	public ISharesHttpClient(WorldTracerISharesAccount wta) {
		this.account = wta;
		this.getHostConfiguration().setHost(account.getHost());
	}

	@Deprecated
	public int executeMethod(HttpMethod method) throws IOException,
			HttpException {
		return executeMethod(method, "UNKNOWN FUNCTION");
	}

	public int executeMethod(HttpMethod method, String functionName)
			throws IOException, HttpException {
		writeToLog(functionName, method.getURI().toString());
		lastUsed = new GregorianCalendar();
		int returnVal;
		try {
			returnVal = super.executeMethod(method);
		} catch (HttpException e) {
			validConnection = false;
			throw e;
		} catch (IOException e) {
			validConnection = false;
			throw e;
		}
		return returnVal;
	}

	public void writeToLog(String description, String uri) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.beginTransaction();
		try {
			SQLQuery query = sess
					.createSQLQuery("INSERT INTO WT_ISHARES_TRANS_LOG (gmttime, username, description, uri) VALUES (?, ?, ?, ?)");
			query.setTimestamp(0, ServiceUtilities.getGMTDate());
			query.setString(1, account.getUsername());
			query.setString(2, truncate(description, 60));
			query.setString(3, truncate(uri, 100));
			query.executeUpdate();
			t.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			try {
				t.rollback();
			} catch (HibernateException e1) {
				// Ignore
			}
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}

	private static String truncate(String str, int maxLen) {
		return StringUtils.substring(str, 0, maxLen);
	}

	public boolean isValidConnection() {
		return validConnection;
	}

	public void setValidConnection(boolean validConnection) {
		this.validConnection = validConnection;
	}

	public WorldTracerISharesAccount getAccount() {
		return account;
	}

	public Calendar getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Calendar lastUsed) {
		this.lastUsed = lastUsed;
	}
	
	private static String getHex( byte [] raw ) {
		String HEXES = "0123456789ABCDEF";
		    if ( raw == null ) {
		      return null;
		    }
		    final StringBuilder hex = new StringBuilder( 2 * raw.length );
		    for ( final byte b : raw ) {
		      hex.append(HEXES.charAt((b & 0xF0) >> 4))
		         .append(HEXES.charAt((b & 0x0F)));
		    }
		    return hex.toString();
	}
	
	  private static String sha1(String pass){
		  try{
			  MessageDigest md = MessageDigest.getInstance("SHA-1");
			  byte [] h = md.digest(pass.getBytes());
			  return ISharesHttpClient.getHex(h);
		  } catch (Exception e){
				e.printStackTrace();
		  }
		  return null;
	  }
	
	  protected static String keyGen(){  
		  Date d = new Date();
		  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		  df.setTimeZone(TimeZone.getTimeZone("GMT"));
		  Random ran = new Random();
		  int i = ran.nextInt(Integer.MAX_VALUE);
		  return df.format(d) + String.format("%8s", Integer.toHexString(i)).replace(' ', '0').toUpperCase();
	  }
	  protected static String hmacHash(String key, String pass){
		  try{
			  return ISharesHttpClient.sha1(key + ISharesHttpClient.sha1(pass));
		  } catch (Exception e){
				e.printStackTrace();
		  }
		  return null;
	  }
	

	public boolean performLogon() {
		// Host: http://ishares.lcc.usairways.com
		try {
			// Login
			String terminalAddress = null;
			PostMethod logonPage = new PostMethod("/cgi-bin/login.cgi");
			logonPage.addParameter("ID", account.getUsername());

			logonPage.addParameter("SSU", account.getCompanyCode());
			
			if ("1".equals(account.getProfile().getParameters().get(ParameterType.USE_CLEAR_TEXT_PASS))){
				logonPage.addParameter("Password", account.getPassword());
			} else {
				logonPage.addParameter("Password", "");
			}
			String hashKey = ISharesHttpClient.keyGen();
			logonPage.addParameter("HMACval", 
					hashKey + ISharesHttpClient.hmacHash(hashKey, account.getPassword()));
			logonPage.addParameter("B1", "Login");
			this.executeMethod(logonPage, "Initial Login");
			String responseString = logonPage.getResponseBodyAsString();
			logonPage.releaseConnection();
			
			if (responseString != null && responseString.contains("/cgi-bin/term.cgi")) {
				// Continue
				Pattern p = Pattern.compile("(/cgi-bin/term.cgi.*)>");
				Matcher m = p.matcher(responseString);
				if (m.find()) {
					terminalAddress = m.group(1);
//					logger.info(terminalAddress);
				} else {
					validConnection = false;
					this.setToken(null);
					return false;
				}
			} else {
				logger.error("Was not able to login");
				return false;
			}
			
			// Load Terminal Page
			GetMethod terminal = new GetMethod(terminalAddress);
			this.executeMethod(terminal, "Get Terminal Page Login");
			Matcher m = tokenPattern.matcher(terminal.getResponseBodyAsString());
			if (m.find()) {
				String t = m.group(1);
				this.setToken(t);
			} else {
				logger.error("No token found...");
				return false;
			}
			terminal.releaseConnection();
			
			// Send sine (effectively logging into the terminal system)
			String methodName = "Sine in";
			String response = sendCommand(methodName, account.getSine());
			if (!response.contains("SINE COMPLETE")) {
				logger.error("Did not sine in correctly: " + response);
			}
			validConnection = true;
			
			
		} catch (HttpException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String sendCommand(String methodName, String command) throws IOException,
			HttpException {
		if (this.getToken() == null) {
			validConnection = false;
			return null;
		}
		
		String responseBody = null;
		PostMethod method = new PostMethod("/cgi-bin/term.cgi");
		logger.debug("Using token: " + this.getToken());
		method.addParameter("Token", this.getToken());
		method.addParameter("Type", "1");
		method.addParameter("q", command);
		method.addParameter("btnG", "Send");
		logger.info("Sending Command: " + command);
		this.executeMethod(method, methodName);
		responseBody = method.getResponseBodyAsString();

		// Parse Token & Set
		Matcher m = tokenPattern.matcher(responseBody);
		if (m.find()) {
			String t = m.group(1);
			logger.debug("Token found: " + t);
			this.setToken(t);
		} else {
			logger.debug("No token found...");
			validConnection = false;
		}

		method.releaseConnection();
		return responseBody;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		logger.debug("Token set to: " + token);
		this.token = token;
	}
}
