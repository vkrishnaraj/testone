<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass = "form2_ld";
%>
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
	var doCheck = 0;
	<jsp:include page="../includes/ldclose.jsp" />
	<% %>
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <html:form action="lostDelay.do" method="post" enctype="multipart/form-data" onsubmit="return validateLdClose(this, doCheck);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.close" />
            (
            <bean:write name="incident" scope="request" />
            )
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <td></td>
              <td></td>
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
    <!-- ICONS MENU -->
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="lostDelay.do?close=1"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.close" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
    <!-- END ICONS MENU -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <span class="reqfield">*</span>
          <bean:message key="message.required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <logic:iterate id="theitem" indexId="i" name="incidentForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan=3>
                  <a name='additem<%= i %>'></a>
                  <b><bean:message key="colname.bag_number" />
                  :
                  <%= theitem.getBagnumber() + 1 %>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.arr_airline_id" />
                  <br>
                  <html:select name="theitem" property="arrivedonairline_ID" styleClass="dropdown" indexed="true">
                    <html:option value="">
                      <bean:message key="select.please_select" />
                    </html:option>
                    <html:options collection="companylist" property="companyCode_ID" labelProperty="companydesc" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.arr_flight_num" />
                  <br>
                  <html:text name="theitem" property="arrivedonflightnum" size="10" maxlength="7" styleClass="textfield" indexed="true" />
                </td>
                <td>
                  <bean:message key="colname.arr_date" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="theitem" property="disarrivedondate" size="13" maxlength="13" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= i %>" name="calendar2<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitem[" + i + "].disarrivedondate" %>','calendar2<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
            </table>
          </logic:iterate>
          <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
            <jsp:include page="../includes/closereport_incl.jsp" />
          </table>
          <jsp:include page="../includes/remarkclose_incl.jsp" />
        </div>
        <logic:notEqual name="incidentForm" property="readonly" value="1">
          <logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <html:submit property="save" styleId="button">
                    <bean:message key="button.save" />
                  </html:submit>
                  &nbsp;
                  <html:submit property="doclose" styleId="button" onclick="doCheck = 1;">
                    <bean:message key="button.closereport" />
                  </html:submit>
                  &nbsp
                  <html:submit property="doclosewt" styleId="button">
                    <bean:message key="button.closetoWT" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </logic:notEqual>
          <logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
          <!-- only admin can edit closed section once it is closed -->
<%
          if (a.getGroup().getDescription().equalsIgnoreCase("Admin")) {
%>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <html:submit property="save" styleId="button">
                    <bean:message key="button.save" />
                  </html:submit>
                </td>
              </tr>
            </table>
<%
          }
%>
          <!-- eof admin -->
        </logic:equal>
      </logic:notEqual>
    </html:form>
