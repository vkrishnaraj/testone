package com.bagnet.nettracer.tracing.dto;


import java.util.Date;

public class PPLC_DTO { 

	String incidentId = null;
	String recordLocator = null;
	String lastName = null;
	String firstName = null;
	private String agentAssigned = null;
	Date sentDate = null;
	Date dueDate = null;
	private Date receivedDate = null;
	Date offerDueDate = null;
	private Date offerSentDate = null;
	private String stationAssigned = null;
	
	
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	public String getRecordLocator() {
		return recordLocator;
	}
	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getAgentAssigned() {
		return agentAssigned;
	}
	public void setAgentAssigned(String agentAssigned) {
		this.agentAssigned = agentAssigned;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public Date getOfferDueDate() {
		return offerDueDate;
	}
	public void setOfferDueDate(Date offerDueDate) {
		this.offerDueDate = offerDueDate;
	}
	public Date getOfferSentDate() {
		return offerSentDate;
	}
	public void setOfferSentDate(Date offerSentDate) {
		this.offerSentDate = offerSentDate;
	}
	public String getStationAssigned() {
		return stationAssigned;
	}
	public void setStationAssigned(String stationcode) {
		this.stationAssigned = stationcode;
	}

}
