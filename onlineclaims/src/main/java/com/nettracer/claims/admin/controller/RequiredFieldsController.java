/**
 * 
 */
package com.nettracer.claims.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.service.RequiredFieldsService;
import com.nettracer.claims.faces.util.FacesUtil;

/**
 * @author Utpal
 *
 */

@Component
@Scope("session")
@Qualifier("requiredFieldsController")
public class RequiredFieldsController {
	private static Logger logger = Logger.getLogger(RequiredFieldsController.class);
	@Autowired
	RequiredFieldsService requiredFieldsService;
	
	List<Label> requiredFieldsLabel;
	
	/**
	 * Navigate to required fields page
	 * 
	 * @return String
	 */
	public String gotoRequiredFields() {
		logger.info("gotoRequiredFields method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(true);
		if (null != session) {
			// get all the labels for required fields
			List<Label> requiredFields = requiredFieldsService.getAllRequiredFields();
			logger.info("Size of requiredFieldsList=" + requiredFields.size());

			// get all the dropdown values
			List<DropDown> dropdownList = requiredFieldsService.getDropDowns();

			List<SelectItem> selectItemlist = new ArrayList<SelectItem>();
			// construct the combobox values
			for (DropDown dropDown : dropdownList) {
				selectItemlist.add(new SelectItem(dropDown.getId(),dropDown.getText()));
			}
			setRequiredFieldsLabel(requiredFields);
			
			session.setAttribute("selectItemlist", selectItemlist);
			return "gotoRequiredFields";
		} else {
			addErrorOnInvalidSession();
			return "logout";
		}
	}
	
	

	public String saveRequiredFields() {
		logger.info("saveRequiredFields method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session) {
			requiredFieldsService.save(getRequiredFieldsLabel());
			FacesUtil.addInfo("Required fields Data saved successfully.");
			logger.info("Required fields Data saved successfully.");
			return "gotoLandingPage";
		} else {
			addErrorOnInvalidSession();
			return "logout";
		}
	}

	public void addErrorOnInvalidSession() {
		FacesUtil
				.addError("Your session has been expired. PLease log in again");
		logger.error("User session has been expired. Needs relogin");
	}

	public List<Label> getRequiredFieldsLabel() {
		return requiredFieldsLabel;
	}

	public void setRequiredFieldsLabel(List<Label> requiredFieldsLabel) {
		this.requiredFieldsLabel = requiredFieldsLabel;
	}

	public void setRequiredFieldsService(RequiredFieldsService requiredFieldsService) {
		this.requiredFieldsService = requiredFieldsService;
	}
	
}
