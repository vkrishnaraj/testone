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
	/*int divId = Integer.valueOf((String) request.getAttribute("divId"));*/
	String cssFormClass = "form2";
%>
<logic:notEmpty name="count" scope="request" >
<logic:notEqual name="count" property="boxCount" value="0">
 <div id="<bean:write  name="count" property="sourceId" />" >
<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
     				<tr id="<bean:write  name="count" property="sourceId" />">
     					
	    				<td style="width:20%;">
	    					<span id="countId<bean:write  name="count" property="sourceId" />">
	    						<bean:write property="boxCount" name="count" />
	    					</span>
	    				</td>
	    				<td style="width:20%;">
	    					<span id="stationName">
	    						<bean:write  name="count" property="sourceName" />
	    					</span>
	    				</td>
	    				<td style="width:40%;">
	    					<center>
	     						<a href='#' id="rem<bean:write  name="count" property="sourceId" />" onclick="remStationAjax('<bean:write name="count" property="sourceId" />')" ><bean:message key="lf.salvage.remove" /></a>
	    					</center>
	    				</td>
     				</tr>
     				</table>
</div>
</logic:notEqual>
</logic:notEmpty>
<logic:empty name="count" scope="request" >
	<table class="<%=cssFormClass %>" style="margin:0;padding:0;" cellspacing=0 cellpadding=0 >
		<tr>
			<td colspan=4 class="summaryActionItem">
				<%=(String) request.getAttribute("message") %>
			</td>
		</tr>
	</table>
</logic:empty>
