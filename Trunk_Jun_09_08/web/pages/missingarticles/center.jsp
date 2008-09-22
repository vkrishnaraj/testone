<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>

<%
	Agent a = (Agent) session.getAttribute("user");
	String cssFormClass = "form2_pil";
%>
<!-- Calendar includes -->

<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
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
function gotoHistoricalReport() {
  o = document.incidentForm;
	o.historical_report.value = "1";
	o.submit();
}
// -->
  </script>

<logic:present name="prepopulate" scope="request">
  <html:form action="missing.do" method="post">
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
            styleClass="textfield" value="" maxlength="6" /></td>
        </tr>
        <%
        	if (TracerProperties.isTrue(TracerProperties.RESERVATION_BY_BAGTAG)) {
        %>
        <tr>
          <td align=center><bean:message
            key="colname.bag_tag_number" /> <br>
          <html:text property="bagTagNumber" size="15" maxlength="10"
            styleClass="textfield" value="" /></td>
        </tr>
        <%
        	}
        %>

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
  <html:form action="missing.do" method="post"
    onsubmit="return validateRest(this);">
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft"><logic:present name="express"
        scope="request">
        <h1><bean:message key="header.express_ma" /></h1>
      </logic:present> <logic:notPresent name="express" scope="request">
        <h1><bean:message key="header.missing_articles" /></h1>
      </logic:notPresent></div>
      <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <td></td>
          <td></td>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <td><a href='javascript:window.print();'><img
              src="deployment/main/images/nettracer/icon_printrpt.gif"
              width="12" height="12"></a></td>
            <td><a href="javascript:window.print();"><bean:message
              key="print_report" /></a> &nbsp;</td>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.MISSING_RECEPIT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','MissingReceipt',800,600);return false;"><img
              src="deployment/main/images/nettracer/icon_printrecpt.gif"
              width="12" height="12"></a></td>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.MISSING_RECEPIT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','MissingReceipt',800,600);return false;"><bean:message
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
          	/*
          																																																										           <dd>
          																																																										             <a href="#incidentinfo"><span class="aa">&nbsp;
          																																																										                 <br />
          																																																										                 &nbsp;</span>
          																																																										               <span class="bb"><bean:message key="menu.incident_info" /></span>
          																																																										               <span class="cc">&nbsp;
          																																																										                 <br />
          																																																										                 &nbsp;</span></a>
          																																																										           </dd>
          				 */
          %>
          <dd><a href="#contact"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.contact_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <dd><a href="#passit"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.itinerary" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <!--<dd><a href="#checkedbaggage"><span class="aa">&nbsp;<br />&nbsp;</span><span class="bb"><bean:message key="menu.checked_bag_info"/></span><span class="cc">&nbsp;<br />&nbsp;</span></a></dd>-->
          <dd><a href="#checkedbaggage"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.bag_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <dd><a href="#missingarticles"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message key="menu.ma" /></span>
          <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <dd><a href="#interimexpense"><span class="aa">&nbsp;
            <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.interim_expense_payout" /></span> <span class="cc">&nbsp;
            <br />
            &nbsp;</span></a></dd>
          </logic:notEqual>
          <dd><a href="#remarks"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.remarks" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
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
            <dd><a href="missing.do?close=1"><span class="aa">&nbsp;
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
        <jsp:include page="/pages/includes/mbrbag_incl.jsp" /> <!-- missing articles -->
        <a name="missingarticles"></a>
        <h1 class="green"><bean:message key="header.ma" /> <a
          href="#"
          onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/creating_missing_article_reports.htm');return false;"><img
          src="deployment/main/images/nettracer/button_help.gif"
          width="20" height="21" border="0"></a></h1>
        <span class="reqfield">*</span> <bean:message
          key="message.required" /> <logic:iterate id="article"
          indexId="i" name="incidentForm" property="articlelist">
          <table class="<%=cssFormClass %>" cellspacing="0"
            cellpadding="0">
            <tr>
              <td><a name='addarticle<%= i %>'></a> <bean:message
                key="colname.article" /> <br>
              <html:text name="article" property="article" size="30"
                maxlength="50" styleClass="textfield" indexed="true" />
              </td>
              <td><bean:message key="colname.purchase_date" /> <br>
              <html:text name="article" property="dispurchasedate"
                size="15" maxlength="10" styleClass="textfield"
                indexed="true" /><img
                src="deployment/main/images/calendar/calendar_icon.gif"
                id="calendar5<%= i %>" name="calendar5<%= i %>"
                height="15" width="20" border="0"
                onmouseover="this.style.cursor='hand'"
                onClick="cal1xx.select2(document.incidentForm, '<%= "article[" + i + "].dispurchasedate" %>','calendar5<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              <td><bean:message key="colname.cost" /> <br>
              <html:text name="article" property="discost" size="13"
                maxlength="12" styleClass="textfield" indexed="true" />
              </td>
              <td><bean:message key="colname.currency" /> <br>
              <html:select name="article" property="currency_ID"
                styleClass="dropdown" indexed="true">
                <html:options collection="currencylist"
                  property="currency_ID" labelProperty="id_desc" />
              </html:select></td>
            </tr>
            <tr>
              <td colspan=4><bean:message key="colname.desc" /> <br>
              <html:textarea name="article" property="description"
                cols="80" rows="5" styleClass="textarea_medium"
                indexed="true" /></td>
            </tr>
            <tr>
              <td colspan=4><html:submit styleId="button"
                property="deleteArticle" indexed="true">
                <bean:message key="button.delete_article" />
              </html:submit></td>
            </tr>
          </table>
        </logic:iterate>
        <center><html:submit property="addarticles"
          styleId="button">
          <bean:message key="button.add_articles" />
        </html:submit></center>
        <br>
        <br>
        &nbsp;&nbsp;&uarr; <a href="#"><bean:message
          key="link.to_top" /></a> <br>
        <br>
        <jsp:include page="/pages/includes/remark_incl.jsp" />
        </div>
        <logic:notEqual name="incidentForm" property="readonly"
          value="1">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" valign="top"><br>
              <html:submit property="save" styleId="button"
                onclick="return validatereqFields(this.form, 'pilfered');">
                <bean:message key="button.saveincident" />
              </html:submit></td>
            </tr>
          </table>
          <br>
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

<logic:present name="item" scope="request">
	document.location.href='#additem<bean:write name="item" scope="request" />';
</logic:present>

<logic:present name="inventory" scope="request">
	document.location.href='#inventory<bean:write name="inventory" scope="request" />';
</logic:present>

<logic:present name="passenger" scope="request">
	document.location.href='#addpassenger<bean:write name="passenger" scope="request" />';
</logic:present>

<logic:present name="remark" scope="request">
	document.location.href="#addremark<bean:write name="remark" scope="request" />";
</logic:present>

<logic:present name="articles" scope="request">
	document.location.href="#addarticle<bean:write name="articles" scope="request" />";
</logic:present>


//-->
    </script>
  </html:form>

</logic:notPresent>