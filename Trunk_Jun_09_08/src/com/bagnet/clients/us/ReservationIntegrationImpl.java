package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

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
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentAddress;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentItinerary;
import com.bagnet.nettracer.ws.onlineclaims.xsd.IncidentPhone;
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
	private static ArrayList<String> firstClass = new ArrayList<String>();
	private static ArrayList<String> businessClass = new ArrayList<String>();
	
	static {
		firstClass.add("F");
		firstClass.add("A");
		firstClass.add("O");
		firstClass.add("P");

		businessClass.add("C");
		businessClass.add("D");
		businessClass.add("Z");
		businessClass.add("I");
		businessClass.add("J");
	}


	private boolean getBooking(String recordLocator) {
		try {
			SharesIntegrationWrapper wrapper = new SharesIntegrationWrapper();
			// Record Locator
			
			boolean result = true;
			if (recordLocator != null && recordLocator.trim().length() > 0) {
				// If searching bag record locator				
				result = wrapper.getBookingByKey(recordLocator, null);
			} else {				
				return false;
			}
			
			if (result) {
				// Get the booking
				booking = wrapper.getBooking();
				pnrContents = wrapper.getPnrContents();
				return true;
			}

		} catch (Exception e) {
			logger.error(e.getStackTrace());
			e.printStackTrace();
		}
		return false;
	}


	private ArrayList<String> getBooking(HttpServletRequest request,
			IncidentForm form) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			SharesIntegrationWrapper wrapper = new SharesIntegrationWrapper();
			// Record Locator
			
			boolean result = true;
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0 && request.getParameter("recordlocator") != null) {
				// If searching bag record locator				
				result = wrapper.getBookingByKey(form.getRecordlocator(), null);
			} else if (form.getBagTagNumber() != null && form.getBagTagNumber().trim().length() > 0 && request.getParameter("bagTagNumber") != null) {
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
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0 && request.getParameter("recordlocator") != null) {
				// If searching bag record locator				
				result = wrapper.getBookingByKey(form.getRecordlocator(), null);
			} else if (form.getBagTagNumber() != null && form.getBagTagNumber().trim().length() > 0 && request.getParameter("bagTagNumber") != null) {
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
		
		form = (OnHandForm) request.getSession().getAttribute("OnHandForm");
		populateOhdFormInner(request, form);
		HttpSession session = request.getSession();
		session.setAttribute("OnHandForm", form);
		return retList;
	}

	private void populateOhdFormInner(HttpServletRequest request, OnHandForm form) {
		
		// Record Locator
		form.setRecordlocator(booking.getRecordLocator());
		Agent user = (Agent) request.getSession().getAttribute("user");
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

				if (pax.getBaggageList().getBaggageArray().length == 1) {
					Baggage bag =	pax.getBaggageList().getBaggageArray(0);
					long deptime = (bag.getBagTagDate().getTime().getTime()) / 3600000;
					long nowtime = ((new Date()).getTime()) / 3600000;
					Long timeDifference = nowtime - deptime;
					if (timeDifference <= HOURS_BACK_ITINERARY) {
						if (bag.getBagTag() != null && bag.getBagTag().length() > 0) {
							form.setBagTagNumber(bag.getBagTag().substring(0, Math.min(bag.getBagTag().length(), 13)));
						}
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
						Long deptime =null;
						if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
							deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
						} else {
							deptime =  ((new Date()).getTime()) / 3600000;
						}
						Long nowtime = ((new Date()).getTime()) / 3600000;
						Long timeDifference = nowtime - deptime;
						
						if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
							fItin.setAirline(seg.getCarrierCode());
							fItin.setFlightnum(seg.getFlightNumber());
							fItin.setLegfrom(seg.getDepartureStation());
							fItin.setLegto(seg.getArrivalStation());
							if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
								fItin.setDepartdate(seg.getDepartureEstimated().getTime());
								fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
							}
							if(seg.getArrivalEstimated().getTime().getTime() != WS_NULL_DATE){
								fItin.setArrivedate(seg.getArrivalEstimated().getTime());
								fItin.setScharrivetime(seg.getArrivalEstimated().getTime());
							}

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
		
		if (request.getParameter("pcn_id") != null) {
			form.setPcn_id(request.getParameter("pcn_id"));
		}
		
		if (pnrContents != null) {
			form.setOtherSystemInformation(pnrContents);
			
			// MJS: non-revenue code logic
			String lastName = form.getPassenger(0).getLastname();
			String revCode = RevenueCodesUtilImpl.getInstance().getNonRevenueCodeFromPnr(pnrContents, lastName);
			if (revCode != null) {
				form.setNonrevenue(1);
				form.setRevenueCode(revCode);
			}
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
				if (pax.getFrequentFlierNumber() != null && pax.getFrequentFlierNumber().length() > 0) {
//					fMem.setCompanycode_ID("US");
				}
				
				fMem.setMembershipnum(pax.getFrequentFlierNumber());
				if (pax.getFrequentFlierNumber() != null && pax.getFrequentFlierNumber().trim().length() > 0) {
					if (pax.getFrequentFlierStatus() != null && pax.getFrequentFlierStatus().length() > 0) {
						fMem.setMembershipstatus(pax.getFrequentFlierStatus());	
					} else {
						fMem.setMembershipstatus("Basic");
					}
					
				}
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
						if (itemtype == TracingConstants.LOST_DELAY && bag.getBagTag() != null && bag.getBagTag().length() > 0) {
							Incident_Claimcheck ic = form.getClaimcheck(bagIndex);
							ic.setClaimchecknum(bag.getBagTag().substring(0, Math.min(bag.getBagTag().length(), 13)));
						}
	
						// bag info
						Item theitem = form.getItem(bagIndex, itemtype);
						if (itemtype != TracingConstants.LOST_DELAY && bag.getBagTag() != null && bag.getBagTag().length() > 0) {
							theitem.setClaimchecknum(bag.getBagTag().substring(0, Math.min(bag.getBagTag().length(), 13)));
						}
						theitem.set_DATEFORMAT(user.getDateformat().getFormat());
						theitem.setCurrency_ID(user.getDefaultcurrency());
						theitem.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
						theitem.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
						theitem.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
						theitem.setBagnumber(bagIndex);
						theitem.setStatus(StatusBMO.getStatus(
								TracingConstants.ITEM_STATUS_OPEN));
	
						bagIndex++;
						form.setNumbagchecked(bagIndex);
					}
				}
			}
		}
		
		
		int itinCount = 0;

		String highestClassService = null;
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
		
						Long deptime = null;
						if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
							deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
						} else {
							deptime =  ((new Date()).getTime()) / 3600000;
						}
						Long nowtime = ((new Date()).getTime()) / 3600000;
						Long timeDifference = nowtime - deptime;
						
						/*
						boolean includeSegment = false;
						if (seg.getDepartureActual().getTime().getTime() != WS_NULL_DATE) {
							includeSegment = true;
						}
						*/
						highestClassService = processService(seg.getServiceClass(), highestClassService);
						
						if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {

							// Create carrier in database in not present.
							CompanyBMO.createCompany(seg.getCarrierCode(), session);
							
							
							 
							
							fItin.setAirline(seg.getCarrierCode());
							fItin.setFlightnum(seg.getFlightNumber());
							fItin.setLegfrom(seg.getDepartureStation());
							fItin.setLegto(seg.getArrivalStation());

							if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
								fItin.setDepartdate(seg.getDepartureEstimated().getTime());
								fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
							}

							if(seg.getArrivalEstimated().getTime().getTime() != WS_NULL_DATE){
								fItin.setArrivedate(seg.getArrivalEstimated().getTime());
								fItin.setScharrivetime(seg.getArrivalEstimated().getTime());
							}
							

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
		
		if (highestClassService != null) {
			for (com.bagnet.nettracer.tracing.db.Passenger pax : form.getPassengerlist()) {
				if (pax.getMembership() != null) {
					AirlineMembership mem = pax.getMembership();
					mem.setMembershipstatus(mem.getMembershipstatus() + "/" + highestClassService);
				}
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
						
						Long deptime = null;
						if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
							deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
						} else {
							deptime =  ((new Date()).getTime()) / 3600000; //What should this be?
						}
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
							if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
								fItin.setDepartdate(seg.getDepartureEstimated().getTime());
								fItin.setSchdeparttime(seg.getDepartureEstimated().getTime());
							}
							if(seg.getArrivalEstimated().getTime().getTime() != WS_NULL_DATE){
								fItin.setArrivedate(seg.getArrivalEstimated().getTime());
								fItin.setScharrivetime(seg.getArrivalEstimated().getTime());
							}

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
	
	
	public Incident populateIncidentForWS(Incident incident, int passIndex) {
		boolean pnrAvailable = getBooking(incident.getPnr());
		if (pnrAvailable) {
						
			if (pnrContents != null) {
				incident.setOsi(pnrContents);
			}
			
			if (booking.getPassengers() != null) {
				Passenger[] aPax = booking.getPassengers().getPassengerArray();
							
				if (aPax.length > passIndex && aPax[passIndex] != null) {
					Passenger pax = aPax[passIndex];
					incident.setNumPassengers(aPax.length);
										
					incident.setMembershipNumber(pax.getFrequentFlierNumber());
					if (pax.getFrequentFlierNumber() != null && pax.getFrequentFlierNumber().trim().length() > 0) {
						if (pax.getFrequentFlierStatus() != null && pax.getFrequentFlierStatus().length() > 0) {
							incident.setMembershipStatus(pax.getFrequentFlierStatus());	
						} else {
							incident.setMembershipStatus("Basic");
						}	
					}
					
					IncidentAddress iAddr = incident.addNewDeliveryAddress();
					incident.setEmail(fMap.mapString(NetTracerField.ADDR_EMAIL, pax.getEmailAddress())); //EMAIL
					
					PassengerAddress[] pAddrs = pax.getPassengerAddresses().getPassengerAddressArray();
					if (pAddrs.length > 0) {
						PassengerAddress pAddr = pAddrs[0];
						iAddr.setAddress1(fMap.mapString(NetTracerField.ADDRESS1, pAddr.getAddressLine1()));
						iAddr.setAddress2(fMap.mapString(NetTracerField.ADDRESS2, pAddr.getAddressLine2() + " " + pAddr.getAddressLine3()));
						iAddr.setCity(fMap.mapString(NetTracerField.ADDR_CITY, pAddr.getCity()));
						iAddr.setCountry(fMap.mapString(NetTracerField.ADDR_COUNTRY, pAddr.getCountryCode()));
						iAddr.setPostalCode(fMap.mapString(NetTracerField.ADDR_ZIP, pAddr.getPostalCode()));
						if (iAddr.getCountry().equals(TracingConstants.US_COUNTRY_CODE)) {
							iAddr.setState(fMap.mapString(NetTracerField.ADDR_STATE, pAddr.getProvinceState()));					
						} else {
							iAddr.setProvince(fMap.mapString(NetTracerField.ADDR_PROVINCE, pAddr.getProvinceState()));
						}				
						if (!pAddr.getPhone().trim().equals("000-")) {
							IncidentPhone iPhone = incident.addNewPhone();
							iPhone.setNumber(fMap.mapString(NetTracerField.ADDR_PHONE, pAddr.getPhone()));
							iPhone.setType(0);    // 												PUT STATIC FINAL VARIABLES SOMEWHERE AND USE HERE!!!
						}
					}
				}
			}
			
			String highestClassService = null;
			// Passenger Itinerary
			if (booking.getPassengerItinerary() != null) {
				Itinerary[] itin = booking.getPassengerItinerary().getItineraryArray();
				if (itin.length > 0) {
					int i = 0;
					if (itin[i].getSegments() != null) {
						Segment[] segs = itin[i].getSegments().getSegmentArray();
						for (int j=0; j<segs.length; ++j) {
							Segment seg = segs[j];
			
							Long deptime = null;
							if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
								deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
							} else {
								deptime =  ((new Date()).getTime()) / 3600000;
							}
							Long nowtime = ((new Date()).getTime()) / 3600000;
							Long timeDifference = nowtime - deptime;
							
							highestClassService = processService(seg.getServiceClass(), highestClassService);
							
							if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
								IncidentItinerary iItin = incident.addNewItinerary();
						
								iItin.setAirline(seg.getCarrierCode());
								iItin.setFlightNum(seg.getFlightNumber());
								iItin.setDepartureCity(seg.getDepartureStation());
								iItin.setArrivalCity(seg.getArrivalStation());
								Calendar cal = new GregorianCalendar();
								if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
									cal.setTime(seg.getDepartureEstimated().getTime());
								}
								iItin.setDepartureDate(cal);
								if(seg.getArrivalEstimated().getTime().getTime() != WS_NULL_DATE){
									cal.setTime(seg.getArrivalEstimated().getTime());
								}
								iItin.setArrivalDate(cal);
								
								iItin.setType(TracingConstants.PASSENGER_ROUTING);
							}
						}
					} else {
						IncidentItinerary iItin = incident.addNewItinerary();
						iItin.setType(TracingConstants.PASSENGER_ROUTING);
					}
				} else {
					IncidentItinerary iItin = incident.addNewItinerary();
					iItin.setType(TracingConstants.PASSENGER_ROUTING);
				}
			}
			
			if (highestClassService != null) {
				incident.setMembershipStatus(incident.getMembershipStatus() + "/" + highestClassService);
			}
			
			// Baggage Itinerary
			if (booking.getBaggageItinerary() != null) {
				Itinerary[] itin = booking.getBaggageItinerary().getItineraryArray();
				if (itin.length > 0) {
					int i = 0;
					if (itin[i].getSegments() != null) {
						Segment[] segs = itin[i].getSegments().getSegmentArray();
						for (int j=0; j<segs.length; ++j) {
							
							Segment seg = segs[j];
							
							Long deptime = null;
							if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
								deptime = (seg.getDepartureEstimated().getTime().getTime()) / 3600000;
							} else {
								deptime =  ((new Date()).getTime()) / 3600000;
							}
							Long nowtime = ((new Date()).getTime()) / 3600000;
							Long timeDifference = nowtime - deptime;
														
							if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
								IncidentItinerary iItin = incident.addNewItinerary();
								iItin.setAirline(seg.getCarrierCode());
								iItin.setFlightNum(seg.getFlightNumber());
								iItin.setDepartureCity(seg.getDepartureStation());
								iItin.setArrivalCity(seg.getArrivalStation());
								Calendar cal = new GregorianCalendar();
								if(seg.getDepartureEstimated().getTime().getTime() != WS_NULL_DATE){
									cal.setTime(seg.getDepartureEstimated().getTime());
								}
								iItin.setDepartureDate(cal);
								if(seg.getArrivalEstimated().getTime().getTime() != WS_NULL_DATE){
									cal.setTime(seg.getArrivalEstimated().getTime());
								}
								iItin.setArrivalDate(cal);
								
								iItin.setType(TracingConstants.BAGGAGE_ROUTING);
							}
						}
					} else {
						IncidentItinerary iItin = incident.addNewItinerary();
						iItin.setType(TracingConstants.BAGGAGE_ROUTING);
					}
				}
			} else {
				IncidentItinerary iItin = incident.addNewItinerary();
				iItin.setType(TracingConstants.BAGGAGE_ROUTING);
			}
		}
		return incident;
	}
	
	
	private String processService(String serviceClass, String highestClassService) {
		
		if (serviceClass == null && highestClassService!= null) {
			return highestClassService;
		} else if (businessClass.contains(serviceClass) && (highestClassService == null || !highestClassService.equalsIgnoreCase("Business"))) {
			return "Business";
		} else if (firstClass.contains(serviceClass) && (highestClassService == null || !highestClassService.equalsIgnoreCase("First"))) {
			return "First";
		} else if (highestClassService == null) {
			return "Coach";
		}
		return highestClassService;
	}
	
	
	
}
