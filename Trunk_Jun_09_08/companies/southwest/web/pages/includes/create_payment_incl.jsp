<%@ page language="java" %>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
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
	case 'PSO':
		break;
	default:
		break;
	}
}
</script>
<tr>
	<td>
		<bean:message key="payment.type"/>
		<br />
		<html:select property="paymentType" onchange="updatePaymentFields(this.options[this.selectedIndex].value);" styleClass="dropdown">
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
