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
<logic:notEmpty name="message" scope="request" >
	<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
		<tr>
			<td colspan=4 style="background-color:#00f000">
				<bean:message key="lf.found.item"/>: <a href='create_found_item.do?foundId=<bean:write name="found" property="id" />' ><bean:write name="found" property="barcode" /></a><logic:notEmpty name="lost" scope="request">, <bean:message key="match.with"/> <bean:message key="lf.lost.report"/>: <a href='create_lost_report.do?lostId=<bean:write name="lost" property="id" />' ><bean:write name="lost" property="id"/></a>,</logic:notEmpty> <bean:message key="item.delivered"/> <bean:write name="item" property="trackingNumber" />
			</td>
		</tr>
	</table>
</logic:notEmpty>
<logic:notEmpty name="messageerror" scope="request" >
	<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
		<tr>
			<td colspan=4 class="summaryActionItem">
				<logic:notEmpty name="found" scope="request">
					<bean:message key="lf.found.item"/>: <a href='create_found_item.do?foundId=<bean:write name="found" property="id" />' ><bean:write name="found" property="barcode" /></a> <bean:message key="error.item.already.delivered"/>
				</logic:notEmpty>
				<logic:empty name="found" scope="request">
					<%=(String) request.getAttribute("messageerror") %>
				</logic:empty>
			</td>
		</tr>
	</table>
</logic:notEmpty>
