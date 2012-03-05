<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryContainer" %>
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.history.FoundHistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="java.util.ArrayList" %>
<%	
	int divId = Integer.valueOf((String) request.getAttribute("divId"));
	String cssFormClass = "form2";
%>
<logic:notEmpty name="found" scope="request" >
<div id="<%=divId %>" >
<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
	<tr>
		<td style="width:20%;">
			<a href='create_found_item.do?foundId=<bean:write name="found" property="id" />' ><bean:write name="found" property="barcode" /></a>
		</td>
		<td style="width:20%;">
			<bean:write name="found" property="disReceivedDate" />
		</td>
		<td style="width:40%;">
			<bean:write name="found" property="summaryDesc" />
		</td>
		<td style="width:20%;">
			<center>
				<a href='#' onclick="removeItemAjax('<%=divId %>','<bean:write name="found" property="id" />')" ><bean:message key="lf.salvage.remove" /></a>
				<!-- input type="button" class="button" id="button_<%=divId %>" onclick="removeItemAjax('<%=divId %>','<bean:write name="found" property="id" />')" value='<bean:message key="lf.salvage.remove" />'-->
			</center>
		</td>
	</tr>
</table>
</div>
</logic:notEmpty>
<logic:empty name="found" scope="request" >
	<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
		<tr>
			<td colspan=4 class="summaryActionItem">
				<%=(String) request.getAttribute("message") %>
			</td>
		</tr>
	</table>
</logic:empty>
