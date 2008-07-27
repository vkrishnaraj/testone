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
	              <bean:message key="colname.enable_ohd_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_ohd" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_ohd" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_ohd" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_lost_found_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_lost_found" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_lost_found" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_lost_found" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_lost_delayed_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_lost_delayed" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_lost_delayed" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_lost_delayed" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_damaged_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_damaged" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_damaged" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_damaged" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_missing_articles_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_missing_articles" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_missing_articles" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_missing_articles" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_claims_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_claims" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_claims" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_claims" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_agent_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_agent" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_agent" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_agent" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_group_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_group" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_group" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_group" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_company_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_company" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_company" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_company" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_shift_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_shift" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_shift" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_shift" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_station_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_station" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_station" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_station" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_losscode_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_loss_codes" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_loss_codes" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_loss_codes" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.enable_airport_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_airport" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_airport" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_airport" value="0">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.no" />
	              </select>
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.enable_delivery_company_auditing" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <select name="audit_delivery_companies" style="dropdown">
	                <option value="1"
	                <logic:equal name="companyForm" property="audit_delivery_companies" value="1">
	                  selected="selected"
	                </logic:equal>
	                >
	                <bean:message key="select.yes" />
	                <option value="0"
	                <logic:equal name="companyForm" property="audit_delivery_companies" value="0">
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
	          