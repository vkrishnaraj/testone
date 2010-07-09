package com.bagnet.nettracer.tracing.db.onlineclaims;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Incident;

@Entity
@Table(name = "oc_claim")
@Proxy(lazy = false)
public class OnlineClaim {

	@Id
	@GeneratedValue
	private long claimId;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class)
	@JoinColumn(name = "incident_id", nullable = false)
	private Incident incident;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date submitDate;
	
	@Column(length = 20)
	private String accept;

	@Column(length = 20)
	private String status;

	@Column(length = 20)
	private String lastName;

	@Column(length = 20)
	private String firstName;

	@Column(length = 1)
	private String middleInitial;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private OCAddress permanentAddress;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private OCAddress mailingAddress;

	@Column(length = 40)
	private String emailAddress;

	@Column(length = 50)
	private String occupation;

	@Column(length = 50)
	private String businessName;
	
	@OneToMany(mappedBy = "claim", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<OCPhone> phone;
	
	@OneToMany(mappedBy = "claim", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<OCItinerary> itinerary;
	
	@OneToMany(mappedBy = "claim", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "bagId")
	@Fetch(FetchMode.SELECT)
	private Set<OCBag> bag;
	
	/*
	 * Persist method chosen so that the item will be persisted, but never deleted.
	 */
	@OneToMany(mappedBy = "claim", cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	private Set<OCFile> file;
	
	// private Phone[] phone;
	// private Itinerary[] itinerary;
	// private Bag[] bag;
	// private File[] file;

	@Column(length = 20)
	private String frequentFlierNumber;

	@Column(length = 20)
	private String socialSecurity;

	@Basic
	private int bagsTravelWith;

	@Basic
	private int bagsDelayed;

	@Basic
	private int bagsStillMissing;

	@Column(length = 20)
	private String checkedLocation;

	@Basic
	private int passengersTravelingWithYou;

	@Column(length = 20)
	private String whereWasBaggageChecked;

	@Basic
	private boolean haveToRecheck;

	@Basic
	private boolean wasBagInspected;

	@Basic
	private boolean ticketWithAnotherAirline;

	@Basic
	private boolean chargedForExcessBaggage;

	@Basic
	private boolean declaredExcessValue;

	@Column(length = 20)
	private String declaredValue;

	@Column(length = 3)
	private String declaredCurrency;

	@Basic
	private boolean bagClearCustoms;

	@Basic
	private boolean baggageReroutedEnRoute;

	@Column(length = 20)
	private String reroutedAirlineCity;

	@Column(length = 50)
	private String reroutedReason;

	@Basic
	private boolean differentClaimCheck;

	@Basic
	private boolean attemptToClaimAtArrival;

	@Column(length = 50)
	private String lastSawBaggage;

	@Column(length = 50)
	private String whereDidYouFileReport;

	@Basic
	private boolean reportedToAnotherAirline;

	@Basic
	private boolean filedPreviousClaim;

	@Column(length = 50)
	private String filedPreviousAirline;

	@Temporal(TemporalType.DATE)
	private Date filedPrevoiusDate;

	@Column(length = 50)
	private String filedPreviousClaimant;

	@Basic
	private boolean tsaInspected;

	@Basic
	private boolean tsaNotePresent;

	@Column(length = 50)
	private String tsaInspectionLocation;

	@Column(length = 1500)
	private String comments;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public OCAddress getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(OCAddress permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public OCAddress getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(OCAddress mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getFrequentFlierNumber() {
		return frequentFlierNumber;
	}

	public void setFrequentFlierNumber(String frequentFlierNumber) {
		this.frequentFlierNumber = frequentFlierNumber;
	}

	public String getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(String socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public int getBagsTravelWith() {
		return bagsTravelWith;
	}

	public void setBagsTravelWith(int bagsTravelWith) {
		this.bagsTravelWith = bagsTravelWith;
	}

	public int getBagsDelayed() {
		return bagsDelayed;
	}

	public void setBagsDelayed(int bagsDelayed) {
		this.bagsDelayed = bagsDelayed;
	}

	public int getBagsStillMissing() {
		return bagsStillMissing;
	}

	public void setBagsStillMissing(int bagsStillMissing) {
		this.bagsStillMissing = bagsStillMissing;
	}

	public String getCheckedLocation() {
		return checkedLocation;
	}

	public void setCheckedLocation(String checkedLocation) {
		this.checkedLocation = checkedLocation;
	}

	public int getPassengersTravelingWithYou() {
		return passengersTravelingWithYou;
	}

	public void setPassengersTravelingWithYou(int passengersTravelingWithYou) {
		this.passengersTravelingWithYou = passengersTravelingWithYou;
	}

	public String getWhereWasBaggageChecked() {
		return whereWasBaggageChecked;
	}

	public void setWhereWasBaggageChecked(String whereWasBaggageChecked) {
		this.whereWasBaggageChecked = whereWasBaggageChecked;
	}

	public boolean isHaveToRecheck() {
		return haveToRecheck;
	}

	public void setHaveToRecheck(boolean haveToRecheck) {
		this.haveToRecheck = haveToRecheck;
	}

	public boolean isWasBagInspected() {
		return wasBagInspected;
	}

	public void setWasBagInspected(boolean wasBagInspected) {
		this.wasBagInspected = wasBagInspected;
	}

	public boolean isTicketWithAnotherAirline() {
		return ticketWithAnotherAirline;
	}

	public void setTicketWithAnotherAirline(boolean ticketWithAnotherAirline) {
		this.ticketWithAnotherAirline = ticketWithAnotherAirline;
	}

	public boolean isChargedForExcessBaggage() {
		return chargedForExcessBaggage;
	}

	public void setChargedForExcessBaggage(boolean chargedForExcessBaggage) {
		this.chargedForExcessBaggage = chargedForExcessBaggage;
	}

	public boolean isDeclaredExcessValue() {
		return declaredExcessValue;
	}

	public void setDeclaredExcessValue(boolean declaredExcessValue) {
		this.declaredExcessValue = declaredExcessValue;
	}

	public String getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(String declaredValue) {
		this.declaredValue = declaredValue;
	}

	public boolean isBagClearCustoms() {
		return bagClearCustoms;
	}

	public void setBagClearCustoms(boolean bagClearCustoms) {
		this.bagClearCustoms = bagClearCustoms;
	}

	public boolean isBaggageReroutedEnRoute() {
		return baggageReroutedEnRoute;
	}

	public void setBaggageReroutedEnRoute(boolean baggageReroutedEnRoute) {
		this.baggageReroutedEnRoute = baggageReroutedEnRoute;
	}

	public String getReroutedAirlineCity() {
		return reroutedAirlineCity;
	}

	public void setReroutedAirlineCity(String reroutedAirlineCity) {
		this.reroutedAirlineCity = reroutedAirlineCity;
	}

	public String getReroutedReason() {
		return reroutedReason;
	}

	public void setReroutedReason(String reroutedReason) {
		this.reroutedReason = reroutedReason;
	}

	public boolean isDifferentClaimCheck() {
		return differentClaimCheck;
	}

	public void setDifferentClaimCheck(boolean differentClaimCheck) {
		this.differentClaimCheck = differentClaimCheck;
	}

	public boolean isAttemptToClaimAtArrival() {
		return attemptToClaimAtArrival;
	}

	public void setAttemptToClaimAtArrival(boolean attemptToClaimAtArrival) {
		this.attemptToClaimAtArrival = attemptToClaimAtArrival;
	}

	public String getLastSawBaggage() {
		return lastSawBaggage;
	}

	public void setLastSawBaggage(String lastSawBaggage) {
		this.lastSawBaggage = lastSawBaggage;
	}

	public String getWhereDidYouFileReport() {
		return whereDidYouFileReport;
	}

	public void setWhereDidYouFileReport(String whereDidYouFileReport) {
		this.whereDidYouFileReport = whereDidYouFileReport;
	}

	public boolean isReportedToAnotherAirline() {
		return reportedToAnotherAirline;
	}

	public void setReportedToAnotherAirline(boolean reportedToAnotherAirline) {
		this.reportedToAnotherAirline = reportedToAnotherAirline;
	}

	public boolean isFiledPreviousClaim() {
		return filedPreviousClaim;
	}

	public void setFiledPreviousClaim(boolean filedPreviousClaim) {
		this.filedPreviousClaim = filedPreviousClaim;
	}

	public String getFiledPreviousAirline() {
		return filedPreviousAirline;
	}

	public void setFiledPreviousAirline(String filedPreviousAirline) {
		this.filedPreviousAirline = filedPreviousAirline;
	}

	public Date getFiledPrevoiusDate() {
		return filedPrevoiusDate;
	}

	public void setFiledPrevoiusDate(Date filedPrevoiusDate) {
		this.filedPrevoiusDate = filedPrevoiusDate;
	}

	public String getFiledPreviousClaimant() {
		return filedPreviousClaimant;
	}

	public void setFiledPreviousClaimant(String filedPreviousClaimant) {
		this.filedPreviousClaimant = filedPreviousClaimant;
	}

	public boolean isTsaInspected() {
		return tsaInspected;
	}

	public void setTsaInspected(boolean tsaInspected) {
		this.tsaInspected = tsaInspected;
	}

	public boolean isTsaNotePresent() {
		return tsaNotePresent;
	}

	public void setTsaNotePresent(boolean tsaNotePresent) {
		this.tsaNotePresent = tsaNotePresent;
	}

	public String getTsaInspectionLocation() {
		return tsaInspectionLocation;
	}

	public void setTsaInspectionLocation(String tsaInspectionLocation) {
		this.tsaInspectionLocation = tsaInspectionLocation;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public String getDeclaredCurrency() {
		return declaredCurrency;
	}

	public void setDeclaredCurrency(String declaredCurrency) {
		this.declaredCurrency = declaredCurrency;
	}
//
//	public Phone[] getPhone() {
//		return phone;
//	}
//
//	public void setPhone(Phone[] phone) {
//		this.phone = phone;
//	}
//
//	public Itinerary[] getItinerary() {
//		return itinerary;
//	}
//
//	public void setItinerary(Itinerary[] itinerary) {
//		this.itinerary = itinerary;
//	}
//
//	public Bag[] getBag() {
//		return bag;
//	}
//
//	public void setBag(Bag[] bag) {
//		this.bag = bag;
//	}
//
//	public File[] getFile() {
//		return file;
//	}
//
//	public void setFile(File[] file) {
//		this.file = file;
//	}

	public Set<OCPhone> getPhone() {
		return phone;
	}

	public void setPhone(Set<OCPhone> phone) {
		this.phone = phone;
	}

	public Set<OCItinerary> getItinerary() {
		return itinerary;
	}

	public void setItinerary(Set<OCItinerary> itinerary) {
		this.itinerary = itinerary;
	}

	public Set<OCBag> getBag() {
		return bag;
	}

	public void setBag(Set<OCBag> bag) {
		this.bag = bag;
	}

	public Set<OCFile> getFile() {
		return file;
	}

	public void setFile(Set<OCFile> file) {
		this.file = file;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	public enum ClaimStatus {
		NEW, PROCESSING, LOCKED
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}
}
