/**
 * 
 */
package com.nettracer.claims.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
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
	private static Logger logger = Logger
			.getLogger(ContentsAndLanguageController.class);
	private static final String PASSENGER_LOGIN = "Login";
	private static final String DIRECTION = "Direction";
	private static final String PASSENGER_INFO = "Passenger";
	private static final String FLIGHT_INFO = "Flight";
	private static final String BAGGAGE_INFO = "Baggage";
	private static final String FRAUD_QUESTIONS = "Fraud";
	private static final String SUBMIT_CLAIM = "Submit";
	private static final String GENERAL_INFO = "General";
	private static final String UPLOAD_INFO = "Upload";
	private static final String SAVED_SREEN = "Saved";

	@Autowired
	AdminService adminService;

	private List<String> activeLanguages = new ArrayList<String>();

	private List<SelectItem> selectCheckBoxeslist;

	private Set<String> languagesInSession=new HashSet<String>();

	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();

	private boolean renderTabPanel;
	private String selectedLanguage; //holds the selected combobox value for selecting the language

	private Map<String, List<Map<String, List<Localetext>>>> languageMap ;
	private List<Map<String, List<Localetext>>> pageMapsList ;
	private Set<String> languageSelectedSet=new HashSet<String>();
	private Map<String,Integer> indexMap= new HashMap<String,Integer>();
	

	/**
	 * Page navigation to Content and Language Page
	 * 
	 * @return String
	 */

	public String gotoContentsAndLanguagePage() {
		logger.debug("gotoContentsAndLanguagePage method is called");
		languagesInSession.clear();
		indexMap.clear();
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			languageMap = new HashMap<String, List<Map<String, List<Localetext>>>>();
			pageMapsList = new ArrayList<Map<String, List<Localetext>>>();
			selectCheckBoxeslist = new ArrayList<SelectItem>();
			Set<SelectItem> items = new LinkedHashSet<SelectItem>();
			try {
				List<Languages> allLanguages = adminService.getLanguages();
				
				for (Languages language : allLanguages) {
					selectCheckBoxeslist.add(new SelectItem(language
							.getDescription()));
					if (language.getActiveStatus() == true) {
						if (null == activeLanguages) {
							activeLanguages = new ArrayList<String>();
						}
						activeLanguages.add(language.getDescription());
						items.add(new SelectItem(language.getDescription()));
						languageDropDown.clear();
						languageDropDown.add(new SelectItem("Please Select a Language"));
						languageDropDown.addAll(items);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoContentsAndLanguage";
		} else {
			FacesUtil
					.addError("Your session has been expired. PLease log in again");
			return "logout";
		}
	}

	@SuppressWarnings("unchecked")
	public void manyCheckBoxListener(ValueChangeEvent valueChangeEvent) {
		logger.debug("Listener:manyCheckBoxListener is called");
		try {
			List<String> checkBoxList = (List<String>) valueChangeEvent
					.getNewValue();
			Set<SelectItem> items = new LinkedHashSet<SelectItem>();

			for (String value : checkBoxList) {
				items.add(new SelectItem(value));
			}
			setRenderTabPanel(items.size() > 0 ? true : false);
			languageDropDown.clear();
			languageDropDown.add(new SelectItem("Please Select a Language"));
			if (items.size() == 0) {
				languageDropDown.clear();
			}
			languageDropDown.addAll(items);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void languageSelectionListener(ValueChangeEvent valueChangeEvent) {
		logger.debug("Listener:languageSelectionListener2 is called");
		try {
			String languageSelected = (String) valueChangeEvent.getNewValue();
			setRenderTabPanel(true); //render the Panel on language selection
			HttpSession session = (HttpSession) FacesUtil.getFacesContext()
			.getExternalContext().getSession(false);
			
			session.setAttribute("lang", languageSelected);
			languageSelectedSet.add(languageSelected);
			
			
			for(String language:languageSelectedSet){
				if(language.equals(languageSelected)){
					if(!languageMap.containsKey(language)){
						pageMapsList.add(getAllPageMap(languageSelected));
						int index=pageMapsList.size()-1;
						session.setAttribute("index", index);
						indexMap.put(language,index);
						if( ! languageSelected.equalsIgnoreCase("Please Select a Language")){
							languageMap.put(languageSelected, pageMapsList);
						}
					}else {
						session.setAttribute("index", indexMap.get(languageSelected));
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Localetext>> getAllPageMap(String languageSelected){
		Map<String, List<Localetext>> tempPageMap = new HashMap<String, List<Localetext>>();
		try {
			List<Localetext> passengerLogins = adminService.getPassengerLoginContents(languageSelected);
			List<Localetext> directions = adminService.getDirectionContents(languageSelected);
			List<Localetext> passengers = adminService.getPassengerContents(languageSelected);
			List<Localetext> flights = adminService.getFlightContents(languageSelected);
			List<Localetext> baggages = adminService.getBaggageContents(languageSelected);
			List<Localetext> generals = adminService.getGeneralContents(languageSelected);
			List<Localetext> uploads = adminService.getUploadContents(languageSelected);
			List<Localetext> fraudquestions = adminService.getFraudQuestionContents(languageSelected);
			List<Localetext> submits = adminService.getSubmitContents(languageSelected);
			List<Localetext> saves = adminService.getSavedContents(languageSelected);

			if(null != passengerLogins){
				tempPageMap.put(PASSENGER_LOGIN, passengerLogins);
			}
			if(null != directions){
				tempPageMap.put(DIRECTION, directions);
			}
			
			if(null != passengers){
				tempPageMap.put(PASSENGER_INFO, passengers);
			}
			if(null != flights){
				tempPageMap.put(FLIGHT_INFO, flights);
			}
			if(null != baggages){
				tempPageMap.put(BAGGAGE_INFO, baggages);
			}
			if(null != generals){
				tempPageMap.put(GENERAL_INFO, generals);
			}
			if(null != uploads){
				tempPageMap.put(UPLOAD_INFO, uploads);
			}
			if(null != fraudquestions){
				tempPageMap.put(FRAUD_QUESTIONS, fraudquestions);
			}
			if(null != submits){
				tempPageMap.put(SUBMIT_CLAIM, submits);
			}
			if(null != saves){
				tempPageMap.put(SAVED_SREEN, saves);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tempPageMap;
	}

	public String save() {
		logger.debug("save method called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			try {
				Map<String, List<Map<String, List<Localetext>>>> tempLangMap 
						= new HashMap<String, List<Map<String, List<Localetext>>>>();
				Map<String, List<Map<String, List<Localetext>>>> mainLanguageMap=getLanguageMap();
				if(mainLanguageMap.size() > 0 && null !=languageDropDown &&  languageDropDown.size() > 0){
					Set<String> dropdownValues=new HashSet<String>();
					for(SelectItem item:languageDropDown){
						dropdownValues.add((String)item.getValue());
					}
					for(Map.Entry<String, List<Map<String, List<Localetext>>>> languageMapEntry : mainLanguageMap.entrySet()){
						if( dropdownValues.contains(languageMapEntry.getKey())){
							tempLangMap.put(languageMapEntry.getKey(), languageMapEntry.getValue());
						}
					}
				}
				if(tempLangMap.size() > 0){
					adminService.saveContentLanguageMaps(tempLangMap);
					FacesUtil.addInfo("Contents and Language information saved successfully.");
					logger.info("Contents and Language information saved successfully.");
				}else{
					FacesUtil.addInfo("No data to save");
				}
			} catch (SimplePersistenceException e) {
				e.printStackTrace();
			}
			
			return "gotoLandingPage";
		} else {
			FacesUtil.addError("Your session has been expired. PLease log in again");
			return "logout";
		}
	}
	
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
	public Set<String> getLanguagesInSession() {
		return languagesInSession;
	}
	public void setLanguagesInSession(Set<String> languagesInSession) {
		this.languagesInSession = languagesInSession;
	}
	public Set<SelectItem> getLanguageDropDown() {
		return languageDropDown;
	}
	public void setLanguageDropDown(Set<SelectItem> languageDropDown) {
		this.languageDropDown = languageDropDown;
	}
	public boolean isRenderTabPanel() {
		return renderTabPanel;
	}
	public void setRenderTabPanel(boolean renderTabPanel) {
		this.renderTabPanel = renderTabPanel;
	}
	public String getSelectedLanguage() {
		return selectedLanguage;
	}
	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}

	public Map<String, List<Map<String, List<Localetext>>>> getLanguageMap() {
		return languageMap;
	}

	public void setLanguageMap(
			Map<String, List<Map<String, List<Localetext>>>> languageMap) {
		this.languageMap = languageMap;
	}
	public List<Map<String, List<Localetext>>> getPageMapsList() {
		return pageMapsList;
	}
	public void setPageMapsList(List<Map<String, List<Localetext>>> pageMapsList) {
		this.pageMapsList = pageMapsList;
	}
	public Set<String> getLanguageSelectedSet() {
		return languageSelectedSet;
	}
	public void setLanguageSelectedSet(Set<String> languageSelectedSet) {
		this.languageSelectedSet = languageSelectedSet;
	}

	public Map<String, Integer> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<String, Integer> indexMap) {
		this.indexMap = indexMap;
	}

	

	
}
