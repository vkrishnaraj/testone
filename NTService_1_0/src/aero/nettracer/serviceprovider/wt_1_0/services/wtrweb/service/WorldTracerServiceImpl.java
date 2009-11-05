package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
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

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
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
	private String flowKey = null;

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
		return;		
	}

	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf,
			WorldTracerResponse response) {
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
		

		if(myDestinationType == 1) {
			sendToDestination = "ALL_STATIONS";
			// TODO: DOES THIS NEED TO USE LOOKUP???
			String allStationsActionArea = "" + detail.getArea();
			forwardMethod.setParameter("sendMessageActionVO.allStationsActionArea", allStationsActionArea);
		} else if(myDestinationType == 2) {
			sendToDestination = "ONE_REGION";
			String regionNumber = "" + detail.getStation();
			String regionActionArea = "" + detail.getArea();
			forwardMethod.setParameter("sendMessageActionVO.regionNumber", regionNumber);
			forwardMethod.setParameter("sendMessageActionVO.regionActionArea", regionActionArea);
		} else {
			HashMap<String, String> lookupHash = new HashMap<String, String>();
			lookupHash.put("AP", "ADDITIONAL_PROMPT_AREA");
			lookupHash.put("FW", "FORWARD_AREA");
			lookupHash.put("AA", "ACTION_AREA");
			
			
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
		
		//////////////////////
		
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
		
		return result;
		
	}
}
