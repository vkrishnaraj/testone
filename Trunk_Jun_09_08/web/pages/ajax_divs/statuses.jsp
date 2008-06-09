<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="java.util.ArrayList" %>


<%
	ArrayList ar = null;
	if (request.getParameter("changeStatuses").equals("" + TracingConstants.AJAX_STATUS_OHD)) {
		ar = (ArrayList)request.getAttribute("oStatusList");
		%>
		<bean:message key="colname.bag_status" />
		<br />
		<%
	} else if (request.getParameter("changeStatuses").equals("" + TracingConstants.AJAX_STATUS_INC)) {
		ar = (ArrayList)session.getAttribute("statuslist");
		%>
		<bean:message key="colname.status" />
		<br />
		<%
	}
	if (ar != null && ar.size() > 0) {
	%>
      <select name="status_ID" class="dropdown">
      	<option value=""><bean:message key="select.all" /></option>
		<%
		Status status = null;
		for (int i=0; i < ar.size(); i++) {
			status = (Status)ar.get(i);
      		%>
    		<option value="<%=status.getStatus_ID()%>" 
    		<% if (status.getStatus_ID() == TracingConstants.MBR_STATUS_OPEN || status.getStatus_ID() == TracingConstants.OHD_STATUS_OPEN) { %>
    		 selected
    		<% } %>
    		
    		><%=status.getDescription()%></option>
    		<%
		}
		%>
		 
		</select>
	<% } %>
