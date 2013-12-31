<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
	int divId = 0;
%>
  
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<SCRIPT SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT >
	var cal1xx = new CalendarPopup();
  </SCRIPT>
  
  <script>
  
  
    
  function textCounter(field, countfield, maxlimit) {
	  	if (field.value.length > maxlimit) {
	  		field.value = field.value.substring(0, maxlimit);
	  	} else {
	  		countfield.value = maxlimit - field.value.length;
	  	}
	}
  
function goprev() {
  o = document.paymentApprovalForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.paymentApprovalForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.paymentApprovalForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}


function submitRejection(taskid) {
	showRejectRemarkDialog(taskid);
}
	
function showRejectRemarkDialog(taskid) {
	var rejectRemarkDialog = jQuery("#rejectRemarkDiv").dialog({
		height: 225,
		width: 355,
		title: 'Rejection Remark',
		modal: true,
		buttons: {
			Submit: function() {
				jQuery(this).dialog("close");
				var rejectRemark = document.getElementById("taskRemark");
				if (rejectRemark && rejectRemark.value == "") {
					alert('You must enter a remark.');
					return;
				}
				document.paymentApprovalForm.rejectId.value=taskid;
				document.paymentApprovalForm.rejectRemark.value=rejectRemark.value;
				document.paymentApprovalForm.submit();
			}
		}
	});
	rejectRemarkDialog.dialog("open");
}

function sortTable(sortOrder) {
	o = document.paymentApprovalForm;
	if(sortOrder==o.sort.value && o.dir.value!=<%=TracingConstants.SORT_DESCENDING%>){
		o.dir.value=<%=TracingConstants.SORT_DESCENDING%>;
	} else {
		o.dir.value=<%=TracingConstants.SORT_ASCENDING%>;
	}
	o.sort.value = sortOrder;
	o.submit();
}
  </script>
  
  <html:form action="paymentApproval.do" method="post" enctype="multipart/form-data" >
  	<input type="hidden" name="sort" value="<%=request.getAttribute("sort")!=null?request.getAttribute("sort"):""%>"/>
  	<input type="hidden" name="dir" value="<%=request.getAttribute("dir")!=null?request.getAttribute("dir"):""%>"/>
	<html:hidden name="paymentApprovalForm" property="rejectRemark" styleId="rejectRemark" value="" />	
	<html:hidden name="paymentApprovalForm" property="rejectId" styleId="rejectId" value="" />	
	<html:hidden property="command" styleId="command" />
	<html:hidden property="_DATEFORMAT" />
	<jsp:include page="/pages/includes/taskmanager_header.jsp" />
      <td id="middlecolumn">
        
        <div id="maincontent">
     		<logic:messagesPresent message="true">
				<%
					Object success = request.getAttribute("success");
					String color = (success != null && ((Boolean) success)) ? "green" : "red";						
				%>
				<span>
					<font color="<%=color %>" >
						<html:messages id="msg" message="true">
							<bean:write name="msg"/>
							<br><br>
						</html:messages>
					</font>
				</span>
			</logic:messagesPresent>
			<h1 class="green">
				<bean:message key="header.payment.approval" />
			</h1>
			<span class="reqfield">*</span>
			<bean:message key="message.required.payment.approval" />
			<logic:empty name="paymentApprovalForm" property="iatlist">
		       		<div style="text-align:center;color:green;" >
		       			<br>
		       			<bean:message key="message.no.payment.approval" />
		       		</div>
	       	</logic:empty>
            <logic:notEmpty name="paymentApprovalForm" property="iatlist">
              <div id="pageheaderright">
                <input type="hidden" name="outputtype" value="<%=TracingConstants.REPORT_OUTPUT_XLS%>" />
                <html:submit  property="generateReport" styleId="button">
                    <bean:message key="button.generateReport" />
                </html:submit>
                <logic:present name="reportfile" scope="request">
                  <script >
                    
                      openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);

                  </script>
                </logic:present>
              </div>
              
              
              <table class="form2" id="payment_approval" style="margin-bottom:0;padding-bottom:0; border-spacing:0; border-collapse:collapse;">
     			<thead>
     			<!--  "id", 			"d.id");
		map.put("incidentId", 	"i.incident_ID");
		map.put("agent", 		"ia.approvalAgent");
		map.put("date",			"iat.generic_timestamp");
		map.put("expenseId",    "ia.expensepayout_id");
		map.put("status" -->
     				<tr>
	     				<td class="header">
	     					<a href="#" style="color:white" onclick="sortTable('taskId');"><bean:message key="colname.disburse.id" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white"  onclick="sortTable('expenseId');"><bean:message key="colname.disburse.expense.id" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('name');"><bean:message key="colname.disburse.name" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('address');"><bean:message key="colname.disburse.address" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('amount');"><bean:message key="colname.disburse.checkamt" /></a>
	     				</td>
	     				<td class="header" >
	     					<bean:message key="colname.disburse.airline" />
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('pnr');"><bean:message key="colname.disburse.pnr" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('incidentId');"><bean:message key="colname.disburse.incident.id" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('reason');"><bean:message key="colname.disburse.reason" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('specialist');"><bean:message key="colname.disburse.specialist" /></a>
	     				</td>
	     				<td class="header" >
	     					<a href="#" style="color:white" onclick="sortTable('approver');"><bean:message key="colname.disburse.approver" /></a>
	     				</td>
	     				<td class="header" >
	     					<bean:message key="colname.disburse.draft" />
	     				</td>
	     				<td class="header" width="13%" >
	     					<bean:message key="colname.disburse.draft.date" />
	     				</td>
	     				<td class="header" width="13%" >
	     					<bean:message key="colname.disburse.mail.date" />
	     				</td>
	     				<td class="header" >
	     					<bean:message key="header.action" />
	     				</td>
     				</tr>
     			</thead>
     			
             	 <bean:define id="iats" name="paymentApprovalForm" property="iatlist" />
             	 <tbody>
     			<logic:iterate indexId="i" id="iat" name="iats" type="com.bagnet.nettracer.tracing.dto.IncidentActivityTaskDTO" >
     				
	     				<tr>
		    				<td>
		    					<a href="customerCommunications.do?command=edit&communicationsId=<bean:write name="iat" property="id" />"><bean:write name="iat" property="id" /></a>
		    					<html:hidden name="iat" property="taskid" indexed="true"/>
	     					</td>
		    				<td>
		    					<a href="EditExpense.do?expense_id=<bean:write name="iat" property="expenseId" />" ><bean:write name="iat" property="expenseId" /></a>
		    					
		    					<html:hidden name="iat" property="expenseId" indexed="true"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="name"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="address"/><br/>
		    					<logic:notEmpty name="iat" property="aptnum">
		    						<bean:write name="iat" property="aptnum"/><br/>
		    					</logic:notEmpty>
		    					<bean:write name="iat" property="city"/>,<bean:write name="iat" property="state"/><br/>
		    					<bean:write name="iat" property="zip"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="dispExpensecheckamt"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="airline"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="pnr"/>
		    				</td>
		    				<td>
		    					<a href="searchIncident.do?incident=<bean:write name="iat" property="incidentId" />" ><bean:write name="iat" property="incidentId" /></a>
		    					
		    					<html:hidden name="iat" property="incidentId" indexed="true"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="reason"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="specialist"/>
		    				</td>
		    				<td >
		    					<bean:write name="iat" property="approver"/>
		    				</td>
		    				<td >
		    					<html:text name="iat" property="expensedraft" styleClass="textfield" indexed="true" size="10" />
		    				</td>
		    				<td >
		    					<html:text name="iat" property="expensedraftdate" styleId="expensedraftdate" styleClass="textfield" indexed="true" size="10"/>
		    					<img src="deployment/main/images/calendar/calendar_icon.gif" id="expensedraftdatecalendar<%= i %>" 
		         				name="expensedraftdatecalendar<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" 
		         				onClick="cal1xx.select2(document.paymentApprovalForm, '<%= "iat["+i+"].expensedraftdate"%>','expensedraftdatecalendar<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;">
		    				</td>
		    				<td >
		    					<html:text name="iat" property="expensemaildate" styleId="expensemaildate" styleClass="textfield" indexed="true" size="10"/>
		    					<img src="deployment/main/images/calendar/calendar_icon.gif" id="expensemaildatecalendar<%= i %>" 
		         				name="expensemaildatecalendar<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" 
		         				onClick="cal1xx.select2(document.paymentApprovalForm, '<%= "iat["+i+"].expensemaildate"%>','expensemaildatecalendar<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;">
		    				</td>
		    				<td >
		     					<logic:equal name="iat" property="status" value="<%=String.valueOf(TracingConstants.FINANCE_STATUS_AWAITING_DISBURSEMENT) %>" >
			    					<a href='#' onclick="submitRejection('<bean:write name="iat" property="id" />')" ><bean:message key="deny" /></a>
		    					</logic:equal>
		    				</td>
	     				</tr>
     			</logic:iterate>
     			</tbody>
            	<tr>
            		<td colspan="15">
			            <jsp:include page="/pages/includes/pagination_incl.jsp" />
            		</td>
            	</tr>
            	<tr>
              		<td colspan="15" align="center">
              			<html:submit styleId="button" property="updateExpense">
              				<bean:message key="button.update" />
              			</html:submit>
                    </td>
               	</tr>
     		</table>
            </logic:notEmpty>
	</div>
	<div id="rejectRemarkDiv" style="display:none;" >
		<table style="width:100%;">
			<tr>
				<td class="header"><bean:message key="colname.remark" /></td>
				<td>
					<textarea id="taskRemark" rows="4" cols="50" maxlength="255" styleClass="textfield"           
						onkeydown="textCounter(taskRemark, counter, 255);" 
     								onkeyup="textCounter(taskRemark, counter, 255);"></textarea>
					<input id="counter" value="255" size="4" disabled />
				</td>
			</tr>
		</table>
	</div>	
</html:form>
