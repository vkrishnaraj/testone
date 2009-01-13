package com.bagnet.clients.b6;

import org.apache.log4j.Logger;

import servicemanager.uddi_5413d78d_d06a_11dd_9aaf_9cc8f268cf20.BaggageService_vs3_0_BP_NT_DEVStub;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.jetblue.schemas._2008._03.framework.baggage.ReservationDetail;
import com.jetblue.schemas._2008._03.framework.services.AuthorizationHeader;
import com.jetblue.schemas._2008._03.framework.services.AuthorizationHeaderDocument;
import com.jetblue.schemas._2008._03.framework.services.baggage.BagNumberRequest;
import com.jetblue.schemas._2008._03.framework.services.baggage.BagNumberRequestDocument;
import com.jetblue.schemas._2008._03.framework.services.baggage.PassengerDetailResponseDocument;
import com.jetblue.schemas._2008._03.framework.services.baggage.RecordLocatorRequest;
import com.jetblue.schemas._2008._03.framework.services.baggage.RecordLocatorRequestDocument;
import com.jetblue.schemas._2008._03.framework.services.baggage.ReservationDetailResponseDocument;

public class JetBlueIntegrationWrapper {

	
	private String errorMessage;
	private String pnrContents;
	
	private Logger logger = Logger.getLogger(JetBlueIntegrationWrapper.class);
	private String endpoint = PropertyBMO.getValue(PropertyBMO.PROPERTY_BOOKING_ENDPOINT);
	private String consumerId = PropertyBMO.getValue("jetblue.reservation.consumerId");
	private String signature = PropertyBMO.getValue("jetblue.reservation.signature");
	private String token = PropertyBMO.getValue("jetblue.reservation.token");
	private ReservationDetail reservationDetail = null;
	
	public JetBlueIntegrationWrapper () {
		System.setProperty("javax.net.ssl.trustStore", "c:\\secure\\ntcerts");
    System.setProperty("javax.net.ssl.trustStorePassword", "nettracer1");
    System.setProperty("javax.net.ssl.keyStore", "c:\\secure\\ntkeys.ks");
    System.setProperty("javax.net.ssl.keyStorePassword", "nettracer1");
	}


	
	public boolean writeCommentToPNR(String recordLocator, String comment) {
		if (TracerProperties.isTrue(TracerProperties.RESERVATION_UPDATE_COMMENT_ON)
				&& recordLocator != null && recordLocator.trim().length() == 6) {
			try {

			} catch (Exception e) {
				e.printStackTrace();
				setErrorMessage("Error calling webservice: " + e.toString());
				return false;
			}
			return true;
		}
		return false;

	}

	/**
	 * Method assumes that one of the two keys is null; it will perform the
	 * reservation system baed on the first key that is not null.
	 * 
	 * @param recordLocator
	 * @param bagTag
	 * @return
	 */
	public boolean getBookingByKey(String recordLocator, String bagTag) {

		try {
			
			if (recordLocator != null) {
				reservationDetail = getBookingByPnr(recordLocator);
			} else if (bagTag != null) {
				bagTag = bagTag.toUpperCase();
				try {
					bagTag = LookupAirlineCodes.getFullBagTag(bagTag);
				} catch (BagtagException e) {
					// Ignore
				}
				reservationDetail = getBookingByTag(bagTag);
			} else {
				return false;
			}

		} catch (Exception e) {
			logger.error("Exception thrown in JetBlue Integration Wrapper", e);
			e.printStackTrace();
			return false;
		}
		if (reservationDetail == null) {
			return false;
		}
		return true;
	}

	private ReservationDetail getBookingByTag(String bagTag) {
		try {
			
			AuthorizationHeaderDocument authDoc = getAuthorizationHeader();

			BaggageService_vs3_0_BP_NT_DEVStub stub = new BaggageService_vs3_0_BP_NT_DEVStub(endpoint);
			
			
			BagNumberRequestDocument requestDoc = BagNumberRequestDocument.Factory.newInstance();
			BagNumberRequest request = requestDoc.addNewBagNumberRequest();
			request.setBagNumber(bagTag);
			
			PassengerDetailResponseDocument responseDoc = stub.GetPassengerDetailByBagNumber(requestDoc , authDoc);
			
			String recordLocator = responseDoc.getPassengerDetailResponse().getPassengerDetail().getRecordLocator();
			

			if (recordLocator != null) {
				return getBookingByPnr(recordLocator);
			}
			
		} catch (Exception e) {
			logger.error("Exception thrown in JetBlue Integration Wrapper", e);
			e.printStackTrace();
		}
		return null;
	}



	private ReservationDetail getBookingByPnr(String recordLocator) {
		try {
			System.out.println("Endpoint: " + endpoint);
			BaggageService_vs3_0_BP_NT_DEVStub stub = new BaggageService_vs3_0_BP_NT_DEVStub(endpoint);
			
			RecordLocatorRequestDocument requestDoc = RecordLocatorRequestDocument.Factory.newInstance();
			RecordLocatorRequest recordRequest = requestDoc.addNewRecordLocatorRequest();
			recordRequest.setRecordLocator(recordLocator);
			
			AuthorizationHeaderDocument authDoc = getAuthorizationHeader();
			
			ReservationDetailResponseDocument responseDoc = stub.GetReservationDetailByRecordLocator(requestDoc, authDoc);
			return responseDoc.getReservationDetailResponse().getReservationDetail();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception thrown in JetBlue Integration Wrapper", e);
			
		}
		return null;
	}

	private boolean getPnrContents(String recordLocator) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
			return false;
		}
		return true;
	}

	/*
	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	*/

	public String getErrorMessage() {
		return errorMessage;
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the pnrContents
	 */
	public String getPnrContents() {
		return pnrContents;
	}

	/**
	 * @param pnrContents
	 *          the pnrContents to set
	 */
	public void setPnrContents(String pnrContents) {
		this.pnrContents = pnrContents;
	}
	
	private AuthorizationHeaderDocument getAuthorizationHeader() {
		AuthorizationHeaderDocument authDoc = AuthorizationHeaderDocument.Factory.newInstance();
		AuthorizationHeader header = authDoc.addNewAuthorizationHeader();
		header.setConsumerId(consumerId);
		header.setSignature(signature);
		header.setToken(token);
		return authDoc;
	}

	public ReservationDetail getReservationDetail() {
		return reservationDetail;
	}

	public void setReservationDetail(ReservationDetail reservationDetail) {
		this.reservationDetail = reservationDetail;
	}
}
