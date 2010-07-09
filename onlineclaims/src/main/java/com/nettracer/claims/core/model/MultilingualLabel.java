package com.nettracer.claims.core.model;



/**
 * @author Utpal Description: This is needed for binding the multilingual labels
 *         with the front end
 */

public class MultilingualLabel {
	
	//Labels For Passenger Login Page --not in use yet
	private String claimNumber;  
	private String lastName;  
	private String tryDiffImage;
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
	private String phoneHome;
	private String phoneMobile;
	private String phoneFax;
	private String phoneBusiness;
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
	private Long phoneHomeState;
	private Long phoneMobileState;
	private Long phoneFaxState;
	private Long phoneBusinessState;
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
	
	private String flightPrevious;
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
	private String fromToAirports;
	private String airlineFlightNo;
	private String flightDate;
	
	//For Bag Info Page -- step 3 of 6
	private String bagDescriptiveText;
	private String bagRequiredFieldMessage;
	private String bagHelp;
	private String aboutYourBag;
	private String bagNumberLabel;
	private String bagTagNumber;
	private String nameonBag;
	private String brandOftheBag;
	private String externalMarkings;
	private String bagPurchaseDate;
	private String colorChart;
	private String bagChart;
	private String bagColor;
	private String bagType;
	private String selectAppropriate;
	private String hardSided;
	private String wheelsRollers;
	private String feet;
	private String trim;
	private String softSided;
	private String zippers;
	private String retractableHandel;
	private String pockets;
	private String locks;
	private String pullStrap;
	private String nameTag;
	private String ribbonsPersonalMarkings;
	private String descriptionOfContents;
	private String gender;
	private String article;
	private String color;
	private String size;
	private String brandOrDescription;
	private String storePurchased;
	private String purchasedDate;
	private String price;
	private String currency;
	private String addMoreItems;
	
	private Long bagTagNumberState;
	private Long nameonBagState;
	private Long brandOftheBagState;
	private Long externalMarkingsState;
	private Long bagPurchaseDateState;
	private Long bagColorState;
	private Long bagTypeState;

	
	//For FIle Upload Page -- step 4 of 6
	private String fileUploadDescriptiveText;
	private String fileUploadRequiredFieldMessage;
	private String fileUploadHelp;
	private String fileUpload;
	private String fileInstruction;
	private String fileSelection;
	private String receiptsToBeUploaded;
	private String addReceipts;
	private String browse;
	private String remove;
	
	//For Fraud Question -- Step 5 of 6
	private String fraudQuestionDescriptiveText;
	private String fraudQuestionRequiredFieldMessage;
	private String fraudQuestionHelp;
	private String moreClaim;
	private String anotherClaim;
	private String yesAbove;
	private String whichAirline;
	private String dateOfClaim;
	private String claimantName;
	private String tsaInfo;
	private String tsaInspect;
	private String bagConfirmNote;
	private String inspectionPlace;
	private String additionalComments;
	
	private Long anotherClaimState;
	private Long whichAirlineState;
	private Long dateOfClaimState;
	private Long claimantNameState;
	private Long tsaInspectState;
	private Long bagConfirmNoteState;
	private Long inspectionPlaceState;
	private Long additionalCommentsState;
	
	//For Claim Submission -step 6
	private String submitClaimDescriptiveText;
	private String submitClaimHelp;
	private String submitClaimRequiredFieldMessage;
	private String useOfInformation;
	private String reservedRights;
	private String agreement;
	private String typeAccept;
	private String confirmation;
	private String luggageLostDate;
	private String signature;
	private String signedDate;
	private String print;
	private String claimSubmit;
	
	private Long typeAcceptState;
	
	//For saved Screen
	private String savedScreenHelp;
	private String savedMessage;
	private String returnLink;
	
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
	
	public String getPhoneHome() {
		return phoneHome;
	}
	public void setPhoneHome(String phoneHome) {
		this.phoneHome = phoneHome;
	}
	public String getPhoneMobile() {
		return phoneMobile;
	}
	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}
	public String getPhoneFax() {
		return phoneFax;
	}
	public void setPhoneFax(String phoneFax) {
		this.phoneFax = phoneFax;
	}
	public String getPhoneBusiness() {
		return phoneBusiness;
	}
	public void setPhoneBusiness(String phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
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
	
	public Long getPhoneHomeState() {
		return phoneHomeState;
	}
	public void setPhoneHomeState(Long phoneHomeState) {
		this.phoneHomeState = phoneHomeState;
	}
	public Long getPhoneMobileState() {
		return phoneMobileState;
	}
	public void setPhoneMobileState(Long phoneMobileState) {
		this.phoneMobileState = phoneMobileState;
	}
	public Long getPhoneFaxState() {
		return phoneFaxState;
	}
	public void setPhoneFaxState(Long phoneFaxState) {
		this.phoneFaxState = phoneFaxState;
	}
	public Long getPhoneBusinessState() {
		return phoneBusinessState;
	}
	public void setPhoneBusinessState(Long phoneBusinessState) {
		this.phoneBusinessState = phoneBusinessState;
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
	public String getTryDiffImage() {
		return tryDiffImage;
	}
	public void setTryDiffImage(String tryDiffImage) {
		this.tryDiffImage = tryDiffImage;
	}
	public String getFlightPrevious() {
		return flightPrevious;
	}
	public void setFlightPrevious(String flightPrevious) {
		this.flightPrevious = flightPrevious;
	}
	public String getSavedScreenHelp() {
		return savedScreenHelp;
	}
	public void setSavedScreenHelp(String savedScreenHelp) {
		this.savedScreenHelp = savedScreenHelp;
	}
	public String getSavedMessage() {
		return savedMessage;
	}
	public void setSavedMessage(String savedMessage) {
		this.savedMessage = savedMessage;
	}
	public String getReturnLink() {
		return returnLink;
	}
	public void setReturnLink(String returnLink) {
		this.returnLink = returnLink;
	}
	public String getSubmitClaimDescriptiveText() {
		return submitClaimDescriptiveText;
	}
	public void setSubmitClaimDescriptiveText(String submitClaimDescriptiveText) {
		this.submitClaimDescriptiveText = submitClaimDescriptiveText;
	}
	public String getSubmitClaimHelp() {
		return submitClaimHelp;
	}
	public void setSubmitClaimHelp(String submitClaimHelp) {
		this.submitClaimHelp = submitClaimHelp;
	}
	public String getSubmitClaimRequiredFieldMessage() {
		return submitClaimRequiredFieldMessage;
	}
	public void setSubmitClaimRequiredFieldMessage(
			String submitClaimRequiredFieldMessage) {
		this.submitClaimRequiredFieldMessage = submitClaimRequiredFieldMessage;
	}
	
	public String getUseOfInformation() {
		return useOfInformation;
	}
	public void setUseOfInformation(String useOfInformation) {
		this.useOfInformation = useOfInformation;
	}
	public String getReservedRights() {
		return reservedRights;
	}
	public void setReservedRights(String reservedRights) {
		this.reservedRights = reservedRights;
	}
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public String getTypeAccept() {
		return typeAccept;
	}
	public void setTypeAccept(String typeAccept) {
		this.typeAccept = typeAccept;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}
	public String getLuggageLostDate() {
		return luggageLostDate;
	}
	public void setLuggageLostDate(String luggageLostDate) {
		this.luggageLostDate = luggageLostDate;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getSignedDate() {
		return signedDate;
	}
	public void setSignedDate(String signedDate) {
		this.signedDate = signedDate;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public Long getTypeAcceptState() {
		return typeAcceptState;
	}
	public void setTypeAcceptState(Long typeAcceptState) {
		this.typeAcceptState = typeAcceptState;
	}
	public String getClaimSubmit() {
		return claimSubmit;
	}
	public void setClaimSubmit(String claimSubmit) {
		this.claimSubmit = claimSubmit;
	}
	public String getFraudQuestionDescriptiveText() {
		return fraudQuestionDescriptiveText;
	}
	public void setFraudQuestionDescriptiveText(String fraudQuestionDescriptiveText) {
		this.fraudQuestionDescriptiveText = fraudQuestionDescriptiveText;
	}
	public String getFraudQuestionRequiredFieldMessage() {
		return fraudQuestionRequiredFieldMessage;
	}
	public void setFraudQuestionRequiredFieldMessage(
			String fraudQuestionRequiredFieldMessage) {
		this.fraudQuestionRequiredFieldMessage = fraudQuestionRequiredFieldMessage;
	}
	public String getFraudQuestionHelp() {
		return fraudQuestionHelp;
	}
	public void setFraudQuestionHelp(String fraudQuestionHelp) {
		this.fraudQuestionHelp = fraudQuestionHelp;
	}
	public String getMoreClaim() {
		return moreClaim;
	}
	public void setMoreClaim(String moreClaim) {
		this.moreClaim = moreClaim;
	}
	public String getAnotherClaim() {
		return anotherClaim;
	}
	public void setAnotherClaim(String anotherClaim) {
		this.anotherClaim = anotherClaim;
	}
	public String getYesAbove() {
		return yesAbove;
	}
	public void setYesAbove(String yesAbove) {
		this.yesAbove = yesAbove;
	}
	public String getWhichAirline() {
		return whichAirline;
	}
	public void setWhichAirline(String whichAirline) {
		this.whichAirline = whichAirline;
	}
	public String getClaimantName() {
		return claimantName;
	}
	public void setClaimantName(String claimantName) {
		this.claimantName = claimantName;
	}
	public String getTsaInfo() {
		return tsaInfo;
	}
	public void setTsaInfo(String tsaInfo) {
		this.tsaInfo = tsaInfo;
	}
	public String getTsaInspect() {
		return tsaInspect;
	}
	public void setTsaInspect(String tsaInspect) {
		this.tsaInspect = tsaInspect;
	}
	public String getInspectionPlace() {
		return inspectionPlace;
	}
	public void setInspectionPlace(String inspectionPlace) {
		this.inspectionPlace = inspectionPlace;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public String getDateOfClaim() {
		return dateOfClaim;
	}
	public void setDateOfClaim(String dateOfClaim) {
		this.dateOfClaim = dateOfClaim;
	}
	public Long getAnotherClaimState() {
		return anotherClaimState;
	}
	public void setAnotherClaimState(Long anotherClaimState) {
		this.anotherClaimState = anotherClaimState;
	}
	public Long getWhichAirlineState() {
		return whichAirlineState;
	}
	public void setWhichAirlineState(Long whichAirlineState) {
		this.whichAirlineState = whichAirlineState;
	}
	public Long getDateOfClaimState() {
		return dateOfClaimState;
	}
	public void setDateOfClaimState(Long dateOfClaimState) {
		this.dateOfClaimState = dateOfClaimState;
	}
	public Long getClaimantNameState() {
		return claimantNameState;
	}
	public void setClaimantNameState(Long claimantNameState) {
		this.claimantNameState = claimantNameState;
	}
	public Long getTsaInspectState() {
		return tsaInspectState;
	}
	public void setTsaInspectState(Long tsaInspectState) {
		this.tsaInspectState = tsaInspectState;
	}
	public Long getInspectionPlaceState() {
		return inspectionPlaceState;
	}
	public void setInspectionPlaceState(Long inspectionPlaceState) {
		this.inspectionPlaceState = inspectionPlaceState;
	}
	public Long getAdditionalCommentsState() {
		return additionalCommentsState;
	}
	public void setAdditionalCommentsState(Long additionalCommentsState) {
		this.additionalCommentsState = additionalCommentsState;
	}
	public String getBagConfirmNote() {
		return bagConfirmNote;
	}
	public void setBagConfirmNote(String bagConfirmNote) {
		this.bagConfirmNote = bagConfirmNote;
	}
	public Long getBagConfirmNoteState() {
		return bagConfirmNoteState;
	}
	public void setBagConfirmNoteState(Long bagConfirmNoteState) {
		this.bagConfirmNoteState = bagConfirmNoteState;
	}
	public String getFileUploadDescriptiveText() {
		return fileUploadDescriptiveText;
	}
	public void setFileUploadDescriptiveText(String fileUploadDescriptiveText) {
		this.fileUploadDescriptiveText = fileUploadDescriptiveText;
	}
	public String getFileUploadRequiredFieldMessage() {
		return fileUploadRequiredFieldMessage;
	}
	public void setFileUploadRequiredFieldMessage(
			String fileUploadRequiredFieldMessage) {
		this.fileUploadRequiredFieldMessage = fileUploadRequiredFieldMessage;
	}
	public String getFileUploadHelp() {
		return fileUploadHelp;
	}
	public void setFileUploadHelp(String fileUploadHelp) {
		this.fileUploadHelp = fileUploadHelp;
	}
	public String getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(String fileUpload) {
		this.fileUpload = fileUpload;
	}
	public String getFileInstruction() {
		return fileInstruction;
	}
	public void setFileInstruction(String fileInstruction) {
		this.fileInstruction = fileInstruction;
	}
	public String getFileSelection() {
		return fileSelection;
	}
	public void setFileSelection(String fileSelection) {
		this.fileSelection = fileSelection;
	}
	public String getReceiptsToBeUploaded() {
		return receiptsToBeUploaded;
	}
	public void setReceiptsToBeUploaded(String receiptsToBeUploaded) {
		this.receiptsToBeUploaded = receiptsToBeUploaded;
	}
	public String getBrowse() {
		return browse;
	}
	public void setBrowse(String browse) {
		this.browse = browse;
	}
	public String getAddReceipts() {
		return addReceipts;
	}
	public void setAddReceipts(String addReceipts) {
		this.addReceipts = addReceipts;
	}
	public String getRemove() {
		return remove;
	}
	public void setRemove(String remove) {
		this.remove = remove;
	}
	public String getBagDescriptiveText() {
		return bagDescriptiveText;
	}
	public void setBagDescriptiveText(String bagDescriptiveText) {
		this.bagDescriptiveText = bagDescriptiveText;
	}
	public String getBagRequiredFieldMessage() {
		return bagRequiredFieldMessage;
	}
	public void setBagRequiredFieldMessage(String bagRequiredFieldMessage) {
		this.bagRequiredFieldMessage = bagRequiredFieldMessage;
	}
	public String getBagHelp() {
		return bagHelp;
	}
	public void setBagHelp(String bagHelp) {
		this.bagHelp = bagHelp;
	}
	public String getAboutYourBag() {
		return aboutYourBag;
	}
	public void setAboutYourBag(String aboutYourBag) {
		this.aboutYourBag = aboutYourBag;
	}
	public String getBagNumberLabel() {
		return bagNumberLabel;
	}
	public void setBagNumberLabel(String bagNumberLabel) {
		this.bagNumberLabel = bagNumberLabel;
	}
	public String getBagTagNumber() {
		return bagTagNumber;
	}
	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}
	public String getNameonBag() {
		return nameonBag;
	}
	public void setNameonBag(String nameonBag) {
		this.nameonBag = nameonBag;
	}
	public String getBrandOftheBag() {
		return brandOftheBag;
	}
	public void setBrandOftheBag(String brandOftheBag) {
		this.brandOftheBag = brandOftheBag;
	}
	public String getExternalMarkings() {
		return externalMarkings;
	}
	public void setExternalMarkings(String externalMarkings) {
		this.externalMarkings = externalMarkings;
	}
	public String getBagPurchaseDate() {
		return bagPurchaseDate;
	}
	public void setBagPurchaseDate(String bagPurchaseDate) {
		this.bagPurchaseDate = bagPurchaseDate;
	}
	public String getColorChart() {
		return colorChart;
	}
	public void setColorChart(String colorChart) {
		this.colorChart = colorChart;
	}
	public String getBagChart() {
		return bagChart;
	}
	public void setBagChart(String bagChart) {
		this.bagChart = bagChart;
	}
	public String getBagColor() {
		return bagColor;
	}
	public void setBagColor(String bagColor) {
		this.bagColor = bagColor;
	}
	public String getBagType() {
		return bagType;
	}
	public void setBagType(String bagType) {
		this.bagType = bagType;
	}
	public String getSelectAppropriate() {
		return selectAppropriate;
	}
	public void setSelectAppropriate(String selectAppropriate) {
		this.selectAppropriate = selectAppropriate;
	}
	public String getHardSided() {
		return hardSided;
	}
	public void setHardSided(String hardSided) {
		this.hardSided = hardSided;
	}
	public String getWheelsRollers() {
		return wheelsRollers;
	}
	public void setWheelsRollers(String wheelsRollers) {
		this.wheelsRollers = wheelsRollers;
	}
	public String getFeet() {
		return feet;
	}
	public void setFeet(String feet) {
		this.feet = feet;
	}
	public String getTrim() {
		return trim;
	}
	public void setTrim(String trim) {
		this.trim = trim;
	}
	public String getSoftSided() {
		return softSided;
	}
	public void setSoftSided(String softSided) {
		this.softSided = softSided;
	}
	public String getZippers() {
		return zippers;
	}
	public void setZippers(String zippers) {
		this.zippers = zippers;
	}
	public String getRetractableHandel() {
		return retractableHandel;
	}
	public void setRetractableHandel(String retractableHandel) {
		this.retractableHandel = retractableHandel;
	}
	public String getPockets() {
		return pockets;
	}
	public void setPockets(String pockets) {
		this.pockets = pockets;
	}
	public String getLocks() {
		return locks;
	}
	public void setLocks(String locks) {
		this.locks = locks;
	}
	public String getPullStrap() {
		return pullStrap;
	}
	public void setPullStrap(String pullStrap) {
		this.pullStrap = pullStrap;
	}
	public String getNameTag() {
		return nameTag;
	}
	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}
	public String getRibbonsPersonalMarkings() {
		return ribbonsPersonalMarkings;
	}
	public void setRibbonsPersonalMarkings(String ribbonsPersonalMarkings) {
		this.ribbonsPersonalMarkings = ribbonsPersonalMarkings;
	}
	public String getDescriptionOfContents() {
		return descriptionOfContents;
	}
	public void setDescriptionOfContents(String descriptionOfContents) {
		this.descriptionOfContents = descriptionOfContents;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getBrandOrDescription() {
		return brandOrDescription;
	}
	public void setBrandOrDescription(String brandOrDescription) {
		this.brandOrDescription = brandOrDescription;
	}
	public String getStorePurchased() {
		return storePurchased;
	}
	public void setStorePurchased(String storePurchased) {
		this.storePurchased = storePurchased;
	}
	public String getPurchasedDate() {
		return purchasedDate;
	}
	public void setPurchasedDate(String purchasedDate) {
		this.purchasedDate = purchasedDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAddMoreItems() {
		return addMoreItems;
	}
	public void setAddMoreItems(String addMoreItems) {
		this.addMoreItems = addMoreItems;
	}
	public Long getBagTagNumberState() {
		return bagTagNumberState;
	}
	public void setBagTagNumberState(Long bagTagNumberState) {
		this.bagTagNumberState = bagTagNumberState;
	}
	public Long getNameonBagState() {
		return nameonBagState;
	}
	public void setNameonBagState(Long nameonBagState) {
		this.nameonBagState = nameonBagState;
	}
	public Long getBrandOftheBagState() {
		return brandOftheBagState;
	}
	public void setBrandOftheBagState(Long brandOftheBagState) {
		this.brandOftheBagState = brandOftheBagState;
	}
	public Long getExternalMarkingsState() {
		return externalMarkingsState;
	}
	public void setExternalMarkingsState(Long externalMarkingsState) {
		this.externalMarkingsState = externalMarkingsState;
	}
	public Long getBagPurchaseDateState() {
		return bagPurchaseDateState;
	}
	public void setBagPurchaseDateState(Long bagPurchaseDateState) {
		this.bagPurchaseDateState = bagPurchaseDateState;
	}
	public Long getBagColorState() {
		return bagColorState;
	}
	public void setBagColorState(Long bagColorState) {
		this.bagColorState = bagColorState;
	}
	public Long getBagTypeState() {
		return bagTypeState;
	}
	public void setBagTypeState(Long bagTypeState) {
		this.bagTypeState = bagTypeState;
	}  
	
	
}
