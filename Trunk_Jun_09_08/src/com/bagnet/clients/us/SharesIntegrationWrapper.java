package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.usairways.lcc.aat_sharesws.services_asmx.AddBookingCommentsDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.AddBookingCommentsResponseDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.Booking;
import com.usairways.lcc.aat_sharesws.services_asmx.GetBookingInformationDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.GetBookingInformationResponseDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.GetPnrContentsDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.GetPnrContentsResponseDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.SendPrintMessageDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.SendPrintMessageResponseDocument;
import com.usairways.lcc.aat_sharesws.services_asmx.ServicesStub;

public class SharesIntegrationWrapper {

	private Booking booking;
	private String errorMessage;
	private final String PASSWORD = "password";
	private String pnrContents;
	private Logger logger = Logger.getLogger(SharesIntegrationWrapper.class);
	private String endpoint = PropertyBMO.getValue(PropertyBMO.PROPERTY_BOOKING_ENDPOINT);
	
	
	public void sendTelex(String message, String address, boolean async){
		if(async){
			try{
				SharesIntegrationThreadHandler.sendTelex(message, address);
			}catch (Exception e){
				//default to sync call
				sendTelex(message, address);
				return;
			}
		} else {
			sendTelex(message, address);
		}
	}
	
	public void sendTelex(String message, String address) {
		Date start = new Date();
		try {
			ServicesStub stub = new ServicesStub(endpoint);
			SendPrintMessageDocument printDoc = SendPrintMessageDocument.Factory.newInstance();
			SendPrintMessageDocument.SendPrintMessage sp = printDoc.addNewSendPrintMessage();
			
			sp.setPassword(PASSWORD);
			sp.setDestination(address);
			sp.setSource(address);
			sp.setMessage(message);
			

			SendPrintMessageResponseDocument responseDoc = stub.sendPrintMessage(printDoc);
			responseDoc.addNewSendPrintMessageResponse();
	
		} catch (Exception e) {
			e.printStackTrace();
			setErrorMessage("Error calling webservice: " + e.toString());
			com.bagnet.nettracer.tracing.utils.general.Logger.logTelex("", "Error calling webservice", start);
			return;
		}
		com.bagnet.nettracer.tracing.utils.general.Logger.logTelex("SENDTELEX", "TELEX SUCCESSFUL: " + address, start);
	}
	
	public boolean writeCommentToPNR(String recordLocator, String comment) {
		if (TracerProperties.isTrue(TracerProperties.get("wt.company.code"),TracerProperties.RESERVATION_UPDATE_COMMENT_ON)
				&& recordLocator != null && recordLocator.trim().length() == 6) {
			try {
				ServicesStub stub = new ServicesStub(endpoint);
				AddBookingCommentsDocument acDoc = AddBookingCommentsDocument.Factory
						.newInstance();
				AddBookingCommentsDocument.AddBookingComments ac = acDoc
						.addNewAddBookingComments();
				ac.setPassword(PASSWORD);
				ac.setComments(comment);
				ac.setRecordLocator(recordLocator);

				AddBookingCommentsResponseDocument responseDoc = stub
						.addBookingComments(acDoc);
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
			ServicesStub stub = new ServicesStub(endpoint);
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
					if(PropertyBMO.isTrue(PropertyBMO.CONVERT_BAGTAG)){
						bagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagTag);
					}
				} catch (BagtagException e) {
					// Ignore
				}
				bi.setBagTagNumber(bagTag);
			} else {
				return false;
			}
			logger.info(biDoc);
			GetBookingInformationResponseDocument responseDoc = stub
					.getBookingInformation(biDoc);
			logger.info(responseDoc); 
			booking = responseDoc.getGetBookingInformationResponse()
					.getGetBookingInformationResult();		

			if (booking != null && booking.getRecordLocator() != null
					&& booking.getRecordLocator().trim().length() > 0) {
				getPnrContents(booking.getRecordLocator());
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
			ServicesStub stub = new ServicesStub(endpoint);
			GetPnrContentsDocument biDoc = GetPnrContentsDocument.Factory
					.newInstance();
			GetPnrContentsDocument.GetPnrContents bi = biDoc.addNewGetPnrContents();

			bi.setPassword(PASSWORD);
			if (recordLocator != null) {
				bi.setRecordLocator(recordLocator);
			} else {
				return false;
			}

			GetPnrContentsResponseDocument responseDoc = stub.getPnrContents(biDoc);
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

	
	public void sendTelexBySlice(String message, String address, String label) {
		int telexMaxLengthPerTransmission = 900;    // max allowed
		
		String myPageLabel = " ";  
		
		int myMaxLength = telexMaxLengthPerTransmission - (label.length() + 8);
		
		if (message.length() < telexMaxLengthPerTransmission) {
			sendTelex(message, address, true);
		} else {
			ArrayList<String> list = StringUtils.divideUpBigString(message, myMaxLength, "*");
			
			int counter = 1;
			
			for (String j: list) {
				myPageLabel = label + " " + counter + " of " + list.size() + "*";
				counter++;
				j = myPageLabel + j;
				
				sendTelex(j, address, true);
			}
		}
	}
}
