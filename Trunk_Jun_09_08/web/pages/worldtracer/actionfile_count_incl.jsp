<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<table class="form2" cellspacing="0" cellpadding="0">
	<tr>
		<td><b><bean:message key="column.file_type" /> </b></td>
		<td>Day1</td>
		<td>Day2</td>
		<td>Day3</td>
		<td>Day4</td>
		<td>Day5</td>
		<td>Day6</td>
		<td>Day7</td>
	</tr>
	<c:if test="${empty afCounts}" >
		<tr>
		<td colspan="8">
			<bean:message key="no.actionFiles"/>
		</td>
		</tr>
	</c:if>
	<c:forEach var="entry" items="${afCounts}">
		<tr>
			<td><bean:message key="actionfile.${entry.key}" /></td>
				<td><c:choose>
					<c:when test="${entry.value.dayOne == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="1" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.dayTwo} </a>
					</c:otherwise>
				</c:choose></td>
				<td><c:choose>
					<c:when test="${entry.value.dayOne == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="2" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.dayTwo} </a>
					</c:otherwise>
				</c:choose></td>
				<td><c:choose>
					<c:when test="${entry.value.dayThree == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="3" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.dayThree} </a>
					</c:otherwise>
				</c:choose></td>
				<td><c:choose>
					<c:when test="${entry.value.dayFour == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="4" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.dayFour} </a>
					</c:otherwise>
				</c:choose></td>
				<td><c:choose>
					<c:when test="${entry.value.dayFive == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="5" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.dayFive} </a>
					</c:otherwise>
				</c:choose></td>
				<td><c:choose>
					<c:when test="${entry.value.daySix == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="6" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.daySix} </a>
					</c:otherwise>
				</c:choose></td>
				<td><c:choose>
					<c:when test="${entry.value.daySeven == 0}">
											0
										</c:when>
					<c:otherwise>
						<c:url value="/actionfileSummary.do" var="actionFileLink">
							<c:param name="category" value="${entry.key}" />
							<c:param name="day" value="7" />
						</c:url>
						<a href="${actionFileLink}"> ${entry.value.daySeven} </a>
					</c:otherwise>
				</c:choose></td>
		</tr>
	</c:forEach>

</table>