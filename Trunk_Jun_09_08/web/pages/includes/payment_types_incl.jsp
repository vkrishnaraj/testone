<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
                        <tr>
                          <td>
                            <bean:message key="colname.draft" />
                            <br />
                            <html:text property="draft" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                          <td>
                            <bean:message key="colname.draftreqdate" />
                            (<c:out value="${date_format}"/>)
                            <br />
                            
                            <input type="text" name="dispDraftreqdate" size="15" maxlength="10" class="textfield" value="<fmt:formatDate value='${expensePayoutForm.draftreqdate}' pattern='${date_format}' />"/>
                            <img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftreqdate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                          <td>
                            <bean:message key="colname.draftpaiddate" />
                            (<%= a.getDateformat().getFormat() %>)
                            <br />
                            <html:text property="dispDraftpaiddate"  size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftpaiddate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
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
                          </td>
                          <td>
                            <bean:message key="colname.mileageamt" />
                            <br />
                            <html:text property="mileageamt" size="15" maxlength="10" styleClass="textfield" />
                          </td>
                        </tr>
