<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    
function goprev() {
  o = document.viewCreatedRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.viewCreatedRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewCreatedRequestForm;
  o.currpage.value = i;
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  <jsp:include page="/pages/includes/validation_incl.jsp" />
  <html:form action="viewCreatedROH.do" method="post" onsubmit="fillzero(this.ohd_num, 13); fillzero(this.incident_ID, 13); return validateRest(this);">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_created_requests" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="header.status" />
                :
              </td>
              <td>
                <html:select property="status" styleClass="dropdown">
                  <html:option value="-1">
                    All
                  </html:option>
                  <html:options collection="ohd_request_status_list" property="status_ID" labelProperty="description" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.on_hand_report_file_number" />
                :
              </td>
              <td>
                <html:text property="ohd_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
              <td>
                <bean:message key="colname.incident_file_num" />
                :
              </td>
              <td>
                <html:text property="incident_ID" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td colspan="4" align="center">
                <br>
                <html:submit styleId="button" property="search">
                  <bean:message key="button.search" />
                </html:submit>
                &nbsp;
                <html:reset styleId="button">
                  <bean:message key="button.reset" />
                </html:reset>
              </td>
            </tr>
          </table>
          <br>
          <h1 class="green">
            <bean:message key="header.search_result" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#My_Task_Manager.htm#View_Created_Requests');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <b><bean:message key="header.ohd" /></b>
              </td>
              <td>
                <b><bean:message key="header.file" /></b>
              </td>
              <td>
                <b><bean:message key="header.match" /></b>
              </td>
              <td>
                <b><bean:message key="header.status" /></b>
              </td>
              <td>
                <b><bean:message key="header.request" /></b>
              </td>
            </tr>
            <logic:present name="requestList" scope="request">
              <logic:iterate id="req" name="requestList">
                <tr>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="req" property="ohd.OHD_ID"/>"><bean:write name="req" property="ohd.OHD_ID" /></a>
                  </td>
                  <td>
                    <logic:present name="req" property="incident_ID">
                      <A HREF="searchIncident.do?incident=<bean:write name="req" property="incident_ID"/>"><bean:write name="req" property="incident_ID" /></a>
                    </logic:present>
                    <logic:notPresent name="req" property="incident_ID">
                      &nbsp;
                    </logic:notPresent>
                  </td>
                  <logic:notEqual name="req" property="match_id" value="0">
                    <td>
                      <A HREF="viewMatches.do?showMatch=1&match_ID=<bean:write name="req" property="match_id"/>"><bean:message key="details" /></a>
                    </td>
                  </logic:notEqual>
                  <logic:equal name="req" property="match_id" value="0">
                    <td>
                      &nbsp;
                    </td>
                  </logic:equal>
                  <td>
                    <logic:equal name="req" property="status.status_ID" value="<%= "" + TracingConstants.OHD_REQUEST_STATUS_FORWARDED %>">
                      <A HREF="forward_on_hand.do?showForward=1&forward_id=<bean:write name="req" property="forward_id"/>"><bean:message name="req" property="status.key" /></a>
                    </logic:equal>
                    <logic:notEqual name="req" property="status.status_ID" value="<%= "" + TracingConstants.OHD_REQUEST_STATUS_FORWARDED %>">
                      <bean:message name="req" property="status.key" />
                    </logic:notEqual>
                  </td>
                  <td>
                    <A HREF="request_on_hand.do?showRequest=1&request_ID=<bean:write name="req" property="ohd_request_id"/>"><bean:message key="details" /></a>
                  </td>
                </tr>
              </logic:iterate>
              
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  
                </td>
              </tr>
              
            </logic:present>
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="11" align="center">
                <INPUT Id="button" type="button" value="Back" onClick="history.back()" Id="button">
                &nbsp;
              </td>
            </tr>
          </table>
        </html:form>
