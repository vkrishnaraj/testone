<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<html:form action="request_on_hand.do" method="post">
  <SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }
    // End -->
  </SCRIPT>
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.request_on_hand_title" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#create transfer request');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.ohd_ID" />
                :
              </td>
              <td>
                <html:hidden name="requestOnHandForm" property="ohd_ID" />
                <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="requestOnHandForm" property="ohd_ID"/>"><bean:write name="requestOnHandForm" property="ohd_ID" /></a>
              </td>
            </tr>
            <logic:present name="requestOnHandForm" property="incident_ID">
              <tr>
                <td>
                  <bean:message key="colname.incident_num" />
                  :
                </td>
                <td>
                  <html:hidden name="requestOnHandForm" property="incident_ID" />
                  <A HREF="searchIncident.do?incident=<bean:write name="requestOnHandForm" property="incident_ID"/>"><bean:write name="requestOnHandForm" property="incident_ID" /></a>
                </td>
              </tr>
            </logic:present>
            <logic:present name="requestOnHandForm" property="match_ID">
              <tr>
                <td>
                  <bean:message key="header.match" />
                  :
                </td>
                <td>
                  <html:hidden name="requestOnHandForm" property="match_ID" />
                  <A HREF="viewMatches.do?showMatch=1&match_ID=<bean:write name="requestOnHandForm" property="match_ID"/>"><bean:write name="requestOnHandForm" property="match_ID" /></a>
                </td>
              </tr>
            </logic:present>
            <tr>
              <td>
                <bean:message key="colname.requestReason" />
                :
              </td>
              <td>
                <html:textarea styleClass="textarea" property="reason" cols="65" rows="20" onkeydown="textCounter(this.form.reason,this.form.remLen,1500);" onkeyup="textCounter(this.form.reason,this.form.remLen,1500);" />
                <input disabled="true" type="text" name=remLen size=4 maxlength=4 value="1500">
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                <INPUT id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
                <html:submit styleId="button" property="save">
                  <bean:message key="button.request" />
                </html:submit>
              </td>
            </tr>
          </table>
        </html:form>
