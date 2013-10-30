/*
 * Created on Oct 25, 2013
 *
 * Sean Fine
 */
package com.bagnet.nettracer.tracing.dto;


/**
 * @author Sean Fine
 *
 * DTO to create the Incoming Incident Report
 */
public class IncomingIncDTO {

	private String incident_ID;
	private String incidentType;
	private String createdDate;
	private String airlineCreated;
	private String stationCreated;
	private String stationAssigned;
	private String status;
	private String color;
	private String type;
	private String itinerary;
	private String positionId;
	private String claimCheck;
	private String passengerName;
	
	public String getIncident_ID() {
		return incident_ID;
	}
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}
	public String getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getAirlineCreated() {
		return airlineCreated;
	}
	public void setAirlineCreated(String airlineCreated) {
		this.airlineCreated = airlineCreated;
	}
	public String getStationCreated() {
		return stationCreated;
	}
	public void setStationCreated(String stationCreated) {
		this.stationCreated = stationCreated;
	}
	public String getStationAssigned() {
		return stationAssigned;
	}
	public void setStationAssigned(String stationAssigned) {
		this.stationAssigned = stationAssigned;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItinerary() {
		return itinerary;
	}
	public void setItinerary(String itinerary) {
		this.itinerary = itinerary;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getClaimCheck() {
		return claimCheck;
	}
	public void setClaimCheck(String claimCheck) {
		this.claimCheck = claimCheck;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
}
