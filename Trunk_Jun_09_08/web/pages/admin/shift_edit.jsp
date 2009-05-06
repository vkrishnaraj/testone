<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<html:form action="shiftAdmin.do" method="post" onsubmit="return validateShiftForm(this);">
  <html:javascript formName="shiftForm" />
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.editShift" />
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
          <bean:message key="shift" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/work_with_shifts.htm#edit shift');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <bean:message key="header.shift_code" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:text styleClass="textfield" name="shiftForm" property="shift_code" size="15" maxlength="15" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.shift_description" />
              :
            </td>
            <td>
              <html:text styleClass="textfield" name="shiftForm" property="shift_description" size="50" maxlength="50" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.locale" />
              :
            </td>
            <td>
              <html:select name="shiftForm" property="locale">
                <html:options collection="localelist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.companyCode" />
              :
            </td>
            <td>
              <html:text styleClass="textfield" name="shiftForm" property="companycode_ID" size="20" maxlength="25" readonly="true" />
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
