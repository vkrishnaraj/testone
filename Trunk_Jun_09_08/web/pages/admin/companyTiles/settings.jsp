<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.forms.MaintainCompanyForm"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>

<%
  Agent  user = (Agent)session.getAttribute("user");
%>


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
	              <bean:message key="colname.total_threads" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="total_threads" size="4" maxlength="4" />
	              (Default: 10)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.seconds_wait" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="seconds_wait" size="4" maxlength="4" />
	              (Default: 0)
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.min_match_percent" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="min_match_percent" size="4" maxlength="4" />
	              (Default: 50)
	            </td>
	          </tr>
	          <logic:notEmpty name="stationList" scope="request">
	            <tr>
	              <td>
	                <bean:message key="colname.default_station_code" />
	                :
	              </td>
	              <td>
	                <html:select name="companyForm" property="default_station_code" styleClass="dropdown">
	                  <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
	                </html:select>
	              </td>
	            </tr>
	          </logic:notEmpty>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.default_loss_code" />
	              :
	            </td>
	            <td>
	              
			<html:select name="companyForm" property="default_loss_code" styleClass="dropdown">
	      <html:option value="0">
	        <bean:message key="select.please_select" />
	      </html:option>
	      <html:options collection="losscodes" property="loss_code" labelProperty="combination" />
	    </html:select>
	    
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.email_customer" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:select name="companyForm" property="email_customer" styleClass="dropdown">
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
	              <bean:message key="colname.email_host" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_host" size="30" maxlength="100" />
	              (Default: localhost)
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.email_port" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_port" size="4" maxlength="10" />
	              (Default: 25)
	            </td>
	          </tr>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.email_from" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_from" size="30" maxlength="100" />
	            </td>
	          </tr>
	         	<tr>
	            <td>
	              <bean:message key="colname.email_to" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="email_to" size="30" maxlength="255" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.blind_email" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="blindEmail" size="30" maxlength="255" />
	            </td>
	          </tr>
	           
	          <tr>
	            <td>
	              <bean:message key="colname.max_image_file_size" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="max_image_file_size" size="5" maxlength="5" />
	            </td>
	    </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.autoclose" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:select name="companyForm" property="autoCloseOhd" style="dropdown">
 					<html:option value="true">
                  		<bean:message key="select.yes" />
                  	</html:option>
                  	<html:option value="false">
                  		<bean:message key="select.no" />
                  	</html:option>
                  </html:select>
	            </td>
	          </tr>
            <jsp:include page="/pages/includes/payment_minumums_incl.jsp" />
            <%
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SCANNER_DATA, user)) {
            %>
	          <tr>
	            <td>
	              <bean:message key="colname.default.scanner.back" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="scannerDefaultBack" size="4" maxlength="4" />
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.default.scanner.forward" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="scannerDefaultForward" size="4" maxlength="4" />
	            </td>
	          </tr>
            <%
            }
	        %>
	        
	          <tr>
	            <td>
	              <bean:message key="colname.auto.close.days.back" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="auto_close_days_back" size="4" maxlength="4" />
	            </td>
	          </tr>

	          <logic:notEmpty name="fullStationList" scope="request">
	            <tr>
	              <td>
	                <bean:message key="colname.auto.close.ld.station" />
	                :
	              </td>
	              <td>
	                <html:select name="companyForm" property="auto_close_ld_station" styleClass="dropdown">
	                  <html:options collection="fullStationList" property="station_ID" labelProperty="stationcode" />
	                </html:select>
	              </td>
	            </tr>
	          </logic:notEmpty>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.auto.close.ld.code" />
	              :
	            </td>
	            <td>
	              
			<html:select name="companyForm" property="auto_close_ld_code" styleClass="dropdown">
	      <html:option value="0">
	        <bean:message key="select.please_select" />
	      </html:option>
	      <html:options collection="losscodes" property="loss_code" labelProperty="combination" />
	    </html:select>
	    
	            </td>
	          </tr>

	          <logic:notEmpty name="fullStationList" scope="request">
	            <tr>
	              <td>
	                <bean:message key="colname.auto.close.dam.station" />
	                :
	              </td>
	              <td>
	                <html:select name="companyForm" property="auto_close_dam_station" styleClass="dropdown">
	                  <html:options collection="fullStationList" property="station_ID" labelProperty="stationcode" />
	                </html:select>
	              </td>
	            </tr>
	          </logic:notEmpty>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.auto.close.dam.code" />
	              :
	            </td>
	            <td>
	              
			<html:select name="companyForm" property="auto_close_dam_code" styleClass="dropdown">
	      <html:option value="0">
	        <bean:message key="select.please_select" />
	      </html:option>
	      <html:options collection="losscodes_dam" property="loss_code" labelProperty="combination" />
	    </html:select>
	    
	            </td>
	          </tr>
	          <tr>
	            <td>
	              <bean:message key="colname.auto.close.ohd.days.back" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="auto_close_ohd_days_back" size="4" maxlength="4" />
	            </td>
	          </tr>

	          <logic:notEmpty name="fullStationList" scope="request">
	            <tr>
	              <td>
	                <bean:message key="colname.auto.close.pil.station" />
	                :
	              </td>
	              <td>
	                <html:select name="companyForm" property="auto_close_pil_station" styleClass="dropdown">
	                  <html:options collection="fullStationList" property="station_ID" labelProperty="stationcode" />
	                </html:select>
	              </td>
	            </tr>
	          </logic:notEmpty>
	          
	          <tr>
	            <td>
	              <bean:message key="colname.auto.close.pil.code" />
	              :
	            </td>
	            <td>
	              
			<html:select name="companyForm" property="auto_close_pil_code" styleClass="dropdown">
	      <html:option value="0">
	        <bean:message key="select.please_select" />
	      </html:option>
	      <html:options collection="losscodes_pil" property="loss_code" labelProperty="combination" />
	    </html:select>
	    
	            </td>
	          </tr>
	          
	         <tr>
	            <td>
	              <bean:message key="colname.incident.lock.mins" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="incident_lock_mins" size="4" maxlength="4" />
	            </td>
	        </tr>
            <tr>
	            <td>
	              <bean:message key="colname.pnrlastxdays" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="pnrlastxdays" size="4" maxlength="4" />
	            </td>
            </tr>
            <tr>
	            <td>
	              <bean:message key="colname.issuance.item.last.x.days" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="issuanceitemxdaysback" size="4" maxlength="4" />
	            </td>
            </tr>
            <tr>
	            <td>
	              <bean:message key="colname.fraud.review" />
	              :
	            </td>
	              
	            <td>
	              <html:select name="companyForm" property="fraudReview" styleClass="dropdown">
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
	              <bean:message key="colname.bagdrop.autorefresh.mins" />
	              :
	            </td>
	            <td>
	              <html:text styleClass="textfield" name="companyForm" property="bagdrop_autorefresh_mins" size="4" maxlength="4" />
	            </td>
            </tr>
	          
		    <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <p align="center">
              	<INPUT type="button" Id="button" value="Back" onClick="history.back()">
	              &nbsp;
	              <html:submit styleId="button" property="save">
	                <bean:message key="button.save" />
	              </html:submit>
              </p>
            </td>
          </tr>



        </table>
	          
