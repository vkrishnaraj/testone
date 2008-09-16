<%@ page language="java" %>
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
  o = document.searchBDOForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchBDOForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchBDOForm;
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
  <html:form action="searchbdo.do" method="post" focus="incident_ID" onsubmit="return validateSearch(this);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.search_bdo" />
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
                  <bean:message key="header.bdo_report" />
                  <br>
                  <html:text property="incident_ID" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td colspan=2>
                  <bean:message key="header.bdo_bag" />
                  <br>
                  <html:text property="ohd_ID" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text property="lastname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text property="firstname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text property="middlename" size="20" maxlength="20" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td nowrap colspan=2>
                  <bean:message key="colname.date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchBDOForm.s_createtime,'calendar3','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchBDOForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td nowrap>
                  <bean:message key="colname.deliverydate" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="deliverydate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchBDOForm.deliverydate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.companycreated" />
                  <br>
                  <html:select property="companycreated_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.stationcreated_nobr" />
                  <br>
                  <html:select property="stationcreated_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.agentusername" />
                  <br>
                  <html:text property="agent" size="20" maxlength="20" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td colspan="3" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;
                  <html:button styleId="button" property="reset" onclick="document.location.href='searchbdo.do';">
                    <bean:message key="button.reset" />
                  </html:button>
                </td>
              </tr>
            </table>
            <logic:present name="bdo_list" scope="request">
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_BDO');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <b><bean:message key="colname.bdo_id" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.agent" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.date" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.station" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.airline" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.delivercompany" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.servicelevel" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.deliverydate" /></b>
                  </td>
                  <td>
                    <b><bean:message key="colname.bagcount" /></b>
                  </td>
                </tr>
                <logic:iterate id="bdos" name="bdo_list" scope="request" type="com.bagnet.nettracer.tracing.db.BDO">
                  <tr>
                    <td nowrap>
                      <a href="bdo.do?bdo_id=<bean:write name="bdos" property="BDO_ID"/>"><bean:write name="bdos" property="BDO_ID_ref" /></a>
                    </td>
                    <td nowrap>
                      <bean:write name="bdos" property="agentinit" />
                      &nbsp;
                    </td>
                    <td nowrap>
                      <bean:write name="bdos" property="dispcreatetime" />
                      &nbsp;
                    </td>
                    <td nowrap>
                      <bean:write name="bdos" property="station.stationcode" />
                      &nbsp;
                    </td>
                    <td nowrap>
                      <bean:write name="bdos" property="companycode_ID" />
                      &nbsp;
                    </td>

                    <td nowrap>
                      <bean:write name="bdos" property="delivercompany.name" />
                      &nbsp;
                    </td>
 				
                    <td nowrap>
                    <logic:present name="bdos" property="servicelevel">
                      <bean:write name="bdos" property="servicelevel.description" />
                    </logic:present>
                      &nbsp;
                    </td>
                    <td nowrap>
                      <bean:write name="bdos" property="dispdeliverydate" />
                      &nbsp;
                    </td>
                    <td nowrap>
                      <bean:write name="bdos" property="bagcount" />
                      &nbsp;
                    </td>
                  </tr>
                </logic:iterate>
                <tr>
                  <td colspan="9">
                    <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  </td>
                </tr>
                <!-- end pagination -->
              </table>
            </logic:present>
          </html:form>
