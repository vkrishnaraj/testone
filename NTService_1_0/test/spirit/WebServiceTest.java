package spirit;

import java.rmi.RemoteException;
import java.util.Date;

import org.apache.axis2.AxisFault;
import org.junit.Test;

import spirit.nettracer.contracts.AddBookingCommentsInput;
import spirit.nettracer.contracts.AddBookingCommentsInputDocument;
import spirit.nettracer.contracts.GetBookingInformationInput;
import spirit.nettracer.contracts.GetBookingInformationInputDocument;
import spirit.nettracer.contracts.GetBookingInformationOutputDocument;
import spirit.nettracer.contracts.INetTracerService_AddBookingComments_ArgumentFaultFault_FaultMessage;
import spirit.nettracer.contracts.INetTracerService_AddBookingComments_NavitaireFaultFault_FaultMessage;
import spirit.nettracer.contracts.INetTracerService_AddBookingComments_ServiceFaultFault_FaultMessage;
import spirit.nettracer.contracts.INetTracerService_GetBookingInformation_ArgumentFaultFault_FaultMessage;
import spirit.nettracer.contracts.INetTracerService_GetBookingInformation_NavitaireFaultFault_FaultMessage;
import spirit.nettracer.contracts.INetTracerService_GetBookingInformation_ServiceFaultFault_FaultMessage;
import spirit.nettracer.contracts.NetTracerImplStub;


public class WebServiceTest {

	@Test
	public void testBooking() {
		try {
			/*
			 	
				<GetBookingInformationOutput xmlns="http://Spirit/NetTracer/Contracts" xmlns:i="http://www.w3.org/2001/XMLSchema-instance" xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
				  <BaggageItinerary xmlns:a="http://Spirit/Common/Contracts"/>
				  <PassengerItinerary xmlns:a="http://Spirit/Common/Contracts"/>
				  <Passengers xmlns:a="http://Spirit/Common/Contracts">
				    <a:Passenger>
				      <a:Addresses/>
				      <a:Baggage>
				        <a:Bag>
				          <a:BagNum>58156761</a:BagNum>
				          <a:BagTagDate>2010-01-14T00:00:00</a:BagTagDate>
				          <a:DescriptionCode>0487796330</a:DescriptionCode> <--- BAG TAG
				          <a:Price>0</a:Price>
				          <a:Source>Unspecified</a:Source>
				        </a:Bag>
				        <a:Bag>
				          <a:BagNum>58156762</a:BagNum>
				          <a:BagTagDate>2010-01-14T00:00:00</a:BagTagDate>
				          <a:DescriptionCode>0487796332</a:DescriptionCode>
				          <a:Price>0</a:Price>
				          <a:Source>Unspecified</a:Source>
				        </a:Bag>
				      </a:Baggage>
				      <a:Email i:nil="true"/>
				      <a:FirstName>Alicia</a:FirstName>
				      <a:FrequentFlyerNumber/>
				      <a:LastName>Maurath</a:LastName>
				      <a:MiddleName/>
				      <a:Suffix/>
				      <a:Title>MRS</a:Title>
				    </a:Passenger>
				  </Passengers>
				  <RecordLocator>EDJHPE</RecordLocator>
				</GetBookingInformationOutput>
			
			 */
			String ENDPOINT = "https://206.57.4.54/NetTracerInternal/NetTracerService.svc";
			String PNR = "N96DQT";
			
			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.out.println(System.getProperty("javax.net.ssl.trustStore"));
			System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

			
			Date date1 = new Date();
			NetTracerImplStub stub = new NetTracerImplStub(ENDPOINT);
			
			GetBookingInformationInputDocument bi = GetBookingInformationInputDocument.Factory.newInstance();
			GetBookingInformationInput bi2 = bi.addNewGetBookingInformationInput();
			bi2.setBagTagNumber("");
			bi2.setRecordLocator(PNR);
			GetBookingInformationOutputDocument response = stub.getBookingInformation(bi);
			Date date2 = new Date();
			
			System.out.println(response);
			System.out.println("Elapsed: " + (date2.getTime() - date1.getTime())/1000);
			
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (INetTracerService_GetBookingInformation_ServiceFaultFault_FaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (INetTracerService_GetBookingInformation_ArgumentFaultFault_FaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (INetTracerService_GetBookingInformation_NavitaireFaultFault_FaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRemarks() {
		try {
			/*
			 	
				<GetBookingInformationOutput xmlns="http://Spirit/NetTracer/Contracts" xmlns:i="http://www.w3.org/2001/XMLSchema-instance" xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
				  <BaggageItinerary xmlns:a="http://Spirit/Common/Contracts"/>
				  <PassengerItinerary xmlns:a="http://Spirit/Common/Contracts"/>
				  <Passengers xmlns:a="http://Spirit/Common/Contracts">
				    <a:Passenger>
				      <a:Addresses/>
				      <a:Baggage>
				        <a:Bag>
				          <a:BagNum>58156761</a:BagNum>
				          <a:BagTagDate>2010-01-14T00:00:00</a:BagTagDate>
				          <a:DescriptionCode>0487796330</a:DescriptionCode> <--- BAG TAG
				          <a:Price>0</a:Price>
				          <a:Source>Unspecified</a:Source>
				        </a:Bag>
				        <a:Bag>
				          <a:BagNum>58156762</a:BagNum>
				          <a:BagTagDate>2010-01-14T00:00:00</a:BagTagDate>
				          <a:DescriptionCode>0487796332</a:DescriptionCode>
				          <a:Price>0</a:Price>
				          <a:Source>Unspecified</a:Source>
				        </a:Bag>
				      </a:Baggage>
				      <a:Email i:nil="true"/>
				      <a:FirstName>Alicia</a:FirstName>
				      <a:FrequentFlyerNumber/>
				      <a:LastName>Maurath</a:LastName>
				      <a:MiddleName/>
				      <a:Suffix/>
				      <a:Title>MRS</a:Title>
				    </a:Passenger>
				  </Passengers>
				  <RecordLocator>EDJHPE</RecordLocator>
				</GetBookingInformationOutput>
			
			 */
			String ENDPOINT = "https://206.57.4.54/NetTracerInternal/NetTracerService.svc";
			String PNR = "N96DQT";
			
			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.out.println(System.getProperty("javax.net.ssl.trustStore"));
			System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

			
			Date date1 = new Date();
			NetTracerImplStub stub = new NetTracerImplStub(ENDPOINT);
			
			AddBookingCommentsInputDocument bi = AddBookingCommentsInputDocument.Factory.newInstance();
			AddBookingCommentsInput bi2 = bi.addNewAddBookingCommentsInput();
			bi2.setComments("123456789A123456789B123456789C123456789D123456789E123456789F123456789G");
			bi2.setRecordLocator(PNR);
			stub.addBookingComments(bi);
			Date date2 = new Date();

			System.out.println("Elapsed: " + (date2.getTime() - date1.getTime())/1000);
			
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (INetTracerService_AddBookingComments_ServiceFaultFault_FaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (INetTracerService_AddBookingComments_ArgumentFaultFault_FaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (INetTracerService_AddBookingComments_NavitaireFaultFault_FaultMessage e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
