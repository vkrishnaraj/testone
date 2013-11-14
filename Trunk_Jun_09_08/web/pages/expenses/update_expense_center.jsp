<%@ page language="java"%>
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/field_validation.js"></SCRIPT>
<%
	Agent a = (Agent) request.getSession().getAttribute("user");
	boolean canEdit = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	boolean canApprove = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE,
			a);
	boolean canPay = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	ExpensePayoutForm epf = (ExpensePayoutForm) request.getAttribute("expensePayoutForm");
	String paytype = (String) request.getSession().getAttribute("paytype");

	boolean submitOk = ((String) request.getSession().getAttribute("paytype")).equals(TracingConstants.ENUM_VOUCHER)? true :false; 

	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session
			.getAttribute("org.apache.struts.action.LOCALE");
%>

    <script language="javascript">
    
	 var cal1xx = new CalendarPopup();	

     var buttonSelected = ''; 
    
      function validateExpense() {
        if (buttonSelected == '') {
          return true;
        } else if (buttonSelected = 'payExpense') {
          
          if (document.expensePayoutForm.checkamt.value != '' && checkFloatGreaterThan0(document.expensePayoutForm.checkamt.value)) {
            if (document.expensePayoutForm.draft.value == '') {
              alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.claims.draftNumberRequired")%>');
              return false;
            }
          }
        }
        return true;
      }
      
      function openPreviewWindow1(fileName) {
    	  window.open("customerCommunications.do?preview_document="+fileName, '', 'width=600,height=800,resizable=yes');
      }
    	      
    </script>
<html:form action="UpdateExpense.do" method="post" onsubmit="return validateExpense(this);">
	<html:hidden name="expensePayoutForm" property="dateFormat" value="<%= a.getDateformat().getFormat() %>"/>
	<html:hidden name="expensePayoutForm" property="tz" value="<%= a.getCurrenttimezone() %>" />
	<html:hidden name="expensePayoutForm" property="expensepayout_ID" />
	<html:hidden name="expensePayoutForm" property="status_id" />
	<fmt:timeZone value="${expensePayoutForm.tz}">
		<tr>
			<td colspan="3" id="pageheadercell">
				<div id="pageheaderleft">

					<h1>
						<bean:message key="header.interim_expense_request" />
						(
						<c:out value="${expensePayoutForm.incident_ID}" />
						)
					</h1>


				</div>
				<div id="pageheaderright">
					<table id="pageheaderright">
						<tr>
							<jsp:include page="/pages/includes/mail_incl.jsp" />
							<td>
								<a href="#"
									onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
										key="Help" /> </a>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
		
		
		<tr>
			<td colspan="3" id="navmenucell">
				<div class="menu">
					<dl>
						<dd>
							<a
								href='searchIncident.do?incident=<c:out value="${expensePayoutForm.incident_ID}" />'><span
								class="aa">&nbsp; <br /> &nbsp;</span> <span class="bb"><bean:message
										key="menu.incident_info" /> </span> <span class="cc">&nbsp; <br />
									&nbsp;</span> </a>
						</dd>

						<dd>
							<a href="#"><span class="aab">&nbsp; <br /> &nbsp;</span> <span
								class="bbb"><bean:message key="menu.interim_expense" />
							</span> <span class="ccb">&nbsp; <br /> &nbsp;</span> </a>
						</dd>

					</dl>
				</div>
			</td>
		</tr>
		
		<tr>
			
			<td id="middlecolumn">
				
				<div id="maincontent">
					<a name="editpayout"></a>
					<% if (submitOk) { %>
					<h1 class="green" align="center">
						Successfully submitted!
 					</h1>
 					<a href="#" onclick="openPreviewWindow1('filename')"> 
						<bean:message key="button.bdo_sendprint" />
					</a>
					<% } else { %>
					<h1 class="green">
						<bean:message key="header.edit_payout" />
						<a href="#"
							onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;">
							<img src="deployment/main/images/nettracer/button_help.gif"
								width="20" height="21" border="0"> </a>
					</h1>					
					<% } %>

					<table class="form2" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<bean:message key="colname.createdate" />
								<br />
								<input type="text" name="createdate" size="15" class="textfield"
									disabled="disabled"
									value="<fmt:formatDate value='${expensePayoutForm.createdate}' pattern='${expensePayoutForm.dateFormat}' />" />
							</td>
							<td>
								<bean:message key="colname.agentusername" />
								<br />
								<html:text property="createUser" size="15"
									styleClass="textfield" disabled="true" />
							</td>
							<td>
								<bean:message key="colname.stationcreated_nobr" />
								<br />
								<html:text property="createStation" size="15"
									styleClass="textfield" disabled="true" />
							</td>
						</tr>
						<tr>
							<td>
								<bean:message key="colname.expense_loc" />
								<br />
								<html:select property="expenselocation_ID" styleClass="dropdown"
									disabled="disabled">
									<html:options collection="stationlist" property="station_ID"
										labelProperty="stationcode" />
								</html:select>
							</td>
							<td>
								<bean:message key="colname.expense_type" />
								<br />

								<html:select property="expensetype_id" styleClass="dropdown"
									disabled="disabled">
									<html:options collection="expensetypelist"
										property="expensetype_ID" labelProperty="description" />
								</html:select>

							</td>
							<td>


								<bean:message key="colname.paycode" />
								<br />
								<html:select property="paycode" styleClass="dropdown"
									disabled="disabled">
									<html:option value="ADV">
										<bean:message key="claim.interim" />
									</html:option>
									<html:option value="DEL">
										<bean:message key="claim.delivery" />
									</html:option>
									<html:option value="FIN">
										<bean:message key="claim.final" />
									</html:option>
									<html:option value="INS">
										<bean:message key="claim.insurance" />
									</html:option>
									<html:option value="OTH">
										<bean:message key="claim.other" />
									</html:option>
								</html:select>
                                <br />
                                <c:if test="${!empty expensePayoutForm.bdo_id}">
                                  <a href="bdo.do?bdo_id=<c:out value="${expensePayoutForm.bdo_id}"/>"><c:out value="${expensePayoutForm.bdo_ref}"/></a>
                                </c:if>
							</td>
						</tr>
						<jsp:include page="/pages/includes/payment_types_incl.jsp" />


						<tr>
							<td colspan="3">
								<bean:message key="agent.comments" />
								<br />
								<textarea rows="6" cols="80" readonly="readonly"><c:forEach items="${expensePayoutForm.oldComments}" var="comment" varStatus="status"><c:out value="${comment.agent.username}" />&nbsp;<fmt:formatDate value="${comment.createDate}" />&#x0D;<c:out value="${comment.content}" /><c:if test="${!status.last }">&#x0D;&#x0D;</c:if></c:forEach></textarea>
							</td>
						</tr>
					<% 
					if (canEdit || canApprove || canPay) {
						%>
						<tr>
							<td colspan="3">
								<bean:message key="colname.new.comments" />
								<br />
								<html:textarea property="newComment" cols="80" rows="5"
									onkeydown="textCounter(this,this,255);"
									onkeyup="textCounter(this,this,255);" />
								<input name='newComment' type="text" id='comments2' value="255"
									size="4" maxlength="4" disabled="disabled" />
							</td>
						</tr>
						<% 
						}
					%>
			
     <logic:iterate id="passenger" name="incidentForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.Passenger">

          <tr>
            <td nowrap colspan=2>
              <bean:message key="colname.last_name" />
              <br>
              <html:text name="passenger" property="lastname" size="20" maxlength="20" styleClass="textfield" />
            </td>
            <td nowrap colspan=2>
              <bean:message key="colname.first_name" />
              <br>
              <html:text name="passenger" property="firstname" size="20" maxlength="20" styleClass="textfield" />
            </td>
            <td>
              <bean:message key="colname.mid_initial" />
              <br>
              <html:text name="passenger" property="middlename" size="1" maxlength="1" styleClass="textfield" />
            </td>
          </tr>

          <logic:present name="passenger" property="addresses">
            <logic:iterate indexId="k" name="passenger" id="address" property="addresses" type="com.bagnet.nettracer.tracing.db.Address">
          	  <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="address" property="address1" size="45" maxlength="50" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="address" property="address2" size="45" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="address" property="city" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state" />
                  <br />
                  <logic:equal name="address" property="countrycode_ID" value="US">
                    <html:select name="address" property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="address" property="countrycode_ID" value="">
                    <html:select name="address" property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="address" property="countrycode_ID" value="">
                    <logic:notEqual name="address" property="countrycode_ID" value="US">
                      <html:select name="address" property="state_ID" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
                        <html:option value="">
                          <bean:message key="select.none" />
                        </html:option>
                        <html:options collection="statelist" property="value" labelProperty="label" />
                      </html:select>
                    </logic:notEqual>
                  </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.province" />
                  <br />
                      <logic:equal name="address" property="countrycode_ID" value="US">
                  <html:text name="address" property="province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="address" property="countrycode_ID" value="">
                  <html:text name="address" property="province" size="15" maxlength="100" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="address" property="countrycode_ID" value="">
                        <logic:notEqual name="address" property="countrycode_ID" value="US">
                  <html:text name="address" property="province" size="15" maxlength="100" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text name="address" property="zip" size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="address" property="countrycode_ID" styleClass="dropdown" onchange="checkstate(this,this.form,'state_ID', 'province');">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
 
              <tr>
                 <logic:equal name="incidentForm" property="incident_ID" value="">
                <td colspan="2" width="33%">
                  <bean:message key="colname.email" />
                  <br>
                  <html:text name="address" property="email" size="42" maxlength="100" styleClass="textfield" />
                  </logic:equal>
                 <logic:notEqual name="incidentForm" property="incident_ID" value="">
                <td colspan="3" width="50%">
                  <bean:message key="colname.email" />
                  <br>
                  <html:text name="address" property="email" size="45" maxlength="100" styleClass="textfield" />
                  </logic:notEqual>
                  <logic:equal name="incidentForm" property="incident_ID" value="">
<%
                    if (i.intValue() == 0 && request.getAttribute("companyDoesntEmail") == null) {
%>
                      <br />
                      <input type="checkbox" name="email_customer" value="1"
                      <logic:equal name="incidentForm" property="email_customer" value="1">
                        checked="checked"
                      </logic:equal>
                      >
                      <b><bean:message key="colname.report_email_cus" /></b>
                      </td>
                      <td width="17%">
                      <% String userLocale = a.getDefaultlocale();	%>
                      <bean:message key="colname.email.language" />
                      <br />
                    <select name="language" class="dropdown">
                      <logic:iterate id="locale" name="receiptLocaleList" scope="session">
                        <option value='<bean:write name="locale" property="value"/>' <%=(((LabelValueBean)locale).getValue().equals(userLocale)? "selected" : "") %>>
                        <bean:write name="locale" property="label" />
                      </logic:iterate>
                    </select>
<%
                    }
%>
                  </logic:equal>
                </td>
       
              </tr>
              
            </logic:iterate>
          </logic:present>
      </logic:iterate> 
					
					
						<tr>
							<td align="center" valign="top" colspan="3">
								<%
									if (epf.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_PENDING) {
												if (canApprove) {
								%>

								<html:submit property="updateExpense" styleId="button">
									<bean:message key="button.updatePayment" />
								</html:submit>
								&nbsp;
								<html:submit property="approveExpense" styleId="button">
									<bean:message key="button.approveinterim" />
								</html:submit>
								&nbsp;
								<html:submit property="denyExpense" styleId="button">
									<bean:message key="button.denyinterim" />
								</html:submit>
								<%
									} else if (canEdit) {
								%>
								<html:submit property="updateExpense" styleId="button">
									<bean:message key="button.updatePayment" />
								</html:submit>
								<%
									}
											} else if (epf.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
												if (canEdit || canPay || canApprove) {
								%>
								<html:submit property="updateExpense" styleId="button">
									<bean:message key="button.updatePayment" />
								</html:submit>&nbsp;
								
								<%
									}
									if (canPay) {
								%>
								<html:submit property="payExpense" styleId="button" onclick="buttonSelected='payExpense';">
									<bean:message key="button.payExpense" />
								</html:submit>
								<%
									}
											}
								else if (canEdit || canPay || canApprove) {
								%>
								<html:submit property="updateRemarkOnly" styleId="button">
									<bean:message key="button.updateCommenOnly" />
								</html:submit>
								<%
												}
								%>
							</td>
						</tr>
					</table>
				</div>
	</fmt:timeZone>
</html:form>
	<logic:present name="receiptName" scope="request">
	    <script language=javascript>
	    	var fileName = '<%=(String) request.getAttribute("receiptName") %>';
   			openPreviewWindow1(fileName);
   			anyLossCodeChanges();
	    </script>
    </logic:present>