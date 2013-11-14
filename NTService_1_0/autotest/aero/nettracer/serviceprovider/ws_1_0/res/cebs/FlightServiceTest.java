package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.swacorp.services.btws.v1.GetFlightListFaultDocument;
import com.swacorp.services.btws.v1.GetFlightListFaultDocument.GetFlightListFault;
import com.swacorp.services.btws.v1.GetFlightListRequestDocument.GetFlightListRequest;
import com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse;
import com.swacorp.services.btws.v1.GetFlightListRequestDocument;
import com.swacorp.services.btws.v1.GetFlightListResponseDocument;
import com.swacorp.services.btws.wsdl.v1.BTWSStub;
import com.swacorp.services.btws.wsdl.v1.GetFlightListError;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.PermissionType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UserNotAuthorizedException;
import aero.nettracer.serviceprovider.common.utils.ServiceUtilities;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.FlightDataResponse;

/**
 * Junit test for Cebs integration of FlightService
 * 
 * @author Loupas
 *
 */
public class FlightServiceTest {
	
	BTWSStub stub;
	
	GetFlightListRequestDocument ai;
	GetFlightListRequestDocument bi;
	GetFlightListRequestDocument ci;
	GetFlightListRequestDocument di;
	GetFlightListRequestDocument ei;
	GetFlightListRequestDocument fi;
	GregorianCalendar now = new GregorianCalendar();
	
	private String username = "southwest_test";
	private String password = "WNpass1!";
	
	/**
	 * Initializes all mock requests/responses
	 */
	public FlightServiceTest() {
		try {
			//Create mock stub
			stub = mock(BTWSStub.class);
			
			//Create first mock response
			ai = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest ai2 = ai.addNewGetFlightListRequest();
			ai2.setDate(new GregorianCalendar());
			ai2.setStation("ATL");
			when(stub.getFlightList(ai)).thenReturn(getResponseOne());
			
			//Create second mock response
			bi = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest bi2 = bi.addNewGetFlightListRequest();
			bi2.setDate(new GregorianCalendar());
			bi2.setStation("FLL");
			when(stub.getFlightList(bi)).thenThrow(getResponseTwo());
			
			//Create third mock response
			ci = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest ci2 = ci.addNewGetFlightListRequest();
			ci2.setDate(new GregorianCalendar());
			ci2.setStation("FLL");
			when(stub.getFlightList(ci)).thenThrow(getResponseThree());
			
			//Create forth mock response
			di = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest di2 = di.addNewGetFlightListRequest();
			di2.setDate(new GregorianCalendar());
			di2.setStation("FLL");
			when(stub.getFlightList(di)).thenReturn(getResponseFour());
			
			//Create fifth mock response
			ei = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest ei2 = ei.addNewGetFlightListRequest();
			ei2.setDate(new GregorianCalendar());
			ei2.setStation("FLL");
			when(stub.getFlightList(ei)).thenReturn(getResponseFive());
			
			//Create sixth mock response
			fi = GetFlightListRequestDocument.Factory.newInstance();
			GetFlightListRequest fi2 = fi.addNewGetFlightListRequest();
			fi2.setDate(new GregorianCalendar());
			fi2.setStation("FLL");
			when(stub.getFlightList(fi)).thenThrow(getResponseSix());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Mock return of single segment
	 * 
	 * @return
	 */
	private GetFlightListResponseDocument getResponseOne() {
		GetFlightListResponseDocument doc = GetFlightListResponseDocument.Factory.newInstance();
		GetFlightListResponse response = doc.addNewGetFlightListResponse();
		com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse.FlightList flightList = response.addNewFlightList();
		com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse.FlightList.Flight flight = flightList.addNewFlight();
		flight.setArrivalCity("ATL");
		flight.setFlight("123");
		flight.setOriginCity("FLL");
		flight.setScheduleArrival(now);
		flight.setAirlineCode("WN");
		return doc;
	}
	
	/**
	 * Mocking error returned from service
	 * 
	 * @return
	 */
	private GetFlightListError getResponseTwo() {
		GetFlightListError ret = new GetFlightListError();
		GetFlightListFaultDocument msg = GetFlightListFaultDocument.Factory.newInstance();
		GetFlightListFault faultList = msg.addNewGetFlightListFault();
		faultList.setCode("error 1");
		ret.setFaultMessage(msg);
		return ret;
	}
	
	/**
	 * Mocking remote error
	 * 
	 * @return
	 */
	private RemoteException getResponseThree() {
		return new RemoteException("Test error");
	}
	
	/**
	 * Mocking empty response
	 * 
	 * @return
	 */
	private GetFlightListResponseDocument getResponseFour() {
		GetFlightListResponseDocument doc = GetFlightListResponseDocument.Factory.newInstance();
		@SuppressWarnings("unused")//response object with missing flight list
		GetFlightListResponse response = doc.addNewGetFlightListResponse();
		return doc;
	}
	
	/**
	 * Mock return of multiple segments
	 * 
	 * @return
	 */
	private GetFlightListResponseDocument getResponseFive() {
		GetFlightListResponseDocument doc = GetFlightListResponseDocument.Factory.newInstance();
		GetFlightListResponse response = doc.addNewGetFlightListResponse();
		com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse.FlightList flightList = response.addNewFlightList();
		for(int i = 0;i<10;i++){
			com.swacorp.services.btws.v1.GetFlightListResponseDocument.GetFlightListResponse.FlightList.Flight flight = flightList.addNewFlight();
			flight.setArrivalCity("ATL");
			flight.setFlight("123" + i);
			flight.setOriginCity("FLL");
			flight.setScheduleArrival(now);
			flight.setAirlineCode("WN");
		}
		return doc;
	}
	
	/**
	 * Mocking ArrayIndexOutOfBoundsException response (to test general Exception handling, Mockito does not allow throwing checked Exceptions)
	 * 
	 * @return
	 */
	private Exception getResponseSix() {
		return new ArrayIndexOutOfBoundsException("Test exception");
	}
	
	/**
	 * Tests happy path single segment return
	 */
	@Test
	public void getFlightInfoSuccessTest(){
		FlightService service = new FlightService();
		service.setBtwsStub(stub);
		service.setRequestDoc(ai);
		FlightDataResponse response = service.getFlightData(null, "ATL", new GregorianCalendar());
		Itinerary data = response.getFlightsArray(0);
		assertTrue(data.getAirline().equals("WN"));
		assertTrue(data.getScharrivetime().getTime().equals(now.getTime()));
		assertTrue(data.getFlightnum().equals("123"));
		assertTrue(data.getDepartureCity().equals("FLL"));
		assertTrue(data.getArrivalCity().equals("ATL"));
	}
	
	/**
	 * Tests error handling for a structured error message returned from the web service
	 */
	@Test
	public void getFlightInfoErrorTest(){
		FlightService service = new FlightService();
		service.setBtwsStub(stub);
		service.setRequestDoc(bi);
		FlightDataResponse response = service.getFlightData(null, "ATL", new GregorianCalendar());
		assertTrue(response.getError().getDescription().equals(ServiceConstants.FLIGHT_DATA_EXCEPTION));
	}	
	
	/**
	 * Tests error handling for remote connection exception
	 */
	@Test
	public void getFlightInfoRemoteErrorTest(){
		FlightService service = new FlightService();
		service.setBtwsStub(stub);
		service.setRequestDoc(ci);
		FlightDataResponse response = service.getFlightData(null, "ATL", new GregorianCalendar());
		assertTrue(response.getError().getDescription().equals(ServiceConstants.REMOTE_EXCEPTION));
	}	
	
	/**
	 * Tests error handling for empty response objects
	 */
	@Test
	public void getFlightInfoEmptyListTest(){
		FlightService service = new FlightService();
		service.setBtwsStub(stub);
		service.setRequestDoc(di);
		FlightDataResponse response = service.getFlightData(null, "ATL", new GregorianCalendar());
		assertTrue(response.getError().getDescription().equals(ServiceConstants.FLIGHT_DATA_EXCEPTION));
	}
	
	/**
	 * Tests happy path multi-segment return
	 */
	@Test
	public void getFlightInfoMultiSegmentTest(){
		FlightService service = new FlightService();
		service.setBtwsStub(stub);
		service.setRequestDoc(ei);
		FlightDataResponse response = service.getFlightData(null, "ATL", new GregorianCalendar());
		int i = 0;
		for(Itinerary data:response.getFlightsArray()){
			assertTrue(data.getAirline().equals("WN"));
			assertTrue(data.getScharrivetime().getTime().equals(now.getTime()));
			assertTrue(data.getFlightnum().equals("123" + i));
			assertTrue(data.getDepartureCity().equals("FLL"));
			assertTrue(data.getArrivalCity().equals("ATL"));
			i++;
		}
	}
	
	/**
	 * Tests error handling for unknown exception
	 */
	@Test
	public void getFlightInfoExceptionTest(){
		FlightService service = new FlightService();
		service.setBtwsStub(stub);
		service.setRequestDoc(fi);
		FlightDataResponse response = service.getFlightData(null, "ATL", new GregorianCalendar());
		assertTrue(response.getError().getDescription().equals(ServiceConstants.UNEXPECTED_EXCEPTION));
	}

	/**
	 * This method can be manually run to verify connection (via console output) to Cebs
	 */
	public void getFlightInfoTest(){
		FlightService service = new FlightService();
		User user = null;
		try {
			user = ServiceUtilities.getAndAuthorizeUser(username,
					password, PermissionType.GET_PREPOP_DATA);
		} catch (UserNotAuthorizedException e) {
			e.printStackTrace();
		}
		GregorianCalendar cal = new GregorianCalendar();
		service.getFlightData(user, "ATL", cal);
	}
	

}
