package com.nettracer.claims.core.service;

import java.util.List;
import java.util.Map;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;

/**
 * @author Utpal Patra
 * 
 */
public interface AdminService {
	/**
	 *  Returns all the required fields labels for the admin page.
	 * @return List<Label>
	 */
	public List<List<Label>> getAllRequiredFields() throws SimplePersistenceException;
	public List<DropDown> getDropDowns() throws SimplePersistenceException;
	public void save(List<Label> requiredFieldsList) throws SimplePersistenceException;
	public Company getApplicationData() throws SimplePersistenceException;
	public void saveApplication(Company company) throws SimplePersistenceException;
	public List<Languages> getLanguages() throws SimplePersistenceException;
	public List<Localetext> getContents(String languageDescription) throws SimplePersistenceException;
	public List<Localetext> getPassengerContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getFlightContents(String languageSelected) throws SimplePersistenceException;
	public void saveContentLanguageMaps(
			Map<String, List<Map<String, List<Localetext>>>> languageMap) throws SimplePersistenceException;
	public List<Localetext> getBaggageContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getGeneralContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getUploadContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getFraudQuestionContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getSubmitContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getSavedContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getPassengerLoginContents(String languageSelected) throws SimplePersistenceException;
	public List<Localetext> getDirectionContents(String languageSelected) throws SimplePersistenceException;
}
