package com.nettracer.claims.core.service;

import java.util.List;
import java.util.Map;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;

/**
 * @author Utpal Patra
 * 
 */
public interface PassengerService {
	public List<Localetext> getPassengerLoginContents(String languageSelected) throws SimplePersistenceException;
	/*public List<Localetext> getPassengerContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getFlightContents(String languageSelected) throws SimplePersistenceException;
	public void saveContentLanguageMaps(
			Map<String, List<Map<String, List<Localetext>>>> languageMap) throws SimplePersistenceException;
	public List<Localetext> getBaggageContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getGeneralContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getUploadContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getFraudQuestionContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getSubmitContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getSavedContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getDirectionContents(String languageSelected) throws SimplePersistenceException;*/

	public List<Localetext> getPassengerDirection(String selectedLanguage)throws SimplePersistenceException;

	public MultilingualLabel getPassengerInfo(String selectedLanguage, Long baggageState) throws SimplePersistenceException;

	public MultilingualLabel getFlightLabels(String selectedLanguage) throws SimplePersistenceException;

}
