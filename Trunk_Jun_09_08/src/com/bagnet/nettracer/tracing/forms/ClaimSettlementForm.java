package com.bagnet.nettracer.tracing.forms;

import java.util.Date;
import java.util.Set;

import org.apache.struts.action.ActionForm;

import com.bagnet.nettracer.tracing.db.ClaimSettlementBag;

public final class ClaimSettlementForm extends ActionForm {

	// All Pages
	int claimSettlementId;
	String incident_id;

	// Claims Process
	String recordLocator;
	Date incidentCreateDate;
	Date dateTakeOver;
	Date contactNumber1;
	Date contactNumber2;

	boolean address;
	boolean phone;
	boolean email;

	Date pplcSent;
	String pplcSentVia;
	Date pplcDue;
	Date pplcReceived;
	Date depreciationDue;
	Date deprecationCom;
	Date offerDue;

	boolean ccFraudCheck;
	boolean phoneFraudCheck;
	boolean nameFraudCheck;

	boolean checkTraceResults1;
	boolean checkTraceResults2;
	boolean checkTraceResults3;

	String bagMatch;

	String comments;

	// Customer Information

	String lastName;
	String firstName;
	String homePhone;
	String businessPhone;
	String cellPhone;
	String fax;
	String emailAddress;

	String streetAddress;
	String apptSuite;
	String city;
	String state;
	String province;
	String zip;
	String country;
	String mailingLabel;

	// Baggage Information
	boolean bagcolortype;
	boolean brand;
	boolean contents;
	Set<ClaimSettlementBag> bags;

	// Claim Disposition
	Date offerSent;
	String offerSentVia;
	Date releaseDue;
	Date revisitRequested;
	String revisitedBy;
	double amountClaimed;
	double amountOffered;
	double additionalVoOffered;
	double totalPaidUSD;
	double totalPaidVouchers;
	double totalPaidTravelCert;

	String claimStatus;
	Date dateStatusChange;

	public int getClaimSettlementId() {
		return claimSettlementId;
	}

	public void setClaimSettlementId(int claimSettlementId) {
		this.claimSettlementId = claimSettlementId;
	}

	public String getIncident_id() {
		return incident_id;
	}

	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}

	public String getRecordLocator() {
		return recordLocator;
	}

	public void setRecordLocator(String recordLocator) {
		this.recordLocator = recordLocator;
	}

	public Date getIncidentCreateDate() {
		return incidentCreateDate;
	}

	public void setIncidentCreateDate(Date incidentCreateDate) {
		this.incidentCreateDate = incidentCreateDate;
	}

	public Date getDateTakeOver() {
		return dateTakeOver;
	}

	public void setDateTakeOver(Date dateTakeOver) {
		this.dateTakeOver = dateTakeOver;
	}

	public Date getContactNumber1() {
		return contactNumber1;
	}

	public void setContactNumber1(Date contactNumber1) {
		this.contactNumber1 = contactNumber1;
	}

	public Date getContactNumber2() {
		return contactNumber2;
	}

	public void setContactNumber2(Date contactNumber2) {
		this.contactNumber2 = contactNumber2;
	}

	public boolean isAddress() {
		return address;
	}

	public void setAddress(boolean address) {
		this.address = address;
	}

	public boolean isPhone() {
		return phone;
	}

	public void setPhone(boolean phone) {
		this.phone = phone;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public Date getPplcSent() {
		return pplcSent;
	}

	public void setPplcSent(Date pplcSent) {
		this.pplcSent = pplcSent;
	}

	public String getPplcSentVia() {
		return pplcSentVia;
	}

	public void setPplcSentVia(String pplcSentVia) {
		this.pplcSentVia = pplcSentVia;
	}

	public Date getPplcDue() {
		return pplcDue;
	}

	public void setPplcDue(Date pplcDue) {
		this.pplcDue = pplcDue;
	}

	public Date getPplcReceived() {
		return pplcReceived;
	}

	public void setPplcReceived(Date pplcReceived) {
		this.pplcReceived = pplcReceived;
	}

	public Date getDepreciationDue() {
		return depreciationDue;
	}

	public void setDepreciationDue(Date depreciationDue) {
		this.depreciationDue = depreciationDue;
	}

	public Date getDeprecationCom() {
		return deprecationCom;
	}

	public void setDeprecationCom(Date deprecationCom) {
		this.deprecationCom = deprecationCom;
	}

	public Date getOfferDue() {
		return offerDue;
	}

	public void setOfferDue(Date offerDue) {
		this.offerDue = offerDue;
	}

	public boolean isCcFraudCheck() {
		return ccFraudCheck;
	}

	public void setCcFraudCheck(boolean ccFraudCheck) {
		this.ccFraudCheck = ccFraudCheck;
	}

	public boolean isPhoneFraudCheck() {
		return phoneFraudCheck;
	}

	public void setPhoneFraudCheck(boolean phoneFraudCheck) {
		this.phoneFraudCheck = phoneFraudCheck;
	}

	public boolean isNameFraudCheck() {
		return nameFraudCheck;
	}

	public void setNameFraudCheck(boolean nameFraudCheck) {
		this.nameFraudCheck = nameFraudCheck;
	}

	public boolean isCheckTraceResults1() {
		return checkTraceResults1;
	}

	public void setCheckTraceResults1(boolean checkTraceResults1) {
		this.checkTraceResults1 = checkTraceResults1;
	}

	public boolean isCheckTraceResults2() {
		return checkTraceResults2;
	}

	public void setCheckTraceResults2(boolean checkTraceResults2) {
		this.checkTraceResults2 = checkTraceResults2;
	}

	public boolean isCheckTraceResults3() {
		return checkTraceResults3;
	}

	public void setCheckTraceResults3(boolean checkTraceResults3) {
		this.checkTraceResults3 = checkTraceResults3;
	}

	public String getBagMatch() {
		return bagMatch;
	}

	public void setBagMatch(String bagMatch) {
		this.bagMatch = bagMatch;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getApptSuite() {
		return apptSuite;
	}

	public void setApptSuite(String apptSuite) {
		this.apptSuite = apptSuite;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMailingLabel() {
		return mailingLabel;
	}

	public void setMailingLabel(String mailingLabel) {
		this.mailingLabel = mailingLabel;
	}

	public boolean isBagcolortype() {
		return bagcolortype;
	}

	public void setBagcolortype(boolean bagcolortype) {
		this.bagcolortype = bagcolortype;
	}

	public boolean isBrand() {
		return brand;
	}

	public void setBrand(boolean brand) {
		this.brand = brand;
	}

	public boolean isContents() {
		return contents;
	}

	public void setContents(boolean contents) {
		this.contents = contents;
	}

	public Set<ClaimSettlementBag> getBags() {
		return bags;
	}

	public void setBags(Set<ClaimSettlementBag> bags) {
		this.bags = bags;
	}

	public Date getOfferSent() {
		return offerSent;
	}

	public void setOfferSent(Date offerSent) {
		this.offerSent = offerSent;
	}

	public String getOfferSentVia() {
		return offerSentVia;
	}

	public void setOfferSentVia(String offerSentVia) {
		this.offerSentVia = offerSentVia;
	}

	public Date getReleaseDue() {
		return releaseDue;
	}

	public void setReleaseDue(Date releaseDue) {
		this.releaseDue = releaseDue;
	}

	public Date getRevisitRequested() {
		return revisitRequested;
	}

	public void setRevisitRequested(Date revisitRequested) {
		this.revisitRequested = revisitRequested;
	}

	public String getRevisitedBy() {
		return revisitedBy;
	}

	public void setRevisitedBy(String revisitedBy) {
		this.revisitedBy = revisitedBy;
	}

	public double getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public double getAmountOffered() {
		return amountOffered;
	}

	public void setAmountOffered(double amountOffered) {
		this.amountOffered = amountOffered;
	}

	public double getAdditionalVoOffered() {
		return additionalVoOffered;
	}

	public void setAdditionalVoOffered(double additionalVoOffered) {
		this.additionalVoOffered = additionalVoOffered;
	}

	public double getTotalPaidUSD() {
		return totalPaidUSD;
	}

	public void setTotalPaidUSD(double totalPaidUSD) {
		this.totalPaidUSD = totalPaidUSD;
	}

	public double getTotalPaidVouchers() {
		return totalPaidVouchers;
	}

	public void setTotalPaidVouchers(double totalPaidVouchers) {
		this.totalPaidVouchers = totalPaidVouchers;
	}

	public double getTotalPaidTravelCert() {
		return totalPaidTravelCert;
	}

	public void setTotalPaidTravelCert(double totalPaidTravelCert) {
		this.totalPaidTravelCert = totalPaidTravelCert;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public Date getDateStatusChange() {
		return dateStatusChange;
	}

	public void setDateStatusChange(Date dateStatusChange) {
		this.dateStatusChange = dateStatusChange;
	}

}