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
  o = document.auditStationForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditStationForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditStationForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}

// -->
</script>
<%
  List compareList = (List)request.getAttribute("compareList");
%>
  <html:form action="audit_station.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_station" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="../../../includes/mail_incl.jsp" />
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
              <a href="audit_station.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_Station" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
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
                <bean:message key="header.stationDesc" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="stationdesc" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.locale" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="locale" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.street_addr" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="address1" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.street_addr" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="address2" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.city" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="city" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.state" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="state" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.country" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="country" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.zip" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="zip" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.phone" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="phone" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.associated_airport" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="associated_airport" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.airport_location" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="airport_location" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.operation_hours" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="operation_hours" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
          </table>
          <br>
          <center><INPUT type="button" Id="button" value="Back" onClick="history.back()"></center>
        </html:form>
