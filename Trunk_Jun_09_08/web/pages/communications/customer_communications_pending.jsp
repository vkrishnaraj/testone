<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<script type="text/javascript" >
	function goprev() {
	  o = document.customerCommunicationsTaskForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.customerCommunicationsTaskForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.customerCommunicationsTaskForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
</script>
<html:form action="customerCommunicationsApp.do" method="post" >
 	<html:hidden property="command" styleId="command" />
	<html:hidden property="_DATEFORMAT" />
	<jsp:include page="/pages/includes/taskmanager_header.jsp" />
	<tr>
		<td id="middlecolumn">
	    	<div id="maincontent">
 			     <logic:messagesPresent message="true">
					<%
						Object success = request.getAttribute("success");
						String color = (success != null && ((Boolean) success)) ? "green" : "red";						
					%>
					<span>
						<font color="<%=color %>" >
							<html:messages id="msg" message="true">
								<bean:write name="msg"/>
								<br><br>
							</html:messages>
						</font>
					</span>
				</logic:messagesPresent>
		     	<h1 class="green">
		         	<bean:message key="header.customer.communications.pending.approval" />
		       	</h1>
		       	<logic:notPresent name="results" scope="request">
		       		<div style="text-align:center;color:green;" >
		       			<br>
		       			<bean:message key="message.no.customer.communications.pending.approval" />
		       		</div>
		       	</logic:notPresent>
	       		<logic:present name="results" scope="request">
	       			<div style="text-align:center;padding-top:1em;padding-bottom:1em;">
	       				<input type="button" value="Start Working!" onclick='this.disabled;document.location.href="";return true;' id="button">
	       			</div>
	              	<display:table requestURI="/customerCommunicationsApp.do" name="requestScope.results" sort="external" class="form2" cellspacing="0" cellpadding="0" id="<%=TracingConstants.TABLE_ID_CUST_COMM_PENDING_APPROVAL %>" defaultsort="1" >
	          			<display:column titleKey="colname.cust.comm.id" property="id" href="customerCommunicationsApp.do" paramId="communicationsId" paramProperty="id" sortable="true" sortName="id" />
	          			<display:column titleKey="colname.cust.comm.incident.id" property="incidentId" href="searchIncident.do" paramId="incident" paramProperty="incidentId" sortable="true" sortName="incidentId" />
	          			<display:column titleKey="colname.cust.comm.description" property="description" sortable="false" style="width:35%;" />
	          			<display:column titleKey="colname.cust.comm.create.by" property="agent" sortable="true" sortName="agent" />
	          			<display:column titleKey="colname.cust.comm.updated" property="dispTaskDate" sortable="true" sortName="date" />
	 			    	<display:footer>
			               	<tr>
				                <td colspan="5">
				                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
				                </td>
			              	</tr>
		              	</display:footer>
	          		</display:table>
		       	</logic:present>
			</div>
		</td>
	</tr>
</html:form>