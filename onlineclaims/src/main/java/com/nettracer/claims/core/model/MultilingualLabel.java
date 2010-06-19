package com.nettracer.claims.core.model;



/**
 * @author Utpal Description: This is needed for binding the multilingual labels
 *         with the front end
 */

public class MultilingualLabel {
	
	//Labels For Passenger Login Page --not in use yet
	private String claimNumber;  
	private String lastName;  
	private String captchaText;  
	private String continueButton;  
	private String descriptiveText;
	
	//Labels For Passenger Info Page
	private String passengerInfoDescriptiveText;
	private String requiredFieldMessage;
	private String passengerInfoHelp;
	private String permanentAddress;
	private String passengerInfolastName;
	private String firstName;
	private String middleInitial;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String stateRegion;
	private String postalCode;
	private String country;
	private String emailAddress;
	private String occupation;
	private String businessName;
	private String mailingAddress;
	private String mailingAddressLine1;
	private String mailingAddressLine2;
	private String mailingCity;
	private String mailingStateRegion;
	private String mailingPostalCode;
	private String mailingCountry;
	private String voiceContact;
	private String phone1;
	private String phone2;
	private String phone3;
	private String phone4;
	private String personalInformation;
	private String frequentFlyerNumber;
	private String socialSecurityNumber;
	private String passengerInfoSave;
	private String passengerInfoCancel;
	private String passengerInfoForward;
	
	//required Fields For Passenger Info Page
	private Long lastNameState;
	private Long firstNameState;
	private Long middleInitialState;
	private Long addressLine1State;
	private Long addressLine2State;
	private Long cityState;
	private Long stateRegionState;
	private Long postalCodeState;
	private Long countryState;
	private Long emailAddressState;
	private Long occupationState;
	private Long businessNameState;
	private Long mailingAddressLine1State;
	private Long mailingAddressLine2State;
	private Long mailingCityState;
	private Long mailingStateRegionState;
	private Long mailingPostalCodeState;
	private Long mailingCountryState;
	private Long phone1State;
	private Long phone2State;
	private Long phone3State;
	private Long phone4State;
	private Long frequentFlyerNumberState;
	private Long socialSecurityNumberState;
	
	//Labels For Flight Info Page
	private String flightDescriptiveText;
	private String flightRequiredFieldMessage;
	private String flightHelp;
	private String aboutYourFlight;
	private String totalTravelBag;
	private String noOfLostBag;
	private String bagCheckedLocation;
	private String noOfPassenger;
	//questions
	private String recheckBag;
	private String inspectbag;
	private String chargeExcessBaggage;
	private String bagNumber;
	private String missingBag;
	private String bagClaimCheck;
	private String declarePayExcessValue;
	private String declaredValue;
	private String placeBagChecked;
	private String clearCustomBag;
	private String bagWeight;
	private String rerouteBag;
	private String differentClaimCheck;
	private String reroutedCityAirline;
	private String reason;
	private String immediateClaimAttempt;
	private String whenBagSeen;
	private String fileReportCity;
	private String reportAnotherAirline;
	//ticket info
	private String aboutYourTicket;
	private String differentAirlineTicket;
	private String ticketNumber;
	private String bagTagNumbers;
	private String bagArrival;
	private String flightYes;
	private String flightNo;
	
	private String itenerary;
	private String fromToAirports;
	private String airlineFlightNo;
	private String flightDate;
	/*private String flightSave;
	private String flightCancel;
	private String flightForward;*/
	
	//required Fields For Flight Page
	private Long totalTravelBagState;
	private Long noOfLostBagState;
	private Long bagCheckedLocationState;
	private Long noOfPassengerState;
	private Long recheckBagState;
	private Long inspectbagState;
	private Long chargeExcessBaggageState;
	private Long bagNumberState;
	private Long missingBagState;
	private Long bagClaimCheckState;
	private Long declarePayExcessValueState;
	private Long declaredValueState;
	private Long placeBagCheckedState;
	private Long clearCustomBagState;
	private Long bagWeightState;
	private Long rerouteBagState;
	private Long differentClaimCheckState;
	private Long reroutedCityAirlineState;
	private Long reasonState;
	private Long immediateClaimAttemptState;
	private Long whenBagSeenState;
	private Long fileReportCityState;
	private Long reportAnotherAirlineState;
	private Long differentAirlineTicketState;
	private Long ticketNumberState;
	private Long bagTagNumbersState;
	private Long bagArrivalState;
	private Long fromToAirportsState;
	private Long airlineFlightNoState;
	private Long flightDateState;
	
	//itenerary
	private String aboutYourItenerary;
	
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCaptchaText() {
		return captchaText;
	}
	public void setCaptchaText(String captchaText) {
		this.captchaText = captchaText;
	}
	public String getContinueButton() {
		return continueButton;
	}
	public void setContinueButton(String continueButton) {
		this.continueButton = continueButton;
	}
	public String getDescriptiveText() {
		return descriptiveText;
	}
	public void setDescriptiveText(String descriptiveText) {
		this.descriptiveText = descriptiveText;
	}
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
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateRegion() {
		return stateRegion;
	}
	public void setStateRegion(String stateRegion) {
		this.stateRegion = stateRegion;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public String getMailingAddressLine1() {
		return mailingAddressLine1;
	}
	public void setMailingAddressLine1(String mailingAddressLine1) {
		this.mailingAddressLine1 = mailingAddressLine1;
	}
	public String getMailingAddressLine2() {
		return mailingAddressLine2;
	}
	public void setMailingAddressLine2(String mailingAddressLine2) {
		this.mailingAddressLine2 = mailingAddressLine2;
	}
	public String getMailingCity() {
		return mailingCity;
	}
	public void setMailingCity(String mailingCity) {
		this.mailingCity = mailingCity;
	}
	public String getMailingStateRegion() {
		return mailingStateRegion;
	}
	public void setMailingStateRegion(String mailingStateRegion) {
		this.mailingStateRegion = mailingStateRegion;
	}
	public String getMailingPostalCode() {
		return mailingPostalCode;
	}
	public void setMailingPostalCode(String mailingPostalCode) {
		this.mailingPostalCode = mailingPostalCode;
	}
	public String getMailingCountry() {
		return mailingCountry;
	}
	public void setMailingCountry(String mailingCountry) {
		this.mailingCountry = mailingCountry;
	}
	public String getVoiceContact() {
		return voiceContact;
	}
	public void setVoiceContact(String voiceContact) {
		this.voiceContact = voiceContact;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getPhone4() {
		return phone4;
	}
	public void setPhone4(String phone4) {
		this.phone4 = phone4;
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
	public Long getLastNameState() {
		return lastNameState;
	}
	public void setLastNameState(Long lastNameState) {
		this.lastNameState = lastNameState;
	}
	public Long getFirstNameState() {
		return firstNameState;
	}
	public void setFirstNameState(Long firstNameState) {
		this.firstNameState = firstNameState;
	}
	public Long getMiddleInitialState() {
		return middleInitialState;
	}
	public void setMiddleInitialState(Long middleInitialState) {
		this.middleInitialState = middleInitialState;
	}
	public Long getAddressLine1State() {
		return addressLine1State;
	}
	public void setAddressLine1State(Long addressLine1State) {
		this.addressLine1State = addressLine1State;
	}
	public Long getAddressLine2State() {
		return addressLine2State;
	}
	public void setAddressLine2State(Long addressLine2State) {
		this.addressLine2State = addressLine2State;
	}
	public Long getCityState() {
		return cityState;
	}
	public void setCityState(Long cityState) {
		this.cityState = cityState;
	}
	public Long getStateRegionState() {
		return stateRegionState;
	}
	public void setStateRegionState(Long stateRegionState) {
		this.stateRegionState = stateRegionState;
	}
	public Long getPostalCodeState() {
		return postalCodeState;
	}
	public void setPostalCodeState(Long postalCodeState) {
		this.postalCodeState = postalCodeState;
	}
	public Long getCountryState() {
		return countryState;
	}
	public void setCountryState(Long countryState) {
		this.countryState = countryState;
	}
	public Long getEmailAddressState() {
		return emailAddressState;
	}
	public void setEmailAddressState(Long emailAddressState) {
		this.emailAddressState = emailAddressState;
	}
	public Long getOccupationState() {
		return occupationState;
	}
	public void setOccupationState(Long occupationState) {
		this.occupationState = occupationState;
	}
	public Long getBusinessNameState() {
		return businessNameState;
	}
	public void setBusinessNameState(Long businessNameState) {
		this.businessNameState = businessNameState;
	}
	public Long getMailingAddressLine1State() {
		return mailingAddressLine1State;
	}
	public void setMailingAddressLine1State(
			Long mailingAddressLine1State) {
		this.mailingAddressLine1State = mailingAddressLine1State;
	}
	public Long getMailingAddressLine2State() {
		return mailingAddressLine2State;
	}
	public void setMailingAddressLine2State(
			Long mailingAddressLine2State) {
		this.mailingAddressLine2State = mailingAddressLine2State;
	}
	public Long getMailingCityState() {
		return mailingCityState;
	}
	public void setMailingCityState(Long mailingCityState) {
		this.mailingCityState = mailingCityState;
	}
	public Long getMailingStateRegionState() {
		return mailingStateRegionState;
	}
	public void setMailingStateRegionState(
			Long mailingStateRegionState) {
		this.mailingStateRegionState = mailingStateRegionState;
	}
	public Long getMailingPostalCodeState() {
		return mailingPostalCodeState;
	}
	public void setMailingPostalCodeState(Long mailingPostalCodeState) {
		this.mailingPostalCodeState = mailingPostalCodeState;
	}
	public Long getMailingCountryState() {
		return mailingCountryState;
	}
	public void setMailingCountryState(Long mailingCountryState) {
		this.mailingCountryState = mailingCountryState;
	}
	public Long getPhone1State() {
		return phone1State;
	}
	public void setPhone1State(Long phone1State) {
		this.phone1State = phone1State;
	}
	public Long getPhone2State() {
		return phone2State;
	}
	public void setPhone2State(Long phone2State) {
		this.phone2State = phone2State;
	}
	public Long getPhone3State() {
		return phone3State;
	}
	public void setPhone3State(Long phone3State) {
		this.phone3State = phone3State;
	}
	public Long getPhone4State() {
		return phone4State;
	}
	public void setPhone4State(Long phone4State) {
		this.phone4State = phone4State;
	}
	public Long getFrequentFlyerNumberState() {
		return frequentFlyerNumberState;
	}
	public void setFrequentFlyerNumberState(
			Long frequentFlyerNumberState) {
		this.frequentFlyerNumberState = frequentFlyerNumberState;
	}
	public Long getSocialSecurityNumberState() {
		return socialSecurityNumberState;
	}
	public void setSocialSecurityNumberState(
			Long socialSecurityNumberState) {
		this.socialSecurityNumberState = socialSecurityNumberState;
	}
	public String getFlightDescriptiveText() {
		return flightDescriptiveText;
	}
	public void setFlightDescriptiveText(String flightDescriptiveText) {
		this.flightDescriptiveText = flightDescriptiveText;
	}
	public String getFlightRequiredFieldMessage() {
		return flightRequiredFieldMessage;
	}
	public void setFlightRequiredFieldMessage(String flightRequiredFieldMessage) {
		this.flightRequiredFieldMessage = flightRequiredFieldMessage;
	}
	public String getFlightHelp() {
		return flightHelp;
	}
	public void setFlightHelp(String flightHelp) {
		this.flightHelp = flightHelp;
	}
	public String getAboutYourFlight() {
		return aboutYourFlight;
	}
	public void setAboutYourFlight(String aboutYourFlight) {
		this.aboutYourFlight = aboutYourFlight;
	}
	public String getTotalTravelBag() {
		return totalTravelBag;
	}
	public void setTotalTravelBag(String totalTravelBag) {
		this.totalTravelBag = totalTravelBag;
	}
	public String getNoOfLostBag() {
		return noOfLostBag;
	}
	public void setNoOfLostBag(String noOfLostBag) {
		this.noOfLostBag = noOfLostBag;
	}
	public String getBagCheckedLocation() {
		return bagCheckedLocation;
	}
	public void setBagCheckedLocation(String bagCheckedLocation) {
		this.bagCheckedLocation = bagCheckedLocation;
	}
	public String getNoOfPassenger() {
		return noOfPassenger;
	}
	public void setNoOfPassenger(String noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}
	public String getRecheckBag() {
		return recheckBag;
	}
	public void setRecheckBag(String recheckBag) {
		this.recheckBag = recheckBag;
	}
	public String getInspectbag() {
		return inspectbag;
	}
	public void setInspectbag(String inspectbag) {
		this.inspectbag = inspectbag;
	}
	public String getChargeExcessBaggage() {
		return chargeExcessBaggage;
	}
	public void setChargeExcessBaggage(String chargeExcessBaggage) {
		this.chargeExcessBaggage = chargeExcessBaggage;
	}
	public String getBagNumber() {
		return bagNumber;
	}
	public void setBagNumber(String bagNumber) {
		this.bagNumber = bagNumber;
	}
	public String getMissingBag() {
		return missingBag;
	}
	public void setMissingBag(String missingBag) {
		this.missingBag = missingBag;
	}
	public String getBagClaimCheck() {
		return bagClaimCheck;
	}
	public void setBagClaimCheck(String bagClaimCheck) {
		this.bagClaimCheck = bagClaimCheck;
	}
	public String getDeclarePayExcessValue() {
		return declarePayExcessValue;
	}
	public void setDeclarePayExcessValue(String declarePayExcessValue) {
		this.declarePayExcessValue = declarePayExcessValue;
	}
	public String getDeclaredValue() {
		return declaredValue;
	}
	public void setDeclaredValue(String declaredValue) {
		this.declaredValue = declaredValue;
	}
	public String getPlaceBagChecked() {
		return placeBagChecked;
	}
	public void setPlaceBagChecked(String placeBagChecked) {
		this.placeBagChecked = placeBagChecked;
	}
	public String getClearCustomBag() {
		return clearCustomBag;
	}
	public void setClearCustomBag(String clearCustomBag) {
		this.clearCustomBag = clearCustomBag;
	}
	public String getBagWeight() {
		return bagWeight;
	}
	public void setBagWeight(String bagWeight) {
		this.bagWeight = bagWeight;
	}
	public String getRerouteBag() {
		return rerouteBag;
	}
	public void setRerouteBag(String rerouteBag) {
		this.rerouteBag = rerouteBag;
	}
	public String getDifferentClaimCheck() {
		return differentClaimCheck;
	}
	public void setDifferentClaimCheck(String differentClaimCheck) {
		this.differentClaimCheck = differentClaimCheck;
	}
	public String getReroutedCityAirline() {
		return reroutedCityAirline;
	}
	public void setReroutedCityAirline(String reroutedCityAirline) {
		this.reroutedCityAirline = reroutedCityAirline;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getImmediateClaimAttempt() {
		return immediateClaimAttempt;
	}
	public void setImmediateClaimAttempt(String immediateClaimAttempt) {
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
	public String getReportAnotherAirline() {
		return reportAnotherAirline;
	}
	public void setReportAnotherAirline(String reportAnotherAirline) {
		this.reportAnotherAirline = reportAnotherAirline;
	}
	public String getAboutYourTicket() {
		return aboutYourTicket;
	}
	public void setAboutYourTicket(String aboutYourTicket) {
		this.aboutYourTicket = aboutYourTicket;
	}
	public String getDifferentAirlineTicket() {
		return differentAirlineTicket;
	}
	public void setDifferentAirlineTicket(String differentAirlineTicket) {
		this.differentAirlineTicket = differentAirlineTicket;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getBagTagNumbers() {
		return bagTagNumbers;
	}
	public void setBagTagNumbers(String bagTagNumbers) {
		this.bagTagNumbers = bagTagNumbers;
	}
	public String getBagArrival() {
		return bagArrival;
	}
	public void setBagArrival(String bagArrival) {
		this.bagArrival = bagArrival;
	}
	public String getFlightYes() {
		return flightYes;
	}
	public void setFlightYes(String flightYes) {
		this.flightYes = flightYes;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getItenerary() {
		return itenerary;
	}
	public void setItenerary(String itenerary) {
		this.itenerary = itenerary;
	}
	public String getFromToAirports() {
		return fromToAirports;
	}
	public void setFromToAirports(String fromToAirports) {
		this.fromToAirports = fromToAirports;
	}
	public String getAirlineFlightNo() {
		return airlineFlightNo;
	}
	public void setAirlineFlightNo(String airlineFlightNo) {
		this.airlineFlightNo = airlineFlightNo;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public Long getTotalTravelBagState() {
		return totalTravelBagState;
	}
	public void setTotalTravelBagState(Long totalTravelBagState) {
		this.totalTravelBagState = totalTravelBagState;
	}
	public Long getNoOfLostBagState() {
		return noOfLostBagState;
	}
	public void setNoOfLostBagState(Long noOfLostBagState) {
		this.noOfLostBagState = noOfLostBagState;
	}
	public Long getBagCheckedLocationState() {
		return bagCheckedLocationState;
	}
	public void setBagCheckedLocationState(Long bagCheckedLocationState) {
		this.bagCheckedLocationState = bagCheckedLocationState;
	}
	public Long getNoOfPassengerState() {
		return noOfPassengerState;
	}
	public void setNoOfPassengerState(Long noOfPassengerState) {
		this.noOfPassengerState = noOfPassengerState;
	}
	public Long getRecheckBagState() {
		return recheckBagState;
	}
	public void setRecheckBagState(Long recheckBagState) {
		this.recheckBagState = recheckBagState;
	}
	public Long getInspectbagState() {
		return inspectbagState;
	}
	public void setInspectbagState(Long inspectbagState) {
		this.inspectbagState = inspectbagState;
	}
	public Long getChargeExcessBaggageState() {
		return chargeExcessBaggageState;
	}
	public void setChargeExcessBaggageState(Long chargeExcessBaggageState) {
		this.chargeExcessBaggageState = chargeExcessBaggageState;
	}
	public Long getBagNumberState() {
		return bagNumberState;
	}
	public void setBagNumberState(Long bagNumberState) {
		this.bagNumberState = bagNumberState;
	}
	public Long getMissingBagState() {
		return missingBagState;
	}
	public void setMissingBagState(Long missingBagState) {
		this.missingBagState = missingBagState;
	}
	public Long getBagClaimCheckState() {
		return bagClaimCheckState;
	}
	public void setBagClaimCheckState(Long bagClaimCheckState) {
		this.bagClaimCheckState = bagClaimCheckState;
	}
	public Long getDeclarePayExcessValueState() {
		return declarePayExcessValueState;
	}
	public void setDeclarePayExcessValueState(Long declarePayExcessValueState) {
		this.declarePayExcessValueState = declarePayExcessValueState;
	}
	public Long getDeclaredValueState() {
		return declaredValueState;
	}
	public void setDeclaredValueState(Long declaredValueState) {
		this.declaredValueState = declaredValueState;
	}
	public Long getPlaceBagCheckedState() {
		return placeBagCheckedState;
	}
	public void setPlaceBagCheckedState(Long placeBagCheckedState) {
		this.placeBagCheckedState = placeBagCheckedState;
	}
	public Long getClearCustomBagState() {
		return clearCustomBagState;
	}
	public void setClearCustomBagState(Long clearCustomBagState) {
		this.clearCustomBagState = clearCustomBagState;
	}
	public Long getBagWeightState() {
		return bagWeightState;
	}
	public void setBagWeightState(Long bagWeightState) {
		this.bagWeightState = bagWeightState;
	}
	public Long getRerouteBagState() {
		return rerouteBagState;
	}
	public void setRerouteBagState(Long rerouteBagState) {
		this.rerouteBagState = rerouteBagState;
	}
	public Long getDifferentClaimCheckState() {
		return differentClaimCheckState;
	}
	public void setDifferentClaimCheckState(Long differentClaimCheckState) {
		this.differentClaimCheckState = differentClaimCheckState;
	}
	public Long getReroutedCityAirlineState() {
		return reroutedCityAirlineState;
	}
	public void setReroutedCityAirlineState(Long reroutedCityAirlineState) {
		this.reroutedCityAirlineState = reroutedCityAirlineState;
	}
	public Long getReasonState() {
		return reasonState;
	}
	public void setReasonState(Long reasonState) {
		this.reasonState = reasonState;
	}
	public Long getImmediateClaimAttemptState() {
		return immediateClaimAttemptState;
	}
	public void setImmediateClaimAttemptState(Long immediateClaimAttemptState) {
		this.immediateClaimAttemptState = immediateClaimAttemptState;
	}
	public Long getWhenBagSeenState() {
		return whenBagSeenState;
	}
	public void setWhenBagSeenState(Long whenBagSeenState) {
		this.whenBagSeenState = whenBagSeenState;
	}
	public Long getFileReportCityState() {
		return fileReportCityState;
	}
	public void setFileReportCityState(Long fileReportCityState) {
		this.fileReportCityState = fileReportCityState;
	}
	public Long getReportAnotherAirlineState() {
		return reportAnotherAirlineState;
	}
	public void setReportAnotherAirlineState(Long reportAnotherAirlineState) {
		this.reportAnotherAirlineState = reportAnotherAirlineState;
	}
	public Long getDifferentAirlineTicketState() {
		return differentAirlineTicketState;
	}
	public void setDifferentAirlineTicketState(Long differentAirlineTicketState) {
		this.differentAirlineTicketState = differentAirlineTicketState;
	}
	public Long getTicketNumberState() {
		return ticketNumberState;
	}
	public void setTicketNumberState(Long ticketNumberState) {
		this.ticketNumberState = ticketNumberState;
	}
	public Long getBagTagNumbersState() {
		return bagTagNumbersState;
	}
	public void setBagTagNumbersState(Long bagTagNumbersState) {
		this.bagTagNumbersState = bagTagNumbersState;
	}
	public Long getBagArrivalState() {
		return bagArrivalState;
	}
	public void setBagArrivalState(Long bagArrivalState) {
		this.bagArrivalState = bagArrivalState;
	}
	public Long getFromToAirportsState() {
		return fromToAirportsState;
	}
	public void setFromToAirportsState(Long fromToAirportsState) {
		this.fromToAirportsState = fromToAirportsState;
	}
	public Long getAirlineFlightNoState() {
		return airlineFlightNoState;
	}
	public void setAirlineFlightNoState(Long airlineFlightNoState) {
		this.airlineFlightNoState = airlineFlightNoState;
	}
	public Long getFlightDateState() {
		return flightDateState;
	}
	public void setFlightDateState(Long flightDateState) {
		this.flightDateState = flightDateState;
	}
	public String getAboutYourItenerary() {
		return aboutYourItenerary;
	}
	public void setAboutYourItenerary(String aboutYourItenerary) {
		this.aboutYourItenerary = aboutYourItenerary;
	}  
	
	
}
