<%@ page language="java"%>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>


<%@page import="com.bagnet.nettracer.tracing.forms.ExpensePayoutForm"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>


<%
	Agent a = (Agent)request.getSession().getAttribute("user");
	boolean canEdit = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	boolean canApprove = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, a);
	boolean canPay = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	
	ExpensePayoutForm epf = (ExpensePayoutForm) request.getAttribute("expensePayoutForm");
%>
<html:form action="SaveExpense.do" method="post">
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
					<h1 class="green">
						<bean:message key="header.edit_payout" />
						<a href="#"
							onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;">
							<img src="deployment/main/images/nettracer/button_help.gif"
								width="20" height="21" border="0"> </a>
					</h1>
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
								<bean:message key="payment.type" />
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
						<tr>
							<td align="center" valign="top" colspan="3">
								<html:submit property="createExpense" styleId="button">
									<bean:message key="button.request_for_approval" />
								</html:submit>
							</td>
						</tr>
					</table>
				</div>
	</fmt:timeZone>

</html:form>

