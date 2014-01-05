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
<script type="text/javascript">

	function textCounter(field, countfield, maxlimit) {
	  	if (field.value.length > maxlimit) {
	  		field.value = field.value.substring(0, maxlimit);
	  	} else {
	  		countfield.value = maxlimit - field.value.length;
	  	}
	}

	function insertNewLine3() {
		if (window.event && window.event.keyCode == 13) {
			window.event.keyCode = 505;
		}
  	}

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
		if (activitySelect.options[activitySelect.selectedIndex].value == -1) {
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
		} else if (activityId == "<%=TracingConstants.OUTBOUND_CORRESPONDANCE %>"){
			showOutboundMessageDialog();
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

	function verifyUnpublish() {
		return confirm('<%=bundle.getString("message.incident.activity.unpublish") %>');
	}
	
	function verifyPublish() {
		return confirm('<%=bundle.getString("message.incident.activity.publish") %>');
	}
	
	function verifyDelete() {
		return confirm('<%=bundle.getString("message.incident.activity.delete") %>');
	}

	function openPreviewWindow(activityId) {
		window.open("customerCommunications.do?view_sample_printout="+activityId, '', 'width=600,height=800,top=2600,resizable=yes');
	}

	function showOutboundMessageDialog() {
		var outboundMessageDialog = jQuery("#outboundMessageDiv").dialog({
			height: 225,
			width: 355,
			title: 'Outbound Message',
			modal: true,
			buttons: {
				Submit: function() {
					jQuery(this).dialog("close");
					var outboundMessage = document.getElementById("outboundMessage");
					if (outboundMessage && outboundMessage.value == "") {
						alert('You must enter an Outbound Message.');
						return;
					}
					document.incidentForm.outMessage.value=outboundMessage.value;
					var incidentId = document.getElementById("incident_ID").value;
					var activitySelect = document.getElementById("activityIdSelect");
					var activityId = activitySelect.options[activitySelect.selectedIndex].value;
					var posturl = 'incidentActivity.do?command=<%=TracingConstants.COMMAND_CREATE %>&incident='+incidentId+'&activity='+activityId;
					
					jQuery.ajax({
						type: "POST",
						url: posturl,
						data: "outMessage="+outboundMessage.value,
						cache: false,
						success: function() {
						    window.location.reload(true);
						}
					});
				}
			}
		});
		outboundMessageDialog.dialog("open");
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

	//label
	function generateLabel(field) {
		if (field.value.length < 1) {
			return false;
		}
		
		field.disabled = true;
		
		var incidentId = document.getElementById("incident_ID").value;
		jQuery.ajax({
 			url: "label.do?incidentId="+ incidentId,
			cache: false,
			success: function(result) {
				alert('<%=bundle.getString("label.created.success") %>');
			},
			error: function(result) {
				field.disabled = false;
				alert('<%=bundle.getString("error.failed.to.save") %>');
			}
		});	 
		
		return true;
	}
//-->
</script>
<html:hidden name="incidentForm" property="outMessage" value=""/>

<div style="float:right">
	<input type="button" id="generateLabelButton" class="button"
		value="<bean:message key="button.label.generate" />" onclick="return generateLabel(this);"/>
</div>

<div>
	<a id="activities" ></a>
	<h1 class="green">
		<bean:message key="header.customer.communications" />
	</h1>
	<span class="reqfield">*</span>
	<bean:message key="message.required" />
</div>

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
		<tr>
			<td class="header" style="width:25%;">
				<bean:message key="colname.claim.status" />
			</td>
			<td>
				&nbsp;&nbsp;
				<html:select name="incidentForm" property="claimStatusId" styleId="claimStatusId" styleClass="dropdown" >
					<html:option value="0" ><bean:message key="select.please_select" /></html:option>
					<html:options collection="claimStatusList" property="status_ID" labelProperty="description" />
				</html:select>
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
						<logic:equal name="activity" property="isCorrespondence" value="true" >
							<logic:notEmpty name="activity" property="messages">
							<br/>
							<bean:message key="colname.messages"/><br/>
								<logic:iterate id="message" name="activity" property="messages" type="com.bagnet.nettracer.tracing.dto.MessageDTO" >
									<bean:write name="message" property="username" /><br/>
									<bean:write name="message" property="dispCreateDate" /><br/>
									<bean:write name="message" property="messageText" filter="false"/><br/><br/>
									
								</logic:iterate>
							</logic:notEmpty>
							
							<logic:notEmpty name="activity" property="files"><br/>
							<bean:message key="colname.files"/><br/>
							<logic:iterate id="file" name="activity" property="files">
								<a href="showImage?ID=<bean:write name='file' property='path'/>/<bean:write name='file' property='filename'/>&useOCPath=1" target="_blank">
									<bean:write name="file" property="filename" />
								</a><br/>
							</logic:iterate>
							</logic:notEmpty>
						</logic:equal>
					</td>
					<td>
						<!-- Currently, customer communications are only identified from other activities by having a status id -->
						<logic:equal name="activity" property="isCustomerCommunication" value="true" >
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
							<logic:equal name="activity" property="statusId" value="<%=String.valueOf(TracingConstants.STATUS_CUSTOMER_COMM_PENDING_WP) %>">
							
								<logic:equal name="activity" property="published" value="true">
									
									<br>
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_UNPUBLISH %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" onclick="return verifyUnpublish();" >
										<bean:message key="customer.communication.action.unpublish" />
									</a>
								</logic:equal>
								<logic:notEqual name="activity" property="published" value="true">
									
									<br>
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_PUBLISH %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" onclick="return verifyPublish();" >
										<bean:message key="customer.communication.action.publish" />
									</a>
								</logic:notEqual>
							</logic:equal>
						</logic:equal>
						
						<logic:equal name="activity" property="isCorrespondence" value="true" >
							<logic:equal name="activity" property="activityCode" value="<%=String.valueOf(TracingConstants.OUTBOUND_CORRESPONDANCE) %>">
								<logic:equal name="activity" property="published" value="true">
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_UNPUBLISH %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" onclick="return verifyUnpublish();" >
										<bean:message key="customer.communication.action.unpublish" />
									</a><br>
								</logic:equal>
								<logic:notEqual name="activity" property="published" value="true">
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_PUBLISH %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" onclick="return verifyPublish();" >
										<bean:message key="customer.communication.action.publish" />
									</a><br>
								</logic:notEqual>
							</logic:equal>
							
							<logic:equal name="activity" property="activityCode" value="<%=String.valueOf(TracingConstants.INBOUND_CORRESPONDANCE) %>">
								<logic:equal name="activity" property="toBeAcknowledged" value="true">
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_ACKNOWLEDGE_INBOUND %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" >
										<bean:message key="customer.communication.action.acknowledge" />
									</a><br>
								</logic:equal>
							</logic:equal>
						</logic:equal>
						
							<% if(TracingConstants.ACTIVITY_CODE_INBOUND_CURE.equals(activity.getActivityCode()) ||
								  TracingConstants.ACTIVITY_CODE_INBOUND_FAX.equals(activity.getActivityCode()) ||
								  TracingConstants.ACTIVITY_CODE_INBOUND_MAIL.equals(activity.getActivityCode()) ||
								  TracingConstants.ACTIVITY_CODE_RECEIVED_DAMAGED_ITEM.equals(activity.getActivityCode())){
							%>
								<logic:equal name="activity" property="toBeAcknowledged" value="true">
									<a href="customerCommunications.do?command=<%=TracingConstants.COMMAND_ACKNOWLEDGE_INBOUND %>&communicationsId=<%=String.valueOf(activity.getId()) %>&incident=<bean:write name="incidentForm" property="incident_ID" />" >
										<bean:message key="customer.communication.action.acknowledge" />
									</a><br>
								</logic:equal>
							<%} %>
						
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
			<option value="-1"><bean:message key="select.please_select" /></option>
		</select>
		&nbsp;&nbsp;
		<input type="button" id="addCommButton" class="button" value="<bean:message key="button.add.action.communication" />" onclick="if (validateIncidentActivity() && validatereqFields(this.form, '<%=formType %>')) { submitIncidentActivity(); }" />
	</center>
<br>
<br>
&nbsp;&nbsp;&uarr;
<a href="#"><bean:message key="link.to_top" /></a>
<br>
<br>
<script type="text/javascript">
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

<div id="outboundMessageDiv" style="display:none;" >
	<table style="width:100%;">
		<tr>
			<td style="width:50%;text-align:right;">Enter Outbound message:<br/></td>
			<td>
				<textarea id="outboundMessage" rows="4" cols="50" styleClass="textfield"           
					onkeydown="textCounter(outboundMessage, counter, 10000); insertNewLine3();" 
    								onkeyup="textCounter(outboundMessage, counter, 10000);"></textarea>
				<input id="counter" value="10000" size="4" disabled />
			</td>
		</tr>
	</table>
</div>