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
	              <bean:message key="header.companyCode" />
	              :
	            </td>
	            <td>
	              <logic:notEqual name="companyForm" property="companyCode" value="">
	                <html:text styleClass="textfield" name="companyForm" property="companyCode" size="5" maxlength="3" readonly="true" />
	              </logic:notEqual>
	              <logic:equal name="companyForm" property="companyCode" value="">
	                <html:text styleClass="textfield" name="companyForm" property="companyCode" size="5" maxlength="3" />
	              </logic:equal>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="header.companyDesc" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="companyDesc" size="50" maxlength="255" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.street_addr" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="addr1" size="50" maxlength="100" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.street_addr" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="addr2" size="50" maxlength="100" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.city" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="city" size="20" maxlength="100" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.state" />
	              :
	            </td>
	            <td>
	              <html:select styleClass="dropdown" name="companyForm" property="state_ID">
	                <html:option value="">
	                  <bean:message key="select.none" />
	                </html:option>
	                <html:options collection="statelist" property="value" labelProperty="label" />
	              </html:select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.country" />
	              :
	            </td>
	            <td>
	              <html:select styleClass="dropdown" name="companyForm" property="countrycode_ID">
	                <html:option value="">
	                  <bean:message key="select.none" />
	                </html:option>
	                <html:options collection="countrylist" property="value" labelProperty="label" />
	              </html:select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.zip" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="zip" size="12" maxlength="11" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.phone" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="phone" size="20" maxlength="25" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.email" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_address" size="50" maxlength="255" />
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
