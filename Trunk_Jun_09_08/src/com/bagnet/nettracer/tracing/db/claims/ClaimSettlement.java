package com.bagnet.nettracer.tracing.db.claims;

import java.util.Date;

import com.bagnet.nettracer.tracing.db.Incident;

public class ClaimSettlement {
	int claimId;

	private String incident_ID;

	// To be populated from object at time of creation
	// TODO: Determine PK
	private String claimSettlementNumber;

	private Incident incident;

	private String claimAgent;
	private Date dateTakeover;
	private String claimType;
	private Date firstContact;
	private Date secondContact;
	private Date pplcSent;
	private Date pplcDue;
	private Date pplcReceived;
	private Date depreciationDue;
	private Date depreciationComplete;
	private Date offerDue;

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

	private String lastName;
	private String firstName;

	public int getClaimId() {
		return claimId;
	}

	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}

	public String getIncident_ID() {
		return incident_ID;
	}

	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}

	public String getClaimSettlementNumber() {
		return claimSettlementNumber;
	}

	public void setClaimSettlementNumber(String claimSettlementNumber) {
		this.claimSettlementNumber = claimSettlementNumber;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public String getClaimAgent() {
		return claimAgent;
	}

	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}

	public Date getDateTakeover() {
		return dateTakeover;
	}

	public void setDateTakeover(Date dateTakeover) {
		this.dateTakeover = dateTakeover;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public Date getFirstContact() {
		return firstContact;
	}

	public void setFirstContact(Date firstContact) {
		this.firstContact = firstContact;
	}

	public Date getSecondContact() {
		return secondContact;
	}

	public void setSecondContact(Date secondContact) {
		this.secondContact = secondContact;
	}

	public Date getPplcSent() {
		return pplcSent;
	}

	public void setPplcSent(Date pplcSent) {
		this.pplcSent = pplcSent;
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

	public Date getDepreciationComplete() {
		return depreciationComplete;
	}

	public void setDepreciationComplete(Date depreciationComplete) {
		this.depreciationComplete = depreciationComplete;
	}

	public Date getOfferDue() {
		return offerDue;
	}

	public void setOfferDue(Date offerDue) {
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

}
