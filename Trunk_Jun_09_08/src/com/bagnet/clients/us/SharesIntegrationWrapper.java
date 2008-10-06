package com.bagnet.clients.us;

import org.apache.log4j.Logger;

import phx_52n_gr90.sharesws.services_asmx.AddBookingCommentsDocument;
import phx_52n_gr90.sharesws.services_asmx.AddBookingCommentsResponseDocument;
import phx_52n_gr90.sharesws.services_asmx.Booking;
import phx_52n_gr90.sharesws.services_asmx.GetBookingInformationDocument;
import phx_52n_gr90.sharesws.services_asmx.GetBookingInformationResponseDocument;
import phx_52n_gr90.sharesws.services_asmx.GetPnrContentsDocument;
import phx_52n_gr90.sharesws.services_asmx.GetPnrContentsResponseDocument;
import phx_52n_gr90.sharesws.services_asmx.SendPrintMessageDocument;
import phx_52n_gr90.sharesws.services_asmx.SendPrintMessageResponseDocument;
import phx_52n_gr90.sharesws.services_asmx.ServicesStub;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

public class SharesIntegrationWrapper {

	private Booking booking;
	private String errorMessage;
	private final String PASSWORD = "password";
	private String pnrContents;
	private Logger logger = Logger.getLogger(SharesIntegrationWrapper.class);

	public void sendTelex(String message, String address) {
		try {
			ServicesStub stub = new ServicesStub();
			SendPrintMessageDocument printDoc = SendPrintMessageDocument.Factory.newInstance();
			SendPrintMessageDocument.SendPrintMessage sp = printDoc.addNewSendPrintMessage();
			
			sp.setPassword(PASSWORD);
			sp.setDestination(address);
			sp.setSource(address);
			sp.setMessage(message);
			

			SendPrintMessageResponseDocument responseDoc = stub.SendPrintMessage(printDoc);
			responseDoc.addNewSendPrintMessageResponse();
	
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
		}
	}
	
	public boolean writeCommentToPNR(String recordLocator, String comment) {
		if (TracerProperties.isTrue(TracerProperties.RESERVATION_UPDATE_COMMENT_ON)
				&& recordLocator != null && recordLocator.trim().length() == 6) {
			try {
				ServicesStub stub = new ServicesStub();
				AddBookingCommentsDocument acDoc = AddBookingCommentsDocument.Factory
						.newInstance();
				AddBookingCommentsDocument.AddBookingComments ac = acDoc
						.addNewAddBookingComments();
				ac.setPassword(PASSWORD);
				ac.setComments(comment);
				ac.setRecordLocator(recordLocator);

				AddBookingCommentsResponseDocument responseDoc = stub
						.AddBookingComments(acDoc);
				responseDoc.getAddBookingCommentsResponse();
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
			ServicesStub stub = new ServicesStub();
			GetBookingInformationDocument biDoc = GetBookingInformationDocument.Factory
					.newInstance();
			GetBookingInformationDocument.GetBookingInformation bi = biDoc
					.addNewGetBookingInformation();
			bi.setPassword(PASSWORD);
			if (recordLocator != null) {
				bi.setRecordLocator(recordLocator);
			} else if (bagTag != null) {
				bagTag = bagTag.toUpperCase();
				try {
					bagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagTag);
				} catch (BagtagException e) {
					// Ignore
				}
				bi.setBagTagNumber(bagTag);
			} else {
				return false;
			}

			logger.info("Getting Booking information");
			GetBookingInformationResponseDocument responseDoc = stub
					.GetBookingInformation(biDoc);
			booking = responseDoc.getGetBookingInformationResponse()
					.getGetBookingInformationResult();		

			if (booking != null && booking.getRecordLocator() != null
					&& booking.getRecordLocator().trim().length() > 0) {
				logger.info("Getting PNR Contents");
				getPnrContents(booking.getRecordLocator());
				logger.info("Contents Retrieved");
			}
		} catch (Exception e) {
			if (e.getMessage().contains("UNBL TO RTRV PNR")) {
				setErrorMessage("shares.error.unable.rtrv.pnr");
			} else if (e.getMessage().contains("Bag Tag Number was not found")) {
				setErrorMessage("shares.error.unable.rtrv.tag");
			} else if (e.getMessage().contains("timed")){
				setErrorMessage("shares.error.unable.timeout");
				e.printStackTrace();
			} else {
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	private boolean getPnrContents(String recordLocator) {
		try {
			ServicesStub stub = new ServicesStub();
			GetPnrContentsDocument biDoc = GetPnrContentsDocument.Factory
					.newInstance();
			GetPnrContentsDocument.GetPnrContents bi = biDoc.addNewGetPnrContents();

			bi.setPassword(PASSWORD);
			if (recordLocator != null) {
				bi.setRecordLocator(recordLocator);
			} else {
				return false;
			}

			GetPnrContentsResponseDocument responseDoc = stub.GetPnrContents(biDoc);
			pnrContents = responseDoc.getGetPnrContentsResponse()
					.getGetPnrContentsResult();
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
			return false;
		}
		return true;
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

}
