<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>

<%@ page language="java"%>

<%
	Agent a = (Agent) session.getAttribute("user");
org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
request.getAttribute("org.apache.struts.action.MESSAGE");
java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
"org.apache.struts.action.LOCALE");
%>

function validateLdClose(form, doCheck) { if (doCheck == 1) {
<%
	Station zzz = StationBMO.getStationByCode("ZZZ", "US");
	int zzzId = zzz.getStation_ID();

	if (request.getAttribute("lostdelay") != null) {
%>
      if (form.faultstation_id.value == <%=zzzId%>) { 
         if (form.loss_code.value != 79) {
          alert("<bean:message key="ldclose.error.settozzz79" />");
          form.loss_code.value = 79;
          return false;
        } 
      }
<%
	}
%>

    }
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
