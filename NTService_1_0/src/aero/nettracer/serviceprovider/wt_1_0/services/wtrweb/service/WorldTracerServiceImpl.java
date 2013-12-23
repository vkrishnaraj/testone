package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
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
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.common.utils.StringUtils;
import aero.nettracer.serviceprovider.ws_1_0.common.WebServiceError;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Address;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ClaimCheck;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Item;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.NotLoggedIntoWorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.PreProcessor;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerAlreadyClosedException;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.TxType;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
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
	private static final int MAX_NAME_LENGTH = 20;
	private static final int MAX_ADDRESS_LINE = 50;
	private static final int MAX_PHONE_LENGTH = 50;
	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format.FREE_FLOW);




	RuleMapper wtRuleMap = new UsWorldTracerRuleMap();
	
	
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
				logger.info(redirect.getResponseBodyAsString());
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
			response.setSuccess(true);
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
		
		
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createFwdFieldMap(msg, dto);
		
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
							if (finalTag != null) {
								search.setParameter("rushBagRecord.rushBagGroup.rushBags[" + addedCount + "].originalBagTag.airlineCode", finalTag.substring(0,2));
								search.setParameter("rushBagRecord.rushBagGroup.rushBags[" + addedCount + "].originalBagTag.tagNumber", finalTag.substring(2,8));
								addedCount++;								
							}
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
			// BEGIN
			StringBuilder sb = new StringBuilder("params:");
			for (NameValuePair foo : search.getParameters()) {
				sb.append(String
						.format("%s=%s,", foo.getName(), foo.getValue()));
			}
			logger.info(sb.toString());
			/// END 
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
		
		Pattern succePatt = Pattern.compile("var recordId = '[0-9]{5}';", Pattern.CASE_INSENSITIVE);
		Matcher succeMat = succePatt.matcher(responseBody);
		
//		if (responseBody.toUpperCase().contains("RECORD CREATED SUCCESSFULLY") || responseBody.toUpperCase().contains("FILE CREATED SUCCESSFULLY")) {
//			response.setSuccess(true);
//			return;
//		}
		if (succeMat.find()) {
			response.setSuccess(true);
			return;
		} else {
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
	

	


	


	public void insertBdo(WorldTracerActionDTO dto, Bdo bdo,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		
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
		search.setParameter("avoidBindingdeliveryOrderVO.deliveryOrder.deliveryAddress.line1", RULES.get(DefaultWorldTracerService.WorldTracerField.PA).formatEntry(m.group(1)));
		search.setParameter("avoidBindingdeliveryOrderVO.deliveryOrder.deliveryAddress.line2", m.group(3) == null ? "" : RULES.get(DefaultWorldTracerService.WorldTracerField.PA).formatEntry(m.group(3)));

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
				search.setParameter("avoidBindingdeliveryOrderVO.deliveryOrder.names[" + addedCount + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(name.replace(" ", "")));
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
			PreProcessor.addIncidentFieldEntry(WorldTracerField.DD, PreProcessor.ITIN_DATE_FORMAT.format(bdo.getDeliveryDate()), result);
		}

		if (result.get(WorldTracerField.DD) == null) {
			throw new WorldTracerException("Could not export BDO due to invalid delivery date. Id: " + bdo.getBdoId());
		}

		PreProcessor.addIncidentFieldEntry(WorldTracerField.LD, bdo.getDeliveryComments(), result);

		PreProcessor.addIncidentFieldEntry(WorldTracerField.AG, PreProcessor.getAgentEntry(bdo.getAgent()), result);

		if (bdo.getPassengers() != null && bdo.getPassengers().length > 0) {
			for (Passenger p : bdo.getPassengers()) {
				getBdoPaxInfo(p, result);
			}
		}

		return result;
	}
	
	private void getBdoPaxInfo(Passenger pax, Map<WorldTracerField, List<String>> result) {
		PreProcessor.addIncidentFieldEntry(WorldTracerField.NM, pax.getLastname(), result);
		Address a = pax.getAddress();
		String addy = StringUtils.join(" ", elimNulls(a.getAddress1()), elimNulls(a.getAddress2()),
				elimNulls(a.getCity()),
				elimNulls(a.getState()).equals("") ? elimNulls(a.getProvince()) : elimNulls(a.getState()),
				elimNulls(a.getZip())).trim().replaceAll("\\s+", " ");
		PreProcessor.addIncidentFieldEntry(WorldTracerField.DA, addy, result);

		PreProcessor.addIncidentFieldEntry(WorldTracerField.PN, wtPhone(a.getHomePhone()), result);
		PreProcessor.addIncidentFieldEntry(WorldTracerField.TP, wtPhone(a.getWorkPhone()), result);
		PreProcessor.addIncidentFieldEntry(WorldTracerField.CP, wtPhone(a.getMobilePhone()), result);
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
		if (!PreProcessor.VALID_BAG_TYPES.contains(type)) {
			type = PreProcessor.DEFAULT_BAG_TYPE;
		}
		colorType += type;

		String desc1 = PreProcessor.mapXDesc(item.getDesc1());
		String desc2 = PreProcessor.mapXDesc(item.getDesc2());
		String desc3 = PreProcessor.mapXDesc(item.getDesc3());

		colorType += getDescString(desc1, desc2, desc3);

		PreProcessor.addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		PreProcessor.addIncidentFieldEntry(WorldTracerField.BI, item.getManufacturer(), result);
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
			if (PreProcessor.wt_mats.contains(desc)) {
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
	
	public void requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.requestQuickOhd(dto, data, response);
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.REQUEST_QOH);
		
		String ahlId = data.getAhl().getAhlId();
		

		NameValuePair[] p1 = { new NameValuePair("_flowId", "requestbag-flow")};
		
		String newLocation = startFlow(p1, "REQUEST QOH", 1);
		String responseBody = null;
		//if wt_ahl_id exists
		PostMethod getAhl = new PostMethod(newLocation);
		NameValuePair[] pg = { new NameValuePair("_flowExecutionKey", flowKey)};
		getAhl.setQueryString(pg);
		getAhl.setParameter("wtrDisplayRequest.recordReference.stationCode", ahlId.substring(0, 3));
		getAhl.setParameter("wtrDisplayRequest.recordReference.airlineCode", ahlId.substring(3, 5));
		getAhl.setParameter("wtrDisplayRequest.recordReference.recordId", ahlId.substring(5));
		getAhl.setParameter("resultsForm.sortOption", "recordReference");
		getAhl.setParameter("radio", "1");
		getAhl.setParameter("_eventId", "continue");
		try {
			debugOut(getAhl, "");
			client.executeMethodWithPause(getAhl, "REQUEST QOH: GET AHL (2)");
			if(getAhl.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getAhl.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getAhl.getResponseHeader("location").getValue();
				Matcher m = FLOW_PATTERN.matcher(newLocation);
				if(m.find()) {
					flowKey = m.group(1).trim();
				}
				else {
					throw new WorldTracerException("no flow key after rqoh get detials: " + newLocation);
				}
				//flowKey = newLocation.split("=")[1];
			}else{
				logger.error("Redirect to Ahl details page error!");
				throw new WorldTracerException("Redirect to Ahl details page error!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		finally {
			getAhl.releaseConnection();
		}
		GetMethod getWtDetails = new GetMethod(newLocation);
		try {
			client.executeMethodWithPause(getWtDetails, "REQUEST QOH: AHL DETAILS (3)");
			if(getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				logger.error("GetWtDetails method redirect again!");
				throw new WorldTracerException("Unable to send RequestQuickOhd");
			}else{
				responseBody = getStringFromInputStream(getWtDetails.getResponseBodyAsStream());
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to send RequestQuickOhd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to send RequestQuickOhd");
		}
		finally {
			getWtDetails.releaseConnection();
		}
		if(responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			throw new WorldTracerException("Unable to send RequestQuickOhd: no matching record");
		}
		//add params to request
		PostMethod requestMethod = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		requestMethod.setQueryString(p2);
		
		requestMethod.setParameter("_eventId", "submit");
		requestMethod.setParameter("onHandOrQuickOnHand", "quickOnHand");
		
		requestMethod.setParameter("wtrForwardRequest.recordReference.stationCode", ahlId.substring(0, 3));
		requestMethod.setParameter("wtrForwardRequest.recordReference.airlineCode", ahlId.substring(3, 5));
		requestMethod.setParameter("wtrForwardRequest.recordReference.recordId", ahlId.substring(5));
		
		requestMethod.setParameter("wtrForwardRequest.quickOnHandStationCode", data.getFromStation());
		requestMethod.setParameter("wtrForwardRequest.quickOnHandAirlineCode", data.getFromAirline());
		
		if (fieldMap.containsKey(WorldTracerField.TN)) {
			List<String> tagList = fieldMap.get(WorldTracerField.TN);
			for(int i = 0; i < tagList.size(); i++){
				String bagTag = fieldMap.get(WorldTracerField.TN).get(i);
				requestMethod.setParameter("wtrForwardRequest.quickOnHandBagTags["+ i +"].airlineCode", bagTag.substring(0, 2));
				requestMethod.setParameter("wtrForwardRequest.quickOnHandBagTags["+ i +"].tagNumber", bagTag.substring(2));
			}
		}
		if (fieldMap.containsKey(WorldTracerField.NM)) {
			List<String> nameList = fieldMap.get(WorldTracerField.NM);
			for(int i=0; i < nameList.size() && i < 3; i++){
				requestMethod.setParameter("wtrForwardRequest.passenger.names[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(nameList.get(i).replace(" ", "")));
			}
		}
		if (fieldMap.containsKey(WorldTracerField.FI)) {
			requestMethod.setParameter("wtrForwardRequest.supplementaryInfo[0]", RULES.get(DefaultWorldTracerService.WorldTracerField.FI).formatEntry(fieldMap.get(WorldTracerField.FI).get(0)).replace(".", "&#46;"));
		}
		if (fieldMap.containsKey(WorldTracerField.SL)) {
			requestMethod.setParameter("wtrForwardRequest.storageLocation", RULES.get(DefaultWorldTracerService.WorldTracerField.SL).formatEntry(fieldMap.get(WorldTracerField.SL).get(0)).replace(".", "&#46;"));
		}
		try {
			debugOut(requestMethod, "");
			client.executeMethodWithPause(requestMethod, "REQUEST QOH: REQUEST (4)");
			if(requestMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || requestMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = requestMethod.getResponseHeader("location").getValue();
			}
			else{
				logger.error("request quick on-hand method not redirect!");
				throw new WorldTracerException("Unable to send RequestQuickOhd: Not redirect!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to send RequestQuickOhd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to send RequestQuickOhd");
		}
		finally {
			requestMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "REQUEST QOH: REDIRECT (5)");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to send RequestQuickOhd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to send RequestQuickOhd");
		}
		finally {
			redirect.releaseConnection();
		}


		if (responseBody.toUpperCase().contains("BAG REQUESTED SUCCESSFULLY")) {
			response.setSuccess(true);
		} else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to ROH. See logs for details";
				logger.error("responseBody:" + responseBody);
			}
			throw new WorldTracerException(errorString);
		}
		
	  
  }

	public void requestOhd(WorldTracerActionDTO dto, RequestOhd data,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.requestQuickOhd(dto, data, response);
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.REQUEST_QOH);
		
		String ahlId = data.getAhl().getAhlId();
		String ohdId = data.getOhdId();
		
		NameValuePair[] p1 = { new NameValuePair("_flowId", "requestbag-flow")};
		String newLocation = startFlow(p1, "REQUEST OHD", 1);

		String responseBody = null;
		//if wt_ahl_id exists
		PostMethod getAhl = new PostMethod(newLocation);
		NameValuePair[] pg = { new NameValuePair("_flowExecutionKey", flowKey)};
		getAhl.setQueryString(pg);
		getAhl.setParameter("wtrDisplayRequest.recordReference.stationCode", ahlId.substring(0, 3));
		getAhl.setParameter("wtrDisplayRequest.recordReference.airlineCode", ahlId.substring(3, 5));
		getAhl.setParameter("wtrDisplayRequest.recordReference.recordId", ahlId.substring(5));
		getAhl.setParameter("resultsForm.sortOption", "recordReference");
		getAhl.setParameter("radio", "1");
		getAhl.setParameter("_eventId", "continue");
		try {
			debugOut(getAhl, "");
			client.executeMethodWithPause(getAhl, "REQUEST OHD: GET AHL (2)");
			if(getAhl.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getAhl.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getAhl.getResponseHeader("location").getValue();
				Matcher m = FLOW_PATTERN.matcher(newLocation);
				if(m.find()) {
					flowKey = m.group(1).trim();
				}
				else {
					throw new WorldTracerException("no flow key after roh details: " + newLocation);
				}
				//flowKey = newLocation.split("=")[1];
			}else{
				logger.error("Redirect to Ahl details page error!");
				throw new WorldTracerException("Redirect to Ahl details page error!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to request ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("Unable to request ohd");
		}
		finally {
			getAhl.releaseConnection();
		}
		GetMethod getWtDetails = new GetMethod(newLocation);
		try {
			client.executeMethodWithPause(getWtDetails, "REQUEST OHD: GET DETAILS (3)");
			if(getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				System.out.println("getWtDetails method redirect again!");
				throw new WorldTracerException("unable to request ohd");
			}else{
				responseBody = getStringFromInputStream(getWtDetails.getResponseBodyAsStream());
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to request ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to request ohd");
		}
		finally {
			getWtDetails.releaseConnection();
		}
		if(responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			throw new WorldTracerException("wt_ahl_id not exists!");
		}
		//add params to request
		PostMethod requestMethod = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		requestMethod.setQueryString(p2);
		
		requestMethod.setParameter("_eventId", "submit");
		requestMethod.setParameter("onHandOrQuickOnHand", "onHand");
		
		requestMethod.setParameter("wtrForwardRequest.recordReference.stationCode", ahlId.substring(0, 3));
		requestMethod.setParameter("wtrForwardRequest.recordReference.airlineCode", ahlId.substring(3, 5));
		requestMethod.setParameter("wtrForwardRequest.recordReference.recordId", ahlId.substring(5));
		
		requestMethod.setParameter("wtrForwardRequest.onHandRecords[0].stationCode", ohdId.substring(0, 3));
		requestMethod.setParameter("wtrForwardRequest.onHandRecords[0].airlineCode", ohdId.substring(3, 5));
		requestMethod.setParameter("wtrForwardRequest.onHandRecords[0].recordId", ohdId.substring(5));
		
		if (fieldMap.containsKey(WorldTracerField.NM)) {
			List<String> nameList = fieldMap.get(WorldTracerField.NM);
			for(int i=0; i < nameList.size() && i < 3; i++){
				requestMethod.setParameter("wtrForwardRequest.passenger.names[" + i + "]", nameList.get(i));
			}
		}
		if (fieldMap.containsKey(WorldTracerField.FI)) {
			requestMethod.setParameter("wtrForwardRequest.supplementaryInfo[0]", RULES.get(DefaultWorldTracerService.WorldTracerField.FI).formatEntry(fieldMap.get(WorldTracerField.FI).get(0)).replace(".", "&#46;"));
		}
		if (fieldMap.containsKey(WorldTracerField.SL)) {
			requestMethod.setParameter("wtrForwardRequest.storageLocation", RULES.get(DefaultWorldTracerService.WorldTracerField.SL).formatEntry(fieldMap.get(WorldTracerField.SL).get(0)).replace(".", "&#46;"));
		}

		try {
			debugOut(requestMethod, "");
			client.executeMethodWithPause(requestMethod, "REQUEST OHD: REQUEST (4)");
			if(requestMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || requestMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = requestMethod.getResponseHeader("location").getValue();
			}
			else{
				logger.error("Request OHD error response: " + requestMethod.getResponseBodyAsString());
				throw new WorldTracerException("request method not redirect!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to request ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to request ohd");
		}
		finally {
			requestMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethodWithPause(redirect, "REQUEST OHD: GET DETAILS (5)");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to request ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to request ohd");
		}
		finally {
			redirect.releaseConnection();
		}
		
		if (responseBody.toUpperCase().contains("BAG REQUESTED SUCCESSFULLY")) {
			response.setSuccess(true);
		} else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to ROH. See logs for details";
				logger.error(errorString);
			}
			logger.error("responseBody:" + responseBody);
			throw new WorldTracerException(errorString);
		}
	  
  }

	public void forwardOhd(WorldTracerActionDTO dto, ForwardOhd data,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.forwardOhd(dto, data, response);
		NameValuePair[] p1 = { new NameValuePair("_flowId", "forwardbag-flow")};
		String ahlId = data.getAhlId();
		String ohdId = data.getOhdId();
		
		String newLocation = startFlow(p1, "FORWARD OHD", 1);
		String responseBody = null;
		//if wt_ohd_id exists
		PostMethod getOnhand = new PostMethod(newLocation);
		NameValuePair[] pg = { new NameValuePair("_flowExecutionKey", flowKey)};
		getOnhand.setQueryString(pg);
		getOnhand.setParameter("wtrDisplayRequest.recordReference.stationCode", ohdId.substring(0, 3));
		getOnhand.setParameter("wtrDisplayRequest.recordReference.airlineCode", ohdId.substring(3, 5));
		getOnhand.setParameter("wtrDisplayRequest.recordReference.recordId", ohdId.substring(5));
		getOnhand.setParameter("resultsForm.sortOption", "recordReference");
		getOnhand.setParameter("_eventId", "continue");
		try {
			debugOut(getOnhand, "");
			client.executeMethodWithPause(getOnhand, "FORWARD OHD: LOAD OHD (2)");
			if(getOnhand.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getOnhand.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getOnhand.getResponseHeader("location").getValue();
				flowKey = newLocation.split("=")[1];
			}else{
				logger.error("Get onhand details error!");
				throw new WorldTracerException("Get onhand details error!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		finally {
			getOnhand.releaseConnection();
		}
		GetMethod getWtDetails = new GetMethod(newLocation);
		try {
			client.executeMethodWithPause(getWtDetails, "FORWARD OHD: GET DETAILS (3)");
			if(getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || 
					getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				logger.error("getWtDetails method redirect again!");
				throw new WorldTracerException("unable to forward ohd");
			}else{
				responseBody = getStringFromInputStream(getWtDetails.getResponseBodyAsStream());
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		finally {
			getWtDetails.releaseConnection();
		}
		if(responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			throw new WorldTracerException("wt_ohd_id not exists!");
		}
		//add params to forward
		PostMethod forwardMethod = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		forwardMethod.setQueryString(p2);
		
		forwardMethod.setParameter("_eventId", "submit");
		forwardMethod.setParameter("onHandOrQuickOnHand", "onHand");
		forwardMethod.setParameter("wtrForwardRequest.sendHandledAirlineCopy", "NO");
		
		forwardMethod.setParameter("wtrForwardRequest.delayedBagReference.stationCode", ahlId.substring(0, 3));
		forwardMethod.setParameter("wtrForwardRequest.delayedBagReference.airlineCode", ahlId.substring(3, 5));
		forwardMethod.setParameter("wtrForwardRequest.delayedBagReference.recordId", ahlId.substring(5));
		
		forwardMethod.setParameter("wtrForwardRequest.onHandRecords[0].stationCode", ohdId.substring(0, 3));
		forwardMethod.setParameter("wtrForwardRequest.onHandRecords[0].airlineCode", ohdId.substring(3, 5));
		forwardMethod.setParameter("wtrForwardRequest.onHandRecords[0].recordId", ohdId.substring(5));
		
		if (fieldMap.containsKey(WorldTracerField.NM)) {
			forwardMethod.setParameter("wtrForwardRequest.passenger.names[0]", fieldMap.get(WorldTracerField.NM).get(0));
		}
		if (fieldMap.containsKey(WorldTracerField.FO)) {
			List<String> itinList = fieldMap.get(WorldTracerField.FO);
			if(itinList != null){
				for(int i = 0; i < itinList.size() && i < 4; i++){
					String[] inti = itinList.get(i).split("/");
					String airlineCode = inti[0].substring(0, 2);
					String flightNumber = inti[0].substring(2);
					String flightDate = inti[1];
					forwardMethod.setParameter("wtrForwardRequest.rushFlights["+ i +"].airlineCode", airlineCode);
					forwardMethod.setParameter("wtrForwardRequest.rushFlights["+ i +"].flightNumber", flightNumber);
					forwardMethod.setParameter("wtrForwardRequest.rushFlights["+ i +"].flightDate", flightDate);
				}
			}
		}
		if (fieldMap.containsKey(WorldTracerField.FW)) {
			List<String> routeList = fieldMap.get(WorldTracerField.FW);
			for (int i = 0; i < routeList.size() && i < 5; i++) {
				String fw = routeList.get(i);
				forwardMethod.setParameter("wtrForwardRequest.rushStationAirlines[0].stationCode", fw.substring(0, 3));
				forwardMethod.setParameter("wtrForwardRequest.rushStationAirlines[0].airlineCode", fw.substring(3));
			}
		}
		if (fieldMap.containsKey(WorldTracerField.XT)) {
			String bagTag = fieldMap.get(WorldTracerField.XT).get(0);
			forwardMethod.setParameter("wtrForwardRequest.rushBagTags[0].airlineCode", bagTag.substring(0, 2));
			forwardMethod.setParameter("wtrForwardRequest.rushBagTags[0].tagNumber", bagTag.substring(2));
		}
		if (fieldMap.containsKey(WorldTracerField.SI)) {
			forwardMethod.setParameter("wtrForwardRequest.supplementaryInfo[0]", fieldMap.get(WorldTracerField.SI).get(0).replace(".", "&#46;"));
		}

		try {
			debugOut(forwardMethod, "");
			client.executeMethodWithPause(forwardMethod, "FORWARD OHD: FORWARD (4)");
			if(forwardMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || forwardMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = forwardMethod.getResponseHeader("location").getValue();
			}
			else{
				//responseBody = getStringFromInputStream(forwardMethod.getResponseBodyAsStream());
				//System.out.println("forward method: " + responseBody);
				throw new WorldTracerException("forward method not redirect!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		finally {
			forwardMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "FORWARD OHD: REDIRECT (5)");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to forward ohd", e);
		}
		finally {
			redirect.releaseConnection();
		}
		String errorString;
		if (responseBody.toUpperCase().contains("BAG FORWARDED SUCCESSFULLY")) {
			errorString = OK;
		} else {
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to forward bag. See logs for details";
			}
		}
		if (OK.equals(errorString)) {
			response.setSuccess(true);
			return;
		}
		throw new WorldTracerException(errorString);
  }

	public void getAhl(WorldTracerActionDTO dto, Ahl data,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String ahlId = data.getAhlId();
		String responseBody = getAhl(ahlId);
		Ahl ahl = parseWTIncident(ahlId, responseBody);
	  response.setAhl(ahl);
  }
	

	public void getOhd(WorldTracerActionDTO dto, Ohd data,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String ohdId = data.getOhdId();
		String responseBody = getOhd(ohdId);
		Ohd ohd = parseWTOhd(ohdId, responseBody);
		response.setOhd(ohd);
	  
  }
	


	public void createAhl(WorldTracerActionDTO dto, Ahl data,
      WorldTracerResponse response) throws WorldTracerException {
		
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createAhlFieldMap(data);  //Create error, corrected field IT

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate incident mapping");
		}
		
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_AHL);
		
		String responseBody = null;
		String newLocation = null;
		try {
			NameValuePair[] p1 = { new NameValuePair("_flowId", "createdelayedbagrecord-flow") };
			newLocation = startFlow(p1, "INSERT INCIDENT", 1);

			PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
			List<String> bagsList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CC);  //for bagTag.airlineCode and bagTag.tagNum
			for(Map.Entry<DefaultWorldTracerService.WorldTracerField, List<String>> fieldEntry : fieldMap.entrySet()){
				List<String> fieldList = fieldEntry.getValue();
				//add bag type
				if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.CT){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String bagFace = fieldList.get(i);
							String colorType = bagFace.substring(0, 2);
							String bagType = bagFace.substring(2, 4);
							String colorDesc = bagFace.substring(4, 7);
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].colorType.colorCode", colorType);
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].colorType.typeCode", bagType);
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].colorType.descriptionCodes", colorDesc);
						}
					}
				}
				//add passenger itinerary
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.FD){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String paxIntinery = fieldList.get(i);
							String[] inti = paxIntinery.split("/");
							String airlineCode = inti[0].substring(0, 2);
							String flightNumber = inti[0].substring(2);
							String flightDate = inti[1];
							search.setParameter("delayedBagRecord.passenger.paxFlights["+ i +"].airlineCode", airlineCode);
							search.setParameter("delayedBagRecord.passenger.paxFlights["+ i +"].flightNumber", flightNumber);
							search.setParameter("delayedBagRecord.passenger.paxFlights["+ i +"].flightDate", flightDate);							
						}
					}
				}
				//add passenger initials
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.IT){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String initial = fieldList.get(i);
							search.setParameter("delayedBagRecord.passenger.initials[" + i +"]", initial);
						}
					}
				}
				//add passenger routing
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.RT){
					if(fieldList != null){
						int originCount = 2;
						for(int i = 0; i < fieldList.size(); i++){
							String station = fieldList.get(i);
							search.setParameter("delayedBagRecord.delayedBagGroup.route.tracingStations["+ i +"]", station);
							if(i <= 1){
								//add first routing
								search.setParameter("delayedBagRecord.delayedBagGroup.route.passengerRoute["+ i +"]", station);
							}else{
								//add other routing								
								search.setParameter("station__origin__field0" + originCount, fieldList.get(i-1));
								search.setParameter("delayedBagRecord.delayedBagGroup.route.passengerRoute["+ originCount +"]", station);
								originCount++;
							}							
						}
					}
				}
				//add passenger names
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.NM){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(i));					
							search.setParameter("delayedBagRecord.passenger.names[" + i +"]", name);
						}
					}
				}
				
				else if (fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.FL) {
					logger.info("FL: " + fieldEntry.getValue().get(0));
					search.setParameter("quickEntryRecord.passenger.frequentFlyerId", fieldEntry.getValue().get(0));
					search.setParameter("delayedBagRecord.passenger.frequentFlyerId", fieldEntry.getValue().get(0));
				}
				
				else if (fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.PS) {
					logger.info("PS: " + fieldEntry.getValue().get(0));

					search.setParameter("delayedBagRecord.passenger.classStatus", fieldEntry.getValue().get(0));
					search.setParameter("quickEntryRecord.passenger.classStatus", fieldEntry.getValue().get(0));
				}

				
				//add first passenger title
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.PT){
					if(fieldList != null){						
						String[] titles = fieldList.get(0).split(". ");
						if(titles.length>0) {
							String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(titles[titles.length-1]);
							search.setParameter("delayedBagRecord.passenger.title", title);
						}
					}
				}
				//add passenger permanent address and country code
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.PA){
					//firstly get country code
					List<String> countryList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CO);
					//add first passenger country
					if(countryList != null){
						search.setParameter("delayedBagRecord.passenger.countryCode", countryList.get(0));
					}
					if(fieldList != null){
						for(int i = 0; i < fieldList.size() && i < 2; i++){
							String address = BASIC_RULE.formatEntry(fieldEntry.getValue().get(i).trim());
							search.setParameter("delayedBagRecord.passenger.permanentAddress.line" + (i+1), address.replace(".", "&#46;"));
							search.setParameter("delayedBagRecord.passenger.delivery.deliveryAddress.line"+(i+1), address.replace(".", "&#46;"));
						}
						search.setParameter("delayedBagRecord.deliveryAddressType", "PERMANENT");
					}
				} else if (fieldEntry.getKey() ==DefaultWorldTracerService.WorldTracerField.ZIP) {
					search.setParameter("delayedBagRecord.passenger.zipCode", fieldEntry.getValue().get(0));
				} else if (fieldEntry.getKey() ==DefaultWorldTracerService.WorldTracerField.STATE) {
					search.setParameter("delayedBagRecord.passenger.state", fieldEntry.getValue().get(0));
				}
				//add passenger temp address and country code
				else if(fieldEntry.getKey() ==DefaultWorldTracerService.WorldTracerField.TA){
					//firstly get country code
					if(null == search.getParameter("delayedBagRecord.passenger.countryCode") || "".equals(search.getParameter("delayedBagRecord.passenger.countryCode").getValue().trim())){
						List<String> countryList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CO);
						//add first passenger country
						if(countryList != null){
							search.setParameter("delayedBagRecord.passenger.countryCode", countryList.get(0));
						}
					}
					if(fieldList != null){
						boolean isTemp = false;
						if(null == search.getParameter("delayedBagRecord.deliveryAddressType") || "TEMPORARY".equalsIgnoreCase(search.getParameter("delayedBagRecord.deliveryAddressType").getValue().trim())){
							search.setParameter("delayedBagRecord.deliveryAddressType", "TEMPORARY");
							isTemp = true;
						}
						for(int i = 0; i < fieldList.size() && i < 2; i++){
							String address = BASIC_RULE.formatEntry(fieldEntry.getValue().get(i).trim());
							search.setParameter("delayedBagRecord.passenger.tempAddress.line" + (i+1), address.replace(".", "&#46;"));
							if(isTemp){
								search.setParameter("delayedBagRecord.passenger.delivery.deliveryAddress.line"+(i+1), address.replace(".", "&#46;"));
							}
						}
					}
				}
				//add first passenger email  ABC/D/FFS/U/FSAG/+/GASGGSDG/T/G/A/GSDG/D/DGS/D/GEGGGE
				else if(fieldEntry.getKey() ==DefaultWorldTracerService.WorldTracerField.EA){
					if(fieldList != null && fieldList.size() >= 1){
						String email = RULES.get(DefaultWorldTracerService.WorldTracerField.EA).formatEntry(fieldList.get(0)).length()>1?fieldList.get(0).replace("/A/", "@")
								.replace("/D/", ".").replace("/U/", "_").replace("/T/", "~").replace("/P/", "+"):"";
						search.setParameter("delayedBagRecord.passenger.email", email);
					}
				}
				//add passenger home/business phone, here maybe execute two times
				else if(fieldEntry.getKey() ==DefaultWorldTracerService.WorldTracerField.PN || fieldEntry.getKey() ==DefaultWorldTracerService.WorldTracerField.TP){
					fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PN);
					List<String> bizPhones = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
					if(bizPhones == null && fieldList != null){
						for(int i = 0; i < (fieldList.size()<=2 ? fieldList.size() : 2); i++){
							search.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(i)));
						}
					}
					else if(fieldList == null && bizPhones != null){
						for(int i = 0; i < (bizPhones.size()<=2 ? bizPhones.size() : 2); i++){
							search.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.TP).formatEntry(bizPhones.get(i)));
						}
					}
					else if(fieldList != null && bizPhones != null){
						String permPhone = "";
						int max = fieldList.size() > bizPhones.size() ? fieldList.size() : bizPhones.size();
						if(max > 2)
							max = 2;
						if(fieldList != null){
							for(int i = 0; i < max; i++){
								if(fieldList.get(i)!=null)
									permPhone += fieldList.get(i) + ",";
								if(bizPhones.get(i)!=null)
									permPhone += bizPhones.get(i) + ",";
								if(permPhone.startsWith(","))
									permPhone = permPhone.substring(1);
								if(permPhone.endsWith(","))
									permPhone = permPhone.substring(0, permPhone.length()-1);
								search.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(permPhone));
							}
						}
					}
				}
				//add passenger cellphone
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.CP){
					if(fieldList != null){
						for(int i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
							search.setParameter("delayedBagRecord.passenger.cellPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(i)));
						}
					}
				}
				//add passenger fax
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.FX){
					if(fieldList != null){
						for(int i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
							search.setParameter("delayedBagRecord.passenger.fax" + (i+1), RULES.get(DefaultWorldTracerService.WorldTracerField.FX).formatEntry(fieldList.get(i)));
						}
					}
				}
				//add number of passenger and booking infomation
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.NP){
					if(fieldList != null){
						search.setParameter("delayedBagRecord.passenger.booking.numberOfPaxInfo", RULES.get(DefaultWorldTracerService.WorldTracerField.NP).formatEntry(fieldList.get(0)));
					}
					search.setParameter("delayedBagRecord.passenger.booking.pooledTicketNumber", "0000");
					search.setParameter("_avoidBindingdelayedBagRecord.passenger.booking.group", "on");
				}
				//add bag itinerary
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.BR){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String bagIntinery = fieldList.get(i);
							String[] inti = bagIntinery.split("/");
							String airlineCode = inti[0].substring(0, 2);
							String flightNumber = inti[0].substring(2);
							String flightDate = inti[1];
							search.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ i +"].airlineCode", airlineCode);
							search.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ i +"].flightNumber", flightNumber);
							search.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ i +"].flightDate", flightDate);
						}
					}
				}
				//add tag number of bags  [US123456, A1234567]
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.TN){
					if(fieldList != null){
						int count = fieldList.size() < bagsList.size() ? fieldList.size() : bagsList.size();
						for(int i = 0; i < count; i++){
							String airtag = fieldList.get(i);
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagTag.airlineCode", airtag.substring(0,2));
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagTag.tagNumber", airtag.substring(2));
						}
					}
				}
				//add brand of bags
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.BI){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].brandInfo.brandInformation", RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(i)));
						}
					}
				}
				//add bags contents:  [01 BOOK/TECHNICAL BOOK.- PHOTO/VIEW PHOTOES, 02 FOOD/GOOD FOODS]
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.CC){
					if(fieldList != null){
						//bags number must be 1, if more would be error in WorldTracer?
						for(int i = 0; i < 1; i++){
							String bagInfo = fieldList.get(i);
							String[] bagContents = bagInfo.split(".- ");
							int countPerBag = bagContents.length;
							for(int j = 0; j < countPerBag; j++){
								String contents = bagContents[j];
								if(j == 0)
									contents = contents.substring(3);
								int index = contents.indexOf("/");
								search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagContents.bagItemsList["+ j +"].category", contents.substring(0, index));
								search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagContents.bagItemsList["+ j +"].descriptionLine1", contents.substring(index+1).replace(".", "&#46;"));
								search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagContents.bagItemsList["+ j +"].descriptionLine2", "/");
								
							}
						}
					}
				}
			}
			search.setParameter("_flowExecutionKey", flowKey);
			search.setParameter("delayedBagRecord.recordReference.stationCode", data.getStationCode());
			search.setParameter("delayedBagRecord.recordReference.airlineCode", data.getAirlineCode());
			search.setParameter("delayedBagRecord.tracingOption", "FULLTRACING");
			search.setParameter("avoidBindingdelayedBagRecord.handledAirlineCopyOption", "");
			search.setParameter("userLang", "1");
			search.setParameter("flowExecutionKey_Flights", flowKey);
			search.setParameter("flowExecutionKey_Bags", flowKey);
			search.setParameter("_avoidBindingdelayedBagRecord.delayedBagGroup.keysCollected", "on");
			search.setParameter("bagType", "Delayed");
			search.setParameter("_eventId", "submit");
			
			try {
				debugOut(search, "insertIncident");
				client.executeMethodWithPause(search, "INSERT INCIDENT: INSERT (2)");
				if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
					newLocation = search.getResponseHeader("location").getValue();
				}
				else{
					logger.error("Insert incident method not redirect!");
					throw new WorldTracerException("unable to export incident");
				}
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to export incident: " + e.getMessage(), e);
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to export incident: " + e.getMessage(), e);
			}
			finally {
				search.releaseConnection();
			}
			//redirect to submit result page and get wt_id
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "INSERT INCIDENT: REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to export incident: " + e.getMessage(), e);
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to export incident: " + e.getMessage(), e);
			}
			finally {
				redirect.releaseConnection();
			}
		} catch (Exception e) {
			throw new WorldTracerConnectionException(
					"Communication error with WorldTracer", e);
		}
		String wt_id = null;
		String errorString = "Submit failed.";
		Pattern succePatt = Pattern.compile("<SPAN.*>([^<>]+)\\[ACTIVE\\/TRACING([^<>]+)]<\\/SPAN>",Pattern.CASE_INSENSITIVE);
		Matcher succeMat = succePatt.matcher(responseBody);
		if(succeMat.find()){
			wt_id = succeMat.group(1).replaceAll("&nbsp;", "").replaceAll("(\\(.*\\))", "");
		}else{
			succePatt = Pattern.compile("error = \'([^\']*)\'",Pattern.CASE_INSENSITIVE);
			succeMat = succePatt.matcher(responseBody);
			if(succeMat.find())
				errorString = succeMat.group(1);
		}
		logger.info("incident wt_id:" + wt_id);
		if (wt_id != null && wt_id.length() >= 10) {
			Ahl responseAhl = new Ahl();
			responseAhl.setAhlId(wt_id.trim());
			response.setAhl(responseAhl);
			response.setSuccess(true);
			return;
		} else {
			logger.error("Exception inserting AHL; responseBody below: \n" + responseBody);
			throw new WorldTracerException(errorString + " Response body below: " + responseBody);
		}
	  
  }

	
	private String getAhl(String wt_id) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = null;

		NameValuePair[] p1 = { new NameValuePair("_flowId", "displaydelayedbagrecord-flow")};
		String newLocation = startFlow(p1, "FIND AHL", 1);

		//submit close form by post method
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		search.setQueryString(p2);
		
		search.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_id.substring(0,3));
		search.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_id.substring(3,5));
		search.setParameter("wtrDisplayRequest.recordReference.recordId", wt_id.substring(5));
		search.setParameter("resultsForm.sortOption", "recordReference");
		search.setParameter("_eventId", "displayrecord");
		
		try {
			debugOut(search, "");
			client.executeMethod(search, "FIND AHL (FRONTEND): SEARCH AHL (2)");
			if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = search.getResponseHeader("location").getValue();
			}
			else{
				newLocation = "";
				logger.error("display AHL postMethod not redirect!");
				throw new WorldTracerException("unable to load ahl");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to load ahl", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to load ahl", e);
		}
		finally {
			search.releaseConnection();
		}
		//redirect to submit result page and get close signal
		if(!"".equals(newLocation)){
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "FIND AHL (FRONTEND): REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
				//System.out.println("close onHandBag result:" + responseBody);
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to load ahl", e);
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to load ahl", e);
			}
			finally {
				redirect.releaseConnection();
			}
	    }//No matching records found
		if (responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			logger.error("Not exists AHL!");
			throw new WorldTracerException("No matching record found:" + wt_id);
		} else {
			return responseBody;
		}	
	}

	private String getOhd(String ohdId) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = null;
		NameValuePair[] p1 = { new NameValuePair("_flowId", "displayonhandbagrecord-flow")};
		String newLocation = startFlow(p1, "FIND OHD", 1);

		//submit close form by post method
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		search.setQueryString(p2);
		
		search.setParameter("wtrDisplayRequest.recordReference.stationCode", ohdId.substring(0,3));
		search.setParameter("wtrDisplayRequest.recordReference.airlineCode", ohdId.substring(3,5));
		search.setParameter("wtrDisplayRequest.recordReference.recordId", ohdId.substring(5));
		search.setParameter("resultsForm.sortOption", "recordReference");
		search.setParameter("_eventId", "displayrecord");
		
		try {
			debugOut(search, "");
			client.executeMethod(search, "FIND OHD (FRONTEND): SEARCH (2)");
			if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = search.getResponseHeader("location").getValue();
			}
			else{
				newLocation = "";
				logger.error("display OHD postMethod not redirect!");
				throw new WorldTracerException("unable to find ohd");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to find ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to find ohd");
		}
		finally {
			search.releaseConnection();
		}
		//redirect to submit result page and get close signal
		if(!"".equals(newLocation)){
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "FIND OHD (FRONTEND): REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
				//System.out.println("close onHandBag result:" + responseBody);
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to find ohd");
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to find ohd");
			}
			finally {
				redirect.releaseConnection();
			}
	    }//No matching records found
		if (responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			logger.error("Not exists OHD!");
			throw new WorldTracerException("No matching record found: " + ohdId);
		} else {
			return responseBody;
		}
  }
	
	private Ahl parseWTIncident(String ahlId, String wtdata) throws WorldTracerException {
		try {
	
			Map<String, String> incidentMap = getParamsFromHtml(wtdata);

			String wt_id;

			ArrayList<Passenger> paxList = new ArrayList<Passenger>();
			// get wt_id
			if(wtdata == null) {
				throw new WorldTracerException("unable to retrieve worldtraer id, wt content is bad");
			}
			wt_id = incidentMap.get("Record_Number");
			if (wt_id == null) {
				wt_id = incidentMap.get("File_Reference_Number");
			}
			if(wt_id == null){
				throw new WorldTracerException("unable to retrieve worldtraer id, wt content is bad");
			}
			logger.debug("parsed worldtracer id, " + wt_id + " from wt response.");

			Ahl ahl = new Ahl();
			
			ahl.setAhlId(ahlId);

			// date
			String datetimestr = incidentMap.get("CreatedDate");
			if(datetimestr != null){
				//calculate created year
				Calendar today = Calendar.getInstance();
				int thisYear = 0;
				int month = 0;
				String[] months = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
				String createMonth = datetimestr.substring(2, 5);
				for(int i = 0; i < months.length; i++){
					if(months[i].equalsIgnoreCase(createMonth)){
						month = i + 1;
						break;
					}
				}
				if(month <= (today.get(Calendar.MONTH) + 1)){
					thisYear = today.get(Calendar.YEAR);
				}else{
					thisYear = today.get(Calendar.YEAR) - 1;
				}
				datetimestr = datetimestr + thisYear + "/0000"; 
				
				if (datetimestr != null) {
					
					Date thedate = ServiceUtilities.convertToDate(datetimestr, "ddMMMyyyy/HHmm", null);
					if(thedate == null) {
						throw new WorldTracerException("unable to import WT file.  Unable to parse create date");
					}
					Calendar date = new GregorianCalendar();
					date.setTime(thedate);
					ahl.setCreateDate(date);
				} else {
					throw new WorldTracerException("unable to import WT file.  Unable to parse create date");
				}
			}
			// passenger
			String lname = null, salu;
			
			
			Passenger pa = new Passenger();
			
			Address addr = new Address();
			String[] lnames = null;
			if(incidentMap.get("Passenger_Identification.Names") != null){
				lnames = incidentMap.get("Passenger_Identification.Names").split("//");
				if(lnames.length > 0) {
					pa.setLastname(limitStringLength(lnames[0].split(" ")[0], MAX_NAME_LENGTH));
				}
			}
			String[] initials = null;
			if(incidentMap.get("Passenger_Identification.Initials") != null){
				initials = incidentMap.get("Passenger_Identification.Initials").split("//");
				if(initials.length > 0) {
					pa.setMiddlename(limitStringLength(initials[0], MAX_NAME_LENGTH));
				}
			}
			pa.setSalutation(0);
			salu = incidentMap.get("Passenger_Identification.Title");
			if (salu != null) {
				if (salu.equals("DR"))
					pa.setSalutation(1);
				else if (salu.equals("MR"))
					pa.setSalutation(2);
				else if (salu.equals("MS"))
					pa.setSalutation(3);
				else if (salu.equals("MISS"))
					pa.setSalutation(4);
				else if (salu.equals("MRS")) pa.setSalutation(5);
			}
			// address
			String street1=null, street2=null, state=null, zip = null, country=null, hphone=null, wphone=null, mphone1=null, mphone2=null;
			if(incidentMap.get("Permanent_Contact_Information.Address") != null){
				String[] streets = incidentMap.get("Permanent_Contact_Information.Address").split("//");
				if(streets.length > 0) street1 = streets[0];
				if(streets.length > 1){
					street2 = streets[1];
				}
				
			}
			if(incidentMap.get("Delivery_Address.State") != null){
				state = incidentMap.get("Delivery_Address.State");
			}
			if(incidentMap.get("Delivery_Address.Zip_Code") != null){
				zip = incidentMap.get("Delivery_Address.Zip_Code");
			}
			if(incidentMap.get("Delivery_Address.Country_Code") != null){
				country = incidentMap.get("Delivery_Address.Country_Code");
				if (country != null && country.length() > 2) 
					country = country.substring(0, 2);
			}
			if(incidentMap.get("Permanent_Contact_Information.Home/Business_Phone_Number") != null){
				String[] hphones = incidentMap.get("Permanent_Contact_Information.Home/Business_Phone_Number").split("//");
				if(hphones.length > 0) hphone = hphones[0];
				if(hphones.length > 1)
					wphone = hphones[1];
			}
			if(incidentMap.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number") != null){
				String[] mphones = incidentMap.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number").split("//");
				if(mphones.length > 0) mphone1 = mphones[0];
				if(mphones.length > 1){
					mphone2 = mphones[1];
				}
			}
			String fax1=null, fax2=null, email=null;
			if(incidentMap.get("Electronic_Contact_Information.Fax") != null){
				String[] faxes = incidentMap.get("Electronic_Contact_Information.Fax").split("//");
				if(faxes.length > 0) fax1 = faxes[0];
				if(faxes.length > 1){
					fax2 = faxes[1];
				}
			}
			if(incidentMap.get("Electronic_Contact_Information.Email_Address") != null){
				String[] emails = incidentMap.get("Electronic_Contact_Information.Email_Address").split("//");
				if (emails.length > 0) email = emails[0];
			}
			
			addr.setAddress1(limitStringLength(street1, MAX_ADDRESS_LINE));
			addr.setState(state);
			addr.setZip(limitStringLength(zip, 9));
			addr.setHomePhone(limitStringLength(hphone, MAX_PHONE_LENGTH));
			addr.setWorkPhone(limitStringLength(wphone, MAX_PHONE_LENGTH));
			addr.setMobilePhone(limitStringLength(mphone1, MAX_PHONE_LENGTH));
			addr.setAltPhone(limitStringLength(fax1, MAX_PHONE_LENGTH));
			addr.setEmailAddress(limitStringLength(email, 100));
			addr.setCountryCode(country);
			
			pa.setAddress(addr);

			paxList.add(pa);

			// passenger 2
			pa = new Passenger();
			if(lnames != null && lnames.length > 1){
				lname = lnames[1].split(" ")[0];
				// has pass 2

				addr = new Address();
				addr.setAddress1(limitStringLength(street2, MAX_ADDRESS_LINE));
				addr.setMobilePhone(limitStringLength(mphone2, MAX_PHONE_LENGTH));
				addr.setAltPhone(limitStringLength(fax2, MAX_PHONE_LENGTH));
				pa.setLastname(limitStringLength(lname, MAX_NAME_LENGTH));
				if(initials != null && initials.length > 1){
					pa.setMiddlename(limitStringLength(initials[1], MAX_NAME_LENGTH));
				}
				paxList.add(pa);
			}
			// passenger 3
			pa = new Passenger();
			if(lnames != null && lnames.length > 2){
				// has pass 3
				lname = lnames[2].split(" ")[0];
				
				addr = new Address();
				pa.setAddress(addr);
				pa.setLastname(limitStringLength(lname, MAX_NAME_LENGTH));
				if(initials != null && initials.length > 2){
					pa.setMiddlename(limitStringLength(initials[2], MAX_NAME_LENGTH));
				}
				paxList.add(pa);
			}
			
			Passenger[] passengers = paxList.toArray(new Passenger[paxList.size()]);
			ahl.setPax(passengers);
			
			// bags
			Item item = null;
			Content ii = null;
			ArrayList<Item> itemlist = new ArrayList<Item>();
			ArrayList<Content> ii_set = null;
			
			String bagtag,color,type=null;
			String xdesc1 = "X",xdesc2 = "X",xdesc3 = "X";
			String cstr = null, ccat = null, cdesc = null;
			int numbags = 0;
			for(Map.Entry<String, String> entry : incidentMap.entrySet()){
				String key = entry.getKey();
				Pattern p = Pattern.compile("Bag_([0123456789]+)", Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(key);
				if(m.find()){
					int tempNum = Integer.parseInt(m.group(1));
					if(tempNum > numbags){
						numbags = tempNum;
					}
				}
			}
			if (numbags > 0) {
				ahl.setNumberBagsChecked(numbags);
			}
			
			ArrayList<ClaimCheck> hashbagTag = new ArrayList<ClaimCheck>();
			for (int i=1; i<=numbags; i++) {
				// create a new bag
				item = new Item();
				bagtag = incidentMap.get("Bag_"+ i +"_details.Tag_#");
				if(bagtag != null && bagtag.length() > 1){
					bagtag = bagtag.replace(" ", "");
					ClaimCheck ic = new ClaimCheck();
					ic.setTagNumber((limitStringLength(bagtag, 13)));
					hashbagTag.add(ic);
				}
				color = incidentMap.get("Bag_"+ i +"_details.Color_Type");
				if (color != null) {
					color = color.replace(" ", "");
					if (color.length() == 7) {
						type = color.substring(2,4);
						xdesc1 = color.substring(4,5);
						xdesc2 = color.substring(5,6);
						xdesc3 = color.substring(6);
						color = color.substring(0,2);
					} else if (color.length() == 8) {
						type = color.substring(3,5);
						xdesc1 = color.substring(5,6);
						xdesc2 = color.substring(6,7);
						xdesc3 = color.substring(7);
						color = color.substring(0,3);
					}
				}


				String brand = incidentMap.get("Bag_"+ i +"_details.Brand_Information");
				if(brand != null){
					item.setManufacturer(limitStringLength(brand, 100));
				}
				
				
				ii_set = new ArrayList<Content>();
				
				String[] cstrs = null;
				if(incidentMap.get("Bag_"+ i +"_details.Bag_Contents") != null){
					cstrs = incidentMap.get("Bag_"+ i +"_details.Bag_Contents").split("//");
					cdesc = null;
					
					for(int j = 0; j < cstrs.length; j++){
						// new content, content 1
						ii = new Content();
						cstr = cstrs[j];
						// first get category
						if (cstr != null) {
							ccat = cstr.substring(0, cstr.indexOf(" "));
							cdesc = cstr.substring(cstr.indexOf(" ") + 1);
						}
						if (ccat != null) {
							ii.setCategory(ccat);
						}
						
						// if wt category is not found, then keep the category in the string
						if (cdesc != null && cdesc.length() > 0) {
							ii.setCategory(ccat);
							ii.setDescription(cdesc);
							ii_set.add(ii);
						}
					}
				}
				//item.setClaimchecknum(bagtag);
				item.setColor(color);
				item.setType(type);
				item.setDesc1(xdesc1);
				item.setDesc2(xdesc2);
				item.setDesc3(xdesc3);
				
				Content[] contents = ii_set.toArray(new Content[ii_set.size()]);
				item.setContent(contents);
				
				
				item.setBagNumber(i-1);
				itemlist.add(item);
			}
			
			Item[] items = itemlist.toArray(new Item[itemlist.size()]);
			ahl.setItem(items);
			
			ClaimCheck[] claimchecks = hashbagTag.toArray(new ClaimCheck[hashbagTag.size()]);
			ahl.setClaimCheck(claimchecks);

			
			// routing and itinerary
			String flightcomp="", flightnum="", tempfdate = "";
			Date flightdate=null;
			String rt = incidentMap.get("FLIGHTS_INFORMATION.Route");
			if(rt != null && rt.length() > 1){
				rt = rt.replace(" ", "");
			}
			String allFlight = incidentMap.get("FLIGHTS_INFORMATION.Flight");
			String[] flights = null;
			if(allFlight != null && allFlight.length() > 1){
				allFlight = allFlight.replace(" ", "");
				flights = allFlight.split("//");
			}
			
			/**
			 * 
			 * multiple flight
			 */
			StringTokenizer st = null;
			ArrayList<String> fc_arr = new ArrayList<String>();
			ArrayList<String> fn_arr = new ArrayList<String>();
			ArrayList<Date> fd_arr = new ArrayList<Date>();
			if(flights != null){
				for(int j = 0; j < flights.length; j++){
					String flight = flights[j];
					String[] fli = flight.split("/");
					if(fli.length > 0) flightcomp = fli[0];
					if(fli.length > 1) flightnum = fli[1];
					if(fli.length > 2) tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = ahl.getCreateDate();
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = ServiceUtilities.convertToDate(tempfdate, "ddMMMyyyy", null);
					}
					fc_arr.add(flightcomp);
					fn_arr.add(flightnum);
					fd_arr.add(flightdate);
				}
			}
			//start  bag routing flight
			String bagAllFlights = incidentMap.get("Bag_Routing.Flight");
			String[] bagFlights = null;
			if(bagAllFlights != null && bagAllFlights.length() > 1){
				bagAllFlights = bagAllFlights.replace(" ", "");
				bagFlights = bagAllFlights.split("//");
			}
			//multi bag flight
			ArrayList<String> bagFlightCom_arr = new ArrayList<String>();
			ArrayList<String> bagFlightNum_arr = new ArrayList<String>();
			ArrayList<Date> bagFlightDate_arr = new ArrayList<Date>();
			if(bagFlights != null){
				for(int j = 0; j < bagFlights.length; j++){
					String flight = bagFlights[j];
					String[] fli = flight.split("/");
					if(fli.length > 0) flightcomp = fli[0];
					if(fli.length > 1) flightnum = fli[1];
					if(fli.length > 2) tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = ahl.getCreateDate();
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = ServiceUtilities.convertToDate(tempfdate, "ddMMMyyyy", null);
					}
					bagFlightCom_arr.add(flightcomp);
					bagFlightNum_arr.add(flightnum);
					bagFlightDate_arr.add(flightdate);
				}
			}
			//end bag flight
				
			st = new StringTokenizer(rt,"/");
			Itinerary iti = null;
			Itinerary itiBag = null;
			
			ArrayList<Itinerary> paxItinerary = new ArrayList<Itinerary>();
			ArrayList<Itinerary> bagItinerary = new ArrayList<Itinerary>();
			
			int i = 0;
			String from,to,nextfrom=null;
			while (st.hasMoreTokens()) {
				from=null; to=null;
				//iti for passenger flight
				iti = new Itinerary();
				
				//itiBag for bag flight
				itiBag = new Itinerary();
				
				
				if (i == 0) {
					// first set
					from = st.nextToken();
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;	// the from for next routing
					}
					//set passenger flight
					iti.setDepartureCity(limitStringLength(from, 3));
					iti.setArrivalCity(to);
					iti.setLegfrom_type(i+1);
					iti.setLegto_type(i+2);
					if (fn_arr.size() > i) iti.setFlightNumber((String)fn_arr.get(i));
					if (fc_arr.size() > i) iti.setAirline((String)fc_arr.get(i));
					if (fd_arr.size() > i) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime((Date)fd_arr.get(i));
						iti.setFlightDate(cal);
					}
					

					//set bag flight
					itiBag.setDepartureCity(limitStringLength(from, 3));
					itiBag.setArrivalCity(limitStringLength(to, 3));
					itiBag.setLegfrom_type(i+1);
					itiBag.setLegto_type(i+2);
					if (bagFlightCom_arr.size() > i) itiBag.setAirline(limitStringLength((String)bagFlightCom_arr.get(i), 3));
					if (bagFlightNum_arr.size() > i) itiBag.setFlightNumber(limitStringLength((String)bagFlightNum_arr.get(i), 4));
					if (bagFlightDate_arr.size() > i) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime((Date)bagFlightDate_arr.get(i));
						itiBag.setFlightDate(cal);
					}
					
				} else {
					from = nextfrom;
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;
					} else {
						break;
					}
					//set passenger flight
					iti.setDepartureCity(limitStringLength(from, 3));
					iti.setArrivalCity(limitStringLength(to, 3));
					iti.setLegfrom_type(i+1);
					iti.setLegto_type(i+2);
					if (fn_arr.size() > i) iti.setFlightNumber(limitStringLength((String)fn_arr.get(i), 4));
					if (fc_arr.size() > i) iti.setAirline(limitStringLength((String)fc_arr.get(i), 3));
					
					if (fd_arr.size() > i) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime((Date)fd_arr.get(i));
						iti.setFlightDate(cal);
					}
					
					//set bag flight
					itiBag.setDepartureCity(from);
					itiBag.setArrivalCity(to);
					itiBag.setLegfrom_type(i+1);
					itiBag.setLegto_type(i+2);
					if (bagFlightCom_arr.size() > i) itiBag.setAirline(limitStringLength((String)bagFlightCom_arr.get(i), 3));
					if (bagFlightNum_arr.size() > i) itiBag.setFlightNumber(limitStringLength((String)bagFlightNum_arr.get(i), 4));
					
					
					if (bagFlightDate_arr.size() > i) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime((Date)bagFlightDate_arr.get(i));
						itiBag.setFlightDate(cal);
					}

				}
				paxItinerary.add(iti);
				bagItinerary.add(itiBag);
				i++;
			}
			
			Itinerary[] paxItin = paxItinerary.toArray(new Itinerary[paxItinerary.size()]);
			ahl.setPaxItinerary(paxItin);

			Itinerary[] bagItin = bagItinerary.toArray(new Itinerary[bagItinerary.size()]);
			ahl.setBagItinerary(bagItin);
			

			return ahl;
		} catch (WorldTracerException e) {
			throw e;
		}
		catch (Exception e) {
			throw new WorldTracerException("unknown error importing WT File", e);
		}
  }
	
	public static Map<String, String> getParamsFromHtml(String htmlBody){
		if(htmlBody == null){
			return null;
		}
		Pattern input_patt = Pattern.compile("<SPAN.*>([^<>]+)[^<>\\[\\]]*<\\/SPAN>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = input_patt.matcher(htmlBody);
		Map<String, String> paramMap = new HashMap<String, String>();
		if (m.find()) {
			String idDate = m.group(1);
			idDate = idDate.replace("&nbsp;", "");
			int index = idDate.indexOf("(");
			paramMap.put("CreatedDate", idDate.substring(index + 1, index + 6));
		}
		input_patt = Pattern.compile("<table id=\"tab_div\"[^<>]*>(.+?)<\\/table>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		m = input_patt.matcher(htmlBody);
		String temp = "";
		if(m.find()){
			htmlBody = m.group(1);
			input_patt = Pattern.compile("<tr><td[^<>]*>(.*?)</td>(.*?)<\\/tr>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = input_patt.matcher(htmlBody);
			String suffix = "";
			while(m.find()){
				String title = m.group(1).trim().replace(" ", "_");
				//String value = m.group(2).replace("<br/>", "//").replace("<BR/>", "//");
				String value = m.group(2).replaceAll("<br/>|<BR/>|<br>|<BR>|<br />|<BR />", "//");
				if(value == null || value.equals("")){
					if(Character.isDigit(title.charAt(0))){
						suffix = "";
						temp = "";
						continue;
					}
					suffix = title + ".";
				}
				else{
					Pattern pat = Pattern.compile("<span[^<>]*?>([^<>]*?)</span>", Pattern.CASE_INSENSITIVE);
					Matcher mat = pat.matcher(value);
					if(mat.find()){
						if((title == null || title.startsWith("<td")) && paramMap.get(temp) != null){
							String newValue = paramMap.get(temp) + "//" + mat.group(1).replace("&nbsp;", " ").trim();
							paramMap.put(temp, newValue);
							
						}else{
							temp = suffix + title;
							paramMap.put(temp, mat.group(1).replace("&nbsp;", " ").trim());
						}
					}
				}
			}
		}
		return paramMap;
	}
	

	private static String limitStringLength(String inputString, int maxLength) {
		if(inputString == null) return null;
		
		if(inputString.length() > maxLength) return inputString.substring(0, maxLength);
		
		return inputString;
	}
	
	private Ohd parseWTOhd(String ohdId, String wtdata) throws WorldTracerException {
		try {
			if (wtdata == null) {
				throw new WorldTracerException("no OHD data to parse");
			}

			// first figure out if this ohd is new or already existing through
			// WT_column
			Ohd ohd = new Ohd();
			Map<String, String> ohdMap = getParamsFromHtml(wtdata);

			String wt_id;
			wt_id = ohdMap.get("Record_Number");
			if (wt_id == null) {
				wt_id = ohdMap.get("File_Reference_Number");
			}
			if (wt_id == null) {
				throw new WorldTracerException("unable to import OHD.  no wt_id was parsed");
			}
			logger.debug("parsed worldtracer id, " + wt_id
					+ " from wt response.");

			ohd.setOhdId(wt_id);

			// found station and holding station are the wt_id 0,3 characters
			String thes = wt_id.substring(0, 3);
			String thec = wt_id.substring(3, 5);

			// date
			String datetimestr = ohdMap.get("CreatedDate");
			if (datetimestr != null) {
				// calculate created year
				Calendar today = Calendar.getInstance();
				int thisYear = 0;
				int month = 0;
				String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN",
						"JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
				String createMonth = datetimestr.substring(2, 5);
				for (int i = 0; i < months.length; i++) {
					if (months[i].equalsIgnoreCase(createMonth)) {
						month = i + 1;
						break;
					}
				}
				if (month <= (today.get(Calendar.MONTH) + 1)) {
					thisYear = today.get(Calendar.YEAR);
				} else {
					thisYear = today.get(Calendar.YEAR) - 1;
				}
				datetimestr = datetimestr + thisYear + "/0000";

				if (datetimestr != null) {
					Date thedate = ServiceUtilities.convertToDate(datetimestr,
							"ddMMMyyyy/HHmm", null);
					if (thedate == null) {
						throw new WorldTracerException(
								"unable to import WT OHD.  Unable to parse create date");
					}
					
					Calendar date = new GregorianCalendar();
					date.setTime(thedate);
					ohd.setCreateDate(date);
					

				} else {
					throw new WorldTracerException(
							"unable to import WT OHD.  Unable to parse create date");
				}
			}
			/*****
			 * missing: email field, frequent flyer number field
			 */
			ArrayList<Address> addrhash = new ArrayList<Address>();
			ArrayList<Passenger> pahash = new ArrayList<Passenger>();
			// passenger
			String lname;
			Passenger pa = new Passenger();
			Address addr = new Address();

			String[] lnames = null;
			if (ohdMap.get("Passenger_Identification.Names") != null) {
				lnames = ohdMap.get("Passenger_Identification.Names").split(
						"//");
				if(lnames.length > 0) {
					pa.setLastname(limitStringLength(lnames[0].split(" ")[0], 20));
				}
			}
			String[] initials = null;
			if (ohdMap.get("Passenger_Identification.Initials") != null) {
				initials = ohdMap.get("Passenger_Identification.Initials")
						.split("//");
				if(initials.length > 0)
					pa.setMiddlename(limitStringLength(initials[0], 20));
			}
			String paaddr1 = null, paaddr2 = null, fax = null, hphone = null, wphone = null, mphone = null;
			if (ohdMap.get("Permanent_Contact_Information.Address") != null) {
				String[] streets = ohdMap.get(
						"Permanent_Contact_Information.Address").split("//");
				if (streets.length > 0) {
					paaddr1 = streets[0];
				}
				if (streets.length > 1) {
					paaddr2 = streets[1];
				}
			}
			if (ohdMap.get("Common_information.Address_on_bag") != null) {
				String[] streets = ohdMap.get(
						"Common_information.Address_on_bag").split("//");
				if (streets.length > 0) {
					paaddr1 = streets[0];
				}
				if (streets.length > 1) {
					paaddr2 = streets[1];
				}
			}
			if (ohdMap
					.get("Permanent_Contact_Information.Home/Business_Phone_Number") != null) {
				String[] hphones = ohdMap
						.get(
								"Permanent_Contact_Information.Home/Business_Phone_Number")
						.split("//");
				if (hphones.length > 0) {
					hphone = hphones[0];
				}
				if (hphones.length > 1)
					wphone = hphones[1];
			} else {
				if (ohdMap.get("Common_information.Phone") != null) {
					String[] hphones = ohdMap.get("Common_information.Phone")
							.split("//");
					if (hphones.length > 0) {
						hphone = hphones[0];
					}
					if (hphones.length > 1)
						wphone = hphones[1];
				}
			}
			if (ohdMap
					.get("Permanent_Contact_Information.Cell/Mobile_Phone_Number") != null) {
				String[] mphones = ohdMap
						.get(
								"Permanent_Contact_Information.Cell/Mobile_Phone_Number")
						.split("//");
				if (mphones.length > 0) {
					mphone = mphones[0];
				}
			}
			if (ohdMap.get("Electronic_Contact_Information.Fax") != null) {
				String[] faxes = ohdMap.get(
						"Electronic_Contact_Information.Fax").split("//");
				if(faxes.length > 0)
					fax = faxes[0];
			}

			addr.setAddress1(limitStringLength(paaddr1, 50));
			addr.setAddress2(limitStringLength(paaddr2, 50));
			addr.setHomePhone(limitStringLength(hphone, 50));
			addr.setWorkPhone(limitStringLength(wphone, 50));
			addr.setMobilePhone(limitStringLength(mphone, 50));
			addr.setAltPhone(limitStringLength(fax, 50));

			pa.setAddress(addr);

			pahash.add(pa);

			// passenger 2
			if (lnames != null && lnames.length > 1) {
				pa = new Passenger();
				lname = lnames[1].split(" ")[0];
				// has pass 2

				addr = new Address();
				pa.setAddress(addr);
				pa.setLastname(limitStringLength(lname, 20));
				if (initials != null && initials.length > 1) {
					pa.setMiddlename(limitStringLength(initials[1], 20));
				}

				pahash.add(pa);
			}
			// passenger 3
			if (lnames != null && lnames.length > 2) {
				pa = new Passenger();
				lname = lnames[2].split(" ")[0];
				// has pass 3
				addrhash = new ArrayList<Address>();
				addr = new Address();


				pa.setAddress(addr);
				pa.setLastname(limitStringLength(lname, 20));
				if (initials != null && initials.length > 2) {
					pa.setMiddlename(limitStringLength(initials[2], 20));
				}
				
				pahash.add(pa);
			}

			Passenger[] paxArray =  pahash.toArray(new Passenger[pahash.size()]);
			ohd.setPax(paxArray);

			// bags
			Content ii = null;
			ArrayList<Content> ii_set = null;

			String bagtag, color, type = null;
			String xdesc1 = "X", xdesc2 = "X", xdesc3 = "X";
			String cstr = null, ccat = null, cdesc = null;
			

			bagtag = ohdMap.get("Bag_1_details.Tag_#");
			if (bagtag != null)
				bagtag = bagtag.replace(" ", "");
			color = ohdMap.get("Bag_1_details.Color_Type");
			if (color != null) {
				color = color.replace(" ", "");
				if (color.length() == 7) {
					type = color.substring(2, 4);
					xdesc1 = color.substring(4, 5);
					xdesc2 = color.substring(5, 6);
					xdesc3 = color.substring(6);
					color = color.substring(0, 2);
				} else if (color.length() == 8) {
					type = color.substring(3, 5);
					xdesc1 = color.substring(5, 6);
					xdesc2 = color.substring(6, 7);
					xdesc3 = color.substring(7);
					color = color.substring(0, 3);
				}
			}
			// manufacturer
			
			Item item = new Item();
			ohd.setItem(item);
			item.setManufacturer(limitStringLength(ohdMap
					.get("Bag_1_details.Brand_Information"), 100));


			// content
			ii_set = new ArrayList<Content>();
			String[] cstrs = null;
			if (ohdMap.get("Bag_1_details.Bag_Contents") != null) {
				cstrs = ohdMap.get("Bag_1_details.Bag_Contents").split("//");
				cdesc = null;
				
				for (int j = 0; j < cstrs.length; j++) {
					// new content, content 1
					ii = new Content();
					cstr = cstrs[j];
					// first get category
					if (cstr != null) {
						ccat = cstr.substring(0, cstr.indexOf(" "));
						cdesc = cstr.substring(cstr.indexOf(" ") + 1);
					}

					if (cdesc != null && cdesc.length() > 0) {
						ii.setCategory(ccat);
						ii.setDescription(cdesc);
						ii_set.add(ii);
					}
				}
			}

			ClaimCheck cc = new ClaimCheck();
			cc.setTagNumber(limitStringLength(bagtag, 13));
			ohd.setClaimCheck(cc);
			
			item.setColor(limitStringLength(color, 2));
			item.setType(limitStringLength(type, 2));
			item.setDesc1(xdesc1);
			item.setDesc2(xdesc2);
			item.setDesc3(xdesc3);
			//(ii_set)
			Content[] content = ii_set.toArray(new Content[ii_set.size()]);
			item.setContent(content);

			// routing and itinerary
			String flightcomp = "", flightnum = "", tempfdate = "";
			Date flightdate = null;
			String rt = ohdMap.get("Flights_and_Routings.Route");
			if (rt != null && rt.length() > 1) {
				rt = rt.replace(" ", "");
			}
			String allFlight = ohdMap.get("Flights_and_Routings.Flight");
			String[] flights = null;
			if (allFlight != null && allFlight.length() > 1) {
				flights = allFlight.replace(" ", "").split("//");
			}

			/**
			 * 
			 * multiple flight
			 */

			StringTokenizer st = null;
			ArrayList<String> fn_arr = new ArrayList<String>();
			ArrayList<String> fc_arr = new ArrayList<String>();
			ArrayList<Date> fd_arr = new ArrayList<Date>();

			if (flights != null) {
				for (int j = 0; j < flights.length; j++) {
					String flight = flights[j];
					String[] fli = flight.split("/");
					
					if(fli.length > 0) flightcomp = fli[0];
					if(fli.length > 1) flightnum = fli[1];
					if(fli.length > 2) tempfdate = fli[2];
					if (tempfdate != null) {
						Calendar gc = ohd.getCreateDate();
						tempfdate += gc.get(GregorianCalendar.YEAR);
						flightdate = ServiceUtilities.convertToDate(tempfdate,
								"ddMMMyyyy", null);
					}
					fc_arr.add(flightcomp);
					fn_arr.add(flightnum);
					fd_arr.add(flightdate);
				}
			}

			st = new StringTokenizer(rt, "/");
			Itinerary oi = null;
			
			ArrayList<Itinerary> bagItin = new ArrayList<Itinerary>();
			
			int i = 0;
			String from, to, nextfrom = null;
			while (st.hasMoreTokens()) {
				from = null;
				to = null;
				oi = new Itinerary();
				if (i == 0) {
					// first set
					from = st.nextToken();
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to; // the from for next routing
					}
					oi.setDepartureCity(limitStringLength(from, 3));
					oi.setArrivalCity(limitStringLength(to, 3));
					if (fn_arr.size() > i)
						oi.setFlightNumber(limitStringLength((String) fn_arr.get(i), 4));
					if (fc_arr.size() > i)
						oi.setAirline(limitStringLength((String) fc_arr.get(i), 3));
					if (fd_arr.size() > i) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime((Date) fd_arr.get(i));
						oi.setFlightDate(cal);
					}
					
				} else {
					from = nextfrom;
					if (st.hasMoreTokens()) {
						to = st.nextToken();
						nextfrom = to;
					} else {
						break;
					}
					oi.setDepartureCity(limitStringLength(from, 3));
					oi.setArrivalCity(limitStringLength(to, 3));
					if (fn_arr.size() > i)
						oi.setFlightNumber(limitStringLength((String) fn_arr.get(i), 4));
					if (fc_arr.size() > i)
						oi.setAirline(limitStringLength((String) fc_arr.get(i), 2));
					if (fd_arr.size() > i) {
						GregorianCalendar cal = new GregorianCalendar();
						cal.setTime((Date) fd_arr.get(i));
						oi.setFlightDate(cal);
					}
					
				}
				bagItin.add(oi);
				i++;
			}
			
			Itinerary[] itin = bagItin.toArray(new Itinerary[bagItin.size()]);
			ohd.setBagItinerary(itin);

			ohd.setStorageLocation(ohdMap.get("Common_information.Storage_Location"));

			return ohd;
		} catch (Exception e) {
			throw new WorldTracerException("unexpected error importing WT OHD",
					e);
		}
  }
	
	public static void keepAlive(WorldTracerHttpClient client) {
		NameValuePair[] p1 = { new NameValuePair("_flowId", "displayonhandbagrecord-flow")};
		GetMethod method = new GetMethod(WTRWEB_FLOW_URL);
		method.setQueryString(p1);
		method.setFollowRedirects(false);
		try {
			client.executeMethod(method, "KEEP-ALIVE");
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void amendAhl(WorldTracerActionDTO dto, Ahl data,
      WorldTracerResponse response) throws WorldTracerException, WorldTracerAlreadyClosedException, NotLoggedIntoWorldTracerException {
		
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createAhlFieldMap(data);
		String wt_ahl_id = data.getAhlId();
		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate incident mapping");
		}

		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.AMEND_AHL);
		//start
		NameValuePair[] p1 = { new NameValuePair("_flowId", "editdelayedbagrecord-flow")};
		String newLocation = startFlow(p1, "AMEND AHL", 1);
		
		//flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//if exists this wt_id
		PostMethod getAhl = new PostMethod(newLocation);
		getAhl.setParameter("_flowExecutionKey", flowKey);
		getAhl.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_ahl_id.substring(0, 3));
		getAhl.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_ahl_id.substring(3, 5));
		getAhl.setParameter("wtrDisplayRequest.recordReference.recordId", wt_ahl_id.substring(5));
		getAhl.setParameter("resultsForm.sortOption", "recordReference");
		getAhl.setParameter("_eventId", "update");
		try {
			debugOut(getAhl, "amendAhl");
			client.executeMethodWithPause(getAhl, "AMEND AHL: GET AHL (2)");
			if(getAhl.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getAhl.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getAhl.getResponseHeader("location").getValue();
			}else{
				logger.error("Get amend ahl details error!");
				throw new WorldTracerException("unable to amend incident");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident");
		}
		finally {
			getAhl.releaseConnection();
		}
		GetMethod getWtDetails = new GetMethod(newLocation);
		try {
			logger.debug(getWtDetails.getQueryString());
			client.executeMethodWithPause(getWtDetails, "AMEND AHL: GET WT DETAILS (3)");
			if(getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				logger.error("getWtDetails method redirect again!");
				throw new WorldTracerException("unable to amend incident");
			}else{
				responseBody = getStringFromInputStream(getWtDetails.getResponseBodyAsStream());
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident", e);
		}
		finally {
			getWtDetails.releaseConnection();
		}
		if(responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			throw new WorldTracerException("wt_ahl_id not exists!");
		}
		Matcher m = FLOW_PATTERN.matcher(newLocation);
		if(m.find()) {
			flowKey = m.group(1).trim();
		}
		//add params to amend
		PostMethod amendMethod = new PostMethod(WTRWEB_FLOW_URL);
		//add normal params
		List<String> bagsList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CT);  //for bagTag.airlineCode and bagTag.tagNum
		DefaultWorldTracerService.WorldTracerField[] fields = DefaultWorldTracerService.WorldTracerField.values();
		for(int k=0; k<fields.length; k++){
			List<String> fieldList = fieldMap.get(fields[k]);
			//add bag type
			if(fields[k] == DefaultWorldTracerService.WorldTracerField.CT){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						String bagFace = fieldList.get(i);
						String colorType = bagFace.substring(0, 2);
						String bagType = bagFace.substring(2, 4);
						String colorDesc = bagFace.substring(4, 7);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].colorType.colorCode", colorType);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].colorType.typeCode", bagType);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].colorType.descriptionCodes", colorDesc);
					}
				}
				for(int j=i; j<10; j++){
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ j +"].colorType.colorCode", "");
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ j +"].colorType.typeCode", "");
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ j +"].colorType.descriptionCodes", "");
				}
			}
			//add passenger itinerary
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.FD){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						String paxIntinery = fieldList.get(i);
						String[] inti = paxIntinery.split("/");
						String airlineCode = inti[0].substring(0, 2);
						String flightNumber = inti[0].substring(2);
						String flightDate = inti[1];
						amendMethod.setParameter("delayedBagRecord.passenger.paxFlights["+ i +"].airlineCode", airlineCode);
						amendMethod.setParameter("delayedBagRecord.passenger.paxFlights["+ i +"].flightNumber", flightNumber);
						amendMethod.setParameter("delayedBagRecord.passenger.paxFlights["+ i +"].flightDate", flightDate);
					}
				}
				for(int j=i; j<4; j++){
					amendMethod.setParameter("delayedBagRecord.passenger.paxFlights["+ j +"].airlineCode", "");
					amendMethod.setParameter("delayedBagRecord.passenger.paxFlights["+ j +"].flightNumber", "");
					amendMethod.setParameter("delayedBagRecord.passenger.paxFlights["+ j +"].flightDate", "");
				}
			}
			//add passenger initials
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.IT){
				if(fieldList != null){
					for(int i = 0; i < fieldList.size(); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.initials[" + i +"]", fieldList.get(i));
					}
				}
			}
			//add passenger routing
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.RT){
				int originCount = 2;
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						String station = fieldList.get(i);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.route.tracingStations["+ i +"]", station);
						if(i <= 1){
							//add first routing
							amendMethod.setParameter("delayedBagRecord.delayedBagGroup.route.passengerRoute["+ i +"]", station);

						}else{
							//add other routing								
							amendMethod.setParameter("station__origin__field0" + originCount, fieldList.get(i-1));
							amendMethod.setParameter("delayedBagRecord.delayedBagGroup.route.passengerRoute["+ originCount +"]", station);
							originCount++;
						}							
					}
				}
				//delete other no value fields
				for(int j=originCount; j<5; j++){
					amendMethod.setParameter("station__origin__field0" + j, "");
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.route.passengerRoute["+ j +"]", "");
				}
				for(int t=i; t<15; t++){
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.route.tracingStations["+ t +"]", "");
				}
			}
			//add passenger names
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.NM){
				if(fieldList != null){
					for(int i = 0; i < fieldList.size(); i++){
						String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(i));
						amendMethod.setParameter("delayedBagRecord.passenger.names[" + i +"]", name);
					}
				}
			}
			
			else if (fields[k] == DefaultWorldTracerService.WorldTracerField.FL) {
				if (fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL) != null && fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL).get(0) != null) {
					logger.info("FL: " + fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL).get(0));
					amendMethod.setParameter("quickEntryRecord.passenger.frequentFlyerId", fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL).get(0));
					amendMethod.setParameter("delayedBagRecord.passenger.frequentFlyerId", fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL).get(0));
				}
			}
			
			else if (fields[k] == DefaultWorldTracerService.WorldTracerField.PS) {
				if (fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS) != null && fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS).get(0) != null) {
					logger.info("PS: " + fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS).get(0));
					
					amendMethod.setParameter("delayedBagRecord.passenger.classStatus", fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS).get(0));
					amendMethod.setParameter("quickEntryRecord.passenger.classStatus", fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS).get(0));					
				}
			}
			
			//add first passenger title
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.PT){
				if(fieldList != null){
					String[] titles = fieldList.get(0).split(". ");
					if(titles.length>0) {
						String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(titles[titles.length-1]);
						amendMethod.setParameter("delayedBagRecord.passenger.title", title);
					}
				}
			}
			//add passenger permanent address and country code
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.PA){
				//firstly get country code
				List<String> countryList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CO);
				//add first passenger country
				if(countryList != null){
					amendMethod.setParameter("delayedBagRecord.passenger.countryCode", countryList.get(0));
				}
				if(fieldList != null){
					for(int i = 0; i < fieldList.size() && i < 2; i++){
						String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
						amendMethod.setParameter("delayedBagRecord.passenger.permanentAddress.line" + (i+1), address.replace(".", "&#46;"));
						amendMethod.setParameter("delayedBagRecord.passenger.delivery.deliveryAddress.line"+(i+1), address.replace(".", "&#46;"));
					}
					amendMethod.setParameter("delayedBagRecord.deliveryAddressType", "PERMANENT");
				}
			} else if (fields[k] == DefaultWorldTracerService.WorldTracerField.ZIP) {
				if (fieldList != null) {
					amendMethod.setParameter("delayedBagRecord.passenger.zipCode", fieldList.get(0));
				}
			} else if (fields[k] == DefaultWorldTracerService.WorldTracerField.STATE) {
				if (fieldList != null) {
					amendMethod.setParameter("delayedBagRecord.passenger.state", fieldList.get(0));
				}
			}
			//add passenger temp address and country code
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.TA){
				//firstly get country code
				if(null == amendMethod.getParameter("delayedBagRecord.passenger.countryCode") || "".equals(amendMethod.getParameter("delayedBagRecord.passenger.countryCode").getValue().trim())){
					List<String> countryList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CO);
					//add first passenger country
					if(countryList != null){
						amendMethod.setParameter("delayedBagRecord.passenger.countryCode", countryList.get(0));
					}
				}
				if(fieldList != null){
					boolean isTemp = false;
					if(null == amendMethod.getParameter("delayedBagRecord.deliveryAddressType") || "TEMPORARY".equalsIgnoreCase(amendMethod.getParameter("delayedBagRecord.deliveryAddressType").getValue().trim())){
						isTemp = true;
						amendMethod.setParameter("delayedBagRecord.deliveryAddressType", "TEMPORARY");
					}
					for(int i = 0; i < fieldList.size() && i < 2; i++){
						String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
						amendMethod.setParameter("delayedBagRecord.passenger.tempAddress.line" + (i+1), address.replace(".", "&#46;"));
						if(isTemp){
							amendMethod.setParameter("delayedBagRecord.passenger.delivery.deliveryAddress.line"+(i+1), address.replace(".", "&#46;"));
						}
					}
				}
			}
			//add first passenger email, cannot be deleted in WorldTracer.  [ABC/D/FFS/U/FSAG/+/GASGGSDG/T/G/A/GSDG/D/DGS/D/GEGGGE]
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.EA){
				if(fieldList != null && fieldList.size() >= 1){
					String email = RULES.get(DefaultWorldTracerService.WorldTracerField.EA).formatEntry(fieldList.get(0)).length()>1?fieldList.get(0).replace("/A/", "@")
							.replace("/D/", ".").replace("/U/", "_").replace("/T/", "~").replace("/P/", "+"):"";
          amendMethod.setParameter("delayedBagRecord.passenger.email", email);
				}else{
					amendMethod.setParameter("delayedBagRecord.passenger.email", "");  //not useful, WorldTracer would restore
				}
			}
			//add passenger home/business phone, phone can not be deleted in WorldTracer
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.PN){
				List<String> bizPhones = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
				String permPhone = "";
				if(bizPhones == null && fieldList != null){
					for(int i = 0; i < (fieldList.size()<2?fieldList.size():2); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(i)));
					}
				}
				else if(fieldList == null && bizPhones != null){
					for(int i = 0; i < (bizPhones.size()<2?bizPhones.size():2); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.TP).formatEntry(bizPhones.get(i)));
					}
				}
				else if(fieldList != null && bizPhones != null){
					int max = fieldList.size() > bizPhones.size() ? fieldList.size() : bizPhones.size();
					if(max > 2)
						max = 2;
					if(fieldList != null){
						for(int i = 0; i < max; i++){
							if(fieldList.get(i)!=null)
								permPhone += fieldList.get(i) + ",";
							if(bizPhones.get(i)!=null)
								permPhone += bizPhones.get(i) + ",";
							if(permPhone.startsWith(","))
								permPhone = permPhone.substring(1);
							if(permPhone.endsWith(","))
								permPhone = permPhone.substring(0, (permPhone.length()-1));
							amendMethod.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(permPhone));
						}
					}
				}
			}
			//add passenger cellphone
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.CP){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.cellPhones[" + i + "]", RULES.get(DefaultWorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(i)));
					}
				}
				for(int j=i; j<2; j++){
					amendMethod.setParameter("delayedBagRecord.passenger.cellPhones[" + j + "]", "");
				}
			}
			//add passenger fax, fax can not be deleted in WorldTracer
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.FX){
				if(fieldList != null){
					for(int i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
						if(!fieldList.get(i).equals("")){
							amendMethod.setParameter("delayedBagRecord.passenger.fax" + (i+1), RULES.get(DefaultWorldTracerService.WorldTracerField.FX).formatEntry(fieldList.get(i)));
						}
					}
				}
			}
			//add number of passenger and booking infomation
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.NP){
				if(fieldList != null && fieldList.get(0).length() > 0){
					amendMethod.setParameter("delayedBagRecord.passenger.booking.numberOfPaxInfo", fieldList.get(0));
				}
				amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.booking.pooledTicketNumber", "0000");
				amendMethod.setParameter("_avoidBindingdelayedBagRecord.passenger.booking.group", "on");
			}
			//add bag itinerary
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.BR){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						String bagIntinery = fieldList.get(i);
						String[] inti = bagIntinery.split("/");
						String airlineCode = inti[0].substring(0, 2);
						String flightNumber = inti[0].substring(2);
						String flightDate = inti[1];
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ i +"].airlineCode", airlineCode);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ i +"].flightNumber", flightNumber);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ i +"].flightDate", flightDate);
					}
				}
				for(int j=i; j<4; j++){
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ j +"].airlineCode", "");
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ j +"].flightNumber", "");
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.bagFlights["+ j +"].flightDate", "");
				}
			}
			//add tag number of bags  [US123456, A1234567]
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.TN){
				int i = 0;
				if(fieldList != null){
					int count = fieldList.size() < bagsList.size() ? fieldList.size() : bagsList.size();
					for(i = 0; i < count; i++){
						String airtag = fieldList.get(i);
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagTag.airlineCode", airtag.substring(0,2));
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagTag.tagNumber", airtag.substring(2));

					}
				}
				for(int j=i; j<10; j++){
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ j +"].bagTag.airlineCode", "");
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ j +"].bagTag.tagNumber", "");
				}
			}
			//add brand of bags
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.BI){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].brandInfo.brandInformation", RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(i)));
					}
				}
				for(int j=i; j<10; j++){
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags[" + j + "].brandInfo.brandInformation", "");
				}
			}
			//add bags contents:  [01 BOOK/TECHNICAL BOOK.- PHOTO/VIEW PHOTOES, 02 FOOD/GOOD FOODS]
			//contents can not be deleted in WorldTracer
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.CC){
				int i = 0;
				if(fieldList != null){
					//bags number must be 1, if more would be error in WorldTracer?
					for(i = 0; i < 1; i++){
						String bagInfo = fieldList.get(i);
						String[] bagContents = bagInfo.split(".- ");
						int countPerBag = bagContents.length;
						for(int j = 0; j < countPerBag; j++){
							String contents = bagContents[j];
							if(j == 0)
								contents = contents.substring(3);
							int index = contents.indexOf("/");
							amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagContents.bagItemsList["+ j +"].category", contents.substring(0, index));
							amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagContents.bagItemsList["+ j +"].descriptionLine1", contents.substring(index+1).replace(".", "&#46;"));
							amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].bagContents.bagItemsList["+ j +"].descriptionLine2", "/");
							
						}
					}
				}
			}
			//add claim params, RL can not be deleted
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.FS){
				if(fieldList != null && fieldList.size() > 0){
					amendMethod.setParameter("delayedBagRecord.delayedBagClaim.faultStation", fieldList.get(0));
				}
			}
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.RL){
				if(fieldList != null && fieldList.size() > 0){
					amendMethod.setParameter("delayedBagRecord.delayedBagClaim.reasonForLoss", fieldList.get(0));
				}
			}
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.RC){
				if(fieldList != null && fieldList.size() > 0){
					amendMethod.setParameter("delayedBagRecord.delayedBagClaim.commentsOnLoss", fieldList.get(0).replace(".", "&#46;"));
				}
			}
		}
		amendMethod.setParameter("delayedBagRecord.recordReference.stationCode", wt_ahl_id.substring(0,3));
		amendMethod.setParameter("delayedBagRecord.recordReference.airlineCode", wt_ahl_id.substring(3,5));
		amendMethod.setParameter("delayedBagRecord.recordReference.recordId", wt_ahl_id.substring(5));
		
		amendMethod.setParameter("_eventId", "amend");
		amendMethod.setParameter("bagType", "Delayed");
		amendMethod.setParameter("_avoidBindingdelayedBagRecord.passenger.booking.group", "on");
		amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagGroup.keysCollected", "on");
		//amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagClaim.insured", "on");
		//amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagClaim.liabilityTag", "on");
		amendMethod.setParameter("_flowExecutionKey", flowKey);
		amendMethod.setParameter("flowExecutionKey_Flights", flowKey);
		amendMethod.setParameter("flowExecutionKey_Bags", flowKey);
		
		try{
			debugOut(amendMethod, "");
			client.executeMethodWithPause(amendMethod, "AMEND AHL: AMEND (4)");
			if(amendMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || amendMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = amendMethod.getResponseHeader("location").getValue();
			}else{
				logger.error("Amend ahl method not redirect!");
				throw new WorldTracerException("Amend ahl method not redirect!");
			}
		}catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident", e);
		}
		finally {
			amendMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "AMEND AHL: REDIRECT (5)");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend incident", e);
		}
		finally {
			redirect.releaseConnection();
		}
		if (responseBody.toUpperCase().contains("RECORD AMENDED SUCCESSFULLY") || responseBody.toUpperCase().contains("FILE AMENDED SUCCESSFULLY")) {
			response.setSuccess(true);
			return;
		} else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to amend AHL. See logs for details";
				logger.error("amend Ahl result:"+responseBody);
			}
			if (errorString.toUpperCase().contains("FILE CLOSED")) {
				throw new WorldTracerAlreadyClosedException(errorString);
			}
			throw new WorldTracerException(errorString);
		}
		
  }
	

	public void closeAhl(WorldTracerActionDTO dto, Ahl ahl,
      WorldTracerResponse response) throws WorldTracerException {
		
		if (ahl.getAhlId() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createCloseFieldMap(ahl);
		if(ahl.getAhlId() == null || ahl.getAhlId().trim().length() < 1){
			throw new WorldTracerException("No associated WorldTracer file");
		}
		try {
			String result = closeInc(fieldMap, ahl.getAhlId(), ahl.getStationCode());
			if(result == null){
				result = amendBeforeClose(fieldMap, ahl.getAhlId(), ahl.getStationCode());
				if(result == null){
					logger.error("amend incident error with WorldTracer");
					//throw new WorldTracerConnectionException();
				}
				result = closeInc(fieldMap, ahl.getAhlId(), ahl.getStationCode());
				if(result == null){
					logger.error("close incident error with WorldTracer");
					throw new WorldTracerConnectionException();
				}
			}
			response.setSuccess(true);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}  
  }
	
	public String amendBeforeClose(
			Map<DefaultWorldTracerService.WorldTracerField, List<String>> fieldMap,
			String wt_id, String stationCode) throws WorldTracerException{
		GetMethod startFlow = new GetMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p1 = { new NameValuePair("_flowId", "editdelayedbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethodWithPause(startFlow, "AMEND BEFORE CLOSE: START FLOW (1)");
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend", e);
		}
		finally {
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start amend_before_close_incident flow not redirect!");
			throw new WorldTracerException("unable to amend before close");
		}
		//redirect to update incident page and get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//The field starts with avoidBinding... means no change in WorldTracer. So it is not necessory to retrieve details.
		PostMethod amendMethod = new PostMethod(WTRWEB_FLOW_URL);
		//add fields no changed
		for(int j=0; j<10; j++){
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].bagTag.airlineCode", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].bagTag.tagNumber", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].colorType.typeCode", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].colorType.colorCode", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].colorType.descriptionCodes", "");
			for(int k=0; k<12; k++){
				amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].brandInfo.brandInformation", "");
				amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].bagContents.bagItemsList[" + k + "].category", "");
				amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].bagContents.bagItemsList[" + k + "].descriptionLine1", "");
				amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.delayedBags[" + j + "].bagContents.bagItemsList[" + k + "].descriptionLine2", "");
			}
		}
		for(int j=0; j<4; j++){
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.bagFlights[" + j + "].airlineCode", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.bagFlights[" + j + "].flightNumber", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.bagFlights[" + j + "].flightDate", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.paxFlights[" + j + "].airlineCode", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.paxFlights[" + j + "].flightNumber", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.paxFlights[" + j + "].flightDate", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.route.passengerRoute[" + j + "]", "");
		}
		for(int j=0; j<15; j++){
			amendMethod.setParameter("avoidBindingdelayedBagRecord.delayedBagGroup.route.tracingStations[" + j + "]", "");
		}
		for(int j=0; j<2; j++){
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.cellPhones[" + j + "]", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.permanentPhones[" + j + "]", "");
		}
		for(int j=0; j<3; j++){
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.names[" + j + "]", "");
			amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.initials[" + j + "]", "");
		}
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.email", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.title", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.state", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.countryCode", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.booking.pooledTicketNumber", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.booking.numberOfPaxInfo", "");
		amendMethod.setParameter("userLang", "1");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.deliveryAddressType", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.delivery.deliveryAddress.line1", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.delivery.deliveryAddress.line2", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.permanentAddress.line1", "");
		amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.permanentAddress.line2", "");
		//add claim params, and submit to amend
		List<String> faultStationList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FS);
		if(faultStationList != null && faultStationList.size() > 0){
			amendMethod.setParameter("delayedBagRecord.delayedBagClaim.faultStation", faultStationList.get(0));
		}
		List<String> reasonCodeList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.RL);
		if(reasonCodeList != null && reasonCodeList.size() > 0){
			amendMethod.setParameter("delayedBagRecord.delayedBagClaim.reasonForLoss", reasonCodeList.get(0));
		}
		List<String> commentList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.RC);
		if(commentList != null && commentList.size() > 0){
			amendMethod.setParameter("delayedBagRecord.delayedBagClaim.commentsOnLoss", commentList.get(0).replace(".", "&#46;"));
		}
		amendMethod.setParameter("delayedBagRecord.delayedBagClaim.paymentToPassengerList[0].costType", "X");
		amendMethod.setParameter("delayedBagRecord.delayedBagClaim.paymentToPassengerList[0].currency", "USD");
		amendMethod.setParameter("delayedBagRecord.delayedBagClaim.paymentToPassengerList[0].value", "0");
		
		amendMethod.setParameter("delayedBagRecord.recordReference.stationCode", wt_id.substring(0,3));
		amendMethod.setParameter("delayedBagRecord.recordReference.airlineCode", wt_id.substring(3,5));
		amendMethod.setParameter("delayedBagRecord.recordReference.recordId", wt_id.substring(5));
		
		amendMethod.setParameter("_eventId", "amend");
		amendMethod.setParameter("bagType", "Delayed");
		amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagClaim.insured", "on");
		amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagClaim.liabilityTag", "on");
		amendMethod.setParameter("_flowExecutionKey", flowKey);
		amendMethod.setParameter("flowExecutionKey_Claim", flowKey);
		try{
			debugOut(amendMethod, "");
			client.executeMethodWithPause(amendMethod, "AMEND BEFORE CLOSE: AMEND (2)");
			if(amendMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || amendMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = amendMethod.getResponseHeader("location").getValue();
			}else{
				logger.error("Amend method before close ahl not redirect!");
				throw new WorldTracerException("Amend method before close ahl not redirect!");
			}
		}catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend before close", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend before close", e);
		}
		finally {
			amendMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "AMEND BEFORE CLOSE: REDIRECT (3)");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend before close", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend before close", e);
		}
		finally {
			redirect.releaseConnection();
		}
		if(responseBody.toUpperCase().contains("RECORD AMENDED SUCCESSFULLY") || responseBody.toUpperCase().contains("FILE AMENDED SUCCESSFULLY")){
			return wt_id;
		}else{
			logger.error("Amend before close incident result:" + responseBody);
			logger.error("amend before close incident failed ");
			return null;
		}
	}
	
	public String closeInc(
			Map<DefaultWorldTracerService.WorldTracerField, List<String>> fieldMap,
			String wt_id, String stationCode) throws WorldTracerException, NotLoggedIntoWorldTracerException{
		String responseBody = null;
		
		NameValuePair[] p1 = { new NameValuePair("_flowId", "closedelayedbagrecord-flow")};
		String newLocation = startFlow(p1, "CLOSE INCIDENT", 1);
		
		PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		search.setQueryString(p2);
		
		search.setParameter("wtrCloseRequest.recordReferences[0].stationCode", wt_id.substring(0,3));
		search.setParameter("wtrCloseRequest.recordReferences[0].airlineCode", wt_id.substring(3,5));
		search.setParameter("wtrCloseRequest.recordReferences[0].recordId", wt_id.substring(5));
		search.setParameter("amendOption[0]", "noamend");
		
		//search.setParameter("_showAllDetailsInReadOnlyArea", "on");
		search.setParameter("_eventId", "continue");
		search.setParameter("wtrDisplayRequest.recordAdditionalInfo.fullRecord", "TRUE");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.fullRecord", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.recordHistory", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.matchingHistory", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.SMSMessages", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.internetMessages", "on");
		
		try {
			debugOut(search, "");
			client.executeMethodWithPause(search, "CLOSE INCIDENT: SEARCH (2)");
			if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || 
					search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = search.getResponseHeader("location").getValue();
			}
			else{
				newLocation = "";
				logger.error("close postMethod not redirect!");
				throw new WorldTracerException("unable to close ahl");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to close ahl");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to close ahl");
		}
		finally {
			search.releaseConnection();
		}
		//redirect to submit result page and get close signal
		if(!"".equals(newLocation)){
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethodWithPause(redirect, "CLOSE INCIDENT: REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
				//System.out.println("close incident result:" + responseBody);
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to close ahl");
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to close ahl");
			}
			finally {
				redirect.releaseConnection();
			}
	    }
		if(responseBody.toUpperCase().contains("FILE CLOSED") 
				|| responseBody.toUpperCase().contains("CLOSE RECORD (FINISH)") || responseBody.toUpperCase().contains("CLOSE FILE (FINISH)")){
			return wt_id;
		}else{
			logger.info("FAILED TO CLOSE AHL REPSONSE. ");
			logger.info("close incident result:" + responseBody);
			return null;
		}
	}


	public void createOhd(WorldTracerActionDTO dto, Ohd data,
      WorldTracerResponse response) throws WorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createOhdFieldMap(data);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_OHD);
		String responseBody = null;
		try {

			NameValuePair[] p1 = { new NameValuePair("_flowId", "createonhandbagrecord-flow")};
			String newLocation = startFlow(p1, "INSERT OHD", 1);
			
			PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
			for(Map.Entry<DefaultWorldTracerService.WorldTracerField, List<String>> fieldEntry : fieldMap.entrySet()){
				List<String> fieldList = fieldEntry.getValue();
				//add color type
				if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.CT){
					if(fieldList != null && fieldList.size() >= 1){
						String bagFace = fieldList.get(0);
						String colorType = bagFace.substring(0, 2);
						String bagType = bagFace.substring(2, 4);
						String colorDesc = bagFace.substring(4, 7);
						search.setParameter("onHandBagRecord.onHandBag.colorType.colorCode", colorType);
						search.setParameter("onHandBagRecord.onHandBag.colorType.typeCode", bagType);
						search.setParameter("onHandBagRecord.onHandBag.colorType.descriptionCodes", colorDesc);
					}
				}
				//add bag(passenger) itinerary
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.FD){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String paxIntinery = fieldList.get(i);
							String[] inti = paxIntinery.split("/");
							String airlineCode = inti[0].substring(0, 2);
							String flightNumber = inti[0].substring(2);
							String flightDate = inti[1];
							search.setParameter("onHandBagRecord.passenger.paxFlights["+ i +"].airlineCode", airlineCode);
							search.setParameter("onHandBagRecord.passenger.paxFlights["+ i +"].flightNumber", flightNumber);
							search.setParameter("onHandBagRecord.passenger.paxFlights["+ i +"].flightDate", flightDate);
						}
					}
				}
				//add passenger routing
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.RT){
					if(fieldList != null){
						int originCount = 2;
						int destinCount = 2;
						for(int i = 0; i < fieldList.size(); i++){
							String station = fieldList.get(i);
							if(i <= 1){
								//add first routing
								search.setParameter("onHandBagRecord.onHandBag.route.passengerRoute["+ i +"]", station);
							}else{
								//add other routing								
								search.setParameter("station__origin__field0" + originCount, fieldList.get(i-1));
								search.setParameter("onHandBagRecord.onHandBag.route.passengerRoute["+ destinCount +"]", station);
								originCount++;
								destinCount++;
							}							
						}
					}
				}
				//add passenger initial
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.IT){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.passenger.initials[0]", fieldList.get(0));
					}
				}
				//add passenger name
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.NM){
					if(fieldList != null && fieldList.size() >= 1){
						String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(0));
						search.setParameter("onHandBagRecord.passenger.names[0]", name);
					}
				}
				//add passenger first name/title
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.PT){
					if(fieldList != null && fieldList.size() >= 1){
						String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
						search.setParameter("onHandBagRecord.passenger.title", title);
					}
				}
				//add passenger address on bag
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.AB){
					if(fieldList != null && fieldList.size() >= 1){
						String ab = RULES.get(DefaultWorldTracerService.WorldTracerField.AB).formatEntry(fieldList.get(0));
						search.setParameter("onHandBagRecord.onHandBag.bagAddress.line1", ab.replace(".", "&#46;"));
					}
				}
				//add passenger phones on bag
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.PN || fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.TP){
					fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PN);
					List<String> workPhoneList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
					String phone = "";
					if(fieldList != null && fieldList.size() >= 1){
						if(fieldList.get(0).length()>0)
							phone = RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(0)) + ",";
					}
					if(workPhoneList != null && workPhoneList.size() >= 1){
						if(workPhoneList.get(0).length()>0){
							phone += RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(workPhoneList.get(0));
						}
					}
					if(phone.endsWith(",")){
						phone = phone.substring(0, phone.length()-1);
					}
					search.setParameter("onHandBagRecord.onHandBag.bagPhones[0]", phone);
				}
				//add passenger mobile phone
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.CP){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.onHandBag.bagPhones[1]", RULES.get(DefaultWorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(0)));
					}
				}
				//add airline code and tagNumber: airlineCode(2 alphabet)+tagNumber(6 digits) or 10 digits(US),auto handle
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.TN){
					if(fieldList != null && fieldList.size() >= 1){
						String airtag = fieldList.get(0);
						search.setParameter("onHandBagRecord.onHandBag.bagTag.airlineCode", airtag.substring(0, 2));
						search.setParameter("onHandBagRecord.onHandBag.bagTag.tagNumber", airtag.substring(2));
					}
				}
				//add brand information 
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.BI){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.onHandBag.brandInfo.brandInformation", RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0)));
					}
				}
				//add contents
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.CC){
					if(fieldList != null && fieldList.size() >= 1){
						String[] contents = fieldList.get(0).split(".- ");
						for(int i = 0; i < contents.length; i++){
							int index = contents[i].indexOf("/");
							search.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + i + "].category", contents[i].substring(0,index));
							search.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + i + "].descriptionLine1", contents[i].substring(index+1).replace(".", "&#46;"));
							search.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + i + "].descriptionLine2", "");
						}
					}
				}
				//add storage location
				else if(fieldEntry.getKey() == DefaultWorldTracerService.WorldTracerField.SL){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.onHandBag.storageLocation", RULES.get(DefaultWorldTracerService.WorldTracerField.SL).formatEntry(fieldList.get(0)).replace(".", "&#46;"));
					}
				}
			}
			search.setParameter("_flowExecutionKey", flowKey);
			search.setParameter("flowExecutionKey_Passenger", flowKey);
			search.setParameter("flowExecutionKey_OnhandBags", flowKey);
			search.setParameter("onHandBagRecord.recordReference.airlineCode", data.getAirlineCode());
			search.setParameter("onHandBagRecord.recordReference.stationCode", data.getStationCode());
			
			search.setParameter("userLang", "1");
			search.setParameter("onHandBagRecord.tracingOption", "FULLTRACING");
			search.setParameter("_eventId", "submit");
			search.setParameter("bagType", "OnHand");
			
			try {
				debugOut(search, "");
				client.executeMethodWithPause(search, "INSERT OHD: INSERT (2)");
				if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
					newLocation = search.getResponseHeader("location").getValue();
				}
				else{
					logger.error("Close on-hand post method not redirect!");
					throw new WorldTracerException("on-hand post method not redirect!");
				}
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to create ohd");
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to create ohd");
			}
			finally {
				search.releaseConnection();
			}
			//redirect to submit result page and get wt_id
			
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect, "INSERT OHD: REDIRECT (3)");
				responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
				//System.out.println("response body:" + responseBody);
				//return true;
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to create ohd");
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to create ohd");
			}
			finally {
				redirect.releaseConnection();
			}
			
		} catch (Exception e) {
			throw new WorldTracerConnectionException(
					"Communication error with WorldTracer", e);
		}
		String wt_id = null;
		String errorString = "Submit failed.";
		Pattern succePatt = Pattern.compile("<SPAN.*>([^<>]+)\\[ACTIVE\\/TRACING]<\\/SPAN>",Pattern.CASE_INSENSITIVE);
		Matcher succeMat = succePatt.matcher(responseBody);
		
		if(succeMat.find()){
			wt_id = succeMat.group(1).replaceAll("&nbsp;", "").replaceAll("(\\(.*\\))", "");
		}else{
			succePatt = Pattern.compile("error = \'([^\']*)\'",Pattern.CASE_INSENSITIVE);
			succeMat = succePatt.matcher(responseBody);
			if(succeMat.find())
				errorString = succeMat.group(1);
		}
		logger.info("onhandbag wt_id:" + wt_id);
		if (wt_id != null && wt_id.length() >= 10) {
			Ohd ohdResponse = new Ohd();
			ohdResponse.setOhdId(wt_id);
			response.setOhd(ohdResponse);
			response.setSuccess(true);
			return;
		} else {
			throw new WorldTracerException(errorString);
		}
	  
  }


	public void closeOhd(WorldTracerActionDTO dto, Ohd data,
      WorldTracerResponse response) throws WorldTracerException {

		if (data.getOhdId() == null) {
			throw new WorldTracerException("Can't close ohd, no associated wt file");
		}
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createOhdCloseFieldMap(data);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate close ohd mapping");
		}

		if(data.getOhdId() == null || data.getOhdId().trim().length() < 1){
			response.setSuccess(false);
			throw new WorldTracerException("Incomplete data");
		}
		String responseBody = null;
		try {

			NameValuePair[] p1 = { new NameValuePair("_flowId", "closeonhandbagrecord-flow")};
			String newLocation = startFlow(p1, "CLOSE OHD", 1);
			PostMethod search = new PostMethod(WTRWEB_FLOW_URL);
			NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
			search.setQueryString(p2);
			
			search.setParameter("wtrCloseRequest.recordReferences[0].stationCode", data.getOhdId().substring(0,3));
			search.setParameter("wtrCloseRequest.recordReferences[0].airlineCode", data.getOhdId().substring(3,5));
			search.setParameter("wtrCloseRequest.recordReferences[0].recordId", data.getOhdId().substring(5));
			search.setParameter("amendOption[0]", "noamend");
			
			//search.setParameter("_showAllDetailsInReadOnlyArea", "on");
			search.setParameter("_eventId", "continue");
			search.setParameter("wtrDisplayRequest.recordAdditionalInfo.fullRecord", "TRUE");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.fullRecord", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.recordHistory", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.matchingHistory", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.SMSMessages", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.internetMessages", "on");
			
			try {
				debugOut(search, "");
				client.executeMethodWithPause(search, "CLOSE OHD: CLOSE (2)");
				if(search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
					newLocation = search.getResponseHeader("location").getValue();
				}
				else{
					newLocation = "";
					logger.error("close onHandBag postMethod not redirect!");
					throw new WorldTracerException("unable to close ohd");
				}
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to close ohd", e);
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to close ohd", e);
			}
			finally {
				search.releaseConnection();
			}
			//redirect to submit result page and get close signal
			if(!"".equals(newLocation)){
				GetMethod redirect = new GetMethod(newLocation);
				try {
					client.executeMethod(redirect, "CLOSE OHD: REDIRECT (3)");
					responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
					//System.out.println("close onHandBag result:" + responseBody);
				}
				catch (HttpException e) {
					logger.error("error", e);
					throw new WorldTracerException("unable to close ohd", e);
				}
				catch (IOException e) {
					logger.error("error", e);
					throw new WorldTracerException("unable to close ohd", e);
				}
				finally {
					redirect.releaseConnection();
				}
		    }
			if (responseBody.toUpperCase().contains("CLOSE RECORD (FINISH)") 
					|| responseBody.toUpperCase().contains("FILE CLOSED") || responseBody.toUpperCase().contains("CLOSE FILE (FINISH)")){
				response.setSuccess(true);
				return;
			} else {
				logger.error("Close onHand bag error: \n" + responseBody);
				throw new WorldTracerException();
			}
			
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}

		
	  
  }

	public void amendOhd(WorldTracerActionDTO dto, Ohd data,
      WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		if (data.getOhdId() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createOhdFieldMap(data);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}
		
		String wt_ohd_id = data.getOhdId();

		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.AMEND_OHD);
		NameValuePair[] p1 = { new NameValuePair("_flowId", "editonhandbagrecord-flow")};
		String newLocation = startFlow(p1, "AMEND OHD", 1);
		
		String responseBody = null;
		//if exists this wt_id
		PostMethod getOnhand = new PostMethod(newLocation);
		getOnhand.setParameter("_flowExecutionKey", flowKey);
		getOnhand.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_ohd_id.substring(0, 3));
		getOnhand.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_ohd_id.substring(3, 5));
		getOnhand.setParameter("wtrDisplayRequest.recordReference.recordId", wt_ohd_id.substring(5));
		getOnhand.setParameter("resultsForm.sortOption", "recordReference");
		getOnhand.setParameter("radio", "1");
		getOnhand.setParameter("_eventId", "update");
		try {
			debugOut(getOnhand, "");
			client.executeMethodWithPause(getOnhand, "AMEND OHD: LOAD OHD (2)");
			if(getOnhand.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getOnhand.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getOnhand.getResponseHeader("location").getValue();
			}else{
				logger.error("Get amend onhand details not redirect!");
				throw new WorldTracerException("Get amend onhand details not redirect!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		finally {
			getOnhand.releaseConnection();
		}
		GetMethod getWtDetails = new GetMethod(newLocation);
		try {
			client.executeMethodWithPause(getWtDetails, "AMEND OHD: GET OHD (3)");
			if(getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				logger.error("GetWtDetails method redirect again!");
				throw new WorldTracerException("unable to amend ohd");
			}else{
				responseBody = getStringFromInputStream(getWtDetails.getResponseBodyAsStream());
			}
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		finally {
			getWtDetails.releaseConnection();
		}
		if(responseBody.toUpperCase().contains("NO MATCHING RECORDS FOUND")){
			throw new WorldTracerException("wt_ohd_id not exists!");
		}
		Matcher m = FLOW_PATTERN.matcher(newLocation);
		if(m.find()) {
			flowKey = m.group(1).trim();
		}
		//add params to amend
		PostMethod amendMethod = new PostMethod(WTRWEB_FLOW_URL);
		DefaultWorldTracerService.WorldTracerField[] fields = DefaultWorldTracerService.WorldTracerField.values();
		for(int k=0; k<fields.length; k++){
			List<String> fieldList = fieldMap.get(fields[k]);
			//add color type
			if(fields[k] == DefaultWorldTracerService.WorldTracerField.CT){
				if(fieldList != null && fieldList.size() >= 1){
					String bagFace = fieldList.get(0);
					String colorType = bagFace.substring(0, 2);
					String bagType = bagFace.substring(2, 4);
					String colorDesc = bagFace.substring(4, 7);
					amendMethod.setParameter("onHandBagRecord.onHandBag.colorType.colorCode", colorType);
					amendMethod.setParameter("onHandBagRecord.onHandBag.colorType.typeCode", bagType);
					amendMethod.setParameter("onHandBagRecord.onHandBag.colorType.descriptionCodes", colorDesc);
				}
			}
			//add bag(passenger) itinerary
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.FD){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						String paxIntinery = fieldList.get(i);
						String[] inti = paxIntinery.split("/");
						String airlineCode = inti[0].substring(0, 2);
						String flightNumber = inti[0].substring(2);
						String flightDate = inti[1];
						amendMethod.setParameter("onHandBagRecord.passenger.paxFlights["+ i +"].airlineCode", airlineCode);
						amendMethod.setParameter("onHandBagRecord.passenger.paxFlights["+ i +"].flightNumber", flightNumber);
						amendMethod.setParameter("onHandBagRecord.passenger.paxFlights["+ i +"].flightDate", flightDate);
					}
				}
				for(int j=i; j<4; j++){
					amendMethod.setParameter("onHandBagRecord.passenger.paxFlights["+ j +"].airlineCode", "");
					amendMethod.setParameter("onHandBagRecord.passenger.paxFlights["+ j +"].flightNumber", "");
					amendMethod.setParameter("onHandBagRecord.passenger.paxFlights["+ j +"].flightDate", "");
				}
			}
			//add passenger routing
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.RT){
				int originCount = 2;
				if(fieldList != null){
					for(int i = 0; i < fieldList.size(); i++){
						String station = fieldList.get(i);
						if(i <= 1){
							//add first routing
							amendMethod.setParameter("onHandBagRecord.onHandBag.route.passengerRoute["+ i +"]", station);
						}else{
							//add other routing								
							amendMethod.setParameter("station__origin__field0" + originCount, fieldList.get(i-1));
							amendMethod.setParameter("onHandBagRecord.onHandBag.route.passengerRoute["+ originCount +"]", station);
							originCount++;
						}							
					}
				}
				for(int j=originCount; j<5; j++){
					amendMethod.setParameter("station__origin__field0" + j, "");
					amendMethod.setParameter("onHandBagRecord.onHandBag.route.passengerRoute["+ j +"]", "");
				}
			}
			//add passenger initial
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.IT){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.passenger.initials[0]", fieldList.get(0));
				}else{
					amendMethod.setParameter("onHandBagRecord.passenger.initials[0]", "");
				}
			}
			//add passenger name. name cannot be deleted in world tracer
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.NM){
				if(fieldList != null && fieldList.size() >= 1){
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(0));
					amendMethod.setParameter("onHandBagRecord.passenger.names[0]", name);
				}
			}
			//add passenger first name/title
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.PT){
				if(fieldList != null && fieldList.size() >= 1){
					String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
					amendMethod.setParameter("onHandBagRecord.passenger.title", title);
				}else{
					amendMethod.setParameter("onHandBagRecord.passenger.title", "");
				}
			}
			//add passenger address on bag
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.AB){
				if(fieldList != null && fieldList.size() >= 1){
					String ab = RULES.get(DefaultWorldTracerService.WorldTracerField.AB).formatEntry(fieldList.get(0));
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagAddress.line1", ab.replace(".", "&#46;"));
				}else{
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagAddress.line1", "");
				}
			}
			//add passenger phones on bag
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.PN){
				List<String> workPhoneList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
//				List<String> mobilePhoneList = fieldMap.get(WorldTracerService.WorldTracerField.CP);
				String phone = "";
				if(fieldList != null && fieldList.size() >= 1){
					if(fieldList.get(0).length()>0)
						phone = RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(0)) + ",";
				}
				if(workPhoneList != null && workPhoneList.size() >= 1){
					if(workPhoneList.get(0).length()>0){
						phone += RULES.get(DefaultWorldTracerService.WorldTracerField.PN).formatEntry(workPhoneList.get(0));
					}	
				}
				if(phone.endsWith(",")){
					phone = phone.substring(0, phone.length()-1);
				}
				amendMethod.setParameter("onHandBagRecord.onHandBag.bagPhones[0]", phone);
				
//				if(mobilePhoneList != null && mobilePhoneList.size() >= 1){
//					if(phone.equals("")){
//						amendMethod.setParameter("onHandBagRecord.onHandBag.bagPhones[0]", mobilePhoneList.get(0));
//					}else{
//						amendMethod.setParameter("onHandBagRecord.onHandBag.bagPhones[1]", mobilePhoneList.get(0));
//					}
//				}

			}				
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.CP){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagPhones[1]", RULES.get(DefaultWorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(0)));
				}
			}
			//add airline code and tagNumber: cannot be deleted
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.TN){
				if(fieldList != null && fieldList.size() >= 1){
					String airtag = fieldList.get(0);
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagTag.airlineCode", airtag.substring(0, 2));
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagTag.tagNumber", airtag.substring(2));
				}else{
					logger.info("Bag Tag Number in World Tracer can not be deleted if exists!");
				}
			}
			//add brand information 
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.BI){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.onHandBag.brandInfo.brandInformation", RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0)));
				}else{
					amendMethod.setParameter("onHandBagRecord.onHandBag.brandInfo.brandInformation", "");
				}
			}
			//add contents, contents cannot be deleted [COMPUTER/FSOKK/FSF PC.- BOOK/NEW BOOKS.- PHOTO/ERER PHOTO]
			//Category cannot be changed. (If changed it would be added as new content in World Tracer.)
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.CC){
				int i = 0;
				if(fieldList != null && fieldList.size() >= 1){
					String[] contents = fieldList.get(0).split(".- ");
					for(i = 0; i < contents.length; i++){
						int index = contents[i].indexOf("/");
						amendMethod.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + i + "].category", contents[i].substring(0, index));
						amendMethod.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + i + "].descriptionLine1", contents[i].substring(index+1).replace(".", "&#46;"));
						amendMethod.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + i + "].descriptionLine2", "");
						
					}
				}
				for(int j=i; j<12; j++){
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + j + "].category", "");
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + j + "].descriptionLine1", "");
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagContents.bagItemsList[" + j + "].descriptionLine2", "");
				}
			}
			//add storage location
			else if(fields[k] == DefaultWorldTracerService.WorldTracerField.SL){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.onHandBag.storageLocation", fieldList.get(0).replace(".", "&#46;"));
				}else{
					amendMethod.setParameter("onHandBagRecord.onHandBag.storageLocation", "");
				}
			}
		}
		amendMethod.setParameter("onHandBagRecord.recordReference.stationCode", wt_ohd_id.substring(0,3));
		amendMethod.setParameter("onHandBagRecord.recordReference.airlineCode", wt_ohd_id.substring(3,5));
		amendMethod.setParameter("onHandBagRecord.recordReference.recordId", wt_ohd_id.substring(5));
		
		amendMethod.setParameter("_eventId", "amend");
		amendMethod.setParameter("bagType", "OnHand");
		amendMethod.setParameter("_flowExecutionKey", flowKey);
		try{
			debugOut(amendMethod, "");
			client.executeMethodWithPause(amendMethod, "AMEND OHD: AMEND (4)");
			if(amendMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY 
					|| amendMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = amendMethod.getResponseHeader("location").getValue();
			}else{
				logger.error("Amend onhand method not redirect!");
				throw new WorldTracerException("Amend onhand method not redirect!");
			}
		}catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		finally {
			amendMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect, "AMEND OHD: REDIRECT (5)");
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to amend ohd");
		}
		finally {
			redirect.releaseConnection();
		}
		if (responseBody.toUpperCase().contains("RECORD AMENDED SUCCESSFULLY") || responseBody.toUpperCase().contains("FILE AMENDED SUCCESSFULLY")) {
			logger.debug("OHD Amend Successfully: " + responseBody);
			response.setSuccess(true);
			return;
		} else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to amend OHD. See logs for details";
				logger.error("amend ohd response:" + responseBody);
			}
			throw new WorldTracerException(errorString);
		}
  }

	public void reinstateOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = susritItem(ohd.getOhdId(), "RIT", "OHD");
		String errorString;
		Pattern error_patt = Pattern.compile("<input[^<>]*id=\"result__cr0\"[^<>]*value=\"([^<>\"]*)\"+?[^<>]*/>+?",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
		} else {
			errorString = responseBody;
		}
		if (OK.equals(errorString) || ALREADY_REINSTATED.equals(errorString)) {
			response.setSuccess(true);
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void suspendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException, NotLoggedIntoWorldTracerException {
		String responseBody = susritItem(ohd.getOhdId(), "SUS", "OHD");
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
}
