package com.bagnet.nettracer.tracing.db.claims;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Incident;

@Entity
@Table(name = "z_b6_claim_settlement")
@Proxy(lazy = false)
public class ClaimSettlement {

	@Id
	@GeneratedValue
	private long claimSettlementId;

	@Column(name = "address1", length = 40)
	private String address1;

	@Column(name = "address2", length = 40)
	private String address2;

	@OneToMany(mappedBy = "claimSettlement", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@IndexColumn(name="position")
	@Fetch(FetchMode.SELECT)
	private List<ClaimSettlementBag> bagList;

	@Column(length = 25)
	private String businessPhone;

	@Column(length = 50)
	private String city;

	@Column(length = 25)
	private String claimAgent;

	@Column(length = 25)
	private String claimType;

	@Basic
	private String comments;

	@Column(length = 2)
	private String countryCode_ID;

	@Basic
	private Date dateStatusChange;

	@Basic
	private Date dateTakeover;

	@Basic
	private Date depreciationComplete;

	@Basic
	private Date depreciationDue;

	@Column(length = 50)
	private String email;

	@Column(length = 25)
	private String fax;

	@Basic
	private Date firstContact;

	@Column(length = 20)
	private String firstName;

	@Column(length = 25)
	private String homePhone;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class)
	@JoinColumn(name = "incident_id", nullable = false)
	private Incident incident;

	@Column(length = 25)
	private String language;

	@Column(length = 20)
	private String lastName;

	@Column(length = 20)
	private String membership;

	@Column(length = 25)
	private String mobilePhone;

	@Basic
	private String newComment;

	@Basic
	private Date offerDue;

	@Basic
	private Date offerSent;

	@Column(length = 20)
	private String offerSentVia;

	@Column(length = 25)
	private String pager;

	@Basic
	private Date pplcDue;

	@Basic
	private Date pplcReceived;

	@Column(name = "pplcSent")
	private Date pplcSent;

	@Column(length = 20)
	private String pplcVia;

	@Column(length = 100)
	private String province;

	@Basic
	private Date releaseDue;

	@Column(length = 20)
	private String revisitedBy;

	@Column(name = "revisitRequested")
	private Date revisitRequested;

	@Basic
	private int salutation;

	@Column(name = "secondContact")
	private Date secondContact;

	@Column(length = 2)
	private String state_ID;

	@Basic
	private boolean verifyAddress;

	@Basic
	private boolean verifyBagColor;

	@Basic
	private boolean verifyBrand;

	@Basic
	private boolean verifyContents;

	@Basic
	private boolean verifyEmail;

	@Basic
	private boolean verifyFraudCC;

	@Basic
	private boolean verifyFraudName;

	@Basic
	private boolean verifyFraudPhone;

	@Basic
	private boolean verifyPhone;

	@Basic
	private boolean verifyTrace1;

	@Basic
	private boolean verifyTrace2;

	@Basic
	private boolean verifyTrace3;

	@Column(length = 9)
	private String zip;
	
	@Basic
	private double overall_weight;

	public double getOverall_weight() {
		return overall_weight;
	}

	public void setOverall_weight(double overall_weight) {
		this.overall_weight = overall_weight;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public String getCity() {
		return city;
	}

	public String getClaimAgent() {
		return claimAgent;
	}

	public String getClaimType() {
		return claimType;
	}

	public String getComments() {
		return comments;
	}

	public String getCountryCode_ID() {
		return countryCode_ID;
	}

	public Date getDateStatusChange() {
		return dateStatusChange;
	}

	public Date getDateTakeover() {
		return dateTakeover;
	}

	public Date getDepreciationComplete() {
		return depreciationComplete;
	}

	public Date getDepreciationDue() {
		return depreciationDue;
	}

	public String getEmail() {
		return email;
	}

	public String getFax() {
		return fax;
	}

	public Date getFirstContact() {
		return firstContact;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public Incident getIncident() {
		return incident;
	}

	public String getLanguage() {
		return language;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMembership() {
		return membership;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public String getNewComment() {
		return newComment;
	}

	public Date getOfferDue() {
		return offerDue;
	}

	public Date getOfferSent() {
		return offerSent;
	}

	public String getOfferSentVia() {
		return offerSentVia;
	}

	public String getPager() {
		return pager;
	}

	public Date getPplcDue() {
		return pplcDue;
	}

	public Date getPplcReceived() {
		return pplcReceived;
	}

	public Date getPplcSent() {
		return pplcSent;
	}

	public String getPplcVia() {
		return pplcVia;
	}

	public String getProvince() {
		return province;
	}

	public Date getReleaseDue() {
		return releaseDue;
	}

	public String getRevisitedBy() {
		return revisitedBy;
	}

	public Date getRevisitRequested() {
		return revisitRequested;
	}

	public Date getSecondContact() {
		return secondContact;
	}

	public String getState_ID() {
		return state_ID;
	}

	public String getZip() {
		return zip;
	}

	public boolean isVerifyAddress() {
		return verifyAddress;
	}

	public boolean isVerifyBagColor() {
		return verifyBagColor;
	}

	public boolean isVerifyBrand() {
		return verifyBrand;
	}

	public boolean isVerifyContents() {
		return verifyContents;
	}

	public boolean isVerifyEmail() {
		return verifyEmail;
	}

	public boolean isVerifyFraudCC() {
		return verifyFraudCC;
	}

	public boolean isVerifyFraudName() {
		return verifyFraudName;
	}

	public boolean isVerifyFraudPhone() {
		return verifyFraudPhone;
	}

	public boolean isVerifyPhone() {
		return verifyPhone;
	}

	public boolean isVerifyTrace1() {
		return verifyTrace1;
	}

	public boolean isVerifyTrace2() {
		return verifyTrace2;
	}

	public boolean isVerifyTrace3() {
		return verifyTrace3;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCountryCode_ID(String countryCode_ID) {
		this.countryCode_ID = countryCode_ID;
	}

	public void setDateStatusChange(Date dateStatusChange) {
		this.dateStatusChange = dateStatusChange;
	}

	public void setDateTakeover(Date dateTakeover) {
		this.dateTakeover = dateTakeover;
	}

	public void setDepreciationComplete(Date depreciationComplete) {
		this.depreciationComplete = depreciationComplete;
	}

	public void setDepreciationDue(Date depreciationDue) {
		this.depreciationDue = depreciationDue;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setFirstContact(Date firstContact) {
		this.firstContact = firstContact;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMembership(String membership) {
		this.membership = membership;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public void setOfferDue(Date offerDue) {
		this.offerDue = offerDue;
	}

	public void setOfferSent(Date offerSent) {
		this.offerSent = offerSent;
	}

	public void setOfferSentVia(String offerSentVia) {
		this.offerSentVia = offerSentVia;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	public void setPplcDue(Date pplcDue) {
		this.pplcDue = pplcDue;
	}

	public void setPplcReceived(Date pplcReceived) {
		this.pplcReceived = pplcReceived;
	}

	public void setPplcSent(Date pplcSent) {
		this.pplcSent = pplcSent;
	}

	public void setPplcVia(String pplcVia) {
		this.pplcVia = pplcVia;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setReleaseDue(Date releaseDue) {
		this.releaseDue = releaseDue;
	}

	public void setRevisitedBy(String revisitedBy) {
		this.revisitedBy = revisitedBy;
	}

	public void setRevisitRequested(Date revisitRequested) {
		this.revisitRequested = revisitRequested;
	}

	public void setSecondContact(Date secondContact) {
		this.secondContact = secondContact;
	}

	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}

	public void setVerifyAddress(boolean verifyAddress) {
		this.verifyAddress = verifyAddress;
	}

	public void setVerifyBagColor(boolean verifyBagColor) {
		this.verifyBagColor = verifyBagColor;
	}

	public void setVerifyBrand(boolean verifyBrand) {
		this.verifyBrand = verifyBrand;
	}

	public void setVerifyContents(boolean verifyContents) {
		this.verifyContents = verifyContents;
	}

	public void setVerifyEmail(boolean verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	public void setVerifyFraudCC(boolean verifyFraudCC) {
		this.verifyFraudCC = verifyFraudCC;
	}

	public void setVerifyFraudName(boolean verifyFraudName) {
		this.verifyFraudName = verifyFraudName;
	}

	public void setVerifyFraudPhone(boolean verifyFraudPhone) {
		this.verifyFraudPhone = verifyFraudPhone;
	}

	public void setVerifyPhone(boolean verifyPhone) {
		this.verifyPhone = verifyPhone;
	}

	public void setVerifyTrace1(boolean verifyTrace1) {
		this.verifyTrace1 = verifyTrace1;
	}

	public void setVerifyTrace2(boolean verifyTrace2) {
		this.verifyTrace2 = verifyTrace2;
	}

	public void setVerifyTrace3(boolean verifyTrace3) {
		this.verifyTrace3 = verifyTrace3;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public int getSalutation() {
		return salutation;
	}

	public void setSalutation(int salutation) {
		this.salutation = salutation;
	}

	public long getClaimSettlementId() {
		return claimSettlementId;
	}

	public void setClaimSettlementId(long claimSettlementId) {
		this.claimSettlementId = claimSettlementId;
	}

	public List<ClaimSettlementBag> getBagList() {
		return bagList;
	}

	public void setBagList(List<ClaimSettlementBag> bagList) {
		this.bagList = bagList;
	}
	
	public ClaimSettlement() {}


}
