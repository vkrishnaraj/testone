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
  
  String claimId = request.getParameter("claim");
  String incidentId = request.getParameter("incident");
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  
    <html:form action="request_info.do" method="post" >
    
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
               <bean:message key="header.request.information" />
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
    	<td id="middlecolumn">
        	<div id="maincontent">
                <h1>
                <%if(incidentId != null){ %>
            		<bean:message key="header.incident_payout" />
            	<%}else{ %>
                    <bean:message key="header.claim_payout" />
            	<%} %>
                	
                </h1>
            	<table class="form2" cellspacing="0" cellpadding="0" >
            		<tr>
            			<td><b><bean:message key="colname.reference.id" /></b></td>
            			<td><b><bean:message key="colname.airline" /></b></td>
            			<%if(incidentId != null){ %>
            			<td><b><bean:message key="colname.incident.date" /></b></td>
            			<%}else{ %>
            			<td><b><bean:message key="colname.claim.date" /></b></td>
            			<%} %>
            		</tr>
            		<logic:iterate id="match" name="requestInfoForm" property="requestedMatches" type="aero.nettracer.fs.model.detection.MatchHistory" >
	            		<tr>
	            			<% if (match.getFile2().getClaim() != null) { %>
		            			<td><bean:write name="match" property="file2.claim.swapId" /></td>
		            			<td><bean:write name="match" property="file2.claim.airline" /></td>
		            			<td><%=match.getFile2().getClaim().getDisClaimDate(a.getDateformat().getFormat()) %></td>
		            		<% } else { %>
		            			<td><bean:write name="match" property="file2.incident.airlineIncidentId" /></td>
		            			<td><bean:write name="match" property="file2.incident.airline" /></td>
		            			<td><%=match.getFile2().getIncident().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		            		<% } %>
	            		</tr>
            		</logic:iterate>
            		<tr><td colspan="3">&nbsp;</td></tr>
	            	<tr><td colspan="3"><bean:message key="colname.message" /></td></tr>
	            	<tr>
	            		<td colspan="3">
	            			<html:textarea property="message" cols="70" rows="10" />
	            		</td>
            		</tr>
            	</table>
            	<br />
            	<center>
            	<%if(request.getParameter("incident")!=null){ %>
            	<input type="hidden" id="incident" name="incident" value="<%=request.getParameter("incident") %>" />
            	<%} %>
					<html:submit property="send" styleId="button">
                      <bean:message key="send_message" />
                    </html:submit>
                    &nbsp;&nbsp;
                    <input id="button" type="button" value='<bean:message key="claim.button.back" />' onClick="history.back()">
                </center>        	
                <br />
            </html:form>
