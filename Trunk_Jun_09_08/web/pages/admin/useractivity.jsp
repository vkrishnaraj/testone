<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <script language="javascript">
    <!--
function goprev() {
  o = document.UserActivityForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.UserActivityForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.UserActivityForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();
}

// -->
  </script>
  <html:form action="useractivity.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.User_Activity" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="../includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    <!-- END PAGE HEADER/SEARCH -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.agentusername" />
                  <br>
                  <html:text property="agent" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="header.status" />
                  <br>
                  <html:select property="activity_status" styleClass="dropdown">
                    <html:option value="-2">
                      <bean:message key="Currently_Logged_On" />
                    </html:option>
                    <html:option value="-1">
                      <bean:message key="all" />
                    </html:option>
                  </html:select>
                </td>
              </tr>
              <tr>
                <td colspan="2">
                  <bean:message key="colname.logon_date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text styleClass="textfield" property="s_time" size="11" maxlength="10" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.UserActivityForm.s_time,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text styleClass="textfield" property="e_time" size="11" maxlength="10" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.UserActivityForm.e_time,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
              <tr>
                <td colspan="2" align="center">
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
              <a href="#" onclick="openHelp('');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <br>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <strong>
                    <bean:message key="header.agentUsername" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.time_logged_on" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.time_logged_off" />
                  </strong>
                </td>
              </tr>
              <logic:present name="agentList" scope="request">
                <logic:iterate id="agent" name="agentList" type="com.bagnet.nettracer.tracing.db.Agent_Logger">
                  <tr>
                    <td>
                      <bean:write name="agent" property="username" />
                    </td>
                    <td>
                      <bean:write name="agent" property="displayLoggedOn" />
                    </td>
                    <td>
                      &nbsp;
                      <bean:write name="agent" property="displayLoggedOff" />
                    </td>
                  </tr>
                </logic:iterate>
              </logic:present>
              <input type="hidden" name="search" value="1">
              <tr>
                <td colspan="11">
                  <jsp:include page="../includes/pagination_incl.jsp" />
                </td>
              </tr>
              <!-- end pagination -->
            </table>
          </html:form>
