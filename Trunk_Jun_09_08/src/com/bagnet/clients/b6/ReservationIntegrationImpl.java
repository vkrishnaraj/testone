package com.bagnet.clients.b6;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.jetblue_framework_contracts_baggage.BagStatus;

import com.bagnet.clients.defaul.DefaultFormFieldMapper;
import com.bagnet.clients.defaul.DefaultFormFieldMapper.NetTracerField;
import com.bagnet.nettracer.integrations.reservation.ReservationIntegration;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.jetblue.schemas._2008._03.framework.Address;
import com.jetblue.schemas._2008._03.framework.FlightKey;
import com.jetblue.schemas._2008._03.framework.Name;
import com.jetblue.schemas._2008._03.framework.Phone;
import com.jetblue.schemas._2008._03.framework.baggage.FlightBag;
import com.jetblue.schemas._2008._03.framework.baggage.PassengerDetail;
import com.jetblue.schemas._2008._03.framework.baggage.PassengerFlight;
import com.jetblue.schemas._2008._03.framework.baggage.ReservationDetail;

public class ReservationIntegrationImpl extends
		com.bagnet.clients.defaul.ReservationIntegrationImpl implements
		ReservationIntegration {
	private static final Logger logger = Logger.getLogger(ReservationIntegrationImpl.class);
	private ReservationDetail booking = null;
	
	private DefaultFormFieldMapper fMap = new DefaultFormFieldMapper();
	
	private final int HOURS_BACK_ITINERARY = 48;
	private final int HOURS_FORWARD_ITINERARY = 24;
	private String pnrContents;


	private ArrayList<String> getBooking(HttpServletRequest request,
			IncidentForm form) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
			
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
				booking = wrapper.getReservationDetail();
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
			JetBlueIntegrationWrapper wrapper = new JetBlueIntegrationWrapper();
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
				booking = wrapper.getReservationDetail();
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
		
		form.setPnr(booking.getRecordLocator());
		Agent user = (Agent) request.getSession().getAttribute("user");
		form.setHolding_company(user.getCompanycode_ID());
		form.setHolding_station(user.getStation().getStationcode());

		int bagIndex = 0;
		int itinCount = 0;
		int paxItinCount = 0;
		int bagItinCount = 0;
		Hashtable<String, Integer> bagTable = new Hashtable<String, Integer>();
		
		String email = booking.getContactEmail();
		Name contactName = booking.getContactName();
		String contactNameString = contactName.getFirstName() + " " + contactName.getLastName();
		Address address = booking.getContactAddress();
		
		String homePhone = null;
		String faxPhone = null;
		String otherPhone = null;
		if (booking.getContactPhoneNumbers().getPhoneArray() != null) {
			for (Phone phone: booking.getContactPhoneNumbers().getPhoneArray()) {
				String newNumber = fMap.mapString(NetTracerField.OHD_ADDR_PHONE, phone.getNumber());
				if (phone.getPhoneType().equals("HOME")) {
					homePhone = newNumber;
				} else if (phone.getPhoneType().equals("FAX")) {
					faxPhone = newNumber;
				} else if (phone.getPhoneType().equals("OTHER")) {
					otherPhone = newNumber;
				}
				
			}
		}
		
		// Pasengers and Bags
		if (booking.getPassengerDetails() != null) {
			PassengerDetail[] paxArray = booking.getPassengerDetails().getPassengerDetailArray();
			
			
			
			if (paxArray != null && paxArray.length > 0) {
				int paxNumber = 0;
				int baggageRoutingCount = 0;
				
				
				for (PassengerDetail pax: paxArray) {
					com.bagnet.nettracer.tracing.db.OHD_Passenger fPax = form.getPassenger(paxNumber);
					
					fPax.setFirstname(fMap.mapString(NetTracerField.PAX_FIRST_NAME, pax.getPassengerName().getFirstName()));
					fPax.setMiddlename(fMap.mapString(NetTracerField.PAX_MIDDLE_NAME, pax.getPassengerName().getMiddleName()));
					fPax.setLastname(fMap.mapString(NetTracerField.PAX_LAST_NAME, pax.getPassengerName().getLastName()));
					
					String fullName = pax.getPassengerName().getFirstName() + " " + pax.getPassengerName().getLastName();
					if (fullName.equalsIgnoreCase(contactNameString) && address != null) {
						
						com.bagnet.nettracer.tracing.db.OHD_Address fAddr = fPax.getAddress(0);
						
						if (email != null) {
							fAddr.setEmail(fMap.mapString(NetTracerField.OHD_ADDR_EMAIL, email));
						}
						
						fAddr.setAddress1(fMap.mapString(NetTracerField.ADDRESS1, address.getLine1()));

						String addressLine2 = "";
						if (address.getLine2() != null) {
							addressLine2 = address.getLine2();
						}
						
						if (address.getLine3() != null) {
							addressLine2 += " " + address.getLine3();
						}
						fAddr.setAddress2(fMap.mapString(NetTracerField.ADDRESS2, addressLine2.trim()));
						fAddr.setCity(fMap.mapString(NetTracerField.ADDR_CITY, address.getCity()));
						fAddr.setZip(fMap.mapString(NetTracerField.ADDR_ZIP, address.getPostalCode()));
						fAddr.setCountrycode_ID(fMap.mapString(NetTracerField.ADDR_COUNTRY, address.getCountry()));
						if (address.getCountry() == null || address.getCountry().equals("US")) {
							fAddr.setState_ID(fMap.mapString(NetTracerField.ADDR_STATE, address.getStateProvince()));
						} else {
							fAddr.setProvince(fMap.mapString(NetTracerField.ADDR_PROVINCE, address.getStateProvince()));
						}
						
						//phone fields already truncated above
						if (homePhone != null) {
							fAddr.setHomephone(homePhone);
						}
						
						if (faxPhone != null) {
							fAddr.setAltphone(faxPhone);
						}
						
						if (otherPhone != null) {
							fAddr.setMobile(otherPhone);
						}
					}
					

					if (pax.getPassengerFlightDetails() != null) {
						PassengerFlight[] flightArray = pax.getPassengerFlightDetails().getPassengerFlightArray();
					
						if (flightArray != null && flightArray.length > 0) {
							for (PassengerFlight flight: flightArray) {
								FlightKey key = flight.getFlightKey();
								Calendar flightDate = key.getFlightDate();
								
								Long deptime = (flightDate.getTime().getTime()) / 3600000;
								Long nowtime = ((new Date()).getTime()) / 3600000;
								Long timeDifference = nowtime - deptime;
								
								boolean useSegment = false;
								if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
									useSegment = true;
								}
								
								// If > -48 hours && < 24 hours
								if (useSegment) {
									
									FlightBag[] flightBags = flight.getBagNumbers().getFlightBagArray();
									if (flightBags != null && flightBags.length > 0)  {
										for (FlightBag bag: flightBags) {
											
											if (bag.getStatus().equals(BagStatus.CHECKED_IN) && !bagTable.containsKey(bag.getBagNumber())) {
												bagTable.put(bag.getBagNumber(), new Integer(0));
												
												bagIndex++;
												if (paxNumber==0 && baggageRoutingCount == 0) {
													
													OHD_Itinerary bagItin = form.getItinerary(itinCount);
													++itinCount;
													++bagItinCount;
													
													bagItin.setDepartdate(flightDate.getTime());
													bagItin.setAirline(key.getAirlineCode());
													bagItin.setFlightnum(key.getFlightNumber());
													bagItin.setLegfrom(key.getDepartureAirport());
													bagItin.setLegto(key.getArrivalAirport());
												}
											}
										}
									}
								}
							}
						} 
					}
					
					++paxNumber;
				}				
			}
			
			if (bagItinCount == 0) {
				OHD_Itinerary bagItin = form.getItinerary(itinCount);
				++itinCount;
				++bagItinCount;
			}
		}
	}
	


	public ArrayList<String> writeCommentToPNR(String comment,
			String recordLocator) {

		WriteThreadPool thread = new WriteThreadPool(recordLocator, comment);
		new Thread(thread).start();
		logger.info("Wrote to PNR: " + comment);
		return null;
		
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
		int itinCount = 0;
		int paxItinCount = 0;
		int bagItinCount = 0;
		Hashtable<String, Integer> bagTable = new Hashtable<String, Integer>();
		
		String email = booking.getContactEmail();
		Name contactName = booking.getContactName();
		String contactNameString = contactName.getFirstName() + " " + contactName.getLastName();
		Address address = booking.getContactAddress();
		
		String homePhone = null;
		String faxPhone = null;
		String otherPhone = null;
		if (booking.getContactPhoneNumbers().getPhoneArray() != null) {
			for (Phone phone: booking.getContactPhoneNumbers().getPhoneArray()) {
				String newNumber = fMap.mapString(NetTracerField.ADDR_PHONE, phone.getNumber());
				if (phone.getPhoneType().equals("HOME")) {
					homePhone = newNumber;
				} else if (phone.getPhoneType().equals("FAX")) {
					faxPhone = newNumber;
				} else if (phone.getPhoneType().equals("OTHER")) {
					otherPhone = newNumber;
				}
				
			}
		}
		
		

		// Pasengers and Bags
		if (booking.getPassengerDetails() != null) {
			PassengerDetail[] paxArray = booking.getPassengerDetails().getPassengerDetailArray();
			
			
			
			if (paxArray != null && paxArray.length > 0) {
				form.setNumpassengers(paxArray.length);
				int paxNumber = 0;
				int baggageRoutingCount = 0;
				
				
				for (PassengerDetail pax: paxArray) {
					com.bagnet.nettracer.tracing.db.Passenger fPax = form.getPassenger(paxNumber);
					
					fPax.setFirstname(fMap.mapString(NetTracerField.PAX_FIRST_NAME, pax.getPassengerName().getFirstName()));
					fPax.setMiddlename(fMap.mapString(NetTracerField.PAX_MIDDLE_NAME, pax.getPassengerName().getMiddleName()));
					fPax.setLastname(fMap.mapString(NetTracerField.PAX_LAST_NAME, pax.getPassengerName().getLastName()));
					
					String fullName = pax.getPassengerName().getFirstName() + " " + pax.getPassengerName().getLastName();
					if (fullName.equalsIgnoreCase(contactNameString) && address != null) {
						
						com.bagnet.nettracer.tracing.db.Address fAddr = fPax.getAddress(0);
						
						if (email != null) {
							fAddr.setEmail(fMap.mapString(NetTracerField.ADDR_EMAIL, email));
						}
						
						fAddr.setAddress1(fMap.mapString(NetTracerField.ADDRESS1, address.getLine1()));

						String addressLine2 = "";
						if (address.getLine2() != null) {
							addressLine2 = address.getLine2();
						}
						
						if (address.getLine3() != null) {
							addressLine2 += " " + address.getLine3();
						}
						fAddr.setAddress2(fMap.mapString(NetTracerField.ADDRESS2, addressLine2.trim()));
						fAddr.setCity(fMap.mapString(NetTracerField.ADDR_CITY, address.getCity()));
						fAddr.setZip(fMap.mapString(NetTracerField.ADDR_ZIP, address.getPostalCode()));
						fAddr.setCountrycode_ID(fMap.mapString(NetTracerField.ADDR_COUNTRY, address.getCountry()));
						if (address.getCountry() == null || address.getCountry().equals("US")) {
							fAddr.setState_ID(fMap.mapString(NetTracerField.ADDR_STATE, address.getStateProvince()));
						} else {
							fAddr.setProvince(fMap.mapString(NetTracerField.ADDR_PROVINCE, address.getStateProvince()));
						}
						
						//phone numbers already mapped above
						if (homePhone != null) {
							fAddr.setHomephone(homePhone);
						}
						
						if (faxPhone != null) {
							fAddr.setAltphone(faxPhone);
						}
						
						if (otherPhone != null) {
							fAddr.setMobile(otherPhone);
						}
					}
					

					if (pax.getPassengerFlightDetails() != null) {
						PassengerFlight[] flightArray = pax.getPassengerFlightDetails().getPassengerFlightArray();
					
						if (flightArray != null && flightArray.length > 0) {
							for (PassengerFlight flight: flightArray) {
								FlightKey key = flight.getFlightKey();
								Calendar flightDate = key.getFlightDate();
								
								Long deptime = (flightDate.getTime().getTime()) / 3600000;
								Long nowtime = ((new Date()).getTime()) / 3600000;
								Long timeDifference = nowtime - deptime;
								
								boolean useSegment = false;
								if (timeDifference <= HOURS_BACK_ITINERARY && timeDifference >= -HOURS_FORWARD_ITINERARY) {
									useSegment = true;
								}
								
								// If > -48 hours && < 24 hours
								if (useSegment) {
									
									if (paxNumber == 0) { 
										Itinerary itin = form.getItinerary(itinCount, TracingConstants.PASSENGER_ROUTING);
										++itinCount;
										++paxItinCount;
										
										itin.setDepartdate(flightDate.getTime());
										itin.setAirline(key.getAirlineCode());
										itin.setFlightnum(key.getFlightNumber());
										itin.setLegfrom(key.getDepartureAirport());
										itin.setLegto(key.getArrivalAirport());
									}
									
									
									FlightBag[] flightBags = flight.getBagNumbers().getFlightBagArray();
									if (flightBags != null && flightBags.length > 0)  {
										for (FlightBag bag: flightBags) {
											
											if (bag.getStatus().equals(BagStatus.CHECKED_IN) && !bagTable.containsKey(bag.getBagNumber())) {
												bagTable.put(bag.getBagNumber(), new Integer(0));
												Incident_Claimcheck ic = form.getClaimcheck(bagIndex);
												ic.setClaimchecknum(bag.getBagNumber());
							
												// bag info
												Item theitem = form.getItem(bagIndex, itemtype);
												if (itemtype != TracingConstants.LOST_DELAY
														&& bag.getBagNumber() != null && bag.getBagNumber().length() > 0) {
													theitem.setClaimchecknum(bag.getBagNumber());
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

												if (paxNumber==0 && baggageRoutingCount == 0) {
													

													Itinerary bagItin = form.getItinerary(itinCount, TracingConstants.BAGGAGE_ROUTING);
													++itinCount;
													++bagItinCount;
													
													bagItin.setDepartdate(flightDate.getTime());
													bagItin.setAirline(key.getAirlineCode());
													bagItin.setFlightnum(key.getFlightNumber());
													bagItin.setLegfrom(key.getDepartureAirport());
													bagItin.setLegto(key.getArrivalAirport());
												}
											}
										}
									}
								}
							}
						} 
					}
					
					++paxNumber;
				}				
			} else {
				form.setNumpassengers(1);
				form.getPassenger(0);
			}
			
			if (paxItinCount == 0) {
				Itinerary bagItin = form.getItinerary(itinCount, TracingConstants.PASSENGER_ROUTING);
				++itinCount;
				++bagItinCount;
			}
			
			if (bagItinCount == 0) {
				Itinerary bagItin = form.getItinerary(itinCount, TracingConstants.BAGGAGE_ROUTING);
				++itinCount;
				++bagItinCount;
			}
			
		}
	}
}
