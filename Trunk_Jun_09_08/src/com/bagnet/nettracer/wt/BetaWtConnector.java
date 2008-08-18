package com.bagnet.nettracer.wt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.DefaultWorldTracerService.WorldTracerField;

public class BetaWtConnector implements WorldTracerConnector {

	private String wtCompanycode;

	private static final Logger logger = Logger.getLogger(BetaWtConnector.class);
	
	private static final Pattern ahl_patt = Pattern.compile("(?:(?:\\s|/)(?:AHL\\s+|A/))(\\w{5}\\d{5})\\b");
	private static final Pattern ohd_patt = Pattern.compile("(?:(?:\\s|/)(?:OHD\\s+|O/))(\\w{5}\\d{5})\\b");
	private static final Pattern percent_patt = Pattern.compile("SCORE\\s*-\\s*(\\d+(\\.\\d{1,2})?)");
	private static final Pattern itemNum_patt = Pattern.compile("^\\s*(\\d+)/", Pattern.MULTILINE);

	private HttpClient client;
	
	private static Map<String, BetaWtConnector> _instanceMap = new HashMap<String, BetaWtConnector>();

	private BetaWtConnector(String companyCode) {
		this.wtCompanycode = companyCode;
		client = connectWT(WorldTracerUtils.getWt_suffix_airline(companyCode) + "/", companyCode);
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

		HttpClient new_client = new HttpClient(new MultiThreadedHttpConnectionManager());

		new_client.getParams().setAuthenticationPreemptive(true);
		//get worldtracer user info
		Company_Specific_Variable comsv = AdminUtils.getCompVariable(companycode);
		String wt_user = comsv.getWt_user();
		String wt_pass = comsv.getWt_pass();
		String wt_http = comsv.getWt_url();
		if(wt_http == null || wt_http.trim().length() == 0) {
			wt_http = TracingConstants.DEFAULT_WT_URL;
		}

		Credentials defaultcreds = new UsernamePasswordCredentials(wt_user, wt_pass);
		new_client.getState().setCredentials(new AuthScope(wt_http, 80, AuthScope.ANY_REALM), defaultcreds);

		return new_client;

	}

	public String insertIncident(Map<WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("AHL"));
		sb.append("&A2=WM&STNARL=");
		sb.append(stationCode.toUpperCase());
		sb.append(companyCode.toUpperCase());
		sb.append("&AHL=");
		sb.append(DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerField, Object[]> entry : DefaultWorldTracerService.INC_FIELD_RULES.entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(getFields(entry.getKey(), fieldMap.get(entry.getKey()), entry.getValue()));
			}
		}
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();

		String responseBody = null;
		try {
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}

		String wt_id = null;
		String errorString = null;
		Pattern success_patt = Pattern.compile("AHL\\s+(\\w{3}\\w{2}\\d{5})");
		Matcher m = success_patt.matcher(responseBody);
		if (m.find()) {
			wt_id = m.group(1);
		} else {
			Pattern error_patt = Pattern.compile("/-(.*?)-/");
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

	public String closeIncident(Map<WorldTracerField, List<String>> fieldMap, String wt_id, String stationCode)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseCAH"));
		sb.append("FR=" + wt_id);
		sb.append("CAH=" + DefaultWorldTracerService.FIELD_SEP);
		
		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerField, Object[]> entry : DefaultWorldTracerService.CAH_FIELD_RULES.entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(getFields(entry.getKey(), fieldMap.get(entry.getKey()), entry.getValue()));
			}
		}
		
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();
		String responseBody = null;
		try {
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		if( responseBody.toUpperCase().contains("FILE WAS CLOSED")) {
			return wt_id;
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("/-(.*?)-/");
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = responseBody;
			}
			throw new WorldTracerException(errorString);
		}
	}
	
	public void sendFwd(Map<WorldTracerField, List<String>> fieldMap, String wt_CompanyCode) throws WorldTracerException {
		
	}

	public String insertOhd(Map<WorldTracerField, List<String>> fieldMap, String companyCode, String stationCode)
			throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("OHD"));
		sb.append("&A2=WM&STNARL=");
		sb.append(stationCode.toUpperCase());
		sb.append(companyCode.toUpperCase());
		sb.append("&OHD=" + DefaultWorldTracerService.FIELD_SEP);

		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerField, Object[]> entry : DefaultWorldTracerService.OHD_FIELD_RULES.entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(getFields(entry.getKey(), fieldMap.get(entry.getKey()), entry.getValue()));
			}
		}

		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();

		String responseBody = null;
		try {
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		String wt_id = null;
		String errorString = null;
		Pattern success_patt = Pattern.compile("WMT1=(\\w{3}\\w{2}\\d{5})\\b");
		Matcher m = success_patt.matcher(responseBody);
		if (m.find()) {
			wt_id = m.group(1);
		} else {
			Pattern error_patt = Pattern.compile("/-(.*?)-/");
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
	
	public String closeOhd(Map<WorldTracerField, List<String>> fieldMap, String wt_id, String wt_stationcode) throws WorldTracerException {
		StringBuilder sb = new StringBuilder();
		sb.append(buildUrlStart("ResponseCOH"));
		sb.append("T1=" + wt_id);
		sb.append("COH=" + DefaultWorldTracerService.FIELD_SEP);
		
		ArrayList<String> temp = new ArrayList<String>();
		for (Map.Entry<WorldTracerField, Object[]> entry : DefaultWorldTracerService.CAH_FIELD_RULES.entrySet()) {
			if (fieldMap.containsKey(entry.getKey())) {
				temp.add(getFields(entry.getKey(), fieldMap.get(entry.getKey()), entry.getValue()));
			}
		}
		
		String queryString = StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP).toUpperCase().replaceAll(
				"\\s+", "%20");
		sb.append(queryString);
		String getstring = sb.toString();
		String responseBody = null;
		try {
			GetMethod method = new GetMethod(getstring);
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		if( responseBody.toUpperCase().contains("FILE WAS CLOSED")) {
			return wt_id;
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile("/-(.*?)-/");
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
		method.addParameter("A2", "WM");
		
		try {
			responseBody = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		String errorString;
		Pattern error_patt = Pattern.compile("/-(.*?)-/");
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if("OK".equals(errorString)) {
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

	public List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType, int day)
			throws WorldTracerException {
		String afData = null;
		String wt_http = WorldTracerUtils.getWt_url(wtCompanycode);
		PostMethod method = buildAfBaseMethod(airline, station, actionFileType, day, wt_http);
		
		try {
			afData = sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
		
		return parseActionFileData(airline, station, actionFileType, day, afData);
	}


	public String findAHL(String wt_id) throws WorldTracerException {
		GetMethod method = new GetMethod( buildUrlStart("DAH") + "T1=" + wt_id.toUpperCase());
		try {
			return sendRequest(method);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
	}

	public String findOHD(String wt_id) throws WorldTracerException {
		GetMethod method = new GetMethod( buildUrlStart("DOH") + "T1=" + wt_id.toUpperCase());
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
		method.addParameter("A2", "WM");
		return method;
	}

	private String getFields(WorldTracerField field, List<String> list, Object[] rules) {
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

		String responseBody = null;
		try {

			method.setDoAuthentication(true);

			// Provide custom retry handler is necessary
			method.getParams()
					.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

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


}
