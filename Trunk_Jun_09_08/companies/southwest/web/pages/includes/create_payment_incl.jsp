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
			document.getElementById("amountColumn").value='<bean:message key="issue.amount"/>' +
			'<br />' +
			'<html:text property="checkamt" maxlength="20" styleClass="textfield"></html:text>';
			document.getElementById("button").value='<bean:message key="button.issue.voucher"/>';
			document.getElementById("distributedmethod").style.display= 'inline';
			issue_voucher = true;
		} else {
			window.alert("NO PERMISSION");
			issue_voucher = false;
		}
	} else {
			document.getElementById("amountColumn").value='<bean:message key="draft.amount"/>' +
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

function validateRequiredFields() {
	if (document.getElementById("button").value != "<bean:message key="button.issue.voucher"/>") {
		return true;
	}
	
	var minimumValue = 1;
	var checkamt = document.getElementsByName("checkamt")[0];
	if (0 < checkamt.value.length && !checkFloatGreaterThanMinimumValue(checkamt.value, minimumValue)) {
        alert("<bean:message key="error.validation.minimum.amount"/>");
		checkamt.focus();
	    return false;
	}

   	var lastnameId = document.getElementsByName("lastname")[0];
	if (lastnameId.value.length < 1){
		alert("<bean:message key='colname.last_name'/>" + " <bean:message key='error.validation.isRequired'/>");
		lastnameId.focus();
		return false;
	} 
	
   	var firstnameId = document.getElementsByName("firstname")[0];
	if (firstnameId.value.length < 1){
		alert("<bean:message key='colname.first_name'/>" + " <bean:message key='error.validation.isRequired'/>");
		firstnameId.focus();
		return false;
	} 
	
   	var address1 = document.getElementsByName("address1")[0];
	if (address1.value.length < 1){
		alert("<bean:message key='colname.street_addr'/>" + " <bean:message key='error.validation.isRequired'/>");
		address1.focus();
		return false;
	}
	
   	var city = document.getElementsByName("city")[0];
	if (city.value.length < 1){
		alert("<bean:message key='colname.city'/>" + " <bean:message key='error.validation.isRequired'/>");
		city.focus();
		return false;
	}
	
   	var countrycodeId = document.getElementsByName("countrycode_ID")[0];
	if (countrycodeId.value.length < 1){
		alert("<bean:message key='colname.country'/>" + " <bean:message key='error.validation.isRequired'/>");
		countrycodeId.focus();
		return false;
	} else {

		if (countrycodeId.value == "US") {
			var stateId = document.getElementsByName("state_ID")[0];
			if (stateId.value.length < 1){
				alert("<bean:message key='colname.state'/>" + " <bean:message key='error.state.required'/>");
				stateId.focus();
				return false;
			}
			
			var zip = document.getElementsByName("zip")[0];
			if (zip.value.length < 1){
				alert("<bean:message key='colname.zip'/>" + " <bean:message key='error.state.required'/>");
				zip.focus();
				return false;
			}
		} else {
			var provinceId = document.getElementsByName("province")[0];
			if (provinceId.value.length < 1){
				alert("<bean:message key='colname.province'/>" + " <bean:message key='error.validation.isRequired'/>");
				provinceId.focus();
				return false;
			}	
		}
	}
	
   	var newCommentId = document.getElementsByName("newComment")[0];
	if (newCommentId.value.length < 1){
		alert("<bean:message key='colname.comments'/>" + " <bean:message key='error.validation.isRequired'/>");
		newCommentId.focus();
		return false;
	} 
	
   	var paycodeId = document.getElementsByName("paycode")[0];
	if (paycodeId.value.length < 1){
		alert("<bean:message key='colname.paycode'/>" + " <bean:message key='error.validation.isRequired'/>");
		paycodeId.focus();
		return false;
	} 
	return true;
}


function checkFloatGreaterThanMinimumValue(value, minimumValue) { 
    // remove '.' before checking digits
    var tempArray = value.split('.');
    var joinedString= tempArray.join('');

    if (!checkInteger(joinedString)) {
      return false;
    }

    var iValue = parseFloat(value);
    return (minimumValue <= iValue);
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
		<html:select styleId="distributemethod" property="distributemethod" styleClass="dropdown" disabled="disabled">
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
