<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="nt" uri="http://nettracerTags"%> 

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <logic:present name="lostdelay" scope="request">
              <bean:message key="message.lostdelay.close.file.success" />
              <p>
                <bean:message key="colname.incident_num" />
                :
                <a href='lostDelay.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
              </logic:present>
              <logic:present name="missingarticles" scope="request">
                <bean:message key="message.missingarticles.close.file.success" />
                <p>
                  <bean:message key="colname.incident_num" />
                  :
                  <a href='missing.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                </logic:present>
                <logic:present name="damaged" scope="request">
                  <bean:message key="message.damaged.close.file.success" />
                  <p>
                    <bean:message key="colname.incident_num" />
                    :
                    <a href='damaged.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                  </logic:present>
                </td>
              </tr>
            </table>
            <%
            	Agent a = (Agent) session.getAttribute("user");
            	boolean hasPermission = UserPermissions.hasPermission(
            			TracingConstants.SYSTEM_COMPONENT_MANAGE_TASKS, a);
            	GeneralTask task = (GeneralTask) session
            			.getAttribute("sessionTaskContainer");
            	String incident_id = (String) request.getAttribute("Incident_ID");
            	boolean display = false;
            	if (hasPermission
            			&& task != null
            			&& task instanceof com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask) {
            		com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mtask = (com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask) task;
            		if (mtask.getIncident() != null
            				&& mtask.getIncident().getIncident_ID() != null
            				&& mtask.getIncident().getIncident_ID()
            						.equals(incident_id)) {
            			display = true;
            		}
            	}

            	if (display) {
            %>
              <div id="maincontent">
             <jsp:include page="/pages/includes/general_task_incl.jsp" />
             </div>
             <%} %>
