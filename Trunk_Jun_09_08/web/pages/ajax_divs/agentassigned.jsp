<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="java.util.ArrayList" %>

<%
	ArrayList ar = (ArrayList)request.getAttribute("agentassignedlist");
	if (ar != null && ar.size() > 0) {
%>

      <bean:message key="colname.agentassigned_nobr" />
      <br>
      <select name="agentassigned_ID" class="dropdown">
      <option value=""><bean:message key="select.please_select" /></option>
      <%
      Agent st = null;
      for (int i =0; i<ar.size();i++) {
      	st = (Agent)ar.get(i);
      %>
      	<option value="<%=st.getAgent_ID()%>"><%=st.getUsername()%></option>
      <% } %>      
			</select>

<% } else { %>
	 <bean:message key="colname.noagentinstation" />
<% }	%>
 