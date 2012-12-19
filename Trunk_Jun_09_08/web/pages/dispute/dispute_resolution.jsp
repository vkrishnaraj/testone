<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>


<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>
<%
  	Agent a = (Agent)session.getAttribute("user");
ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
  	String cssFormClass = "form2_ld";
  
  	String incident = "" + request.getAttribute("incident");
  	int incidentType = DisputeResolutionUtils.getIncidentType(incident);
  	
	String targetAction = "lostDelay.do";
	if (incidentType == TracingConstants.MISSING_ARTICLES) {
		targetAction = "missing.do";
	} else if (incidentType == TracingConstants.DAMAGED_BAG) {
		targetAction = "damaged.do";
	}
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
    
    <jsp:include page="/pages/includes/ldclose.jsp" />
  </script>
  
 <SCRIPT LANGUAGE="JavaScript">
  function textCounter2(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } else {
      countfield.value = maxlimit - field.value.length;
    }
  }
  
  function validateReqDisputeForm(form)
  {
  	for (var j=0;j < form.length; j++) {
  	
      currentElement = form.elements[j];
      currentElementName=currentElement.name;
  	if (currentElementName.indexOf("faultstation_id") != -1) {  
		
		 if (currentElement.value == "")
		  {
		    alert("<%= (String)bundle.getString( "colname.faultstation") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
		    currentElement.focus();
		    return false;
		  }
		}
	  else if (currentElementName.indexOf("loss_code") != -1) {  
		
		 if (currentElement.value == "0")
		  {
		    alert("<%= (String)bundle.getString( "colname.closereport.losscode") %>" + " <%= (String)bundle.getString( "error.validation.isRequired") %>");
		    currentElement.focus();
		    return false;
		  }
		}
		
	}
  }
 </SCRIPT> 
 
  
  
  <html:form action="disputeResolution.do?actionType=new" method="post" enctype="multipart/form-data" onsubmit="return validateLdClose(this, doCheck);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.dispute" />
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
    <input type='hidden' name='id' value='<bean:write name="incident" scope="request"/>'>
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
              <a href="<%=targetAction %>?close=1"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.close" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href='disputeResolution.do?incident=<bean:write name="incident" scope="request"/>'><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.dispute.resolution" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
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

          <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <input type="hidden" name="close" value="1">
            <jsp:include page="/pages/includes/disputereport_incl.jsp" />
          </table>
          
		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
			<tr>
				<td valign="top" colspan=3>
					<bean:message key="colname.dispute.resolution.explanation" /><br>
                  	<textarea name="disputeRemark" cols="80" rows="10" onkeydown="textCounter2(this.form.elements['disputeRemark'], this.form.elements['remarkLimit'],1500);" onkeyup="textCounter2(this.form.elements['disputeRemark'], this.form.elements['remarkLimit'],1500);"></textarea>
                  	<input name="remarkLimit" type="text" value="1500" size="4" maxlength="4" disabled="true" />
		        </td>
		    </tr>
		</table>

            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                  <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a)){ %>
                    <input type="submit" id="button" value='<bean:message key="button.dispute.fault" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>";return false;'>
                  <% } %>
                    <html:submit property="save" styleId="button">
                      <bean:message key="button.save" />
                    </html:submit>
                  </logic:equal>
                  <logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                  
                  <% if (TracerProperties.isTrue(TracerProperties.SAVE_ON_CLOSE_PAGE)) { %>
                    <html:submit property="save" styleId="button">
                      <bean:message key="button.save" />
                    </html:submit>
                  <% } %>

                  &nbsp;
                  
                  <html:submit property="requestDispute" styleId="button" onclick="doCheck = 2; return validateReqDisputeForm(this.form)" >
                    <bean:message key="button.request.dispute.report" />
                  </html:submit>
                  </logic:notEqual>
                  &nbsp;
                  	<c:if test="${!empty incidentForm.wt_id and incidentForm.wtFile.wt_status != 'CLOSED'}">
                  <%	
                   if (a.getStation().getCompany().getVariable().getWt_enabled() == 1){
                	   if (a.getStation().getCompany().getVariable().getWt_write_enabled() == 1){
                		   if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT, a)){
                  %>
                  
                    <html:submit property="doclosewt" styleId="button">
                      <bean:message key="button.closetoWT" />
                    </html:submit>
                  <%
                		   }
                	   }
                   }
                  %>
                  </c:if>
                </td>
              </tr>
            </table>
        </div>
    </html:form>
