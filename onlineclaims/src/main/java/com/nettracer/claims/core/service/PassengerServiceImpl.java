package com.nettracer.claims.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.AdminDao;
import com.nettracer.claims.core.dao.PassengerDao;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;

/**
 * @author David Antosh
 * @author utpal.patra
 * @Date: Nov 4, 2009 Description : Provides Services by calling Dao methods.
 *        Can be called from the Controllers.
 */
@Service
public class PassengerServiceImpl implements PassengerService {
	//private static Logger logger = Logger.getLogger(RequiredFieldsServiceImpl.class);
	
	@Autowired
	private PassengerDao passengerDao;
	
	@Autowired
	private AdminDao adminDao;

	@Override
	public List<Localetext> getPassengerLoginContents(String languageSelected)
			throws SimplePersistenceException {
		return (List<Localetext>)passengerDao.getPassengerLoginContents(languageSelected);
	}

	public void setPassengerDao(PassengerDao passengerDao) {
		this.passengerDao = passengerDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	@Override
	public List<Localetext> getPassengerDirection(String selectedLanguage)
			throws SimplePersistenceException {
		
		return passengerDao.getPassengerDirection(selectedLanguage);
	}

	@Override
	public MultilingualLabel getPassengerInfo(String selectedLanguage,Long baggageState)
			throws SimplePersistenceException {
		MultilingualLabel multilingualLabel=new MultilingualLabel();
		List<Localetext> localetextList=passengerDao.getPassengerInfo(selectedLanguage);
		if(localetextList != null && localetextList.size() >0){

			for(Localetext localetext:localetextList){
				
				Long requiredFieldStatus =  baggageState == 1L ? localetext.getLabel().getDelayedState() 
						: baggageState == 2L ? localetext.getLabel().getPilferedState()
								: localetext.getLabel().getDamagedState(); 
				if(localetext.getLabel().getLabel().contains("requiredFieldMessage")){
					multilingualLabel.setRequiredFieldMessage(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Permanent")){
					multilingualLabel.setPermanentAddress(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Help")){
					multilingualLabel.setPassengerInfoHelp(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Descriptive")){
					multilingualLabel.setPassengerInfoDescriptiveText(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Last")){
					multilingualLabel.setPassengerInfolastName(localetext.getDisplayText());
					multilingualLabel.setLastNameState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("First")){
					multilingualLabel.setFirstName(localetext.getDisplayText());
					multilingualLabel.setFirstNameState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("Middle")){
					multilingualLabel.setMiddleInitial(localetext.getDisplayText());
					multilingualLabel.setMiddleInitialState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("AddressLine1")){
					multilingualLabel.setAddressLine1(localetext.getDisplayText());
					multilingualLabel.setAddressLine1State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("AddressLine2")){
					multilingualLabel.setAddressLine2(localetext.getDisplayText());
					multilingualLabel.setAddressLine2State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("City")){
					multilingualLabel.setCity(localetext.getDisplayText());
					multilingualLabel.setCityState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("State/Region")){
					multilingualLabel.setStateRegion(localetext.getDisplayText());
					multilingualLabel.setStateRegionState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Postal Code")){
					multilingualLabel.setPostalCode(localetext.getDisplayText());
					multilingualLabel.setPostalCodeState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("Email")){
					multilingualLabel.setEmailAddress(localetext.getDisplayText());
					multilingualLabel.setEmailAddressState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Country")){
					multilingualLabel.setCountry(localetext.getDisplayText());
					multilingualLabel.setCountryState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("Occupation")){
					multilingualLabel.setOccupation(localetext.getDisplayText());
					multilingualLabel.setOccupationState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("Business")){
					multilingualLabel.setBusinessName(localetext.getDisplayText());
					multilingualLabel.setBusinessNameState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("MailingAddress")){
					multilingualLabel.setMailingAddress(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("Mailing Address Line 1")){
					multilingualLabel.setMailingAddressLine1(localetext.getDisplayText());
					multilingualLabel.setMailingAddressLine1State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().contains("Mailing Address Line 2")){
					multilingualLabel.setMailingAddressLine2(localetext.getDisplayText());
					multilingualLabel.setMailingAddressLine2State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Mailing City")){
					multilingualLabel.setMailingCity(localetext.getDisplayText());
					multilingualLabel.setMailingCityState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Mailing State/Region")){
					multilingualLabel.setMailingStateRegion(localetext.getDisplayText());
					multilingualLabel.setMailingStateRegionState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Mailing Postal Code")){
					multilingualLabel.setMailingPostalCode(localetext.getDisplayText());
					multilingualLabel.setMailingPostalCodeState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("MailingCountry")){
					multilingualLabel.setMailingCountry(localetext.getDisplayText());
					multilingualLabel.setMailingCountryState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Voice Contact")){
					multilingualLabel.setVoiceContact(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Phone 1")){
					multilingualLabel.setPhone1(localetext.getDisplayText());
					multilingualLabel.setPhone1State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Phone 2")){
					multilingualLabel.setPhone2(localetext.getDisplayText());
					multilingualLabel.setPhone2State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Phone 3")){
					multilingualLabel.setPhone3(localetext.getDisplayText());
					multilingualLabel.setPhone3State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Phone 4")){
					multilingualLabel.setPhone4(localetext.getDisplayText());
					multilingualLabel.setPhone4State(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Personal Information")){
					multilingualLabel.setPersonalInformation(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Frequent Flyer Number")){
					multilingualLabel.setFrequentFlyerNumber(localetext.getDisplayText());
					multilingualLabel.setFrequentFlyerNumberState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Social Security Number")){
					multilingualLabel.setSocialSecurityNumber(localetext.getDisplayText());
					multilingualLabel.setSocialSecurityNumberState(requiredFieldStatus);
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Save")){
					multilingualLabel.setPassengerInfoSave(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Cancel")){
					multilingualLabel.setPassengerInfoCancel(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().equalsIgnoreCase("Forward")){
					multilingualLabel.setPassengerInfoForward(localetext.getDisplayText());
				}
				
			}
		}else{
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public MultilingualLabel getFlightLabels(String selectedLanguage)
			throws SimplePersistenceException {
		MultilingualLabel multilingualLabel=new MultilingualLabel();
		List<Localetext> localetextList=passengerDao.getFlightLabels(selectedLanguage);
		if(localetextList != null){
			for(Localetext localetext:localetextList){
				if(localetext.getLabel().getLabel().contains("flightDescriptiveText")){
					multilingualLabel.setFlightDescriptiveText(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("flightRequiredFieldMessage")){
					multilingualLabel.setFlightRequiredFieldMessage(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("flightHelp")){
					multilingualLabel.setFlightHelp(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("aboutYourFlight")){
					multilingualLabel.setAboutYourFlight(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("totalTravelBag")){
					multilingualLabel.setTotalTravelBag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("noOfLostBag")){
					multilingualLabel.setNoOfLostBag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("bagCheckedLocation")){
					multilingualLabel.setBagCheckedLocation(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("noOfPassenger")){
					multilingualLabel.setNoOfPassenger(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("recheckBag")){
					multilingualLabel.setRecheckBag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("inspectbag")){
					multilingualLabel.setInspectbag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("chargeExcessBaggage")){
					multilingualLabel.setChargeExcessBaggage(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("bagNumber")){
					multilingualLabel.setBagNumber(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("missingBag")){
					multilingualLabel.setMissingBag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("bagClaimCheck")){
					multilingualLabel.setBagClaimCheck(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("declarePayExcessValue")){
					multilingualLabel.setDeclarePayExcessValue(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("declaredValue")){
					multilingualLabel.setDeclaredValue(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("placeBagChecked")){
					multilingualLabel.setPlaceBagChecked(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("clearCustomBag")){
					multilingualLabel.setClearCustomBag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("bagWeight")){
					multilingualLabel.setBagWeight(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("rerouteBag")){
					multilingualLabel.setRerouteBag(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("differentClaimCheck")){
					multilingualLabel.setDifferentClaimCheck(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("reroutedCityAirline")){
					multilingualLabel.setReroutedCityAirline(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("reason")){
					multilingualLabel.setReason(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("immediateClaimAttempt")){
					multilingualLabel.setImmediateClaimAttempt(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("whenBagSeen")){
					multilingualLabel.setWhenBagSeen(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("fileReportCity")){
					multilingualLabel.setFileReportCity(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("reportAnotherAirline")){
					multilingualLabel.setReportAnotherAirline(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("aboutYourTicket")){
					multilingualLabel.setAboutYourTicket(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("differentAirlineTicket")){
					multilingualLabel.setDifferentAirlineTicket(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("ticketNumber")){
					multilingualLabel.setTicketNumber(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("bagTagNumbers")){
					multilingualLabel.setBagTagNumbers(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("bagArrival")){
					multilingualLabel.setBagArrival(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("flightYes")){
					multilingualLabel.setFlightYes(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("flightNo")){
					multilingualLabel.setFlightNo(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("itenerary")){
					multilingualLabel.setItenerary(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("fromToAirports")){
					multilingualLabel.setFromToAirports(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("airlineFlightNo")){
					multilingualLabel.setAirlineFlightNo(localetext.getDisplayText());
				}else if(localetext.getLabel().getLabel().contains("flightDate")){
					multilingualLabel.setFlightDate(localetext.getDisplayText());
				}
			}
			return multilingualLabel;
		}else{
			return null;
		}
	}


}
