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

import com.nettracer.claims.admin.SessionScopeBean;
import com.nettracer.claims.core.model.DropDown;
import com.nettracer.claims.core.model.Label;
import com.nettracer.claims.core.service.AdminService;
import com.nettracer.claims.faces.util.FacesUtil;

/**
 * @author Utpal
 * 
 */

@Component
@Scope("session")
@Qualifier("requiredFieldsController")
public class RequiredFieldsController {
	private static Logger logger = Logger
			.getLogger(RequiredFieldsController.class);
	@Autowired
	AdminService adminService;

	List<List<Label>> allRequiredFields;

	/**
	 * Navigate to required fields page
	 * 
	 * @return String
	 */
	public String gotoRequiredFields() {
		logger.debug("gotoRequiredFields method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
			sessionBean.setLandingRenderer(true); //for landing page link
			// get all the labels for required fields
			try {
				setAllRequiredFields(adminService.getAllRequiredFields());

				// get all the dropdown values
				List<DropDown> dropdownList = adminService.getDropDowns();

				List<SelectItem> selectItemlist = new ArrayList<SelectItem>();
				// construct the combobox values
				for (DropDown dropDown : dropdownList) {
					selectItemlist.add(new SelectItem(dropDown.getId(),
							dropDown.getText()));
				}
				session.setAttribute("selectItemlist", selectItemlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoRequiredFields";
		} else {
			if(session != null){
				SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
				sessionBean.setLandingRenderer(false); //for landing page link
			}
			addErrorOnInvalidSession();
			return "logout";
		}
	}

	/**
	 * 
	 * Save all the Edited required Fileds Values.
	 */
	public String saveRequiredFields() {
		logger.debug("saveRequiredFields method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
				SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
				sessionBean.setLandingRenderer(false);
			List<List<Label>> lists = (List<List<Label>>) getAllRequiredFields();
			try {
				for (List<Label> labels : lists) {
					adminService.save(labels);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			FacesUtil.addInfo("Required fields Data saved successfully.");
			logger.info("Required fields Data saved successfully.");
			return "gotoLandingPage";
		} else {
			if(session != null){
				SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
				sessionBean.setLandingRenderer(false); //for landing page link
			}
			addErrorOnInvalidSession();
			return "logout";
		}
	}

	public void addErrorOnInvalidSession() {
		FacesUtil
				.addError("Your session has been expired. PLease log in again");
		logger.error("User session has been expired. Needs relogin");
	}

	public void setRequiredFieldsService(AdminService requiredFieldsService) {
		this.adminService = requiredFieldsService;
	}

	public List<List<Label>> getAllRequiredFields() {
		return allRequiredFields;
	}

	public void setAllRequiredFields(List<List<Label>> allRequiredFields) {
		this.allRequiredFields = allRequiredFields;
	}


}
