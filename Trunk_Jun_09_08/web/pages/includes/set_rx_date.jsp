<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<%
	Agent a = (Agent)session.getAttribute("user");
%>

<style type="text/css" >
#error {
	color:#ff0000;
	margin-top:0.5em;
	margin-bottom:0.5em;
}
</style>

<script type="text/javascript" >
	function setRxTimestamp(set) {
		jQuery.ajax({
			url:"damaged.do?ajax=1&incident_id=<bean:write name='incidentForm' property='incident_ID'/>&set="+set,
			cache: false,
			success: function(result) {
				jQuery("#rxTimestampDiv").html(result);
			}
		});	
	}
</script>
<div id="rxTimestampDiv" style="margin-top:0.5em;margin-bottom:0.5em;">
	<% if (request.getAttribute("error") != null) { %>
		<div id="error" >
			<bean:message key="<%=(String) request.getAttribute("error") %>" />
		</div>
	<% } %>
	<logic:empty name="incidentForm" property="rxTimestamp" >
		<input type="button" value='<bean:message key="button.receive.at.lz" />' id="rxButton" class="button" title='<bean:message key="tooltip.set.rx.time" />' onclick="setRxTimestamp(true);" />
	</logic:empty>
	<logic:notEmpty name="incidentForm" property="rxTimestamp" >
		<bean:message key="received.lz.timestamp" />:&nbsp;
		<html:text name="incidentForm" property="dispRxTimestamp" size="20" readonly="true" styleClass="textfield" />
		<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_RECEIVE_TIMESTAMP_DELETE, a)) { %>
			&nbsp;
			<input type="button" value='<bean:message key="button.receive.at.lz.undo" />' id="undoRxButton" class="button" title='<bean:message key="tooltip.undo.rx.time" />' onclick="setRxTimestamp(false);" />
		<% } %>
	</logic:notEmpty>
</div>