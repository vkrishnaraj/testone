package aero.nettracer.selfservice.claims;

import java.util.Calendar;

public class Claim {
	private long claimId;
	private int version;
	private ClaimStatus status;
	private Calendar dateStarted;
	private Calendar dateSubmitted;
	private Claimant claimant;

	private boolean chargedForExcessBaggage;
	private int numberOfBagsChecked;
	private int numberOfBagsMissing;
	private boolean declareExcessValue;
	private boolean emailCopy;
	private boolean anyMemberFiledBaggageClaim3Years;
	private String previousClaimAirline;
	private String previousClaimClaimant;
	private String previousClaimDate;
	private float claimAmount;
	private String claimAmountCurrencyType;

	private String signature1;
	private String signature2;
	private Calendar date;

	private Segments[] itinerary;

	// BAG-SPECIFIC
	private CheckedLocation checkedBagLocation;
	private String checkedBagLocationOther;
	private boolean didBagClearCustoms;
	private int estimatedWeight;
	private Measure estimatedWeightType;
	private boolean wasBaggageReroutedOrRecheckedEnroute;
	private boolean wasGivenNewClaimCheck;
	private String cityReroutedIn;
	private String airlineReroutedBy;
	private String reasonRerouted;
	private boolean attemptedClaimImmediatelyUponArrival;
	private String lastSawBag;
	private String atWhatCityFileReport;
	private boolean reportedToAnotherAirline;
	private String reportedCity;
	private String reportedAirline;
	private String airlineReferenceNumber;

	private Baggage[] baggageInformation;

	// Believed not bag-specific
	private boolean havePrivateInsuranceOrCcCoverage;
	private String coverageCompanyNameAndAddress;

	public long getClaimId() {
		return claimId;
	}

	public void setClaimId(long claimId) {
		this.claimId = claimId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ClaimStatus getStatus() {
		return status;
	}

	public void setStatus(ClaimStatus status) {
		this.status = status;
	}

	public Calendar getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Calendar dateStarted) {
		this.dateStarted = dateStarted;
	}

	public Calendar getDateSubmitted() {
		return dateSubmitted;
	}

	public void setDateSubmitted(Calendar dateSubmitted) {
		this.dateSubmitted = dateSubmitted;
	}

	public Claimant getClaimant() {
		return claimant;
	}

	public void setClaimant(Claimant claimant) {
		this.claimant = claimant;
	}

	public boolean isChargedForExcessBaggage() {
		return chargedForExcessBaggage;
	}

	public void setChargedForExcessBaggage(boolean chargedForExcessBaggage) {
		this.chargedForExcessBaggage = chargedForExcessBaggage;
	}

	public int getNumberOfBagsChecked() {
		return numberOfBagsChecked;
	}

	public void setNumberOfBagsChecked(int numberOfBagsChecked) {
		this.numberOfBagsChecked = numberOfBagsChecked;
	}

	public int getNumberOfBagsMissing() {
		return numberOfBagsMissing;
	}

	public void setNumberOfBagsMissing(int numberOfBagsMissing) {
		this.numberOfBagsMissing = numberOfBagsMissing;
	}

	public boolean isDeclareExcessValue() {
		return declareExcessValue;
	}

	public void setDeclareExcessValue(boolean declareExcessValue) {
		this.declareExcessValue = declareExcessValue;
	}

	public boolean isEmailCopy() {
		return emailCopy;
	}

	public void setEmailCopy(boolean emailCopy) {
		this.emailCopy = emailCopy;
	}

	public boolean isAnyMemberFiledBaggageClaim3Years() {
		return anyMemberFiledBaggageClaim3Years;
	}

	public void setAnyMemberFiledBaggageClaim3Years(boolean anyMemberFiledBaggageClaim3Years) {
		this.anyMemberFiledBaggageClaim3Years = anyMemberFiledBaggageClaim3Years;
	}

	public String getPreviousClaimAirline() {
		return previousClaimAirline;
	}

	public void setPreviousClaimAirline(String previousClaimAirline) {
		this.previousClaimAirline = previousClaimAirline;
	}

	public String getPreviousClaimClaimant() {
		return previousClaimClaimant;
	}

	public void setPreviousClaimClaimant(String previousClaimClaimant) {
		this.previousClaimClaimant = previousClaimClaimant;
	}

	public String getPreviousClaimDate() {
		return previousClaimDate;
	}

	public void setPreviousClaimDate(String previousClaimDate) {
		this.previousClaimDate = previousClaimDate;
	}

	public float getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(float claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getClaimAmountCurrencyType() {
		return claimAmountCurrencyType;
	}

	public void setClaimAmountCurrencyType(String claimAmountCurrencyType) {
		this.claimAmountCurrencyType = claimAmountCurrencyType;
	}

	public String getSignature1() {
		return signature1;
	}

	public void setSignature1(String signature1) {
		this.signature1 = signature1;
	}

	public String getSignature2() {
		return signature2;
	}

	public void setSignature2(String signature2) {
		this.signature2 = signature2;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public CheckedLocation getCheckedBagLocation() {
		return checkedBagLocation;
	}

	public void setCheckedBagLocation(CheckedLocation checkedBagLocation) {
		this.checkedBagLocation = checkedBagLocation;
	}

	public String getCheckedBagLocationOther() {
		return checkedBagLocationOther;
	}

	public void setCheckedBagLocationOther(String checkedBagLocationOther) {
		this.checkedBagLocationOther = checkedBagLocationOther;
	}

	public boolean isDidBagClearCustoms() {
		return didBagClearCustoms;
	}

	public void setDidBagClearCustoms(boolean didBagClearCustoms) {
		this.didBagClearCustoms = didBagClearCustoms;
	}

	public int getEstimatedWeight() {
		return estimatedWeight;
	}

	public void setEstimatedWeight(int estimatedWeight) {
		this.estimatedWeight = estimatedWeight;
	}

	public Measure getEstimatedWeightType() {
		return estimatedWeightType;
	}

	public void setEstimatedWeightType(Measure estimatedWeightType) {
		this.estimatedWeightType = estimatedWeightType;
	}

	public boolean isWasBaggageReroutedOrRecheckedEnroute() {
		return wasBaggageReroutedOrRecheckedEnroute;
	}

	public void setWasBaggageReroutedOrRecheckedEnroute(boolean wasBaggageReroutedOrRecheckedEnroute) {
		this.wasBaggageReroutedOrRecheckedEnroute = wasBaggageReroutedOrRecheckedEnroute;
	}

	public boolean isWasGivenNewClaimCheck() {
		return wasGivenNewClaimCheck;
	}

	public void setWasGivenNewClaimCheck(boolean wasGivenNewClaimCheck) {
		this.wasGivenNewClaimCheck = wasGivenNewClaimCheck;
	}

	public String getCityReroutedIn() {
		return cityReroutedIn;
	}

	public void setCityReroutedIn(String cityReroutedIn) {
		this.cityReroutedIn = cityReroutedIn;
	}

	public String getAirlineReroutedBy() {
		return airlineReroutedBy;
	}

	public void setAirlineReroutedBy(String airlineReroutedBy) {
		this.airlineReroutedBy = airlineReroutedBy;
	}

	public String getReasonRerouted() {
		return reasonRerouted;
	}

	public void setReasonRerouted(String reasonRerouted) {
		this.reasonRerouted = reasonRerouted;
	}

	public boolean isAttemptedClaimImmediatelyUponArrival() {
		return attemptedClaimImmediatelyUponArrival;
	}

	public void setAttemptedClaimImmediatelyUponArrival(boolean attemptedClaimImmediatelyUponArrival) {
		this.attemptedClaimImmediatelyUponArrival = attemptedClaimImmediatelyUponArrival;
	}

	public String getLastSawBag() {
		return lastSawBag;
	}

	public void setLastSawBag(String lastSawBag) {
		this.lastSawBag = lastSawBag;
	}

	public String getAtWhatCityFileReport() {
		return atWhatCityFileReport;
	}

	public void setAtWhatCityFileReport(String atWhatCityFileReport) {
		this.atWhatCityFileReport = atWhatCityFileReport;
	}

	public boolean isReportedToAnotherAirline() {
		return reportedToAnotherAirline;
	}

	public void setReportedToAnotherAirline(boolean reportedToAnotherAirline) {
		this.reportedToAnotherAirline = reportedToAnotherAirline;
	}

	public String getReportedCity() {
		return reportedCity;
	}

	public void setReportedCity(String reportedCity) {
		this.reportedCity = reportedCity;
	}

	public String getReportedAirline() {
		return reportedAirline;
	}

	public void setReportedAirline(String reportedAirline) {
		this.reportedAirline = reportedAirline;
	}

	public String getAirlineReferenceNumber() {
		return airlineReferenceNumber;
	}

	public void setAirlineReferenceNumber(String airlineReferenceNumber) {
		this.airlineReferenceNumber = airlineReferenceNumber;
	}

	public boolean isHavePrivateInsuranceOrCcCoverage() {
		return havePrivateInsuranceOrCcCoverage;
	}

	public void setHavePrivateInsuranceOrCcCoverage(boolean havePrivateInsuranceOrCcCoverage) {
		this.havePrivateInsuranceOrCcCoverage = havePrivateInsuranceOrCcCoverage;
	}

	public String getCoverageCompanyNameAndAddress() {
		return coverageCompanyNameAndAddress;
	}

	public void setCoverageCompanyNameAndAddress(String coverageCompanyNameAndAddress) {
		this.coverageCompanyNameAndAddress = coverageCompanyNameAndAddress;
	}

	public Segments[] getItinerary() {
		return itinerary;
	}

	public void setItinerary(Segments[] itinerary) {
		this.itinerary = itinerary;
	}

	public Baggage[] getBaggageInformation() {
		return baggageInformation;
	}

	public void setBaggageInformation(Baggage[] baggageInformation) {
		this.baggageInformation = baggageInformation;
	}

}
