<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

<html:form action="worldtracercount.do" method="post">
	<jsp:include page="worldtracer_viewaf_header.jsp" />
	<!-- END PAGE HEADER/SEARCH -->
	<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green">
		<bean:message key="header.file_count" />
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
 	}
 %> <%
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
			href="worldtraceraf.do?viewaction=<%=Integer.parseInt((String)request.getAttribute("wt_type"))-1 %>"><bean:message
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
		</logic:messagesPresent> </font>
				<table class="form2" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<b><bean:message key="column.file_type" />
							</b>
						</td>
						<td>Day1</td>
						<td>Day2</td>
						<td>Day3</td>
						<td>Day4</td>
						<td>Day5</td>
						<td>Day6</td>
						<td>Day7</td>
					</tr>
					<c:forEach var="countActionFile" items="${countlist}">
						<tr>
							<td>
								<c:out value="${countActionFile.type}" />
							</td>
							<c:forEach var="amount" items="${countActionFile.counts}"
								varStatus="currentDay">
								<td>
									<c:choose>
										<c:when test="${amount == 0}">
											0
										</c:when>
										<c:otherwise>
											<c:url	value="/worldtraceraf.do" var="actionFileLink">
												<c:param name="viewaction" value="${countActionFile.type}" />
												<c:param name="d" value="${currentDay.index + 1}" />
											</c:url>
										<a href="${actionFileLink}" >
											${amount}
										</a>
										</c:otherwise>
									</c:choose>
								</td>
							</c:forEach>
						</tr>
					</c:forEach>

				</table>
				<br>
			<logic:notEmpty name="viewaction" scope="request">
				 <input type="hidden" name="viewaction" value='<bean:write name="viewaction" scope="request"/>'>
			</logic:notEmpty>
		 <logic:notEmpty name="type" scope="request">
		 	<input type="hidden" name="type" value='<bean:write name="type" scope="request"/>'>
		 </logic:notEmpty>
		 <logic:notEmpty name="d" scope="request">
		 	<input type="hidden" name="d" value='<bean:write name="d" scope="request"/>'>
		 </logic:notEmpty>
		 <logic:notEmpty name="count" scope="request">
		 	 <input type="hidden" name="count" value='<bean:write name="count" scope="request"/>'>
		 </logic:notEmpty>
		
</html:form>
