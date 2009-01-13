<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
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

    // -->
  </SCRIPT>
            <tr>
              <td colspan="3" id="navmenucell">
                <div class="menu">
                  <dl>
                    <dd>
                      <a href='searchIncident.do?incident='><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.incident_info" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href="claim_resolution.do"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_payout" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
<%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
%>
                        <dd>
                          <a href="claim_prorate.do"><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_prorate" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
<%
                      }
%>

                    <dd>
                      <a href="#"><span class="aab">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bbb"><bean:message key="menu.claim_settlement" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                  </dl>

            
            
            <!-- END ICONS MENU -->
            <tr>
              <!-- MIDDLE COLUMN -->
              <td id="middlecolumn">
                <!-- MAIN BODY -->
                <div id="maincontent">
  
          <html:form action="claim_resolution.do" method="post">
          <h1 class="green">Create Claim Settlement</h1>
          
          <p/>&nbsp;
          
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                  <html:submit property="createclaimsettlement" styleId="button">
                    <bean:message key="button.createclaimsettlement" />
                  </html:submit>
                </td>
              </tr>
            </table>
                      </html:form>
  
                  
            
