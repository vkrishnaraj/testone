/**
 * 
 */
package aero.nettracer.portal.faces.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang.StringUtils;

public class StringAsUpper implements  Converter {
	public Object getAsObject(FacesContext facesContext  
			, UIComponent component, String value) { 
		 if(StringUtils.isEmpty(value)){
	            return null;
	        }
		return value.toString().toUpperCase();  
	}  

	public String getAsString(FacesContext facesContext  
			, UIComponent component, Object value) {  
		if( value == null ) {
	    	return null;
	   	}	
	   	if(value.equals("")){
	   		return "";
	   	}
		return value.toString().toUpperCase();  
	} 
}
