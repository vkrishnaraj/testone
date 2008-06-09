<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
<script language="javascript">
    <!--
function gotoHistoricalReport() {
  o = document.incidentForm;
	o.historical_report.value = "1";
	o.submit();
}
// -->
  </script>
    <logic:present name="prepopulate" scope="request">
    <html:form action="damaged.do" method="post">
    <!-- search for record locator-->
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.prepopulate" />
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
    <tr>
      <td id="middlecolumn" >
        <div id="maincontent" align="center">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td align=center>
                <bean:message key="colname.recordlocator" />
                <br>
                <html:text property="recordlocator" size="20" styleClass="textfield" value="" />
              </td>
              <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit property="doprepopulate" styleId="button">
                    <bean:message key="button.populate" />
                  </html:submit>
                  <html:submit property="skip_prepopulate" styleId="button">
                    <bean:message key="button.skip_populate" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </html:form>
        </logic:present>
        <!-- regular incident -->
        <logic:notPresent name="prepopulate" scope="request">
        
        
  <jsp:include page="../includes/validation_incl.jsp" />
  <html:form action="damaged.do" method="post" enctype="multipart/form-data" onsubmit="return validateRest(this);">
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <logic:present name="express" scope="request">
            <h1>
              <bean:message key="header.express_damaged" />
            </h1>
          </logic:present>
          <logic:notPresent name="express" scope="request">
            <h1>
              <bean:message key="header.damaged" />
            </h1>
          </logic:notPresent>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <td></td>
              <td></td>
              <logic:notEqual name="incidentForm" property="incident_ID" value="">
                <td>
                  <a href='javascript:window.print();'><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"></a>
                </td>
                <td>
                  <a href="javascript:window.print();"><bean:message key="print_report" /></a>
                  &nbsp;
                </td>
                <td>
                  <a href='#' onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.DAMAGE_RECEPIT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','DamageReceipt',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrecpt.gif" width="12" height="12"></a>
                </td>
                <td>
                  <a href='#' onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.DAMAGE_RECEPIT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','DamageReceipt',800,600);return false;"><bean:message key="link.lost_delay_receipt" /></a>
                  &nbsp;
                </td>
              </logic:notEqual>
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
    <logic:present name="express" scope="request">
    <!-- express input //-->
    <jsp:include page="../includes/mbrexpress_incl.jsp" />
    <!-- eof express input //-->
  </logic:present>
  <logic:notPresent name="express" scope="request">
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
          	<% /*
            <dd>
              <a href="#incidentinfo"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            */ %>
            <dd>
              <a href="#contact"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.contact_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="#passit"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.itinerary" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <!--<dd><a href="#checkedbaggage"><span class="aa">&nbsp;<br />&nbsp;</span><span class="bb"><bean:message key="menu.checked_bag_info"/></span><span class="cc">&nbsp;<br />&nbsp;</span></a></dd>-->
            <dd>
              <a href="#checkedbaggage"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.damaged_bag_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <logic:notEqual name="incidentForm" property="incident_ID" value="">
            <dd>
              <a href="#interimexpense"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.interim_expense_payout" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            </logic:notEqual>
            <dd>
              <a href="#remarks"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.remarks" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <logic:notEqual name="incidentForm" property="incident_ID" value="">
              <dd>
                <a href="otherTasks.do?type=1&file=<bean:write name="incidentForm" property="incident_ID"/>"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.tasks" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
<%
              if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIMS, a)) {
%>
                <logic:notEqual name="incidentForm" property="readonly" value="1">
<%
                  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_RESOLUTION, a)) {
%>
                    <dd>
                      <a href="claim_resolution.do"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claims" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
<%
                  } else if (UserPermissions.hasPermission(
                          TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
%>
                    <dd>
                      <a href="claim_prorate.do"><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claims" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
<%
                  }
%>
                </logic:notEqual>
<%
              }
%>
              <dd>
                <a href="damaged.do?close=1"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.close" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
              <dd>
                <a href="#" onclick="gotoHistoricalReport();"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.history" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
            </logic:notEqual>
          </dl>
        </div>
      </td>
    </tr>
    <!-- END ICONS MENU -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <jsp:include page="../includes/reportinfo_addr_iti_bagcheck_incl.jsp" />
        <jsp:include page="../includes/mbrbag_incl.jsp" />
        <jsp:include page="../includes/remark_incl.jsp" />
      
      <logic:notEqual name="incidentForm" property="readonly" value="1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" valign="top">
              <br>
              <html:submit property="save" styleId="button" onclick="return validatereqFields(this.form, 'damaged');">
                <bean:message key="button.saveincident" />
              </html:submit>
            </td>
          </tr>
        </table>
      </logic:notEqual>
      <logic:equal name="incidentForm" property="readonly" value="1">
      <logic:equal name="incidentForm" property="allow_remark_update" value="1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" valign="top">
              <br>
              <logic:notEqual name="incidentForm" property="incident_ID" value="">
                <html:submit property="save" styleId="button">
                  <bean:message key="button.saveremark" />
                </html:submit>
              </logic:notEqual>
            </td>
          </tr>
        </table>
      </logic:equal>
      </logic:equal>
    </logic:notPresent>
    <!-- this not present is for non express inputs //-->
    <script language="javascript">
      <!--

<logic:present name="passit" scope="request">
	document.location.href="#passit";
</logic:present>

<logic:present name="bagit" scope="request">
	document.location.href="#bagit";
</logic:present>

<logic:present name="passenger" scope="request">
	document.location.href='#addpassenger<bean:write name="passenger" scope="request" />';
</logic:present>

<logic:present name="item" scope="request">
	document.location.href='#additem<bean:write name="item" scope="request" />';
</logic:present>

<logic:present name="inventory" scope="request">
	document.location.href='#inventory<bean:write name="inventory" scope="request" />';
</logic:present>

<logic:present name="remark" scope="request">
	document.location.href="#addremark<bean:write name="remark" scope="request" />";
</logic:present>

<logic:present name="upload" scope="request">
	document.location.href='#upload<bean:write name="upload" scope="request" />';
</logic:present>

//-->
    </script>
  </html:form>

  </logic:notPresent>