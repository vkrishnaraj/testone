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
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
    <!--
  var cal1xx = new CalendarPopup(); 

    // -->
  </SCRIPT>
  <jsp:include page="/pages/includes/validation_incl.jsp" />
<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd><a href='searchIncident.do?incident='><span class="aa">&nbsp;
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

    <dd><a href="#"><span class="aab">&nbsp; <br />
    &nbsp;</span> <span class="bbb"><bean:message
      key="menu.claim_customer" /></span> <span class="ccb">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=3"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_baggage" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=4"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_summary" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

  </dl>



  <!-- END ICONS MENU -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn"><!-- MAIN BODY -->
    <div id="maincontent"><html:form action="claim_settlement.do"
      method="post">

      <h1 class="green">Customer Information</h1>

      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="2"><bean:message
            key="claimsettlement.lastName" /><br />
          <html:text name="claimSettlementForm" property="lastName"
            size="15" maxlength="20" styleClass="textfield" /></td>
          <td colspan="2"><bean:message
            key="claimsettlement.firstName" /><br />
          <html:text name="claimSettlementForm" property="firstName"
            size="15" maxlength="20" styleClass="textfield" /></td>
          <td><bean:message key="claimsettlement.salutation" /><br />
          <html:select name="claimSettlementForm" property="salutation"
            styleClass="dropdown">
            <html:option value="0">
              <bean:message key="select.please_select" />
            </html:option>
            <html:option value="1">
              <bean:message key="select.dr" />
            </html:option>
            <html:option value="2">
              <bean:message key="select.mr" />
            </html:option>
            <html:option value="3">
              <bean:message key="select.ms" />
            </html:option>
            <html:option value="4">
              <bean:message key="select.miss" />
            </html:option>
            <html:option value="5">
              <bean:message key="select.mrs" />
            </html:option>
            <html:option value="6">
              <bean:message key="select.other" />
            </html:option>
          </html:select></td>
        </tr>

        <tr>
          <td width=33% colspan="2"><bean:message
            key="claimsettlement.address1" /><br />
          <html:text name="claimSettlementForm" property="address1"
            size="25" maxlength="50" styleClass="textfield" /></td>
          <td colspan="2"><bean:message
            key="claimsettlement.address2" /><br />
          <html:text
            name="claimSettlementForm" property="address2" size="25"
            maxlength="50"  styleClass="textfield" /></td>
          <td><bean:message key="claimsettlement.language" /><br />
          <html:select name="claimSettlementForm" property="salutation"
            styleClass="dropdown">
            <html:option value="0">
              <bean:message key="select.please_select" />
            </html:option>
          </html:select></td>
        </tr>
        <tr>
          <td><bean:message key="claimsettlement.city" /> <br>
          <html:text name="claimSettlementForm" property="city" size="15" maxlength="50"  styleClass="textfield" /></td>
          <td><bean:message key="claimsettlement.stateProvince" />
          <br />
          <html:select name="claimSettlementForm"
            property="state_ID" styleClass="dropdown"
            onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
            <html:option value="">
              <bean:message key="select.none" />
            </html:option>
            <html:options collection="statelist" property="value"
              labelProperty="label" />
          </html:select></td>
          <td><bean:message key="claimsettlement.province" /> <br />

          <logic:equal name="claimSettlementForm"
            property="countrycode_ID" value="US">
            <html:text
              property="province"
              size="15" maxlength="100" styleClass="disabledtextfield"
              disabled="true" />
          </logic:equal>
          
          <logic:equal name="claimSettlementForm"
            property="countrycode_ID" value="">
            <html:text
              property="province"
              size="15" maxlength="100" styleClass="textfield" />
          </logic:equal>
          
          <logic:notEqual name="claimSettlementForm"
            property="countrycode_ID" value="">
            <logic:notEqual name="claimSettlementForm"
              property="countrycode_ID" value="US">
              <html:text
                property="province"
                size="15" maxlength="100" styleClass="textfield" />
            </logic:notEqual>
          </logic:notEqual></td>
          <td><bean:message key="claimsettlement.zip" /><br>
          <html:text name="claimSettlementForm" property="zip" size="15" maxlength="9"  styleClass="textfield" /></td>
          
          <td><bean:message key="claimsettlement.country" /><br>
  
          <html:select property="countrycode_ID" styleClass="dropdown" onchange="checkstate(this,this.form,'state_ID', 'province');">
            <html:option value="">
              <bean:message key="select.none" />
            </html:option>
            <html:options name="claimSettlementForm" collection="countrylist" property="value" labelProperty="label" />
          </html:select>
        </td>
        </tr>


        <tr>
          <td><bean:message key="claimsettlement.home" /><br>
          <html:text name="claimSettlementForm" property="homePhone" size="15" maxlength="25"  styleClass="textfield" />
          </td>
          <td><bean:message key="claimsettlement.business" /><br>
          <html:text name="claimSettlementForm" property="businessPhone" size="15" maxlength="25"  styleClass="textfield" />
          </td>
          <td><bean:message key="claimsettlement.mobile" /><br>
          <html:text name="claimSettlementForm" property="mobilePhone" size="15" maxlength="25"  styleClass="textfield" />
          </td>
          <td><bean:message key="claimsettlement.pager" /><br>
          <html:text name="claimSettlementForm" property="pager" size="15" maxlength="25"  styleClass="textfield" />
          </td>
          <td><bean:message key="claimsettlement.fax" /><br>
          <html:text name="claimSettlementForm" property="fax" size="15" maxlength="25"  styleClass="textfield" />
          </td>
        </tr>

        <tr>
          <td colspan="3"><bean:message key="claimsettlement.emailAddress" /><br />
          <html:text name="claimSettlementForm" property="email" size="40" maxlength="50" styleClass="textfield" />
          </td>
          <td colspan="2"><bean:message key="claimsettlement.membership" /><br />
          <html:text name="claimSettlementForm" property="membership" size="15" maxlength="15" styleClass="textfield" />
          </td>

        </tr>
      </table>



      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" valign="top"><br>
          <html:submit property="save2" styleId="button">
            <bean:message key="button.save" />
          </html:submit></td>
        </tr>
      </table>
      <p />
    </html:form>