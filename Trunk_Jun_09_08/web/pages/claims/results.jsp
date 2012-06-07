<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="aero.nettracer.fs.model.detection.AccessRequest" %>
<%@ page import="aero.nettracer.fs.model.FsClaim" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="java.util.LinkedHashSet" %>
<%@page import="com.bagnet.nettracer.tracing.forms.FraudResultsForm"%>

<tr>
	<td class="header">
		<bean:message key="colname.fraudresults.select.request" />
	</td>
	<td class="header">
		<bean:message key="colname.fraudresults.select.delete" />
	</td>
	<td class="header">
		<bean:message key="colname.reference.id" />
	</td>
	<td class="header">
		<bean:message key="colname.reference.type" />
	</td>
	<td class="header">
		<bean:message key="colname.fraudresults.company" />
	</td>
	<td class="header">
		<bean:message key="colname.fraudresults.claim_date" />
	</td>
	<td class="header">
		<bean:message key="colname.fraudresults.match_summary" />
	</td>
	<td class="header">
		<bean:message key="colname.fraudresults.details" />
	</td>
</tr>
<% String results = request.getParameter("beanProperty"); %>

<% boolean hasResults = false;%>
<logic:iterate id="result" indexId="i" name='<%=request.getParameter("beanName") %>' property='<%=results %>' type="aero.nettracer.fs.model.detection.MatchHistory" >
<% 
	hasResults = true;
	Agent a = (Agent)session.getAttribute("user");
	AccessRequest.RequestStatus status = result.getFile2().getRequestStatus();
	String company = request.getParameter("company");
	String disStatus = result.getFile2().getDisStatus();
	boolean sameCompany = result.getFile2().getValidatingCompanycode().equals(company);
	boolean ntUser = PropertyBMO.isTrue("nt.user");
	FsClaim[] claims = result.getFile2().getClaims().toArray(new FsClaim[0]);
	if (claims != null && claims.length > 0) { %>
	<tr <%=disStatus %>>
		<td rowspan=<%=claims.length %>><input type="checkbox" name="<%=results%>[<%=i%>].requestSelected" <% if (sameCompany) { %>disabled<% } %> /></td>
		<td rowspan=<%=claims.length %>><input type="checkbox" name="<%=results%>[<%=i%>].deleteSelected" /></td>
		<% 
			int j = 0;
			if (sameCompany) { %>
			<td>
				<bean:message key="colname.claim.id" />:&#160;<a href="claim_resolution.do?claimId=<%=claims[j].getSwapId() %>">
					<%=claims[j].getSwapId() %>
				</a><br/>
				<% if (claims[j].getIncident().getAirlineIncidentId() != null) { 
						if (ntUser) { %>
				<bean:message key="claim.incident.number" />:&#160;<a href="searchIncident.do?incident=<%=claims[j].getIncident().getAirlineIncidentId() %>">
					<%=claims[j].getIncident().getAirlineIncidentId() %>
				</a>
					<% } else { %>
				<bean:message key="claim.incident.number" />:&#160;<%=claims[j].getIncident().getAirlineIncidentId() %>
					<% }
					} %>
			</td>
		<% } else { %>
			<td><bean:message key="colname.claim.id" />:&#160;<%=claims[j].getSwapId() %><br/>
			<% if (claims[j].getIncident().getAirlineIncidentId() != null) { %>
				<bean:message key="claim.incident.number" />:&#160;<%=claims[j].getIncident().getAirlineIncidentId() %>
			<% } %>
			</td>
		<% } %>
		<td>
			<%
				switch (claims[j].getClaimType()) {
					case 0: %>
						<bean:message key="match.type.claim" />
					<%	break;
					case TracingConstants.LOST_DELAY: %>
						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
					<%	break;
					case TracingConstants.MISSING_ARTICLES: %>
						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
					<%	break;
					case TracingConstants.DAMAGED_BAG: %>
						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
					<%	break;
					default:
						break;
				}
			%>
		</td>
		<td><%=claims[j].getAirline() %></td>
		<td><%=claims[j].getDisClaimDate(a.getDateformat().getFormat()) %></td>
		<td rowspan=<%=claims.length %>>
			<%=result.getFile2().getDisStatusText() %>
			<bean:write name="result" property="matchSummary" filter="false" />
		</td>
		<td rowspan=<%=claims.length %>>
			<a href="fraud_results.do?matchId=<%=result.getId() %><logic:present name="incident" scope="request">&incident=<bean:write name="incident" scope="request"/></logic:present>">
				<bean:message key="claim.match.details" />
			</a>
			<% if (!sameCompany) { %>
			<br/><br/>
			<b><%=status == null ? "" : status %></b>
			<% } %>
		</td>
	</tr>
	<% if (claims.length > 1) {
		   for (j = 1; j < claims.length; ++j) { %>
			<tr>
		<!-- HERE'S WHERE THE FUN BEGINS -->
		<% if (sameCompany) { %>
			<td>
				<a href="claim_resolution.do?claimId=<%=claims[j].getSwapId() %>">
					<%=claims[j].getSwapId() %>
				</a>
			</td>
		<% } else { %>
			<td><%=claims[j].getSwapId() %></td>
		<% } %>
		<td>
			<%
				switch (claims[j].getClaimType()) {
					case 0: %>
						<bean:message key="match.type.claim" />
					<%	break;
					case TracingConstants.LOST_DELAY: %>
						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
					<%	break;
					case TracingConstants.MISSING_ARTICLES: %>
						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
					<%	break;
					case TracingConstants.DAMAGED_BAG: %>
						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
					<%	break;
					default:
						break;
				}
			%>
		</td>
		<td><%=claims[j].getAirline() %></td>
		<td><%=claims[j].getDisClaimDate(a.getDateformat().getFormat()) %></td>
		
		</tr>
		<% } 
		} %>
		
		
		
			<% } else { %>
				<tr <%=disStatus %>>
					<td><input type="checkbox" name="<%=results%>[<%=i%>].requestSelected" <% if (sameCompany) { %>disabled<% } %> /></td>
					<td><input type="checkbox" name="<%=results%>[<%=i%>].deleteSelected" /></td>
					<% if (result.getFile2().getIncident().getAirline().equals(company)) { %>
					<td>
						<a href="searchIncident.do?incident=<%=result.getFile2().getIncident().getAirlineIncidentId() %>">
							<bean:write name="result" property="file2.incident.airlineIncidentId" />
						</a>
					</td>
					<% } else { %>
					<td><bean:write name="result" property="file2.incident.airlineIncidentId" /></td>
			<% } %>
					<td>
			<logic:equal name="result" property="file2.incident.incidentType" value="0" >
				<bean:message key="match.type.incident" />
			</logic:equal>
			<logic:equal name="result" property="file2.incident.incidentType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
				<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.lostdelay" />
			</logic:equal>
			<logic:equal name="result" property="file2.incident.incidentType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
				<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.missing" />
			</logic:equal>
			<logic:equal name="result" property="file2.incident.incidentType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
				<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.damaged" />
			</logic:equal>
		</td>
		<td><bean:write name="result" property="file2.incident.airline" /></td>
		<td><%=result.getFile2().getIncident().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		<td>
			<%=result.getFile2().getDisStatusText() %>
			<bean:write name="result" property="matchSummary" filter="false" />
		</td>
		<!-- FUN'S OVER!! -->
		
		<td>
			<a href="fraud_results.do?matchId=<%=result.getId() %>&incident=<bean:write name="incident" scope="request"/>">
				<bean:message key="claim.match.details" />
			</a>
			<% if (!sameCompany) { %>
			<br/><br/>
			<b><%=status == null ? "" : status %></b>
			<% } %>
					</td>
				</tr>
			<% } %>
</logic:iterate>
<% if(hasResults == false){%>
<tr>
	<td colspan="8">
	<bean:message key="fraud.no.results"/>
	</td>
</tr>

<% } %>