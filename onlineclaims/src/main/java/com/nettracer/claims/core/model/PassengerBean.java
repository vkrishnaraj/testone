package com.nettracer.claims.core.model;

import java.util.ArrayList;
import java.util.List;




/**
 * @author Utpal 
 * Description: This is needed for binding the Application Data
 *         with the front end
 */

public class PassengerBean {
	
	
	//Data binding For Passenger Info Page
	private String passengerInfoDescriptiveText;
	private String requiredFieldMessage;
	private String passengerInfoHelp;
	private String permanentAddress;
	private String passengerInfolastName;
	private List<Address> address=new ArrayList<Address>();
	private List<Passenger> passengers=new ArrayList<Passenger>();
	private String personalInformation;
	private String frequentFlyerNumber;
	private String socialSecurityNumber;
	private String passengerInfoSave;
	private String passengerInfoCancel;
	private String passengerInfoForward;
	private String incidentID;
	private Integer bagsTravelWith;
	private Integer lostBag;
	private String bagCheckLocation;
	private Integer noOfPassenger;
	private Boolean recheckBag;
	private Boolean inspectbag;
	private Boolean chargeExcessBaggage;
	private Integer noOfCheckedBag;
	private Integer noOfmissingBag;
	private String bagClaimCheck;
	private Boolean declarePayExcessValue;
	private Double declaredValue;
	private String placeBagChecked;
	private Boolean clearCustomBag;
	private Double bagWeight;
	private Boolean rerouteBag;
	private Boolean differentClaimCheck;
	private String reroutedCityAirline;
	private String reason;
	private Boolean immediateClaimAttempt;
	private String whenBagSeen;
	private String fileReportCity;
	private Boolean reportAnotherAirline;
	private Boolean differentAirlineTicket;
	private String ticketNumber;
	private List<Bag> bagTagList = new ArrayList<Bag>() ; //for datatable for bag tag number in page2
	private String aboutYourItenerary;
	private List<Itinerary> itineraryList= new ArrayList<Itinerary>();
	
	
	//For About Your Flight Page
	private List<Bag> bagList;
	
	
	//For Submit CLaim Page
	private String typeAccept;
	
	public String getPassengerInfoDescriptiveText() {
		return passengerInfoDescriptiveText;
	}
	public void setPassengerInfoDescriptiveText(String passengerInfoDescriptiveText) {
		this.passengerInfoDescriptiveText = passengerInfoDescriptiveText;
	}
	public String getRequiredFieldMessage() {
		return requiredFieldMessage;
	}
	public void setRequiredFieldMessage(String requiredFieldMessage) {
		this.requiredFieldMessage = requiredFieldMessage;
	}
	public String getPassengerInfoHelp() {
		return passengerInfoHelp;
	}
	public void setPassengerInfoHelp(String passengerInfoHelp) {
		this.passengerInfoHelp = passengerInfoHelp;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getPassengerInfolastName() {
		return passengerInfolastName;
	}
	public void setPassengerInfolastName(String passengerInfolastName) {
		this.passengerInfolastName = passengerInfolastName;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public String getPersonalInformation() {
		return personalInformation;
	}
	public void setPersonalInformation(String personalInformation) {
		this.personalInformation = personalInformation;
	}
	public String getFrequentFlyerNumber() {
		return frequentFlyerNumber;
	}
	public void setFrequentFlyerNumber(String frequentFlyerNumber) {
		this.frequentFlyerNumber = frequentFlyerNumber;
	}
	public String getSocialSecurityNumber() {
		return socialSecurityNumber;
	}
	public void setSocialSecurityNumber(String socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}
	public String getPassengerInfoSave() {
		return passengerInfoSave;
	}
	public void setPassengerInfoSave(String passengerInfoSave) {
		this.passengerInfoSave = passengerInfoSave;
	}
	public String getPassengerInfoCancel() {
		return passengerInfoCancel;
	}
	public void setPassengerInfoCancel(String passengerInfoCancel) {
		this.passengerInfoCancel = passengerInfoCancel;
	}
	public String getPassengerInfoForward() {
		return passengerInfoForward;
	}
	public void setPassengerInfoForward(String passengerInfoForward) {
		this.passengerInfoForward = passengerInfoForward;
	}
	public List<Passenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
	public String getIncidentID() {
		return incidentID;
	}
	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}
	public Integer getBagsTravelWith() {
		return bagsTravelWith;
	}
	public void setBagsTravelWith(Integer bagsTravelWith) {
		this.bagsTravelWith = bagsTravelWith;
	}
	public Integer getLostBag() {
		return lostBag;
	}
	public void setLostBag(Integer lostBag) {
		this.lostBag = lostBag;
	}
	public String getBagCheckLocation() {
		return bagCheckLocation;
	}
	public void setBagCheckLocation(String bagCheckLocation) {
		this.bagCheckLocation = bagCheckLocation;
	}
	public Integer getNoOfPassenger() {
		return noOfPassenger;
	}
	public void setNoOfPassenger(Integer noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}
	public Boolean getRecheckBag() {
		return recheckBag;
	}
	public void setRecheckBag(Boolean recheckBag) {
		this.recheckBag = recheckBag;
	}
	public Boolean getInspectbag() {
		return inspectbag;
	}
	public void setInspectbag(Boolean inspectbag) {
		this.inspectbag = inspectbag;
	}
	public Boolean getChargeExcessBaggage() {
		return chargeExcessBaggage;
	}
	public void setChargeExcessBaggage(Boolean chargeExcessBaggage) {
		this.chargeExcessBaggage = chargeExcessBaggage;
	}
	public Integer getNoOfCheckedBag() {
		return noOfCheckedBag;
	}
	public void setNoOfCheckedBag(Integer noOfCheckedBag) {
		this.noOfCheckedBag = noOfCheckedBag;
	}
	public Integer getNoOfmissingBag() {
		return noOfmissingBag;
	}
	public void setNoOfmissingBag(Integer noOfmissingBag) {
		this.noOfmissingBag = noOfmissingBag;
	}
	public String getBagClaimCheck() {
		return bagClaimCheck;
	}
	public void setBagClaimCheck(String bagClaimCheck) {
		this.bagClaimCheck = bagClaimCheck;
	}
	public Boolean getDeclarePayExcessValue() {
		return declarePayExcessValue;
	}
	public void setDeclarePayExcessValue(Boolean declarePayExcessValue) {
		this.declarePayExcessValue = declarePayExcessValue;
	}
	public Double getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(Double declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getPlaceBagChecked() {
		return placeBagChecked;
	}
	public void setPlaceBagChecked(String placeBagChecked) {
		this.placeBagChecked = placeBagChecked;
	}
	public Boolean getClearCustomBag() {
		return clearCustomBag;
	}
	public void setClearCustomBag(Boolean clearCustomBag) {
		this.clearCustomBag = clearCustomBag;
	}
	public Double getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(Double bagWeight) {
		this.bagWeight = bagWeight;
	}
	public Boolean getRerouteBag() {
		return rerouteBag;
	}
	public void setRerouteBag(Boolean rerouteBag) {
		this.rerouteBag = rerouteBag;
	}
	public Boolean getDifferentClaimCheck() {
		return differentClaimCheck;
	}
	public void setDifferentClaimCheck(Boolean differentClaimCheck) {
		this.differentClaimCheck = differentClaimCheck;
	}
	public String getReroutedCityAirline() {
		return reroutedCityAirline;
	}
	public void setReroutedCityAirline(String reroutedCityAirline) {
		this.reroutedCityAirline = reroutedCityAirline;
	}
	public Boolean getImmediateClaimAttempt() {
		return immediateClaimAttempt;
	}
	public void setImmediateClaimAttempt(Boolean immediateClaimAttempt) {
		this.immediateClaimAttempt = immediateClaimAttempt;
	}
	public String getWhenBagSeen() {
		return whenBagSeen;
	}
	public void setWhenBagSeen(String whenBagSeen) {
		this.whenBagSeen = whenBagSeen;
	}
	public String getFileReportCity() {
		return fileReportCity;
	}
	public void setFileReportCity(String fileReportCity) {
		this.fileReportCity = fileReportCity;
	}
	public Boolean getReportAnotherAirline() {
		return reportAnotherAirline;
	}
	public void setReportAnotherAirline(Boolean reportAnotherAirline) {
		this.reportAnotherAirline = reportAnotherAirline;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public List<Bag> getBagTagList() {
		return  bagTagList;
	}
	public void setBagTagList(List<Bag> bagTagList) {
		this.bagTagList = bagTagList;
	}
	public String getAboutYourItenerary() {
		return aboutYourItenerary;
	}
	public void setAboutYourItenerary(String aboutYourItenerary) {
		this.aboutYourItenerary = aboutYourItenerary;
	}
	public List<Itinerary> getItineraryList() {
		return itineraryList;
	}
	public void setItineraryList(List<Itinerary> itineraryList) {
		this.itineraryList = itineraryList;
	}
	public List<Bag> getBagList() {
		return bagList;
	}
	public void setBagList(List<Bag> bagList) {
		this.bagList = bagList;
	}
	public Boolean getDifferentAirlineTicket() {
		return differentAirlineTicket;
	}
	public void setDifferentAirlineTicket(Boolean differentAirlineTicket) {
		this.differentAirlineTicket = differentAirlineTicket;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getTypeAccept() {
		return typeAccept;
	}
	public void setTypeAccept(String typeAccept) {
		this.typeAccept = typeAccept;
	}	
	
	
	
}
