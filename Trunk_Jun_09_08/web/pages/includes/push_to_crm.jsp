<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>

<%
  Agent a = (Agent)session.getAttribute("user");
  String agentName = a.getUsername();
  String currentIncidentId = "" + request.getAttribute("currentIncidentId");
  int x = 0;
%>
&nbsp;<br />
<c:if
	test="${!empty incidentForm.crmFile and !empty incidentForm.crmFile.crm_key}">
	<bean:message key="crm.key" />: <c:out value="${incidentForm.crmFile.crm_key}" />
	
</c:if>
<c:if
	test="${empty incidentForm.crmFile or empty incidentForm.crmFile.crm_key}">
	<input type="button" value='<bean:message key="button.push.to.crm"/>'
		onclick="pushDataToCrm();" id="button" />
</c:if>
<br />&nbsp;
