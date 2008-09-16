<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    <!--
  function goprev() {
    o = document.groupForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.groupForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.groupForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}
// -->
  </script>
  <jsp:include page="/pages/includes/validation_incl.jsp" />
  <html:form action="createGroup.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.groupAdmin" />
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
    <!-- END PAGE HEADER/SEARCH -->
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
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_groups" />
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
                <bean:message key="header.groupName" />
                :
              </td>
              <td colspan="3">
                <html:text property="groupSearchName" size="53" maxlength="50" styleClass="textfield" />
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
            <bean:message key="groups" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/maintain_groups.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <input type="hidden" name="delete1" value="" />
          <input type="hidden" name="groupID" value="" />
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <b><bean:message key="header.groupName" /></b>
              </td>
              <td>
                <b><bean:message key="header.groupDesc" /></b>
              </td>
              <td>
                <b><bean:message key="header.companyCode" /></b>
              </td>
              <td>
                <b><bean:message key="header.editPermissions" /></b>
              </td>
              <td>
                <b><bean:message key="header.deleteGroup" /></b>
              </td>
            </tr>
            <logic:present name="groupList" scope="request">
              <logic:iterate id="group" name="groupList" type="com.bagnet.nettracer.tracing.db.UserGroup">
                <tr>
                  <td>
                    <A HREF="createGroup.do?groupID=<bean:write name="group" property="userGroup_ID"/>&edit=1"><bean:write name="group" property="description" /></A>
                  </td>
                  <td>
                    <logic:empty name="group" property="description2">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="group" property="description2" />
                  </td>
                  <td>
                    <bean:write name="group" property="companycode_ID" />
                  </td>
                  <td>
                    &nbsp;
<%
                    if (a.getGroup().getUserGroup_ID() != group.getUserGroup_ID()) {
%>
                      <A HREF="componentAdmin.do?groupId=<bean:write name="group" property="userGroup_ID"/>"><bean:message key="manage" /></A>
<%
                    }
%>
                  </td>
                  <td>
                    <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteGroup('<bean:write name="group" property="description"/>',<bean:write name="group" property="userGroup_ID"/>);" id="button">
                  </td>
                </tr>
              </logic:iterate>
              <!-- pagination -->
              <tr>
                <td colspan="5">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
            </logic:present>
            <tr>
              <td colspan="5">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="5" align="center">
                <INPUT Id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
                <html:hidden property="companyCode" />
                <html:submit styleId="button" property="addNew">
                  <bean:message key="button.add.group" />
                </html:submit>
              </td>
            </tr>
          </table>
        </html:form>
