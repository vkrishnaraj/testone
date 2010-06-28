package com.nettracer.claims.passenger.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVAdvancedIncident;
import com.bagnet.nettracer.ws.core.pojo.xsd.WSPVItem;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.nettracer.claims.admin.bootstrap.PassengerBootstrap;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Address;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;
import com.nettracer.claims.core.model.Passenger;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.core.service.PassengerService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.passenger.LoginBean;
import com.nettracer.claims.passenger.SessionPassengerBean;
import com.nettracer.claims.webservices.client.OnlineClaimsWS;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation for the passenger
 *         side.
 */

@Component
@Scope("request")
@Qualifier("passengerLoginController")
public class PassengerLoginController {
	private static Logger logger = Logger
			.getLogger(PassengerLoginController.class);
	private static final String CAPTCHA_STATUS = "Correct";

	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	// private List<Map<String, List<Localetext>>> pageMapsList ;
	private Map<String, List<Localetext>> pageMaps;
	private List<Localetext> loginPageList;
	private String selectedLanguage = "English-US"; // holds the dropdown value
	// for language selection
	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();
	private List<Localetext> passengerDirectionList;
	private Long baggageState;
	private PassengerBean passengerBean;
	private MultilingualLabel loginLabel;
	/*
	 * @Autowired AdminService adminService;
	 */

	@Autowired
	PassengerService passengerService;

	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	public PassengerLoginController() {
		logger.info("PassengerController constructor");
		List<Languages> languagesList = PassengerBootstrap
				.getLanguageDropDown();
		for (Languages language : languagesList) {
			if (language.getActiveStatus() == true) {
				languageDropDown.add(new SelectItem(language.getDescription()));
			}
		}
		loginPageList = PassengerBootstrap.getLoginPageList();
		logger
				.info("Size of loginPageList inside PassengerController constructor= "
						+ loginPageList.size());
		if (loginPageList != null && loginPageList.size() > 0) {
			setLoginLabels();
		}
	}

	/**
	 * Set the labels for login page
	 * 
	 */
	private void setLoginLabels() {
		loginLabel = new MultilingualLabel();
		for (Localetext localetext : loginPageList) {
			if (localetext.getLabel().getLabel().contains("Claim Number")) {
				loginLabel.setClaimNumber(localetext.getDisplayText());
			} else if (localetext.getLabel().getLabel().contains("Last Name")) {
				loginLabel.setLastName(localetext.getDisplayText());
			} else if (localetext.getLabel().getLabel().contains(
					"Try a different image")) {
				loginLabel.setTryDiffImage(localetext.getDisplayText());
			} else if (localetext.getLabel().getLabel().contains(
					"Type the code shown")) {
				loginLabel.setCaptchaText(localetext.getDisplayText());
			} else if (localetext.getLabel().getLabel().contains("Continue")) {
				loginLabel.setContinueButton(localetext.getDisplayText());
			}
		}
	}

	public void languageSelectionListener(ValueChangeEvent valueChangeEvent) {
		logger.info("Listener:languageSelectionListener is called");
		try {
			selectedLanguage = (String) valueChangeEvent.getNewValue();
			FacesContext context = FacesUtil.getFacesContext();
			HttpSession session = (HttpSession) context.getExternalContext()
					.getSession(false);
			session.setAttribute("selectedLanguage", selectedLanguage);
			/*
			 * HttpSession session = (HttpSession) FacesUtil.getFacesContext()
			 * .getExternalContext().getSession(false);
			 */
			loginPageList = passengerService
					.getPassengerLoginContents(selectedLanguage);
			if (loginPageList != null && loginPageList.size() > 0) {
				setLoginLabels();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * If the validation would successful for login page then navigate to 2nd
	 * page. i.e. the landing page
	 * 
	 * @return String
	 * @throws AxisFault
	 */
	public String gotoDirectionPage() {
		logger.debug("gotoDirectionPage method is called");
		FacesContext context = FacesUtil.getFacesContext();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);
		try {
			PassengerView passengerView = onlineClaimsWS.getPassengerView(
					loginBean.getClaimNumber(), loginBean.getLastName());

			if (passengerView.getAuthenticationSuccess()) {
				if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {

					SessionPassengerBean sessionPassengerBean = (SessionPassengerBean) session
							.getAttribute("sessionPassengerBean");
					sessionPassengerBean.setLogoutRenderer(true);
					session.setAttribute("sessionPassengerBean",
							sessionPassengerBean);
					session.setAttribute("loggedPassenger", "loggedPassenger");

					baggageState = passengerView.getClaimId();
					passengerDirectionList = passengerService
							.getPassengerDirection(selectedLanguage);
					session.setAttribute("passengerDirectionList",
							passengerDirectionList);

					WSPVAdvancedIncident passengerData = passengerView
							.getData();

					passengerBean = onlineClaimsWS
							.getPassengerData(passengerData);

					DataModel airportCodeList = new ListDataModel(
							passengerService.getAirportList());

					session.setAttribute("passengerBean", passengerBean);
					session.setAttribute("baggageState", baggageState);
					session.setAttribute("selectedLanguage", selectedLanguage);
					session.setAttribute("airportCodeList", airportCodeList);
					return "gotoDirectionPage";
				} else {
					clearCaptchaCache();
					return null;
				}

			} else {
				FacesUtil
						.addError("Incorrect Claim Number and Last Name combination. Please try again.");
				logger
						.error("Claim Number and Last Name are incorrect for admin for the IP Adress: "
								+ ((HttpServletRequest) FacesUtil
										.getFacesContext().getExternalContext()
										.getRequest()).getRemoteAddr());
				if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {
					captchaBean.setStatus("");
				}
				clearInputCache();
				clearCaptchaCache();
				return null;
			}
		} catch (AxisFault e) {
			logger.error("AxisFault Error");
			FacesUtil
					.addError("There is a Problem with the webservices, Please try again");
			// Codes for testing :hardcoded data.
			if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {

				SessionPassengerBean sessionPassengerBean = (SessionPassengerBean) session
						.getAttribute("sessionPassengerBean");
				sessionPassengerBean.setLogoutRenderer(true);
				session.setAttribute("sessionPassengerBean",
						sessionPassengerBean);
				session.setAttribute("loggedPassenger", "loggedPassenger");
				try {
					passengerDirectionList = passengerService
							.getPassengerDirection(selectedLanguage);
					session.setAttribute("passengerDirectionList",
							passengerDirectionList);

					passengerBean = new PassengerBean();
					passengerBean.setIncidentID("CBSB600057287");
					List<Address> addresses = new ArrayList<Address>();
					List<Passenger> passengers = new ArrayList<Passenger>();
					for (int i = 0; i < 2; i++) {
						Address address = new Address();
						address.setEmailAddress("utpal@test.com");
						address.setPhone1("123456789");
						address.setPhone2("987654352");
						address.setPhone4("000111222");
						address.setHotel("Hotel1");
						addresses.add(address);
					}

					Passenger passenger = new Passenger();
					passenger.setFirstName("Byron");
					passenger.setLastName("Smith");
					passenger.setMiddleInitial("K");
					passengers.add(passenger);

					if (passengerBean.getItineraryList().size() == 0) {
						for (int i = 0; i < 5; i++) {
							passengerBean.getItineraryList().add(
									new Itinerary());
						}
					}
					passengerBean.setAddress(addresses);
					passengerBean.setPassengers(passengers);

					DataModel airportCodeList = new ListDataModel(
							passengerService.getAirportList());
					session.setAttribute("airportCodeList", airportCodeList);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				session.setAttribute("passengerBean", passengerBean);
				session.setAttribute("baggageState", 1L);
				session.setAttribute("selectedLanguage", selectedLanguage);

				return "gotoDirectionPage";
			} else {
				clearCaptchaCache();
				return null;
			}

			// e.printStackTrace();
			// return null;
		} catch (RemoteException e) {
			logger.error("Error:RemoteException");
			e.printStackTrace();
			return null;
		} catch (SimplePersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 */
	public void clearCaptchaCache() {
		logger
				.debug("clearCaptchaCache method is called to clear the wrong captcha input texts");
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
		HtmlInputText inputSecret = (HtmlInputText) htmlPanelGrid
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

	public List<Localetext> getPassengerDirectionList() {
		return passengerDirectionList;
	}

	public Long getBaggageState() {
		return baggageState;
	}

	public void setBaggageState(Long baggageState) {
		this.baggageState = baggageState;
	}

	public PassengerBean getPassengerBean() {
		return passengerBean;
	}

	public void setPassengerBean(PassengerBean passengerBean) {
		this.passengerBean = passengerBean;
	}

	public MultilingualLabel getLoginLabel() {
		return loginLabel;
	}

	public void setLoginLabel(MultilingualLabel loginLabel) {
		this.loginLabel = loginLabel;
	}

}
