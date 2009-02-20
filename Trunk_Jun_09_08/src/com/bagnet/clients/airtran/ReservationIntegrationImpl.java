package com.bagnet.clients.airtran;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.navitaire.schemas.messages.booking.ArrayOfBaggage;
import com.navitaire.schemas.messages.booking.ArrayOfBookingContact;
import com.navitaire.schemas.messages.booking.ArrayOfBookingPassenger;
import com.navitaire.schemas.messages.booking.ArrayOfPassengerAddress;
import com.navitaire.schemas.messages.booking.Baggage;
import com.navitaire.schemas.messages.booking.Booking;
import com.navitaire.schemas.messages.booking.BookingContact;
import com.navitaire.schemas.messages.booking.BookingPassenger;
import com.navitaire.schemas.messages.booking.PassengerAddress;
import com.navitaire.schemas.messages.itinerary.ArrayOfJourneyService;
import com.navitaire.schemas.messages.itinerary.ArrayOfLeg;
import com.navitaire.schemas.messages.itinerary.ArrayOfSegment;
import com.navitaire.schemas.messages.itinerary.JourneyService;
import com.navitaire.schemas.messages.itinerary.Leg;
import com.navitaire.schemas.messages.itinerary.Segment;

public class ReservationIntegrationImpl extends
		com.bagnet.clients.defaul.ReservationIntegrationImpl implements
		com.bagnet.nettracer.integrations.reservation.ReservationIntegration {

	public ArrayList<String> populateIncidentForm(HttpServletRequest request, IncidentForm form,
			int incidentType) {
		ArrayList<String> errors = new ArrayList<String>();
		try {

				if (form.getRecordlocator() == null || form.getRecordlocator().trim().length() == 0) {
					addError(errors, "error.no.recordlocator");

				} else {

					NewSkiesIntegrationWrapper aiw = new NewSkiesIntegrationWrapper();
					boolean callresult = aiw.getBooking(form.getRecordlocator());
					
					if (!callresult) {
						if (aiw.getErrormsg().equals("error.norecord")) {
							
							addError(errors, "error.no.recordlocator");
						}
						else addError(errors, aiw.getErrormsg());
						return errors;
					} else {
						Booking book = aiw.getThebook();
						populateIncident(form, request, incidentType, book);
						HttpSession session = request.getSession();
						session.setAttribute("incidentForm", form);
					}

					request.setAttribute("newform", "1");
				}

			
			return errors;

		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			return errors;
		}
	}

	public ArrayList<String> writeCommentToPNR(String comment, String recordLocator) {
		boolean worked = updateComment(comment, recordLocator);
		return null;
	}

	private static void populateIncident(IncidentForm theform,
			HttpServletRequest request, int itemtype, Booking thebook) {
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		//theform = new IncidentForm();
		

		// theform = new IncidentForm();
		//IncidentBMO iBMO = new IncidentBMO();

		/*
		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		session.setAttribute("incidentForm", theform);
		// session
		// set create time
		theform.setCreatedate(TracerDateTime.getGMTDate());
		theform.setCreatetime(TracerDateTime.getGMTDate());
		// set station
		theform.setStationcreated_ID(user.getStation().getStation_ID());
		theform.setStationcreated(user.getStation());
		theform.setStationassigned_ID(user.getStation().getStation_ID());
		List agentassignedlist = TracerUtils.getAgentlist(theform
				.getStationassigned_ID());
		request.setAttribute("agentassignedlist", agentassignedlist);

		// theform.setFaultstation(new Station());
		// set agent
		theform.setAgent(user);
		

		// set status as temp to start off
		Status status = new Status();
		status.setStatus_ID(TracingConstants.MBR_STATUS_TEMP);
		theform.setStatus(status);

		// set report method
		theform.setReportmethod(user.getStation().getCompany().getVariable()
				.getReport_method());
		theform.setRecordlocator(thebook.getRecordLocator());

		*/

		
		// passenger

		// # of passengers on the plane
		ArrayOfBookingPassenger aobp = thebook.getBookingPassengers();
		BookingPassenger[] bpas = aobp.getBookingPassengerArray();
		if (bpas != null && bpas.length > 0)
			theform.setNumpassengers(bpas.length);
		else
			theform.setNumpassengers(1);

		if (bpas != null && bpas.length > 0) {
			for (int i = 0; i < bpas.length; i++) {
				Passenger pa = theform.getPassenger(i);
				Address addr = pa.getAddress(0);

				BookingPassenger bp = bpas[i];

				// name
				com.navitaire.schemas.messages.common.Name bpn = bp.getName();
				if (bpn != null) {
					pa.setFirstname(bpn.getFirstName());
					pa.setMiddlename(bpn.getMiddleName());
					pa.setLastname(bpn.getLastName());
				}

				// address
				ArrayOfPassengerAddress aopa = bp.getPassengerAddresses();

				if (aopa.getPassengerAddressArray() != null
						&& aopa.getPassengerAddressArray().length > 0) {
					PassengerAddress paddr = aopa.getPassengerAddressArray(0);

					addr.setAddress1(paddr.getAddressLine1());
					addr.setAddress2(paddr.getAddressLine2());
					addr.setCity(paddr.getCity());
					addr.setState_ID(paddr.getProvinceState());
					addr.setZip(paddr.getPostalCode());
					addr.setCountrycode_ID(paddr.getCountryCode());
					addr.setHomephone(paddr.getPhone());
					// addr.setAltphone(paddr.getFax());
					// addr.setWorkphone(paddr.getWorkPhone());
					// addr.setMobile(paddr.getOtherPhone());
					ArrayOfBookingContact aobc = thebook.getBookingContacts();
					if (aobc.getBookingContactArray() != null
							&& aobc.getBookingContactArray().length > 0) {
						BookingContact bc = aobc.getBookingContactArray(0);
						addr.setEmail(bc.getEmailAddress());
					}

				}

				// ff#
				AirlineMembership am = new AirlineMembership();
				if (bpas != null && bpas.length > (i + 1)) {
					BookingPassenger bpa = aobp.getBookingPassengerArray(i);
					am.setMembershipnum(bpa.getCustomerNumber());
				}
				pa.setMembership(am);

			}
		} else {
			theform.getPassenger(0);
		}

		// get routing
		int routingindex = 0;
		ArrayOfJourneyService abjs = thebook.getJourneyServices();
		JourneyService[] bjss = abjs.getJourneyServiceArray();
		long deptime, nowtime;

		if (bjss != null && bjss.length > 0) {
			for (int i = 0; i < bjss.length; i++) {
				JourneyService bjs = bjss[i];
				ArrayOfSegment ass = bjs.getSegments();
				Segment[] ss = ass.getSegmentArray();
				if (ss != null && ss.length > 0) {
					for (int j = 0; j < ss.length; j++) {
						Segment bs = ss[j];
						// if more than 24 hours old, don't import
						deptime = (bs.getSTD().getTime().getTime()) / 3600000;
						nowtime = ((new Date()).getTime()) / 3600000;
						if (nowtime - deptime <= 24 && nowtime >= deptime) {

							Itinerary pi = theform.getItinerary(routingindex,
									TracingConstants.PASSENGER_ROUTING); // passenger
							pi.setAirline(bs.getFlightDesignator().getCarrierCode());
							pi.setFlightnum(bs.getFlightDesignator().getFlightNumber());
							pi.setLegfrom(bs.getDepartureStation());
							pi.setLegto(bs.getArrivalStation());
							pi.setDepartdate(bs.getSTD().getTime());
							pi.setArrivedate(bs.getSTA().getTime());
							pi.setSchdeparttime(bs.getSTD().getTime());
							pi.setScharrivetime(bs.getSTA().getTime());
							routingindex++;
						}

						ArrayOfLeg als = bs.getLegs();
						Leg[] lss = als.getLegArray();
						if (lss != null && lss.length > 0) {
							for (int k = 0; k < lss.length; k++) {
								Leg leg = lss[k];

								deptime = (leg.getSTD().getTime().getTime()) / 3600000;
								nowtime = ((new Date()).getTime()) / 3600000;
								if (nowtime - deptime <= 24 && nowtime >= deptime) {
									Itinerary bi = theform.getItinerary(routingindex,
											TracingConstants.BAGGAGE_ROUTING);
									bi.setAirline(leg.getFlightDesignator().getCarrierCode());
									bi.setFlightnum(leg.getFlightDesignator().getFlightNumber());
									bi.setLegfrom(leg.getDepartureStation());
									bi.setLegto(leg.getArrivalStation());
									bi.setDepartdate(leg.getSTD().getTime());
									bi.setArrivedate(leg.getSTA().getTime());
									bi.setSchdeparttime(leg.getSTD().getTime());
									bi.setScharrivetime(leg.getSTA().getTime());
									routingindex++;
								}
							}
						}
					}
				}
			}
		}
		if (routingindex == 0) {
			theform.getItinerary(0, TracingConstants.PASSENGER_ROUTING); // passenger
			theform.getItinerary(1, TracingConstants.BAGGAGE_ROUTING); // bag route
		}

		// claim check and bag info
		int allindex = 0;
		if (bpas != null && bpas.length > 0) {
			for (int i = 0; i < bpas.length; i++) {
				BookingPassenger bpa = bpas[i];
				ArrayOfBaggage aob = bpa.getBaggageList();
				Baggage[] baggages = aob.getBaggageArray();
				if (baggages != null && baggages.length > 0) {
					for (int j = 0; j < baggages.length; j++) {

						Baggage bag = baggages[j];
						deptime = (bag.getOSTagDate().getTime().getTime()) / 3600000;
						nowtime = ((new Date()).getTime()) / 3600000;
						if (nowtime - deptime <= 24 && nowtime >= deptime) {

							// claimcheck
							if (bag.getOSTag() != null && bag.getOSTag().length() > 0) {
								Incident_Claimcheck ic = theform.getClaimcheck(allindex);
								ic.setClaimchecknum(bag.getOSTag());
							}

							// bag info
							Item theitem = theform.getItem(allindex, itemtype);
							if (itemtype != TracingConstants.LOST_DELAY
									&& bag.getOSTag() != null && bag.getOSTag().length() > 0) {
								theitem.setClaimchecknum(bag.getOSTag());
							}
							theitem.set_DATEFORMAT(user.getDateformat().getFormat());
							theitem.setCurrency_ID(user.getDefaultcurrency());
							theitem.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
							theitem.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
							theitem.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
							theitem.setBagnumber(allindex);
							theitem.setStatus(StatusBMO.getStatus(
									TracingConstants.ITEM_STATUS_OPEN, user.getCurrentlocale()));

							allindex++;
						}

					}
				}
			}
		}
		if (allindex == 0) {
			theform.getClaimcheck(0);
			Item theitem = theform.getItem(allindex, itemtype);
			theitem.set_DATEFORMAT(user.getDateformat().getFormat());
			theitem.setCurrency_ID(user.getDefaultcurrency());
			theitem.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
			theitem.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
			theitem.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
			theitem.setBagnumber(allindex);
			theitem.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN,
					user.getCurrentlocale()));
		}

		// set number of bags checked in
		if (allindex > 0)
			theform.setNumbagchecked(allindex);
		else
			theform.setNumbagchecked(1);

		// set new remark with current time and current agent
		Remark r = theform.getRemark(theform.getRemarklist().size());
		r.setCreatetime(new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT)
				.format(TracerDateTime.getGMTDate()));
		r.setAgent(user);
		r.set_DATEFORMAT(user.getDateformat().getFormat());
		r.set_TIMEFORMAT(user.getTimeformat().getFormat());
		r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		// create new article
		if (itemtype == TracingConstants.MISSING_ARTICLES) {
			Articles a = theform.getArticle(0);
			a.set_DATEFORMAT(user.getDateformat().getFormat());
			a.setCurrency_ID(user.getDefaultcurrency());
		}

		Company_Specific_Variable csv = AdminUtils.getCompVariable(user
				.getCompanycode_ID());
		theform.setEmail_customer(csv.isEmail_customer() ? 1 : 0);
	}
	
	private static boolean updateComment(String comment, String recordlocator) {
		try {

			// run again webservice
			NewSkiesIntegrationWrapper aiw = new NewSkiesIntegrationWrapper();
			boolean callresult = aiw.updateBookingComment(recordlocator,comment);
			
			if (!callresult) {
				return false;
			}
				
			return true;
			
		} catch (Exception e) {
			return false;
		}

		
	}
}
