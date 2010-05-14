/**
 * 
 */
package com.nettracer.claims.admin;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nettracer.claims.faces.util.CaptchaBean;
import com.nettracer.claims.faces.util.FacesUtil;

/**
 * @author Utpal
 * Description: This is the main controller for all the managed beans 
 * 				including Navigation rule and validation.
 */
public class AdminController {
	private static Logger logger = Logger.getLogger(AdminController.class);
	private static final String	CAPTCHA_STATUS="Correct";
	//These would be replaced with spring IOC 
	CaptchaBean captchaBean =new CaptchaBean();
	LoginBean loginBean=new LoginBean();

	/**
	 * If the validation would successful for login page then navigate to 2nd page.
	 * @return String
	 */
	public String gotoLandingPage(){
		logger.info("gotoLandingPage method is called");
		//Had to use hard coded value for testing
		if(!(loginBean.getUserName().equalsIgnoreCase("dummy") 
				&& loginBean.getPassword().equalsIgnoreCase("dummy"))){
			FacesUtil.addError("Incorrect username and password combination. Please try again.");
			logger.warn("Username and Password are incorrect for admin");
		}
		clearCache();
		if(captchaBean.check().equalsIgnoreCase(CAPTCHA_STATUS)){
			FacesContext context = FacesUtil.getFacesContext();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
			SessionScopeBean sessionBean=(SessionScopeBean)session.getAttribute("sessionBean");
			sessionBean.setLogoutRenderer(true);
			session.setAttribute("sessionBean", sessionBean);
			return "gotoLandingPage";
		}else{
			return null;
		}
	}
	
	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 * 
	 */
	public void clearCache(){
		logger.info("clearCache method is called to clear the wrong captcha input texts");
		FacesContext context = FacesUtil.getFacesContext();
		/*ViewHandler viewHandler = context.getApplication().getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional*/
		UIViewRoot viewRoot = context.getViewRoot();
		HtmlInputText inputText = null;
		HtmlForm htmlForm=(HtmlForm) viewRoot.findComponent("loginForm");
		HtmlPanelGrid htmlPanelGrid=(HtmlPanelGrid)htmlForm.findComponent("loginPanel");
		
		inputText = (HtmlInputText) htmlPanelGrid.findComponent("captcha");
		inputText.setValue("");
	}
	
	/**
	 * This method is used to logout the session
	 * @return String
	 */
	public String logout(){
		logger.info("invalidate method is called for logout");
		FacesContext facesContext=FacesUtil.getFacesContext();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
		if (session != null) {
			session.invalidate();
			logger.info("User session is closed");
		}
		return "logout";
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

	
	
}
