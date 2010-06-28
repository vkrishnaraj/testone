package com.nettracer.claims.passenger.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.Bag;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.core.service.PassengerService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.passenger.LoginBean;
import com.nettracer.claims.webservices.client.OnlineClaimsWS;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation for the passenger
 *         side.
 */

@Component
@Scope("session")
@Qualifier("passengerController")
public class PassengerController {
	private static Logger logger = Logger.getLogger(PassengerController.class);

	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	private PassengerBean passengerBean;
	private Long baggageState;
	// private List<Map<String, List<Localetext>>> pageMapsList ;
	private Map<String, List<Localetext>> pageMaps;
	private List<Localetext> loginPageList;
	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();
	private List<Localetext> passengerDirectionList;
	private MultilingualLabel passengerInfoLabel;
	private MultilingualLabel flightLabel;
	private MultilingualLabel fraudQuestionLabel;
	private MultilingualLabel submitClaimLabel;
	private MultilingualLabel savedScreenLabel;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private Set<SelectItem> lostBagItems = new LinkedHashSet<SelectItem>();
	private DataModel airportCodeList;
	private DataModel itineraryList;
	private int itineraryTableIndex;

	/*
	 * @Autowired AdminService adminService;
	 */

	@Autowired
	PassengerService passengerService;

	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	/**
	 * This is the 1st step for a passenger to fill up his claims form
	 * 
	 * @return String
	 */

	public String gotoPassengerInfo() {
		logger.debug("gotoPassengerInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session
						.getAttribute("selectedLanguage");
				passengerInfoLabel = passengerService.getPassengerInfo(
						selectedLanguage, baggageState);
				this.passengerBean = (PassengerBean) session
						.getAttribute("passengerBean");
				List<CountryCode> countries = passengerService.getCountries();
				for (CountryCode countryCode : countries) {
					selectItems.add(new SelectItem(countryCode.getId(),
							countryCode.getCountry()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoPassengerInfo";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}

	/**
	 * Save the Passenger Data on passengerinfo page
	 * 
	 */

	public void savePassengerInfo() {
		logger.debug("savePassengerInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				// passengerBean =
				// (PassengerBean)session.getAttribute("passengerBean");
				boolean saveData = onlineClaimsWS
						.savePassengerInfo(passengerBean);
				if (saveData) {
					FacesUtil
							.addInfo("Passenger infomation saved successfully.");
					logger.info("Passenger infomation saved successfully.");
				} else {
					logger.error("Error in persisting the Data");
					FacesUtil.addError("Error in persisting the Data");
				}
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
			}
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
		}
	}

	/**
	 * Reset all the input fields to their default values.
	 * 
	 * 
	 */
	public String cancel() {
		FacesContext context = FacesUtil.getFacesContext();
		ViewHandler viewHandler = context.getApplication().getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context
				.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional

		return null;
	}

	/**
	 * This is the 2nd step for a passenger to fill up his claims form i.e. the
	 * Flight details
	 * 
	 * @return String
	 */
	public String gotoFlightDetails() {
		logger.debug("gotoFlightDetails method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session
						.getAttribute("selectedLanguage");
				flightLabel = passengerService.getFlightLabels(
						selectedLanguage, baggageState);
				airportCodeList = (DataModel) session
						.getAttribute("airportCodeList");
				this.itineraryList = new ListDataModel(passengerBean
						.getItineraryList());
				if (null != passengerBean.getDeclarePayExcessValue()
						&& passengerBean.getDeclarePayExcessValue()) {
					flightLabel.setDeclaredValueState(2L);
				} else {
					flightLabel.setDeclaredValueState(1L);
				}

				if (null != passengerBean.getClearCustomBag()
						&& passengerBean.getClearCustomBag()) {
					flightLabel.setBagWeightState(2L);
				} else {
					flightLabel.setBagWeightState(1L);
				}

				if (null != passengerBean.getRerouteBag()
						&& passengerBean.getRerouteBag()) {
					flightLabel.setReroutedCityAirlineState(2L);
				} else {
					flightLabel.setReroutedCityAirlineState(1L);
				}
				// passengerBean =
				// (PassengerBean)session.getAttribute("passengerBean");
				// if(0 == passengerBean.getBagTagList().size()){
				// passengerBean.getBagTagList().add(new HtmlBagComponent());
				// }
				// session.setAttribute("passengerBean", passengerBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoFlightDetails";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}

	/**
	 * Calling this method at selection of the no of baggage on top of the
	 * flight details page
	 * 
	 * @param valueChangeEvent
	 */
	public void bagSelectListener(ValueChangeEvent valueChangeEvent) {
		logger.info("bagSelectListener method");
		Integer noOfBags = (Integer) valueChangeEvent.getNewValue();
		if (null != lostBagItems) {
			lostBagItems.clear();
		}
		if (null != passengerBean.getBagTagList()) {
			passengerBean.getBagTagList().clear();
		}
		if (null != noOfBags && noOfBags > 0) {
			List<Bag> bagList = new ArrayList<Bag>();
			for (int i = 1; i <= noOfBags; i++) {
				lostBagItems.add(new SelectItem(new Integer(i)));
				Bag bag = new Bag();
				bagList.add(bag);
				passengerBean.setBagTagList(bagList); // for about your ticket
														// bag tag table

				// Logic for step 3 o 6 About Your Bag (multiple page)
				passengerBean.setBagList(bagList);
			}
		}
	}

	/**
	 * Calling this method to set the required attribute for Excess Value field.
	 * 
	 * @param valueChangeEvent
	 */
	public void excessValueListener(ValueChangeEvent valueChangeEvent) {
		logger.info("ValueChangeListener called: excessValueListener");
		Boolean valueDeclared = (Boolean) valueChangeEvent.getNewValue();
		if (null != valueDeclared && valueDeclared) {
			flightLabel.setDeclaredValueState(2L);
		} else {
			flightLabel.setDeclaredValueState(1L);
		}
	}

	/**
	 * Calling this method to set the required attribute for Estimated Bag
	 * Weight field.
	 * 
	 * @param valueChangeEvent
	 */
	public void clearCustomListener(ValueChangeEvent valueChangeEvent) {
		logger.info("ValueChangeListener called: clearCustomListener");
		Boolean clearCustom = (Boolean) valueChangeEvent.getNewValue();
		if (null != clearCustom && clearCustom) {
			flightLabel.setBagWeightState(2L);
		} else {
			flightLabel.setBagWeightState(1L);
		}
	}

	/**
	 * Calling this method to set the required attribute for Rerouted City
	 * field.
	 * 
	 * @param valueChangeEvent
	 */
	public void differentClaimCheckListener(ValueChangeEvent valueChangeEvent) {
		logger.info("ValueChangeListener called: differentClaimCheckListener");
		Boolean differentClaim = (Boolean) valueChangeEvent.getNewValue();
		if (null != differentClaim && differentClaim) {
			flightLabel.setReroutedCityAirlineState(2L);
		} else {
			flightLabel.setReroutedCityAirlineState(1L);
		}
	}

	/**
	 * To select the airport code from modal panel and assign to appropriate row
	 * for itinerary datatable.
	 * 
	 * @param event
	 */
	public void selectFromAirportCode(ActionEvent event) {
		logger.info("Listener: selectedAirportCode method");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		Itinerary itineraryLocal = null;
		try {
			Airport airport = (Airport) getAirportCodeList().getRowData();
			/*
			 * if(session.getAttribute("itinerary") != null){
			 * itineraryLocal=(Itinerary)session.getAttribute("itinerary");
			 * }else{ itineraryLocal= new Itinerary(); }
			 */

			if (getItineraryList().isRowAvailable()) {
				itineraryLocal = (Itinerary) getItineraryList().getRowData();
				itineraryLocal.setDepartureCity(airport.getAirportCode());
			} else {
				itineraryLocal = new Itinerary();
				itineraryLocal.setDepartureCity(airport.getAirportCode());
			}
			session.setAttribute("itinerary", itineraryLocal);
			passengerBean.getItineraryList().set(getItineraryTableIndex(),
					itineraryLocal);
			setItineraryList(new ListDataModel(passengerBean.getItineraryList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * To select the airport code from modal panel and assign to appropriate row
	 * for itinerary datatable.
	 * 
	 * @param event
	 */
	public void selectToAirportCode(ActionEvent event) {
		logger.info("Listener: selectedAirportCode2 method");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		Itinerary itineraryLocal = null;
		try {
			Airport airport = (Airport) getAirportCodeList().getRowData();
			if (session.getAttribute("itinerary") != null) {
				itineraryLocal = (Itinerary) session.getAttribute("itinerary");
			} else {
				itineraryLocal = new Itinerary();
			}
			if (getItineraryList().isRowAvailable()) {
				itineraryLocal = (Itinerary) getItineraryList().getRowData();
				itineraryLocal.setArrivalCity(airport.getAirportCode());
			} else {
				itineraryLocal.setArrivalCity(airport.getAirportCode());
			}
			passengerBean.getItineraryList().set(getItineraryTableIndex(),
					itineraryLocal);
			setItineraryList(new ListDataModel(passengerBean.getItineraryList()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Navigation for the Fraud Question Page.i.e. step 5
	 * 
	 * @return String
	 */
	public String gotoFraudQuestion() {
		logger.info("gotoFraudQuestion method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session.getAttribute("selectedLanguage");
				fraudQuestionLabel = passengerService.getFraudQuestionLabel(selectedLanguage, baggageState);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoFraudQuestion";
		} else {

			FacesUtil.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the Claim submission Page.i.e. step 6
	 * 
	 * @return String
	 */
	public String gotoSubmitClaim() {
		logger.info("gotoSubmitClaim method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session
						.getAttribute("selectedLanguage");
				submitClaimLabel = passengerService.getSubmitClaimLabel(
						selectedLanguage, baggageState);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoSubmitClaim";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the last page
	 * 
	 * @return String
	 */
	public String gotoSavedScreen() {
		logger
				.info("gotoSavedScreen method is called to go to the last screen after submitting "
						+ "	a successful claim");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session.getAttribute("selectedLanguage");
				savedScreenLabel = passengerService.getSavedScreenLabel(selectedLanguage, baggageState);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoSavedScreen";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}

	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 */
	public void clearCaptchaCache() {
		logger
				.info("clearCaptchaCache method is called to clear the wrong captcha input texts");
		FacesContext context = FacesUtil.getFacesContext();
		/*
		 * ViewHandler viewHandler = context.getApplication().getViewHandler();
		 * UIViewRoot viewRoot = viewHandler.createView(context,
		 * context.getViewRoot().getViewId()); context.setViewRoot(viewRoot);
		 * context.renderResponse(); // Optional
		 */
		UIViewRoot viewRoot = context.getViewRoot();
		HtmlInputText inputText = null;
		HtmlForm htmlForm = (HtmlForm) viewRoot
				.findComponent("loginPassengerForm");
		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) htmlForm
				.findComponent("loginPassengerPanel");

		inputText = (HtmlInputText) htmlPanelGrid.findComponent("captcha");
		inputText.setValue("");
	}

	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 */
	public void clearInputCache() {
		logger
				.debug("clearInputCache method is called to clear the wrong captcha input texts");
		FacesContext context = FacesUtil.getFacesContext();
		UIViewRoot viewRoot = context.getViewRoot();
		HtmlInputText inputText = null;
		HtmlForm htmlForm = (HtmlForm) viewRoot
				.findComponent("loginPassengerForm");
		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) htmlForm
				.findComponent("loginPassengerPanel");

		inputText = (HtmlInputText) htmlPanelGrid.findComponent("claimNumber");
		if (null != inputText) {
			inputText.setValue("");
		}
		HtmlInputSecret inputSecret = (HtmlInputSecret) htmlPanelGrid
				.findComponent("lastName");
		if (null != inputSecret) {
			inputSecret.setValue("");
		}
	}

	public String passengerLogout() {
		return FacesUtil.passengerLogout();
	}

	public CaptchaBean getCaptchaBean() {
		return captchaBean;
	}

	public void setCaptchaBean(CaptchaBean captchaBean) {
		this.captchaBean = captchaBean;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public Map<String, List<Localetext>> getPageMaps() {
		return pageMaps;
	}

	public void setPageMaps(Map<String, List<Localetext>> pageMaps) {
		this.pageMaps = pageMaps;
	}

	public List<Localetext> getLoginPageList() {
		return loginPageList;
	}

	public void setLoginPageList(List<Localetext> loginPageList) {
		this.loginPageList = loginPageList;
	}

	public Set<SelectItem> getLanguageDropDown() {
		return languageDropDown;
	}

	public void setLanguageDropDown(Set<SelectItem> languageDropDown) {
		this.languageDropDown = languageDropDown;
	}

	public List<Localetext> getPassengerDirectionList() {
		return passengerDirectionList;
	}

	public MultilingualLabel getPassengerInfoLabel() {
		return passengerInfoLabel;
	}

	public MultilingualLabel getFlightLabel() {
		return flightLabel;
	}

	public void setFlightLabel(MultilingualLabel flightLabel) {
		this.flightLabel = flightLabel;
	}

	public PassengerBean getPassengerBean() {
		return passengerBean;
	}

	public void setPassengerBean(PassengerBean passengerBean) {
		this.passengerBean = passengerBean;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public Set<SelectItem> getLostBagItems() {
		return lostBagItems;
	}

	public void setLostBagItems(Set<SelectItem> lostBagItems) {
		this.lostBagItems = lostBagItems;
	}

	public void setOnlineClaimsWS(OnlineClaimsWS onlineClaimsWS) {
		this.onlineClaimsWS = onlineClaimsWS;
	}

	public DataModel getAirportCodeList() {
		return airportCodeList;
	}

	public void setAirportCodeList(DataModel airportCodeList) {
		this.airportCodeList = airportCodeList;
	}

	public DataModel getItineraryList() {
		return itineraryList;
	}

	public void setItineraryList(DataModel itineraryList) {
		this.itineraryList = itineraryList;
	}

	public int getItineraryTableIndex() {
		return itineraryTableIndex;
	}

	public void setItineraryTableIndex(int itineraryTableIndex) {
		this.itineraryTableIndex = itineraryTableIndex;
	}

	public MultilingualLabel getSubmitClaimLabel() {
		return submitClaimLabel;
	}

	public MultilingualLabel getSavedScreenLabel() {
		return savedScreenLabel;
	}

	public void setSavedScreenLabel(MultilingualLabel savedScreenLabel) {
		this.savedScreenLabel = savedScreenLabel;
	}

	public MultilingualLabel getFraudQuestionLabel() {
		return fraudQuestionLabel;
	}

}
