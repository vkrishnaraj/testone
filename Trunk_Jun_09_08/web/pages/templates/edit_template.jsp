<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
	Agent a = (Agent) session.getAttribute("user");
	ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>
<script type="text/javascript" src="deployment/main/js/ckeditor/ckeditor.js"></script>
<script type="text/javascript">

	function insertVariable(variableSelect) {
		if (variableSelect.selectedIndex != -1) {
			var option = variableSelect.options[variableSelect.selectedIndex];
			if (option.value != -1) {
				CKEDITOR.instances['data'].insertHtml('\{' + option.value + '\}');
				variableSelect.selectedIndex = -1;	
			}
		}
	}

	function saveOrUpdateTemplate() {
		if (validateTemplateForm(document.templateEditForm)) {
			var id = document.getElementById('id');
			if (id.value == 0) {
				setCommand("<%=TracingConstants.COMMAND_CREATE %>");
			} else {
				setCommand("<%=TracingConstants.COMMAND_UPDATE %>");
			}
			document.templateEditForm.submit();
		}
	}

	function saveAndPreview() {
		document.getElementById('preview').value = "true";
		saveOrUpdateTemplate();
	}
	
	function setCommand(command) {
		document.getElementById('command').value = command;
	}

	function confirmDelete() {
		var del = confirm('<%=bundle.getString("message.confirm.delete") %>');
		if (del == true) {
			setCommand("<%=TracingConstants.COMMAND_DELETE %>")
			document.templateEditForm.submit();
		}
	}

</script>
<html:form focus="name" action="editTemplate.do" method="post" >
<html:hidden property="command" styleId="command" />
<html:hidden property="preview" styleId="preview" />
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
								<bean:write name="msg"/><br>								
							</html:messages>
						</font>
						<br>
					</span>
				</logic:messagesPresent>
				<table id="pageheaderright" cellspacing="0" cellpadding="0" >
					<tr>
						<td>
							<h1 class="green">
								<logic:equal name="templateEditForm" property="id" value="0">
									<bean:message key="header.create" />
								</logic:equal>
								<logic:notEqual name="templateEditForm" property="id" value="0">
									<bean:message key="header.edit" />
								</logic:notEqual>
								<bean:message key="header.document.template" />
			   				</h1>
							<span class="reqfield">*</span>
							<bean:message key="message.required" />
						</td>
						<td style="text-align:right;">
							<logic:notEqual name="templateEditForm" property="id" value="0" >
								<a href="#" onclick="submitPrintRequest('editTemplate.do?preview_document=<bean:write name="templateEditForm" property="id" />','width=600,height=800,resizable=yes,scrollbars=yes');">
									<bean:message key="document.preview" />
								</a>
							</logic:notEqual>
							<logic:notPresent name="previewLink" scope="request" >
								&nbsp;
							</logic:notPresent>
						</td>
					</tr>			
				</table> 
				<table class="form2_dam" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<bean:message key="colname.template.id" />
							<br>
							<html:text property="id" readonly="true" styleId="id" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.create.date" />
							<br>
							<html:text property="disCreateDate" styleId="disCreateDate" styleClass="textfield" disabled="true" />
						</td>
						<td>
							<bean:message key="colname.updated.date" />
							<br>
							<html:text property="disLastUpdatedDate" styleId="disLastUpdateDate" styleClass="textfield" disabled="true" />
						</td>
					</tr>
				</table>
				<table class="form2_dam" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							*&nbsp;<bean:message key="colname.template.name" />
							<br>
							<html:text property="name" size="60" styleId="name" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.template.template.type" />
							<br>
							<html:select property="typeAvailableFor" styleId="typeAvailableFor" styleClass="dropdown" >
								<html:optionsCollection name="templateEditForm" property="typesList" value="ordinal" label="displayName" />
							</html:select>
						</td>
						<td>
							<bean:message key="colname.template.active" />
							<br>
							<html:checkbox property="active" />
						</td>
					</tr>
					<tr>
						<td colspan="3">
							<bean:message key="colname.template.description" />
							<br>
							<html:textarea property="description" styleId="description" cols="80" rows="3" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<bean:message key="colname.template.data" />
							<br>
							<textarea id="data" name="data"><bean:write name="templateEditForm" property="data" /></textarea>
						</td>
						<td>
							<bean:message key="colname.template.variables" />
							<br>
							<select id="variableSelect" ondblclick="insertVariable(this);" class="dropdown" size="40" >
							<% 
								Map<String, List<String>> templateVars = (Map<String, List<String>>) session.getAttribute("templateVars"); 
								for (String group: templateVars.keySet()) {	
							%>
									<optgroup label="<%=group %>" >
										<% 
											for (String var: templateVars.get(group)) {
										%>
												<option value="<%=group + "." + var %>" ><%=var %></option>													
										<%
											}
										%>	
									</optgroup>
							<% 	
								} 
							%>
							</select>
						</td>
					</tr>
				</table>
				<script type="text/javascript" >
					CKEDITOR.replace('data',
						{
							toolbar: 'TemplateToolbar'
						});
				</script>
			</div>
			<div style="text-align:center;">
				<br>
				<input id="saveButton" type="button" class="button" value='<bean:message key="button.save" />' onclick="saveOrUpdateTemplate();">
				<logic:notEqual name="templateEditForm" property="id" value="0">
					&nbsp;&nbsp;
					<input id="deleteButton" type="button" class="button" value='<bean:message key="button.delete" />' onclick="confirmDelete();">
				</logic:notEqual>
			</div>
		</td>
	</tr>
</html:form>