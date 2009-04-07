package com.bagnet.nettracer.wt.svc;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;

public class WorldTracerConnection extends HttpClient {

	private static final Logger logger = Logger
			.getLogger(WorldTracerConnection.class);

	private String host = null;
	private String mode = null;
	private WorldTracerAccount account = null;

	public WorldTracerConnection(WorldTracerAccount account, String host,
			String mode) {
		super();
		this.host = host;
		this.mode = mode;
		this.account = account;
		org.apache.commons.httpclient.protocol.ProtocolSocketFactory ssl = new SSLProtocolSocketFactory();
		Protocol https = new Protocol("https", ssl, 443);
		this.getHostConfiguration().setHost(this.host, 443, https);
		logger.debug("Creating connection to WorldTracer for: "+ account.getUsername());
	}

	public boolean login() {
		PostMethod login = new PostMethod("/WorldTracerWeb/j_acegi_security_check");
		GetMethod redirect = null;
		login.addParameter("username", account.getUsername());
		logger.debug("Logging into WorldTracer: " + account.getUsername()); 

		if (isTrainingMode()) {
			login.addParameter("j_username", account.getUsername() + "@" + account.getCompanyCode() + "@Training");
			login.addParameter("mode", "Training");
		} else {
			login.addParameter("j_username", account.getUsername() + "@" + account.getCompanyCode()+  "@Production");
			login.addParameter("mode", "Production");
		}
		login.addParameter("j_password", account.getPassword());

		try {
			this.executeMethod(login);

			// Provide custom retry handler is necessary
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
				this.executeMethod(redirect);
				if (redirect.getStatusCode() == HttpStatus.SC_OK) {
					return true;
				}
				else {
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
			this.executeMethod(logout);
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
}
