/**
 * 
 */
package aero.nettracer.portal.faces.util;

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
	
}
