package com.bagnet.clients.alaska;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;

public class ReservationIntegrationImpl extends
		com.bagnet.clients.defaul.ReservationIntegrationImpl implements
		ReservationIntegration {

	public ArrayList<String> populateIncidentForm(HttpServletRequest request,
			IncidentForm form, int incidentType) {
		
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		form.setAgent(user);
		
		// Get objects to populate
		Passenger currentPax = form.getPassenger(0);
		Address address = currentPax.getAddress(0);
		Itinerary ia = form.getItinerary(0, TracingConstants.PASSENGER_ROUTING);
//		Itinerary ib = form.getItinerary(1, TracingConstants.PASSENGER_ROUTING);
		Itinerary ic = form.getItinerary(1, TracingConstants.BAGGAGE_ROUTING);
//		Itinerary id = form.getItinerary(3, TracingConstants.BAGGAGE_ROUTING);
		Incident_Claimcheck claim1 = form.getClaimcheck(0);
//		Incident_Claimcheck claim2 = form.getClaimcheck(1);
		form.getItem(0, -1);

		
		// Populate objects
		claim1.setClaimchecknum("DA456789");
		
		form.setRecordlocator("DADEMO");
		form.setNumpassengers(1);
		form.setNumbagchecked(1);
		form.setNumbagreceived(0);
		form.setTicketnumber("0271234567890");
		
		form.setOtherSystemInformation("DADEMO\n1.1BRABSON/JOHN\n1 DA 726A 31DEC T DFWFLL HK2   130P  410P\nTKT/TIME LIMIT\n1.T-TL/X/1200/06MAR/MOW016\nAA FACTS\n1.OSI AA TKTL TL/X/1200/06MAR/MOW016\n2.OSI CLAIMED FROM SU PNR LOC MGDOLE\nPHONES\n1.H-555-555-5555\nREMARKS\n1.H-PAX CALLED TO CONF RES AUG10\n");
		
		currentPax.setFirstname("JOHN");
		currentPax.setLastname("Brabson");
		
		AirlineMembership am = new AirlineMembership();
		am.setCompanycode_ID("DA");
		am.setMembershipnum("123456789");
		am.setMembershipstatus("Gold");
		currentPax.setMembership(am);
		
		address.setAddress1("1511 3rd Avenue");
		address.setCity("Seattle");
		address.setState_ID("WA");
		address.setZip("98101");
		address.setPermanent(true);
		address.setHomephone("555-555-5555");
		
		ia.setAirline("DA");
		ia.setLegfrom("ATL");
		ia.setLegto("DFW");
		ia.setFlightnum("1100");
		
//		GregorianCalendar dt = new GregorianCalendar(2010,6,14,6,30);
//		GregorianCalendar at = new GregorianCalendar(2010,6,14,9,30);
		GregorianCalendar dt = new GregorianCalendar();
		GregorianCalendar at = new GregorianCalendar();
		
		ia.setDepartdate(dt.getTime());
		ia.setSchdeparttime(dt.getTime());
		ia.setArrivedate(at.getTime());
		ia.setScharrivetime(at.getTime());
		
		ia.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
		
		ic.setAirline("DA");
		ic.setLegfrom("ATL");
		ic.setLegto("DFW");
		ic.setFlightnum("1100");
		
		ic.setDepartdate(dt.getTime());
		ic.setSchdeparttime(dt.getTime());
		ic.setArrivedate(at.getTime());
		ic.setScharrivetime(at.getTime());
		
		ic.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		
//		ib.setAirline("US");
//		ib.setLegfrom("ATL");
//		ib.setLegto("FLL");
//		ib.setFlightnum("2222");
//		ib.setDepartdate(new Date());
//		ib.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
//		
//		ic.setAirline("US");
//		ic.setLegfrom("DFW");
//		ic.setLegto("ATL");
//		ic.setFlightnum("1111");
//		ic.setDepartdate(new Date());
//		ic.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
//		
//		id.setAirline("US");
//		id.setLegfrom("ATL");
//		id.setLegto("FLL");
//		id.setFlightnum("2222");
//		id.setDepartdate(new Date());
//		id.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		
		
//		Item item = form.getItem(0, incidentType);
//		item.setColor("BU");
//		item.setFnameonbag("Jimmy");
//		item.setLnameonbag("Brabson");
//		item.setBagtype("22");
//		item.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
//		item.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
//		item.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
//		item.setBagnumber(0);
//		item.setStatus(StatusBMO.getStatus(
//				TracingConstants.ITEM_STATUS_OPEN));
		
//		ArrayList inventorylist = new ArrayList();
//		Item_Inventory toAdd = new Item_Inventory();
//		toAdd.setCategorytype_ID(29);
//		toAdd.setDescription("Mickey Mouse shirt");
//		inventorylist.add(toAdd);
		
//		item.setInventorylist(inventorylist);
		
		session.setAttribute("incidentForm", form);
		
		return new ArrayList<String>();
	}

	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {
		return new ArrayList<String>();
	}
	

	public ArrayList<String> populateOhdForm(HttpServletRequest request,
			OnHandForm form) {
		

		
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		form.setAgent(user);
		

		if (form.get_DATEFORMAT() == null) {
			form.set_DATEFORMAT(user.getDateformat().getFormat());
		}
		
		if (form.get_TIMEFORMAT() == null) {
			form.set_TIMEFORMAT(user.getTimeformat().getFormat());
		}
		
		if (form.get_TIMEZONE() == null) {
			form.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
		}
		

		form.setHolding_company(user.getCompanycode_ID());
		form.setHolding_station(user.getStation().getStationcode());
		
		
		// Get objects to populate
		OHD_Passenger currentPax = form.getPassenger(0);
		OHD_Address address = currentPax.getAddress(0);
		
		OHD_Itinerary ia = form.getItinerary(0);
//		Itinerary ib = form.getItinerary(1, TracingConstants.PASSENGER_ROUTING);
//		Itinerary ic = form.getItinerary(1, TracingConstants.BAGGAGE_ROUTING);
//		Itinerary id = form.getItinerary(3, TracingConstants.BAGGAGE_ROUTING);
//		Incident_Claimcheck claim1 = form.getClaimcheck(0);
		form.setBagTagNumber("DA456789");
//		Incident_Claimcheck claim2 = form.getClaimcheck(1);
//		form.getItem(0, -1);

		
		// Populate objects
//		claim1.setClaimchecknum("AS456789");
		
		form.setRecordlocator("DADEMO");
//		form.setNumpassengers(1);
//		form.setNumbagchecked(1);
//		form.setNumbagreceived(0);
//		form.setTicketnumber("0271234567890");
//		form.set
		
		
//		form.setOtherSystemInformation("ALASKA\n1.1BRABSON/JIMMY\n1 DA 726A 31DEC T DFWFLL HK2   130P  410P\nTKT/TIME LIMIT\n1.T-TL/X/1200/06MAR/MOW016\nAA FACTS\n1.OSI AA TKTL TL/X/1200/06MAR/MOW016\n2.OSI CLAIMED FROM SU PNR LOC MGDOLE\nPHONES\n1.H-555-555-5555\nREMARKS\n1.H-PAX CALLED TO CONF RES AUG10\n");
		
		currentPax.setFirstname("John");
		currentPax.setLastname("Brabson");
		
		form.setMembershipnum("123456789");
		form.setMembershipstatus("Gold");
		
//		AirlineMembership am = new AirlineMembership();
//		am.setCompanycode_ID("AS");
//		am.setMembershipnum("123456789");
//		am.setMembershipstatus("Gold");
//		currentPax.setMembership(am);
		
		address.setAddress1("1511 3rd Avenue");
		address.setCity("Seattle");
		address.setState_ID("WA");
		address.setZip("98101");
//		address.setPermanent(true);
		address.setHomephone("555-555-5555");
		
		ia.setAirline("DA");
		ia.setLegfrom("ATL");
		ia.setLegto("DFW");
		ia.setFlightnum("1100");
		
//		GregorianCalendar dt = new GregorianCalendar(2010,6,14,6,30);
//		GregorianCalendar at = new GregorianCalendar(2010,6,14,9,30);
		GregorianCalendar dt = new GregorianCalendar();
		GregorianCalendar at = new GregorianCalendar();
		ia.setDepartdate(dt.getTime());
		ia.setSchdeparttime(dt.getTime());
		ia.setArrivedate(at.getTime());
		ia.setScharrivetime(at.getTime());
//		
//		ia.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
//		
//		ic.setAirline("AS");
//		ic.setLegfrom("MCO");
//		ic.setLegto("SEA");
//		ic.setFlightnum("1100");
//		
//		ic.setDepartdate(dt.getTime());
//		ic.setSchdeparttime(dt.getTime());
//		ic.setArrivedate(at.getTime());
//		ic.setScharrivetime(at.getTime());
//		
//		ic.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		
		session.setAttribute("OnHandForm", form);
		
		return new ArrayList<String>();
		
	}

}
