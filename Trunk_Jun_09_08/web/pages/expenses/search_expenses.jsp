<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  boolean canApprove = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, a);
%>
  
  <%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="com.bagnet.nettracer.tracing.forms.SearchExpenseForm"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  <script language="javascript">
    
function goprev() {
  o = document.searchExpenseForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchExpenseForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchExpenseForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}


  </script>
  
  <html:form action="SearchExpenses.do" method="post" onsubmit="fillzero(this.incident_id, 13);">
   <input type=hidden name=approve1 value="">
    <input type=hidden name=payout_ID value="">
    <input type=hidden name=deny1 value="">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.search_payouts" />
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
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td width=33%>
                  <bean:message key="colname.incident_num" />
                  <br>
                  <html:text property="incident_id" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td width=33%>
                  <bean:message key="createdate.start"/>
                  <br />
                  <input type="text" name="createStartDate" class="textfield" size="10" maxlength="10" value="<c:out value='${searchExpenseForm.createStartDate}'/>" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchExpenseForm.createStartDate, 'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
                <td width=33%>
                  <bean:message key="createdate.end" />
                  <br>
                  <input type="text" name="createEndDate" class="textfield" size="10" maxlength="10" value="<c:out value='${searchExpenseForm.createEndDate}'/>" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchExpenseForm.createEndDate, 'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="approval.startdate"/>
                  <br>
                  <input type="text" name="approveStartDate" class="textfield" size="10" maxlength="10" value="<c:out value='${searchExpenseForm.approveStartDate}'/>" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchExpenseForm.approveStartDate, 'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
                <td>
                  <bean:message key="approval.enddate" />
                  <br>
                  <input type="text" name="approveEndDate" class="textfield" size="10" maxlength="10" value="<c:out value='${searchExpenseForm.approveEndDate}'/>" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchExpenseForm.approveEndDate, 'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
                <td>
                  <bean:message key="station.code" />
                  <br>
                  <html:select property="stationCode" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="create.agent"/>
                  <br>
                  <html:text property="createAgent" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="expense.status" />
                  <br>
                  <html:select property="statusId" styleClass="dropdown">
                	  <html:option value="0">
                     	 <bean:message key="select.all" />
                  	  </html:option>
                      <html:options collection="expenseStatusList" property="status_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="expense.type" />
                  <br>
                  <html:select property="expenseTypeId" styleClass="dropdown">
                	 <html:option value="0">
                   		  <bean:message key="select.all" />
                   	</html:option>
                      <html:options collection="expensetypelist" property="expensetype_ID" labelProperty="description" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="minimum.amount" />
                  <br>
                  <html:text property="minimumAmount" size="10" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="maximum.amount" />
                  <br>
                  <html:text property="maximumAmount" size="10" maxlength="20" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td colspan="3" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                </td>
              </tr>
            </table>
            <logic:present name="expenseList" scope="request">
              <div id="pageheaderleft">
                <h1 class="green">
                  <bean:message key="header.search_result" />
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                </h1>
              </div>

              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
<tr>
                <%
                      if (canApprove) {
%>
                  <td>
                    <b><bean:message key="header.batch_approve" /></b>
                  </td>
<%
                }
%>
              <td>
                <strong>
                  <bean:message key="colname.incident_num" />
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
                  <bean:message key="header.status" />
                </strong>
              </td>
              <td>
                <b><bean:message key="header.action" /></b>
              </td>
            </tr>
                <logic:iterate id="expenselist" name="expenseList" type="com.bagnet.nettracer.tracing.db.ExpensePayout">
                <tr>
                <%
                      if (canApprove) {
%>
                    <td>
                      <%
                        if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_PENDING) {
                      %>
                        <input type="checkbox" name="code" value="<bean:write name="expenselist" property="expensepayout_ID"/>">
                      <% } %>
                      &nbsp;
                    </td>
                    <%
                    }
                     %>
                  <td>
                  	<logic:present property="incident" name="expenselist">
                    	<a href='searchIncident.do?incident=<bean:write name="expenselist" property="incident.incident_ID"/>'><bean:write name="expenselist" property="incident.incident_ID" /></a>
                    </logic:present>&nbsp;
                  </td>
                  <td>
                  	<logic:present property="ohd" name="expenselist">
                    	<a href='addOnHandBag.do?ohd_ID=<bean:write name="expenselist" property="ohd.OHD_ID"/>'><bean:write name="expenselist" property="ohd.OHD_ID" /></a>
                    </logic:present>&nbsp;
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
                  <td align="left">
                    <bean:message name="expenselist" property="status.key" />
                  </td>
                  <%
                      if (canApprove) {
                        if (expenselist.getStatus().getStatus_ID() == TracingConstants.EXPENSEPAYOUT_STATUS_PENDING) {
%>


                        <td>
                          <a href="ApproveExpense.do?expense_id=<bean:write name="expenselist" property="expensepayout_ID"/>"><bean:message key="approve" /></a>
                          &nbsp;
                          <a href="DenyExpense.do?expense_id=<bean:write name="expenselist" property="expensepayout_ID"/>"><bean:message key="deny" /></a>
                        </td>

                  
                  <% } else {
                  %>
                    <td>
                      N/A
                    </td>

                  <%
                  }
                      }
%>
                </tr>
              </logic:iterate>
                <tr>
                  <td colspan="10">
                    
                    <jsp:include page="/pages/includes/pagination_incl.jsp" />
                    
                  </td>
                </tr>
                
                <%
                      if (canApprove) {
%>
<tr>
              <td colspan="11" align="center">
              <INPUT type="button" value="Batch Approve" onClick="batchApprove()" Id="button">
                    &nbsp;&nbsp;
                    <INPUT type="button" value="Batch Deny" onClick="batchDeny()" Id="button">
                    </td>
                    </tr>
                    <%
                    }
                     %>
              </table>
              <script language=javascript>
                
  document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
