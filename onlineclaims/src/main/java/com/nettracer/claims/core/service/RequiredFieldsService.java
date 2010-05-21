package com.nettracer.claims.core.service;

import java.util.List;

import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;

/**
 * @author Utpal Patra
 * 
 */
public interface RequiredFieldsService {
	/**
	 *  Returns all the required fields labels for the admin page.
	 * @return List<Label>
	 */
	public List<List<Label>> getAllRequiredFields();
	public List<DropDown> getDropDowns();
	public void save(List<Label> requiredFieldsList);
	public Company getApplicationData();
	public void saveApplication(Company company);
}
