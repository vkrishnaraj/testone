<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ page import="com.bagnet.nettracer.tracing.forms.communications.TaskSearchForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="org.displaytag.tags.TableTagParameters"%>
<%@ page import="org.displaytag.util.ParamEncoder"%>

<%
	Agent a = (Agent)session.getAttribute("user");
%>
  
<SCRIPT type="text/javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT type="text/javascript" >
   
	var cal1xx = new CalendarPopup();	

	function goprev() {
	  o = document.taskSearchForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.taskSearchForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.taskSearchForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
	function setCommand(command) {
		document.getElementById("command").value = command;
		document.taskSearchForm.submit();
	}

</SCRIPT>
  
  
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="viewPendingTasks.do" method="post">
	<html:hidden property="command" styleId="command" />
	<html:hidden property="_DATEFORMAT" />
    <tr>
      	<td colspan="3" id="pageheadercell">
        	<div id="pageheaderleft">
           		<h1>
              		<bean:message key="view.pending.tasks" />
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
            	<bean:message key="header.search_criteria" />
            	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          	</h1>
            <table class="form2" cellspacing="0" cellpadding="0">
            	<tr>
            		<td>
	            		<bean:message key="tasks.not.in.work.agent.name" />
	            		<br>
	            		<html:text name="taskSearchForm" property="agentName" styleId="agentName" size="20" maxlength="20" styleClass="textfield" /> 
            		</td>
            		<td>
						<bean:message key="colname.date_range" />
		                (<%= a.getDateformat().getFormat() %>)
		                <br>
		                <html:text property="s_createtime" size="10" maxlength="10" styleId="s_createtime" styleClass="textfield" />&nbsp;<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.taskSearchForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
		                &nbsp;-&nbsp;
		                <html:text property="e_createtime" size="10" maxlength="10" styleId="e_createtime" styleClass="textfield" />&nbsp;<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.taskSearchForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
            		</td>
					<td colspan=2>
            			<bean:message key="tasks.not.in.work.task.type" />
            			<br>
						<html:select name="taskSearchForm" property="taskType" styleId="taskType" styleClass="dropdown" >
	          	        	<html:option value="">
                      			<bean:message key="select.all" />
                    		</html:option>
							<html:options collection="taskTypesList" property="value" labelProperty="label" />
						</html:select>
            		</td>
            	</tr>
            	<tr>
            		<td>
	            		<bean:message key="tasks.not.in.work.passenger.last.name" />
	            		<br>
	            		<html:text name="taskSearchForm" property="passengerLastName" styleId="passengerLastName" size="20" maxlength="20" styleClass="textfield" />
            		</td>
            		<td>
	            		<bean:message key="tasks.not.in.work.passenger.first.name" />
	            		<br>
	            		<html:text name="taskSearchForm" property="passengerFirstName" styleId="passengerFirstName" size="20" maxlength="20" styleClass="textfield" />
            		</td>
           			<td>
            			<bean:message key="tasks.not.in.work.status" />
            			<br>
						<html:select name="taskSearchForm" property="status" styleId="status" styleClass="dropdown" >
	          	        	<html:option value="">
                      			<bean:message key="select.all" />
                    		</html:option>
							<html:options collection="incidentActivityStatusList" property="value" labelProperty="label" />
						</html:select>
            		</td>
            		<td>
            			<bean:message key="tasks.not.in.work.acaa" />
            			<br>
     					<html:select name="taskSearchForm" property="acaa" styleId="status" styleClass="dropdown" >
	          	        	<html:option value="-1"><bean:message key="select.all" /></html:option>
	          	        	<html:option value="<%=String.valueOf(TracingConstants.NO) %>"><bean:message key="select.no" /></html:option>
	          	        	<html:option value="<%=String.valueOf(TracingConstants.YES) %>"><bean:message key="select.yes" /></html:option>
						</html:select>
            		</td>
            	</tr>
            	<tr>
            		<td align="center" colspan="4">
            			<input id="searchButton" type="button" class="button" value='<bean:message key="button.search" />' onclick="setCommand('<%=TracingConstants.COMMAND_SEARCH %>')" />
      			        &nbsp;&nbsp;
            			<html:reset styleId="resetButton" styleClass="button">
            				<bean:message key="button.reset" />
            			</html:reset>
              		</td>
            	</tr>
            </table>
            
            <logic:present name="results" scope="request">
            	<h1 class="green">
                	<bean:message key="header.search_result" />
             	</h1>
              	<a id="result"></a>
              	<display:table requestURI="/viewPendingTasks.do" name="requestScope.results" sort="external" class="form2" cellspacing="0" cellpadding="0" id="<%=TracingConstants.TABLE_ID_TASKS_NOT_IN_WORK %>" defaultsort="1" >
          			<display:column titleKey="colname.create_date_time" property="dispTaskDate" sortable="true" sortName="openDate" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.agent.name" property="agent" sortable="true" sortName="incidentAgent" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.task.type" property="taskType" sortable="true" sortName="taskType" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.status" property="status" sortable="true" sortName="statusDesc" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.incident.id" property="incidentId" href="searchIncident.do" paramId="incident" paramProperty="incidentId" sortable="true" sortName="incidentId" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.passenger.last.name" property="lastName" sortable="true" sortName="passengerLastName" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.passenger.first.name" property="firstName" sortable="true" sortName="passengerFirstName" headerClass="header" />
          			<display:column titleKey="tasks.not.in.work.acaa" property="acaa" sortable="true" sortName="acaa" headerClass="header" />
 			    	<display:footer>
		               	<tr>
			                <td colspan="8">
			                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
			                </td>
		              	</tr>
	              	</display:footer>
          		</display:table>
              	<script type="text/javascript" >
 			 		document.location.href="#result";
              	</script>
           	</logic:present>
       	</div>
    	</td>
 	</tr>
 </html:form>
