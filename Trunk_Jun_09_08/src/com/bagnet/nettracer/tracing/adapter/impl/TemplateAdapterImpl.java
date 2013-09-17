package com.bagnet.nettracer.tracing.adapter.impl;

import java.util.Date;

import com.bagnet.nettracer.tracing.adapter.TemplateAdapter;
import com.bagnet.nettracer.tracing.utils.DateUtils;

public class TemplateAdapterImpl implements TemplateAdapter {
	
	private String dateFormat;
	
	// Address info
	private String addressAddress1 = "";
	private String addressAddress2 = "";
	private String addressCity = "";
	private String addressState = "";
	private String addressZip = "";
	
	// Agent info
	private String agentFirstName = "";
	private String agentLastName = "";
	private String agentInitials = "";
	
	// Claim info
	private String claimId = "";
	private String claimType = "";
	
	// Incident info
	private String incidentId = "";
	private String incidentType = "";
	
	// Passenger info
	private String passengerFirstName = "";
	private String passengerLastName = "";
	
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getAddressAddress1() {
		return addressAddress1;
	}
	
	public void setAddressAddress1(String addressAddress1) {
		this.addressAddress1 = addressAddress1;
	}
	
	public String getAddressAddress2() {
		return addressAddress2;
	}
	
	public void setAddressAddress2(String addressAddress2) {
		this.addressAddress2 = addressAddress2;
	}
	
	public String getAddressCity() {
		return addressCity;
	}
	
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	
	public String getAddressState() {
		return addressState;
	}
	
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	
	public String getAddressZip() {
		return addressZip;
	}
	
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	
	public String getAgentFirstName() {
		return agentFirstName;
	}
	
	public void setAgentFirstName(String agentFirstName) {
		this.agentFirstName = agentFirstName;
	}
	
	public String getAgentLastName() {
		return agentLastName;
	}
	
	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}
	
	public String getAgentInitials() {
		return agentInitials;
	}
	
	public void setAgentInitials(String agentInitials) {
		this.agentInitials = agentInitials;
	}
	
	public String getClaimId() {
		return claimId;
	}
	
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	
	public String getClaimType() {
		return claimType;
	}
	
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	public String getIncidentId() {
		return incidentId;
	}
	
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	
	public String getIncidentType() {
		return incidentType;
	}
	
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	
	public String getPassengerFirstName() {
		return passengerFirstName;
	}
	
	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}
	
	public String getPassengerLastName() {
		return passengerLastName;
	}
	
	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}
	
	public String getDateToday() {
		return DateUtils.formatDate(new Date(), this.dateFormat, null, null);
	}

}
