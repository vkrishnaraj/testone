<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
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
                            <html:text property="draft" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                          <td>
                            <bean:message key="colname.draftreqdate" />
                            (
                            <%= a.getDateformat().getFormat() %>)
                            <br />
                            <html:text property="dispDraftreqdate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftreqdate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                          <td>
                            <bean:message key="colname.draftpaiddate" />
                            (
                            <%= a.getDateformat().getFormat() %>)
                            <br />
                            <html:text property="dispDraftpaiddate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftpaiddate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.checkamt" />
                            <br />
                            <html:text property="checkamt" size="11" maxlength="10" styleClass="textfield" />
                            <br />
                            <bean:message key="colname.currency" />
                            <br />
                            <html:select property="currency_ID" styleClass="dropdown">
                              <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                            </html:select>
                          </td>
                          <td>
                            <bean:message key="colname.voucheramt" />
                            <br />
			    <html:text property="voucheramt" size="15" maxlength="10" styleClass="textfield" />
                            <br />
                            <bean:message key="colname.voucherexp" />
                            <br />
                            <html:text property="dispVoucherExpirationDate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispVoucherExpirationDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
                          </td>
                          <td>
                            <bean:message key="colname.cc_refund" />
                            <br />
                            <html:text property="creditCardRefund" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.incidental.auth.amount" />
                            <br />
                            <html:text property="incidentalAmountAuth" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                          <td colspan="2">
                            <bean:message key="colname.incidental.claim.amount" />
                            <br />
                            <html:text property="incidentalAmountClaimed" size="15" maxlength="10" styleClass="textfield" />
                          </td>
		  </tr>

