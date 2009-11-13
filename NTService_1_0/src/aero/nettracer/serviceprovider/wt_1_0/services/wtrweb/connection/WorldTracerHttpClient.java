package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import localhost.ProcessImage3Stub;
import localhost.ProcessSingleImageDocument;
import localhost.ProcessSingleImageResponseDocument;
import localhost.ProcessSingleImageDocument.ProcessSingleImage;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.serviceprovider.common.db.WorldTracerWebAccount;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerConnection;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerServiceImpl;

public class WorldTracerHttpClient extends HttpClient implements
		WorldTracerConnection {

	private static final int DEFAULT_MILLISECOND_WAIT = 2000;
	private static final int TWO_MINUTES = 120000;

	private Pattern pat = Pattern.compile("captcha.do\\?random=([0-9]*)");
	private Matcher mat = null;
	private byte[] byteCaptcha = null;
	private Calendar captchaTimestamp = null;
	private Integer key = null;

	private Calendar lastUsed = new GregorianCalendar();
	private boolean validConnection = false;
	private boolean isWaitingOnCaptcha = false;
	private WorldTracerWebAccount account;
	


	private static final Logger logger = Logger
			.getLogger(WorldTracerHttpClient.class);
	

	@Deprecated
	public WorldTracerHttpClient() {
		super();
	}

	@Deprecated
	public WorldTracerHttpClient(HttpClientParams params,
			HttpConnectionManager httpConnectionManager) {
		super(params, httpConnectionManager);
	}

	@Deprecated
	public WorldTracerHttpClient(HttpClientParams arg0) {
		super(arg0);
	}

	@Deprecated
	public WorldTracerHttpClient(HttpConnectionManager httpConnectionManager) {
		super(httpConnectionManager);
	}

	public WorldTracerHttpClient(WorldTracerWebAccount wta, Integer key) {
		this.account = wta;
		this.key = key;
		org.apache.commons.httpclient.protocol.ProtocolSocketFactory ssl = new SSLProtocolSocketFactory();
		Protocol https = new Protocol("https", ssl, 443);
		this.getHostConfiguration().setHost(account.getHost(), 443, https);
		HttpConnectionParams params = this.getHttpConnectionManager()
				.getParams();
		params.setConnectionTimeout(TWO_MINUTES);
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

	public int executeMethodWithPause(HttpMethod method, String functionName)
			throws IOException, HttpException {
		return executeMethodWithPause(method, DEFAULT_MILLISECOND_WAIT,
				functionName);
	}

	public int executeMethodWithPause(HttpMethod method, long pauseInMillis,
			String functionName) throws IOException, HttpException {
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

	public void writeToLog(String description, String uri) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.beginTransaction();
		try {
			SQLQuery query = sess
					.createSQLQuery("INSERT INTO WT_WEB_TRANS_LOG (gmttime, username, description, uri) VALUES (?, ?, ?, ?)");
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

	public WorldTracerWebAccount getAccount() {
		return account;
	}

	public boolean isWaitingOnCaptcha() {
		return isWaitingOnCaptcha;
	}

	public void setWaitingOnCaptcha(boolean isWaitingOnCaptcha) {
		this.isWaitingOnCaptcha = isWaitingOnCaptcha;
	}

	public byte[] getByteCaptcha() {
		return byteCaptcha;
	}

	public void setByteCaptcha(byte[] byteCaptcha) {
		this.byteCaptcha = byteCaptcha;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Calendar getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Calendar lastUsed) {
		this.lastUsed = lastUsed;
	}

	public Calendar getCaptchaTimestamp() {
		return captchaTimestamp;
	}

	public void setCaptchaTimestamp(Calendar captchaTimestamp) {
		this.captchaTimestamp = captchaTimestamp;
	}

	public void getLogonPageAndCaptcha() throws WorldTracerException {
		String response = null;
		String captchaUrl = null;
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
		        byte[] originalByteCaptcha = captchaMethod.getResponseBody().clone();
				
		        ByteArrayInputStream bais = new ByteArrayInputStream(originalByteCaptcha);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedImage image = ImageIO.read(bais);
				ImageIO.write(image, "bmp", baos);
				byteCaptcha = baos.toByteArray();
				captchaTimestamp = new GregorianCalendar();
				this.isWaitingOnCaptcha = true;
		        
			} else {
				this.captchaTimestamp = new GregorianCalendar();
				this.validConnection = false;
				logger.error("Error parsing for captcha...");
				throw new WorldTracerException("Error parsing for captcha...");
			}
		} catch (HttpException e1) {
			this.captchaTimestamp = new GregorianCalendar();
			this.validConnection = false;
			logger.error("Error getting logon page...", e1);
			throw new WorldTracerException("Could not acquire logon page: HttpException");
		} catch (IOException e1) {
			this.captchaTimestamp = new GregorianCalendar();
			this.validConnection = false;
			logger.error("Error getting logon page...", e1);
			throw new WorldTracerException("Could not acquire logon page: IOException");
		} finally {
			try {
				captchaMethod.releaseConnection();
			} catch (Exception e) {
				// ignore
			}
		}
	}

	public boolean performLogon(boolean loginWithCaptcha, String captchaText) {
		PostMethod login = new PostMethod("/WorldTracerWeb/j_acegi_security_check");
		GetMethod redirect = null;
		login.addParameter("username", account.getUsername());
		logger.debug("Logging into WorldTracer: " + account.getUsername());

		
		if (this.getAccount().isTrainingMode()) {
			login.addParameter("j_username", account.getUsername() + "@"
					+ account.getCompanyCode() + "@Training");
			login.addParameter("mode", "Training");
		} else {
			login.addParameter("j_username", account.getUsername() + "@"
					+ account.getCompanyCode() + "@Production");
			login.addParameter("mode", "Production");
		}
		
		login.addParameter("j_password", account.getPassword());
		if (loginWithCaptcha) {
			login.addParameter("_captcha_confirmation_text", captchaText);
		}
		
		this.byteCaptcha = null;
		this.captchaTimestamp = new GregorianCalendar();
		this.isWaitingOnCaptcha = false;
		

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
		return true;
	}
	

	public String getAutoProcessCaptcha(byte[] bmpImage){

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
			logger.info("Web Service Captcha Response: "  + response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Web Service Captcha Error: ", e);
		}
		
		if (response == null || response.contains("Could Not Decode")) {
			logger.error("Could not decode: " + response);
			return null;
		}
		return response;
	}

	public void keepAlive() {
		WorldTracerServiceImpl.keepAlive(this);
		
  }
}
