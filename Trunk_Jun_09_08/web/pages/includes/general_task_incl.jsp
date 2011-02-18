<%@ page language="java" %>
<%@ taglib prefix="nt" uri="http://nettracerTags"%> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>

<%
Agent a = (Agent)session.getAttribute("user");
boolean hasPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_MANAGE_TASKS, a);
if (hasPermission) {
%>
<c:if test="${!empty sessionTaskContainer and !empty nt:instanceOf(sessionTaskContainer,'com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask')}">

<%
	  
      

  
  String cssFormClass;
 
  int report_type = 0;
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
      cssFormClass = "form2_pil";
    }
  }
%>
  <h1 class="green">
    <bean:message key="header.nettracer.currentTask" />
  </h1>
      
      <div id="netTracerGeneralTask">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
				<tr><td width ="20%"><bean:message key="generaltask.tasktype" />:</td><td><strong><bean:message name="sessionTaskContainer" property="label"/></strong></td></tr>
				<tr><td width ="20%"><bean:message key="colname.agentassigned_nobr" />:</td><td><bean:write name="sessionTaskContainer" property="assigned_agent.username"/></td></tr>
				<tr><td width ="20%"><bean:message key="generaltask.timestarted" />:</td><td><bean:write name="sessionTaskContainer" property="opened_timestamp"/>&nbsp;&nbsp;<input type="button" value="<bean:message key="button.task.pause"/>" onclick="" id="button"></td></tr>
		 		<tr><td width ="20%"><bean:message key="generaltask.description" />:</td><td><bean:message name="sessionTaskContainer" property="description"/></td></tr>
		 		

		  
				<tr><td colspan="2">
          <span style="float:left">
          	<input type="button" value="<bean:message key="button.task.complete" />" onclick="" id="button">
          </span>
          <span style="float:right">
          	<select id="task.defer.time" class="dropdown">
          		<option value=""><bean:message key="select.please_select" /></option>
          		<option value="30min"><bean:message key="generaltask.defer.30min" /></option>
          		<option value="2hr"><bean:message key="generaltask.defer.2hr" /></option>
          		<option value="4hr"><bean:message key="generaltask.defer.4hr" /></option>
          		<option value="12hr"><bean:message key="generaltask.defer.12hr" /></option>
          		<option value="24hr"><bean:message key="generaltask.defer.24hr" /></option>
          	</select>
          	<input type="button" value="<bean:message key="button.task.defer" />" onclick="" id="button">&nbsp;&nbsp;
          	<input type="button" value="<bean:message key="button.task.abort" />" onclick="" id="button">
          </span>
		  </td>
          </tr>
        </table>
        </div>

      <br>
      <br>
      &nbsp;&nbsp;&uarr;
      <a href="#"><bean:message key="link.to_top" /></a>
      <br>
      <br>

</c:if>
<% } %>
