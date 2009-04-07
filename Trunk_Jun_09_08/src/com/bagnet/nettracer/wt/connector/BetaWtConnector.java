package com.bagnet.nettracer.wt.connector;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.wt.WorldTracerAlreadyClosedException;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.svc.DefaultWorldTracerService;
import com.bagnet.nettracer.wt.svc.RuleMapper;
import com.bagnet.nettracer.wt.svc.WorldTracerRule;
import com.bagnet.nettracer.wt.svc.WorldTracerService;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public class BetaWtConnector implements WorldTracerConnector {

	private static final String OK = "OK";

	private static final String ALREADY_REINSTATED = "ALREADY REINSTATED";

	private String wtCompanycode;
	
	public static enum UserType {WM, WT};
	
	private UserType userType = UserType.WT;

	private static final Logger logger = Logger.getLogger(BetaWtConnector.class);
	
	private static final Pattern ahl_patt = Pattern.compile("(?:(?:\\s|/)(?:AHL\\s+|A/|FILE\\s+))(\\w{5}\\d{5})\\b");
	private static final Pattern ohd_patt = Pattern.compile("(?:(?:\\s|/)(?:OHD\\s+|O/|ON-HAND\\s+))(\\w{5}\\d{5})\\b");
	private static final Pattern percent_patt = Pattern.compile("SCORE\\s*-\\s*(\\d+(\\.\\d{1,2})?)");
	private static final Pattern itemNum_patt = Pattern.compile("^\\s*(\\d+)/", Pattern.MULTILINE);
	private static final Pattern qoh_success = Pattern.compile("rohOK\\s*\\(\\s*[01]\\s*\\)");

	private static final Object ALREADY_SUSPENDED = "ALREADY SUSPENDED";

	private HttpClient client;
	
	private static Map<String, BetaWtConnector> _instanceMap = new HashMap<String, BetaWtConnector>();
	
	private RuleMapper wtRuleMap;

	private BetaWtConnector(String companyCode) {
		this.wtCompanycode = companyCode;
		client = connectWT(null, companyCode);
	}
	
	public static synchronized WorldTracerConnector getInstance(String companyCode) {
		if (_instanceMap == null) {
			_instanceMap = new HashMap<String, BetaWtConnector>();
			_instanceMap.put(companyCode.toUpperCase(), new BetaWtConnector(companyCode));
		}
		else if (_instanceMap.get(companyCode.toUpperCase()) == null) {
			_instanceMap.put(companyCode.toUpperCase(), new BetaWtConnector(companyCode));
		}
		return _instanceMap.get(companyCode.toUpperCase());
	}
	
	private HttpClient connectWT(String urlext,String companycode) {
		//get worldtracer user info
		Company_Specific_Variable comsv = AdminUtils.getCompVariable(companycode);
		if (comsv.getWt_enabled() == 0) {
			return null;
		}

		String wt_user = comsv.getWt_user();
		String wt_pass = comsv.getWt_pass();
		String wt_http = comsv.getWt_url();
		if(wt_http == null || wt_http.trim().length() == 0) {
			wt_http = TracingConstants.DEFAULT_WT_URL;
		}

		Credentials defaultcreds = new UsernamePasswordCredentials(wt_user, wt_pass);
		MultiThreadedHttpConnectionManager cm = new MultiThreadedHttpConnectionManager();
		HostConfiguration hc = new HostConfiguration();
		hc.setHost(wt_http);
		cm.getParams().setMaxConnectionsPerHost(hc, 8);
		HttpClient new_client = new HttpClient(cm);
		new_client.getParams().setAuthenticationPreemptive(true);
		new_client.getState().setCredentials(new AuthScope(wt_http, 80, AuthScope.ANY_REALM), defaultcreds);

		return new_client;

	}
	public String amendAhl(Map<WorldTracerField, List<String>> fieldMap, String wt_ahl_id) throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseAAH"));
		sb.append("A2=" + userType.name());
		
		sb.append("FR="+ wt_ahl_id);
		sb.append("AAH=");
		sb.append(DefaultWorldTracerService.FIELD_SEP);
		
		ArrayList<String> temp = new ArrayList<String>();
//		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : DefaultWorldTracerService.AMEND_AHL_FIELD_RULES.entrySet()) {
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.AMEND_AHL).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
			else {
				String result =entry.getValue().getFieldString(entry.getKey(), null);
				if (result != null) {
					temp.add(result);
				}
			}
		}
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll("\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();

		String responseBody = null;
		try {
			logger.info("GETSTring creat AHL: " + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}

		if( responseBody.toUpperCase().contains("WAS AMENDED")) {
			return "AHL AMENDED";
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to amend AHL. See logs for details";
			}
			if(errorString.toUpperCase().contains("/CLOSED")) {
				throw new WorldTracerAlreadyClosedException(errorString);
			}
			throw new WorldTracerException(errorString);
		}
	}

	public String amendOhd(Map<WorldTracerField, List<String>> fieldMap, String wt_ohd_id) throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseAOH"));
		sb.append("&A2=" + userType.name());
		sb.append("&FR="+ wt_ohd_id);
		sb.append("&AOH=");
		sb.append(DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.AMEND_OHD).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
			else {
				String result =entry.getValue().getFieldString(entry.getKey(), null);
				if (result != null) {
					temp.add(result);
				}
			}
		}
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll("\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();

		String responseBody = null;
		try {
			logger.info("GETSTring amend OHD: " + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}

		if( responseBody.toUpperCase().contains("FILE WAS AMENDED")) {
			return "OHD AMENDED";
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to amend OHD. See logs for details";
			}
			throw new WorldTracerException(errorString);
		}
	}
	
	public String requestQoh(String fromStation, String fromAirline, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap)
	throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseROH"));
		sb.append("A2=" + userType.name());
		sb.append("T1="+wt_ahl_id);
		sb.append("STN="+fromStation);
		sb.append("ARL="+fromAirline);
		sb.append("ROH=");
		sb.append(DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.REQUEST_QOH).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString().replaceAll("\\s+", "%20");

		String responseBody = null;
		try {
			logger.info("GETSTring Request QOH: " + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		Matcher m1 = qoh_success.matcher(responseBody);
		if(( responseBody.toUpperCase().split("ROH MESSAGE SENT").length >= 3) || m1.find()) {
			return "QOH Requested";
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to Request QOH. See logs for details";
			}
			throw new WorldTracerException(errorString);
		}
		
	}
	public String requestOhd(String wt_ohd_id, String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseROH"));
		sb.append("A2=" + userType.name());
		sb.append("T1="+wt_ahl_id);
		sb.append("ROH=");
		sb.append(DefaultWorldTracerService.FIELD_SEP + "OHD " + wt_ohd_id + DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.REQUEST_OHD).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString().replaceAll("\\s+", "%20");

		String responseBody = null;
		try {
			logger.info("GETSTring Request OHD: " + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		Matcher m1 = qoh_success.matcher(responseBody);
		if(( responseBody.toUpperCase().split("ROH MESSAGE SENT").length >= 3) || m1.find()) {
			return "OHD Requested";
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to ROH. See logs for details";
			}
			throw new WorldTracerException(errorString);
		}
	}
	
	
	public String insertIncident(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("AHL"));
		sb.append("&A2=" + userType.name() + "&STNARL=");
		sb.append(stationCode.toUpperCase());
		sb.append(companyCode.toUpperCase());
		sb.append("&AHL=");
		sb.append(DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.CREATE_AHL).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();

		String responseBody = null;
		try {
			logger.info("GETSTring creat AHL: " + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}

		String wt_id = null;
		String errorString = null;
		Pattern success_patt = Pattern.compile("AHL\\s+(\\w{3}\\w{2}\\d{5})");
		Matcher m = success_patt.matcher(responseBody);
		Pattern success2_patt = Pattern.compile("WT\\s+DAH\\s+(\\w{3}\\w{2}\\d{5})");
		Matcher m2 = success2_patt .matcher(responseBody);
		if (m.find()) {
			wt_id = m.group(1);
		} 
		else if(m2.find()) {
			wt_id = m2.group(1);
		}
		else {
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
		}

		if (wt_id != null && wt_id.length() >= 10) {
			return wt_id;
		} else {
			throw new WorldTracerException(errorString);
		}
	}

	public String closeIncident(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String wt_id, String stationCode)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseCAH"));
		sb.append("A2=" + userType.name());
		sb.append("FR=" + wt_id);
		sb.append("CAH=" + DefaultWorldTracerService.FIELD_SEP);
		
		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.CLOSE_AHL).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}
		
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();
		String responseBody = null;
		try {
			logger.info("GETSTring close AHL: " + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		if( responseBody.toUpperCase().contains("FILE WAS CLOSED") || responseBody.toUpperCase().contains("FILE CLOSED")) {
			return wt_id;
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
			throw new WorldTracerException(errorString);
		}
	}
	
	public String sendFwd(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String stationCode, String companyCode) throws WorldTracerException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("FWD"));
		sb.append("A2=" + userType.name() + "STNARL=");
		sb.append(stationCode.toUpperCase());
		sb.append(companyCode.toUpperCase());
		sb.append("FWD=" + DefaultWorldTracerService.FIELD_SEP);
		
		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.FWD_GENERAL).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}
		
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "_");
		
		sb.append(queryString);
		String getstring = sb.toString();
		String responseBody = null;
		try {
			logger.info("GETSTring FWD: " + getstring);
			GetMethod method = new GetMethod();
			method.getParams().setParameter(HttpMethodParams.STRICT_TRANSFER_ENCODING, false);
			URI uri = new URI(getstring, false);
			method.setURI(uri);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString)) {
			return errorString;
		}
		throw new WorldTracerException(errorString);
	}

	public String insertOhd(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("OHD"));
		sb.append("&A2=" + userType.name() + "&STNARL=");
		sb.append(stationCode.toUpperCase());
		sb.append(companyCode.toUpperCase());
		sb.append("&OHD=" + DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.CREATE_OHD).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}

		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();

		String responseBody = null;
		try {
			logger.info("GETSTring create ohd:" + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		String wt_id = null;
		String errorString = null;
		Pattern success_patt = Pattern.compile("W(?:M|T)T1=(\\w{3}\\w{2}\\d{5})\\b");
		Matcher m = success_patt.matcher(responseBody);
		if (m.find()) {
			wt_id = m.group(1);
		} else {
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
		}

		if (wt_id != null && wt_id.length() >= 10) {
			return wt_id;
		} else {
			throw new WorldTracerException(errorString);
		}
	}
	
	public String closeOhd(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap, String wt_id, String wt_stationcode) throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseCOH"));
		sb.append("A2=" + userType.name());
		sb.append("T1=" + wt_id);
		sb.append("COH=" + DefaultWorldTracerService.FIELD_SEP);
		
		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerService.WorldTracerField, WorldTracerRule<String>> entry : wtRuleMap.getRule(TxType.CLOSE_OHD).entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(entry.getValue().getFieldString(entry.getKey(), fieldMap.get(entry.getKey())));
			}
		}
		
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();
		String responseBody = null;
		try {
			logger.info("GETSTring close ohd:" + getstring);
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		if( responseBody.toUpperCase().contains("FILE WAS CLOSED") || responseBody.toUpperCase().contains("FILE CLOSED")) {
			return wt_id;
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
			throw new WorldTracerException(errorString);
		}
	}
	
	public void eraseActionFile(String station_id, String companyCode, ActionFileType area, int day, int itemNum) throws WorldTracerException {
		String responseBody = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		StringBuilder sb = new StringBuilder("http://" + wt_http + "/");
		sb.append("cgi-bin/bagEXF.exe");
		
		PostMethod method = new PostMethod(sb.toString());
		method.addParameter("STN", station_id);
		method.addParameter("ARL", wtCompanycode);
		method.addParameter("A1", companyCode);
		method.addParameter("AREA", area.name());
		method.addParameter("DAY", "D" + day);
		method.addParameter("ITEM", Integer.toString(itemNum));
		method.addParameter("submit" , "EXF");
		method.addParameter("A2", userType.name());
		
		try {
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString)) {
			return;
		}
		throw new WorldTracerException(errorString);
		
	}


	public Worldtracer_Actionfiles getActionFile(String airline, String station, ActionFileType actionFileType,
			int day, int itemNum) throws WorldTracerException {
		String afData = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		PostMethod method = buildAfBaseMethod(airline, station, actionFileType, day, wt_http);
		method.setParameter("ITEM", Integer.toString(itemNum));
		try {
			afData = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		List<Worldtracer_Actionfiles> result = parseActionFileData(airline, station, actionFileType, day, afData);
		if(result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	public List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType,
			int day) throws WorldTracerException {
		return getActionFiles(airline, station, actionFileType, day, 0, 0);
	}

	public List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType, int day, int startItem, int endItem)
			throws WorldTracerException {
		String afData = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		PostMethod method = buildAfBaseMethod(airline, station, actionFileType, day, wt_http);
		if(startItem > 0 && endItem >= startItem) {
			String itemString = String.format("%d-%d", startItem, endItem);
			method.setParameter("ITEM", itemString);
		}
		try {
			afData = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		return parseActionFileData(airline, station, actionFileType, day, afData);
	}


	public String findAHL(String wt_id) throws WorldTracerException {
		GetMethod method = new GetMethod( buildUrlStart("DAH") + "A2=" + userType.name() + "T1=" + wt_id.toUpperCase());
		try {
			return sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
	}

	public String findOHD(String wt_id) throws WorldTracerException {
		GetMethod method = new GetMethod( buildUrlStart("DOH") + "A2=" + userType.name() + "T1=" + wt_id.toUpperCase());
		try {
			return sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
	}
	
	private List<Worldtracer_Actionfiles> parseActionFileData(String airline, String station,
			ActionFileType actionFileType, int day, String afData) {
		String ac_start = "<input type=\"hidden\" name=\"menuITEM\" value=\"";
		String ac_end = "\">";
		
		List<Worldtracer_Actionfiles> result = new ArrayList<Worldtracer_Actionfiles>();

		// if it's a form based response we get the actionFile text out of
		// the hidden input fields
		boolean found = false;
		Pattern form_pattern = Pattern.compile(ac_start + "(.*?)" + ac_end, Pattern.CASE_INSENSITIVE
				| Pattern.DOTALL);
		Matcher m = form_pattern.matcher(afData);
		while (m.find()) {
			found = true;
			if (logger.isDebugEnabled()) {
				logger.debug("Parsed Item: \n" + m.group(1).trim());
			}
			result.add(createActionFile(m.group(1), actionFileType, day, station, airline));
		}
		// if there were no form based entries, try to use the preformatted
		// section, assuming no forms
		if (!found) {
			Pattern pre_pattern = Pattern.compile("<pre.*?>(.*?)</pre>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = pre_pattern.matcher(afData);
			if (m.find()) {
				String contents = m.group(1);
				contents = contents.replaceAll("(<br>)|(<BR>)", "\n");
				Pattern af_patt = Pattern.compile(
						"(?:\n|^|>|\r)(\\d+/.*?)(?=(((\\n|>|\\r)\\d+/)|$|(END OF REPORT)))",
						Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				m = af_patt.matcher(contents);
				while (m.find()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Parsed Item: \n" + m.group(1).trim());
					}
					result.add(createActionFile(m.group(1), actionFileType, day, station, airline));
				}
			}
		}
		return result;
	}

	private PostMethod buildAfBaseMethod(String airline, String station, ActionFileType actionFileType, int day,
			String wt_http) {
		StringBuilder sb = new StringBuilder("http://" + wt_http + "/");
		sb.append("cgi-bin/bagDXF.exe");
		PostMethod method = new PostMethod(sb.toString());
		method.addParameter("STN", station.toUpperCase());
		method.addParameter("ARL", airline.toUpperCase());
		method.addParameter("AREA", actionFileType.name());
		method.addParameter("DAY", "D" + day);
		method.addParameter("submit", "DXF");
		method.addParameter("A2", userType.name());
		return method;
	}

	private String getFields(WorldTracerService.WorldTracerField field, List<String> list, Object[] rules) {
		ArrayList<String> temp = new ArrayList<String>();

		List<String> subset;

		int max = (Integer) rules[0];
		DefaultWorldTracerService.RepeatType repeat = (DefaultWorldTracerService.RepeatType) rules[1];

		if (list.size() > max) {
			subset = list.subList(0, max);
		} else {
			subset = list;
		}

		switch (repeat) {
		case NONE:
			return field + list.get(0);
		case MULTIPLE:
			for (String entry : subset) {
				temp.add(field + entry);
			}
			return StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP);
		case SAME_LINE:
			return field + StringUtils.join(subset, DefaultWorldTracerService.ENTRY_SEP);
		case MANY_LINES:
			return field
					+ StringUtils.join(subset, DefaultWorldTracerService.FIELD_SEP
							+ DefaultWorldTracerService.CONTINUATION);
		}
		return "";
	}

	private String buildUrlStart(String requestType) {
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		StringBuilder sb = new StringBuilder("http://" + wt_http + "/");
		sb.append("cgi-bin/bag" + requestType + ".exe?A1=");
		sb.append(wtCompanycode.toLowerCase());
		return sb.toString();
	}

	private String sendRequest(HttpMethod method) throws IOException {

		String responseBody = "";
		try {

			method.setDoAuthentication(true);

			// Provide custom retry handler is necessary
			method.getParams()
					.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
			method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 120000);
			
			logger.info("query string is: " + method.getQueryString());
			logger.info("path is: " +  method.getPath());

			// Execute the method.
			int statusCode = client.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				logger.error("Method failed: " + method.getStatusLine());
				throw new IOException("http request failed with response code of " + statusCode);
			}

			// Read the response body.
			responseBody = method.getResponseBodyAsString();
		} finally {
			if (method != null) {
				method.releaseConnection();
			}
		}
		return responseBody;
	}

	private Worldtracer_Actionfiles createActionFile(String rawText, ActionFileType actionFileType, int day,
			String station, String airline) {

		String wt_ahl_id = "";
		String wt_ohd_id = "";
		double percent = 0;
		int itemNum = 0;

		Matcher m = ahl_patt.matcher(rawText);
		if (m.find()) {
			wt_ahl_id = m.group(1);
		}
		m = ohd_patt.matcher(rawText);
		if (m.find()) {
			wt_ohd_id = m.group(1);
		}
		m = itemNum_patt.matcher(rawText);
		if (m.find()) {
			itemNum = Integer.parseInt(m.group(1));
		}
		if (actionFileType == ActionFileType.WM) {
			m = percent_patt.matcher(rawText);
			if (m.find()) {
				percent = Double.parseDouble(m.group(1));
			}
		}

		Worldtracer_Actionfiles waf = new Worldtracer_Actionfiles();
		waf.setAction_file_text(rawText.trim());
		waf.setAction_file_type(actionFileType);
		waf.setAirline(airline);
		waf.setDay(day);
		waf.setItem_number(itemNum);
		waf.setPercent_match(percent);
		waf.setStation(station);
		waf.setWt_incident_id(wt_ahl_id);
		waf.setWt_ohd_id(wt_ohd_id);

		return waf;
	}

	public void suspendAHL(String wt_id, String agent) throws WorldTracerException {
		// TODO Auto-generated method stub
		String responseBody = susritItem(wt_id, "SUS", "AHL", agent);
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString) || ALREADY_SUSPENDED.equals(errorString)) {
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void suspendOHD(String wt_id, String agent) throws WorldTracerException {
		// TODO Auto-generated method stub
		String responseBody = susritItem(wt_id, "SUS", "OHD", agent);
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString) || ALREADY_SUSPENDED.equals(errorString)) {
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void reinstateAHL(String wt_id, String agent) throws WorldTracerException {

		//TODO figure out if it worked
		String responseBody = susritItem(wt_id, "RIT", "AHL", agent);
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString) || ALREADY_REINSTATED.equals(errorString)) {
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void reinstateOHD(String wt_id, String agent) throws WorldTracerException {
		// TODO Auto-generated method stub\
		String responseBody = susritItem(wt_id, "RIT", "OHD", agent);
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString) || ALREADY_REINSTATED.equals(errorString)) {
			return;
		}
		throw new WorldTracerException(errorString);
	}
	
	private String susritItem(String wt_id, String action, String type, String agent) {
		String responseBody = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		StringBuilder sb = new StringBuilder("http://" + wt_http + "/");
		sb.append("cgi-bin/bagSUS.exe");
		
		PostMethod method = new PostMethod(sb.toString());
		method.addParameter("A1", wtCompanycode.toUpperCase());
		method.addParameter("A2", userType.name());
		method.addParameter("ACTION", action + "1");
		method.addParameter("TYP1", type);
		method.addParameter("FR1", wt_id.toLowerCase());
		for(int i=2; i <= 10; i++)  {
			method.addParameter("TYP" + i, type);
			method.addParameter("FR" + i, "");
		}
		method.addParameter("AG", agent);
		method.addParameter("B1", "Submit " + action);

		try {
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		return responseBody;
	}

	public String forwardOhd(Map<WorldTracerField, List<String>> fieldMap, String ohd_id, String ahl_id) throws WorldTracerException {
		String responseBody = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		StringBuilder sb = new StringBuilder("http://" + wt_http + "/");
		sb.append("cgi-bin/bagResponseFOH.exe");
		
		PostMethod method = new PostMethod(sb.toString());
		

		
		method.addParameter("AHL", ahl_id);
		
		
		if(fieldMap.containsKey(WorldTracerField.NM)) {
			method.addParameter("NM", fieldMap.get(WorldTracerField.NM).get(0));
		}
		if(fieldMap.containsKey(WorldTracerField.FO)) {
			List<String> itinList = fieldMap.get(WorldTracerField.FO);
			
			for (int i = 0; i < itinList.size() && i < 4; i++) {
				String fo = itinList.get(i);
				method.addParameter("FO" + (i+1), fo);
			}
		}
		if(fieldMap.containsKey(WorldTracerField.FW)) {
			List<String> routeList = fieldMap.get(WorldTracerField.FW);
			
			for (int i = 0; i < routeList.size() && i < 5; i++) {
				String fw = routeList.get(i);
				method.addParameter("FW" + (i+1), fw);
			}
		}
		if(fieldMap.containsKey(WorldTracerField.XT)) {
			method.addParameter("XT1", fieldMap.get(WorldTracerField.XT).get(0));
		}
		
		if(fieldMap.containsKey(WorldTracerField.SI)) {
			method.addParameter("SI1", fieldMap.get(WorldTracerField.SI).get(0));
		}
		if(fieldMap.containsKey(WorldTracerField.TX)) {
			List<String> ttList = fieldMap.get(WorldTracerField.TX);
			for (int i = 0; i < ttList.size() && i < 4; i++) {
				String tt = ttList.get(i);
				method.addParameter("TX" + (i+1), tt);
			}
		}
		if(fieldMap.containsKey(WorldTracerField.AG)) {
			method.addParameter("AG", fieldMap.get(WorldTracerField.AG).get(0).toUpperCase());
		}
		method.addParameter("A1", wtCompanycode);
		method.addParameter("OHD1", ohd_id);
		method.addParameter("B1", "Submit FOH");
		
		try {
			responseBody = sendRequest(method);
		}	catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		String errorString;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/.*</body>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if(OK.equals(errorString)) {
			return errorString;
		}
		throw new WorldTracerException(errorString);
	}

	/**
	 * 
	 */
	public String createBdo(Map<WorldTracerField, List<String>> fieldMap, String ahl_id, String ohd_id,
			DeliverCompany delivercompany, Station station) throws WorldTracerException {
			// TODO Auto-generated method stub
		/* in yet another fun twist, this uses a post request but basically you just enter a bunch of "LINE1"
		 * "LINE2" etc parameters that represent the lines that would be sent to the worldtracer system.  However
		 * it also appears you have up to 17 lines to work with in roughly the following format where data on 
		 * 
		 * !LINE1=WM BDO/I <<AHL|OHD>> <<WT FILE REF>> /<<GMT TIME>>/<<GMT DATE>>
		 * !LINE2=DS <<STATIONCODE>><<ARLINE>>01 - <<DELIVERY SERVICE NAME>>/<<DS PHONE>>
		 * !LINE3=         <<STATION NAME>> / <<STATION PHONE>>
		 * !LINE4= SVC LVL <<LEVEL>>/TN
		 * LINE5=NM01 <<NAME>> .NM02 .NM03
		 * !LINE6=PA01 <<Permanent address (we aren't sending)>>
		 * LINE7=PA02 << Perm adresss 2>>
		 * LINE7=DA01 <<DELIVERY ADDRESS>>
		 * LINE9=DA02 << DA 2>>
		 * !LINE8= .DD <<DELIVERY DATE>>
		 * LINE9=PN01 <<PERM PHONE>>
		 * !LINE10=LD01 <<DELIVERY INSTRUCTIONS>>
		 * !LINE11=CT01 <<BAG COLOR/TYPE>> [.CT02 <<CT>>] ...
		 * LINE12= /RECHARGE -    /SIGNATURE FOR THE RECEIPT OF <<# OF BAGS>> BAG   .RT <<RT BAG TOOK TO GET TO THIS STATION?????>>
		 */
		int linenum = 1;
		
		List<String> lines = new ArrayList<String>();
		String tempName = delivercompany.getName();
		if(tempName == null || tempName.trim().length() < 1) {
			tempName = "DEFAULT DELIVERY CO";
		}
		else if (tempName.length() > 26) {
			tempName = tempName.substring(0, 26);
		}
		String tempPhone = delivercompany.getPhone();
		if(tempPhone == null || tempPhone.trim().length() < 1) {
			tempPhone = "PHONE";
		}
		else if (tempPhone.length() > 20 ) {
			tempPhone  = tempPhone.substring(0, 20);
		}
		lines.add(String.format("DS %s%s01 - %s/%s", station.getWt_stationcode(), wtCompanycode, tempName, tempPhone));
		
		String tempDesc = station.getStationdesc();
		if(tempDesc == null || tempDesc.trim().length() < 1) {
			tempDesc = "DEFAULT DELIVERY CO";
		}
		else if (tempDesc.length() > 26) {
			tempDesc = tempDesc.substring(0, 26);
		}
		lines.add(String.format("     %s / %s", 
				tempDesc, 
				station.getPhone() != null && station.getPhone().trim().length() > 0 ? station.getPhone() : "PHONE"));
		lines.add(" SVC LVL STD/TN");
		List<String> names = fieldMap.get(WorldTracerField.NM);
		
		if (names != null) {
			int i = 0;
			List<String> result = new ArrayList<String>();
			for(String name : names) {
				if(name != null && name.trim().length() > 0) {
					i++;
					String temp = name.replaceAll("[^a-zA-Z ]", "");
					if(temp.length() > 20) {
						temp = temp.substring(0, 20);
					}
					result.add(String.format("NM%02d %s", i, temp));
					if(i >= 3) break;
				}
			}
			lines.add(StringUtils.join(result, " ."));
		}
		lines.add("PA01");

		List<String> deliveryAddresses = fieldMap.get(WorldTracerField.DA);
		if(deliveryAddresses != null && deliveryAddresses.size() > 0) {
			String addr = deliveryAddresses.get(0);
			if (addr.length() > 58) {
				addr = addr.substring(0,57);
			}
			lines.add("DA01 " + addr);
		}
		else {
			throw new WorldTracerException("Cannot send BDO. No Delivery Address");
		}
		
		List<String> delDates = fieldMap.get(WorldTracerField.DD);
		if(delDates != null && delDates.size() > 0) {
			lines.add(" .DD " + delDates.get(0));
		}
		else {
			throw new WorldTracerException("Cannot send BDO.  Invalid delivery date");
		}
		
		List<String> phones = fieldMap.get(WorldTracerField.PN);
		if(phones != null && phones.size() > 0) {
			String temp = phones.get(0);
			if(temp.length() > 20) {
				temp = temp.substring(0,20);
			}
			lines.add("PN01 " + phones.get(0));
		}
		
		List<String> instructions = fieldMap.get(WorldTracerField.LD);
		if(instructions != null && instructions.size() > 0) {
			String temp = instructions.get(0);
			if(temp.length() > 58) {
				temp = temp.substring(0, 57);
			}
			lines.add("LD01 " + instructions.get(0));
		}
		else {
			lines.add("LD01");
		}
		
		List<String> bagTypes = fieldMap.get(WorldTracerField.CT);
		int bagCount = 0;
		if(bagTypes != null && bagTypes.size() > 0) {
			List<String> result = new ArrayList<String>();
			for(String ct : bagTypes) {
				if(ct != null && ct.trim().length() > 0) {
					bagCount++;
					result.add(String.format("CT%02d %s", bagCount, ct));
					if(bagCount>=3) break;
				}
			}
			lines.add(StringUtils.join(result, " ."));
		}
		else {
			throw new WorldTracerException("Cannot send BDO. No bag descriptions");
		}
		
		lines.add(String.format(" /RECHARGE -    /SIGNATURE FOR RECEIPT OF %d BAG%s", bagCount, (bagCount > 1) ? "S" : ""));
		
		String responseBody = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		StringBuilder sb = new StringBuilder("http://" + wt_http + "/");
		sb.append("cgi-bin/bagBDO.exe");

		Date now = TracerDateTime.getGMTDate();
		boolean success = false;
		Pattern error_patt = Pattern.compile("<body.*>.*/-(.*?)-/", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		if(ahl_id != null) {
			PostMethod method = new PostMethod(sb.toString());
			
			method.addParameter("LINE1", String.format("%s BDO/I AHL %1$s /%2$tI%2$tMGMT/%2$td%2$tb%2$ty", userType.name(), ahl_id, now));
			
			int index = 0;
			for(index = 0; index < lines.size(); index++) {
				method.addParameter(String.format("LINE%d", index + 2), lines.get(index).toUpperCase());
			}
			for(int i = index + 2; i <=17 ; i++) {
				method.addParameter("LINE" + i, "");
			}
			try {
				responseBody = sendRequest(method);
				logger.debug("got this when sending ahl bdo:\n" + responseBody);

			}	catch (Exception e) {
				throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
			}
			String errorString;

			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
			if(OK.equals(errorString)) {
				success = true;
			}
			else {
				logger.warn("Unable to send bdo for AHL");
			}
		}
		
		if(ohd_id != null) {
			PostMethod method = new PostMethod(sb.toString());
			
			method.addParameter("LINE1", String.format("%s BDO/I OHD %1$s /%2$tI%2$tMGMT/%2$td%2$tb%2$ty", userType.name(), ohd_id, now));
			
			int index = 0;
			for(index = 0; index < lines.size(); index++) {
				method.addParameter(String.format("LINE%d", index + 2), lines.get(index).toUpperCase());
			}
			for(int i = index + 2; i <=17 ; i++) {
				method.addParameter("LINE" + i, "");
			}
			try {
				responseBody = sendRequest(method);
				logger.debug("got this when sending ohd bdo:\n" + responseBody);
			}	catch (Exception e) {
				throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
			}
			Matcher m = error_patt.matcher(responseBody);
			String errorString;
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
			if(OK.equals(errorString)) {
				success = true;
			}
			else {
				logger.warn("Unable to send bdo for OHD");
			}

		}
		
		if(success) {
			return "YAY";
		}
		else {
			throw new WorldTracerException("Bdo failed");
		}	
	
	}

	public void setWtRuleMap(RuleMapper wtRuleMap) {
		this.wtRuleMap = wtRuleMap;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public EnumMap<ActionFileType, int[]> getActionFileCounts(
			String companyCode, String wtStation) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getActionFileDetails(String companyCode, String stationCode,
			ActionFileType type, int day, int itemNumber) {
		// TODO Auto-generated method stub
		return null;
	}


	public Collection<Worldtracer_Actionfiles> getActionFileSummary(
			String companyCode, String stationCode, ActionFileType type, int day) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Worldtracer_Actionfiles> getActionFiles(String companyCode,
			String stationCode, ActionFileType afType, int day, int count)
			throws WorldTracerException {
		// TODO Auto-generated method stub
		return null;
	}


	public void initialize() throws Exception {
		// TODO Auto-generated method stub
		
	}


	public void logout() {
		// TODO Auto-generated method stub
		
	}

}

