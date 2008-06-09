<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils" %>
<%@ page import="java.util.List" %>
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

// -->
</script>
<html:form action="audit_claims.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.audit_claims" />
          (
          <bean:write name="incident_ID" scope="request" />
          )
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
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <strong>
                <bean:message key="colname.field" />
              </strong>
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <strong>
                  <bean:message key="colname.modifiedOn" />
                  :
                  <bean:write name="audit_claims" property="dispmodify_time" />
                  (
                  <bean:write name="audit_claims" property="modify_agent.username" />
                  )
                </strong>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.claim_status" />
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <logic:present name="audit_claims" property="status">
                  <bean:write name="audit_claims" property="status.description" />
                </logic:present>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.claim_amount" />
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <bean:write name="audit_claims" property="disclaimamount" />
                &nbsp;
                <bean:write name="audit_claims" property="claimcurrency_ID" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.ssn" />
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <bean:write name="audit_claims" property="ssn" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.reason" />
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <bean:write name="audit_claims" property="modify_reason" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="header.audit_payouts" />
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <logic:iterate indexId="i" id="expenselist" name="audit_claims" property="expense_list" type="com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout">
                  <bean:message key="colname.createdate" />
                  :
                  <bean:write name="expenselist" property="discreatedate" />
                  <br>
                  <bean:message key="colname.expense_loc" />
                  :
                  <bean:write name="expenselist" property="expenselocation.stationcode" />
                  <bean:message key="colname.expense_type" />
                  :
                  <bean:write name="expenselist" property="expensetype.description" />
                  <br>
                  <bean:message key="colname.paycode" />
                  :
                  <bean:write name="expenselist" property="paycode" />
                  <br>
                  <bean:message key="colname.draft" />
                  :
                  <bean:write name="expenselist" property="draft" />
                  <br>
                  <bean:message key="colname.draftreqdate" />
                  :
                  <bean:write name="expenselist" property="disdraftreqdate" />
                  <br>
                  <bean:message key="colname.draftpaiddate" />
                  :
                  <bean:write name="expenselist" property="disdraftpaiddate" />
                  <br>
                  <bean:message key="colname.checkamt" />
                  :
                  <bean:write name="expenselist" property="discheckamt" />
                  <bean:write name="expenselist" property="currency_ID" />
                  <br>
                  <bean:message key="colname.voucheramt" />
                  :
                  <bean:write name="expenselist" property="disvoucheramt" />
                  <br>
                  <bean:message key="colname.mileageamt" />
                  :
                  <bean:write name="expenselist" property="mileageamt" />
                  <br>
                  <bean:message key="colname.comments" />
                  :
                  <bean:write name="expenselist" property="comments" />
                  <br>
                  <bean:message key="colname.reason" />
                  :
                  <br>
                  <bean:write name="expenselist" property="modify_reason" />
                  <br>
                  <br>
                </logic:iterate>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="header.claim_prorate" />
            </td>
            <logic:iterate id="audit_claims" name="compareList" scope="request">
              <td>
                <logic:present name="audit_claims" property="audit_claimprorate">
                  <logic:iterate id="it_list" indexId="k" name="audit_claims" property="audit_claimprorate.pi_list">
                    <bean:message key="colname.airlineflightdate" />
                    :
                    <bean:write name="it_list" property="airline" />
                    /
                    <bean:write name="it_list" property="flightnum" />
                    /
                    <bean:write name="it_list" property="disdepartdate" />
                    <br>
                    <bean:message key="colname.from" />
                    :
                    <bean:write name="it_list" property="legfrom" />
                    <br>
                    <bean:message key="colname.to" />
                    :
                    <bean:write name="it_list" property="legto" />
                    <br>
                    <bean:message key="colname.statute_miles" />
                    :
                    <bean:write name="it_list" property="dismiles" />
                    <br>
                    <bean:message key="colname.percentage" />
                    :
                    <bean:write name="it_list" property="dispercentage" />
                    <br>
                    <bean:message key="colname.share" />
                    :
                    <bean:write name="it_list" property="disshare" />
                    <br>
                    <bean:message key="colname.currency" />
                    :
                    <bean:write name="it_list" property="currency_ID" />
                    <br>
                    <br>
                  </logic:iterate>
                </logic:present>
              </td>
            </logic:iterate>
          </table>
        </html:form>
