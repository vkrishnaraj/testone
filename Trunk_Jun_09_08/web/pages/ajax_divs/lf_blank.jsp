<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%
	String cssFormClass = "form2";
%>
<logic:empty name="found" scope="request" >
	<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
		<tr>
			<td colspan=5 class="summaryActionItem">
				<%=(String) request.getAttribute("message") %>
			</td>
		</tr>
	</table>
</logic:empty>