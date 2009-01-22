package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag;

public final class ClaimSettlementForm extends ActionForm {

	private static final long serialVersionUID = -2233399054963089004L;
	
	// To be populated from incident
	private String incident_ID;
	private String incidentStatus;
	private String incidentItemType;
	private String incidentCreateDate;
	private String matchDetected;
	private String recordLocator;

	// To be populated from object at time of creation
	private String claimSettlementId;
	private String incidentId;

	private String claimAgent;
	private String dateTakeover;
	private String claimType;
	private String firstContact;
	private String secondContact;
	private String pplcSent;
	private String pplcDue;
	private String pplcReceived;
	private String depreciationDue;
	private String depreciationComplete;
	private String offerDue;

	private boolean verifyAddress;
	private boolean verifyPhone;
	private boolean verifyEmail;
	private boolean verifyBagColor;
	private boolean verifyBrand;
	private boolean verifyContents;
	private boolean verifyFraudCC;
	private boolean verifyFraudPhone;
	private boolean verifyFraudName;
	private boolean verifyTrace1;
	private boolean verifyTrace2;
	private boolean verifyTrace3;

	private String comments;
	private String newComment;
	private String pplcVia;

	// Claim Settlement Page 2
	private String lastName;
	private String firstName;
	private int salutation;
	private String address1;
	private String address2;
	private String language;
	private String city;
	private String state_ID;
	private String province;
	private String zip;
	private String countrycode_ID = "US";

	private String homePhone;
	private String businessPhone;
	private String mobilePhone;
	private String pager;
	private String fax;

	private String email;
	private String membership;

	private String offerSent; // date
	private String offerSentVia; // string
	private String totalPaid = "0.00";
	private String totalPaidVouchers = "0.00";
	private String releaseDue; // date
	private String amountClaimed = "0.00";
	private String amountOffered = "0.00";
	private String auditVOOffered = "0.00";
	private String totalPaidCertif = "0.00";
	private String revisitRequested; // date
	private String dateStatusChange; // date
	private String revisitedBy; // 20 characters

	private ArrayList<ClaimSettlementBag> bagList;

	public String getIncident_ID() {
		return incident_ID;
	}

	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	public String getIncidentStatus() {
		return incidentStatus;
	}

	public void setIncidentStatus(String incidentStatus) {
		this.incidentStatus = incidentStatus;
	}

	public String getIncidentItemType() {
		return incidentItemType;
	}

	public void setIncidentItemType(String incidentItemType) {
		this.incidentItemType = incidentItemType;
	}

	public String getIncidentCreateDate() {
		return incidentCreateDate;
	}

	public void setIncidentCreateDate(String incidentCreateDate) {
		this.incidentCreateDate = incidentCreateDate;
	}

	public String getMatchDetected() {
		return matchDetected;
	}

	public void setMatchDetected(String matchDetected) {
		this.matchDetected = matchDetected;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public String getClaimSettlementId() {
		return claimSettlementId;
	}

	public void setClaimSettlementId(String claimSettlementId) {
		this.claimSettlementId = claimSettlementId;
	}

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	public String getClaimAgent() {
		return claimAgent;
	}

	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}

	public String getDateTakeover() {
		return dateTakeover;
	}

	public void setDateTakeover(String dateTakeover) {
		this.dateTakeover = dateTakeover;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public String getFirstContact() {
		return firstContact;
	}

	public void setFirstContact(String firstContact) {
		this.firstContact = firstContact;
	}

	public String getSecondContact() {
		return secondContact;
	}

	public void setSecondContact(String secondContact) {
		this.secondContact = secondContact;
	}

	public String getPplcSent() {
		return pplcSent;
	}

	public void setPplcSent(String pplcSent) {
		this.pplcSent = pplcSent;
	}

	public String getPplcDue() {
		return pplcDue;
	}

	public void setPplcDue(String pplcDue) {
		this.pplcDue = pplcDue;
	}

	public String getPplcReceived() {
		return pplcReceived;
	}

	public void setPplcReceived(String pplcReceived) {
		this.pplcReceived = pplcReceived;
	}

	public String getDepreciationDue() {
		return depreciationDue;
	}

	public void setDepreciationDue(String depreciationDue) {
		this.depreciationDue = depreciationDue;
	}

	public String getDepreciationComplete() {
		return depreciationComplete;
	}

	public void setDepreciationComplete(String depreciationComplete) {
		this.depreciationComplete = depreciationComplete;
	}

	public String getOfferDue() {
		return offerDue;
	}

	public void setOfferDue(String offerDue) {
		this.offerDue = offerDue;
	}

	public boolean isVerifyAddress() {
		return verifyAddress;
	}

	public void setVerifyAddress(boolean verifyAddress) {
		this.verifyAddress = verifyAddress;
	}

	public boolean isVerifyPhone() {
		return verifyPhone;
	}

	public void setVerifyPhone(boolean verifyPhone) {
		this.verifyPhone = verifyPhone;
	}

	public boolean isVerifyEmail() {
		return verifyEmail;
	}

	public void setVerifyEmail(boolean verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	public boolean isVerifyBagColor() {
		return verifyBagColor;
	}

	public void setVerifyBagColor(boolean verifyBagColor) {
		this.verifyBagColor = verifyBagColor;
	}

	public boolean isVerifyBrand() {
		return verifyBrand;
	}

	public void setVerifyBrand(boolean verifyBrand) {
		this.verifyBrand = verifyBrand;
	}

	public boolean isVerifyContents() {
		return verifyContents;
	}

	public void setVerifyContents(boolean verifyContents) {
		this.verifyContents = verifyContents;
	}

	public boolean isVerifyFraudCC() {
		return verifyFraudCC;
	}

	public void setVerifyFraudCC(boolean verifyFraudCC) {
		this.verifyFraudCC = verifyFraudCC;
	}

	public boolean isVerifyFraudPhone() {
		return verifyFraudPhone;
	}

	public void setVerifyFraudPhone(boolean verifyFraudPhone) {
		this.verifyFraudPhone = verifyFraudPhone;
	}

	public boolean isVerifyFraudName() {
		return verifyFraudName;
	}

	public void setVerifyFraudName(boolean verifyFraudName) {
		this.verifyFraudName = verifyFraudName;
	}

	public boolean isVerifyTrace1() {
		return verifyTrace1;
	}

	public void setVerifyTrace1(boolean verifyTrace1) {
		this.verifyTrace1 = verifyTrace1;
	}

	public boolean isVerifyTrace2() {
		return verifyTrace2;
	}

	public void setVerifyTrace2(boolean verifyTrace2) {
		this.verifyTrace2 = verifyTrace2;
	}

	public boolean isVerifyTrace3() {
		return verifyTrace3;
	}

	public void setVerifyTrace3(boolean verifyTrace3) {
		this.verifyTrace3 = verifyTrace3;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public String getPplcVia() {
		return pplcVia;
	}

	public void setPplcVia(String pplcVia) {
		this.pplcVia = pplcVia;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMembership() {
		return membership;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public String getCountrycode_ID() {
		return countrycode_ID;
	}

	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}

	public ArrayList<ClaimSettlementBag> getBagList() {
		return bagList;
	}

	public void setBagList(ArrayList<ClaimSettlementBag> bagList) {
		this.bagList = bagList;
	}

	public String getOfferSent() {
		return offerSent;
	}

	public void setOfferSent(String offerSent) {
		this.offerSent = offerSent;
	}

	public String getOfferSentVia() {
		return offerSentVia;
	}

	public void setOfferSentVia(String offerSentVia) {
		this.offerSentVia = offerSentVia;
	}

	public String getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(String totalPaid) {
		this.totalPaid = totalPaid;
	}

	public String getTotalPaidVouchers() {
		return totalPaidVouchers;
	}

	public void setTotalPaidVouchers(String totalPaidVouchers) {
		this.totalPaidVouchers = totalPaidVouchers;
	}

	public String getReleaseDue() {
		return releaseDue;
	}

	public void setReleaseDue(String releaseDue) {
		this.releaseDue = releaseDue;
	}

	public String getAmountOffered() {
		return amountOffered;
	}

	public void setAmountOffered(String amountOffered) {
		this.amountOffered = amountOffered;
	}

	public String getAuditVOOffered() {
		return auditVOOffered;
	}

	public void setAuditVOOffered(String auditVOOffered) {
		this.auditVOOffered = auditVOOffered;
	}

	public String getTotalPaidCertif() {
		return totalPaidCertif;
	}

	public void setTotalPaidCertif(String totalPaidCertif) {
		this.totalPaidCertif = totalPaidCertif;
	}

	public String getRevisitRequested() {
		return revisitRequested;
	}

	public void setRevisitRequested(String revisitRequested) {
		this.revisitRequested = revisitRequested;
	}

	public String getDateStatusChange() {
		return dateStatusChange;
	}

	public void setDateStatusChange(String dateStatusChange) {
		this.dateStatusChange = dateStatusChange;
	}

	public String getRevisitedBy() {
		return revisitedBy;
	}

	public void setRevisitedBy(String revisitedBy) {
		this.revisitedBy = revisitedBy;
	}

	public String getState_ID() {
		return state_ID;
	}

	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}

	public String getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(String amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public int getSalutation() {
		return salutation;
	}

	public void setSalutation(int salutation) {
		this.salutation = salutation;
	}

}