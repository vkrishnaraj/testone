package com.nettracer.claims.passenger.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nettracer.claims.admin.bootstrap.PassengerBootstrap;
import com.nettracer.claims.core.model.Languages;
import com.nettracer.claims.core.model.Localetext;
import com.nettracer.claims.core.service.AdminService;
import com.nettracer.claims.core.service.PassengerService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;
import com.nettracer.claims.passenger.LoginBean;
import com.nettracer.claims.passenger.SessionPassengerBean;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation for the passenger side.
 */

@Component
@Scope("request")
@Qualifier("passengerController")
public class PassengerController {
	private static Logger logger = Logger.getLogger(PassengerController.class);
	private static final String CAPTCHA_STATUS = "Correct";
	
	
	CaptchaBean captchaBean = new CaptchaBean();
	LoginBean loginBean = new LoginBean();
	//private List<Map<String, List<Localetext>>> pageMapsList ;
	private Map<String, List<Localetext>> pageMaps ;
	private List<Localetext> loginPageList;
	private String selectedLanguage; //holds the dropdown value for language selection
	private Set<SelectItem> languageDropDown = new LinkedHashSet<SelectItem>();
	
	/*@Autowired
	AdminService adminService;*/
	
	@Autowired
	PassengerService passengerService;
	
	public PassengerController(){
		logger.info("PassengerController constructor");
		List<Languages> languagesList=PassengerBootstrap.getLanguageDropDown();
		for(Languages language:languagesList){
			if (language.getActiveStatus() == true){
				languageDropDown.add(new SelectItem(language.getDescription()));
			}
		}
		loginPageList= PassengerBootstrap.getLoginPageList();
		logger.info("Size of loginPageList inside PassengerController constructor= "+loginPageList.size());

	}

	
	
	
	public void languageSelectionListener(ValueChangeEvent valueChangeEvent){
		logger.info("Listener:languageSelectionListener is called");
		try {
			String languageSelected = (String) valueChangeEvent.getNewValue();
			HttpSession session = (HttpSession) FacesUtil.getFacesContext()
					.getExternalContext().getSession(false);
			loginPageList=passengerService.getPassengerLoginContents(languageSelected);
			
			
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
		// Had to use hard coded value for testing
		
		
		if ((loginBean.getClaimNumber().equals("dummy")  && loginBean
				.getLastName().equals("dummy"))) {

			if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {
				FacesContext context = FacesUtil.getFacesContext();
				HttpSession session = (HttpSession) context
						.getExternalContext().getSession(false);
				SessionPassengerBean sessionPassengerBean = (SessionPassengerBean) session
						.getAttribute("sessionPassengerBean");
				sessionPassengerBean.setLogoutRenderer(true);
				session.setAttribute("sessionPassengerBean", sessionPassengerBean);
				session.setAttribute("loggedPassenger", "loggedPassenger");
				return "gotoDirectionPage";
			} else {
				clearCaptchaCache();
				return null;
			}

		} else {
			FacesUtil.addError("Incorrect Claim Number and Last Name combination. Please try again.");
			logger.error("Claim Number and Last Name are incorrect for admin for the IP Adress: "
							+((HttpServletRequest)FacesUtil.getFacesContext()
							.getExternalContext().getRequest()).getRemoteAddr());
			if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {
				captchaBean.setStatus("");
			}
			clearInputCache();
			clearCaptchaCache();
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
		HtmlForm htmlForm = (HtmlForm) viewRoot.findComponent("loginForm");
		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) htmlForm
				.findComponent("loginPanel");

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
		HtmlForm htmlForm = (HtmlForm) viewRoot.findComponent("loginPassengerForm");
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


}
