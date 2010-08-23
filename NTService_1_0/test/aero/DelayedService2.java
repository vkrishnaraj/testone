package aero;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

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

import aero.sita.www.bag.wtr._2009._01.AmountType;
import aero.sita.www.bag.wtr._2009._01.BagDescType;
import aero.sita.www.bag.wtr._2009._01.BagElmsType;
import aero.sita.www.bag.wtr._2009._01.BagMatrlType;
import aero.sita.www.bag.wtr._2009._01.ColorCodeType;
import aero.sita.www.bag.wtr._2009._01.ColorTypeDescType;
import aero.sita.www.bag.wtr._2009._01.CostType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType;
import aero.sita.www.bag.wtr._2009._01.DelayedBagType;
import aero.sita.www.bag.wtr._2009._01.DelayedClaimAmendType;
import aero.sita.www.bag.wtr._2009._01.FlightDateType;
import aero.sita.www.bag.wtr._2009._01.FlightSegmentType;
import aero.sita.www.bag.wtr._2009._01.OriginDestinationType;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryAmendType;
import aero.sita.www.bag.wtr._2009._01.PassengerItineraryType;
import aero.sita.www.bag.wtr._2009._01.PassengerPaymentAmendType;
import aero.sita.www.bag.wtr._2009._01.RecordIdentifierType;
import aero.sita.www.bag.wtr._2009._01.RecordReferenceType;
import aero.sita.www.bag.wtr._2009._01.RecordType;
import aero.sita.www.bag.wtr._2009._01.StationAirlineType;
import aero.sita.www.bag.wtr._2009._01.WTRBagsCreateRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagRecReadRSDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument;
import aero.sita.www.bag.wtr._2009._01.WTRStatusRSDocument;
import aero.sita.www.bag.wtr._2009._01.DelayedBagGroupType.BaggageItinerary;
import aero.sita.www.bag.wtr._2009._01.WTRCloseRecordsRQDocument.WTRCloseRecordsRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsCreateRQDocument.WTRDelayedBagsCreateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRDelayedBagsRecUpdateRQDocument.WTRDelayedBagsRecUpdateRQ;
import aero.sita.www.bag.wtr._2009._01.WTRReadRecordRQDocument.WTRReadRecordRQ;
import aero.sita.www.bag.wtr.delayedbagservice.DelayedBagServiceStub;

public class DelayedService2 {

	private static final String CREATE_STATION_CODE = "XCI";
	private static final int REFERENCE_ID = 10061;
	
	private static final String DESTINATION_STATION = "XCI";
	private static final String ORIGIN_STATION = "XLF";

	private static final String AIRLINE_CODE = "US";
	private static final String CLAIM_CURRENCY_CODE = "USD";
	private static final BigDecimal CLAIM_AMOUNT = new BigDecimal(0);

	 String endpoint = "https://webservice-qa.worldtracer.aero/DelayedBagService/0.1";

	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(DelayedService2.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}

//	@Test
	public void testDate() {
		
		FlightDateType p3 = FlightDateType.Factory.newInstance();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		
		
		for (String a: TimeZone.getAvailableIDs()) {
			cal.setTimeZone(TimeZone.getTimeZone(a));
			p3.setDate(cal);
			System.out.println(a);
			System.out.println(p3);
			System.out.println("--------------");
		}
	}
	
	@Test
	public void testCreate() throws RemoteException, XMLStreamException {

		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
		configureClient(stub);

		WTRDelayedBagsCreateRQDocument d = WTRDelayedBagsCreateRQDocument.Factory.newInstance();
		WTRDelayedBagsCreateRQ d1 = d.addNewWTRDelayedBagsCreateRQ();

		// Set version & POS
		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		
//		DelayedClaimType d2 = d1.addNewClaim();
//		d2.setFaultStationCode(CREATE_STATION_CODE);
//		d2.setLossReasonCode(79);
//		d2.setLossComments("79 - Unknown");
//		
//		PassengerPaymentType d3 = d2.addNewPassengerPayments().addNewPassengerPayment();
//		d3.setType(CostType.X);
//		AmountType amt = d3.addNewAmount();
//		amt.setCurrencyCode(CLAIM_CURRENCY_CODE);
//		amt.setAmount(CLAIM_AMOUNT);
		
		StationAirlineType sat = d1.addNewRefStationAirline();
		sat.setAirlineCode(AIRLINE_CODE);
		sat.setStationCode(CREATE_STATION_CODE);

		DelayedBagGroupType t2 = d1.addNewDelayedBagGroup();
		BaggageItinerary iti = t2.addNewBaggageItinerary();
		FlightDateType iti1 = iti.addNewFlightDateOrARNK().addNewFlightDate();
		iti.addNewFlightDateOrARNK().addNewFlightDate();
		
		iti1.setAirlineCode("US");
		iti1.setDate(new GregorianCalendar());
		iti1.setFlightNumber("123");
		
		
		DelayedBagType t3 = t2.addNewDelayedBags().addNewDelayedBag();
		t3.addNewBrandInfo().setStringValue("AMERIBAG");
		ColorTypeDescType t4 = t3.addNewColorTypeDesc();
		t4.setColorCode(ColorCodeType.BK);
		t4.setTypeCode(22);
		BagDescType t5 = t4.addNewDescriptor();
		t5.setMtrlElement(BagMatrlType.M);
		t5.addNewOtherElement().set(BagElmsType.B);
		t5.addNewOtherElement().set(BagElmsType.C);
		
		
		// TODO: TYPE
		PassengerItineraryType p1 = d1.addNewPassengers();
		p1.setPooledTktNumber("0000");
//		p1.setPNR("ABCDEF");
		p1.addNewNames().addName("SMITH");
		p1.setTitle("MR");
		p1.addNewInitials().addNewIntial().setStringValue("S");
		
		FlightSegmentType p2 = p1.addNewItinerary().addNewFlightSegments().addNewFlightSegmentOrARNK().addNewFlightSegment();
		FlightDateType p3 = p2.addNewFlightAndDate();
		p3.setAirlineCode("US");
		GregorianCalendar cal = new GregorianCalendar();
		// HERE
//		cal.setTimeZone();
		p3.setDate(cal);
		p3.setFlightNumber("0123");
		
		OriginDestinationType p4 = p2.addNewOriginDestination();
		p4.setOrigin(ORIGIN_STATION);
		p4.setDestination(DESTINATION_STATION);
		
		d1.setAgentID("AGENT");

		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

		WTRBagsCreateRSDocument response = null;
		try {
			System.out.println(d);
			
			Thread.sleep(1000*5);
			response = stub.create(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse2(response);
	}


	private void configureClient(org.apache.axis2.client.Stub stub) throws AxisFault {
		ServiceClient client = stub._getServiceClient();
		
		client.engageModule("rampart");
		try {
			client.getAxisService().getPolicySubject().attachPolicy(loadPolicy("/policy.xml"));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}
		
		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));


		removeFaultPhases(client);
		removePhases(client);
	}


	private void removePhases(ServiceClient client) {
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
	

	private void removeFaultPhases(ServiceClient client) {
		List<Phase> phases = client.getAxisConfiguration().getInFaultFlowPhases();
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

	@Test
	public void testRead() throws RemoteException, XMLStreamException {

		/*
		
		 
		 <wtr:WTR_DelayedBagRecReadRS Version="0.1" xmlns:wtr="http://www.sita.aero/bag/wtr/2009/01" xmlns:iata="http://www.iata.org/IATA/2007/00" xmlns:xsd="http://www.w3.org/1999/XMLSchema" xmlns:SOAP-EN="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance">
  <wtr:DiaryInfo>
    <wtr:CreateDate>2009-12-07T15:34:00-05:00</wtr:CreateDate>
    <wtr:TracingDate>2009-12-07T00:00:00-05:00</wtr:TracingDate>
    <wtr:CloseDate>2009-12-07T16:48:00-05:00</wtr:CloseDate>
  </wtr:DiaryInfo>
  <wtr:DelayedBagGroup>
    <wtr:DelayedBags>
      <wtr:DelayedBag>
        <wtr:ColorTypeDesc Suspended="false">
          <wtr:ColorCode>BU</wtr:ColorCode>
          <wtr:TypeCode>22</wtr:TypeCode>
          <wtr:Descriptor>
            <wtr:MtrlElement>M</wtr:MtrlElement>
            <wtr:OtherElement>B</wtr:OtherElement>
            <wtr:OtherElement>C</wtr:OtherElement>
          </wtr:Descriptor>
        </wtr:ColorTypeDesc>
        <wtr:BrandInfo Suspended="false">AMERIBAG</wtr:BrandInfo>
      </wtr:DelayedBag>
    </wtr:DelayedBags>
  </wtr:DelayedBagGroup>
  <wtr:Passengers>
    <wtr:Names>
      <wtr:Name>SMITH</wtr:Name>
    </wtr:Names>
    <wtr:Initials>
      <wtr:Intial>S</wtr:Intial>
    </wtr:Initials>
    <wtr:Itinerary>
      <wtr:FlightSegments>
        <wtr:FlightSegmentOrARNK>
          <wtr:FlightSegment>
            <wtr:FlightAndDate>
              <wtr:AirlineCode>US</wtr:AirlineCode>
              <wtr:FlightNumber>9999</wtr:FlightNumber>
              <wtr:Date>2009-12-07</wtr:Date>
            </wtr:FlightAndDate>
            <wtr:OriginDestination>
              <wtr:Origin>PHX</wtr:Origin>
              <wtr:Destination>ATL</wtr:Destination>
            </wtr:OriginDestination>
          </wtr:FlightSegment>
        </wtr:FlightSegmentOrARNK>
      </wtr:FlightSegments>
      <wtr:AdditionalRoutes/>
    </wtr:Itinerary>
  </wtr:Passengers>
  <wtr:Claim>
    <wtr:ClaimAmout>
      <wtr:Amount CurrencyCode="USD" Amount="0"/>
    </wtr:ClaimAmout>
    <wtr:PassengerPayments>
      <wtr:PassengerPayment>
        <wtr:Type>X</wtr:Type>
        <wtr:Amount CurrencyCode="USD" Amount="0"/>
      </wtr:PassengerPayment>
    </wtr:PassengerPayments>
    <wtr:FaultStationCode>DEN</wtr:FaultStationCode>
    <wtr:LossComments>79 - UNKNOWN</wtr:LossComments>
    <wtr:LossReasonCode>79</wtr:LossReasonCode>
  </wtr:Claim>
  <wtr:RecordHistory>
    <wtr:History>AHL  07DEC/2034GMT AG AGENT FROM WTW WM BY US
AAH  07DEC/2134GMT /US AG AGENT WTW ELEMENTS
     RL01/FS01/CA01/
AAH  07DEC/2144GMT /US AG AGENT WTW ELEMENTS RC01/
AAH  07DEC/2148GMT /US AG AGENT WTW ELEMENTS CS01/
CFI  07DEC/2148GMT /US AG AGENT WTW
-   MCH  07DEC/2036 NO MATCH</wtr:History>
  </wtr:RecordHistory>
</wtr:WTR_DelayedBagRecReadRS>
	
		 */
		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
		configureClient(stub);
		
		WTRReadRecordRQDocument d = WTRReadRecordRQDocument.Factory.newInstance();
		WTRReadRecordRQ d1 = d.addNewWTRReadRecordRQ();

		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		RecordIdentifierType d2 = d1.addNewRecordID();
		RecordReferenceType d3 = d2.addNewRecordReference();
		d3.setReferenceNumber(REFERENCE_ID);
		d3.setAirlineCode(AIRLINE_CODE);
		d3.setStationCode(CREATE_STATION_CODE);
		d2.setRecordType(RecordType.DELAYED);
		d1.setAgentID("AGENT");
		
		
//		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
//		RecordIdentifierType d2 = d1.addNewRecordID();
//		RecordReferenceType d3 = d2.addNewRecordReference();
//		d3.setReferenceNumber(14239);
//		d3.setAirlineCode("US");
//		d3.setStationCode("ABE");
//		d2.setRecordType(RecordType.DELAYED);
//		d1.setAgentID("AGENT");



		WTRDelayedBagRecReadRSDocument response = null;
		try {
			System.out.println(d);
			Thread.sleep(5*1000);
			response = stub.read(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse(response);
		int i = 0;
	}

	private void processResponse(WTRDelayedBagRecReadRSDocument response) {
		System.out.println(response);	
	}
	
	private void processResponse2(WTRBagsCreateRSDocument response) {
		System.out.println(response);	
	}
	
	private void processResponse3(WTRStatusRSDocument response) {
		System.out.println(response);	
	}
	
	private void processResponse4(WTRStatusRSDocument response) {
		System.out.println(response);	
	}
	
	
//	@Test
	public void testClose() throws RemoteException, XMLStreamException {
		/*
		
		Sample Response 1: 
		
		<wtr:WTR_StatusRS Version="0.1" xmlns:wtr="http://www.sita.aero/bag/wtr/2009/01" xmlns:iata="http://www.iata.org/IATA/2007/00" xmlns:xsd="http://www.w3.org/1999/XMLSchema" xmlns:SOAP-EN="http://schemas.xmlsoap.org/soap/envelope/" xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/" xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance">
		  <wtr:Success/>
		  <wtr:Warnings>
		    <iata:Warning RecordID="DENUS10025" Code="4065">DOES NOT CONTAIN REQUIRED MANDATORY ELEMENTS TO CLOSE THE RECORD</iata:Warning>
		  </wtr:Warnings>
		</wtr:WTR_StatusRS>
		*/

		
		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
		configureClient(stub);
		
		WTRCloseRecordsRQDocument d = WTRCloseRecordsRQDocument.Factory.newInstance();
		WTRCloseRecordsRQ d1 = d.addNewWTRCloseRecordsRQ();

		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		
		RecordReferenceType d2 = d1.addNewRecords().addNewRecordReference();
		d2.setAirlineCode(AIRLINE_CODE);
		d2.setStationCode(CREATE_STATION_CODE);
		d2.setReferenceNumber(10025);
		d1.setRecordType(RecordType.DELAYED);
		d1.setAgentID("AGENT");

		System.setProperty("javax.net.ssl.trustStore", "c:\\jdk\\jre\\lib\\security\\cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

		System.out.println(System.getProperty("javax.net.ssl.trustStore"));
		System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

		WTRStatusRSDocument response = null;
		try {
			System.out.println(d);
			response = stub.close(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse3(response);
	}
	
	@Test
	public void testAmend() throws RemoteException, XMLStreamException {

		DelayedBagServiceStub stub = new DelayedBagServiceStub(endpoint);
		configureClient(stub);
		
		WTRDelayedBagsRecUpdateRQDocument d = WTRDelayedBagsRecUpdateRQDocument.Factory.newInstance();
		WTRDelayedBagsRecUpdateRQ d1 = d.addNewWTRDelayedBagsRecUpdateRQ();

		RecordReferenceType t1 = d1.addNewRecordReference();
		t1.setAirlineCode(AIRLINE_CODE);
		t1.setStationCode(CREATE_STATION_CODE);
		t1.setReferenceNumber(REFERENCE_ID);
		
		BigDecimal a = new BigDecimal(0.1);
		BigDecimal b = a.setScale(1, RoundingMode.HALF_UP);
		d1.setVersion(b);
		d1.addNewPOS().addNewSource().setAirlineVendorID(AIRLINE_CODE);
		
		PassengerItineraryAmendType c = d1.addNewPassengers();
//		Initials cc = c.addNewInitials();
//		Intial ccc = cc.addNewIntial();
		
		
		DelayedClaimAmendType d2 = d1.addNewClaim();
		
//		Amount d3 = d2.addNewClaimAmout().addNewAmount();
//		d3.setAmount(CLAIM_AMOUNT);
//		d3.setCurrencyCode(CLAIM_CURRENCY_CODE);
		
		PassengerPaymentAmendType d3 = d2.addNewPassengerPayments().addNewPassengerPayment();
		d3.setType(CostType.X);
		AmountType amt = d3.addNewAmount();
		amt.setCurrencyCode(CLAIM_CURRENCY_CODE);
		amt.setAmount(CLAIM_AMOUNT);
		
		
		d2.addNewFaultStationCode().setStringValue(CREATE_STATION_CODE);
		d2.addNewLossReasonCode().setIntValue(79);
		d2.addNewLossComments().setStringValue("79 - Unknown");
		
		
		

		d1.setAgentID("AGENT");


		WTRStatusRSDocument response = null;
		try {
			System.out.println(d);
//			System.exit(0);
			response = stub.update(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertNotNull(response);
		processResponse4(response);
	}

}
