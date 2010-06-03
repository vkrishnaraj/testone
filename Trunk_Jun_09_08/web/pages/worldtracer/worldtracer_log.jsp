<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
	Agent a = (Agent) session.getAttribute("user");
%>

<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>

<script language="javascript">
    
function goprev() {
  o = document.searchWtLogsForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchWtLogsForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchWtLogsForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}


  </script>

<html:form action="worldtracerlog.do" method="post" focus="txType" onsubmit="fillzero(this.incident_id, 13); fillzero(this.ohd_id, 13); return true;">
	<tr>
		<td colspan="3" id="pageheadercell">
		<div id="pageheaderleft">
		<h1><bean:message key="header.search_wt_logs" /></h1>
		</div>
		<div id="pageheaderright">
		<table id="pageheaderright">
			<tr>
				<jsp:include page="/pages/includes/mail_incl.jsp" />
				<td><a href="#"
					onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
					key="Help" /></a></td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	
	<tr>
		
		<td id="middlecolumn">
		<div id="maincontent">
		<h1 class="green"><bean:message key="header.search_criteria" />
		<a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img
			src="deployment/main/images/nettracer/button_help.gif" width="20"
			height="21" border="0"></a></h1>
		<br>
		<strong> <bean:message key="wildcard" /> </strong> <font color=red>
		<logic:messagesPresent message="true">
			<html:messages id="msg" message="true">
				<br />
				<bean:write name="msg" />
				<br />
			</html:messages>
		</logic:messagesPresent> </font>
		<table class="form2" cellspacing="0" cellpadding="0">
			<tr>

				<td width="35%"><bean:message key="colname.wt_txtype" /> <br>
				<html:select property="txType" styleClass="dropdown">
					<html:option value="ALL">
						<bean:message key="select.all" />
					</html:option>
					<c:forEach items="${wt_txtypes}" var="wt_tx">
						<html:option value="${wt_tx.value}">
							<bean:message key="${wt_tx.messageKey }" />
						</html:option>
					</c:forEach>
				</html:select></td>
				<td width="15%"><bean:message key="colname.wt_txstatus" /> <br />
				<html:select property="result" styleClass="dropdown">
					<html:option value="ALL">
						<bean:message key="select.all" />
					</html:option>
					<c:forEach items="${statuslist}" var="status">
						<html:option value="${status.value}">
							<bean:message key="${status.messageKey }" />
						</html:option>
					</c:forEach>
				</html:select></td>
				<td width="25%"><bean:message key="colname.incident_num" /> <br />
				<html:text property="incident_id" size="20" maxlength="13"
					styleClass="textfield" onblur="fillzero(this,13);" /></td>
				<td width="25%"><bean:message key="colname.ohd_ID" /> <br />
				<html:text property="ohd_id" size="20" maxlength="13"
					styleClass="textfield" onblur="fillzero(this,13);" /></td>
			</tr>
			<tr>
				<td nowrap="nowrap"><bean:message key="colname.date_range" />
				( <%=a.getDateformat().getFormat()%>) <br>
				<html:text property="startDate" size="10" maxlength="10"
					styleClass="textfield" /><img
					src="deployment/main/images/calendar/calendar_icon.gif"
					id="calendar" name="calendar" height="15" width="20" border="0"
					onmouseover="this.style.cursor='hand'"
					onClick="cal1xx.select(document.searchWtLogsForm.startDate,'calendar','<%=a.getDateformat().getFormat()%>'); return false;">-
				<html:text property="endDate" size="10" maxlength="10"
					styleClass="textfield" /><img
					src="deployment/main/images/calendar/calendar_icon.gif"
					id="calendar2" name="calendar2" height="15" width="20" border="0"
					onmouseover="this.style.cursor='hand'"
					onClick="cal1xx.select(document.searchWtLogsForm.endDate,'calendar2','<%=a.getDateformat().getFormat()%>'); return false;">
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center" valign="top"><html:submit
					property="search" styleId="button">
					<bean:message key="button.retrieve" />
				</html:submit> &nbsp; <input type="button" name="reset" id="button"
					value="<bean:message key="button.reset" />"
					onclick="document.location.href='worldtracerlog.do?reset=1';" /></td>
			</tr>
		</table>
		<c:if test="${!empty resultlist}">
			<div id="pageheaderleft">
			<h1 class="green"><bean:message key="header.search_result" /> <a
				href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img
				src="deployment/main/images/nettracer/button_help.gif" width="20"
				height="21" border="0"></a></h1>
			</div>
			<%-- build the result list --%>
			<a name="result"></a>
			<table class="form2" cellspacing="0" cellpadding="0" width="500">
				<tr>
					<th><bean:message key="colname.wt_txDate" /></th>
					<th><bean:message key="colname.wt_txType" /></th>
					<th><bean:message key="colname.wt_txResult" /></th>
					<th><bean:message key="colname.wt_error" /></th>
					<th><bean:message key="colname.incident_num" /></th>
					<th><bean:message key="colname.ohd_ID" /></th>
				</tr>
				<c:forEach var="wtTx" items="${resultlist}">
					<tr>
						<td><c:out value="${wtTx.createDate}" /></td>
						<td><bean:message key="${wtTx.txType.messageKey}" /></td>
						<td><bean:message key="${wtTx.result.messageKey}" /></td>
						<td><c:choose>
							<c:when test="${!empty wtTx.error}">
							${wtTx.error}
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose></td>
						<td><c:choose>
							<c:when test="${!empty wtTx.incident}">
								<a href="searchIncident.do?incident=${wtTx.incident.incident_ID}">${wtTx.incident.incident_ID}</a>
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose></td>
						<td><c:choose>
							<c:when test="${!empty wtTx.ohd}">
								<a href="addOnHandBag.do?ohd_ID=${wtTx.ohd.OHD_ID}">${wtTx.ohd.OHD_ID}</a>
							</c:when>
							<c:otherwise>&nbsp;</c:otherwise>
						</c:choose></td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="6"> <jsp:include
						page="/pages/includes/pagination_incl.jsp" /> 
					</td>
				</tr>
				
			</table>
			<script language=javascript>
                
  document.location.href="#result";

              </script>
		</c:if>
</html:form>
