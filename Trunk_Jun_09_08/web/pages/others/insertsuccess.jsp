<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.hibernate.HibernateWrapper"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants" %>
<%
	int reportType = -1;
	String receiptType = "";
	  Agent a = (Agent)session.getAttribute("user");
	  String company = a.getCompanycode_ID();

%>

<tr>
  
  <td id="middlecolumn">
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
	    <h1 class="green">
            <logic:present name="lostdelay" scope="request">
              <bean:message key="message.lostdelay.success" />
              <p>
                <bean:message key="colname.incident_num" />
                :
                <a href='lostDelay.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                <%
                	reportType = ReportingConstants.LOST_RECEIPT_RPT;
                	receiptType = "LostReceipt";
                %>
              </logic:present>
              <logic:present name="missingarticles" scope="request">
                <bean:message key="message.missingarticles.success" />
                <p>
                  <bean:message key="colname.incident_num" />
                  :
                  <a href='missing.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                <%
                	reportType = ReportingConstants.MISSING_RECEPIT_RPT;
                	receiptType = "MissingRecepit";
               	%>
                </logic:present>
                <logic:present name="damaged" scope="request">
                  <bean:message key="message.damaged.success" />
                  <p>
                    <bean:message key="colname.incident_num" />
                    :
                    <a href='damaged.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                <%
                	reportType = ReportingConstants.DAMAGE_RECEPIT_RPT;
                	receiptType = "DamagedReceipt";
               	%>
                  </logic:present>
                </h1></td>
              </tr>
              <tr>
                <td id="printreceipt">&nbsp;<br>
	                <a href='#'
		              	onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%= reportType %>&incident=<bean:write name="Incident_ID" scope="request" />&new=1','<%= receiptType %>',800,600);return false;">
		              		<img src="deployment/main/images/nettracer/icon_printrecpt.gif" width="12" height="12">
		            </a>
           			<a href='#'
          				onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%= reportType %>&incident=<bean:write name="Incident_ID" scope="request" />&new=1','<%= receiptType %>',800,600);return false;">
          				Print Receipt
          			</a>&nbsp;
      			</td>
              </tr>
              <% if (reportType==ReportingConstants.DAMAGE_RECEPIT_RPT) {
              	
              %>
        		<div id="maincontent">
                <h1>
                	<bean:message key="claim.fraud.primary_results" />
                </h1>
            	<table class="form2" cellspacing="0" cellpadding="0" >
            		<tr>
            			<td>
            				<b><bean:message key="colname.reference.id" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.reference.type" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.company" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.claim_date" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.match_summary" /></b>
            			</td>
            			<td>
            				<b><bean:message key="colname.fraudresults.details" /></b>
            			</td>
            		</tr>
	 	           		<logic:iterate id="pResult" name="fraudResults" type="aero.nettracer.fs.model.detection.MatchHistory" scope="session">
	            			<% if (pResult.getFile2().getClaim() != null) { %>
		            			<tr>
		            				<% if (pResult.getFile2().getClaim().getAirline().equals(company)) { %>
		            				<td>
		            					<a href="claim_resolution.do?claimId=<%=pResult.getFile2().getClaim().getSwapId() %>">
		            						<bean:write name="pResult" property="file2.claim.swapId" />
		            					</a>
		            				</td>
		            				<% } else { %>
		            				<td><bean:write name="pResult" property="file2.claim.id" /></td>
		            				<% } %>
		            				<td>
		            					<logic:match name="pResult" property="file2.claim.claimType" value="0" >
		            						<bean:message key="colname.na" />
		            					</logic:match>
		            					<logic:equal name="pResult" property="file2.claim.claimType" value="<%= String.valueOf(com.bagnet.nettracer.tracing.constant.TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="file2.claim.claimType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="file2.claim.claimType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="pResult" property="file2.claim.airline" /></td>
		            				<td><%=pResult.getFile2().getClaim().getDisClaimDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="pResult" property="matchSummary" filter="false" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=pResult.getId() %>">
		            						<bean:message key="claim.match.details" />
		            					</a>
		            				</td>
		            			</tr>
	            			<% } else { %>
		            			<tr>
		            				<% if (pResult.getFile2().getIncident().getAirline().equals(company)) { %>
		            				<td><html:checkbox name="pResult" property="selected" disabled="true" /></td>
		            				<td>
		            					<a href="searchIncident.do?incident=<%=pResult.getFile2().getIncident().getAirlineIncidentId() %>">
		            						<bean:write name="pResult" property="file2.incident.airlineIncidentId" />
		            					</a>
		            				</td>
		            				<% } else { %>
		            				<td><html:checkbox name="pResult" property="selected" /></td>
		            				<td><bean:write name="pResult" property="file2.incident.airlineIncidentId" /></td>
		            				<% } %>
		            				<td>
		            					<logic:match name="pResult" property="file2.incident.incidentType" value="0" >
		            						<bean:message key="colname.na" />
		            					</logic:match>
		            					<logic:equal name="pResult" property="file2.incident.incidentType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="file2.incident.incidentType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="file2.incident.incidentType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="pResult" property="file2.incident.airline" /></td>
		            				<td><%=pResult.getFile2().getIncident().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="pResult" property="matchSummary" filter="false" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=pResult.getId() %>">
		            						<bean:message key="claim.match.details" />
		            					</a>
		            				</td>
		            			</tr>
	            			<% } %>
	            		</logic:iterate>
            	</table>
	          </div>
              <% } %>
              
              
            </table>
