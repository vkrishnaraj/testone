<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="css/master.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="js/protofx.js"></script>
		<script language="javascript" src="js/nettracer.js"></script>

		<title><spring:message code="pv0" />
		</title>
	</head>
	<body>
		<form:form method="POST" action="search.htm" name="searchForm">
			<table cellspacing="0" width="600" border="0">
				<tr>
					<td>
  						<img src="images/pvlogo.jpg" />
					</td>
					<!-- <td valign="top" align="right">
					<c:choose>
						<c:when test="${siteLanguage == 'en'}">
						<a href="search.htm?locale=br"><spring:message code="port"/></a>
						</c:when>
						<c:when test="${(empty siteLanguage) and cookie.userLanguage.value == 'en'}">
						<a href="search.htm?locale=br"><spring:message code="port"/></a>
						</c:when>
						<c:otherwise>
						<a href="search.htm?locale=en"><spring:message code="english"/></a>
						</c:otherwise>
					</c:choose>
					</td> -->
				</tr>
				<tr>
					<td id="topcell" colspan="2">
						<!-- here -->

						<div id="content" style="">

							<p />
							<h3>
								<spring:message code="pv1" />
							</h3>
							<p>
								<spring:message code="pv2" />
							</p>
							<p>
								<spring:message code="pv2.more" />
							</p>
							<c:if test="${!empty noneFound }">
								<p>
									<font color=red><spring:message code="no.incident.found" />
									</font>
								</p>
							</c:if>

							<div id="formbox" style="margin-top: 1em; width: 575px;">

								<h3>
									<spring:message code="pv3" />
								</h3>
								<table width="100%">
									<tr>
										<td class="label" width="15%">
											<span><spring:message code="pv4" />:</span>
										</td>
										<td width="25%">
											<form:input path="lastname" tabindex="1" cssClass="textField"
												size="20" maxlength="50" />
													<spring:hasBindErrors name="command">
														<br/><font color=red><form:errors path="lastname" /></font>
													</spring:hasBindErrors>			
										</td>
										<td width="2%"></td>
										<td width="48%" rowspan="3">

											<p>
												<br />
												<font style="font-size: 11px;"><spring:message
														code="pv5" />
												</font>
											</p>

										</td>
									</tr>
									<tr>
										<td width="15%" class="label">
											<span><spring:message code="pv6" />:</span>
										</td>
										<td width="25%">
											<form:input path="claimnumber" tabindex="2"
												cssClass="textField" size="20" maxlength="50"
												onblur="fillzero(this,13);" />
												<spring:hasBindErrors name="command">
													<br/><font color=red><form:errors path="claimnumber" /></font>
												</spring:hasBindErrors>
										</td>
										<td width="2%"></td>
									</tr>
									<tr>

										<td>
											&nbsp;
										</td>
										<td>
											<span class="button" style="width: 100px;">
												<input type="submit" tabindex="3" value='<spring:message code="pv7" />' align="absmiddle" />
											</span>
										</td>
										<td width="2%"></td>
									</tr>
								</table>
							</div>
							<p />

						</div>
						</td>
						</tr>
						</table>
						</form:form>

						</body>
</html>
