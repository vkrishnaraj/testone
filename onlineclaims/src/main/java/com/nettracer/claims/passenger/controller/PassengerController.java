package com.nettracer.claims.passenger.controller;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.core.model.Airport;
import com.nettracer.claims.core.model.Bag;
import com.nettracer.claims.core.model.Content;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.Itinerary;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.MultilingualLabel;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.core.service.PassengerService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.faces.util.File;
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
	private MultilingualLabel bagDetailsLabel;
	private MultilingualLabel fileUploadLabel;
	private MultilingualLabel fraudQuestionLabel;
	private MultilingualLabel submitClaimLabel;
	private MultilingualLabel savedScreenLabel;
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private Set<SelectItem> lostBagItems = new LinkedHashSet<SelectItem>();
	private DataModel airportCodeList;
	private DataModel itineraryList;
	private int itineraryTableIndex;
	private int bagIndex;
	private FileUploadBean fileUploadBean;
	private int uploadsAvailable = 10;
	// ------------------- File Upload Myfaces
	private UploadedFile upFile;
	boolean rendSuccess = false;
	boolean rendFailure = false;
	private DataModel fileDataModelList;
	// -- End File Upload

	
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
				boolean saveData = onlineClaimsWS.savePassengerInfo(passengerBean);
				if (saveData) {
					FacesUtil.addInfo("Passenger infomation saved successfully.");
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
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
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
			}
			passengerBean.setBagTagList(bagList); // for about your ticket, bag tag table
			
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
	 * Call for validation of itinerary table
	 * 
	 * 
	 */
	public String getModalPanel() {
		logger.info("getModalPanel method");
		if (passengerBean.getItineraryList() != null
				&& passengerBean.getItineraryList().get(
						getItineraryTableIndex()) != null
				&& passengerBean.getItineraryList().get(
						getItineraryTableIndex()).getDepartureCity() != null) {
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
	 * Navigation for the Bagage Details Page.i.e. step 3
	 * 
	 * @return String
	 */
	public String gotoBagDetails() {
		logger.info("gotoBagDetails method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
		.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				if (null != passengerBean.getBagList()) {
					passengerBean.getBagList().clear();
				}
				List<Bag> bagTagList=passengerBean.getBagTagList();
				List<Bag> lostBagList=new ArrayList<Bag>();
				if(bagTagList != null && bagTagList.size() >0){
					for(Bag lostBag:bagTagList){
						if(Boolean.parseBoolean(lostBag.getBagArrivalStatus()) == false){
							lostBagList.add(lostBag);
						}
					}
					if(passengerBean.getLostBag() != lostBagList.size()){
						FacesUtil.addError("No. of Lost Bag and Bag Arrival radion button status does not match");
						return null;
					}
					// Logic for step 3 o 6 About Your Bag (multiple page)
					passengerBean.setBagList(lostBagList);
					
					List<Bag> bags=passengerBean.getBagList();
					for(Bag b:bags){
						List<Content> contentList=new ArrayList<Content>();
						if(b.getContentList() != null){
							b.getContentList().clear();
						}
						for(int i=1;i<=4;i++){
							Content content=new Content();
							contentList.add(content);
						}
						b.setContentList(contentList);
						contentList=null;
					}
					
				}
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session.getAttribute("selectedLanguage");
				bagDetailsLabel = passengerService.getBagDetailsLabel(selectedLanguage, baggageState);
				//session.setAttribute("passengerBean", passengerBean);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoBagDetails";
		} else {
			FacesUtil.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}
	
	
	public String addMoreItems(){
		logger.info("addMoreItems method call");
		List<Bag> bags=passengerBean.getBagList();
		Bag bag=bags.get(getBagIndex());
		bag.getContentList().add(new Content());
		return null;
	}

	/**
	 * Navigation for the File Upload Page.i.e. step 4
	 * 
	 * @return String
	 */
	public String gotoFileUpload() {
		logger.info("gotoFileUpload method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			try {
				baggageState = (Long) session.getAttribute("baggageState");
				String selectedLanguage = (String) session
						.getAttribute("selectedLanguage");
				fileUploadLabel = passengerService.getFileUploadLabel(
						selectedLanguage, baggageState);
				passengerBean.getFiles().clear();
				session.setAttribute("passengerBean", passengerBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoFileUpload";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
		}
	}

	
	/**
	 * 
	 * File Upload Logic
	 * @throws IOException
	 */
	public String upload() throws IOException {
		logger.info("upload called");
		try {
			if(upFile != null){

				int extDot = upFile.getName().lastIndexOf('.');
				if (extDot > 0) {
					String extension = upFile.getName().substring(extDot + 1);
					if (extension.equals("jpg") || extension.equals("gif") || extension.equals("pdf")) {
						InputStream stream = upFile.getInputStream();
						long size = upFile.getSize();
						byte[] buffer = new byte[(int) size];
						stream.read(buffer, 0, (int) size);
						stream.close();
						rendSuccess = true;
						rendFailure = false;
						logger.info("File Uploaded Successfully.");
						File file = new File();
						byte[] data = upFile.getBytes();
						file.setName(upFile.getName());
						file.setLength(upFile.getSize());
						file.setData(data);
						if(passengerBean.getFiles() !=null){
							List<File> existingFiles=passengerBean.getFiles();
							for(File existingFile:existingFiles){
								if(upFile.getName().equals(existingFile.getName())){
									logger.warn("File is already existing");
									FacesUtil.addError("File is already existing");
									return "no";
								}
							}
							passengerBean.getFiles().add(file);
							fileDataModelList=new ListDataModel(passengerBean.getFiles());
						}
						file=null; 
						return "ok";
					}else{
						logger.error("File type not supported");
						FacesUtil.addError("File type not supported");
					}
				}

			}
			return "no";
		} catch (Exception ioe) {
			logger.info("File Upload Unsuccessful.");
			ioe.printStackTrace();
			rendSuccess = false;
			rendFailure = true;
			return "no";
		}
	}
	

	/**
	 * 
	 * Remove a File from the main file list
	 * 
	 */
	public void removeFileListener(ActionEvent event){
		logger.info("removeFileListener called");
		File file= (File)fileDataModelList.getRowData();
		List<File> files=passengerBean.getFiles();
		int fileSize=files.size();
		for(int i=fileSize-1;i>=0; i--){
			File f=files.get(i);
			if(f.getName().equals(file.getName())){
				passengerBean.getFiles().remove(i);
			}
		}
		fileDataModelList=null;
		fileDataModelList=new ListDataModel(passengerBean.getFiles());
		
	}
	// -------------------End of File Upload
	
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
				String selectedLanguage = (String) session
						.getAttribute("selectedLanguage");
				fraudQuestionLabel = passengerService.getFraudQuestionLabel(
						selectedLanguage, baggageState);
				if (null != passengerBean.getAnotherClaim() && passengerBean.getAnotherClaim()) {
					fraudQuestionLabel.setWhichAirlineState(2L);
					fraudQuestionLabel.setDateOfClaimState(2L);
					fraudQuestionLabel.setClaimantNameState(2L);
				} else {
					fraudQuestionLabel.setWhichAirlineState(1L);
					fraudQuestionLabel.setDateOfClaimState(1L);
					fraudQuestionLabel.setClaimantNameState(1L);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoFraudQuestion";
		} else {

			FacesUtil
					.addError("Your session has been expired. Please log in again");
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
		Boolean anotherClaim = (Boolean) valueChangeEvent.getNewValue();
		if (anotherClaim) {
			fraudQuestionLabel.setWhichAirlineState(2L);
			fraudQuestionLabel.setDateOfClaimState(2L);
			fraudQuestionLabel.setClaimantNameState(2L);
		} else {
			fraudQuestionLabel.setWhichAirlineState(1L);
			fraudQuestionLabel.setDateOfClaimState(1L);
			fraudQuestionLabel.setClaimantNameState(1L);
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
				String selectedLanguage = (String) session
						.getAttribute("selectedLanguage");
				savedScreenLabel = passengerService.getSavedScreenLabel(
						selectedLanguage, baggageState);
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

	public MultilingualLabel getFileUploadLabel() {
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

	public MultilingualLabel getBagDetailsLabel() {
		return bagDetailsLabel;
	}

	public void setBagDetailsLabel(MultilingualLabel bagDetailsLabel) {
		this.bagDetailsLabel = bagDetailsLabel;
	}

	public int getBagIndex() {
		return bagIndex;
	}

	public void setBagIndex(int bagIndex) {
		this.bagIndex = bagIndex;
	}
	
	
	
	
	/**
	 * Test Code : to be deleted later on : Richfaces File Upload
	 * @param event
	 * @throws Exception
	 */
	/*public void fileUploadListener(UploadEvent event) throws Exception {
		logger.info("File Upload Listener called");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext
				.getExternalContext().getContext();
		String fullPath = servletContext.getRealPath("/") + "WEB-INF" + "\\"
				+ "uploadedFiles\\";
		UploadItem item = event.getUploadItem();
		if (item != null) {
			if (item.getFileName() != null) {
				File file = new File();
				byte[] data = item.getData();
				String fulpath = item.getFileName();
				String fileName = fulpath
						.substring(fulpath.lastIndexOf("\\") + 1);
				String totalPath = fullPath
						+ fileName.substring(fileName.lastIndexOf("\\") + 1);
				java.io.File f = new java.io.File(totalPath);
				if (!f.exists()) {
					f.createNewFile();

				} else {
					f.delete();
					f.createNewFile();
				}
				// FileOutputStream fos = new
				// FileOutputStream(fullPath+fileName.substring(fileName.lastIndexOf("\\")
				// + 1));
				FileOutputStream fos = new FileOutputStream(totalPath);
				if (data != null) {
					fos.write(data);
					file.setLength(new Long(item.getData().length));
					fos.close();
				}
				file.setName(fulpath);
				file.setData(data);
				// passBean.getFiles().add(file);
				uploadsAvailable--;
			}
		}

	}*/

}
