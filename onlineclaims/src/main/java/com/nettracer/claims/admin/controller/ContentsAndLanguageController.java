/**
 * 
 */
package com.nettracer.claims.admin.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.service.AdminService;
import com.nettracer.claims.faces.util.FacesUtil;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation.
 */

@Component
@Scope("session")
@Qualifier("contentsAndLanguageController")
public class ContentsAndLanguageController {
	private static Logger logger = Logger.getLogger(ContentsAndLanguageController.class);
	
	@Autowired
	AdminService adminService;
	
	private List<String> activeLanguages=new ArrayList<String>();
	
	private List<SelectItem> selectCheckBoxeslist;
	
	private String selectedLanguage;
	
	private Set<SelectItem> languageDropDown=new LinkedHashSet<SelectItem>();

	/**
	 * Page navigation to Content and Language Page
	 * 
	 * @return String
	 */
	

	public String gotoContentsAndLanguagePage() {
		logger.info("gotoContentsAndLanguagePage method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			List<Languages> allLanguages=adminService.getLanguages();
			selectCheckBoxeslist = new ArrayList<SelectItem>();
			//construct the check boxes
			for(Languages language:allLanguages){
				selectCheckBoxeslist.add(new SelectItem(language.getDescription()));
				if(language.getActiveStatus()==true){
					if(null == activeLanguages){
						activeLanguages=new ArrayList<String>();
					}
					activeLanguages.add(language.getDescription());
				}
			}
			languageDropDown.add(new SelectItem("Please Select a Language"));
			return "gotoContentsAndLanguage";
		} else {
			FacesUtil
					.addError("Your session has been expired. PLease log in again");
			return "logout";
		}
	}

	@SuppressWarnings("unchecked")
	public void manyCheckBoxListener(ValueChangeEvent valueChangeEvent){
		logger.info("Listener:manyCheckBoxListener is called");
		try{
		List<String> checkBoxList=(List<String>)valueChangeEvent.getNewValue();
		Set<SelectItem> items=new LinkedHashSet<SelectItem>();
		
		for(String value:checkBoxList){
			items.add(new SelectItem(value));
		}
		languageDropDown.clear();
		languageDropDown.add(new SelectItem("Please Select a Language"));
		languageDropDown.addAll(items);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*@SuppressWarnings("unchecked")
	public void languageSelectionListener(ValueChangeEvent valueChangeEvent){
		logger.info("Listener:languageSelectionListener is called");
		try{
		String selectedMenu=(String)valueChangeEvent.getNewValue();
		Set<SelectItem> items=new LinkedHashSet<SelectItem>();
		
		for(String value:checkBoxList){
			items.add(new SelectItem(value));
		}
		languageDropDown.clear();
		languageDropDown.add(new SelectItem("Please Select a Language"));
		languageDropDown.addAll(items);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/

	/*public String saveApplication() {
		logger.info("saveApplication called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			requiredFieldsService.saveApplication(this.getCompany());
			FacesUtil.addInfo("Application Data saved successfully.");
			logger.info("Application Data saved successfully.");
			return "gotoLandingPage";
		} else {
			FacesUtil
					.addError("Your session has been expired. PLease log in again");
			return "logout";
		}
	}*/
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}


	public List<String> getActiveLanguages() {
		return activeLanguages;
	}


	public void setActiveLanguages(List<String> activeLanguages) {
		this.activeLanguages = activeLanguages;
	}



	public List<SelectItem> getSelectCheckBoxeslist() {
		return selectCheckBoxeslist;
	}



	public void setSelectCheckBoxeslist(List<SelectItem> selectCheckBoxeslist) {
		this.selectCheckBoxeslist = selectCheckBoxeslist;
	}



	public String getSelectedLanguage() {
		return selectedLanguage;
	}



	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}

	public Set<SelectItem> getLanguageDropDown() {
		return languageDropDown;
	}

	public void setLanguageDropDown(Set<SelectItem> languageDropDown) {
		this.languageDropDown = languageDropDown;
	}

	

	
	
}
