<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.CompanyBMO" %>
<%@ page import="aero.nettracer.fs.model.FsClaim" %>
<%
  Agent a = (Agent)session.getAttribute("user");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
	request.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
	"org.apache.struts.action.LOCALE");

%>
  
  <%@page import="com.bagnet.nettracer.tracing.bmo.RequestOhdBMO"%>
<script language="javascript">
    
function goprev() {
  o = document.viewIncomingRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.viewIncomingRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewIncomingRequestForm;
  o.currpage.value = i;
  o.submit();
}
function updatePagination() {
    return true;
}


  function batchReceive()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.receive") %>?"))
    {  
      var checked = 0;
      var received="";
    
      for (var j=0;j<document.viewIncomingRequestForm.length;j++) 
      {
        currentElement = document.viewIncomingRequestForm.elements[j];
        if (currentElement.type=="checkbox")
        {
          if (currentElement.checked)
          {
            if (checked > 0) 
              received += ",";
            checked +=1;
            received +=currentElement.value;
          }
        }
      }

      if (checked < 1)
      {
        alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingBags") %>");
      }
      else
      {
        document.viewIncomingRequestForm.close1.value="1";
        document.viewIncomingRequestForm.ohd_ID.value=received;
        document.viewIncomingRequestForm.submit();
      }
    }
  } 


  </script>
  <html:form action="incomingBags.do" method="post" onsubmit="fillzero(this.ohd_num, 13); return true;">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.fraud_requests" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/incoming_bags.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>

          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
           		<tr >
           			<td class="header">
           				<b><bean:message key="colname.reference.id" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.reference.type" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.fraudresults.company" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.fraudresults.claim_date" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.fraudresults.message" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.fraudresults.match_summary" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.fraudresults.action" /></b>
           			</td>
           		</tr>
            <logic:present name="requestList" scope="request">
              <logic:iterate id="requested" name="requestList" type="aero.nettracer.fs.model.detection.AccessRequest">
              	<%
              		String disStatus = requested.getFile().getDisStatus();
              		int rowspan = 1;
              		if (requested.getFile().getClaims() != null && !requested.getFile().getClaims().isEmpty()) {
              			FsClaim[] claims = requested.getFile().getClaims().toArray(new FsClaim[0]);
              			int i = 0;
              			rowspan = claims.length;
              	%>
              			<tr <%=disStatus %>>
              				<td>
		           				<a href='claim_resolution.do?claimId=<%=claims[i].getSwapId() %>&back=1'>
		           					<%=claims[i].getSwapId() %>
		           				</a>
		           			</td>
		           			<td>
		           				<%
									switch (claims[i].getClaimType()) {
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
		           			<td rowspan=<%=rowspan %>>
	            				<bean:write name="requested" property="requestedAirline" />
	            			</td>
	            			<td rowspan=<%=rowspan %>>
	            				<%=requested.getDisRequestedDate(a.getDateformat().getFormat()) %>
	            			</td>
	            			<td rowspan=<%=rowspan %>>
	            				<div style="width:200px;word-wrap:break-word;">
	            				<% if (requested.getMessage() != null) { %>
	            				<bean:write name="requested" property="message.message" />
	            				<% } %>
	            				</div>
	            			</td>
	            			<td rowspan=<%=rowspan %>>
	            				<%=requested.getFile().getDisStatusText() %>
	            				<bean:write name="requested" property="matchHistory.matchSummary" filter="false" />
	            			</td>
	            			<td rowspan=<%=rowspan %>>
	            				<a href="fraudRequests.do?matchId=<%=requested.getMatchHistory().getId() %>">
	           						<bean:message key="claim.match.details" />
	           					</a><br /><br/>
	            				<a href="fraudRequests.do?approveId=<%=requested.getId() %>">
	           						<bean:message key="claim.match.approve" />
	           					</a><br /><br/>
	            				<a href="fraudRequests.do?denyId=<%=requested.getId() %>">
	           						<bean:message key="claim.match.deny" />
	           					</a>
	            			</td>
              			</tr>
              		<%
              		if (claims.length > 1) {
              		   for (i = 1; i < claims.length; ++i) { %>
              		   	<tr <%=disStatus %>>
              				<td>
		           				<a href='claim_resolution.do?claimId=<%=claims[i].getSwapId() %>&back=1'>
		           					<bean:write name="requested" property="file.claim.swapId" />
		           				</a>
		           			</td>
		           			<td>
		           				<%
									switch (claims[i].getClaimType()) {
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
	           			</tr>              			
          			<% }
              		}
              		} else { %>
	            		<tr <%=disStatus %>>
		        			<td>
			    				<a href='searchIncident.do?incident=<bean:write name="requested" property="file.incident.airlineIncidentId" />&back=1' >
			    					<bean:write name="requested" property="file.incident.airlineIncidentId" />
			    				</a>
			    			</td>
			    			<td>
							        <logic:equal name="requested" property="file.incident.incidentType" value="0" >
			   						<bean:message key="match.type.incident" />
			   					</logic:equal>
			   					<logic:equal name="requested" property="file.incident.incidentType" value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>" >
			   						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.lostdelay" />
			   					</logic:equal>
			   					<logic:equal name="requested" property="file.incident.incidentType" value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>" >
			   						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.missing" />
			   					</logic:equal>
			   					<logic:equal name="requested" property="file.incident.incidentType" value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>" >
			   						<bean:message key="match.type.incident" />:&nbsp;<bean:message key="claim.type.damaged" />
			   					</logic:equal>
			    			</td>
			    			<td>
			    				<bean:write name="requested" property="requestedAirline" />
			    			</td>
			    			<td>
			    				<%=requested.getDisRequestedDate(a.getDateformat().getFormat()) %>
			    			</td>
			    			<td>
			    				<div style="width:200px;word-wrap:break-word;">
			    				<% if (requested.getMessage() != null) { %>
			    				<bean:write name="requested" property="message.message" />
			    				<% } %>
			    				</div>
			    			</td>
			    			<td>
			    				<%=requested.getFile().getDisStatusText() %>
			    				<bean:write name="requested" property="matchHistory.matchSummary" filter="false" />
			    			</td>
			    			<td>
			    				<a href="fraudRequests.do?matchId=<%=requested.getMatchHistory().getId() %>">
			   						<bean:message key="claim.match.details" />
			   					</a><br /><br/>
			    				<a href="fraudRequests.do?approveId=<%=requested.getId() %>">
			   						<bean:message key="claim.match.approve" />
			   					</a><br /><br/>
			    				<a href="fraudRequests.do?denyId=<%=requested.getId() %>">
			   						<bean:message key="claim.match.deny" />
			   					</a>
			    			</td>
		            </tr>
		   		<% } %>
              </logic:iterate>
              
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  
                </td>
              </tr>
              
            </logic:present>
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>
          </table>
          
        </html:form>
