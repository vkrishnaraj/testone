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
  
  <script language="javascript">
    

function goprev() {
  o = document.createdInterimExpenseForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.createdInterimExpenseForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.createdInterimExpenseForm;
  o.currpage.value = i;
  o.submit();
}
function updatePagination() {
    return true;
}
function sortInterimExpense(sortOrder) {
	o = document.createdInterimExpenseForm;
	o.sort.value = sortOrder;
	o.submit();
}


  </script>
  <html:form action="createdExpenseRequests.do" method="post" onsubmit="fillzero(this.incident_num, 13); return true;">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
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
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_created_interim_expense" />
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
                <html:select property="expense_status" styleClass="dropdown">
                  <html:option value="-1">
                    All
                  </html:option>
                  <html:options collection="expenseStatusList" property="status_ID" labelProperty="description" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.incident_num" />
                :
              </td>
              <td colspan="3">
                <html:text property="incident_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
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
            <bean:message key="header.createdinterimexpenserequests" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/incoming_bags.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <strong>
                  <a href="#" onclick="sortInterimExpense('incident');"><bean:message key="colname.incident_num" /></a>
                </strong>
              </td>
              <td>
                <strong>
                 <bean:message key="colname.ohd_num" />
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
                  <a href="#" onclick="sortInterimExpense('status');"><bean:message key="header.status" /></a>
                </strong>
              </td>
            </tr>
            <logic:present name="expenseList" scope="request">
              <logic:iterate id="expenselist" name="expenseList" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
                <tr>
                  <td>
                    <logic:notEmpty name="expenselist" property="incident">
                      <a href='searchIncident.do?incident=<bean:write name="expenselist" property="incident.incident_ID"/>'><bean:write name="expenselist" property="incident.incident_ID" /></a>
                    </logic:notEmpty>
                    &nbsp;
                  </td>
                  <td>
                    <logic:notEmpty name="expenselist" property="ohd">
                      <a href='addOnHandBag.do?ohd_ID=<bean:write name="expenselist" property="ohd.OHD_ID"/>'><bean:write name="expenselist" property="ohd.OHD_ID" /></a>
                    </logic:notEmpty>
                    &nbsp;
                  </td>
                  <td>
                    <a href='EditExpense.do?expense_id=<bean:write name="expenselist" property="expensepayout_ID"/>'><bean:message key="details" /></a>
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
                  <td>
                    <bean:message name="expenselist" property="status.key" />
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
          </table>
        </html:form>
