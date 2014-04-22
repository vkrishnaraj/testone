<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.taskmanager.inbound.UnassignedInboundAgentElement" %>

<%@page import="com.bagnet.nettracer.tracing.forms.UnassignedInboundQueueForm" %>

<%
  Agent a = (Agent)session.getAttribute("user");
  int day = 0;
  
  request.setAttribute("", null);
%>
  
  <script type="text/javascript" SRC="deployment/main/js/date.js"></script>
  <script type="text/javascript" SRC="deployment/main/js/AnchorPosition.js"></script>
  <script type="text/javascript" SRC="deployment/main/js/PopupWindow.js"></script>
  <script type="text/javascript" SRC="deployment/main/js/popcalendar.js"></script>

    <script>
	function updateAssignment(id) {
		var assignAgent = document.getElementById('agentAssigned_'+id).value;
		jQuery.ajax({
 			url:"unassignedInboundQueue.do?assignAgent=" + assignAgent + "&assignIncident=" + id,
			cache: false,
			success: function(result) {
				return true;
			}
		});	
	}
   
    </script>
  
  <html:form action="unassignedInboundQueue.do" method="post">
	  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
			<bean:message key='header.unassignedinbound.title'/>
        </h1>
      </div>
    </td>
  </tr>
  
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
			<logic:messagesPresent message="true">
				<div>
					<html:messages id="msg" message="true">
						<font color="red"><bean:write name="msg"/><br/></font>
					</html:messages>
				</div>
			</logic:messagesPresent>
 
	        <div class="headerleft">
		        <h1 class="green">
		          <bean:message key='header.unassignedinbound.matrix.title'/>
		        </h1>
	        </div>
        
          <display:table requestURI="/unassignedInboundQueue.do" name="unassignedInboundQueueForm.agentMatrix" class="form2" cellspacing="0" cellpadding="0" id="agentElement" excludedParams="*" >
	       	<display:setProperty name="basic.empty.showtable" value="true"/>
	       	<display:column style="width:30%;" titleKey="agent" value="${agentElement.agent.username}" sortable="false" headerClass="header" />
	       	<display:column style="width:25%;" titleKey="colname.unassignedinbound.load" headerClass="header">
	       		<input type="text" name="agentMatrix[${agentElement_rowNum - 1}].load" value="${agentElement.agent.loadpercentage}" size="1" class="textfield" />
	       	</display:column>
	       	<display:column style="width:15%;" titleKey="colname.unassignedinbound.inbound" headerClass="header">
		         <%String inboundId =  "agentMatrix[" + (agentElement_rowNum - 1) + "].inbound"; %>
		         <input type="checkbox" name="agentMatrix[${agentElement_rowNum - 1}].inbound" property="<%=inboundId %>" <% if(((UnassignedInboundAgentElement)agentElement).getAgent().getInbound()){ %> checked="true" <% } %> />
		    </display:column>
		    <display:column style="width:15%;" titleKey="colname.unassignedinbound.acaa" headerClass="header">
		         <%String acaaId =  "agentMatrix[" + (agentElement_rowNum - 1) + "].acaa"; %>
		         <input type="checkbox" name="agentMatrix[${agentElement_rowNum - 1}].acaa" property="<%=acaaId %>" <% if(((UnassignedInboundAgentElement)agentElement).getAgent().getAcaa()){ %> checked="true" <% } %> />
		    </display:column>
		    <display:column style="width:15%;" titleKey="colname.unassignedinbound.damaged" headerClass="header">
		         <%String damagedId =  "agentMatrix[" + (agentElement_rowNum - 1) + "].damaged"; %>
		         <input type="checkbox" name="agentMatrix[${agentElement_rowNum - 1}].damaged" property="<%=damagedId %>" <% if(((UnassignedInboundAgentElement)agentElement).getAgent().getDamaged()){ %> checked="true" <% } %> />
		    </display:column>
	       	
	       <display:footer>
			    <tr>
            		<td colspan="5" align="center">
              			<html:submit styleId="button" property="autoassign" >
                			<bean:message key="button.unassignedinbound.autoassign" />
              			</html:submit>
              			<html:submit styleId="button" property="reset" >
                			<bean:message key="button.unassignedinbound.reset" />
              			</html:submit>
            		</td>
          		</tr>
		 	</display:footer>
	       	</display:table>

		
		<br/>
        <div class="headerleft">
        <h1 class="green">
          <bean:message key='header.unassignedinbound.tasklist.title'/>
        </h1>
        </div>
  		<br/>
           <display:table requestURI="/unassignedInboundQueue.do" name="unassignedInboundQueueForm.incidentList" class="form2" cellspacing="0" cellpadding="0" id="incident" sort="external"  excludedParams="*" >
	       	<display:setProperty name="basic.empty.showtable" value="true"/>
	       	
	       	<display:column style="width:25%;" titleKey="agent" sortable="true" sortName="username" headerClass="header">
	       		<select id="agentAssigned_${incident_rowNum -1}" name="incident.incident.agentassigned.agent_ID" class="dropdown" onchange="updateAssignment(${incident_rowNum -1});" >
                	<option value="-1">Select</option>
			       		<c:forEach var="agentElement" items="${unassignedInboundQueueForm.agentMatrix}">
			       			<c:if test="${not empty agentElement.agent and not empty agentElement.agent.username}">
			       				<option value="${agentElement.agent.agent_ID}"
					       			<c:if test="${not empty incident.incident.agentassigned and not empty incident.incident.agentassigned.username and incident.incident.agentassigned.username.equalsIgnoreCase(agentElement.agent.username)}">
					       				selected
					       			</c:if>
							    	>${agentElement.agent.username}
						    	</option>
			       			</c:if>
						</c:forEach>
				</select>
	       	</display:column>
	       	
	       	<display:column style="width:25%;" titleKey="colname.incident_num" headerClass="header" sortable="true" sortName="incidentid">
	       		<a href="<c:url value="/searchIncident.do?incident=${incident.incident.incident_ID}"/>">${incident.incident.incident_ID}</a>
	       	</display:column>
	       	<display:column style="width:25%;" titleKey="colname.unassignedinbound.tasktype" value="${incident.dispTaskList}&nbsp;" sortable="true" sortName="type" headerClass="header" />
	       	<display:column style="width:25%;" titleKey="colname.unassignedinbound.createtimestamp" value="${incident.oldestTask.dispOpened_timestamp}&nbsp;" sortable="true" sortName="date" headerClass="header" />
	       	
	       <display:footer>
			    <tr>
            		<td colspan="4" align="center">
              			<html:submit styleId="button" property="commit" >
                			<bean:message key="button.unassignedinbound.commit" />
              			</html:submit>
            		</td>
          		</tr>
		 	</display:footer>
	       	</display:table>
  		</div>
  	</td>
  </tr>
  </html:form>
