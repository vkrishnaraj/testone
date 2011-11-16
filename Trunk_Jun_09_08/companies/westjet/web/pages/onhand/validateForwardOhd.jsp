<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>

<%
Agent a = (Agent)session.getAttribute("user");
ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>

<SCRIPT LANGUAGE="JavaScript">

function validateForwardOHD (form) {
	for (var j=0;j<form.length;j++) {
		currentElement = form.elements[j];
    	currentElementName=currentElement.name;

    	if (currentElementName.indexOf("ohdList") != -1 && currentElement.value.length == 0) {
	    	alert('<%=resources.getString("forward.ohd.expedite.tag.num") + " " + resources.getString("error.validation.isRequired") %>')
	    	currentElement.focus();
	        return false;
	    }

	    if (currentElementName.indexOf("legfrom") != -1 && currentElement.value.length == 0) {
	    	alert('<%=resources.getString("forward.ohd.leg.from") + " " + resources.getString("error.validation.isRequired") %>')
	    	currentElement.focus();
	        return false;
	    }
	    
	    if (currentElementName.indexOf("legto") != -1 && currentElement.value.length == 0) {
	    	alert('<%=resources.getString("forward.ohd.leg.to") + " " + resources.getString("error.validation.isRequired") %>')
	    	currentElement.focus();
	        return false;
	    }
	    
	    if (currentElementName.indexOf("flightnum") != -1 && currentElement.value.length == 0) {
	    	alert('<%=resources.getString("forward.ohd.flight.num") + " " + resources.getString("error.validation.isRequired") %>')
	    	currentElement.focus();
	        return false;
	    }
	    
  	}
}
  
</SCRIPT>