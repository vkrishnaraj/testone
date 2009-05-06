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
	String headerKey = ("header.wt_" + request.getAttribute("afType")).toLowerCase();
%>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>

<script language="javascript">
    
function goprev() {
  o = document.worldtracerafForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.worldtracerafForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.worldtracerafForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
<html:form action="actionFileSummary.do" method="post">
	<jsp:include page="worldtracer_viewaf_header.jsp" />


	
	<tr>
		
		<td id="middlecolumn">
		<div id="maincontent">
		<h1 class="green">
		<bean:message key="<%= headerKey %>" />
 	&nbsp;<bean:message key="header.wt_day"/>&nbsp;<c:out value="${day}" default="1" />
  <a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img
			src="deployment/main/images/nettracer/button_help.gif" width="20"
			height="21" border="0"></a></h1>

 <br>

		<font color=red> <logic:messagesPresent message="true">
			<html:messages id="msg" message="true">
				<br />
				<bean:write name="msg" />
				<br />
			</html:messages>
		</logic:messagesPresent> </font> <logic:equal name="deleted" value="1" scope="request">
			<p align="center"><font color="green"><bean:message
				key="colname.af_del" /></font></p>
		</logic:equal>
			<table class="form2" cellspacing="0" cellpadding="0">
				<tr>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=1">Day
					1:</a></td>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=2">Day
					2:</a></td>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=3">Day
					3:</a></td>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=4">Day
					4:</a></td>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=5">Day
					5:</a></td>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=6">Day
					6:</a></td>
					<td><a
						href="actionFileSummary.do?category=<c:out value='${afType}'/>&day=7">Day
					7:</a></td>
				</tr>
			</table>
		<logic:present name="afList" scope="request">
			<h1 class="green"><bean:message key="header.search_result" /> <a
				href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Worldtracer_actionfiles');return false;"><img
				src="deployment/main/images/nettracer/button_help.gif" width="20"
				height="21" border="0"></a></h1>
			<a name="result"></a>
			<table class="form2" cellspacing="0" cellpadding="0" width="500">
				<tr>
					<td><b><bean:message key="colname.wt_ahl_id" /></b></td>
					<td><b><bean:message key="colname.wt_ohd_id" /></b></td>
					<td><b><bean:message key="colname.actionfiles" /></b></td>
					<td><b><bean:message key="colname.deleteaf" /></b></td>
				</tr>
				<c:forEach var="actionData" items="${afList}">
				<tr>
					<td>
						<c:choose>
						<c:when test="${!empty actionData.incident_id}">
							<c:url	value="/lostDelay.do" var="incidentLink">
								<c:param name="incident_ID" value="${actionData.incident_id}" />
							</c:url>
							<bean:message key="nettracer.id" />:&nbsp;<a href="${incidentLink}">${actionData.incident_id}</a>
							<br /><bean:message key="worldtracer.id" />:&nbsp;${actionData.wt_incident_id}
						</c:when>
						<c:when test="${!empty actionData.wt_incident_id}">
						<bean:message key="worldtracer.id" />:&nbsp;
						<a href="searchIncident.do?wt_id=${actionData.wt_incident_id}">${actionData.wt_incident_id}</a>
            
						</c:when>
						<c:otherwise>
							<c:url	value="/lostDelay.do" var="incidentLink">
								<c:param name="wt_af_id" value="${actionData.af_id}" />
							</c:url>

							<a href="${incidentLink}"><bean:message key="no.wt_ahl.id" /></a>
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${!empty actionData.ohd_id}">
								<c:url value="/addOnHandBag.do" var="ohdLink">
									<c:param name="ohd_ID" value="${actionData.ohd_id}" />
								</c:url>
								<bean:message key="nettracer.id" />:&nbsp;<a href="${ohdLink}">${actionData.ohd_id}</a>
								<br />
								<bean:message key="worldtracer.id" />:&nbsp;${actionData.wt_ohd_id}
								<br />
							<a href="worldtracerroh.do?wt_ohd_id=${actionData.wt_ohd_id}&wt_ahl_id=${actionData.wt_incident_id}"><bean:message key="wt.request.ohd"/></a>
						</c:when>
							<c:when test="${!empty actionData.wt_ohd_id}">
								<bean:message key="worldtracer.id" />:&nbsp;
						<a href="addOnHandBag.do?wt_id=${actionData.wt_ohd_id}">${actionData.wt_ohd_id}</a>
						<br />
						<a href="worldtracerroh.do?wt_ohd_id=${actionData.wt_ohd_id}&wt_ahl_id=${actionData.wt_incident_id}"><bean:message key="wt.request.ohd"/></a>
							</c:when>
							<c:otherwise>
								<c:url value="/addOnHandBag.do" var="ohdLink">
									<c:param name="wt_af_id" value="${actionData.af_id}" />
								</c:url>
								<a href="${ohdLink}"><bean:message key="no.wt_ohd.id" /></a>
							</c:otherwise>
						</c:choose>
					</td>
						<td><c:choose>
							<c:when test="${empty actionData.af_text}">
								<c:out value="${actionData.af_summary}" escapeXml="false" />
								&nbsp;
								<a href="actionFileDetail.do?category=${afType}&day=${day}&itemNum=${actionData.item_number}&currpage=${currpage}&rowsperpage=${rowsperpage}"><bean:message key="wt.af.details" /></a>
							</c:when>
							<c:otherwise>
								<c:out value="${actionData.af_text}" escapeXml="false" />
							</c:otherwise>
						</c:choose></td>
						<td><a
							href="actionFileDelete.do?category=${afType}&day=${day}&itemNum=${actionData.item_number}&currpage=${currpage}&rowsperpage=${rowsperpage}"><bean:message key="wt.af.delete" /></a>
					</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="3"> <jsp:include
						page="/pages/includes/pagination_incl.jsp" /> 
					</td>
				</tr>
			</table>
		</logic:present>
		<input type="hidden" name="category" value='<%=request.getParameter("category") %>' />
		<input type="hidden" name="day" value='<%=request.getParameter("day") %>' />

</html:form>
