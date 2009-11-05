package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.common.utils.StringUtils;
import aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Address;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.Item;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.common.WtqSegment;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WorldTracerHttpClient;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.ParsingUtils.ActionFileType;

public class WorldTracerServiceImpl implements WorldTracerService {

	WorldTracerHttpClient client = null;

	private static final Logger logger = Logger
			.getLogger(WorldTracerServiceImpl.class);
	public static final String WTRWEB_FLOW_URL = "/WorldTracerWeb/wtwflow.do";
	private static final Pattern FLOW_PATTERN = Pattern
			.compile("_flowExecutionKey=(.*)$");
	private static final Pattern BDO_ERROR_PATTERN = Pattern.compile(
			"\\berror\\s*=\\s*'([^']+?)'", Pattern.CASE_INSENSITIVE);
	private static final String OK = "OK";
	private static final String ALREADY_REINSTATED = "ALREADY REINSTATED";
	private static final String ALREADY_SUSPENDED = "ALREADY SUSPENDED";
	private String flowKey = null;
	private static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);
	private static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");
	private static final String DEFAULT_BAG_TYPE = "99";
	private static final String UNKNOWN_AIRLINE = "YY";
	
	private static final List<String> VALID_BAG_TYPES = Arrays.asList(new String[] { "01", "02", "03", "05", "06",
			"07", "08", "09", "10", "12", "20", "22", "23", "25", "26", "27", "28", "29", "50", "51", "52", "53", "54",
			"55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "71", "72", "73",
			"74", "75", "81", "82", "83", "85", "89", "90", "92", "93", "94", "95", "96", "97", "98", "99" });

	RuleMapper wtRuleMap = new UsWorldTracerRuleMap();
	
	private static final List<String> wt_mats = Arrays.asList("D", "L", "M", "R", "T");
	private static final List<String> wt_descs = Arrays.asList("D", "L", "M", "R", "T", "B", "K", "C", "H", "S", "W",
			"X");
	
	public WorldTracerServiceImpl(WorldTracerActionDTO dto) {
		this.client = (WorldTracerHttpClient) dto.getConnection();

	}

	public void getActionFileCounts(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response)
			throws WorldTracerException, NotLoggedIntoWorldTracerException {

		NameValuePair[] p1 = {
				new NameValuePair("_flowId", "actionfilestation-flow"),
				new NameValuePair("isHDQ", "N") };
		String newLocation = startFlow(p1, "GET AF COUNTS (FRONTEND)", 1);

		// submit close form by post method
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaRequest.stationCode", data
				.getStation());
		search.setParameter("wtrActionAreaRequest.airlineCode", data
				.getAirline());
		search.setParameter("_multipleMessage", "on");
		search.setParameter("manageSelectAreaOrDayVO.timeInterval", "-1");
		search.setParameter("manageSelectAreaOrDayVO.actionAreaName",
				"FORWARD_AREA");
		search.setParameter("manageSelectAreaOrDayVO.expandedChar", "");
		search.setParameter("manageSelectAreaOrDayVO.day", "DAY_1");
		search.setParameter("manageSelectAreaOrDayVO.errroStatus", "false");
		search.setParameter("_eventId", "refresh");
		try {
			debugOut(search, "");
			client.executeMethod(search,
					"GET AF COUNTS (FRONTEND): GET COUNTS (2)");
			if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				newLocation = search.getResponseHeader("location").getValue();
			} else {
				newLocation = "";
				logger.error("actionfile refresh post not redirect!");
				throw new WorldTracerException("unable to count action files");
			}
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to count action files");
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to count action files");
		} finally {
			search.releaseConnection();
		}
		InputStream inStream = null;
		if (!"".equals(newLocation)) {
			flowKey = newLocation.split("=")[1];
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect,
						"GET AF COUNTS (FRONTEND): REDIRECT (3)");
				inStream = redirect.getResponseBodyAsStream();
				EnumMap<ActionFileType, int[]> result = ParsingUtils
						.parseActionFileCounts(inStream, "ISO-8859-1");
				if (result != null) {
					ArrayList<ActionFileCount> counts = new ArrayList<ActionFileCount>();
					for (Entry<ActionFileType, int[]> entry : result.entrySet()) {
						int[] values = entry.getValue();
						for (int day = 1; day < 8; ++day) {
							int i = values[day - 1];
							if (i > 0) {
								ActionFileCount c = new ActionFileCount();
								c.setDay(day);
								c.setType(entry.getKey().name());
								c.setCount(i);
								counts.add(c);
								
							}
						}
					}
					response.setCounts(counts.toArray(new ActionFileCount[counts.size()]));
					response.setSuccess(true);
				}
				return;
				// System.out.println("close incident result:" + responseBody);
			} catch (HttpException e) {
				throw new WorldTracerException(e);
			} catch (IOException e) {
				throw new WorldTracerException(e);
			} catch (ParserException e) {
				throw new WorldTracerException(e);
			} finally {
				if (inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						logger.debug("unable to close input stream");
					}
				}
				redirect.releaseConnection();

			}
		} else {
			throw new WorldTracerException("unable to get action file counts");
		}
	}

	private String startFlow(NameValuePair[] getParams, String flowName,
			int stepNum) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		return startFlow(getParams, flowName, stepNum, true, 0L);
	}

	private String startFlow(NameValuePair[] getParams, String flowName,
			int stepNum, boolean usePause, long pauseInMillis)
			throws WorldTracerException, NotLoggedIntoWorldTracerException {
		int attempts = 0;
		String functionName = String.format("%s (%d)", flowName, stepNum);
		GetMethod method = new GetMethod(WTRWEB_FLOW_URL);

		method.setQueryString(getParams);
		method.setFollowRedirects(false);

		executeMethod(method, functionName, usePause, pauseInMillis);

		if (method.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY
				&& method.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.debug(functionName + " did not redirect");
			throw new WorldTracerException(functionName + " not redirected");
		}
		String newLocation = method.getResponseHeader("location").getValue();
		Matcher m = FLOW_PATTERN.matcher(newLocation);
		if (m.find()) {
			flowKey = m.group(1);
			return newLocation;
		} else {
			logger.warn("flow key not returned from " + functionName
					+ ".  got redirect: " + newLocation);
		}

		logger.info("user " + this.client.getAccount()
				+ " appears to be logged out.");
		this.client.setValidConnection(false);
		throw new NotLoggedIntoWorldTracerException();

	}

	private void debugOut(PostMethod method, String methodName) {
		if (method != null && logger.isDebugEnabled()) {
			logger.debug("about to call " + method.getQueryString()
					+ " with the following paramters:");
			StringBuilder sb = new StringBuilder("params:");
			for (NameValuePair foo : method.getParameters()) {
				sb.append(String
						.format("%s=%s,", foo.getName(), foo.getValue()));
			}
			logger.debug(sb.toString());
		}
	}

	private void executeMethod(HttpMethod method, String functionName,
			boolean usePause, long pauseInMillis) throws WorldTracerException {
		executeMethod(method, functionName, usePause, pauseInMillis, true);
	}

	private void executeMethod(HttpMethod method, String functionName,
			boolean usePause, long pauseInMillis, boolean doRelease)
			throws WorldTracerException {
		try {
			if (usePause) {
				if (pauseInMillis > 0) {
					client.executeMethodWithPause(method, pauseInMillis,
							functionName);
				} else {
					client.executeMethodWithPause(method, functionName);
				}
			} else {
				client.executeMethod(method, functionName);
			}
		} catch (Exception e) {
			String errorMsg = String.format("Error executing %s, Cause: %s",
					functionName, e.getMessage());
			logger.error(errorMsg, e);
			throw new WorldTracerException(errorMsg, e);
		} finally {
			if (doRelease) {
				method.releaseConnection();
			}
		}
	}
	

	private void startFlowWithRedirect(NameValuePair[] getParams, String flowName,
			int stepNum, boolean usePause) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		startFlowWithRedirect(getParams, flowName, stepNum, usePause, 0L);
	}

	private void startFlowWithRedirect(NameValuePair[] getParams, String flowName,
			int stepNum, boolean usePause, long pauseInMillis)
			throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String newLocation = startFlow(getParams, flowName, stepNum, usePause, pauseInMillis);
		GetMethod redirect = new GetMethod(newLocation);
		executeMethod(redirect, flowName, usePause, pauseInMillis, true);
	}

	public void getActionFileSummary(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		ActionFileType afType = ActionFileType.valueOf(data.getType());
		NameValuePair[] startFlowParams = {
				new NameValuePair("_flowId", "searchmessages-flow"), 
				new NameValuePair("isHDQ", "N"), 
				new NameValuePair("isSubflow", "No")
				};
		
		startFlowWithRedirect(startFlowParams, "ACTION FILE SUMMARY", 1, false);
		
		NameValuePair[] summaryQueryParams = {new NameValuePair("_flowExecutionKey", flowKey)};

		PostMethod method = new PostMethod(WTRWEB_FLOW_URL);
		method.setFollowRedirects(false);
		InputStream inStream = null;
		method.setQueryString(summaryQueryParams);
		method.setParameter("wtrActionAreaReadRequest.stationCode", data.getStation());
		method.setParameter("wtrActionAreaReadRequest.airlineCode", data.getAirline());
		method.setParameter("searchMessagesVO.startNumber", "1");
		method.setParameter("searchMessagesVO.endNumber", "99");
		method.setParameter("_eventId", "Refresh");
		method.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", afType.areaId());
		method.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + data.getDay());

		
		executeMethod(method, "ACTION FILE SUMMARY (2)", false, 0L);
		
		if(method.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && 
				method.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.debug("ACTION FILE SUMMARY (2) did not redirect");
			throw new WorldTracerException("ACTION FILE SUMMARY (2) not redirected");
		}
		
		String newLocation = method.getResponseHeader("location").getValue();
		Matcher m = FLOW_PATTERN.matcher(newLocation);
		if(m.find()) {
			flowKey = m.group(1).trim();
		}
		
		GetMethod redirect = new GetMethod(newLocation);
		
		try {
			executeMethod(redirect, "REDIR for: ACTION FILE SUMMARY (2)", false, 0, false);
			inStream = redirect.getResponseBodyAsStream();
			List<ActionFile> list = ParsingUtils.parseActionFileSummary(inStream, "ISO-8859-1");
			response.setActionFiles(list.toArray(new ActionFile[list.size()]));
		} catch (Exception e) {
			logger.error("unable to parse action file summary", e);
			throw new WorldTracerException("unable to parse action files summary", e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					//pass
				}
			}
			redirect.releaseConnection();
		}
		
		
	}

	public void getActionFileDetails(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		ActionFileType afType = ActionFileType.valueOf(data.getType());
		NameValuePair[] startFlowParams = {
				new NameValuePair("_flowId", "searchmessages-flow"), 
				new NameValuePair("isHDQ", "N"), 
				new NameValuePair("isSubflow", "No")
				};
		
		startFlowWithRedirect(startFlowParams, "ACTION FILE DETAIL", 1, false);
		
		NameValuePair[] queryParams = {new NameValuePair("_flowExecutionKey", flowKey)};
		
		PostMethod method = new PostMethod(WTRWEB_FLOW_URL);
		method.setFollowRedirects(false);
		
		method.setQueryString(queryParams);
		method.setParameter("wtrActionAreaReadRequest.stationCode", data.getStation());
		method.setParameter("wtrActionAreaReadRequest.airlineCode", data.getAirline());
		method.setParameter("searchMessagesVO.startNumber", Integer.toString(data.getNumber()));
		method.setParameter("searchMessagesVO.endNumber", Integer.toString(data.getNumber()));
		method.setParameter("_eventId", "Refresh");
		method.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", afType.areaId());
		method.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + data.getDay());
		
		executeMethod(method, "ACTION FILE DETAIL (2)", false, 0L);
		
		if(method.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && 
				method.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.debug("ACTION FILE DETAIL (2) did not redirect");
			throw new WorldTracerException("ACTION FILE DETAIL (2) not redirected");
		}
		
		String newLocation = method.getResponseHeader("location").getValue();
		Matcher m = FLOW_PATTERN.matcher(newLocation);
		if(m.find()) {
			flowKey = m.group(1).trim();
		}
		
		GetMethod redirect = new GetMethod(newLocation);
		
		try {
			executeMethod(redirect, "REDIR for: ACTION FILE DETAIL (2)", false, 0, false);
		} catch (Exception e) {
			logger.error("unable to parse action file summary", e);
			throw new WorldTracerException("unable to parse action files summary", e);
		} finally {
			redirect.releaseConnection();
		}
		
		queryParams[0] = new NameValuePair("_flowExecutionKey", flowKey);
		
		method = new PostMethod(WTRWEB_FLOW_URL);
		method.setFollowRedirects(false);
		
		method.setQueryString(queryParams);
		method.setParameter("wtrActionAreaReadRequest.stationCode", data.getStation());
		method.setParameter("wtrActionAreaReadRequest.airlineCode", data.getAirline());
		method.setParameter("wtrActionAreaReadRequest.searchByRecordReference.stationCode", "");
		method.setParameter("wtrActionAreaReadRequest.searchByRecordReference.airlineCode", "");
		method.setParameter("wtrActionAreaReadRequest.searchByRecordReference.recordId", "");
		method.setParameter("searchMessagesVO.startNumber", Integer.toString(data.getNumber()));
		method.setParameter("searchMessagesVO.endNumber", Integer.toString(data.getNumber()));
		method.setParameter("_eventId", "DetailedView");
		method.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", afType.areaId());
		method.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + data.getDay());
		method.setParameter("1[]", "checkbox");
		method.setParameter("searchMessagesVO.messageListItems[0].checked", "true");
		method.setParameter("_searchMessagesVO.messageListItems[0].checked", "on");
		
		executeMethod(method, "ACTION FILE DETAIL (3)", false, 0L);
		
		if(method.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && 
				method.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.debug("ACTION FILE DETAIL (3) did not redirect");
			throw new WorldTracerException("ACTION FILE DETAIL (3) not redirected");
		}
		
		newLocation = method.getResponseHeader("location").getValue();
		m = FLOW_PATTERN.matcher(newLocation);
		if(m.find()) {
			flowKey = m.group(1).trim();
		}
		
		redirect = new GetMethod(newLocation);
		
		InputStream inStream = null;
		try {
			executeMethod(redirect, "REDIR for: ACTION FILE SUMMARY (2)", false, 0, false);
			inStream = redirect.getResponseBodyAsStream();
			String[] parsed = ParsingUtils.parseActionFileDetail(inStream, "ISO-8859-1");
			if(parsed != null) {
				String result = parsed[1];
				String ahl_id = ParsingUtils.parseAhlId(result);
				String ohd_id = ParsingUtils.parseOhdId(result);
				double percent = ParsingUtils.parsePercentMatch(result);
				
				ActionFile[] afa = new ActionFile[1];
				ActionFile af = new ActionFile();
				af.setAhlId(ahl_id);
				af.setOhdId(ohd_id);
				af.setPercentMatch(percent);
				af.setItemNumber(data.getNumber());
				af.setDetails(result);
				afa[0] = af;
				response.setActionFiles(afa);
			}
		} catch (Exception e) {
			logger.error("unable to parse action file summary", e);
			throw new WorldTracerException("unable to parse action files summary", e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					//pass
				}
			}
			redirect.releaseConnection();
		}
		
	}

	public void eraseActionFile(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		ActionFileType area = ActionFileType.valueOf(data.getType());
		
		NameValuePair[] p1 = {
				new NameValuePair("_flowId", "erasemessages-flow"),
				new NameValuePair("isHDQ", "N") };
		String newLocation = startFlow(p1, "ERASE AF", 1);
		
		// find the file
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaReadRequest.stationCode", data.getStation());
		search.setParameter("wtrActionAreaReadRequest.airlineCode", data.getAirline());
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.stationCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.airlineCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.recordId", "");
		search.setParameter("searchMessagesVO.startNumber", Integer.toString(data.getNumber()));
		search.setParameter("searchMessagesVO.endNumber", Integer.toString(data.getNumber()));
		search.setParameter("_eventId", "Refresh");
		search.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", area.areaId());
		search.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + Integer.toString(data.getDay()));

		try {
			debugOut(search, "");
			client.executeMethodWithPause(search, "ERASE AF: GET AF (2)");
			if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = search.getResponseHeader("location").getValue();
				flowKey = newLocation.split("=")[1];
			}
			else{
				newLocation = "";
				logger.error("display AHL postMethod not redirect!");
				throw new WorldTracerException("unable to erase action file");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		}
		finally {
			search.releaseConnection();
		}
		//not sure i need to do this
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "ERASE AF: REDIRECT (3)");
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		}
		finally {
			redirect.releaseConnection();
		}
		search = new PostMethod(WTRWEB_FLOW_URL);
		p2[0] = new NameValuePair("_flowExecutionKey", flowKey);
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaReadRequest.stationCode", data.getStation());
		search.setParameter("wtrActionAreaReadRequest.airlineCode", data.getAirline());
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.stationCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.airlineCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.recordId", "");
		search.setParameter("searchMessagesVO.startNumber", Integer.toString(data.getNumber()));
		search.setParameter("searchMessagesVO.endNumber", Integer.toString(data.getNumber()));
		search.setParameter("_eventId", "Erase");
		search.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", area.areaId());
		search.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + Integer.toString(data.getDay()));
		search.setParameter("1[]", "checkbox");
		search.setParameter("searchMessagesVO.messageListItems[0].checked", "true");
		search.setParameter("_searchMessagesVO.messageListItems[0].checked", "on");

		try {
			debugOut(search, "");
			client.executeMethodWithPause(search, "ERASE AF: ERASE (4)");
			if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = search.getResponseHeader("location").getValue();
			}
			else{
				newLocation = "";
				logger.error("erase action file may have failed?");
				throw new WorldTracerException("unable to erase action file");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		}
		finally {
			search.releaseConnection();
		}
		response.setSuccess(true);		
	}

	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf,
			WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String result = null;
		PxfDetails[] details = pxf.getPxfDetails();

		NameValuePair[] p1 = {
				new NameValuePair("_flowId", "sendmessage-flow"),
				new NameValuePair("isHDQ", "N") };
		
		String newLocation = startFlow(p1, "SEND_PXF", 1);
		startFlowWithRedirect(p1, "SEND_PXF", 1, false);
		
		
				
		String responseBody = null;
		//add params to forward
		PostMethod forwardMethod = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		forwardMethod.setQueryString(p2);
		
		//get to the WT interface and send the PXF request
		//first compose information package 
		forwardMethod.setParameter("isHDQ", "yes");
		
		int myDestinationType = pxf.getDestination();
		String sendToDestination = null;  // default 
		PxfDetails detail = null;
		if (details.length >= 1) {
			detail = details[1];
		} else {
			detail = new PxfDetails();
		}
		
		HashMap<String, String> lookupHash = new HashMap<String, String>();
		lookupHash.put("AP", "ADDITIONAL_PROMPT_AREA");
		lookupHash.put("FW", "FORWARD_AREA");
		lookupHash.put("AA", "ACTION_AREA");

		if(myDestinationType == 1) {
			sendToDestination = "ALL_STATIONS";

			String allStationsActionArea = lookupHash.get(detail.getArea());
			forwardMethod.setParameter("sendMessageActionVO.allStationsActionArea", allStationsActionArea);
		} else if(myDestinationType == 2) {
			sendToDestination = "ONE_REGION";
			String regionNumber = "" + detail.getStation();
			String regionActionArea = "" + detail.getArea();
			forwardMethod.setParameter("sendMessageActionVO.regionNumber", regionNumber);
			forwardMethod.setParameter("sendMessageActionVO.regionActionArea", regionActionArea);
		} else {
			
			
			
			sendToDestination = "ACTION_AREA_ADDRESSES";


			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[0].stationCode", detail.getStation());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[0].airlineCode", detail.getAirline());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[0].actionAreaType", lookupHash.get(detail.getArea()));
			
			if (details.length >= 1) {
				detail = details[1];
			} else {
				detail = new PxfDetails();
			}
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[1].stationCode", detail.getStation());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[1].airlineCode", detail.getAirline());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[1].actionAreaType", lookupHash.get(detail.getArea()));
			
			if (details.length >= 2) {
				detail = details[2];
			} else {
				detail = new PxfDetails();
			}
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[2].stationCode", detail.getStation());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[2].airlineCode", detail.getAirline());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[2].actionAreaType", lookupHash.get(detail.getArea()));
			
			if (details.length >= 3) {
				detail = details[3];
			} else {
				detail = new PxfDetails();
			}
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[3].stationCode", detail.getStation());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[3].airlineCode", detail.getAirline());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[3].actionAreaType", lookupHash.get(detail.getArea()));
			
			if (details.length >= 4) {
				detail = details[4];
			} else {
				detail = new PxfDetails();
			}
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[4].stationCode", detail.getStation());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[4].airlineCode", detail.getAirline());
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[4].actionAreaType", lookupHash.get(detail.getArea()));
			
			forwardMethod.setParameter("_sendMessageActionVO.schedule", "on");
			
			
			for (int i=0; i<4; ++i) {
				forwardMethod.setParameter("sendMessageActionVO.relatedRecords[" + i + "].recordType", "");
				forwardMethod.setParameter("sendMessageActionVO.relatedRecords[" + i + "].recordReference.stationCode", "");
				forwardMethod.setParameter("sendMessageActionVO.relatedRecords[" + i + "].recordReference.airlineCode", "");
				forwardMethod.setParameter("sendMessageActionVO.relatedRecords[" + i + "].recordReference.recordId", "");
			}
			
		}
		forwardMethod.setParameter("sendMessageActionVO.sendToDestination", sendToDestination);
		
		for (int i=0; i<5; ++i) {
			String[] myTeletypes = pxf.getTeletype();
			if (i< myTeletypes.length) {
				forwardMethod.setParameter("sendMessageActionVO.ttyAddresses[" + i + "]", myTeletypes[i]);
			} else {
				forwardMethod.setParameter("sendMessageActionVO.ttyAddresses[" + i + "]", "");
			}
		}
		
		
		forwardMethod.setParameter("wtrActionAreaWriteRequest.message", pxf.getContent());
		forwardMethod.setParameter("_eventId", "Send");
		
		//execute the send PXF request
		try {
			for (NameValuePair pair: forwardMethod.getParameters()) {
				logger.info(pair.getName() + ": " + pair.getValue());
			}
			
			client.executeMethodWithPause(forwardMethod, "SEND_PXF (2)");
			if(forwardMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || forwardMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = forwardMethod.getResponseHeader("location").getValue();
			}
			else{
				throw new WorldTracerException("send pxf method not redirect!");
			}
		}
		catch (HttpException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			forwardMethod.releaseConnection();
		}

		//check redirect
		if (!"".equals(newLocation)) {
			GetMethod redirect = new GetMethod(newLocation);
			try {
				
				client.executeMethod(redirect, "SEND PXF: REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect
						.getResponseBodyAsStream());
			} catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerConnectionException("could not read pxf response");
			} catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerConnectionException("could not read pxf response");
			} finally {
				redirect.releaseConnection();
			}
		} else {
			throw new WorldTracerException("unable to send pxf redirect");
		}
		if (responseBody == null) {
			throw new WorldTracerException("unable to send pxf redirect");
		}
		
		if (responseBody.contains("alert(\"Message sent successfully\")") && responseBody.contains("var message = \'1\';")) {
			result = "pxfSuccess";
		} else {
			result = responseBody;
			logger.error("PXF did not work ! Error detail : " + responseBody);
			throw new WorldTracerException("PXF Failed");
		}
	}
	
	public String getStringFromInputStream(InputStream is) throws IOException{
		if(is == null){
			return null;
		}
		StringBuilder replySB = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String tempString = "";
		while((tempString = reader.readLine())!=null){
			replySB.append(tempString);
		}
		reader.close();
		
		return new String(replySB);
	}


	public void reinstateAhl(WorldTracerActionDTO dto, Ahl ahl,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
	  reinstateAHL(ahl.getAhlId(), response);
  }
	
	public void suspendAhl(WorldTracerActionDTO dto, Ahl ahl,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
	  suspendAHL(ahl.getAhlId(), response);
  }
	
	private void reinstateAHL(String wt_id, WorldTracerResponse response)
	    throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = susritItem(wt_id, "RIT", "AHL");
		String errorString;
		Pattern error_patt = Pattern.compile(
		    "<input[^<>]*id=\"result__cr0\"[^<>]*value=\"([^<>\"]*)\"+?[^<>]*/>+?",
		    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if (OK.equals(errorString) || ALREADY_REINSTATED.equals(errorString)) {
			return;
		}
		throw new WorldTracerException(errorString);
	}



	private void suspendAHL(String wt_id, WorldTracerResponse response)
			throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = susritItem(wt_id, "SUS", "AHL");
		String errorString;
		Pattern error_patt = Pattern.compile("<input[^<>]*id=\"result__cr0\"[^<>]*value=\"([^<>\"]*)\"+?[^<>]*/>+?",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if (OK.equals(errorString) || ALREADY_SUSPENDED.equals(errorString)) {
			response.setSuccess(true);
			return;
		}
		throw new WorldTracerException(errorString);
	}

	
	private String susritItem(String wt_id, String action, String type) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = null;
		String flowId = null;
		String amendOption = null;
		if(action.equals("SUS") && type.equals("AHL")){
			flowId = "suspenddelayedbagrecord-flow";
			amendOption = "fullsuspendnoamend";
		}
		else if(action.equals("RIT") && type.equals("AHL")){
			flowId = "reinstatedelayedbagrecord-flow";
			amendOption = "fullreinstatenoamend";
		}
		else if(action.equals("SUS") && type.equals("OHD")){
			flowId = "suspendonhandbagrecord-flow";
			amendOption = "fullsuspendnoamend";
		}
		else if(action.equals("RIT") && type.equals("OHD")){
			flowId = "reinstateonhandbagrecord-flow";
			amendOption = "fullreinstatenoamend";
		}

		NameValuePair[] p1 = { new NameValuePair("_flowId", flowId)};
		String newLocation = startFlow(p1, "SUSRIT", 1);
		
		//submit to suspend/reinstate
		PostMethod amend = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p = {new NameValuePair("_flowExecutionKey", flowKey)};
		amend.setQueryString(p);

		amend.setParameter("wtrStateChangeRequest.recordStates[0].stationCode", wt_id.substring(0,3));
		amend.setParameter("wtrStateChangeRequest.recordStates[0].airlineCode", wt_id.substring(3,5));
		amend.setParameter("wtrStateChangeRequest.recordStates[0].recordId", wt_id.substring(5));
		amend.setParameter("_showAllDetailsInReadOnlyArea", "on");
		amend.setParameter("_wtrDisplayRequest.recordAdditionalInfo.recordHistory", "on");
		amend.setParameter("_wtrDisplayRequest.recordAdditionalInfo.matchingHistory", "on");
		amend.setParameter("_wtrDisplayRequest.recordAdditionalInfo.fullRecord", "on");
		amend.setParameter("_wtrDisplayRequest.recordAdditionalInfo.SMSMessages", "on");
		amend.setParameter("_wtrDisplayRequest.recordAdditionalInfo.internetMessages", "on");
		amend.setParameter("resultsForm.sortOption", "recordReference");
		amend.setParameter("amendOption[0]", amendOption);
		amend.setParameter("_eventId", "continue");
		
		try {
			debugOut(amend, "");
			client.executeMethodWithPause(amend, "SUSRIT: PERFORM ACTION (2)");
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to sus/rit item", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to sus/rit item", e);
		}
		finally {
			amend.releaseConnection();
		}
		if(amend.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && amend.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("suspend/reinstate flow not redirect!");
			throw new WorldTracerException("unable to sus/rit item");
		}
		newLocation = amend.getResponseHeader("location").getValue();
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "SUSRIT: REDIRECT (3");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to sus/rit item", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to sus/rit item", e);
		}
		finally {
			redirect.releaseConnection();
		}

		return responseBody;
	}

	public void sendForwardMessage(WorldTracerActionDTO dto, ForwardMessage msg,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		
		
		Map<WorldTracerField, List<String>> fieldMap = createFwdFieldMap(msg);
		
		Pattern itinPatt = Pattern.compile("(\\w{2})(\\w+)/(\\w+)", Pattern.CASE_INSENSITIVE);
		String responseBody = null;
		NameValuePair[] p1 = { new NameValuePair("_flowId",
				"createrushdbagrecord-flow") };
		String newLocation = startFlow(p1, "SEND FORWARD", 1);
		
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);

		search.setParameter("rushBagRecord.recordReference.stationCode", msg.getFromStation());
		search.setParameter("rushBagRecord.recordReference.airlineCode",
				msg.getFromAirline());

		EnumMap<WorldTracerField, WorldTracerRule<String>> fwdRules = wtRuleMap.getRule(TxType.FWD_GENERAL);
		for (Entry<WorldTracerField, WorldTracerRule<String>> entry : fwdRules.entrySet()) {
			
			if (fieldMap.containsKey(entry.getKey())) {
				switch (entry.getKey()) {
				case TN:
					List<String> tagList = null;
					tagList = fieldMap.get(entry.getKey());
					if(tagList != null) {
						int addedCount = 0;
						for (String rawTag: tagList) {
							if(addedCount > entry.getValue().getMaxAllowed()) break;
							
							String finalTag = null;
							try {
								finalTag = BagTagConversion.getTwoCharacterBagTag(rawTag);
							} catch (BagtagException e) {
								logger.debug("Unable to convert bagtag while creating fwd: " + rawTag);
								continue;
							}
							
							search.setParameter("rushBagRecord.rushBagGroup.rushBags[" + addedCount + "].originalBagTag.airlineCode", finalTag.substring(0,2));
							search.setParameter("rushBagRecord.rushBagGroup.rushBags[" + addedCount + "].originalBagTag.tagNumber", finalTag.substring(2,8));
							addedCount++;
						}
					}
					break;
				case XT:
					List<String> xtList = null;
					xtList = fieldMap.get(entry.getKey());
					if(xtList != null) {
						int addedCount = 0;
						for (String rawTag : xtList) {
							if(addedCount > entry.getValue().getMaxAllowed()) break;
							
							String finalTag = null;
							try {
								finalTag = BagTagConversion.getTwoCharacterBagTag(rawTag);
							} catch (BagtagException e) {
								logger.debug("Unable to convert bagtag while creating fwd: " + rawTag);
								continue;
							}
							
							search.setParameter("rushBagRecord.rushBagGroup.rushBags[" + addedCount + "].rushBagTag.airlineCode", finalTag.substring(0,2));
							search.setParameter("rushBagRecord.rushBagGroup.rushBags[" + addedCount + "].rushBagTag.tagNumber", finalTag.substring(2,8));
							addedCount++;
							
						}
					}
					break;
				case FO:
					List<String> itinList = fieldMap.get(entry.getKey());
					if(itinList != null) {
						int addedCount = 0;
						for (String itin : itinList) {
							
							if(addedCount > entry.getValue().getMaxAllowed()) break;
							
							Matcher m = itinPatt.matcher(itin);
							if(m.find()) {
								String airline = m.group(1);
								String flightNum = m.group(2);
								String date = m.group(3);
								search.setParameter("rushBagRecord.rushBagGroup.rushFlights[" + addedCount + "].airlineCode", airline);
								search.setParameter("rushBagRecord.rushBagGroup.rushFlights[" + addedCount + "].flightNumber", flightNum);
								search.setParameter("rushBagRecord.rushBagGroup.rushFlights[" + addedCount + "].flightDate", date);
								addedCount++;
							}
							else {
								logger.error("Unable to parse FO field itin while creating a FWD");
								continue;
							}
							
							
						}
					}
					break;
				case FW:
					List<String> fwList = fieldMap.get(entry.getKey());
					if(fwList != null) {
						int addedCount = 0;
						for (String fw : fwList) {
							if(addedCount > entry.getValue().getMaxAllowed()) break;
							search.setParameter("rushBagRecord.rushBagGroup.rushStationAirlines[" + addedCount + "].stationCode", fw.substring(0,3));
							search.setParameter("rushBagRecord.rushBagGroup.rushStationAirlines[" + addedCount + "].airlineCode", fw.substring(3,5));
							addedCount++;
						}
					}
					break;
				case NM:
					List<String> nameList = fieldMap.get(entry.getKey());
					if(nameList != null) {
						int addedCount = 0;
						for (String name : nameList) {
							if(addedCount > entry.getValue().getMaxAllowed()) break;
							search.setParameter("rushBagRecord.passenger.names[" + addedCount + "]", entry.getValue().formatEntry(name));
							addedCount++;
						}
					}
					break;
				case RL:
					List<String> losscodeList = fieldMap.get(entry.getKey());
					if(losscodeList != null && losscodeList.size() > 0)  {
						search.setParameter("rushBagRecord.reasonForLossCode", losscodeList.get(0));
					}
					break;
				case RC:
					List<String> losscommentList = fieldMap.get(entry.getKey());
					if(losscommentList != null && losscommentList.size() > 0)  {
						search.setParameter("rushBagRecord.commentsOnLoss", entry.getValue().formatEntry(losscommentList.get(0)));
					}
					break;
				case SI:
					List<String> suppList = fieldMap.get(entry.getKey());
					if(suppList != null && suppList.size() > 0)  {
						search.setParameter("rushBagRecord.supplementaryInfo[0]", entry.getValue().formatEntry(suppList.get(0)));
					}
					break;
				case TX:
					List<String> ttypeList = fieldMap.get(entry.getKey());
					if(ttypeList != null) {
						int addedCount = 0;
						for (String name : ttypeList) {
							if(addedCount > entry.getValue().getMaxAllowed()) break;
							search.setParameter("rushBagRecord.teletypeAdresses[" + addedCount + "]", name);
							addedCount++;
						}
					}
					break;
				default:
					break;
				}
			}
		}
		search.addParameter("_eventId", "submit");
		search.addParameter("bagType", "Rush");
		try {
			debugOut(search, "");
			client.executeMethodWithPause(search, "SEND FORWARD: SEND (2)");
			if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				newLocation = search.getResponseHeader("location").getValue();
			} else {
				newLocation = "";
				logger.debug("fwd rush bag post not redirect!");
				throw new WorldTracerException("fwd submit did not go through");
			}
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerConnectionException("fwd submit did not go through");
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerConnectionException("fwd submit did not go through");
		} finally {
			search.releaseConnection();
		}
		
		if (!"".equals(newLocation)) {
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "SEND FORWARD: REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect
						.getResponseBodyAsStream());
				// System.out.println("close incident result:" + responseBody);
			} catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerConnectionException("could not read final fwd response");
			} catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerConnectionException("could not read final fwd response");
			} finally {
				redirect.releaseConnection();
			}
		}
		else {
			throw new WorldTracerException("unable to submit fwd redirect");
		}
		
		if (responseBody == null) {
			throw new WorldTracerException("unable to submit fwd redirect");
		}
		
		if (responseBody.toUpperCase().contains("RECORD CREATED SUCCESSFULLY") || responseBody.toUpperCase().contains("FILE CREATED SUCCESSFULLY")) {
			response.setSuccess(true);
			return;
		}
		else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to send Fwd. See logs for details";
				logger.error("send fwd result:"+responseBody);
			}
			if (errorString.toUpperCase().contains("FILE CLOSED")) {
				//throw new WorldTracerAlreadyClosedException(errorString);
				response.setSuccess(false);
				response.setError(new WebServiceError("Cannot forward: file already closed."));
				return;
			}
			throw new WorldTracerException(errorString);
		}
  }
	
	private Map<WorldTracerField, List<String>> createFwdFieldMap(ForwardMessage fwd) throws WorldTracerException {
		if (fwd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(fwd.getAgent()), result);

		addConvertedTag(fwd.getExpediteTag(), WorldTracerField.XT, result, fwd.getFromAirline());

		if (fwd.getName() != null) {
			for (String name : fwd.getName()) {
				addIncidentFieldEntry(WorldTracerField.NM, name.trim(), result);
			}
		}

		if (fwd.getTeletype() != null) {
			for (String tt : fwd.getTeletype()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt.trim(), result);
			}
		}

		addIncidentFieldEntry(WorldTracerField.SI, fwd.getSuplementaryInfo(), result);
		addIncidentFieldEntry(WorldTracerField.TI, fwd.getTextInfo(), result);

		String fw = null;
		if (fwd.getItinerary() != null) {
			for (WtqSegment itin : fwd.getItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
						|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
						|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
						|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
					continue;
				}
				String fnum = wtFlightNumber(itin.getFlightnum());
				String fd = null;
				if (fnum.length() == 0) {
					fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
				} else {
					fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
				}
				addIncidentFieldEntry(WorldTracerField.FO, fd, result);
				addIncidentFieldEntry(WorldTracerField.NF, fd, result);
				List<String> routing = result.get(WorldTracerField.NR);
				if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
					addIncidentFieldEntry(WorldTracerField.NR, itin.getLegfrom().trim(), result);
				}
				addIncidentFieldEntry(WorldTracerField.NR, itin.getLegto().trim(), result);
				fw = itin.getLegto() + itin.getAirline();
				addIncidentFieldEntry(WorldTracerField.FW, fw, result);
			}
		}
		List<String> foo = result.get(WorldTracerField.FW);
		if (foo != null && foo.size() > 0) {
			foo.set(foo.size() - 1, fwd.getDestinationStation() + fwd.getDestinationAirline());
		} else {
			throw new WorldTracerException("invalid forward itinerary");
		}

		return result;
	}
	

	protected void addIncidentFieldEntry(WorldTracerField key, String value, Map<WorldTracerField, List<String>> result) {
		if (value == null || value.trim().length() <= 0) {
			return;
		}

		List<String> entryList = result.get(key);
		if (entryList == null) {
			entryList = new ArrayList<String>();
			entryList.add(value);
			result.put(key, entryList);
		} else {
			entryList.add(value);
		}
	}
	
	private void addConvertedTag(String tag, WorldTracerField field, Map<WorldTracerField, List<String>> result, String companyCode) {
		String bagTagString = null;
		try {
			bagTagString = BagTagConversion.getTwoCharacterBagTag(tag.trim());
		} catch (BagtagException e) {
			// couldn't figure out the tag.
			Pattern wt_patt = Pattern.compile("([a-zA-Z0-9]{2})(\\d{1,6})");
			Matcher m = wt_patt.matcher(tag.trim());
			if (m.find() && BagTagConversion.getThreeDigitTicketingCode(m.group(1)) != null) {
				bagTagString = String.format("%s%06d", m.group(1), Integer.parseInt(m.group(2)));
			} else {
				Pattern base_patt = Pattern.compile("(\\d{1,6})(\\D|$)");
				m = base_patt.matcher(tag.trim());
				if (m.find()) {
					bagTagString = companyCode + m.group(1);
				}
			}
		}
		if (bagTagString != null && bagTagString.matches(".*[1-9].*")) {
			if (result.get(field) == null || !(result.get(field).contains(bagTagString))) {
				addIncidentFieldEntry(field, bagTagString, result);
			}
		}
	}
	
	private static String getAgentEntry(Agent ag) {
		if (ag != null)
			return (ag.getUsername().length() > 7 ? ag.getUsername().substring(0, 7)
			    : ag.getUsername())
			    + "/" + ag.getAirline();
		return "NTRACER";
	}
	
	private String wtFlightNumber(String flightnum) {
		if (flightnum == null)
			return "";

		Matcher m = FLIGHTNUM_FORMAT.matcher(flightnum);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	public void insertBdo(WorldTracerActionDTO dto, Bdo bdo,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
	  // TODO Auto-generated method stub
		
		Map<WorldTracerField, List<String>> fieldMap = createBdoFieldMap(bdo);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate Bdo mapping, ID: " + bdo.getBdoId());
		}
		
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_BDO);
		
		String responseBody = null;
		NameValuePair[] p1 = { new NameValuePair("_flowId",
				"deliverDelayedBag-flow") };
		String newLocation = startFlow(p1, "CREATE BDO", 1);
		// submit close form by post method
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);

		search.setParameter("wtrDisplayRequest.recordReference.stationCode",
				bdo.getAhlId().substring(0, 3));
		search.setParameter("wtrDisplayRequest.recordReference.airlineCode",
				bdo.getAhlId().substring(3, 5));
		search.setParameter("wtrDisplayRequest.recordReference.recordId",
				bdo.getAhlId().substring(5));

		search.setParameter("wtrDeliveryRequest.deliverySationCode", bdo.getAhlId()
				.substring(0, 3));

		search.setParameter("_eventId", "submit");

		try {
			debugOut(search, "");
			client.executeMethodWithPause(search, "CREATE BDO: PREPARE AHL DELIVERY (2)");
			if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				newLocation = search.getResponseHeader("location").getValue();
			} else {
				newLocation = "";
				logger.error("bdo post1 not redirect!");
				throw new WorldTracerException("unable to send BDO");
			}
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to send BDO");
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to send BDO");
		} finally {
			search.releaseConnection();
		}

		if (!"".equals(newLocation)) {
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "CREATE BDO: REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect
						.getResponseBodyAsStream());
				// System.out.println("close incident result:" + responseBody);
			} catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to send BDO", e);
			} catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to send BDO", e);
			} finally {
				redirect.releaseConnection();
			}
		}
		Matcher m = FLOW_PATTERN.matcher(newLocation);
		if(m.find()) {
			flowKey = m.group(1).trim();
		}
		NameValuePair[] p3 = { new NameValuePair("_flowExecutionKey", flowKey) };

		search = new PostMethod(WTRWEB_FLOW_URL);
		search.setQueryString(p3);
		
		String dcName = bdo.getDeliveryCompany();
		
		if(dcName == null || dcName.trim().length() < 1) {
			dcName = "DEFAULT DELIVERY";
		}
		dcName = dcName.replaceAll("[\\.#\"><%]", " ").replaceAll("\\s+", " ").trim();
		
		if(dcName.length() > 44) {
			dcName = dcName.substring(0, 44);
		}
		
		search.setParameter("deliveryOrderVO.deliveryOrder.deliveryServiceCompany", String.format("01 - %s", dcName));
		search.setParameter("deliveryOrderVO.deliveryOrder.deliveryDate",
				fieldMap.get(WorldTracerField.DD).get(0));
		search.setParameter("deliveryOrderVO.deliveryAddr1", "true");
		String da = fieldMap.get(WorldTracerField.DA).get(0);
		Pattern daPatt = Pattern.compile("(.{1,55})( (.{1,55}))?");
		m = daPatt.matcher(da);
		if (!m.find()) {
			throw new WorldTracerException("Unable to parse delivery address");
		}
		search.setParameter("avoidBindingdeliveryOrderVO.deliveryOrder.deliveryAddress.line1", RULES.get(WorldTracerService.WorldTracerField.PA).formatEntry(m.group(1)));
		search.setParameter("avoidBindingdeliveryOrderVO.deliveryOrder.deliveryAddress.line2", m.group(3) == null ? "" : RULES.get(WorldTracerService.WorldTracerField.PA).formatEntry(m.group(3)));

		Pattern ctPatt = Pattern.compile("(\\w{2})(\\d{2})(\\w{1,3})?");
		List<String> bagList = null;
		bagList = fieldMap.get(WorldTracerField.CT);
		if (bagList != null) {
			int addedCount = 0;
			for (String ct : bagList) {
				if (addedCount > 9)
					break;

				m = ctPatt.matcher(ct);
				if (m.find()) {
					search.setParameter(String.format("deliveryOrderVO.colorType%d", addedCount + 1), "true");
					search.setParameter(
							"avoidBindingdeliveryOrderVO.deliveryOrder.colorTypes["
									+ addedCount + "].colorCode", m.group(1));
					search.setParameter(
							"avoidBindingdeliveryOrderVO.deliveryOrder.colorTypes["
									+ addedCount + "].typeCode", m.group(2));
					if (m.group(3) != null) {
						search.setParameter(
								"avoidBindingdeliveryOrderVO.deliveryOrder.colorTypes["
										+ addedCount + "].descriptionCodes", m
										.group(3));
						addedCount++;
					}
				}
			}
		}
		List<String> nameList = null;
		nameList = fieldMap.get(WorldTracerField.NM);
		if (nameList != null) {
			int addedCount = 0;
			for (String name : nameList) {
				if (addedCount > 2)
					break;
				search.setParameter("avoidBindingdeliveryOrderVO.deliveryOrder.names[" + addedCount + "]", RULES.get(WorldTracerService.WorldTracerField.NM).formatEntry(name));
				addedCount++;

			}
		}

		search.setParameter("deliveryOrderVO.deliveryOrder.originStation",
				bdo.getOriginationStationCode());
		search.setParameter("_eventId", "Submit");
		try {
			debugOut(search, "");
			client.executeMethodWithPause(search, "CREATE BDO: BDO (4)");
			if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				newLocation = search.getResponseHeader("location").getValue();
			} else {
				newLocation = "";
				logger.error("bdo post2 not redirect!");
				throw new WorldTracerException("unable to send BDO");
			}
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to send BDO");
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to send BDO");
		} finally {
			search.releaseConnection();
		}

		if (!"".equals(newLocation)) {
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "CREATE BDO: REDIRECT (5)");
				responseBody = getStringFromInputStream(redirect
						.getResponseBodyAsStream());
				logger.debug("Create BDO result result:" + responseBody);
			} catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to send BDO");
			} catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to send BDO");
			} finally {
				redirect.releaseConnection();
			}
		}

		m = BDO_ERROR_PATTERN.matcher(responseBody);
		if(m.find()) {
			throw new WorldTracerException(m.group(1));
		}
		
		if(! responseBody.toUpperCase().contains("SUCCESSFULLY DELIVERED THE GIVEN ORDER")) {
			throw new WorldTracerException("unable to send BDO");
		}
		
		response.setSuccess(true);
	  
  }
	
	private Map<WorldTracerField, List<String>> createBdoFieldMap(Bdo bdo) throws WorldTracerException {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (bdo.getItems() == null || bdo.getItems().length < 1) {
			throw new WorldTracerException("Can't send a bdo with no Items for bdo: " + bdo.getBdoId());
		}
		for (Item item : bdo.getItems()) {
			getItemInfo(item, result, false);
		}

		if (bdo.getDeliveryDate() != null) {
			addIncidentFieldEntry(WorldTracerField.DD, ITIN_DATE_FORMAT.format(bdo.getDeliveryDate()), result);
		}

		if (result.get(WorldTracerField.DD) == null) {
			throw new WorldTracerException("Could not export BDO due to invalid delivery date. Id: " + bdo.getBdoId());
		}

		addIncidentFieldEntry(WorldTracerField.LD, bdo.getDeliveryComments(), result);

		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(bdo.getAgent()), result);

		if (bdo.getPassengers() != null && bdo.getPassengers().length > 0) {
			for (Passenger p : bdo.getPassengers()) {
				getBdoPaxInfo(p, result);
			}
		}

		return result;
	}
	
	private void getBdoPaxInfo(Passenger pax, Map<WorldTracerField, List<String>> result) {
		addIncidentFieldEntry(WorldTracerField.NM, pax.getLastname(), result);
		Address a = pax.getAddress();
		String addy = StringUtils.join(" ", elimNulls(a.getAddress1()), elimNulls(a.getAddress2()),
				elimNulls(a.getCity()),
				elimNulls(a.getState()).equals("") ? elimNulls(a.getProvince()) : elimNulls(a.getState()),
				elimNulls(a.getZip())).trim().replaceAll("\\s+", " ");
		addIncidentFieldEntry(WorldTracerField.DA, addy, result);

		addIncidentFieldEntry(WorldTracerField.PN, wtPhone(a.getHomePhone()), result);
		addIncidentFieldEntry(WorldTracerField.TP, wtPhone(a.getWorkPhone()), result);
		addIncidentFieldEntry(WorldTracerField.CP, wtPhone(a.getMobilePhone()), result);
	}
	
	protected void getItemInfo(Item item, Map<WorldTracerField, List<String>> result, boolean includeXdesc) {

		if (item.getColor() == null || item.getColor().trim().length() <= 0 || item.getType() == null
				|| item.getType().trim().length() != 2) {
			return;
		}

		String colorType = "";
		if ("TD".equals(item.getColor().trim())) {
			colorType = "BN";
		} else {
			colorType = item.getColor().trim();
		}

		String type = item.getType().trim();
		if (!VALID_BAG_TYPES.contains(type)) {
			type = DEFAULT_BAG_TYPE;
		}
		colorType += type;

		String desc1 = mapXDesc(item.getDesc1());
		String desc2 = mapXDesc(item.getDesc2());
		String desc3 = mapXDesc(item.getDesc3());

		colorType += getDescString(desc1, desc2, desc3);

		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		addIncidentFieldEntry(WorldTracerField.BI, item.getManufacturer(), result);
	}
	
	private String mapXDesc(String code) {
		if (code == null || !wt_descs.contains(code)) {
			return "X";
		}
		return code;
	}
	
	private String elimNulls(String str) {
		if (str != null)
			return str.trim();
		return "";
	}
	
	private String wtPhone(String rawText) {
		return rawText != null ? rawText.replaceAll("\\D", "") : null;
	}


	private String getDescString(String... descs) {
		// need to remove duplicates
		Set<String> foo = new HashSet<String>(Arrays.asList(descs));
		String result = "";
		boolean hasMat = false;
		for (String desc : foo) {
			if (wt_mats.contains(desc)) {
				if (!hasMat) {
					result += desc;
					hasMat = true;
				}
			} else {
				result += desc;
			}
		}
		if (result.length() > 3) {
			return result.substring(0, 3);
		} else if (result.length() == 3) {
			return result;
		} else if (result.length() == 2) {
			return result + "X";
		} else if (result.length() == 1) {
			return result + "XX";
		} else {
			return "XXX";
		}
	}
	
	public void closeAhl(WorldTracerActionDTO dto, Ahl ahl,
      WorldTracerResponse response) {
	  // TODO Auto-generated method stub
	  
  }
	

}
