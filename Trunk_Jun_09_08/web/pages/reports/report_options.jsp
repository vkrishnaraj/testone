<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  Agent agent = (Agent)session.getAttribute("user");
%>
  <%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<html:form action="statReport.do" method="post">
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	
	function changebutton() {
		document.statReportForm.createbutton.disabled = true;
		document.statReportForm.createbutton.value = "Please Wait...";
		document.statReportForm.create.value = "Create Report";
		document.statReportForm.submit();
	}
	
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <html:hidden property="reportnum" />
  <html:hidden property="customreportnum" />
  <input type="hidden" name="create" value="">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.analytical_report" />
        </h1>
      </div>
      <div id="pageheaderright">
        <table id="pageheaderright">
          <tr>
            <jsp:include page="/pages/includes/mail_incl.jsp" />
            <td>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/analytical_reports_summary.htm');return false;"><bean:message key="Help" /></a>
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
<%
          String a = "", b = "", c = "", d = "", e = "", f = "", g = "", z = "";
%>
          <logic:equal name="reportnum" scope="request" value="3">
<%
            a = "b";
%>
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="4">
<%
            b = "b";
%>
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="5">
<%
            c = "b";
%>
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="6">
<%
            d = "b";
%>
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="7">
<%
            e = "b";
%>
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="8">
<%
            f = "b";
%>
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="10">
<%
            g = "b";
%>
          </logic:equal>          
          <logic:equal name="reportnum" scope="request" value="20">
<%
            z = "b";
%>
          </logic:equal>

<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OCCURRENCES, agent)) {
%>          
          <dd>
            <a href="statReport.do?reportnum=3"><span class="aa<%= a %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= a %>"><bean:message key="menu.reportnum.3" /></span>
              <span class="cc<%= a %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BY_PASSENGER_BOARDINGS, agent)) {
%>
          <dd>
            <a href="statReport.do?reportnum=4"><span class="aa<%= b %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= b %>"><bean:message key="menu.reportnum.4" /></span>
              <span class="cc<%= b %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OCCURRENCES_PER_FLIGHT, agent)) {
%>
          <dd>
            <a href="statReport.do?reportnum=5"><span class="aa<%= c %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= c %>"><bean:message key="menu.reportnum.5" /></span>
              <span class="cc<%= c %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISBURSEMENTS, agent)) {
%>
          <dd>
            <a href="statReport.do?reportnum=6"><span class="aa<%= d %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= d %>"><bean:message key="menu.reportnum.6" /></span>
              <span class="cc<%= d %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_STATION, agent)) {
%>
          <dd>
            <a href="statReport.do?reportnum=7"><span class="aa<%= e %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= e %>"><bean:message key="menu.reportnum.7" /></span>
              <span class="cc<%= e %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_RECOVERY, agent)) {
%>

          <dd>
            <a href="statReport.do?reportnum=8"><span class="aa<%= f %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= f %>"><bean:message key="menu.reportnum.8" /></span>
              <span class="cc<%= f %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_BAGGAGE_REPORT, agent)) {
%>

          <dd>
            <a href="statReport.do?reportnum=10"><span class="aa<%= g %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= g %>"><bean:message key="menu.reportnum.10" /></span>
              <span class="cc<%= g %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>         	
<%
		}
%>
<%
		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUSTOM_REPORTS, agent)) {
%>
          <dd>
            <a href="statReport.do?reportnum=20"><span class="aa<%= z %>">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb<%= z %>"><bean:message key="menu.reportnum.20" /></span>
              <span class="cc<%= z %>">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
<%
}
%>          
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
        <h1 class="green">
          <logic:equal name="reportnum" scope="request" value="3">
          <bean:message key="header.reportnum.3" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/occurrences_report.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="4">
          <bean:message key="header.reportnum.4" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/by_passenger_boarding.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="5">
          <bean:message key="header.reportnum.5" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/occurrences_per_flight.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="6">
          <bean:message key="header.reportnum.6" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/disbursements_report.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="7">
          <bean:message key="header.reportnum.7" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/station_reports.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="8">
          <bean:message key="header.reportnum.8" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/recovery.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="10">
          <bean:message key="header.reportnum.10" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/onhand.htm');return false;">
          </logic:equal>
          <logic:equal name="reportnum" scope="request" value="20">
          	<c:choose>
	          	<c:when test="${!empty customreportnum}">
		          	<bean:message key="header.customreportnum.${customreportnum}" />
		        </c:when>
		        <c:otherwise>
		        	<bean:message key="header.reportnum.20" />
	        	</c:otherwise>
        	</c:choose>
          </logic:equal>
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#analytical_reports/analytical_reports_summary.htm');return false;">
        	<img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0">
        	</a>
           </h1>
                  <span class="reqfield">*</span>
                  <bean:message key="message.required" />
                  <font color=red>
                    <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                  </font>
                  <br>
                  
                  <!-- custom reporting //-->
                  <logic:equal name="reportnum" scope="request" value="20">
                  
                  <!-- custom report 1 MBR report -->
                  
                  
                  <c:if test="${!empty customreportnum}">
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td colspan=2>
                          <strong>
                            <bean:message key="header.report_options" />
                        </strong>
                      </td>
                    </tr>
                    <jsp:include page="/pages/clients/${company}/report${customreportnum}.jsp" />
                    </table>
                  </c:if>
	                  
                  <logic:notPresent name="customreportnum" scope="request">
                  <table class="form2" cellspacing="0" cellpadding="0">
                  <tr>
                    <td>
                      <strong>
                        <bean:message key="header.choose_custom_report" />
                      </strong>
                    </td>
                  </tr>
                  
                  <logic:iterate id="report" indexId="i" name="customReports" type="com.bagnet.nettracer.tracing.db.Report" scope="request">
                    <tr>
                      <td>
                        <a href="statReport.do?reportnum=20&customreportnum=<bean:write name="report" property="number"/>"><bean:write name="report" property="resourceValue"/></a>
                      </td>
                    </tr>
                  </logic:iterate>

                  </table>
                  </logic:notPresent>
                  
                  </logic:equal>
                  <!-- ----------------end of custom reporting------------------------ //-->
                  
                  <table class="form2" cellspacing="0" cellpadding="0">
                  
                  <logic:notEqual name="reportnum" scope="request" value="10">
                  <logic:notEqual name="reportnum" scope="request" value="20">
                  	<jsp:include page="report_options_incl_3_8.jsp" />
                  </logic:notEqual>
                  </logic:notEqual>
                  
                  <logic:equal name="reportnum" scope="request" value="10">
                  	<jsp:include page="report_options_incl_10.jsp" />
                  </logic:equal>
                  
                  <logic:notEqual name="reportnum" scope="request" value="20">
                  
	              <tr>
	                <td>
	                  <bean:message key="colname.report_output_type" />
	                  :
	                </td>
	                <td>
                      <% if (!TracerProperties.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
    	                  <html:radio property="outputtype" value="0" />
    	                  <bean:message key="radio.pdf" />
    	                  <html:radio property="outputtype" value="1" />
    	                  <bean:message key="radio.html" />
    	                  <html:radio property="outputtype" value="2" />
    	                  <bean:message key="radio.xls" />
    	                  <html:radio property="outputtype" value="3" />
    	                  <bean:message key="radio.csv" />
    	                  <html:radio property="outputtype" value="4" />
    	                  <bean:message key="radio.xml" />
                      <% } else { %>
                        <html:radio property="outputtype" value="1" />
                        <bean:message key="radio.html" />
                      <% } %>
	                </td>
	              </tr>
	              <tr>
	                <td colspan="2" align="center" valign="top" bgcolor=white>
	                  <br>
	                  <INPUT type="button" value="Back" id="button" onClick="history.back()">
	                  &nbsp;
	                  <html:button property="createbutton" styleId="button" onclick="changebutton();">
	                    <bean:message key="button.createreport" />
	                  </html:button>
	                </td>
	              </tr>
	              <logic:present name="reportfile" scope="request">
	              <!-- result -->
	              <tr>
	                <td colspan=2 align=center bgcolor=white>
	                  <br>
	                  <br>
	                  <a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a>
	                  <p>
	                    <a target="reportwin" href="reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>"><b><bean:message key="link.save_report" /></b></a>
	                  </td>
	                </tr>
	              </logic:present>

	            </logic:notEqual> <!-- end of not equal custom report //-->
	            </table>
            
            <logic:present name="reportfile" scope="request">
              <script language=javascript>
                <!--

openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);

//-->
              </script>
            </logic:present>
          </html:form>
