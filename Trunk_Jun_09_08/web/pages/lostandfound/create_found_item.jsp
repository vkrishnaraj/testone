<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2_dam";
%>


<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="" action="create_found_item.do" method="post" onsubmit="return validateSearch(this);">
<input type="hidden" name="delete_these_elements" value="" />
   	<tr>
   		<td id="middlecolumn">        
     		<div id="maincontent">
         		<logic:present name="reportfile" scope="request">
            		<center><a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a></center>
           		</logic:present>
				<h1 class="green">
		        	<bean:message key="header.found.item" />
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" /> 
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
       				<tr>
             			<td>
               				START FILLING CRAP OUT !!                  
             			</td>
           			</tr>
				</table>
   			</div>
   		</td>
   	</tr>
</html:form>
