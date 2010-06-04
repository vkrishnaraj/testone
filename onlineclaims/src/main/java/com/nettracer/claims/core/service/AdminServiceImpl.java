package com.nettracer.claims.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.AdminDao;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;

/**
 * @author David Antosh
 * @author utpal.patra
 * @Date: Nov 4, 2009 Description : Provides Services by calling Dao methods.
 *        Can be called from the Controllers.
 */
@Service
public class AdminServiceImpl implements AdminService {
	//private static Logger logger = Logger.getLogger(RequiredFieldsServiceImpl.class);
	
	@Autowired
	private AdminDao adminDao;

	public List<List<Label>> getAllRequiredFields() throws SimplePersistenceException {
		return getAdminDao().getAll();
	}
	

	public List<DropDown> getDropDowns()  throws SimplePersistenceException{
		return getAdminDao().getDropDowns();
	}

	public void save(List<Label> requiredFieldsList)  throws SimplePersistenceException{
		getAdminDao().save(requiredFieldsList);
	}

	public Company getApplicationData()  throws SimplePersistenceException{
		return getAdminDao().getApplicationData();
	}

	public void saveApplication(Company company) throws SimplePersistenceException {
		getAdminDao().saveCompany(company);
	}
	
	public List<Languages> getLanguages() throws SimplePersistenceException{
		return getAdminDao().getLanguages();
	}
	
	public List<Localetext> getContents(String languageDescription) throws SimplePersistenceException{
		return getAdminDao().getContents(languageDescription);
	}
	
	
	@Override
	public List<Localetext> getPassengerContents(String languageSelected)
			throws SimplePersistenceException {
		return getAdminDao().getPassengerContents(languageSelected);
	}
	@Override
	public List<Localetext> getFlightContents(String languageSelected)
			throws SimplePersistenceException {
		return getAdminDao().getFlightContents(languageSelected);
	}
	

	@Override
	public List<Localetext> getBaggageContents(String languageSelected)
			throws SimplePersistenceException {
		
		return getAdminDao().getBaggageContents(languageSelected);
	}


	@Override
	public List<Localetext> getFraudQuestionContents(String languageSelected)
			throws SimplePersistenceException {
		
		return getAdminDao().getFraudQuestionContents(languageSelected);
	}


	@Override
	public List<Localetext> getGeneralContents(String languageSelected)
			throws SimplePersistenceException {
		
		return getAdminDao().getGeneralContents(languageSelected);
	}


	@Override
	public List<Localetext> getSavedContents(String languageSelected)
			throws SimplePersistenceException {
		
		return getAdminDao().getSavedContents(languageSelected);
	}


	@Override
	public List<Localetext> getSubmitContents(String languageSelected)
			throws SimplePersistenceException {
		
		return getAdminDao().getSubmitContents(languageSelected);
	}


	@Override
	public List<Localetext> getUploadContents(String languageSelected)
			throws SimplePersistenceException {
		
		return getAdminDao().getUploadContents(languageSelected);
	}
	
	@Override
	public List<Localetext> getDirectionContents(String languageSelected)
			throws SimplePersistenceException {
		return getAdminDao().getDirectionContents(languageSelected);
	}


	@Override
	public List<Localetext> getPassengerLoginContents(String languageSelected)
			throws SimplePersistenceException {
		return getAdminDao().getPassengerLoginContents(languageSelected);
	}



	@Override
	public void saveContentLanguageMaps(Map<String, List<Map<String, List<Localetext>>>> languageMap)
				throws SimplePersistenceException {
		 getAdminDao().saveContentLanguageMaps(languageMap);
		
	}

	public AdminDao getAdminDao() {
		return adminDao;
	}


	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}


	
	

	

}
