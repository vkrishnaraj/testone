package com.bagnet.nettracer.ws.onlineclaims;

import java.util.Date;

public class Claim {

	private long claimId;
	private int claimType;
	private String accept;
	private String status;
	private String lastName;
	private String firstName;
	private String middleInitial;
	private Address permanentAddress;
	private Address mailingAddress;
	private String emailAddress;
	private String occupation;
	private String businessName;
	private Phone[] phone;
	private Itinerary[] itinerary;
	private Bag[] bag;
	private String frequentFlierNumber;
	private String socialSecurity;
	private int bagsTravelWith;
	private int bagsDelayed;
	private int bagsStillMissing;
	private String checkedLocation;
	private int passengersTravelingWithYou;
	private String whereWasBaggageChecked;
	private boolean haveToRecheck;
	private boolean wasBagInspected;
	private boolean ticketWithAnotherAirline;
	private boolean chargedForExcessBaggage;
	private boolean declaredExcessValue;
	private String declaredValue;
	private boolean bagClearCustoms;
	private boolean baggageReroutedEnRoute;
	private String reroutedAirlineCity;
	private String reroutedReason;
	private boolean differentClaimCheck;
	private boolean attemptToClaimAtArrival;
	private String lastSawBaggage;
	private String whereDidYouFileReport;
	private boolean reportedToAnotherAirline;
	private File[] file;
	private boolean filedPreviousClaim;
	private String filedPreviousAirline;
	private Date filedPrevoiusDate;
	private String filedPreviousClaimant;
	private boolean tsaInspected;
	private boolean tsaNotePresent;
	private String tsaInspectionLocation;
	private String comments;
	private String ticketNumber;
	private String paxClaimDate;
	private String paxClaimAmount;
	private String paxIpAddress;

	public String getPaxClaimDate() {
		return paxClaimDate;
	}

	public void setPaxClaimDate(String paxClaimDate) {
		this.paxClaimDate = paxClaimDate;
	}

	public String getPaxClaimAmount() {
		return paxClaimAmount;
	}

	public void setPaxClaimAmount(String paxClaimAmount) {
		this.paxClaimAmount = paxClaimAmount;
	}

	public String getPaxIpAddress() {
		return paxIpAddress;
	}

	public void setPaxIpAddress(String paxIpAddress) {
		this.paxIpAddress = paxIpAddress;
	}

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

	public Address getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(Address permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public Address getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(Address mailingAddress) {
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

	public Phone[] getPhone() {
		return phone;
	}

	public void setPhone(Phone[] phone) {
		this.phone = phone;
	}

	public Itinerary[] getItinerary() {
		return itinerary;
	}

	public void setItinerary(Itinerary[] itinerary) {
		this.itinerary = itinerary;
	}

	public Bag[] getBag() {
		return bag;
	}

	public void setBag(Bag[] bag) {
		this.bag = bag;
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

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public int getClaimType() {
		return claimType;
	}

	public void setClaimType(int claimType) {
		this.claimType = claimType;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

}
