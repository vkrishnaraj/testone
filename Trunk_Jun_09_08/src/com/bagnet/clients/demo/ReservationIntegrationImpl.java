package com.bagnet.clients.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone;

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
		Itinerary ib = form.getItinerary(1, TracingConstants.PASSENGER_ROUTING);
		Itinerary ic = form.getItinerary(2, TracingConstants.BAGGAGE_ROUTING);
		Itinerary id = form.getItinerary(3, TracingConstants.BAGGAGE_ROUTING);
		Incident_Claimcheck claim1 = form.getClaimcheck(0);
		Incident_Claimcheck claim2 = form.getClaimcheck(1);
		form.getItem(0, -1);

		
		// Populate objects
		claim1.setClaimchecknum("US000001");
		
		form.setRecordlocator("DUNLAF");
		form.setNumpassengers(1);
		form.setNumbagchecked(2);
		form.setNumbagreceived(1);
		form.setTicketnumber("0001234567890");
		
		form.setOtherSystemInformation("PNR Content\nECQ8Y6\n1. 1SANDERS/MICHAEL\n1 B61270T 09MAY MO LASPHX HK1   610A  833A 32A*\nFONE-\n1.WEB7260332*DAYOFDEPFON/EH781\n2.WEB*JETBLUE.COM BOOKING/N000\n3.WEB*EMAIL-MSANDERS*NETTRACER.AERO/N000\nTKT-T/FQR/XXXXXXXXXXXX6355/24301196232/0001//50.00\nGEN FAX-  *TKN/SSR FOR TKT NBRS\n1.SSRDOCSUSHK1/////28JUN65/M//SANDERS/MICHAEL/D-MICHAEL/S\n  SSRDOCSUS///TEPHEND\n2.OSI US CTCH 781-726-0332\nFOP-$DSXXXXXXXXXXX3333/0112\nRMKS-\n1.CARDHOLDER: MICHAEL SANDERS\n2.CARDHOLDER: 5010 EDGECLIFF*WICHITA FALLS*TX*76302*USA\n3.CLIENT IP: 255.255.255.255\n");
		
		currentPax.setFirstname("Michael");
		currentPax.setLastname("Sanders");
		
		
		AirlineMembership am = new AirlineMembership();
		am.setCompanycode_ID("DA");
		am.setMembershipnum("123456789");
		am.setMembershipstatus("Gold");
		currentPax.setMembership(am);
		
		address.setAddress1("5010 Edgecliff");
		address.setCity("Wichita Falls");
		address.setState_ID("TX");
		address.setZip("76302");
		address.setPermanent(true);
		address.setHomephone("409-886-5941");
		address.setEmail("msanders@wf.net");
		
		ia.setAirline("US");
		ia.setLegfrom("IAH");
		ia.setLegto("ATL");
		ia.setFlightnum("1111");
		ia.setDepartdate(new Date());
		ia.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
		
		ib.setAirline("US");
		ib.setLegfrom("ATL");
		ib.setLegto("FLL");
		ib.setFlightnum("2222");
		ib.setDepartdate(new Date());
		ib.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
		
		ic.setAirline("US");
		ic.setLegfrom("IAH");
		ic.setLegto("ATL");
		ic.setFlightnum("1111");
		ic.setDepartdate(new Date());
		ic.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		
		id.setAirline("US");
		id.setLegfrom("ATL");
		id.setLegto("FLL");
		id.setFlightnum("2222");
		id.setDepartdate(new Date());
		id.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
		
		/*
		Item item = form.getItem(0, incidentType);
		item.setColor("BU");
		item.setFnameonbag("Joseph");
		item.setFnameonbag("Brown");
		item.setBagtype("22");
		item.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
		item.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
		item.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
		item.setBagnumber(0);
		item.setStatus(StatusBMO.getStatus(
				TracingConstants.ITEM_STATUS_OPEN, user.getCurrentlocale()));
		*/
		
		session.setAttribute("incidentForm", form);
		
		return new ArrayList<String>();
	}
	
	public Incident populateIncidentForWS(Incident incident, int passIndex) {
		incident.addClaimCheck("US112233");
		incident.addClaimCheck("US332211");
		IncidentItinerary itin;
		itin = incident.addNewItinerary();
		itin.setAirline("US");
		itin.setFlightNum("111");
		itin.setArrivalCity("ATL");
		itin.setDepartureCity("JFK");
		itin.setArrivalDate(new GregorianCalendar());
		itin.setDepartureDate(new GregorianCalendar());
		itin.setType(TracingConstants.PASSENGER_ROUTING);
		itin = incident.addNewItinerary();
		itin.setAirline("US");
		itin.setFlightNum("111");
		itin.setArrivalCity("ATL");
		itin.setDepartureCity("JFK");
		itin.setArrivalDate(new GregorianCalendar());
		itin.setDepartureDate(new GregorianCalendar());
		itin.setType(TracingConstants.BAGGAGE_ROUTING);
		// ITIN TYPE?
		
		IncidentAddress iAddr = incident.addNewDeliveryAddress();
		iAddr.setAddress1("123 Test");
		iAddr.setAddress2("APT 321");
		iAddr.setCity("Test City");
		iAddr.setState("GA");
		iAddr.setPostalCode("30152");
		iAddr.setCountry("US");
		
		incident.setEmail("test@email.com");
		IncidentPhone iPhone = incident.addNewPhone();
		iPhone.setNumber("123-321-1234");
		iPhone.setType(0);
		
		iPhone = incident.addNewPhone();
		iPhone.setNumber("323-121-4321");
		iPhone.setType(1);
		return incident;
	}

	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {
		return new ArrayList<String>();
	}

}
