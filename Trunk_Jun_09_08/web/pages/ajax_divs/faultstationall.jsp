<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Station" %>
<%@ page import="java.util.ArrayList" %>

<%
	ArrayList ar = (ArrayList)request.getAttribute("faultstationlist");
	if (ar != null && ar.size() > 0) {
%>

      <bean:message key="colname.faultstation" />
      <br>
      <select name="faultstation_ID" class="dropdown">
      	<option value="0"><bean:message key="select.all" /></option>
      <%
      Station st = null;
      for (int i =0; i<ar.size();i++) {
      	st = (Station)ar.get(i);
      %>
      	<option value="<%=st.getStation_ID()%>"><%=st.getStationcode()%></option>
      <% } %>      
			</select>

<% } %>
 