package aero.nettracer.serviceprovider.ws_1_0.res.radixx;

import java.util.HashMap;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.radixx_connectpoint_request.ArrayOfCarrierCode;
import org.datacontract.schemas._2004._07.radixx_connectpoint_request.CarrierCode;
import org.datacontract.schemas._2004._07.radixx_connectpoint_request.ReservationInfo;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_request.RetrievePNRActionTypes;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.Airline;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.AirlinePerson;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.ContactInfo;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.Customer;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.LogicalFlight;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.PhysicalFlight;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.ReservationContact;
import org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_response.ViewPNR;
import org.datacontract.schemas._2004._07.radixx_connectpoint_security_response.ViewSecurityToken;
import org.tempuri.ConnectPoint_ReservationStub;
import org.tempuri.ConnectPoint_SecurityStub;
import org.tempuri.RetrievePNRDocument.RetrievePNR;
import org.tempuri.RetrievePNRResponseDocument;
import org.tempuri.RetrievePNRResponseDocument.RetrievePNRResponse;
import org.tempuri.RetrieveSecurityTokenDocument;
import org.tempuri.RetrieveSecurityTokenDocument.RetrieveSecurityToken;
import org.tempuri.RetrieveSecurityTokenResponseDocument;
import org.tempuri.RetrieveSecurityTokenResponseDocument.RetrieveSecurityTokenResponse;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.radixx.wsradixx.radixxbooking.AddCommentToReservationDocument;
import com.radixx.wsradixx.radixxbooking.AddCommentToReservationDocument.AddCommentToReservation;
import com.radixx.wsradixx.radixxbooking.RadixxBookingStub;
import com.radixx.wsradixx.radixxbooking.ResSaveDocument;
import com.radixx.wsradixx.radixxbooking.ResSaveDocument.ResSave;

public class Reservation implements ReservationInterface {

	private static Logger logger = Logger.getLogger(Reservation.class);

	private static final String AGENT_NAME = "NetTracer_1D_Uat";
	private static final String PASSWORD = "netTr@cer0830";
	private static final String SERIES_NUMBER = "299";
	
	@Override
	public EnplanementResponse getEnplanements(User user) throws UnexpectedException {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr, String bagTag) throws UnexpectedException {
		return null;
	}
	
	public String getSecureToken(User user) throws UnexpectedException {

		try {
		
			String endpoint = user.getProfile().getParameters().get(ParameterType.RES_SESSION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			ConnectPoint_SecurityStub stub = new ConnectPoint_SecurityStub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));

			// CREATE OUTGOING DOCUMENT
			String resUser = user.getProfile().getParameters().get(ParameterType.RESERVATION_USER);
			String pass = user.getProfile().getParameters().get(ParameterType.RESERVATION_PASS);
			RetrieveSecurityTokenDocument bi = RetrieveSecurityTokenDocument.Factory.newInstance();
			RetrieveSecurityToken bi2 = bi.addNewRetrieveSecurityToken();
			org.datacontract.schemas._2004._07.radixx_connectpoint_security_request.RetrieveSecurityToken bi3 = bi2.addNewRetrieveSecurityTokenRequest();
			ArrayOfCarrierCode codes = bi3.addNewCarrierCodes();
			CarrierCode code = codes.addNewCarrierCode();
			code.setAccessibleCarrierCode(user.getProfile().getAirline());
			bi3.setLogonID(resUser != null ? resUser: AGENT_NAME);
			bi3.setPassword(pass != null ? pass : PASSWORD);
			
			// MAKE REQUEST WITH STUB
			RetrieveSecurityTokenResponseDocument doc = stub.retrieveSecurityToken(bi);
			RetrieveSecurityTokenResponse res = doc.getRetrieveSecurityTokenResponse();
			ViewSecurityToken token = res.getRetrieveSecurityTokenResult();
			String secureToken = token.getSecurityToken();
			
			return secureToken;
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} 
		
	}
	
	private RetrievePNRResponseDocument getBooking(User user, String token, String pnr) {
		RetrievePNRResponseDocument doc = RetrievePNRResponseDocument.Factory.newInstance();
		try {
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			ConnectPoint_ReservationStub stub = new ConnectPoint_ReservationStub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));
	
			// CREATE OUTGOING DOCUMENT
			org.tempuri.RetrievePNRDocument bi = org.tempuri.RetrievePNRDocument.Factory.newInstance();
			RetrievePNR bi2 = bi.addNewRetrievePNR();
			org.datacontract.schemas._2004._07.radixx_connectpoint_reservation_request.RetrievePNR bi3 = bi2.addNewRetrievePnrRequest();
			ArrayOfCarrierCode codes = bi3.addNewCarrierCodes();
			CarrierCode code = codes.addNewCarrierCode();
			code.setAccessibleCarrierCode(user.getProfile().getAirline());
			bi3.setSecurityGUID(token);
			bi3.setActionType(RetrievePNRActionTypes.GET_RESERVATION);
			ReservationInfo info = bi3.addNewReservationInfo();
			info.setSeriesNumber(SERIES_NUMBER);
			info.setConfirmationNumber(pnr);
			
			// MAKE REQUEST WITH STUB
			doc = stub.retrievePNR(bi);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public ReservationResponse getReservationData(User user, String pnr, String bagTag) throws UnexpectedException {
		// DEFINE RESPONSE
		ReservationResponse response = ReservationResponse.Factory
		.newInstance();
		

		try {
			String token = getSecureToken(user);
			if (token == null) {
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
				return response;
			}
			
			RetrievePNRResponseDocument doc = getBooking(user, token, pnr);

			// OUTPUT RESPONSE
			logger.info("Radixx Response: " + doc);
			
			// CHECK FOR VALID RESPONSE
			if (doc != null && doc.getRetrievePNRResponse() != null) {
				// ADD RESERVATION TO THE RESPONSE
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response
				.addNewReservation();

				// DO WORK
				int bagsChecked = 0;

				RetrievePNRResponse bookingResponse = doc.getRetrievePNRResponse();
				ViewPNR booking = bookingResponse.getRetrievePNRResult();
				
				HashMap<Integer, Passenger> passMap = new HashMap<Integer, Passenger>();
				
				if (booking.getReservationContacts() != null && booking.getReservationContacts().getReservationContactArray() != null) {
					for (ReservationContact pax: booking.getReservationContacts().getReservationContactArray()) {
						
						if (!passMap.containsKey(pax.getPersonOrgID())) {
						
							Passenger p = res.addNewPassengers();
							Address add = p.addNewAddresses();
							
							if (pax.getFirstName() != null) {
								p.setFirstname(pax.getFirstName());
							}
							
							if (pax.getLastName() != null) {
								p.setLastname(pax.getLastName());
							}
							
							if (pax.getMiddleName() != null) {
								p.setMiddlename(pax.getMiddleName());
							}
							
							if (pax.getAddress() != null) {
								add.setAddress1(pax.getAddress());
							}
							
							if (pax.getAddress2() != null) {
								add.setAddress2(pax.getAddress2());
							}
							
							if (pax.getCity() != null) {
								add.setCity(pax.getCity());
							}
							
							if (pax.getState() != null) {
								if (pax.getCountry() != null) {
									add.setCountry(pax.getCountry());
									if ("US".equals(pax.getCountry())) {
										add.setState(pax.getState());
									} else {
										add.setProvince(pax.getState());
									}
								} else {
									add.setProvince(pax.getState());
								}
							}
							
							if (pax.getPostal() != null) {
								add.setZip(pax.getPostal());
							}
							
							passMap.put(pax.getPersonOrgID(), p);
							
							if (pax.getContactInfos() != null && pax.getContactInfos().getContactInfoArray() != null) {
								mapContactInfo(pax.getContactInfos().getContactInfoArray(), passMap);
							}
						}
					}
				}
				
				if (booking.getContactInfos() != null && booking.getContactInfos().getContactInfoArray() != null) {
					mapContactInfo(booking.getContactInfos().getContactInfoArray(), passMap);
				}				

				if (booking.getAirlines() != null && booking.getAirlines().getAirlineArray() != null) {
					for (Airline airline: booking.getAirlines().getAirlineArray()) {
						if (airline.getLogicalFlight() != null && airline.getLogicalFlight().getLogicalFlightArray() != null) {
							for (LogicalFlight flight : airline.getLogicalFlight().getLogicalFlightArray()) {
								if (flight.getPhysicalFlights() != null && flight.getPhysicalFlights().getPhysicalFlightArray() != null) {
									for (PhysicalFlight seg : flight.getPhysicalFlights().getPhysicalFlightArray()) {
										Itinerary itin = res.addNewPassengerItinerary();
										mapSegmentsToItinerary(seg, itin);
										Itinerary bagItin = res.addNewBagItinerary();
										mapSegmentsToItinerary(seg, bagItin);
										if (seg.getCustomers() != null && seg.getCustomers().getCustomerArray() != null) {
											for (Customer cust : seg.getCustomers().getCustomerArray()) {
												if (cust.getAirlinePersons() != null && cust.getAirlinePersons().getAirlinePersonArray() != null) {
													for (AirlinePerson pax : cust.getAirlinePersons().getAirlinePersonArray()) {
														
														if (passMap.containsKey(pax.getPersonOrgID())) {
															Passenger p = passMap.get(pax.getPersonOrgID());

															if (pax.getFFNum() != null) {
																p.setFfNumber(pax.getFFNum());
															}
															
														} else {
														
															Passenger p = res.addNewPassengers();
															Address add = p.addNewAddresses();
															
															if (pax.getFFNum() != null) {
																p.setFfNumber(pax.getFFNum());
															}
															
															if (pax.getFirstName() != null) {
																p.setFirstname(pax.getFirstName());
															}
															
															if (pax.getLastName() != null) {
																p.setLastname(pax.getLastName());
															}
															
															if (pax.getMiddleName() != null) {
																p.setMiddlename(pax.getMiddleName());
															}
															
															if (pax.getAddress() != null) {
																add.setAddress1(pax.getAddress());
															}
															
															if (pax.getAddress2() != null) {
																add.setAddress2(pax.getAddress2());
															}
															
															if (pax.getCity() != null) {
																add.setCity(pax.getCity());
															}
															
															if (pax.getState() != null) {
																if (pax.getCountry() != null) {
																	add.setCountry(pax.getCountry());
																	if ("US".equals(pax.getCountry())) {
																		add.setState(pax.getState());
																	} else {
																		add.setProvince(pax.getState());
																	}
																} else {
																	add.setProvince(pax.getState());
																}
															}
															
															if (pax.getPostal() != null) {
																add.setZip(pax.getPostal());
															}
															
															passMap.put(pax.getPersonOrgID(), p);
															
														}
														
													} // End AirlinePerson loop
												}
											} // End Customer loop
										}
									} // END Physical Flight loop
								}
							} // END Logical Flight loop
						}
					} // END Airline loop
				}
				
				if (booking.getConfirmationNumber() != null && booking.getConfirmationNumber().length() > 0) {
					res.setPnr(booking.getConfirmationNumber());
				}
				
				res.setNumberChecked(bagsChecked);
				res.setPaxAffected(passMap.size());
			}
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		} 
		return response;
	}

	private void mapSegmentsToItinerary(PhysicalFlight seg, Itinerary i) {
		if (seg.getArrivaltime() != null) {
			i.setActarrivetime(seg.getArrivaltime());
			i.setScharrivetime(seg.getArrivaltime());
		}
		
		if (seg.getDepartureTime() != null) {
			i.setActdeparttime(seg.getDepartureTime());
			i.setSchdeparttime(seg.getDepartureTime());
		}
		
		if (seg.getCarrierCode() != null)
			i.setAirline(seg.getCarrierCode());
		
		if (seg.getOrigin() != null)
			i.setArrivalCity(seg.getOrigin());
		
		if (seg.getDestination() != null)
			i.setDepartureCity(seg.getDestination());
		
		if (seg.getFlightNumber() != null)
			i.setFlightnum(seg.getFlightNumber());
	}
	
	private void mapContactInfo(ContactInfo[] infos, HashMap<Integer, Passenger> passMap) {
		for (ContactInfo info : infos) {
			if (passMap.containsKey(info.getPersonOrgID())) {
				Passenger p = passMap.get(info.getPersonOrgID());
				Address add = p.getAddresses();
				if (info.getPhoneNumber() != null) {
					String phone = info.getPhoneNumber();
					if (info.getAreaCode() != null) {
						phone = "(" + info.getAreaCode() + ") " + phone;
					}
					if (info.getCountryCode() != null) {
						phone = info.getCountryCode() + " " + phone; 
					}
					if (info.getExtension() != null) {
						phone = phone + " ext:" + info.getExtension();
					}
					add.setAltPhone(phone);
				}

				if (info.getContactField() != null) {
					switch (info.getContactType()) {
					case 0:
						add.setHomePhone(info.getContactField());
						break;
					case 1:
						add.setMobilePhone(info.getContactField());
						break;
					case 2:
						add.setWorkPhone(info.getContactField());
						break;
					case 4:
						add.setEmailAddress(info.getContactField());
						break;
					default:
						break;
					}
				}
			}
		}
	}

	// TODO: ABOVE HERE
	@Override
	public RemarkResponse writeRemark(User user, String pnr, String remark) throws UnexpectedException {
		RemarkResponse response = RemarkResponse.Factory.newInstance();
		try {
			String token = getSecureToken(user);
			if (token == null) {
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
				return response;
			}
			
			RetrievePNRResponseDocument doc = getBooking(user, token, pnr);
			
			String endpoint = user.getProfile().getParameters().get(ParameterType.RES_RADIXX_ENDPOINT_2);

			RadixxBookingStub stub = new RadixxBookingStub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));
			AddCommentToReservationDocument bi = AddCommentToReservationDocument.Factory.newInstance();
			AddCommentToReservation bi2 = bi.addNewAddCommentToReservation();
			bi2.setComment(remark);
			bi2.setConfirmationNum(pnr);
			bi2.setSecurityGUID(token);
			bi2.setSeriesNum(SERIES_NUMBER);
			stub.addCommentToReservation(bi);
			
			ResSaveDocument savDoc = ResSaveDocument.Factory.newInstance();
			ResSave save = savDoc.addNewResSave();
			save.setSecurityGUID(token);
			save.setConfirmationNum(pnr);
			save.setSeriesNum(SERIES_NUMBER);
			stub.resSave(savDoc);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return response;
	}

}
