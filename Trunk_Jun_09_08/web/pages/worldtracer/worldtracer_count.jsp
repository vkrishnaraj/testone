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
											<c:url	value="/worldtracercount.do" var="actionFileLink">
												<c:param name="viewaction" value="cw" />
												<c:param name="type" value="${countActionFile.type}" />
												<c:param name="d" value="${currentDay.index + 1}" />
												<c:param name="count" value="${amount }" />
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
				<table class="form2" cellspacing="0" cellpadding="0" width="500">
				<tr>
				   <tr>
					<td><b><bean:message key="colname.wt_ahl_id" /></b></td>
					<td><b><bean:message key="colname.wt_ohd_id" /></b></td>
					<td><b><bean:message key="colname.actionfiles" /></b></td>
					<td><b><bean:message key="colname.deleteaf" /></b></td>
				</tr>
				<logic:present name="displaylist" scope="request">
					<logic:iterate id="displayActionFile" name="displaylist" type="com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles">
						<tr>
						<%
							String res = displayActionFile.getAction_file_text();
											res = res.replace("\n", "<br>");
						%>
							
							<td><logic:notEqual name="displayActionFile" property="wt_incident_id"
							value="">
							<a href="javascript:void(0);"
								onclick="openWindow('worldtraceraf.do?rawtext=1&ahl_id=<bean:write name="displayActionFile" property="wt_incident_id" />','wtrawtext',500,600);return false;"><bean:write
								name="displayActionFile" property="wt_incident_id" /></a>
						&nbsp;
						</logic:notEqual> <logic:equal name="displayActionFile" property="wt_incident_id" value="">
							<a
								href="lostDelay.do?wt_af_id=<bean:write name="displayActionFile" property="id" />">No_AHL_ID</a>
						</logic:equal></td>

						<td><logic:notEqual name="displayActionFile" property="wt_ohd_id"
							value="">
							<a href="javascript:void(0);"
								onclick="openWindow('worldtraceraf.do?rawtext=1&ohd_id=<bean:write name="displayActionFile" property="wt_ohd_id" />','wtrawtext',500,600);return false;"><bean:write
								name="displayActionFile" property="wt_ohd_id" /></a>
                    	&nbsp;
                    	</logic:notEqual> <logic:equal name="displayActionFile" property="wt_ohd_id"
							value="">
							<a
								href="addOnHandBag.do?wt_af_id=<bean:write name="displayActionFile" property="id" />">No_OHD_ID</a>
						</logic:equal></td>
							<td>
							<%
							out.println(res);
											// <bean:write name="results" property="action_file_text" />
						    %>
							</td>
							<logic:notEmpty name="delete" scope="request">
							<td><a
							href=worldtracercount.do?viewaction=cw&type=<bean:write name="type" scope="request"/>&d=<bean:write name="d" scope="request"/>&count=<%=request.getAttribute("delete")%>&delete=<bean:write name="displayActionFile" property="id"/>>Delete</a>
							</td>
							</logic:notEmpty>
							<logic:empty name="delete" scope="request">
							<td><a
							href=worldtracercount.do?viewaction=cw&type=<bean:write name="type" scope="request"/>&d=<bean:write name="d" scope="request"/>&count=<%=request.getAttribute("count")%>&delete=<bean:write name="displayActionFile" property="id"/>>Delete</a>
							</td>
							</logic:empty>

						</tr>
					</logic:iterate>
				</logic:present>
				<tr>
					<td colspan="3"><!-- pagination --> <jsp:include
						page="../includes/pagination_incl.jsp" /> <!-- eof pagination -->
					</td>
				</tr>	 
			</table>
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
