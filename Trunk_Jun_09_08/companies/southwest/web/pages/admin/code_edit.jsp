<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<html:form action="codeAdmin.do" method="post" onsubmit="return validateCodeForm(this);">
  <html:javascript formName="codeForm" />
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.editCode" />
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
          <bean:message key="losscode" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_loss_codes.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <td>
              <bean:message key="header.code_number" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <logic:notEmpty name="codeForm" property="loss_code">
                <html:text disabled="true" styleClass="textfield" name="codeForm" property="loss_code" size="3" maxlength="2" />
                <input type="hidden" name="code_id" value='<bean:write name="codeForm" property="code_id"/>'>
                <input type="hidden" name="loss_code" value='<bean:write name="codeForm" property="loss_code"/>'>
              </logic:notEmpty>
              <logic:empty name="codeForm" property="loss_code">
                <html:text styleClass="textfield" name="codeForm" property="loss_code" size="3" maxlength="2" />
              </logic:empty>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.report_type" />
              :
            </td>
            <td>
              <logic:notEmpty name="codeForm" property="loss_code">
                <html:select name="codeForm" property="report_type" disabled="true">
                  <html:options collection="reportTypes" property="itemType_ID" labelProperty="description" />
                </html:select>
                <input type="hidden" name="report_type" value='<bean:write name="codeForm" property="report_type"/>'>
              </logic:notEmpty>
              <logic:empty name="codeForm" property="loss_code">
                <html:select name="codeForm" property="report_type">
                  <html:options collection="reportTypes" property="itemType_ID" labelProperty="description" />
                </html:select>
              </logic:empty>
            </td>
          </tr>
          <td>
            <bean:message key="header.code_description" />
            <font color=red>
              *
            </font>
            :
          </td>
          <td>
            <html:text styleClass="textfield" name="codeForm" property="description" size="50" maxlength="150" />
          </td>
        </tr>
          </tr>
          <td>
            <bean:message key="header.visible_to_limited_users" />
            <font color=red>
              *
            </font>
            :
          </td>
          <td>
            <html:checkbox property="visibleToLimited" value="1"></html:checkbox>
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.locale" />
            :
          </td>
          <td>
            <logic:notEmpty name="codeForm" property="loss_code">
              <html:select name="codeForm" property="locale" disabled="true">
                <html:options collection="localelist" property="value" labelProperty="label" />
              </html:select>
              <input type="hidden" name="locale" value='<bean:write name="codeForm" property="locale"/>'>
            </logic:notEmpty>
            <logic:empty name="codeForm" property="loss_code">
              <html:select name="codeForm" property="locale">
                <html:options collection="localelist" property="value" labelProperty="label" />
              </html:select>
            </logic:empty>
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.companyCode" />
            :
          </td>
          <td>
            <html:text styleClass="textfield" name="codeForm" property="companycode_ID" size="4" maxlength="4" disabled="true" />
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.active" />
            :
          </td>
          <td>
            <html:checkbox property="active" value="1"></html:checkbox>
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.controllable" />
            :
          </td>
          <td>
            <html:checkbox property="controllable" value="1"></html:checkbox>
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.departStation" />
            :
          </td>
          <td>
            <html:checkbox property="departStation" value="1"></html:checkbox>
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.transferStation" />
            :
          </td>
          <td>
            <html:checkbox property="transferStation" value="1"></html:checkbox>
          </td>
        </tr>
        <tr>
          <td>
            <bean:message key="header.destinationStation" />
            :
          </td>
          <td>
            <html:checkbox property="destinationStation" value="1"></html:checkbox>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            &nbsp;
          </td>
        </tr>
        <tr>
          <td align="center" colspan="2">
            <INPUT type="button" Id="button" value="Back" onClick="history.back()">
            &nbsp;
            <html:submit styleId="button" property="save">
              <bean:message key="button.save" />
            </html:submit>
          </td>
        </tr>
      </table>
    </html:form>
