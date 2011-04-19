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
              <dd>
                <a href='claim_resolution.do?claimId=<bean:write name="claimForm" property="claim.id" />' ><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
<%
            } if (ntUser) {
%>
					<dd>
                   	<a href='fraud_results.do?claimId=<bean:write name="claimForm" property="claim.swapId" />' ><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
		            <dd>
		              <a href='claim_prorate.do'><span class="aa">&nbsp;
		                  <br />
		                  &nbsp;</span>
		                <span class="bb"><bean:message key="menu.claim_prorate" /></span>
		                <span class="cc">&nbsp;
		                  <br />
		                  &nbsp;</span></a>
		            </dd>
            <% } else { %>
					<dd>
                   	<a href='fraud_results.do'><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
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
            		<logic:notEmpty name="fraudResultsForm" property="primaryResults" >
	 	           		<logic:iterate id="pResult" name="fraudResultsForm" property="primaryResults" type="aero.nettracer.fs.model.detection.MatchHistory" >
	            			<logic:notEmpty name="pResult" property="claim2" >
		            			<tr>
		            				<td><html:checkbox name="pResult" property="selected" disabled="<%= pResult.getClaim2().getAirline().equals(company) %>" /></td>
		            				<td><bean:write name="pResult" property="claim2.swapId" /></td>
		            				<td>
		            					<logic:equal name="pResult" property="claim2.claimType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="claim2.claimType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="claim2.claimType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="pResult" property="claim2.airline" /></td>
		            				<td><%=pResult.getClaim2().getDisClaimDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="pResult" property="matchSummary" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=pResult.getId() %>">
		            						<bean:message key="claim.match.details" />
		            					</a>
		            				</td>
		            			</tr>
	            			</logic:notEmpty>
	            			<logic:notEmpty name="pResult" property="incident2" >
		            			<tr>
		            				<td><html:checkbox name="pResult" property="selected" disabled="<%= pResult.getIncident2().getAirline().equals(company) %>" /></td>
		            				<td><bean:write name="pResult" property="incident2.swapId" /></td>
		            				<td>
		            					<logic:equal name="pResult" property="incident2.incidentType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="incident2.incidentType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="pResult" property="incident2.incidentType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="pResult" property="incident2.airline" /></td>
		            				<td><%=pResult.getIncident2().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="pResult" property="matchSummary" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=pResult.getId() %>">
		            						<bean:message key="claim.match.details" />
		            					</a>
		            				</td>
		            			</tr>
	            			</logic:notEmpty>
	            		</logic:iterate>
            		</logic:notEmpty>
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
            		<logic:notEmpty name="fraudResultsForm" property="secondaryResults" >
	            		<logic:iterate id="sResult" name="fraudResultsForm" property="secondaryResults" type="aero.nettracer.fs.model.detection.MatchHistory" >
	            			<logic:notEmpty name="sResult" property="claim1" >
		            			<tr>
		            				<td><html:checkbox name="sResult" property="selected" disabled="<%= sResult.getClaim1().getAirline().equals(company) %>" /></td>
		            				<td><bean:write name="sResult" property="claim1.swapId" /></td>
		            				<td>
		            					<logic:equal name="sResult" property="claim1.claimType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="claim1.claimType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="claim1.claimType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.claim" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="sResult" property="claim1.airline" /></td>
		            				<td><%=sResult.getClaim2().getDisClaimDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="sResult" property="matchSummary" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=sResult.getId() %>">
		            						<bean:message key="claim.match.details" />
										</a>
									</td>
		            			</tr>
	            			</logic:notEmpty>
	            			<logic:notEmpty name="sResult" property="incident1" >
		            			<tr>
		            				<td><html:checkbox name="sResult" property="selected" disabled="<%= sResult.getIncident1().getAirline().equals(company) %>" /></td>
		            				<td><bean:write name="sResult" property="incident1.swapId" /></td>
		            				<td>
		            					<logic:equal name="sResult" property="incident1.claimType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.lostdelay" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="incident.claimType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.missing" />
		            					</logic:equal>
		            					<logic:equal name="sResult" property="incident1.claimType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
		            						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.damaged" />
		            					</logic:equal>
	            					</td>
		            				<td><bean:write name="sResult" property="incident1.airline" /></td>
		            				<td><%=sResult.getIncident2().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		            				<td><bean:write name="sResult" property="matchSummary" /></td>
		            				<td>
		            					<a href="fraud_results.do?matchId=<%=sResult.getId() %>">
		            						<bean:message key="claim.match.details" />
										</a>
									</td>
		            			</tr>
	            			</logic:notEmpty>
	            		</logic:iterate>
            		</logic:notEmpty>
            	</table>
            	<br />
            	<center>
					<html:submit property="requestInfo" styleId="button">
                      <bean:message key="button.request_info" />
                    </html:submit>
                </center>        	
                <br />
            </html:form>
