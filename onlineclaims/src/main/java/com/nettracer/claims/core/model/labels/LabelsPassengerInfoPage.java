package com.nettracer.claims.core.model.labels;

import java.util.List;

import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Localetext;

public class LabelsPassengerInfoPage {

	// Passenger Info
	public static final String PAS_LASTN = "LastName";
	public static final String PAS_FIRSTN = "FirstName";
	public static final String PAS_MIDDLEI = "MiddleInitial";
	public static final String PAS_PERMADDR = "Permanent Address";
	public static final String PAS_ADDRONE = "AddressLine1";
	public static final String PAS_ADDRTWO = "AddressLine2";
	public static final String PAS_CITY = "City";
	public static final String PAS_STATE = "State/Region";
	public static final String PAS_ZIP = "Postal Code";
	public static final String PAS_COUNTRY = "Country";
	public static final String PAS_EMAIL = "Email Address";
	public static final String PAS_JOB = "Occupation";
	public static final String PAS_BUSNAME = "Business Name";
	public static final String PAS_MAIL = "MailingAddress";
	public static final String PAS_MAILADDRONE = "Mailing Address Line 1";
	public static final String PAS_MAILADDRTWO = "Mailing Address Line 2";
	public static final String PAS_MAILCITY = "Mailing City";
	public static final String PAS_MAILSTATE = "Mailing State/Region";
	public static final String PAS_MAILZIP = "Mailing Postal Code";
	public static final String PAS_MAILCOUNTRY = "MailingCountry";
	public static final String PAS_VOICE = "Voice Contact";
	public static final String PAS_PHNHOME = "Phone Home";
	public static final String PAS_PHNMOBILE = "Phone Mobile";
	public static final String PAS_PHNFAX = "Phone Fax";
	public static final String PAS_PHNBUSS = "Phone Business";
	public static final String PAS_PERSINFO = "Personal Information";
	public static final String PAS_FREQFLY = "Frequent Flyer Number";
	public static final String PAS_SSN = "Social Security Number";
	public static final String PAS_DESC = "Descriptive text US";
	public static final String PAS_SAVE = "Save";
	public static final String PAS_CANCEL = "Cancel";
	public static final String PAS_FORW = "Forward";
	public static final String PAS_REQMESS = "requiredFieldMessage";
	public static final String PAS_HELP = "Help";
	public static final String PAS_MUSTVALUEFIELD = "You must enter a value for the field";
	public static final String PAS_MUSTVALUEQUES = "You must enter a value for the Question";
	public static final String PAS_VALIDMESS = "Validation Message";
	public static final String PAS_VALUENOGREATER = "Value Can't be greater than";
	public static final String PAS_BAGSTRAVEL = "How many bags did you travel with";
	public static final String PAS_BAGSARRIVE = "How many bags arrived";

	// Labels For Passenger Info Page
	private LabelText lastName;
	private LabelText firstName;
	private LabelText middleInitial;
	private LabelText permanentAddress;
	private LabelText addressLine1;
	private LabelText addressLine2;
	private LabelText city;
	private LabelText stateRegion;
	private LabelText postalCode;
	private LabelText country;
	private LabelText emailAddress;
	private LabelText occupation;
	private LabelText businessName;
	private LabelText mailingAddress;
	private LabelText mailingAddressLine1;
	private LabelText mailingAddressLine2;
	private LabelText mailingCity;
	private LabelText mailingStateRegion;
	private LabelText mailingPostalCode;
	private LabelText mailingCountry;
	private LabelText voiceContact;
	private LabelText phoneHome;
	private LabelText phoneMobile;
	private LabelText phoneFax;
	private LabelText phoneBusiness;
	private LabelText personalInformation;
	private LabelText frequentFlyerNumber;
	private LabelText socialSecurityNumber;
	private LabelText descriptiveText;
	private LabelText save;
	private LabelText cancel;
	private LabelText forward;
	private LabelText requiredFieldMessage;
	private LabelText help;
	private LabelText requiredValueField;
	private LabelText requiredQuestionField;
	private LabelText validationMess;
	private LabelText valueNoGreater;
	private LabelText bagsTraval;
	private LabelText bagsArrive;

	public LabelText getLastName() {
		return lastName;
	}

	public void setLastName(LabelText lastName) {
		this.lastName = lastName;
	}

	public LabelText getFirstName() {
		return firstName;
	}

	public void setFirstName(LabelText firstName) {
		this.firstName = firstName;
	}

	public LabelText getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(LabelText middleInitial) {
		this.middleInitial = middleInitial;
	}

	public LabelText getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(LabelText permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public LabelText getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(LabelText addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public LabelText getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(LabelText addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public LabelText getCity() {
		return city;
	}

	public void setCity(LabelText city) {
		this.city = city;
	}

	public LabelText getStateRegion() {
		return stateRegion;
	}

	public void setStateRegion(LabelText stateRegion) {
		this.stateRegion = stateRegion;
	}

	public LabelText getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(LabelText postalCode) {
		this.postalCode = postalCode;
	}

	public LabelText getCountry() {
		return country;
	}

	public void setCountry(LabelText country) {
		this.country = country;
	}

	public LabelText getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(LabelText emailAddress) {
		this.emailAddress = emailAddress;
	}

	public LabelText getOccupation() {
		return occupation;
	}

	public void setOccupation(LabelText occupation) {
		this.occupation = occupation;
	}

	public LabelText getBusinessName() {
		return businessName;
	}

	public void setBusinessName(LabelText businessName) {
		this.businessName = businessName;
	}

	public LabelText getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(LabelText mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public LabelText getMailingAddressLine1() {
		return mailingAddressLine1;
	}

	public void setMailingAddressLine1(LabelText mailingAddressLine1) {
		this.mailingAddressLine1 = mailingAddressLine1;
	}

	public LabelText getMailingAddressLine2() {
		return mailingAddressLine2;
	}

	public void setMailingAddressLine2(LabelText mailingAddressLine2) {
		this.mailingAddressLine2 = mailingAddressLine2;
	}

	public LabelText getMailingCity() {
		return mailingCity;
	}

	public void setMailingCity(LabelText mailingCity) {
		this.mailingCity = mailingCity;
	}

	public LabelText getMailingStateRegion() {
		return mailingStateRegion;
	}

	public void setMailingStateRegion(LabelText mailingStateRegion) {
		this.mailingStateRegion = mailingStateRegion;
	}

	public LabelText getMailingPostalCode() {
		return mailingPostalCode;
	}

	public void setMailingPostalCode(LabelText mailingPostalCode) {
		this.mailingPostalCode = mailingPostalCode;
	}

	public LabelText getMailingCountry() {
		return mailingCountry;
	}

	public void setMailingCountry(LabelText mailingCountry) {
		this.mailingCountry = mailingCountry;
	}

	public LabelText getVoiceContact() {
		return voiceContact;
	}

	public void setVoiceContact(LabelText voiceContact) {
		this.voiceContact = voiceContact;
	}

	public LabelText getPhoneHome() {
		return phoneHome;
	}

	public void setPhoneHome(LabelText phoneHome) {
		this.phoneHome = phoneHome;
	}

	public LabelText getPhoneMobile() {
		return phoneMobile;
	}

	public void setPhoneMobile(LabelText phoneMobile) {
		this.phoneMobile = phoneMobile;
	}

	public LabelText getPhoneFax() {
		return phoneFax;
	}

	public void setPhoneFax(LabelText phoneFax) {
		this.phoneFax = phoneFax;
	}

	public LabelText getPhoneBusiness() {
		return phoneBusiness;
	}

	public void setPhoneBusiness(LabelText phoneBusiness) {
		this.phoneBusiness = phoneBusiness;
	}

	public LabelText getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(LabelText personalInformation) {
		this.personalInformation = personalInformation;
	}

	public LabelText getFrequentFlyerNumber() {
		return frequentFlyerNumber;
	}

	public void setFrequentFlyerNumber(LabelText frequentFlyerNumber) {
		this.frequentFlyerNumber = frequentFlyerNumber;
	}

	public LabelText getSocialSecurityNumber() {
		return socialSecurityNumber;
	}

	public void setSocialSecurityNumber(LabelText socialSecurityNumber) {
		this.socialSecurityNumber = socialSecurityNumber;
	}

	public LabelText getDescriptiveText() {
		return descriptiveText;
	}

	public void setDescriptiveText(LabelText descriptiveText) {
		this.descriptiveText = descriptiveText;
	}

	public LabelText getSave() {
		return save;
	}

	public void setSave(LabelText save) {
		this.save = save;
	}

	public LabelText getCancel() {
		return cancel;
	}

	public void setCancel(LabelText cancel) {
		this.cancel = cancel;
	}

	public LabelText getForward() {
		return forward;
	}

	public void setForward(LabelText forward) {
		this.forward = forward;
	}

	public LabelText getRequiredFieldMessage() {
		return requiredFieldMessage;
	}

	public void setRequiredFieldMessage(LabelText requiredFieldMessage) {
		this.requiredFieldMessage = requiredFieldMessage;
	}

	public LabelText getHelp() {
		return help;
	}

	public void setHelp(LabelText help) {
		this.help = help;
	}

	public LabelText getRequiredValueField() {
		return requiredValueField;
	}

	public void setRequiredValueField(LabelText requiredValueField) {
		this.requiredValueField = requiredValueField;
	}

	public LabelText getRequiredQuestionField() {
		return requiredQuestionField;
	}

	public void setRequiredQuestionField(LabelText requiredQuestionField) {
		this.requiredQuestionField = requiredQuestionField;
	}

	public LabelText getValidationMess() {
		return validationMess;
	}

	public void setValidationMess(LabelText validationMess) {
		this.validationMess = validationMess;
	}

	public LabelText getValueNoGreater() {
		return valueNoGreater;
	}

	public void setValueNoGreater(LabelText valueNoGreater) {
		this.valueNoGreater = valueNoGreater;
	}

	public LabelText getBagsTraval() {
		return bagsTraval;
	}

	public void setBagsTraval(LabelText bagsTraval) {
		this.bagsTraval = bagsTraval;
	}

	public LabelText getBagsArrive() {
		return bagsArrive;
	}

	public void setBagsArrive(LabelText bagsArrive) {
		this.bagsArrive = bagsArrive;
	}
	
	public LabelsPassengerInfoPage(List<Localetext> localetextList, Long baggageState) {

		for (Localetext localetext : localetextList) {
		
			Label currentLabel = localetext.getLabel();
			
			Long requiredFieldStatus = baggageState == 1L ? currentLabel.getDelayedState()
					: baggageState == 2L ? currentLabel.getPilferedState() 
							: currentLabel.getDamagedState();
					
			if (currentLabel.getLabel().equals(PAS_ADDRONE)) {
				setAddressLine1(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_ADDRTWO)) {
				setAddressLine2(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_BAGSARRIVE)) {
				setBagsArrive(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_BAGSTRAVEL)) {
				setBagsTraval(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_BUSNAME)) {
				setBusinessName(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_CANCEL)) {
				setCancel(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_CITY)) {
				setCity(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_COUNTRY)) {
				setCountry(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_DESC)) {
				setDescriptiveText(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_EMAIL)) {
				setEmailAddress(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_FIRSTN)) {
				setFirstName(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_FORW)) {
				setForward(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_FREQFLY)) {
				setFrequentFlyerNumber(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_HELP)) {
				setHelp(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_JOB)) {
				setOccupation(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_LASTN)) {
				setLastName(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAIL)) {
				setMailingAddress(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAILADDRONE)) {
				setMailingAddressLine1(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAILADDRTWO)) {
				setMailingAddressLine2(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAILCITY)) {
				setMailingCity(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAILCOUNTRY)) {
				setMailingCountry(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAILSTATE)) {
				setMailingStateRegion(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MAILZIP)) {
				setMailingPostalCode(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MIDDLEI)) {
				setMiddleInitial(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MUSTVALUEFIELD)) {
				setRequiredValueField(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_MUSTVALUEQUES)) {
				setRequiredQuestionField(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_PERMADDR)) {
				setPermanentAddress(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_PERSINFO)) {
				setPersonalInformation(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_PHNBUSS)) {
				setPhoneBusiness(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_PHNFAX)) {
				setPhoneFax(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_PHNHOME)) {
				setPhoneHome(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_PHNMOBILE)) {
				setPhoneMobile(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_REQMESS)) {
				setRequiredFieldMessage(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_SAVE)) {
				setSave(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_SSN)) {
				setSocialSecurityNumber(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_STATE)) {
				setStateRegion(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_VALIDMESS)) {
				setValidationMess(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_VALUENOGREATER)) {
				setValueNoGreater(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_VOICE)) {
				setVoiceContact(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			} else if (currentLabel.getLabel().equals(PAS_ZIP)) {
				setPostalCode(new LabelText(localetext.getDisplayText(), requiredFieldStatus));
			}
			
		}
	}

}
