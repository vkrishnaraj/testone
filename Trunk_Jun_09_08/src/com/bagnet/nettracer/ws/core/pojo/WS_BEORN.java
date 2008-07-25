package com.bagnet.nettracer.ws.core.pojo;

import java.util.Date;

public class WS_BEORN {

	private String claimCheckNumber;
	private String expediteNumber;
	private boolean tagOff;
	private String destinationStation;
	private String message;
	private Integer lossCode;
	private String faultStation;
	private WS_ForwardItinerary[] baggageItinerary;
	private WS_ForwardItinerary[] forwardItinerary;
	private String foundAtStation;
	private Date foundDateTime;
	

	/**
	 * @return Returns the claimCheckNumber.
	 */
	public String getclaimCheckNumber() {
		return claimCheckNumber;
	}

	/**
	 * @param claimCheckNumber
	 *          The claimCheckNumber to set.
	 */
	public void setclaimCheckNumber(String claimCheckNumber) {
		this.claimCheckNumber = claimCheckNumber;
	}

	
	/**
	 * @return Returns the expediteNumber.
	 */
	public String getExpediteNumber() {
		return expediteNumber;
	}

	/**
	 * @param expediteNumber
	 *          The expediteNumber to set.
	 */
	public void setExpediteNumber(String expediteNumber) {
		this.expediteNumber = expediteNumber;
	}
	
	/**
	 * @return Returns the tagOff.
	 */
	public boolean isTagOff() {
		return tagOff;
	}

	/**
	 * @param tagOff
	 *          The tagOff to set.
	 */
	public void setExpediteNumber(boolean tagOff) {
		this.tagOff = tagOff;
	}

	/**
	 * @return Returns the destinationStation.
	 */
	public String getdestinationStation() {
		return destinationStation;
	}

	/**
	 * @param destinationStation
	 *          The destinationStation to set.
	 */
	public void setdestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *          The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the lossCode.
	 */
	public Integer getLossCode() {
		return lossCode;
	}

	/**
	 * @param lossCode
	 *          The lossCode to set.
	 */
	public void setLossCode(Integer lossCode) {
		this.lossCode = lossCode;
	}
	
	/**
	 * @return Returns the faultStation.
	 */
	public String getFaultStation() {
		return faultStation;
	}

	/**
	 * @param faultStation
	 *          The faultStation to set.
	 */
	public void setFaultStation(String faultStation) {
		this.faultStation = faultStation;
	}

	/**
	 * @return The forwarding itinerary
	 */
	public WS_ForwardItinerary[] getBaggageItinerary() {
		return baggageItinerary;
	}

	/**
	 * @param forwardItinerary The forwarding itinerary.
	 */
	public void setBaggageItinerary(WS_ForwardItinerary[] baggageItinerary) {
		this.baggageItinerary = baggageItinerary;
	}

	/**
	 * @return The forwarding itinerary
	 */
	public WS_ForwardItinerary[] getForwardItinerary() {
		return forwardItinerary;
	}

	/**
	 * @param forwardItinerary The forwarding itinerary.
	 */
	public void setForwardItinerary(WS_ForwardItinerary[] forwardItinerary) {
		this.forwardItinerary = forwardItinerary;
	}

	/**
	 * @return the foundAtStation
	 */
	public String getFoundAtStation() {
		return foundAtStation;
	}
	/**
	 * @param foundAtStation the foundAtStation to set
	 */
	public void setFoundAtStation(String foundAtStation) {
		this.foundAtStation = foundAtStation;
	}

	/**
	 * @return the foundDateTime
	 */
	public Date getFounddatetime() {
		return foundDateTime;
	}
	/**
	 * @param founddatetime the foundDateTime to set
	 */
	public void setFounddatetime(Date foundDateTime) {
		this.foundDateTime = foundDateTime;
	}
}
