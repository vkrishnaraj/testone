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
<html:form action="fraudRequests.do" method="post">
  <input type="hidden" name="request_ID" value='<%= request.getAttribute("deny_ID") %>'>
  <input type="hidden" name="deny" value="1">
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.request_deny_request" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_incoming_requests.htm#forward_requested_bag');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.reference.id" />
                :
              </td>
              <td>
                <bean:message key="colname.companycode.request" />
                :
              </td>
            </tr>
            <tr>
              <td>
              	<bean:write scope="request" name="claim_ID" />
              </td>
              <td>
              	<bean:write scope="request" name="airlineRequest" />
              </td>
            </tr>
            <tr>
              <td colspan=2>
                <bean:message key="colname.deny.message" />
                :
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <html:textarea property="message" rows="10" cols="80" />
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
    </html:form>
