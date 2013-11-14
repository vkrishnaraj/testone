package com.bagnet.clients.defaul;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation;

import com.bagnet.clients.defaul.DefaultFormFieldMapper.NetTracerField;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.BagDrop;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;


public class NTReservationIntegrationImpl extends
		com.bagnet.clients.defaul.ReservationIntegrationImpl implements
		ReservationIntegration {
	
	private static final Logger logger = Logger.getLogger(NTReservationIntegrationImpl.class);
	private Reservation booking = null;
	private DefaultFormFieldMapper fMap = new DefaultFormFieldMapper();
	private final int HOURS_BACK_ITINERARY = PropertyBMO.getValueAsInt(PropertyBMO.RESERVATION_HOURS_BACK);
	private final int HOURS_FORWARD_ITINERARY = PropertyBMO.getValueAsInt(PropertyBMO.RESERVATION_HOURS_FORWARD);
	


	private ArrayList<String> getBooking(HttpServletRequest request,
			IncidentForm form) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			NTIntegrationWrapper wrapper = new NTIntegrationWrapper();
			
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0) {
				// If searching bag record locator				
				booking = wrapper.getReservationData(form.getRecordlocator(), null);
			} else if (form.getBagTagNumber() != null && form.getBagTagNumber().trim().length() > 0) {
				// If searching by bag tag number
				booking = wrapper.getReservationData(null, form.getBagTagNumber());
			} else {
				if (TracerProperties.isTrue(form.getAgent().getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG)) {
					addError(errors, "error.no.recordlocator.bagtag");
				} else {
					addError(errors, "error.no.recordlocator");
				}
					
				return errors;
			}

			if (wrapper.getErrorMessage() != null) {
				addError(errors, wrapper.getErrorMessage());
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
			NTIntegrationWrapper wrapper = new NTIntegrationWrapper();
			
			if (form.getRecordlocator() != null && form.getRecordlocator().trim().length() > 0) {
				// If searching bag record locator				
				booking = wrapper.getReservationData(form.getRecordlocator(), null);
			} else if (form.getBagTagNumber() != null && form.getBagTagNumber().trim().length() > 0) {
				// If searching by bag tag number
				booking = wrapper.getReservationData(null, form.getBagTagNumber());
			} else {
				if (TracerProperties.isTrue(form.getAgent().getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG)) {
					addError(errors, "error.no.recordlocator.bagtag");
				} else {
					addError(errors, "error.no.recordlocator");
				}
					
				return errors;
			}
			
			if (wrapper.getErrorMessage() != null) {
				addError(errors, wrapper.getErrorMessage());
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
		if (booking != null) {
			populateIncidentFormInner(form, incidentType, request);
			HttpSession session = request.getSession();
			session.setAttribute("incidentForm", form);
		} else {
			retList.add("error.communication.reservation.system");
		}
		return retList;
	}
	
	public ArrayList<String> populateOhdForm(HttpServletRequest request,
			OnHandForm form) {

		
		ArrayList<String> retList = getBooking(request, form);
		
		if (retList.size() > 0) {
			return retList;
		}

		if (booking != null) {
			populateOhdFormInner(request, form);
			HttpSession session = request.getSession();
			session.setAttribute("OnHandForm", form);
		} else {
			retList.add("error.communication.reservation.system");
		}
		return retList;

	}

	

	@SuppressWarnings("rawtypes")
	private void populateOhdFormInner(HttpServletRequest request, OnHandForm form) {
		
		
		
		Agent user = (Agent) request.getSession().getAttribute("user");
		form.setHolding_company(user.getCompanycode_ID());
		form.setHolding_station(user.getStation().getStationcode());
		form.setAgent(user);

		form.setPnr(booking.getPnr());
		
		// Does not apply to on-hands
		
		///// Collections /////	
		if (booking.getClaimChecksArray().length == 1) {
			ClaimCheck cc =	booking.getClaimChecksArray(0);
			if (cc.getTimeChecked() != null) {
				
				Long checkedTime = (cc.getTimeChecked().getTime().getTime()) / 3600000;
				Long nowtime = ((new Date()).getTime()) / 3600000;
				Long timeDifference = nowtime - checkedTime;
				if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
					form.setBagTagNumber(fMap.mapStringAndTrim(NetTracerField.CLAIM_CHECK_NUMBER, cc.getTagNumber()));
				}
			}
		}
		
		
		Passenger[] paxArr = booking.getPassengersArray();
		for (int i = 0; i< paxArr.length; ++i) {
			Passenger pax = paxArr[i];
			
			
			
			OHD_Passenger formPax = form.getPassenger(i);
			formPax.setFirstname(fMap.mapStringAndTrim(NetTracerField.PAX_FIRST_NAME, pax.getFirstname()));
			formPax.setLastname(fMap.mapStringAndTrim(NetTracerField.PAX_LAST_NAME, pax.getLastname()));
			formPax.setMiddlename(fMap.mapStringAndTrim(NetTracerField.PAX_MIDDLE_NAME, pax.getMiddlename()));
			
			if (paxArr.length == 1) {
				form.setCompanycode_ID(fMap.mapStringAndTrim(NetTracerField.MEMBERSHIP_AIRLINE, pax.getFfAirline()));
				form.setMembershipnum(fMap.mapStringAndTrim(NetTracerField.MEMBERSHIP_NUM, pax.getFfNumber()));
				form.setMembershipstatus(fMap.mapStringAndTrim(NetTracerField.MEMBERSHIP_STATUS, pax.getFfStatus()));
			}
			
			Address add = pax.getAddresses();
									
			OHD_Address faddr = new OHD_Address();
			faddr.setOhd_passenger(formPax);
			
			faddr.setAddress1(fMap.mapStringAndTrim(NetTracerField.ADDRESS1, add.getAddress1()));
			faddr.setAddress2(fMap.mapStringAndTrim(NetTracerField.ADDRESS2, add.getAddress2()));
			faddr.setCity(fMap.mapStringAndTrim(NetTracerField.ADDRESS2, add.getCity()));
			
			
			
			if (add.getCountry() == null){
				if(user.getCompanycode_ID().equals("WS") || !TracerUtils.isValidState(add.getState())) {
					faddr.setProvince(fMap.mapStringAndTrim(NetTracerField.ADDR_PROVINCE, add.getState()));
				} else {
					faddr.setState_ID(fMap.mapStringAndTrim(NetTracerField.ADDR_STATE, add.getState()));
				}
			} else {
				if(add.getCountry().equals("US")){
					faddr.setState_ID(fMap.mapStringAndTrim(NetTracerField.ADDR_STATE, add.getState()));
				} else {
					faddr.setProvince(fMap.mapStringAndTrim(NetTracerField.ADDR_PROVINCE, add.getState()));
				}
			}
			
			faddr.setZip(fMap.mapStringAndTrim(NetTracerField.ADDR_ZIP, add.getZip()));
			faddr.setCountrycode_ID(fMap.mapStringAndTrim(NetTracerField.ADDR_COUNTRY, add.getCountry()));
			
			faddr.setEmail(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_EMAIL, add.getEmailAddress()));
			faddr.setHomephone(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getHomePhone()));
			faddr.setMobile(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getMobilePhone()));
			faddr.setWorkphone(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getWorkPhone()));
			faddr.setAltphone(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getAltPhone()));
			
			
			HashSet<OHD_Address> faddresses = new HashSet<OHD_Address>();
			faddresses.add(faddr);
			formPax.setAddresses(faddresses);
		}
		
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] itinArr = booking.getPassengerItineraryArray();
		int routingType = TracingConstants.PASSENGER_ROUTING;
		form.setItinerarylist(new ArrayList());
		processOhdItinerary(form, itinArr, routingType);
		
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] bagItinArr = booking.getBagItineraryArray();
		routingType = TracingConstants.BAGGAGE_ROUTING;
		processOhdItinerary(form, bagItinArr, routingType);
	}

	private void processOhdItinerary(
			OnHandForm form,
			aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] bagItinArr,
			int routingType) {
		boolean noneAdded = true;
		
		if (bagItinArr != null && bagItinArr.length != 0) { 
			for (int i = 0; i< bagItinArr.length; ++i) {
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary itin = bagItinArr[i];
				
				Calendar baseTime = null;
				if (itin.getSchdeparttime() != null) {
					baseTime = itin.getSchdeparttime();
				} else if (itin.getTimeChecked() != null) {
					baseTime = itin.getTimeChecked();
				}
				if (baseTime != null) {
					
					Long checkedTime = (baseTime.getTime().getTime()) / 3600000;
					Long nowtime = ((new Date()).getTime()) / 3600000;
					Long timeDifference = nowtime - checkedTime;
					if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
						OHD_Itinerary fitin = form.getItinerary(form.getItinerarylist().size());
						fitin.setItinerarytype(routingType);
						fitin.setAirline(fMap.mapStringAndTrim(NetTracerField.AIRLINE, itin.getAirline()));
						fitin.setFlightnum(fMap.mapStringAndTrim(NetTracerField.FLIGHT, itin.getFlightnum()));
						fitin.setDepartdate(processDate(itin.getSchdeparttime()));
						fitin.setSchdeparttime(processDate(itin.getSchdeparttime()));
						fitin.setLegfrom(fMap.mapStringAndTrim(NetTracerField.AIRPORT, itin.getDepartureCity()));
						fitin.setLegto(fMap.mapStringAndTrim(NetTracerField.AIRPORT, itin.getArrivalCity()));
						fitin.setSchdeparttime(processDate(itin.getSchdeparttime()));
						fitin.setScharrivetime(processDate(itin.getScharrivetime()));
						noneAdded = false;
					}
				}
			}
		}
		
		if (noneAdded && form.getItinerarylist().size() == 0) {
			OHD_Itinerary fitin = form.getItinerary(form.getItinerarylist().size());
			fitin.setItinerarytype(routingType);
		}
	}
	
	private void processIncItinerary(
			IncidentForm form,
			aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] bagItinArr,
			int routingType) {
		boolean noneAdded = true;
		if (bagItinArr != null && bagItinArr.length != 0) {
			for (int i = 0; i < bagItinArr.length; ++i) {
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary itin = bagItinArr[i];
				
				Calendar baseTime = null;
				if (itin.getSchdeparttime() != null) {
					baseTime = itin.getSchdeparttime();
				} else if (itin.getTimeChecked() != null) {
					baseTime = itin.getTimeChecked();
				}
				if (baseTime != null) {

					Long checkedTime = (baseTime.getTime()
							.getTime()) / 3600000;
					Long nowtime = ((new Date()).getTime()) / 3600000;
					Long timeDifference = nowtime - checkedTime;
					// if you are looking here for a missing itinerary, please
					// double-check the dates that were returned from the 
					// SABRE response. The departure date may be a year in the
					// future.
					if (timeDifference <= HOURS_BACK_ITINERARY
							&& timeDifference >= -HOURS_FORWARD_ITINERARY) {
						com.bagnet.nettracer.tracing.db.Itinerary fitin = form.getItinerary(form.getItinerarylist().size(), routingType);
						
						fitin.setAirline(fMap.mapStringAndTrim(NetTracerField.AIRLINE,
								itin.getAirline()));
						fitin.setFlightnum(fMap.mapStringAndTrim(
								NetTracerField.FLIGHT, itin.getFlightnum()));
						fitin
								.setDepartdate(processDate(itin
										.getSchdeparttime()));
						fitin.setSchdeparttime(processDate(itin
								.getSchdeparttime()));
						fitin.setLegfrom(fMap.mapStringAndTrim(NetTracerField.AIRPORT,
								itin.getDepartureCity()));
						fitin.setLegto(fMap.mapStringAndTrim(NetTracerField.AIRPORT,
								itin.getArrivalCity()));
						fitin.setSchdeparttime(processDate(itin
								.getSchdeparttime()));
						fitin.setScharrivetime(processDate(itin
								.getScharrivetime()));
						noneAdded = false;
					}
				}
			}
		}
		if (noneAdded) {
			@SuppressWarnings("unused")
			com.bagnet.nettracer.tracing.db.Itinerary fitin = form.getItinerary(form.getItinerarylist().size(), routingType);
		}
	}

	private Date processDate(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		else return calendar.getTime();
	}

	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {

		NTIntegrationWrapper wrapper = new NTIntegrationWrapper();
		// ArrayList<String> list = StringUtils.splitOnWordBreak(comment, 45);
		// for (String str: list) {
		wrapper.writeCommentToPNR(recordLocator, comment);
		// }
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void populateIncidentFormInner(IncidentForm form,
			int itemtype, HttpServletRequest request) {
		
		Agent user = (Agent) request.getSession().getAttribute("user");
				
		form.setRecordlocator(booking.getPnr());
		form.setNumpassengers(booking.getPaxAffected());
		form.setNumbagchecked(booking.getNumberChecked());
		form.setAgent(user);
				
		form.setAgent(user);
		if (booking.getOsi() != null && PropertyBMO.isTrue(PropertyBMO.PROPERTY_NT_RES_OSI_ON)) {
			form.setOtherSystemInformation(booking.getOsi());
		}

		form.setItemlist(new ArrayList());
		
		if (booking.getClaimChecksArray() != null) {
			boolean claimCheckAdded = false;
			int itemIndex = 0;
			for (ClaimCheck cc : booking.getClaimChecksArray()) {
				if (cc.getTimeChecked() != null) {
					Long checkedTime = (cc.getTimeChecked().getTime().getTime()) / 3600000;
					Long nowtime = ((new Date()).getTime()) / 3600000;
					Long timeDifference = nowtime - checkedTime;
					if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
						
						// Add ClaimCheck
						if (!claimCheckAdded) {
							form.setClaimchecklist(new ArrayList());
							claimCheckAdded = true;
						}
						Incident_Claimcheck ic = form.getClaimcheck(form
								.getClaimchecklist().size());
						ic.setClaimchecknum(cc.getTagNumber());
						
						// Add Item
						Item theitem = form.getItem(itemIndex, itemtype);
						theitem.set_DATEFORMAT(user.getDateformat().getFormat());
						theitem.setCurrency_ID(user.getDefaultcurrency());
						theitem.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
						theitem.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
						theitem.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
						theitem.setBagnumber(itemIndex);
						theitem.setStatus(StatusBMO.getStatus(
								TracingConstants.ITEM_STATUS_OPEN));
						if (itemtype != TracingConstants.LOST_DELAY && cc.getTagNumber() != null) {
							theitem.setClaimchecknum(cc.getTagNumber());
						}
						theitem.setPosId(cc.getPosId());
						itemIndex++;
					}
				}
			}
		}
		
		
		//////
		
		Passenger[] paxArr = booking.getPassengersArray();
		for (int i = 0; i< paxArr.length; ++i) {
			Passenger pax = paxArr[i];
			
			
			
			com.bagnet.nettracer.tracing.db.Passenger formPax = form.getPassenger(i);
			formPax.setSalutation(pax.getSalutation());
			formPax.setFirstname(fMap.mapStringAndTrim(NetTracerField.PAX_FIRST_NAME, pax.getFirstname()));
			formPax.setLastname(fMap.mapStringAndTrim(NetTracerField.PAX_LAST_NAME, pax.getLastname()));
			formPax.setMiddlename(fMap.mapStringAndTrim(NetTracerField.PAX_MIDDLE_NAME, pax.getMiddlename()));			
			
			if (pax.getFfAirline() != null || pax.getFfNumber() != null || pax.getFfStatus() != null) {
				
				if (formPax.getMembership() == null) {
					formPax.setMembership(new AirlineMembership());
				}
				
				formPax.setSalutation(pax.getSalutation());
				
				AirlineMembership memShip = formPax.getMembership();
				memShip.setCompanycode_ID(fMap.mapStringAndTrim(NetTracerField.MEMBERSHIP_AIRLINE, pax.getFfAirline()));
				memShip.setMembershipnum(fMap.mapStringAndTrim(NetTracerField.MEMBERSHIP_NUM, pax.getFfNumber()));
				memShip.setMembershipstatus(fMap.mapStringAndTrim(NetTracerField.MEMBERSHIP_STATUS, pax.getFfStatus()));
			}
			
			
			Address add = pax.getAddresses();
									
			com.bagnet.nettracer.tracing.db.Address faddr = new com.bagnet.nettracer.tracing.db.Address();
			faddr.setPassenger(formPax);
			faddr.setAddress1(fMap.mapStringAndTrim(NetTracerField.ADDRESS1, add.getAddress1()));
			faddr.setAddress2(fMap.mapStringAndTrim(NetTracerField.ADDRESS2, add.getAddress2()));
			faddr.setCity(fMap.mapStringAndTrim(NetTracerField.ADDRESS2, add.getCity()));
			

			if (add.getCountry() == null){
				if(user.getCompanycode_ID().equals("WS") || !TracerUtils.isValidState(add.getState())) {
					faddr.setProvince(fMap.mapStringAndTrim(NetTracerField.ADDR_PROVINCE, add.getState()));
				} else {
					faddr.setState_ID(fMap.mapStringAndTrim(NetTracerField.ADDR_STATE, add.getState()));
				}
			} else {
				if(add.getCountry().equals("US")){
					faddr.setState_ID(fMap.mapStringAndTrim(NetTracerField.ADDR_STATE, add.getState()));
				} else {
					faddr.setProvince(fMap.mapStringAndTrim(NetTracerField.ADDR_PROVINCE, add.getState()));
				}
			}
			
			faddr.setZip(fMap.mapString(NetTracerField.ADDR_ZIP, add.getZip()));
			faddr.setCountrycode_ID(fMap.mapStringAndTrim(NetTracerField.ADDR_COUNTRY, add.getCountry()));
			
			faddr.setEmail(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_EMAIL, add.getEmailAddress()));
			faddr.setHomephone(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getHomePhone()));
			faddr.setMobile(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getMobilePhone()));
			faddr.setWorkphone(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getWorkPhone()));
			faddr.setAltphone(fMap.mapStringAndTrim(NetTracerField.OHD_ADDR_PHONE, add.getAltPhone()));
			
			
			HashSet<com.bagnet.nettracer.tracing.db.Address> faddresses = new HashSet<com.bagnet.nettracer.tracing.db.Address>();
			faddresses.add(faddr);
			formPax.setAddresses(faddresses);
		}
		
		form.setItinerarylist(new ArrayList());
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] itinArr = booking.getPassengerItineraryArray();
		int routingType = TracingConstants.PASSENGER_ROUTING;
		processIncItinerary(form, itinArr, routingType);
		
		aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary[] bagItinArr = booking.getBagItineraryArray();
		routingType = TracingConstants.BAGGAGE_ROUTING;
		processIncItinerary(form, bagItinArr, routingType);
		
	}
	
	/* (non-Javadoc)
	 * @see com.bagnet.clients.defaul.ReservationIntegrationImpl#getFlightInfo(java.lang.String, java.util.Calendar)
	 */
	@Override
	public ArrayList<BagDrop> getFlightInfo(String stationcode, Calendar date) {
		NTIntegrationWrapper wrapper = new NTIntegrationWrapper();
		return wrapper.getFlightInfo(stationcode, date);
	}
}
