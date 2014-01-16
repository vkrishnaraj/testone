<%@ page language="java"%>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout"%>
<%@page import="com.bagnet.nettracer.tracing.forms.ExpensePayoutForm"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
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
<SCRIPT LANGUAGE="JavaScript">
   
    var issue_voucher = false;
	var cal1xx = new CalendarPopup();

	    function ReSubmitWS() {
	    	alert('Error in submitting Web Service. Please Resubmit or Call Administrator for connection issue.');
	    };
	    
	    function validateEmail() {
	    	var selectlist = document.getElementById("distributemethod");
	    	templateId = selectlist.options[selectlist.selectedIndex].value;
	    	    	 
	    	var templateSelect2 = document.getElementById("email");
			templateId2 = templateSelect2.value;
			if (templateId == "EMAIL" && templateId2 == "" ){
	    		 alert('Email address is required.'); 
	    		 return false;
	    	}
	    	return true;
	    };	
	    
	    function textCounter(field, countfield, maxlimit) {
	        if (field.value.length > maxlimit) {
	          field.value = field.value.substring(0, maxlimit);
	        } else {
	          countfield.value = maxlimit - field.value.length;
	        }
	      }	
	    
		function confirmPrint(message){
			var currentForm;
		    jQuery('<div></div>').appendTo('body')
		                    .html('<div><h6>'+message+'?</h6></div>')
		                    .dialog({
								height: 50,
								width: 350,
								modal: true,
		                        buttons: {
		                            Yes: function () {
		                                jQuery(this).dialog("close");
		                            	expensePayoutForm.submit();
		                            },
		                            No: function () {
		                            	jQuery(this).dialog("close");
		                            }
		                        }

		                    });


		    };		    
	    
  </SCRIPT>


<%
	Agent a = (Agent)request.getSession().getAttribute("user");
	boolean canEdit = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	boolean canApprove = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE, a);
	boolean canPay = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	boolean swaBsoPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_PROCESS, a) && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_ADMIN,a);
	ExpensePayoutForm epf = (ExpensePayoutForm) request.getAttribute("expensePayoutForm");
%>
<html:form action="SaveExpense.do" method="post" onsubmit="if (issue_voucher) { confirmPrint('Are you sure you want to Issue this LUV Voucher?'); return false; }">
	
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
				
				<div id="maincontent"><font color=red>
				      <logic:messagesPresent message="true">
				        <html:messages id="msg" message="true">
				          <br />
				          <bean:write name="msg" />
				          <br />
				        </html:messages>
				      </logic:messagesPresent> </font>
					<br />
					<a name="editpayout"></a>
					<h1 class="green">
						<bean:message key="header.addnew_payout" />
						<a href="#"
							onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img
								src="deployment/main/images/nettracer/button_help.gif"
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
								<html:select property="expenselocation_ID" styleClass="dropdown" disabled="<%=swaBsoPermission %>">
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
								<html:select property="paycode" styleClass="dropdown"  disabled="<%=swaBsoPermission %>">
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
							</td>
						</tr>
						<% if(swaBsoPermission){ %>
					 	<tr>
					    	<td>
					        	<bean:message key="colname.draft" />
					            <br />
					            <html:text property="draft" size="15" maxlength="10" styleClass="textfield" />
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
					            <html:text property="dispDraftpaiddate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispDraftpaiddate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
					            
					            <br/><br/>
					            
					            <bean:message key="colname.maildate" />
					            (<%= a.getDateformat().getFormat() %>)
					            <br />
					            <html:text property="dispMaildate" size="15" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.expensePayoutForm.dispMaildate,'calendar3','<%= a.getDateformat().getFormat() %>'); return false;">
					        </td>
					            
					    </tr>
					    <% } %>
						<jsp:include page="/pages/includes/create_payment_incl.jsp" />
						<tr>
							<td colspan="3">
								<bean:message key="colname.comments" />
								<br />
								<html:textarea property="newComment" cols="80" rows="5"
									onkeydown="textCounter(this.form.newComment,this.form.comments2,255);"
									onkeyup="textCounter(this.form.newComment,this.form.comments2,255);" />
								<input name='comments2' type="text" id='comments2' value="255"
									size="4" maxlength="4" disabled="disabled"/>
							</td>
						</tr>
			
     <logic:iterate id="passenger" name="expensePayoutForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.PassengerExp">

          <tr>
            <td nowrap >
              <bean:message key="colname.last_name" />
              <br>
              <html:text name="passenger" property="lastname" size="20" maxlength="20"  styleClass="textfield" indexed="true"/>
            </td>
            <td nowrap >
              <bean:message key="colname.first_name" />
              <br>
              <html:text name="passenger" property="firstname" size="20" maxlength="20"  styleClass="textfield" indexed="true"/>
            </td>
            <td>
              <bean:message key="colname.mid_initial" />
              <br>
              <html:text name="passenger" property="middlename" size="1" maxlength="1"  styleClass="textfield" indexed="true"/>
            </td>
          </tr>
      	  <tr>
            <td colspan=2 >
              <bean:message key="colname.street_addr1" />
              <br>
              <html:text name="passenger" property="address1" size="45" maxlength="50" styleClass="textfield" indexed="true"/>
            </td>
            <td nowrap >
              <bean:message key="colname.street_addr2" />
              <br>
              <html:text name="passenger" property="address2" size="45" maxlength="50" styleClass="textfield" indexed="true"/>
            </td>
          </tr>
          <tr>
          	<td>
             <bean:message key="colname.city" />
             <br>
             <html:text name="passenger" property="city" size="15" maxlength="50" styleClass="textfield" indexed="true"/>
            </td>
            <td>
             <bean:message key="colname.state" />
             <br />
             <logic:equal name="passenger" property="countrycode_ID" value="US">
               <html:select  name="passenger" property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" indexed="true" >
                 <html:option value="">
                   <bean:message key="select.none" />
                     </html:option>
                     <html:options collection="statelist" property="value" labelProperty="label" />
               </html:select>
             </logic:equal>
             <logic:equal name="passenger" property="countrycode_ID" value="">
               <html:select name="passenger" property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" indexed="true" >
                 <html:option value="">
                   <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                 </html:select>
             </logic:equal>
             <logic:notEqual name="passenger" property="countrycode_ID" value="">
                <logic:notEqual name="passenger" property="countrycode_ID" value="US">
                  <html:select name="passenger" property="state_ID" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" indexed="true">
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
                <logic:equal name="passenger" property="countrycode_ID" value="US">
                  <html:text name="passenger" property="province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" indexed="true"/>
                    </logic:equal>
                      <logic:equal name="passenger" property="countrycode_ID" value="">
                  <html:text name="passenger" property="province" size="15" maxlength="100" styleClass="textfield" indexed="true"/>
                      </logic:equal>
                      <logic:notEqual name="passenger" property="countrycode_ID" value="">
                        <logic:notEqual name="passenger" property="countrycode_ID" value="US">
                  <html:text name="passenger" property="province" size="15" maxlength="100" styleClass="textfield" indexed="true"/>
                         </logic:notEqual>
                      </logic:notEqual>
             </td>
           </tr>
           <tr>
             <td>
               <bean:message key="colname.home_ph" />
               <br>
               <html:text name="passenger" property="homephone" size="15" maxlength="25" styleClass="textfield" indexed="true"/>
             </td>
             <td>
               <bean:message key="colname.business_ph" />
               <br>
               <html:text name="passenger" property="workphone" size="15" maxlength="25" styleClass="textfield" indexed="true"/>
             </td>
             <td>
               <bean:message key="colname.mobile_ph" />
               <br>
               <html:text name="passenger" property="mobile" size="15" maxlength="25" styleClass="textfield" indexed="true"/>
             </td>
           </tr>           
           <tr>
             <td colspan=2>
               <bean:message key="colname.zip" />
               <br>
                 <html:text name="passenger" property="zip" size="15" maxlength="11" styleClass="textfield" indexed="true"/>
             </td>
             <td>
               <bean:message key="colname.country" />
               <br>
                 <html:select name="passenger" property="countrycode_ID" styleClass="dropdown" onchange="checkstate(this,this.form,'state_ID', 'province');" indexed="true">
                   <html:option value="">
                      <bean:message key="select.none" />
                   </html:option>
                  <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                 </html:select>
              </td>
            </tr>
     	    <tr >
               <td colspan="2" width="33%">
                  <bean:message key="colname.email" />
                  <br>
                  <html:text styleId="email" name="passenger" property="email" size="42" maxlength="100" styleClass="textfield" indexed="true"/>
               </td>  
            </tr>
      </logic:iterate> 
      
	<% if(swaBsoPermission){  %>
        
        	<html:hidden property="paycode" name="expensePayoutForm" value="ADV"/> <!-- Default value, will save as blank otherwise -->
        	<html:hidden property="expenselocation_ID" name="expensePayoutForm"/>
    <% } %>

				
						<%
						if(canEdit) {
						 %>
						<tr>
							<td align="center" valign="top" colspan="3">

								<html:submit  property="createExpense" styleId="button" onclick="return validateEmail();"> 
									<bean:message key="button.request_for_approval" />
								</html:submit>
							</td>
						</tr>
						<%
						}
						 %>
						 
					</table>
				</div>
	</fmt:timeZone>
</html:form>
 <script type="text/javascript">
var selectlist = document.getElementById('getpaymenttype');
updatePaymentFields(selectlist.options[selectlist.selectedIndex].value);
</script >	
<c:if test="${expensePayoutForm.wssubmitp == 'no'}">
    <script type="text/javascript">ReSubmitWS();</script>
 </c:if>
