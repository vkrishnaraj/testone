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
<!-- Calendar includes -->
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <!--
  var cal1xx = new CalendarPopup(); 

    // -->
  </SCRIPT>
<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd>
    <a href='searchIncident.do?incident='><span class="aa">&nbsp;
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
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
%>
                        <dd>
                          <a href="claim_prorate.do"><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_prorate" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
<%
                      }
%>

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

    <dd><a href="claim_settlement.do?screen=4"><span class="aa">&nbsp; <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_summary" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

  </dl>



  <!-- END ICONS MENU -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn"><!-- MAIN BODY -->
    <div id="maincontent"><html:form action="claim_resolution.do"
      method="post">

      <h1 class="green">Claim Process</h1>

      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td width=33%>PAWOB Number<br />
          <input type="text" size="10" value="ATLB6000001" disabled /></td>
          <td width=33%>PAWOB Type<br />
          <input type="text" size="10" value="Lost/Delayed" disabled /></td>
          <td width=33%>Claim Status<br />
          <input type="text" size="10" value="Open" disabled /></td>
          <td width=33%>Date of Claim<br />
          <input type="text" size="10" value="01/01/2009" disabled /></td>
          <td width=33%>Match Detected<br />
          <input type="text" size="10" value="YES"
            /></td>
        </tr>

        <tr>
          <td width=33%>Record Locator<br />
          <input type="text" size="10" value="ABCDEF" disabled /></td>
          <td width=33%>CBS Crewmember<br />
          <input type="text" value="jbuser" /></td>
          <td width=33%>Date Takeover<br />
          <input name="dateTakeover" type="text" size="10"
            value="01/01/2009" /> <img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;">
          </td>
          <td width=33%>Claim Type<br />
          <select>
            <option>MC99</option>
            <option>Domestic
            <option>
          </select></td>
        </tr>
      </table>

      <p />
      <table cellspacing="0" cellpadding="0" class="form2">
        <tr>
          <td colspan="2" width="50%" class="header"><Strong>Contact
          Attempts</strong></td>
          <td colspan="2" width="50%" class="header"><Strong>Verification</strong>
          </td>
        <tr>
          <td width="25%" cellpadding="0" cellspacing="0" border="0">First
          Contact</td>
          <td width="25%"><input type="text" size="10"
            value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;">
          </td>
          <td rowspan="14"><strong>Content Checks</strong><br />
          <input type="checkbox" />Address<br />
          <input type="checkbox" />Phone<br />
          <input type="checkbox" />Email<br />
          <input type="checkbox" />Bag Color/Type<br />
          <input type="checkbox" />Brand<br />
          <input type="checkbox" />Contents<br />
          <p /><strong>Fraud Checks</strong><br />
          <input type="checkbox" />Credit Card<br />
          <input type="checkbox" />Phone<br />
          <input type="checkbox" />Name<br />
          <p /><strong>Check Trace Results</strong><br />
          <input type="checkbox" />Credit Card<br />
          <input type="checkbox" />Phone<br />
          <input type="checkbox" />Name<br />
          </td>
        </tr>
        <tr>
          <td>Second Contact</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;">
          </td>
        </tr>
        <tr>
          <td colspan="2" class="header"><Strong>PPLC
          Actions</strong></td>
        </tr>
        <tr>
          <td align="center" colspan="2">
<a href='#'
onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.PPLC_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','LostReceipt',800,600);return false;"><bean:message
key="link.print.pplc" /></a>
            
          </td>
        </tr>
        <tr>
          <td>PPLC Sent Date</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
          <td>PPLC Sent Via</td>
          <td><select>
            <option>Email</option>
          </select></td>
        </tr>
        <tr>
          <td>PPLC Due</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
          <td>PPLC Received</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"></td>
        </tr>

        <tr>
          <td colspan="2" class="header"><Strong>Follow-up
          Actions</strong></td>
        </tr>
        <tr>
          <td>Depreciation Due</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"></td>
        </tr>

        <tr>
          <td>Depreciation Complete</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"></td>
        </tr>
        <tr>
          <td>Offer Due</td>
          <td><input type="text" size="10" value="01/01/2009" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="itcalendar0" name="itcalendar0" height="15" width="20"
            border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.claimSettlementForm, 'dateTakeover','itcalendar0','MM/dd/yyyy'); return false;"></td>
        </tr>


      </table>
      <table cellspacing="0" cellpadding="0" class="form2">
        <tr>
          <td>Comments<br />
          <textarea name="comments" cols="70" rows="10"
            onkeydown="textCounter(this,this,255);"
            onkeyup="textCounter(this,this,255);" disabled />
10:22:00 08-16-2008 <jbuser1>
These are comments that have previously been left.

10:22:00 08-16-2008 <jbuser2>
These are comments that have previously been left.
</textarea> <br />
          Add to Comments<br />
          <textarea name="comments2" cols="70" rows="3"
            onkeydown="textCounter(this,this,255);"
            onkeyup="textCounter(this,this,255);" />These are new comments.</textarea>
          </td>
        </tr>
      </table>


      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" valign="top"><br>
          <html:submit property="save" styleId="button">
            <bean:message key="button.save" />
          </html:submit></td>
        </tr>
      </table>
    </html:form>