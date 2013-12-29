<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="java.util.Locale, org.apache.struts.util.PropertyMessageResources"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="org.apache.commons.lang.math.NumberUtils, org.apache.commons.lang.StringUtils"%>

<%
	String sortBy=StringUtils.stripToEmpty((String)request.getAttribute("sortBy"));	
	Locale myLocale = (Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
%>
<script type="text/javascript">
<!--
function goprev() {
  o = document.deliverForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.sortBy.value="<%=sortBy%>"
  o.submit();
}

function gonext() {
  o = document.deliverForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.sortBy.value="<%=sortBy%>"
  o.submit();
}

function gopage(i) {
  o = document.deliverForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.sortBy.value="<%=sortBy%>"
  o.submit();

}
function updatePagination() {
    return true;
}

function previewDocuments(checkboxName) {
	if (!validateCheckboxes(checkboxName)) {
		return false;
	}
	
	var url = "documentPrintCommunications.do?print=";
	url += "<%=(String) myMessages.getMessage(myLocale, "document.print.preview").replace(" ", "+")%>";

	var checkboxes = document.getElementsByName(checkboxName);
    for (var j=0;j < checkboxes.length; j++) {
      	currentElement = checkboxes[j];
		if (currentElement.checked && 0 < currentElement.value.length) {
			url += "&" + checkboxName + "=" + currentElement.value;
		}
    }
    
    openWindowWithBar(url,'report',800,600); 
	
	return true;
}

function confirmDeleteDocument(docId) {
	if (!isPositiveInteger(docId)) {
		alert('<%=(String) myMessages.getMessage(myLocale, "document.delete.failure")%>');
		return false;
	} else if (!confirm('<%=(String) myMessages.getMessage(myLocale, "message.document.delete") %>')) {
		return false;
	}
	
	jQuery.ajax({
		url: "customerCommunications.do?ajax=y&command=delete&communicationsId=" + docId,
		cache: false,
		success: function(result) {
			alert('<%=(String) myMessages.getMessage(myLocale, "document.delete.success")%>');
			location.reload();
		},
		error: function(result) {
			alert('<%=(String) myMessages.getMessage(myLocale, "document.delete.failure")%>');
		}
	});	 
	
	return true;
}

function validateCheckboxes(checkboxName) {	    
	var checkboxes = document.getElementsByName(checkboxName);
	if (checkboxes.length < 1) {
		alert("<%=(String) myMessages.getMessage(myLocale, "error.document.print.queue")%>");
		return false;
	}
	  
    for (var j=0;j < checkboxes.length; j++) {
      	currentElement = checkboxes[j];
		if (currentElement.checked) {
			 return true;
		}
    }
    
	alert("<%=(String) myMessages.getMessage(myLocale, "error.validation.missing.document")%>");
	currentElement = checkboxes[0]
	if (currentElement) {
		currentElement.focus();
	}

	return false;
}
//-->
</script>

  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
  <html:form action="documentPrintCommunications.do" method="post">
    <tr>
      <td id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
          	<bean:message key="header.document.print.queue"/>
          </h1>
        </div>
        <div id="pageheaderright">
        	&nbsp;
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
				  
       		<c:if test="${not empty printedTaskIds}">
       			<strong>
       				<bean:message key="confirm.print.success" />
       			</strong>
       			<br/>
       			
				<input type="hidden" name="printedTaskIds" value="${printedTaskIds}"/>
				<input type="submit" name="confirm_print" value="<bean:message key="oc.document.yes" />" id="button"/>
				<input type="submit" name="cancel" value="<bean:message key="oc.document.no" />" id="button"/>

				<hr/>
	            
	             <div>
	             	<a href="#" onclick="openWindowWithBar('documentPrintCommunications.do?generateFile=<bean:write name="generateFile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a>
	              </div>
	              <script>
	              		openWindowWithBar('documentPrintCommunications.do?generateFile=<bean:write name="generateFile" scope="request" />','report',800,600);
				</script>
	        </c:if>
		        
			<c:if test="${not empty incidentActivitylist}">
				<display:table id="incidentActivity" name="requestScope.incidentActivitylist" requestURI="/documentPrintCommunications.do" sort="external" 
		          		size="<%=NumberUtils.toInt((String)request.getAttribute("rowcount"))%>" pagesize="<%=NumberUtils.toInt((String)request.getAttribute("rowsperpage"))%>"
		          		class="form2" cellspacing="0" cellpadding="0" partialList="true">
		          		
		          		<display:column titleKey="colname.select" headerClass="header">
		          			<input type="checkbox" name="incident_activity_task_id" value="${incidentActivity.tasks[fn:length(incidentActivity.tasks) - 1].task_id}"/>
		          		</display:column>
		
		          		<display:column titleKey="colname.incident_num" sortable="true" headerClass="header"
										sortName="<%=sortBy.equals(TracingConstants.SortParam.INCIDENT.getParamString())?TracingConstants.SortParam.INCIDENT_REV.getParamString():TracingConstants.SortParam.INCIDENT.getParamString()%>">
		          			<a href="<c:url value="/searchIncident.do?incident=${incidentActivity.incident.incident_ID}"/>">${incidentActivity.incident.incident_ID}</a>
						</display:column>
						
		          		<display:column titleKey="colname.name" sortable="true" value="${incidentActivity.approvalAgent.lastname}, ${incidentActivity.approvalAgent.firstname}" headerClass="header"
										sortName="<%=sortBy.equals(TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_NAME.getParamString())?TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_NAME_REV.getParamString():TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_NAME.getParamString()%>"/>
		
						<display:column titleKey="customer.communication.approving.agent" sortable="true" value="${incidentActivity.approvalAgent.username}&nbsp;" headerClass="header"
										sortName="<%=sortBy.equals(TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_USERNAME.getParamString())?TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_USERNAME_REV.getParamString():TracingConstants.SortParam.INCIDENT_ACTIVITY_APPROVAL_AGENT_USERNAME.getParamString()%>"/>
		          		
						<display:column titleKey="colname.incident.activity.document.id" sortable="true" value="${incidentActivity.document.id}&nbsp;" headerClass="header"
										sortName="<%=sortBy.equals(TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_ID.getParamString())?TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_ID_REV.getParamString():TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_ID.getParamString()%>"/>
		
						<display:column titleKey="colname.incident.activity.document.title" sortable="true" value="${incidentActivity.document.title}&nbsp;" headerClass="header"
										sortName="<%=sortBy.equals(TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_TITLE.getParamString())?TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_TITLE_REV.getParamString():TracingConstants.SortParam.INCIDENT_ACTIVITY_DOCUMENT_TITLE.getParamString()%>"/>
		
						<display:column titleKey="header.action" headerClass="header">
		          			<a onclick="confirmDeleteDocument(${incidentActivity.id});"><bean:message key="delete"/></a>
			          	</display:column>
		            </display:table>
		            
					<table class="form2" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<input type="button" name="checkAllButton" id="button" value="<bean:message key="css.select.all" />" onclick="checkAllCheckboxes('incident_activity_task_id');"/>
								
								&nbsp;&nbsp;								
								<input type="button" name="print" value="<bean:message key="document.print.preview" />" id="button" onclick="return previewDocuments('incident_activity_task_id');">
								
								&nbsp;&nbsp;								
								<input type="submit" name="print" value="<bean:message key="document.print.generate" />" id="button" onclick="return validateCheckboxes('incident_activity_task_id');">
							</td>
					    </tr>
				    </table>		
  			</c:if>
  		</div> 
  		</td>
  		</tr>
</html:form>