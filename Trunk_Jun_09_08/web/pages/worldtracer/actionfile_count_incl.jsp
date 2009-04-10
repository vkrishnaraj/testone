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
		<td align="center">Day1</td>
		<td align="center">Day2</td>
		<td align="center">Day3</td>
		<td align="center">Day4</td>
		<td align="center">Day5</td>
		<td align="center">Day6</td>
		<td align="center">Day7</td>
	</tr>
	<c:if test="${empty afCounts}" >
		<tr>
		<td colspan="8"  align="center">
			<bean:message key="no.actionFiles"/>
		</td>
		</tr>
	</c:if>
	<c:forEach var="aftype" items="${afTypes}">
		<tr>
			<td><bean:message key="actionfile.${aftype}" /></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].dayOne) or  afCounts[aftype].dayOne == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="1" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].dayOne} </a>
					</c:otherwise>
				</c:choose></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].dayTwo) or  afCounts[aftype].dayTwo == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="2" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].dayTwo} </a>
					</c:otherwise>
				</c:choose></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].dayThree) or  afCounts[aftype].dayThree == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="3" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].dayThree} </a>
					</c:otherwise>
				</c:choose></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].dayFour) or  afCounts[aftype].dayFour == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="4" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].dayFour} </a>
					</c:otherwise>
				</c:choose></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].dayFive) or  afCounts[aftype].dayFive == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="5" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].dayFive} </a>
					</c:otherwise>
				</c:choose></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].daySix) or  afCounts[aftype].daySix == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="6" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].daySix} </a>
					</c:otherwise>
				</c:choose></td>
				<td align="center"><c:choose>
					<c:when test="${(empty afCounts[aftype].daySeven) or  afCounts[aftype].daySeven == 0}">
											&nbsp;
										</c:when>
					<c:otherwise>
						<c:url value="/actionFileSummary.do" var="actionFileLink">
							<c:param name="category" value="${aftype}" />
							<c:param name="day" value="7" />
						</c:url>
						<a href="${actionFileLink}"> ${afCounts[aftype].daySeven} </a>
					</c:otherwise>
				</c:choose></td>
		</tr>
	</c:forEach>

</table>