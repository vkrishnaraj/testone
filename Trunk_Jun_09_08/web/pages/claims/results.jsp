<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="aero.nettracer.fs.model.detection.AccessRequest" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>

<tr>
	<td class="header">
		<bean:message key="colname.fraudresults.select" />
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
<logic:iterate id="result" indexId="i" name='<%=request.getParameter("beanName") %>' property='<%=results %>' type="aero.nettracer.fs.model.detection.MatchHistory" >
<% 
	Agent a = (Agent)session.getAttribute("user");
	AccessRequest.RequestStatus status = result.getFile2().getRequestStatus();
	String company = request.getParameter("company");
	boolean sameCompany;
	if (result.getFile2().getClaim() != null) {
		sameCompany = result.getFile2().getClaim().getAirline().equals(company);
	} else {
		sameCompany = result.getFile2().getIncident().getAirline().equals(company);
	}
	
	if (result.getFile2().getClaim() != null) { %>
	<tr <%=result.getFile2().getDisStatus() %>>
		<td><input type="checkbox" name="<%=results%>[<%=i%>].selected" /></td>
		<% if (sameCompany) { %>
		<td>
			<a href="claim_resolution.do?claimId=<%=result.getFile2().getClaim().getSwapId() %>">
				<bean:write name="result" property="file2.claim.swapId" />
			</a>
		</td>
		<% } else { %>
		<td><bean:write name="result" property="file2.claim.swapId" /></td>
		<% } %>
		<td>
			<logic:equal name="result" property="file2.claim.claimType" value="0" >
				<bean:message key="match.type.claim" />
			</logic:equal>
			<logic:equal name="result" property="file2.claim.claimType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
				<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
			</logic:equal>
			<logic:equal name="result" property="file2.claim.claimType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
				<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
			</logic:equal>
			<logic:equal name="result" property="file2.claim.claimType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
				<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
			</logic:equal>
		</td>
		<td><bean:write name="result" property="file2.claim.airline" /></td>
		<td><%=result.getFile2().getClaim().getDisClaimDate(a.getDateformat().getFormat()) %></td>
		<td>
			<%=result.getFile2().getDisStatusText() %>
			<bean:write name="result" property="matchSummary" filter="false" />
		</td>
		<td>
			<a href="fraud_results.do?matchId=<%=result.getId() %>">
				<bean:message key="claim.match.details" />
			</a>
			<% if (!sameCompany) { %>
			<br/><br/>
			<b><%=status == null ? "" : status %></b>
			<% } %>
					</td>
				</tr>
			<% } else { %>
				<tr <%=result.getFile2().getDisStatus() %>>
					<td><input type="checkbox" name="<%=results%>[<%=i%>].selected" /></td>
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
		<td>
			<a href="fraud_results.do?matchId=<%=result.getId() %>">
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