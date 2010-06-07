/**
 * 
 */
package com.nettracer.claims.admin.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.service.AdminService;
import com.nettracer.claims.core.service.PassengerService;

/**
 * @author utpal
 *
 */
public class PassengerBootstrap implements InitializingBean {
	private static Logger logger = Logger.getLogger(PassengerBootstrap.class);
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
	
	private static List<Localetext> loginPageList;
	private static List<Languages> languageDropDown;

	@Autowired
	AdminService adminService;
	
	/*@Autowired
	PassengerService passengerService;*/
	
	@Override
	public  void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		logger.info("afterPropertiesSet of  PassengerBootstrap is Invoked");
		
		languageDropDown=adminService.getLanguages();
		loginPageList=getAllPageMap("English-US").get(PASSENGER_LOGIN); //by default language is US-English
		logger.info("Size of loginPageList= "+loginPageList);
	}
	
	public static List<Localetext> getLoginPageList(){
		return loginPageList;
	}
	
	
	
	public static List<Languages> getLanguageDropDown() {
		return languageDropDown;
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

	

}
