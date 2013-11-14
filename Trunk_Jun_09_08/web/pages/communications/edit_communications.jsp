<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<script type="text/javascript" src="deployment/main/js/ckeditor/ckeditor.js"></script>
<%
	Agent a = (Agent)session.getAttribute("user");

	String cssFormClass = "";

	if (request.getAttribute("lostdelay") != null) {
		cssFormClass = "form2_ld";
	} else if (request.getAttribute("missing") != null) {
 		cssFormClass = "form2_pil";
	} else {
		cssFormClass = "form2_dam";   		
	}
  
%>
<script language="javascript" >

	function saveOrUpdate() {
		var id = document.getElementById('documentId');
		if (id.value == 0) {
			setCommand("<%=TracingConstants.COMMAND_CREATE %>");
		} else {
			setCommand("<%=TracingConstants.COMMAND_UPDATE %>");
		}
		document.customerCommunicationsForm.submit();
	}
	
	function saveAndPreview() {
		document.getElementById('preview').value = "true";
		saveOrUpdate();
	}
	
	function setCommand(command) {
		document.getElementById('command').value = command;
	}

	function openPreviewWindow(fileName) {
		window.open("customerCommunications.do?preview_document="+fileName, '', 'width=600,height=800,resizable=yes');
	}
	
</script>
<html:form action="customerCommunications.do" focus="documentTitle" method="post" >
<html:hidden property="command" styleId="command" />
<html:hidden property="preview" styleId="preview" />
<html:hidden property="id" styleId="id" />
<html:hidden property="incidentId" styleId="incidentId" />
<html:hidden property="templateId" styleId="templateId" />
<html:hidden property="fileName" styleId="fileName" />
	<tr>
		<td colspan="3" id="pageheadercell">
			<div id="pageheaderleft">
				<h1>
					<bean:message key="colname.incident.id" />:&nbsp;<bean:write name="customerCommunicationsForm" property="incidentId" />
				</h1>
			</div>
		</td>
	</tr>
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="customerCommunicationsForm" property="incidentId" />#activities'>
              	<span class="aa">
              		&nbsp;
              		<br />
              		&nbsp;
             	</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
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
								<logic:equal name="customerCommunicationsForm" property="documentId" value="0">
									<bean:message key="header.create" />
								</logic:equal>
								<logic:notEqual name="customerCommunicationsForm" property="documentId" value="0">
									<bean:message key="header.edit" />
								</logic:notEqual>
								<bean:message key="header.outgoing.communication" />
			   				</h1>
						</td>
						<td style="text-align:right;">
							<logic:present name="previewLink" scope="request" >
								<a href="#" onclick="openPreviewWindow('<%=(String) request.getAttribute("previewLink") %>');">
									<bean:message key="link.preview" />
								</a>
							</logic:present>
							<logic:notPresent name="previewLink" scope="request" >
								&nbsp;
							</logic:notPresent>
						</td>
					</tr>			
				</table>
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
					<tr>
						<td class="header">
							<bean:message key="header.document.title" />
						</td>
						<td>
							<html:text property="documentTitle" styleId="documentTitle" size="60" styleClass="textfield" />
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="header.document.id" />
						</td>
						<td>
							<html:text property="documentId" styleId="documentId" readonly="true" styleClass="textfield" />
						</td>
					</tr>
					<tr>
						<td class="header">
							<bean:message key="communications.type" />
						</td>
						<td>
							<html:select name="customerCommunicationsForm" property="custCommId" styleClass="dropdown" >
								<html:options collection="customerCommunicationsList" property="status_ID" labelProperty="description" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td colspan="2" >
							<textarea id="data" name="data"><bean:write name="customerCommunicationsForm" property="data" /></textarea>
							<script type="text/javascript" >
								CKEDITOR.replace('data',
									{
										toolbar: 'TemplateToolbar'
									});
							</script>
						</td>
					</tr>
				</table>
				<div style="text-align:center;" >
					&nbsp;&nbsp;
					<input type="button" id="submitCustComm" class="button" value='<bean:message key="button.save" />'  onclick="saveOrUpdate();"/>
					&nbsp;&nbsp;
					<input id="savePreviewButton" type="button" class="button" value='<bean:message key="button.save.preview" />' onclick="saveAndPreview();">
				</div>
			</div>
		</td>
	</tr>
</html:form>
