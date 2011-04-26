<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  String company = a.getCompanycode_ID();

  boolean ntUser = PropertyBMO.isTrue("nt.user");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  
    <html:form action="fraud_results.do" method="post" >
    
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.fraud_results" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="/pages/includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    
    
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
          <% if (ntUser) { %>
            <dd>
            	<logic:empty name="incident" scope="request" >
              <a href='searchIncident.do' ><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
                  </logic:empty>
            	<logic:notEmpty name="incident" scope="request" >
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
                  </logic:notEmpty>
            </dd>
<%			}
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, a)) {
%>
			  <logic:greaterThan name="claimForm" property="claim.id" value="0" >
              <dd>
                <a href='claim_resolution.do?claimId=<bean:write name="claimForm" property="claim.id" />' ><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
              </logic:greaterThan>
              <logic:equal name="claimForm" property="claim.id" value="0" >
              <dd>
                <a href='claim_resolution.do' ><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
              </logic:equal>
            		<logic:greaterThan name="claimForm" property="claim.id" value="0" >
                   	<dd>
                   	<a href='fraud_results.do?claimId=<bean:write name="claimForm" property="claim.id" />' ><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
                   </logic:greaterThan>
                   <logic:equal name="claimForm" property="claim.id" value="0" >
					<dd>
                   	<a href='fraud_results.do'><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
                   </logic:equal>
<%
            } if (ntUser) {
%>
		            <dd>
		              <a href='claim_prorate.do'><span class="aa">&nbsp;
		                  <br />
		                  &nbsp;</span>
		                <span class="bb"><bean:message key="menu.claim_prorate" /></span>
		                <span class="cc">&nbsp;
		                  <br />
		                  &nbsp;</span></a>
		            </dd>
            <% } %>
            
          </dl>
        </div>
      </td>
    </tr>
    
    <tr>            
    	<td id="middlecolumn">
        	<div id="maincontent">
                <h1>
                	<bean:message key="claim.fraud.primary_results" />
                </h1>
            	<table class="form2" cellspacing="0" cellpadding="0" >
            		<tr>
            			<td>
            				<b><bean:message key="colname.fraudresults.select" /></b>
            			</td>
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
	 	           		<logic:iterate id="pResult" indexId="i" name="fraudResultsForm" property="primaryResults" type="aero.nettracer.fs.model.detection.MatchHistory" >
	            			<% if (pResult.getFile2().getClaim() != null) { %>
		            			<tr>
		            				<% if (pResult.getFile2().getClaim().getAirline().equals(company)) { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" disabled /></td>
		            				<td>
		            					<a href="claim_resolution.do?claimId=<%=pResult.getFile2().getClaim().getSwapId() %>">
		            						<bean:write name="pResult" property="file2.claim.swapId" />
		            					</a>
		            				</td>
		            				<% } else { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" /></td>
		            				<td><bean:write name="pResult" property="file2.claim.id" /></td>
		            				<% } %>
		            				<td>
		            					<logic:match name="pResult" property="file2.claim.claimType" value="0" >
		            						<bean:message key="colname.na" />
		            					</logic:match>
		            					<logic:equal name="pResult" property="file2.claim.claimType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
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
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" disabled /></td>
		            				<td>
		            					<a href="searchIncident.do?incident=<%=pResult.getFile2().getIncident().getAirlineIncidentId() %>">
		            						<bean:write name="pResult" property="file2.incident.airlineIncidentId" />
		            					</a>
		            				</td>
		            				<% } else { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" /></td>
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
            	<br />
            	<br />
            	<h1>
            		<bean:message key="claim.fraud.secondary_results" />
            	</h1>
            	<table class="form2" cellspacing="0" cellpadding="0" >
            		<tr>
            			<td>
            				<b><bean:message key="colname.fraudresults.select" /></b>
            			</td>
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
	            		<logic:iterate id="sResult" indexId="i" name="fraudResultsForm" property="secondaryResults" type="aero.nettracer.fs.model.detection.MatchHistory" >
	            			<% if (sResult.getFile1().getClaim() != null) { %>
		            			<tr>
		            				<% if (sResult.getFile1().getClaim().getAirline().equals(company)) { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" disabled /></td>
		            				<td>
		            					<a href="claim_resolution.do?claimId=<%=sResult.getFile1().getClaim().getSwapId() %>">
		            						<bean:write name="sResult" property="file1.claim.swapId" />
		            					</a>
		            				</td>
		            				<% } else { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" /></td>
		            				<td><bean:write name="sResult" property="file1.claim.id" /></td>
		            				<% } %>
		            				<td>
		            					<logic:match name="sResult" property="file1.claim.claimType" value="0" >
		            						<bean:message key="colname.na" />
		            					</logic:match>
		            					<logic:equal name="sResult" property="file1.claim.claimType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="file1.claim.claimType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="file1.claim.claimType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="sResult" property="file1.claim.airline" /></td>
		            				<td><%=sResult.getFile1().getClaim().getDisClaimDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="sResult" property="matchSummary" filter="false" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=sResult.getId() %>">
		            						<bean:message key="claim.match.details" />
										</a>
									</td>
		            			</tr>
	            			<% } else { %>
		            			<tr>
		            				<% if (sResult.getFile1().getIncident().getAirline().equals(company)) { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" disabled /></td>
		            				<td>
		            					<a href="searchIncident.do?incident=<%=sResult.getFile1().getIncident().getAirlineIncidentId() %>">
		            						<bean:write name="sResult" property="file1.incident.airlineIncidentId" />
		            					</a>
		            				</td>
		            				<% } else { %>
		            				<td><input type="checkbox" name="primaryResults[<%=i%>].selected" /></td>
		            				<td><bean:write name="sResult" property="file1.incident.airlineIncidentId" /></td>
		            				<% } %>
		            				<td>
		            					<logic:match name="sResult" property="file1.incident.incidentType" value="0" >
		            						<bean:message key="colname.na" />
		            					</logic:match>
		            					<logic:equal name="sResult" property="file1.incident.incidentType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="file1.incident.incidentType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="file1.incident.incidentType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="sResult" property="file1.incident.airline" /></td>
		            				<td><%=sResult.getFile1().getIncident().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="sResult" property="matchSummary" filter="false" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=sResult.getId() %>">
		            						<bean:message key="claim.match.details" />
										</a>
									</td>
		            			</tr>
	            			<% } %>
	            		</logic:iterate>
            	</table>
            	<br />
            	<center>
					<html:submit property="requestInfo" styleId="button">
                      <bean:message key="button.request_info" />
                    </html:submit>
                </center>        	
                <br />
            </html:form>
