<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.forms.templates.TemplateSearchForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="org.displaytag.tags.TableTagParameters"%>
<%@ page import="org.displaytag.util.ParamEncoder"%>

<%
	Agent a = (Agent)session.getAttribute("user");
	
	long id = 0;
	if (request.getAttribute("templateSearchForm") != null) {
		((TemplateSearchForm) request.getAttribute("templateSearchForm")).getId();
	}
%>
  
<SCRIPT type="text/javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT type="text/javascript" >
   
	var cal1xx = new CalendarPopup();	

	function goprev() {
	  o = document.templateSearchForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.templateSearchForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.templateSearchForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
	function setCommand(command) {
		document.getElementById("command").value = command;
		document.templateSearchForm.submit();
	}

</SCRIPT>
  
  
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="searchTemplate.do" method="post">
	<html:hidden property="command" styleId="command" />
	<html:hidden property="_DATEFORMAT" />
    <tr>
      	<td colspan="3" id="pageheadercell">
        	<div id="pageheaderleft">
           		<h1>
              		<bean:message key="search.document.template" />
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
            			<bean:message key="colname.template.id" />
            			<br>
            			<% if (id == 0) { %>
           					<html:text property="id" value="" styleId="id" styleClass="textfield" />
           				<% } else { %>
           					<html:text property="id" styleId="id" styleClass="textfield" />
           				<% } %>
            		</td>
            		<td nowrap>
		                <bean:message key="colname.date_range" />
		                (<%= a.getDateformat().getFormat() %>)
		                <br>
		                <html:text property="s_createtime" size="10" maxlength="10" styleId="s_createtime" styleClass="textfield" />&nbsp;<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.documentTemplateSearchForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
		                &nbsp;-&nbsp;
		                <html:text property="e_createtime" size="10" maxlength="10" styleId="e_createtime" styleClass="textfield" />&nbsp;<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.documentTemplateSearchForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
	                </td>
            		<td>
            			<bean:message key="colname.template.active" />
						<br>
						<html:select property="active" styleId="active" styleClass="dropdown" >
							<html:options collection="templateStatusList" property="status_ID" labelProperty="description" />
						</html:select>
            		</td>
            	</tr>
            	<tr>
            		<td>
            			<bean:message key="colname.template.name" />
						<br>
						<html:text property="name" styleId="name" styleClass="textfield" />
            		</td>
            		<td colspan="2">
            			<bean:message key="colname.template.description" />
						<br>
						<html:text property="description" size="60" styleId="description" styleClass="textfield" />
            		</td>
            	</tr>
            	<tr>
            		<td align="center" colspan="3">
            			<input id="searchButton" type="button" class="button" value='<bean:message key="button.search" />' onclick="setCommand('<%=TracingConstants.COMMAND_SEARCH %>')" />
            			&nbsp;&nbsp;
            			<input id="resetButton" type="button" class="button" value='<bean:message key="button.reset" />' onclick="setCommand('<%=TracingConstants.COMMAND_CLEAR %>')" />
            			&nbsp;&nbsp;
            			<input id="createButton" type="button" class="button" value='<bean:message key="button.create" />' onclick="setCommand('<%=TracingConstants.COMMAND_CREATE %>')" />
              		</td>
            	</tr>
            </table>
            
            <logic:present name="results" scope="request">
            	<h1 class="green">
                	<bean:message key="header.search_result" />
             	</h1>
              	<a id="result"></a>
              	<display:table requestURI="/searchTemplate.do" name="requestScope.results" sort="external" class="form2" cellspacing="0" cellpadding="0" id="<%=TracingConstants.TABLE_ID_TEMPLATES %>" defaultsort="1" >
          			<display:column titleKey="colname.template.id" property="id" href="editTemplate.do" paramId="template_id" paramProperty="id" sortable="true" sortName="id" headerClass="header" />
          			<display:column titleKey="colname.template.name" property="name" href="editTemplate.do" paramId="template_id" paramProperty="id" sortable="true" sortName="name" headerClass="header" />
          			<display:column titleKey="colname.template.description" property="description" sortable="false" style="width:35%;" headerClass="header" />
          			<display:column titleKey="colname.create.date" property="dispCreateDate" sortable="true" sortName="createDate" headerClass="header" />
          			<display:column titleKey="colname.template.active" property="dispActive" sortable="true" sortName="active" headerClass="header" />
 			    	<display:footer>
		               	<tr>
			                <td colspan="5">
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
