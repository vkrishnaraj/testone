package aero.nettracer.serviceprovider.ws_1_0.res.webjet;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.tempuri.AddBookingCommentsDocument;
import org.tempuri.AddBookingCommentsDocument.AddBookingComments;
import org.tempuri.AutenticacaoHeader;
import org.tempuri.Baggage;
import org.tempuri.Booking;
import org.tempuri.ConsultaSimplesHeader;
import org.tempuri.EfetuarLoginDocument;
import org.tempuri.EfetuarLoginDocument.EfetuarLogin;
import org.tempuri.EfetuarLoginResponseDocument;
import org.tempuri.EfetuarLoginResponseDocument.EfetuarLoginResponse;
import org.tempuri.FlightLeg;
import org.tempuri.GetBookingInformationDocument;
import org.tempuri.GetBookingInformationDocument.GetBookingInformation;
import org.tempuri.GetBookingInformationResponseDocument;
import org.tempuri.GetBookingInformationResponseDocument.GetBookingInformationResponse;
import org.tempuri.Language;
import org.tempuri.Passenger;
import org.tempuri.SessaoUsuario;
import org.tempuri.WSNetTracerStub;

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

public class Reservation implements ReservationInterface {

	private static Logger logger = Logger.getLogger(Reservation.class);
	
	private static final String USU_EMAIL = "nettracer@webjet.com.br";
	private static final String USU_SENHA = "nettracer123";
	private static final String USU_SIGLA_CIA_AEREA = "WJB";
	
	@Override
	public EnplanementResponse getEnplanements(User user) throws UnexpectedException {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr, String bagTag) throws UnexpectedException {
		return null;
	}
	
	public SessaoUsuario getSession(User user) throws UnexpectedException {

		try {
		
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			WSNetTracerStub stub = new WSNetTracerStub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));

			// CREATE OUTGOING DOCUMENT
			EfetuarLoginDocument bi = EfetuarLoginDocument.Factory.newInstance();
			EfetuarLogin bi2 = bi.addNewEfetuarLogin();
			AutenticacaoHeader bi3 = bi2.addNewParAutenticacaoHeader();
			bi3.setLanguage(Language.ENGLISH);
			bi3.setUsuEmail(USU_EMAIL);
			bi3.setUsuSenha(USU_SENHA);
			bi3.setSiglaCiaAerea(USU_SIGLA_CIA_AEREA);
			
			// MAKE REQUEST WITH STUB
			EfetuarLoginResponseDocument docRes = stub.efetuarLogin(bi);
			EfetuarLoginResponse sessionRes = docRes.getEfetuarLoginResponse();
			return sessionRes.getEfetuarLoginResult();
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} 
		
	}

	@Override
	public ReservationResponse getReservationData(User user, String pnr, String bagTag) throws UnexpectedException {
		// DEFINE RESPONSE
		ReservationResponse response = ReservationResponse.Factory
		.newInstance();
		

		try {
			SessaoUsuario session = getSession(user);
			if (session == null) {
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
				return response;
			}
			
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			
			// 1) STUB & TIMEOUTES
			WSNetTracerStub stub = new WSNetTracerStub(endpoint);
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1 * 60 * 1000));
			stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT, new Integer(1 * 60 * 1000));

			// CREATE OUTGOING DOCUMENT
			GetBookingInformationDocument bi = GetBookingInformationDocument.Factory.newInstance();
			GetBookingInformation bi2 = bi.addNewGetBookingInformation();
			bi2.setBookingLocator(pnr);
			ConsultaSimplesHeader header = bi2.addNewOConsultaHeader();
			header.setSessao(session);
			bi2.setOConsultaHeader(header);
			
			// MAKE REQUEST WITH STUB
			GetBookingInformationResponseDocument webjetres = stub.getBookingInformation(bi);
			

			// OUTPUT RESPONSE
			logger.info("WebJet Response: " + webjetres);
			
			// CHECK FOR VALID RESPONSE
			if (webjetres!= null && webjetres.getGetBookingInformationResponse() != null) {
				// ADD RESERVATION TO THE RESPONSE
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response
				.addNewReservation();

				// DO WORK
				int bagsChecked = 0;
				int paxAffected = 0;

				GetBookingInformationResponse bookingResponse = webjetres.getGetBookingInformationResponse();
				Booking booking = bookingResponse.getGetBookingInformationResult();
				
				
				if (booking.getPassengers() != null && booking.getPassengers().getPassengerArray() != null) {
					for (Passenger pax: booking.getPassengers().getPassengerArray()) {
						
						aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger p = res.addNewPassengers();
						Address add = p.addNewAddresses();
						
						if (pax.getEmail() != null) {
							add.setEmailAddress(pax.getEmail());
						}
						
						if (pax.getFirstName() != null) {
							p.setFirstname(pax.getFirstName());
						}
						
						if (pax.getLastName() != null) {
							p.setLastname(pax.getLastName());
						}
						
						if (pax.getFidelityCardNumber() != null) {
							p.setFfNumber(pax.getFidelityCardNumber());
						}
						
						if (pax.getBags() != null && pax.getBags().getBaggageArray().length > 0) {

							for (Baggage bag: pax.getBags().getBaggageArray()) {
								
								ClaimCheck cc = res.addNewClaimChecks();
								cc.setTagNumber(bag.getBagTagNumber());
								
							}
						}
						

						if (pax.getFlightSegment() != null && pax.getFlightSegment().getFlightLegs() != null && 
								pax.getFlightSegment().getFlightLegs().getFlightLegArray()!= null) {
							for (FlightLeg seg: pax.getFlightSegment().getFlightLegs().getFlightLegArray()) {
								Itinerary i = res.addNewPassengerItinerary();
								mapSegmentsToItinerary(seg, i);
								Itinerary j = res.addNewBagItinerary();
								mapSegmentsToItinerary(seg, j);
							}
						}
					}
				}
				
				if (booking.getBookingLocator() != null && booking.getBookingLocator().length() > 0) {
					res.setPnr(booking.getBookingLocator());
				}
				
				res.setNumberChecked(bagsChecked);
				res.setPaxAffected(paxAffected);
			}
			
			
			
		} catch (Exception e) {

			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		} 
		return response;
	}

	private void mapSegmentsToItinerary(FlightLeg seg, Itinerary i) {
//		if (seg.getArrivalDateTime() != null)
//			i.setActarrivetime(seg.getArrivalDateTime());
//		
//		if (seg.getDepartureDateTime() != null)
//			i.setActdeparttime(seg.getDepartureDateTime());
//		
//		if (seg.get != null)
//			i.setAirline(seg.getCarrierCode());
//		
		if (seg.getOriginAirport() != null && seg.getOriginAirport().getIATACode() != null)
			i.setArrivalCity(seg.getOriginAirport().getIATACode());
		
		if (seg.getDestinyAirport() != null && seg.getDestinyAirport().getIATACode() != null)
			i.setDepartureCity(seg.getDestinyAirport().getIATACode());
		
		if (seg.getFlight() != null && seg.getFlight().getFlightNumber() != 0)
			i.setFlightnum(String.valueOf(seg.getFlight().getFlightNumber()));
		
		if (seg.getGMTArrivalDateTime() != null)
			i.setScharrivetime(seg.getGMTArrivalDateTime());
		
		if (seg.getGMTDepartureDateTime() != null)
			i.setSchdeparttime(seg.getGMTDepartureDateTime());
	}

	// TODO: ABOVE HERE
	@Override
	public RemarkResponse writeRemark(User user, String pnr, String remark) throws UnexpectedException {
		RemarkResponse response = RemarkResponse.Factory.newInstance();
		try {
			SessaoUsuario session = getSession(user);
			if (session == null) {
				response.addNewError().setDescription(ServiceConstants.UNEXPECTED_EXCEPTION);
				return response;
			}
			
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);

			WSNetTracerStub stub = new WSNetTracerStub(endpoint);
			AddBookingCommentsDocument bi = AddBookingCommentsDocument.Factory.newInstance();
			AddBookingComments bi2 = bi.addNewAddBookingComments();
			org.tempuri.AddBookingComments bi3 = bi2.addNewOAddBookingComments();
			bi3.setComments(remark);
			bi3.setRecordLocator(pnr);
			bi3.setSessao(session);
			stub.addBookingComments(bi);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return response;
	}

}
