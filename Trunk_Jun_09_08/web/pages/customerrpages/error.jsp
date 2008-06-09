<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="org.apache.log4j.Logger" %>



   
   <tr>
      <td align="center">
      	<p />
        <h1>

<%

	if (request.getParameter("errortype") != null) {
		if (request.getParameter("errortype").equals("403")) {
		%>
			<bean:message key="error.error403.desc" />
		<%
		} else if (request.getParameter("errortype").equals("404")) {
		%>
			<bean:message key="error.error404.desc" />
		<%			
		} else {
		%>
			<bean:message key="error.error500.desc" />
		<%	
		}
	} else {
	%>
		<bean:message key="error.error500.desc" />
	<%		
	}

%>
        </h1>
      </td>
    </tr>
    <tr>
      <td align=center>
        <br>
        <br>
        <INPUT Id="button" type="button" value="Back" onClick="history.back()">
        <p />
      </td>
    </tr>
