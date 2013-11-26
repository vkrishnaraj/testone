<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.forms.ExpensePayoutForm"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%
  Agent a = (Agent)session.getAttribute("user");
ExpensePayoutForm epf = (ExpensePayoutForm) request.getAttribute("expensePayoutForm");
boolean hasImmFulfillPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_IMMEDIATE_FULFILLMENT, a);
boolean hasEmailFulfillPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EMAIL_FULFILLMENT, a);
boolean hasMailFulfillPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAIL_FULFILLMENT, a);
boolean hasCancelPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CANCEL_A_VOUCHER, a);

boolean swaBsoPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_PROCESS, a);
boolean swaIsInBSO=(epf!=null && a!=null && a.getStation()!=null && epf.getExpenselocation_ID()==a.getStation().getStation_ID());
boolean voucherValue =epf.getVoucheramt()>0;


%>

  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">

	var cal1xx = new CalendarPopup();	


  </SCRIPT>
 	<tr>
    	<td>
        	<bean:message key="colname.draft" />
            <br />
            <html:text property="draft" size="15" maxlength="10" styleClass="textfield" disabled="<%=((swaBsoPermission&&swaIsInBSO) || !swaBsoPermission)?false:true %>"/>
        </td>
        <td>
            <bean:message key="colname.draftreqdate" />
            (<%= a.getDateformat().getFormat() %>)
            <br />
            <html:text property="dispDraftreqdate"  size="15" maxlength="10" styleClass="textfield" />
            <img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftreqdate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
        </td>
        <td>
            <bean:message key="colname.draftpaiddate" />
            (<%= a.getDateformat().getFormat() %>)
            <br />
            <html:text property="dispDraftpaiddate" disabled="<%=((swaBsoPermission&&swaIsInBSO) || !swaBsoPermission)?false:true %>" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftpaiddate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
    </tr>
    <tr>
    	<td>
           <bean:message key="colname.checkamt" />
           <br />
           <html:text property="checkamt" size="11" maxlength="10" styleClass="textfield" disabled="<%=((swaBsoPermission&&swaIsInBSO) || !swaBsoPermission)?false:true %>"/>
           <br />
                     <bean:message key="colname.currency" />
           <br />
           <html:select property="currency_ID" styleClass="dropdown">
           		<html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
           </html:select>
        </td>
        <td id="voucherAmount">
            <bean:message key="issue.voucheramt" />
            <br />
			<html:text property="voucheramt" size="15" maxlength="10" styleClass="textfield" disabled="<%=((!voucherValue && swaBsoPermission && swaIsInBSO) || !swaBsoPermission)?false:true %>" />
			<br />
			<% if(hasImmFulfillPermission || hasEmailFulfillPermission || hasMailFulfillPermission) { %>			
						<bean:message key="colname.distribution_method" />
			<br />
			<html:select property="distributemethod" styleClass="dropdown" disabled="disabled">
			<% if(epf.getDistributemethod().equals(TracingConstants.DISTR_IMME)) { %>		
			<html:option value="IMME">
				<bean:message key="Immediate.fulfill" />
			</html:option>
			<% } %>
			<% if(epf.getDistributemethod().equals(TracingConstants.DISTR_EMAIL)) { %>	
			<html:option value="EMAIL">
				<bean:message key="Email.fulfill" />
			</html:option>
			<% } %>
			<% if(epf.getDistributemethod().equals(TracingConstants.DISTR_MAIL)) { %>
			<html:option value="MAIL">
				<bean:message key="Mail.fulfill" />
			</html:option>
			<% } %>
			</html:select>
			<% } %>
		</td>
        <td>
            <bean:message key="colname.mileageamt" />
            <br />
            <html:text property="mileageamt" size="15" maxlength="10" styleClass="textfield" />
        </td>                        
     </tr>

	 
        <% if(voucherValue && swaBsoPermission){ %>
        	<html:hidden property="voucheramt" />
        <% } %>
