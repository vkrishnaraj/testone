package com.nettracer.claims.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nettracer.claims.core.dao.AdminDao;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.model.Languages;

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

	public List<List<Label>> getAllRequiredFields() {
		return getAdminDao().getAll();
	}
	

	public List<DropDown> getDropDowns() {
		return getAdminDao().getDropDowns();
	}

	public void save(List<Label> requiredFieldsList) {
		getAdminDao().save(requiredFieldsList);
	}

	public Company getApplicationData() {
		return getAdminDao().getApplicationData();
	}

	public void saveApplication(Company company) {
		getAdminDao().saveCompany(company);
	}
	
	public List<Languages> getLanguages(){
		return getAdminDao().getLanguages();
	}


	public AdminDao getAdminDao() {
		return adminDao;
	}


	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	

}
