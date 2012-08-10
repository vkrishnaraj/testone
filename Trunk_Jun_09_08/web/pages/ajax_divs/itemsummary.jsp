<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryContainer" %>
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.history.FoundHistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="java.util.ArrayList" %>

<%
	ArrayList<FoundHistoryObject> history = ((HistoryContainer) request.getSession().getAttribute("historyContainer")).getNewestFoundHistoryItems(PropertyBMO.getValueAsInt("lfc.item.entry.display.count"));
	int divNum = Integer.parseInt((String) request.getAttribute("divId"));
	request.removeAttribute("divId");
	FoundHistoryObject fho = (FoundHistoryObject) history.get(divNum);
	int status = fho.getFound().getEntryStatus();
	boolean needsVerification = status == TracingConstants.LF_STATUS_VERIFICATION_NEEDED || (fho.isHasTraceResults() && status != TracingConstants.LF_STATUS_MOVED);
%>
<span style="font-weight: bold;" ><bean:message key="colname.lfc.item.id" />:&nbsp;</span><a href="create_found_item.do?barcode=<%=fho.getFound().getBarcode() %>"><%=fho.getFound().getBarcode() %></a>
<br>
<span style="font-weight: bold;" ><bean:message key="lfc.summary.desc" />:&nbsp;</span><%=fho.getFound().getSummaryDesc() %>
<br>
<span style="font-weight: bold;" ><bean:message key="lfc.item.entry.status" />:&nbsp;</span>
<%
	if (status == TracingConstants.LF_STATUS_ENTERED) {
%>
	<bean:message key="lf.status.entered" />
<%
	} else if (status == TracingConstants.LF_STATUS_VERIFICATION_NEEDED) {
%>
	<bean:message key="lf.status.verification.needed" />
<%
	} else if (status == TracingConstants.LF_STATUS_MOVED) {
%>
	<bean:message key="lf.status.moved" />
<%
	}
%>
<% 	if (needsVerification) { %>
	<div id="moveDiv_<%=divNum %>" >
		<span style="font-weight: bold;" ><bean:message key="lfc.bin.id" />:&nbsp;</span>
		<html:text name="enterItemsForm" property="found.binId" size="5" maxlength="50" styleClass="textfield" />
		<center>
			<input class="button" type="button" value='<bean:message key="lfc.button.item.moved" />' onClick="updateItemLocation('<%=divNum %>','<%=fho.getUniqueId() %>')">
		</center>
		<script>
			document.getElementById('saveButton').disabled = true;
		</script>
	</div>
<%	} %>
