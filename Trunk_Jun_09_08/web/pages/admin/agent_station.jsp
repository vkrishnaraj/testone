<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    
function goprev() {
  o = document.stationForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.stationForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.stationForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}

function sortAgents(sortOrder) {
	o = document.stationForm;
	o.sort.value = sortOrder;
	o.submit();
}
function updatePagination() {
    return true;
}

  </script>
  <html:form action="stationAdmin.do" method="post">

    <html:hidden name="stationForm" property="stationId" />
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.editStation" />
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
            <span class="bold"><bean:message key="AddingToStation" />
              :</span>
            <bean:write name="stationForm" property="stationCode" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_stations.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td colspan="10">
                <span class="bold"><bean:message key="companyCodeText" />
                  :</span>
                <bean:write name="stationForm" property="companyCode" />
                |
                <span class="bold"><bean:message key="header.stationCode" />
                  :</span>
                <logic:present name="stationList">
                  <html:select styleClass="dropdown" name="stationForm" property="station_id" onchange="submit()">
                    <html:option value="-1">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </logic:present>
                <logic:present name="stationForm" property="stationId">
                  <html:hidden name="stationForm" property="stationId" />
                </logic:present>
              </td>
            </tr>
            <input type="hidden" name="addNewAgent" value="1">
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
            <logic:present name="agentList" scope="request">
              <tr>
                <td width="5%">
                  &nbsp;
                </td>
                <td>
                  <b><a href="#" onclick="sortAgents('username');"><bean:message key="header.agentUsername" /></a></b>
                </td>
                <td>
                  <b><a href="#" onclick="sortAgents('station');"><bean:message key="header.agentStation" /></a></b>
                </td>
                <td>
                  <b><a href="#" onclick="sortAgents('group');"><bean:message key="header.agentGroup" /></a></b>
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
              </tr>
              <logic:iterate id="agent" name="agentList" type="com.bagnet.nettracer.tracing.db.Agent">
                <tr>
                  <td width="5%">
                    <INPUT TYPE=CHECKBOX NAME="agent_ID" value="<bean:write name="agent" property="agent_ID"/>" <% DynaActionForm form = (DynaActionForm)request.getAttribute("stationForm");  if (form != null && ("" + (agent.getStation().getStation_ID())).equals((String)form.get("stationId"))) { %> CHECKED <% } %>>
                  </td>
<%
                  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_AGENTS, a)) {
%>
                    <td>
                      <A HREF="agentAdmin.do?edit=1&stationId=<bean:write name="agent" property="station.station_ID"/>&agentId=<bean:write name="agent" property="agent_ID"/>"><bean:write name="agent" property="username" /></A>
                    </td>
<%
                  } else {
%>
                    <td>
                      <bean:write name="agent" property="username" />
                    </td>
<%
                  }
%>
                  <td>
                    <bean:write name="agent" property="station.stationcode" />
                  </td>
                  <td>
                    <bean:write name="agent" property="group.description" />
                  </td>
                  <td>
                    <bean:write name="agent" property="lastname" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="agent" property="firstname" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="agent" property="mname" />
                    &nbsp;
                  </td>
                  <td>
                    <logic:equal name="agent" property="active" value="true">
                      <bean:message key="select.yes" />
                    </logic:equal>
                    <logic:notEqual name="agent" property="active" value="true">
                      <bean:message key="select.no" />
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:iterate>
              
              <tr>
                <td colspan="10">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
              
              <tr>
                <td colspan="10">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="10" align="center">
                  <html:submit styleId="button" property="addAgents">
                    <bean:message key="button.add.station.agent" />
                  </html:submit>
                </td>
              </tr>
            </logic:present>
            <tr>
              <td colspan="9" align="center">
                <INPUT Id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
              </td>
            </tr>
          </table>
        </html:form>
