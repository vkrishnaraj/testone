<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

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

<script type="text/javascript" src="deployment/main/js/fckeditor/fckeditor.js"></script>
<script type="text/javascript">

	var oFCKeditor;
	
	function insertVariable(variableSelect) {
		if (variableSelect.selectedIndex != -1) {
			var option = variableSelect.options[variableSelect.selectedIndex];
			if (option.value != -1) {
				var editor = FCKeditorAPI.GetInstance('data');
				editor.InsertHtml('\{' + option.value + '\}');
				variableSelect.selectedIndex = -1;	
			}
		}
	}

	function saveOrUpdateTemplate() {
		var id = document.getElementById('id');
		if (id.value == 0) {
			setCommand("<%=TracingConstants.COMMAND_CREATE %>");
		} else {
			setCommand("<%=TracingConstants.COMMAND_UPDATE %>");
		}
	}
	
	function setCommand(command) {
		document.getElementById('command').value = command;
	}

	function confirmDelete() {
		var del = confirm('<%=bundle.getString("message.confirm.delete") %>');
		if (del == true) {
			setCommand("<%=TracingConstants.COMMAND_DELETE %>")
			document.getElementById("documentTemplateForm").submit();
		}
	}

</script>
<html:form action="documentTemplate.do" method="post" onsubmit="return validateTemplateForm(this);" >
<html:hidden property="command"/>
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
					<logic:equal name="documentTemplateForm" property="id" value="0">
						<bean:message key="header.create" />
					</logic:equal>
					<logic:notEqual name="documentTemplateForm" property="id" value="0">
						<bean:message key="header.edit" />
					</logic:notEqual>
					<bean:message key="header.document.template" />
   				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
				<table class="form2_dam" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<bean:message key="colname.template.id" />
							<br>
							<html:text name="documentTemplateForm" property="id" readonly="true" styleId="id" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.create.date" />
							<br>
							<html:text name="documentTemplateForm" property="disCreateDate" styleId="disCreateDate" styleClass="textfield" disabled="true" />
						</td>
						<td>
							<bean:message key="colname.updated.date" />
							<br>
							<html:text name="documentTemplateForm" property="disLastUpdatedDate" styleId="disLastUpdateDate" styleClass="textfield" disabled="true" />
						</td>
					</tr>
				</table>
				<table class="form2_dam" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<bean:message key="colname.template.name" />
							<br>
							<html:text name="documentTemplateForm" property="name" size="60" styleId="name" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.template.active" />
							<br>
							<html:checkbox property="active" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<bean:message key="colname.template.description" />
							<br>
							<html:textarea name="documentTemplateForm" property="description" styleId="description" cols="80" rows="3" />
						</td>
					</tr>
					<tr>
						<td>
							<textarea id="data" name="data"><bean:write name="documentTemplateForm" property="data" /></textarea>
						</td>
						<td>
							<bean:message key="colname.template.variables" />
							<br>
							<select ondblclick="insertVariable(this);" class="dropdown" size="33" >
							<% 
								Map<String, List<String>> documentTemplateVars = (Map<String, List<String>>) session.getAttribute("documentTemplateVars"); 
								for (String group: documentTemplateVars.keySet()) {	
							%>
									<optgroup label="<%=group %>" >
										<% 
											for (String var: documentTemplateVars.get(group)) {
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
					oFCKeditor = new FCKeditor('data') ;
					oFCKeditor.BasePath = 'deployment/main/js/fckeditor/' ;
					oFCKeditor.ToolbarSet = 'bagbuzz';
					oFCKeditor.Height	= 450 ;
					oFCKeditor.Width    = 550 ;
					oFCKeditor.ReplaceTextarea() ;
				</script>
			</div>
			<div style="text-align:center;">
				<br>
				<input type="submit" class="button" value='<bean:message key="button.save" />' onclick="saveOrUpdateTemplate();">
				<logic:notEqual name="documentTemplateForm" property="id" value="0">
					&nbsp;&nbsp;
					<input type="button" class="button" value='<bean:message key="button.delete" />' onclick="confirmDelete();">
				</logic:notEqual>
			</div>
		</td>
	</tr>
</html:form>
