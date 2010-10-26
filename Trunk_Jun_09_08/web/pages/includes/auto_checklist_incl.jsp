<%@ page language="java" %>
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

<c:if test="${!empty incidentForm.incident_ID and !empty incidentObj and incidentForm.incident_ID == incidentObj.incident_ID}">
<%
	  Agent a = (Agent)session.getAttribute("user");
      boolean bIncidentChecklist = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST, a);
      boolean bIncidentChecklistReadOnly = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST_READ_ONLY, a);
      boolean ldCrmIntegration = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PUSH_LD, a);
      boolean damCrmIntegration = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PUSH_DAM, a);
      boolean pilCrmIntegration = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PUSH_PIL, a);
      
      if (bIncidentChecklist || bIncidentChecklistReadOnly || ldCrmIntegration || damCrmIntegration || pilCrmIntegration) {

  
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
    <bean:message key="header.additional.functions" />
  </h1>
      
      <div id="pax_0">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
		 <td align="center"> 
		  <%
		  if (bIncidentChecklist || bIncidentChecklistReadOnly) {
		  %>
          <script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/jquery.bgiframe.min.js"></script>
            
              
              &nbsp;<br />
              <script type="text/javascript">
          		function checklistModalBox() {
              		jQuery.ui.dialog.defaults.bgiframe = true;
              		jQuery("#dialog").dialog({bgiframe : true,
        				autoOpen: false, modal: true, draggable: false, resizable: false, 
        				width: 620, height: 300, title: 'Incident Checklist' 
        			});
					jQuery('#dialog-inner-content').html(getLoadingContent());	
					jQuery("#dialog").dialog("open");	
					jQuery('#dialog-inner-content').load("incidentChecklist.do?incident_id=<bean:write name='incidentForm' property='incident_ID'/>", {}, function() {checklistModalCallback();});
          		}
          	 </script>
			<input type="button" value="View Incident Checklist" onclick="checklistModalBox();" id="button" />

              <br />&nbsp;
            
           <% 			  
		  } 
		  if (
				  (report_type == 0 && damCrmIntegration) || 
				  (report_type == 1 && ldCrmIntegration) || 
				  (report_type == 2 && pilCrmIntegration)) {
		  %>
		  
		              <hr width="50%"/>
              <script type="text/javascript">
				
				function pushDataToCrm() {
					jQuery('#crmContent').html(getProcessingContent());
					jQuery('#crmContent').load("pushToCrm.do?incident_id=<bean:write name='incidentForm' property='incident_ID'/>");
				}
          	 </script>
              <div id="crmContent">
              
              <jsp:include page="/pages/includes/push_to_crm.jsp" />
              
			  </div>

              <br />&nbsp;
                   <hr width="50%"/>
            
		   <% 			  
		  }
		  IncidentForm form = (IncidentForm)session.getAttribute("incidentForm"); 
		  if (form.getOc_claim_id() != 0) {
		  	String url = "displayClaim.do?ajax=1&claimId=" + form.getOc_claim_id();
		  %>
		  	<input type="button" value="View Online Claim" onclick="loadSlideupContainer('<%=url %>')" id="button" />
		  	<br />&nbsp;
		  <% 
		  } 
		  %>
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

<% 
      }
%>
</c:if>
