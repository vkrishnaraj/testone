<%@page import="java.util.Locale"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="org.apache.struts.util.PropertyMessageResources"%>
<%@ page language="java"%>

<%
	Agent a = (Agent) session.getAttribute("user");
    PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
    Locale  myLocale   = (Locale)session.getAttribute("org.apache.struts.action.LOCALE");
%>

function validateDamClose(form, doCheck)
{
	if (doCheck == 1) {
    	for (var j=0; j < form.length; j++) {
	      currentElement = form.elements[j];
	      currentElementName=currentElement.name;
	      if (currentElementName.indexOf("].lossCode") != -1) { 
	        if (currentElement.value.length == 0 || currentElement.value == "0") {
	          alert("<%= (String)myMessages.getMessage(myLocale, "colname.loss.code") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
	        }
	        
	        if(lossCodeChange!=false){
  				alert("<%=(String)myMessages.getMessage(myLocale, "loss.code.remark")%>" + " <%=(String)myMessages.getMessage(myLocale,	"error.validation.isRequired")%>");
     	
				return false;
			}
	      } else if (currentElementName.indexOf("].faultStation_id") != -1) {
	        if (currentElement.value.length == 0) {
	          alert("<%= (String)myMessages.getMessage(myLocale, "colname.fault.station") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
	        }
	      } 
	    }
	    validateClose();
    }
	return true;
}