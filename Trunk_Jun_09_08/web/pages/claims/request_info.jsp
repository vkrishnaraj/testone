<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="aero.nettracer.fs.model.FsClaim" %>
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
            			<td colspan="2"><b><bean:message key="colname.reference.id" /></b></td>
            			<td colspan="2"><b><bean:message key="colname.airline" /></b></td>
            			<%if(incidentId != null){ %>
            			<td colspan="2"><b><bean:message key="colname.incident.date" /></b></td>
            			<%}else{ %>
            			<td colspan="2"><b><bean:message key="colname.claim.date" /></b></td>
            			<%} %>
            		</tr>
            		<logic:iterate id="match" name="requestInfoForm" property="requestedMatches" type="aero.nettracer.fs.model.detection.MatchHistory" >
	            		<tr>
	            			<% 
	            			   if (match.getFile2().getClaims() != null && !match.getFile2().getClaims().isEmpty()) { 
	            			   		FsClaim[] claims = match.getFile2().getClaims().toArray(new FsClaim[0]);
	            			   		for (int i = 0; i < claims.length; ++i) { %>
				            			<td colspan="2"><%=claims[i].getSwapId() %></td>
				            			<td colspan="2"><%=claims[i].getAirline() %></td>
				            			<td colspan="2"><%=claims[i].getDisClaimDate(a.getDateformat().getFormat()) %></td>
		            		<%		}
		            		   } else { %>
		            			<td colspan="2"><bean:write name="match" property="file2.incident.airlineIncidentId" /></td>
		            			<td colspan="2"><bean:write name="match" property="file2.incident.airline" /></td>
		            			<td colspan="2"><%=match.getFile2().getIncident().getDisOpenDate(a.getDateformat().getFormat()) %></td>
		            		<% } %>
	            		</tr>
            		</logic:iterate>
            		<tr>
            			<td colspan="2"><b><bean:message key="colname.requestagent" /></b></td>
            			<td colspan="2"><b><bean:message key="colname.contact.email" /></b></td>
            			<td colspan="2"><b><bean:message key="colname.contact.phone" /></b></td>
            		</tr>
            		<tr>
            			<td colspan="2"><html:text property="contactName" maxlength="250"/></td>
            			<td colspan="2"><html:text property="contactEmail" maxlength="250"/></td>
            			<td colspan="2"><html:text property="contactPhone" maxlength="250"/></td>
            		</tr>
            		<tr><td colspan="6">&nbsp;</td></tr>
	            	<tr><td colspan="6"><bean:message key="colname.message" /></td></tr>
	            	<tr>
	            		<td colspan="6">
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
