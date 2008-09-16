<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.AuditTrailUtils" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    <!--
function goprev() {
  o = document.auditTrailForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditTrailForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditTrailForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}
// -->
  </script>
  <html:form action="auditTrail.do" method="post">
    <html:hidden property="auditType" />
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_trail" />
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
    <!-- ICONS MENU -->
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
<%
            String b = "", c = "", d = "", e = "", f = "";
%>
            <logic:equal name="auditTrailForm" property="auditType" value="<%= new Integer(AuditTrailUtils.AUD_CLAIM).toString() %>">
<%
              b = "b";
%>
            </logic:equal>
            <logic:equal name="auditTrailForm" property="auditType" value="<%= new Integer(AuditTrailUtils.AUD_PAYOUT).toString() %>">
<%
              c = "b";
%>
            </logic:equal>
            <logic:equal name="auditTrailForm" property="auditType" value="<%= new Integer(AuditTrailUtils.AUD_INCIDENT).toString() %>">
<%
              d = "b";
%>
            </logic:equal>
            <dd>
              <a href="auditTrail.do?audit=<%= AuditTrailUtils.AUD_CLAIM %>&auditParam=<bean:write name="auditTrailForm" property="auditParam"/>"><span class="aa<%= b %>">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb<%= b %>"><bean:message key="menu.audittrail.claim" /></span>
                <span class="cc<%= b %>">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="auditTrail.do?audit=<%= AuditTrailUtils.AUD_PAYOUT %>&auditParam=<bean:write name="auditTrailForm" property="auditParam"/>"><span class="aa<%= c %>">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb<%= c %>"><bean:message key="menu.audittrail.payout" /></span>
                <span class="cc<%= c %>">&nbsp;
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
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.incident_num" />
                  :
                </td>
                <td>
                  <html:text property="auditParam" size="15" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
              </tr>
              <tr>
                <td colspan="6" align="center" valign="top">
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
            <logic:present name="resultlist" scope="request">
              <logic:equal name="auditTrailForm" property="auditType" value="<%= new Integer(AuditTrailUtils.AUD_CLAIM).toString() %>">
                <jsp:include page="/pages/includes/audit/audit_claim_incl.jsp" />
              </logic:equal>
              <logic:equal name="auditTrailForm" property="auditType" value="<%= new Integer(AuditTrailUtils.AUD_PAYOUT).toString() %>">
                <jsp:include page="/pages/includes/audit/audit_payout_incl.jsp" />
              </logic:equal>
              <!-- pagination -->
              <jsp:include page="/pages/includes/pagination_incl.jsp" />
            </td>
          </tr>
          <!-- end pagination -->
        </table>
      </logic:present>
    </html:form>
