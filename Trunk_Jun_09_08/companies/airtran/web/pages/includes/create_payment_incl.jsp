<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
<script type="text/javascript">

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
	default:
		break;
	}
}
</script>
<tr>
	<td>
		<bean:message key="colname.expense_type"/>
		<br />
		<html:select property="paymentType" onchange="updatePaymentFields(this.options[this.selectedIndex].value);">
			<html:optionsCollection name="paymentTypeList"/>
		</html:select>
	</td>
	<td id="amountColumn">
		<bean:message key="draft.amount"/>
		<br />
		<html:text property="checkamt" maxlength="20"></html:text>
	</td>
	<td id="detailColumn">
		<bean:message key="colname.currency" />
        <br />
        <html:select property="currency_ID" styleClass="dropdown">
            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
        </html:select>
	</td>
</tr>
<tr>
  <td colspan="3">
    <bean:message key="colname.draft" />
    <br />
    <html:text property="draft" size="15" maxlength="10" styleClass="textfield" />
  </td>
</tr>