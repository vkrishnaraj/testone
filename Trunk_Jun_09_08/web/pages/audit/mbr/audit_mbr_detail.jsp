<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
<%@page import="com.bagnet.nettracer.tracing.db.audit.Audit_Incident"%><script language="javascript">
    
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
function updatePagination() {
    return true;
}

  </script>
  
  <html:form action="audit_mbr.do" method="post">
    <input type="hidden" name="detail" value="1">
    <input type="hidden" name="audit_incident_id">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_mbr_modification_history" />
            (
            <bean:write name="incident_ID" scope="request" />
            )
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
              <a href="audit_mbr.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_Mishandled_Report" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
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
      
      <td id="middlecolumn">
        
        <div id="maincontent">
<%
          String incident_id = (String)request.getAttribute("incident_ID");
%>
<%
          if (incident_id != null) {
%>
            <input type="hidden" name="incident_ID" value="<%= incident_id %>">
<%
          }
%>
          <logic:present name="auditmbrlist" scope="request">
          
          <a name="result"></a>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <strong>
                  <bean:message key="colname.select" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.mbr_report_num" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.modifiedOn" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.modifiedBy" />
                </strong>
              </td>
            </tr>
            <logic:iterate id="aincident" name="auditmbrlist" type="com.bagnet.nettracer.tracing.db.audit.Audit_Incident">
              <tr>
                <td>
                  <input type="checkbox" name="audit_id" value="<bean:write name="aincident" property="audit_incident_id"/>">
                </td>
                <td>
                  <a href="searchIncident.do?incident=<bean:write name="aincident" property="incident_ID"/>"><bean:write name="aincident" property="incident_ID" /></a>
                </td>
                <td>
                  <bean:write name="aincident" property="dispmodify_time" />
                </td>
                <td>
<% if (((Audit_Incident)aincident).getModify_agent() != null) { out.println(((Audit_Incident)aincident).getModify_agent().getUsername()); } else {out.println("Customer");}%>
                </td>
              </tr>
            </logic:iterate>
            <tr>
              <td colspan="11">
                
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
                
              </td>
            </tr>
            
            <tr>
              <td colspan="11">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="11" align="center">
                <INPUT type="button" Id="button" value="Back" onClick="history.back()">
                &nbsp;
                <INPUT type="button" Id="button" value="<bean:message key="button.compare.file.details"/>" onClick="submitCompareMBRform()">
              </td>
            </tr>
          </table>
        </logic:present>
      </html:form>
