<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<SCRIPT LANGUAGE="JavaScript">
  function textCounter(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } else {
      countfield.value = maxlimit - field.value.length;
    }
  }

</SCRIPT>
<html:form action="request_on_hand.do" method="post">
  <input type="hidden" name="request_ID" value='<%= request.getAttribute("request_ID") %>'>
  <input type="hidden" name="deny" value="1">
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.request_deny_title" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_incoming_requests.htm#forward_requested_bag');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <br>
        <logic:present name="ohd_request" scope="request">
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.ohd_ID" />
                :
              </td>
              <td>
                <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd_request" property="ohd.OHD_ID"/>"><bean:write name="ohd_request" property="ohd.OHD_ID" /></a>
              </td>
            </tr>
            <logic:present name="ohd_request" property="incident_ID">
              <tr>
                <td>
                  <bean:message key="colname.incident_num" />
                  :
                </td>
                <td>
                  <A HREF="searchIncident.do?incident=<bean:write name="ohd_request" property="incident_ID"/>"><bean:write name="ohd_request" property="incident_ID" /></a>
                </td>
              </tr>
            </logic:present>
            <logic:present name="ohd_request" property="match_ID">
              <tr>
                <td>
                  <bean:message key="header.match" />
                  :
                </td>
                <td>
                  <A HREF="viewMatches.do?showMatch=1&match_ID=<bean:write name="ohd_request" property="match_id"/>"><bean:write name="ohd_request" property="match_id" /></a>
                </td>
              </tr>
            </logic:present>
            <tr>
              <td>
                <bean:message key="header.request_station" />
                :
              </td>
              <td>
                <bean:write name="ohd_request" property="requestForStation.company.companyCode_ID" />
                &nbsp;
                <bean:write name="ohd_request" property="requestForStation.stationcode" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="header.status" />
                :
              </td>
              <td>
                <bean:message name="ohd_request" property="status.key" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.requestReason" />
                :
              </td>
              <td>
                <html:textarea name="ohd_request" property="reason" cols="70" rows="20" readonly="true" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.denialRequestReason" />
                :
              </td>
              <td>
                <textarea name="denialReason" cols="70" rows="20" onkeydown="textCounter(this.form.denialReason,this.form.remLen,1500);" onkeyup="textCounter(this.form.denialReason,this.form.remLen,1500);" /></textarea>
              <input disabled="true" type="text" name=remLen size=4 maxlength=4 value="1500">
            </td>
          </tr>
          <tr>
            <td colspan="2">
              &nbsp;
            </td>
          </tr>
          <tr>
            <td align="center" colspan="2">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="done_deny">
                <bean:message key="button.denyRequest" />
              </html:submit>
            </td>
          </tr>
        </table>
      </logic:present>
    </html:form>
