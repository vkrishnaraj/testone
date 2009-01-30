<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
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
  o = document.viewMassOnHandsForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.viewMassOnHandsForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewMassOnHandsForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}
function sortMass(sortOrder) {
	o = document.viewMassOnHandsForm;
	o.sort.value = sortOrder;
	o.submit();
}

// -->
  </script>
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <html:form action="massOnhands.do" method="post">
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
            <bean:message key="header.search_mass_on_hand" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/mass_on-hand_bag.htm#add_mass_on_hand');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="colname.on_hand_report_number" />
                :
              </td>
              <td colspan="3">
                <html:text property="ohd_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td colspan=4>
                <bean:message key="colname.date_range" />
                (
                <%= a.getDateformat().getFormat() %>)
                <br>
                <html:text property="s_time" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.viewMassOnHandsForm.s_time,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                <html:text property="e_time" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.viewMassOnHandsForm.e_time,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
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
            <bean:message key="Mass_On-hands" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/mass_on-hand_bag.htm#add_mass_on_hand');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <a href="#" onclick="sortMass('ohd');"><strong>
                    <bean:message key="colname.on_hand_report_number" /></a>
                </strong>
              </td>
              <td>
                <a href="#" onclick="sortMass('createdate');"><strong>
                    <bean:message key="colname.ohd_create_date" /></a>
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.bag_tag_number" />
                </strong>
              </td>
            </tr>
            <logic:present name="ohdList" scope="request">
              <logic:iterate id="ohd" name="ohdList" scope="request">
                <tr>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
                  </td>
                  <td>
                    <bean:write name="ohd" property="displaydate" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="claimnum" />
                    &nbsp;
                  </td>
                </tr>
              </logic:iterate>
            </logic:present>
            <input type="hidden" name="search" value="1">
            <tr>
              <td colspan="11">
                <!-- pagination -->
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
                <!-- eof pagination -->
              </td>
            </tr>
            <!-- end pagination -->
          </table>
        </html:form>
