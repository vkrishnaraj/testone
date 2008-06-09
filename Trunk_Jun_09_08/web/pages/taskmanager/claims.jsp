<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
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
  o = document.claimToBeProcessedForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.claimToBeProcessedForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.claimToBeProcessedForm;
  o.currpage.value = i;
  o.submit();

}

function sortClaims(sortOrder) {
	o = document.claimToBeProcessedForm;
	o.sort.value = sortOrder;
	o.submit();
}

// -->
  </script>
  <html:form action="claims.do" method="post">
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
    <jsp:include page="../includes/taskmanager_header.jsp" />
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_claims" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="colname.incident_num" />
                :
              </td>
              <td colspan="3">
                <html:text property="inc_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td colspan=4>
                <bean:message key="colname.date_range" />
                (
                <%= a.getDateformat().getFormat() %>)
                <br>
                <html:text property="s_time" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.claimToBeProcessedForm.s_time,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                <html:text property="e_time" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.claimToBeProcessedForm.e_time,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
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
            <bean:message key="header.claims" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <TR>
              <TD>
                <a href="#" onclick="sortClaims('incident');"><b><bean:message key="colname.incident_num" /></b></a>
              </TD>
              <TD>
                <a href="#" onclick="sortClaims('createdate');"><b><bean:message key="header.date" /></b></a>
              </TD>
            </TR>
            <logic:present name="claimsList" scope="request">
              <logic:iterate name="claimsList" id="claim">
                <TR>
                  <TD>
                    <a href='searchIncident.do?incident=<bean:write name="claim" property="incident_ID"/>'><bean:write name="claim" property="incident_ID" /></a>
                  </TD>
                  <td>
                    <bean:write name="claim" property="displaydate" />
                  </td>
                </TR>
              </logic:iterate>
              <!-- pagination -->
              <tr>
                <td colspan="11">
                  <jsp:include page="../includes/pagination_incl.jsp" />
                  <!-- eof pagination -->
                </td>
              </tr>
              <!-- end pagination -->
            </logic:present>
            <tr>
              <td colspan="2" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                <INPUT type="button" value="Back" onClick="history.back()" Id="button">
                &nbsp;
              </td>
            </tr>
          </TABLE>
        </html:form>
