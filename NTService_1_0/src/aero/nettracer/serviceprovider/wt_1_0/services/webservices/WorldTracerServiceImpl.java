package aero.nettracer.serviceprovider.wt_1_0.services.webservices;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;

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
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iata.www.iata._2007._00.ErrorType;
import org.iata.www.iata._2007._00.StateProvType;
import org.iata.www.iata._2007._00.WarningType;

import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileRequestData;
import aero.nettracer.serviceprovider.wt_1_0.common.Address;
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
import aero.nettracer.serviceprovider.wt_1_0.common.PxfDetails;
import aero.nettracer.serviceprovider.wt_1_0.common.Qoh;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Tag;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.PreProcessor;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerRecordNotFoundException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.TxType;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
import aero.nettracer.serviceprovider.wt_1_0.services.ishares.service.CommandNotProperlyFormedException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.BasicRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.RuleMapper;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.UsWorldTracerRuleMap;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerConnectionException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format;
import aero.sita.www.bag.wtr._2009._01.AdditionalInfoAmendType.FurtherInfo;
import aero.sita.www.bag.wtr._2009._01.AlphaLength2To16;
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
import aero.sita.www.bag.wtr._2009._01.ContactInfoAmendType.TempAddress;
import aero.sita.www.bag.wtr._2009._01.ContactInfoType;
import aero.sita.www.bag.wtr._2009._01.ContentType;
import aero.sita.www.bag.wtr._2009._01.ContentsAmendType;
import aero.sita.www.bag.wtr._2009._01.ContentsType;
import aero.sita.www.bag.wtr._2009._01.CostType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupAmendType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagType;
import aero.sita.www.bag.wtr._2009._01.DelayedClaimAmendType;
import aero.sita.www.bag.wtr._2009._01.DelayedClaimAmendType.TracingFinalized;
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
import aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Language;
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
import aero.sita.www.bag.wtr._2009._01.InboxMessageSearchType;
import aero.sita.www.bag.wtr._2009._01.StringLength0To58AmendType;
import aero.sita.www.bag.wtr._2009._01.StringLength1To58;
import aero.sita.www.bag.wtr._2009._01.WTRAddressAmendType;
import aero.sita.www.bag.wtr._2009._01.WTRAddressAmendType.Country;
import aero.sita.www.bag.wtr._2009._01.WTRAddressAmendType.PostalCode;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecReadRSDocument.WTROnhandBagRecReadRS.Passengers;

import org.iata.www.iata._2007._00.TTYAddress;
import aero.sita.www.bag.wtr._2009._01.WTRBagsCreateRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecUpdateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRQuickOnhandBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReinstateRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRStatusRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRSuspendRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRTracingStateChangeRQType;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageCountRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageCountRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageCountRQDocument.WTRInboxMessageCountRQ;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageSendRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagsRequestRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageEraseRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagsRequestRQDocument;
import aero.sita.www.bag.wtr._2009._01.BagElmsType.Enum;
import aero.sita.www.bag.wtr._2009._01.InboxAreaType;
import aero.sita.www.bag.wtr._2009._01.WTRAddressType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupAmendType.DelayedBags.DelayedBag;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType.BaggageItinerary;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType.DelayedBags;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary.Routes;
import aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Initials.Intial;
import aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Names.Name;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryType.Itinerary.FlightSegments;
import aero.sita.www.bag.wtr._2009._01.PassengerType.Initials;
import aero.sita.www.bag.wtr._2009._01.PassengerType.Names;
import aero.sita.www.bag.wtr._2009._01.RushBagGroupType.RushDestinations;
import aero.sita.www.bag.wtr._2009._01.RushBagGroupType.RushFlights;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument.WTRCloseRecordsRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagRecReadRSDocument.WTRDelayedBagRecReadRS;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument.WTRDelayedBagsCreateRQ;
//import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQDocument.WTRDelayedBagsRecUpdateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQType;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.Bags;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.DeliveryCompany;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.DeliveryInfo;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.Bags.Bag;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.Bags.Bag.ColorType;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.OnHandBags;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.RushBagTags;
import aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.TeletypeAddresses;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument.WTROnhandBagCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecReadRSDocument.WTROnhandBagRecReadRS;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecUpdateRQDocument.WTROnhandBagRecUpdateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRQuickOnhandBagsCreateRQDocument.WTRQuickOnhandBagsCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRQuickOnhandBagsCreateRQDocument.WTRQuickOnhandBagsCreateRQ.BagTags;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagsRequestRQDocument.WTROnhandBagsRequestRQ;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageEraseRQDocument.WTRInboxMessageEraseRQ;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageSendRQDocument.WTRInboxMessageSendRQ;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRQDocument.WTRInboxMessageReadRQ;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRQDocument.WTRInboxMessageReadRQ;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagsRequestRQDocument.WTROnhandBagsRequestRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument.WTRReadRecordRQ;
import aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument.WTRRushBagsCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument.WTRRushBagsCreateRQ.SupplimentalInfo;
import aero.sita.www.bag.wtr.delayedbagservice.DelayedBagServiceStub;
import aero.sita.www.bag.wtr.onhandbagservice.OnhandBagServiceStub;
import aero.sita.www.bag.wtr.rushbagservice.RushBagServiceStub;
import aero.sita.www.bag.wtr.inboxservice.InboxServiceStub;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageCountRSDocument.WTRInboxMessageCountRS.MessageCounts.MessageCount;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageSendRQDocument.WTRInboxMessageSendRQ.Destination;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageSendRQDocument.WTRInboxMessageSendRQ.Destination.InboxAddress;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRQDocument.WTRInboxMessageReadRQ.InboxMessageSearch;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRSDocument.WTRInboxMessageReadRS.Messages;
import aero.sita.www.bag.wtr._2009._01.WTRInboxMessageReadRSDocument.WTRInboxMessageReadRS.Messages.Message;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagsRequestRQDocument.WTROnhandBagsRequestRQ.OnhandBags;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagsRequestRQDocument.WTROnhandBagsRequestRQ.QuickOnhandBags;
import aero.sita.www.bag.wtr._2009._01.impl.DelayedClaimAmendTypeImpl.TracingFinalizedImpl;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;

public class WorldTracerServiceImpl implements WorldTracerService {

	private static final String NEWLINE = "\n";
	private static final String POST_DISPATCH_SECURITY_VERIFICATION_HANDLER = "Post dispatch security verification handler";
	private static final String DISPATCH = "Dispatch";
	private static final String SECURITY = "Security";
	private static final String APACHE_RAMPART_INFLOW_HANDLER = "Apache Rampart inflow handler";
	private static final Integer INTEGER_4_MINUTES = new Integer(4 * 60 * 1000);
	// private static final String C_SECURE_CACERTS = "c:\\secure\\cacerts";
	// private static final String CHANGEIT = "changeit";
	private static final String UNABLE_TO_GENERATE_INCIDENT_MAPPING = "Unable to generate incident mapping";
	private static final String WEB_SERVICE_CONNECTION_ISSUE = "Web Service Connection Issue";
	private static final String UNKNOWN_FAILURE = "Unknown Failure";
	private static final String WEB_SERVICE_ERROR_MESSAGE = "WS Error: ";
	private static final String EXCEPTION_FOUND_RESPONSE = "Exception found... Response: ";
	private static final String ACTION_BEING_PERFORMED = "Action Being Performed: ";
	// private static final String JAVAX_NET_SSL_TRUST_STORE_PASSWORD =
	// "javax.net.ssl.trustStorePassword";
	// private static final String JAVAX_NET_SSL_TRUST_STORE =
	// "javax.net.ssl.trustStore";
	private static final String RAMPART = "rampart";
	private static final String POLICY_XML = "policy.xml";
	private static final String INSERT_INTO_WT_WS_TRANS_LOG_GMTTIME_DESCRIPTION_VALUES = "INSERT INTO WT_WS_TRANS_LOG (gmttime, description) VALUES (?, ?)";
	private static final String METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES = "Method not available via web services.";
	private static final Logger logger = Logger.getLogger(WorldTracerServiceImpl.class);
	private static BigDecimal VERSION_0_PT_1 = new BigDecimal(0.1).setScale(1, RoundingMode.HALF_UP);
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);

	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format.FREE_FLOW);
	private static final boolean FORCE_FAILURE = false;
	RuleMapper wtRuleMap = new UsWorldTracerRuleMap();

	private static final int MAX_CONTENT_DESC_LENGTH = 90;
	private static final int MAX_CONTENT_CAT_LINES = 1;
	private static final int MAX_CONTENT_SPLIT = 2;
	private static final int LOSS_COMMENT_MAX = 58;
  
	private boolean restrictedAreaType(WorldTracerActionDTO dto, String areaType) {
		String types = dto.getUser().getProfile().getParameters().get(ParameterType.RESTRICTED_AREA_TYPE);
		if(types != null){
			return types.contains(areaType) ;
		} else {
			//by default we allow all area types
			return false;
		}
	}
	
	
	public WorldTracerServiceImpl(WorldTracerActionDTO dto) {

	}
	
	protected static boolean validate(org.apache.xmlbeans.XmlObject xml){
		org.apache.xmlbeans.XmlOptions options = new XmlOptions();
		ArrayList<XmlError> errors = new ArrayList<XmlError>();
		options.setErrorListener(errors);
		try{
			boolean valid = xml.validate(options);
			if(!valid){
				for(XmlError error:errors){
					String errorStr = "Message: " + error.getMessage() + "\n";
					errorStr += "Location of invalid XML: " + error.getCursorLocation().xmlText() + "\n";
					logger.error(errorStr);
				}
			}
			return valid;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//TODO
	protected static Map<String,String> combineContentFields(Content [] contents){
		HashMap<String,String> m = new HashMap<String,String>();
		for(Content content:contents){
			if(content.getCategory() != null 
					&& content.getDescription() != null
					&& content.getCategory().trim().length() > 0 
					&& content.getDescription().trim().length() > 0){
				if(m.containsKey(content.getCategory())){
					m.put(content.getCategory(), m.get(content.getCategory()) + " " + content.getDescription());
				} else {
					m.put(content.getCategory(), content.getDescription());
				}
			}
		}
		HashMap<String,String> toReturn = new HashMap<String, String>();
		for(String key:m.keySet()){
			String s = m.get(key)
			.trim()
			.toUpperCase()
			.replaceAll(Format.CONTENT_FIELD.replaceChars(), " ")
			.replaceAll("\\s+", " ");
			ArrayList<String> al = aero.nettracer.serviceprovider.common.utils.StringUtils.splitOnWordBreak(s, MAX_CONTENT_DESC_LENGTH);			
			for(int j = 0; j < al.size() && j < MAX_CONTENT_CAT_LINES; j++){
				if(al.get(j).length() > 0){
					toReturn.put(key+j, al.get(j));
				}
			}
		}
		return toReturn;
	}

	public void writeToLog(String description) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction t = sess.beginTransaction();
		try {
			SQLQuery query = sess.createSQLQuery(INSERT_INTO_WT_WS_TRANS_LOG_GMTTIME_DESCRIPTION_VALUES);
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
		try{
			//which one to use?
			//OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

			WTRDeliveryOrderCreateRQDocument d = WTRDeliveryOrderCreateRQDocument.Factory.newInstance();
			WTRDeliveryOrderCreateRQ d1 = d.addNewWTRDeliveryOrderCreateRQ();
			
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID("NTRACER");
			
			RecordIdentifierType r = d1.addNewRecordID();
			//ishares has AHL, were as the examples for ws has DELAYED
			r.setRecordType(RecordType.DELAYED);
			RecordReferenceType r1 = r.addNewRecordReference();
			
			String ahlId = bdo.getAhlId();
			if(ahlId != null && ahlId.trim().length() > 0){
				r1.setAirlineCode(ahlId.substring(3, 5));
				r1.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
				r1.setStationCode(ahlId.substring(0, 3));
			}
			
			DeliveryOrder dOrder = d1.addNewDeliveryOrder();
			
			//delivery company - station, customer code, id
			
			DeliveryCompany dc = dOrder.addNewDeliveryCompany();
			dc.setID("01");//?
			dc.setCustomerCode("DS");//?
			dc.setStation(bdo.getStationCode());
			
			//delivery info - need delivery date 
			
			DeliveryInfo di = dOrder.addNewDeliveryInfo();
			if (bdo.getDeliveryDate() != null) {
				di.setDeliveryDate(bdo.getDeliveryDate());
			}
			
			//bags--need bag
			Bags bags = dOrder.addNewBags();
			int i = 0;
			for(Item item:bdo.getItems()){
				Bag b = bags.addNewBag();
				b.setSeq(++i);
				
				ColorType ct = b.addNewColorType();
				ct.setColorCode(ColorCodeType.Enum.forString(item.getColor()));
				if(item.getType() != null){
					//type is required
					ct.setTypeCode(new Integer(item.getType()));
				}
			}
			
			
			/*
			
			DeliveryInfo di = dOrder.addNewDeliveryInfo();
			aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.DeliveryInfo.Names n = null;
			int paxCount = 0;
			for (Passenger pax:bdo.getPassengers()){
				if(n == null){
					n = di.addNewNames();
				}
				aero.sita.www.bag.wtr._2009._01.WTRDeliveryOrderCreateRQDocument.WTRDeliveryOrderCreateRQ.DeliveryOrder.DeliveryInfo.Names.Name name = n.addNewName();
				name.setStringValue(pax.getLastname());
			}
			*/

			
			
			
//			String specificCommandType = "BDO";
//			Date DelivDate = new Date();
//			// Make sure we have a valid delivery date - need to consider if we do
//			// not have a DD
//			// should we just use todays date
//			if (bdo.getDeliveryDate() != null) {
//				DelivDate = bdo.getDeliveryDate().getTime();
//			} else {
//				throw new CommandNotProperlyFormedException();
//			}
//
//			HashMap<String, String> map = new HashMap<String, String>();
//			map.put("COMMAND_TYPE", connectionType);
//			map.put("FILE_TYPE", "AHL");
//			map.put("WT_AHL_ID", bdo.getAhlId());
//			map.put("DELIVERY_SERVICE", "DS");
//			map.put("STATION_CODE", bdo.getStationCode());
//			map.put("AIRLINE_CODE", bdo.getAirlineCode());
//			map.put("DELIVERY_SERVICE_ID", "01"); // Assume that we will always use
//													// the first delivery company in
//													// the predefined WT list
//			map.put("COLOR_TYPE", "01"); // Assume we will always deliver the first
//											// bag since we do not keep track of WT
//											// bags
//
//			// TODO see if there is a way to determine which bag we should deliver
//			// instead of defaulting to CT01
//
//			String command = "{COMMAND_TYPE} BDO {FILE_TYPE} {WT_AHL_ID}\r{DELIVERY_SERVICE} {STATION_CODE}{AIRLINE_CODE}{DELIVERY_SERVICE_ID}/CT{COLOR_TYPE}";
//
//			command = inputValuesIntoCommand(map, command);
			
			
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "INSERT BDO";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.createDeliveryOrder(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
					&& wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
						&& wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void eraseActionFile(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response)
			throws WorldTracerException {
		try {
			InboxServiceStub stub = new InboxServiceStub(getInboxEndpoint(dto));
			configureClient(stub, getEnvironment(dto));
			WTRInboxMessageEraseRQDocument d = WTRInboxMessageEraseRQDocument.Factory
					.newInstance();
			WTRInboxMessageEraseRQ d1 = d.addNewWTRInboxMessageEraseRQ();

			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID("NTRACER");

			InboxMessageSearchType ims = d1.addNewInboxMessageSearch();
			ims.setAirlineCode(data.getAirline());
			ims.setStationCode(data.getStation());
			ims.setAreaType(InboxAreaType.Enum.forString(data.getType()));
			ims.setDay(data.getDay());
			if(data.getSeq() != null && data.getSeq().trim().length() > 0){
				ims.setSeq(data.getSeq());
			}
			InboxMessageSearchType.MessageRange.Range r = ims
					.addNewMessageRange().addNewRange();
			r.setStart(data.getNumber());
			r.setEnd(data.getNumber());

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "INBOX ERASE";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.eraseMessages(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
					&& wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
						&& wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void requestOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws WorldTracerException {
		try {
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor
					.requestOhd(dto, data, response);
			DelayedBagServiceStub stub = new DelayedBagServiceStub(
					getDelayedEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

			WTROnhandBagsRequestRQDocument d = WTROnhandBagsRequestRQDocument.Factory
					.newInstance();
			WTROnhandBagsRequestRQ d1 = d.addNewWTROnhandBagsRequestRQ();
			
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID(PreProcessor.getAgentEntry(data.getAgent()));

			RecordReferenceType rrt = d1.addNewDelayedBag();
			String ahlId = data.getAhl().getAhlId();
			rrt.setAirlineCode(ahlId.substring(3, 5));
			rrt.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			rrt.setStationCode(ahlId.substring(0, 3));

			OnhandBags ohb = d1.addNewOnhandBags();
			RecordReferenceType rrt2 = ohb.addNewOnhandBag();
			String ohdId = data.getOhdId();
			rrt2.setAirlineCode(ohdId.substring(3, 5));
			rrt2.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
			rrt2.setStationCode(ohdId.substring(0, 3));

			String freeFormText = null;
			if ((List<String>) fieldMap.get(WorldTracerField.FI) != null
					&& ((List<String>) fieldMap.get(WorldTracerField.FI))
							.size() > 0) {
				freeFormText = ((List<String>) fieldMap
						.get(WorldTracerField.FI)).get(0);
			}

			if (freeFormText != null) {
				d1.setFurtherInfo(freeFormText);
				WTROnhandBagsRequestRQ.SupplimentalInfo si = d1.addNewSupplimentalInfo();
				si.addTextLine(freeFormText);
			}

			// TODO there is no teletype field in the wsdl
			// String[] myTeletypes = data.getTeletype();
			// if(myTeletypes != null) {
			// WTRInboxMessageSendRQ.TeletypeAddresses ta =
			// d1.addNewTeletypeAddresses();
			// List<String> txs = Arrays.asList(myTeletypes);
			// for(String tx: txs) {
			// ta.addTeletypeAddress(tx);
			// }
			// }

			if (data.getAhl().getPax() != null
					&& data.getAhl().getPax().length > 0) {
				WTROnhandBagsRequestRQ.Names n = d1.addNewNames();
				for (int i = 0; i < data.getAhl().getPax().length && i < 3; i++) {
					Passenger myPax = data.getAhl().getPax()[i];
					n.addName(myPax.getLastname());
				}
			}

			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "ONHAND REQUEST";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.requestOnhand(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
					&& wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
						&& wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}

		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data,
			WorldTracerResponse response) throws WorldTracerException {
		try {
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor
					.requestOhd(dto, data, response);
			DelayedBagServiceStub stub = new DelayedBagServiceStub(
					getDelayedEndpoint(dto));
			configureClient(stub, getEnvironment(dto));
			
			WTROnhandBagsRequestRQDocument d = WTROnhandBagsRequestRQDocument.Factory
					.newInstance();
			WTROnhandBagsRequestRQ d1 = d.addNewWTROnhandBagsRequestRQ();
			
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID(PreProcessor.getAgentEntry(data.getAgent()));

			RecordReferenceType rrt = d1.addNewDelayedBag();
			String ahlId = data.getAhl().getAhlId();
			rrt.setAirlineCode(ahlId.substring(3, 5));
			rrt.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));
			rrt.setStationCode(ahlId.substring(0, 3));

//			OnhandBags ohb = d1.addNewOnhandBags();
//			RecordReferenceType rrt2 = ohb.addNewOnhandBag();
//			String ohdId = data.getOhdId();
//			rrt2.setAirlineCode(ohdId.substring(3, 5));
//			rrt2.setReferenceNumber(Integer.parseInt(ohdId.substring(5)));
//			rrt2.setStationCode(ohdId.substring(0, 3));
			
			QuickOnhandBags ohb = d1.addNewQuickOnhandBags();
			ohb.setAirlineCode(data.getFromAirline());
			ohb.setStationCode(data.getFromStation());
			BagTagType bt = ohb.addNewBagTag();
			bt.setAirlineCode(data.getFromAirline());
			bt.setTagSequence(((List<String>)fieldMap.get(WorldTracerField.TN)).get(0).substring(2));
			

			String freeFormText = null;
			if ((List<String>) fieldMap.get(WorldTracerField.FI) != null
					&& ((List<String>) fieldMap.get(WorldTracerField.FI))
							.size() > 0) {
				freeFormText = ((List<String>) fieldMap
						.get(WorldTracerField.FI)).get(0);
			}

			if (freeFormText != null) {
				d1.setFurtherInfo(freeFormText);
				WTROnhandBagsRequestRQ.SupplimentalInfo si = d1.addNewSupplimentalInfo();
				si.addTextLine(freeFormText);
			}

			// TODO there is no teletype field in the wsdl
			// String[] myTeletypes = data.getTeletype();
			// if(myTeletypes != null) {
			// WTRInboxMessageSendRQ.TeletypeAddresses ta =
			// d1.addNewTeletypeAddresses();
			// List<String> txs = Arrays.asList(myTeletypes);
			// for(String tx: txs) {
			// ta.addTeletypeAddress(tx);
			// }
			// }

			if (data.getAhl().getPax() != null
					&& data.getAhl().getPax().length > 0) {
				WTROnhandBagsRequestRQ.Names n = d1.addNewNames();
				for (int i = 0; i < data.getAhl().getPax().length && i < 3; i++) {
					Passenger myPax = data.getAhl().getPax()[i];
					n.addName(myPax.getLastname());
				}
			}

			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "ONHAND REQUEST";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.requestOnhand(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
					&& wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
						&& wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}

		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void placeActionFile(WorldTracerActionDTO dto, Pxf pxf,
			WorldTracerResponse response) throws WorldTracerException {
		try {
			InboxServiceStub stub = new InboxServiceStub(getInboxEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

			WTRInboxMessageSendRQDocument d = WTRInboxMessageSendRQDocument.Factory
					.newInstance();
			WTRInboxMessageSendRQ d1 = d.addNewWTRInboxMessageSendRQ();

			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID("NTRACER");

			Destination des = d1.addNewDestination();
			PxfDetails[] myPxfDetails = pxf.getPxfDetails();
			if (myPxfDetails != null) {
				List<PxfDetails> targets = Arrays.asList(myPxfDetails);
				for (PxfDetails target : targets) {
					if(target.getAirline().trim().length() > 0 && target.getStation().trim().length() > 0){
						InboxAddress i = des.addNewInboxAddress();
						i.setAirlineCode(target.getAirline().trim().toUpperCase());
						i.setStationCode(target.getStation().trim().toUpperCase());
						i.setAreaType(InboxAreaType.Enum
								.forString(target.getArea()));
					}
				}
			}

			String[] myTeletypes = pxf.getTeletype();
			if (myTeletypes != null && myTeletypes.length > 0) {
				WTRInboxMessageSendRQ.TeletypeAddresses ta = null;
				List<String> txs = Arrays.asList(myTeletypes);
				for (String tx : txs) {
					if(tx.trim().length() > 0){
						if (ta == null){
							ta = d1.addNewTeletypeAddresses();
						}
						ta.addTeletypeAddress(tx.trim().toUpperCase());
					}
				}
			}

			d1.setOriginStation(pxf.getSendingStation());
			// TODO find character limit from wsdl //3000
			d1.setMessage(pxf.getContent().toUpperCase());

			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "INBOX SEND";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.sendMessage(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null
					&& wsresponse.getWTRStatusRS().getSuccess() != null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null
						&& wsresponse.getWTRStatusRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRStatusRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}

		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void getActionFileDetails(WorldTracerActionDTO dto, ActionFileRequestData data, WorldTracerResponse response)
			throws WorldTracerException {throw new WorldTracerException(METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES);
	}

	private static double parsePercentMatch(String content) {
		Pattern PERCENT_PATT = Pattern
				.compile("SCORE\\s*-\\s*(\\d+(\\.\\d{1,2})?)");
		if (content == null)
			return 0.0D;
		Matcher m = PERCENT_PATT.matcher(content);
		if (m.find()) {
			return Double.parseDouble(m.group(1));
		}
		return 0.0D;
	}

	private static String parseAhlId(String content) {
		Pattern AHL_PATT = Pattern.compile(
				"(?:\\bAHL\\s+|A/|FILE\\s+)(\\w{5}\\d{5})\\b",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		if (content == null)
			return null;
		Matcher m = AHL_PATT.matcher(content);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	private static String parseOhdId(String content) {
		Pattern OHD_PATT = Pattern.compile(
				"(?:\\bOHD\\s+|O/|ON-HAND\\s+)(\\w{5}\\d{5})\\b",
				Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		if (content == null)
			return null;
		Matcher m = OHD_PATT.matcher(content);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	public void getActionFileSummary(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response)
			throws WorldTracerException {

		try {
			InboxServiceStub stub = new InboxServiceStub(getInboxEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

			WTRInboxMessageReadRQDocument d = WTRInboxMessageReadRQDocument.Factory
					.newInstance();
			WTRInboxMessageReadRQ d1 = d.addNewWTRInboxMessageReadRQ();

			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID("NTRACER");

			InboxMessageSearch ims = d1.addNewInboxMessageSearch();
			ims.setAirlineCode(data.getAirline());
			ims.setStationCode(data.getStation());
			ims.setAreaType(InboxAreaType.Enum.forString(data.getType()));
			ims.setDay(data.getDay());
			if(data.getSeq() != null && data.getSeq().trim().length() > 0){
				ims.setSeq(data.getSeq());
			}

			WTRInboxMessageReadRSDocument wsresponse = null;
			try {
				String label = "INBOX READ";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.readMessages(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}
			if (wsresponse != null
					&& wsresponse.getWTRInboxMessageReadRS() != null
					&& wsresponse.getWTRInboxMessageReadRS().getMessages() != null) {
				response.setSuccess(true);
				
				ArrayList<ActionFile> afal = new ArrayList<ActionFile>();
				if (wsresponse.getWTRInboxMessageReadRS().getMessages().getMessageArray() != null) {
					for (Message m : wsresponse.getWTRInboxMessageReadRS()
							.getMessages().getMessageArray()) {
						ActionFile result = new ActionFile();
						result.setItemNumber(m.getID());
						result.setSummary(m.getStringValue());
						result.setDetails(m.getStringValue());
						result.setAhlId(parseAhlId(m.getStringValue()));
						result.setOhdId(parseOhdId(m.getStringValue()));
						result.setPercentMatch(parsePercentMatch(m
								.getStringValue()));
						afal.add(result);
					}
				}

				response.setActionFiles(afal
						.toArray(new ActionFile[afal.size()]));
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null
						&& wsresponse.getWTRInboxMessageReadRS() != null
						&& wsresponse.getWTRInboxMessageReadRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRInboxMessageReadRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
						if (error.toString().indexOf("NO MESSAGES ON FILE") > -1){
							logger.error("NO MESSAGES ON FILE");
							throw new WorldTracerRecordNotFoundException();
						}
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}

		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void getActionFileCounts(WorldTracerActionDTO dto,
			ActionFileRequestData data, WorldTracerResponse response)
			throws WorldTracerException {
		try {
			InboxServiceStub stub = new InboxServiceStub(getInboxEndpoint(dto));

			configureClient(stub, getEnvironment(dto));

			WTRInboxMessageCountRQDocument d = WTRInboxMessageCountRQDocument.Factory
					.newInstance();
			WTRInboxMessageCountRQ d1 = d.addNewWTRInboxMessageCountRQ();

			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(
					dto.getUser().getProfile().getAirline());
			d1.setAgentID("NTRACER");

			StationAirlineType sat = d1.addNewStationAirline();
			sat.setAirlineCode(data.getAirline());
			sat.setStationCode(data.getStation());

			// Send Message
			WTRInboxMessageCountRSDocument wsresponse = null;
			try {
				String label = "INBOX COUNT";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.getMessageCount(d);

				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null
					&& wsresponse.getWTRInboxMessageCountRS() != null) {

				ArrayList<ActionFileCount> afcal = new ArrayList<ActionFileCount>();
				if (wsresponse.getWTRInboxMessageCountRS().getMessageCounts() != null) {

					MessageCount[] mcarray = wsresponse.getWTRInboxMessageCountRS().getMessageCounts().getMessageCountArray();
					for (MessageCount mc : mcarray) {
						if(!restrictedAreaType(dto,mc.getAreaType().toString())){
							if (mc.getDay1() > 0) {
								ActionFileCount afc1 = new ActionFileCount();
								afc1.setType(mc.getAreaType().toString());
								afc1.setDay(1);
								afc1.setCount(mc.getDay1());
								if (mc.getSeq() != null){
									afc1.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc1);
							}
							if (mc.getDay2() > 0) {
								ActionFileCount afc2 = new ActionFileCount();
								afc2.setType(mc.getAreaType().toString());
								afc2.setDay(2);
								afc2.setCount(mc.getDay2());
								if (mc.getSeq() != null){
									afc2.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc2);
							}
							if (mc.getDay3() > 0) {
								ActionFileCount afc3 = new ActionFileCount();
								afc3.setType(mc.getAreaType().toString());
								afc3.setDay(3);
								afc3.setCount(mc.getDay3());
								if (mc.getSeq() != null){
									afc3.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc3);
							}
							if (mc.getDay4() > 0) {
								ActionFileCount afc4 = new ActionFileCount();
								afc4.setType(mc.getAreaType().toString());
								afc4.setDay(4);
								afc4.setCount(mc.getDay4());
								if (mc.getSeq() != null){
									afc4.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc4);
							}
							if (mc.getDay5() > 0) {
								ActionFileCount afc5 = new ActionFileCount();
								afc5.setType(mc.getAreaType().toString());
								afc5.setDay(5);
								afc5.setCount(mc.getDay5());
								if (mc.getSeq() != null){
									afc5.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc5);
							}
							if (mc.getDay6() > 0) {
								ActionFileCount afc6 = new ActionFileCount();
								afc6.setType(mc.getAreaType().toString());
								afc6.setDay(6);
								afc6.setCount(mc.getDay6());
								if (mc.getSeq() != null){
									afc6.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc6);
							}
							if (mc.getDay7() > 0) {
								ActionFileCount afc7 = new ActionFileCount();
								afc7.setType(mc.getAreaType().toString());
								afc7.setDay(7);
								afc7.setCount(mc.getDay7());
								if (mc.getSeq() != null){
									afc7.setSeq(mc.getSeq().toString());
								}
								afcal.add(afc7);
							}
						}
					}
				}
				response.setCounts(afcal.toArray(new ActionFileCount[afcal
						.size()]));

			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null
						&& wsresponse.getWTRInboxMessageCountRS() != null
						&& wsresponse.getWTRInboxMessageCountRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRInboxMessageCountRS()
							.getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE
								+ error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(
					WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}


	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(WorldTracerServiceImpl.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}

	private static void configureClient(org.apache.axis2.client.Stub stub, String envIdentifier) throws AxisFault {

		// System.setProperty(JAVAX_NET_SSL_TRUST_STORE, C_SECURE_CACERTS);
		// System.setProperty(JAVAX_NET_SSL_TRUST_STORE_PASSWORD, CHANGEIT);
		//
		// System.out.println(System.getProperty(JAVAX_NET_SSL_TRUST_STORE));
		// System.out.println(System.getProperty(JAVAX_NET_SSL_TRUST_STORE_PASSWORD));

		ServiceClient client = stub._getServiceClient();

		stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, INTEGER_4_MINUTES);
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, INTEGER_4_MINUTES);

		client.engageModule(RAMPART);
		try {
			String policy = "/" + envIdentifier + "_" + POLICY_XML;
			logger.debug("Policy: " + policy);
			client.getAxisService().getPolicySubject().attachPolicy(loadPolicy(policy));
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
			configureClient(stub, getEnvironment(dto));

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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			configureClient(stub, getEnvironment(dto));

			WTROnhandBagCreateRQDocument d = WTROnhandBagCreateRQDocument.Factory.newInstance();
			WTROnhandBagCreateRQ d1 = d.addNewWTROnhandBagCreateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			OnHandBagType d2 = d1.addNewOnHandBag();
			
			String freeFormText = null;
			if ((List<String>) fieldMap.get(WorldTracerField.FI) != null
					&& ((List<String>) fieldMap.get(WorldTracerField.FI))
							.size() > 0) {
				freeFormText = ((List<String>) fieldMap
						.get(WorldTracerField.FI)).get(0);
			}

			if (freeFormText != null) {
				d1.addNewAdditionalInfo().setFurtherInfo(freeFormText);
			}

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
				String bi = RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0));
				if(ohd.getItem() != null && ohd.getItem().getExternaldesc() != null && ohd.getItem().getExternaldesc().trim().length() > 0){
					bi = bi + "/" + RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(ohd.getItem().getExternaldesc().trim());
				}
				if(bi.length()>50){
					d2.addNewBrandInfo().setStringValue(bi.substring(0,50));
				} else {
					d2.addNewBrandInfo().setStringValue(bi);
				}
			} else if(ohd.getItem() != null && ohd.getItem().getExternaldesc() != null && ohd.getItem().getExternaldesc().trim().length() > 0){
				String bi = "YY/" + RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(ohd.getItem().getExternaldesc().trim());
				if(bi.length()>50){
					d2.addNewBrandInfo().setStringValue(bi.substring(0,50));
				} else {
					d2.addNewBrandInfo().setStringValue(bi);
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);
			if (fieldList != null && fieldList.size() > 0) {
				String airtag = fieldList.get(0);

				try {
					airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
				} catch (BagtagException e) {
					logger.debug("Unable to convert bagtag while creating fwd: " + airtag);
				}
				if (airtag != null) {
					BagTagType t3 = d2.addNewBagTag();
					t3.setAirlineCode(airtag.substring(0, 2));
					t3.setTagSequence(airtag.substring(2));					
				}
			}

			int itinCount = 0;
			aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary i1 = d2.addNewItinerary();
			aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary.FlightSegments f1 = i1.addNewFlightSegments();
			Routes rts = i1.addNewRoutes();

			for (Itinerary itin : ohd.getBagItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
				} else {

					FlightOptionalDateOrARNKType p3 = f1.addNewFlightSegment();
					FlightOptionalDateType p5 = p3.addNewFlightDate();

					String flightNum = PreProcessor.wtFlightNumber(itin.getFlightNumber());
					String airlineCode = itin.getAirline();
					if (flightNum == null || flightNum.equals("")
							|| flightNum.equals("00")) {
						airlineCode = "YY";
						// do not set the flightNum
					} else {
						p5.setFlightNumber(flightNum);
					}
					p5.setAirlineCode(airlineCode);
					p5.setDate(itin.getFlightDate());

					if (itinCount == 0) {
						rts.addNewRoute().setStringValue(itin.getDepartureCity().trim());
					}
					rts.addNewRoute().setStringValue(itin.getArrivalCity().trim());
					itinCount++;
				}
			}

			PassengerType p1 = d1.addNewPassengers();
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			List<String> fieldList2 = fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			Names names = null;
			Initials initials = null;
			if (fieldList != null) {
				for (int j = 0; j < fieldList.size(); j++) {
					if (names == null) {
						names = p1.addNewNames();
					}
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));

					String name2="";
					if(name!=null && name.length()>0)
						name2=name.replace(" ", "");
					names.addName(name2);
					if (fieldList2 != null && fieldList2.size() > j) {
						if(initials == null){
							initials = p1.addNewInitials();
						}
						initials.addNewIntial().setStringValue(fieldList2.get(j));
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
				WTRAddressType ba = d2.addNewBagAddress();
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

			
			if (ohd.getItem() != null && ohd.getItem().getContent() != null){
				Content[] contentsList = ohd.getItem().getContent();
				ContentsType c2 = null;
				
				Map<String,String> cm = combineContentFields(contentsList);
				
				for(String key:cm.keySet()){
					if(c2 == null){
						c2 = d2.addNewBagContents();
					}
					ContentType c = c2.addNewContent();
					c.setCategory(key.substring(0, key.length()-1));
//					c.setCategory(key);
					c.setDescription(cm.get(key));
				}
				
//				for (Content content:contentsList){
//					if(content.getDescription().trim().length() > 0 && content.getCategory().equals("UNKNOWN") == false){
//						if(c2 == null){
//							c2 = d2.addNewBagContents();
//						}
//						String desc = content.getDescription()
//						.trim()
//						.toUpperCase()
//						.replaceAll(Format.CONTENT_FIELD.replaceChars(), " ")
//						.replaceAll("\\s+", " ");
//						ArrayList<String> al = aero.nettracer.serviceprovider.common.utils.StringUtils.splitOnWordBreak(desc, MAX_CONTENT_DESC_LENGTH);
//						for(int i = 0; i < al.size() && i < MAX_CONTENT_SPLIT; i++){
//							if(al.get(i).length() > 0){
//								ContentType c = c2.addNewContent();
//								c.setCategory(content.getCategory());
//								c.setDescription(al.get(i));
//							}
//						}
//					}
//				}
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
				validate(d);
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
				String ohdId = rrt.getStationCode() + rrt.getAirlineCode() + rrt.getReferenceNumber();
				Ohd ohdx = new Ohd();
				ohdx.setOhdId(ohdId);
				response.setOhd(ohdx);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRBagsCreateRS() != null && wsresponse.getWTRBagsCreateRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRBagsCreateRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void reinstateOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {
		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void suspendOhd(WorldTracerActionDTO dto, Ohd ohd, WorldTracerResponse response) throws WorldTracerException {

		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			configureClient(stub, getEnvironment(dto));

			WTRDelayedBagsCreateRQDocument d = WTRDelayedBagsCreateRQDocument.Factory.newInstance();
			WTRDelayedBagsCreateRQ d1 = d.addNewWTRDelayedBagsCreateRQ();

			String freeFormText = null;
			if ((List<String>) fieldMap.get(WorldTracerField.FI) != null
					&& ((List<String>) fieldMap.get(WorldTracerField.FI))
							.size() > 0) {
				freeFormText = ((List<String>) fieldMap
						.get(WorldTracerField.FI)).get(0);
			}

			if (freeFormText != null) {
				d1.addNewAdditionalInfo().setFurtherInfo(freeFormText);
			}
			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(data.getAirlineCode());

			// Set values required to close a claim
			DelayedClaimType d2 = d1.addNewClaim();

			if (incident.getFaultReason() != 0 || incident.getFaultReason() > 79) {
				d2.setLossReasonCode(incident.getFaultReason());
				d2.setLossComments(StringUtils.substring(incident.getFaultReasonDescription(), 0, LOSS_COMMENT_MAX));

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

			Item[] items = data.getItem();
			
			if (fieldList != null) {
				for (int i = 0; i < fieldList.size() && i < 10; i++) {

					String bagFace = fieldList.get(i);
					String colorType = bagFace.substring(0, 2);
					String bagType = bagFace.substring(2, 4);
					String bagDesc = bagFace.substring(4, 7);

					DelayedBagType t3 = t22.addNewDelayedBag();
					ContentsType cx = null;
//					ContentsType cx = t3.addNewBagContents();

					if (tagList != null && i < tagList.size()) {
						BagTagType tag = t3.addNewBagTag();
						String airtag = tagList.get(i);
						tag.setAirlineCode(airtag.substring(0, 2));
						tag.setTagSequence(airtag.substring(2));
					}

//					if (fieldList2 != null && i < fieldList2.size()) {
//						t3.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList2.get(i)));
//					}
					
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
					
					if (items.length > i ){
						Item item = items[i];
						if (item.getManufacturer() != null && item.getManufacturer().length() > 0) {
							String brandinfo=(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(item.getManufacturer())+"/"+
									RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(item.getExternaldesc()));
							if(brandinfo.length()>50){
								t3.addNewBrandInfo().setStringValue(brandinfo.substring(0, 50));
							} else {
								t3.addNewBrandInfo().setStringValue(brandinfo);
							}
						} else if(item.getExternaldesc() != null && item.getExternaldesc().trim().length() > 0){
							String brandinfo=("YY/"+	RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(item.getExternaldesc()));
							if(brandinfo.length()>50){
								t3.addNewBrandInfo().setStringValue(brandinfo.substring(0, 50));
							} else {
								t3.addNewBrandInfo().setStringValue(brandinfo);
							}
						}
						if (item.getContent() != null){
							Content[] cList = item.getContent();
							
							Map<String,String>cm = combineContentFields(cList);
							for(String key:cm.keySet()){
								if(cx == null){
									cx = t3.addNewBagContents();
								}
								ContentType c = cx.addNewContent();
								c.setCategory(key.substring(0, key.length()-1));
//								c.setCategory(key);
								c.setDescription(cm.get(key));
							}
//							for (Content content:cList){
//								String desc = content.getDescription()
//								.trim()
//								.toUpperCase()
//								.replaceAll(Format.CONTENT_FIELD.replaceChars(), " ")
//								.replaceAll("\\s+", " ");
//								ArrayList<String> al = aero.nettracer.serviceprovider.common.utils.StringUtils.splitOnWordBreak(desc, MAX_CONTENT_DESC_LENGTH);
//								for(int j = 0; j < al.size() && j < MAX_CONTENT_SPLIT; j++){
//									if(al.get(j).length() > 0){
//										ContentType c = cx.addNewContent();
//										c.setCategory(content.getCategory());
//										c.setDescription(al.get(j));
//									}
//								}
//							}
						}
					} else {
						throw new WorldTracerConnectionException("Bag/Item mismatch");
					}
				}
			}

			// Set Passengers & Contact Information
			PassengerItineraryType p1 = d1.addNewPassengers();
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
			fieldList2 = fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			if (fieldList != null && fieldList.size() > 0) {
				Names pp1 = p1.addNewNames();
				Initials pi1 = null;
				for (int j = 0; j < fieldList.size(); j++) {
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					if(name!=null && name.length()>0)
						name=name.replace(" ", "");
					pp1.addName(name);
					

					if (fieldList2 != null && fieldList2.size() > j) {
						if(pi1 == null){
							pi1 = p1.addNewInitials();
						}
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
				String status = fieldList.get(0);
				if (status.contains("/")) {
					String[] st = null;
					if (status != null) {
						st = status.split("/");
						if (st.length > 0) {
							p1.setStatus(st[0]);
						}
						if (st.length > 1) {
							String fareType = st[1].substring(0, 1);
							String fareBasis = null;
							if (fareType.equalsIgnoreCase("B")) {
								fareBasis = "C";
							} else if (fareType.equalsIgnoreCase("F")) {
								fareBasis = "F";
							} else {
								fareBasis = "Y";
							}
							p1.setFareBasis(fareBasis);
						}
					}
				} else {
					p1.setStatus(status);
					p1.setFareBasis(status);
				}

			}


			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.LA);
			if (fieldList != null && fieldList.size() > 0) {
				String language = RULES.get(DefaultWorldTracerService.WorldTracerField.LA).formatEntry(fieldList.get(0));
				p1.setLanguage(language);
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
				WTRAddressType pa = ct.addNewPermanentAddress();
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					pa.addAddressLine(address);
				}
			}
			
//			Address ntpa = PreProcessor.getAhlAddress(data, true);
//			
//			if(ntpa != null){
//				WTRAddressType pa = ct.addNewPermanentAddress();
//				if(ntpa.getAddress1()!=null && ntpa.getAddress1().length() > 0){
//					StringLength1To58 pa1 = pa.addNewAddressLine();
//					pa1.setStringValue(ntpa.getAddress1());
//				}
//				if(ntpa.getAddress2()!=null && ntpa.getAddress2().length() > 0){
//					StringLength1To58 pa2 = pa.addNewAddressLine();
//					pa2.setStringValue(ntpa.getAddress2());
//				}
//				if(ntpa.getCity()!=null && ntpa.getCity().length() > 0){
//					pa.setCity(ntpa.getCity());
//				}
//				if(ntpa.getState() != null && ntpa.getState().length() > 0 
//						&& ntpa.getCountryCode() != null 
//						&& (ntpa.getCountryCode().equalsIgnoreCase("US") || ntpa.getCountryCode().equalsIgnoreCase("United States"))){
//					StateProvType state = pa.addNewState();
//					state.setStringValue(ntpa.getState());
//					pa.setState(state);
//				} else if(ntpa.getProvince() != null && ntpa.getProvince().length() > 0){
//					//there is no province field for WT
//					StateProvType state = pa.addNewState();
//					state.setStringValue(ntpa.getProvince());
//					pa.setState(state);
//				}
//				if(ntpa.getZip() != null && ntpa.getZip().length() > 0){
//					aero.sita.www.bag.wtr._2009._01.WTRAddressType.PostalCode zip = pa.addNewPostalCode();
//					zip.setStringValue(ntpa.getZip());
//					pa.setPostalCode(zip);
//				}
//				if(ntpa.getCountryCode() != null && ntpa.getCountryCode().length() > 0){
//					aero.sita.www.bag.wtr._2009._01.WTRAddressType.Country country = pa.addNewCountry();
//					country.setStringValue(ntpa.getCountryCode());
//					pa.setCountry(country);
//				}
//			}
			

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
				WTRAddressType ta = ct.addNewTempAddress().addNewAddress();			
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					ta.addAddressLine(address);
				}
			}

			//get first temp address
//			Address ntta = PreProcessor.getAhlAddress(data, false);
//			
//			if(ntta != null){
//				aero.sita.www.bag.wtr._2009._01.ContactInfoType.TempAddress ta = ct.addNewTempAddress();
//				WTRAddressType t = ta.addNewAddress();
//				if(ntta.getAddress1()!=null && ntta.getAddress1().length() > 0){
//					StringLength1To58 ta1 = t.addNewAddressLine();
//					ta1.setStringValue(ntta.getAddress1());
//				}
//				if(ntta.getAddress2()!=null && ntta.getAddress2().length() > 0){
//					StringLength1To58 ta2 = t.addNewAddressLine();
//					ta2.setStringValue(ntta.getAddress2());
//				}
//				if(ntta.getCity()!=null && ntta.getCity().length() > 0){
//					t.setCity(ntta.getCity());
//				}
//				if(ntta.getState() != null && ntta.getState().length() > 0 
//						&& ntta.getCountryCode() != null 
//						&& (ntta.getCountryCode().equalsIgnoreCase("US") || ntta.getCountryCode().equalsIgnoreCase("United States"))){
//					StateProvType state = t.addNewState();
//					state.setStringValue(ntta.getState());
//					t.setState(state);
//				} else if(ntta.getProvince() != null && ntta.getProvince().length() > 0){
//					//there is no province field for WT
//					StateProvType state = t.addNewState();
//					state.setStringValue(ntta.getProvince());
//					t.setState(state);
//				}
//				if(ntta.getZip() != null && ntta.getZip().length() > 0){
//					aero.sita.www.bag.wtr._2009._01.WTRAddressType.PostalCode zip = t.addNewPostalCode();
//					zip.setStringValue(ntta.getZip());
//					t.setPostalCode(zip);
//				}
//				if(ntta.getCountryCode() != null && ntta.getCountryCode().length() > 0){
//					aero.sita.www.bag.wtr._2009._01.WTRAddressType.Country country = t.addNewCountry();
//					country.setStringValue(ntta.getCountryCode());
//					t.setCountry(country);
//				}
//			}
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
			if (fieldList != null && fieldList.size() > 0) {
				d1.setAgentID(fieldList.get(0));
			}

			// Send Message
			WTRBagsCreateRSDocument wsresponse = null;
			try {
				String label = "Create AHL";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

	public void suspendAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void reinstateAhl(WorldTracerActionDTO dto, Ahl ahl, WorldTracerResponse response) throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			//TODO loupas handle error message for logging
			throw new WorldTracerConnectionException("Communication error with WorldTracer", e);
		}
	}

	private boolean closeInc(Map<WorldTracerField, List<String>> fieldMap, String ahlId, String stationCode, Ahl ahl, WorldTracerResponse response, WorldTracerActionDTO dto)
			throws WorldTracerException {
		try {
			DelayedBagServiceStub stub = new DelayedBagServiceStub(getDelayedEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

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
				validate(d);
				wsresponse = stub.close(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRStatusRS() != null && wsresponse.getWTRStatusRS().getSuccess() != null && wsresponse.getWTRStatusRS().getErrors() == null
					&& wsresponse.getWTRStatusRS().getWarnings() == null) {
				response.setSuccess(true);
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRStatusRS() != null) {
					if (wsresponse.getWTRStatusRS().getErrors() != null) {
						ErrorType[] errors = wsresponse.getWTRStatusRS().getErrors().getErrorArray();
						for (ErrorType error : errors) {
							errorMsg.append(error.getStringValue());
							logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
						}
					}

					if (wsresponse.getWTRStatusRS().getWarnings() != null) {
						WarningType[] errors = wsresponse.getWTRStatusRS().getWarnings().getWarningArray();
						for (WarningType error : errors) {
							if (error.getStringValue().contains("RECORD ALREADY CLOSED")) {
								response.setSuccess(true);
							} else {
								errorMsg.append(error.getStringValue());
								logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
							}
						}
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			configureClient(stub, getEnvironment(dto));

			WTRForwardOnhandBagsRQDocument d = WTRForwardOnhandBagsRQDocument.Factory.newInstance();
			WTRForwardOnhandBagsRQ d1 = d.addNewWTRForwardOnhandBagsRQ();
			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordReferenceType rrt = d1.addNewDelayedBag();
			String ahlId = data.getAhlId().trim().toUpperCase();
			rrt.setAirlineCode(ahlId.substring(3, 5));
			rrt.setStationCode(ahlId.substring(0, 3));
			rrt.setReferenceNumber(Integer.parseInt(ahlId.substring(5)));

			RecordReferenceType bags = d1.addNewOnHandBags().addNewRecordReference();
			String ohdId = data.getOhdId().trim().toUpperCase();
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
					if(name!=null && name.length()>0)
						name=name.replace(" ", "");
					names.addName(name.trim().toUpperCase());

				}
			}

			d1.addNewRushFlights();
			RushBagTags rbt = d1.addNewRushBagTags();

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.XT);
			if (fieldList != null && fieldList.size() > 0) {

				BagTagType btt = rbt.addNewBagTag();
				String airtag = fieldList.get(0).trim().toUpperCase();
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
						fdt.setAirlineCode(itin.getAirline().trim().toUpperCase());
						fdt.setDate(itin.getFlightDate());
						fdt.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber().trim().toUpperCase()));
						StationAirlineType sat = rds.addNewDestination();
						sat.setAirlineCode(itin.getAirline().trim().toUpperCase());
						sat.setStationCode(itin.getArrivalCity().trim().toUpperCase());
					}
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.SI);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.WTRForwardOnhandBagsRQDocument.WTRForwardOnhandBagsRQ.SupplimentalInfo d2 = d1.addNewSupplimentalInfo();
				String text = RULES.get(DefaultWorldTracerService.WorldTracerField.SI).formatEntry(fieldList.get(0));
				d2.addTextLine(text.trim().toUpperCase());
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TX);
			if (fieldList != null && fieldList.size() > 0) {
				TeletypeAddresses add = d1.addNewTeletypeAddresses();

				for (int i = 0; i < fieldList.size() && i < 10; i++) {
					add.addTeletypeAddress(fieldList.get(i).trim().toUpperCase());
				}
			}

			d1.setAgentID(PreProcessor.getAgentEntry(data.getAgent()).trim().toUpperCase());

			// Send Message
			WTRStatusRSDocument wsresponse = null;
			try {
				String label = "Forward OHD";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}
	}

	public void sendForwardMessage(WorldTracerActionDTO dto, ForwardMessage msg, WorldTracerResponse response) throws WorldTracerException {
		EnumMap<WorldTracerField, WorldTracerRule<String>> fwdRules = wtRuleMap.getRule(TxType.FWD_GENERAL);
		try {
			Map<WorldTracerField, List<String>> fieldMap = PreProcessor.createFwdFieldMap(msg, dto);

			RushBagServiceStub stub = new RushBagServiceStub(getRushEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

			WTRRushBagsCreateRQDocument d = WTRRushBagsCreateRQDocument.Factory.newInstance();
			WTRRushBagsCreateRQ d1 = d.addNewWTRRushBagsCreateRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			StationAirlineType s1 = d1.addNewRefStationAirline();
			//to-do
			
			s1.setAirlineCode(msg.getFromAirline().toUpperCase());
			s1.setStationCode(msg.getFromStation().toUpperCase());

			
			//example: YYCWS10042
			String crid = msg.getCrossReferenceId();
			if(crid != null){
				crid = crid.trim().toUpperCase();
				if(crid.matches("^[A-Z]{5}[0-9]+$")){
					RecordIdentifierType d3 = d1.addNewCrossReferenceRecord();
					RecordReferenceType d4 = d3.addNewRecordReference();
					d3.setRecordType(RecordType.DELAYED);
					d4.setAirlineCode(crid.substring(3, 5));
					d4.setReferenceNumber(Integer.parseInt(crid.substring(5)));
					d4.setStationCode(crid.substring(0, 3));
				}
			}

			RushBagGroupType rb = d1.addNewRushBagGroup();
			RushBagType rbt = rb.addNewRushBags().addNewRushBag();
			
			if(msg.getName() != null){
				aero.sita.www.bag.wtr._2009._01.RushBagGroupType.Names rbn = null;
				for(String name:msg.getName()){
					if(name.length()>1){
						if(name.length() > 16){
							name = name.substring(0, 16);
						}
						if(rbn == null){
							rbn = rb.addNewNames();
						}
						rbn.addName(name);
					}
				}
			}

			List<String> fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);
			if (fieldList != null && fieldList.size() > 0) {

				BagTagType btt = rbt.addNewOriginalBagTag();
				String airtag = fieldList.get(0);
				btt.setAirlineCode(airtag.substring(0, 2).toUpperCase());
				btt.setTagSequence(airtag.substring(2));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.XT);
			if (fieldList != null && fieldList.size() > 0) {

				BagTagType btt = rbt.addNewRushBagTag();
				String airtag = fieldList.get(0);
				btt.setAirlineCode(airtag.substring(0, 2).toUpperCase());
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
						fdt.setAirlineCode(itin.getAirline().toUpperCase());
						fdt.setDate(itin.getFlightDate());
						fdt.setFlightNumber(PreProcessor.wtFlightNumber(itin.getFlightNumber()));
						StationAirlineType sat = rds.addNewDestination();
						sat.setAirlineCode(itin.getAirline().toUpperCase());
						sat.setStationCode(itin.getArrivalCity().trim().toUpperCase());
					}
				}
			}

			if (msg.getFaultStation() != null) {
				d1.setFaultStationCode(msg.getFaultStation().trim().toUpperCase());
			}

			if (msg.getFaultReasonDescription() != null && msg.getFaultReasonDescription().trim().length() > 0) {
				d1.setLossComments(StringUtils.substring(msg.getFaultReasonDescription(), 0, LOSS_COMMENT_MAX));
			}

			if (msg.getFaultReason() != 0) {
				d1.setLossReasonCode(msg.getFaultReason());
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.SI);
			if (fieldList != null && fieldList.size() > 0) {
				SupplimentalInfo d2 = d1.addNewSupplimentalInfo();
				String text = fwdRules.get(DefaultWorldTracerService.WorldTracerField.SI).formatEntry(fieldList.get(0));
				d2.addTextLine(text.trim().toUpperCase());
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TX);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.WTRRushBagsCreateRQDocument.WTRRushBagsCreateRQ.TeletypeAddresses add = d1.addNewTeletypeAddresses();

				for (int i = 0; i < fieldList.size() && i < 10; i++) {
					add.addTeletypeAddress(fieldList.get(i).trim().toUpperCase());
				}
			}

			d1.setAgentID(PreProcessor.getAgentEntry(msg.getAgent()).trim().toUpperCase());

			// Send Message
			WTRBagsCreateRSDocument wsresponse = null;
			try {
				String label = "Send General Forward Msg";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			configureClient(stub, getEnvironment(dto));

			WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
			WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordIdentifierType t1 = d1.addNewRecordID();
			t1.setRecordType(RecordType.DELAYED);

			RecordReferenceType t2 = t1.addNewRecordReference();
			String ahlId = ahl.getAhlId().trim().toUpperCase();

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
				validate(d);
				wsresponse = stub.read(d);
				logger.info(wsresponse);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			if (wsresponse != null && wsresponse.getWTRDelayedBagRecReadRS() != null && wsresponse.getWTRDelayedBagRecReadRS().getDelayedBagGroup() != null) {

				handleResponse(response, ahlId, wsresponse);

			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTRDelayedBagRecReadRS() != null && wsresponse.getWTRDelayedBagRecReadRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTRDelayedBagRecReadRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
						if (error.toString().indexOf("RECORD NOT FOUND") > -1){
							logger.error("Record not found");
							throw new WorldTracerRecordNotFoundException();
						}
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			
			if(wr1.getAdditionalInfo()!=null){
				rahl.setFurtherInfo(wr1.getAdditionalInfo().getFurtherInfo());
			}

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
					//logger.error("Parsing issue: " + e, e);
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
					
					if(wsp.getLanguage()!=null){
						fPax.setLanguageFreeFlow(wsp.getLanguage());
					}
				}

				if (wsp.getFrequentFlyerID() != null) {
					fPax.setFfNumber(wsp.getFrequentFlyerID());
				}

				String status = "";
				if (wsp.getStatus() != null) {
					status = wsp.getStatus();
				}
				if (wsp.getFareBasis() != null) {
					if (status.length() > 0) {
						status += " " + wsp.getFareBasis();
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
					if(ci.getEmails()!=null){
						add.setEmailAddress(ci.getEmails().getEmailArray(0));
					}

					add.setZip(ci.getZipCode());
					if (ci.getCountry() != null)
						add.setCountryCode(ci.getCountry().getCountryCode());

					if (ci.getPermPhones() != null)
						add.setHomePhone(ci.getPermPhones().getPhoneArray(0));
					if (ci.getCellPhones() != null)
						add.setMobilePhone(ci.getCellPhones().getPhoneArray(0));
					if (ci.getTempPhones() != null)
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
								logger.error("Parsing issue: " + e, e);
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
							item.setContent(contents.toArray(new Content[0]));
						}

						if(bag.getBrandInfo() != null){
							item.setManufacturer(bag.getBrandInfo().getStringValue());
						}
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
					logger.error("Parsing issue: " + e, e);
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
			configureClient(stub, getEnvironment(dto));

			WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
			WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

			// Set version & POS
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());

			RecordIdentifierType t1 = d1.addNewRecordID();
			t1.setRecordType(RecordType.ON_HAND);

			RecordReferenceType t2 = t1.addNewRecordReference();
			String ohdId = ohd.getOhdId().trim().toUpperCase();

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
				validate(d);
				wsresponse = stub.read(d);
				logger.info(wsresponse);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(EXCEPTION_FOUND_RESPONSE + wsresponse, e);
			}

			// Process Response and/or Error Messages
			// It seems that a success state from WT is not guaranteed, so we are going to look for the non-existance of an error message.  Is this the right approach...who knows...
			if (wsresponse != null && wsresponse.getWTROnhandBagRecReadRS() != null && wsresponse.getWTROnhandBagRecReadRS().getErrors() == null) {
				response.setSuccess(true);

				Ohd rohd = new Ohd();
				response.setOhd(rohd);
				rohd.setOhdId(ohdId);
				rohd.setAirlineCode(ohdId.substring(3, 5));
				rohd.setStationCode(ohdId.substring(0, 3));

				WTROnhandBagRecReadRS ores = wsresponse.getWTROnhandBagRecReadRS();
				
				rohd.setCreateDate(ores.getDiaryInfo().getCreateDate());
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

					if (ores.getOnHandBag().getBagContents() != null
							&& ores.getOnHandBag().getBagContents().getContentArray() != null) {
						ArrayList<Content> contentList = new ArrayList<Content>();
						for(ContentType ct:ores.getOnHandBag().getBagContents().getContentArray()){
							Content c = new Content();
							c.setCategory(ct.getCategory());
							c.setDescription(ct.getDescription());
							contentList.add(c);
						}
						if(contentList.size() > 0){
							item.setContent(contentList.toArray(new Content[0]));
						}
					}
					
					if(ores.getOnHandBag().getBrandInfo() != null){
						item.setManufacturer(ores.getOnHandBag().getBrandInfo().getStringValue());
					}
					if (ores.getOnHandBag().getBagTag() != null) {
						ClaimCheck cc = new ClaimCheck();
						rohd.setClaimCheck(cc);
						cc.setAirlineCode(ores.getOnHandBag().getBagTag().getAirlineCode());
						cc.setTagNumber(ores.getOnHandBag().getBagTag().getTagSequence());
					}

				}
				if(ores.getAdditionalInfo()!=null && ores.getAdditionalInfo().getFurtherInfo()!=null){
					rohd.setFurtherInfo(ores.getAdditionalInfo().getFurtherInfo());
				}
				
				if(ores.getPassengers()!=null){

					Passengers wsp = ores.getPassengers();

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
					rohd.setPax(paxList.toArray(new Passenger[paxList.size()]));

					if (fPax != null) {
						if (wsp.getTitle() != null) {
							// HERE
							int sal = mapSalutation(wsp.getTitle());
							fPax.setSalutation(sal);
						}
						
						if(wsp.getLanguage()!=null){
							fPax.setLanguageFreeFlow(wsp.getLanguage());
						}
					}

					if (wsp.getFrequentFlyerID() != null) {
						fPax.setFfNumber(wsp.getFrequentFlyerID());
					}

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
						if(ci.getEmails()!=null){
							add.setEmailAddress(ci.getEmails().getEmailArray(0));
						}

						if(ores.getOnHandBag().getBagAddress()!=null){
							if(ores.getOnHandBag().getBagAddress().getAddressLineArray(0)!=null){
								add.setAddress1(ores.getOnHandBag().getBagAddress().getAddressLineArray(0));
							}
							if(ores.getOnHandBag().getBagAddress().getCity()!=null){
								add.setCity(ores.getOnHandBag().getBagAddress().getCity());
							}
							if(ores.getOnHandBag().getBagAddress().getCountry()!=null){
								add.setCountryCode(ores.getOnHandBag().getBagAddress().getCountry().getCode());
							}
							if(ores.getOnHandBag().getBagAddress().getPostalCode()!=null){
								add.setZip(ores.getOnHandBag().getBagAddress().getPostalCode().getStringValue());
							}
						}
						

						if (ci.getPermPhones() != null)
							add.setHomePhone(ci.getPermPhones().getPhoneArray(0));
						if (ci.getCellPhones() != null)
							add.setMobilePhone(ci.getCellPhones().getPhoneArray(0));
						if (ci.getTempPhones() != null)
							add.setAltPhone(ci.getTempPhones().getPhoneArray(0));

					}

				}
			} else {
				StringBuffer errorMsg = new StringBuffer();

				if (wsresponse != null && wsresponse.getWTROnhandBagRecReadRS() != null && wsresponse.getWTROnhandBagRecReadRS().getErrors() != null) {
					ErrorType[] errors = wsresponse.getWTROnhandBagRecReadRS().getErrors().getErrorArray();
					for (ErrorType error : errors) {
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
						if (error.toString().indexOf("RECORD NOT FOUND") > -1){
							logger.error("Record not found");
							throw new WorldTracerRecordNotFoundException();
						}
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			configureClient(stub, getEnvironment(dto));

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
				String bi = RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList.get(0));
				if(ohd.getItem() != null && ohd.getItem().getExternaldesc() != null && ohd.getItem().getExternaldesc().trim().length() > 0){
					bi = bi + "/" + RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(ohd.getItem().getExternaldesc().trim());
				}
				if(bi.length()>50){
					d2.addNewBrandInfo().setStringValue(bi.substring(0,50));
				} else {
					d2.addNewBrandInfo().setStringValue(bi);
				}
			} else if(ohd.getItem() != null && ohd.getItem().getExternaldesc() != null && ohd.getItem().getExternaldesc().trim().length() > 0){
				String bi = "YY/" + RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(ohd.getItem().getExternaldesc().trim());
				if(bi.length()>50){
					d2.addNewBrandInfo().setStringValue(bi.substring(0,50));
				} else {
					d2.addNewBrandInfo().setStringValue(bi);
				}
			}
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.TN);
			if (fieldList != null && fieldList.size() > 0) {
				String airtag = fieldList.get(0);

				try {
					airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
				} catch (BagtagException e) {
					logger.debug("Unable to convert bagtag while creating fwd: " + airtag);
				}
				if (airtag != null) {
					BagTagAmendType t3 = d2.addNewBagTag();
					t3.setAirlineCode(airtag.substring(0, 2));
					t3.setTagSequence(airtag.substring(2));					
				}
			}

			int itinCount = 0;
			aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.Itinerary i1 = d2.addNewItinerary();
			aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.Itinerary.FlightSegments f1 = i1.addNewFlightSegments();
			aero.sita.www.bag.wtr._2009._01.OnHandBagAmendType.Itinerary.Routes rts = i1.addNewRoutes();

			for (Itinerary itin : ohd.getBagItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null || itin.getDepartureCity().trim().length() <= 0
						|| itin.getArrivalCity() == null || itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null || itinCount > 4) {
				} else {

					FlightOptionalDateOrARNKType p3 = f1.addNewFlightSegment();
					FlightOptionalDateType p5 = p3.addNewFlightDate();

					String flightNum = PreProcessor.wtFlightNumber(itin.getFlightNumber());
					String airlineCode = itin.getAirline();
					if (flightNum == null || flightNum.equals("")
							|| flightNum.equals("00")) {
						airlineCode = "YY";
					} else {
						p5.setFlightNumber(flightNum);
					}
					p5.setAirlineCode(airlineCode);
					p5.setDate(itin.getFlightDate());

					if (itinCount == 0) {
						rts.addNewRoute().setStringValue(itin.getDepartureCity().trim());
					}
					rts.addNewRoute().setStringValue(itin.getArrivalCity().trim());
					itinCount++;
				}
			}

			PassengerAmendType p1 = d1.addNewPassengers();

			fieldList = fieldMap
					.get(DefaultWorldTracerService.WorldTracerField.NM);
			 List<String> fieldList2 =
			 fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Names names = null;
			aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Initials initials = p1.addNewInitials();
			if (fieldList != null) {
				for (int j = 0; j < fieldList.size(); j++) {
					if (names == null) {
						names = p1.addNewNames();
					}
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					Name nm = names.addNewName();
					nm.setSeq(j + 1);
						
					String name2="";
					if(name!=null && name.length()>0)
						name2=name.replace(" ", "");
					nm.setStringValue(name2);
					
					if (fieldList2 != null && fieldList2.size() > j) {
						if(initials == null){
							initials = p1.addNewInitials();
						}
						Intial it = initials.addNewIntial();
						it.setStringValue(fieldList2.get(j));
						it.setSeq(j+1);
					}
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
				WTRAddressAmendType ba = d2.addNewBagAddress();
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					StringLength0To58AmendType bad = ba.addNewAddressLine();
					bad.setSeq(i + 1);
					bad.setStringValue(address);
				}
			}

			ContactInfoAmendType ct = p1.addNewContactInfo();

			WorldTracerField field = DefaultWorldTracerService.WorldTracerField.EA;
			fieldList = fieldMap.get(field);

			if (fieldList != null && fieldList.size() > 0) {
				StringLength0To58AmendType em1 = ct.addNewEmails().addNewEmail();
				em1.setSeq(1);
				em1.setStringValue(fieldList.get(0));
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


			ContentsAmendType c2 = null;
			if (ohd.getItem() != null && ohd.getItem().getContent() != null){
				Content[] cList = ohd.getItem().getContent();
				
				Map<String,String> cm = combineContentFields(cList);
				
				for(String key:cm.keySet()){
					if(c2 == null){
						c2 = d2.addNewBagContents();
					}
					ContentType c = c2.addNewContent();
					c.setCategory(key.substring(0, key.length()-1));
//					c.setCategory(key);
					c.setDescription(cm.get(key));
			
				}
//				for (Content content:cList){
//					if(content.getDescription().trim().length() > 0 && content.getCategory().equals("UNKNOWN") == false){
//						if(c2 == null){
//							c2 = d2.addNewBagContents();
//						}
//						String desc = content.getDescription()
//						.trim()
//						.toUpperCase()
//						.replaceAll(Format.CONTENT_FIELD.replaceChars(), " ")
//						.replaceAll("\\s+", " ");
//						ArrayList<String> al = aero.nettracer.serviceprovider.common.utils.StringUtils.splitOnWordBreak(desc, MAX_CONTENT_DESC_LENGTH);
//						for(int i = 0; i < al.size() && i < MAX_CONTENT_SPLIT; i++){
//							if(al.get(i).length() > 0){
//								ContentType c = c2.addNewContent();
//								c.setCategory(content.getCategory());
//								c.setDescription(al.get(i));
//							}
//						}
//					}
//				}
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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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
			configureClient(stub, getEnvironment(dto));

			WTRDelayedBagsRecUpdateRQDocument d = WTRDelayedBagsRecUpdateRQDocument.Factory.newInstance();
			WTRDelayedBagsRecUpdateRQType d1 = d.addNewWTRDelayedBagsRecUpdateRQ();

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
				d2.addNewLossComments().setStringValue(StringUtils.substring(incident.getFaultReasonDescription(), 0, LOSS_COMMENT_MAX));

			} else {
				d2.addNewLossReasonCode().setIntValue(79);
				d2.addNewLossComments().setStringValue("Created in error");
			}
			
			if (incident.getTracingFinalized() != null) {
				TracingFinalized tf = TracingFinalized.Factory.newInstance();
				tf.setDateValue(incident.getTracingFinalized().getTime());
				//tf.setPaperClaim(true);
				d2.setTracingFinalized(tf);
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

			Item[] items = data.getItem();
			
			if (fieldList != null) {
				for (int i = 0; i < fieldList.size() && i < 10; i++) {

					String bagFace = fieldList.get(i);
					String colorType = bagFace.substring(0, 2);
					String bagType = bagFace.substring(2, 4);
					String bagDesc = bagFace.substring(4, 7);

					DelayedBag t3 = t22.addNewDelayedBag();
					t3.setSeq(i + 1);
					ContentsAmendType cx = null;
//					ContentsAmendType cx = t3.addNewBagContents();

					if (tagList != null && i < tagList.size()) {
						BagTagAmendType tag = t3.addNewBagTag();
						String airtag = tagList.get(i);
						tag.setAirlineCode(airtag.substring(0, 2));
						tag.setTagSequence(airtag.substring(2));
					}

//					if (fieldList2 != null && i < fieldList2.size()) {
//						t3.addNewBrandInfo().setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(fieldList2.get(i)));
//					}
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

					if (items.length > i ){
						Item item = items[i];
						
						if (item.getManufacturer() != null && item.getManufacturer().length() > 0) {
							String brandinfo=(RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(item.getManufacturer())+"/"+
									RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(item.getExternaldesc()));
							if(brandinfo.length()>50){
								t3.addNewBrandInfo().setStringValue(brandinfo.substring(0, 50));
							} else {
								t3.addNewBrandInfo().setStringValue(brandinfo);
							}
						} else if(item.getExternaldesc() != null && item.getExternaldesc().trim().length() > 0){
							String brandinfo=("YY/"+	RULES.get(DefaultWorldTracerService.WorldTracerField.BI).formatEntry(item.getExternaldesc()));
							if(brandinfo.length()>50){
								t3.addNewBrandInfo().setStringValue(brandinfo.substring(0, 50));
							} else {
								t3.addNewBrandInfo().setStringValue(brandinfo);
							}
						}
						
						if (item.getContent() != null){
							Content[] cList = item.getContent();
							
							Map<String,String>cm = combineContentFields(cList);
							for(String key:cm.keySet()){
								if(cx == null){
									cx = t3.addNewBagContents();
								}
								ContentType c = cx.addNewContent();
								c.setCategory(key.substring(0, key.length()-1));
//								c.setCategory(key);
								c.setDescription(cm.get(key));
							}
						}
					} else {
						throw new WorldTracerConnectionException("Bag/Item mismatch");
					}
				}
			}

			// Set Passengers & Contact Information
			PassengerItineraryAmendType p1 = d1.addNewPassengers();

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.NM);
		 fieldList2 =
		 fieldMap.get(DefaultWorldTracerService.WorldTracerField.IT);
			if (fieldList != null && fieldList.size() > 0) {
				aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Names p11 = p1.addNewNames();
				aero.sita.www.bag.wtr._2009._01.PassengerAmendType.Initials initials = null;
				for (int j = 0; j < fieldList.size(); j++) {
					String name = RULES.get(DefaultWorldTracerService.WorldTracerField.NM).formatEntry(fieldList.get(j));
					Name nm = p11.addNewName();

					String name2="";
					if(name!=null && name.length()>0)
						name2=name.replace(" ", "");
					nm.setStringValue(name2);
					nm.setSeq(j + 1);
					if (fieldList2 != null && fieldList2.size() > j) {
						if(initials == null){
							initials = p1.addNewInitials();
						}
						Intial it = initials.addNewIntial();
						it.setStringValue(fieldList2.get(j));
						it.setSeq(j+1);
					}
				}
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.FL);
			if (fieldList != null && fieldList.size() > 0) {
				p1.addNewFrequentFlyerID().setStringValue(fieldList.get(0));
			}

			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.PS);

			if (fieldList != null && fieldList.size() > 0) {
				String status = fieldList.get(0);
				if (status.contains("/")) {
					String[] st = null;
					if (status != null) {
						st = status.split("/");
						if (st.length > 0) {
							p1.addNewStatus().setStringValue(st[0]);
						}
						if (st.length > 1) {
							String fareType = st[1].substring(0, 1);
							String fareBasis = null;
							if (fareType.equalsIgnoreCase("B")) {
								fareBasis = "C";
							} else if (fareType.equalsIgnoreCase("F")) {
								fareBasis = "F";
							} else {
								fareBasis = "Y";
							}
							p1.addNewFareBasis().setStringValue(fareBasis);
						}
					}
				} else {
					p1.addNewStatus().setStringValue(status);
					p1.addNewFareBasis().setStringValue(status);
				}

			}
			
			fieldList = fieldMap.get(DefaultWorldTracerService.WorldTracerField.LA);
			if (fieldList != null && fieldList.size() > 0) {
				Language language = Language.Factory.newInstance();
				language.setStringValue(RULES.get(DefaultWorldTracerService.WorldTracerField.LA).formatEntry(fieldList.get(0)));
				p1.setLanguage(language);
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
				WTRAddressAmendType pa = ct.addNewPermanentAddress();
				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					StringLength0To58AmendType pa1 = pa.addNewAddressLine();
					pa1.setSeq(i + 1);
					pa1.setStringValue(address);
				}
			}
			
//			
//			//find first permanent address
//			Address ntpa = PreProcessor.getAhlAddress(data, true);
//
//			if(ntpa != null){
//				WTRAddressAmendType pa = ct.addNewPermanentAddress();
//				if(ntpa.getAddress1()!=null && ntpa.getAddress1().length() > 0){
//					StringLength0To58AmendType pa1 = pa.addNewAddressLine();
//					pa1.setSeq(1);
//					pa1.setStringValue(ntpa.getAddress1());
//				}
//				if(ntpa.getAddress2()!=null && ntpa.getAddress2().length() > 0){
//					StringLength0To58AmendType pa2 = pa.addNewAddressLine();
//					pa2.setSeq(2);
//					pa2.setStringValue(ntpa.getAddress2());
//				}
//				if(ntpa.getCity()!=null && ntpa.getCity().length() > 0){
//					pa.setCity(ntpa.getCity());
//				}
//				if(ntpa.getState() != null && ntpa.getState().length() > 0 
//						&& ntpa.getCountryCode() != null 
//						&& (ntpa.getCountryCode().equalsIgnoreCase("US") || ntpa.getCountryCode().equalsIgnoreCase("United States"))){
//					WTRAddressAmendType.State state = pa.addNewState();
//					state.setStringValue(ntpa.getState());
//					pa.setState(state);
//				} else if(ntpa.getProvince() != null && ntpa.getProvince().length() > 0){
//					//there is no province field for WT
//					WTRAddressAmendType.State state = pa.addNewState();
//					state.setStringValue(ntpa.getProvince());
//					pa.setState(state);
//				}
//				if(ntpa.getZip() != null && ntpa.getZip().length() > 0){
//					PostalCode zip = pa.addNewPostalCode();
//					zip.setStringValue(ntpa.getZip());
//					pa.setPostalCode(zip);
//				}
//				if(ntpa.getCountryCode() != null && ntpa.getCountryCode().length() > 0){
//					Country country = pa.addNewCountry();
//					country.setStringValue(ntpa.getCountryCode());
//					pa.setCountry(country);
//				}
//			}

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
				WTRAddressAmendType ta = ct.addNewTempAddress().addNewAddress();

				for (int i = 0; i < fieldList.size() && i < 2; i++) {
					String address = BASIC_RULE.formatEntry(fieldList.get(i).trim());
					StringLength0To58AmendType ta1 = ta.addNewAddressLine();
					ta1.setSeq(i + 1);
					ta1.setStringValue(address);
				}
			}
			
			//get first temp address
//			Address ntta = PreProcessor.getAhlAddress(data, false);
//			
//			if(ntta != null){
//				TempAddress ta = ct.addNewTempAddress();
//				WTRAddressAmendType t = ta.addNewAddress();
//				if(ntta.getAddress1()!=null && ntta.getAddress1().length() > 0){
//					StringLength0To58AmendType ta1 = t.addNewAddressLine();
//					ta1.setSeq(1);
//					ta1.setStringValue(ntta.getAddress1());
//				}
//				if(ntta.getAddress2()!=null && ntta.getAddress2().length() > 0){
//					StringLength0To58AmendType ta2 = t.addNewAddressLine();
//					ta2.setSeq(2);
//					ta2.setStringValue(ntta.getAddress2());
//				}
//				if(ntta.getCity()!=null && ntta.getCity().length() > 0){
//					t.setCity(ntta.getCity());
//				}
//				if(ntta.getState() != null && ntta.getState().length() > 0 
//						&& ntta.getCountryCode() != null 
//						&& (ntta.getCountryCode().equalsIgnoreCase("US") || ntta.getCountryCode().equalsIgnoreCase("United States"))){
//					WTRAddressAmendType.State state = t.addNewState();
//					state.setStringValue(ntta.getState());
//					t.setState(state);
//				} else if(ntta.getProvince() != null && ntta.getProvince().length() > 0){
//					//there is no province field for WT
//					WTRAddressAmendType.State state = t.addNewState();
//					state.setStringValue(ntta.getProvince());
//					t.setState(state);
//				}
//				if(ntta.getZip() != null && ntta.getZip().length() > 0){
//					PostalCode zip = t.addNewPostalCode();
//					zip.setStringValue(ntta.getZip());
//					t.setPostalCode(zip);
//				}
//				if(ntta.getCountryCode() != null && ntta.getCountryCode().length() > 0){
//					Country country = t.addNewCountry();
//					country.setStringValue(ntta.getCountryCode());
//					t.setCountry(country);
//				}
//			}

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

			aero.sita.www.bag.wtr._2009._01.DelayedBagGroupAmendType.BaggageItinerary iti = t2.addNewBaggageItinerary();

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
				validate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
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

	private String getInboxEndpoint(WorldTracerActionDTO dto) {
		return dto.getUser().getProfile().getParameters().get(
				ParameterType.INBOX_ENDPOINT);
	}

	private String getEnvironment(WorldTracerActionDTO dto) {
		String retVal = dto.getUser().getProfile().getParameters().get(ParameterType.CLIENT_ENVIRONMENT);
		if (retVal == null)
			return "";
		else
			return retVal;
	}

	public void sendQoh(WorldTracerActionDTO dto, Qoh qoh, WorldTracerResponse response) throws WorldTracerException {
		// try {
		// airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
		// } catch (BagtagException e) {
		// logger.debug("Unable to convert bagtag while creating fwd: " +
		// airtag);
		// }
		//
		// BagTagType t3 = d2.addNewBagTag();
		// t3.setAirlineCode(airtag.substring(0, 2));
		// t3.setTagSequence(airtag.substring(2));
		try {

			OnhandBagServiceStub stub = new OnhandBagServiceStub(getOnhandEndpoint(dto));
			configureClient(stub, getEnvironment(dto));

			WTRQuickOnhandBagsCreateRQDocument d = WTRQuickOnhandBagsCreateRQDocument.Factory.newInstance();
			WTRQuickOnhandBagsCreateRQ d1 = d.addNewWTRQuickOnhandBagsCreateRQ();

			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID(dto.getUser().getProfile().getAirline());
			StationAirlineType station = d1.addNewRefStationAirline();
			station.setAirlineCode(dto.getUser().getProfile().getAirline());
			station.setStationCode(qoh.getStationCode());

			d1.setAgentID(PreProcessor.getAgentEntry(null));

			BagTags tags = d1.addNewBagTags();

			for (Tag t : qoh.getTags()) {
				String airtag = null;
				try {
					// I have opted to ignore the airline code for our web
					// services and am including the whole
					// tag in the tag sequence field.
					airtag = t.getTagSequence();
					airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
					if (airtag != null) {
						BagTagType tag = tags.addNewBagTag();
						tag.setAirlineCode(airtag.substring(0, 2));
						tag.setTagSequence(airtag.substring(2));
					}
				} catch (BagtagException e) {
					logger.debug("Unable to convert bagtag while creating fwd: " + airtag);
				}
			}

			WTRStatusRSDocument wsresponse = null;

			try {
				String label = "Send QOH";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				if (FORCE_FAILURE) {
					return;
				}
				writeToLog(label);
				validate(d);
				wsresponse = stub.quickCreate(d);
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
						errorMsg.append(error.getStringValue());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.isEmpty()) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				throw e;

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			throw e;
		}

	}

}
