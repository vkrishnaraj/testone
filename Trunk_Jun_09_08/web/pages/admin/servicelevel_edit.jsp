<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%
	Agent a = (Agent) session.getAttribute("user");
%>
<html:form action="bdoAdmin.do" method="post"
	onsubmit="return validateMaintainDeliveryCompanyForm(this);">
	<html:javascript formName="MaintainDeliveryCompanyForm" />
	<html:hidden name="MaintainDeliveryCompanyForm"	property="address" value="n/a"/>
	<html:hidden name="MaintainDeliveryCompanyForm"	property="name" value="n/a"/>
	<html:hidden name="MaintainDeliveryCompanyForm"	property="delivercompany_ID" />
	<html:hidden name="MaintainDeliveryCompanyForm"	property="serviceLevel_ID" />
	<tr>
		<td colspan="3" id="pageheadercell">
		<div id="pageheaderleft">
		<h1><bean:message key="header.editDeliveryCompany_servicelevel" /></h1>
		</div>
		<div id="pageheaderright">
		<table id="pageheaderright">
			<tr>
				<jsp:include page="/pages/includes/mail_incl.jsp" />
				<td><a href="#"
					onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
					key="Help" /></a></td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
	
	<tr>
		
		<td id="middlecolumn">
		<div id="maincontent">
		<h1 class="green"><bean:message key="header.deliverycompany_servicelevel" />: <bean:write name="MaintainDeliveryCompanyForm"	property="deliveryCompanyName" /><a
			href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img
			src="deployment/main/images/nettracer/button_help.gif" width="20"
			height="21" border="0"></a></h1>
		<table class="form2" cellspacing="0" cellpadding="0">
			<font color=red> <logic:messagesPresent message="true">
				<html:messages id="msg" message="true">
					<br />
					<bean:write name="msg" />
					<br />
				</html:messages>
			</logic:messagesPresent> </font>
			<tr>
				<td><bean:message key="header.deliverycompany_servicelevel" /> <font
					color=red> *: </td>
				<td><html:text styleClass="textfield"
					name="MaintainDeliveryCompanyForm" property="description" size="30"
					maxlength="50" /></td>
			</tr>
			<tr>
				<td><bean:message key="header.deliverycompany_servicelevel.service_code" /> <font
					color=red> *: </td>
				<td><html:text styleClass="textfield"
					name="MaintainDeliveryCompanyForm" property="service_code" size="30"
					maxlength="50" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><INPUT Id="button" type="button"
					value="Back" onClick="history.back()"> &nbsp; <html:submit
					styleId="button" property="saveServiceLevel">
					<bean:message key="button.save" />
				</html:submit></td>
			</tr>
		</table>
</html:form>
