<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.forms.MaintainCompanyForm"%>

        <h1 class="green">
          <bean:message key="Company" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          <input type="hidden" name="pageState" value="<%=request.getAttribute("pageState") %>" />
        </h1>
        
        
        <logic:present name="saved" scope="request">
			<p align="center">
            	<font color="green"><bean:message key="prompt.company_saved" /></font>
  			</p>
        </logic:present>
          
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          	          <tr>
	            <td>
	              <html:hidden name="companyForm" property="companyCode" />
	              <bean:message key="colname.pass_expire_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="pass_expire_days" size="4" maxlength="4" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.account_lockout" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="account_lockout" size="4" maxlength="4" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.secure_pw_policy" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="secure_password" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="secure_password" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="secure_password" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
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
	          