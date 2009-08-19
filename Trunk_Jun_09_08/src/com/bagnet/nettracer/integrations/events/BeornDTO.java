package com.bagnet.nettracer.integrations.events;

import java.util.Date;

public class BeornDTO {

	private String tagNumber;
	private String expediteNumber;
	private String finalFlightNumber;
	private String finalFlightAirline;
	private Date finalFlightDepartureDate;
	private String finalDestination;
	private String reasonForLoss;
	private String faultStation;
	private String specialInstructions;
	private String onhand;

	public String getOnhand() {
		return onhand;
	}

	public void setOnhand(String onhand) {
		this.onhand = onhand;
	}

	/**
	 * @return the tagNumber
	 */
	public String getTagNumber() {
		return tagNumber;
	}

	/**
	 * @param tagNumber
	 *          the tagNumber to set
	 */
	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	/**
	 * @return the expediteNumber
	 */
	public String getExpediteNumber() {
		return expediteNumber;
	}

	/**
	 * @param expediteNumber
	 *          the expediteNumber to set
	 */
	public void setExpediteNumber(String expediteNumber) {
		this.expediteNumber = expediteNumber;
	}



	/**
	 * @return the finalDestination
	 */
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination
	 *          the finalDestination to set
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return the reasonForLoss
	 */
	public String getReasonForLoss() {
		return reasonForLoss;
	}

	/**
	 * @param reasonForLoss
	 *          the reasonForLoss to set
	 */
	public void setReasonForLoss(String reasonForLoss) {
		this.reasonForLoss = reasonForLoss;
	}

	/**
	 * @return the faultStation
	 */
	public String getFaultStation() {
		return faultStation;
	}

	/**
	 * @param faultStation
	 *          the faultStation to set
	 */
	public void setFaultStation(String faultStation) {
		this.faultStation = faultStation;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions
	 *          the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the finalFlightNumber
	 */
	public String getFinalFlightNumber() {
		return finalFlightNumber;
	}

	/**
	 * @param finalFlightNumber the finalFlightNumber to set
	 */
	public void setFinalFlightNumber(String finalFlightNumber) {
		this.finalFlightNumber = finalFlightNumber;
	}

	/**
	 * @return the finalFlightAirline
	 */
	public String getFinalFlightAirline() {
		return finalFlightAirline;
	}

	/**
	 * @param finalFlightAirline the finalFlightAirline to set
	 */
	public void setFinalFlightAirline(String finalFlightAirline) {
		this.finalFlightAirline = finalFlightAirline;
	}

	/**
	 * @return the finalFlightDepartureDate
	 */
	public Date getFinalFlightDepartureDate() {
		return finalFlightDepartureDate;
	}

	/**
	 * @param finalFlightDepartureDate the finalFlightDepartureDate to set
	 */
	public void setFinalFlightDepartureDate(Date finalFlightDepartureDate) {
		this.finalFlightDepartureDate = finalFlightDepartureDate;
	}

}
