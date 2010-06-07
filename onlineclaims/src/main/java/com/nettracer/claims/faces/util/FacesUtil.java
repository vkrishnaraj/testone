/**
 * 
 */
package com.nettracer.claims.faces.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

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
			session.invalidate();
			addError("You have been successfully signed out.");
			logger.info("User session is closed");
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
			session.removeAttribute("passengerLogout");
			session.invalidate();
			addError("You have been successfully signed out.");
			logger.info("User session is closed");
		}
		return "passengerLogout";
	}

	
}
