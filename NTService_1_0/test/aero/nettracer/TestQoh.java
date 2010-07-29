package aero.nettracer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.iata.www.iata._2007._00.ErrorType;
import org.junit.Test;

import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.wt_1_0.common.Qoh;
import aero.nettracer.serviceprovider.wt_1_0.common.Tag;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.webservices.WorldTracerServiceImpl;
import aero.sita.www.bag.wtr._2009._01.BagTagType;
import aero.sita.www.bag.wtr._2009._01.StationAirlineType;
import aero.sita.www.bag.wtr._2009._01.WTRQuickOnhandBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRStatusRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRQuickOnhandBagsCreateRQDocument.WTRQuickOnhandBagsCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRQuickOnhandBagsCreateRQDocument.WTRQuickOnhandBagsCreateRQ.BagTags;
import aero.sita.www.bag.wtr.onhandbagservice.OnhandBagServiceStub;

public class TestQoh {
	

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
	private static final String WEB_SERVICE_ERROR_MESSAGE = "WS Error: ";
	private static final String EXCEPTION_FOUND_RESPONSE = "Exception found... Response: ";
	private static final String ACTION_BEING_PERFORMED = "Action Being Performed: ";
	private static final String JAVAX_NET_SSL_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
	private static final String JAVAX_NET_SSL_TRUST_STORE = "javax.net.ssl.trustStore";
	private static final String RAMPART = "rampart";
	private static final String POLICY_XML = "/policy.xml";
	private static final String INSERT_INTO_WT_WS_TRANS_LOG_GMTTIME_DESCRIPTION_VALUES = "INSERT INTO WT_WS_TRANS_LOG (gmttime, description) VALUES (?, ?)";
	private static final String METHOD_NOT_AVAILABLE_VIA_WEB_SERVICES = "Method not available via web services.";
	
	private static BigDecimal VERSION_0_PT_1 = new BigDecimal(0.1).setScale(1, RoundingMode.HALF_UP);
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);



	private static final Logger logger = Logger.getLogger(TestQoh.class);
	

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
	
	@Test
	public void testQoh() {
		WorldTracerResponse response = new WorldTracerResponse();
		Qoh qoh = new Qoh();
		Tag t1 = new Tag();
		t1.setAirlineCode("US");
		t1.setTagSequence("US123123");
		Tag t2 = new Tag();
		t2.setAirlineCode("US");
		t2.setTagSequence("US123124");
		
		Tag[] tagsx = new Tag[2];
		tagsx[0] = t1;
		tagsx[1] = t2;
		
		qoh.setTags(tagsx);
		
		try {
			
			OnhandBagServiceStub stub = new OnhandBagServiceStub("https://webservice.worldtracer.aero/OnhandBagService/0.1");
			configureClient(stub);

			WTRQuickOnhandBagsCreateRQDocument d = WTRQuickOnhandBagsCreateRQDocument.Factory.newInstance();
			WTRQuickOnhandBagsCreateRQ d1 = d.addNewWTRQuickOnhandBagsCreateRQ();
			
			d1.setVersion(VERSION_0_PT_1);
			d1.addNewPOS().addNewSource().setAirlineVendorID("US");
			StationAirlineType station = d1.addNewRefStationAirline();
			station.setAirlineCode("US");
			station.setStationCode("XAX");
			
			BagTags tags = d1.addNewBagTags();
			
			for (Tag t: qoh.getTags()) {
				String airtag = null;
				try {
					// I have opted to ignore the airline code for our web services and am including the whole
					// tag in the tag sequence field.
					airtag = t.getTagSequence();
					airtag = BagTagConversion.getTwoCharacterBagTag(airtag);
					
					BagTagType tag = tags.addNewBagTag();
					tag.setAirlineCode(airtag.substring(0, 2));
					tag.setTagSequence(airtag.substring(2));
					
				} catch (BagtagException e) {
					logger.debug("Unable to convert bagtag while creating fwd: " + airtag);
				}
			}
			
			WTRStatusRSDocument wsresponse = null;

			try {
				String label = "Send QOH";
				logger.info(ACTION_BEING_PERFORMED + label + NEWLINE + d);
				logger.info(d);
				Thread.sleep (10000);
//				writeToLog(label);
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
						errorMsg.append(error.getShortText());
						logger.error(WEB_SERVICE_ERROR_MESSAGE + error.toString());
					}
				}

				String returnError = errorMsg.toString();
				if (returnError.length() > 0) {
					returnError = UNKNOWN_FAILURE;
				}

				WorldTracerException e = new WorldTracerException(returnError);
				e.printStackTrace();
				logger.error(e);

			}
		} catch (AxisFault axisFault) {
			logger.error("Connection Issue: ", axisFault);
			WorldTracerException e = new WorldTracerException(WEB_SERVICE_CONNECTION_ISSUE, axisFault);
			e.printStackTrace();
			logger.error(e);
		}
		
		
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
	

	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(WorldTracerServiceImpl.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}
}
