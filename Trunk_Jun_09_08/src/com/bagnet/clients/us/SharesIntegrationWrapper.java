package com.bagnet.clients.us;

import phx_52n_gr90.sharesws.services_asmx.Booking;
import phx_52n_gr90.sharesws.services_asmx.GetBookingInformationDocument;
import phx_52n_gr90.sharesws.services_asmx.GetBookingInformationResponseDocument;
import phx_52n_gr90.sharesws.services_asmx.ServicesStub;

public class SharesIntegrationWrapper {

	private String endpoint;
	private Booking booking;
	private String errorMessage;

	/**
	 * Method assumes that one of the two keys is null; it will perform the reservation system
	 * baed on the first key that is not null.
	 * @param recordLocator
	 * @param bagTag
	 * @return
	 */
	public boolean getBookingByKey(String recordLocator, String bagTag) {
		try {
			ServicesStub stub = new ServicesStub(null, endpoint);
			GetBookingInformationDocument biDoc = GetBookingInformationDocument.Factory.newInstance();
			GetBookingInformationDocument.GetBookingInformation bi = biDoc.addNewGetBookingInformation();
			if (recordLocator != null) {
				bi.setRecordLocator(recordLocator);
			} else if (bagTag != null) {
				bi.setBagTagNumber(bagTag);
			} else {
				return false;
			}

			GetBookingInformationResponseDocument responseDoc = stub.GetBookingInformation(biDoc);
			booking = responseDoc.getGetBookingInformationResponse().getGetBookingInformationResult();
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
		}
		return false;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
