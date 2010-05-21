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
	
	List<List<Label>> allRequiredFields;
	
	/**
	 * Navigate to required fields page
	 * 
	 * @return String
	 */
	public String gotoRequiredFields() {
		logger.info("gotoRequiredFields method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			// get all the labels for required fields
			setAllRequiredFields(requiredFieldsService.getAllRequiredFields());

			// get all the dropdown values
			List<DropDown> dropdownList = requiredFieldsService.getDropDowns();

			List<SelectItem> selectItemlist = new ArrayList<SelectItem>();
			// construct the combobox values
			for (DropDown dropDown : dropdownList) {
				selectItemlist.add(new SelectItem(dropDown.getId(),dropDown.getText()));
			}
			session.setAttribute("selectItemlist", selectItemlist);
			return "gotoRequiredFields";
		} else {
			addErrorOnInvalidSession();
			return "logout";
		}
	}
	
	
/**
 * 
 * Save all the Edited required Fileds Values.
 */
	public String saveRequiredFields() {
		logger.info("saveRequiredFields method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			List<List<Label>> lists=(List<List<Label>>)getAllRequiredFields();
			for(List<Label> labels:lists){
				requiredFieldsService.save(labels);
			}
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

	

	public void setRequiredFieldsService(RequiredFieldsService requiredFieldsService) {
		this.requiredFieldsService = requiredFieldsService;
	}



	public List<List<Label>> getAllRequiredFields() {
		return allRequiredFields;
	}



	public void setAllRequiredFields(List<List<Label>> allRequiredFields) {
		this.allRequiredFields = allRequiredFields;
	}



	
}
