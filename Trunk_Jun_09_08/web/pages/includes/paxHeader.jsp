<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<td colspan=5>
<%
	if(Integer.parseInt(request.getParameter("header_index")) > 0) {
%> <b><bean:message key="colname.addi_pass_info" /></b> <%
 	}
 	else {
 %> <b><bean:message key="colname.pri_pass_info" /></b>
<%
	}
%>
</td>