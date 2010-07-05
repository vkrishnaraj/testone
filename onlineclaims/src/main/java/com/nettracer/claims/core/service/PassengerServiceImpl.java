package com.nettracer.claims.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.AdminDao;
import com.nettracer.claims.core.dao.PassengerDao;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.CountryCode;
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
	// private static Logger logger =
	// Logger.getLogger(RequiredFieldsServiceImpl.class);

	@Autowired
	private PassengerDao passengerDao;

	@Autowired
	private AdminDao adminDao;

	@Override
	public List<Localetext> getPassengerLoginContents(String languageSelected)
			throws SimplePersistenceException {
		return (List<Localetext>) passengerDao
				.getPassengerLoginContents(languageSelected);
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
	public MultilingualLabel getPassengerInfo(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		MultilingualLabel multilingualLabel = new MultilingualLabel();
		Long requiredFieldStatus = null;
		List<Localetext> localetextList = passengerDao
				.getPassengerInfo(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			for (Localetext localetext : localetextList) {

				requiredFieldStatus = baggageState == 1L ? localetext
						.getLabel().getDelayedState()
						: baggageState == 2L ? localetext.getLabel()
								.getPilferedState() : localetext.getLabel()
								.getDamagedState();
				if (localetext.getLabel().getLabel().contains(
						"requiredFieldMessage")) {
					multilingualLabel.setRequiredFieldMessage(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"Permanent")) {
					multilingualLabel.setPermanentAddress(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Help")) {
					multilingualLabel.setPassengerInfoHelp(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"Descriptive")) {
					multilingualLabel
							.setPassengerInfoDescriptiveText(localetext
									.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Last")) {
					multilingualLabel.setPassengerInfolastName(localetext
							.getDisplayText());
					multilingualLabel.setLastNameState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains("First")) {
					multilingualLabel.setFirstName(localetext.getDisplayText());
					multilingualLabel.setFirstNameState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains("Middle")) {
					multilingualLabel.setMiddleInitial(localetext
							.getDisplayText());
					multilingualLabel
							.setMiddleInitialState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains(
						"AddressLine1")) {
					multilingualLabel.setAddressLine1(localetext
							.getDisplayText());
					multilingualLabel.setAddressLine1State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains(
						"AddressLine2")) {
					multilingualLabel.setAddressLine2(localetext
							.getDisplayText());
					multilingualLabel.setAddressLine2State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"City")) {
					multilingualLabel.setCity(localetext.getDisplayText());
					multilingualLabel.setCityState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"State/Region")) {
					multilingualLabel.setStateRegion(localetext
							.getDisplayText());
					multilingualLabel.setStateRegionState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Postal Code")) {
					multilingualLabel
							.setPostalCode(localetext.getDisplayText());
					multilingualLabel.setPostalCodeState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains("Email")) {
					multilingualLabel.setEmailAddress(localetext
							.getDisplayText());
					multilingualLabel.setEmailAddressState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Country")) {
					multilingualLabel.setCountry(localetext.getDisplayText());
					multilingualLabel.setCountryState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains(
						"Occupation")) {
					multilingualLabel
							.setOccupation(localetext.getDisplayText());
					multilingualLabel.setOccupationState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel()
						.contains("Business")) {
					multilingualLabel.setBusinessName(localetext
							.getDisplayText());
					multilingualLabel.setBusinessNameState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"MailingAddress")) {
					multilingualLabel.setMailingAddress(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"Mailing Address Line 1")) {
					multilingualLabel.setMailingAddressLine1(localetext
							.getDisplayText());
					multilingualLabel
							.setMailingAddressLine1State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().contains(
						"Mailing Address Line 2")) {
					multilingualLabel.setMailingAddressLine2(localetext
							.getDisplayText());
					multilingualLabel
							.setMailingAddressLine2State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Mailing City")) {
					multilingualLabel.setMailingCity(localetext
							.getDisplayText());
					multilingualLabel.setMailingCityState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Mailing State/Region")) {
					multilingualLabel.setMailingStateRegion(localetext
							.getDisplayText());
					multilingualLabel
							.setMailingStateRegionState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Mailing Postal Code")) {
					multilingualLabel.setMailingPostalCode(localetext
							.getDisplayText());
					multilingualLabel
							.setMailingPostalCodeState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"MailingCountry")) {
					multilingualLabel.setMailingCountry(localetext
							.getDisplayText());
					multilingualLabel
							.setMailingCountryState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Voice Contact")) {
					multilingualLabel.setVoiceContact(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Phone 1")) {
					multilingualLabel.setPhone1(localetext.getDisplayText());
					multilingualLabel.setPhone1State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Phone 2")) {
					multilingualLabel.setPhone2(localetext.getDisplayText());
					multilingualLabel.setPhone2State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Phone 3")) {
					multilingualLabel.setPhone3(localetext.getDisplayText());
					multilingualLabel.setPhone3State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Phone 4")) {
					multilingualLabel.setPhone4(localetext.getDisplayText());
					multilingualLabel.setPhone4State(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Personal Information")) {
					multilingualLabel.setPersonalInformation(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Frequent Flyer Number")) {
					multilingualLabel.setFrequentFlyerNumber(localetext
							.getDisplayText());
					multilingualLabel
							.setFrequentFlyerNumberState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Social Security Number")) {
					multilingualLabel.setSocialSecurityNumber(localetext
							.getDisplayText());
					multilingualLabel
							.setSocialSecurityNumberState(requiredFieldStatus);
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Save")) {
					multilingualLabel.setPassengerInfoSave(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Cancel")) {
					multilingualLabel.setPassengerInfoCancel(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().equalsIgnoreCase(
						"Forward")) {
					multilingualLabel.setPassengerInfoForward(localetext
							.getDisplayText());
				}
				requiredFieldStatus = null;
			}
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public MultilingualLabel getFlightLabels(String selectedLanguage,
			Long flightBaggageState) throws SimplePersistenceException {
		MultilingualLabel multilingualLabel = new MultilingualLabel();
		Long requiredFieldFlightStatus = null;
		List<Localetext> localetextList = passengerDao
		.getFlightLabels(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			for (Localetext localetext : localetextList) {
				requiredFieldFlightStatus = flightBaggageState == 1L ? localetext
						.getLabel().getDelayedState()
						: flightBaggageState == 2L ? localetext.getLabel()
								.getPilferedState() : localetext.getLabel()
								.getDamagedState();
								if (localetext.getLabel().getLabel().contains(
								"flightDescriptiveText")) {
									multilingualLabel.setFlightDescriptiveText(localetext
											.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"flightRequiredFieldMessage")) {
									multilingualLabel.setFlightRequiredFieldMessage(localetext
											.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"flightHelp")) {
									multilingualLabel
									.setFlightHelp(localetext.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"About Your Flight")) {
									multilingualLabel.setAboutYourFlight(localetext
											.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"How many bags did you travel with")) {
									multilingualLabel.setTotalTravelBag(localetext
											.getDisplayText());
									multilingualLabel
									.setTotalTravelBagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"How many bags were lost/delayed")) {
									multilingualLabel.setNoOfLostBag(localetext
											.getDisplayText());
									multilingualLabel
									.setNoOfLostBagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Where did you check your bag")) {
									multilingualLabel.setBagCheckedLocation(localetext
											.getDisplayText());
									multilingualLabel
									.setBagCheckedLocationState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Number of Passengers travelling with you")) {
									multilingualLabel.setNoOfPassenger(localetext
											.getDisplayText());
									multilingualLabel
									.setNoOfPassengerState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Did you have to recheck your baggage")) {
									multilingualLabel
									.setRecheckBag(localetext.getDisplayText());
									multilingualLabel
									.setRecheckBagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Was your bag inspected")) {
									multilingualLabel
									.setInspectbag(localetext.getDisplayText());
									multilingualLabel
									.setInspectbagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Were you charged for excess baggage")) {
									multilingualLabel.setChargeExcessBaggage(localetext
											.getDisplayText());
									multilingualLabel
									.setChargeExcessBaggageState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"No of bags checked")) {
									multilingualLabel.setBagNumber(localetext.getDisplayText());
									multilingualLabel
									.setBagNumberState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"No of bags missing")) {
									multilingualLabel
									.setMissingBag(localetext.getDisplayText());
									multilingualLabel
									.setMissingBagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Bag Claim Check ")) {
									multilingualLabel.setBagClaimCheck(localetext
											.getDisplayText());
									multilingualLabel
									.setBagClaimCheckState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"pay for excess value")) {
									multilingualLabel.setDeclarePayExcessValue(localetext
											.getDisplayText());
									multilingualLabel
									.setDeclarePayExcessValueState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Value Declared")) {
									multilingualLabel.setDeclaredValue(localetext
											.getDisplayText());
									multilingualLabel
									.setDeclaredValueState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Where did you check your baggage")) {
									multilingualLabel.setPlaceBagChecked(localetext
											.getDisplayText());
									multilingualLabel
									.setPlaceBagCheckedState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"bag clear customs")) {
									multilingualLabel.setClearCustomBag(localetext
											.getDisplayText());
									multilingualLabel
									.setClearCustomBagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Estimated Weight")) {
									multilingualLabel.setBagWeight(localetext.getDisplayText());
									multilingualLabel
									.setBagWeightState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"baggage rerouted or rechecked enroute")) {
									multilingualLabel
									.setRerouteBag(localetext.getDisplayText());
									multilingualLabel
									.setRerouteBagState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"given a different claim check")) {
									multilingualLabel.setDifferentClaimCheck(localetext
											.getDisplayText());
									multilingualLabel
									.setDifferentClaimCheckState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"City/Airline rerouted by")) {
									multilingualLabel.setReroutedCityAirline(localetext
											.getDisplayText());
									multilingualLabel
									.setReroutedCityAirlineState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains("Reason")) {
									multilingualLabel.setReason(localetext.getDisplayText());
									multilingualLabel.setReasonState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Did you attempt to claim immediately ")) {
									multilingualLabel.setImmediateClaimAttempt(localetext
											.getDisplayText());
									multilingualLabel
									.setImmediateClaimAttemptState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"last see your baggage")) {
									multilingualLabel.setWhenBagSeen(localetext
											.getDisplayText());
									multilingualLabel
									.setWhenBagSeenState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"US Airways city did you file your report")) {
									multilingualLabel.setFileReportCity(localetext
											.getDisplayText());
									multilingualLabel
									.setFileReportCityState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"mishandling reported")) {
									multilingualLabel.setReportAnotherAirline(localetext
											.getDisplayText());
									multilingualLabel
									.setReportAnotherAirlineState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"About Your Ticket")) {
									multilingualLabel.setAboutYourTicket(localetext
											.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"ticket with a different airline")) {
									multilingualLabel.setDifferentAirlineTicket(localetext
											.getDisplayText());
									multilingualLabel
									.setDifferentAirlineTicketState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Ticket Number")) {
									multilingualLabel.setTicketNumber(localetext
											.getDisplayText());
									multilingualLabel
									.setTicketNumberState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Bag Tag Numbers")) {
									multilingualLabel.setBagTagNumbers(localetext
											.getDisplayText());
									multilingualLabel
									.setBagTagNumbersState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Did this Bag Arrive")) {
									multilingualLabel
									.setBagArrival(localetext.getDisplayText());
									multilingualLabel
									.setBagArrivalState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"flightYes")) {
									multilingualLabel.setFlightYes(localetext.getDisplayText());
								} else if (localetext.getLabel().getLabel()
										.contains("flightNo")) {
									multilingualLabel.setFlightNo(localetext.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"About your Itinerary")) {
									multilingualLabel.setAboutYourItenerary(localetext
											.getDisplayText());
								} else if (localetext.getLabel().getLabel().contains(
								"From/To Airports")) {
									multilingualLabel.setFromToAirports(localetext
											.getDisplayText());
									multilingualLabel
									.setFromToAirportsState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains(
								"Airline/Flight Number")) {
									multilingualLabel.setAirlineFlightNo(localetext
											.getDisplayText());
									multilingualLabel
									.setAirlineFlightNoState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel().contains("Date")) {
									multilingualLabel
									.setFlightDate(localetext.getDisplayText());
									multilingualLabel
									.setFlightDateState(requiredFieldFlightStatus);
								} else if (localetext.getLabel().getLabel()
										.contains("Previous")) {
									multilingualLabel.setFlightPrevious(localetext
											.getDisplayText());
								}
								requiredFieldFlightStatus = null;
			}
			return multilingualLabel;
		} else {
			return null;
		}
	}
	
	/**
	 * Get the labels for Bag Details page
	 */
	@Override
	public MultilingualLabel getBagDetailsLabel(String selectedLanguage,
			Long bagBaggageState) throws SimplePersistenceException {
		MultilingualLabel multilingualLabel = new MultilingualLabel();
		Long requiredFieldBagStatus = null;
		List<Localetext> localetextList = passengerDao.getBagDetailsLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			for (Localetext localetext : localetextList) {
				requiredFieldBagStatus = bagBaggageState == 1L ? localetext	.getLabel().getDelayedState()
						: bagBaggageState == 2L ? localetext.getLabel().getPilferedState() 
						: localetext.getLabel().getDamagedState();
				if (localetext.getLabel().getLabel().contains("Descriptive text")) {
					multilingualLabel.setBagDescriptiveText(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Required Field Message")) {
					multilingualLabel.setBagRequiredFieldMessage(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Help")) {
					multilingualLabel.setBagHelp(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("About Your Bag")) {
					multilingualLabel.setAboutYourBag(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Bag Number")) {
					multilingualLabel.setBagNumberLabel(localetext.getDisplayText());
				}else if (localetext.getLabel().getLabel().contains("Bag Tag Number")) {
					multilingualLabel.setBagTagNumber(localetext.getDisplayText());
					multilingualLabel.setBagTagNumberState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("Name on Bag")) {
					multilingualLabel.setNameonBag(localetext.getDisplayText());
					multilingualLabel.setNameonBagState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("Brand Of the Bag")) {
					multilingualLabel.setBrandOftheBag(localetext.getDisplayText());
					multilingualLabel.setBrandOftheBagState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("External Markings")) {
					multilingualLabel.setExternalMarkings(localetext.getDisplayText());
					multilingualLabel.setExternalMarkingsState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("Bag Purchase Date")) {
					multilingualLabel.setBagPurchaseDate(localetext.getDisplayText());
					multilingualLabel.setBagPurchaseDateState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("Color Chart")) {
					multilingualLabel.setColorChart(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("BagChart")) {
					multilingualLabel.setBagChart(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Bag Color")) {
					multilingualLabel.setBagColor(localetext.getDisplayText());
					multilingualLabel.setBagColorState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("Bag Type")) {
					multilingualLabel.setBagType(localetext.getDisplayText());
					multilingualLabel.setBagTypeState(requiredFieldBagStatus);
				} else if (localetext.getLabel().getLabel().contains("appropriate")) {
					multilingualLabel.setSelectAppropriate(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Hard Sided")) {
					multilingualLabel.setHardSided(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Wheels/Rollers")) {
					multilingualLabel.setWheelsRollers(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Feet")) {
					multilingualLabel.setFeet(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Trim")) {
					multilingualLabel.setTrim(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Soft Sided")) {
					multilingualLabel.setSoftSided(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Zippers")) {
					multilingualLabel.setZippers(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Retractable Handel")) {
					multilingualLabel.setRetractableHandel(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Pockets")) {
					multilingualLabel.setPockets(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Locks")) {
					multilingualLabel.setLocks(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Pull Strap")) {
					multilingualLabel.setPullStrap(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Name Tag")) {
					multilingualLabel.setNameTag(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Ribbons/Personal Markings")) {
					multilingualLabel.setRibbonsPersonalMarkings(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Description of Contents")) {
					multilingualLabel.setDescriptionOfContents(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Gender")) {
					multilingualLabel.setGender(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Article")) {
					multilingualLabel.setArticle(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Color")) {
					multilingualLabel.setColor(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Size")) {
					multilingualLabel.setSize(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Brand or Description")) {
					multilingualLabel.setBrandOrDescription(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Store Purchased")) {
					multilingualLabel.setStorePurchased(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Purchased Date")) {
					multilingualLabel.setPurchasedDate(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("price")) {
					multilingualLabel.setPrice(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Currency")) {
					multilingualLabel.setCurrency(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Add More Items")) {
					multilingualLabel.setAddMoreItems(localetext.getDisplayText());
				}  
				requiredFieldBagStatus = null;
			}
			return multilingualLabel;
		} else {
			return null;
		}
	}
	
	/**
	 * Get the labels for File Upload page
	 */
	@Override
	public MultilingualLabel getFileUploadLabel(String selectedLanguage,
			Long fileBaggageState) throws SimplePersistenceException {

		MultilingualLabel multilingualLabel = new MultilingualLabel();
		//Long requiredFieldFileStatus = null;
		List<Localetext> localetextList = passengerDao.getFileUploadLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			for (Localetext localetext : localetextList) {
				
				/*requiredFieldFileStatus = fileBaggageState == 1L ? localetext.getLabel().getDelayedState()
						: fileBaggageState == 2L ? localetext.getLabel().getPilferedState() 
						: localetext.getLabel().getDamagedState();*/

				if (localetext.getLabel().getLabel().contains("Descriptive text")) {
					multilingualLabel.setFileUploadDescriptiveText(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Required Field Message")) {
					multilingualLabel.setFileUploadRequiredFieldMessage(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("File Help")) {
					multilingualLabel.setFileUploadHelp(localetext.getDisplayText());
				}else if (localetext.getLabel().getLabel().contains("File Upload")) {
					multilingualLabel.setFileUpload(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("File Selection")) {
					multilingualLabel.setFileSelection(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Receipts to be uploaded")) {
					multilingualLabel.setReceiptsToBeUploaded(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Add Receipts")) {
					multilingualLabel.setAddReceipts(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Remove")) {
					multilingualLabel.setRemove(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("File Instruction")) {
					multilingualLabel.setFileInstruction(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("Browse")) {
					multilingualLabel.setBrowse(localetext.getDisplayText());
				} 
				//requiredFieldFileStatus = null;
			}
			return multilingualLabel;
		} else {
			return null;
		}
	
	}

	/**
	 * Get the labels for Fraud Question page
	 */
	@Override
	public MultilingualLabel getFraudQuestionLabel(String selectedLanguage,
			Long fraudBaggageState) throws SimplePersistenceException {
		MultilingualLabel multilingualLabel = new MultilingualLabel();
		Long requiredFieldFraudtatus = null;
		List<Localetext> localetextList = passengerDao.getFraudQuestionLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			for (Localetext localetext : localetextList) {
				
				requiredFieldFraudtatus = fraudBaggageState == 1L ? localetext.getLabel().getDelayedState()
						: fraudBaggageState == 2L ? localetext.getLabel().getPilferedState() 
						: localetext.getLabel().getDamagedState();

				if (localetext.getLabel().getLabel().contains("fraudQuestionDescriptiveText")) {
					multilingualLabel.setFraudQuestionDescriptiveText(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("fraudQuestionRequiredFieldMessage")) {
					multilingualLabel.setFraudQuestionRequiredFieldMessage(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("fraudQuestionHelp")) {
					multilingualLabel.setFraudQuestionHelp(localetext.getDisplayText());
				}else if (localetext.getLabel().getLabel().contains("moreClaim")) {
					multilingualLabel.setMoreClaim(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("anotherClaim")) {
					multilingualLabel.setAnotherClaim(localetext.getDisplayText());
					multilingualLabel.setAnotherClaimState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("yesAbove")) {
					multilingualLabel.setYesAbove(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("whichAirline")) {
					multilingualLabel.setWhichAirline(localetext.getDisplayText());
					multilingualLabel.setWhichAirlineState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("dateOfClaim")) {
					multilingualLabel.setDateOfClaim(localetext	.getDisplayText());
					multilingualLabel.setDateOfClaimState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("claimantName")) {
					multilingualLabel.setClaimantName(localetext.getDisplayText());
					multilingualLabel.setClaimantNameState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("TSA information")) {
					multilingualLabel.setTsaInfo(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("TSA inspect")) {
					multilingualLabel.setTsaInspect(localetext.getDisplayText());
					multilingualLabel.setTsaInspectState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("note inserted in the bag")) {
					multilingualLabel.setBagConfirmNote(localetext.getDisplayText());
					multilingualLabel.setBagConfirmNoteState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("inspection take place")) {
					multilingualLabel.setInspectionPlace(localetext.getDisplayText());
					multilingualLabel.setInspectionPlaceState(requiredFieldFraudtatus);
				} else if (localetext.getLabel().getLabel().contains("Additional Comments")) {
					multilingualLabel.setAdditionalComments(localetext.getDisplayText());
					multilingualLabel.setAdditionalCommentsState(requiredFieldFraudtatus);
				} 
				requiredFieldFraudtatus = null;
			}
			return multilingualLabel;
		} else {
			return null;
		}
	}

	/**
	 * Get the labels for submit claim page
	 */
	@Override
	public MultilingualLabel getSubmitClaimLabel(String selectedLanguage,
			Long submitBaggageState) throws SimplePersistenceException {
		MultilingualLabel multilingualLabel = new MultilingualLabel();
		Long requiredFieldFlightStatus = null;
		List<Localetext> localetextList = passengerDao
				.getSubmitClaimLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			for (Localetext localetext : localetextList) {
				requiredFieldFlightStatus = submitBaggageState == 1L ? localetext
						.getLabel().getDelayedState()
						: submitBaggageState == 2L ? localetext.getLabel()
								.getPilferedState() : localetext.getLabel()
								.getDamagedState();
				if (localetext.getLabel().getLabel().contains(
						"submitClaimDescriptiveText")) {
					multilingualLabel.setSubmitClaimDescriptiveText(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"submitClaimHelp")) {
					multilingualLabel.setSubmitClaimHelp(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"submitClaimRequiredFieldMessage")) {
					multilingualLabel
							.setSubmitClaimRequiredFieldMessage(localetext
									.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"useOfInformation")) {
					multilingualLabel.setUseOfInformation(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"reservedRights")) {
					multilingualLabel.setReservedRights(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"agreement")) {
					multilingualLabel.setAgreement(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"typeAccept")) {
					multilingualLabel
							.setTypeAccept(localetext.getDisplayText());
					multilingualLabel
							.setTypeAcceptState(requiredFieldFlightStatus);
				} else if (localetext.getLabel().getLabel().contains(
						"confirmation")) {
					multilingualLabel.setConfirmation(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"luggageLostDate")) {
					multilingualLabel.setLuggageLostDate(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"signature")) {
					multilingualLabel.setSignature(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"signedDate")) {
					multilingualLabel
							.setSignedDate(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains("print")) {
					multilingualLabel.setPrint(localetext.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"claimSubmit")) {
					multilingualLabel.setClaimSubmit(localetext
							.getDisplayText());
				}
				requiredFieldFlightStatus = null;
			}
			return multilingualLabel;
		} else {
			return null;
		}
	}

	/**
	 * Get the labels for Saved Screen page
	 */

	@Override
	public MultilingualLabel getSavedScreenLabel(String selectedLanguage,
			Long savedBaggageState) throws SimplePersistenceException {
		MultilingualLabel multilingualLabel = new MultilingualLabel();
		List<Localetext> localetextList = passengerDao
				.getSavedScreenLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			for (Localetext localetext : localetextList) {
				if (localetext.getLabel().getLabel().contains("savedMessage")) {
					multilingualLabel.setSavedMessage(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"savedScreenHelp")) {
					multilingualLabel.setSavedScreenHelp(localetext
							.getDisplayText());
				} else if (localetext.getLabel().getLabel().contains(
						"returnLink")) {
					multilingualLabel
							.setReturnLink(localetext.getDisplayText());
				}
			}
			return multilingualLabel;
		} else {
			return null;
		}
	}

	@Override
	public List<CountryCode> getCountries() throws SimplePersistenceException {
		List<CountryCode> countries = passengerDao.getCountries();
		return countries;
	}

	@Override
	public List<Airport> getAirportList() throws SimplePersistenceException {
		return passengerDao.getAirportList();
	}



	

}
