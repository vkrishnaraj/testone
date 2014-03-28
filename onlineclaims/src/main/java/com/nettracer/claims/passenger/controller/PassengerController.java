package com.nettracer.claims.passenger.controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
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
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.Airline;
import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.Bag;
import com.nettracer.claims.core.model.Content;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.Currency;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.Passenger;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.core.model.labels.LabelText;
import com.nettracer.claims.core.model.labels.LabelsAdditionalInfoPage;
import com.nettracer.claims.core.model.labels.LabelsBagDetailsPage;
import com.nettracer.claims.core.model.labels.LabelsFileUploadPage;
import com.nettracer.claims.core.model.labels.LabelsFlightInfoPage;
import com.nettracer.claims.core.model.labels.LabelsGeneralApplication;
import com.nettracer.claims.core.model.labels.LabelsPassengerInfoPage;
import com.nettracer.claims.core.model.labels.LabelsSubmitClaimPage;
import com.nettracer.claims.core.model.labels.LabelsSubmitSuccessPage;
import com.nettracer.claims.core.service.PaxViewService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.faces.util.File;
import com.nettracer.claims.faces.util.FileHelper;
import com.nettracer.claims.faces.util.FileUploadBean;
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
	
	private final int PAGE_DIRECTION = 0;
	private final int PAGE_INSTRUCTION = 1;
	private final int PAGE_LIABILITY = 2;
	private final int PAGE_PASSENGER_INFO = 3;
	private final int PAGE_FLIGHT_INFO = 4;
	private final int PAGE_BAG_INFO = 5;
	private final int PAGE_FILE_UPLOAD = 6;
	private final int PAGE_ADDITIONAL_INFO = 7;
	private final int PAGE_SUBMIT = 8;
	private final int PAGE_SUCCESS = 9;

	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	private PassengerBean passengerBean;
	private Long baggageState;
	// private List<Map<String, List<Localetext>>> pageMapsList ;
	private Map<String, List<Localetext>> pageMaps;
	private List<Localetext> loginPageList;
	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();
	private LabelsPassengerInfoPage passengerInfoLabel;
	private LabelsFlightInfoPage flightLabel;
	private LabelsBagDetailsPage bagDetailsLabel;
	private LabelsFileUploadPage fileUploadLabel;
	private LabelsAdditionalInfoPage fraudQuestionLabel;
	private LabelsSubmitClaimPage submitClaimLabel;
	private LabelsSubmitSuccessPage savedScreenLabel;
	private LabelsGeneralApplication generalLabel;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private Set<SelectItem> lostBagItems = new LinkedHashSet<SelectItem>();
	private DataModel airportCodeList;
	private List<Airport> airportList;
	private int itineraryTableIndex;
	private int bagPageIndex;
	private int bagIndex;
	private List<SelectItem> currencyList;
	private FileUploadBean fileUploadBean;
	private int uploadsAvailable = 10;
	// ------------------- File Upload Myfaces
	private UploadedFile upFile;
	boolean rendSuccess = false;
	boolean rendFailure = false;
	private DataModel fileDataModelList;
	// -- End File Upload
	private String bagChartType;
	// private List<Bag> bags ;
	private boolean readOnlyOnSubmitted;
	private List<SelectItem> airlines;
	private String appFontSize = "none";
	private boolean interimFile = false;
	private int currentPage = PAGE_DIRECTION;	

	@Autowired
	PaxViewService passengerService;

	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	private static long fileId = 1L;

	@PostConstruct
	public void post() {
		HttpSession session = (HttpSession) FacesUtil.getFacesContext().getExternalContext().getSession(false);
		if (session.isNew()) {
            FacesContext fc = FacesContext.getCurrentInstance();
            NavigationHandler nav = fc.getApplication().getNavigationHandler();
			nav.handleNavigation(fc, null, "login?faces-redirect=true");
            fc.renderResponse();
			return;
		}
		
		try {
			baggageState = (Long) session.getAttribute("baggageState");
			String selectedLanguage = (String) session.getAttribute("selectedLanguage");
			passengerInfoLabel = passengerService.getPassengerInfoPage(selectedLanguage, baggageState);
			flightLabel = passengerService.getFlightInfoPage(selectedLanguage, baggageState);
			bagDetailsLabel = passengerService.getBagDetailsPage(selectedLanguage, baggageState);
			fileUploadLabel = passengerService.getFileUploadPage(selectedLanguage, baggageState);
			fraudQuestionLabel = passengerService.getFraudQuestionPage(selectedLanguage, baggageState);
			submitClaimLabel = passengerService.getSubmitClaimPage(selectedLanguage, baggageState);
			savedScreenLabel = passengerService.getSavedScreenPage(selectedLanguage, baggageState);
			generalLabel = passengerService.getGeneralPage(selectedLanguage, baggageState);
			
			List<CountryCode> countries = passengerService.getCountries();
			for (CountryCode countryCode : countries) {
				selectItems.add(new SelectItem(countryCode.getId(), countryCode.getCountry()));
			}
			
			if (airlines == null) {
				airlines = new ArrayList<SelectItem>();
				for (Airline air : passengerService.getAirlines()) {
					airlines.add(new SelectItem(air.getAirlineCode(), air
							.getAirlineDesc()));
				}
			}
			
			currencyList = Currency.getCurrencies();
		} catch (Exception e) {
			logger.error("Post Exception ...", e);
		}
		
	}

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
				this.passengerBean = (PassengerBean) session
						.getAttribute("passengerBean");
				if (null != passengerBean.getStatus()
						&& !passengerBean.getStatus().equalsIgnoreCase("NEW")) {
					readOnlyOnSubmitted = true;
				} else {
					readOnlyOnSubmitted = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentPage = PAGE_PASSENGER_INFO;
			return "gotoPassengerInfo";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * This is the 1st step for a passenger to fill up his claims form
	 * 
	 * @return String
	 */

	public String gotoLiability() {
		logger.debug("gotoLiability method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_LIABILITY;
			return "gotoLiability";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * This is the 1st step for a passenger to fill up his claims form
	 * 
	 * @return String
	 */

	public String gotoPPFInstruction() {
		logger.debug("gotoPPFInstruction method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_INSTRUCTION;
			return "gotoPPFInstruction";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * This is the 1st step for a passenger to fill up his claims form
	 * 
	 * @return String
	 */

	public String goBackToLiability() {
		logger.debug("goBackToLiability method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_LIABILITY;
			return "gotoLiability";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * This is the 1st step for a passenger to fill up his claims form
	 * 
	 * @return String
	 */

	public String goBackToPPFInstruction() {
		logger.debug("goBackToPPFInstruction method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_INSTRUCTION;
			return "gotoPPFInstruction";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Save the Passenger Data on Passenger Contact Info page
	 * 
	 */

	public String savePassengerInfo() {
		if (checkNameValidation()) {
			return actuallySavePassengerInfo();
		}
		return null;
	}

	/**
	 * Save the Passenger Data on Passenger Contact Info page
	 * 
	 */

	public String actuallySavePassengerInfo() {
		logger.debug("savePassengerInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				boolean saveData = onlineClaimsWS.savePassengerInfo(
						passengerBean, (Claim) session.getAttribute("claim"));
				session.setAttribute("saveData", saveData);
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
				return null;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
				return null;
			}
			return null;
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}
	
	private boolean checkNameValidation() {
		boolean lastReq = passengerInfoLabel.getLastName().isRequired();
		boolean firstReq = passengerInfoLabel.getFirstName().isRequired();
		boolean midReq = passengerInfoLabel.getMiddleInitial().isRequired();
		boolean toReturn = true;
		if (passengerBean != null && passengerBean.getPassengers() != null) {
			int passNum = 1;
			for (Passenger pass : passengerBean.getPassengers()) {
				if (lastReq && (pass.getLastName() == null || pass.getLastName().trim().length() == 0)) {
					toReturn = false;
					FacesUtil.addError("Passenger " + passNum + ": Last Name is Required.");
				}
				if (firstReq && (pass.getFirstName() == null || pass.getFirstName().trim().length() == 0)) {
					toReturn = false;
					FacesUtil.addError("Passenger " + passNum + ": First Name is Required.");
				}
				if (midReq && (pass.getMiddleInitial() == null || pass.getMiddleInitial().trim().length() == 0)) {
					toReturn = false;
					FacesUtil.addError("Passenger " + passNum + ": Middle Initial is Required.");
				}
				passNum++;
			}
			if (!(passengerBean.isDelayed() || passengerBean.isDamaged() || passengerBean.isPilferage() || passengerBean.isInterim())) {
				toReturn = false;
				FacesUtil.addError("Claim Type is Required.");
			}
		}
		return toReturn;
	}

	/**
	 * Save the Flight Data on Flight Info page
	 * 
	 */
	public String saveFlightInfo() {
		if (populateItineraryCities()) {
			return actuallySaveFlightInfo();
		}
		return null;
	}

	private String actuallySaveFlightInfo() {
		logger.debug("saveFlightInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				boolean saveFlightData = onlineClaimsWS.saveFlightInfo(
						passengerBean, (Claim) session.getAttribute("claim"));
				// session.setAttribute("saveFlightData", saveFlightData);
				if (saveFlightData) {
					FacesUtil.addInfo("Flight infomation saved successfully.");
					logger.info("Flight infomation saved successfully.");
				} else {
					logger.error("Error in persisting the Data");
					FacesUtil.addError("Error in persisting the Data");
				}
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
				return null;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
				return null;
			}
			return null;
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Save the Bag information
	 * 
	 */
	public String saveBagInfo() {
		if (validateDollarAmounts()) {
			return actuallySaveBagInfo();
		}
		return null;
	}

	private String actuallySaveBagInfo() {
		logger.debug("saveBagInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				boolean saveBagData = onlineClaimsWS.saveBagInfo(passengerBean,
						(Claim) session.getAttribute("claim"));
				// session.setAttribute("saveBagData", saveBagData);
				if (saveBagData) {
					FacesUtil.addInfo("Bag infomation saved successfully.");
					logger.info("Bag infomation saved successfully.");
				} else {
					logger.error("Error in persisting the Data");
					FacesUtil.addError("Error in persisting the Data");
				}
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
				return null;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
				return null;
			}
			return null;
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Save the File information
	 * 
	 */

	public String saveFileInfo() {
		logger.debug("saveFileInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				boolean saveFile = onlineClaimsWS.saveFileInfo(passengerBean,
						(Claim) session.getAttribute("claim"));
				// session.setAttribute("saveFile", saveFile);
				if (saveFile) {
					FacesUtil.addInfo("File infomation saved successfully.");
					logger.info("File infomation saved successfully.");
				} else {
					logger.error("Error in persisting the Data");
					FacesUtil.addError("Error in persisting the Data");
				}
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
				return null;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
				return null;
			}
			return null;
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Save the FraudQuestion
	 * 
	 */

	public String saveFraudQuestion() {
		logger.debug("saveFileInfo method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				boolean savefraud = onlineClaimsWS.saveFraudQuestion(
						passengerBean, (Claim) session.getAttribute("claim"));
				// session.setAttribute("savefraud", savefraud);
				if (savefraud) {
					FacesUtil
							.addInfo("Additional Infomation Saved Successfully.");
					logger.info("additional infomation saved successfully.");
				} else {
					logger.error("Error in persisting the Data");
					FacesUtil.addError("Error in persisting the Data");
				}
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
				return null;
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
				return null;
			}
			return null;
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Save the Final Claim
	 * 
	 */

	public boolean saveFinalCLaim(HttpSession session) {
		logger.debug("submit method is called");
			try {
				boolean finalClaim = onlineClaimsWS.saveFinalCLaim(
						passengerBean, (Claim) session.getAttribute("claim"));
				if (finalClaim) {
					logger.info("CLaim  saved successfully.");
					Claim reload = onlineClaimsWS.getClaim(passengerBean.getPassengerData(), 
							passengerBean.getPassengerData().getPassengersArray(0).getLastname(), 
							passengerBean.getPassengerData().getPassengersArray(0).getFirstname());
					passengerBean = onlineClaimsWS.getPassengerData(passengerBean.getPassengerData(), reload);
					return true;
				} else {
					return false;
				}
			} catch (AxisFault e) {
				e.printStackTrace();
				FacesUtil.addError("Error in persisting the Data");
			} catch (RemoteException e) {
				e.printStackTrace();
				FacesUtil.addError("Connection failure, Please try again");
			}
			return false;
	}

	/**
	 * Submit the Passenger Data on submit claim page
	 * 
	 */

	public String submitPassengerInfo() {
		logger.debug("submitPassengerInfo method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			if (!checkAccept()) {
				FacesUtil.addError("Please type the word ACCEPT in capital letters for every passenger.");
				return null;
			}
			if (saveFinalCLaim(session)) {
				session.setAttribute("passengerBean", passengerBean);
				return gotoSavedScreen();
			} else {
				logger.error("Claim submission is not successful");
				FacesUtil.addError("Claim submission is not successful");
				return null;
			}
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Reset all the input fields to their default values.
	 * 
	 * 
	 */
	public String cancel() {
		logger.debug("cancel method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			/*
			 * FacesContext context = FacesUtil.getFacesContext(); ViewHandler
			 * viewHandler = context.getApplication().getViewHandler();
			 * UIViewRoot viewRoot = viewHandler.createView(context,
			 * context.getViewRoot().getViewId());
			 * context.setViewRoot(viewRoot); context.renderResponse(); //
			 * Optional
			 */
			currentPage = PAGE_DIRECTION;
			return "gotoDirectionPage";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}

	}

	/**
	 * This is the 2nd step for a passenger to fill up his claims form i.e. the
	 * Flight details
	 * 
	 * @return String
	 */
	public String gotoFlightDetails() {
		logger.debug("gotoFlightDetails method is called");
		if (checkNameValidation()) {
			savePassengerInfo();

			HttpSession session = (HttpSession) FacesUtil.getFacesContext()
					.getExternalContext().getSession(false);
			if (null != session && null != session.getAttribute("loggedPassenger")) {
				try {
					airportCodeList = (DataModel) session
							.getAttribute("airportCodeList");
					populateItenarary();
					if (null != passengerBean.getDeclarePayExcessValue()
							&& passengerBean.getDeclarePayExcessValue()) {
						flightLabel.getDeclaredValue().setState(
								LabelText.STATUS_REQUIRED);
					} else {
						flightLabel.getDeclaredValue().setState(
								LabelText.STATUS_NORMAL);
					}
	
					if (1 == passengerBean.getClearCustomBag()) {
						flightLabel.getBagWeight().setState(
								LabelText.STATUS_REQUIRED);
					} else {
						flightLabel.getBagWeight()
								.setState(LabelText.STATUS_NORMAL);
					}
	
					if (null != passengerBean.getRerouteBag()
							&& passengerBean.getRerouteBag()) {
						flightLabel.getReroutedCityAirline().setState(
								LabelText.STATUS_REQUIRED);
					} else {
						flightLabel.getReroutedCityAirline().setState(
								LabelText.STATUS_NORMAL);
					}
	
					// webservice integration code
	
					Integer noOfBags = passengerBean.getBagsTravelWith();
					if (null != noOfBags && noOfBags > 0) {
						if (null != lostBagItems) {
							lostBagItems.clear();
						}
						for (int i = 1; i <= noOfBags; i++) {
							lostBagItems.add(new SelectItem(new Integer(i)));
						}
					}
					
					if (airportList == null) {
						airportList = passengerService.getAirportList();
					}
	
					// end of webservice integration
	
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentPage = PAGE_FLIGHT_INFO;
				return "gotoFlightDetails";
			} else {
				FacesUtil
						.addError("Your session has been expired. Please login again");
				currentPage = PAGE_DIRECTION;
				return "passengerLogout";
			}
		}
		return null;
	}

	private void populateItenarary() {
		List<Airport> airs = null;
		try {
			airs = passengerService.getAirportList();
		} catch (SimplePersistenceException ex) {
			ex.printStackTrace();
			airs = null;
		}
		if (passengerBean.getItineraryList() != null && airs != null) {
			for (Itinerary itin : passengerBean.getItineraryList()) {
				if (itin != null) {
					boolean arriveDone = false;
					boolean departDone = false;
					if (itin.getDepartureCity() == null) {
						departDone = true;
					}
					if (itin.getArrivalCity() == null) {
						arriveDone = true;
					}
					for (Airport air : airs) {
						if (!departDone
								&& itin.getDepartureCity().equalsIgnoreCase(
										air.getAirportCode())) {
							itin.setDeptCityFormText(air.getAirportCode()
									+ " - " + air.getAirportDesc());
							departDone = true;
						}
						if (!arriveDone
								&& itin.getArrivalCity().equalsIgnoreCase(
										air.getAirportCode())) {
							itin.setArrvCityFormText(air.getAirportCode()
									+ " - " + air.getAirportDesc());
							arriveDone = true;
						}
						if (arriveDone && departDone) {
							break;
						}
					}
				}
			}
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
			}
			passengerBean.setBagTagList(bagList); // for about your ticket, bag
													// tag table

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
			flightLabel.getDeclaredValue().setState(LabelText.STATUS_REQUIRED);
		} else {
			flightLabel.getDeclaredValue().setState(LabelText.STATUS_NORMAL);
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
		Integer clearCustom = (Integer) valueChangeEvent.getNewValue();
		if (null != clearCustom && clearCustom == 1) {
			flightLabel.getBagWeight().setState(LabelText.STATUS_REQUIRED);
		} else {
			flightLabel.getBagWeight().setState(LabelText.STATUS_NORMAL);
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
			flightLabel.getReroutedCityAirline().setState(
					LabelText.STATUS_REQUIRED);
		} else {
			flightLabel.getReroutedCityAirline().setState(
					LabelText.STATUS_NORMAL);
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

			itineraryLocal = passengerBean.getItineraryList().get(
					getItineraryTableIndex());
			itineraryLocal.setDepartureCity(airport.getAirportCode());
			itineraryLocal.setDeptCityFormText(airport.getAirportCode() + "-"
					+ airport.getAirportDesc());
			passengerBean.getItineraryList().set(getItineraryTableIndex(),
					itineraryLocal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Call for validation of itinerary table
	 * 
	 * 
	 */
	public String getModalPanel() {
		logger.info("getModalPanel method");
		if (passengerBean.getItineraryList() != null
				&& passengerBean.getItineraryList().get(
						getItineraryTableIndex()) != null
				&& passengerBean.getItineraryList()
						.get(getItineraryTableIndex()).getDepartureCity() != null) {
			return "flightModalPanel2";
		} else {
			return "warnModalPanel";
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
			itineraryLocal = passengerBean.getItineraryList().get(
					getItineraryTableIndex());
			itineraryLocal.setArrivalCity(airport.getAirportCode());
			itineraryLocal.setArrvCityFormText(airport.getAirportCode() + "-"
					+ airport.getAirportDesc());
			passengerBean.getItineraryList().set(getItineraryTableIndex(),
					itineraryLocal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adding More Segment for the itinerary
	 * 
	 * @return String
	 */
	public String addNewSegment() {
		logger.info("addNewSegment method call");
		passengerBean.getItineraryList().add(new Itinerary());
		setItineraryTableIndex(0);
		return null;
	}

	/**
	 * Adding More Segment for the itinerary
	 * 
	 * @return String
	 */
	public String deleteLastSegment() {
		logger.info("deleteLastSegment method call");
		if (passengerBean != null && passengerBean.getItineraryList() != null
				&& passengerBean.getItineraryList().size() > 1) {
			passengerBean.getItineraryList().remove(
					passengerBean.getItineraryList().size() - 1);
		}
		setItineraryTableIndex(0);
		return null;
	}

	/**
	 * Adding More Segment for the itinerary
	 * 
	 * @return String
	 */
	public String addNewPass() {
		logger.info("addNewPass method call");
		passengerBean.getPassengers().add(new Passenger());
		return null;
	}

	/**
	 * Adding More Segment for the itinerary
	 * 
	 * @return String
	 */
	public String deleteLastPass() {
		logger.info("deleteLastPass method call");
		if (passengerBean != null && passengerBean.getPassengers() != null
				&& passengerBean.getPassengers().size() > 1) {
			passengerBean.getPassengers().remove(
					passengerBean.getPassengers().size() - 1);
		}
		return null;
	}

	public boolean isRenderDeleteSegment() {
		if (!isReadOnlyOnSubmitted()) {
			// if (passengerBean != null && passengerBean.getItineraryList() !=
			// null && passengerBean.getItineraryList().size() > 1) {
			return true;
			// }
		}
		return false;
	}

	public List<Airport> autocomplete(Object suggest) {
		Date start = new Date();
		String pref = (String) suggest;
		pref = pref.toLowerCase();
		if (pref.length() > 6) {
			pref = pref.substring(0, 6);
		}
		
		List<Airport> airports = null;
		try {
			airports = passengerService.getAirportList(pref);
		} catch (Exception e) {
			
		}
		
		if (airports != null) {
			logger.fatal("Returning SQL Generated AutoComplete List. " + (new Date().getTime() - start.getTime()));
			return airports;
		} else {
			ArrayList<Airport> result = new ArrayList<Airport>();
			ArrayList<Airport> coded = new ArrayList<Airport>();
			ArrayList<Airport> described = new ArrayList<Airport>();
	
			Iterator<Airport> iterator = airportList.iterator();
	
			while (iterator.hasNext()) {
				Airport elem = ((Airport) iterator.next());
				boolean notCode = true;
				if (elem != null && elem.getAirportCode() != null) {
					String code = elem.getAirportCode().toLowerCase().trim();
					if (code.startsWith(pref)) {
							coded.add(elem);
							notCode = false;
					}
				}
				if (notCode && elem != null && elem.getAirportDesc() != null) {
					String desc = elem.getAirportDesc().toLowerCase().trim();
					if (desc.startsWith(pref)) {
							described.add(elem);
					}
				}
			}
			result.addAll(coded);
			result.addAll(described);
	
			return result;
		}
	}
	
	private boolean populateItineraryCities() {
		boolean noErrors = true;
		if (passengerBean.getItineraryList() != null && passengerBean.getItineraryList().size() > 0) {
			int itinCount = 1;
			boolean isBlank = true;
			for (Itinerary itin : passengerBean.getItineraryList()) {
				if (itin != null) {
					String deptCity = itin.getDeptCityFormText();
					String arrvCity = itin.getArrvCityFormText();
					if (deptCity != null && deptCity.length() > 2
							&& checkValidAirport(deptCity.substring(0, 3))) {
						itin.setDepartureCity(deptCity.substring(0, 3).toUpperCase());
						isBlank = false;
					} else if (deptCity == null || deptCity.trim().length() == 0) {
						continue;
					} else {
						if (noErrors) {
							FacesUtil.addError("When filling out the \"From/To Airports\" section please select your airport" +
									" from the autocomplete list.");
						}
						FacesUtil.addError("The Departure City for Segment #" + itinCount + " is not valid.");
						noErrors = false;
					}
					if (arrvCity != null && arrvCity.length() > 2
							&& checkValidAirport(arrvCity.substring(0, 3))) {
						itin.setArrivalCity(arrvCity.substring(0, 3).toUpperCase());
					} else {
						if (noErrors) {
							FacesUtil.addError("When filling out the \"From/To Airports\" section please select your airport" +
									" from the autocomplete list.");
						}
						FacesUtil.addError("The Arrival City for Segment #" + itinCount + " is not valid.");
						noErrors = false;
					}
					if (itin.getAirline() == null || itin.getAirline().trim().length() == 0) {
						FacesUtil.addError("The Airline for Segment #" + itinCount + " is required.");
						noErrors = false;
					}
					if (itin.getFlightNum() == null || itin.getFlightNum().trim().length() == 0) {
						FacesUtil.addError("The Flight Number for Segment #" + itinCount + " is required.");
						noErrors = false;
					}
					if (itin.getJourneyDate() == null) {
						FacesUtil.addError("The Journey Date for Segment #" + itinCount + " is required.");
						noErrors = false;
					}
				} else {
					FacesUtil.addError("Segment #" + itinCount + " is invalid.");
					noErrors = false;
				}
				itinCount++;
			}
			if (isBlank) {
				FacesUtil.addError("There needs to be at least one segment for your itinerary.");
				noErrors = false;
			}
		} else {
			FacesUtil.addError("There needs to be at least one segment for your itinerary.");
			noErrors = false;
		}
		if (noErrors) {
			return true;
		}
		return false;
	}
	
	private boolean checkValidAirport(String airport) {
		airport = airport.toLowerCase().trim();
		if (airport.length() == 3) {
			for (Airport aPort : airportList) {
				if (airport.equals(aPort.getAirportCode().toLowerCase().trim())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Navigation for the Bagage Details Page.i.e. step 3
	 * 
	 * @return String
	 */
	public String gotoBagDetails() {
		logger.info("gotoBagDetails method is called");
		if (populateItineraryCities()) {
			actuallySaveFlightInfo();
			HttpSession session = (HttpSession) FacesUtil.getFacesContext()
					.getExternalContext().getSession(false);
			if (null != session && null != session.getAttribute("loggedPassenger")) {
				try {
					List<Bag> bagTagList = passengerBean.getBagTagList();
					List<Bag> lostBagList = new ArrayList<Bag>();
					if (passengerBean.getBagsTravelWith() == 0) {
						FacesUtil.addError("Please select the number of checked bags you traveled with");
						return null;
					}
					if (bagTagList != null && bagTagList.size() > 0) {
						for (Bag lostBag : bagTagList) {
							if (passengerBean.isPilferage() || passengerBean.isDamaged() || !(Boolean.parseBoolean(lostBag.getBagArrivalStatus()))) {
								Bag tempBag = new Bag();
								tempBag.setBagArrivalStatus(lostBag.getBagArrivalStatus());
								tempBag.setBagTagNumber(lostBag.getBagTagNumber());
								lostBagList.add(tempBag);
							}
						}
						if (!(passengerBean.isPilferage() || passengerBean.isDamaged()) && passengerBean.isDelayed() && passengerBean.getLostBag() != lostBagList.size()) {
							FacesUtil.addError("Please match the no. of Bags lost with the Arrival status of the Bag");
							return null;
						}
						
						if (passengerBean.isInterim()) {
							Bag interimBag = new Bag();
							interimBag.setBagArrivalStatus("true");
							interimBag.setBagTagNumber("INTERIM");
							lostBagList.add(interimBag);
						}
						// Logic for step 3 o 6 About Your Bag (multiple page)
						// passengerBean.setBagList(lostBagList);
	
						List<Bag> compiledList = new ArrayList<Bag>();
						if (passengerBean.getBagList() == null
								|| passengerBean.getBagList().size() == 0) {
							compiledList = lostBagList;
						} else {
							List<Bag> oldBagList = passengerBean.getBagList();
							int offset = 0;
							if (passengerBean.isInterim() && "INTERIM".equals(oldBagList.get(oldBagList.size() - 1).getBagTagNumber())) {
								offset = 1;
							}
							for (Bag l : lostBagList) {
								if ((oldBagList.size() - offset) > 0) {
									int index = 0;
									if (l.getBagTagNumber().equals("INTERIM")) {
										index = oldBagList.size() - 1;
									}
									Bag b = oldBagList.get(index);
									if (b != null) {
										b.setBagTagNumber(l.getBagTagNumber());
										b.setBagArrivalStatus(l.getBagArrivalStatus());
										compiledList.add(b);
									}
									oldBagList.remove(index);
								} else {
									if (l.getBagTagNumber().equals("INTERIM") && oldBagList.size() > 0) {
										Bag b = oldBagList.get(0);
										if (b != null) {
											b.setBagTagNumber(l.getBagTagNumber());
											b.setBagArrivalStatus(l.getBagArrivalStatus());
											compiledList.add(b);
										}
									} else {
										compiledList.add(l);
									}
								}
							}
						}
	
						for (Bag b : compiledList) {
							if (b.getContentList() == null
									|| b.getContentList().size() == 0) {
								List<Content> contentList = new ArrayList<Content>();
								for (int i = 1; i <= 1; i++) {
									Content content = new Content();
									content.setPrice(0.0);
									content.setPriceString("0.00");
									contentList.add(content);
								}
								b.setContentList(contentList);
								contentList = null;
							}
						}
						passengerBean.setBagList(compiledList);
					}
	
					// session.setAttribute("passengerBean", passengerBean);
	
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentPage = PAGE_BAG_INFO;
				return "gotoBagDetails";
			} else {
				FacesUtil
						.addError("Your session has been expired. Please log in again");
				currentPage = PAGE_DIRECTION;
				return "passengerLogout";
			}
		}
		return null;
	}

	/**
	 * Adding More items per Baggage
	 * 
	 * @return String
	 */
	public String addMoreItems() {
		logger.info("addMoreItems method call");
		Bag bag = passengerBean.getBagList().get(getBagIndex());
		Content tempContent = new Content();
		tempContent.setPrice(0.0);
		tempContent.setPriceString("0.00");
		bag.getContentList().add(tempContent);
		passengerBean.getBagList().set(getBagIndex(), bag);
		return null;
	}

	/**
	 * Deleting item per Baggage
	 * 
	 * @return String
	 */
	public String deleteLastItem() {
		logger.info("deleteLastItem method call");
		Bag bag = passengerBean.getBagList().get(getBagIndex());
		if (bag != null && bag.getContentList() != null
				&& bag.getContentList().size() > 1) {
			bag.getContentList().remove(bag.getContentList().size() - 1);
			passengerBean.getBagList().set(getBagIndex(), bag);
		}
		return null;
	}

	/**
	 * Select the Bag Type from the pop up Image.
	 * 
	 * @return String
	 */
	public String selectBagChart() {
		logger.info("selectBagChart method call");
		Bag bag = passengerBean.getBagList().get(getBagPageIndex());
		bag.setBagType(getBagChartType());
		passengerBean.getBagList().set(getBagPageIndex(), bag);
		return null;
	}

	/**
	 * Navigation for the File Upload Page.i.e. step 4
	 * 
	 * @return String
	 */
	public String gotoFileUpload() {
		logger.info("gotoFileUpload method is called");
		if (validateDollarAmounts()) {
			actuallySaveBagInfo();
			HttpSession session = (HttpSession) FacesUtil.getFacesContext()
					.getExternalContext().getSession(false);
			if (null != session
					&& null != session.getAttribute("loggedPassenger")) {
				try {
					if (null != passengerBean.getFiles()
							&& passengerBean.getFiles().size() > 0) {
						fileDataModelList = new ListDataModel(
								passengerBean.getFiles());
					}
					session.setAttribute("passengerBean", passengerBean);
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentPage = PAGE_FILE_UPLOAD;
				return "gotoFileUpload";
			} else {
				FacesUtil
						.addError("Your session has been expired. Please log in again");
				currentPage = PAGE_DIRECTION;
				return "passengerLogout";
			}
		}
		return null;
	}

	private boolean validateDollarAmounts() {
		List<Bag> bags = passengerBean.getBagList();
		boolean goodFormat = true;
		if (bags != null && bags.size() > 0) {
			int b = 1;
			for (Bag bag : bags) {
				if (bag != null && bag.getContentList() != null
						&& bag.getContentList().size() > 0) {
					List<Content> contents = bag.getContentList();
					int c = 1;
					for (Content cont : contents) {
						String price = cont.getPriceString();
						if (price != null && price.trim().length() > 0) {
							price = price.replaceAll("[^0-9\\.]", "");
							if (price.length() > 0 && price.matches("^[0-9]*\\.?[0-9]{0,2}$")) {
								cont.setPrice(Double.parseDouble(price));
								cont.setPriceString(price);
							} else {
								goodFormat = false;
								FacesUtil.addError("Please enter the price for content "
												+ c + " in bag " + b + " using the ####.## format");
							}
						} else {
							goodFormat = false;
							FacesUtil.addError("Please enter the price for content "
											+ c + " in bag " + b + " using the ####.## format");
						}
						c++;
					}
				}
				b++;
			}
		}
		return goodFormat;
	}

	/**
	 * 
	 * File Upload Logic
	 * 
	 * @throws IOException
	 */
	public String upload() throws IOException {
		logger.info("upload called");
		try {
			if (upFile != null) {
				int extDot = upFile.getName().lastIndexOf('.');
				if (extDot > 0) {
					String extension = upFile.getName().substring(extDot + 1);
					if (extension.equals("jpg") || extension.equals("gif")
							|| extension.equals("pdf")
							|| extension.equalsIgnoreCase("png")) {
						// InputStream stream = upFile.getInputStream();
						// long size = upFile.getSize();
						// byte[] buffer = new byte[(int) size];
						/*
						 * stream.read(buffer, 0, (int) size); stream.close();
						 * rendSuccess = true; rendFailure = false;
						 */
						String fileName = upFile.getName().contains("\\") ? upFile
								.getName().substring(
										upFile.getName().lastIndexOf("\\") + 1,
										upFile.getName().length()) : upFile
								.getName(); // Fixing the bugs in IE
						File file = new File();
						byte[] data = upFile.getBytes();
						file.setName(fileName);
						file.setLength(upFile.getSize());
						file.setData(data);
						// file.setId(fileId++);
						if (passengerBean.getFiles() != null) {
							List<File> existingFiles = passengerBean.getFiles();
							for (File existingFile : existingFiles) {
								if (fileName.equals(existingFile.getName())) {
									logger.warn("File is already existing");
									FacesUtil
											.addError("File is already existing");
									return null; //"no";
								}
							}
							HttpSession session = (HttpSession) FacesUtil
									.getFacesContext().getExternalContext()
									.getSession(false);
							FileHelper.saveImage(passengerBean.getIncidentID(),
									fileName, data);
							file.setPath(FileHelper.getPath());
							file.setInterim(isInterimFile());
							passengerBean.getFiles().add(file);
							fileDataModelList = new ListDataModel(
									passengerBean.getFiles());
							logger.info("File Uploaded Successfully.");
						}
						file = null;
						return null; //"ok";
					} else {
						logger.error("File type not supported");
						FacesUtil.addError("File type not supported");
					}
				}

			}
			return null; //"no";
		} catch (Exception ioe) {
			logger.info("File Upload Unsuccessful.");
			ioe.printStackTrace();
			rendSuccess = false;
			rendFailure = true;
			return null; //"no";
		}
	}

	/**
	 * 
	 * Remove a File from the main file list
	 * 
	 */
	public void removeFileListener(ActionEvent event) {
		logger.info("removeFileListener called");
		File file = (File) fileDataModelList.getRowData();
		List<File> files = passengerBean.getFiles();
		int fileSize = files.size();
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		try {
			FileHelper.deleteImage(passengerBean.getIncidentID(), file.getName(), file.getPath());
			for (int i = fileSize - 1; i >= 0; i--) {
				File f = files.get(i);
				if (f.getName().equals(file.getName())) {
					passengerBean.getFiles().remove(i);
				}
			}
			fileDataModelList = null;
			fileDataModelList = new ListDataModel(passengerBean.getFiles());
		} catch (IOException e) {
			logger.info("File can not be deleted");
			e.printStackTrace();
		}

	}

	// -------------------End of File Upload

	/**
	 * Navigation for the Fraud Question Page.i.e. step 5
	 * 
	 * @return String
	 */
	public String gotoFraudQuestion() {
		logger.info("gotoFraudQuestion method is called");
		saveFileInfo();

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				if (1 == passengerBean.getAnotherClaim()) {
					fraudQuestionLabel.getWhichAirline().setState(
							LabelText.STATUS_REQUIRED);
					fraudQuestionLabel.getDateOfClaim().setState(
							LabelText.STATUS_REQUIRED);
					fraudQuestionLabel.getClaimantName().setState(
							LabelText.STATUS_REQUIRED);
				} else {
					fraudQuestionLabel.getWhichAirline().setState(
							LabelText.STATUS_NORMAL);
					fraudQuestionLabel.getDateOfClaim().setState(
							LabelText.STATUS_NORMAL);
					fraudQuestionLabel.getClaimantName().setState(
							LabelText.STATUS_NORMAL);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentPage = PAGE_ADDITIONAL_INFO;
			return "gotoFraudQuestion";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Listener for the Fraud Question Page.i.e. step 5
	 * 
	 * 
	 */
	public void selectAnotherClaim(ValueChangeEvent valueChangeEvent) {
		logger.info("Listener: selectAnotherClaim called");
		Integer anotherClaim = (Integer) valueChangeEvent.getNewValue();
		if (anotherClaim != null && anotherClaim == 1) {
			fraudQuestionLabel.getWhichAirline().setState(
					LabelText.STATUS_REQUIRED);
			fraudQuestionLabel.getDateOfClaim().setState(
					LabelText.STATUS_REQUIRED);
			fraudQuestionLabel.getClaimantName().setState(
					LabelText.STATUS_REQUIRED);
		} else {
			fraudQuestionLabel.getWhichAirline().setState(
					LabelText.STATUS_NORMAL);
			fraudQuestionLabel.getDateOfClaim().setState(
					LabelText.STATUS_NORMAL);
			fraudQuestionLabel.getClaimantName().setState(
					LabelText.STATUS_NORMAL);
		}
	}

	/**
	 * Navigation for the Claim submission Page.i.e. step 6
	 * 
	 * @return String
	 */
	public String gotoSubmitClaim() {
		logger.info("gotoSubmitClaim method is called");
		saveFraudQuestion();
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			//populateClaimTotal(); //REMOVED PER MEAGAN 7-28-11
			currentPage = PAGE_SUBMIT;
			return "gotoSubmitClaim";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}
	
	private void populateClaimTotal() {
		String theCurrency = null;
		double theTotal = 0D;
		if (passengerBean != null && passengerBean.getBagList() != null) {
			for (Bag bag : passengerBean.getBagList()) {
				if (bag != null) {
					String temp = bag.getGrandTotal();
					if (temp != null && !temp.equals("Multiple Currencies.")) {
						if (theCurrency == null) {
							theCurrency = bag.getBagCurrency();
						} else if (!theCurrency.equals(bag.getBagCurrency())) {
							theCurrency = "USD";
							theTotal = 0D;
							break;
						}
						theTotal += Double.parseDouble(temp);
					} else {
						theCurrency = "USD";
						theTotal = 0D;
						break;
					}
				}
			}
			theTotal = Math.round(theTotal * 100)/100.0D;
			passengerBean.setClaimAmount(theTotal + "");
			passengerBean.setClaimAmountCurrency(theCurrency);
		}
		
	}

	/**
	 * Navigation for the last page
	 * 
	 * @return String
	 */
	public String gotoSavedScreen() {
		logger.info("gotoSavedScreen method is called to go to the last screen after submitting "
				+ "	a successful claim");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			setReadOnlyOnSubmitted(true);
			passengerBean.setStatus("SUBMITTED");
			currentPage = PAGE_SUCCESS;
			return "gotoSavedScreen";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}
	
	private boolean checkAccept() {
		if (passengerBean.getPassengers() != null && passengerBean.getPassengers().size() > 0) {
			for (Passenger pass : passengerBean.getPassengers()) {
				if (pass == null || pass.getAccept() == null || !pass.getAccept().trim().equals("ACCEPT")) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 */
	public void clearCaptchaCache() {
		logger.info("clearCaptchaCache method is called to clear the wrong captcha input texts");
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
		logger.debug("clearInputCache method is called to clear the wrong captcha input texts");
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

	/**
	 * Navigation for thePassengerDetails while clicking on Previous Button
	 * 
	 * @return String
	 */
	public String goBackToPassengerDetails() {
		logger.debug("goBackToPassengerDetails method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_PASSENGER_INFO;
			return "gotoPassengerInfo";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the FlightDetails while clicking on Previous Button
	 * 
	 * @return String
	 */
	public String goBackToFlightDetails() {
		logger.debug("goBackToFlightDetails method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			populateItenarary();
			currentPage = PAGE_FLIGHT_INFO;
			return "gotoFlightDetails";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the Bag Details while clicking on Previous Button
	 * 
	 * @return String
	 */
	public String goBackToBagDetails() {
		logger.debug("goBackToBagDetails method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_BAG_INFO;
			return "gotoBagDetails";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the FileUpload while clicking on Previous Button
	 * 
	 * @return String
	 */
	public String goBackToFileUpload() {
		logger.debug("goBackToFileUpload method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_FILE_UPLOAD;
			return "gotoFileUpload";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the FraudQuestion while clicking on Previous Button
	 * 
	 * @return String
	 */
	public String goBackToFraudQuestion() {
		logger.debug("goBackToFraudQuestion method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_ADDITIONAL_INFO;
			return "gotoFraudQuestion";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}

	/**
	 * Navigation for the FraudQuestion while clicking on Previous Button
	 * 
	 * @return String
	 */
	public String goBackToSubmitClaim() {
		logger.debug("goBackToFraudQuestion method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			currentPage = PAGE_SUBMIT;
			return "gotoSubmitClaim";
		} else {
			FacesUtil
					.addError("Your session has been expired. Please log in again");
			currentPage = PAGE_DIRECTION;
			return "passengerLogout";
		}
	}
	
	public boolean isBcdRender() {
		if (passengerBean != null && passengerBean.getBagCheckLocation() != null
				&& passengerBean.getBagCheckLocation().trim().equals("Other")) {
			return true;
		}
		return false;
	}
	
	public List<SelectItem> getOwnerList() {
		if (passengerBean != null && passengerBean.getPassengers() != null) {
			List<SelectItem> toReturn = new ArrayList<SelectItem>();
			for (Passenger pass : passengerBean.getPassengers()) {
				if (pass != null) {
					String selValue = pass.getLastName() + "::" + pass.getFirstName() + (pass.getMiddleInitial() != null ? "::" + pass.getMiddleInitial() : "");
					String selLabel = (pass.getLastName().length() > 4 ? pass.getLastName().substring(0, 4) + ".." : pass.getLastName())
							+ ", " + pass.getFirstName() + (pass.getMiddleInitial() != null ? " " + pass.getMiddleInitial() : "");
					toReturn.add(new SelectItem(selValue, selLabel));
				}
			}
			return toReturn;
		}
		return new ArrayList<SelectItem>();
	}
	
	public void setOwnerList(List<SelectItem> list) {
		//EMPTY
	}

	public String passengerLogout() {
//		switch (currentPage) {
//			case PAGE_PASSENGER_INFO:
//				savePassengerInfo();
//				break;
//			case PAGE_FLIGHT_INFO:
//				saveFlightInfo();
//				break;
//			case PAGE_BAG_INFO:
//				saveBagInfo();
//				break;
//			case PAGE_FILE_UPLOAD:
//				saveFileInfo();
//				break;
//			case PAGE_ADDITIONAL_INFO:
//				saveFraudQuestion();
//				break;
//			case PAGE_SUBMIT:
//				submitPassengerInfo();
//				break;
//		}
//		if (!FacesUtil.isError()) {
			currentPage = PAGE_DIRECTION;
			return FacesUtil.passengerLogout();
//		}
//		return null;
	}
	
	public String getBagCheckDescriptionPrint() {
		if (passengerBean == null) {
			HttpSession session = (HttpSession) FacesUtil.getFacesContext().getExternalContext().getSession(false);
			this.passengerBean = (PassengerBean) session.getAttribute("passengerBean");
		}
		String toReturn = passengerBean.getBagCheckDescription();
		return (toReturn == null ? "" : ": " + toReturn);
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

	public LabelsPassengerInfoPage getPassengerInfoLabel() {
		return passengerInfoLabel;
	}

	public LabelsGeneralApplication getGeneralLabel() {
		return generalLabel;
	}

	public LabelsFlightInfoPage getFlightLabel() {
		return flightLabel;
	}

	public void setFlightLabel(LabelsFlightInfoPage flightLabel) {
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

	public int getItineraryTableIndex() {
		return itineraryTableIndex;
	}

	public void setItineraryTableIndex(int itineraryTableIndex) {
		this.itineraryTableIndex = itineraryTableIndex;
	}

	public LabelsSubmitClaimPage getSubmitClaimLabel() {
		return submitClaimLabel;
	}

	public LabelsSubmitSuccessPage getSavedScreenLabel() {
		return savedScreenLabel;
	}

	public void setSavedScreenLabel(LabelsSubmitSuccessPage savedScreenLabel) {
		this.savedScreenLabel = savedScreenLabel;
	}

	public LabelsAdditionalInfoPage getFraudQuestionLabel() {
		return fraudQuestionLabel;
	}

	public LabelsFileUploadPage getFileUploadLabel() {
		return fileUploadLabel;
	}

	public FileUploadBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(FileUploadBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	// -------------------Myfaces File Upload
	public UploadedFile getUpFile() {
		return upFile;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}

	public boolean getRendSuccess() {
		return rendSuccess;
	}

	public void setRendSuccess(boolean rendSuccess) {
		this.rendSuccess = rendSuccess;
	}

	public boolean getRendFailure() {
		return rendFailure;
	}

	public void setRendFailure(boolean rendFailure) {
		this.rendFailure = rendFailure;
	}

	public DataModel getFileDataModelList() {
		return fileDataModelList;
	}

	public void setFileDataModelList(DataModel fileDataModelList) {
		this.fileDataModelList = fileDataModelList;
	}

	public LabelsBagDetailsPage getBagDetailsLabel() {
		return bagDetailsLabel;
	}

	public void setBagDetailsLabel(LabelsBagDetailsPage bagDetailsLabel) {
		this.bagDetailsLabel = bagDetailsLabel;
	}

	public int getBagIndex() {
		return bagIndex;
	}

	public void setBagIndex(int bagIndex) {
		this.bagIndex = bagIndex;
	}

	public List<SelectItem> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<SelectItem> currencyList) {
		this.currencyList = currencyList;
	}

	public String getBagChartType() {
		return bagChartType;
	}

	public void setBagChartType(String bagChartType) {
		this.bagChartType = bagChartType;
	}

	public int getBagPageIndex() {
		return bagPageIndex;
	}

	public void setBagPageIndex(int bagPageIndex) {
		this.bagPageIndex = bagPageIndex;
	}

	public boolean isReadOnlyOnSubmitted() {
		return readOnlyOnSubmitted;
	}

	public void setReadOnlyOnSubmitted(boolean readOnlyOnSubmitted) {
		this.readOnlyOnSubmitted = readOnlyOnSubmitted;
	}

	public List<SelectItem> getAirlines() {
		return airlines;
	}

	public void setAirlines(List<SelectItem> airlines) {
		this.airlines = airlines;
	}

	public boolean isInterimFile() {
		return interimFile;
	}

	public void setInterimFile(boolean interimFile) {
		this.interimFile = interimFile;
	}

	public String getAppFontSize() {
		return appFontSize;
	}

	public void setAppFontSize(String appFontSize) {
		this.appFontSize = appFontSize;
	}

	/**
	 * Test Code : to be deleted later on : Richfaces File Upload -not
	 * compatible with this application
	 * 
	 * @param event
	 * @throws Exception
	 */
	/*
	 * public void fileUploadListener(UploadEvent event) throws Exception {
	 * logger.info("File Upload Listener called"); FacesContext facesContext =
	 * FacesContext.getCurrentInstance(); ServletContext servletContext =
	 * (ServletContext) facesContext .getExternalContext().getContext(); String
	 * fullPath = servletContext.getRealPath("/") + "WEB-INF" + "\\" +
	 * "uploadedFiles\\"; UploadItem item = event.getUploadItem(); if (item !=
	 * null) { if (item.getFileName() != null) { File file = new File(); byte[]
	 * data = item.getData(); String fulpath = item.getFileName(); String
	 * fileName = fulpath .substring(fulpath.lastIndexOf("\\") + 1); String
	 * totalPath = fullPath + fileName.substring(fileName.lastIndexOf("\\") +
	 * 1); java.io.File f = new java.io.File(totalPath); if (!f.exists()) {
	 * f.createNewFile();
	 * 
	 * } else { f.delete(); f.createNewFile(); } // FileOutputStream fos = new
	 * //
	 * FileOutputStream(fullPath+fileName.substring(fileName.lastIndexOf("\\")
	 * // + 1)); FileOutputStream fos = new FileOutputStream(totalPath); if
	 * (data != null) { fos.write(data); file.setLength(new
	 * Long(item.getData().length)); fos.close(); } file.setName(fulpath);
	 * file.setData(data); // passBean.getFiles().add(file); uploadsAvailable--;
	 * } }
	 * 
	 * }
	 */

}
