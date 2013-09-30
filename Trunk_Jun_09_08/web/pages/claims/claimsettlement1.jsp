<%@ page language="java"%>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%
boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
boolean ntUser = PropertyBMO.isTrue("nt.user");
  Agent a = (Agent) session.getAttribute("user");
%>

<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.forms.ClaimSettlementForm"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
  var cal1xx = new CalendarPopup(); 


  </SCRIPT>
    </SCRIPT>
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="claimsettlement.header.process" />
          </h1>
        </div>
      </td>
    </tr>
  
<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd>
    <a href='searchIncident.do?incident=<bean:write name="claimSettlementForm" property="incident_ID"/>'><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.incident_info" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    
    <dd><a href="#"><span class="aab">&nbsp; <br />
    &nbsp;</span> <span class="bbb"><bean:message
      key="menu.claim_process" /></span> <span class="ccb">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=2"><span class="aa">&nbsp; <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_customer" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=3"><span class="aa">&nbsp; <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_baggage" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_resolution.do"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message key="menu.claim_payout" /></span>
    <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    
        <%
    	if (UserPermissions.hasPermission(
    			TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
    %>
    <dd><a href="claim_prorate.do"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_prorate" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    <%
    	}
    %>
	
	
     <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_DEPREC_CALCULATOR, a) && (ntUser || ntfsUser)) { %>
       <dd>
       <a href='claim_deprec_calc.do?incident_id=<bean:write name="claimSettlementForm" property="incident_ID" />'><span class="aa">&nbsp;
           <br />
           &nbsp;</span>
         <span class="bb"><bean:message key="menu.claim_deprec_calc" /></span>
         <span class="cc">&nbsp;
           <br />
           &nbsp;</span></a>
     </dd>
     <% } %>
  </dl>



  
  <tr>
    
    <td id="middlecolumn">
    <div id="maincontent"><html:form action="claim_settlement.do"
      method="post">

      <h1 class="green"><bean:message key="claimsettlement.header.process" /></h1>

      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td width=33%><bean:message key="claimsettlement.incident_ID" /><br />
          <html:text name="claimSettlementForm" property="incident_ID" size="15" disabled="true" styleClass="textfield" /></td>
          <td width=33%><bean:message key="claimsettlement.itemtype" /><br />
          <html:text name="claimSettlementForm" property="incidentItemType" size="15" disabled="true" styleClass="textfield" /></td>
          <td width=33%><bean:message key="claimsettlement.incident_status" /><br />
          <html:text name="claimSettlementForm" property="incidentStatus" size="15" disabled="true" styleClass="textfield" /></td>
          <td width=33%><bean:message key="claimsettlement.incident_createdate" /><br />
          <html:text name="claimSettlementForm" property="incidentCreateDate" size="15" disabled="true" styleClass="textfield" /></td>
          <td width=33%><bean:message key="claimsettlement.match_detected" /><br />
          <html:text name="claimSettlementForm" property="matchDetected" size="15" disabled="true" styleClass="textfield" /></td>
        </tr>

        <tr>
          <td width=33%><bean:message key="claimsettlement.recordLocator" /><br />
          <html:text name="claimSettlementForm" property="recordLocator" size="15" disabled="true" styleClass="textfield" /></td>
          <td width=33%><bean:message key="claimsettlement.agent" /><br />
          <html:text name="claimSettlementForm" property="claimAgent" size="15" styleClass="textfield" /></td>
          <td width=33% nowrap><bean:message key="claimsettlement.dateTakeover" /><br />
          <html:text name="claimSettlementForm" property="dateTakeover" size="12" maxlength="11" styleClass="textfield" />
            <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;">
          </td>
          <td colspan="2"><bean:message key="claimsettlement.claimType" /><br />
          <html:select name="claimSettlementForm" property="claimType" styleClass="dropdown">
            <html:option value="claimsettlement.domestic"><bean:message key="claimsettlement.domestic" /></html:option>
            <html:option value="claimsettlement.mc99"><bean:message key="claimsettlement.mc99" /></html:option>
          </html:select>         
          </td>
        </tr>
      </table>

      <p />
      <table cellspacing="0" cellpadding="0" class="form2">
        <tr>
          <td colspan="2" width="50%" class="header"><Strong><bean:message key="claimsettlement.contactAttempts" /></strong></td>
          <td colspan="2" width="50%" class="header"><Strong><bean:message key="claimsettlement.verification" /></strong>
          </td>
        <tr>
          <td width="25%" cellpadding="0" cellspacing="0" border="0"><bean:message key="claimsettlement.firstContact" /></td>
          <td width="25%">
          <html:text name="claimSettlementForm" property="firstContact" size="12" maxlength="11" styleClass="textfield" />
          <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar1" name="itcalendar1" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'firstContact','itcalendar1','MM/dd/yyyy'); return false;">
          </td>
          <td rowspan="19"><strong><bean:message key="claimsettlement.contentChecks" /></strong><br />
          <html:checkbox name="claimSettlementForm" property="verifyAddress"><bean:message key="claimsettlement.address" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyPhone"><bean:message key="claimsettlement.phone" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyEmail"><bean:message key="claimsettlement.email" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyBagColor"><bean:message key="claimsettlement.bagColor" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyBrand"><bean:message key="claimsettlement.brand" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyContents"><bean:message key="claimsettlement.contents" /></html:checkbox><br />
          
          <p /><strong><bean:message key="claimsettlement.fraudChecks" /></strong><br />
          <html:checkbox name="claimSettlementForm" property="verifyFraudCC"><bean:message key="claimsettlement.creditCard" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyFraudPhone"><bean:message key="claimsettlement.phone" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyFraudName"><bean:message key="claimsettlement.name" /></html:checkbox><br />

          <p /><strong><bean:message key="claimsettlement.checkTraceResults" /></strong><br />
          <html:checkbox name="claimSettlementForm" property="verifyTrace1"><bean:message key="claimsettlement.traceResults1" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyTrace2"><bean:message key="claimsettlement.traceResults2" /></html:checkbox><br />
          <html:checkbox name="claimSettlementForm" property="verifyTrace3"><bean:message key="claimsettlement.traceResults3" /></html:checkbox><br />

          </td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.secondContact" /></td>
          <td>
          <html:text name="claimSettlementForm" property="secondContact" size="12" maxlength="11" styleClass="textfield" />
          <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar2" name="itcalendar2" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'secondContact','itcalendar2','MM/dd/yyyy'); return false;">
          </td>
        </tr>
        <!-- total weight input box begins -->
        <tr>
          <td colspan="2" class="header"><Strong><bean:message key="claimsettlement.pplcActions" /></strong></td>
        </tr>
        <tr>
          <td>Total Baggage Weight</td>
          <td>
			<html:text name="claimSettlementForm" property="overall_weight" size="8" maxlength="10" styleClass="textfield" />
			lb
          </td>
        </tr>
        <!-- total weight input box ends -->
        <tr>
          <td align="center" colspan="2">
          <%
            String thisIncidentId = ((ClaimSettlementForm)session.getAttribute("claimSettlementForm")).getIncident_ID();
          %>                    
          <input type="button" id="button" onclick="openReportWindow('searchIncident.do?receipt=1&toprint=19&incident=<%= thisIncidentId %>','LostReceipt',800,600);return false;" value="<bean:message key="link.print.pplc" />">

            
          </td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.pplcSent" /></td>
          <td>
          <html:text name="claimSettlementForm" property="pplcSent" size="12" maxlength="11" styleClass="textfield" />
          
          <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar3" name="itcalendar3" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'pplcSent','itcalendar3','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.pplcVia" /></td>
          <td>
            <html:select name="claimSettlementForm" property="pplcVia" styleClass="dropdown">
              <html:option value="email"><bean:message key="claimsettlement.email" /></html:option>
              <html:option value="mail"><bean:message key="claimsettlement.mail" /></html:option>
              <html:option value="fax"><bean:message key="claimsettlement.fax" /></html:option>
            </html:select>
          </td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.pplcDue" /></td>
          <td>
            <html:text name="claimSettlementForm" property="pplcDue" size="12" maxlength="11" styleClass="textfield" />
          <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar4" name="itcalendar4" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'pplcDue','itcalendar4','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.pplcReceived" /></td>
          <td>
            <html:text name="claimSettlementForm" property="pplcReceived" size="12" maxlength="11" styleClass="textfield" />
            <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar5" name="itcalendar5" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'pplcReceived','itcalendar5','MM/dd/yyyy'); return false;"></td>
        </tr>

        <tr>
          <td colspan="2" class="header"><Strong><bean:message key="claimsettlement.followUp" /></strong></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.depreciationDue" /></td>
          <td><html:text name="claimSettlementForm" property="depreciationDue" size="12" maxlength="11" styleClass="textfield" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar6" name="itcalendar6" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'depreciationDue','itcalendar6','MM/dd/yyyy'); return false;"></td>
        </tr>

        <tr>
          <td><bean:message key="claimsettlement.depreciationComplete" /></td>
          <td><html:text name="claimSettlementForm" property="depreciationComplete" size="12" maxlength="11" styleClass="textfield" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar7" name="itcalendar7" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'depreciationComplete','itcalendar7','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.offerDue" /></td>
          <td><html:text name="claimSettlementForm" property="offerDue" size="12" maxlength="11" styleClass="textfield" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar8" name="itcalendar8" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'offerDue','itcalendar8','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
        	<td>
        		<bean:message key="claimsettlement.offerSent" />
        	</td>
        	<td>
        		<html:text name="claimSettlementForm" property="offerSent" size="12" maxlength="11" styleClass="textfield" />
        		<img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar0" name="itcalendar0"
        			height="15" width="20" border="0" onmouseover="this.style.cursor='hand'"
        			onClick="cal1xx.select2(document.claimSettlementForm, 'offerSent','itcalendar0','MM/dd/yyyy'); return false;" />
        	</td>
        </tr>
        <tr>
        	<td>
        		<bean:message key="claimsettlement.offerSentVia" />
        	</td>
        	<td>
        		<html:select name="claimSettlementForm" property="offerSentVia" styleId="dropdown">
            		<html:option value=""><bean:message key="select.please_select" /></html:option>
                    <html:option value="email"><bean:message key="claimsettlement.email" /></html:option>
              		<html:option value="mail"><bean:message key="claimsettlement.mail" /></html:option>
              		<html:option value="fax"><bean:message key="claimsettlement.fax" /></html:option>
          		</html:select>
        	</td>
        </tr>
        <tr>
        	<td><bean:message key="claimsettlement.releaseDue" /></td>
        	<td>
        		<html:text name="claimSettlementForm" property="releaseDue" size="12" maxlength="11" styleClass="textfield" />
        		<img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar1" name="itcalendar1"
        			height="15" width="20" border="0" onmouseover="this.style.cursor='hand'"
            		onClick="cal1xx.select2(document.claimSettlementForm, 'releaseDue','itcalendar1','MM/dd/yyyy'); return false;" />
        	</td>
        </tr>
        <tr>
        	<td><bean:message key="claimsettlement.revisitRequested" /></td>
        	<td>
        		<html:text name="claimSettlementForm" property="revisitRequested" size="12" maxlength="11" styleClass="textfield" />
            	<img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar2" name="itcalendar2"
            		height="15" width="20" border="0" onmouseover="this.style.cursor='hand'"
            		onClick="cal1xx.select2(document.claimSettlementForm, 'revisitRequested','itcalendar2','MM/dd/yyyy'); return false;" />
        	</td>
        </tr>
        <tr>
        	<td><bean:message key="claimsettlement.revisitedBy" /></td>
        	<td>
        		<html:text name="claimSettlementForm" property="revisitedBy" size="20" maxlength="20" styleClass="textfield" />
        	</td>
        </tr>
      </table>
      <table cellspacing="0" cellpadding="0" class="form2">
        <tr>
          <td><bean:message key="claimsettlement.comments" /><br />
          <html:textarea name="claimSettlementForm" property="comments" cols="70" rows="10" disabled="true"></html:textarea>
      <br />
          <bean:message key="claimsettlement.newComment" /><br />
          <html:textarea name="claimSettlementForm" property="newComment" cols="70" rows="3"></html:textarea>

          </td>
        </tr>
      </table>


      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" valign="top"><br>
          <html:submit property="save1" styleId="button">
            <bean:message key="button.save" />
          </html:submit></td>
        </tr>
      </table>
    </html:form>


