<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="css/master.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/nettracer.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><spring:message code="page.title" />
		</title>
	</head>
	<body>
	<div id="wrapper">
		
				<c:choose>
						<c:when test="${siteLanguage == 'fr'}">
							<div id="header_fr"></div>
						</c:when>
						<c:when		test="${(empty siteLanguage) and cookie.userLanguage.value == 'fr'}">
							<div id="header_fr"></div>
						</c:when>
						<c:otherwise>
							<div id="header"></div>
						</c:otherwise>
					</c:choose>	
			
			<!-- /header -->
			<div id="content">
				<h1>
					<spring:message code="search.header" />
				</h1>
				<div id="francais">
					&gt;&gt;
					<c:choose>
						<c:when test="${siteLanguage == 'fr'}">
							<a href="search.htm?locale=en"><spring:message code="english" />
							</a>
						</c:when>
						<c:when
							test="${(empty siteLanguage) and cookie.userLanguage.value == 'fr'}">
							<a href="search.htm?locale=en"><spring:message code="english" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="search.htm?locale=fr"><spring:message code="french" />
							</a>
						</c:otherwise>
					</c:choose>
				</div>
				<!-- /francais -->
				<p>
					<strong><spring:message code="delayed.baggage" />
					</strong>:
					<c:choose>
						<c:when test="${param.message == 1}"><spring:message code="notice11" /></c:when>
						<c:otherwise><spring:message code="notice1" /></c:otherwise>
					</c:choose>
				</p>

				<form:form method="POST" action="search.htm">
					<table cellspacing="0" cellpadding="0" id="inputDelayedBags">
						<c:if test="${!empty noneFound }">
							<tr>
								<td>
									&nbsp;
								</td>
								<td class="formError" colspan="2">
									<spring:message code="no.incident.found" />
								</td>
							</tr>
						</c:if>
						<tr>
							<td>
								<label for="last name">
									<spring:message code="field.lastname" />
									:
								</label>
							</td>
							<td class="formField">
								<form:input path="lastname" tabindex="1" cssClass="formField" />
							</td>
							<td class="formError">
								<form:errors path="lastname" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="claim number">
									<spring:message code="field.claimnumber" />
									:
								</label>
							</td>
							<td class="formField">
								<form:input path="claimnumber" tabindex="2" cssClass="formField"
									onblur="fillzero(this,13);" />
							</td>
							<td class="formError">
								<form:errors path="claimnumber" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td class="smallFormText" colspan="2">
								<strong><spring:message code="field.claimnumber" />
								</strong> -
								<spring:message code="claim.omit" />
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;
							</td>
							<td>
								<div id="searchButtonContainer">
									<input type="submit" class="button" tabindex="3" value='<spring:message code="submit.query"/>' />
								</div>
							</td>
						</tr>
					</table>
				</form:form>
				<div class="hr"></div>
				<p>
					<img src="images/icon_Advisory.png" alt="Important Note" width="20"
						height="20" style="float: left; margin: 0 10px 100px 0;" />
					<spring:message code="notice2" />
				</p>
			</div>
			<!-- /content -->
			<div id="footer">
				<div id="copyright">
					<spring:message code="rights.reserved" />
				</div>
			</div>
			<!-- /footer -->
		</div>
	</body>
</html>
