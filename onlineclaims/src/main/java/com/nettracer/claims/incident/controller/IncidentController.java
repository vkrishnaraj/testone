package com.nettracer.claims.incident.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.core.model.IncidentBean;
import com.nettracer.claims.core.service.PassengerService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.incident.LoginBean;
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
	private IncidentBean incidentBean;

	@Autowired
	PassengerService passengerService;

	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	@PostConstruct
	public void loadBeans() {
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
		.getExternalContext().getSession(false);
		incidentBean = (IncidentBean) session.getAttribute("incidentBean");
		logger.error("CLAIM CHECKS");
		for (String claim : incidentBean.getClaimCheck()) {
			logger.error(claim);
		}
	}
	
	public String gotoStepTwo() {
		return "gotoStepTwoPage";
	}
	
	public String gotoStepThree() {
		return "gotoStepOnePage";
	}
	
	public String gobackStepOne() {
		return "gotoStepOnePage";
	}
	
	
	/**
	 * Reset all the input fields to their default values.
	 * 
	 * 
	 */
	public String cancel() {
		logger.debug("cancel method is called");

		HttpSession session = (HttpSession) FacesUtil.getFacesContext().getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("loggedPassenger")) {
			return "gotoDirectionPage";
		} else {
			FacesUtil.addError("Your session has been expired. Please log in again");
			return "passengerLogout";
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

	public void setOnlineClaimsWS(OnlineClaimsWS onlineClaimsWS) {
		this.onlineClaimsWS = onlineClaimsWS;
	}

	public IncidentBean getIncidentBean() {
		return incidentBean;
	}

	public void setIncidentBean(IncidentBean incident) {
		this.incidentBean = incident;
	}

}
