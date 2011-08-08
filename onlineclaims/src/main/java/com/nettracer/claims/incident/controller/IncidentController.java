package com.nettracer.claims.incident.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
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
import com.bagnet.nettracer.ws.onlineclaims.xsd.Claim;
import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;
import com.bagnet.nettracer.ws.onlineclaims.xsd.PassengerView;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.CountryCode;
import com.nettracer.claims.core.model.IncidentBean;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.model.PassengerBean;
import com.nettracer.claims.core.model.StateCode;
import com.nettracer.claims.core.service.PaxViewService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.incident.LoginBean;
import com.nettracer.claims.passenger.SessionPassengerBean;
import com.nettracer.claims.webservices.client.OnlineClaimsWS;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation for the passenger
 *         side.
 */

@Component
@Scope("session")
@Qualifier("incidentController")
public class IncidentController {
	private static Logger logger = Logger.getLogger(IncidentController.class);

	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	private List<SelectItem> selectItems = new ArrayList<SelectItem>();
	private List<SelectItem> stateItems = new ArrayList<SelectItem>();
	private IncidentBean incidentBean;

	@Autowired
	PaxViewService passengerService;

	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	@PostConstruct
	public void loadBeans() {
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
		.getExternalContext().getSession(false);
		incidentBean = (IncidentBean) session.getAttribute("incidentBean");
		logger.debug("CLAIM CHECKS");
		if (incidentBean.getClaimCheck() != null) {
			for (String claim : incidentBean.getClaimCheck()) {
				logger.debug(claim);
			}
		}
	}

	public boolean isMobile() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return req.getRequestURI().contains("/mobile/");
	}
	
	public String gotoStepTwo() {
		logger.debug("gotoStepTwo method is called");
		return checkSession("gotoStepTwoPage");
	}
	
	public String gotoStepThree() {
		logger.debug("gotoStepThree method is called");
		try {
			List<CountryCode> countries = passengerService.getCountries();
			for (CountryCode countryCode : countries) {
				selectItems.add(new SelectItem(countryCode.getId(),countryCode.getCountry()));
			}
			List<StateCode> states = passengerService.getStates();
			for (StateCode stateCode : states) {
				stateItems.add(new SelectItem(stateCode.getId(),stateCode.getState()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkSession("gotoStepThreePage");
	}
	
	public String gotoStepFour() {
		logger.debug("gotoStepFour method is called");
		return checkSession("gotoStepFourPage");
	}
	
	public String gotoComplete() {
		logger.debug("gotoComplete method is called");
		String next = checkSession("gotoCompletePage");
		if (next.contains("gotoIncidentLogin")) {
			return next;
		}
		if (saveIncident()) {
			return next;
		} else {
			// Put error here!
			return null;
		}
	}
	
	public String gobackStepOne() {
		logger.debug("gobackStepOne method is called");
		return checkSession("gotoStepOnePage");
	}
	
	public String gobackStepTwo() {
		logger.debug("gobackStepTwo method is called");
		return checkSession("gotoStepTwoPage");
	}
	
	public String gobackStepThree() {
		logger.debug("gobackStepThree method is called");
		return checkSession("gotoStepThreePage");
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
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		try {
			PassengerView passengerView = onlineClaimsWS.getPassengerView(
					incidentBean.getIncidentID(), incidentBean.getLastName(), incidentBean.getFirstName());  
			
			if (passengerView.getAuthenticationSuccess()) {

					if (!isMobile()) {
						SessionPassengerBean sessionPassengerBean = new SessionPassengerBean();
						sessionPassengerBean.setLogoutRenderer(true);
						session.setAttribute("sessionPassengerBean",sessionPassengerBean);
					}
					session.setAttribute("loggedPassenger", "loggedPassenger");

					Long baggageState = passengerView.getClaimId();
					List<Localetext> passengerDirectionList = passengerService.getDirectionContents("English-US");
					session.setAttribute("passengerDirectionList",passengerDirectionList);

					WSPVAdvancedIncident passengerData = passengerView.getData();
					
					Claim claim=onlineClaimsWS.getClaim(passengerData,incidentBean.getLastName(), incidentBean.getFirstName());
					PassengerBean passengerBean = onlineClaimsWS.getPassengerData(passengerData,claim);
					passengerBean.setPassengerData(passengerData);
					passengerBean.setIncidentID(passengerData.getIncidentID());

					DataModel airportCodeList = new ListDataModel(passengerService.getAirportList());

					session.setAttribute("claim", claim);
					session.setAttribute("passengerBean", passengerBean);
					session.setAttribute("baggageState", baggageState);
					session.setAttribute("selectedLanguage", "English-US");
					session.setAttribute("airportCodeList", airportCodeList);
					return (isMobile() ? "gotoMobileDirectionPage" : "gotoDirectionPage");

			} else {
				FacesUtil.addError("Incorrect Claim Number and Last Name combination. Please try again.");
				logger.error("Claim Number and Last Name are incorrect for admin for the IP Adress: "
								+ ((HttpServletRequest) FacesUtil
										.getFacesContext().getExternalContext()
										.getRequest()).getRemoteAddr());
				return null;
			}
		} catch (AxisFault e) {
			logger.error("AxisFault Error");
			FacesUtil.addError("There is a problem communicating with our bag service.  Please try again later.");
			return null;
		} catch (RemoteException e) {
			logger.error("Error:RemoteException");
			e.printStackTrace();
			return null;
		} catch (SimplePersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String checkSession(String nextPage) {
		logger.debug("checking session...");
		String toReturn = "";
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			toReturn = nextPage;
		} else {
			FacesUtil.addError("Your session has been expired. Please log in again");
			toReturn = "gotoIncidentLogin";
		}
		return (isMobile() ? nextPage + "Mobile" : nextPage);
	}

	private boolean saveIncident() {
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
			.getExternalContext().getSession(false);
		try {
			String incidentID = onlineClaimsWS.saveIncident(incidentBean, (Incident) session.getAttribute("incident"));
			if (incidentID != null) {
				incidentBean.setIncidentID(incidentID);
				return true;
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

	public void setOnlineClaimsWS(OnlineClaimsWS onlineClaimsWS) {
		this.onlineClaimsWS = onlineClaimsWS;
	}

	public IncidentBean getIncidentBean() {
		return incidentBean;
	}

	public void setIncidentBean(IncidentBean incident) {
		this.incidentBean = incident;
	}

	public List<SelectItem> getSelectItems() {
		return selectItems;
	}

	public void setSelectItems(List<SelectItem> selectItems) {
		this.selectItems = selectItems;
	}

	public List<SelectItem> getStateItems() {
		return stateItems;
	}

	public void setStateItems(List<SelectItem> stateItems) {
		this.stateItems = stateItems;
	}

}
