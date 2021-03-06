/**
 * 
 */
package com.nettracer.claims.faces.util;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nettracer.claims.admin.SessionScopeBean;
import com.nettracer.claims.passenger.SessionPassengerBean;

/**
 * @author Utpal
 *
 */
public class FacesUtil {
	private static Logger logger = Logger.getLogger(FacesUtil.class);
	/**
	 * Get the FacesContext Object 
	 * 
	 */
	public static FacesContext getFacesContext(){
		return  FacesContext.getCurrentInstance();
	}
	public static void addInfo(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
	}

	public static void addError(String message){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
	}
	
	public static boolean isError() {
		Iterator<FacesMessage> iter = FacesContext.getCurrentInstance().getMessages();
		while (iter.hasNext()) {
			FacesMessage current = iter.next();
			if (current.getSeverity().equals(FacesMessage.SEVERITY_ERROR)) {
				return true;
			}
		}
		return false;
	}
	
	public static void addInfo(String componentId,String message){
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
	}
	
	public static void addError(String componentId,String message){
		FacesContext.getCurrentInstance().addMessage(componentId, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, ""));
	}
	public static Object getRequestParameter(String name) {
        Object o= (Object) FacesContext.getCurrentInstance().getExternalContext()
            .getRequestParameterMap().get(name);
        o= (Object) FacesContext.getCurrentInstance().getExternalContext()
        .getRequestParameterMap().get(name);
       
        return o;
    }
	
	/**
	 * This method is used to logout the session
	 * 
	 * @return String
	 */
	public static String logout() {
		logger.debug("logout method is called");
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);
		if (session != null) {
			session.removeAttribute("logged");
			SessionScopeBean sessionBean=(SessionScopeBean)session.getAttribute("sessionBean");
			sessionBean.setLogoutRenderer(false);
			sessionBean.setLandingRenderer(false);
			session.setAttribute("sessionBean", sessionBean);
			addError("You have been successfully signed out.");
			logger.info("Admin has signed out");
		}
		return "logout";
	}
	
	/**
	 * This method is used to logout the session
	 * 
	 * @return String
	 */
	public static String passengerLogout() {
		logger.debug("passengerLogout method is called");
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
			.getExternalContext().getSession(false);
		if (session != null) {
			//session.removeAttribute("passengerLogout");//
			session.removeAttribute("loggedPassenger");
			SessionPassengerBean sessionPassengerBean=(SessionPassengerBean)session.getAttribute("sessionPassengerBean");
			if (sessionPassengerBean == null) {
				sessionPassengerBean = new SessionPassengerBean();
			}
			sessionPassengerBean.setLogoutRenderer(false);
			session.setAttribute("sessionPassengerBean", sessionPassengerBean);
			//session.invalidate();
			addError("You have been successfully signed out.");
			logger.info("User has signed out");
		}
		return "passengerLogout";
	}

	
}
