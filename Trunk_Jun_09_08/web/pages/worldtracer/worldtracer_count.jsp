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

<html:form action="worldtracercount.do" method="post">
	<jsp:include page="worldtracer_viewaf_header.jsp" />
	
	<tr>
		
		<td id="middlecolumn">
		<div id="maincontent">
		<h1 class="green">
		<div id="pageheaderleft">
		<bean:message key="header.file_count" />
 <a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img
			src="deployment/main/images/nettracer/button_help.gif" width="20"
			height="21" border="0"></a></h1>
			</div>
		<div id="pageheaderright">
			<logic:present name="cbroStationID" scope="session">
	            <bean:message key="Station" /> : 
	            <select name="cbroStation" onchange="submit()" class="textfield">
	              <logic:iterate id="station" name="stationlist" scope="session" type="com.bagnet.nettracer.tracing.db.Station">
	                <option value="<bean:write name="station" property="station_ID"/>" <% if (session.getAttribute("cbroStationID").equals("" + station.getStation_ID())) { %> selected <% } %>>
	                <bean:write name="station" property="stationcode" />
	                </option>
	              </logic:iterate>
	            </select>
	        </logic:present>
		</div>
		<br>
		<font color="red"> <logic:messagesPresent message="true">
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
								<bean:message key="actionfile.${countActionFile.type}" />
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
