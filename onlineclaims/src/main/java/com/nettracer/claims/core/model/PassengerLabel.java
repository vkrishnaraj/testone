package com.nettracer.claims.core.model;



/**
 * @author Utpal Description: This is needed for binding the Application Data
 *         with the front end
 */

public class PassengerLabel {
	
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
	
	
	
}
