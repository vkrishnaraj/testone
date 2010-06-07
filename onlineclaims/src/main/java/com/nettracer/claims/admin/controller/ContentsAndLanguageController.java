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

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.admin.SessionScopeBean;
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
	private static Logger logger = Logger.getLogger(ContentsAndLanguageController.class);
	
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
	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();
	private boolean renderTabPanel;
	private String selectedLanguage; //holds the selected combobox value for selecting the language
	private Map<String, List<Map<String, List<Localetext>>>> languageMap ; //master map
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
		indexMap.clear();
		activeLanguages.clear();
		languageDropDown.clear();
		setRenderTabPanel(false);
		
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
			sessionBean.setLandingRenderer(true); //for landing page link
			
			languageMap = new HashMap<String, List<Map<String, List<Localetext>>>>();
			pageMapsList = new ArrayList<Map<String, List<Localetext>>>();
			selectCheckBoxeslist = new ArrayList<SelectItem>();
			Set<SelectItem> items = new LinkedHashSet<SelectItem>();
			try {
				List<Languages> allLanguages = adminService.getLanguages();
				
				for (Languages language : allLanguages) { //from DB
					selectCheckBoxeslist.add(new SelectItem(language.getDescription())); //adding to checkbox list
					if (language.getActiveStatus() == true) {
						activeLanguages.add(language.getDescription());
						items.add(new SelectItem(language.getDescription()));
						languageDropDown.clear();
						//languageDropDown.add(new SelectItem("Please Select a Language"));
						languageDropDown.addAll(items);
						
						//Logic :set all the tab panel data internally
						if(!languageMap.containsKey(language.getDescription())){
							pageMapsList.add(getAllPageMap(language.getDescription())); //get all the values from DB
							
							//setting the index for xhtml page
							int index=pageMapsList.size()-1;
							//session.setAttribute("index", index); //index value is needed and to be used in the xhtml page
							indexMap.put(language.getDescription(),index);
							
							languageMap.put(language.getDescription(), pageMapsList);
						}
					//end of logic
					}
				}
				if(null != languageDropDown && languageDropDown.size() > 0){
					setSelectedLanguage("Please Select a Language");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoContentsAndLanguage";
		} else {
			if(session != null){
				SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
				sessionBean.setLandingRenderer(false); //for landing page link
			}
			FacesUtil
					.addError("Your session has been expired. PLease log in again");
			return "logout";
		}
	}

	/**
	 * Calling this method at the time of checkbox click.
	 * 
	 * @param valueChangeEvent
	 */
	@SuppressWarnings("unchecked")
	public void manyCheckBoxListener(ValueChangeEvent valueChangeEvent) {
		logger.debug("Listener:manyCheckBoxListener is called");
		languageMap.clear();
		pageMapsList.clear();
		try {
			List<String> newCheckBoxList = (List<String>) valueChangeEvent.getNewValue();
			//List<String> oldCheckBoxList = (List<String>) valueChangeEvent.getOldValue();
			Set<SelectItem> items = new LinkedHashSet<SelectItem>();

			for (String languageValue : newCheckBoxList) {
				items.add(new SelectItem(languageValue));
				
			//Logic :If an admin wants to uncheck a language to deactivate it without touching the drop down
				if(!languageMap.containsKey(languageValue)){
					pageMapsList.add(getAllPageMap(languageValue)); //get all the values from DB
					if( ! languageValue.equalsIgnoreCase("Please Select a Language")){
						//setting the index for xhtml page
						int index=pageMapsList.size()-1;
						indexMap.put(languageValue,index);
						languageMap.put(languageValue, pageMapsList);
					}
				}
			//end of logic
			}
			if(items.size() == 0){
				setRenderTabPanel(false); //don't display the tab panel if all the languages are unchecked
			}
			
			
			languageDropDown.clear();
			//languageDropDown.add(new SelectItem("Please Select a Language"));
			languageDropDown.addAll(items); //construct the final drop down
			
			//set the drop down value --not working right now in the GUI level
			/*if(oldCheckBoxList.size() < newCheckBoxList.size()){
				for(String newLanguageValue :  newCheckBoxList){
					if( ! oldCheckBoxList.contains(newLanguageValue)){
						setSelectedLanguage(newLanguageValue);//set the drop down value for the newly checked language
					}
				}
				
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Listener method call for the language drop down
	 * 
	 * @param valueChangeEvent
	 */
	public void languageSelectionListener(ValueChangeEvent valueChangeEvent) {
		logger.debug("Listener:languageSelectionListener is called");
		
		try {
			String languageSelected = (String) valueChangeEvent.getNewValue();
			setRenderTabPanel(true); //render the Panel on language selection
			HttpSession session = (HttpSession) FacesUtil.getFacesContext()
			.getExternalContext().getSession(false);
			session.setAttribute("lang", languageSelected);
			languageSelectedSet.add(languageSelected);
			
			/*if(languageDropDown.size() >0){
				pageMapsList.clear();
				for(SelectItem item: languageDropDown){
					if(!((String)item.getValue()).equalsIgnoreCase("Please Select a Language")){
						if(!languageMap.containsKey((String)item.getValue())){
							pageMapsList.add(getAllPageMap((String)item.getValue())); //get all the values from DB
							int index=pageMapsList.size()-1;
							session.setAttribute("index", index); //index value is needed and to be used in the xhtml page
							indexMap.put((String)item.getValue(),index);
							languageMap.put((String)item.getValue(), pageMapsList);
						}else {
							session.setAttribute("index", indexMap.get((String)item.getValue()));
						}
						
					}
				
				}
			}*/
			
			for(String language:languageSelectedSet){
				if(null !=language && null !=languageSelected && language.equals(languageSelected)){
					if(!languageMap.containsKey(language)){
						pageMapsList.add(getAllPageMap(languageSelected)); //get all the values from DB
						int index=pageMapsList.size()-1;
						session.setAttribute("index", index); //index value is needed and to be used in the xhtml page
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
	
	/**
	 * To get all the texts w.r.t. its corresponding languages and add it to a Map that would contain 
	 * 	language as a key and the List of pages as the value.
	 * 
	 * @param languageSelected
	 * @return Map<String, List<Localetext>>
	 */
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

	/**
	 * Persist the data for content and Language
	 * 
	 * @return String for page navigation
	 */
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
					setSelectedLanguage("Please Select a Language");
					SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
					sessionBean.setLandingRenderer(false);
				}else{
					FacesUtil.addInfo("No data to save");
				}
			} catch (SimplePersistenceException e) {
				e.printStackTrace();
			}
			return "gotoLandingPage";
		} else {
			if(session != null){
				SessionScopeBean sessionBean = (SessionScopeBean) session.getAttribute("sessionBean");
				sessionBean.setLandingRenderer(false);
			}
			FacesUtil.addError("Your session has been expired. Please log in again");
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
