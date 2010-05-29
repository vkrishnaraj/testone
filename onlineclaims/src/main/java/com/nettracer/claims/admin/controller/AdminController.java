/**
 * 
 */
package com.nettracer.claims.admin.controller;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bagnet.nettracer.ws.onlineclaims.impl.AuthAdminUserDocumentImpl;
import com.nettracer.claims.admin.LoginBean;
import com.nettracer.claims.admin.SessionScopeBean;
import com.nettracer.claims.core.model.Company;
import com.nettracer.claims.core.service.AdminService;
import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;

/**
 * @author Utpal Description: This is the main controller for all the managed
 *         beans including Navigation rule and validation.
 */

@Component
@Scope("request")
@Qualifier("adminController")
public class AdminController {
	private static Logger logger = Logger.getLogger(AdminController.class);
	private static final String CAPTCHA_STATUS = "Correct";
	// These would be replaced with spring IOC
	CaptchaBean captchaBean = new CaptchaBean();

	LoginBean loginBean = new LoginBean();
	@Autowired
	AdminService adminService;

	Company company = new Company();

	/**
	 * If the validation would successful for login page then navigate to 2nd
	 * page. i.e. the landing page
	 * 
	 * @return String
	 */
	public String gotoLandingPage() {
		logger.debug("gotoLandingPage method is called");
		// Had to use hard coded value for testing
		
		if ((loginBean.getUserName().equalsIgnoreCase("dummy") && loginBean
				.getPassword().equalsIgnoreCase("dummy"))) {

			if (captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)) {
				FacesContext context = FacesUtil.getFacesContext();
				HttpSession session = (HttpSession) context
						.getExternalContext().getSession(false);
				SessionScopeBean sessionBean = (SessionScopeBean) session
						.getAttribute("sessionBean");
				sessionBean.setLogoutRenderer(true);
				session.setAttribute("sessionBean", sessionBean);
				session.setAttribute("logged", "logged");
				return "gotoLandingPage";
			} else {
				clearCaptchaCache();
				return null;
			}

		} else {
			FacesUtil
					.addError("Incorrect username and password combination. Please try again.");
			logger.error("Username and Password are incorrect for admin for the IP Adress: "
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

	public String gotoMaintainApplicationPage() {
		logger.debug("gotoMaintainApplicationPage method is called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			try {
				Company company = adminService.getApplicationData();
				this.setCompany(company);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "gotoMaintainApplication";
		} else {
			FacesUtil
					.addError("Your session has been expired. PLease log in again");
			return "logout";
		}
	}

	public String saveApplication() {
		logger.debug("saveApplication called");
		HttpSession session = (HttpSession) FacesUtil.getFacesContext()
				.getExternalContext().getSession(false);
		if (null != session && null != session.getAttribute("logged")) {
			try {
				adminService.saveApplication(this.getCompany());
			} catch (Exception e) {
				e.printStackTrace();
			}
			FacesUtil.addInfo("Application Data saved successfully.");
			logger.info("Application Data saved successfully.");
			return "gotoLandingPage";
		} else {
			FacesUtil
					.addError("Your session has been expired. PLease log in again");
			return "logout";
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
		HtmlForm htmlForm = (HtmlForm) viewRoot.findComponent("loginForm");
		HtmlPanelGrid htmlPanelGrid = (HtmlPanelGrid) htmlForm
				.findComponent("loginPanel");

		inputText = (HtmlInputText) htmlPanelGrid.findComponent("userName");
		if (null != inputText) {
			inputText.setValue("");
		}
		HtmlInputSecret inputSecret = (HtmlInputSecret) htmlPanelGrid
				.findComponent("password");
		if (null != inputSecret) {
			inputSecret.setValue("");
		}
	}

	public String logout() {
		return FacesUtil.logout();
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

	public AdminService getRequiredFieldsService() {
		return adminService;
	}

	public void setRequiredFieldsService(AdminService requiredFieldsService) {
		this.adminService = requiredFieldsService;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
