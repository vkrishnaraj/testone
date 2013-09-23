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
	private String passengerPhoneNumber = "";
	
	// Found Item info
	private String foundItemId = "";
	private String foundItemType = "";
	private String foundItemColor = "";
	private String foundItemItem = "";
	private String foundItemDescription = "";
	private String foundItemCaseColor = "";
	
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getAddressAddress1() {
		return addressAddress1 != null ? addressAddress1 : "";
	}
	
	public void setAddressAddress1(String addressAddress1) {
		this.addressAddress1 = addressAddress1;
	}
	
	public String getAddressAddress2() {
		return addressAddress2 != null ? addressAddress2 : "";
	}
	
	public void setAddressAddress2(String addressAddress2) {
		this.addressAddress2 = addressAddress2;
	}
	
	public String getAddressCity() {
		return addressCity != null ? addressCity : "";
	}
	
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	
	public String getAddressState() {
		return addressState != null ? addressState : "";
	}
	
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	
	public String getAddressZip() {
		return addressZip != null ? addressZip : "";
	}
	
	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}
	
	public String getAgentFirstName() {
		return agentFirstName != null ? agentFirstName : "";
	}
	
	public void setAgentFirstName(String agentFirstName) {
		this.agentFirstName = agentFirstName;
	}
	
	public String getAgentLastName() {
		return agentLastName != null ? agentLastName : "";
	}
	
	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}
	
	public String getAgentInitials() {
		return agentInitials != null ? agentInitials : "";
	}
	
	public void setAgentInitials(String agentInitials) {
		this.agentInitials = agentInitials;
	}
	
	public String getClaimId() {
		return claimId != null ? claimId : "";
	}
	
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	
	public String getClaimType() {
		return claimType != null ? claimType : "";
	}
	
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	
	public String getIncidentId() {
		return incidentId != null ? incidentId : "";
	}
	
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	
	public String getIncidentType() {
		return incidentType != null ? incidentType : "";
	}
	
	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}
	
	public String getPassengerFirstName() {
		return passengerFirstName != null ? passengerFirstName : "";
	}
	
	public void setPassengerFirstName(String passengerFirstName) {
		this.passengerFirstName = passengerFirstName;
	}
	
	public String getPassengerLastName() {
		return passengerLastName != null ? passengerLastName : "";
	}
	
	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}
	
	public String getPassengerPhoneNumber() {
		return passengerPhoneNumber != null ? passengerPhoneNumber : "";
	}

	public void setPassengerPhoneNumber(String passengerPhoneNumber) {
		this.passengerPhoneNumber = passengerPhoneNumber;
	}

	public String getFoundItemId() {
		return foundItemId != null ? foundItemId : "";
	}

	public void setFoundItemId(String foundItemId) {
		this.foundItemId = foundItemId;
	}

	public String getFoundItemType() {
		return foundItemType != null ? foundItemType : "";
	}

	public void setFoundItemType(String foundItemType) {
		this.foundItemType = foundItemType;
	}

	public String getFoundItemColor() {
		return foundItemColor != null ? foundItemColor : "";
	}

	public void setFoundItemColor(String foundItemColor) {
		this.foundItemColor = foundItemColor;
	}

	public String getFoundItemItem() {
		return foundItemItem != null ? foundItemItem : "";
	}

	public void setFoundItemItem(String foundItemItem) {
		this.foundItemItem = foundItemItem;
	}

	public String getFoundItemDescription() {
		return foundItemDescription != null ? foundItemDescription : "";
	}

	public void setFoundItemDescription(String foundItemDescription) {
		this.foundItemDescription = foundItemDescription;
	}

	public String getFoundItemCaseColor() {
		return foundItemCaseColor != null ? foundItemCaseColor : "";
	}

	public void setFoundItemCaseColor(String foundItemCaseColor) {
		this.foundItemCaseColor = foundItemCaseColor;
	}

	public String getDateToday() {
		return DateUtils.formatDate(new Date(), this.dateFormat, null, null);
	}

}
