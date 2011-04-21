package com.nettracer.claims.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.PaxViewDao;
import com.nettracer.claims.core.exception.SimplePersistenceException;
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

@Service
public class PaxViewServiceImpl implements PaxViewService {

	@Autowired
	private PaxViewDao passengerDao;

	public void setPassengerDao(PaxViewDao passengerDao) {
		this.passengerDao = passengerDao;
	}

	@Override
	public LabelsLoginPage getLoginPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsLoginPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getPassengerLoginContents(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			
			multilingualLabel = new LabelsLoginPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public List<Localetext> getPassengerLoginContents(String languageSelected)
			throws SimplePersistenceException {
		return passengerDao.getPassengerLoginContents(languageSelected);
	}

	@Override
	public LabelsDirectionPage getDirectionPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsDirectionPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getPassengerDirection(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			
			multilingualLabel = new LabelsDirectionPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}
	
	@Override
	public List<Localetext> getDirectionContents(String languageSelected)
			throws SimplePersistenceException {
		return passengerDao.getPassengerDirection(languageSelected);
	}

	@Override
	public LabelsPassengerInfoPage getPassengerInfoPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsPassengerInfoPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getPassengerInfo(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			
			multilingualLabel = new LabelsPassengerInfoPage(localetextList, baggageState);
		} else {
			return null;	
		}
		return multilingualLabel;
	}
	
	@Override
	public List<Localetext> getPassengerContents(String languageSelected)
			throws SimplePersistenceException {
		return passengerDao.getPassengerInfo(languageSelected);
	}

	@Override
	public LabelsFlightInfoPage getFlightInfoPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsFlightInfoPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao
		.getFlightLabels(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {
			
			multilingualLabel = new LabelsFlightInfoPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}
	
	@Override
	public List<Localetext> getFlightContents(String languageSelected)
			throws SimplePersistenceException {
		return passengerDao.getFlightLabels(languageSelected);
	}
	
	/**
	 * Get the labels for Bag Details page
	 */
	@Override
	public LabelsBagDetailsPage getBagDetailsPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsBagDetailsPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getBagDetailsLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			multilingualLabel = new LabelsBagDetailsPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public List<Localetext> getBaggageContents(String languageSelected)
			throws SimplePersistenceException {
		
		return passengerDao.getBagDetailsLabel(languageSelected);
	}
	
	/**
	 * Get the labels for File Upload page
	 */
	@Override
	public LabelsFileUploadPage getFileUploadPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {

		LabelsFileUploadPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getFileUploadLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			multilingualLabel = new LabelsFileUploadPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	
	}

	@Override
	public List<Localetext> getUploadContents(String languageSelected)
			throws SimplePersistenceException {
		
		return passengerDao.getFileUploadLabel(languageSelected);
	}

	/**
	 * Get the labels for Fraud Question page
	 */
	@Override
	public LabelsAdditionalInfoPage getFraudQuestionPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsAdditionalInfoPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getFraudQuestionLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			multilingualLabel = new LabelsAdditionalInfoPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public List<Localetext> getFraudQuestionContents(String languageSelected)
			throws SimplePersistenceException {
		
		return passengerDao.getFraudQuestionLabel(languageSelected);
	}

	/**
	 * Get the labels for submit claim page
	 */
	@Override
	public LabelsSubmitClaimPage getSubmitClaimPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsSubmitClaimPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getSubmitClaimLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			multilingualLabel = new LabelsSubmitClaimPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public List<Localetext> getSubmitContents(String languageSelected)
			throws SimplePersistenceException {
		
		return passengerDao.getSubmitClaimLabel(languageSelected);
	}
	
	@Override
	public LabelsSubmitSuccessPage getSavedScreenPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsSubmitSuccessPage multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getSavedScreenLabel(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			multilingualLabel = new LabelsSubmitSuccessPage(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public List<Localetext> getSavedContents(String languageSelected)
			throws SimplePersistenceException {
		
		return passengerDao.getSavedScreenLabel(languageSelected);
	}

	@Override
	public LabelsGeneralApplication getGeneralPage(String selectedLanguage,
			Long baggageState) throws SimplePersistenceException {
		LabelsGeneralApplication multilingualLabel = null;
		List<Localetext> localetextList = passengerDao.getGeneralContents(selectedLanguage);
		if (localetextList != null && localetextList.size() > 0) {

			multilingualLabel = new LabelsGeneralApplication(localetextList, baggageState);
		} else {
			return null;
		}
		return multilingualLabel;
	}

	@Override
	public List<Localetext> getGeneralContents(String languageSelected)
			throws SimplePersistenceException {
		
		return passengerDao.getGeneralContents(languageSelected);
	}

	@Override
	public List<CountryCode> getCountries() throws SimplePersistenceException {
		return passengerDao.getCountries();
	}

	@Override
	public List<StateCode> getStates() throws SimplePersistenceException {
		return passengerDao.getStates();
	}

	@Override
	public List<Airport> getAirportList() throws SimplePersistenceException {
		return passengerDao.getAirportList();
	}

	@Override
	public List<List<Label>> getAllRequiredFields() throws SimplePersistenceException {
		return passengerDao.getAll();
	}

	@Override
	public List<DropDown> getDropDowns()  throws SimplePersistenceException{
		return passengerDao.getDropDowns();
	}

	@Override
	public Company getApplicationData()  throws SimplePersistenceException{
		return passengerDao.getApplicationData();
	}

	@Override
	public List<Languages> getLanguages() throws SimplePersistenceException{
		return passengerDao.getLanguages();
	}

	@Override
	public List<Localetext> getContents(String languageDescription) throws SimplePersistenceException{
		return passengerDao.getContents(languageDescription);
	}

	@Override
	public void save(List<Label> requiredFieldsList)  throws SimplePersistenceException{
		passengerDao.save(requiredFieldsList);
	}

	@Override
	public void saveApplication(Company company) throws SimplePersistenceException {
		passengerDao.saveCompany(company);
	}

	@Override
	public void saveContentLanguageMaps(Map<String, List<Map<String, List<Localetext>>>> languageMap)
				throws SimplePersistenceException {
		passengerDao.saveContentLanguageMaps(languageMap);
		
	}

}
