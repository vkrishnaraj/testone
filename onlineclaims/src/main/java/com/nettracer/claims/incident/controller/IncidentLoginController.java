package com.nettracer.claims.incident.controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bagnet.nettracer.ws.onlineclaims.xsd.Incident;
import com.nettracer.claims.core.exception.SimplePersistenceException;
import com.nettracer.claims.core.model.IncidentBean;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.service.PaxViewService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.incident.LoginBean;
import com.nettracer.claims.utils.ClaimsProperties;
import com.nettracer.claims.webservices.client.OnlineClaimsWS;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation for the passenger
 *         side.
 */

@Component
@Scope("request")
@Qualifier("incidentLoginController")
public class IncidentLoginController {
	private static Logger logger = Logger
			.getLogger(IncidentLoginController.class);
	private static final String CAPTCHA_STATUS = "Correct";

	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	private String selectedLanguage = "English-US"; // holds the dropdown value
	// for language selection
	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();
	private IncidentBean incidentBean;

	@Autowired
	PaxViewService passengerService;

	@Autowired
	OnlineClaimsWS onlineClaimsWS;

	public IncidentLoginController() {
		logger.info("IncidentController constructor-1");

	}

	@Autowired
	public IncidentLoginController(PaxViewService adminService) {
		logger.info("IncidentController constructor-2");
		List<Languages> languagesList;
		try {
			languagesList = adminService.getLanguages();

			for (Languages language : languagesList) {
				if (language.getActiveStatus() == true) {
					languageDropDown.add(new SelectItem(language
							.getDescription()));
				}
			}
		} catch (SimplePersistenceException e) {
			e.printStackTrace();
		}
	}

	public boolean isMobile() {
		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return req.getRequestURI().contains("/mobile/");
	}

	@PostConstruct
	public void checkMobile() {
		logger.info("CHECKING FOR MOBILE BROWSER!!!");

		HttpServletRequest req = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		if (!isMobile()) {
			HttpServletResponse res = (HttpServletResponse) FacesContext
					.getCurrentInstance().getExternalContext().getResponse();
			String userAgent = req.getHeader("User-Agent").toLowerCase();
			logger.info("USER-AGENT: " + userAgent);
			if (userAgent
					.matches(".*(android|avantgo|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |"
							+ "maemo|midp|mmp|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|"
							+ "vodafone|wap|windows (ce|phone)|xda|xiino).*")
					|| userAgent
							.substring(0, 4)
							.matches(
									"1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|"
											+ "al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|"
											+ "bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|"
											+ "dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|"
											+ "fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|"
											+ "hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|"
											+ "ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|"
											+ "54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|"
											+ "mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|"
											+ "ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|"
											+ "pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|"
											+ "rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|"
											+ "sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|"
											+ "ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|"
											+ "veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|"
											+ "wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-")) {
				logger.info("YOU FOUND ME!!!");
				try {
					res.sendRedirect(ClaimsProperties.get(ClaimsProperties.MOBILE_LOCATION) + "incident/mobile/login.do");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	public String gotoStepOne() {
		logger.debug("gotoStepOne method is called");
		FacesContext context = FacesUtil.getFacesContext();
		HttpSession session = (HttpSession) context.getExternalContext()
				.getSession(false);
		if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {
			try {
				Incident incident = onlineClaimsWS.getIncident(loginBean.getConfNumber(), loginBean.getLastName(), loginBean.getFirstName());

				if (incident.getAuthStatus() == 3) { // AUTH_SUCCESS = 3

					session.setAttribute("loggedPassenger", "loggedPassenger");

					incidentBean = onlineClaimsWS.getIncidentData(incident);

					session.setAttribute("incident", incident);
					session.setAttribute("incidentBean", incidentBean);
					session.setAttribute("selectedLanguage", selectedLanguage);

					return (isMobile() ? "gotoStepOnePageMobile" : "gotoStepOnePage");

				} else if (incident.getAuthStatus() == 2) { // AUTH_EXPIRED = 2
					FacesUtil.addError("The time period for submitting your incident report to this system has expired. " +
							"Please go to the service desk or call the airline.");
					logger.error("Online incident for AuthID " + incident.getAuthID() + " has expired.");
					clearInputCache();
					return null;
				} else if (incident.getAuthStatus() == 1) { // AUTH_COMPLETE = 1
					incidentBean = new IncidentBean();
					incidentBean.setIncidentID(incident.getPnr());
					incidentBean.setLastName(incident.getLastName());
					session.setAttribute("incidentBean", incidentBean);
					return (isMobile() ? "gotoCompletePageMobile" : "gotoCompletePage");
				} else { // AUTH_FAILURE = 0
					FacesUtil.addError("Incorrect Conformation Code and Name combination. Please try again.");
					logger.error("PNR, Last Name, and First Name are incorrect for admin for the IP Adress: "
							+ ((HttpServletRequest) FacesUtil.getFacesContext()
									.getExternalContext().getRequest())
									.getRemoteAddr());
					clearInputCache();
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
			}
		} else {
			clearCaptchaCache();
			return null;
		}
	}

	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 */
	public void clearCaptchaCache() {
		logger.debug("clearCaptchaCache method is called to clear the wrong captcha input texts");
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
		inputText = (HtmlInputText) htmlPanelGrid.findComponent("firstName");
		if (null != inputText) {
			inputText.setValue("");
		}
		HtmlInputText inputSecret = (HtmlInputText) htmlPanelGrid
				.findComponent("lastName");
		if (null != inputSecret) {
			inputSecret.setValue("");
		}
		HtmlInputText inputConf = (HtmlInputText) htmlPanelGrid
				.findComponent("confNumber");
		if (null != inputConf) {
			inputConf.setValue("");
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

	public IncidentBean getIncidentBean() {
		return incidentBean;
	}

	public void setIncidentBean(IncidentBean incidentBean) {
		this.incidentBean = incidentBean;
	}

}
