<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.forms.MaintainCompanyForm"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>

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
	              <bean:message key="colname.wt_enabled" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            
	            <td>
	            <html:select name="companyForm" property="wt_enabled" styleClass="dropdown">
                  <html:option value="1">
                  	<bean:message key="select.yes" />
                  </html:option>
                  <html:option value="0">
                  	<bean:message key="select.no" />
                  </html:option>
                </html:select>
                
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.wt_write_enabled" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            
	            <td>
	            <html:select name="companyForm" property="wt_write_enabled" styleClass="dropdown">
                  <html:option value="1">
                  	<bean:message key="select.yes" />
                  </html:option>
                  <html:option value="0">
                  	<bean:message key="select.no" />
                  </html:option>
                </html:select>
                
	            </td>
	          </tr>
              <tr>
	            <td>
	              <bean:message key="colname.wt_url" />

	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="wt_url" size="30"  />
	              </br>
	                   (Default: <%= TracingConstants.DEFAULT_WT_URL %>)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <html:hidden name="companyForm" property="companyCode" />
	              <bean:message key="colname.wt_airlinecode" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="wt_airlinecode" size="10" maxlength="10" />
	         
	            </td>
	          </tr>

          	  <tr>
	            <td>
	              <html:hidden name="companyForm" property="companyCode" />
	              <bean:message key="colname.mbr_to_wt_days" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="mbr_to_wt_days" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          	  <tr>
	            <td>
	              <html:hidden name="companyForm" property="companyCode" />
	              <bean:message key="colname.oal_inc_hours" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="oal_inc_hours" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.ohd_to_wt_hours" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="ohd_to_wt_hours" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	                    	  <tr>
	            <td>
	              <html:hidden name="companyForm" property="companyCode" />
	              <bean:message key="colname.oal_ohd_hours" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="oal_ohd_hours" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          
	          
	                    <tr>
	            <td>
	              <bean:message key="colname.wt_auto_amend" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	            <html:select name="companyForm" property="auto_wt_amend" styleClass="dropdown">
                  <html:option value="true">
                  	<bean:message key="select.on" />
                  </html:option>
                  <html:option value="false">
                  	<bean:message key="select.off" />
                  </html:option>
                </html:select>
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.wt_user" />
	              <font color=red>
	              *
	              </font>
	              :
	            </td>
	            <td>
	              <html:password styleClass="textfield" name="companyForm" property="wt_user" size="15" maxlength="15" />
	            </td>  
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.wt_pass" />
	              <font color=red>
	              *
	              </font>
	              :
	            </td>
	            <td>
	              <html:password styleClass="textfield" name="companyForm" property="wt_pass" size="15" maxlength="15" />
	            </td>  
	          </tr>
	      <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
        </table>
        <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_WT_OTHER_CARRIER, ((Agent)session.getAttribute("user")))){ %>
		<h1>
              <bean:message key="colname.wt_carriers"/>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
	      <tr>
            <td colspan="2">
              <bean:message key="colname.wt_carriers.info"/>
            </td>
          </tr>
	      <tr>
            <td colspan="2">
              <table class="form2" cellspacing="0" cellpadding="0">
              <% String removeCarrierAction="/companyAdmin.do?edit=1&companyCode="+((Agent)session.getAttribute("user")).getCompanycode_ID(); %>
   				
              <logic:iterate name="companyForm" property="wtCompanyList" id="wtcomp">
                   <tr>
	              	<td><bean:write name="wtcomp" property="wtCompanyCode"/> - <bean:write name="wtcomp" property="companyName"/>
	              	</td>
	              	<td>
	              		<html:link action="<%=removeCarrierAction %>" indexed="true" paramId="removeCarrier" scope="request"><bean:message key="wt.carrier.remove" /></html:link>
	            	</td>
	              </tr>
              </logic:iterate>
              <tr>
              	<td>
              		<html:select name="companyForm" property="selectedCarrier">
              			<html:options collection="wtCompanylistByName" property="companyCode_ID" labelProperty="companydesc"/>
              		</html:select>
              	</td>
              	<td>
   					<html:submit styleId="button" property="addCarrier"><bean:message key="wt.carrier.add" /></html:submit>
              	</td>
              </tr>
              </table>
            </td>
          </tr>
          
          </table>
        <% } %>
        
        <table class="form2" cellspacing="0" cellpadding="0">
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
	          