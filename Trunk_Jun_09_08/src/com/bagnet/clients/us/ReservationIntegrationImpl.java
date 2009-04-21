package com.bagnet.clients.us;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bagnet.clients.defaul.DefaultFormFieldMapper;
import com.bagnet.clients.defaul.DefaultFormFieldMapper.NetTracerField;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.usairways.lcc.aat_sharesws.services_asmx.Baggage;
import com.usairways.lcc.aat_sharesws.services_asmx.Booking;
import com.usairways.lcc.aat_sharesws.services_asmx.Itinerary;
import com.usairways.lcc.aat_sharesws.services_asmx.Passenger;
import com.usairways.lcc.aat_sharesws.services_asmx.PassengerAddress;
import com.usairways.lcc.aat_sharesws.services_asmx.Segment;

public class ReservationIntegrationImpl extends
		com.bagnet.clients.defaul.ReservationIntegrationImpl implements
		ReservationIntegration {
	private Logger logger = Logger.getLogger(ReservationIntegrationImpl.class);
	private Booking booking = null;
	private final long WS_NULL_DATE = -62135578800000L;
	private final int HOURS_BACK_ITINERARY = 24;
	private final int HOURS_FORWARD_ITINERARY = 12;
	private String pnrContents;
	private DefaultFormFieldMapper fMap = new DefaultFormFieldMapper();


	private ArrayList<String> getBooking(HttpServletRequest request,
			IncidentForm form) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			SharesIntegrationWrapper wrapper = new SharesIntegrationWrapper();
			// Record Locator
			
			boolean result = true;
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0) {
				// If searching bag record locator				
				result = wrapper.getBookingByKey(form.getRecordlocator(), null);
			} else if (form.getBagTagNumber() != null && form.getBagTagNumber().trim().length() > 0) {
				// If searching by bag tag number
				result = wrapper.getBookingByKey(null, form.getBagTagNumber());
			} else {
				// Neither a bag tag or record locator was provided.
				if (wrapper.getErrorMessage() != null) {
					addError(errors, wrapper.getErrorMessage());
				} else {
					addError(errors, "error.no.recordlocator.bagtag");
				}
					
				return errors;
			}
			
			if (!result) {
				addError(errors, "error.no.recordlocator.bagtag");
			} else {
				// Get the booking
				booking = wrapper.getBooking();
				pnrContents = wrapper.getPnrContents();
			}

			return errors;

		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			return errors;
		}
	}
	
	private ArrayList<String> getBooking(HttpServletRequest request,
			OnHandForm form) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			SharesIntegrationWrapper wrapper = new SharesIntegrationWrapper();
			// Record Locator
			
			boolean result = true;
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0) {
				// If searching bag record locator				
				result = wrapper.getBookingByKey(form.getRecordlocator(), null);
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
				booking = wrapper.getBooking();
			}

			return errors;

		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
			return errors;
		}
	}
	
	public ArrayList<String> populateIncidentForm(HttpServletRequest request,
			IncidentForm form, int incidentType) {
		
		ArrayList<String> retList = getBooking(request, form);
		
		if (retList.size() > 0) {
			return retList;
		}
		
		form = (IncidentForm) request.getSession().getAttribute("incidentForm");
		populateIncidentFormInner(form, incidentType, request);
		HttpSession session = request.getSession();
		session.setAttribute("incidentForm", form);
		return retList;
	}
	
	public ArrayList<String> populateOhdForm(HttpServletRequest request,
			OnHandForm form) {
		ArrayList<String> retList = getBooking(request, form);
		
		if (retList.size() > 0) {
			return retList;
		}
			
		populateOhdFormInner(request, form);
		HttpSession session = request.getSession();
		session.setAttribute("OnHandForm", form);
		return retList;
	}

	private void populateOhdFormInner(HttpServletRequest request, OnHandForm form) {
		
		// Record Locator
		form.setRecordlocator(booking.getRecordLocator());
		Agent user = (Agent) request.getSession().getAttribute("user");
		form.setHolding_company(user.getCompanycode_ID());
		form.setHolding_station(user.getStation().getStationcode());
		
		// Passengers and Bags
		if (booking.getPassengers() != null) {
			Passenger[] aPax = booking.getPassengers().getPassengerArray();
			
			for (int i = 0; i<aPax.length; ++i) {
				Passenger pax = aPax[i];
			
				com.bagnet.nettracer.tracing.db.OHD_Passenger fPax = form.getPassenger(i);
				
				fPax.setFirstname(fMap.mapString(NetTracerField.PAX_FIRST_NAME, pax.getFirstName()));
				fPax.setMiddlename(fMap.mapString(NetTracerField.PAX_MIDDLE_NAME, pax.getMiddleName()));
				fPax.setLastname(fMap.mapString(NetTracerField.PAX_LAST_NAME, pax.getLastName()));
				
				com.bagnet.nettracer.tracing.db.OHD_Address fAddr = fPax.getAddress(0);
				fAddr.setEmail(fMap.mapString(NetTracerField.OHD_ADDR_EMAIL, pax.getEmailAddress()));
				
				PassengerAddress[] pAddrs = pax.getPassengerAddresses().getPassengerAddressArray();
				for (int j=0; j<pAddrs.length; ++j) {
					PassengerAddress pAddr = pAddrs[j];
					fAddr.setAddress1(fMap.mapString(NetTracerField.ADDRESS1, pAddr.getAddressLine1()));
					fAddr.setAddress2(fMap.mapString(NetTracerField.ADDRESS2, pAddr.getAddressLine2() + " " + pAddr.getAddressLine3()));
					fAddr.setCity(fMap.mapString(NetTracerField.ADDR_CITY, pAddr.getCity()));
					fAddr.setCountrycode_ID(fMap.mapString(NetTracerField.ADDR_COUNTRY, pAddr.getCountryCode()));
					if (!pAddr.getPhone().equals("000-")) {
						fAddr.setHomephone(fMap.mapString(NetTracerField.OHD_ADDR_PHONE, pAddr.getPhone()));
					}
					
					fAddr.setZip(fMap.mapString(NetTracerField.ADDR_ZIP, pAddr.getPostalCode()));
					if (fAddr.getCountrycode_ID().equals(TracingConstants.US_COUNTRY_CODE)) {
						fAddr.setState_ID(fMap.mapString(NetTracerField.ADDR_STATE, pAddr.getProvinceState()));					
					} else {
						fAddr.setProvince(fMap.mapString(NetTracerField.ADDR_PROVINCE, pAddr.getProvinceState()));
					}				
				}
			}
		}
		
		int itinCount = 0;
	
		// Baggage Itinerary
		if (booking.getBaggageItinerary() != null) {
			Itinerary[] itin = booking.getBaggageItinerary().getItineraryArray();
			if (itin.length > 0) {
				int i = 0;
				if (itin[i].getSegments() != null) {
					Segment[] segs = itin[i].getSegments().getSegmentArray();
					for (int j=0; j<segs.length; ++j) {
						com.bagnet.nettracer.tracing.db.OHD_Itinerary fItin = form.getItinerary(itinCount);
						Segment seg = segs[j];
						
						Long deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
						Long nowtime = ((new Date()).getTime()) / 3600000;
						Long timeDifference = nowtime - deptime;
						
						/*
						boolean includeSegment = false;
						if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
							includeSegment = true;
						}
						*/
						
						if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
							fItin.setAirline(seg.getCarrierCode());
							fItin.setFlightnum(seg.getFlightNumber());
							fItin.setLegfrom(seg.getDepartureStation());
							fItin.setLegto(seg.getArrivalStation());
							fItin.setDepartdate(seg.getDepartureEstimated().getTime());
							fItin.setArrivedate(seg.getArrivalEstimated().getTime());
							fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
							fItin.setScharrivetime(seg.getArrivalEstimated().getTime());

							if (seg.getArrivalActual().getTime().getTime() != WS_NULL_DATE) {
								fItin.setActarrivetime(seg.getArrivalActual().getTime());
							}
							
							if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
								fItin.setActdeparttime(seg.getDepartureActual().getTime());
							}
							fItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
							itinCount ++;
						}
					}
				} else {
					com.bagnet.nettracer.tracing.db.OHD_Itinerary fItin = form.getItinerary(itinCount);
					fItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
					itinCount ++;
				}
			}
		}
		if (itinCount == 0) {
			com.bagnet.nettracer.tracing.db.OHD_Itinerary fItin = form.getItinerary(itinCount);
			fItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
			itinCount ++;
		}
	}


	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {

		SharesIntegrationWrapper wrapper = new SharesIntegrationWrapper();
		wrapper.writeCommentToPNR(recordLocator, comment);
		return new ArrayList<String>();
	}

	private void populateIncidentFormInner(IncidentForm form,
			int itemtype, HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		
		// Record Locator
		form.setRecordlocator(booking.getRecordLocator());
		form.setAgent(user);
		if (pnrContents != null) {
			form.setOtherSystemInformation(pnrContents);
		}
		int bagIndex = 0;
		
		// Pasengers and Bags
		if (booking.getPassengers() != null) {
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
				
				fPax.setFirstname(fMap.mapString(NetTracerField.PAX_FIRST_NAME, pax.getFirstName()));
				fPax.setMiddlename(fMap.mapString(NetTracerField.PAX_MIDDLE_NAME, pax.getMiddleName()));
				fPax.setLastname(fMap.mapString(NetTracerField.PAX_LAST_NAME, pax.getLastName()));
	
				AirlineMembership fMem = new AirlineMembership();
				fMem.setMembershipnum(pax.getFrequentFlierNumber());
				fPax.setMembership(fMem);
				
				// Not currently using these two web-service provided fields:
				// pax.getTitle();
				// pax.getSuffix();
				
				com.bagnet.nettracer.tracing.db.Address fAddr = fPax.getAddress(0);
				fAddr.setEmail(fMap.mapString(NetTracerField.ADDR_EMAIL, pax.getEmailAddress()));
				
				PassengerAddress[] pAddrs = pax.getPassengerAddresses().getPassengerAddressArray();
				for (int j=0; j<pAddrs.length; ++j) {
					PassengerAddress pAddr = pAddrs[j];
					fAddr.setAddress1(fMap.mapString(NetTracerField.ADDRESS1, pAddr.getAddressLine1()));
					fAddr.setAddress2(fMap.mapString(NetTracerField.ADDRESS2, pAddr.getAddressLine2() + " " + pAddr.getAddressLine3()));
					fAddr.setCity(fMap.mapString(NetTracerField.ADDR_CITY, pAddr.getCity()));
					fAddr.setCountrycode_ID(fMap.mapString(NetTracerField.ADDR_COUNTRY, pAddr.getCountryCode()));
					if (!pAddr.getPhone().trim().equals("000-")) {
						fAddr.setHomephone(fMap.mapString(NetTracerField.ADDR_PHONE, pAddr.getPhone()));
					}
					fAddr.setZip(fMap.mapString(NetTracerField.ADDR_ZIP, pAddr.getPostalCode()));
					if (fAddr.getCountrycode_ID().equals(TracingConstants.US_COUNTRY_CODE)) {
						fAddr.setState_ID(fMap.mapString(NetTracerField.ADDR_STATE, pAddr.getProvinceState()));					
					} else {
						fAddr.setProvince(fMap.mapString(NetTracerField.ADDR_PROVINCE, pAddr.getProvinceState()));
					}				
				}
				
				Baggage[] paxBags = pax.getBaggageList().getBaggageArray();
				
				for (int j=0; j<paxBags.length; ++j) {
					Baggage bag = paxBags[j];
					
					long deptime = (bag.getBagTagDate().getTime().getTime()) / 3600000;
					long nowtime = ((new Date()).getTime()) / 3600000;
					Long timeDifference = nowtime - deptime;
					if (timeDifference <= HOURS_BACK_ITINERARY) {

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
						form.setNumbagchecked(bagIndex);
					}
				}
			}
		}
		
		
		int itinCount = 0;

		// Passenger Itinerary
		if (booking.getPassengerItinerary() != null) {
			Itinerary[] itin = booking.getPassengerItinerary().getItineraryArray();
			if (itin.length > 0) {
				int i = 0;
				if (itin[i].getSegments() != null) {
					Segment[] segs = itin[i].getSegments().getSegmentArray();
					for (int j=0; j<segs.length; ++j) {
						com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.PASSENGER_ROUTING);
						Segment seg = segs[j];
		
						Long deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
						Long nowtime = ((new Date()).getTime()) / 3600000;
						Long timeDifference = nowtime - deptime;
						
						/*
						boolean includeSegment = false;
						if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
							includeSegment = true;
						}
						*/
						
						if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {

							// Create carrier in database in not present.
							CompanyBMO.createCompany(seg.getCarrierCode(), session);

							fItin.setAirline(seg.getCarrierCode());
							fItin.setFlightnum(seg.getFlightNumber());
							fItin.setLegfrom(seg.getDepartureStation());
							fItin.setLegto(seg.getArrivalStation());
							fItin.setDepartdate(seg.getDepartureEstimated().getTime());
							fItin.setArrivedate(seg.getArrivalEstimated().getTime());
							fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
							fItin.setScharrivetime(seg.getArrivalEstimated().getTime());
							

							if (seg.getArrivalActual().getTime().getTime() != WS_NULL_DATE) {
								fItin.setActarrivetime(seg.getArrivalActual().getTime());
							}
							
							if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
								fItin.setActdeparttime(seg.getDepartureActual().getTime());
							}
							fItin.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
							itinCount ++;
						}
					}
				} else {
					com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.PASSENGER_ROUTING);
					fItin.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
					itinCount ++;
				}
			}
			if (itinCount == 0) {
				com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.PASSENGER_ROUTING);
				fItin.setItinerarytype(TracingConstants.PASSENGER_ROUTING);
				itinCount ++;
			}
		}
		
		// Baggage Itinerary
		int bagItinCount = 0;
		if (booking.getBaggageItinerary() != null) {
			Itinerary[] itin = booking.getBaggageItinerary().getItineraryArray();
			if (itin.length > 0) {
				int i = 0;
				if (itin[i].getSegments() != null) {
					Segment[] segs = itin[i].getSegments().getSegmentArray();
					for (int j=0; j<segs.length; ++j) {
						
						Segment seg = segs[j];
						
						Long deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
						Long nowtime = ((new Date()).getTime()) / 3600000;
						Long timeDifference = nowtime - deptime;
						
						/*
						boolean includeSegment = false;
						if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
							includeSegment = true;
						}
						*/
						
						if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
							com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.BAGGAGE_ROUTING);							
							fItin.setAirline(seg.getCarrierCode());
							fItin.setFlightnum(seg.getFlightNumber());
							fItin.setLegfrom(seg.getDepartureStation());
							fItin.setLegto(seg.getArrivalStation());
							fItin.setDepartdate(seg.getDepartureEstimated().getTime());
							fItin.setArrivedate(seg.getArrivalEstimated().getTime());
							fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
							fItin.setScharrivetime(seg.getArrivalEstimated().getTime());

							if (seg.getArrivalActual().getTime().getTime() != WS_NULL_DATE) {
								fItin.setActarrivetime(seg.getArrivalActual().getTime());
							}
							
							if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
								fItin.setActdeparttime(seg.getDepartureActual().getTime());
							}
							fItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
							itinCount ++;
							bagItinCount ++;
						}
					}
				} else {
					com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.BAGGAGE_ROUTING);
					fItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
					itinCount ++;
					bagItinCount ++;
				}
			}
		}
		if (bagItinCount == 0) {
			com.bagnet.nettracer.tracing.db.Itinerary fItin = form.getItinerary(itinCount, TracingConstants.BAGGAGE_ROUTING);
			fItin.setItinerarytype(TracingConstants.BAGGAGE_ROUTING);
			itinCount ++;
		}
	}
	
}
