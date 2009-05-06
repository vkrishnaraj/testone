<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>

<%
  Agent a = (Agent)session.getAttribute("user");
%>


<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>

<script language="javascript">
  


   function deleteAgent (userName,agentId,stationId)
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.delete") %>" + userName + "?"))
    {  
      document.agentForm.stationId.value=stationId;
      document.agentForm.delete1.value="1";
      document.agentForm.agentId.value=agentId;
      document.agentForm.submit();
    }
  }


</script>


<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<script language="javascript">
  
    function goprev() {
      o = document.agentForm;
      o.prevpage.value = "1";
      o.pagination.value="1";
      o.submit();
    }
    
    function gonext() {
      o = document.agentForm;
      o.nextpage.value="1";
      o.pagination.value="1";
      o.submit();
    }
    
    function gopage(i) {
      o = document.agentForm;
      o.currpage.value = i;
      o.pagination.value="1";
      o.submit();
    }
    
    function sortAgents(sortOrder) {
  		o = document.agentForm;
  		o.sort.value = sortOrder;
  		o.submit();
		}
    function updatePagination() {
        return true;
    }

</script>

<html:form action="agentAdmin.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.agentAdmin" />
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
          <bean:message key="header.search_agents" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <br>
        <strong>
          <bean:message key="wildcard" />
        </strong>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
              <bean:message key="header.agentUsername" />
              :
            </td>
            <td colspan="3">
              <html:text property="agentSearchName" size="12" maxlength="10" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentFname" />
              :
            </td>
            <td colspan="3">
              <html:text property="agentSearchFName" size="12" maxlength="10" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="header.agentLname" />
              :
            </td>
            <td colspan="3">
              <html:text property="agentSearchLName" size="12" maxlength="10" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td colspan="4" align="center">
              <br>
              <html:submit styleId="button" property="search">
                <bean:message key="button.search" />
              </html:submit>
              &nbsp;
              <html:reset styleId="button">
                <bean:message key="button.reset" />
              </html:reset>
            </td>
          </tr>
        </table>
        <br>
        <h1 class="green">
          <bean:message key="users" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/administration.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td colspan="9">
              <span class="bold"><bean:message key="companyCodeText" />
                :</span>
              <bean:write name="agentForm" property="companyCode" />
              |
              <span class="bold"><bean:message key="header.stationCode" />
                :</span>
              <logic:present name="statList">
                <html:select name="agentForm" property="station_id" styleClass="dropdown" onchange="submit()">
                  <html:option value="-1">
                    <bean:message key="select.all" />
                  </html:option>
                  <html:options collection="statList" property="value" labelProperty="label" />
                </html:select>
              </logic:present>
              |
              <span class="bold"><bean:message key="header.active" />
                :</span>
                <html:select name="agentForm" property="active" styleClass="dropdown" onchange="submit()">
                  <html:option value="-1">
                    <bean:message key="select.all" />
                  </html:option>
                  <html:option value="true">
                  	<bean:message key="active" />
                  </html:option>
                  <html:option value="false">
                  	<bean:message key="inactive" />
                  </html:option>
                </html:select>
<%
                  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_WEB_SERVICE_AGENTS, a)) {
%>

               |
              <span class="bold"><bean:message key="agent.type" />
                :</span>
                <html:select name="agentForm" property="agentType" styleClass="dropdown" onchange="submit()">
                  <html:option value="<%=TracingConstants.MAINTAIN_AGENTS_TYPE_AGENTS %>">
                  	<bean:message key="users" />
                  </html:option>
                  <html:option value="<%=TracingConstants.MAINTAIN_AGENTS_TYPE_WEBSRVICE %>">
                  	<bean:message key="webservices" />
                  </html:option>
                </html:select>
<%
                  }
%>
            </td>
          </tr>
<%
          String sort = (String)request.getAttribute("sort");

          if (sort != null && sort.length() > 0) {
%>
            <input type=hidden name=sort value='<%= sort %>'>
<%
          } else {
%>
            <input type=hidden name=sort value="">
<%
          }
%>
          <html:hidden name="agentForm" property="companyCode" />
          <tr>
            <td>
              <b><a href="#" onclick="sortAgents('username');"><bean:message key="header.agentUsername" /></a></b>
            </td>
            <td>
              <b><a href="#" onclick="sortAgents('group');"><bean:message key="header.agentGroup" /></a></b>
            </td>
            <td>
              <b><a href="#" onclick="sortAgents('station');"><bean:message key="header.agentStation" /></a></b>
            </td>
            <td>
              <b><a href="#" onclick="sortAgents('lastname');"><bean:message key="header.agentLname" /></a></b>
            </td>
            <td>
              <b><bean:message key="header.agentFname" /></b>
            </td>
            <td>
              <b><bean:message key="header.agentMname" /></b>
            </td>
            <td>
              <b><a href="#" onclick="sortAgents('active');"><bean:message key="header.agentActive" /></a></b>
            </td>
            <td>
              <b><bean:message key="header.deleteAgent" /></b>
            </td>
          </tr>
          <input type="hidden" name="delete1" value="" />
          <input type="hidden" name="stationId" value="" />
          <input type="hidden" name="agentId" value="" />
          <logic:present name="agentList" scope="request">
            <logic:iterate id="agent" name="agentList">
              <tr>
                <td>
                  <A HREF="agentAdmin.do?edit=1&stationId=<bean:write name="agent" property="station.station_ID"/>&agentId=<bean:write name="agent" property="agent_ID"/>"><bean:write name="agent" property="username" /></A>
                </td>
                <td>
                  <bean:write name="agent" property="group.description" />
                </td>
                <td>
                  <bean:write name="agent" property="station.stationcode" />
                </td>
                <td>
                  <bean:write name="agent" property="lastname" />
                </td>
                <td>
                  <bean:write name="agent" property="firstname" />
                </td>
                <td>
                  <logic:empty name="agent" property="mname">
                    &nbsp;
                  </logic:empty>
                  <bean:write name="agent" property="mname" />
                </td>
                <td>
                  <logic:equal name="agent" property="active" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="agent" property="active" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                </td>
                <td>
                  <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteAgent('<bean:write name="agent" property="username"/>',<bean:write name="agent" property="agent_ID"/>,<bean:write name="agent" property="station.station_ID"/>);" id="button">
                </td>
              </tr>
            </logic:iterate>
            
            <tr>
              <td colspan="9">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
              </td>
            </tr>
          </logic:present>
          <tr>
            <td colspan="8">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="9" align="center">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="addNew">
                <bean:message key="button.add.new.agent" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
