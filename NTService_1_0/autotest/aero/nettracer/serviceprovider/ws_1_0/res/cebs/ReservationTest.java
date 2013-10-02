package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.Test;

import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.swacorp.services.btws.v1.GetPNRRequestDocument;
import com.swacorp.services.btws.v1.GetPNRRequestDocument.GetPNRRequest;
import com.swacorp.services.btws.v1.GetPNRResponseDocument;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.AddressList;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.AddressList.Address;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.FlightList;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.FlightList.Flight;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.NameList;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.NameList.Name;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.PhoneList;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.PhoneList.Phone;
import com.swacorp.services.btws.wsdl.v1.BTWSStub;

public class ReservationTest {
	
	BTWSStub stub;
	GetPNRRequestDocument ai;
	GetPNRRequestDocument bi;
	GetPNRRequestDocument ci;
	
	public ReservationTest() {
		try {
			//Create mock stub
			stub = mock(BTWSStub.class);
			
			//Create first mock response
			ai = GetPNRRequestDocument.Factory.newInstance();
			GetPNRRequest ai2 = ai.addNewGetPNRRequest();
			ai2.setPNR("AAAAAA");
			when(stub.getPNR(ai)).thenReturn(getResponseOne());
			
			//Create second mock response
			bi = GetPNRRequestDocument.Factory.newInstance();
			GetPNRRequest bi2 = bi.addNewGetPNRRequest();
			bi2.setPNR("BBBBBB");
			when(stub.getPNR(bi)).thenReturn(getResponseTwo());
			
			//Create third mock response
			ci = GetPNRRequestDocument.Factory.newInstance();
			GetPNRRequest ci2 = ci.addNewGetPNRRequest();
			ci2.setPNR("CCCCCC");
			when(stub.getPNR(ci)).thenReturn(getResponseThree());
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testReservationOne() {
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation ntRes = getNtReservation(ai, "AAAAAA");
		
		// Check General
		assertEquals("AAAAAA", ntRes.getPnr());
		assertEquals(1, ntRes.getPaxAffected());
		
		// Check Passengers
		assertNotNull(ntRes.getPassengersArray());
		assertEquals(1, ntRes.getPassengersArray().length);
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger pass = ntRes.getPassengersArray(0);
		assertEquals("Albert", pass.getFirstname());
		assertEquals("Wesker", pass.getLastname());
		assertNotNull(pass.getAddresses());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address addr = pass.getAddresses();
		assertEquals("111 A", addr.getAddress1());
		assertEquals("Atown", addr.getCity());
		assertEquals("GA", addr.getState());
		assertEquals("30339", addr.getZip());
		assertEquals("a@a.com", addr.getEmailAddress());
		assertEquals("1 (111) 111-1111 ext:123", addr.getHomePhone());
		
		// Check Itineraries
		assertNotNull(ntRes.getBagItineraryArray());
		assertEquals(1, ntRes.getBagItineraryArray().length);
		Itinerary itin = ntRes.getBagItineraryArray(0);
		assertEquals("WN", itin.getAirline());
		assertEquals("ATL", itin.getArrivalCity());
		assertEquals("JFK", itin.getDepartureCity());
		assertEquals("111", itin.getFlightnum());
		assertNotNull(ntRes.getPassengerItineraryArray());
		assertEquals(1, ntRes.getPassengerItineraryArray().length);
		Itinerary pItin = ntRes.getPassengerItineraryArray(0);
		assertEquals("WN", pItin.getAirline());
		assertEquals("ATL", pItin.getArrivalCity());
		assertEquals("JFK", pItin.getDepartureCity());
		assertEquals("111", pItin.getFlightnum());
	}
	
	@Test
	public void testReservationTwo() {
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation ntRes = getNtReservation(bi, "BBBBBB");
		
		// Check General
		assertEquals("BBBBBB", ntRes.getPnr());
		assertEquals(2, ntRes.getPaxAffected());
		
		// Check Passengers
		assertNotNull(ntRes.getPassengersArray());
		assertEquals(2, ntRes.getPassengersArray().length);
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger pass = ntRes.getPassengersArray(0);
		assertEquals("Bobby", pass.getFirstname());
		assertEquals("Flay", pass.getLastname());
		assertNotNull(pass.getAddresses());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address addr = pass.getAddresses();
		assertEquals("222 B", addr.getAddress1());
		assertEquals("Btown", addr.getCity());
		assertEquals("GA", addr.getState());
		assertEquals("30339", addr.getZip());
		assertEquals("b@b.com", addr.getEmailAddress());
		assertEquals("1 (222) 222-2221", addr.getHomePhone());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger pass2 = ntRes.getPassengersArray(1);
		assertEquals("Billy", pass2.getFirstname());
		assertEquals("Flay", pass2.getLastname());
		assertNotNull(pass.getAddresses());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address addr2 = pass2.getAddresses();
		assertEquals("1 (222) 222-2222", addr2.getWorkPhone());
		
		// Check Itineraries
		assertNotNull(ntRes.getBagItineraryArray());
		assertEquals(2, ntRes.getBagItineraryArray().length);
		Itinerary itin = ntRes.getBagItineraryArray(0);
		assertEquals("WN", itin.getAirline());
		assertEquals("ATL", itin.getArrivalCity());
		assertEquals("JFK", itin.getDepartureCity());
		assertEquals("221", itin.getFlightnum());
		Itinerary itin2 = ntRes.getBagItineraryArray(1);
		assertEquals("WN", itin2.getAirline());
		assertEquals("LAX", itin2.getArrivalCity());
		assertEquals("ATL", itin2.getDepartureCity());
		assertEquals("222", itin2.getFlightnum());
		assertNotNull(ntRes.getPassengerItineraryArray());
		assertEquals(2, ntRes.getPassengerItineraryArray().length);
		Itinerary pItin = ntRes.getPassengerItineraryArray(0);
		assertEquals("WN", pItin.getAirline());
		assertEquals("ATL", pItin.getArrivalCity());
		assertEquals("JFK", pItin.getDepartureCity());
		assertEquals("221", pItin.getFlightnum());
		Itinerary pItin2 = ntRes.getPassengerItineraryArray(1);
		assertEquals("WN", pItin2.getAirline());
		assertEquals("LAX", pItin2.getArrivalCity());
		assertEquals("ATL", pItin2.getDepartureCity());
		assertEquals("222", pItin2.getFlightnum());
	}
	
	@Test
	public void testReservationThree() {
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation ntRes = getNtReservation(ci, "CCCCCC");
		
		// Check General
		assertEquals("CCCCCC", ntRes.getPnr());
		assertEquals(2, ntRes.getPaxAffected());
		
		// Check Passengers
		assertNotNull(ntRes.getPassengersArray());
		assertEquals(2, ntRes.getPassengersArray().length);
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger pass = ntRes.getPassengersArray(0);
		assertEquals("Charlie", pass.getFirstname());
		assertEquals("Chaplin", pass.getLastname());
		assertNotNull(pass.getAddresses());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address addr = pass.getAddresses();
		assertEquals("333 C", addr.getAddress1());
		assertEquals("Ctown", addr.getCity());
		assertEquals("GA", addr.getState());
		assertEquals("30339", addr.getZip());
		assertEquals("c@c.com", addr.getEmailAddress());
		assertEquals("1 (333) 333-3331", addr.getMobilePhone());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger pass2 = ntRes.getPassengersArray(1);
		assertEquals("Coraline", pass2.getFirstname());
		assertEquals("Jones", pass2.getLastname());
		assertNotNull(pass.getAddresses());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address addr2 = pass2.getAddresses();
		assertEquals("3332 C", addr2.getAddress1());
		assertEquals("Ctown", addr2.getCity());
		assertEquals("GA", addr2.getState());
		assertEquals("30332", addr2.getZip());
		assertEquals("c2@c.com", addr2.getEmailAddress());
		assertEquals("1 (333) 333-3332", addr2.getWorkPhone());
		
		// Check Itineraries
		assertNotNull(ntRes.getPassengerItineraryArray());
		assertEquals(0, ntRes.getPassengerItineraryArray().length);
		assertNotNull(ntRes.getBagItineraryArray());
		assertEquals(0, ntRes.getBagItineraryArray().length);
	}
	
	
	////////////////////////////////////         HELPER METHODS          ///////////////////////////////////////
	
	private aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation getNtReservation(GetPNRRequestDocument doc, String pnr) {
		Reservation res = new Reservation();
		res.setStub(stub);
		res.setDoc(doc);
		User user = new User();
		ReservationResponse resp = null;
		try {
			resp = res.getReservationData(user, pnr, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(resp);
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation ntRes = resp.getReservation();
		assertNotNull(ntRes);
		return ntRes;
	}
	
	private GetPNRResponseDocument getResponseOne() {
		GetPNRResponseDocument doc = GetPNRResponseDocument.Factory.newInstance();
		GetPNRResponse res = doc.addNewGetPNRResponse();
		res.setPNR("AAAAAA");
		Passenger pass = res.addNewPassenger();
		
		AddressList addrList = pass.addNewAddressList();
		Address addr = addrList.addNewAddress();
		addr.setAddressLine1("111 A");
		addr.setCity("Atown");
		addr.setState("GA");
		addr.setPostalCode("30339");
		addr.setEmail("a@a.com");
		
		NameList nameList = pass.addNewNameList();
		Name name = nameList.addNewName();
		name.setFirstName("Albert");
		name.setLastName("Wesker");
		
		FlightList flightList = pass.addNewFlightList();
		Flight flight = flightList.addNewFlight();
		flight.setAirline("WN");
		flight.setDestination("ATL");
		flight.setOrigin("JFK");
		flight.setFlightNum("111");
		flight.setDepartureDate(Calendar.getInstance());
		
		PhoneList phoneList = pass.addNewPhoneList();
		Phone phone = phoneList.addNewPhone();
		phone.setCountryCode("1");
		phone.setAreaCode("111");
		phone.setExchangeNumber("111");
		phone.setLineNumber("1111");
		phone.setPhoneType("P_HOME");
		phone.setExtensionNumber("123");
		
		return doc;
	}
	
	private GetPNRResponseDocument getResponseTwo() {
		GetPNRResponseDocument doc = GetPNRResponseDocument.Factory.newInstance();
		GetPNRResponse res = doc.addNewGetPNRResponse();
		res.setPNR("BBBBBB");
		Passenger pass = res.addNewPassenger();
		
		AddressList addrList = pass.addNewAddressList();
		Address addr = addrList.addNewAddress();
		addr.setAddressLine1("222 B");
		addr.setCity("Btown");
		addr.setState("GA");
		addr.setPostalCode("30339");
		addr.setEmail("b@b.com");
		
		NameList nameList = pass.addNewNameList();
		Name name = nameList.addNewName();
		name.setFirstName("Bobby");
		name.setLastName("Flay");
		Name name2 = nameList.addNewName();
		name2.setFirstName("Billy");
		name2.setLastName("Flay");
		
		FlightList flightList = pass.addNewFlightList();
		Flight flight = flightList.addNewFlight();
		flight.setAirline("WN");
		flight.setDestination("ATL");
		flight.setOrigin("JFK");
		flight.setFlightNum("221");
		flight.setDepartureDate(Calendar.getInstance());
		Flight flight2 = flightList.addNewFlight();
		flight2.setAirline("WN");
		flight2.setDestination("LAX");
		flight2.setOrigin("ATL");
		flight2.setFlightNum("222");
		flight2.setDepartureDate(Calendar.getInstance());
		
		PhoneList phoneList = pass.addNewPhoneList();
		Phone phone = phoneList.addNewPhone();
		phone.setCountryCode("1");
		phone.setAreaCode("222");
		phone.setExchangeNumber("222");
		phone.setLineNumber("2221");
		phone.setPhoneType("P_HOME");
		Phone phone2 = phoneList.addNewPhone();
		phone2.setCountryCode("1");
		phone2.setAreaCode("222");
		phone2.setExchangeNumber("222");
		phone2.setLineNumber("2222");
		phone2.setPhoneType("P_WORK");
		
		return doc;
	}
	
	private GetPNRResponseDocument getResponseThree() {
		GetPNRResponseDocument doc = GetPNRResponseDocument.Factory.newInstance();
		GetPNRResponse res = doc.addNewGetPNRResponse();
		res.setPNR("CCCCCC");
		Passenger pass = res.addNewPassenger();
		
		AddressList addrList = pass.addNewAddressList();
		Address addr = addrList.addNewAddress();
		addr.setAddressLine1("333 C");
		addr.setCity("Ctown");
		addr.setState("GA");
		addr.setPostalCode("30339");
		addr.setEmail("c@c.com");
		Address addr2 = addrList.addNewAddress();
		addr2.setAddressLine1("3332 C");
		addr2.setCity("Ctown");
		addr2.setState("GA");
		addr2.setPostalCode("30332");
		addr2.setEmail("c2@c.com");
		
		NameList nameList = pass.addNewNameList();
		Name name = nameList.addNewName();
		name.setFirstName("Charlie");
		name.setLastName("Chaplin");
		Name name2 = nameList.addNewName();
		name2.setFirstName("Coraline");
		name2.setLastName("Jones");
		
		PhoneList phoneList = pass.addNewPhoneList();
		Phone phone = phoneList.addNewPhone();
		phone.setCountryCode("1");
		phone.setAreaCode("333");
		phone.setExchangeNumber("333");
		phone.setLineNumber("3331");
		phone.setPhoneType("P_MOBILE");
		Phone phone2 = phoneList.addNewPhone();
		phone2.setCountryCode("1");
		phone2.setAreaCode("333");
		phone2.setExchangeNumber("333");
		phone2.setLineNumber("3332");
		phone2.setPhoneType("P_WORK");
		
		return doc;
	}
	
}