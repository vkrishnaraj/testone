package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import phx_52n_gr90.sharesws.services_asmx.*;

import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

public class ReservationIntegrationImpl extends
		com.bagnet.clients.defaul.ReservationIntegrationImpl implements
		ReservationIntegration {
	Logger logger = Logger.getLogger(ReservationIntegrationImpl.class);

	public ArrayList<String> populateIncidentForm(HttpServletRequest request,
			IncidentForm form, int incidentType) {
		ArrayList<String> errors = null;
		try {
			SharesIntegrationWrapper wrapper = new SharesIntegrationWrapper();
			// Record Locator
			
			boolean result = true;
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0) {
				// If searching bag record locator				
				result = wrapper.getBookingByKey(null, form.getRecordlocator());
			} else if (form.getBagTagNumber() != null && form.getBagTagNumber().trim().length() > 0) {
				// If searching by bag tag number
				result = wrapper.getBookingByKey(null, form.getBagTagNumber());
			} else {
				// Neither a bag tag or record locator was provided.
				addError(errors, "error.no.recordlocator.bagtag");
				return errors;
			}
			
			if (!result) {
				if (wrapper.getErrorMessage() != null) {
					addError(errors, wrapper.getErrorMessage());
				} else {
					addError(errors, "error.no.recordlocator.bagtag");
				}
			} else {
				// Get the booking
				Booking booking = wrapper.getBooking();
				populateIncidentForm(booking, form, incidentType, request);
				
				// Populate the incident
			}

			return errors;

		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			return errors;
		}

	}


	private void populateIncidentForm(Booking booking, IncidentForm form,
			int itemtype, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		
		// Record Locator
		form.setRecordlocator(booking.getRecordLocator());
		int bagIndex = 0;
		
		// Pasengers and Bags
		Passenger[] aPax = booking.getPassengers().getPassengerArray();
		if (aPax.length > 0) {
			form.setNumpassengers(aPax.length);
		} else {
			form.setNumpassengers(1);
			form.getPassenger(0);
		}
		
		for (int i = 0; i<aPax.length; ++i) {
			Passenger pax = aPax[i];
		
			com.bagnet.nettracer.tracing.db.Passenger fPax = form.getPassenger(i);
			
			fPax.setFirstname(pax.getFirstName());
			fPax.setMiddlename(pax.getMiddleName());
			fPax.setLastname(pax.getLastName());

			AirlineMembership fMem = new AirlineMembership();
			fMem.setMembershipnum(pax.getFrequentFlierNumber());
			fPax.setMembership(fMem);
			
			// Not currently using these two web-service provided fields:
			// pax.getTitle();
			// pax.getSuffix();
			
			com.bagnet.nettracer.tracing.db.Address fAddr = fPax.getAddress(0);
			fAddr.setEmail(pax.getEmailAddress());
			
			PassengerAddress[] pAddrs = pax.getPassengerAddresses().getPassengerAddressArray();
			for (int j=0; j<pAddrs.length; ++j) {
				PassengerAddress pAddr = pAddrs[j];
				fAddr.setAddress1(pAddr.getAddressLine1());
				fAddr.setAddress2(pAddr.getAddressLine2() + " " + pAddr.getAddressLine3());
				fAddr.setCity(pAddr.getCity());
				fAddr.setCountrycode_ID(pAddr.getCountryCode());
				fAddr.setHomephone(pAddr.getPhone());
				fAddr.setZip(pAddr.getPostalCode());
				if (fAddr.getCountrycode_ID().equals(TracingConstants.US_COUNTRY_CODE)) {
					fAddr.setState_ID(pAddr.getProvinceState());					
				} else {
					fAddr.setProvince(pAddr.getProvinceState());
				}				
			}
			
			Baggage[] paxBags = pax.getBaggageList().getBaggageArray();
			
			for (int j=0; j<paxBags.length; ++j) {
				Baggage bag = paxBags[j];
				
				long deptime = (bag.getBagTagDate().getTime().getTime()) / 3600000;
				long nowtime = ((new Date()).getTime()) / 3600000;

				if (nowtime - deptime <= 24 && nowtime >= deptime) {
					// claimcheck
					if (bag.getBagTag() != null && bag.getBagTag().length() > 0) {
						Incident_Claimcheck ic = form.getClaimcheck(bagIndex);
						ic.setClaimchecknum(bag.getBagTag());
					}

					// bag info
					Item theitem = form.getItem(bagIndex, itemtype);
					if (itemtype != TracingConstants.LOST_DELAY
							&& bag.getBagTag() != null && bag.getBagTag().length() > 0) {
						theitem.setClaimchecknum(bag.getBagTag());
					}
					theitem.set_DATEFORMAT(user.getDateformat().getFormat());
					theitem.setCurrency_ID(user.getDefaultcurrency());
					theitem.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
					theitem.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
					theitem.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
					theitem.setBagnumber(bagIndex);
					theitem.setStatus(StatusBMO.getStatus(
							TracingConstants.ITEM_STATUS_OPEN, user.getCurrentlocale()));

					bagIndex++;
				}
			}
		}
		
		
		int itinCount = 0;

		// Passenger Itinerary
		Itinerary[] itin = booking.getPassengerItinerary().getItineraryArray();
		for (int i=0; i<itin.length; ++i) {
			Segment[] segs = itin[i].getSegments().getSegmentArray();
			for (int j=0; j<segs.length; ++j) {
				com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.PASSENGER_ROUTING);

				Segment seg = segs[j];
				fItin.setAirline(seg.getCarrierCode());
				fItin.setFlightnum(seg.getFlightNumber());
				fItin.setLegfrom(seg.getDepartureStation());
				fItin.setLegto(seg.getArrivalStation());
				fItin.setDepartdate(seg.getDepartureEstimated().getTime());
				fItin.setArrivedate(seg.getArrivalEstimated().getTime());
				fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
				fItin.setScharrivetime(seg.getArrivalEstimated().getTime());
				fItin.setActarrivetime(seg.getArrivalActual().getTime());
				fItin.setActdeparttime(seg.getDepartureActual().getTime());
				itinCount ++;
			}
		}
		
		// Baggage Itinerary
		itin = booking.getBaggageItinerary().getItineraryArray();
		for (int i=0; i<itin.length; ++i) {
			Segment[] segs = itin[i].getSegments().getSegmentArray();
			for (int j=0; j<segs.length; ++j) {
				com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.BAGGAGE_ROUTING);

				Segment seg = segs[j];
				fItin.setAirline(seg.getCarrierCode());
				fItin.setFlightnum(seg.getFlightNumber());
				fItin.setLegfrom(seg.getDepartureStation());
				fItin.setLegto(seg.getArrivalStation());
				fItin.setDepartdate(seg.getDepartureEstimated().getTime());
				fItin.setArrivedate(seg.getArrivalEstimated().getTime());
				fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
				fItin.setScharrivetime(seg.getArrivalEstimated().getTime());
				fItin.setActarrivetime(seg.getArrivalActual().getTime());
				fItin.setActdeparttime(seg.getDepartureActual().getTime());
				itinCount ++;
			}
		}
	}


	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {
		// TODO Auto-generated method stub
		return null;
	}

}
