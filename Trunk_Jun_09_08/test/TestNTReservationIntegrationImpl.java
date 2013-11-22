

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument;
import aero.nettracer.serviceprovider.ws_1_0.GetReservationDataResponseDocument.GetReservationDataResponse;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.forms.IncidentForm;


public class TestNTReservationIntegrationImpl extends
		com.bagnet.clients.defaul.NTReservationIntegrationImpl implements
		ReservationIntegration {
	
	public ArrayList<String> populateIncidentForm(HttpServletRequest request, IncidentForm form, int incidentType){
		if(form.getRecordlocator().equals("BBBBBB")){
				super.booking=getTestResponse();
		} else if(form.getRecordlocator().equals("AAAAAA")) {
			super.booking=getTestResponse2();
		} else {
			super.booking=getTestResponse3();
		}
		if (booking != null) {
			
			super.populateIncidentFormInner(form, incidentType, request);
			
			HttpSession session = request.getSession();
			session.setAttribute("incidentForm", form);
		}
		return null;
	}
	
	private Reservation getTestResponse() {
		GetReservationDataResponseDocument doc = GetReservationDataResponseDocument.Factory.newInstance();
		GetReservationDataResponse resDataRes = doc.addNewGetReservationDataResponse();
		ReservationResponse ret=resDataRes.addNewReturn();
		Reservation res=ret.addNewReservation();
		res.setPnr("CCCCCC");
		Passenger pass = res.addNewPassengers();
		
		Address addr = pass.addNewAddresses();
		addr.setAddress1("333 C");
		addr.setCity("Ctown");
		addr.setState("GA");
		addr.setZip("30339");
		addr.setEmailAddress("c@c.com");
		Address addr2 = pass.addNewAddresses();
		addr2.setAddress1("3332 C");
		addr2.setCity("Ctown");
		addr2.setState("GA");
		addr2.setZip("30332");
		addr2.setEmailAddress("c2@c.com");
		
		pass.setFirstname("Charlie");
		pass.setLastname("Chaplin");
		
		ClaimCheck cc=res.addNewClaimChecks();
		cc.setPosId("12345678");
		cc.setTimeChecked(new GregorianCalendar());

		ClaimCheck cc2=res.addNewClaimChecks();
		cc2.setPosId("87654321");
		cc2.setTimeChecked(new GregorianCalendar());
		
		return res;
	}
	private Reservation getTestResponse2() {
		GetReservationDataResponseDocument doc = GetReservationDataResponseDocument.Factory.newInstance();
		GetReservationDataResponse resDataRes = doc.addNewGetReservationDataResponse();
		ReservationResponse ret=resDataRes.addNewReturn();
		Reservation res=ret.addNewReservation();
		res.setPnr("CCCCCC");
		Passenger pass = res.addNewPassengers();
		
		Address addr = pass.addNewAddresses();
		addr.setAddress1("333 C");
		addr.setCity("Ctown");
		addr.setState("GA");
		addr.setZip("30339");
		addr.setEmailAddress("c@c.com");
		Address addr2 = pass.addNewAddresses();
		addr2.setAddress1("3332 C");
		addr2.setCity("Ctown");
		addr2.setState("GA");
		addr2.setZip("30332");
		addr2.setEmailAddress("c2@c.com");
		
		pass.setFirstname("Charlie");
		pass.setLastname("Chaplin");
		
		ClaimCheck cc=res.addNewClaimChecks();
		cc.setPosId("12345678");
		cc.setTimeChecked(new GregorianCalendar());

		ClaimCheck cc2=res.addNewClaimChecks();
		cc2.setPosId("12345678");
		cc2.setTimeChecked(new GregorianCalendar());
		
		return res;
	}
	private Reservation getTestResponse3() {
		GetReservationDataResponseDocument doc = GetReservationDataResponseDocument.Factory.newInstance();
		GetReservationDataResponse resDataRes = doc.addNewGetReservationDataResponse();
		ReservationResponse ret=resDataRes.addNewReturn();
		Reservation res=ret.addNewReservation();
		res.setPnr("CCCCCC");
		Passenger pass = res.addNewPassengers();
		
		Address addr = pass.addNewAddresses();
		addr.setAddress1("333 C");
		addr.setCity("Ctown");
		addr.setState("GA");
		addr.setZip("30339");
		addr.setEmailAddress("c@c.com");
		Address addr2 = pass.addNewAddresses();
		addr2.setAddress1("3332 C");
		addr2.setCity("Ctown");
		addr2.setState("GA");
		addr2.setZip("30332");
		addr2.setEmailAddress("c2@c.com");
		
		pass.setFirstname("Charlie");
		pass.setLastname("Chaplin");
		
		ClaimCheck cc=res.addNewClaimChecks();
		cc.setTimeChecked(new GregorianCalendar());

		ClaimCheck cc2=res.addNewClaimChecks();
		cc2.setTimeChecked(new GregorianCalendar());
		
		return res;
	}
	
}