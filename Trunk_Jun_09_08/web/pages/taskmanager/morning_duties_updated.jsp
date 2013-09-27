<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  boolean isCss = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_MANAGE_CSS_DAILY_CALLS, a);
  String url = "GeneralTask";
  if (isCss) {
	  url = "css_calls";
  }
%>

<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
          
          
          <logic:present name="errorMsg" scope="request">
          <bean:message name="errorMsg" scope="request"/>
          <br/>
          <br/>
          	<logic:present name="Incident_ID" scope="request">
          	<a href='<%=url %>.do?loadIncident=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
          	</logic:present>
          	<logic:present name="taskmanagerbutton" scope="request">
  			<input type="button" value="Return to Task Manager" onclick='document.location.href="logon.do?taskmanager=1";' id="button">
  			</logic:present>   
          </logic:present>
          
          
          
          <logic:notPresent name="errorMsg" scope="request">
          <bean:message key="generaltask.taskupdatedsuccessfully" />
            <% int day = 1;
               if (request.getAttribute("day") != null) { 
               		day = (Integer) request.getAttribute("day"); 
               	}%>
          	<br/>
          	<logic:present name="gettaskbutton">
          	<input type="button" value="<bean:message key="generaltask.getnexttask" />" onclick='document.location.href="<%=url %>.do?gettask=<%=day %>";' id="button">
  			</logic:present>   
  			<logic:present name="taskmanagerbutton" scope="request">
  			<input type="button" value="Return to Task Manager" onclick='document.location.href="logon.do?taskmanager=1";' id="button">
  			</logic:present>   
         </logic:notPresent>
          </h1>
                </td>
              </tr>
            </table>
