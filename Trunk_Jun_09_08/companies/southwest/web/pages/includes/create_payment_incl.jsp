<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.forms.ExpensePayoutForm"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<script type="text/javascript">
<%
Agent a = (Agent) request.getSession().getAttribute("user");
boolean canEdit = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE,a);
boolean canApprove = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE,a);
boolean canPay = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
boolean hasImmFulfillPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_IMMEDIATE_FULFILLMENT, a);
boolean hasEmailFulfillPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EMAIL_FULFILLMENT, a);
boolean hasMailFulfillPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAIL_FULFILLMENT, a);
boolean hasCancelPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CANCEL_A_VOUCHER, a);
%>
function getStatusIds(type) {
	if (type=='VOUCH') {
		if (<%=hasImmFulfillPermission%> || <%=hasEmailFulfillPermission%> || <%=hasMailFulfillPermission%> ){
			document.getElementById("amountColumn").innerHTML='<bean:message key="issue.amount"/>' +
			'<br />' +
			'<html:text property="checkamt" maxlength="20" styleClass="textfield"></html:text>';
			document.getElementById("button").value='<bean:message key="button.issue.voucher"/>';
			document.getElementById("distributedmethod").style.display= 'inline';
			issue_voucher = true;
		} else {
			window.alert("NO PERMISSION");
			issue_voucher = false;
		}
	} else if (type!='VOUCH'){
			document.getElementById("amountColumn").innerHTML='<bean:message key="draft.amount"/>' +
			'<br />' +
			'<html:text property="checkamt" maxlength="20" styleClass="textfield"></html:text>';
			document.getElementById("button").value='<bean:message key="button.request_for_approval"/>';
			document.getElementById("distributedmethod").style.display= 'none';
			issue_voucher = false;
	}
}

function updatePaymentFields(newType) {
	switch(newType) {
	case 'DRAFT':
		var x = document.getElementById('amountColumn');
		break;
	case 'VOUCH':
		break;
	case 'MILE':
		break;
	case 'INC':
		break;
	case 'CCREF':
		break;
	case 'PSO':
		break;
	default:
		break;
	}
	getStatusIds(newType);
}
</script>
<tr>
	<td>
		<bean:message key="colname.expense_type"/>
		<br />
		<html:select styleId="getpaymenttype" property="paymentType" onchange="updatePaymentFields(this.options[this.selectedIndex].value);" styleClass="dropdown">
			<html:options collection="paymentTypeList" property="value" labelProperty="label" />
			<html:option value="PSO">
				<bean:message key="payment.type.pso" />
			</html:option>
		</html:select>
	</td>
	<td id="amountColumn">
		<bean:message key="draft.amount"/>
		<br />
		<html:text property="checkamt" maxlength="20" styleClass="textfield"></html:text>
	</td>
	<td id="detailColumn">
		<bean:message key="colname.currency" />
        <br />
        <html:select property="currency_ID" styleClass="dropdown">
            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
        </html:select>
	</td>
</tr>
<% if(hasImmFulfillPermission || hasEmailFulfillPermission || hasMailFulfillPermission) { %>	
<tr>
	<td></td>
	<td id="distributedmethod" style="display:none;">
		<bean:message key="colname.distribution_method" />
		<br />
		<html:select property="distributemethod" styleClass="dropdown" disabled="disabled">
			<% if(hasImmFulfillPermission) { %>		
			<html:option value="IMME">
				<bean:message key="Immediate.fulfill" />
			</html:option>
			<% } %>
			<% if(hasEmailFulfillPermission) { %>	
			<html:option value="EMAIL">
				<bean:message key="Email.fulfill" />
			</html:option>
			<% } %>
			<% if(hasMailFulfillPermission) { %>
			<html:option value="MAIL">
				<bean:message key="Mail.fulfill" />
			</html:option>
			<% } %>
		</html:select>
        <br />	
	</td>
	<td ></td>
</tr>
<% } %>
