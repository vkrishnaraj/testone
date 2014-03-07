<%@ page language="java"%>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.ExpensePayoutForm"%>
<%@ page import="com.bagnet.nettracer.tracing.bmo.ExpensePayoutBMO"%>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@ page import="org.apache.struts.util.LabelValueBean"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale, org.apache.struts.util.PropertyMessageResources"%>
<%@ page import="com.bagnet.nettracer.tracing.enums.TemplateType"%>
<%@ page import="java.util.TimeZone, com.bagnet.nettracer.tracing.utils.AdminUtils"%>

<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>

<script src="deployment/main/js/date.js"></script>
<script src="deployment/main/js/AnchorPosition.js"></script>
<script src="deployment/main/js/PopupWindow.js"></script>
<script src="deployment/main/js/popcalendar.js"></script>
<script src="deployment/main/js/field_validation.js"></script>
<%
	Agent a = (Agent) request.getSession().getAttribute("user");
	boolean canEdit = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	boolean canApprove = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_APPROVE_EXPENSE,
			a);
	boolean canPay = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_EXPENSE, a);
	boolean hasCancelPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CANCEL_A_VOUCHER, a);
	ExpensePayoutForm epf = (ExpensePayoutForm) request.getAttribute("expensePayoutForm");

	boolean submitOk = (epf.getPaymentType() !=null && epf.getPaymentType().equals(TracingConstants.ENUM_VOUCHER) && epf.getOrdernum() !=null)? true :false; 
	boolean showprint = epf.getPrintcount() == 0 ? true : false;
	String today = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
	String createdate = new SimpleDateFormat("MM/dd/yyyy").format(epf.getCreatedate());
	boolean sameday = today.equals(createdate);
	boolean showcancel = (sameday && epf.getCancelcount() == 0 ) ? true : false;
	boolean bsoPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_PROCESS, a) && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BSO_ADMIN,a);
	boolean payApproveCreatePerm = (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAYMENT_APPROVAL_CREATE, a) || UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAYMENT_APPROVAL, a)) && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAYMENT_APPROVAL_ADMIN,a);
	boolean isInBSO=(epf!=null && a!=null && a.getStation()!=null && epf.getExpenselocation_ID()==a.getStation().getStation_ID());
	boolean isVoucher=(epf!=null && epf.getPaymentType().equals("VOUCH"));
	boolean passDataInExp = PropertyBMO.isTrue(PropertyBMO.PASSENGER_DATA_IN_EXPENSE);
	
	Locale myLocale = (Locale) session.getAttribute("org.apache.struts.action.LOCALE");
	PropertyMessageResources myMessages = (PropertyMessageResources) request.getAttribute("org.apache.struts.action.MESSAGE");
	
	TimeZone timeZone = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(a.getDefaulttimezone()).getTimezone());
%>

    <script>
    
	 var cal1xx = new CalendarPopup();	

     var buttonSelected = ''; 
    
      function validateExpense() {
    	  <% if(bsoPermission){ 
    	  		double bsoLimit=0;
 	  			if(request.getAttribute("bsoLimit")!=null){
 	  				bsoLimit=(Double)request.getAttribute("bsoLimit"); 
 	  			} %>
	    	  	var checkAmt=document.getElementById("checkAmt");
	    	  	if(checkAmt!=null && <%=bsoLimit!=0%> && checkAmt.value><%=bsoLimit%>){

	                alert('<%= (String) myMessages.getMessage(myLocale, "unable.create.over.bso.limit", new Object[]{bsoLimit})%>');
	                return false;
    	  		}
    	  <% } %>
    	  var check
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

    	function loadList(selectId, url) {
    		if (document.getElementById(selectId).options.length > 1) return;

    		jQuery.ajax({
     			url:url,
    			cache: false,
    			success: function(result) {
    				populateSelect(selectId, result);
    			}
    		});	
    	}

    	function populateSelect(selectId, json) {
    		var activitySelect = document.getElementById(selectId);		
    		for (var i = 0; i < json.length; ++i) {
    			activitySelect.options[activitySelect.options.length] = new Option(json[i].description, json[i].value);
    		}
    	}
      
      	function submitIncidentActivity() {
      		showClaimSettleDialog();
  		}
      	
      	/** TODO: Figure out how to dynamically update this method **/
      	function showClaimSettleDialog() {
    		if (document.getElementById("claimSettleSelect").options.length == 1) {
    			loadList("claimSettleSelect", "customerCommunications.do?templateList=<%=String.valueOf(TemplateType.CLAIM_SETTLEMENT.getOrdinal()) %>");
    		}

   			var claimSettleSelectDialog = jQuery("#claimSettleSelectDiv").dialog({
 										height: 50,
 										width: 350,
 										title: '<%=myMessages.getMessage(myLocale, "voucher.title.select.document")%>',
 										modal: true,
 										buttons: {
 											Submit: function() {
 												jQuery(this).dialog("close");
 												var claimSettleSelect = document.getElementById("claimSettleSelect");
 												claimSettleId = claimSettleSelect.options[claimSettleSelect.selectedIndex].value;
 												if (claimSettleId == "") {
 													alert('<%=myMessages.getMessage(myLocale, "voucher.select.document")%>');
 													return;
 												}
 												submitRequest(claimSettleId);
 											}
 										}
 									});
   			claimSettleSelectDialog.dialog("open");
    	}

    	function submitRequest(templateId) {
    		var incidentId = document.getElementById("incident_ID").value;
    		var expenseId = document.getElementById("expensepayout_ID").value;
    		var activityId = "<%=TracingConstants.CREATE_SETTLEMENT_ACTIVITY%>";
    		var url = 'incidentActivity.do?command=<%=TracingConstants.COMMAND_CREATE %>&incident='+incidentId+'&activity='+activityId+'&expense='+expenseId;
    		if (templateId && templateId != null) {
    			url = url + '&templateId='+templateId;
    		}		
    		window.location.href=url;
    	}

      function showTemplateSelectDialog() {
    	alert('<%=myMessages.getMessage(myLocale, "voucher.alert.cancel")%>');
   		var templateSelectDialog = jQuery("#templateSelectDiv").dialog({
    										height: 50,
    										width: 350,
    										title: '<%=myMessages.getMessage(myLocale, "voucher.title.cancel")%>',
    										modal: true,
    										buttons: {
    											Submit: function() {
    												jQuery(this).dialog("close");
    												var templateSelect = document.getElementById("templateSelect");
    												templateId = templateSelect.options[templateSelect.selectedIndex].value;
    												if (templateId.length < 1) {
    													alert('<%=myMessages.getMessage(myLocale, "voucher.cancel.reason")%>');
    													return;
    												}
    												document.expensePayoutForm.cancelreason.value=templateId;
    												document.expensePayoutForm.submit();
    											}
    										}
    									});
    		templateSelectDialog.dialog("open");
    	}

	    function reSubmitWS() {
	    	alert('<%=myMessages.getMessage(myLocale, "voucher.alert.error.webservice")%>');
	    };  
	    
	    function submitOK(ordernum) {
	    	alert('<%=myMessages.getMessage(myLocale, "voucher.alert.cancelled")%>'+ ordernum);
	    };   	    
		
		function doPrint(message){
			jQuery('<div></div>').appendTo('body')
		                    .html('<div><h6>'+message+'</h6></div>')
		                    .dialog({
								height: 50,
								width: 350,
								modal: true,
		                        buttons: {
		                            No: function () {
		                            	jQuery(this).dialog("close");
		                            	document.expensePayoutForm.toremark.value="yes";
		                    			document.expensePayoutForm.submit();
		                            },
		                            Yes: function () {
		                                jQuery(this).dialog("close");
		                      	    	document.expensePayoutForm.printcount.value="1";
		                      	    	document.expensePayoutForm.submit();
		                            }
		                        }

		                    });
	
			submitPrintRequest('reporting?print=<%=ReportingConstants.EXPENSE_LUV_VOUCHER %>','width=600,height=800,resizable=yes,scrollbars=yes');
		 };
		    
    </script>
	<html:form action="UpdateExpense.do" method="post" onsubmit="return validateExpense(this);">
	<html:hidden name="expensePayoutForm" property="dateFormat" value="<%= a.getDateformat().getFormat() %>"/>
	<html:hidden name="expensePayoutForm" property="tz" value="<%= a.getCurrenttimezone() %>" />
	<html:hidden name="expensePayoutForm" property="expensepayout_ID" />
	<html:hidden name="expensePayoutForm" property="incident_ID" />
	<html:hidden name="expensePayoutForm" property="status_id" />
	<html:hidden name="expensePayoutForm" property="toremark"  value="no"/>
	<html:hidden name="expensePayoutForm" property="printcount"  value="0"/>
	<html:hidden name="expensePayoutForm" property="cancelcount"  value="0"/>
	<html:hidden name="expensePayoutForm" property="cancelreason" styleId="cancelreason" value="" />	
	<fmt:timeZone value="<%=timeZone%>">
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
						<logic:present property="incident_ID" name="expensePayoutForm">
						<dd>
							<a
								href='searchIncident.do?incident=<c:out value="${expensePayoutForm.incident_ID}" />'><span
								class="aa">&nbsp; <br /> &nbsp;</span> <span class="bb"><bean:message
										key="menu.incident_info" /> </span> <span class="cc">&nbsp; <br />
									&nbsp;</span> </a>
						</dd>
						</logic:present>
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
					<logic:messagesPresent message="true">
						<font color=red>
					        <html:messages id="msg" message="true">
					          <br />
					          <bean:write name="msg" />
					          <br />
					        </html:messages>
					    </font>
						<br />
				    </logic:messagesPresent>
					
					<% if (submitOk) { %>
					<c:if test="${expensePayoutForm.showsuccess == 1}">
    					<center><font color=green>
            			Successfully Submitted!
          				</font></center>
          				<%request.getSession().setAttribute("showsubmit", "0");	%>
 					</c:if>
					<div align="right" width="100%" >
						Status:<c:if test="${expensePayoutForm.printcount == 0 && expensePayoutForm.cancelcount == 0}">
								<% if (sameday) { %>Valid
								<% } else { %>Expired
								<% } %>
						       </c:if>

							   <c:if test="${expensePayoutForm.printcount == 1 && expensePayoutForm.cancelcount == 0}">Printed</c:if>
						       <c:if test="${expensePayoutForm.cancelcount == 1}">Cancelled</c:if> 
					</div>
					<div align="right" width="100%" >
					<% if (showprint && showcancel) { %>
						<a name="printrpt" href='#' onclick="doPrint('<%=myMessages.getMessage(myLocale, "message.confirm.print.voucher")%>')">
						<bean:message key="button.bdo_sendprint" />
						</a>
						&nbsp;&nbsp;
					<% } %>	
					<% if (hasCancelPermission && showcancel ) { %>
						<a href="#" onclick="showTemplateSelectDialog(); "> 
							Cancel
						</a>
					<% } %>		
					</div>
<div id="templateSelectDiv" style="display:none;" >
	<table style="width:100%;">
		<tr>
			<td style="width:50%;text-align:right;">Select a Cancel Reason:</td>
			<td style="width:50%;text-align:left;">
				<select id="templateSelect" style="	font-size:9px;border:1px solid #569ECD;margin:2px 0px 1px 0px;display:inline;" >
					<option value="INCAMT">INCORRECT AMOUNT</option>
					<option value="INCNAME">INCORRECT NAME</option>
					<option value="INCQNT">INCORRECT QUANTITY</option>
				</select>
			</td>
		</tr>
	</table>
</div>				
					<% } else { %>
					<a name="editpayout"></a>
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
									disabled="<%=bsoPermission %>">
									<html:options collection="stationlist" property="station_ID"
										labelProperty="stationcode" />
								</html:select>
							</td>
							<td>
								<bean:message key="payment.type" />
								<br />

								<html:select property="expensetype_id" styleClass="dropdown"
									disabled="<%=((bsoPermission&&isInBSO) || !bsoPermission)?false:true %>">
									<html:options collection="expensetypelist"
										property="expensetype_ID" labelProperty="description" />
								</html:select>

							</td>
							<td>


								<bean:message key="colname.paycode" />
								<br />
								<html:select property="paycode" styleClass="dropdown"
									disabled="<%=bsoPermission %>">
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
								<textarea rows="6" cols="80" readonly="readonly"><c:forEach items="${expensePayoutForm.oldComments}" var="comment" varStatus="status"><c:out value="${comment.agent.username}" />&nbsp;<fmt:formatDate value="${comment.createDate}" pattern="${comment.agent.dateformat.format} ${comment.agent.timeformat.format}"/>&#x0D;<c:out value="${comment.content}" /><c:if test="${!status.last }">&#x0D;&#x0D;</c:if></c:forEach></textarea>
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
									onkeyup="textCounter(this,this,255);" 
									disabled="<%=((bsoPermission&&isInBSO) || !bsoPermission)?false:true %>"/>
								<input name='newComment' type="text" id='comments2' value="255"
									size="4" maxlength="4" disabled="disabled" />
							</td>
						</tr>
						<% 
						}
					%>

<% if (passDataInExp) { %>
          <tr>
            <td nowrap >
              <bean:message key="colname.last_name" />
              <br>
              <html:text property="lastname" size="20" maxlength="20"  styleClass="textfield" disabled="<%=isVoucher %>"/>
            </td>
            <td nowrap >
              <bean:message key="colname.first_name" />
              <br>
              <html:text property="firstname" size="20" maxlength="20"  styleClass="textfield" disabled="<%=isVoucher %>"/>
            </td>
            <td>
              <bean:message key="colname.mid_initial" />
              <br>
              <html:text property="middlename" size="1" maxlength="1"  styleClass="textfield" disabled="<%=isVoucher %>"/>
            </td>
          </tr>
      	  <tr>
            <td colspan=2 >
              <bean:message key="colname.street_addr1" />
              <br>
              <html:text property="address1" size="45" maxlength="50" styleClass="textfield" disabled="true"/>
            </td>
            <td nowrap >
              <bean:message key="colname.street_addr2" />
              <br>
              <html:text property="address2" size="45" maxlength="50" styleClass="textfield" disabled="true"/>
            </td>
          </tr>
          <tr>
          	<td>
             <bean:message key="colname.city" />
             <br>
             <html:text property="city" size="15" maxlength="50" styleClass="textfield" disabled="true"/>
            </td>
            <td>
             <bean:message key="colname.state" />
             <br />
             <logic:equal name="expensePayoutForm" property="countrycode_ID" value="US">
               <html:select  property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" disabled="true">
                 <html:option value="">
                   <bean:message key="select.none" />
                     </html:option>
                     <html:options collection="statelist" property="value" labelProperty="label" />
               </html:select>
             </logic:equal>
             <logic:equal name="expensePayoutForm" property="countrycode_ID" value="">
               <html:select property="state_ID" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" disabled="true">
                 <html:option value="">
                   <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                 </html:select>
             </logic:equal>
             <logic:notEqual name="expensePayoutForm" property="countrycode_ID" value="">
                <logic:notEqual name="expensePayoutForm" property="countrycode_ID" value="US">
                  <html:select property="state_ID" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
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
                <logic:equal name="expensePayoutForm" property="countrycode_ID" value="US">
                  <html:text property="province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                    </logic:equal>
                      <logic:equal name="expensePayoutForm" property="countrycode_ID" value="">
                  <html:text property="province" size="15" maxlength="100" styleClass="textfield" disabled="true"/>
                      </logic:equal>
                      <logic:notEqual name="expensePayoutForm" property="countrycode_ID" value="">
                        <logic:notEqual name="expensePayoutForm" property="countrycode_ID" value="US">
                  <html:text property="province" size="15" maxlength="100" styleClass="textfield" disabled="true"/>
                         </logic:notEqual>
                      </logic:notEqual>
             </td>
           </tr>
           <tr>
             <td>
               <bean:message key="colname.home_ph" />
               <br>
               <html:text property="homephone" size="15" maxlength="25" styleClass="textfield" disabled="true"/>
             </td>
             <td>
               <bean:message key="colname.business_ph" />
               <br>
               <html:text property="workphone" size="15" maxlength="25" styleClass="textfield" disabled="true"/>
             </td>
             <td>
               <bean:message key="colname.mobile_ph" />
               <br>
               <html:text property="mobile" size="15" maxlength="25" styleClass="textfield" disabled="true"/>
             </td>
           </tr>                    
           <tr>
             <td colspan=2>
               <bean:message key="colname.zip" />
               <br>
                 <html:text property="zip" size="15" maxlength="11" styleClass="textfield" disabled="true"/>
             </td>
             <td>
               <bean:message key="colname.country" />
               <br>
                 <html:select property="countrycode_ID" styleClass="dropdown" onchange="checkstate(this,this.form,'state_ID', 'province');" disabled="true">
                   <html:option value="">
                      <bean:message key="select.none" />
                   </html:option>
                  <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                 </html:select>
              </td>
            </tr>
     	    <tr>
               <td colspan="2" width="33%">
                  <bean:message key="colname.email" />
                  <br>
                  <html:text property="email" size="42" maxlength="100" styleClass="textfield" disabled="true"/>
               </td>  
            </tr>
        	<html:hidden property="email"/>
			<html:hidden property="countrycode_ID"/>
			<html:hidden property="state_ID"/>
			<html:hidden property="address2"/>
			<html:hidden property="address1"/>
			<html:hidden property="province"/>
			<html:hidden property="city"/>
			<html:hidden property="zip"/>
        	<% if (!isInBSO || isVoucher) { %>
				<html:hidden property="lastname"/>
				<html:hidden property="firstname"/>
				<html:hidden property="middlename"/>
			<% } %>			
<% } %>
						<tr>
							<td align="center" valign="top" colspan="3">
								<%
									if (epf.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_PENDING) {
												if (canApprove && !payApproveCreatePerm ) {
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
								if(payApproveCreatePerm && !bsoPermission && !epf.isHasIncidentActivity()){ %>
									<input type="button" id="createClaimSettlement" class="button" value="<bean:message key="button.action.submit.review" />" onclick="if (validateExpense(this.form)) { submitIncidentActivity(); }" />

									<div id="claimSettleSelectDiv" style="display:none;" >
										<table style="width:100%;">
											<tr>
												<td style="width:50%;text-align:right;">Select a Document:</td>
												<td style="width:50%;text-align:left;">
													<select id="claimSettleSelect" style="	font-size:9px;border:1px solid #569ECD;margin:2px 0px 1px 0px;display:inline;" >
														<option value="0"><bean:message key="select.please_select" /></option>
													</select>
												</td>
											</tr>
										</table>
									</div>	
								<% }
								} else if (epf.getStatus_id() == TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED) {
									if (canEdit || canPay || canApprove) { %>
										<html:submit property="updateExpense" styleId="button">
											<bean:message key="button.updatePayment" />
										</html:submit>&nbsp;
								
								<% }
									if (canPay && !bsoPermission) { %>
										<html:submit property="payExpense" styleId="button" onclick="buttonSelected='payExpense';">
											<bean:message key="button.payExpense" />
										</html:submit>
									<%}
								} else if (!submitOk && (canEdit || canPay || canApprove)) {
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
	<% if(bsoPermission){ 
        	if(!isInBSO){%>
        		<html:hidden property="expensetype_id"/>
				<html:hidden property="newComment"/>
        	<% } %>
        
        	<html:hidden property="paycode" />
        	<html:hidden property="expenselocation_ID" />
    <% } %>
<jsp:include page="/pages/includes/print_options.jsp" />
</html:form>
<c:if test="${expensePayoutForm.wssubmitc == 'no'}">
	<script type="text/javascript">reSubmitWS();</script>
</c:if>
<c:if test="${expensePayoutForm.wssubmitc == 'yes' and expensePayoutForm.cancelcount == 1}">
	<script type="text/javascript">submitOK('<c:out value="${expensePayoutForm.ordernum}" />');</script>
</c:if>
<c:if test="${expensePayoutForm.toremark == 'yes'}">
	<% epf.setToremark(null); %>
	<script type="text/javascript">
		doPrint('<%=myMessages.getMessage(myLocale, "message.confirm.print.voucher")%>');
	</script>
</c:if>

