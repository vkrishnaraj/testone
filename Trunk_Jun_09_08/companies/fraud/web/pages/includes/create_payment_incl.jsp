<%@ page language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
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
		<bean:message key="colname.voucherexp" />
                            <br />
                            <html:text property="dispVoucherExpirationDate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispVoucherExpirationDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
	</td>
</tr>