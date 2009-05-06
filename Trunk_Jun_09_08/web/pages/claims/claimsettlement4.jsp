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
<%
	Agent a = (Agent) session.getAttribute("user");
%>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
  var cal1xx = new CalendarPopup(); 


  </SCRIPT>
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="claimsettlement.header.disposition" />
          </h1>
        </div>
      </td>
    </tr>

<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd><a href='searchIncident.do?incident=<bean:write name="claimSettlementForm" property="incident_ID"/>'><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.incident_info" /></span> <span class="cc">&nbsp; <br />
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
    <dd><a href="claim_settlement.do?screen=1"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_process" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=2"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_customer" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=3"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_baggage" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="#"><span class="aab">&nbsp; <br />
    &nbsp;</span> <span class="bbb"><bean:message
      key="menu.claim_summary" /></span> <span class="ccb">&nbsp; <br />
    &nbsp;</span></a></dd>

  </dl>



  
  <tr>
    
    <td id="middlecolumn">
    <div id="maincontent"><html:form action="claim_settlement.do"
      method="post">

      <h1 class="green"><bean:message
        key="claimsettlement.disposition" /></h1>

      <table class="form2" cellspacing="0" cellpadding="0" border="0">
        <tr>
          <td><bean:message key="claimsettlement.offerSent" /><br />
          <html:text name="claimSettlementForm" property="offerSent"
            size="12" maxlength="11" styleClass="textfield" /> <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'offerSent','itcalendar0','MM/dd/yyyy'); return false;">
          </td>
          <td><bean:message key="claimsettlement.amountClaimed" /><br />

          <html:text name="claimSettlementForm" property="amountClaimed"
            size="10" maxlength="10" styleClass="textfield" /></td>
          <td><strong><bean:message
            key="claimsettlement.totalPaid" /></strong><br />
          <html:text name="claimSettlementForm" property="totalPaid"
            size="10" maxlength="10" styleClass="textfield" /></td>

        </tr>
        <tr>
          <td><bean:message key="claimsettlement.offerSentVia" /><br />
          <html:select name="claimSettlementForm"
            property="offerSentVia" styleId="dropdown">
            <html:option value="">
              <bean:message key="select.please_select" />
            </html:option>
            <html:option value="mail">Mail</html:option>
            <html:option value="email">Email</html:option>
          </html:select>
          <td><bean:message key="claimsettlement.amountOffered" /><br />
          <html:text name="claimSettlementForm" property="amountOffered"
            size="10" maxlength="10" styleClass="textfield" /></td>
          <td><strong><bean:message
            key="claimsettlement.totalPaidVouchers" /></strong><br />
          <html:text name="claimSettlementForm"
            property="totalPaidVouchers" size="10" maxlength="10"
            styleClass="textfield" /></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.releaseDue" /><br />
          <html:text name="claimSettlementForm" property="releaseDue"
            size="12" maxlength="11" styleClass="textfield" /> <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar1" name="itcalendar1" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'releaseDue','itcalendar1','MM/dd/yyyy'); return false;">
          </td>
          <td><bean:message key="claimsettlement.auditVOOffered" /><br />
          <html:text name="claimSettlementForm"
            property="auditVOOffered" size="10" maxlength="10"
            styleClass="textfield" /></td>
          <td><strong><bean:message
            key="claimsettlement.totalPaidCertif" /></strong><br />
          <html:text name="claimSettlementForm"
            property="totalPaidCertif" size="10" maxlength="10"
            styleClass="textfield" /></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.revisitRequested" /><br />
          <html:text name="claimSettlementForm"
            property="revisitRequested" size="12" maxlength="11"
            styleClass="textfield" /> <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar2" name="itcalendar2" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'revisitRequested','itcalendar2','MM/dd/yyyy'); return false;">
          <td><bean:message key="claimsettlement.revisitedBy" /><br />
          <html:text name="claimSettlementForm" property="revisitedBy"
            size="20" maxlength="20" styleClass="textfield" /></td>
          <td><bean:message key="claimsettlement.dateStatusChange" /><br />
          <html:text name="claimSettlementForm"
            property="dateStatusChange" size="12" maxlength="11"
            styleClass="textfield" /> <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar3" name="itcalendar3" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateStatusChange','itcalendar3','MM/dd/yyyy'); return false;">
          </td>
        </tr>
      </table>


      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" valign="top"><br>
          <html:submit property="save4" styleId="button">
            <bean:message key="button.save" />
          </html:submit></td>
        </tr>
      </table>

      <p />&nbsp;
      <h1 class="green"><bean:message key="header.payout_summary" />
      <a href="#"
        onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img
        src="deployment/main/images/nettracer/button_help.gif"
        width="20" height="21" border="0"></a></h1>
      <jsp:include page="/pages/includes/incident_expense_incl.jsp">
        <jsp:param name="formCss" value="form2" />
      </jsp:include>



    </html:form>