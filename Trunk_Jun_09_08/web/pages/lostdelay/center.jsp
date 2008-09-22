<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>

<%
	Agent a = (Agent) session.getAttribute("user");
	String cssFormClass = "form2_ld";
%>


<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
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
  <html:form action="lostDelay.do" method="post">
    <!-- search for record locator-->
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
      <h1><bean:message key="header.prepopulate" /></h1>
      </div>
      <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <jsp:include page="/pages/includes/mail_incl.jsp" />
          <td><a href="#"
            onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
            key="Help" /></a></td>
        </tr>
      </table>
      </div>
      </td>
    </tr>
    <tr>
      <td id="middlecolumn">
      <div id="maincontent" align="center"><font color=red>
      <logic:messagesPresent message="true">
        <html:messages id="msg" message="true">
          <br />
          <bean:write name="msg" />
          <br />
        </html:messages>
      </logic:messagesPresent> </font> <br>
      <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
        <tr>
          <td align=center><bean:message
            key="colname.recordlocator" /> <br>
          <html:text property="recordlocator" size="15"
            styleClass="textfield" value="" maxlength="6"/></td>
        </tr>
        <% if (TracerProperties.isTrue(TracerProperties.RESERVATION_BY_BAGTAG)) { %>
          <tr>
            <td align=center><bean:message
              key="colname.bag_tag_number" /> <br>
            <html:text property="bagTagNumber" size="15" maxlength="10"
              styleClass="textfield" value="" /></td>
          </tr>
        <% } %>
        <tr>
          <td align="center" valign="top" colspan="12"><html:submit
            property="doprepopulate" styleId="button">
            <bean:message key="button.populate" />
          </html:submit> <html:submit property="skip_prepopulate" styleId="button">
            <bean:message key="button.skip_populate" />
          </html:submit></td>
        </tr>
      </table>
  </html:form>
</logic:present>
<!-- regular incident -->
<logic:notPresent name="prepopulate" scope="request">



  <jsp:include page="/pages/includes/validation_incl.jsp" />
  <html:form action="lostDelay.do" method="post"
    onsubmit="return validateRest(this);">
    <html:hidden property="otherSystemInformation" />
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft"><logic:present name="express"
        scope="request">
        <h1><bean:message key="header.express_lostdelay" /></h1>
      </logic:present> <logic:notPresent name="express" scope="request">
        <h1><bean:message key="header.lostdelay" /></h1>
      </logic:notPresent></div>
      <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <td><a href='javascript:window.print();'><img
              src="deployment/main/images/nettracer/icon_printrpt.gif"
              width="12" height="12"></a></td>
            <td><a href="javascript:window.print();"><bean:message
              key="print_report" /></a> &nbsp;</td>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.LOST_RECEIPT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','LostReceipt',800,600);return false;"><img
              src="deployment/main/images/nettracer/icon_printrecpt.gif"
              width="12" height="12"></a></td>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.LOST_RECEIPT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','LostReceipt',800,600);return false;"><bean:message
              key="link.lost_delay_receipt" /></a> &nbsp;</td>
          </logic:notEqual>
          <jsp:include page="/pages/includes/mail_incl.jsp" />
          <td><a href="#"
            onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
            key="Help" /></a></td>
        </tr>
      </table>
      </div>
      </td>
    </tr>
    <!-- END PAGE HEADER/SEARCH -->
    <!-- ICONS MENU -->
    <logic:present name="express" scope="request">
      <!-- express input //-->
      <jsp:include page="/pages/includes/mbrexpress_incl.jsp" />
      <!-- eof express input //-->
    </logic:present>
    <logic:notPresent name="express" scope="request">
      <tr>
        <td colspan="3" id="navmenucell">
        <div class="menu">
        <dl>
          <%
          	if (TracerProperties
          						.isTrue(TracerProperties.INCIDENT_TAB_INC_INFORMATION)) {
          %>
          <dd><a href="#incidentinfo"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.incident_info" /></span> <span class="cc">&nbsp;
          <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties
          						.isTrue(TracerProperties.INCIDENT_TAB_PASSENGER)) {
          %>
          <dd><a href="#contact"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.contact_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties
          						.isTrue(TracerProperties.INCIDENT_TAB_ITINERARY)) {
          %>
          <dd><a href="#passit"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.itinerary" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties.isTrue(TracerProperties.INCIDENT_TAB_BAGGAGE)) {
          %>
          <dd><a href="#checkedbaggage"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.bag_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties.isTrue(TracerProperties.INCIDENT_TAB_INTERIM)) {
          %>

          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <dd><a href="#interimexpense"><span class="aa">&nbsp;
            <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.interim_expense_payout" /></span> <span class="cc">&nbsp;
            <br />
            &nbsp;</span></a></dd>
          </logic:notEqual>
          <%
          	}
          %>
          <%
          	if (TracerProperties.isTrue(TracerProperties.INCIDENT_TAB_REMARKS)) {
          %>

          <dd><a href="#remarks"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.remarks" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <dd><a
              href='viewMatches.do?clear=1&incident_ID=<bean:write name="incidentForm" property="incident_ID"/>'><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.matches" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <dd><a
              href="otherTasks.do?type=1&file=<bean:write name="incidentForm" property="incident_ID"/>"><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.tasks" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <%
            	if (UserPermissions.hasPermission(
            							TracingConstants.SYSTEM_COMPONENT_NAME_CLAIMS, a)) {
            %>
            <logic:notEqual name="incidentForm" property="readonly"
              value="1">
              <%
              	if (UserPermissions.hasPermission(
              									TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_RESOLUTION,
              									a)) {
              %>
              <dd><a href="claim_resolution.do"><span
                class="aa">&nbsp; <br />
              &nbsp;</span> <span class="bb"><bean:message
                key="menu.claims" /></span> <span class="cc">&nbsp; <br />
              &nbsp;</span></a></dd>
              <%
              	} else if (UserPermissions.hasPermission(
              									TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
              %>
              <dd><a href="claim_prorate.do"><span class="aa">&nbsp;
              <br />
              &nbsp;</span> <span class="bb"><bean:message
                key="menu.claims" /></span> <span class="cc">&nbsp; <br />
              &nbsp;</span></a></dd>
              <%
              	}
              %>
            </logic:notEqual>
            <%
            	}
            %>
            <%
            	if (UserPermissions.hasPermission(
            							TracingConstants.SYSTEM_COMPONENT_NAME_BDO, a)) {
            %>
            <dd><a
              href='bdo.do?mbr_id=<bean:write name="incidentForm" property="incident_ID"/>'><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message key="menu.bdo" /></span>
            <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <%
            	}
            %>
            <dd><a href="lostDelay.do?close=1"><span class="aa">&nbsp;
            <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.close" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <dd><a href="#" onclick="gotoHistoricalReport();"><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.history" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
          </logic:notEqual>
        </dl>
        </div>
        </td>
      </tr>
      <!-- END ICONS MENU -->
      <tr>
        <!-- MIDDLE COLUMN -->
        <td id="middlecolumn"><!-- MAIN BODY --> <jsp:include
          page="/pages/includes/reportinfo_addr_iti_bagcheck_incl.jsp" />
        <!-- claimcheck numbers --> <a name="claimcheck"></a> <a
          name='addclaimcheck'></a>
        <h1 class="green"><bean:message key="colname.claimnum" />
        <a href="#"
          onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_check_information_(ld).htm#add claim check');return false;"><img
          src="deployment/main/images/nettracer/button_help.gif"
          width="20" height="21" border="0"></a></h1>
        <table class="<%=cssFormClass %>" cellspacing="0"
          cellpadding="0">
          <logic:iterate id="claimcheck" indexId="i" name="incidentForm"
            property="claimchecklist">
            <tr>
              <td width="30%" nowrap="nowrap"><bean:message
                key="colname.claimnum.req" /> :</td>
              <td><html:text name="claimcheck"
                property="claimchecknum" size="13" maxlength="13"
                styleClass="textfield" indexed="true" /> <%
                  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SCANNER_DATA, a)) {
                %> <logic:notEmpty name="claimcheck"
                property="claimchecknum">
                <a
                  href="scannerData.do?bagTagNumber=<bean:write name="claimcheck" property="claimchecknum" />&incidentId=<bean:write name="incidentForm" property="incident_ID" />"><br />
                <bean:message key="scanner.link" /></a>
              </logic:notEmpty> <%
 	}
 %>
              </td>
              <td><logic:notEqual name="incidentForm"
                property="incident_ID" value="">
                <logic:notEmpty name="claimcheck" property="OHD_ID">
                  <a
                    href='addOnHandBag.do?ohd_ID=<bean:write name="claimcheck" property="OHD_ID"/>'><bean:write
                    name="claimcheck" property="OHD_ID" /></a>
                    &nbsp;
                    <logic:notPresent name="cantmatch" scope="request">

                    <input type="submit" name="unmatchclaim<%= i %>"
                      value='<bean:message key="button.un_match"/>'
                      id="button">
                  </logic:notPresent>
                </logic:notEmpty>
                <logic:notPresent name="cantmatch" scope="request">
                  <logic:empty name="claimcheck" property="OHD_ID">
                      &nbsp;&nbsp;
                      <bean:message key="colname.matched_ohd" />
                      :
                      <html:text name="claimcheck" property="tempOHD_ID"
                      size="13" maxlength="13" styleClass="textfield"
                      indexed="true" onblur="fillzero(this,13);" />
                    <input type="submit" name="matchclaim<%= i %>"
                      value='<bean:message key="button.do_match"/>'
                      id="button">
                  </logic:empty>
                </logic:notPresent>
              </logic:notEqual></td>
            </tr>
            <tr>
              <td colspan="3"><html:submit styleId="button"
                property="deleteClaimcheck" indexed="true">
                <bean:message key="button.delete_claim" />
              </html:submit></td>
            </tr>
          </logic:iterate>
        </table>
        <center><html:submit property="addclaimcheck"
          styleId="button">
          <bean:message key="button.add_claimcheck" />
        </html:submit></center>
        <jsp:include page="/pages/includes/mbrbag_incl.jsp" /> <jsp:include
          page="/pages/includes/remark_incl.jsp" />
        </div>
        <logic:notEqual name="incidentForm" property="readonly"
          value="1">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" valign="top"><br>
              <logic:notEqual name="incidentForm" property="incident_ID"
                value="">
                <%
                	if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
                %>
                <html:submit property="save" styleId="button"
                  onclick="return validatereqWtIncFields(this.form, 'lostdelay');">
                  <bean:message key="button.save" />
                </html:submit>
                <%
                	} else {
                %>
                <html:submit property="save" styleId="button"
                  onclick="return validatereqFields(this.form, 'lostdelay');">
                  <bean:message key="button.save" />
                </html:submit>
                <%
                	}
                %>

                <%
                	if (UserPermissions.hasPermission(
                								TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER, a)) {
                							if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
                								if (a.getStation().getCompany().getVariable()
                										.getWt_write_enabled() == 1) {
                									if (UserPermissions
                											.hasPermission(
                													TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT,
                													a)) {
                %>
				&nbsp;&nbsp;&nbsp;&nbsp;

					<c:if test="${empty pendingWtAction}">
                  <c:choose>
                    <c:when
                      test="${(incidentForm.wt_id == '') || (incidentForm.wt_id == null)}">
                      <html:submit styleId="button" property="savetowt"
                        onclick="return validatereqWtIncFields(this.form, 'lostdelay');">
                        <bean:message key="button.savetoWT" />
                      </html:submit>
                    </c:when>
                    <c:when
                      test="${incidentForm.wtFile.wt_status == 'ACTIVE'}">
                      <html:submit styleId="button" property="amendWT"
                        onclick="return validatereqWtIncFields(this.form, 'lostdelay');">
                        <bean:message key="button.amendWT" />
                      </html:submit>
                    </c:when>
                  </c:choose>
                </c:if>

                <%
                	}
                								}
                							}
                						}
                %>
              </logic:notEqual> <logic:equal name="incidentForm" property="incident_ID"
                value="">
                <input type="hidden" name="savetemp" value="">
                <input type="button" name="s" value="Save as Temporary"
                  onclick="if(validatereqFields(this.form)){saveIncidentTemporary(this.form, 'lostdelay')};"
                  id="button">
                &nbsp;&nbsp;&nbsp;
                <%
                	if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
                %>
                <html:submit property="savetracing" styleId="button"
                  onclick="return validatereqWtIncFields(this.form, 'lostdelay');">
                  <bean:message key="button.savetracing" />
                </html:submit>
                <%
                	} else {
                %>
                <html:submit property="savetracing" styleId="button"
                  onclick="return validatereqFields(this.form, 'lostdelay');">
                  <bean:message key="button.savetracing" />
                </html:submit>
                <%
                	}
                %>
              </logic:equal></td>
            </tr>
          </table>
        </logic:notEqual> <logic:equal name="incidentForm" property="readonly" value="1">
          <logic:equal name="incidentForm"
            property="allow_remark_update" value="1">
            <table width="100%" border="0" cellpadding="0"
              cellspacing="0">
              <tr>
                <td align="center" valign="top"><br>
                <logic:notEqual name="incidentForm"
                  property="incident_ID" value="">
                  <html:submit property="save" styleId="button">
                    <bean:message key="button.saveremark" />
                  </html:submit>
                </logic:notEqual></td>
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

<logic:present name="claimcheck" scope="request">
	document.location.href="#addclaimcheck";
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

//-->
    </script>
  </html:form>


</logic:notPresent>
