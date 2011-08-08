package com.nettracer.claims.core.service;

import java.util.List;
import java.util.Map;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Airline;
import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.StateCode;
import com.nettracer.claims.core.model.labels.LabelsAdditionalInfoPage;
import com.nettracer.claims.core.model.labels.LabelsBagDetailsPage;
import com.nettracer.claims.core.model.labels.LabelsDirectionPage;
import com.nettracer.claims.core.model.labels.LabelsFileUploadPage;
import com.nettracer.claims.core.model.labels.LabelsFlightInfoPage;
import com.nettracer.claims.core.model.labels.LabelsGeneralApplication;
import com.nettracer.claims.core.model.labels.LabelsLoginPage;
import com.nettracer.claims.core.model.labels.LabelsPassengerInfoPage;
import com.nettracer.claims.core.model.labels.LabelsSubmitClaimPage;
import com.nettracer.claims.core.model.labels.LabelsSubmitSuccessPage;

public interface PaxViewService {
    
	public LabelsLoginPage getLoginPage(String languageSelected,
			Long baggageState) throws SimplePersistenceException;

	public LabelsDirectionPage getDirectionPage(String selectedLanguage,
			Long baggageState)throws SimplePersistenceException;

	public LabelsPassengerInfoPage getPassengerInfoPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsFlightInfoPage getFlightInfoPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsBagDetailsPage getBagDetailsPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsFileUploadPage getFileUploadPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsAdditionalInfoPage getFraudQuestionPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsSubmitClaimPage getSubmitClaimPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsSubmitSuccessPage getSavedScreenPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public LabelsGeneralApplication getGeneralPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException;

	public List<CountryCode> getCountries() throws SimplePersistenceException;

	public List<StateCode> getStates() throws SimplePersistenceException;

	public List<Airport> getAirportList() throws SimplePersistenceException;
	public List<Airport> getAirportList(String compare) throws SimplePersistenceException;
	// ******************************************************************************************

	public List<Localetext> getPassengerLoginContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getDirectionContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getPassengerContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getFlightContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getBaggageContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getUploadContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getFraudQuestionContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getSubmitContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getSavedContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getGeneralContents(String languageSelected) throws SimplePersistenceException;

	public List<Localetext> getContents(String languageDescription) throws SimplePersistenceException;
	
	public List<List<Label>> getAllRequiredFields() throws SimplePersistenceException;
	
	public List<DropDown> getDropDowns() throws SimplePersistenceException;
	
	public List<Languages> getLanguages() throws SimplePersistenceException;
	
	public Company getApplicationData() throws SimplePersistenceException;
	
	public void save(List<Label> requiredFieldsList) throws SimplePersistenceException;
	
	public void saveApplication(Company company) throws SimplePersistenceException;
	
	public void saveContentLanguageMaps(
			Map<String, List<Map<String, List<Localetext>>>> languageMap) throws SimplePersistenceException;
	
	public List<Airline> getAirlines() throws SimplePersistenceException;
}
