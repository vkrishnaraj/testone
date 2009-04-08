package com.bagnet.nettracer.wt.svc;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;
import com.bagnet.nettracer.wt.bmo.WorldTracerAccountBMO;

public class WorldTracerConnection extends HttpClient {

	private static final Logger logger = Logger
			.getLogger(WorldTracerConnection.class);
	
	private static final Logger hitCounter = Logger
	.getLogger("WORLD_TRACER_HIT_LOG");

	private String host = null;
	private String mode = null;
	private WorldTracerAccount account = null;
	private Calendar lastUsed = new GregorianCalendar();
	private boolean validConnection = false;

	private static final int DEFAULT_MILLISECOND_WAIT = 2000;

	/**
	 * Method marked as deprecated to discourage use and instead encourage
	 * use of the other executeMethods that pass in function names.
	 */
	@Deprecated
	public int executeMethod(HttpMethod method) throws IOException, HttpException {
		return executeMethod(method, "UNKNOWN FUNCTION");
	}
	
	public int executeMethod(HttpMethod method, String functionName) throws IOException, HttpException {
		hitCounter.info(account.getUsername() + "\t" + functionName + "\t" + method.getURI());
		lastUsed = new GregorianCalendar();
		return super.executeMethod(method);
	}

	public int executeMethodWithPause(HttpMethod method, String functionName) throws IOException,
			HttpException {
		return executeMethodWithPause(method, DEFAULT_MILLISECOND_WAIT, functionName);
	}

	public int executeMethodWithPause(HttpMethod method, long pauseInMillis, String functionName)
			throws IOException, HttpException {
		GregorianCalendar newDate = new GregorianCalendar();
		long millisDifference = newDate.getTimeInMillis()
				- lastUsed.getTimeInMillis();
		if (millisDifference > 0 & millisDifference < pauseInMillis) {
			try {
				Thread.sleep(pauseInMillis - millisDifference);
			} catch (InterruptedException e) {
				// Ignore
			}
		}
		return executeMethod(method, functionName);
	}

	public WorldTracerConnection(WorldTracerAccount account, String host,
			String mode) {
		super();
		this.host = host;
		this.mode = mode;
		this.account = account;
		org.apache.commons.httpclient.protocol.ProtocolSocketFactory ssl = new SSLProtocolSocketFactory();
		Protocol https = new Protocol("https", ssl, 443);
		this.getHostConfiguration().setHost(this.host, 443, https);
		logger.debug("Creating connection to WorldTracer for: "
				+ account.getUsername());
	}

	public boolean login() {
		WorldTracerAccount a = WorldTracerAccountBMO.getAccount(account.getId());
		if (a != null && !account.getPassword().equals(a.getPassword())) {
			account = a;
		}

		PostMethod login = new PostMethod("/WorldTracerWeb/j_acegi_security_check");
		GetMethod redirect = null;
		login.addParameter("username", account.getUsername());
		logger.debug("Logging into WorldTracer: " + account.getUsername());

		if (isTrainingMode()) {
			login.addParameter("j_username", account.getUsername() + "@"
					+ account.getCompanyCode() + "@Training");
			login.addParameter("mode", "Training");
		} else {
			login.addParameter("j_username", account.getUsername() + "@"
					+ account.getCompanyCode() + "@Production");
			login.addParameter("mode", "Production");
		}
		login.addParameter("j_password", account.getPassword());

		try {
			this.executeMethod(login, "LOGGING IN: INITIAL");
		} catch (HttpException e) {
			logger.error("Error logging in to worldtracer...", e);
			return false;
		} catch (IOException e) {
			logger.error("Error logging in to worldtracer...", e);
			return false;
		} finally {
			login.releaseConnection();
		}

		int result = login.getStatusCode();
		if (result == HttpStatus.SC_MOVED_TEMPORARILY) {
			String newLoc = login.getResponseHeader("location").getValue();
			redirect = new GetMethod(newLoc);
			try {
				this.executeMethod(redirect, "LOGGING IN: REDIRECT");
				if (redirect.getStatusCode() == HttpStatus.SC_OK) {
					return true;
				} else {
					return false;
				}
			} catch (HttpException e) {
				logger.error("Error logging in to worldtracer...", e);
				return false;
			} catch (IOException e) {
				logger.error("Error logging in to worldtracer...", e);
				return false;
			} finally {
				redirect.releaseConnection();
			}
		}
		return false;

	}

	public void logout() {
		GetMethod logout = new GetMethod("/WorldTracerWeb/j_acegi_logout");
		try {
			this.executeMethod(logout, "LOGGING OUT");
			// Provide custom retry handler is necessary
		} catch (HttpException e) {
			logger.error("Error logging out of worldtracer...", e);
			return;
		} catch (IOException e) {
			logger.error("Error logging out of worldtracer...", e);
			e.printStackTrace();
			return;
		} finally {
			logout.releaseConnection();
		}
	}

	private boolean isTrainingMode() {
		if (mode.equalsIgnoreCase("TRAINING")) {
			return true;
		}
		return false;
	}

	public Calendar getLastUsed() {
		return lastUsed;
	}
}
