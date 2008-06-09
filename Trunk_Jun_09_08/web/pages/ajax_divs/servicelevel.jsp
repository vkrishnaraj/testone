<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel" %>
<%@ page import="java.util.ArrayList" %>

<%
	ArrayList ar = (ArrayList)request.getAttribute("servicelevels");
	if (ar != null && ar.size() > 0) {
%>

      <select name="servicelevel_ID" class="dropdown">
      	<option value=""><bean:message key="select.please_select" /></option>
      <%
      Deliver_ServiceLevel ds = null;
      for (int i =0; i<ar.size();i++) {
      	ds = (Deliver_ServiceLevel)ar.get(i);
      %>
      	<option value="<%=ds.getServicelevel_ID()%>"><%=ds.getDescription()%></option>
      <% } %>      
			</select>

<% } %>
 