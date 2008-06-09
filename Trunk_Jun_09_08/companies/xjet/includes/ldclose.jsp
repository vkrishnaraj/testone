<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.struts.action.Action" %>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>


function validateLdClose(form, key)
{	
	if (key == 0) {
		return true;
	}
	doCheck = 0;
	
	var theindex = 0;
	for (var j=0;j<form.length;j++) {
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
	return true;
}

