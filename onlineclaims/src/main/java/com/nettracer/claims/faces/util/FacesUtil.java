/**
 * 
 */
package com.nettracer.claims.faces.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author Utpal
 *
 */
public class FacesUtil {
	
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
}
