<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
	<c:if test="${empty afCounts}">
		<tr>
			<td colspan="8" align="center"><bean:message
				key="no.actionFiles" /></td>
		</tr>
	</c:if>
	<logic:present name="countListEmpty" scope="request">
		<tr>
			<td colspan="8" align="center"><bean:message
				key="no.actionFiles" /></td>
		</tr>
	</logic:present>

	<c:forEach var="counts" items="${afCounts}">
		<tr>
			<td><bean:message key="actionfile.${counts.af_type}" /> ${counts.af_seq}</td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.dayOne) or  counts.dayOne == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="1" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.dayOne} </a>
				</c:otherwise>
			</c:choose></td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.dayTwo) or  counts.dayTwo == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="2" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.dayTwo} </a>
				</c:otherwise>
			</c:choose></td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.dayThree) or  counts.dayThree == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="3" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.dayThree} </a>
				</c:otherwise>
			</c:choose></td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.dayFour) or  counts.dayFour == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="4" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.dayFour} </a>
				</c:otherwise>
			</c:choose></td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.dayFive) or  counts.dayFive == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="5" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.dayFive} </a>
				</c:otherwise>
			</c:choose></td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.daySix) or  counts.daySix == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="6" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.daySix} </a>
				</c:otherwise>
			</c:choose></td>
			<td align="center"><c:choose>
				<c:when test="${(empty counts.daySeven) or  counts.daySeven == 0}">
											&nbsp;
										</c:when>
				<c:otherwise>
					<c:url value="/actionFileSummary.do" var="actionFileLink">
						<c:param name="category" value="${counts.af_type}" />
						<c:param name="day" value="7" />
						<c:param name="seq" value="${counts.af_seq}" />
					</c:url>
					<a href="${actionFileLink}"> ${counts.daySeven} </a>
				</c:otherwise>
			</c:choose></td>
		</tr>
	</c:forEach>




</table>