package com.bagnet.nettracer.ws.onlineclaims;

import java.util.Date;

public class Claim {

	private long claimId;
	private int claimType;
	private String status;
	private String claimStatus;
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
	private int haveToRecheck;
	private int wasBagInspected;
	private int ticketWithAnotherAirline;
	private int chargedForExcessBaggage;
	private int declaredExcessValue;
	private String declaredCurrency;
	private String declaredValue;
	private int bagClearCustoms;
	private int baggageReroutedEnRoute;
	private String reroutedAirlineCity;
	private String reroutedReason;
	private int differentClaimCheck;
	private int attemptToClaimAtArrival;
	private String lastSawBaggage;
	private String whereDidYouFileReport;
	private int reportedToAnotherAirline;
	private File[] file;
	private int filedPreviousClaim;
	private String filedPreviousAirline;
	private Date filedPrevoiusDate;
	private String filedPreviousClaimant;
	private int tsaInspected;
	private int tsaNotePresent;
	private String tsaInspectionLocation;
	private String comments;
	private String ticketNumber;
	private String paxClaimDate;
	private String paxClaimAmount;
	private String paxIpAddress;
	private Passenger[] passenger;
	private String bagWeight;
	private String paxClaimAmountCurrency;
	private String checkedLocationDescription;
	private String reportedAirline;
	private String reportedCity;
	private String reportedFileNumber;
	private int privateInsurance;
	private String privateInsuranceName;
	private String privateInsuranceAddr;
	private int requestForeignCurrency;
	private String foreignCurrencyEmail;
	private Date bagReceivedDate;
	private String reasonForTravel;
	private String lengthOfStay;
	private Message[] messages;

	public String getReasonForTravel() {
		return reasonForTravel;
	}

	public void setReasonForTravel(String reasonForTravel) {
		this.reasonForTravel = reasonForTravel;
	}

	public String getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(String lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}
	
	public int getRequestForeignCurrency() {
		return requestForeignCurrency;
	}

	public void setRequestForeignCurrency(int requestForeignCurrency) {
		this.requestForeignCurrency = requestForeignCurrency;
	}

	public String getForeignCurrencyEmail() {
		return foreignCurrencyEmail;
	}

	public void setForeignCurrencyEmail(String foreignCurrencyEmail) {
		this.foreignCurrencyEmail = foreignCurrencyEmail;
	}

	public Date getBagReceivedDate() {
		return bagReceivedDate;
	}

	public void setBagReceivedDate(Date bagReceivedDate) {
		this.bagReceivedDate = bagReceivedDate;
	}

	public String getDeclaredCurrency() {
		return declaredCurrency;
	}

	public void setDeclaredCurrency(String declaredCurrency) {
		this.declaredCurrency = declaredCurrency;
	}

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

	public int getHaveToRecheck() {
		return haveToRecheck;
	}

	public void setHaveToRecheck(int haveToRecheck) {
		this.haveToRecheck = haveToRecheck;
	}

	public int getWasBagInspected() {
		return wasBagInspected;
	}

	public void setWasBagInspected(int wasBagInspected) {
		this.wasBagInspected = wasBagInspected;
	}

	public int getTicketWithAnotherAirline() {
		return ticketWithAnotherAirline;
	}

	public void setTicketWithAnotherAirline(int ticketWithAnotherAirline) {
		this.ticketWithAnotherAirline = ticketWithAnotherAirline;
	}

	public int getChargedForExcessBaggage() {
		return chargedForExcessBaggage;
	}

	public void setChargedForExcessBaggage(int chargedForExcessBaggage) {
		this.chargedForExcessBaggage = chargedForExcessBaggage;
	}

	public int getDeclaredExcessValue() {
		return declaredExcessValue;
	}

	public void setDeclaredExcessValue(int declaredExcessValue) {
		this.declaredExcessValue = declaredExcessValue;
	}

	public String getDeclaredValue() {
		return declaredValue;
	}

	public void setDeclaredValue(String declaredValue) {
		this.declaredValue = declaredValue;
	}

	public int getBagClearCustoms() {
		return bagClearCustoms;
	}

	public void setBagClearCustoms(int bagClearCustoms) {
		this.bagClearCustoms = bagClearCustoms;
	}

	public int getBaggageReroutedEnRoute() {
		return baggageReroutedEnRoute;
	}

	public void setBaggageReroutedEnRoute(int baggageReroutedEnRoute) {
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

	public int getDifferentClaimCheck() {
		return differentClaimCheck;
	}

	public void setDifferentClaimCheck(int differentClaimCheck) {
		this.differentClaimCheck = differentClaimCheck;
	}

	public int getAttemptToClaimAtArrival() {
		return attemptToClaimAtArrival;
	}

	public void setAttemptToClaimAtArrival(int attemptToClaimAtArrival) {
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

	public int getReportedToAnotherAirline() {
		return reportedToAnotherAirline;
	}

	public void setReportedToAnotherAirline(int reportedToAnotherAirline) {
		this.reportedToAnotherAirline = reportedToAnotherAirline;
	}

	public int getFiledPreviousClaim() {
		return filedPreviousClaim;
	}

	public void setFiledPreviousClaim(int filedPreviousClaim) {
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

	public int getTsaInspected() {
		return tsaInspected;
	}

	public void setTsaInspected(int tsaInspected) {
		this.tsaInspected = tsaInspected;
	}

	public int getTsaNotePresent() {
		return tsaNotePresent;
	}

	public void setTsaNotePresent(int tsaNotePresent) {
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

	public Passenger[] getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger[] passenger) {
		this.passenger = passenger;
	}

	public String getBagWeight() {
		return bagWeight;
	}

	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}

	public String getPaxClaimAmountCurrency() {
		return paxClaimAmountCurrency;
	}

	public void setPaxClaimAmountCurrency(String paxClaimAmountCurrency) {
		this.paxClaimAmountCurrency = paxClaimAmountCurrency;
	}

	public String getCheckedLocationDescription() {
		return checkedLocationDescription;
	}

	public void setCheckedLocationDescription(String checkedLocationDescription) {
		this.checkedLocationDescription = checkedLocationDescription;
	}

	public String getReportedAirline() {
		return reportedAirline;
	}

	public void setReportedAirline(String reportedAirline) {
		this.reportedAirline = reportedAirline;
	}

	public String getReportedCity() {
		return reportedCity;
	}

	public void setReportedCity(String reportedCity) {
		this.reportedCity = reportedCity;
	}

	public String getReportedFileNumber() {
		return reportedFileNumber;
	}

	public void setReportedFileNumber(String reportedFileNumber) {
		this.reportedFileNumber = reportedFileNumber;
	}

	public int getPrivateInsurance() {
		return privateInsurance;
	}

	public void setPrivateInsurance(int privateInsurance) {
		this.privateInsurance = privateInsurance;
	}

	public String getPrivateInsuranceName() {
		return privateInsuranceName;
	}

	public void setPrivateInsuranceName(String privateInsuranceName) {
		this.privateInsuranceName = privateInsuranceName;
	}

	public String getPrivateInsuranceAddr() {
		return privateInsuranceAddr;
	}

	public void setPrivateInsuranceAddr(String privateInsuranceAddr) {
		this.privateInsuranceAddr = privateInsuranceAddr;
	}

	public Message[] getMessages() {
		return messages;
	}

	public void setMessages(Message[] messages) {
		this.messages = messages;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

}
