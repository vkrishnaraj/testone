<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ChecklistVersion" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ChecklistTask" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ChecklistTaskOption" %>
<%@ page import="com.bagnet.nettracer.tracing.db.IncidentChecklist" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%
  Agent a = (Agent)session.getAttribute("user");
  String agentName = a.getUsername();
  String currentIncidentId = "" + request.getAttribute("currentIncidentId");
  int x = 0;
%>


<form id="incidentChecklistForm" action="incidentChecklist.do?incident_id=<%=currentIncidentId %>" method="post">
<input id="action" type="hidden" name="action" value="">
<input id="undoReference" type="hidden" name="undoReference" value="">
<div id="users-contain" class="ui-widget">
	<table id="users" class="ui-widget ui-widget-content">
		<thead>
			<tr class="ui-widget-header ">
				<th width="100px"><bean:message key="table.header.incident.checklist.task" /></th>
				<th><bean:message key="table.header.incident.checklist.status" /></th>
				<th width="200px"><bean:message key="table.header.incident.checklist.information" /></th>
			</tr>
		</thead>
		<tbody>
			<logic:iterate id="checklistTask" name="ready4ViewListOfTasks" type="com.bagnet.nettracer.tracing.db.ChecklistTask">
				<tr>
					<td><bean:message name="checklistTask" property="description"/></td>
					<td>
					<logic:empty name="checklistTask" property="snapshotData">
					<select name="selectedOptions[<%=x++ %>]"><option value="">Please select</option>
						<bean:define id="taskOptionsList" name="checklistTask" property="checklistTaskOptions" />
                		<logic:iterate id="taskOption" name="taskOptionsList" type="com.bagnet.nettracer.tracing.db.ChecklistTaskOption">
                			<option value='<bean:write name="taskOption" property="id"/>'><bean:message name="taskOption" property="description"/></option>
                		</logic:iterate>
                    </select>
                    </logic:empty>
                    <logic:notEmpty name="checklistTask" property="snapshotData">
                    	<bean:define id="snapshotIncidentChecklist" name="checklistTask" property="snapshotData" />
                    	<bean:define id="selectedTaskOption" name="snapshotIncidentChecklist" property="checklistTaskOption" />
						<bean:message name="selectedTaskOption" property="description" />
                    </logic:notEmpty>
                    </td>
					<td ><logic:notEmpty name="checklistTask" property="snapshotData">
							<bean:write name="snapshotIncidentChecklist" property="timestamp" />
						 	<bean:define id="agentWhoPerformedTask" name="snapshotIncidentChecklist" property="agent" />
						 	 (<bean:write name="agentWhoPerformedTask" property="username" />)<br />
						<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST, a)) {%>
							<a style="text-decoration:underline;cursor:hand" onclick="undo(<bean:write name='snapshotIncidentChecklist' property='id' />);"><bean:message key="incident.checklist.click.to.undo" /></a>
						<% } %>
						</logic:notEmpty>
					</td>
				</tr>		
			</logic:iterate>
		</tbody>
	</table>
</div>
</form>

<script type="text/javascript">
	function checklistModalCallback() {
		<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_INCIDENT_CHECKLIST, a) && (x > 0)) {%>
			jQuery('#dialog').dialog('option', 'buttons', { "Save": function() {jQuery('#action').val('save'); jQuery('#incidentChecklistForm').submit(); } });
		<% } %>
		jQuery('#incidentChecklistForm').ajaxForm({beforeSubmit: preProcess, success: processResponse});
	}

	function undo(incidentChecklistId) {
		jQuery('#action').val('undo');
		jQuery('#undoReference').val(incidentChecklistId);
		jQuery('#incidentChecklistForm').submit();
	}

	function preProcess(formData, jqForm, options) {
		jQuery('#dialog-inner-content').html(getSavingContent());	
	}

	function processResponse(responseText, statusText) {
		jQuery('#dialog-inner-content').html(responseText);
		checklistModalCallback();
	}
</script>


