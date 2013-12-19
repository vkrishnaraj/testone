<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>

<style>
legend {
 	font-size:1em;
 	font-weight:bold;
 	color:#000099;
}
</style>

<script type="text/javascript" src="deployment/main/js/ckeditor/ckeditor.js"></script>
<%
	Agent a = (Agent)session.getAttribute("user");
	ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
	String cssFormClass = "";

	if (request.getAttribute("lostdelay") != null) {
		cssFormClass = "form2_ld";
	} else if (request.getAttribute("missing") != null) {
 		cssFormClass = "form2_pil";
	} else {
		cssFormClass = "form2_dam";   		
	}
	
	boolean fraudReview=UserPermissions.hasPermission(TracingConstants.FRAUD_REVIEW, a);
	boolean supervisorReview=UserPermissions.hasPermission(TracingConstants.SUPERVISOR_REVIEW, a);
	boolean canApproveDeny=fraudReview || supervisorReview;
  
%>
<script>

	function textCounter(field, countfield, maxlimit) {
    	if (field.value.length > maxlimit) {
    		field.value = field.value.substring(0, maxlimit);
    	} else {
    		countfield.value = maxlimit - field.value.length;
    	}
	}

	function saveOrUpdate() {
		var id = document.getElementById('documentId');
		if (id.value == 0) {
			setCommand("<%=TracingConstants.COMMAND_CREATE %>");
		} else {
			setCommand("<%=TracingConstants.COMMAND_UPDATE %>");
		}
		document.customerCommunicationsForm.submit();
	}
	
	function acknowledge() {
		setCommand("<%=TracingConstants.COMMAND_ACKNOWLEDGE %>");
		document.customerCommunicationsForm.submit();
	}
	
	function saveAndPreview() {
		document.getElementById('preview').value = "true";
		saveOrUpdate();
	}
	
	function setCommand(command) {
		document.getElementById('command').value = command;
	}

	function openPreviewWindow(fileName) {
		window.open("customerCommunications.do?preview_document="+fileName, '', 'width=600,height=800,resizable=yes');
	}

	function approveCommunication(approveStatus) {
		submitTask(approveStatus);
	}

	function rejectCommunication(rejectStatus) {
		if (!verifyRemark()) {
			alert('<%=bundle.getString("message.cust.comm.task.enter.remark") %>');
			document.getElementById("taskRemark").focus();
		} else {
			submitTask(rejectStatus);
		}
	}

	function verifyRemark() {
		var remark = document.getElementById("taskRemark");
		return remark.value != null && remark.value.length > 0;
	}

	function submitTask(status) {
		var taskId = document.getElementById("taskId").value;
		var remark = document.getElementById("taskRemark").value;
		window.location="customerCommunicationsTasks.do?taskId="+taskId+"&taskStatus="+status+"&remark="+encodeURIComponent(remark);
	}
	
</script>
<html:form action="customerCommunications.do" focus="documentTitle" method="post" >
<html:hidden property="command" styleId="command" />
<html:hidden property="preview" styleId="preview" />
<html:hidden property="id" styleId="id" />
<html:hidden property="incidentId" styleId="incidentId" />
<html:hidden property="templateId" styleId="templateId" />
<html:hidden property="expenseId" styleId="expenseId" />
<html:hidden property="fileName" styleId="fileName" />
<html:hidden property="taskId" styleId="taskId" />
<html:hidden property="taskStatus" styleId="taskStatus" />
	<tr>
		<td colspan="3" id="pageheadercell">
			<div id="pageheaderleft">
				<h1>
					<bean:message key="colname.incident.id" />:&nbsp;<bean:write name="customerCommunicationsForm" property="incidentId" />
				</h1>
			</div>
		</td>
	</tr>
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="customerCommunicationsForm" property="incidentId" />#activities'>
              	<span class="aa">
              		&nbsp;
              		<br />
              		&nbsp;
             	</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
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
				<logic:messagesPresent message="true">
					<%
						Object success = request.getAttribute("success");
						String color = (success != null && ((Boolean) success)) ? "green" : "red";						
					%>
					<span>
						<font color="<%=color %>" >
							<html:messages id="msg" message="true">
								<bean:write name="msg"/><br>								
							</html:messages>
						</font>
						<br>
					</span>
				</logic:messagesPresent>
				<table id="pageheaderright" cellspacing="0" cellpadding="0" >
					<tr>
						<td>
							<h1>
								<logic:equal name="customerCommunicationsForm" property="documentId" value="0">
									<bean:message key="header.create" />
								</logic:equal>
								<logic:notEqual name="customerCommunicationsForm" property="documentId" value="0">
									<bean:message key="header.edit" />
								</logic:notEqual>
								<bean:message key="header.outgoing.communication" />
			   				</h1>
						</td>
						<td style="text-align:right;">
							<logic:present name="previewLink" scope="request" >
								<a href="#" onclick="openPreviewWindow('<%=(String) request.getAttribute("previewLink") %>');">
									<bean:message key="link.preview" />
								</a>
							</logic:present>
							<logic:notPresent name="previewLink" scope="request" >
								&nbsp;
							</logic:notPresent>
						</td>
					</tr>			
				</table>
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
					<tr>
						<td class="header">
							<bean:message key="header.document.title" />
						</td>
						<td>
							<html:text property="documentTitle" styleId="documentTitle" size="60" styleClass="textfield" />
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="header.document.id" />
						</td>
						<td>
							<html:text property="documentId" styleId="documentId" readonly="true" styleClass="textfield" />
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="communications.type" />
						</td>
						<td>
							<html:select name="customerCommunicationsForm" property="custCommId" styleClass="dropdown" >
								<html:options collection="customerCommunicationsList" property="status_ID" labelProperty="description" />
							</html:select>
						</td>
					</tr>
					<logic:notEmpty name="customerCommunicationsForm" property="expenseId">
						<logic:notEqual name="customerCommunicationsForm" property="expenseId" value="0">
							<tr>
								<td  class="header" style="width:50%"><bean:message key="document.for.expense"/>:</td>
								<td>
									<a href="EditExpense.do?expense_id=<bean:write name="customerCommunicationsForm" property="expenseId"/>">
										<bean:write name="customerCommunicationsForm" property="expenseId"/>
									</a>
								</td>
							</tr>
						</logic:notEqual>
					</logic:notEmpty>
					<tr>
						<td colspan="2" >
							<textarea id="data" name="data"><bean:write name="customerCommunicationsForm" property="data" /></textarea>
							<script type="text/javascript" >
								CKEDITOR.replace('data',
									{
										toolbar: 'TemplateToolbar'
									});
							</script>
						</td>
					</tr>
				</table>
				<div style="text-align:center;" >
					&nbsp;&nbsp;
					<input type="button" id="submitCustComm" class="button" value='<bean:message key="button.save" />'  onclick="saveOrUpdate();"/>
					&nbsp;&nbsp;
					<input id="savePreviewButton" type="button" class="button" value='<bean:message key="button.save.preview" />' onclick="saveAndPreview();">
					<logic:equal name="customerCommunicationsForm" property="agentId" value="<%=String.valueOf(a.getAgent_ID()) %>">
						<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED)  %>">
							&nbsp;&nbsp;
							<input id="acknowledgeButton" type="button" class="button" value='<bean:message key="button.acknowledge" />' onclick="acknowledge();">
						</logic:equal>
						<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED)  %>">
							&nbsp;&nbsp;
							<input id="acknowledgeButton" type="button" class="button" value='<bean:message key="button.acknowledge" />' onclick="acknowledge();">
						</logic:equal>
						<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_DENIED)  %>">
							&nbsp;&nbsp;
							<input id="acknowledgeButton" type="button" class="button" value='<bean:message key="button.acknowledge" />' onclick="acknowledge();">
						</logic:equal>
						<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.FINANCE_STATUS_FINANCE_REJECTED)  %>">
							&nbsp;&nbsp;
							<input id="acknowledgeButton" type="button" class="button" value='<bean:message key="button.acknowledge" />' onclick="acknowledge();">
						</logic:equal>
					</logic:equal>
				</div>
				<logic:notEmpty name="customerCommunicationsForm" property="remarks" >
					<br>
					<h1><bean:message key="colname.remarks" /></h1>
					<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
						<tr>
							<td class="header" style="width:15%;">
								<bean:message key="colname.create_agent" />
							</td>
							<td class="header" style="width:20%;">
								<bean:message key="colname.date_time" />
							</td>
							<td class="header" style="width:65%;">
								<bean:message key="colname.cust.comm.remark" />
							</td>
						</tr>
						<logic:iterate id="remark" name="customerCommunicationsForm" property="remarks" type="com.bagnet.nettracer.tracing.dto.IncidentActivityRemarkDTO" >
							<tr>
								<td>
									<bean:write name="remark" property="agent" />
								</td>
								<td>
									<bean:write name="remark" property="dispCreateDate" />
								</td>
								<td>
									<bean:write name="remark" property="remarkText" />
								</td>
							</tr>
						</logic:iterate>
					</table>
				</logic:notEmpty>
				<logic:notEqual name="customerCommunicationsForm" property="taskId" value="0" >
						<logic:equal name="customerCommunicationsForm" property="pendingReview" value="true">
						<br>
						<fieldset style="border: 1px solid #000000;text-align:center;">
							<legend><bean:message key="colname.cust.comm.approval" /></legend>
							<div style="width:90%;">
							<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
								<tr>
									<td class="header"><bean:message key="colname.remark" /></td>
									<td>
										<textarea id="taskRemark" rows="4" cols="50" maxlength="255" styleClass="textfield"           
											onkeydown="textCounter(taskRemark, counter, 255);" 
	         								onkeyup="textCounter(taskRemark, counter, 255);"></textarea>
										<input id="counter" value="255" size="4" disabled />
									</td>
								</tr>
								<tr>
									<td colspan=2 style="text-align:center;" >
										<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_PENDING) %>"> 
											<input id="approveButton" type="button" class="button" value="<bean:message key="cust.comm.approve" />" onclick="approveCommunication('<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_APPROVED) %>');" />
											&nbsp;
											<input id="rejectButton" type="button" class="button" value="<bean:message key="cust.comm.reject" />" onclick="rejectCommunication('<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_DENIED) %>');" />
										</logic:equal>
										<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.FINANCE_STATUS_FRAUD_REVIEW) %>"> 
											<input id="approveButton" type="button" class="button" value="<bean:message key="cust.comm.approve" />" onclick="approveCommunication('<%=String.valueOf(TracingConstants.FINANCE_STATUS_FRAUD_APPROVED) %>');" />
											&nbsp;
											<input id="rejectButton" type="button" class="button" value="<bean:message key="cust.comm.reject" />" onclick="rejectCommunication('<%=String.valueOf(TracingConstants.FINANCE_STATUS_FRAUD_REJECTED) %>');" />
										</logic:equal>
										<logic:equal name="customerCommunicationsForm" property="taskStatus" value="<%=String.valueOf(TracingConstants.FINANCE_STATUS_SUPERVISOR_REVIEW) %>"> 
											<input id="approveButton" type="button" class="button" value="<bean:message key="cust.comm.approve" />" onclick="approveCommunication('<%=String.valueOf(TracingConstants.FINANCE_STATUS_SUPERVISOR_APPROVED) %>');" />
											&nbsp;
											<input id="rejectButton" type="button" class="button" value="<bean:message key="cust.comm.reject" />" onclick="rejectCommunication('<%=String.valueOf(TracingConstants.FINANCE_STATUS_SUPERVISOR_REJECTED) %>');" />
										</logic:equal>
									</td>
								</tr>
							</table>
							</div>
							<div style="text-align:center;" >
							</div> 
						</fieldset>
					<br/>
					</logic:equal>
				</logic:notEqual>
			</div>
		</td>
	</tr>
</html:form>
