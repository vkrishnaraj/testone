<%@page import="java.util.Locale"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="org.apache.struts.util.PropertyMessageResources"%>
<%@ page language="java"%>

<%
	Agent a = (Agent) session.getAttribute("user");
    PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
    Locale  myLocale   = (Locale)session.getAttribute("org.apache.struts.action.LOCALE");
%>

function validateMissingClose(form, doCheck)
{

	if (doCheck == 1) {
  		return window.confirm("Have you verified any Travel Bank Credit(s) for this file has been created?");
  	}
}