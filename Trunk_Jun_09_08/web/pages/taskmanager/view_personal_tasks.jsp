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

<%@page import="com.bagnet.nettracer.tracing.forms.PersonalTasksForm" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  
  request.setAttribute("", null);
%>
  
  <script language="javascript">
    
function goprev() {
  o = document.personalTasksForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.personalTasksForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
	  o = document.personalTasksForm;
	  o.currpage.value = i;
	  o.pagination.value="1";
	  o.submit();
}
function updatePagination() {
    return true;
}


  </script>
  
  <html:form action="personalTasks.do" method="post">

	  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
			<bean:message key='header.agentInboundQueue'/>
        </h1>
      </div>
    </td>
  </tr>
  
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
 
        <div class="headerleft">
        <h1 class="green">
          <bean:message key='tasks'/>
        </h1>
        </div>
        <br/>
        <br/>
        
           <display:table requestURI="/personalTasks.do" name="personalTasksForm.taskList" class="form2" cellspacing="0" cellpadding="0" id="task" >
	       	<display:setProperty name="basic.empty.showtable" value="true"/>
	       	<display:column style="width:25%;" titleKey="colname.incident_num" sortable="true" sortName="incident_id" headerClass="header">
	       		<a href="<c:url value="/searchIncident.do?incident=${task.inboundqueue.incident.incident_ID}"/>">${task.inboundqueue.incident.incident_ID}</a>
	       	</display:column>
	       	<display:column style="width:25%;" titleKey="colname.unassignedinbound.tasktype" value="${task.description}&nbsp;" sortable="true" sortName="type" headerClass="header" />
	       	<display:column style="width:25%;" titleKey="colname.unassignedinbound.origin" value="${task.inboundqueue.activity.description}&nbsp;" sortable="true" sortName="origin" headerClass="header" />
	       	<display:column style="width:25%;" titleKey="colname.unassignedinbound.createtimestamp" value="${task.dispOpened_timestamp}&nbsp;" sortable="true" sortName="opened_timestamp" headerClass="header" />
	       	
	       <display:footer>
			    <tr>
					<td colspan="4">
				       	<jsp:include page="/pages/includes/pagination_incl.jsp" />
					</td>
			    </tr>
			   <logic:equal name="personalTasksForm" property="hasNextTask" value="true">
			    <tr>
					<td colspan="4" align="center">
					<html:submit styleId="button" property="getNext" >
                		<bean:message key="button.personaltask.getnext" />
              		</html:submit>
              		</td>
              	</tr>
				</div>
		</logic:equal>
		 	</display:footer>
	       	</display:table>
  


  </html:form>
