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
  o = document.auditMBRForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditMBRForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditMBRForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}
// -->
  </script>
  <html:form action="audit_claims.do" method="post" onsubmit="fillzero(this.incident_ID, 13); return true;">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_claims" />
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
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href="audit_ohd.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_On_hand" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_unchecked.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Unchecked_Property" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_mbr.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Mishandled_Report" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_claims.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_Claims" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_agent.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Agent" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_station.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Station" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_shift.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Shift" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_airport.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Airport" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_group.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Group" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_company.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Company" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="audit_losscode.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Loss_Code" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
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
                  <bean:message key="colname.mbr_report_num" />
                  <br>
                  <html:text property="incident_ID" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td>
                  <bean:message key="colname.report_type" />
                  <br>
                  <html:select property="itemType_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="mbrreporttypes" property="itemType_ID" labelProperty="description" />
                  </html:select>
                </td>
                <tr>
                  <td>
                    <bean:message key="colname.modifying_agentusername" />
                    <br>
                    <html:text property="agent" size="20" maxlength="20" styleClass="textfield" />
                  </td>
                  <td>
                    <bean:message key="colname.modified_date_range" />
                    (
                    <%= a.getDateformat().getFormat() %>)
                    <br>
                    <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.auditMBRForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                    <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.auditMBRForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                </tr>
                <tr>
                  <td colspan="3" align="center" valign="top">
                    <html:submit property="search" styleId="button">
                      <bean:message key="button.retrieve" />
                    </html:submit>
                    &nbsp;
                    <html:reset styleId="button">
                      <bean:message key="button.reset" />
                    </html:reset>
                  </td>
                </tr>
              </table>
              <logic:present name="mbrlist" scope="request">
              <!-- result -->
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <strong>
                      <bean:message key="colname.incident_num" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.report_type" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.incident_create_date" />
                      :
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="header.companyCode" />
                      :
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.stationcreated" />
                      :
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.stationassigned" />
                      :
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="header.status" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.ticket" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.claimnum" />
                    </strong>
                  </td>
                  <td>
                    <strong>
                      <bean:message key="colname.pass_name" />
                    </strong>
                  </td>
                </tr>
                <logic:iterate id="results" name="mbrlist" type="com.bagnet.nettracer.tracing.db.Incident">
                  <bean:define id="items" name="results" property="itemlist" />
                  <bean:define id="claimchecks" name="results" property="claimcheck_list" />
                  <bean:define id="itinerary" name="results" property="itinerary_list" />
                  <bean:define id="passengers" name="results" property="passenger_list" />
                  <tr>
                    <td>
                      <a href='audit_claims.do?detail=1&incident_ID=<bean:write name="results" property="incident_ID"/>'><bean:write name="results" property="incident_ID" /></a>
                    </td>
                    <td>
                      <bean:write name="results" property="itemtype.description" />
                    </td>
                    <td nowrap>
                      <bean:write name="results" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.company.companyCode_ID" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.stationcode" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationassigned.stationcode" />
                    </td>
                    <td>
                      <bean:write name="results" property="status.description" />
                    </td>
                    <td>
                      <logic:empty name="results" property="ticketnumber">
                        &nbsp;
                      </logic:empty>
                      <bean:write name="results" property="ticketnumber" />
                    </td>
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:notEqual name="item_list" property="claimchecknum" value="">
                          <bean:write name="item_list" property="claimchecknum" />
                          <br>
                        </logic:notEqual>
                      </logic:iterate>
                      <logic:iterate id="claimcheck_list" name="claimchecks" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
                        <logic:notEqual name="claimcheck_list" property="claimchecknum" value="">
                          <bean:write name="claimcheck_list" property="claimchecknum" />
                          <br>
                        </logic:notEqual>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
<%
                      boolean hasp = false;
%>
                      <logic:iterate id="passenger_list" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
<%
                        hasp = false;
%>
                        <logic:notEqual name="passenger_list" property="lastname" value="">
                          <bean:write name="passenger_list" property="lastname" />
                          ,
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <logic:notEqual name="passenger_list" property="firstname" value="">
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <bean:write name="passenger_list" property="firstname" />
                        <bean:write name="passenger_list" property="middlename" />
<%
                        if (hasp) {
%>
                          <br>
<%
                        }
%>
                      </logic:iterate>
                      &nbsp;
                    </td>
                  </tr>
                </logic:iterate>
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
              <script language=javascript>
                <!--
  document.location.href="#result";
  //-->
              </script>
            </logic:present>
          </html:form>
