package com.nettracer.claims.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.RequiredFieldsDao;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;

/**
 * @author David Antosh
 * @author utpal.patra
 * @Date: Nov 4, 2009 Description : Provides Services by calling Dao methods.
 *        Can be called from the Controllers.
 */
@Service
public class RequiredFieldsServiceImpl implements RequiredFieldsService {

	@Autowired
	private RequiredFieldsDao requiredFieldsDao;

	public List<Label> getAllRequiredFields() {
		return getRequiredFieldsDao().getAll();
	}

	public List<DropDown> getDropDowns() {
		return getRequiredFieldsDao().getDropDowns();
	}

	public void save(List<Label> requiredFieldsList) {
		getRequiredFieldsDao().save(requiredFieldsList);
	}

	public Company getApplicationData() {
		return getRequiredFieldsDao().getApplicationData();
	}

	public void saveApplication(Company company) {
		getRequiredFieldsDao().saveCompany(company);
	}

	public RequiredFieldsDao getRequiredFieldsDao() {
		return requiredFieldsDao;
	}

	public void setRequiredFieldsDao(RequiredFieldsDao requiredFieldsDao) {
		this.requiredFieldsDao = requiredFieldsDao;
	}

}
