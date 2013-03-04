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
<%@ page import="aero.nettracer.fs.model.detection.AccessRequestDTO" %>
<%@ page import="aero.nettracer.fs.model.detection.AccessRequest" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.ViewFraudRequestForm" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  ViewFraudRequestForm form = (ViewFraudRequestForm)session.getAttribute("viewFraudRequestForm");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
	request.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
	"org.apache.struts.action.LOCALE");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
	var cal1xx = new CalendarPopup();	
  </SCRIPT>
  
  <%@page import="com.bagnet.nettracer.tracing.bmo.RequestOhdBMO"%>
<script language="javascript">
    
function goprev() {
  o = document.viewFraudRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.viewFraudRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewFraudRequestForm;
  o.currpage.value = i;
  o.submit();
}
function updatePagination() {
    return true;
}

  </script>
  <html:form action="fraudRequests.do" method="post">
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
		
			<table class="form2" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<bean:message key="fraud.access.request.type" />:
					<br>
					<html:select name="viewFraudRequestForm" property="dto.type" styleId="value" styleClass="dropdown" >
						<html:option value="<%=String.valueOf(TracingConstants.FS_ACCESS_REQUEST_TYPE_ALL) %>" ><bean:message key="fraud.access.request.type.all" /></html:option>
      					<html:option value="<%=String.valueOf(TracingConstants.FS_ACCESS_REQUEST_TYPE_INCOMING) %>" ><bean:message key="fraud.access.request.type.incoming" /></html:option>
      					<html:option value="<%=String.valueOf(TracingConstants.FS_ACCESS_REQUEST_TYPE_OUTGOING) %>" ><bean:message key="fraud.access.request.type.outgoing" /></html:option>
           			</html:select>
				</td>
                <td nowrap>
                  <bean:message key="colname.date_range" />:
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                    <html:text name="viewFraudRequestForm" property="startDate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.viewFraudRequestForm.startDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
       				<html:text name="viewFraudRequestForm" property="endDate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.viewFraudRequestForm.endDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
					<td>
						<bean:message key="fraud.access.request.status" />:
						<br>						
						<html:checkbox  styleId='pending' name='viewFraudRequestForm' property="dto.pending" /><bean:message key="fraud.access.request.pending" />&nbsp;
						<html:checkbox  styleId='approved' name='viewFraudRequestForm' property="dto.approved" /><bean:message key="fraud.access.request.approved" />&nbsp;
						<html:checkbox  styleId='denied' name='viewFraudRequestForm' property="dto.denied" /><bean:message key="fraud.access.request.denied" />&nbsp;
						
					</td>
				</tr>
				     <tr>
     					<td colspan=3 >
     						<center>
								<html:submit property="filter" styleId="button">
									<bean:message key="button.filter" />
								</html:submit>
							</center>
     					</td>
     				</tr>
			</table>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
           		<tr >
           			<td class="header">
           				<b><bean:message key="fraud.access.request.type" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.reference.id" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.reference.type" /></b>
           			</td>
           			<td class="header">
           				<b><bean:message key="colname.contact.info" /></b>
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
           	<% boolean hasResults = false;%>	
            <logic:present name="requestList" scope="session">
              <logic:iterate id="requested" name="requestList" type="aero.nettracer.fs.model.detection.AccessRequest">
              	<%

              		hasResults=true;
              		boolean outgoing = false;
              		if(requested.getRequestedAirline().equalsIgnoreCase(a.getCompanycode_ID())){
              			outgoing = true;
              		}
              		String disStatus = requested.getFile().getDisStatus();
              		int rowspan = 1;
              		if (requested.getFile().getClaims() != null && !requested.getFile().getClaims().isEmpty()) {
              			FsClaim[] claims = requested.getFile().getClaims().toArray(new FsClaim[0]);
              			int i = 0;
              			rowspan = claims.length;
              	%>
              	

              			<tr <%=disStatus %>>
              				<td>
              					<% if(outgoing){ %>
              						<bean:message key="fraud.access.request.type.outgoing" />
              					<% } else { %>
              						<bean:message key="fraud.access.request.type.incoming" />
              					<% } %>
              				</td>
              				<td>
              					<% if(!outgoing){ %>
		           				<a href='claim_resolution.do?claimId=<%=claims[i].getSwapId() %>&back=1'>
		           					<%=claims[i].getSwapId() %>
		           				</a>
		           				<% } else { %>
		           				<a href='claim_resolution.do?claimId=<%=claims[i].getSwapId() %>&back=1'>
		           					<%=claims[i].getSwapId() %>
	           					</a>
		           				<% } %>
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
		           			<% if(!outgoing){ %>
	            				<bean:message key="colname.fraudresults.company" />:<b><bean:write name="requested" property="requestedAirline" /></b>
	            				<% if(requested.getContactName() != null && requested.getContactName().trim().length() > 0){ %>
	            					<br/><bean:write name="requested" property="contactName" />
	            				<% } if(requested.getContactEmail() != null && requested.getContactEmail().trim().length() > 0){ %>
	            					<br/><bean:write name="requested" property="contactEmail" />
	            				<% } if(requested.getContactPhone() != null && requested.getContactPhone().trim().length() > 0){ %>
	            					<br/><bean:write name="requested" property="contactPhone" />
	            				<% } %>
	            			<% } else { %>
	            				<bean:message key="colname.fraudresults.company" />:<b><bean:write name="requested" property="file.validatingCompanycode" /></b>
	            			<% } %>
	            			</td>
	            			<td rowspan=<%=rowspan %>>
	            				<%=requested.getDisRequestedDate(a.getDateformat().getFormat()) %>
	            			</td>
	            			<td rowspan=<%=rowspan %>>
	            				<div style="width:200px;word-wrap:break-word;">
	            				<% if (requested.getMessage() != null) { %>
	            				<bean:write name="requested" property="message.message" filter="false"/>
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
	           					<% if(outgoing != true && requested.getStatus().equals(AccessRequest.RequestStatus.Created)){ %>
	            				<a href="fraudRequests.do?approveId=<%=requested.getId() %>">
	           						<bean:message key="claim.match.approve" />
	           					</a><br /><br/>
	            				<a href="fraudRequests.do?denyId=<%=requested.getId() %>&claimId=<%=claims[i].getSwapId() %>&airlineRequest=<bean:write name="requested" property="requestedAirline" />">
	           						<bean:message key="claim.match.deny" />
	           					</a>
	           					<% } else { %>
	           						<bean:write name="requested" property="status"/>
	           					<% } %>
	            			</td>
              			</tr>
              		<%
              		if (claims.length > 1) {
              		   for (i = 1; i < claims.length; ++i) { %>
              		   	<tr <%=disStatus %>>
              		   	    <td>
              					<% if(outgoing){ %>
              						<bean:message key="fraud.access.request.type.outgoing" />
              					<% } else { %>
              						<bean:message key="fraud.access.request.type.incoming" />
              					<% } %>
              				</td>
              				<td>
              					<% if(!outgoing){ %>
	           					<a href='claim_resolution.do?claimId=<%=claims[i].getSwapId() %>&back=1'>
		           					<span><%=claims[i].getSwapId() %></span>
		           				</a>
		           				<% } else { %>
		           					<span><%=claims[i].getSwapId() %></span>
		           				<% } %>
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
              					<% if(outgoing){ %>
              						<bean:message key="fraud.access.request.type.outgoing" />
              					<% } else { %>
              						<bean:message key="fraud.access.request.type.incoming" />
              					<% } %>
              				</td>
		        			<td>
		        				<% if(!outgoing){ %>
			    				<a href='searchIncident.do?incident=<bean:write name="requested" property="file.incident.airlineIncidentId" />&back=1' >
			    					<bean:write name="requested" property="file.incident.airlineIncidentId" />
			    				</a>
		           				<% } else { %>
		           					<bean:write name="requested" property="file.incident.airlineIncidentId" />
		           				<% } %>
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
			    			<% if(!outgoing) { %>
			    				<bean:write name="requested" property="requestedAirline" />
			    			<% } else { %>
	            				<bean:write name="requested" property="file.validatingCompanycode" />
	            			<% } %>
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
			   				<% if(outgoing != true && requested.getStatus().equals(AccessRequest.RequestStatus.Created)){ %>
			    				<a href="fraudRequests.do?approveId=<%=requested.getId() %>">
			   						<bean:message key="claim.match.approve" />
			   					</a><br /><br/>
			    				<a href="fraudRequests.do?denyId=<%=requested.getId() %>">
			   						<bean:message key="claim.match.deny" />
			   					</a>
			   				<% } else { %>
	           						<bean:write name="requested" property="status"/>
	           				<% } %>
			    			</td>
		            </tr>
		   		<% } %>
              </logic:iterate>
              <% if(hasResults == false){ %>
				<tr>
					<td colspan="8">
					<bean:message key="fraud.access.request.no.results"/>
				</td>
				</tr>
				<% } %>
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
