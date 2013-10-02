package aero.nettracer.serviceprovider.ws_1_0.res.cebs;

import java.io.File;
import java.net.URL;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.log4j.Logger;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;
import aero.nettracer.utilities.ssl.AuthSSLProtocolSocketFactory;

import com.swacorp.services.btws.v1.GetPNRRequestDocument;
import com.swacorp.services.btws.v1.GetPNRRequestDocument.GetPNRRequest;
import com.swacorp.services.btws.v1.GetPNRResponseDocument;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.AddressList.Address;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.FlightList.Flight;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.NameList.Name;
import com.swacorp.services.btws.v1.GetPNRResponseDocument.GetPNRResponse.Passenger.PhoneList.Phone;
import com.swacorp.services.btws.wsdl.v1.BTWSStub;

public class Reservation implements ReservationInterface {

	private static Logger logger = Logger.getLogger(Reservation.class);
	
	private static final String PHONE_TYPE_HOME = "P_HOME";
	private static final String PHONE_TYPE_WORK = "P_WORK";
	private static final String PHONE_TYPE_MOBILE = "P_MOBILE";
	
	@Override
	public EnplanementResponse getEnplanements(User user) throws UnexpectedException {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr, String bagTag) throws UnexpectedException {
		return null;
	}

	@Override
	public RemarkResponse writeRemark(User user, String pnr, String remark) throws UnexpectedException {
		return null;
	}
	
	/**
	 * This method creates a custom ProtocolSocketFactory using the truststore and keystore locations passed in. It will then
	 * set this custom protocol on the stubs service client for use by just this stub. This is needed for 2 way SSL.
	 */
	private void prepareTwoWaySSL(BTWSStub stub, String truststoreLoc, String keystoreLoc, String trustpass, String keypass) {
		try {
			// Preparing the stores
			URL truststoreUrl = new File(truststoreLoc).toURI().toURL();
			URL keystoreUrl = new File(keystoreLoc).toURI().toURL();
			// Creating the custom factory
			ProtocolSocketFactory socketFactory = new AuthSSLProtocolSocketFactory(keystoreUrl, keypass, truststoreUrl, trustpass);
			// Creating a custom https protocol with the factory
			Protocol twoWaySSLProtocolHandler = new Protocol("https", socketFactory, 443);
			// Setting the protocol on the stub
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER, twoWaySSLProtocolHandler);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

	@Override
	public ReservationResponse getReservationData(User user, String pnr, String bagTag) throws UnexpectedException {
		
		// DEFINE RESPONSE
		ReservationResponse response = ReservationResponse.Factory.newInstance();

		try {
			logger.debug("CEBS Request generating");
			
			// Grabbing all the user profile parameters needed for this call.
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			String truststore = user.getProfile().getParameters().get(ParameterType.TRUSTSTORE);
			String keystore = user.getProfile().getParameters().get(ParameterType.KEYSTORE);
			String trustpass = user.getProfile().getParameters().get(ParameterType.TRUSTPASS);
			String keypass = user.getProfile().getParameters().get(ParameterType.KEYPASS);
			
			// STUB & 2 WAY SSL
			BTWSStub stub = new BTWSStub(endpoint);
			prepareTwoWaySSL(stub, truststore, keystore, trustpass, keypass);

			// CREATE OUTGOING DOCUMENT
			GetPNRRequestDocument bi = GetPNRRequestDocument.Factory.newInstance();
			GetPNRRequest bi2 = bi.addNewGetPNRRequest();
			bi2.setPNR(pnr);
			
			// MAKE REQUEST WITH STUB
			GetPNRResponseDocument bookingRes = stub.getPNR(bi);
			
			// OUTPUT RESPONSE
			logger.info("CEBS Response: " + bookingRes);
			
			// CHECK FOR VALID RESPONSE
			if (bookingRes!= null && bookingRes.getGetPNRResponse() != null) {
				
				// ADD RESERVATION TO THE NT RESPONSE
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response.addNewReservation();

				int bagsChecked = 0;
				int paxAffected = 0;

				GetPNRResponse booking = bookingRes.getGetPNRResponse();
				
				if (booking.getPassenger() != null) {
					Passenger pass = booking.getPassenger();
					
					// PREPARE ADDRESS LIST
					Address[] addresses = null;
					if (pass.getAddressList() != null && pass.getAddressList().getAddressArray() != null) {
						addresses = pass.getAddressList().getAddressArray();
					}
					
					// PREPARE PHONES
					Phone[] phones = null;
					if (pass.getPhoneList() != null && pass.getPhoneList().getPhoneArray() != null) {
						phones = pass.getPhoneList().getPhoneArray();
					}
					
					// PROCESS NAME LIST
					if (pass.getNameList() != null && pass.getNameList().getNameArray() != null) {
						int addrIndex = 0;
						int phoneIndex = 0;
						for (Name name : pass.getNameList().getNameArray()) {

							aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger p = res.addNewPassengers();
							
							if (name.getFirstName() != null) {
								p.setFirstname(name.getFirstName());
							}
							if (name.getLastName() != null) {
								p.setLastname(name.getLastName());
							}
							if (name.getRapidReward() != null) {
								p.setFfNumber(name.getRapidReward());
							}
							aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address a = p.addNewAddresses();
							
							if (addresses != null && addresses.length > addrIndex) {
								Address address = addresses[addrIndex];
								
								if (address.getAddressLine1() != null) {
									a.setAddress1(address.getAddressLine1());
								}
								if (address.getAddressLine2() != null) {
									a.setAddress2(address.getAddressLine2());
								}
								if (address.getCity() != null) {
									a.setCity(address.getCity());
								}
								if (address.getState() != null) {
									a.setState(address.getState());
								}
								if (address.getProvince() != null) {
									a.setProvince(address.getProvince());
								}
								if (address.getPostalCode() != null) {
									a.setZip(address.getPostalCode());
								}
								if (address.getCountryCode() != null) {
									a.setCountry(address.getCountryCode());
								}
								if (address.getEmail() != null) {
									a.setEmailAddress(address.getEmail());
								}
								addrIndex++;
							}
							
							if (phones != null && phones.length > phoneIndex) {
								Phone phone = phones[phoneIndex];
								translatePhone(phone, a);
								phoneIndex++;	
							}
							paxAffected++;
						}
					}
					
					// PROCESS FLIGHT LIST
					if (pass.getFlightList() != null && pass.getFlightList().getFlightArray() != null) {
						for (Flight flight : pass.getFlightList().getFlightArray()) {
							Itinerary itin = res.addNewPassengerItinerary();
							mapSegmentsToItinerary(flight, itin);
							Itinerary bagItin = res.addNewBagItinerary();
							mapSegmentsToItinerary(flight, bagItin);
						}
					}
				}
				
				if (booking.getPNR() != null && booking.getPNR().length() > 0) {
					res.setPnr(booking.getPNR());
				}
				
				res.setNumberChecked(bagsChecked);
				res.setPaxAffected(paxAffected);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
		} 
		return response;
	}
	
	/**
	 * Inserts the information from the BTWS Phone object into the proper place on the Address object.
	 */
	private void translatePhone(Phone phone, aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address a) {
		String phoneStr = combineNumber(phone);
		if (PHONE_TYPE_HOME.equals(phone.getPhoneType())) {
			a.setHomePhone(phoneStr);
		} else if (PHONE_TYPE_WORK.equals(phone.getPhoneType())) {
			a.setWorkPhone(phoneStr);
		} else if (PHONE_TYPE_MOBILE.equals(phone.getPhoneType())) {
			a.setMobilePhone(phoneStr);
		} else {
			a.setAltPhone(phoneStr);
		}
	}
	
	/**
	 * Converts the information in the BTWS Phone object into a single readable phone string.
	 */
	private String combineNumber(Phone phone) {
		String toReturn = "";
		if (phone.getCountryCode() != null) {
			toReturn = phone.getCountryCode() + " ";
		}
		if (phone.getAreaCode() != null) {
			toReturn += "(" + phone.getAreaCode() + ") ";
		}
		boolean exchange = false;
		if (phone.getExchangeNumber() != null) {
			toReturn += phone.getExchangeNumber();
			exchange = true;
		}
		if (phone.getLineNumber() != null) {
			toReturn += (exchange ? "-" : "") + phone.getLineNumber();
		}
		if (phone.getExtensionNumber() != null) {
			toReturn += " ext:" + phone.getExtensionNumber();
		}
		return toReturn;
	}

	/**
	 * Inserts the information in a BTWS Flight object into the proper places in an NT Itinerary.
	 */
	private void mapSegmentsToItinerary(Flight flight, Itinerary i) {
		
		// FLIGHT INFO
		if (flight.getAirline() != null) {
				i.setAirline(flight.getAirline());
			}
		if (flight.getFlightNum() != null) {
				i.setFlightnum(flight.getFlightNum());
		}
		
		// STATIONS
		if (flight.getDestination() != null) {
			i.setArrivalCity(flight.getDestination());
		}
		if (flight.getOrigin() != null) {
			i.setDepartureCity(flight.getOrigin());
		}
		
		// DATE
		if (flight.getDepartureDate() != null) {
			i.setSchdeparttime(flight.getDepartureDate());
		}
	}
}