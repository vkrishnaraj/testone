package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iata.www.iata._2007._00.ErrorType;

import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.Bdo;
import aero.nettracer.serviceprovider.wt_1_0.common.ClaimCheck;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.common.Expenses;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Item;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.Pxf;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.PreProcessor;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.TxType;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.BasicRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.RuleMapper;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.UsWorldTracerRuleMap;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerConnectionException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService;
import aero.sita.www.bag.wtr._2009._01.AmountType;
import aero.sita.www.bag.wtr._2009._01.BagDescType;
import aero.sita.www.bag.wtr._2009._01.BagElmsType;
import aero.sita.www.bag.wtr._2009._01.BagMatrlType;
import aero.sita.www.bag.wtr._2009._01.BagTagAmendType;
import aero.sita.www.bag.wtr._2009._01.BagTagType;
import aero.sita.www.bag.wtr._2009._01.ColorCodeType;
import aero.sita.www.bag.wtr._2009._01.ColorTypeDescAmendType;
import aero.sita.www.bag.wtr._2009._01.ColorTypeDescType;
import aero.sita.www.bag.wtr._2009._01.ContactInfoAmendType;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType;
import aero.sita.www.bag.wtr._2009._01.ContentType;
import aero.sita.www.bag.wtr._2009._01.ContentsAmendType;
import aero.sita.www.bag.wtr._2009._01.ContentsType;
import aero.sita.www.bag.wtr._2009._01.CostType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupAmendType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagType;
import aero.sita.www.bag.wtr._2009._01.DelayedClaimAmendType;
import aero.sita.www.bag.wtr._2009._01.DelayedClaimType;
import aero.sita.www.bag.wtr._2009._01.FlightDateOrARNKType;
import aero.sita.www.bag.wtr._2009._01.FlightDateType;
import aero.sita.www.bag.wtr._2009._01.FlightOptionalDateOrARNKType;
import aero.sita.www.bag.wtr._2009._01.FlightOptionalDateType;
import aero.sita.www.bag.wtr._2009._01.FlightSegmentOrARNKType;
import aero.sita.www.bag.wtr._2009._01.FlightSegmentType;
import aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType;
import aero.sita.www.bag.wtr._2009._01.OriginDestinationType;
import aero.sita.www.bag.wtr._2009._01.PassengerAmendType;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryAmendType;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryType;
import aero.sita.www.bag.wtr._2009._01.PassengerPaymentAmendType;
import aero.sita.www.bag.wtr._2009._01.PassengerPaymentType;
import aero.sita.www.bag.wtr._2009._01.PassengerType;
import aero.sita.www.bag.wtr._2009._01.PhoneAmendType;
import aero.sita.www.bag.wtr._2009._01.RecordIdentifierType;
import aero.sita.www.bag.wtr._2009._01.RecordReferenceType;
import aero.sita.www.bag.wtr._2009._01.RecordType;
import aero.sita.www.bag.wtr._2009._01.RushBagGroupType;
import aero.sita.www.bag.wtr._2009._01.RushBagType;
import aero.sita.www.bag.wtr._2009._01.StationAirlineType;
import aero.sita.www.bag.wtr._2009._01.StringLength0To58AmendType;
import aero.sita.www.bag.wtr._2009._01.WTRBagsCreateRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecUpdateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReinstateRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRStatusRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRSuspendRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRTracingStateChangeRQType;
import aero.sita.www.bag.wtr._2009._01.BagElmsType.Enum;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType.PermanentAddress;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType.TempAddress.Address;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupAmendType.DelayedBags.DelayedBag;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType.BaggageItinerary;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType.DelayedBags;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType.BagAddress;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary.Routes;
import aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Names.Name;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryType.Itinerary.FlightSegments;
import aero.sita.www.bag.wtr._2009._01.PassengerType.Initials;
import aero.sita.www.bag.wtr._2009._01.PassengerType.Names;
import aero.sita.www.bag.wtr._2009._01.RushBagGroupType.RushDestinations;
import aero.sita.www.bag.wtr._2009._01.RushBagGroupType.RushFlights;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument.WTRCloseRecordsRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagRecReadRSDocument.WTRDelayedBagRecReadRS;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument.WTRDelayedBagsCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQDocument.WTRDelayedBagsRecUpdateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.RushBagTags;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.TeletypeAddresses;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument.WTROnhandBagCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecReadRSDocument.WTROnhandBagRecReadRS;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecUpdateRQDocument.WTROnhandBagRecUpdateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument.WTRReadRecordRQ;
import aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument.WTRRushBagsCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument.WTRRushBagsCreateRQ.SupplimentalInfo;
import aero.sita.www.bag.wtr.delayedbagservice.DelayedBagServiceStub;
import aero.sita.www.bag.wtr.onhandbagservice.OnhandBagServiceStub;
import aero.sita.www.bag.wtr.rushbagservice.RushBagServiceStub;

public class WorldTracerServiceImpl implements WorldTracerService {

	private static final String NEWLINE = "\n";
	private static final String POST_DISPATCH_SECURITY_VERIFICATION_HANDLER = "Post dispatch security verification handler";
	private static final String DISPATCH = "Dispatch";
	private static final String SECURITY = "Security";
	private static final String APACHE_RAMPART_INFLOW_HANDLER = "Apache Rampart inflow handler";
	private static final Integer INTEGER_4_MINUTES = new Integer(4 * 60 * 1000);
	private static final String C_SECURE_CACERTS = "c:\\secure\\cacerts";
	private static final String CHANGEIT = "changeit";
	private static final String UNABLE_TO_GENERATE_INCIDENT_MAPPING = "Unable to generate incident mapping";
	private static final String WEB_SERVICE_CONNECTION_ISSUE = "Web Service Connection Issue";
	private static final String UNKNOWN_FAILURE = "Unknown Failure";
	private static final String WEB_SERVICE_ERROR_MESSAGE = "Web Service Error Message: ";
	private static final String EXCEPTION_FOUND_RESPONSE = "Exception found... Response: ";
	private static final String ACTION_BEING_PERFORMED = "Action Being Performed: ";
	private static final String JAVAX_NET_SSL_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
	private static final String JAVAX_NET_SSL_TRUST_STORE = "javax.net.ssl.trustStore";
	private static final String RAMPART = "rampart";
	private static final String POLICY_XML = "/policy.xml";
	private static final String INSERT_INTO_WT_WS_TRANS_LOG_GMTTIME_DESCRIPTION_VALUES = "INSERT INTO WT_WS_TRANS_LOG (gmttime, description) VALUES (?, ?)";
	private static final String METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES = "Method not available via web services.";
	private static final Logger logger = Logger.getLogger(WorldTracerServiceImpl.class);
	private static BigDecimal VERSION_0_PT_1 = new BigDecimal(0.1).setScale(1, RoundingMode.HALF_UP);
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);


	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format.FREE_FLOW);
	private static final boolean FORCE_FAILURE = false;
	RuleMapper wtRuleMap = new UsWorldTracerRuleMap();
	public WorldTracerServiceImpl(WorldTracerActionDTO dto) {

	}
	

	public void writeToLog(String description) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.beginTransaction();
		try {
			SQLQuery query = sess
					.createSQLQuery(INSERT_INTO_WT_WS_TRANS_LOG_GMTTIME_DESCRIPTION_VALUES);
			query.setTimestamp(0, ServiceUtilities.getGMTDate());
			query.setString(1, StringUtils.substring(description, 0, 60));
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

	public void insertBdo(WorldTracerActionDTO dto, Bdo bdo, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void eraseActionFile(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void requestOhd(WorldTracerActionDTO dto, RequestOhd data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void getActionFileDetails(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void getActionFileSummary(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	public void getActionFileCounts(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response) throws WorldTracerException {
		throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(WorldTracerServiceImpl.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}

	private static void configureClient(org.apache.axis2.client.Stub stub) throws AxisFault {

		System.setProperty(JAVAX_NET_SSL_TRUST_STORE, C_SECURE_CACERTS);
		System.setProperty(JAVAX_NET_SSL_TRUST_STORE_PASSWORD, CHANGEIT);

		System.out.println(System.getProperty(JAVAX_NET_SSL_TRUST_STORE));
		System.out.println(System.getProperty(JAVAX_NET_SSL_TRUST_STORE_PASSWORD));

		ServiceClient client = stub._getServiceClient();

		stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, INTEGER_4_MINUTES);
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, INTEGER_4_MINUTES);

		client.engageModule(RAMPART);
		try {
			client.getAxisService().getPolicySubject().attachPolicy(loadPolicy(POLICY_XML));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		removeFaultPhases(client);
		removePhases(client);
	}

	private static void removeFaultPhases(ServiceClient client) {
		List<Phase> phases = client.getAxisConfiguration().getInFaultFlowPhases();

		for (Phase p : phases) {
			HandlerDescription removeThis = null;
			if (p.getPhaseName().equals(SECURITY) || p.getPhaseName().equals(DISPATCH)) {
				List<Handler> l = p.getHandlers();
				for (Handler h : l) {
					if (h.getName().equals(APACHE_RAMPART_INFLOW_HANDLER) || h.getName().equals(POST_DISPATCH_SECURITY_VERIFICATION_HANDLER)) {
						removeThis = h.getHandlerDesc();
					}
				}
			}

			if (removeThis != null) {
				p.removeHandler(removeThis);
			}
		}
	}

	private static void removePhases(ServiceClient client) {
		List<Phase> phases = client.getAxisConfiguration().getInFlowPhases();

		for (Phase p : phases) {
			HandlerDescription removeThis = null;
			if (p.getPhaseName().equals(SECURITY) || p.getPhaseName().equals(DISPATCH)) {
				List<Handler> l = p.getHandlers();
				for (Handler h : l) {

					if (h.getName().equals(APACHE_RAMPART_INFLOW_HANDLER) || h.getName().equals(POST_DISPATCH_SECURITY_VERIFICATION_HANDLER)) {
						removeThis = h.getHandlerDesc();
					}
				}
			}

			if (removeThis != null) {
				p.removeHandler(removeThis);
			}
		}
	}

	public void closeOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {
		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub);

			WTRCloseRecordsRQDocument d = WTRCloseRecordsRQDocument.Factory.newInstance();
			WTRCloseRecordsRQ d1 = d.addNewWTRCloseRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			d1.setRecordType(RecordType.ON_HAND);
			RecordReferenceType r = d1.addNewRecords().addNewRecordReference();
			String ohdId = ohd.getOhdId();

			r.setAirlineCode(ohdId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			r.setStationCode(ohdId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Close OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.close(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);

			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}



	public void createOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {
		/*
		 * SUCCESS MESSAGE: <wtr:WTR_BagsCreateRS Version="0.1"
		 * xmlns:wtr="http://www.sita.aero/bag/wtr/2009/01"
		 * xmlns:iata="http://www.iata.org/IATA/2007/00"
		 * xmlns:xsd="http://www.w3.org/1999/XMLSchema"
		 * xmlns:SOAP-EN="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
		 * xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"> <wtr:Success/>
		 * <wtr:RecordID> <wtr:RecordType>ON-HAND</wtr:RecordType>
		 * <wtr:RecordReference StationCode="DEN" AirlineCode="US"
		 * ReferenceNumber="10031"/> </wtr:RecordID> </wtr:WTR_BagsCreateRS>
		 */
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createOhdFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_OHD);

		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub);

			WTROnhandBagCreateRQDocument d = WTROnhandBagCreateRQDocument.Factory.newInstance();
			WTROnhandBagCreateRQ d1 = d.addNewWTROnhandBagCreateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			OnHandBagType d2 = d1.addNewOnHandBag();

			List<String> fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CT);
			if (fieldList != null && fieldList.size() >= 1) {
				String bagFace = fieldList.get(0);
				String colorType = bagFace.substring(0, 2);
				String bagType = bagFace.substring(2, 4);
				String bagDesc = bagFace.substring(4, 7);

				ColorTypeDescType t1 = d2.addNewColorTypeDesc();
				t1.setColorCode(ColorCodeType.Enum.forString(colorType));
				t1.setTypeCode(Integer.valueOf(bagType));

				BagDescType t5 = t1.addNewDescriptor();

				for (int j = 0; j < 3; ++j) {
					String letter = bagDesc.substring(j, j + 1);

					if (PreProcessor.wt_mats.contains(letter)) {
						t5.setMtrlElement(BagMatrlType.Enum.forString(letter));
					} else {
						Enum e = BagElmsType.Enum.forString(letter);

						if (e == null || e.intValue() > 7 || e.intValue() < 1) {
							t5.addNewOtherElement().set(BagElmsType.Enum.forString("X"));
						} else {
							t5.addNewOtherElement().set(e);
						}
					}
				}

			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.BI);
			if (fieldList != null && fieldList.size() > 0) {
				d2.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0)));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);
			if (fieldList != null && fieldList.size() > 0) {
				String airtag = fieldList.get(0);

				try {
					airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
				} catch (BagtagException e) {
					logger.debug("Unable to convert bagtag while creating fwd: " + airtag);
				}

				BagTagType t3 = d2.addNewBagTag();
				t3.setAirlineCode(airtag.substring(0, 2));
				t3.setTagSequence(airtag.substring(2));
			}

			int itinCount = 0;
			aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary i1 = d2.addNewItinerary();
			aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary.FlightSegments f1 = i1.addNewFlightSegments();
			Routes rts = i1.addNewRoutes();

			for (Itinerary itin : ohd.getBagItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
				} else {
					itinCount++;

					FlightOptionalDateOrARNKType p3 = f1.addNewFlightSegment();
					FlightOptionalDateType p5 = p3.addNewFlightDate();

					p5.setAirlineCode(itin.getAirline());
					p5.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
					p5.setDate(itin.getFlightDate());

					if (itinCount == 0) {
						rts.addNewRoute().setStringValue(itin.getDepartureCity().trim());
					}
					rts.addNewRoute().setStringValue(itin.getArrivalCity().trim());
				}
			}

			PassengerType p1 = d1.addNewPassengers();

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			List<String> fieldList2 = fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			Names names = null;
			if (fieldList != null) {
				for (int j = 0; j < fieldList.size(); j++) {
					if (j == 0) {
						names = p1.addNewNames();
					}
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					names.addName(name);
					if (fieldList2 != null && fieldList2.size() > j) {
						p1.addNewInitials().addNewIntial().setStringValue(fieldList2.get(j));
					}
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PT);
			if (fieldList != null && fieldList.size() > 0) {
				String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
				p1.setTitle(title);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL);
			if (fieldList != null && fieldList.size() > 0) {
				p1.setFrequentFlyerID(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PR);
			if (fieldList != null && fieldList.size() > 0) {
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.PR).formatEntry(fieldList.get(0));
				p1.setPNR(text);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.AB);
			if (fieldList != null && fieldList.size() > 0) {
				BagAddress ba = d2.addNewBagAddress();
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					ba.addAddressLine(address);
				}
			}

			ContactInfoType ct = p1.addNewContactInfo();

			WorldTracerField field = DefaultWorldTracerService.WorldTracerField.EA;
			fieldList = fieldMap.get(field);
			if (fieldList != null && fieldList.size() > 0) {
				// ct.setEmail(fieldList.get(0));
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

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewTempPhones().addPhone(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FX);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewFaxes().addFax(fieldList.get(0));
			}

			List<String> contentsList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CC);
			ContentsType c2 = null;
			if (contentsList != null) {
				for (int i = 0; i < contentsList.size() && i < 12; i++) {
	
					if (i == 0) {
						c2 = d2.addNewBagContents();
					}
	
					String contents = contentsList.get(i);
					int index = contents.indexOf("/");
	
					ContentType c = c2.addNewContent();
					c.setCategory(contents.substring(0, index));
					c.setDescription(contents.substring(index + 1));
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.SL);
			if (fieldList != null && fieldList.size() > 0) {
				d2.setStorageLocation(RULES.get(DefaultWorldTracerService.WorldTracerField.SL).formatEntry(fieldMap.get(WorldTracerField.SL).get(0)));
			}

			StationAirlineType s1 = d1.addNewRefStationAirline();
			s1.setAirlineCode(ohd.getAirlineCode());
			s1.setStationCode(ohd.getStationCode());

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRBagsCreateRSDocument wsresponse = null;
			try {
				String label = "Create OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.create(d);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getSuccess() != null) {
				response.setSuccess(true);
				RecordReferenceType rrt = wsresponse.getWTRBagsCreateRS().getRecordID().getRecordReference();
				String ohdId = rrt.getStationCode() + rrt.getAirlineCode() + rrt.getReferenceNumber();
				Ohd ohdx = new Ohd();
				ohdx.setOhdId(ohdId);
				response.setOhd(ohdx);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRBagsCreateRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void reinstateOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {
		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub);

			WTRReinstateRecordsRQDocument d = WTRReinstateRecordsRQDocument.Factory.newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRReinstateRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			d1.setRecordType(RecordType.ON_HAND);
			RecordReferenceType r = d1.addNewRecords().addNewRecord().addNewRecordReference();
			String ohdId = ohd.getOhdId();

			r.setAirlineCode(ohdId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			r.setStationCode(ohdId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Reinstate OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.reinstate(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void suspendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {

		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub);

			WTRSuspendRecordsRQDocument d = WTRSuspendRecordsRQDocument.Factory.newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRSuspendRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			d1.setRecordType(RecordType.ON_HAND);
			RecordReferenceType r = d1.addNewRecords().addNewRecord().addNewRecordReference();
			String ohdId = ohd.getOhdId();

			r.setAirlineCode(ohdId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			r.setStationCode(ohdId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Suspend OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.suspend(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void createAhl(WorldTracerActionDTO dto, Ahl data, WorldTracerResponse response) throws WorldTracerException {
		Ahl incident = data;

		try {
			// Being used primarily for validating all required data is present
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createAhlFieldMap(data); // Create
			// error,
			// corrected
			// field
			// IT
			if (fieldMap == null) {
				throw new WorldTracerException(UNABLE_TO_GENERATE_INCIDENT_MAPPING);
			}

			EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_AHL);

			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
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

			// String cs_fmt = "%02d %s/%s%1.2f";
			int claimCount = 0;
			if (incident.getExpenses() != null) {
				// String cost;

				if (incident.getExpenses() != null) {
					for (Expenses expense : incident.getExpenses()) {
						if (expense.getApprovalDate() != null && expense.getCurrrency() != null) {
							claimCount++;
							AmountType amt = d3.addNewAmount();
							aero.sita.www.bag.wtr._2009._01.CostType.Enum e = CostType.Enum.forString(expense.getPaycode());
							if (e == null)
								e = CostType.X;
							d3.setType(e);
							BigDecimal claimAmount = new BigDecimal(0);
							amt.setAmount(claimAmount);
							amt.setCurrencyCode(expense.getCurrrency());
						}
					}
				}
			}

			// see if we added a CS
			if (claimCount == 0) {
				AmountType amt = d3.addNewAmount();
				aero.sita.www.bag.wtr._2009._01.CostType.Enum e = CostType.X;
				d3.setType(e);
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

			BaggageItinerary iti = t2.addNewBaggageItinerary();
			DelayedBags t22 = t2.addNewDelayedBags();
			
			int itinCountA = 0;

			for (Itinerary itin : incident.getBagItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCountA > 4) {
				} else {
					itinCountA++;
					FlightDateType p3 = iti.addNewFlightDateOrARNK().addNewFlightDate();
					p3.setAirlineCode(itin.getAirline());
					p3.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
					p3.setDate(itin.getFlightDate());
				}
			}

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

					DelayedBagType t3 = t22.addNewDelayedBag();
					ContentsType cx = t3.addNewBagContents();

					if (tagList != null && i < tagList.size()) {
						BagTagType tag = t3.addNewBagTag();
						String airtag = tagList.get(i);
						tag.setAirlineCode(airtag.substring(0, 2));
						tag.setTagSequence(airtag.substring(2));
					}

					if (fieldList2 != null && i < fieldList2.size()) {
						t3.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList2.get(i)));
					}
					ColorTypeDescType t4 = t3.addNewColorTypeDesc();
					t4.setColorCode(ColorCodeType.Enum.forString(colorType));
					t4.setTypeCode(Integer.valueOf(bagType));

					BagDescType t5 = t4.addNewDescriptor();

					for (int j = 0; j < 3; ++j) {
						String letter = bagDesc.substring(j, j + 1);

						if (PreProcessor.wt_mats.contains(letter)) {
							t5.setMtrlElement(BagMatrlType.Enum.forString(letter));
						} else {
							Enum e = BagElmsType.Enum.forString(letter);

							if (e == null || e.intValue() > 7 || e.intValue() < 1) {
								t5.addNewOtherElement().set(BagElmsType.Enum.forString("X"));
							} else {
								t5.addNewOtherElement().set(e);
							}
						}
					}

					if (contentsList != null && contentsList.size() > 0) {

						for (int k = 0; k < 1; k++) {
							String bagInfo = contentsList.get(i);
							String[] bagContents = bagInfo.split(".- ");
							int countPerBag = bagContents.length;
							for (int j = 0; j < countPerBag && j < 12; j++) {
								String contents = bagContents[j];
								if (j == 0)
									contents = contents.substring(3);
								int index = contents.indexOf("/");

								ContentType c = cx.addNewContent();
								c.setCategory(contents.substring(0, index));
								c.setDescription(contents.substring(index + 1));
							}
						}
					}
				}
			}

			// Set Passengers & Contact Information
			PassengerItineraryType p1 = d1.addNewPassengers();
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			fieldList2 = fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			if (fieldList != null && fieldList.size() > 0) {
				Names pp1 = p1.addNewNames();
				Initials pi1 = p1.addNewInitials();
				for (int j = 0; j < fieldList.size(); j++) {
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					
					pp1.addName(name);
					
					if (fieldList2 != null && fieldList2.size() > j) {
						pi1.addNewIntial().setStringValue(fieldList2.get(j));
					}
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
			} else if (data.getPnrLocator() != null) {
				p1.setPNR(data.getPnrLocator());
			}
			p1.setPooledTktNumber("0000");

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

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewTempPhones().addPhone(fieldList.get(0));
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
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
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

			// fieldList =
			// fieldMap.get(DefaultWorldTracerService.WorldTracerField.STATE);
			// if (fieldList != null && fieldList.size() > 0) {
			// ct.setState(fieldList.get(0));
			// }

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TA);
			if (fieldList != null && fieldList.size() > 0) {
				Address ta = ct.addNewTempAddress().addNewAddress();

				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					ta.addAddressLine(address);
				}
			}

			// Set Flight Information

			int itinCount = 0;
			FlightSegments p2 = p1.addNewItinerary().addNewFlightSegments();
			for (Itinerary itin : incident.getPaxItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
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
				String label = "Create AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.create(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
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
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}




	public void suspendAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub);

			WTRSuspendRecordsRQDocument d = WTRSuspendRecordsRQDocument.Factory.newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRSuspendRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			d1.setRecordType(RecordType.DELAYED);
			RecordReferenceType r = d1.addNewRecords().addNewRecord().addNewRecordReference();
			String ahlId = ahl.getAhlId();

			r.setAirlineCode(ahlId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			r.setStationCode(ahlId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ahl.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Suspend AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.suspend(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void reinstateAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub);

			WTRReinstateRecordsRQDocument d = WTRReinstateRecordsRQDocument.Factory.newInstance();
			WTRTracingStateChangeRQType d1 = d.addNewWTRReinstateRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			d1.setRecordType(RecordType.DELAYED);
			RecordReferenceType r = d1.addNewRecords().addNewRecord().addNewRecordReference();
			String ahlId = ahl.getAhlId();

			r.setAirlineCode(ahlId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			r.setStationCode(ahlId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ahl.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Reinstate AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.reinstate(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void closeAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		if (ahl.getAhlId() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}

		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createCloseFieldMap(ahl);
		if (ahl.getAhlId() == null || ahl.getAhlId().trim().length() < 1) {
			throw new WorldTracerException("No associated WorldTracer file");
		}
		try {
			boolean success = closeInc(fieldMap, ahl.getAhlId(), ahl.getStationCode(), ahl, response, dto);
			if (!success) {
				success = amendBeforeClose(fieldMap, ahl.getAhlId(), ahl.getStationCode(), ahl, response, dto);
				if (!success) {
					logger.error("amend incident error with WorldTracer");
				}
				success = closeInc(fieldMap, ahl.getAhlId(), ahl.getStationCode(), ahl, response, dto);
				if (!success) {
					logger.error("close incident error with WorldTracer");
					throw new WorldTracerConnectionException();
				}
			}
			response.setSuccess(true);
		} catch (Exception e) {
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
	}

	private boolean closeInc(Map<WorldTracerField, List<String>> fieldMap, String ahlId, String stationCode, Ahl ahl, WorldTracerResponse response, WorldTracerActionDTO dto)
			throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub);

			WTRCloseRecordsRQDocument d = WTRCloseRecordsRQDocument.Factory.newInstance();
			WTRCloseRecordsRQ d1 = d.addNewWTRCloseRecordsRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordReferenceType d2 = d1.addNewRecords().addNewRecordReference();
			d2.setAirlineCode(ahlId.substring(3, 5));
			d2.setStationCode(ahlId.substring(0, 3));
			d2.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			d1.setRecordType(RecordType.DELAYED);
			d1.setAgentID(PreProcessor.getAgentEntry(ahl.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Close AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return false;
				}
				writeToLog(label);
				wsresponse = stub.close(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

		// Return whether it was a success
		return response.isSuccess();
	}

	public void forwardOhd(WorldTracerActionDTO dto, ForwardOhd data, WorldTracerResponse response) throws WorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.forwardOhd(dto, data, response);
		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.CREATE_OHD);
		try {

			RushBagServiceStub stub = new RushBagServiceStub(getRushEndpoint(dto));
			configureClient(stub);

			WTRForwardOnhandBagsRQDocument d = WTRForwardOnhandBagsRQDocument.Factory.newInstance();
			WTRForwardOnhandBagsRQ d1 = d.addNewWTRForwardOnhandBagsRQ();
			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordReferenceType rrt = d1.addNewDelayedBag();
			String ahlId = data.getAhlId();
			rrt.setAirlineCode(ahlId.substring(3, 5));
			rrt.setStationCode(ahlId.substring(0, 3));
			rrt.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));

			RecordReferenceType bags = d1.addNewOnHandBags().addNewRecordReference();
			String ohdId = data.getOhdId();
			bags.setAirlineCode(ohdId.substring(3, 5));
			bags.setStationCode(ohdId.substring(0, 3));
			bags.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));

			List<String> fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.Names names = null;
			if (fieldList != null) {
				for (int j = 0; j < fieldList.size(); j++) {
					if (j == 0) {
						names = d1.addNewNames();
					}
	
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					names.addName(name);
	
				}
			}

			d1.addNewRushFlights();
			RushBagTags rbt = d1.addNewRushBagTags();

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.XT);
			if (fieldList != null && fieldList.size() > 0) {

				BagTagType btt = rbt.addNewBagTag();
				String airtag = fieldList.get(0);
				btt.setAirlineCode(airtag.substring(0, 2));
				btt.setTagSequence(airtag.substring(2));
			}

			aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.RushFlights rf = d1.addNewRushFlights();
			aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.RushDestinations rds = d1.addNewRushDestinations();
			int itinCount = 0;
			if (data.getItinerary() != null) {
				for (Itinerary itin : data.getItinerary()) {
					if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightNumber() == null || itin.getFlightNumber().trim().length() <= 0
							|| itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
							|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null) {
						continue;
					} else {
						itinCount++;
						FlightDateType fdt = rf.addNewFlightDateOrARNK().addNewFlightDate();
						fdt.setAirlineCode(itin.getAirline());
						fdt.setDate(itin.getFlightDate());
						fdt.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
						StationAirlineType sat = rds.addNewDestination();
						sat.setAirlineCode(itin.getAirline());
						sat.setStationCode(itin.getArrivalCity());
					}
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.SI);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.SupplimentalInfo d2 = d1.addNewSupplimentalInfo();
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.SI).formatEntry(fieldList.get(0));
				d2.addTextLine(text);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TX);
			if (fieldList != null && fieldList.size() > 0) {
				TeletypeAddresses add = d1.addNewTeletypeAddresses();

				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					add.addTeletypeAddress(fieldList.get(i));
				}
			}

			d1.setAgentID(PreProcessor.getAgentEntry(data.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Forward OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.forwardOnhandBags(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}



	public void sendForwardMessage(WorldTracerActionDTO dto, ForwardMessage msg, WorldTracerResponse response) throws WorldTracerException {
		EnumMap<WorldTracerField, WorldTracerRule<String>> fwdRules = wtRuleMap.getRule(TxType.FWD_GENERAL);
		try {
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createFwdFieldMap(msg, dto);

			RushBagServiceStub stub = new RushBagServiceStub(getRushEndpoint(dto));
			configureClient(stub);

			WTRRushBagsCreateRQDocument d = WTRRushBagsCreateRQDocument.Factory.newInstance();
			WTRRushBagsCreateRQ d1 = d.addNewWTRRushBagsCreateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			StationAirlineType s1 = d1.addNewRefStationAirline();
			s1.setAirlineCode(msg.getFromAirline());
			s1.setStationCode(msg.getFromStation());

			RushBagGroupType rb = d1.addNewRushBagGroup();
			RushBagType rbt = rb.addNewRushBags().addNewRushBag();

			List<String> fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);
			if (fieldList != null && fieldList.size() > 0) {

				BagTagType btt = rbt.addNewOriginalBagTag();
				String airtag = fieldList.get(0);
				btt.setAirlineCode(airtag.substring(0, 2));
				btt.setTagSequence(airtag.substring(2));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.XT);
			if (fieldList != null && fieldList.size() > 0) {

				BagTagType btt = rbt.addNewRushBagTag();
				String airtag = fieldList.get(0);
				btt.setAirlineCode(airtag.substring(0, 2));
				btt.setTagSequence(airtag.substring(2));
			}

			RushFlights rf = rb.addNewRushFlights();
			RushDestinations rds = rb.addNewRushDestinations();
			int itinCount = 0;
			if (msg.getItinerary() != null) {
				for (Itinerary itin : msg.getItinerary()) {
					if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightNumber() == null || itin.getFlightNumber().trim().length() <= 0
							|| itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
							|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null) {
						continue;
					} else {
						itinCount++;
						FlightDateType fdt = rf.addNewFlightDateOrARNK().addNewFlightDate();
						fdt.setAirlineCode(itin.getAirline());
						fdt.setDate(itin.getFlightDate());
						fdt.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
						StationAirlineType sat = rds.addNewDestination();
						sat.setAirlineCode(itin.getAirline());
						sat.setStationCode(itin.getArrivalCity());
					}
				}
			}

			if (msg.getFaultStation() != null) {
				d1.setFaultStationCode(msg.getFaultStation());
			}

			if (msg.getFaultReasonDescription() != null) {
				d1.setLossComments(msg.getFaultReasonDescription());
			}

			if (msg.getFaultReason() != 0) {
				d1.setLossReasonCode(msg.getFaultReason());
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.SI);
			if (fieldList != null && fieldList.size() > 0) {
				SupplimentalInfo d2 = d1.addNewSupplimentalInfo();
				String text = fwdRules.get(DefaultWorldTracerService.WorldTracerField.SI).formatEntry(fieldList.get(0));
				d2.addTextLine(text);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TX);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument.WTRRushBagsCreateRQ.TeletypeAddresses add = d1.addNewTeletypeAddresses();

				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					add.addTeletypeAddress(fieldList.get(i));
				}
			}

			d1.setAgentID(PreProcessor.getAgentEntry(msg.getAgent()));

			// Send Message
			WTRBagsCreateRSDocument wsresponse = null;
			try {
				String label = "Send General Forward Msg";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.create(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRBagsCreateRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	private boolean amendBeforeClose(Map<WorldTracerField, List<String>> fieldMap, String ahlId, String stationCode, Ahl ahl, WorldTracerResponse response, WorldTracerActionDTO dto)
			throws WorldTracerException {
		amendAhl(dto, ahl, response);

		if (response.isSuccess()) {
			response.setSuccess(false);
			return true;
		}

		response.setSuccess(false);
		return false;
	}

	public void getAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {

		try {

			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub);

			WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
			WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordIdentifierType t1 = d1.addNewRecordID();
			t1.setRecordType(RecordType.ON_HAND);

			RecordReferenceType t2 = t1.addNewRecordReference();
			String ahlId = ahl.getAhlId();

			t2.setAirlineCode(ahlId.substring(3, 5));
			t2.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			t2.setStationCode(ahlId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ahl.getAgent()));
			// Send Message
			WTRDelayedBagRecReadRSDocument wsresponse = null;
			try {
				String label = "Get AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.read(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRDelayedBagRecReadRS() != null && wsresponse.getWTRDelayedBagRecReadRS().getSuccess() != null) {

				handleResponse(response, ahlId, wsresponse);

			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRDelayedBagRecReadRS() != null && wsresponse.getWTRDelayedBagRecReadRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRDelayedBagRecReadRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	private void handleResponse(WorldTracerResponse response, String ahlId, WTRDelayedBagRecReadRSDocument wsresponse) {
		try {
			response.setSuccess(true);

			Ahl rahl = new Ahl();
			response.setAhl(rahl);
			// File Info (record reference, createdate, etc)
			WTRDelayedBagRecReadRS wr1 = wsresponse.getWTRDelayedBagRecReadRS();

			rahl.setAhlId(ahlId);
			rahl.setAirlineCode(ahlId.substring(3, 5));
			rahl.setStationCode(ahlId.substring(0, 3));

			if (wr1.getDiaryInfo() != null && wr1.getDiaryInfo().getCreateDate() != null) {
				rahl.setCreateDate(wr1.getDiaryInfo().getCreateDate());
			}

			if (wr1.getPassengers() != null) {
				PassengerItineraryType wsp = wr1.getPassengers();

				if (wsp.getPNR() != null)
					rahl.setPnrLocator(wsp.getPNR());

				try {
					rahl.setNumberPaxAffected(Integer.parseInt(wsp.getNoOfPassengers()));
				} catch (Exception e) {
					// ignore
				}

				ArrayList<Passenger> paxList = new ArrayList<Passenger>();
				Passenger fPax = null;

				String[] temp = wsp.getNames().getNameArray();
				for (String tem : temp) {
					Passenger p = new Passenger();
					paxList.add(p);
					p.setLastname(tem);
					if (fPax == null) {
						fPax = p;
					}
				}
				rahl.setPax(paxList.toArray(new Passenger[paxList.size()]));

				if (fPax != null) {
					if (wsp.getTitle() != null) {
						// HERE
						int sal = mapSalutation(wsp.getTitle());
						fPax.setSalutation(sal);
					}
				}

				if (wsp.getFrequentFlyerID() != null) {
					fPax.setFfNumber(wsp.getFrequentFlyerID());
				}

				String status = "";
				if (wsp.getStatus() != null) {
					status = wsp.getStatus();
				}
				if (wsp.getStatus() != null) {
					if (status.length() > 0) {
						status += " " + wsp.getStatus();
					} else {
						status = wsp.getFareBasis();
					}
				}

				fPax.setFfStatus(status);

				if (wsp.getContactInfo() != null) {
					ContactInfoType ci = wsp.getContactInfo();

					aero.nettracer.serviceprovider.wt_1_0.common.Address add = new aero.nettracer.serviceprovider.wt_1_0.common.Address();
					fPax.setAddress(add);

					if (ci.getPermanentAddress() != null) {
						int length = ci.getPermanentAddress().getAddressLineArray().length;

						if (length >= 1)
							add.setAddress1(ci.getPermanentAddress().getAddressLineArray(0));
						if (length >= 2)
							add.setAddress2(ci.getPermanentAddress().getAddressLineArray(1));
					}

					add.setZip(ci.getZipCode());
					if (ci.getCountry() != null)
						add.setCountryCode(ci.getCountry().getCountryCode());

					if (ci.getCellPhones() != null)
						add.setHomePhone(ci.getPermPhones().getPhoneArray(0));
					if (ci.getCellPhones() != null)
						add.setMobilePhone(ci.getCellPhones().getPhoneArray(0));
					if (ci.getCellPhones() != null)
						add.setAltPhone(ci.getTempPhones().getPhoneArray(0));

				}

				FlightSegments segs = wsp.getItinerary().getFlightSegments();
				ArrayList<Itinerary> s = new ArrayList<Itinerary>();

				for (FlightSegmentOrARNKType seg : segs.getFlightSegmentOrARNKArray()) {
					FlightSegmentType fst = seg.getFlightSegment();
					Itinerary i = new Itinerary();

					i.setAirline(fst.getFlightAndDate().getAirlineCode());
					i.setArrivalCity(fst.getOriginDestination().getDestination());
					i.setDepartureCity(fst.getOriginDestination().getOrigin());
					i.setFlightDate(fst.getFlightAndDate().getDate());
					i.setFlightNumber(fst.getFlightAndDate().getFlightNumber());

					s.add(i);
				}

				rahl.setPaxItinerary(s.toArray(new Itinerary[s.size()]));

			}

			DelayedBagGroupType db1 = wr1.getDelayedBagGroup();

			if (db1.getDelayedBags() != null) {

				DelayedBagType[] bags = db1.getDelayedBags().getDelayedBagArray();
				ArrayList<Item> items = new ArrayList<Item>();
				ArrayList<ClaimCheck> claimchecks = new ArrayList<ClaimCheck>();
				for (DelayedBagType bag : bags) {
					if (bag != null) {
						Item item = new Item();

						if (bag.getColorTypeDesc() != null) {
							try {
								item.setColor(bag.getColorTypeDesc().getColorCode().toString());
								int type = bag.getColorTypeDesc().getTypeCode();
								item.setType(Integer.toString(type));
								if (bag.getColorTypeDesc().getDescriptor() != null) {
									BagDescType bd = bag.getColorTypeDesc().getDescriptor();
									int descCt = 0;
									if (bd.getMtrlElement() != null) {
										item.setDesc1(bd.getMtrlElement().toString());
										descCt++;
									}
									if (bd.getOtherElementArray() != null && bd.getOtherElementArray().length > 0) {
										for (int i = 0; i < 3 && i < bd.getOtherElementArray().length; ++i) {
											Enum it = bd.getOtherElementArray(i);
											switch (descCt) {
											case 0:
												item.setDesc1(it.toString());
												descCt++;
												break;
											case 1:
												item.setDesc2(it.toString());
												descCt++;
												break;
											case 2:
												item.setDesc3(it.toString());
												descCt++;
												break;
											}
										}
									}
								}

							} catch (Exception e) {
								// Ignore
							}
						}

						if (bag.getBagContents() != null && bag.getBagContents().getContentArray().length > 0) {
							ArrayList<Content> contents = new ArrayList<Content>();
							for (ContentType bcont : bag.getBagContents().getContentArray()) {
								Content content = new Content();
								contents.add(content);
								content.setCategory(bcont.getCategory());
								content.setDescription(bcont.getDescription());
							}
						}

						item.setManufacturer(bag.getBrandInfo().getStringValue());
						items.add(item);

						if (bag.getBagTag() != null) {
							ClaimCheck cc = new ClaimCheck();
							cc.setAirlineCode(bag.getBagTag().getAirlineCode());
							cc.setTagNumber(bag.getBagTag().getTagSequence());
							claimchecks.add(cc);
						}
					}
				}

				rahl.setClaimCheck(claimchecks.toArray(new ClaimCheck[claimchecks.size()]));
				rahl.setItem(items.toArray(new Item[items.size()]));

				try {
					if (db1.getBaggageItinerary() != null && db1.getBaggageItinerary().getFlightDateOrARNKArray() != null
							&& db1.getBaggageItinerary().getFlightDateOrARNKArray().length > 0) {
						ArrayList<Itinerary> itin = new ArrayList<Itinerary>();

						for (FlightDateOrARNKType type : db1.getBaggageItinerary().getFlightDateOrARNKArray()) {
							Itinerary seg = new Itinerary();
							seg.setAirline(type.getFlightDate().getAirlineCode());
							seg.setFlightDate(type.getFlightDate().getDate());
							seg.setFlightNumber(type.getFlightDate().getFlightNumber());
							itin.add(seg);
						}

						rahl.setBagItinerary(itin.toArray(new Itinerary[itin.size()]));
					}
				} catch (Exception e) {
					// ignore
				}
			}

		} catch (Exception e) {
			logger.error("Parsing issue identified: " + e, e);
		}
	}

	private int mapSalutation(String title) {
		String salu = title;
		if (salu != null) {
			if (salu.equals("DR"))
				return 1;
			else if (salu.equals("MR"))
				return 2;
			else if (salu.equals("MS"))
				return 3;
			else if (salu.equals("MISS"))
				return 4;
			else if (salu.equals("MRS"))
				return 5;
		}
		return 0;
	}

	public void getOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {

		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub);

			WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
			WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordIdentifierType t1 = d1.addNewRecordID();
			t1.setRecordType(RecordType.ON_HAND);

			RecordReferenceType t2 = t1.addNewRecordReference();
			String ohdId = ohd.getOhdId();

			t2.setAirlineCode(ohdId.substring(3, 5));
			t2.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			t2.setStationCode(ohdId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));
			// Send Message
			WTROnhandBagRecReadRSDocument wsresponse = null;
			try {
				String label = "Get OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}				
				writeToLog(label);
				wsresponse = stub.read(d);
				logger.info(wsresponse);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTROnhandBagRecReadRS() != null && wsresponse.getWTROnhandBagRecReadRS().getSuccess() != null) {
				response.setSuccess(true);

				Ohd rohd = new Ohd();
				response.setOhd(rohd);
				rohd.setOhdId(ohdId);
				rohd.setAirlineCode(ohdId.substring(3, 5));
				rohd.setStationCode(ohdId.substring(0, 3));

				WTROnhandBagRecReadRS ores = wsresponse.getWTROnhandBagRecReadRS();
				Item item = new Item();
				rohd.setItem(item);

				if (ores.getOnHandBag() != null) {
					if (ores.getOnHandBag().getColorTypeDesc() != null) {
						item.setColor(ores.getOnHandBag().getColorTypeDesc().getColorCode().toString());
						item.setType(Integer.toString(ores.getOnHandBag().getColorTypeDesc().getTypeCode()));

						BagDescType bd = ores.getOnHandBag().getColorTypeDesc().getDescriptor();
						int descCt = 0;
						if (bd.getMtrlElement() != null) {
							item.setDesc1(bd.getMtrlElement().toString());
							descCt++;
						}
						if (bd.getOtherElementArray() != null && bd.getOtherElementArray().length > 0) {
							for (int i = 0; i < 3 && i < bd.getOtherElementArray().length; ++i) {
								Enum it = bd.getOtherElementArray(i);
								switch (descCt) {
								case 0:
									item.setDesc1(it.toString());
									descCt++;
									break;
								case 1:
									item.setDesc2(it.toString());
									descCt++;
									break;
								case 2:
									item.setDesc3(it.toString());
									descCt++;
									break;
								}
							}
						}
					}

					if (ores.getOnHandBag().getBagContents() != null) {
						ores.getOnHandBag().getBagContents().getContentArray();
					}

					item.setManufacturer(ores.getOnHandBag().getBrandInfo().getStringValue());
					if (ores.getOnHandBag().getBagTag() != null) {
						ClaimCheck cc = new ClaimCheck();
						rohd.setClaimCheck(cc);
						cc.setAirlineCode(ores.getOnHandBag().getBagTag().getAirlineCode());
						cc.setTagNumber(ores.getOnHandBag().getBagTag().getTagSequence());
					}

				}

			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTROnhandBagRecReadRS() != null && wsresponse.getWTROnhandBagRecReadRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTROnhandBagRecReadRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void amendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {

		/*
		 * SUCCESS MESSAGE: <wtr:WTR_BagsCreateRS Version="0.1"
		 * xmlns:wtr="http://www.sita.aero/bag/wtr/2009/01"
		 * xmlns:iata="http://www.iata.org/IATA/2007/00"
		 * xmlns:xsd="http://www.w3.org/1999/XMLSchema"
		 * xmlns:SOAP-EN="http://schemas.xmlsoap.org/soap/envelope/"
		 * xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
		 * xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"> <wtr:Success/>
		 * <wtr:RecordID> <wtr:RecordType>ON-HAND</wtr:RecordType>
		 * <wtr:RecordReference StationCode="DEN" AirlineCode="US"
		 * ReferenceNumber="10031"/> </wtr:RecordID> </wtr:WTR_BagsCreateRS>
		 */
		Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createOhdFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.AMEND_OHD);

		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub);

			WTROnhandBagRecUpdateRQDocument d = WTROnhandBagRecUpdateRQDocument.Factory.newInstance();
			WTROnhandBagRecUpdateRQ d1 = d.addNewWTROnhandBagRecUpdateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordReferenceType r = d1.addNewRecordReference();
			String ohdId = ohd.getOhdId();
			r.setAirlineCode(ohdId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			r.setStationCode(ohdId.substring(0, 3));

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			OnHandBagAmendType d2 = d1.addNewOnHandBag();

			List<String> fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CT);
			if (fieldList != null && fieldList.size() >= 1) {
				String bagFace = fieldList.get(0);
				String colorType = bagFace.substring(0, 2);
				String bagType = bagFace.substring(2, 4);
				String bagDesc = bagFace.substring(4, 7);

				ColorTypeDescAmendType t1 = d2.addNewColorTypeDesc();
				t1.setColorCode(ColorCodeType.Enum.forString(colorType));
				t1.setTypeCode(Integer.valueOf(bagType));

				BagDescType t5 = t1.addNewDescriptor();

				for (int j = 0; j < 3; ++j) {
					String letter = bagDesc.substring(j, j + 1);

					if (PreProcessor.wt_mats.contains(letter)) {
						t5.setMtrlElement(BagMatrlType.Enum.forString(letter));
					} else {
						Enum e = BagElmsType.Enum.forString(letter);

						if (e == null || e.intValue() > 7 || e.intValue() < 1) {
							t5.addNewOtherElement().set(BagElmsType.Enum.forString("X"));
						} else {
							t5.addNewOtherElement().set(e);
						}
					}
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.BI);
			if (fieldList != null && fieldList.size() > 0) {
				d2.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0)));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);
			if (fieldList != null && fieldList.size() > 0) {
				String airtag = fieldList.get(0);

				try {
					airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
				} catch (BagtagException e) {
					logger.debug("Unable to convert bagtag while creating fwd: " + airtag);
				}

				BagTagAmendType t3 = d2.addNewBagTag();
				t3.setAirlineCode(airtag.substring(0, 2));
				t3.setTagSequence(airtag.substring(2));
			}

			int itinCount = 0;
			aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.Itinerary i1 = d2.addNewItinerary();
			aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.Itinerary.FlightSegments f1 = i1.addNewFlightSegments();
			aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.Itinerary.Routes rts = i1.addNewRoutes();

			for (Itinerary itin : ohd.getBagItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
				} else {
					itinCount++;

					FlightOptionalDateOrARNKType p3 = f1.addNewFlightSegment();
					FlightOptionalDateType p5 = p3.addNewFlightDate();

					p5.setAirlineCode(itin.getAirline());
					p5.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
					p5.setDate(itin.getFlightDate());

					if (itinCount == 0) {
						rts.addNewRoute().setStringValue(itin.getDepartureCity().trim());
					}
					rts.addNewRoute().setStringValue(itin.getArrivalCity().trim());
				}
			}

			PassengerAmendType p1 = d1.addNewPassengers();

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			// List<String> fieldList2 =
			// fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Names names = null;
			if (fieldList != null) {
				for (int j = 0; j < fieldList.size(); j++) {
					if (j == 0) {
						names = p1.addNewNames();
					}
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					Name nm = names.addNewName();
					nm.setSeq(j+1);
					nm.setStringValue(name);
					// EXCEPTION CAUSED WITHIN AXIS ATTEMPTING TO ADD INITIALS HERE
					// if (fieldList2 != null && fieldList2.size() > j) {
					// p1.addNewInitials().addNewIntial().setStringValue(fieldList2.get(j));
					// }
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PT);
			if (fieldList != null && fieldList.size() > 0) {
				String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
				p1.addNewTitle().setStringValue(title);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL);
			if (fieldList != null && fieldList.size() > 0) {
				p1.addNewFrequentFlyerID().setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PR);
			if (fieldList != null && fieldList.size() > 0) {
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.PR).formatEntry(fieldList.get(0));
				p1.addNewPNR().setStringValue(text);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.AB);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.BagAddress ba = d2.addNewBagAddress();
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					StringLength0To58AmendType bad = ba.addNewAddressLine();
					bad.setSeq(i+1);
					bad.setStringValue(address);
				}
			}

			ContactInfoAmendType ct = p1.addNewContactInfo();

			WorldTracerField field = DefaultWorldTracerService.WorldTracerField.EA;
			fieldList = fieldMap.get(field);

			 if (fieldList != null && fieldList.size() > 0) {
				 ct.addNewEmails().addNewEmail().setStringValue(fieldList.get(0));
			 }

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PN);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewPermPhones().addNewPhone();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CP);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewCellPhones().addNewPhone();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewTempPhones().addNewPhone();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FX);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewFaxes().addNewFax();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			List<String> contentsList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CC);
			ContentsAmendType c2 = null;
			for (int i = 0; i < contentsList.size() && i < 12; i++) {

				if (i == 0) {
					c2 = d2.addNewBagContents();
				}

				String contents = contentsList.get(i);
				int index = contents.indexOf("/");

				ContentType c = c2.addNewContent();
				c.setCategory(contents.substring(0, index));
				c.setDescription(contents.substring(index + 1));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.SL);
			if (fieldList != null && fieldList.size() > 0) {
				d2.addNewStorageLocation().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.SL).formatEntry(fieldMap.get(WorldTracerField.SL).get(0)));
			}

			d1.setAgentID(PreProcessor.getAgentEntry(ohd.getAgent()));

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Amend OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.update(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void amendAhl(WorldTracerActionDTO dto, Ahl data, WorldTracerResponse response) throws WorldTracerException {
		Ahl incident = data;

		try {
			// Being used primarily for validating all required data is present
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createAhlFieldMap(data); // Create
			// error,
			// corrected
			// field
			// IT
			if (fieldMap == null) {
				throw new WorldTracerException(UNABLE_TO_GENERATE_INCIDENT_MAPPING);
			}

			EnumMap<WorldTracerField, WorldTracerRule<String>> RULES = wtRuleMap.getRule(TxType.AMEND_AHL);

			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub);

			WTRDelayedBagsRecUpdateRQDocument d = WTRDelayedBagsRecUpdateRQDocument.Factory.newInstance();
			WTRDelayedBagsRecUpdateRQ d1 = d.addNewWTRDelayedBagsRecUpdateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(data.getAirlineCode());

			RecordReferenceType r = d1.addNewRecordReference();
			String ahlId = data.getAhlId();
			r.setAirlineCode(ahlId.substring(3, 5));
			r.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			r.setStationCode(ahlId.substring(0, 3));

			// Set values required to close a claim

			DelayedClaimAmendType d2 = d1.addNewClaim();

			if (incident.getFaultReason() != 0 || incident.getFaultReason() > 79) {
				d2.addNewLossReasonCode().setIntValue(incident.getFaultReason());
				d2.addNewLossComments().setStringValue(incident.getFaultReasonDescription());

			} else {
				d2.addNewLossReasonCode().setIntValue(79);
				d2.addNewLossComments().setStringValue("Created in error");
			}

			if (incident.getFaultStation() != null) {
				if (incident.getFaultStation() == null || incident.getFaultStation().trim().length() < 1) {
					d2.addNewFaultStationCode().setStringValue(incident.getStationCode());
				} else {
					d2.addNewFaultStationCode().setStringValue(incident.getFaultStation());
				}
			} else {
				d2.addNewFaultStationCode().setStringValue(incident.getStationCode());
			}

			PassengerPaymentAmendType d3 = d2.addNewPassengerPayments().addNewPassengerPayment();
			d3.setSeq(1);
			// String cs_fmt = "%02d %s/%s%1.2f";
			int claimCount = 0;
			if (incident.getExpenses() != null) {
				// String cost;

				if (incident.getExpenses() != null) {
					for (Expenses expense : incident.getExpenses()) {
						if (expense.getApprovalDate() != null && expense.getCurrrency() != null) {
							claimCount++;
							AmountType amt = d3.addNewAmount();
							aero.sita.www.bag.wtr._2009._01.CostType.Enum e = CostType.Enum.forString(expense.getPaycode());
							if (e == null)
								e = CostType.X;
							d3.setType(e);
							BigDecimal claimAmount = new BigDecimal(0);
							amt.setAmount(claimAmount);
							amt.setCurrencyCode(expense.getCurrrency());
						}
					}
				}
			}

			// see if we added a CS
			if (claimCount == 0) {
				AmountType amt = d3.addNewAmount();
				aero.sita.www.bag.wtr._2009._01.CostType.Enum e = CostType.X;
				d3.setType(e);
				BigDecimal claimAmount = new BigDecimal(0);
				amt.setAmount(claimAmount);
				amt.setCurrencyCode("USD");
			}

			// Set Baggage Information
			DelayedBagGroupAmendType t2 = d1.addNewDelayedBagGroup();
			aero.sita.www.bag.wtr._2009._01.DelayedBagGroupAmendType.DelayedBags t22 = t2.addNewDelayedBags();

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

					DelayedBag t3 = t22.addNewDelayedBag();
					t3.setSeq(i+1);
					ContentsAmendType cx = t3.addNewBagContents();

					if (tagList != null && i < tagList.size()) {
						BagTagAmendType tag = t3.addNewBagTag();
						String airtag = tagList.get(i);
						tag.setAirlineCode(airtag.substring(0, 2));
						tag.setTagSequence(airtag.substring(2));
					}

					if (fieldList2 != null && i < fieldList2.size()) {
						t3.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList2.get(i)));
					}
					ColorTypeDescAmendType t4 = t3.addNewColorTypeDesc();
					t4.setColorCode(ColorCodeType.Enum.forString(colorType));
					t4.setTypeCode(Integer.valueOf(bagType));

					BagDescType t5 = t4.addNewDescriptor();

					for (int j = 0; j < 3; ++j) {
						String letter = bagDesc.substring(j, j + 1);

						if (PreProcessor.wt_mats.contains(letter)) {
							t5.setMtrlElement(BagMatrlType.Enum.forString(letter));
						} else {
							Enum e = BagElmsType.Enum.forString(letter);

							if (e == null || e.intValue() > 7 || e.intValue() < 1) {
								t5.addNewOtherElement().set(BagElmsType.Enum.forString("X"));
							} else {
								t5.addNewOtherElement().set(e);
							}
						}
					}

					if (contentsList != null && contentsList.size() > 0) {

						for (int k = 0; k < 1; k++) {
							String bagInfo = contentsList.get(i);
							String[] bagContents = bagInfo.split(".- ");
							int countPerBag = bagContents.length;
							for (int j = 0; j < countPerBag && j < 12; j++) {
								String contents = bagContents[j];
								if (j == 0)
									contents = contents.substring(3);
								int index = contents.indexOf("/");

								ContentType c = cx.addNewContent();
								c.setCategory(contents.substring(0, index));
								c.setDescription(contents.substring(index + 1));
							}
						}
					}
				}
			}

			// Set Passengers & Contact Information
			PassengerItineraryAmendType p1 = d1.addNewPassengers();

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			// fieldList2 =
			// fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Names p11 = p1.addNewNames();
				for (int j = 0; j < fieldList.size(); j++) {
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					Name nm = p11.addNewName();
					nm.setStringValue(name);
					nm.setSeq(j+1);
					// EXCEPTION CAUSED WITHIN AXIS ATTEMPTING TO ADD INITIALS HERE
					// if (fieldList2 != null && fieldList2.size() > j) {
					// 	p1.addNewInitials().addNewIntial().setStringValue(fieldList2.get(j));
					// }
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL);
			if (fieldList != null && fieldList.size() > 0) {
				p1.addNewFrequentFlyerID().setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS);
			if (fieldList != null && fieldList.size() > 0) {
				p1.addNewStatus().setStringValue(fieldList.get(0));
				p1.addNewFareBasis().setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PT);
			if (fieldList != null && fieldList.size() > 0) {
				String title = RULES.get(DefaultWorldTracerService.WorldTracerField.PT).formatEntry(fieldList.get(0));
				p1.addNewTitle().setStringValue(title);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NP);
			if (fieldList != null && fieldList.size() > 0) {
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.NP).formatEntry(fieldList.get(0));
				p1.addNewNoOfPassengers().setStringValue(text);
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PR);
			if (fieldList != null && fieldList.size() > 0) {
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.PR).formatEntry(fieldList.get(0));
				p1.addNewPNR().setStringValue(text);

			}

			ContactInfoAmendType ct = p1.addNewContactInfo();

			WorldTracerField field = DefaultWorldTracerService.WorldTracerField.EA;
			fieldList = fieldMap.get(field);
			if (fieldList != null && fieldList.size() > 0) {
				StringLength0To58AmendType e1 = ct.addNewEmails().addNewEmail();
				e1.setSeq(1);
				e1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PN);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewPermPhones().addNewPhone();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TP);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewTempPhones().addNewPhone();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CP);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewCellPhones().addNewPhone();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FX);
			if (fieldList != null && fieldList.size() > 0) {
				PhoneAmendType ph1 = ct.addNewFaxes().addNewFax();
				ph1.setSeq(1);
				ph1.setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.CO);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewCountry().setCountryCode(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PA);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.ContactInfoAmendType.PermanentAddress pa = ct.addNewPermanentAddress();
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					StringLength0To58AmendType pa1 = pa.addNewAddressLine();
					pa1.setSeq(i+1);
					pa1.setStringValue(address);
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.ZIP);
			if (fieldList != null && fieldList.size() > 0) {
				ct.addNewZipCode().setStringValue(fieldList.get(0));
			}

			// MARKER 1
			// MARKER 2

			// fieldList =
			// fieldMap.get(DefaultWorldTracerService.WorldTracerField.STATE);
			// if (fieldList != null && fieldList.size() > 0) {
			// ct.setState(fieldList.get(0));
			// }

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TA);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.ContactInfoAmendType.TempAddress.Address ta = ct.addNewTempAddress().addNewAddress();

				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					StringLength0To58AmendType ta1 = ta.addNewAddressLine();
					ta1.setSeq(i+1);
					ta1.setStringValue(address);
				}
			}

			// Set Flight Information
			int itinCount = 0;
			aero.sita.www.bag.wtr._2009._01.PassengerItineraryAmendType.Itinerary.FlightSegments p2 = p1.addNewItinerary().addNewFlightSegments();
			for (Itinerary itin : incident.getPaxItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
				} else {
					itinCount++;
					FlightSegmentType p3 = p2.addNewFlight().addNewFlightSegment();
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
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Amend AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				wsresponse = stub.update(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	private String getOnhandEndpoint(WorldTracerActionDTO dto) {
		return dto.getUser().getProfile().getParameters().get(ParameterType.ONHAND_ENDPOINT);
	}

	private String getDelayedEndpoint(WorldTracerActionDTO dto) {
		return dto.getUser().getProfile().getParameters().get(ParameterType.DELAYED_ENDPOINT);
	}
	
	private String getRushEndpoint(WorldTracerActionDTO dto) {
		return dto.getUser().getProfile().getParameters().get(ParameterType.RUSH_ENDPOINT);
	}

	
}
