package aero.nettracer.serviceprovider.ws_1_0.res.spirit;

import org.apache.log4j.Logger;

import spirit.common.contracts.Bag;
import spirit.common.contracts.Passenger;
import spirit.common.contracts.Segment;
import spirit.nettracer.contracts.AddBookingCommentsInput;
import spirit.nettracer.contracts.AddBookingCommentsInputDocument;
import spirit.nettracer.contracts.GetBookingInformationInput;
import spirit.nettracer.contracts.GetBookingInformationInputDocument;
import spirit.nettracer.contracts.GetBookingInformationOutput;
import spirit.nettracer.contracts.GetBookingInformationOutputDocument;
import spirit.nettracer.contracts.NetTracerImplStub;
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
	
	@Override
	public EnplanementResponse getEnplanements(User user) throws UnexpectedException {
		return null;
	}

	@Override
	public OsiResponse getOsiContents(User user, String pnr, String bagTag) throws UnexpectedException {
		return null;
	}

	@Override
	public ReservationResponse getReservationData(User user, String pnr, String bagTag) throws UnexpectedException {
		ReservationResponse response = ReservationResponse.Factory
		.newInstance();
		

		try {
		
			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
			
			
			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.out.println(System.getProperty("javax.net.ssl.trustStore"));
			System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

			
			NetTracerImplStub stub = new NetTracerImplStub(endpoint);
			
			GetBookingInformationInputDocument bi = GetBookingInformationInputDocument.Factory.newInstance();
			GetBookingInformationInput bi2 = bi.addNewGetBookingInformationInput();
			bi2.setBagTagNumber("");
			bi2.setRecordLocator(pnr);
			GetBookingInformationOutputDocument spiritres = stub.getBookingInformation(bi);
			
			logger.info("Spirit Response: " + spiritres);
			
			if (spiritres!= null && spiritres.getGetBookingInformationOutput() != null) {
				aero.nettracer.serviceprovider.ws_1_0.common.xsd.Reservation res = response
				.addNewReservation();
				
				int bagsChecked = 0;
				int paxAffected = 0;
				
				GetBookingInformationOutput booking = spiritres.getGetBookingInformationOutput();
				
				
				if (booking.getPassengers() != null && booking.getPassengers().getPassengerArray() != null) {
					for (Passenger pax: booking.getPassengers().getPassengerArray()) {
						
						aero.nettracer.serviceprovider.ws_1_0.common.xsd.Passenger p = res.addNewPassengers();
						Address add = p.addNewAddresses();
						
						if (pax.getEmail() != null)
							add.setEmailAddress(pax.getEmail());
						
						if (pax.getFirstName() != null)
							p.setFirstname(pax.getFirstName());
						
						if (pax.getMiddleName() != null)
							p.setMiddlename(pax.getMiddleName());
						
						if (pax.getLastName() != null) {
							if (pax.getSuffix() != null) {
								p.setLastname(pax.getLastName() + " " + pax.getSuffix());
							} else {
								p.setLastname(pax.getLastName());
							}
						}
						
						if (pax.getFrequentFlyerNumber() != null)
							p.setFfNumber(pax.getFrequentFlyerNumber());
						

						if (pax.getTitle() != null) {
							p.setSalutation(0);
							String salu = pax.getTitle().toUpperCase();
							if (salu != null) {
								if (salu.equals("DR"))
									p.setSalutation(1);
								else if (salu.equals("MR"))
									p.setSalutation(2);
								else if (salu.equals("MS"))
									p.setSalutation(3);
								else if (salu.equals("MISS"))
									p.setSalutation(4);
								else if (salu.equals("MRS")) 
									p.setSalutation(5);
							}
						}
						
						
						if (pax.getAddresses() != null) {
							for (spirit.common.contracts.Address a: pax.getAddresses().getAddressArray()) {
								if (a.getAddress1() != null)
									add.setAddress1(a.getAddress1());
								
								if (a.getAddress2() != null)
									add.setAddress2(a.getAddress2());
								
								if (a.getCity() != null)
									add.setCity(a.getCity());
								
								if (a.getPostalCode() != null)
									add.setZip(a.getPostalCode());
								
								if (a.getPhone() != null)
									add.setMobilePhone(a.getPhone());
								
								if (a.getCountryCode() != null)
									add.setCountry(a.getCountryCode());
								
								if (a.getCountryCode() != null && !a.getCountryCode().equals("US")) {
									add.setProvince(a.getProvinceState());
								} else {
									add.setState(a.getProvinceState());
								}
								
								// TODO: What to do with address 3?
								// a.getAddress3();
								
							}
						}
						
						if (pax.getBaggage() != null && pax.getBaggage().getBagArray().length > 0) {
//							Calendar lastCheckedDate = null;
							for (Bag bag: pax.getBaggage().getBagArray()) {
								
								ClaimCheck cc = res.addNewClaimChecks();
								cc.setTimeChecked(bag.getBagTagDate());
								cc.setTagNumber(bag.getDescriptionCode());
								
								/*
								if (lastCheckedDate == null) {
									lastCheckedDate = bag.getBagTagDate();
								} else if (bag.getBagTagDate().getTimeInMillis() > lastCheckedDate.getTimeInMillis()) {
									lastCheckedDate = bag.getBagTagDate();
								}						

								if (res.getCheckedLocation() == 0 && bag.getBagTagDate().equals(lastCheckedDate)) {
									// static final int INT_UNSPECIFIED = 1;
									// static final int INT_CUSTOMER = 2;
									// static final int INT_KIOSK = 3;
									// static final int INT_RES_SALES_AGENT = 4;
									// static final int INT_RES_GROUP_AGENT = 5;
									// static final int INT_TRAVEL_AGENT = 6;
									// static final int INT_AGENT_CHECK_IN = 7;
									// static final int INT_DCS = 8;
									// bag.getSource();
									// res.setCheckedLocation(arg0);
								}
								*/

								
							}
						}
					}
				}
				

				if (booking.getBaggageItinerary() != null && booking.getBaggageItinerary().getSegmentArray() != null) {
					for (Segment seg: booking.getBaggageItinerary().getSegmentArray()) {
						Itinerary i = res.addNewBagItinerary();
						mapSegmentsToItinerary(seg, i);
					}
				}
				
				if (booking.getPassengerItinerary() != null && booking.getPassengerItinerary().getSegmentArray() != null) {
					for (Segment seg: booking.getBaggageItinerary().getSegmentArray()) {
						Itinerary i = res.addNewPassengerItinerary();
						mapSegmentsToItinerary(seg, i);
					}					
				}
				
				if (booking.getRecordLocator() != null && booking.getRecordLocator().length() > 0) {
					res.setPnr(booking.getRecordLocator());
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

	private void mapSegmentsToItinerary(Segment seg, Itinerary i) {
//		if (seg.getArrivalDateTime() != null)
//			i.setActarrivetime(seg.getArrivalDateTime());
//		
//		if (seg.getDepartureDateTime() != null)
//			i.setActdeparttime(seg.getDepartureDateTime());
//		
		if (seg.getArrivalStation() != null)
			i.setArrivalCity(seg.getArrivalStation());
		
		if (seg.getDepartureStation() != null)
			i.setDepartureCity(seg.getDepartureStation());
		
		if (seg.getNbr() != null)
			i.setFlightnum(seg.getNbr());
		
		if (seg.getCarrierCode() != null)
			i.setAirline(seg.getCarrierCode());
		
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

			String endpoint = user.getProfile().getParameters().get(ParameterType.RESERVATION_ENDPOINT);
				//"https://206.57.4.54/NetTracerInternal/NetTracerService.svc";
			
			System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.out.println(System.getProperty("javax.net.ssl.trustStore"));
			System.out.println(System.getProperty("javax.net.ssl.trustStorePassword"));

			NetTracerImplStub stub = new NetTracerImplStub(endpoint);			
			AddBookingCommentsInputDocument bi = AddBookingCommentsInputDocument.Factory.newInstance();
			AddBookingCommentsInput bi2 = bi.addNewAddBookingCommentsInput();
			bi2.setComments(remark);
			bi2.setRecordLocator(pnr);
			stub.addBookingComments(bi);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.addNewError().setDescription(
					ServiceConstants.UNEXPECTED_EXCEPTION);
		}
		return response;
	}

}
