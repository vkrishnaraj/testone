<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

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
				onclick="openHelp('pages/WebHelp/NetTracer.htm#Retrieve.htm#Worldtracer_actionfiles');return false;"><img
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
				<logic:iterate id="results" name="resultlist"
					type="com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles">
					<tr>
						<%
							String res = results.getAction_file_text();
											res = res.replace("\n", "<br>");
						%>
						<td><logic:notEqual name="results" property="wt_incident_id"
							value="">
							<a href="javascript:void(0);"
								onclick="openWindow('worldtraceraf.do?rawtext=1&ahl_id=<bean:write name="results" property="wt_incident_id" />','wtrawtext',500,600);return false;"><bean:write
								name="results" property="wt_incident_id" /></a>
						&nbsp;
						</logic:notEqual> <logic:equal name="results" property="wt_incident_id" value="">
							<a
								href="lostDelay.do?wt_af_id=<bean:write name="results" property="id" />">No_AHL_ID</a>
						</logic:equal></td>

						<td><logic:notEqual name="results" property="wt_ohd_id"
							value="">
							<a href="javascript:void(0);"
								onclick="openWindow('worldtraceraf.do?rawtext=1&ohd_id=<bean:write name="results" property="wt_ohd_id" />','wtrawtext',500,600);return false;"><bean:write
								name="results" property="wt_ohd_id" /></a>
                    	&nbsp;
                    	</logic:notEqual> <logic:equal name="results" property="wt_ohd_id"
							value="">
							<a
								href="addOnHandBag.do?wt_af_id=<bean:write name="results" property="id" />">No_OHD_ID</a>
						</logic:equal></td>

						<td>
						<%
							out.println(res);
											// <bean:write name="results" property="action_file_text" />
						%>
						</td>
						<td><a
							href="worldtraceraf.do?viewaction=<%=header_text %>&d=<%=request.getParameter("d")%>&delete=<bean:write name="results" property="id"/>">Delete</a></td>

					</tr>
				</logic:iterate>
				<tr>
					<td colspan="3"><!-- pagination --> <jsp:include
						page="../includes/pagination_incl.jsp" /> <!-- eof pagination -->
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
