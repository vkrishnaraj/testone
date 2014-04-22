<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>

<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>

<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Company" %>

<% 
Agent a = (Agent) session.getAttribute("user");
ResourceBundle bundle = ResourceBundle.getBundle(
		"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
Company c = com.bagnet.nettracer.tracing.bmo.CompanyBMO.getCompany(a.getCompanycode_ID());
String passSecure = bundle.getString("error.security.password.secure").replace("{0}","" + (c!=null?c.getVariable().getMin_pass_size():8));
String passMin = bundle.getString("error.security.password.minimal").replace("{0}","" + (c!=null?c.getVariable().getMin_pass_size():8));
%>



<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
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
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color="red">
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <logic:present name="minimalPolicy" scope="request">
		            	<%=passMin%>
            </logic:present>
            <logic:present name="securePolicy" scope="request">
		         		<%=passSecure%>
            </logic:present>
          </font>
          <tr>
            <td>
              <bean:message key="header.agentUsername" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
<%
              String aNew = (String)request.getAttribute("aNew");
%>
<%
              if (aNew != null && aNew.length() > 0) {
%>
                <input type="hidden" name="aNew" value="1">
                <html:text name="agentForm" property="username" size="12" maxlength="10" styleClass="textfield" />
<%
              } else {
%>
                <html:text name="agentForm" property="username" size="12" maxlength="10" readonly="true" styleClass="textfield" />
<%
              }
%>
            </td>
          </tr>
          <logic:equal name="agentForm" property="accountLocked" value="true">
          <tr>
            <td>
              <bean:message key="header.accountLocked" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="accountLocked" styleClass="dropdown">
                <html:option value="true">
                  <bean:message key="select.yes" />
                </html:option>
                <html:option value="false">
                  <bean:message key="select.no" />
                </html:option>
              </html:select>
            </td>
          </tr>
          </logic:equal>
          <tr>
            <td>
              <input id="passwordChanged" type="hidden" name="passwordChanged" value="0">
              <bean:message key="header.agentPassword" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:password name="agentForm" property="password" size="12\" autocomplete=\"off"  maxlength="18" styleClass="textfield" onchange="document.getElementById('passwordChanged').value='1'; document.getElementsByName('resetPassword')[0].options[0].selected=true;"/>
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
              <html:password name="agentForm" property="password2" size="12" maxlength="18" styleClass="textfield" onchange="document.getElementById('passwordChanged').value='1'; document.getElementsByName('resetPassword')[0].options[0].selected=true;"/>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.resetPassword" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="resetPassword" styleClass="dropdown">
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
              <bean:message key="header.companyCode" />
              :
            </td>
            <td>
              <html:text name="agentForm" property="companyCode" size="5" maxlength="3" readonly="true" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentSubcompany" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
            	<% if(a.getSubcompany()!=null){ %>
       			<html:select name="agentForm" property="subcompany_id" disabled="true" styleClass="dropdown">
               	 	<html:options collection="subcompList" property="value" labelProperty="label" />
              	</html:select>
         		<% } else { %>
         		<html:select name="agentForm" property="subcompany_id" styleClass="dropdown">
                	<html:options collection="subcompList" property="value" labelProperty="label" />
             	</html:select>
         		<% } %>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentStation" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="station_id" styleClass="dropdown">
                <html:options collection="statList" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentGroup" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="group_id" styleClass="dropdown">
				<html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="grouplist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentFname" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:text name="agentForm" property="fname" size="12" maxlength="20" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentMname" />
              :
            </td>
            <td>
              <html:text name="agentForm" property="mname" size="12" maxlength="20" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentLname" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:text name="agentForm" property="lname" size="12" maxlength="20" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentTimeout" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:text name="agentForm" property="timeout" size="4" maxlength="3" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentActive" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="active" styleClass="dropdown">
                <html:option value="true">
                  <bean:message key="select.yes" />
                </html:option>
                <html:option value="false">
                  <bean:message key="select.no" />
                </html:option>
              </html:select>
            </td>
          </tr>
<%
          if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_WEB_SERVICE_AGENTS, a)) {
%>
          <tr>
            <td>
              <bean:message key="header.agentWebEnabled" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="web_enabled" styleClass="dropdown">
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
              <bean:message key="header.agentWsEnabled" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="ws_enabled" styleClass="dropdown">
                <html:option value="false">
                  <bean:message key="select.no" />
                </html:option>
                <html:option value="true">
                  <bean:message key="select.yes" />
                </html:option>
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentMaxWsSessions" />
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:text name="agentForm" property="max_ws_sessions" size="4" maxlength="3" styleClass="textfield" />
            </td>
          </tr>
<%
          }
          else {
          	
%>          
		<html:hidden name="agentForm" property="max_ws_sessions"/>
<%
		}
%>
          <tr>
            <td>
              <bean:message key="header.agentDefaultTimezone" />
              <font color=red>
                *
              </font>
              :
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
              <font color=red>
                *
              </font>
              :
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
              <font color=red>
                *
              </font>
              :
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
              <font color=red>
                *
              </font>
              :
            </td>
            <td>
              <html:select name="agentForm" property="currLocale" styleClass="dropdown">
                <html:options collection="localelist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentDefCurrency" />
              <font color=red>
                *
              </font>
              :
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
              <font color=red>
                *
              </font>
              :
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
              <font color=red>
                *
              </font>
              :
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
              :
            </td>
            <td>
              <logic:present name="shiftList" scope="request">
                <html:select name="agentForm" property="shift_id" styleClass="dropdown">
                  <html:option value="">
                    <bean:message key="select.none" />
                  </html:option>
                  <html:options collection="shiftList" property="shift_id" labelProperty="shift_description" />
                </html:select>
              </logic:present>
            </td>
          </tr>

		<%
          if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UNASSIGNED_INBOUND_QUEUE, a)) {
		%>
          <tr>
            <td>
              <bean:message key="header.agentInboundQueue" />
              :
            </td>
            <td>
              <html:select name="agentForm" property="inboundQueue" styleClass="dropdown" styleId="inboundQueue" onchange="displayUnassignedTasks(this.value)">
                <html:option value="true" >
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
            </td>
            <td>
	            <table  border="0" id="unassigned_tasks">
				    <tr>
				        <td width="15%">
				        	<bean:message key="colname.unassignedinbound.load" /><br>
				       		<html:text name="agentForm" property="loadpercentage" size="5" maxlength="5" styleClass="textfield" onblur="validateLoad(this.value)" />
				        </td>
				        
				        <td width="15%">
				        	<bean:message key="colname.unassignedinbound.inbound" /><br>
				            <html:checkbox name="agentForm" property="inbound"/>
				        </td>
				        <td width="15%">
				        	<bean:message key="colname.unassignedinbound.acaa" /><br>
				            <html:checkbox name="agentForm" property="acaa"/>
				        </td>
				        <td width="15%">
				        	<bean:message key="colname.unassignedinbound.damaged" /><br>
				            <html:checkbox name="agentForm" property="damaged"/>
				        </td>
				    </tr>
				</table>
            </td>
          </tr>
  			<% } %>        
          
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2">
              <center><INPUT type="button" Id="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="save" onclick="return validateStation();">
                <bean:message key="button.save" />
              </html:submit></center>
            </td>
          </tr>
        </table>
      </html:form>
  <script language="javascript">
    

	function validateStation() {
		var element = (document.getElementsByName('station_id'))[0];
		if (element.value != '') {
		} else {
			alert("<bean:message key="agent.save.nostation" />");
			element.focus();
			return false;
		}
		element = null;
		element = (document.getElementsByName('group_id'))[0];
		if (element.value != '') {
		} else {
			alert("<bean:message key="agent.save.nogroup" />");
			element.focus();
			return false;
		}
		return true;
	}
	
	function validateLoad(val) {
		if (!isNaN(val)) {
		} else {
			alert("<bean:message key="agent.save.invalidload" />");
			loadelement.focus();
			return false;
		}		
		return true;
	}

	
	function displayUnassignedTasks(val){
		if(val=="true"){
			document.getElementById("unassigned_tasks").style.display="inline";
		} else {
			document.getElementById("unassigned_tasks").style.display="none";
		}					
	}
	displayUnassignedTasks("<bean:write property="inboundQueue" name="agentForm"/>");
	</script>
      