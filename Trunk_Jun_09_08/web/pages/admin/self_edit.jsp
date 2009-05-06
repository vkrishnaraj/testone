<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<html:form action="agentAdmin.do" method="post" onsubmit="return validateAgentForm(this);">
  <html:javascript formName="agentForm" />
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.editAgent" />
        </h1>
      </div>
      <div id="pageheaderright">
        <table id="pageheaderright">
          <tr>
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
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="agent" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/work_with_agent_profiles.htm#maintain user profile');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color="red">
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <logic:present name="minimalPolicy" scope="request">
            	<bean:message key="error.security.password.minimal"/>
            </logic:present>
            <logic:present name="securePolicy" scope="request">
            	<bean:message key="error.security.password.secure"/>
            </logic:present>
          </font>
          <input type="hidden" name="self" value="1">
          <html:hidden name="agentForm" property="max_ws_sessions" />
          <tr>
            <td>
              <bean:message key="header.agentUsername" />
            </td>
            <td>
              <html:text name="agentForm" property="username" size="12" maxlength="10" styleClass="textfield" readonly="true" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentPassword" />
            </td>
            <td>
              <input id="passwordChanged" type="hidden" name="passwordChanged" value="0">
              <html:password name="agentForm" property="password" size="12" maxlength="18" styleClass="textfield"  onchange="document.getElementById('passwordChanged').value='1';"/>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentConfirmPassword" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:password name="agentForm" property="password2" size="12" maxlength="20" styleClass="textfield"  onchange="document.getElementById('passwordChanged').value='1';"/>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.companyCode" />
            </td>
            <td>
              <html:text name="agentForm" property="companyCode" size="5" maxlength="3" disabled="true" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentStation" />
            </td>
            <td>
              <html:select name="agentForm" property="station_id" styleClass="dropdown" disabled="true">
                <html:options collection="statList" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentGroup" />
            </td>
            <td>
              <html:select name="agentForm" property="group_id" styleClass="dropdown" disabled="true">
                <html:options collection="grouplist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentFname" />
            </td>
            <td>
              <html:text name="agentForm" property="fname" size="12" maxlength="30" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentMname" />
            </td>
            <td>
              <html:text name="agentForm" property="mname" size="12" maxlength="30" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentLname" />
            </td>
            <td>
              <html:text name="agentForm" property="lname" size="12" maxlength="30" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentTimeout" />
            </td>
            <td>
              <html:text name="agentForm" property="timeout" size="4" maxlength="3" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentActive" />
            </td>
            <td>
              <html:select name="agentForm" property="active" styleClass="dropdown" disabled="true">
                <html:option value="true">
                  <bean:message key="select.yes" />
                </html:option>
                <html:option value="false">
                  <bean:message key="select.no" />
                </html:option>
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentDefaultTimezone" />
            </td>
            <td>
              <html:select name="agentForm" property="defaultTimezone" styleClass="dropdown">
                <html:options collection="timezones" property="id" labelProperty="description" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentCurrentTimezone" />
            </td>
            <td>
              <html:select name="agentForm" property="currentTimezone" styleClass="dropdown">
                <html:options collection="timezones" property="id" labelProperty="description" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentDefaultLocale" />
            </td>
            <td>
              <html:select name="agentForm" property="defLocale" styleClass="dropdown">
                <html:options collection="localelist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentCurrLocale" />
            </td>
            <td>
              <html:select name="agentForm" property="currLocale" onchange="submit()" styleClass="dropdown">
                <html:options collection="localelist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentDefCurrency" />
            </td>
            <td>
              <html:select name="agentForm" property="defCurrency" styleClass="dropdown">
                <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentDateformat" />
            </td>
            <td>
              <html:select name="agentForm" property="dateFormat" styleClass="dropdown">
                <html:options collection="dateformatlist" property="dateformat_ID" labelProperty="format" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentTimeformat" />
            </td>
            <td>
              <html:select name="agentForm" property="timeFormat" styleClass="dropdown">
                <html:options collection="timeformatlist" property="timeformat_ID" labelProperty="format" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.shift_description" />
            </td>
            <td>
              <logic:present name="shiftList" scope="request">
                <html:select name="agentForm" property="shift_id" styleClass="dropdown" disabled="true">
                  <html:option value="">
                    <bean:message key="select.none" />
                  </html:option>
                  <html:options collection="shiftList" property="shift_id" labelProperty="shift_description" />
                </html:select>
              </logic:present>
            </td>
          </tr>
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <center><INPUT type="button" Id="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="save">
                <bean:message key="button.save" />
              </html:submit></center>
            </td>
          </tr>
        </table>
      </html:form>
