package aero.nettracer.portal.faces.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateConverter implements Converter{
	private static Log log = LogFactory.getLog(DateConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component,String value) throws ConverterException{
        if(StringUtils.isEmpty(value)){
            return null;
        }
        
    	String datePattern = StringUtils.defaultIfEmpty((String)component.getAttributes().get("datePattern"), "MM/dd/yyyy"); 
    	Date newDate = null;
    	SimpleDateFormat sdf = null;
    	
    	try {
            /* If the date has format of 4-digit year, use the format of 2-digit to convert to date. This format handles both 2-digit and 4-digit year */   
    		if (StringUtils.contains(datePattern, "yyyy")) {    			    
    			datePattern = datePattern.replaceAll("yyyy", "yy");    			
    		}	   
    		
    		sdf = new SimpleDateFormat(datePattern);
    		newDate = sdf.parse(value);    		
	   	} catch (Exception ex) {
	   		log.error("Error converting date: " + value);	
	   		ex.printStackTrace();
	   		throw new ConverterException("Conversion error occurred.");
	   	}
        
		return newDate;
	}

	 public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
	   	if( value == null ) {
	    	return null;
	   	}	
	   	if(value.equals("")){
	   		return "";
	   	}
	   	String datePattern = StringUtils.defaultIfEmpty((String)component.getAttributes().get("datePattern"), "MM/dd/yyyy");
	   	String dateString = null;
	   	
	   	try {
	   		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);	   		   	 
	   		dateString = sdf.format((Date) value);
	   	} catch (Exception ex) {
	   		log.error("Error Converting to String, value "+value);
	   		ex.printStackTrace();
	   		throw new ConverterException("Rendering for display failed.");
	   	}
	   	
		return dateString;
	}
}
