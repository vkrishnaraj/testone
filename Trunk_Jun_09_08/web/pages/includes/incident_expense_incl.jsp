<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Iterator" %>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Locale" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.ExpenseUtils" %>


<script>
function toggleExpenseSummary() {
	var div = document.getElementById("expense_summary_div");
	var div_view = document.getElementById("expense_summary_div_view");
	if (div.style.display=="block" && div_view.style.display=="none"){
		div.style.display="none";
		div_view.style.display="block";
	}else{
		div.style.display="block";
		div_view.style.display="none";
	}
}
</script>
<table class='<%=request.getParameter("formCss")%>' cellspacing="0"
	cellpadding="0">
	<tr>
		<td><strong> <bean:message key="colname.createdate" />
		</strong></td>
		<td><strong> <bean:message key="header.status" />
		</strong></td>
		<td><strong> <bean:message key="header.expensetype" />
		</strong></td>
		<td><strong> <bean:message key="colname.paycode" />
		</strong></td>
		<td><strong> <bean:message key="colname.checkamt_br" />
		</strong></td>
		<td><strong> <bean:message
					key="header.approval_deny_date_br" />
		</strong></td>
		<td><strong> <bean:message key="colname.draftreqdate_br" />
		</strong></td>
		<td><strong> <bean:message
					key="colname.draftpaiddate_br" />
		</strong></td>
		<td><strong> <bean:message key="colname.draft_br" />
		</strong></td>
		<td><strong> <bean:message key="colname.action" />
		</strong></td>
	</tr>
<%
	Agent a = (Agent) session.getAttribute("user");
	ResourceBundle bundle = ResourceBundle.getBundle(
			"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
	String multipleCurrencies = bundle.getString("colname.grandtotal.multiple.currency");
	int payTypeCount = 5;
	String[] grandTotalDisplay = ExpenseUtils.initializeStringArray(payTypeCount, "0.00");
	Map<String, double[]> expenseSummary = new TreeMap<String, double[]>();
%>
	<logic:iterate id="expenselist" name="incidentForm"
		property="expenselist"
		type="com.bagnet.nettracer.tracing.db.ExpensePayout">
		<bean:define id="expensetype" name="expenselist"
			property="expensetype"
			type="com.bagnet.nettracer.tracing.db.ExpenseType" />
		<bean:define id="expenselocation" name="expenselist"
			property="expenselocation"
			type="com.bagnet.nettracer.tracing.db.Station" />
<%
	double amount = ExpenseUtils.getPaymentAmount(expenselist);
	expenseSummary = (Map<String, double[]>)ExpenseUtils.populateExpensePaySubtotals(expenselist, expenseSummary, amount);
%>
		<tr>
			<td><bean:write name="expenselist" property="discreatedate" />
				&nbsp;</td>
			<td valign="top"><bean:message name="expenselist"
					property="status.key" /> &nbsp;</td>
			<td valign="top"><bean:write name="expenselist"
					property="paytype" /> &nbsp;
			<td><bean:write name="expenselist" property="paycode" /> &nbsp;
			</td>
<%
	if (expenselist.getPaytype().startsWith("MILE")){
		String mileage = String.valueOf(amount).substring(0, String.valueOf(amount).indexOf("."));
%>
			<td align="right" nowrap><%=mileage %>
			</td>

<%		
	}else{
%>
			<td align="right" nowrap><%=TracingConstants.DECIMALFORMAT.format(amount) %>&nbsp; <bean:write name="expenselist"
					property="currency_ID" />
			</td>

<%
	}
%>
			<td><bean:write name="expenselist" property="dispapproval_date" />
				&nbsp;</td>
			<td><bean:write name="expenselist" property="disdraftreqdate" />
				&nbsp;</td>
			<td><bean:write name="expenselist" property="disdraftpaiddate" />
				&nbsp;</td>
			<td><bean:write name="expenselist" property="draft" /> &nbsp;</td>
			<logic:notEqual name="incidentForm" property="readonly" value="1">
				<td><a
					href="EditExpense.do?expense_id=<bean:write name='expenselist' property='expensepayout_ID'/>"><bean:message
							key="colname.modify" /></a></td>
			</logic:notEqual>
		</tr>
	</logic:iterate>
	

	<tr>
		<td colspan="10" align="center">
			<div id="expense_summary_div" style="display: none">
				<table class='<%=request.getParameter("formCss")%>' cellspacing="0"
	cellpadding="0">
					<tr>
						<td width="16%"></td>
						<td width="8%">&nbsp;</td>
						<td width="12%" align="left"><strong><bean:message key="STATUS_KEY_52" /></strong></td>
						<td width="12%" align="left"><strong><bean:message key="STATUS_KEY_53" /></strong></td>
						<td width="12%" align="left"><strong><bean:message key="STATUS_KEY_54" /></strong></td>
						<td width="12%" align="left"><strong><bean:message key="STATUS_KEY_55" /></strong></td>
						<td width="12%" align="left"><strong><bean:message key="STATUS_KEY_94" /></strong></td>
						<td width="16%"></td>
					</tr>
				
<%
	Set<String> e = expenseSummary.keySet();
	Iterator it = e.iterator();
	boolean hasMileage = false;
	String[] subtotalDisplay = new String[5];
	String[] grandtotalMileageDisplay = new String[5];
    while ( it.hasNext()){
    	 String key = (String)it.next();
    	 double[] values = expenseSummary.get(key);
     	 String currentCurrencyDisplay = key.substring(key.indexOf("-") + 1, key.indexOf("-") + 4);
     	 if (key.startsWith("MILE")){
     		hasMileage = true;  
     		//currentCurrencyDisplay = "";
     		subtotalDisplay[0] = String.valueOf(values[0]).substring(0, String.valueOf(values[0]).indexOf("."));
     		subtotalDisplay[1] = String.valueOf(values[1]).substring(0, String.valueOf(values[1]).indexOf("."));
     		subtotalDisplay[2] = String.valueOf(values[2]).substring(0, String.valueOf(values[2]).indexOf("."));
     		subtotalDisplay[3] = String.valueOf(values[3]).substring(0, String.valueOf(values[3]).indexOf("."));
     		subtotalDisplay[4] = String.valueOf(values[4]).substring(0, String.valueOf(values[4]).indexOf("."));
     		grandtotalMileageDisplay[0] = subtotalDisplay[0];
     		grandtotalMileageDisplay[1] = subtotalDisplay[1];
     		grandtotalMileageDisplay[2] = subtotalDisplay[2];
     		grandtotalMileageDisplay[3] = subtotalDisplay[3];
     		grandtotalMileageDisplay[4] = subtotalDisplay[4];
     	 }else{
      		subtotalDisplay[0] = TracingConstants.DECIMALFORMAT.format(values[0]) + "&nbsp;&nbsp;" + currentCurrencyDisplay;
      		subtotalDisplay[1] = TracingConstants.DECIMALFORMAT.format(values[1]) + "&nbsp;&nbsp;" + currentCurrencyDisplay;
      		subtotalDisplay[2] = TracingConstants.DECIMALFORMAT.format(values[2]) + "&nbsp;&nbsp;" + currentCurrencyDisplay;
      		subtotalDisplay[3] = TracingConstants.DECIMALFORMAT.format(values[3]) + "&nbsp;&nbsp;" + currentCurrencyDisplay;
      		subtotalDisplay[4] = TracingConstants.DECIMALFORMAT.format(values[4]) + "&nbsp;&nbsp;" + currentCurrencyDisplay;    		 
     	 }
%>
					<tr>
						<td></td>
						<td align="left" ><strong><%=key.substring(0, key.indexOf("-")) %></strong></td>
						<td align="right" ><%=subtotalDisplay[0]%></td>
						<td align="right" ><%=subtotalDisplay[1]%></td>
						<td align="right" ><%=subtotalDisplay[2]%></td>
						<td align="right" ><%=subtotalDisplay[3]%></td>
						<td align="right" ><%=subtotalDisplay[4]%></td>
						<td></td>
					</tr>
<%
 	}//end while
 	grandTotalDisplay = ExpenseUtils.populateExpensePayGrandTotals(expenseSummary, multipleCurrencies);
%>
					<tr>
						<td></td>
						<td align="left" ><strong><bean:message key="colname.grandtotal" />:</strong></td>
						<td align="right" ><strong><%=grandTotalDisplay[0] %></strong></td>
						<td align="right" ><strong><%=grandTotalDisplay[1] %></strong></td>
						<td align="right" ><strong><%=grandTotalDisplay[2] %></strong></td>
						<td align="right" ><strong><%=grandTotalDisplay[3] %></strong></td>
						<td align="right" ><strong><%=grandTotalDisplay[4] %></strong></td>
						<td></td>
					</tr>
<%
	if (hasMileage){
%>
					<tr>
						<td></td>
						<td align="left" >&nbsp;</td>
						<td align="right" ><strong><%=grandtotalMileageDisplay[0] %></strong></td>
						<td align="right" ><strong><%=grandtotalMileageDisplay[1] %></strong></td>
						<td align="right" ><strong><%=grandtotalMileageDisplay[2] %></strong></td>
						<td align="right" ><strong><%=grandtotalMileageDisplay[3] %></strong></td>
						<td align="right" ><strong><%=grandtotalMileageDisplay[4] %></strong></td>
						<td></td>
					</tr>

<%
	}
%>					
				</table>
			<a href="#interimexpense" onclick="toggleExpenseSummary()"><bean:message
					key="colname.hide_expense_summary" /></a>
</div> 
<div id="expense_summary_div_view" style="display: block"> 
<a href="#interimexpense" onclick="toggleExpenseSummary()"><bean:message
					key="colname.view_expense_summary" /></a>
					</div> 
		</td>
	</tr>

	
	<logic:notEmpty name="incidentForm" property="readonly">
		<logic:notEqual name="incidentForm" property="readonly" value="1">
			<tr>
				<td align="center" valign="top" colspan="12"><html:button
						property="addnewexpense" styleId="button"
						onclick="document.location.href='CreateExpense.do'">
						<bean:message key="button.add_payout" />
					</html:button></td>
			</tr>
		</logic:notEqual>
	</logic:notEmpty>
</table>
