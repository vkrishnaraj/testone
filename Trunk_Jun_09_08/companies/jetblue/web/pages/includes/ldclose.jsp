<%@page import="java.util.Locale"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="org.apache.struts.util.PropertyMessageResources"%>
<%@ page language="java"%>

<%
	Agent a = (Agent) session.getAttribute("user");
    PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
    Locale  myLocale   = (Locale)session.getAttribute("org.apache.struts.action.LOCALE");
%>


function validateLdClose(form, doCheck)
{
	if (doCheck == 1) {
    	for (var j=0; j < form.length; j++) {
	      currentElement = form.elements[j];
	      currentElementName=currentElement.name;
	      if (currentElementName.indexOf("arrivedonairline_ID") != -1) { 
	        if (currentElement.value.length == 0) {
	          alert("<%= (String)myMessages.getMessage(myLocale, "colname.arr_airline_id") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
	        }
	      } else if (currentElementName.indexOf("arrivedonflightnum") != -1) {
	        if (currentElement.value.length == 0) {
	          alert("<%= (String)myMessages.getMessage(myLocale, "colname.arr_flight_num") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
	        }
	      } else if (currentElementName.indexOf("disarrivedondate") != -1) {
	        if (currentElement.value.length == 0) {
	          alert("<%= (String)myMessages.getMessage(myLocale, "colname.arr_date") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
	          currentElement.focus();
	          return false;
	        }
	      } 
	    }
    }
	return true;
}
