<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="java.util.ArrayList" %>


<%
	ArrayList ar = null;
	String headerKey = "colname.status";
	Integer ohdStatus = (Integer)request.getAttribute("ohdStatus");
	boolean isOhd = false;
	
	if (request.getParameter("changeStatuses").equals("" + TracingConstants.AJAX_STATUS_OHD)) {
		isOhd = true;
		headerKey = "colname.ohd_status";
		ar = (ArrayList)session.getAttribute("ohdStatusList");
	}
	else {
		ar = (ArrayList)session.getAttribute("statuslist");
	}
%>
		<bean:message key="<%= headerKey %>" />
		<br />
		<%
		if (ar != null && ar.size() > 0) {
		%>
      	<select name="status_ID" class="dropdown">
      	<option value="0" 
      	<% if(isOhd && ohdStatus.intValue() == TracingConstants.OHD_STATUS_ALL) {
    	%>
    	selected="selected" 	
    	<% } %>
      	><bean:message key="select.all" /></option>
      	<% if(isOhd) { %>
      	<option value="-1" 
      	<% if(ohdStatus.intValue() == TracingConstants.OHD_STATUS_ACTIVE) {
    	%>
    	selected="selected" 	
    	<% } %>
    	><bean:message key="select.all_active" /></option>
    	<% } %>
		<%
		Status status = null;
		for (int i=0; i < ar.size(); i++) {
			status = (Status)ar.get(i);
      		%>
    		<option value="<%=status.getStatus_ID()%>" 
    		<% if ((!isOhd && status.getStatus_ID() == TracingConstants.MBR_STATUS_OPEN )
    				|| (isOhd && status.getStatus_ID() == ohdStatus.intValue())) { %>
    		 selected="selected"
    		<% } %>
    		>
    		<%=status.getDescription()%></option>
    		<%
			}
			%>
		</select>
	<% } %>
