<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%@ page import="java.util.List" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.dto.OptionDTO"%>
<%@ page import="com.bagnet.nettracer.tracing.enums.TemplateType"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%
	Agent a = (Agent)session.getAttribute("user");
	boolean canEdit = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_EDIT, a);
	boolean canDelete = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_DELETE, a);
	boolean canViewPublished = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_VIEW_PUBLISHED, a);

	ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
	
	String cssFormClass = "";
	String formType = "";
	if (request.getAttribute("lostdelay") != null) {
		formType = "lostdelay";
    	cssFormClass = "form2_ld";
  	} else if (request.getAttribute("missing") != null) {
		formType = "missing";
     	cssFormClass = "form2_pil";
   	} else {
		formType = "damaged";
   		cssFormClass = "form2_dam";   		
  	}
%>
<script language="javascript" >

	function loadList(selectId, url) {
		if (document.getElementById(selectId).options.length > 1) return;

		jQuery.ajax({
 			url:url,
			cache: false,
			success: function(result) {
				populateSelect(selectId, result);
			}
		});	
	}

	function populateSelect(selectId, json) {
		var activitySelect = document.getElementById(selectId);		
		for (var i = 0; i < json.length; ++i) {
			activitySelect.options[activitySelect.options.length] = new Option(json[i].description, json[i].value);
		}
	}

	function validateIncidentActivity() {
		var activitySelect = document.getElementById("activityIdSelect");
		if (activitySelect.options[activitySelect.selectedIndex].value == 0) {
			alert('<%=(String) bundle.getString( "communications.type") + " " + (String) bundle.getString("error.validation.isRequired")%>');
			templateSelect.focus();
			return false;
		}
		return true;
	}

	

	function submitIncidentActivity() {
		var activitySelect = document.getElementById("activityIdSelect");
		var activityId = activitySelect.options[activitySelect.selectedIndex].value;
		if (activityId == "<%=TracingConstants.ACTIVITY_CUSTOMER_COMMUNICATION %>") {
			showTemplateSelectDialog();
		} else {
			submitRequest();
		}
	}

	function submitRequest(templateId) {
		var incidentId = document.getElementById("incident_ID").value;
		var activitySelect = document.getElementById("activityIdSelect");
		var activityId = activitySelect.options[activitySelect.selectedIndex].value;
		var url = 'incidentActivity.do?command=<%=TracingConstants.COMMAND_CREATE %>&incident='+incidentId+'&activity='+activityId;
		if (templateId && templateId != null) {
			url = url + '&templateId='+templateId;
		}		
		window.location.href=url;
	}

	function verifyDelete() {
		return confirm('<%=bundle.getString("message.incident.activity.delete") %>');
	}

	function openPreviewWindow(activityId) {
		window.open("customerCommunications.do?view_sample_printout="+activityId, '', 'width=600,height=800,top=2600,resizable=yes');
	}

	function showTemplateSelectDialog() {
		if (document.getElementById("templateSelect").options.length == 1) {
			loadList("templateSelect", "customerCommunications.do?templateList=<%=String.valueOf(TemplateType.INCIDENT.getOrdinal()) %>")
		}

		var templateSelectDialog = jQuery("#templateSelectDiv").dialog({
										height: 50,
										width: 350,
										title: '<bean:message key="message.customer.communications.dialog.title" />',
										modal: true,
										buttons: {
											Ok: function() {
												jQuery(this).dialog("close");
												var templateSelect = document.getElementById("templateSelect");
												templateId = templateSelect.options[templateSelect.selectedIndex].value;
												if (templateId == "0") {
													alert('You must select a document to send to the passenger.');
													return;
												}
												submitRequest(templateId);												
											},
											Cancel: function() {
												jQuery(this).dialog("close");
											}
										}
									});
		templateSelectDialog.dialog("open");
	}

</script>
<h1 class="green">
	<bean:message key="header.customer.communications" />
</h1>
<span class="reqfield">*</span>
<bean:message key="message.required" />
<input type="hidden" name="templateId" id="templateId" value="" />
	<table  class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		<tr>
			<td class="header" style="width:25%">
				<bean:message key="colname.customer.preference" />
			</td>
			<td>
				&nbsp;&nbsp;
				<html:select name="incidentForm" property="custCommId" styleId="custCommId" styleClass="dropdown" >
					<html:options collection="customerCommunicationsList" property="status_ID" labelProperty="description" />
				</html:select>
				&nbsp;&nbsp;
				<input type="submit" id="setCommMethodButton" class="button" name="saveCustCommId" value="<bean:message key="button.save.preference" />" onclick="return validatereqFields(this.form, '<%=formType %>')" />
			</td>
		</tr>
	</table>
	<logic:notEmpty name="incidentForm" property="activityDtos" >
		<table  class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
			<tr>
				<td class="header" style="width:15%;">
					<bean:message key="incident.activity.date.time.created" />
				</td>
				<td class="header" style="width:15%;">
					<bean:message key="incident.activity.agent.created" />
				</td>
				<td class="header" style="width:50%;">
					<bean:message key="incident.activity.description" />
				</td>
				<td class="header" style="width:20%;">
					<bean:message key="incident.activity.action" />
				</td>
			</tr>
			<logic:iterate id="activity" name="incidentForm" property="activityDtos" type="com.bagnet.nettracer.tracing.dto.IncidentActivityDTO" >
				<tr>
					<td>
						<bean:write name="activity" property="dispCreateDate" />
					</td>
					<td>
						<bean:write name="activity" property="agent" />
					</td>
					<td>
						<bean:write name="activity" property="description" />
					</td>
					<td>
						<!-- Currently, customer communications are only identified from other activities by having a status id -->
						<logic:notEqual name="activity" property="statusId" value="0" >
							<logic:notEqual name="activity" property="statusId" value="<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_PUBLISHED) %>">
								<% if (canEdit) { %>
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_EDIT %>&communicationsId=<%=String.valueOf(activity.getId()) %>">
										<bean:message key="customer.communication.action.edit" />
									</a>
									<br>
								<% } %>
								<% if (canDelete) { %>
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_DELETE %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" onclick="return verifyDelete();" >
										<bean:message key="customer.communication.action.delete" />
									</a>
									<br>
								<% } %>
								<% if (canEdit) { %>
									<a href="#" onclick="openPreviewWindow('<%=activity.getId() %>')">
										<bean:message key="customer.communication.action.preview" />
									</a>
								<% } %>
								<% if (!canEdit && !canDelete) { %>
									&nbsp;
								<% } %>
							</logic:notEqual>
							<logic:equal name="activity" property="statusId" value="<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_PUBLISHED) %>">
								<% if (canViewPublished) { %>
									<a href="#" onclick="openPreviewWindow('<%=activity.getId() %>')">
										<bean:message key="customer.communication.action.view" />
									</a>
									&nbsp;
									(<bean:message key="<%="STATUS_KEY_" + activity.getCustCommId() %>" />)
									<br>
									<bean:message key="customer.communication.published" />&nbsp;<bean:write name="activity" property="dispPublishedDate" />
								<% } else { %>
									&nbsp;
								<% } %>
							</logic:equal>
						</logic:notEqual>
						<logic:equal name="activity" property="statusId" value="0" >
							<% if (canDelete) { %>
								<a href="incidentActivity.do?command=<%=TracingConstants.COMMAND_DELETE %>&activity=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" onclick="return verifyDelete();" >
									<bean:message key="delete" />
								</a>
							<% } else { %>
								&nbsp;
							<% } %>
						</logic:equal>
					</td>
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<center>
		<select id="activityIdSelect" class="dropdown" >
			<option value="0"><bean:message key="select.please_select" /></option>
		</select>
		&nbsp;&nbsp;
		<input type="button" id="addCommButton" class="button" value="<bean:message key="button.add.action.communication" />" onclick="if (validateIncidentActivity() && validatereqFields(this.form, '<%=formType %>')) { submitIncidentActivity(); }" />
	</center>
	<a id="activities" />
<br>
<br>
&nbsp;&nbsp;&uarr;
<a href="#"><bean:message key="link.to_top" /></a>
<br>
<br>
<script language="javascript">
	loadList("activityIdSelect", "incidentActivity.do?activityList=1");
</script>

<div id="templateSelectDiv" style="display:none;" >
	<table style="width:100%;">
		<tr>
			<td style="width:50%;text-align:right;">Select a Document:</td>
			<td style="width:50%;text-align:left;">
				<select id="templateSelect" style="	font-size:9px;border:1px solid #569ECD;margin:2px 0px 1px 0px;display:inline;" >
					<option value="0"><bean:message key="select.please_select" /></option>
				</select>
			</td>
		</tr>
	</table>
</div>