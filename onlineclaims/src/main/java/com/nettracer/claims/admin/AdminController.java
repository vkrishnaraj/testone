/**
 * 
 */
package com.nettracer.claims.admin;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;

import com.nettracer.claims.faces.util.CaptchaBean;

/**
 * @author Utpal
 * Description: This is the main controller for all the managed beans 
 * 				including Navigation rule and validation.
 */
public class AdminController {
	CaptchaBean captchaBean =new CaptchaBean();
	LoginBean loginBean=new LoginBean();
	/**
	 * If the validation would successful for login page then navigate to 2nd page.
	 * @return String
	 */
	public String gotoSecondPage(){
		clearCache();
		return (captchaBean.check().equalsIgnoreCase("Correct") ? "gotoSecondPage" : null);
	}
	
	/*
	 * Clear the browser cache(component value) from Apply request value phase
	 * 
	 */
	public void clearCache(){
		FacesContext context = FacesContext.getCurrentInstance();
		/*ViewHandler viewHandler = context.getApplication().getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse(); // Optional*/
		UIViewRoot viewRoot = context.getViewRoot();
		HtmlInputText inputText = null;
		HtmlForm htmlForm=(HtmlForm) viewRoot.findComponent("loginForm");
		HtmlPanelGrid htmlPanelGrid=(HtmlPanelGrid)htmlForm.findComponent("loginPanel");
		
		inputText = (HtmlInputText) htmlPanelGrid.findComponent("captcha");
		if(null != inputText){
			inputText.setValue("");
		}
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
