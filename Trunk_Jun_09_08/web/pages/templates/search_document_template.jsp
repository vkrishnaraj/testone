<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.forms.templates.DocumentTemplateSearchForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="org.displaytag.tags.TableTagParameters"%>
<%@ page import="org.displaytag.util.ParamEncoder"%>

<%
	Agent a = (Agent)session.getAttribute("user");
	
	long id = ((DocumentTemplateSearchForm) request.getAttribute("documentTemplateSearchForm")).getId();
%>
  
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
   
	var cal1xx = new CalendarPopup();	

	function goprev() {
	  o = document.documentTemplateSearchForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.documentTemplateSearchForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.documentTemplateSearchForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
	function setCommand(command) {
		document.getElementById("command").value = command;
	}

</SCRIPT>
  
  
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="searchDocumentTemplate.do" method="post">
	<html:hidden property="command" />
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
            	<bean:message key="header.search_criteria" />
            	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          	</h1>
            <table class="form2" cellspacing="0" cellpadding="0">
            	<tr>
            		<td>
            			<bean:message key="colname.template.id" />
            			<br>
            			<% if (id == 0) { %>
           					<html:text property="id" value="" styleClass="textfield" />
           				<% } else { %>
           					<html:text property="id" styleClass="textfield" />
           				<% } %>
            		</td>
            		<td nowrap>
		                <bean:message key="colname.date_range" />
		                (<%= a.getDateformat().getFormat() %>)
		                <br>
		                <html:text property="s_createtime" size="10" maxlength="10" styleClass="textfield" />&nbsp;<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.documentTemplateSearchForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
		                &nbsp;-&nbsp;
		                <html:text property="e_createtime" size="10" maxlength="10" styleClass="textfield" />&nbsp;<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.documentTemplateSearchForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
	                </td>
            		<td>
            			<bean:message key="colname.template.active" />
						<br>
						<html:select property="active" styleClass="dropdown" >
							<html:options collection="documentTemplateStatusList" property="status_ID" labelProperty="description" />
						</html:select>
            		</td>
            	</tr>
            	<tr>
            		<td colspan="3">
            			<bean:message key="colname.template.name" />
						<br>
						<html:text property="name" size="60" styleClass="textfield" />
            		</td>
            	</tr>
            	<tr>
            		<td align="center" colspan="3">
            			<input type="submit" class="button" value='<bean:message key="button.search" />' onclick="setCommand('<%=TracingConstants.COMMAND_SEARCH %>')" />
            			&nbsp;&nbsp;
            			<input type="submit" class="button" value='<bean:message key="button.reset" />' onclick="setCommand('<%=TracingConstants.COMMAND_CLEAR %>')" />
            			&nbsp;&nbsp;
            			<input type="submit" class="button" value='<bean:message key="button.create" />' onclick="setCommand('<%=TracingConstants.COMMAND_CREATE %>')" />
              		</td>
            	</tr>
            </table>
            
            <logic:present name="results" scope="request">
            	<h1 class="green">
                	<bean:message key="header.search_result" />
             	</h1>
              	<a name="result"></a>
              	<display:table requestURI="/searchDocumentTemplate.do" name="requestScope.results" sort="external" class="form2" cellspacing="0" cellpadding="0" id="<%=TracingConstants.TABLE_ID_TEMPLATES %>" defaultsort="1" >
          			<display:column titleKey="colname.template.id" property="id" href="documentTemplate.do" paramId="template_id" paramProperty="id" sortable="true" sortName="id" />
          			<display:column titleKey="colname.template.name" property="name" href="documentTemplate.do" paramId="template_id" paramProperty="id" sortable="true" sortName="name" />
          			<display:column titleKey="colname.create.date" property="dispCreateDate" sortable="true" sortName="createDate" />
          			<display:column titleKey="colname.template.active" property="dispActive" sortable="true" sortName="active" />
 			    	<display:footer>
		               	<tr>
			                <td colspan="4">
			                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
			                </td>
		              	</tr>
	              	</display:footer>
          		</display:table>
              	<script language=javascript>
 			 		document.location.href="#result";
              	</script>
           	</logic:present>
       	</div>
    	</td>
 	</tr>
 </html:form>
