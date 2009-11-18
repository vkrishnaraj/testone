<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
	Agent a = (Agent) session.getAttribute("user");
%>


<html:form action="wtCaptcha.do" method="post">
	<tr>
		
		<td id="middlecolumn">
		<div id="maincontent">
		<h1 class="green">WorldTracer Login</h1>
		To log into the WorldTracer system, please enter the text found in the following graphic.<br/> 
		
		<p>&nbsp;</p><br/> 
		<center><img src="showCaptcha"/><br/>
		<html:text property="captcha_text"/><br />
		<html:submit property="submit" ></html:submit></center>
		</div>

		
</html:form>
