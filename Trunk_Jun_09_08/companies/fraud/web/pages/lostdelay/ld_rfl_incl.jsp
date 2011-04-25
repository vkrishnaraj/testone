<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%
	String cssFormClass = "form2_ld";
	int report_type = 1;

	if(request.getAttribute("lostdelay") != null) {
		cssFormClass = "form2_ld";
		report_type = 1;
	}
	else if(request.getAttribute("missing") != null) {
		cssFormClass = "form2_pil";
		report_type = 2;
	}
	else {
		cssFormClass = "form2_dam";
		report_type = 0;
	}
%>
<a name="faultinformation"></a>
<h1 class="green"><bean:message key="header.fault_information" />
<%
	if(report_type == 0) {
%> <a href="#"
	onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/close_reports.htm#Close Report_Fields');return false;">
<%
	}
	else if(report_type == 1) {
%> <a href="#"
	onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/close_reports_(ld).htm#Close Report_Fields');return false;">
<%
	}
	else if(report_type == 2) {
%> <a href="#"
	onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/close_reports_(ma).htm#Close Report_Fields');return false;">
<%
	}
%> <img src="deployment/main/images/nettracer/button_help.gif"
	width="20" height="21" border="0"></a></h1>
<span class="reqfield">*</span>
<bean:message key="message.required" />
<table class="<%=cssFormClass%>" cellspacing="0" cellpadding="0">
		                    <%	String incidentId = "" + request.getAttribute("incident");
    		  					if (DisputeResolutionUtils.isIncidentLocked(incidentId)) { %>
    <jsp:include page="/pages/includes/closereport_no_lock_ro_incl.jsp" />
    		  				<%  } else { %>	
    <jsp:include page="/pages/includes/closereport_no_lock_incl.jsp" />
    		  				<%  } %>
	
</table>
<br>
&nbsp;&nbsp;&uarr;
<a href="#"><bean:message key="link.to_top" /></a>
<br>
<br>