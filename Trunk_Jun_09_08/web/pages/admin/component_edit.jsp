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
  boolean bsoAdmin = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_ADMIN, a);
%>
  <script language="javascript">
    
  function goprev() {
    o = document.groupForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.edit.value="1";
    o.submit();
  }

  function gonext() {
    o = document.groupForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.edit.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.groupForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.edit.value="1";
    o.submit();

  }
  function updatePagination() {
	  o.edit.value="1";
	  o.submit();
  }

  </script>
  <html:form action="createGroup.do" method="post" onsubmit="return validateGroupForm(this);">
    <html:javascript formName="groupForm" />
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.editGroup" />
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
            <bean:message key="group" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="header.groupName" />
                <font color="red">
                  *
                </font>
                :
              </td>
              <td>
                <logic:present name="groupForm" property="groupID">
                  <html:hidden name="groupForm" property="groupID" />
                  <html:hidden name="groupForm" property="edit" value=""/>
                </logic:present>
                <html:text styleClass="textfield" name="groupForm" property="groupName" size="53" maxlength="50" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="header.groupDesc" />
                <font color="red">
                  *
                </font>
                :
              </td>
              <td>
                <html:text styleClass="textfield" name="groupForm" property="groupDesc" size="53" maxlength="50" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="header.companyCode" />
                :
              </td>
              <td>
                <html:text styleClass="textfield" name="groupForm" property="companyCode" size="4" maxlength="3" readonly="true" />
              </td>
            </tr>
            <% if(bsoAdmin){ %>
            <tr>
              <td>
                <bean:message key="header.bsoLimit" />
                :
              </td>
              <td>
                <html:text styleClass="textfield" name="groupForm" property="bsoLimit" size="10" maxlength="10" />
              </td>
            </tr>
            <% } %>
            <tr>
              <td colspan="3" align="center">
                <INPUT Id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
                <html:submit styleId="button" property="save">
                  <bean:message key="button.save" />
                </html:submit>
              </td>
            </tr>
          </table>
          <logic:present name="agentList" scope="request">
            <br>
            <h1 class="green">
              <bean:message key="group_users" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <b><bean:message key="header.agentUsername" /></b>
                </td>
                <td>
                  <b><bean:message key="header.agentStation" /></b>
                </td>
                <td>
                  <b><bean:message key="header.agentGroup" /></b>
                </td>
                <td>
                  <b><bean:message key="header.agentLname" /></b>
                </td>
                <td>
                  <b><bean:message key="header.agentFname" /></b>
                </td>
                <td>
                  <b><bean:message key="header.agentMname" /></b>
                </td>
                <td>
                  <b><bean:message key="header.agentActive" /></b>
                </td>
              </tr>
              <logic:iterate id="agent" name="agentList">
                <tr>
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
                  <td colspan="8">
                      <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  </td>
              </tr>
              <tr>
                <td colspan="8">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="8" align="center">
                  <html:submit styleId="button" property="addNewAgent">
                    <bean:message key="button.add.group.agent" />
                  </html:submit>
                </td>
              </tr>

            </table>
          </logic:present>
        </html:form>
