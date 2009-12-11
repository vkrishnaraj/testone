package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.log4j.Logger;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.iata.www.iata._2007._00.ErrorType;

import aero.DelayedService2;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.Expenses;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.PreProcessor;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.TxType;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.connection.WorldTracerHttpClient;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.BasicRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.RuleMapper;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.UsWorldTracerRuleMap;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService;
import aero.sita.www.bag.wtr._2009._01.BagDescType;
import aero.sita.www.bag.wtr._2009._01.BagElmsType;
import aero.sita.www.bag.wtr._2009._01.BagMatrlType;
import aero.sita.www.bag.wtr._2009._01.BagTagType;
import aero.sita.www.bag.wtr._2009._01.ColorCodeType;
import aero.sita.www.bag.wtr._2009._01.ColorTypeDescType;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType;
import aero.sita.www.bag.wtr._2009._01.ContentType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagType;
import aero.sita.www.bag.wtr._2009._01.DelayedClaimType;
import aero.sita.www.bag.wtr._2009._01.FlightDateType;
import aero.sita.www.bag.wtr._2009._01.FlightSegmentType;
import aero.sita.www.bag.wtr._2009._01.OriginDestinationType;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryType;
import aero.sita.www.bag.wtr._2009._01.PassengerPaymentType;
import aero.sita.www.bag.wtr._2009._01.RecordReferenceType;
import aero.sita.www.bag.wtr._2009._01.RecordType;
import aero.sita.www.bag.wtr._2009._01.StationAirlineType;
import aero.sita.www.bag.wtr._2009._01.WTRBagsCreateRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReinstateRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRStatusRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRSuspendRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRTracingStateChangeRQType;
import aero.sita.www.bag.wtr._2009._01.BagElmsType.Enum;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType.PermanentAddress;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType.TempAddress.Address;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryType.Itinerary.FlightSegments;
import aero.sita.www.bag.wtr._2009._01.PassengerPaymentType.Amount;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument.WTRDelayedBagsCreateRQ;
import aero.sita.www.bag.wtr.delayedbagservice.DelayedBagServiceStub;
import aero.sita.www.bag.wtr.onhandbagservice.OnhandBagServiceStub;

public class WorldTracerServiceImpl implements WorldTracerService {

	// TODO: MARKER 1: Potentially Delivery Address - Appears unecessary until BDO
	// TODO: MARKER 2: NO STATE IN WEB SERVICES - Is not in WS model
	
	WorldTracerHttpClient client = null;

	private static final Logger logger = Logger.getLogger(WorldTracerServiceImpl.class);
	public static final String WTRWEB_FLOW_URL = "/WorldTracerWeb/wtwflow.do";
	private static final Pattern FLOW_PATTERN = Pattern.compile("_flowExecutionKey=(.*)$");
	private static final Pattern BDO_ERROR_PATTERN = Pattern.compile("\\berror\\s*=\\s*'([^']+?)'", Pattern.CASE_INSENSITIVE);
	private static final String OK = "OK";
	private static final String ALREADY_REINSTATED = "ALREADY REINSTATED";
	private static final String ALREADY_SUSPENDED = "ALREADY SUSPENDED";
	private String flowKey = null;
	private static final int MAX_NAME_LENGTH = 20;
	private static final int MAX_ADDRESS_LINE = 50;
	private static final int MAX_PHONE_LENGTH = 50;

	private static final String DEFAULT_CLAIM_CURRENCY_CODE = "USD";
	private static BigDecimal VERSION_0_PT_1 = new BigDecimal(0.1).setScale(1, RoundingMode.HALF_UP);
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);
	
	// String endpoint = "http://chocolate.nettracer.aero:8080";
	String delayedEndpoint = "https://webservice-qa.worldtracer.aero/DelayedBagService/0.1";
	String onhandEndpoint = "https://webservice-qa.worldtracer.aero/OnhandBagService/0.1";
	
	

	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format.FREE_FLOW);

	// TODO: UNDO
	private static final boolean FORCE_FAILURE = true;

	RuleMapper wtRuleMap = new UsWorldTracerRuleMap();

	public WorldTracerServiceImpl(WorldTracerActionDTO dto) {

	}

	public void insertBdo(WorldTracerActionDTO dto, Bdo bdo, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	public void eraseActionFile(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	public void requestOhd(WorldTracerActionDTO dto, RequestOhd data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	public void requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	public void getActionFileDetails(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	public void getActionFileSummary(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException("Method not available via web services.");
	}

	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(DelayedService2.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}

	private GregorianCalendar getCalendarFromString(String v) throws ParseException {
		Date d = ITIN_DATE_FORMAT.parse(v);
		GregorianCalendar x = new GregorianCalendar();
		GregorianCalendar y = new GregorianCalendar();
		y.setTime(d);
		
		y.set(Calendar.YEAR, x.get(Calendar.YEAR));
		if (y.getTimeInMillis() > x.getTimeInMillis()) {
			y.add(Calendar.YEAR, -1);
		}
		return y;
	}
	
	private static void configureClient(org.apache.axis2.client.Stub stub) throws AxisFault {

		System.setProperty("javax.net.ssl.trustStore", "c:\\jdk\\jre\\lib\\security\\cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

		ServiceClient client = stub._getServiceClient();

		client.engageModule("rampart");
		try {
			client.getAxisService().getPolicySubject().attachPolicy(loadPolicy("/policy.xml"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Phase> phases = client.getAxisConfiguration().getInFlowPhases();
		AxisConfiguration config = client.getAxisConfiguration();

		for (Phase p : phases) {
			HandlerDescription removeThis = null;
			System.out.println("Phase: " + p.getPhaseName());
			if (p.getPhaseName().equals("Security") || p.getPhaseName().equals("Dispatch")) {
				List<Handler> l = p.getHandlers();
				for (Handler h : l) {
					System.out.println("  Handler: " + h.getName());
					if (h.getName().equals("Apache Rampart inflow handler") || h.getName().equals("Post dispatch security verification handler")) {
						removeThis = h.getHandlerDesc();
					}
				}
			}

			if (removeThis != null) {
				p.removeHandler(removeThis);
			}
		}
	}

	public void amendOhd(WorldTracerActionDTO dto, Ohd data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void closeOhd(WorldTracerActionDTO dto, Ohd data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void createOhd(WorldTracerActionDTO dto, Ohd data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void forwardOhd(WorldTracerActionDTO dto, ForwardOhd data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void getActionFileCounts(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void getOhd(WorldTracerActionDTO dto, Ohd data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void sendForwardMessage(WorldTracerActionDTO dto, ForwardMessage msg, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void reinstateOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {
		// TODO Auto-generated method stub
		try {
			
			OnhandBagServiceStub stub = new OnhandBagServiceStub(onhandEndpoint);
			configureClient(stub);

			WTRReinstateRecordsRQDocument d = WTRReinstateRecordsRQDocument.Factory
			    .newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRReinstateRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
			    dto.getUser().getProfile().getAirline());

			
			d1.setRecordType(RecordType.ON_HAND);
			RecordReferenceType r = d1.addNewRecords().addNewRecord()
			    .addNewRecordReference();
			String ohdId = ohd.getOhdId();

			r.setAirlineCode(ohdId.substring(0, 3));
			r.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			r.setStationCode(ohdId.substring(3, 5));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				logger.info(d);
				if (FORCE_FAILURE) {
					return;
				}
				wsresponse = stub.reinstate(d);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
			    && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
				    && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors()
					    .getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error("Web Service Error Message: " + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = "Unknown Failure";
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(
			    "Web Service Connection Issue", axisFault);
			throw e;
		}

	}

	public void suspendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {

		try {
			
			OnhandBagServiceStub stub = new OnhandBagServiceStub(onhandEndpoint);
			configureClient(stub);

			WTRSuspendRecordsRQDocument d = WTRSuspendRecordsRQDocument.Factory
			    .newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRSuspendRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
			    dto.getUser().getProfile().getAirline());

			
			d1.setRecordType(RecordType.ON_HAND);
			RecordReferenceType r = d1.addNewRecords().addNewRecord()
			    .addNewRecordReference();
			String ohdId = ohd.getOhdId();

			r.setAirlineCode(ohdId.substring(0, 3));
			r.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			r.setStationCode(ohdId.substring(3, 5));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				logger.info(d);
				if (FORCE_FAILURE) {
					return;
				}
				wsresponse = stub.suspend(d);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
			    && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
				    && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors()
					    .getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error("Web Service Error Message: " + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = "Unknown Failure";
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(
			    "Web Service Connection Issue", axisFault);
			throw e;
		}
	}

	public void createAhl(WorldTracerActionDTO dto, Ahl data, WorldTracerResponse response) throws WorldTracerException {
		Ahl incident = data;

		try {
			// Being used primarily for validating all required data is present
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createAhlFieldMap(data);  //Create error, corrected field IT
			if (fieldMap == null) {
				throw new WorldTracerException("Unable to generate incident mapping");
			}
			
			EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_AHL);
			
			DelayedBagServiceStub stub = new DelayedBagServiceStub(delayedEndpoint);
			configureClient(stub);

			WTRDelayedBagsCreateRQDocument d = WTRDelayedBagsCreateRQDocument.Factory.newInstance();
			WTRDelayedBagsCreateRQ d1 = d.addNewWTRDelayedBagsCreateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(data.getAirlineCode());

			// Set values required to close a claim
			DelayedClaimType d2 = d1.addNewClaim();
		
			if (incident.getFaultReason() != 0 || incident.getFaultReason() > 79) {
				d2.setLossReasonCode(incident.getFaultReason());
				d2.setLossComments(incident.getFaultReasonDescription());
				
			} else {
				d2.setLossReasonCode(79);
				d2.setLossComments("Created in error");
			}
	
			if (incident.getFaultStation() != null) {
				if (incident.getFaultStation() == null || incident.getFaultStation().trim().length() < 1) {
					d2.setFaultStationCode(incident.getStationCode());
				} else {
					d2.setFaultStationCode(incident.getFaultStation());
				}
			} else {
				d2.setFaultStationCode(incident.getStationCode());
			}
			
			PassengerPaymentType d3 = d2.addNewPassengerPayments().addNewPassengerPayment();
			String cs_fmt = "%02d %s/%s%1.2f";
			int claimCount = 0;
			if (incident.getExpenses() != null) {
				String cost;

				if (incident.getExpenses() != null) {
					for (Expenses expense : incident.getExpenses()) {
						if (expense.getApprovalDate() != null && expense.getCurrrency() != null) {
							claimCount++;
							Amount amt = d3.addNewAmount();
							BigDecimal claimAmount = new BigDecimal(0);
							amt.setAmount(claimAmount);
							amt.setCurrencyCode(expense.getCurrrency());
						}
					}
				}
			}

			// see if we added a CS
			if (claimCount == 0) {
				Amount amt = d3.addNewAmount();
				BigDecimal claimAmount = new BigDecimal(0);
				amt.setAmount(claimAmount);
				amt.setCurrencyCode("USD");
			}
			
			// Set Creation Data
			StationAirlineType sat = d1.addNewRefStationAirline();
			sat.setAirlineCode(incident.getAirlineCode());
			sat.setStationCode(incident.getStationCode());

			// Set Baggage Information
			DelayedBagGroupType t2 = d1.addNewDelayedBagGroup();
			
			
			
			List<String> fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CT);
			List<String> fieldList2 = fieldMap.get(DefaultWorldTracerService.WorldTracerField.BI);
			List<String> contentsList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CC);
			List<String> tagList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);

			if (fieldList != null) {
				for (int i = 0; i < fieldList.size() && i < 10; i++) {

					String bagFace = fieldList.get(i);
					String colorType = bagFace.substring(0, 2);
					String bagType = bagFace.substring(2, 4);
					String bagDesc = bagFace.substring(4, 7);

					DelayedBagType t3 = t2.addNewDelayedBags().addNewDelayedBag();
					
					if (tagList != null && i < tagList.size()) {
						BagTagType tag = t3.addNewBagTag();
						String airtag = fieldList.get(i);
						tag.setAirlineCode(airtag.substring(0,2));
						tag.setTagSequence(airtag.substring(2));
					}
					
					if (fieldList2 != null && i < fieldList2.size()) {
						t3.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList2.get(i)));
					}
					ColorTypeDescType t4 = t3.addNewColorTypeDesc();
					t4.setColorCode(ColorCodeType.Enum.forString(colorType));
					t4.setTypeCode(Integer.valueOf(bagType));
					
					BagDescType t5 = t4.addNewDescriptor();
					
					Enum xType = BagElmsType.X;
			
					for (int j=0; j<3; ++j) {
						String letter = bagDesc.substring(j, j);
						
						if (PreProcessor.wt_mats.contains(letter)) {
							t5.setMtrlElement(BagMatrlType.Enum.forString(letter));
						} else {
							t5.addNewOtherElement().set(BagElmsType.Enum.forString(letter));
						}
					}
					
					if (contentsList != null && contentsList.size() > 0) {
						
						for(int k = 0; k < 1; k++){
							String bagInfo = contentsList.get(i);
							String[] bagContents = bagInfo.split(".- ");
							int countPerBag = bagContents.length;
							for(int j = 0; j < countPerBag && j < 12; j++){
								String contents = bagContents[j];
								if(j == 0)
									contents = contents.substring(3);
								int index = contents.indexOf("/");

								ContentType c = t3.addNewBagContents().addNewContent();
								c.setCategory(contents.substring(0, index));
								c.setDescription(contents.substring(index+1));
							}
						}
					}
				}
			}
			
			
			// Set Passengers & Contact Information
			PassengerItineraryType p1 = d1.addNewPassengers();
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			fieldList2 = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			for(int j = 0; j < fieldList.size(); j++){
				String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
				p1.addNewNames().addName(name);
				if (fieldList2 != null && fieldList2.size() > j) {
					p1.addNewInitials().addNewIntial().setStringValue(fieldList2.get(j));
				}

			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL);
			if (fieldList != null && fieldList.size() > 0) {
				p1.setFrequentFlyerID(fieldList.get(0));	
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS);
			if (fieldList != null && fieldList.size() > 0) {
				p1.setStatus(fieldList.get(0));
				p1.setFareBasis(fieldList.get(0));
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PT);
			if (fieldList != null && fieldList.size() > 0) {
				String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
				p1.setTitle(title);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NP);
			if (fieldList != null && fieldList.size() > 0) {
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.NP).formatEntry(fieldList.get(0));
				p1.setNoOfPassengers(text);
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PR);
			if (fieldList != null && fieldList.size() > 0) {
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.PR).formatEntry(fieldList.get(0));
				p1.setPNR(text);
			}
			
			ContactInfoType ct = p1.addNewContactInfo();
			
			WorldTracerField field = DefaultWorldTracerService.WorldTracerField.EA;
			fieldList = fieldMap.get(field);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewEmails().addEmail(fieldList.get(0));
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PN);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewPermPhones().addPhone(fieldList.get(0));
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CP);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewCellPhones().addPhone(fieldList.get(0));
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CP);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewCellPhones().addPhone(fieldList.get(0));
			}
			
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FX);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewFaxes().addFax(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CO);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewCountry().setCountryCode(fieldList.get(0));
			}


			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PA);
			if (fieldList != null && fieldList.size() > 0) {
				PermanentAddress pa = ct.addNewPermanentAddress();
				for(int i = 0; i < fieldList.size() && i < 2; i++){
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					pa.addAddressLine(address);
				}
			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.ZIP);
			if (fieldList != null && fieldList.size() > 0) {
				ct.setZipCode(fieldList.get(0));
			}



			// MARKER 1
			// MARKER 2

//			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.STATE);
//			if (fieldList != null && fieldList.size() > 0) {
//				ct.setState(fieldList.get(0));
//			}
			
			
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TA);
			if (fieldList != null && fieldList.size() > 0) {
				Address ta = ct.addNewTempAddress().addNewAddress();
				
				for(int i = 0; i < fieldList.size() && i < 2; i++){
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					ta.addAddressLine(address);
				}
			}
			
			
			
			
			// Set Flight Information
			
			int itinCount = 0;
			FlightSegments p2 = p1.addNewItinerary().addNewFlightSegments();
			for (Itinerary itin: incident.getPaxItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null
						|| itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
						|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
				} else {
					itinCount++;
					FlightSegmentType p3 = p2.addNewFlightSegmentOrARNK().addNewFlightSegment();
					FlightDateType p5 = p3.addNewFlightAndDate();
					
					p5.setAirlineCode(itin.getAirline());
					p5.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
					p5.setDate(itin.getFlightDate());
					
					OriginDestinationType p4 = p3.addNewOriginDestination();
					p4.setOrigin(itin.getDepartureCity().trim());
					p4.setDestination(itin.getArrivalCity().trim());
				}
			}
			
			

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.AG);
			d1.setAgentID(fieldList.get(0));

			// Send Message
			WTRBagsCreateRSDocument wsresponse = null;
			try {
				logger.info(d);
				if (FORCE_FAILURE) {
					return;
				}
				wsresponse = stub.create(d);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getSuccess() != null) {
				response.setSuccess(true);
				RecordReferenceType rrt = wsresponse.getWTRBagsCreateRS().getRecordID().getRecordReference();
				String ahlId = rrt.getStationCode() + rrt.getAirlineCode() + rrt.getReferenceNumber();
				Ahl ahl = new Ahl();
				ahl.setAhlId(ahlId);
				response.setAhl(ahl);
			} else {
				StringBuffer errorMsg = new StringBuffer();
				
				if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRBagsCreateRS().getErrors().getErrorArray();
					for (ErrorType error: errors) {
						errorMsg.append(error.getShortText());
						logger.error("Web Service Error Message: " + error.toString());
					}
				}
				
				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = "Unknown Failure";
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;
				
			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException("Web Service Connection Issue", axisFault);
			throw e;
		}



	}

	public void amendAhl(WorldTracerActionDTO dto, Ahl data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void suspendAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(delayedEndpoint);
			configureClient(stub);

			WTRSuspendRecordsRQDocument d = WTRSuspendRecordsRQDocument.Factory
			    .newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRSuspendRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
			    dto.getUser().getProfile().getAirline());

			
			d1.setRecordType(RecordType.DELAYED);
			RecordReferenceType r = d1.addNewRecords().addNewRecord()
			    .addNewRecordReference();
			String ahlId = ahl.getAhlId();

			r.setAirlineCode(ahlId.substring(0, 3));
			r.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			r.setStationCode(ahlId.substring(3, 5));

			d1.setAgentID(PreProcessor.getAgentEntry(ahl.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				logger.info(d);
				if (FORCE_FAILURE) {
					return;
				}
				wsresponse = stub.suspend(d);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
			    && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
				    && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors()
					    .getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error("Web Service Error Message: " + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = "Unknown Failure";
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(
			    "Web Service Connection Issue", axisFault);
			throw e;
		}
	}

	public void reinstateAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(delayedEndpoint);
			configureClient(stub);

			WTRReinstateRecordsRQDocument d = WTRReinstateRecordsRQDocument.Factory
			    .newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRReinstateRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
			    dto.getUser().getProfile().getAirline());

			
			d1.setRecordType(RecordType.DELAYED);
			RecordReferenceType r = d1.addNewRecords().addNewRecord()
			    .addNewRecordReference();
			String ahlId = ahl.getAhlId();

			r.setAirlineCode(ahlId.substring(0, 3));
			r.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			r.setStationCode(ahlId.substring(3, 5));

			d1.setAgentID(PreProcessor.getAgentEntry(ahl.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				logger.info(d);
				if (FORCE_FAILURE) {
					return;
				}
				wsresponse = stub.reinstate(d);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
			    && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
				    && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors()
					    .getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error("Web Service Error Message: " + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = "Unknown Failure";
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(
			    "Web Service Connection Issue", axisFault);
			throw e;
		}
	}

	public void closeAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

	public void getAhl(WorldTracerActionDTO dto, Ahl data, WorldTracerResponse response) {
		// TODO Auto-generated method stub

	}

}
