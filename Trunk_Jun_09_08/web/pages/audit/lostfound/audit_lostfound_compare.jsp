<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident" %>
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
  o = document.auditLostFoundForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditLostFoundForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditLostFoundForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}

// -->
</script>
<%
  List compareList = (List)request.getAttribute("compareList");
%>
  <html:form action="audit_unchecked.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_unchecked" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="../../includes/mail_incl.jsp" />
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
              <a href="audit_unchecked.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_Unchecked_Property" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
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
              <a href="audit_claims.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Claims" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
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
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <strong>
                    <bean:message key="colname.modifiedOn" />
                    :
                    <bean:write name="audit_lfi" property="displaytime_modified" />
                    (
                    <bean:write name="audit_lfi" property="modifying_agent.username" />
                    )
                  </strong>
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.status" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="report_status.description" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.disposal_status" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:present name="audit_lfi" property="disposal_status">
                    <bean:write name="audit_lfi" property="disposal_status.description" />
                  </logic:present>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.file_close_date" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="report_status.status_ID" value='<%= "" + TracingConstants.LOST_FOUND_CLOSED %>'>
                    <bean:write name="audit_lfi" property="dispCloseDateTime" />
                  </logic:equal>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.closed_by" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:present name="audit_lfi" property="closing_agent">
                    <bean:write name="audit_lfi" property="closing_agent.username" />
                  </logic:present>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.passenger_info" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:message key="colname.last_name" />
                  :
                  <bean:write name="audit_lfi" property="customer_lastname" />
                  &nbsp;
                  <bean:message key="colname.first_name" />
                  :
                  <bean:write name="audit_lfi" property="customer_firstname" />
                  &nbsp;
                  <bean:message key="colname.mid_initial" />
                  :
                  <bean:write name="audit_lfi" property="customer_mname" />
                  <br>
                  <bean:message key="colname.street_addr1" />
                  :
                  <bean:write name="audit_lfi" property="customer_address1" />
                  <br>
                  <bean:message key="colname.street_addr2" />
                  :
                  <bean:write name="audit_lfi" property="customer_address2" />
                  <br>
                  <bean:message key="colname.city" />
                  :
                  <bean:write name="audit_lfi" property="customer_city" />
                  <br>
                  <bean:message key="colname.state" />
                  :
                  <bean:write name="audit_lfi" property="state" />
                  <bean:message key="colname.province" />
                  :
                  <bean:write name="audit_lfi" property="customer_province" />
                  <br>
                  <bean:message key="colname.zip" />
                  :
                  <bean:write name="audit_lfi" property="customer_zip" />
                  <bean:message key="colname.country" />
                  :
                  <bean:write name="audit_lfi" property="country" />
                  <br>
                  <bean:message key="colname.home_ph" />
                  :
                  <bean:write name="audit_lfi" property="customer_tel" />
                  <bean:message key="colname.email" />
                  :
                  <bean:write name="audit_lfi" property="customer_email" />
                </td>
              </logic:iterate>
            </tr>
<%
            Audit_LostAndFoundIncident incident = (Audit_LostAndFoundIncident)compareList.get(0);
            String                     found    = "0";

            if (incident.getReport_type() == 1) {
              found = "1";
            }
%>
<%
            if (found.equals("0")) {
%>
              <tr>
                <td>
                  <bean:message key="colname.found_by" />
                </td>
                <logic:iterate id="audit_lfi" name="compareList" scope="request">
                  <td>
                    <bean:write name="audit_lfi" property="finding_agent_name" />
                    &nbsp;
                  </td>
                </logic:iterate>
              </tr>
<%
            }
%>
            <tr>
              <td>
<%
                if (found.equals("1")) {
%>
                  <bean:message key="colname.date_lost" />
<%
                } else {
%>
                  <bean:message key="colname.date_found" />
<%
                }
%>
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="dispDateFoundLost" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
<%
                if (found.equals("1")) {
%>
                  <bean:message key="colname.lost_location" />
<%
                } else {
%>
                  <bean:message key="colname.found_location" />
<%
                }
%>
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="location" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.idescription" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="item_description" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.remarks" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="remark" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
          </table>
          <br>
          <center><INPUT type="button" Id="button" value="Back" onClick="history.back()"></center>
        </html:form>
