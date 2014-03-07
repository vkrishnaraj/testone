<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.db.dr.Dispute" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.dr.DisputeUtils" %>

<%
	  Agent a = (Agent)session.getAttribute("user");
	  String cssFormClass = "form2_ld";
	  IncidentForm myform=(com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm");
	  String incident_ID = myform.getIncident_ID();
	  
	  	Dispute myDispute = DisputeUtils.getDisputeByIncidentId(incident_ID);
		String disputeProcess = "false";
		if (myDispute != null) {
			disputeProcess = "true";
		} 
		request.setAttribute("disputeProcess", disputeProcess);
  
	  String disputeActionType = "view";
	  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)
			  && myDispute != null
			  && myDispute.getStatus().getStatus_ID() == TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN) { 
			disputeActionType = "viewToResolve"; 
	  }
	  boolean bagLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES));  
%>
  
  <%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<script language="javascript" SRC="deployment/main/js/date.js"></script>
  <script language="javascript" SRC="deployment/main/js/AnchorPosition.js"></script>
  <script language="javascript" SRC="deployment/main/js/PopupWindow.js"></script>
  <script language="javascript" SRC="deployment/main/js/popcalendar.js"></script>
  <script language="javascript" SRC="deployment/main/js/ajax_forall.js"></script>
  <script language="javascript">
	var cal1xx = new CalendarPopup();	
	var doCheck = 0;
	var doSaveCheck = 0;
    
    <jsp:include page="/pages/includes/ldclose.jsp" />
    
  </script>
  
  <html:form styleId="dirtyCheck-form" action="lostDelay.do" method="post" enctype="multipart/form-data" onsubmit="return validateLdClose(this, doCheck);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.close" />
            (
            <bean:write name="incident" scope="request" />
            )
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <td></td>
              <td></td>
              <jsp:include page="/pages/includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    
    
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="lostDelay.do?close=1"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.close" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a) 
            		|| UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a)){ %>
            <logic:equal name="disputeProcess" scope="request" value="true">
            <dd>
              <a href='disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=<%=disputeActionType %>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.dispute.resolution" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            </logic:equal>
            <% } %>
          </dl>
        </div>
      </td>
    </tr>
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <span class="reqfield">*</span>
          <bean:message key="message.required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          
            <jsp:include page="/pages/includes/closebagloss_incl.jsp" />
          <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
           <html:hidden name="close" property="close" value="1"/>
           	<% if(!bagLossCodes){
           		if (DisputeResolutionUtils.isIncidentLocked(request.getAttribute("incident").toString())) { %>
            <jsp:include page="/pages/includes/closereport_no_lock_ro_incl.jsp" />
            <% } else { %>
            <jsp:include page="/pages/includes/closereport_incl.jsp" />
            <% }
          	}%>
            
          </table>
          <jsp:include page="/pages/includes/remarkclose_incl.jsp" />
        </div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                      <logic:equal name="disputeProcess" scope="request" value="false">
		                  <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a) && (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a))){ 
		                   		String incidentId = "" + request.getAttribute("incident");
    		  					if (! DisputeResolutionUtils.isIncidentLocked(incidentId)) { %>
		                    <input type="submit" id="button" value='<bean:message key="button.dispute.fault" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=start";return false;'>
		                     <% } 
		                    } %>
	                  </logic:equal>
                    <html:submit property="save" styleId="button" onclick="anyLossCodeChanges(); doSaveCheck=1;">
                      <bean:message key="button.save" />
                    </html:submit>
                  </logic:equal>
                  <logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                  
                  	<% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_ALLOW_OPEN_INCIDENT_DISPUTE)) { %>
                      <logic:equal name="disputeProcess" scope="request" value="false">
		                  <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a) && (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a))){ 
		                   		String incidentId = "" + request.getAttribute("incident");
    		  					if (! DisputeResolutionUtils.isIncidentLocked(incidentId)) { %>
		                    <input type="submit" id="button" value='<bean:message key="button.dispute.fault" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=start";return false;'>
		                     <% } 
		                    } %>
	                  </logic:equal>
	                <% } 
                     if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SAVE_ON_CLOSE_PAGE)) { %>
                    <html:submit property="save" styleId="button" onclick="anyLossCodeChanges(); doSaveCheck=1;">
                      <bean:message key="button.save" />
                    </html:submit>
                  <% } %>

                  &nbsp;
                  <% if (((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING) { %>
                  <html:submit property="doclose" styleId="button" disabled="true" >
                    <bean:message key="button.closereport" />
                  </html:submit>
                  <% } else { %>
                  <html:submit property="doclose" styleId="button" onclick="anyLossCodeChanges(); doCheck = 1;" >
                    <bean:message key="button.closereport" />
                  </html:submit>
                  <% } %>
                  </logic:notEqual>
                  &nbsp;
                  	<c:if test="${!empty incidentForm.wt_id and incidentForm.wtFile.wt_status != 'CLOSED'}">
                  <%	
                   if (a.getStation().getCompany().getVariable().getWt_enabled() == 1){
                	   if (a.getStation().getCompany().getVariable().getWt_write_enabled() == 1){
                		   if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT, a)){
                  %>
                  <% if (((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING) { %>
                    <html:submit property="doclosewt" styleId="wtbutton" disabled="true">
                      <bean:message key="button.closetoWT" />
                    </html:submit>
                  <% } else { %>
                    <html:submit property="doclosewt" styleId="wtbutton">
                      <bean:message key="button.closetoWT" />
                    </html:submit>
                  <% }
                		   }
                	   }
                   }
                  %>
                  </c:if>
                </td>
              </tr>
                  <% if (((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING) { %>
              <tr>
                <td>
          		  <font color=red>
                    <bean:message key="tracing.status.close.disabled" />
                  </font>
                </td>
              </tr>
              	  <% } %>
            </table>
    </html:form>
