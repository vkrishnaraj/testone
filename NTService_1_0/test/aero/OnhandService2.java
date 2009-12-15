package aero;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import junit.framework.Assert;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.junit.Test;

import aero.sita.www.bag.wtr._2009._01.BagDescType;
import aero.sita.www.bag.wtr._2009._01.BagElmsType;
import aero.sita.www.bag.wtr._2009._01.BagMatrlType;
import aero.sita.www.bag.wtr._2009._01.BagTagType;
import aero.sita.www.bag.wtr._2009._01.ColorCodeType;
import aero.sita.www.bag.wtr._2009._01.ColorTypeDescType;
import aero.sita.www.bag.wtr._2009._01.FlightOptionalDateType;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType;
import aero.sita.www.bag.wtr._2009._01.RecordIdentifierType;
import aero.sita.www.bag.wtr._2009._01.RecordReferenceType;
import aero.sita.www.bag.wtr._2009._01.RecordType;
import aero.sita.www.bag.wtr._2009._01.StationAirlineType;
import aero.sita.www.bag.wtr._2009._01.WTRBagsCreateRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRStatusRSDocument;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary;
import aero.sita.www.bag.wtr._2009._01.OnHandBagType.Itinerary.Routes;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument.WTRCloseRecordsRQ;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument.WTROnhandBagCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTROnhandBagCreateRQDocument.WTROnhandBagCreateRQ.Passengers;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument.WTRReadRecordRQ;
import aero.sita.www.bag.wtr.onhandbagservice.OnhandBagServiceStub;

public class OnhandService2 {

	private static final int REFERENCE_ID = 10025;
	private static final String DESTINATION_STATION = "ATL";
	private static final String ORIGIN_STATION = "PHX";
	private static final String CREATE_STATION_CODE = "DEN";
	private static final String AIRLINE_CODE = "US";
	private static final String CLAIM_CURRENCY_CODE = "USD";
	private static final BigDecimal CLAIM_AMOUNT = new BigDecimal(0);
	 //String endpoint = "http://chocolate.nettracer.aero:8080";
//	 String endpoint = "https://webservice-qa.worldtracer.aero/DelayedBagService/0.1";
		String onhandEndpoint = "https://webservice-qa.worldtracer.aero/OnhandBagService/0.1";
		
	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(OnhandService2.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}

	private void processResponse2(WTRBagsCreateRSDocument response) {
		System.out.println(response);	
	}
	
	@Test
	public void testRead() throws RemoteException, XMLStreamException {

		OnhandBagServiceStub stub = new OnhandBagServiceStub(onhandEndpoint);
		configureClient(stub);

		WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
		WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

		// Set version & POS
		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		
		RecordIdentifierType t1 = d1.addNewRecordID();
		t1.setRecordType(RecordType.ON_HAND);

		RecordReferenceType t2 = t1.addNewRecordReference();
		t2.setAirlineCode("US");
		t2.setReferenceNumber(10031);
		t2.setStationCode("DEN");
		
		d1.setAgentID("AGENT");
		
		WTROnhandBagRecReadRSDocument response = null;
		try {
			System.out.println(d);
			Thread.sleep(1000*5);
//			System.exit(0);
			response = stub.read(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse(response);
	}
	
private void processResponse(WTROnhandBagRecReadRSDocument response) {
		// TODO Auto-generated method stub
		System.out.println(response);
	}

	@Test
	public void testClose() throws RemoteException, XMLStreamException {

		OnhandBagServiceStub stub = new OnhandBagServiceStub(onhandEndpoint);
		configureClient(stub);

		WTRCloseRecordsRQDocument d = WTRCloseRecordsRQDocument.Factory.newInstance();
		WTRCloseRecordsRQ d1 = d.addNewWTRCloseRecordsRQ();

		// Set version & POS
		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		
		RecordReferenceType t1 = d1.addNewRecords().addNewRecordReference();
		t1.setReferenceNumber(10031);
		t1.setAirlineCode("US");
		t1.setStationCode("DEN");
		d1.setRecordType(RecordType.ON_HAND);
//		d1.set
		d1.setAgentID("AGENT");
		
		WTRStatusRSDocument response = null;
		try {
			System.out.println(d);
			Thread.sleep(1000*5);
//			System.exit(0);
			response = stub.close(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse1(response);
	}
	
private void processResponse1(WTRStatusRSDocument response) {
	System.out.println(response);
}

//	@Test
	public void testCreate() throws RemoteException, XMLStreamException {
		/*
<wtr:WTR_BagsCreateRS Version="0.1" xmlns:wtr="http://www.sita.aero/bag/wtr/2009/01" xmlns:iata="http://www.iata.org/IATA/2007/00" xmlns:xsd="http://www.w3.org/1999/XMLSchema" xmlns:SOAP-EN="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance">
  <wtr:Success/>
  <wtr:RecordID>
    <wtr:RecordType>ON-HAND</wtr:RecordType>
    <wtr:RecordReference StationCode="DEN" AirlineCode="US" ReferenceNumber="10031"/>
  </wtr:RecordID>
</wtr:WTR_BagsCreateRS>
		 */
		OnhandBagServiceStub stub = new OnhandBagServiceStub(onhandEndpoint);
		configureClient(stub);

		WTROnhandBagCreateRQDocument d = WTROnhandBagCreateRQDocument.Factory.newInstance();
		WTROnhandBagCreateRQ d1 = d.addNewWTROnhandBagCreateRQ();

		// Set version & POS
		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		
		OnHandBagType d2 = d1.addNewOnHandBag();
		
		ColorTypeDescType t1 = d2.addNewColorTypeDesc();
		t1.setColorCode(ColorCodeType.BU);
		t1.setTypeCode(22);
		BagDescType t2 = t1.addNewDescriptor();
		t2.setMtrlElement(BagMatrlType.M);
		String letter = "S";
		t2.addNewOtherElement().set(BagElmsType.Enum.forString(letter));
		letter = "X";
		t2.addNewOtherElement().set(BagElmsType.Enum.forString(letter));
				
		BagTagType t3 = d2.addNewBagTag();
		t3.setAirlineCode("US");
		t3.setTagSequence("123456");
				
		d2.addNewBrandInfo().setStringValue("AMERIBAG");
		
		// ITIN REQUIRED
		Itinerary d3 = d2.addNewItinerary();
		Routes rts = d3.addNewRoutes();
		rts.addNewRoute().setStringValue("DEN");
		rts.addNewRoute().setStringValue("PHX");
		
		FlightOptionalDateType d4 = d3.addNewFlightSegments().addNewFlightSegment().addNewFlightDate();
		
		d4.setAirlineCode("US");
		d4.setDate(new GregorianCalendar());
		d4.setFlightNumber("9999");
		
		
//		d2.addNewBagContents();
//		d2.addNewBagAddress();
		Passengers p1 = d1.addNewPassengers();
		
		StationAirlineType s1 = d1.addNewRefStationAirline();
		s1.setAirlineCode("US");
		s1.setStationCode(CREATE_STATION_CODE);
		d1.setAgentID("AGENT");
		
		WTRBagsCreateRSDocument response = null;
		try {
			System.out.println(d);
			Thread.sleep(1000*5);
//			System.exit(0);
			response = stub.create(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse2(response);
	}


	private void configureClient(org.apache.axis2.client.Stub stub) throws AxisFault {


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
			System.exit(0);
		}
		
		List<Phase> phases = client.getAxisConfiguration().getInFlowPhases();
		AxisConfiguration config = client.getAxisConfiguration();
		

		
		for (Phase p: phases) {
			HandlerDescription removeThis = null;
			System.out.println("Phase: " + p.getPhaseName());
			if (p.getPhaseName().equals("Security") || p.getPhaseName().equals("Dispatch")) {
				List<Handler> l = p.getHandlers();
				for (Handler h: l) {
					System.out.println("  Handler: " + h.getName());
					if(h.getName().equals("Apache Rampart inflow handler") || h.getName().equals("Post dispatch security verification handler")) {
						removeThis = h.getHandlerDesc();
					}
				}
			}
			
			if (removeThis != null) {
				p.removeHandler(removeThis);
			}
		}
	}
//
//	@Test
//	public void testRead() throws RemoteException, XMLStreamException {
//
//		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
//		configureClient(stub);
//		
//		WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
//		WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();
//
//		BigDecimal a = new BigDecimal(0.1);
//		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
//		d1.setVersion(b);
//		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
//		RecordIdentifierType d2 = d1.addNewRecordID();
//		RecordReferenceType d3 = d2.addNewRecordReference();
//		d3.setReferenceNumber(REFERENCE_ID);
//		d3.setAirlineCode(AIRLINE_CODE);
//		d3.setStationCode(CREATE_STATION_CODE);
//		d2.setRecordType(RecordType.DELAYED);
//		d1.setAgentID("AGENT");
//
//		System.setProperty("javax.net.ssl.trustStore", "c:\\jdk\\jre\\lib\\security\\cacerts");
//		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//
//		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
//		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));
//
//		WTRDelayedBagRecReadRSDocument response = null;
//		try {
//			System.out.println(d);
//			response = stub.read(d);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Assert.assertNotNull(response);
//		processResponse(response);
//	}
//
//	private void processResponse(WTRDelayedBagRecReadRSDocument response) {
//		System.out.println(response);	
//	}
//	

//	
//	private void processResponse3(WTRStatusRSDocument response) {
//		System.out.println(response);	
//	}
//	
//	private void processResponse4(WTRStatusRSDocument response) {
//		System.out.println(response);	
//	}
//	
//	
////	@Test
//	public void testClose() throws RemoteException, XMLStreamException {
//		/*
//		
//		Sample Response 1: 
//		
//		<wtr:WTR_StatusRS Version="0.1" xmlns:wtr="http://www.sita.aero/bag/wtr/2009/01" xmlns:iata="http://www.iata.org/IATA/2007/00" xmlns:xsd="http://www.w3.org/1999/XMLSchema" xmlns:SOAP-EN="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance">
//		  <wtr:Success/>
//		  <wtr:Warnings>
//		    <iata:Warning RecordID="DENUS10025" Code="4065">DOES NOT CONTAIN REQUIRED MANDATORY ELEMENTS TO CLOSE THE RECORD</iata:Warning>
//		  </wtr:Warnings>
//		</wtr:WTR_StatusRS>
//		*/
//
//		
//		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
//		configureClient(stub);
//		
//		WTRCloseRecordsRQDocument d = WTRCloseRecordsRQDocument.Factory.newInstance();
//		WTRCloseRecordsRQ d1 = d.addNewWTRCloseRecordsRQ();
//
//		BigDecimal a = new BigDecimal(0.1);
//		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
//		d1.setVersion(b);
//		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
//		
//		RecordReferenceType d2 = d1.addNewRecords().addNewRecordReference();
//		d2.setAirlineCode(AIRLINE_CODE);
//		d2.setStationCode(CREATE_STATION_CODE);
//		d2.setReferenceNumber(10025);
//		d1.setRecordType(RecordType.DELAYED);
//		d1.setAgentID("AGENT");
//
//		System.setProperty("javax.net.ssl.trustStore", "c:\\jdk\\jre\\lib\\security\\cacerts");
//		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//
//		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
//		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));
//
//		WTRStatusRSDocument response = null;
//		try {
//			System.out.println(d);
//			response = stub.close(d);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Assert.assertNotNull(response);
//		processResponse3(response);
//	}
//	
////	@Test
//	public void testAmend() throws RemoteException, XMLStreamException {
//
//		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
//		configureClient(stub);
//		
//		WTRDelayedBagsRecUpdateRQDocument d = WTRDelayedBagsRecUpdateRQDocument.Factory.newInstance();
//		WTRDelayedBagsRecUpdateRQ d1 = d.addNewWTRDelayedBagsRecUpdateRQ();
//
//		RecordReferenceType t1 = d1.addNewRecordReference();
//		t1.setAirlineCode(AIRLINE_CODE);
//		t1.setStationCode(CREATE_STATION_CODE);
//		t1.setReferenceNumber(REFERENCE_ID);
//		
//		BigDecimal a = new BigDecimal(0.1);
//		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
//		d1.setVersion(b);
//		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
//		
//		
//		
//		DelayedClaimAmendType d2 = d1.addNewClaim();
//		
////		Amount d3 = d2.addNewClaimAmout().addNewAmount();
////		d3.setAmount(CLAIM_AMOUNT);
////		d3.setCurrencyCode(CLAIM_CURRENCY_CODE);
//		
//		PassengerPayment d3 = d2.addNewPassengerPayments().addNewPassengerPayment();
//		d3.setType(CostType.X);
//		Amount amt = d3.addNewAmount();
//		amt.setCurrencyCode(CLAIM_CURRENCY_CODE);
//		amt.setAmount(CLAIM_AMOUNT);
//		
//		
//		d2.addNewFaultStationCode().setStringValue(CREATE_STATION_CODE);
//		d2.addNewLossReasonCode().setIntValue(79);
//		d2.addNewLossComments().setStringValue("79 - Unknown");
//		
//		
//		
//
//		d1.setAgentID("AGENT");
//
//		System.setProperty("javax.net.ssl.trustStore", "c:\\jdk\\jre\\lib\\security\\cacerts");
//		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
//
//		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
//		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));
//
//		WTRStatusRSDocument response = null;
//		try {
//			System.out.println(d);
//			response = stub.update(d);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		Assert.assertNotNull(response);
//		processResponse4(response);
//	}

}
