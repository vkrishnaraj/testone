<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
Agent a = (Agent) session.getAttribute("user");
OnHandForm onHandForm = (OnHandForm) session.getAttribute("OnHandForm");
%>

<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.forms.OnHandForm"%><table class='<%=request.getParameter("tableClass")%>' cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap="nowrap" width="100" >
			<div id="faultstationdiv">
				<html:hidden property="faultstation_ID" />
				<bean:message key="colname.faultstation" />
				<br />
				<c:choose>
					<c:when test="${OnHandForm.faultstation_ID == 0}">
						<bean:message key="none"/>
					</c:when>
					<c:otherwise>
						<%= StationBMO.getStation(onHandForm.getFaultstation_ID()).getStationcode() %>					
					</c:otherwise>
				</c:choose>
			</div>
		</td>
		<td nowrap="nowrap" width="250">
			<html:hidden property="loss_code"/>
			<bean:message key="colname.closereport.losscode" />
			<br />
			<c:choose>
				<c:when test="${OnHandForm.loss_code == 0}">
					<bean:message key="none"/>
				</c:when>
				<c:otherwise>
					<%= LossCodeBMO.getCode(onHandForm.getLoss_code()).getDescription() %>					
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>