package aero.nettracer.serviceprovider.ws_1_0.res.navitaire;

import java.util.List;

import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.engine.Handler;
import org.apache.axis2.engine.Phase;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;

import aero.nettracer.serviceprovider.common.ServiceConstants;
import aero.nettracer.serviceprovider.common.db.ParameterType;
import aero.nettracer.serviceprovider.common.db.User;
import aero.nettracer.serviceprovider.common.exceptions.UnexpectedException;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Address;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.ClaimCheck;
import aero.nettracer.serviceprovider.ws_1_0.common.xsd.Itinerary;
import aero.nettracer.serviceprovider.ws_1_0.res.ReservationInterface;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.EnplanementResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.OsiResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.RemarkResponse;
import aero.nettracer.serviceprovider.ws_1_0.response.xsd.ReservationResponse;

import com.navitaire.schemas.webservices.BookingManagerStub;
import com.navitaire.schemas.webservices.ContractVersionDocument;
import com.navitaire.schemas.webservices.LogonResponseDocument;
import com.navitaire.schemas.webservices.LogonResponseDocument.LogonResponse;
import com.navitaire.schemas.webservices.SessionManagerStub;
import com.navitaire.schemas.webservices.SignatureDocument;
import com.navitaire.schemas.webservices.datacontracts.booking.AddBookingCommentsRequestData;
import com.navitaire.schemas.webservices.datacontracts.booking.ArrayOfBookingComment;
import com.navitaire.schemas.webservices.datacontracts.booking.Booking;
import com.navitaire.schemas.webservices.datacontracts.booking.BookingComment;
import com.navitaire.schemas.webservices.datacontracts.booking.BookingContact;
import com.navitaire.schemas.webservices.datacontracts.booking.BookingName;
import com.navitaire.schemas.webservices.datacontracts.booking.GetBookingRequestData;
import com.navitaire.schemas.webservices.datacontracts.booking.GetByRecordLocator;
import com.navitaire.schemas.webservices.datacontracts.booking.Journey;
import com.navitaire.schemas.webservices.datacontracts.booking.Passenger;
import com.navitaire.schemas.webservices.datacontracts.booking.PassengerBag;
import com.navitaire.schemas.webservices.datacontracts.booking.PassengerProgram;
import com.navitaire.schemas.webservices.datacontracts.booking.Segment;
import com.navitaire.schemas.webservices.datacontracts.common.FlightDesignator;
import com.navitaire.schemas.webservices.datacontracts.common.enumerations.CommentType;
import com.navitaire.schemas.webservices.datacontracts.common.enumerations.GetBookingBy;
import com.navitaire.schemas.webservices.datacontracts.common.enumerations.MessageState;
import com.navitaire.schemas.webservices.datacontracts.session.LogonRequestData;
import com.navitaire.schemas.webservices.servicecontracts.bookingservice.AddBookingCommentsRequestDocument;
import com.navitaire.schemas.webservices.servicecontracts.bookingservice.AddBookingCommentsRequestDocument.AddBookingCommentsRequest;
import com.navitaire.schemas.webservices.servicecontracts.bookingservice.GetBookingRequestDocument;
import com.navitaire.schemas.webservices.servicecontracts.bookingservice.GetBookingRequestDocument.GetBookingRequest;
import com.navitaire.schemas.webservices.servicecontracts.bookingservice.GetBookingResponseDocument;
import com.navitaire.schemas.webservices.servicecontracts.bookingservice.GetBookingResponseDocument.GetBookingResponse;
import com.navitaire.schemas.webservices.servicecontracts.sessionservice.LogonRequestDocument;
import com.navitaire.schemas.webservices.servicecontracts.sessionservice.LogonRequestDocument.LogonRequest;
import com.navitaire.schemas.webservices.servicecontracts.sessionservice.LogoutRequestDocument;
import com.navitaire.schemas.webservices.servicecontracts.sessionservice.LogoutRequestDocument.LogoutRequest;

public class Reservation implements ReservationInterface {

	private static Logger logger = Logger.getLogger(Reservation.class);
	
	private static final String AGENT_NAME = "nettracer_integration";
	private static final String PASSWORD = "Nettracer13*";
	private static final String DOMAIN_CODE = "EXT";
	private static final int CONTRACT_VERSION = 344;
	private static final String RAMPART = "rampart";
	private static final String POLICY_XML = "NAVITAIRE_policy.xml";
	private static final String POST_DISPATCH_SECURITY_VERIFICATION_HANDLER = "Post dispatch security verification handler";
	private static final String DISPATCH = "Dispatch";
	private static final String SECURITY = "Security";
	private static final String APACHE_RAMPART_INFLOW_HANDLER = "Apache Rampart inflow handler";
	
	private int contract_version = 0;
	
	
	
	public int getContract_version(User user) {
		if (contract_version == 0) {
			String temp = user.getProfile().getParameters().get(ParameterType.CONTRACT_VERSION);
			if (temp != null) {
				contract_version = Integer.parseInt(temp);
			} else {
				contract_version = CONTRACT_VERSION;
			}
		}
		return contract_version;
	}

	@Override
	public EnplanementResponse getEnplanements(User user) throws UnexpectedException {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr, String bagTag) throws UnexpectedException {
		return null;
	}

	private static void configureClient(org.apache.axis2.client.Stub stub) throws AxisFault {
		
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
		stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));

		ServiceClient client = stub._getServiceClient();

		client.engageModule(RAMPART);
		try {
			String policy = "/" + POLICY_XML;
			logger.debug("Policy: " + policy);
			client.getAxisService().getPolicySubject().attachPolicy(loadPolicy(policy));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		removeFaultPhases(client);
		removePhases(client);
	}

	private static Policy loadPolicy(String xmlPath) throws Exception {
		StAXOMBuilder builder = new StAXOMBuilder(Reservation.class.getResourceAsStream(xmlPath));
		return PolicyEngine.getPolicy(builder.getDocumentElement());
	}

	private static void removeFaultPhases(ServiceClient client) {
		List<Phase> phases = client.getAxisConfiguration().getInFaultFlowPhases();

		for (Phase p : phases) {
			HandlerDescription removeThis = null;
			if (p.getPhaseName().equals(SECURITY) || p.getPhaseName().equals(DISPATCH)) {
				List<Handler> l = p.getHandlers();
				for (Handler h : l) {
					if (h.getName().equals(APACHE_RAMPART_INFLOW_HANDLER) || h.getName().equals(POST_DISPATCH_SECURITY_VERIFICATION_HANDLER)) {
						removeThis = h.getHandlerDesc();
					}
				}
			}

			if (removeThis != null) {
				p.removeHandler(removeThis);
			}
		}
	}

	private static void removePhases(ServiceClient client) {
		List<Phase> phases = client.getAxisConfiguration().getInFlowPhases();

		for (Phase p : phases) {
			HandlerDescription removeThis = null;
			if (p.getPhaseName().equals(SECURITY) || p.getPhaseName().equals(DISPATCH)) {
				List<Handler> l = p.getHandlers();
				for (Handler h : l) {

					if (h.getName().equals(APACHE_RAMPART_INFLOW_HANDLER) || h.getName().equals(POST_DISPATCH_SECURITY_VERIFICATION_HANDLER)) {
						removeThis = h.getHandlerDesc();
					}
				}
			}

			if (removeThis != null) {
				p.removeHandler(removeThis);
			}
		}
	}
	
	public String logon(User user) throws UnexpectedException {

		try {
		
			String endpoint = user.getProfile().getParameters().get(ParameterType.RES_SESSION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			SessionManagerStub stub = new SessionManagerStub(endpoint);
			configureClient(stub);

			// CREATE OUTGOING DOCUMENT
			String resUser = user.getProfile().getParameters().get(ParameterType.RESERVATION_USER);
			String pass = user.getProfile().getParameters().get(ParameterType.RESERVATION_PASS);
			String domain = user.getProfile().getParameters().get(ParameterType.DOMAIN_CODE);
			LogonRequestDocument bi = LogonRequestDocument.Factory.newInstance();
			LogonRequest bi2 = bi.addNewLogonRequest();
			LogonRequestData bi3 = bi2.addNewLogonRequestData();
			bi3.setAgentName(resUser != null ? resUser: AGENT_NAME);
			bi3.setPassword(pass != null ? pass : PASSWORD);
			bi3.setDomainCode(domain != null ? domain : DOMAIN_CODE);
			
			// CREATE CONTRACT VERSION
			ContractVersionDocument cv = ContractVersionDocument.Factory.newInstance();
			cv.setContractVersion(getContract_version(user));
			
			// MAKE REQUEST WITH STUB
			LogonResponseDocument docRes = stub.logon(bi, cv);
			LogonResponse sessionRes = docRes.getLogonResponse();
			return sessionRes.getSignature();
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} 		
	}
	
	public boolean logout(User user, String signature) throws UnexpectedException {

		try {
		
			String endpoint = user.getProfile().getParameters().get(ParameterType.RES_SESSION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			SessionManagerStub stub = new SessionManagerStub(endpoint);
			configureClient(stub);

			// CREATE OUTGOING DOCUMENT
			LogoutRequestDocument bi = LogoutRequestDocument.Factory.newInstance();
			bi.addNewLogoutRequest();
			
			// CREATE CONTRACT VERSION
			ContractVersionDocument cv = ContractVersionDocument.Factory.newInstance();
			cv.setContractVersion(getContract_version(user));
			
			// CREATE SIGNATURE
			SignatureDocument sig = SignatureDocument.Factory.newInstance();
			sig.setSignature(signature);
			
			// MAKE REQUEST WITH STUB
			stub.logout(bi, cv, sig);
			return true;
			
		} catch (Exception e) {

			e.printStackTrace();
			return false;
		} 		
	}

	@Override
	public ReservationResponse getReservationData(User user, String pnr, String bagTag) throws UnexpectedException {
		// DEFINE RESPONSE
		ReservationResponse response = ReservationResponse.Factory
		.newInstance();
		

		try {
			String signature = logon(user);
			
			if (signature == null) {
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
				return response;
			}
			
			// CREATE CONTRACT VERSION
			ContractVersionDocument cv = ContractVersionDocument.Factory.newInstance();
			cv.setContractVersion(getContract_version(user));
			
			// CREATE SIGNATURE
			SignatureDocument sig = SignatureDocument.Factory.newInstance();
			sig.setSignature(signature);
			
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			BookingManagerStub stub = new BookingManagerStub(endpoint);
			configureClient(stub);

			// CREATE OUTGOING DOCUMENT
			GetBookingRequestDocument bi = GetBookingRequestDocument.Factory.newInstance();
			GetBookingRequest bi2 = bi.addNewGetBookingRequest();
			GetBookingRequestData bi3 = bi2.addNewGetBookingReqData();
			bi3.setGetBookingBy(GetBookingBy.RECORD_LOCATOR);
			GetByRecordLocator bi4 = bi3.addNewGetByRecordLocator();
			bi4.setRecordLocator(pnr);
			
			// MAKE REQUEST WITH STUB
			GetBookingResponseDocument bookingRes = stub.getBooking(bi, cv, sig);
			
			// OUTPUT RESPONSE
			logger.info("Navitaire Response: " + bookingRes);
			
			// CHECK FOR VALID RESPONSE
			if (bookingRes!= null && bookingRes.getGetBookingResponse() != null) {
				// ADD RESERVATION TO THE RESPONSE
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response
				.addNewReservation();

				// DO WORK
				int bagsChecked = 0;
				int paxAffected = 0;

				GetBookingResponse bookingResponse = bookingRes.getGetBookingResponse();
				Booking booking = bookingResponse.getBooking();
				
				if (booking.getBookingContacts() != null && booking.getBookingContacts().getBookingContactArray() != null) {
					for (BookingContact pax: booking.getBookingContacts().getBookingContactArray()) {

						aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger p = res.addNewPassengers();
						Address add = p.addNewAddresses();
						
						// EMAIL
						if (pax.getEmailAddress() != null) {
							add.setEmailAddress(pax.getEmailAddress());
						}
						
						// PHONES
						if (pax.getWorkPhone() != null) {
							add.setWorkPhone(pax.getWorkPhone());
						}
						if (pax.getHomePhone() != null) {
							add.setHomePhone(pax.getHomePhone());
						}
						if (pax.getOtherPhone() != null) {
							add.setAltPhone(pax.getOtherPhone());
						}
						if (pax.getFax() != null) {
							add.setPagerNumber(pax.getFax());
						}
						
						// ADDRESS
						if (pax.getCountryCode() != null) {
							add.setCountry(pax.getCountryCode());
						}
						if (pax.getAddressLine1() != null) {
							add.setAddress1(pax.getAddressLine1());
						}
						if (pax.getAddressLine2() != null || pax.getAddressLine3() != null) {
							add.setAddress2(pax.getAddressLine2() != null ? pax.getAddressLine2() + " " : "" + pax.getAddressLine3() != null ? pax.getAddressLine3() : "");
						}
						if (pax.getCity() != null) {
							add.setCity(pax.getCity());
						}
						if (pax.getProvinceState() != null) {
							if ("US".equals(pax.getCountryCode())) {
								add.setState(pax.getProvinceState());
							} else {
								add.setProvince(pax.getProvinceState());
							}
						}
						if (pax.getPostalCode() != null) {
							add.setZip(pax.getPostalCode());
						}
						
						// NAME
						if (pax.getNames() != null && pax.getNames().getBookingNameArray() != null && pax.getNames().getBookingNameArray().length > 0) {
							BookingName name = pax.getNames().getBookingNameArray(0);
							if (name.getFirstName() != null) {
								p.setFirstname(name.getFirstName());
							}
							if (name.getMiddleName() != null) {
								p.setMiddlename(name.getMiddleName());
							}
							if (name.getLastName() != null) {
								p.setLastname(name.getLastName());
							}
						}
						
						// FFNUMBER
						if (pax.getCustomerNumber() != null) {
							p.setFfNumber(pax.getCustomerNumber());
						}
					}
				}
				
				
				if (booking.getPassengers() != null && booking.getPassengers().getPassengerArray() != null) {
					for (Passenger pax: booking.getPassengers().getPassengerArray()) {
						
						aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger p = res.addNewPassengers();
						Address add = p.addNewAddresses();

						// NAME
						if (pax.getNames() != null && pax.getNames().getBookingNameArray() != null && pax.getNames().getBookingNameArray().length > 0) {
							BookingName name = pax.getNames().getBookingNameArray(0);
							if (name.getFirstName() != null) {
								p.setFirstname(name.getFirstName());
							}
							if (name.getMiddleName() != null) {
								p.setMiddlename(name.getMiddleName());
							}
							if (name.getLastName() != null) {
								p.setLastname(name.getLastName());
							}
						}
						
						// FF INFO
						if (pax.getPassengerProgram() != null) {
							PassengerProgram prog = pax.getPassengerProgram();
							if (prog.getProgramCode() != null) {
								p.setFfAirline(prog.getProgramCode());
							}
							if (prog.getProgramLevelCode() != null) {
								p.setFfStatus(prog.getProgramLevelCode());
							}
							if (prog.getProgramNumber() != null) {
								p.setFfNumber(prog.getProgramNumber());
							}
						}
						
						if (pax.getPassengerBags() != null && pax.getPassengerBags().getPassengerBagArray() != null && pax.getPassengerBags().getPassengerBagArray().length > 0) {

							for (PassengerBag bag: pax.getPassengerBags().getPassengerBagArray()) {
								
								ClaimCheck cc = res.addNewClaimChecks();
								cc.setTagNumber(bag.getOSTag());
								cc.setTimeChecked(bag.getOSTagDate());
							}
						}
					}
				}
				
				if (booking.getJourneys() != null && booking.getJourneys().getJourneyArray() != null) {
					for (Journey journey : booking.getJourneys().getJourneyArray()) {
						if (journey.getSegments() != null && journey.getSegments().getSegmentArray() != null) {
							for (Segment seg : journey.getSegments().getSegmentArray()) {
								Itinerary itin = res.addNewPassengerItinerary();
								mapSegmentsToItinerary(seg, itin);
								Itinerary bagItin = res.addNewBagItinerary();
								mapSegmentsToItinerary(seg, bagItin);
								// CG: MAY NEED TO REPLACE ABOVE TWO LINES WITH THE COMMENTED CODE BELOW AND ADJUST MAP METHOD.
//								if (seg.getLegs() != null && seg.getLegs().getLegArray() != null) {
//									for (Leg leg : seg.getLegs().getLegArray()) {
//										Itinerary itin = res.addNewPassengerItinerary();
//										mapSegmentsToItinerary(leg, itin);
//									}
//								}
							}
						}
					}
				}
				
				if (booking.getRecordLocator() != null && booking.getRecordLocator().length() > 0) {
					res.setPnr(booking.getRecordLocator());
				}
				
				res.setNumberChecked(bagsChecked);
				res.setPaxAffected(paxAffected);
			}
			
			logout(user, signature);
			
		} catch (Exception e) {

			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		} 
		return response;
	}

	private void mapSegmentsToItinerary(Segment seg, Itinerary i) {
		
		// FLIGHT INFO
		if (seg.getFlightDesignator() != null) {
			FlightDesignator fly = seg.getFlightDesignator();
			if (fly.getCarrierCode() != null) {
				i.setAirline(fly.getCarrierCode());
			}
			if (fly.getFlightNumber() != null) {
				i.setFlightnum(fly.getFlightNumber());
			}
		}
		
		// STATIONS
		if (seg.getArrivalStation() != null) {
			i.setArrivalCity(seg.getArrivalStation());
		}
		if (seg.getDepartureStation() != null) {
			i.setDepartureCity(seg.getDepartureStation());
		}
		
		// DATES
		if (seg.getSTA() != null)
			i.setScharrivetime(seg.getSTA());
		
		if (seg.getSTD() != null)
			i.setSchdeparttime(seg.getSTD());
	}

	// TODO: ABOVE HERE
	@Override
	public RemarkResponse writeRemark(User user, String pnr, String remark) throws UnexpectedException {
		RemarkResponse response = RemarkResponse.Factory.newInstance();
		try {
			String signature = logon(user);
			
			if (signature == null) {
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
				return response;
			}
			
			// CREATE CONTRACT VERSION
			ContractVersionDocument cv = ContractVersionDocument.Factory.newInstance();
			cv.setContractVersion(getContract_version(user));
			
			// CREATE SIGNATURE
			SignatureDocument sig = SignatureDocument.Factory.newInstance();
			sig.setSignature(signature);
			
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);

			BookingManagerStub stub = new BookingManagerStub(endpoint);
			configureClient(stub);
			
			AddBookingCommentsRequestDocument bi = AddBookingCommentsRequestDocument.Factory.newInstance();
			AddBookingCommentsRequest bi2 = bi.addNewAddBookingCommentsRequest();
			AddBookingCommentsRequestData bi3 = bi2.addNewAddBookingCommentsReqData();
			bi3.setRecordLocator(pnr);
			ArrayOfBookingComment bi4 = bi3.addNewBookingComments();
			BookingComment bi5 = bi4.addNewBookingComment();
			bi5.setState(MessageState.NEW);
			bi5.setCommentType(CommentType.MANIFEST);
			bi5.setCommentText(remark);
			stub.addBookingComments(bi, cv, sig);
			
			logout(user, signature);
			
		} catch (Exception e) {
			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return response;
	}

}
