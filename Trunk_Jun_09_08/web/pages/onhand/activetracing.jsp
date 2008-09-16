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
  <html:form action="activeTracing.do" method="post" focus="incident_ID">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.active_tracing" />
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
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#Active_Tracing.htm#Search_On_Hand');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.ld_report_num" />
                <font color=red>
                  *
                </font>
                :
              </td>
              <td>
                <html:text name="activeTracingForm" property="incident_ID" size="13" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.scope" />
                :
              </td>
              <td>
                <html:select name="activeTracingForm" property="scope" styleClass="dropdown">
                  <html:option value="0">
                    <bean:message key="select.industry" />
                  </html:option>
                  <html:option value="1">
                    <bean:message key="select.company" />
                  </html:option>
                  <html:option value="2">
                    <bean:message key="select.station" />
                  </html:option>
                </html:select>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.date_range" />
                (
                <%= a.getDateformat().getFormat() %>):
              </td>
              <td nowrap>
                <html:text name="activeTracingForm" property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.activeTracingForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                <html:text name="activeTracingForm" property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.activeTracingForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                <br>
                <html:submit property="search" styleId="button">
                  <bean:message key="button.search" />
                </html:submit>
              </td>
            </tr>
          </table>
          <logic:present name="result" scope="request">
          <!-- result -->
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td valign="top">
                <bean:message key="message.matches.made" />
                <a href="message.do?inbox=1"><bean:message key="inbox" /></a>
                .
              </td>
            </tr>
          </table>
        </logic:present>
      </html:form>
