<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <jsp:include page="../includes/validation_incl.jsp" />
  <script language="javascript">
    <!--
function goprev() {
  o = document.interimExpenseRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.interimExpenseRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.interimExpenseRequestForm;
  o.currpage.value = i;
  o.submit();
}

function sortInterimExpense(sortOrder) {
	o = document.interimExpenseRequestForm;
	o.sort.value = sortOrder;
	o.submit();
}

// -->
  </script>
  <html:form action="expenseRequests.do" method="post">
    <jsp:include page="../includes/taskmanager_header.jsp" />
<%
    String sort = (String)request.getAttribute("sort");

    if (sort != null && sort.length() > 0) {
%>
      <input type=hidden name=sort value='<%= sort %>'>
<%
    } else {
%>
      <input type=hidden name=sort value="">
<%
    }
%>
    <input type=hidden name=approve1 value="">
    <input type=hidden name=payout_ID value="">
    <input type=hidden name=deny1 value="">
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_interim_expense" />
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
                <bean:message key="colname.incident_num" />
                :
              </td>
              <td>
                <html:text property="incident_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
              <td>
                <bean:message key="header.status" />
                :
              </td>
              <td>
                <html:select property="payout_status" styleClass="dropdown">
                  <html:option value="-1">
                    All
                  </html:option>
                  <html:options collection="expense_status_list" property="status_ID" labelProperty="description" />
                </html:select>
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
            <bean:message key="header.interimexpenserequests" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/incoming_bags.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <logic:present name="cbroStationID" scope="session">
<%
                if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                  <td>
                    <b><bean:message key="header.batch_approve" /></b>
                  </td>
<%
                }
%>
              </logic:present>
              <logic:notPresent name="cbroStationID" scope="session">
                <td>
                  <b><bean:message key="header.batch_approve" /></b>
                </td>
              </logic:notPresent>
              <td>
                <strong>
                  <a href="#" onclick="sortInterimExpense('incident');"><bean:message key="colname.incident_num" /></a>
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="claim" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.draft" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.checkamt" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.voucheramt" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="colname.mileageamt" />
                </strong>
              </td>
              <td>
                <strong>
                  <bean:message key="header.status" />
                </strong>
              </td>
              <td>
                <b><bean:message key="header.action" /></b>
              </td>
            </tr>
            <logic:present name="expenseList" scope="request">
              <logic:iterate id="expenselist" name="expenseList" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
                <tr>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <logic:equal name="expenselist" property="status.description" value="Pending">
                          <input type="checkbox" name="code" value="<bean:write name="expenselist" property="expensepayout_ID"/>">
                        </logic:equal>
                        &nbsp;
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <logic:equal name="expenselist" property="status.description" value="Pending">
                        <input type="checkbox" name="code" value="<bean:write name="expenselist" property="expensepayout_ID"/>">
                      </logic:equal>
                      &nbsp;
                    </td>
                  </logic:notPresent>
                  <td>
                    <a href='searchIncident.do?incident=<bean:write name="expenselist" property="claim.incident.incident_ID"/>'><bean:write name="expenselist" property="claim.incident.incident_ID" /></a>
                  </td>
                  <td>
                    <a href='claim_resolution.do?approveinterim=1&incidentid=<bean:write name="expenselist" property="claim.incident.incident_ID"/>&exp_id=<bean:write name="expenselist" property="expensepayout_ID"/>'><bean:message key="details" /></a>
                  </td>
                  <td>
                    <bean:write name="expenselist" property="draft" />
                    &nbsp;
                  </td>
                  <td align="right" nowrap>
                    <bean:write name="expenselist" property="discheckamt" />
                    &nbsp;
                    <bean:write name="expenselist" property="currency_ID" />
                  </td>
                  <td align="right">
                    &nbsp;
                    <bean:write name="expenselist" property="disvoucheramt" />
                  </td>
                  <td align="right">
                    &nbsp;
                    <bean:write name="expenselist" property="mileageamt" />
                  </td>
                  <td align="left">
                    <bean:write name="expenselist" property="status.description" />
                  </td>
                  <logic:equal name="expenselist" property="status.description" value="Pending">
                    <logic:present name="cbroStationID" scope="session">
<%
                      if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                        <td>
                          <a href="expenseRequests.do?approve=1&payout_ID=<bean:write name="expenselist" property="expensepayout_ID"/>"><bean:message key="approve" /></a>
                          &nbsp;
                          <a href="expenseRequests.do?deny=1&payout_ID=<bean:write name="expenselist" property="expensepayout_ID"/>"><bean:message key="deny" /></a>
                        </td>
<%
                      }
%>
                    </logic:present>
                    <logic:notPresent name="cbroStationID" scope="session">
                      <td>
                        <a href="expenseRequests.do?approve=1&payout_ID=<bean:write name="expenselist" property="expensepayout_ID"/>"><bean:message key="approve" /></a>
                        &nbsp;
                        <a href="expenseRequests.do?deny=1&payout_ID=<bean:write name="expenselist" property="expensepayout_ID"/>"><bean:message key="deny" /></a>
                      </td>
                    </logic:notPresent>
                  </logic:equal>
                  <logic:notEqual name="expenselist" property="status.description" value="Pending">
                    <td>
                      N/A
                    </td>
                  </logic:notEqual>
                </tr>
              </logic:iterate>
              <!-- pagination -->
              <tr>
                <td colspan="11">
                  <jsp:include page="../includes/pagination_incl.jsp" />
                </td>
              </tr>
              <!-- end pagination -->
            </logic:present>
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="11" align="center">
                <INPUT type="button" value="Back" onClick="history.back()" Id="button">
                &nbsp;
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <INPUT type="button" value="Batch Approve" onClick="batchApprove()" Id="button">
                    &nbsp;&nbsp;
                    <INPUT type="button" value="Batch Deny" onClick="batchDeny()" Id="button">
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <INPUT type="button" value="Batch Approve" onClick="batchApprove()" Id="button">
                  &nbsp;&nbsp;
                  <INPUT type="button" value="Batch Deny" onClick="batchDeny()" Id="button">
                </logic:notPresent>
              </td>
            </tr>
          </table>
        </html:form>
