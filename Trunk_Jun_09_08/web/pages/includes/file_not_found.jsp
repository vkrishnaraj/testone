<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<html>
	<body>
		<h1><bean:message key="file.not.found" arg0="<%=(String) request.getAttribute("fileName") %>" /></h1>
	</body>
</html>