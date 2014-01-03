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

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	


</SCRIPT>

<script language="javascript">
  
function goprev() {
  o = document.auditAgentForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditAgentForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditAgentForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

</script>
<%
  List compareList = (List)request.getAttribute("compareList");
%>
  <html:form action="audit_agent.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_agent" />
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
              <a href="audit_agent.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_Agent" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
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
      
      <td id="middlecolumn">
        
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
                <bean:message key="header.agentStation" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="station.stationcode" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentGroup" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="group.description" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentFname" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="firstname" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentMname" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="mname" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentLname" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="lastname" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentTimeout" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="timeout" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentActive" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="active" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="active" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentWebEnabled" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="web_enabled" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="web_enabled" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentWsEnabled" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="ws_enabled" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="ws_enabled" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
			<tr>
              <td>
                <bean:message key="header.agentMaxWsSessions" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="max_ws_sessions" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentDefaultTimezone" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="dtZone" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentCurrentTimezone" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="ctZone" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentDefaultLocale" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="defaultlocale" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentCurrLocale" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="currentlocale" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentDefCurrency" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="defaultcurrency" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentDateformat" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="dateformat.format" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentTimeformat" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_lfi" property="timeformat.format" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.shift_description" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:present name="audit_lfi" property="shift">
                    <bean:write name="audit_lfi" property="shift.shift_code" />
                  </logic:present>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.accountLocked" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="account_locked" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="account_locked" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.agentInboundQueue" />
              </td>
              <logic:iterate id="audit_lfi" name="compareList" scope="request">
                <td>
                  <logic:equal name="audit_lfi" property="inboundQueue" value="true">
                    <bean:message key="select.yes" />
                  </logic:equal>
                  <logic:notEqual name="audit_lfi" property="inboundQueue" value="true">
                    <bean:message key="select.no" />
                  </logic:notEqual>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
          </table>
          <br>
          <center><INPUT type="button" Id="button" value="Back" onClick="history.back()"></center>
        </html:form>
