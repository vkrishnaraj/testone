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
<!-- Calendar includes -->
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
<!-- calendar stuff ends here -->
<script language="javascript">
    <!--
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
// -->
  </script>

<html:form action="worldtraceraf.do" method="post">

	<jsp:include page="worldtracer_viewaf_header.jsp" />


	<!-- END PAGE HEADER/SEARCH -->
	<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green">
		<%
			String header_text = "fw";
				if (request.getAttribute("wt_type") != null
						&& request.getAttribute("wt_type").equals("FW")) {
				header_text = "fw";
		%> <bean:message key="header.wt_fw" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("AA")) {
 			header_text = "aa";
 %> <bean:message key="header.wt_aa" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("WM")) {
 			header_text = "wm";
 %> <bean:message key="header.wt_wm" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("SP")) {
 			header_text = "sp";
 %> <bean:message key="header.wt_sp" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("AP")) {
 			header_text = "ap";
 %> <bean:message key="header.wt_ap" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("CM")) {
 			header_text = "cm";
 %> <bean:message key="header.wt_cm" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("EM")) {
 			header_text = "em";
 %> <bean:message key="header.wt_em" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("LM")) {
 			header_text = "lm";
 %> <bean:message key="header.wt_lm" /> <%
 	} else if (request.getAttribute("wt_type") != null
 				&& request.getAttribute("wt_type").equals("PR")) {
 			header_text = "pr";
 %> <bean:message key="header.wt_pr" /> <%
 	}else if(request.getAttribute("wt_type")!=null&&request.getAttribute("wt_type").equals("CW")){
 %> <bean:message key="header.wt_cw"/><%
            header_text="cw";
 	}
 	%>
 	<c:if test="${af_day != '-1'}">
 	&nbsp;<bean:message key="header.wt_day"/>&nbsp;<c:out value="${af_day}" default="1" />
 	</c:if>
 <%
 	if (request.getParameter("ahl_id") != null
 				&& request.getParameter("ahl_id").length() > 9) {
 			out.println("&nbsp;&nbsp;( "
 					+ request.getParameter("ahl_id") + " )");
 		} else if (request.getParameter("ohd_id") != null
 				&& request.getParameter("ohd_id").length() > 9) {
 			out.println("&nbsp;&nbsp;( "
 					+ request.getParameter("ohd_id") + " )");
 		}
 %> <a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img
			src="deployment/main/images/nettracer/button_help.gif" width="20"
			height="21" border="0"></a></h1>

		<%
			if ((request.getParameter("ahl_id") != null && request
						.getParameter("ahl_id").length() > 9)
						|| (request.getParameter("ohd_id") != null && request
								.getParameter("ohd_id").length() > 9)) {
		%>
		<div align="right" style=""><a
			href="worldtraceraf.do?viewaction=<%=request.getAttribute("wt_type") %>"><bean:message
			key="link.back_to_all" /></a></div>
		<%
			}
		%> <br>

		<font color=red> <logic:messagesPresent message="true">
			<html:messages id="msg" message="true">
				<br />
				<bean:write name="msg" />
				<br />
			</html:messages>
		</logic:messagesPresent> </font> <logic:equal name="afdeleted" value="1" scope="request">
			<p align="center"><font color="green"><bean:message
				key="colname.af_del" /></font></p>
		</logic:equal> <logic:notEqual name="wt_id_specific" value="1" scope="request">
			<table class="form2" cellspacing="0" cellpadding="0">
				<tr>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=1">Day
					1:</a></td>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=2">Day
					2:</a></td>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=3">Day
					3:</a></td>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=4">Day
					4:</a></td>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=5">Day
					5:</a></td>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=6">Day
					6:</a></td>
					<td><a
						href="worldtraceraf.do?viewaction=<%=header_text %>&d=7">Day
					7:</a></td>
				</tr>
			</table>
		</logic:notEqual> <logic:present name="resultlist" scope="request">
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
				<c:forEach var="actionData" items="${resultlist}">
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
						<a href="javascript:void(0);" onclick="openWindow('worldtraceraf.do?rawtext=1&ahl_id=${actionData.wt_incident_id}','wtrawtext',500,600);return false;">${actionData.wt_incident_id}</a>
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
							<c:url	value="/addOnHandBag.do" var="ohdLink">
								<c:param name="ohd_ID" value="${actionData.ohd_id}" />
							</c:url>
							<bean:message key="nettracer.id" />:&nbsp;<a href="${ohdLink}">${actionData.ohd_id}</a>
							<br /><bean:message key="worldtracer.id" />:&nbsp;${actionData.wt_ohd_id}
						</c:when>
						<c:when test="${!empty actionData.wt_ohd_id}">
						<bean:message key="worldtracer.id" />:&nbsp;
						<a href="javascript:void(0);" onclick="openWindow('worldtraceraf.do?rawtext=1&ohd_id=${actionData.wt_ohd_id}','wtrawtext',500,600);return false;">${actionData.wt_ohd_id}</a>
						</c:when>
						<c:otherwise>
							<c:url	value="/addOnHandBag.do" var="ohdLink">
								<c:param name="wt_af_id" value="${actionData.af_id}" />
							</c:url>
							<a href="${ohdLink}"><bean:message key="no.wt_ohd.id" /></a>
						</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:out value="${actionData.af_text}" escapeXml="false"/>
					</td>
					<td><a
							href="worldtraceraf.do?viewaction=<%=header_text %>&d=<%=request.getParameter("d")%>&delete=${actionData.af_id}">Delete</a>
					</td>
				</c:forEach>
				<tr>
					<td colspan="3"><!-- pagination --> <jsp:include
						page="/pages/includes/pagination_incl.jsp" /> <!-- eof pagination -->
					</td>
				</tr>
				<!-- end pagination -->
			</table>

		</logic:present> <input type="hidden" name="viewaction"
			value="<%=request.getParameter("viewaction") %>"> <input
			type="hidden" name="d" value="<%=request.getParameter("d") %>">
		<input type="hidden" name="wt_id_specific"
			value="<%=request.getAttribute("wt_id_specific") %>"> <input
			type="hidden" name="ahl_id"
			value="<%=request.getParameter("ahl_id") %>"> <input
			type="hidden" name="ohd_id"
			value="<%=request.getParameter("ohd_id") %>">
</html:form>
