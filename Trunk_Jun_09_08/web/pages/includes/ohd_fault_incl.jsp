<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<table class='<%=request.getParameter("tableClass")%>' cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap="nowrap" width="100" >
			<div id="faultstationdiv">
			<c:if test="${!empty faultStationList}">
				<bean:message key="colname.faultstation" />
				<br />
				<html:select property="faultstation_ID" styleClass="dropdown">
					<html:option value="0">
						<bean:message key="select.please_select" />
					</html:option>
					<c:forEach items="${faultStationList}" var="fs">
						<html:option value="${fs.station_ID}">${fs.stationcode}</html:option>
					</c:forEach>
				</html:select>
			</c:if>
			</div>
		</td>
		<td nowrap="nowrap" width="250">
			<bean:message key="colname.closereport.losscode" />
			<br />
			<html:select property="loss_code" styleClass="dropdown">
				<html:option value="0">
					<bean:message key="select.please_select" />
				</html:option>
				<c:forEach items="${lossCodes}" var="lc">
				<html:option value="${lc.code_id}">
					${lc.description}
				</html:option>
				</c:forEach>
			</html:select>
		</td>
	</tr>
</table>