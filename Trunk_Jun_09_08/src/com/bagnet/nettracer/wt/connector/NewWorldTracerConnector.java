package com.bagnet.nettracer.wt.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.htmlparser.util.ParserException;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.wt.WorldTracerAlreadyClosedException;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.svc.DefaultWorldTracerService;
import com.bagnet.nettracer.wt.svc.RuleMapper;
import com.bagnet.nettracer.wt.svc.WorldTracerConnectionPool;
import com.bagnet.nettracer.wt.svc.WorldTracerRule;
import com.bagnet.nettracer.wt.svc.WorldTracerService;
import com.bagnet.nettracer.wt.svc.WorldTracerService.TxType;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;
import com.bagnet.nettracer.wt.utils.ParsingUtils;

public class NewWorldTracerConnector implements WorldTracerConnector {
	private static final String OK = "OK";

	private static final String ALREADY_REINSTATED = "ALREADY REINSTATED";

	private String wtCompanycode;
	
	private String lastFlowKey = null;
	
	WorldTracerConnectionPool pool = null;
	
	RuleMapper wtRuleMap;
	
	public static enum UserType {
		WM, WT
	};
	
	public static enum StateCode{
		AK("Alaska"),
		AL("Alabama"),
		AZ("Arizona"),
		AR("Arkansas"),
		CA("California"),
		CO("Colorado"),
		CT("Connecticut"),
		DE("Delaware"),
		DC("District of Columbia"),
		FL("Florida"),
		GA("Georgia"),
		HI("Hawaii"),
		ID("Idaho"),
		IL("Illinois"),
		IN("Indiana"),
		IA("Iowa"),
		KA("Kansas"),
		KY("Kentucky"),
		LA("Louisiana"),
		ME("Maine"),
		MH("Marshall Island"),
		MD("Maryland"),
		MA("Massachusetts"),
		MI("Michigan"),
		MN("Minnesota"),
		MS("Mississippi"),
		MO("Missouri"),
		MT("Montana"),
		NE("Nebraska"),
		NV("Nevada"),
		NH("New Hampshire"),
		NJ("New Jersey"),
		NM("New Mexico"),
		NY("New York"),
		NC("North Carolina"),
		ND("North Dakota"),
		OH("Ohio"),
		OK("Oklahoma"),
		OR("Oregon"),
		PA("Pennsylvania"),
		RI("Rhode Island"),
		SC("South Carolina"),
		SD("South Dakota"),
		TN("Tennessee"),
		TX("Texas"),
		UT("Utah"),
		VA("Virginia"),
		WA("Washington"),
		WV("West Virginia"),
		WI("Wisconsin"),
		WY("Wyoming"),
		AE("APO(EU,ME,AF,CN)"),  //APO AE
		AP("APO(Pacific)"),      //APO AP
		AA("APO(Americas)"),     //APO AA
		VT("Vermont");
		StateCode(String stateName){
			this.stateName = stateName;
		}
		private final String stateName;
		public String getStateName(){
			return stateName;
		}
	}

	private static final Logger logger = Logger
			.getLogger(NewWorldTracerConnector.class);

	private static final Object ALREADY_SUSPENDED = "ALREADY SUSPENDED";

	private HttpClient client;
	
	private WorldTracerRule<String> BASIC_RULE = DefaultWorldTracerService.BASIC_RULE;

	public NewWorldTracerConnector(WorldTracerConnectionPool pool, String companyCode) {
		this.wtCompanycode = companyCode;
		this.pool = pool;
	}
	
	public void initialize() throws Exception {
		this.client = (HttpClient) pool.borrowObject();
	}

	public String insertIncident(Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,String companyCode, String stationCode) throws WorldTracerException {
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_AHL);
		
		lastFlowKey = null;
		String responseBody = null;
		try {
			GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
			NameValuePair[] p1 = { new NameValuePair("_flowId", "createdelayedbagrecord-flow") };
			startFlow.setQueryString(p1);
			startFlow.setFollowRedirects(false);
			try {
				client.executeMethod(startFlow);
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
				startFlow.releaseConnection();
			}
			if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && 
					startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
				logger.error("start flow not redirect!");
				throw new WorldTracerException("unable to export incident");
			}
			//get flow key
			String newLocation = startFlow.getResponseHeader("location").getValue();
			String flowKey = newLocation.split("=")[1];
			//submit form by post method
			PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
			List<String> bagsList = fieldMap.get(WorldTracerService.WorldTracerField.CC);  //for bagTag.airlineCode and bagTag.tagNum
			for(Map.Entry<WorldTracerService.WorldTracerField, List<String>> fieldEntry : fieldMap.entrySet()){
				List<String> fieldList = fieldEntry.getValue();
				//add bag type
				if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.CT){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.FD){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.IT){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String initial = fieldList.get(i);
							search.setParameter("delayedBagRecord.passenger.initials[" + i +"]", initial);
						}
					}
				}
				//add passenger routing
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.RT){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.NM){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							String name = RULES.get(WorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(i));					
							search.setParameter("delayedBagRecord.passenger.names[" + i +"]", name);
						}
					}
				}
				//add first passenger title
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.PT){
					if(fieldList != null){						
						String[] titles = fieldList.get(0).split(". ");
						if(titles.length>0) {
							String title = RULES.get(WorldTracerService.WorldTracerField.PT).formatEntry(titles[titles.length-1]);
							search.setParameter("delayedBagRecord.passenger.title", title);
						}
					}
				}
				//add passenger permanent address and country code
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.PA){
					//firstly get country code
					List<String> countryList = fieldMap.get(WorldTracerService.WorldTracerField.CO);
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
				} else if (fieldEntry.getKey() == WorldTracerService.WorldTracerField.ZIP) {
					search.setParameter("delayedBagRecord.passenger.zipCode", fieldEntry.getValue().get(0));
				} else if (fieldEntry.getKey() == WorldTracerService.WorldTracerField.STATE) {
					search.setParameter("delayedBagRecord.passenger.state", fieldEntry.getValue().get(0));
				}
				//add passenger temp address and country code
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.TA){
					//firstly get country code
					if(null == search.getParameter("delayedBagRecord.passenger.countryCode") || "".equals(search.getParameter("delayedBagRecord.passenger.countryCode").getValue().trim())){
						List<String> countryList = fieldMap.get(WorldTracerService.WorldTracerField.CO);
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.EA){
					if(fieldList != null && fieldList.size() >= 1){
						String email = RULES.get(WorldTracerService.WorldTracerField.EA).formatEntry(fieldList.get(0)).length()>1?fieldList.get(0).replace("/A/", "@")
								.replace("/D/", ".").replace("/U/", "_").replace("/T/", "~").replace("/P/", "+"):"";
						search.setParameter("delayedBagRecord.passenger.email", email);
					}
				}
				//add passenger home/business phone, here maybe execute two times
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.PN || fieldEntry.getKey() == WorldTracerService.WorldTracerField.TP){
					fieldList = fieldMap.get(WorldTracerService.WorldTracerField.PN);
					List<String> bizPhones = fieldMap.get(WorldTracerService.WorldTracerField.TP);
					if(bizPhones == null && fieldList != null){
						for(int i = 0; i < (fieldList.size()<=2 ? fieldList.size() : 2); i++){
							search.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(i)));
						}
					}
					else if(fieldList == null && bizPhones != null){
						for(int i = 0; i < (bizPhones.size()<=2 ? bizPhones.size() : 2); i++){
							search.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.TP).formatEntry(bizPhones.get(i)));
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
								search.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(permPhone));
							}
						}
					}
				}
				//add passenger cellphone
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.CP){
					if(fieldList != null){
						for(int i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
							search.setParameter("delayedBagRecord.passenger.cellPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(i)));
						}
					}
				}
				//add passenger fax
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.FX){
					if(fieldList != null){
						for(int i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
							search.setParameter("delayedBagRecord.passenger.fax" + (i+1), RULES.get(WorldTracerService.WorldTracerField.FX).formatEntry(fieldList.get(i)));
						}
					}
				}
				//add number of passenger and booking infomation
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.NP){
					if(fieldList != null){
						search.setParameter("delayedBagRecord.passenger.booking.numberOfPaxInfo", RULES.get(WorldTracerService.WorldTracerField.NP).formatEntry(fieldList.get(0)));
					}
					search.setParameter("delayedBagRecord.passenger.booking.pooledTicketNumber", "0000");
					search.setParameter("_avoidBindingdelayedBagRecord.passenger.booking.group", "on");
				}
				//add bag itinerary
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.BR){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.TN){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.BI){
					if(fieldList != null){
						for(int i = 0; i < fieldList.size(); i++){
							search.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].brandInfo.brandInformation", RULES.get(WorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(i)));
						}
					}
				}
				//add bags contents:  [01 BOOK/TECHNICAL BOOK.- PHOTO/VIEW PHOTOES, 02 FOOD/GOOD FOODS]
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.CC){
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
			search.setParameter("delayedBagRecord.recordReference.stationCode", stationCode);
			search.setParameter("delayedBagRecord.recordReference.airlineCode", companyCode);
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
				client.executeMethod(search);
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
				client.executeMethod(redirect);
				responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
				//System.out.println("response body:" + responseBody);
				//return true;
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
		Pattern succePatt = Pattern.compile("<SPAN>([^<>]+)\\[ACTIVE\\/TRACING]<\\/SPAN>",Pattern.CASE_INSENSITIVE);
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
			return wt_id;
		} else {
			throw new WorldTracerException(errorString);
		}
		
	}

	private void debugOut(PostMethod method, String methodName) {
		if(method!= null && logger.isDebugEnabled()) {
			logger.debug("about to call " + method.getQueryString() + " with the following paramters:");
			StringBuilder sb = new StringBuilder("params:");
			for(NameValuePair foo : method.getParameters()) {
				sb.append(String.format("%s=%s,", foo.getName(), foo.getValue()));
			}
			logger.debug(sb.toString());
		}
	}

	public String amendAhl(Map<WorldTracerField, List<String>> fieldMap,
			String wt_ahl_id) throws WorldTracerException {
		lastFlowKey = null;
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.AMEND_AHL);
		//start
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "editdelayedbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start amendAhl flow not redirect!");
			throw new WorldTracerException("unable to amend incident");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
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
			client.executeMethod(getAhl);
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
			client.executeMethod(getWtDetails);
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
		//add params to amend
		PostMethod amendMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
		//add normal params
		List<String> bagsList = fieldMap.get(WorldTracerService.WorldTracerField.CT);  //for bagTag.airlineCode and bagTag.tagNum
		WorldTracerService.WorldTracerField[] fields = WorldTracerService.WorldTracerField.values();
		for(int k=0; k<fields.length; k++){
			List<String> fieldList = fieldMap.get(fields[k]);
			//add bag type
			if(fields[k] == WorldTracerService.WorldTracerField.CT){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.FD){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.IT){
				if(fieldList != null){
					for(int i = 0; i < fieldList.size(); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.initials[" + i +"]", fieldList.get(i));
					}
				}
			}
			//add passenger routing
			else if(fields[k] == WorldTracerService.WorldTracerField.RT){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.NM){
				if(fieldList != null){
					for(int i = 0; i < fieldList.size(); i++){
						String name = RULES.get(WorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(i));
						amendMethod.setParameter("delayedBagRecord.passenger.names[" + i +"]", name);
					}
				}
			}
			//add first passenger title
			else if(fields[k] == WorldTracerService.WorldTracerField.PT){
				if(fieldList != null){
					String[] titles = fieldList.get(0).split(". ");
					if(titles.length>0) {
						String title = RULES.get(WorldTracerService.WorldTracerField.PT).formatEntry(titles[titles.length-1]);
						amendMethod.setParameter("delayedBagRecord.passenger.title", title);
					}
				}
			}
			//add passenger permanent address and country code
			else if(fields[k] == WorldTracerService.WorldTracerField.PA){
				//firstly get country code
				List<String> countryList = fieldMap.get(WorldTracerService.WorldTracerField.CO);
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
			} else if (fields[k] == WorldTracerService.WorldTracerField.ZIP) {
				if (fieldList != null) {
					amendMethod.setParameter("delayedBagRecord.passenger.zipCode", fieldList.get(0));
				}
			} else if (fields[k] == WorldTracerService.WorldTracerField.STATE) {
				if (fieldList != null) {
					amendMethod.setParameter("delayedBagRecord.passenger.state", fieldList.get(0));
				}
			}
			//add passenger temp address and country code
			else if(fields[k] == WorldTracerService.WorldTracerField.TA){
				//firstly get country code
				if(null == amendMethod.getParameter("delayedBagRecord.passenger.countryCode") || "".equals(amendMethod.getParameter("delayedBagRecord.passenger.countryCode").getValue().trim())){
					List<String> countryList = fieldMap.get(WorldTracerService.WorldTracerField.CO);
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
			else if(fields[k] == WorldTracerService.WorldTracerField.EA){
				if(fieldList != null && fieldList.size() >= 1){
					String email = RULES.get(WorldTracerService.WorldTracerField.EA).formatEntry(fieldList.get(0)).length()>1?fieldList.get(0).replace("/A/", "@")
							.replace("/D/", ".").replace("/U/", "_").replace("/T/", "~").replace("/P/", "+"):"";
          amendMethod.setParameter("delayedBagRecord.passenger.email", email);
				}else{
					amendMethod.setParameter("delayedBagRecord.passenger.email", "");  //not useful, WorldTracer would restore
				}
			}
			//add passenger home/business phone, phone can not be deleted in WorldTracer
			else if(fields[k] == WorldTracerService.WorldTracerField.PN){
				List<String> bizPhones = fieldMap.get(WorldTracerService.WorldTracerField.TP);
				String permPhone = "";
				if(bizPhones == null && fieldList != null){
					for(int i = 0; i < (fieldList.size()<2?fieldList.size():2); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(i)));
					}
				}
				else if(fieldList == null && bizPhones != null){
					for(int i = 0; i < (bizPhones.size()<2?bizPhones.size():2); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.TP).formatEntry(bizPhones.get(i)));
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
							amendMethod.setParameter("delayedBagRecord.passenger.permanentPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(permPhone));
						}
					}
				}
			}
			//add passenger cellphone
			else if(fields[k] == WorldTracerService.WorldTracerField.CP){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
						amendMethod.setParameter("delayedBagRecord.passenger.cellPhones[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(i)));
					}
				}
				for(int j=i; j<2; j++){
					amendMethod.setParameter("delayedBagRecord.passenger.cellPhones[" + j + "]", "");
				}
			}
			//add passenger fax, fax can not be deleted in WorldTracer
			else if(fields[k] == WorldTracerService.WorldTracerField.FX){
				if(fieldList != null){
					for(int i = 0; i < (fieldList.size()>2?2:fieldList.size()); i++){
						if(!fieldList.get(i).equals("")){
							amendMethod.setParameter("delayedBagRecord.passenger.fax" + (i+1), RULES.get(WorldTracerService.WorldTracerField.FX).formatEntry(fieldList.get(i)));
						}
					}
				}
			}
			//add number of passenger and booking infomation
			else if(fields[k] == WorldTracerService.WorldTracerField.NP){
				if(fieldList != null && fieldList.get(0).length() > 0){
					amendMethod.setParameter("delayedBagRecord.passenger.booking.numberOfPaxInfo", fieldList.get(0));
				}
				amendMethod.setParameter("avoidBindingdelayedBagRecord.passenger.booking.pooledTicketNumber", "0000");
				amendMethod.setParameter("_avoidBindingdelayedBagRecord.passenger.booking.group", "on");
			}
			//add bag itinerary
			else if(fields[k] == WorldTracerService.WorldTracerField.BR){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.TN){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.BI){
				int i = 0;
				if(fieldList != null){
					for(i = 0; i < fieldList.size(); i++){
						amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags["+ i +"].brandInfo.brandInformation", RULES.get(WorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(i)));
					}
				}
				for(int j=i; j<10; j++){
					amendMethod.setParameter("delayedBagRecord.delayedBagGroup.delayedBags[" + j + "].brandInfo.brandInformation", "");
				}
			}
			//add bags contents:  [01 BOOK/TECHNICAL BOOK.- PHOTO/VIEW PHOTOES, 02 FOOD/GOOD FOODS]
			//contents can not be deleted in WorldTracer
			else if(fields[k] == WorldTracerService.WorldTracerField.CC){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.FS){
				if(fieldList != null && fieldList.size() > 0){
					amendMethod.setParameter("delayedBagRecord.delayedBagClaim.faultStation", fieldList.get(0));
				}
			}
			else if(fields[k] == WorldTracerService.WorldTracerField.RL){
				if(fieldList != null && fieldList.size() > 0){
					amendMethod.setParameter("delayedBagRecord.delayedBagClaim.reasonForLoss", fieldList.get(0));
				}
			}
			else if(fields[k] == WorldTracerService.WorldTracerField.RC){
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
		amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagClaim.insured", "on");
		amendMethod.setParameter("_avoidBindingdelayedBagRecord.delayedBagClaim.liabilityTag", "on");
		amendMethod.setParameter("_flowExecutionKey", flowKey);
		amendMethod.setParameter("flowExecutionKey_Flights", flowKey);
		amendMethod.setParameter("flowExecutionKey_Bags", flowKey);
		
		try{
			debugOut(amendMethod, "");
			client.executeMethod(amendMethod);
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
			client.executeMethod(redirect);
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
		if (responseBody.toUpperCase().contains("RECORD AMENDED SUCCESSFULLY")) {
			return "AHL AMENDED";
		} else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
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

	public String amendOhd(Map<WorldTracerField, List<String>> fieldMap,
			String wt_ohd_id) throws WorldTracerException {
		lastFlowKey = null;
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.AMEND_OHD);
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "editonhandbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start amend onhand flow not redirect!");
			throw new WorldTracerException("unable to amend ohd");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
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
			client.executeMethod(getOnhand);
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
			client.executeMethod(getWtDetails);
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
		//add params to amend
		PostMethod amendMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
		WorldTracerService.WorldTracerField[] fields = WorldTracerService.WorldTracerField.values();
		for(int k=0; k<fields.length; k++){
			List<String> fieldList = fieldMap.get(fields[k]);
			//add color type
			if(fields[k] == WorldTracerService.WorldTracerField.CT){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.FD){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.RT){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.IT){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.passenger.initials[0]", fieldList.get(0));
				}else{
					amendMethod.setParameter("onHandBagRecord.passenger.initials[0]", "");
				}
			}
			//add passenger name. name cannot be deleted in world tracer
			else if(fields[k] == WorldTracerService.WorldTracerField.NM){
				if(fieldList != null && fieldList.size() >= 1){
					String name = RULES.get(WorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(0));
					amendMethod.setParameter("onHandBagRecord.passenger.names[0]", name);
				}
			}
			//add passenger first name/title
			else if(fields[k] == WorldTracerService.WorldTracerField.PT){
				if(fieldList != null && fieldList.size() >= 1){
					String title = RULES.get(WorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
					amendMethod.setParameter("onHandBagRecord.passenger.title", title);
				}else{
					amendMethod.setParameter("onHandBagRecord.passenger.title", "");
				}
			}
			//add passenger address on bag
			else if(fields[k] == WorldTracerService.WorldTracerField.AB){
				if(fieldList != null && fieldList.size() >= 1){
					String ab = RULES.get(WorldTracerService.WorldTracerField.AB).formatEntry(fieldList.get(0));
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagAddress.line1", ab.replace(".", "&#46;"));
				}else{
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagAddress.line1", "");
				}
			}
			//add passenger phones on bag
			else if(fields[k] == WorldTracerService.WorldTracerField.PN){
				List<String> workPhoneList = fieldMap.get(WorldTracerService.WorldTracerField.TP);
//				List<String> mobilePhoneList = fieldMap.get(WorldTracerService.WorldTracerField.CP);
				String phone = "";
				if(fieldList != null && fieldList.size() >= 1){
					if(fieldList.get(0).length()>0)
						phone = RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(0)) + ",";
				}
				if(workPhoneList != null && workPhoneList.size() >= 1){
					if(workPhoneList.get(0).length()>0){
						phone += RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(workPhoneList.get(0));
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
			else if(fields[k] == WorldTracerService.WorldTracerField.CP){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagPhones[1]", RULES.get(WorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(0)));
				}
			}
			//add airline code and tagNumber: cannot be deleted
			else if(fields[k] == WorldTracerService.WorldTracerField.TN){
				if(fieldList != null && fieldList.size() >= 1){
					String airtag = fieldList.get(0);
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagTag.airlineCode", airtag.substring(0, 2));
					amendMethod.setParameter("onHandBagRecord.onHandBag.bagTag.tagNumber", airtag.substring(2));
				}else{
					logger.info("Bag Tag Number in World Tracer can not be deleted if exists!");
				}
			}
			//add brand information 
			else if(fields[k] == WorldTracerService.WorldTracerField.BI){
				if(fieldList != null && fieldList.size() >= 1){
					amendMethod.setParameter("onHandBagRecord.onHandBag.brandInfo.brandInformation", RULES.get(WorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0)));
				}else{
					amendMethod.setParameter("onHandBagRecord.onHandBag.brandInfo.brandInformation", "");
				}
			}
			//add contents, contents cannot be deleted [COMPUTER/FSOKK/FSF PC.- BOOK/NEW BOOKS.- PHOTO/ERER PHOTO]
			//Category cannot be changed. (If changed it would be added as new content in World Tracer.)
			else if(fields[k] == WorldTracerService.WorldTracerField.CC){
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
			else if(fields[k] == WorldTracerService.WorldTracerField.SL){
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
			client.executeMethod(amendMethod);
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
			client.executeMethod(redirect);
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
		if (responseBody.toUpperCase().contains("RECORD AMENDED SUCCESSFULLY")) {
			logger.debug("OHD Amend Output: " + responseBody);
			return "OHD AMENDED";
		} else {
			String errorString;
			Pattern error_patt = Pattern.compile(
					"error = '([^<>']*)'", Pattern.CASE_INSENSITIVE
							| Pattern.DOTALL);
			Matcher m = error_patt.matcher(responseBody);
			if (m.find()) {
				errorString = m.group(1);
			} else {
				errorString = "Unable to amend OHD. See logs for details";
				logger.error("amend ohd response:" + responseBody);
			}
			throw new WorldTracerException(errorString);
		}
	}

	public String requestQoh(String fromStation, String fromAirline,
			String wt_ahl_id, Map<WorldTracerField, List<String>> fieldMap)
			throws WorldTracerException {
		
		lastFlowKey = null;
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.REQUEST_QOH);
		
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "requestbag-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start request quick onhand flow not redirect!");
			throw new WorldTracerException("start request quick onhand flow not redirect!");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//if wt_ahl_id exists
		PostMethod getAhl = new PostMethod(newLocation);
		NameValuePair[] pg = { new NameValuePair("_flowExecutionKey", flowKey)};
		getAhl.setQueryString(pg);
		getAhl.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_ahl_id.substring(0, 3));
		getAhl.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_ahl_id.substring(3, 5));
		getAhl.setParameter("wtrDisplayRequest.recordReference.recordId", wt_ahl_id.substring(5));
		getAhl.setParameter("resultsForm.sortOption", "recordReference");
		getAhl.setParameter("radio", "1");
		getAhl.setParameter("_eventId", "continue");
		try {
			debugOut(getAhl, "");
			client.executeMethod(getAhl);
			if(getAhl.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getAhl.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getAhl.getResponseHeader("location").getValue();
				flowKey = newLocation.split("=")[1];
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
			client.executeMethod(getWtDetails);
			if(getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getWtDetails.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				logger.error("GetWtDetails method redirect again!");
				return null;
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
			throw new WorldTracerException("wt_ahl_id not exists!");
		}
		//add params to request
		PostMethod requestMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		requestMethod.setQueryString(p2);
		
		requestMethod.setParameter("_eventId", "submit");
		requestMethod.setParameter("onHandOrQuickOnHand", "quickOnHand");
		
		requestMethod.setParameter("wtrForwardRequest.recordReference.stationCode", wt_ahl_id.substring(0, 3));
		requestMethod.setParameter("wtrForwardRequest.recordReference.airlineCode", wt_ahl_id.substring(3, 5));
		requestMethod.setParameter("wtrForwardRequest.recordReference.recordId", wt_ahl_id.substring(5));
		
		requestMethod.setParameter("wtrForwardRequest.quickOnHandStationCode", fromStation);
		requestMethod.setParameter("wtrForwardRequest.quickOnHandAirlineCode", fromAirline);
		
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
				requestMethod.setParameter("wtrForwardRequest.passenger.names[" + i + "]", RULES.get(WorldTracerService.WorldTracerField.NM).formatEntry(nameList.get(i)));
			}
		}
		if (fieldMap.containsKey(WorldTracerField.FI)) {
			requestMethod.setParameter("wtrForwardRequest.supplementaryInfo[0]", RULES.get(WorldTracerService.WorldTracerField.FI).formatEntry(fieldMap.get(WorldTracerField.FI).get(0)).replace(".", "&#46;"));
		}
		if (fieldMap.containsKey(WorldTracerField.SL)) {
			requestMethod.setParameter("wtrForwardRequest.storageLocation", RULES.get(WorldTracerService.WorldTracerField.SL).formatEntry(fieldMap.get(WorldTracerField.SL).get(0)).replace(".", "&#46;"));
		}
		try {
			debugOut(requestMethod, "");
			client.executeMethod(requestMethod);
			if(requestMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || requestMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = requestMethod.getResponseHeader("location").getValue();
			}
			else{
				logger.error("request quick on-hand method not redirect!");
				throw new WorldTracerException("request quick on-hand method not redirect!");
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
			requestMethod.releaseConnection();
		}
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect);
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
			//System.out.println("response body:" + responseBody);
			//return true;
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
		//Matcher m1 = qoh_success.matcher(responseBody);
		if (responseBody.toUpperCase().contains("BAG REQUESTED SUCCESSFULLY")) {
			return "QOH Requested";
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

	public String requestOhd(String wt_ohd_id, String wt_ahl_id,
			Map<WorldTracerField, List<String>> fieldMap)
			throws WorldTracerException {

		lastFlowKey = null;
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.REQUEST_QOH);
		
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "requestbag-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start request onhand flow not redirect!");
			throw new WorldTracerException("start request onhand flow not redirect!");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//if wt_ahl_id exists
		PostMethod getAhl = new PostMethod(newLocation);
		NameValuePair[] pg = { new NameValuePair("_flowExecutionKey", flowKey)};
		getAhl.setQueryString(pg);
		getAhl.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_ahl_id.substring(0, 3));
		getAhl.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_ahl_id.substring(3, 5));
		getAhl.setParameter("wtrDisplayRequest.recordReference.recordId", wt_ahl_id.substring(5));
		getAhl.setParameter("resultsForm.sortOption", "recordReference");
		getAhl.setParameter("radio", "1");
		getAhl.setParameter("_eventId", "continue");
		try {
			debugOut(getAhl, "");
			client.executeMethod(getAhl);
			if(getAhl.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || getAhl.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = getAhl.getResponseHeader("location").getValue();
				flowKey = newLocation.split("=")[1];
			}else{
				logger.error("Redirect to Ahl details page error!");
				throw new WorldTracerException("Redirect to Ahl details page error!");
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
			getAhl.releaseConnection();
		}
		GetMethod getWtDetails = new GetMethod(newLocation);
		try {
			client.executeMethod(getWtDetails);
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
		PostMethod requestMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		requestMethod.setQueryString(p2);
		
		requestMethod.setParameter("_eventId", "submit");
		requestMethod.setParameter("onHandOrQuickOnHand", "onHand");
		
		requestMethod.setParameter("wtrForwardRequest.recordReference.stationCode", wt_ahl_id.substring(0, 3));
		requestMethod.setParameter("wtrForwardRequest.recordReference.airlineCode", wt_ahl_id.substring(3, 5));
		requestMethod.setParameter("wtrForwardRequest.recordReference.recordId", wt_ahl_id.substring(5));
		
		requestMethod.setParameter("wtrForwardRequest.onHandRecords[0].stationCode", wt_ohd_id.substring(0, 3));
		requestMethod.setParameter("wtrForwardRequest.onHandRecords[0].airlineCode", wt_ohd_id.substring(3, 5));
		requestMethod.setParameter("wtrForwardRequest.onHandRecords[0].recordId", wt_ohd_id.substring(5));
		
		if (fieldMap.containsKey(WorldTracerField.NM)) {
			List<String> nameList = fieldMap.get(WorldTracerField.NM);
			for(int i=0; i < nameList.size() && i < 3; i++){
				requestMethod.setParameter("wtrForwardRequest.passenger.names[" + i + "]", nameList.get(i));
			}
		}
		if (fieldMap.containsKey(WorldTracerField.FI)) {
			requestMethod.setParameter("wtrForwardRequest.supplementaryInfo[0]", RULES.get(WorldTracerService.WorldTracerField.FI).formatEntry(fieldMap.get(WorldTracerField.FI).get(0)).replace(".", "&#46;"));
		}
		if (fieldMap.containsKey(WorldTracerField.SL)) {
			requestMethod.setParameter("wtrForwardRequest.storageLocation", RULES.get(WorldTracerService.WorldTracerField.SL).formatEntry(fieldMap.get(WorldTracerField.SL).get(0)).replace(".", "&#46;"));
		}

		try {
			debugOut(requestMethod, "");
			client.executeMethod(requestMethod);
			if(requestMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || requestMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = requestMethod.getResponseHeader("location").getValue();
			}
			else{
				//newLocation = "";
				//responseBody = getStringFromInputStream(forwardMethod.getResponseBodyAsStream());
				//System.out.println("forward method: " + responseBody);
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
			client.executeMethod(redirect);
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
			//System.out.println("response body:" + responseBody);
			//return true;
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
		//Matcher m1 = qoh_success.matcher(responseBody);
		if (responseBody.toUpperCase().contains("BAG REQUESTED SUCCESSFULLY")) {
			return "QOH Requested";
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

	public String closeIncident(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String wt_id, String stationCode) throws WorldTracerException {
		lastFlowKey = null;
		if(wt_id == null || wt_id.trim().length() < 1){
			return null;
		}
		try {
			String result = closeInc(fieldMap, wt_id, stationCode);
			if(result == null){
				result = amendBeforeClose(fieldMap, wt_id, stationCode);
				if(result == null){
					logger.error("amend incident error with WorldTracer");
					throw new WorldTracerConnectionException();
				}
				result = closeInc(fieldMap, wt_id, stationCode);
				if(result == null){
					logger.error("close incident error with WorldTracer");
					throw new WorldTracerConnectionException();
				}
			}
			return result;
		} catch (Exception e) {
			throw new WorldTracerConnectionException(
					"Communication error with WorldTracer", e);
		}
	}
	/**
	 * close an incident, return wt_id or null
	 * @param fieldMap
	 * @param wt_id
	 * @param stationCode
	 * @return wt_id or null
	 * @throws WorldTracerException
	 */
	public String closeInc(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String wt_id, String stationCode) throws WorldTracerException{
		lastFlowKey = null;
		String responseBody = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "closedelayedbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			System.out.println("start close incident flow not redirect!");
			return null;
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		//submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		search.setQueryString(p2);
		
		search.setParameter("wtrCloseRequest.recordReferences[0].stationCode", wt_id.substring(0,3));
		search.setParameter("wtrCloseRequest.recordReferences[0].airlineCode", wt_id.substring(3,5));
		search.setParameter("wtrCloseRequest.recordReferences[0].recordId", wt_id.substring(5));
		search.setParameter("amendOption[0]", "noamend");
		
		search.setParameter("_showAllDetailsInReadOnlyArea", "on");
		search.setParameter("_eventId", "continue");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.fullRecord", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.recordHistory", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.matchingHistory", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.SMSMessages", "on");
		search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.internetMessages", "on");
		
		try {
			debugOut(search, "");
			client.executeMethod(search);
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
				client.executeMethod(redirect);
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
				|| responseBody.toUpperCase().contains("CLOSE RECORD (FINISH)")){
			return wt_id;
		}else{
			return null;
		}
	}

/*	public String sendFwd(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String stationCode, String companyCode) throws WorldTracerException {
		
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { 
				new NameValuePair("_flowId", "sendmessage-flow"),
				new NameValuePair("isHDQ", "N"),
				};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
		}
		catch (HttpException e) {
			logger.error("error", e);;
		}
		catch (IOException e) {
			logger.error("error", e);;
		}
		finally {
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start forward message flow not redirect!");
			return null;
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//add params to forward
		PostMethod forwardMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		forwardMethod.setQueryString(p2);
		
		forwardMethod.setParameter("_eventId", "Send");
		forwardMethod.setParameter("isHDQ", "yes");
		forwardMethod.setParameter("sendMessageActionVO.sendToDestination", "ACTION_AREA_ADDRESSES");
		forwardMethod.setParameter("_sendMessageActionVO.schedule", "on");
		if (fieldMap.containsKey(WorldTracerField.FW)) {
			String fwdStation = fieldMap.get(WorldTracerField.FW).get(0);
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[0].stationCode", fwdStation.substring(0, 3));
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[0].airlineCode", fwdStation.substring(3));
			forwardMethod.setParameter("sendMessageActionVO.actionMessageAddresses[0].actionAreaType", "FORWARD_AREA");
		}
		//Teletype format important in WorldTracer
		//Should contain sequence of three alphabetic characters followed with                  //ABC
		//sequence of two alphanumeric characters and                                           //AB or 12
		//sequence of three alphabetic characters or sequence of two alpha numeric characters.  //ABC or AB or 12
		//tty:/^([a-zA-Z]{3}[a-zA-Z0-9]{2}[a-zA-Z0-9]{2}|[a-zA-Z]{3}[a-zA-Z0-9]{2}[a-zA-Z]{3})$/
		if (fieldMap.containsKey(WorldTracerField.TX)) {
			List<String> ttList = fieldMap.get(WorldTracerField.TX);
			for(int i = 0; i < ttList.size() && i < 4; i++) {
				String tty = ttList.get(i);
				Pattern pat = Pattern.compile("^([a-zA-Z]{3}[a-zA-Z0-9]{2}[a-zA-Z0-9]{2}|[a-zA-Z]{3}[a-zA-Z0-9]{2}[a-zA-Z]{3})$");
				Matcher mat = pat.matcher(tty);
				if(mat.matches()){
					forwardMethod.setParameter("sendMessageActionVO.ttyAddresses["+ i +"]", tty);
				}else{
					logger.error("Teletype Address error!");
					throw new WorldTracerException("Teletype Address error!");
				}
			}
		}
		//Expedite Number: aaaa; Passenger Name: xxx xxx xxx; From/To: xxx/xxx; 
		//Airline/Flight Number: us176; Depart Date: 03/26/2009; Tag Number: 12345; 
		//Reason for Loss: xx; Supplementary Information: xxx; 
		StringBuilder messageSB = new StringBuilder();
		if (fieldMap.containsKey(WorldTracerField.XT)) {
			messageSB.append("Expedite Number: ");
			messageSB.append(fieldMap.get(WorldTracerField.XT).get(0));
			messageSB.append("; ");
		}
		if (fieldMap.containsKey(WorldTracerField.NM)) {
			messageSB.append("Passenger Name:");
			List<String> nameList = fieldMap.get(WorldTracerField.NM);
			for(int i=0; i<nameList.size(); i++){
				messageSB.append(" " + nameList.get(i));
			}
			messageSB.append("; ");
		}
		if (fieldMap.containsKey(WorldTracerField.NR)) {
			messageSB.append("From/To:");
			List<String> routingList = fieldMap.get(WorldTracerField.NR);
			for(int i = 0; i < routingList.size(); i++){
				messageSB.append(" " + routingList.get(i));
			}
			messageSB.append("; ");
		}
		if (fieldMap.containsKey(WorldTracerField.NF)) {
			messageSB.append("Flight Number:");
			List<String> flightList = fieldMap.get(WorldTracerField.NF);
			for(int i = 0; i < flightList.size(); i++){
				messageSB.append(" " + flightList.get(i).split("/")[0]);
			}
			messageSB.append("; Depart Date:");
			for(int i = 0; i < flightList.size(); i++){
				messageSB.append(" " + flightList.get(i).split("/")[1]);
			}
			messageSB.append("; ");
		}
		if (fieldMap.containsKey(WorldTracerField.RL)) {
			messageSB.append("Reason for Loss: " + fieldMap.get(WorldTracerField.NR).get(0));
			messageSB.append("; ");
		}
		if (fieldMap.containsKey(WorldTracerField.SI)) {
			messageSB.append("Supplementary Information: " + fieldMap.get(WorldTracerField.SI).get(0));
			messageSB.append("; ");
		}
		if(messageSB.length() > 2){
			messageSB.delete(messageSB.length()-2, messageSB.length());
		}else{
			messageSB.append("no message");
		}
		forwardMethod.setParameter("wtrActionAreaWriteRequest.message", messageSB.toString());

		try {
			client.executeMethod(forwardMethod);
			if(forwardMethod.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY || forwardMethod.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY){
				newLocation = forwardMethod.getResponseHeader("location").getValue();
			}
			else{
				throw new WorldTracerException("forward message method not redirect!");
			}
		}
		catch (HttpException e) {
			logger.error("error", e);;
		}
		catch (IOException e) {
			logger.error("error", e);;
		}
		finally {
			forwardMethod.releaseConnection();
		}
		//redirect to submit result page and get message
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect);
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
		}
		catch (HttpException e) {
			logger.error("error", e);;
		}
		catch (IOException e) {
			logger.error("error", e);;
		}
		finally {
			redirect.releaseConnection();
		}
		String errorString;
		Pattern error_patt = Pattern.compile("error = '([^<>']+)'", 
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = error_patt.matcher(responseBody);
		if (m.find()) {
			errorString = m.group(1);
			logger.error(errorString);
		} else {
			errorString = OK;
		}
		if (OK.equals(errorString)) {
			return errorString;
		}
		throw new WorldTracerException(errorString);
	}
	*/
	
	public String sendFwd(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String stationCode, String companyCode) throws WorldTracerException {
		lastFlowKey = null;
		Pattern itinPatt = Pattern.compile("(\\w{2})(\\w+)/(\\w+)", Pattern.CASE_INSENSITIVE);
		String responseBody = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId",
				"createrushdbagrecord-flow") };
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerConnectionException("unable to send fwd", e);
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerConnectionException("unable to send fwd", e);
		} finally {
			startFlow.releaseConnection();
		}
		if (startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY
				&& startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start rush bag (FWD) flow not redirect!");
			throw new WorldTracerConnectionException("Fwd flow did not initiate");
		}
		// get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		// submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);

		search.setParameter("rushBagRecord.recordReference.stationCode", stationCode);
		search.setParameter("rushBagRecord.recordReference.airlineCode",
				companyCode);

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
								finalTag = LookupAirlineCodes.getTwoCharacterBagTag(rawTag);
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
								finalTag = LookupAirlineCodes.getTwoCharacterBagTag(rawTag);
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
			client.executeMethod(search);
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
				client.executeMethod(redirect);
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
		
		if (responseBody.toUpperCase().contains("RECORD CREATED SUCCESSFULLY")) {
			return "fwdSuccess";
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
				throw new WorldTracerAlreadyClosedException(errorString);
			}
			throw new WorldTracerException(errorString);
		}
	}



	public String insertOhd(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String companyCode, String stationCode) throws WorldTracerException {
		lastFlowKey = null;
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_OHD);
		String responseBody = null;
		try {
			GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
			NameValuePair[] p1 = { new NameValuePair("_flowId", "createonhandbagrecord-flow")};
			startFlow.setQueryString(p1);
			startFlow.setFollowRedirects(false);
			try {
				client.executeMethod(startFlow);
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
				startFlow.releaseConnection();
			}
			if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && 
					startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
				//return false;
				logger.error("start insert incident flow not redirect!");
				throw new WorldTracerException("start insert ohd flow not redirect!");
			}
			//get flow key
			String newLocation = startFlow.getResponseHeader("location").getValue();
			String flowKey = newLocation.split("=")[1];
			//submit form by post method
			PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
			for(Map.Entry<WorldTracerService.WorldTracerField, List<String>> fieldEntry : fieldMap.entrySet()){
				List<String> fieldList = fieldEntry.getValue();
				//add color type
				if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.CT){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.FD){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.RT){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.IT){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.passenger.initials[0]", fieldList.get(0));
					}
				}
				//add passenger name
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.NM){
					if(fieldList != null && fieldList.size() >= 1){
						String name = RULES.get(WorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(0));
						search.setParameter("onHandBagRecord.passenger.names[0]", name);
					}
				}
				//add passenger first name/title
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.PT){
					if(fieldList != null && fieldList.size() >= 1){
						String title = RULES.get(WorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
						search.setParameter("onHandBagRecord.passenger.title", title);
					}
				}
				//add passenger address on bag
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.AB){
					if(fieldList != null && fieldList.size() >= 1){
						String ab = RULES.get(WorldTracerService.WorldTracerField.AB).formatEntry(fieldList.get(0));
						search.setParameter("onHandBagRecord.onHandBag.bagAddress.line1", ab.replace(".", "&#46;"));
					}
				}
				//add passenger phones on bag
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.PN || fieldEntry.getKey() == WorldTracerService.WorldTracerField.TP){
					fieldList = fieldMap.get(WorldTracerService.WorldTracerField.PN);
					List<String> workPhoneList = fieldMap.get(WorldTracerService.WorldTracerField.TP);
					String phone = "";
					if(fieldList != null && fieldList.size() >= 1){
						if(fieldList.get(0).length()>0)
							phone = RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(fieldList.get(0)) + ",";
					}
					if(workPhoneList != null && workPhoneList.size() >= 1){
						if(workPhoneList.get(0).length()>0){
							phone += RULES.get(WorldTracerService.WorldTracerField.PN).formatEntry(workPhoneList.get(0));
						}
					}
					if(phone.endsWith(",")){
						phone = phone.substring(0, phone.length()-1);
					}
					search.setParameter("onHandBagRecord.onHandBag.bagPhones[0]", phone);
				}
				//add passenger mobile phone
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.CP){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.onHandBag.bagPhones[1]", RULES.get(WorldTracerService.WorldTracerField.CP).formatEntry(fieldList.get(0)));
					}
				}
				//add airline code and tagNumber: airlineCode(2 alphabet)+tagNumber(6 digits) or 10 digits(US),auto handle
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.TN){
					if(fieldList != null && fieldList.size() >= 1){
						String airtag = fieldList.get(0);
						search.setParameter("onHandBagRecord.onHandBag.bagTag.airlineCode", airtag.substring(0, 2));
						search.setParameter("onHandBagRecord.onHandBag.bagTag.tagNumber", airtag.substring(2));
					}
				}
				//add brand information 
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.BI){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.onHandBag.brandInfo.brandInformation", RULES.get(WorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0)));
					}
				}
				//add contents
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.CC){
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
				else if(fieldEntry.getKey() == WorldTracerService.WorldTracerField.SL){
					if(fieldList != null && fieldList.size() >= 1){
						search.setParameter("onHandBagRecord.onHandBag.storageLocation", RULES.get(WorldTracerService.WorldTracerField.SL).formatEntry(fieldList.get(0)).replace(".", "&#46;"));
					}
				}
			}
			search.setParameter("_flowExecutionKey", flowKey);
			search.setParameter("flowExecutionKey_Passenger", flowKey);
			search.setParameter("flowExecutionKey_OnhandBags", flowKey);
			search.setParameter("onHandBagRecord.recordReference.airlineCode", companyCode);
			search.setParameter("onHandBagRecord.recordReference.stationCode", stationCode);
			
			search.setParameter("userLang", "1");
			search.setParameter("onHandBagRecord.tracingOption", "FULLTRACING");
			search.setParameter("_eventId", "submit");
			search.setParameter("bagType", "OnHand");
			
			try {
				debugOut(search, "");
				client.executeMethod(search);
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
				client.executeMethod(redirect);
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
		Pattern succePatt = Pattern.compile("<SPAN>([^<>]+)\\[ACTIVE\\/TRACING]<\\/SPAN>",Pattern.CASE_INSENSITIVE);
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
			return wt_id;
		} else {
			throw new WorldTracerException(errorString);
		}
	}

	public String closeOhd(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String wt_id, String wt_stationcode) throws WorldTracerException {
		lastFlowKey = null;
		if(wt_id == null || wt_id.trim().length() < 1){
			return null;
		}
		String responseBody = null;
		try {
			GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
			NameValuePair[] p1 = { new NameValuePair("_flowId", "closeonhandbagrecord-flow")};
			startFlow.setQueryString(p1);
			startFlow.setFollowRedirects(false);
			try {
				client.executeMethod(startFlow);
			}
			catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to close ohd");
			}
			catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to close ohd");
			}
			finally {
				startFlow.releaseConnection();
			}
			if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
				logger.error("start flow not redirect!");
				throw new WorldTracerException("unable to close ohd");
			}
			//get flow key
			String newLocation = startFlow.getResponseHeader("location").getValue();
			String flowKey = newLocation.split("=")[1];
			//submit close form by post method
			PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
			NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
			search.setQueryString(p2);
			
			search.setParameter("wtrCloseRequest.recordReferences[0].stationCode", wt_id.substring(0,3));
			search.setParameter("wtrCloseRequest.recordReferences[0].airlineCode", wt_id.substring(3,5));
			search.setParameter("wtrCloseRequest.recordReferences[0].recordId", wt_id.substring(5));
			search.setParameter("amendOption[0]", "noamend");
			
			search.setParameter("_showAllDetailsInReadOnlyArea", "on");
			search.setParameter("_eventId", "continue");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.fullRecord", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.recordHistory", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.matchingHistory", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.SMSMessages", "on");
			search.setParameter("_wtrDisplayRequest.recordAdditionalInfo.internetMessages", "on");
			
			try {
				debugOut(search, "");
				client.executeMethod(search);
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
					client.executeMethod(redirect);
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
					|| responseBody.toUpperCase().contains("FILE CLOSED")){
				return wt_id;
			} else {
				logger.error("Close onHand bag error");
				throw new WorldTracerException();
			}
			
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
	}

	public void eraseActionFile(String station_id, String companyCode,
			ActionFileType area, int day, int itemNum)
			throws WorldTracerException {
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = {
				new NameValuePair("_flowId", "erasemessages-flow"),
				new NameValuePair("isHDQ", "N") };
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to erase action file", e);
		} finally {
			startFlow.releaseConnection();
		}
		if (startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY
				&& startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start action file count flow not redirect!");
			throw new WorldTracerException("unable to erase action file");
			
		}
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		// find the file
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaReadRequest.stationCode", station_id);
		search.setParameter("wtrActionAreaReadRequest.airlineCode", companyCode);
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.stationCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.airlineCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.recordId", "");
		search.setParameter("searchMessagesVO.startNumber", Integer.toString(itemNum));
		search.setParameter("searchMessagesVO.endNumber", Integer.toString(itemNum));
		search.setParameter("_eventId", "Refresh");
		search.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", area.areaId());
		search.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + Integer.toString(day));

		try {
			debugOut(search, "");
			client.executeMethod(search);
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
			client.executeMethod(redirect);
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
		search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		p2[0] = new NameValuePair("_flowExecutionKey", flowKey);
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaReadRequest.stationCode", station_id);
		search.setParameter("wtrActionAreaReadRequest.airlineCode", companyCode);
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.stationCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.airlineCode", "");
		search.setParameter("wtrActionAreaReadRequest.searchByRecordReference.recordId", "");
		search.setParameter("searchMessagesVO.startNumber", Integer.toString(itemNum));
		search.setParameter("searchMessagesVO.endNumber", Integer.toString(itemNum));
		search.setParameter("_eventId", "Erase");
		search.setParameter("wtrActionAreaReadRequest.actionAreaName.actionAreaType", area.areaId());
		search.setParameter("wtrActionAreaReadRequest.actionAreaDayNumber", "DAY_" + Integer.toString(day));
		search.setParameter("1[]", "checkbox");
		search.setParameter("searchMessagesVO.messageListItems[0].checked", "true");
		search.setParameter("_searchMessagesVO.messageListItems[0].checked", "on");

		try {
			debugOut(search, "");
			client.executeMethod(search);
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

	public String findAHL(String wt_id) throws WorldTracerException {
		lastFlowKey = null;
		String responseBody = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "displaydelayedbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start flow not redirect!");
			throw new WorldTracerException("unable to load ahl");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		//submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		search.setQueryString(p2);
		
		search.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_id.substring(0,3));
		search.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_id.substring(3,5));
		search.setParameter("wtrDisplayRequest.recordReference.recordId", wt_id.substring(5));
		search.setParameter("resultsForm.sortOption", "recordReference");
		search.setParameter("_eventId", "displayrecord");
		
		try {
			debugOut(search, "");
			client.executeMethod(search);
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
				client.executeMethod(redirect);
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
			throw new WorldTracerRecordNotFoundException(wt_id);
		} else {
			return responseBody;
		}
		
	}

	public String findOHD(String wt_id) throws WorldTracerException {
		lastFlowKey = null;
		String responseBody = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "displayonhandbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start flow not redirect!");
			return null;
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		//submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		search.setQueryString(p2);
		
		search.setParameter("wtrDisplayRequest.recordReference.stationCode", wt_id.substring(0,3));
		search.setParameter("wtrDisplayRequest.recordReference.airlineCode", wt_id.substring(3,5));
		search.setParameter("wtrDisplayRequest.recordReference.recordId", wt_id.substring(5));
		search.setParameter("resultsForm.sortOption", "recordReference");
		search.setParameter("_eventId", "displayrecord");
		
		try {
			debugOut(search, "");
			client.executeMethod(search);
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
				client.executeMethod(redirect);
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
			throw new WorldTracerRecordNotFoundException();
		} else {
//			logger.error("Close onHand bag error");
//			throw new WorldTracerException();
			return responseBody;
		}
	}


	public void suspendAHL(String wt_id, String agent)
			throws WorldTracerException {
		lastFlowKey = null;
		String responseBody = susritItem(wt_id, "SUS", "AHL", agent);
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
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void suspendOHD(String wt_id, String agent)
			throws WorldTracerException {
		lastFlowKey = null;
		String responseBody = susritItem(wt_id, "SUS", "OHD", agent);
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
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void reinstateAHL(String wt_id, String agent)
			throws WorldTracerException {
		lastFlowKey = null;
		String responseBody = susritItem(wt_id, "RIT", "AHL", agent);
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
			return;
		}
		throw new WorldTracerException(errorString);
	}

	public void reinstateOHD(String wt_id, String agent)
			throws WorldTracerException {
		lastFlowKey = null;
		String responseBody = susritItem(wt_id, "RIT", "OHD", agent);
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
			return;
		}
		throw new WorldTracerException(errorString);
	}

	private String susritItem(String wt_id, String action, String type,
			String agent) throws WorldTracerException {
		lastFlowKey = null;
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
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", flowId)};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start flow not redirect!");
			throw new WorldTracerException("unable to sus/rit item");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		//submit to suspend/reinstate
		PostMethod amend = new PostMethod("/WorldTracerWeb/wtwflow.do");
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
			client.executeMethod(amend);
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
			client.executeMethod(redirect);
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

	public String forwardOhd(Map<WorldTracerField, List<String>> fieldMap,
			String ohd_id, String ahl_id) throws WorldTracerException {
		lastFlowKey = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "forwardbag-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start forward onhand flow not redirect!");
			throw new WorldTracerException("unable to forward ohd");
		}
		//get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//if wt_ohd_id exists
		PostMethod getOnhand = new PostMethod(newLocation);
		NameValuePair[] pg = { new NameValuePair("_flowExecutionKey", flowKey)};
		getOnhand.setQueryString(pg);
		getOnhand.setParameter("wtrDisplayRequest.recordReference.stationCode", ohd_id.substring(0, 3));
		getOnhand.setParameter("wtrDisplayRequest.recordReference.airlineCode", ohd_id.substring(3, 5));
		getOnhand.setParameter("wtrDisplayRequest.recordReference.recordId", ohd_id.substring(5));
		getOnhand.setParameter("resultsForm.sortOption", "recordReference");
		getOnhand.setParameter("_eventId", "continue");
		try {
			debugOut(getOnhand, "");
			client.executeMethod(getOnhand);
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
			client.executeMethod(getWtDetails);
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
		PostMethod forwardMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey)};
		forwardMethod.setQueryString(p2);
		
		forwardMethod.setParameter("_eventId", "submit");
		forwardMethod.setParameter("onHandOrQuickOnHand", "onHand");
		forwardMethod.setParameter("wtrForwardRequest.sendHandledAirlineCopy", "NO");
		
		forwardMethod.setParameter("wtrForwardRequest.delayedBagReference.stationCode", ahl_id.substring(0, 3));
		forwardMethod.setParameter("wtrForwardRequest.delayedBagReference.airlineCode", ahl_id.substring(3, 5));
		forwardMethod.setParameter("wtrForwardRequest.delayedBagReference.recordId", ahl_id.substring(5));
		
		forwardMethod.setParameter("wtrForwardRequest.onHandRecords[0].stationCode", ohd_id.substring(0, 3));
		forwardMethod.setParameter("wtrForwardRequest.onHandRecords[0].airlineCode", ohd_id.substring(3, 5));
		forwardMethod.setParameter("wtrForwardRequest.onHandRecords[0].recordId", ohd_id.substring(5));
		
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
//		if (fieldMap.containsKey(WorldTracerField.TX)) {
//			List<String> ttList = fieldMap.get(WorldTracerField.TX);
//			for (int i = 0; i < ttList.size() && i < 4; i++) {
//				String tt = ttList.get(i);
//				method.addParameter("TX" + (i + 1), tt);
//			}
//		}

		try {
			debugOut(forwardMethod, "");
			client.executeMethod(forwardMethod);
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
			client.executeMethod(redirect);
			responseBody = getStringFromInputStream(redirect.getResponseBodyAsStream());
			//System.out.println("response body:" + responseBody);
			//return true;
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
			return errorString;
		}
		throw new WorldTracerException(errorString);
	}

	/**
	 * 
	 */
	public String createBdo(Map<WorldTracerField, List<String>> fieldMap,
			String ahl_id, String ohd_id, DeliverCompany delivercompany,
			Station station) throws WorldTracerException {
		lastFlowKey = null;
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_BDO);
		
		String responseBody = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId",
				"deliverDelayedBag-flow") };
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to create bdo", e);
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to create bdo", e);
		} finally {
			startFlow.releaseConnection();
		}
		if (startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY
				&& startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start bdo flow not redirect!");
			throw new WorldTracerException("unable to send BDO");
		}
		// get flow key
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		// submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);

		search.setParameter("wtrDisplayRequest.recordReference.stationCode",
				ahl_id.substring(0, 3));
		search.setParameter("wtrDisplayRequest.recordReference.airlineCode",
				ahl_id.substring(3, 5));
		search.setParameter("wtrDisplayRequest.recordReference.recordId",
				ahl_id.substring(5));

		search.setParameter("wtrDeliveryRequest.deliverySationCode", ahl_id
				.substring(0, 3));

		search.setParameter("_eventId", "submit");

		try {
			debugOut(search, "");
			client.executeMethod(search);
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
				client.executeMethod(redirect);
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

		search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		search.setQueryString(p2);
		search.setParameter(
				"deliveryOrderVO.deliveryorder.deliveryservicecompany", "01");
		search.setParameter("deliveryOrderVO.deliveryOrder.deliveryDate",
				fieldMap.get(WorldTracerField.DD).get(0));
		search.setParameter("deliveryOrderVO.deliveryAddr1", "true");
		String da = fieldMap.get(WorldTracerField.DA).get(0);
		Pattern daPatt = Pattern.compile("(.{1,55})( (.{1,55}))?");
		Matcher m = daPatt.matcher(da);
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
					search.setParameter("deliveryOrderVO.colorType"
							+ addedCount, "true");
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
				station.getWt_stationcode());
		search.setParameter("_eventId", "submit");
		try {
			debugOut(search, "");
			client.executeMethod(search);
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
				client.executeMethod(redirect);
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
		if(! responseBody.toUpperCase().contains("SUCCESSFULLY DELIVERED THE GIVEN ORDER")) {
			throw new WorldTracerException("unable to send BDO");
		}
		return "bdo sent";
	}
	/**
	 * update claim information before close an incident, return wt_id or null
	 * @param fieldMap
	 * @param wt_id
	 * @param stationCode
	 * @return wt_id or null
	 * @throws WorldTracerException
	 */
	public String amendBeforeClose(
			Map<WorldTracerService.WorldTracerField, List<String>> fieldMap,
			String wt_id, String stationCode) throws WorldTracerException{
		lastFlowKey = null;
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "editdelayedbagrecord-flow")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
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
		String flowKey = newLocation.split("=")[1];
		String responseBody = null;
		//The field starts with avoidBinding... means no change in WorldTracer. So it is not necessory to retrieve details.
		PostMethod amendMethod = new PostMethod("/WorldTracerWeb/wtwflow.do");
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
		List<String> faultStationList = fieldMap.get(WorldTracerService.WorldTracerField.FS);
		if(faultStationList != null && faultStationList.size() > 0){
			amendMethod.setParameter("delayedBagRecord.delayedBagClaim.faultStation", faultStationList.get(0));
		}
		List<String> reasonCodeList = fieldMap.get(WorldTracerService.WorldTracerField.RL);
		if(reasonCodeList != null && reasonCodeList.size() > 0){
			amendMethod.setParameter("delayedBagRecord.delayedBagClaim.reasonForLoss", reasonCodeList.get(0));
		}
		List<String> commentList = fieldMap.get(WorldTracerService.WorldTracerField.RC);
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
			client.executeMethod(amendMethod);
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
			client.executeMethod(redirect);
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
		if(responseBody.toUpperCase().contains("RECORD AMENDED SUCCESSFULLY")){
			return wt_id;
		}else{
			logger.error("amend incident failed");
			return null;
		}
	}
	/**
	 * get String From InputStream, failed return null
	 * @param is inputStream
	 * @return String or null
	 * @throws IOException
	 */
	public String getStringFromInputStream(InputStream is) throws IOException{
		lastFlowKey = null;
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
		//is.close();
		
		return new String(replySB);
	}

	public EnumMap<ActionFileType, int[]> getActionFileCounts(
		String companyCode, String wtStation) throws WorldTracerException {
		GetMethod startFlow = new GetMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p1 = { new NameValuePair("_flowId", "actionfilestation-flow"), new NameValuePair("isHDQ", "N")};
		startFlow.setQueryString(p1);
		startFlow.setFollowRedirects(false);
		try {
			client.executeMethod(startFlow);
		}
		catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to get action file counts", e);
		}
		catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to get action file counts", e);
		}
		finally {
			startFlow.releaseConnection();
		}
		if(startFlow.getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY && startFlow.getStatusCode() != HttpStatus.SC_MOVED_PERMANENTLY) {
			logger.error("start action file count flow not redirect!");
			throw new WorldTracerException("unable to get action file counts");
		}
		String newLocation = startFlow.getResponseHeader("location").getValue();
		String flowKey = newLocation.split("=")[1];
		// submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaRequest.stationCode", wtStation);
		search.setParameter("wtrActionAreaRequest.airlineCode", companyCode);
		search.setParameter("_multipleMessage", "on");
		search.setParameter("manageSelectAreaOrDayVO.timeInterval", "-1");
		search.setParameter("manageSelectAreaOrDayVO.actionAreaName", "FORWARD_AREA");
		search.setParameter("manageSelectAreaOrDayVO.expandedChar", "");
		search.setParameter("manageSelectAreaOrDayVO.day", "DAY_1");
		search.setParameter("manageSelectAreaOrDayVO.errroStatus", "false");
		search.setParameter("_eventId", "refresh");
		try {
			debugOut(search, "");
			client.executeMethod(search);
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
				client.executeMethod(redirect);
				inStream = redirect.getResponseBodyAsStream();
				lastFlowKey = flowKey;
				return ParsingUtils.parseActionFileCounts(inStream, "ISO-8859-1");
				// System.out.println("close incident result:" + responseBody);
			} catch (HttpException e) {
				throw new WorldTracerException(e);
			} catch (IOException e) {
				throw new WorldTracerException(e);
			} catch (ParserException e) {
				throw new WorldTracerException(e);
			} finally {
				if(inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						logger.debug("unable to close input stream");
					}
				}
				redirect.releaseConnection();
				
			}
		}
		else {
			throw new WorldTracerException("unable to get action file counts");
		}
	}


	public List<Worldtracer_Actionfiles> getActionFiles(String companyCode,
			String stationCode, ActionFileType afType, int day, int count) throws WorldTracerException {
		String flowKey = lastFlowKey;
		ArrayList<Worldtracer_Actionfiles> afList = new ArrayList<Worldtracer_Actionfiles>();
		// submit close form by post method
		PostMethod search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		NameValuePair[] p2 = { new NameValuePair("_flowExecutionKey", flowKey) };
		search.setQueryString(p2);
		search.setParameter("wtrActionAreaRequest.stationCode", stationCode);
		search.setParameter("wtrActionAreaRequest.airlineCode", companyCode);
		search.setParameter("_multipleMessage", "on");
		search.setParameter("manageSelectAreaOrDayVO.timeInterval", "-1");
		search.setParameter("manageSelectAreaOrDayVO.actionAreaName", afType.areaId());
		search.setParameter("manageSelectAreaOrDayVO.expandedChar", "");
		search.setParameter("manageSelectAreaOrDayVO.day", "DAY_" + day);
		search.setParameter("manageSelectAreaOrDayVO.errroStatus", "true");
		search.setParameter("_eventId", "Send");
		String newLocation = null;
		try {
			debugOut(search, "");
			client.executeMethod(search);
			if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				newLocation = search.getResponseHeader("location").getValue();
			} else {
				newLocation = "";
				logger.error("actionfile refresh post not redirect!");
			}
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to get action files", e);
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to get action files", e);
		} finally {
			search.releaseConnection();
		}
		InputStream inStream = null;
		String[] details = null;
		if (!"".equals(newLocation)) {
			flowKey = newLocation.split("=")[1];
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect);
				inStream = redirect.getResponseBodyAsStream();
				lastFlowKey = flowKey;
				details = ParsingUtils.parseActionFileDetail(inStream, "ISO-8859-1");
				Worldtracer_Actionfiles af = new Worldtracer_Actionfiles();
				af.setAction_file_text(details[1]);
				af.setAction_file_type(afType);
				af.setAirline(companyCode);
				af.setDay(day);
				af.setDeleted(false);
				af.setItem_number(Integer.parseInt(details[0]));
				af.setStation(stationCode);
				afList.add(af);
			} catch (Exception e) {
				throw new WorldTracerException(e);
			} finally {
				if(inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						logger.debug("unable to close input stream");
					}
				}
				redirect.releaseConnection();
				
			}
			
		}
		for(int i = 1; i < count; i++ ) {
			search = new PostMethod("/WorldTracerWeb/wtwflow.do");
			p2[0] = new NameValuePair("_flowExecutionKey", flowKey);
			search.setQueryString(p2);
			search.setParameter("detailedViewVO.searchMessageId", "1");
			search.setParameter("startRange", "1");
			search.setParameter("endRange", "99");
			search.setParameter("currentMsg", details != null && details.length > 0 ? details[0] : Integer.toString(i));
			search.setParameter("_eventId", "Next");
			search.setParameter("copyMessageActionVO.destinationType", "teleType");
			search.setParameter("copyMessageActionVO.actionMessageAddresses[0].actionAreaType", "ADDITIONAL_PROMPT_AREA");
			search.setParameter("copyMessageActionVO.actionMessageAddresses[1].actionAreaType", "ADDITIONAL_PROMPT_AREA");
			search.setParameter("copyMessageActionVO.actionMessageAddresses[2].actionAreaType", "ADDITIONAL_PROMPT_AREA");
			search.setParameter("copyMessageActionVO.actionMessageAddresses[3].actionAreaType", "ADDITIONAL_PROMPT_AREA");
			search.setParameter("copyMessageActionVO.actionMessageAddresses[4].actionAreaType", "ADDITIONAL_PROMPT_AREA");
			search.setParameter("afterDMFService", "0");
			search.setParameter("notValidDMF", "false");
			search.setParameter("containsOHDRecord", "false");
			search.setParameter("wtrActionAreaTransferReadRequest.searchByRecordReference.airlineCode", companyCode);
			search.setParameter("successMessage", "4");
			try {
				debugOut(search, "");
				client.executeMethod(search);
				if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
						|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
					newLocation = search.getResponseHeader("location").getValue();
				} else {
					newLocation = "";
					logger.debug("actionfile next mesage not redirect!");
					break;
				}
			} catch (HttpException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to get action files", e);
			} catch (IOException e) {
				logger.error("error", e);
				throw new WorldTracerException("unable to get action files", e);
			} finally {
				search.releaseConnection();
			}
			flowKey = newLocation.split("=")[1];
			GetMethod redirect = new GetMethod(newLocation);
			try {
				client.executeMethod(redirect);
				inStream = redirect.getResponseBodyAsStream();
				lastFlowKey = flowKey;
				details = ParsingUtils.parseActionFileDetail(inStream, "ISO-8859-1");
				Worldtracer_Actionfiles af = new Worldtracer_Actionfiles();
				af.setAction_file_text(details[1]);
				af.setAction_file_type(afType);
				af.setAirline(companyCode);
				af.setDay(day);
				af.setDeleted(false);
				af.setItem_number(Integer.parseInt(details[0]));
				af.setStation(stationCode);
				afList.add(af);
			} catch (Exception e) {
				throw new WorldTracerException(e);
			} finally {
				if(inStream != null) {
					try {
						inStream.close();
					} catch (IOException e) {
						logger.debug("unable to close input stream");
					}
				}
				redirect.releaseConnection();
				
			}
		}
		//finally we need to go back to the count screen
		search = new PostMethod("/WorldTracerWeb/wtwflow.do");
		p2[0] = new NameValuePair("_flowExecutionKey", flowKey);
		search.setQueryString(p2);
		search.setParameter("detailedViewVO.searchMessageId", "1");
		search.setParameter("startRange", "1");
		search.setParameter("endRange", "99");
		search.setParameter("currentMsg", details != null && details.length > 0 ? details[0] : Integer.toString(count));
		search.setParameter("_eventId", "back");
		search.setParameter("copyMessageActionVO.destinationType", "teleType");
		search.setParameter("copyMessageActionVO.ttyAddresses[0]", "");
		search.setParameter("copyMessageActionVO.ttyAddresses[1]", "");
		search.setParameter("copyMessageActionVO.ttyAddresses[2]", "");
		search.setParameter("copyMessageActionVO.ttyAddresses[3]", "");
		search.setParameter("copyMessageActionVO.ttyAddresses[4]", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[0].stationCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[0].airlineCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[0].actionAreaType", "ADDITIONAL_PROMPT_AREA");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[1].stationCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[1].airlineCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[1].actionAreaType", "ADDITIONAL_PROMPT_AREA");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[2].stationCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[2].airlineCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[2].actionAreaType", "ADDITIONAL_PROMPT_AREA");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[3].stationCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[3].airlineCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[3].actionAreaType", "ADDITIONAL_PROMPT_AREA");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[4].stationCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[4].airlineCode", "");
		search.setParameter("copyMessageActionVO.actionMessageAddresses[4].actionAreaType", "ADDITIONAL_PROMPT_AREA");
		search.setParameter("afterDMFService", "0");
		search.setParameter("notValidDMF", "false");
		search.setParameter("containsOHDRecord", "false");
		search.setParameter("wtrActionAreaTransferReadRequest.recordType", "");
		search.setParameter("wtrActionAreaTransferReadRequest.searchByRecordReference.stationCode", "");
		search.setParameter("wtrActionAreaTransferReadRequest.searchByRecordReference.airlineCode", companyCode);
		search.setParameter("wtrActionAreaTransferReadRequest.searchByRecordReference.recordId", "");
		search.setParameter("successMessage", "4");
		try {
			debugOut(search, "");
			client.executeMethod(search);
			if (search.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY
					|| search.getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY) {
				newLocation = search.getResponseHeader("location").getValue();
			} else {
				newLocation = "";
				logger.debug("actionfile next mesage not redirect!");
				throw new WorldTracerException("broken trying to get through action files");
			}
		} catch (HttpException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to get action files", e);
		} catch (IOException e) {
			logger.error("error", e);
			throw new WorldTracerException("unable to get action files", e);
		} finally {
			search.releaseConnection();
		}
		flowKey = newLocation.split("=")[1];
		lastFlowKey = flowKey;
		GetMethod redirect = new GetMethod(newLocation);
		try {
			client.executeMethod(redirect);
		} catch (Exception e) {
			throw new WorldTracerException(e);
		} finally {
			redirect.releaseConnection();
		}
		
		return afList;
	}

	public void logout() {
		if (pool != null) {
			try {
				pool.returnObject(client);
			} catch (Exception e) {
				logger.error("Unable to return client to pool", e);
			}
		}
	}

	public void setWtCompanycode(String wtCompanycode) {
		this.wtCompanycode = wtCompanycode;
	}

	public void setPool(WorldTracerConnectionPool pool) {
		this.pool = pool;
	}

	public void setWtRuleMap(RuleMapper wtRuleMap) {
		this.wtRuleMap = wtRuleMap;
	}

	
}
