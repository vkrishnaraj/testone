package com.bagnet.nettracer.wt.svc;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.mail.internet.InternetAddress;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import localhost.ProcessImage3Stub;
import localhost.ProcessSingleImageDocument;
import localhost.ProcessSingleImageResponseDocument;
import localhost.ProcessSingleImageDocument.ProcessSingleImage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.email.HtmlEmail;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.wt.WorldTracerAccount;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.wt.bmo.WorldTracerAccountBMO;
import com.bagnet.nettracer.wt.connector.NewWorldTracerConnector;

public class WorldTracerConnection extends HttpClient {

	private static final Logger logger = Logger
			.getLogger(WorldTracerConnection.class);
	
	private String host = null;
	private String mode = null;
	private WorldTracerAccount account = null;
	private Calendar lastUsed = new GregorianCalendar();
	private boolean validConnection = false;
	private Pattern pat = Pattern.compile("captcha.do\\?random=([0-9]*)");
	private Matcher mat = null;
	byte[] byteCaptcha = null;
	private int sequentialFailedAttempts = 0;
	
	public void incrementSequentialFailedAttempts() {
		++sequentialFailedAttempts;
	}
	
	private static final int DEFAULT_MILLISECOND_WAIT = 2000;

	private static final int TWO_MINUTES = 120000;

	/**
	 * Method marked as deprecated to discourage use and instead encourage
	 * use of the other executeMethods that pass in function names.
	 */
	@Deprecated
	public int executeMethod(HttpMethod method) throws IOException, HttpException {
		return executeMethod(method, "UNKNOWN FUNCTION");
	}
	
	public int executeMethod(HttpMethod method, String functionName) throws IOException, HttpException {
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
		HttpConnectionParams params = this.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(TWO_MINUTES);
		logger.debug("Creating connection to WorldTracer for: "
				+ account.getUsername());
	}

	// FOR BACKEND USE ONLY
	public boolean login() {

		
		WorldTracerAccount a = WorldTracerAccountBMO.getAccount(account.getId());
		if (a != null && !account.getPassword().equals(a.getPassword())) {
			account = a;
		}
		
		String captcha = null; 
		String captchaText = null;
		
		try {
			captcha = PropertyBMO.getValue(PropertyBMO.PROPERTY_WT_CAPTCHA);	
		} catch (Exception e) {
			captcha = null;
		}
		
		
		
		if (("1").equals(captcha)) {
			int attempts = 1;
			BufferedImage image = null;
			boolean userInputDriven = false;
			while (captchaText == null && attempts <= 2) {
				attempts++;
				String response = null;
				GetMethod initLogin = new GetMethod("/WorldTracerWeb/login.do");
				try {
					
					// GET LOGON PAGE
					this.executeMethod(initLogin, "LOGGING IN: HOME PAGE (1)");
					response = initLogin.getResponseBodyAsString();
					mat = pat.matcher(response);

					if (mat.find()) {
						logger.info("Captcha found");

						// Get Captcha
						captcha = mat.group();
						String captchaId = mat.group(1);

						logger.info("captchaMethod: /WorldTracerWeb/" + captcha);
						GetMethod captchaMethod = new GetMethod(
								"/WorldTracerWeb/" + captcha);
						this.executeMethod(captchaMethod,
								"LOGGING IN: GET CAPTCHA");
						byte[] byteCaptcha = captchaMethod.getResponseBody();

						
						// Save Image
						ByteArrayInputStream bais = new ByteArrayInputStream(byteCaptcha);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						image = ImageIO.read(bais);
						ImageIO.write(image, "bmp", baos);
						byte[] bmpImage = baos.toByteArray();
						
						File outputFile = new File(captchaId + ".bmp");
						if (!outputFile.exists()) {
							outputFile.createNewFile();
						}
						FileOutputStream fos = new FileOutputStream(outputFile);
						fos.write(bmpImage);
						fos.close();
					
						captchaText = getAutoProcessCaptcha(captchaId, bmpImage);

					} else {
						logger.error("Error parsing for captcha...");
						return false;
					}
				} catch (HttpException e1) {
					logger.error("Error getting logon page...", e1);
					return false;
				} catch (IOException e1) {
					logger.error("Error getting logon page...", e1);
					return false;
				}

				// TODO: HERE
				if ((captchaText == null && image != null && sequentialFailedAttempts > 2) || userInputDriven) {
					// Send Email
					Company company = CompanyBMO.getCompany(this.getAccount().getCompanyCode());
					
					if (!userInputDriven) {
						try {
							HtmlEmail he = new HtmlEmail();
							System.out.println(company.getVariable().getEmail_host());
							he.setHostName(company.getVariable().getEmail_host());
							he.setSmtpPort(company.getVariable().getEmail_port());
							he.setFrom("support@nettracer.aero");
							ArrayList toList = new ArrayList();
							toList.add(new InternetAddress("support@nettracer.aero"));
							he.setTo(toList);
							he.setSubject("PLEASE ENTER CAPTCHA: " + company + " ["+ TracerProperties.getInstanceLabel() + "]");
							he.setHtmlMsg("Please enter the captcha for " + company + " ["+ TracerProperties.getInstanceLabel() + "]");
							he.setCharset("UTF-8");
							he.send();
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Errors...");
						}
					}
					
					// Get Input
					userInputDriven = true;
					Icon icon = new ImageIcon(image);
					String input = (String) JOptionPane.showInputDialog(null, "Please enter the captcha text...  Hit cancel to refresh...", "Captcha Text", JOptionPane.QUESTION_MESSAGE, icon, null, null);
					System.out.println("Input provided: " + input);
					
					if (input == null) {
						captchaText = null;
						attempts = 0;
					} else {
						captchaText = input;
					}	
				}
			}
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
		if (captchaText != null) {
			login.addParameter("_captcha_confirmation_text", captchaText);
		}

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
					validConnection = true;
					this.sequentialFailedAttempts = 0;
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
	
	public void keepAlive() {
		GetMethod keepAlive = new GetMethod(NewWorldTracerConnector.WTRWEB_FLOW_URL);
		try {
			NameValuePair[] p1 = { new NameValuePair("_flowId", "traceandsearchonhandbag-flow") };
			keepAlive.setQueryString(p1);
			keepAlive.setFollowRedirects(false);
			this.executeMethod(keepAlive, "KEEP ALIVE");
			logger.info("Ran keepalive command...");
			// Provide custom retry handler is necessary
		} catch (HttpException e) {
			logger.error("Error keeping alive worldtracer connection...", e);
			return;
		} catch (IOException e) {
			logger.error("Error keeping alive worldtracer connection...", e);
			e.printStackTrace();
			return;
		} finally {
			keepAlive.releaseConnection();
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
	
	public void writeToLog(String description, String uri) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.beginTransaction();
		try {
			SQLQuery query = sess.createSQLQuery("INSERT INTO WT_WEB_TRANS_LOG (gmttime, username, description, uri) VALUES (?, ?, ?, ?)");
			query.setTimestamp(0, TracerDateTime.getGMTDate());
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

	public WorldTracerAccount getAccount() {
		return account;
	}
	
	public byte[] frontendLogin1GetCaptcha() {
		WorldTracerAccount a = WorldTracerAccountBMO.getAccount(account.getId());
		if (a != null && !account.getPassword().equals(a.getPassword())) {
			account = a;
		}
		
		String response = null;
		String captchaUrl = null;
		boolean useCaptcha = PropertyBMO.isTrue(PropertyBMO.PROPERTY_WT_CAPTCHA);
		
		
		
		if (useCaptcha) {
			GetMethod initLogin = new GetMethod("/WorldTracerWeb/login.do");
			GetMethod captchaMethod = null;
			try {
				this.executeMethod(initLogin, "LOGGING IN: HOME PAGE (1)");
				response = initLogin.getResponseBodyAsString();
				mat = pat.matcher(response);

				if (mat.find()) {
					logger.info("Captcha found");

					captchaUrl = mat.group();
					
					logger.debug("captchaMethod: /WorldTracerWeb/" + captchaUrl);
			        captchaMethod = new GetMethod("/WorldTracerWeb/" + captchaUrl);
			        this.executeMethod(captchaMethod, "LOGGING IN: GET CAPTCHA");
			        byteCaptcha = captchaMethod.getResponseBody().clone();
			        
			        
				} else {
					logger.error("Error parsing for captcha...");
					return null;
				}
			} catch (HttpException e1) {
				logger.error("Error getting logon page...", e1);
				return null;
			} catch (IOException e1) {
				logger.error("Error getting logon page...", e1);
				return null;
			} finally {
				try {
					captchaMethod.releaseConnection();
				} catch (Exception e) {
					// ignore
				}
			}
			
		}	
		return byteCaptcha;
	}
	
	public boolean frontendLogin2SetCaptcha(String captchaText) {
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
		login.addParameter("_captcha_confirmation_text", captchaText);
		

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
					validConnection = true;
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

	public byte[] getCaptcha() {
		return byteCaptcha;
	}
	
	public static String getAutoProcessCaptcha(String name, byte[] bmpImage){

		String response = null;
		try {
			// Convert Image to Bitmap
			
			// Send Web Service Call
			String captchaEndpoint = "http://75.126.93.225/ImageProcess/ProcessImage3.asmx";
			ProcessImage3Stub stub = new ProcessImage3Stub(captchaEndpoint);
			ProcessSingleImageDocument psid = ProcessSingleImageDocument.Factory.newInstance();
			ProcessSingleImage psi = psid.addNewProcessSingleImage();
			psi.setImgIn(bmpImage);
			
			ProcessSingleImageResponseDocument res = stub.ProcessSingleImage(psid);
			response = res.getProcessSingleImageResponse().getProcessSingleImageResult().trim();
			logger.info("Web Service Captcha Response: "  + name + response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Web Service Captcha Error: " + name, e);
		}
		
		if (response == null || response.contains("Could Not Decode")) {
			logger.error("Could not decode: " + name + response);
			return null;
		}
		return response;
	}
}
