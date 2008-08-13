package com.bagnet.nettracer.tracing.dto;

public class SearchIncidentReportDTO {

	private String incidentId; // DONE
	private String type; // DONE
	private String date;
	private String status; // DONE 
	private String ticketNumber;
	private String claimCheck;
	private String passengerName;
	private String stationCreated;
	private String stationAssigned;

	/**
	 * @return the incidentId
	 */
	public String getIncidentId() {
		return incidentId;
	}
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}
	/**
	 * @param ticketNumber the ticketNumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	/**
	 * @return the claimCheck
	 */
	public String getClaimCheck() {
		return claimCheck;
	}
	/**
	 * @param claimCheck the claimCheck to set
	 */
	public void setClaimCheck(String claimCheck) {
		this.claimCheck = claimCheck;
	}
	/**
	 * @return the passengerName
	 */
	public String getPassengerName() {
		return passengerName;
	}
	/**
	 * @param passengerName the passengerName to set
	 */
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	/**
	 * @return the stationCreated
	 */
	public String getStationCreated() {
		return stationCreated;
	}
	/**
	 * @param stationCreated the stationCreated to set
	 */
	public void setStationCreated(String stationCreated) {
		this.stationCreated = stationCreated;
	}
	/**
	 * @return the stationAssigned
	 */
	public String getStationAssigned() {
		return stationAssigned;
	}
	/**
	 * @param stationAssigned the stationAssigned to set
	 */
	public void setStationAssigned(String stationAssigned) {
		this.stationAssigned = stationAssigned;
	}

}
