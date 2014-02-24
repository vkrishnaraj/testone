<%@page import="com.bagnet.nettracer.tracing.forms.ClaimForm"%>
<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="aero.nettracer.fs.model.Person" %>
<%@ page import="aero.nettracer.fs.model.FsReceipt" %>
<%@ page import="aero.nettracer.fs.model.Attachment" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Company" %>
<%@ page import="java.util.ArrayList" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  ArrayList<LabelValueBean> stateList = (ArrayList<LabelValueBean>) session.getAttribute("statelist");
  ArrayList<Company> companyList = (ArrayList<Company>) session.getAttribute("companylistByName");
  ArrayList<LabelValueBean> countryList = (ArrayList<LabelValueBean>) session.getAttribute("countrylist");
  
  ClaimForm cform=(ClaimForm)session.getAttribute("claimForm");
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
  boolean crapFields=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_REPORT_ADJUSTMENT_PREDICTION, a);
  boolean hasIncident=(cform.getClaim()!=null && cform.getClaim().getNtIncidentId()!=null);
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	
    function changebutton() {
        document.claimForm.saveclaim.disabled = true;
        document.claimForm.saveclaim.value = "<bean:message key="ajax.please_wait" />";
        document.claimForm.save.disabled = false;
      }
      
      function undoChangebutton() {
        document.claimForm.saveclaim.disabled = false;
        document.claimForm.saveclaim.value = "<bean:message key="button.claim.resolution.save" />";
        document.claimForm.save.disabled = true;
      }

      function show(name,link1,link2) {
    	  jQuery(name).show();
    	  toggleLinks(link1,link2);
    	  document.getElementById(name).value = "true";
      }
      
      function hide(name,link1,link2) {
    	  jQuery(name).hide();
    	  toggleLinks(link1,link2);
    	  document.getElementById(name).value = "false";
      }
      
      function toggleLinks(link1,link2) {
	  	  if (jQuery(link1).is(':visible')) {
	  		  jQuery(link1).hide();
	  		  jQuery(link2).show();
	  	  } else {
	  		  jQuery(link1).show();
	  		  jQuery(link2).hide();
	  	  }
  	  }
      
      function setField(fieldId) {
    	  var field = document.getElementById(fieldId);
    	  field.value = "1";
      }
      
      function fieldChanged(fieldId) {
    	  if (fieldId.indexOf("state") != -1) {
    		  var state = document.getElementById(fieldId);
    		  var province = document.getElementById(fieldId.replace("state", "province"));
    		  var country = document.getElementById(fieldId.replace("state", "country"));
    		  stateChanged(state, province, country);
    	  } else if (fieldId.indexOf("province") != -1) {
    		  var state = document.getElementById(fieldId.replace("province", "state"));
    		  var province = document.getElementById(fieldId);
    		  var country = document.getElementById(fieldId.replace("province", "country"));
    		  provinceChanged(state, province, country);
    	  } else if (fieldId.indexOf("country") != -1) {
    		  var state = document.getElementById(fieldId.replace("country", "state"));
    		  var province = document.getElementById(fieldId.replace("country", "province"));
    		  var country = document.getElementById(fieldId);
    		  countryChanged(state, province, country);
    	  }
      }
      
      function stateChanged(state, province, country) {
    	 if (!state || !province || !country) {
    		 return;
    	 }
    	 
  		if (state.value == "") {
  			province.disabled = false;	
  			province.className = "textfield";
  		} else {
  			province.value = "";
  			province.disabled = true;
  			province.className = "disabledtextfield";
  			country.value = "US";
  		}
  	}
  	
  	function provinceChanged(state, province, country) {
    	 if (!state || !province) {
    		 return;
    	 }
  		if (province.value == "") {
  			state.disabled = false;
  		} else {
  			state.value = "";
  			state.disabled = true;
  		}
  	}
  	
  	function countryChanged(state, province, country) {
    	 if (!state || !province || !country) {
    		 return;
    	 }
  		if (country.value == "") {
  			state.disabled = false;
  			province.disabled = false;
  			province.className = "textfield";
  		} else if (country.value == "US") {
  			state.disabled = false;
  			province.value = "";
  			province.disabled = true;
  			province.className = "disabledtextfield";
  		} else {
  			state.value = "";
  			state.disabled = true;
  			province.disabled = false;
  			province.className = "textfield";
  		}
  	}
      
  </SCRIPT>
 
        <html:form styleId="dirtyCheck-form" action="claim_resolution.do" method="post" enctype="multipart/form-data" onsubmit="return validateFsClaimForm(this);">
          <input type="hidden" name="delete_these_elements" value="" />
            <html:javascript formName="claimForm" />
            <tr>
              <td colspan="3" id="pageheadercell">
                <div id="pageheaderleft">
                  <logic:present name="editinterim" scope="request">
                    <h1>
                      <bean:message key="header.interim_expense_request" />
                    </h1>
                  </logic:present>
                  <logic:notPresent name="editinterim" scope="request">
                    <h1>
                      <bean:message key="header.claim_payout" />
                    </h1>
                  </logic:notPresent>
                </div>
                <div id="pageheaderright">
                  <table id="pageheaderright">
                    <tr>
	                  <% if (ntUser) { %>
                      <logic:notPresent name="editinterim" scope="request">
        				<logic:notPresent name="noincident" scope="request">
                        <td>
                          <a href="#" onclick="openReportWindow('reporting?print=<bean:write name="CLAIM_PAYOUT_RPT" scope="request"/>&outputtype=0','ExpensePayout',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"></a>
                        </td>
                        <td>
                          <a href="#" onclick="openReportWindow('reporting?print=<bean:write name="CLAIM_PAYOUT_RPT" scope="request"/>&outputtype=0','ExpensePayout',800,600);return false;"><bean:message key="link.claim_payout" /></a>
                        </td>
                		</logic:notPresent>
                      </logic:notPresent>
	                  <% } %>
                      <jsp:include page="/pages/includes/mail_incl.jsp" />
                      <td>
                        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
                      </td>
                    </tr>
                    <% if(crapFields && hasIncident){ %>
                    	<logic:notEqual name="claimForm" property="claim.id" value="0">
	                    	<logic:present name="claimForm" property="claim.ntIncident">
		                    <tr>
		                    	<td>
		                          <a href="#" onclick="openReportWindow('reporting?print=<%=ReportingConstants.CRAP_SHEET %>&outputtype=0','CRAPSheet',800,600);return false;"><img src="deployment/main/images/nettracer/icon_printrpt.gif" width="12" height="12"></a>
		                        </td>
		                        <td>
		                          <a href="#" onclick="openReportWindow('reporting?print=<%=ReportingConstants.CRAP_SHEET %>&outputtype=0','CRAPSheet',800,600);return false;"><bean:message key="link.crap_sheet" /></a>
		                        </td>
			                </tr>
			                </logic:present>
		                </logic:notEqual>
                    <%} %>
                  </table>
                </div>
              </td>
            </tr>
            
            
            <tr>
              <td colspan="3" id="navmenucell">
                <div class="menu">
                  <dl>
                  <% if (ntUser) { %>
                    	<logic:notEmpty name="claimForm" property="claim.ntIncident" >
                    <dd>
                      		<a href='searchIncident.do?incident=<bean:write name="claimForm" property="claim.ntIncident.incident_ID" />'>
                      <span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.incident_info" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                        </logic:notEmpty>
                   <% } %>
                    <logic:present name="editinterim" scope="request">
                      <dd>
                        <a href="#"><span class="aab">&nbsp;
                            <br />
                            &nbsp;</span>
                          <span class="bbb"><bean:message key="menu.interim_expense" /></span>
                          <span class="ccb">&nbsp;
                            <br />
                            &nbsp;</span></a>
                      </dd>
                    </logic:present>
                    <logic:notPresent name="editinterim" scope="request">


                      <%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_SETTLEMENT, a)) {
                        if (request.getAttribute("claimSettlementExists")==null) {
                        	String incident = (String) request.getAttribute("incident");
                        	if (incident == null) { %>
                        		<logic:notEmpty name="claimForm" property="claim.ntIncident" >
                        			<dd>
			                          <a href='claim_settlement.do?incident_ID=<bean:write name="claimForm" property="claim.ntIncident.incident_ID" />'>
			                          	<span class="aa">&nbsp;<br />&nbsp;</span>
				                        <span class="bb"><bean:message key="menu.claim_settlement" /></span>
				                        <span class="cc">&nbsp;<br />&nbsp;</span>
				                      </a>
			                        </dd>
                        		</logic:notEmpty>
                        	<% } else { %>
             
                        <dd>
                          <a href='claim_settlement.do?incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_settlement" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
							<% } 
                        	} else {
%>

                    <dd>
                       <a href='claim_settlement.do?screen=1&incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_process" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    
                    <dd>
                      <a href='claim_settlement.do?screen=2&incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_customer" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>

                    <dd>
                      <a href='claim_settlement.do?screen=3&incident_ID=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_baggage" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
<%
                      }
                    }
%>
                      <dd>
                        <a href="#"><span class="aab">&nbsp;
                            <br />
                            &nbsp;</span>
                          <span class="bbb"><bean:message key="menu.claim_payout" /></span>
                          <span class="ccb">&nbsp;
                            <br />
                            &nbsp;</span></a>
                      </dd>
                      <dd>
                      <%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, a)) {
                      	if ((ntUser && ntfsUser) || ntfsUser) { 
                      		if (((ClaimForm)session.getAttribute("claimForm")).getClaim().getId() == 0) {
	                      		if (((ClaimForm)session.getAttribute("claimForm")).getClaim().getIncident() != null && 
	                      				((ClaimForm)session.getAttribute("claimForm")).getClaim().getIncident().getId() != 0) { %>
	                      
			                   <a href='fraud_results.do?incident=<bean:write name="claimForm" property="claim.ntIncident.incident_ID" />'><span class="aa">&nbsp;<br />&nbsp;</span>
			                   	<span class="bb"><bean:message key="menu.fraud.checks" /></span>
			                        <span class="cc">&nbsp;
			                          <br />
			                          &nbsp;</span></a>
	                      		<% } 
                      		} else { %>
		                   	<a href='fraud_results.do?claimId=<bean:write name="claimForm" property="claim.id" />' ><span class="aa">&nbsp;<br />&nbsp;</span>
		                   	<span class="bb"><bean:message key="menu.fraud.checks" /></span>
	                        <span class="cc">&nbsp;<br />&nbsp;</span></a>
                      	
                      		<% } %>
	                   </dd>
                      <% }
                      }
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a) && ntUser) { %>
                      <logic:notEmpty name="claimForm" property="claim.ntIncident" >
                        <dd>
                          <a href='claim_prorate.do?incident=<bean:write name="claimForm" property="claim.ntIncident.incident_ID" />'><span class="aa">&nbsp;
                              <br />
                              &nbsp;</span>
                            <span class="bb"><bean:message key="menu.claim_prorate" /></span>
                            <span class="cc">&nbsp;
                              <br />
                              &nbsp;</span></a>
                        </dd>
                      </logic:notEmpty>
<%
                      }
                      %>

                    </logic:notPresent>
                    <logic:notEqual name="claimForm" property="claim.id" value="0">
                    <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_DEPREC_CALCULATOR, a) && (ntUser || ntfsUser)) { %>
                      <dd>
                      <a href='claim_deprec_calc.do?claim_id=<bean:write name="claimForm" property="claim.id" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_deprec_calc" /></span>
                        <span class="cc">&nbsp;
                          <br />
                          &nbsp;</span></a>
                    </dd>
                    <% } %>
                    </logic:notEqual>
                  </dl>
                </div>
              </td>
            </tr>
            
            <tr>
              
              <td id="middlecolumn">
                
                <div id="maincontent">
                  <logic:notPresent name="editinterim" scope="request">
                    <a name="claimamount"></a>
                    <h1 class="green">
                      <bean:message key="header.claim_amount" />
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages>
                      <logic:notEmpty name="validateAirline" scope="request"><bean:message key="error.airlineIncFound"/> <a href="claim_resolution.do?claimId=<bean:write name="validateAirline" scope="request"/>"><bean:write name="validateAirline" scope="request"/></a></logic:notEmpty></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td colspan="2">
                        	<bean:message key="colname.claim.id" />
                        	<br />
                        	<html:text name="claimForm" property="claim.id" size="5" styleClass="textfield" disabled="true" />
                        </td>
                        <td colspan="2">
                        	<bean:message key="colname.claim.date" />
                        	<br />
                        	<html:text property="dispCreateTime" styleClass="textfield" disabled="true" />
                        </td>
                        <td colspan="2">
                        	<bean:message key="colname.claim.create.agent" />
                        	<br />
                        	<logic:notEmpty name="claimForm" property="claim.createagent">
                        		<html:text  name="claimForm" property="claim.createagent.username" size="10" styleClass="textfield" disabled="true" />
                        	</logic:notEmpty>
                        	<logic:empty name="claimForm" property="claim.createagent">
                        		<input type="text"  value="" size="10" styleClass="textfield" disabled="true" />
                        	</logic:empty>
                        </td></tr><tr>
                      	<td colspan="4">
                      		<bean:message key="header.claim_type"/>
                      		<br />
                      		<html:select name="claimForm" property="claim.claimType" styleClass="dropdown" >
			                  <html:option value="">
			                  </html:option>
			                  <html:option value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>">
			                    <bean:message key="claim.type.lostdelay" />
			                  </html:option>
			                  <html:option value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>">
			                    <bean:message key="claim.type.missing" />
			                  </html:option>
			                  <html:option value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>">
			                    <bean:message key="claim.type.damaged" />
			                  </html:option>
			                </html:select>
                      	</td>
                        
                        <td colspan="2">
                          <bean:message key="colname.claim_status" />
                          <br />
                          <div id="tohide1">
                          <html:select property="claim.statusId" styleClass="dropdown">
                            <html:options collection="claimstatuslist" property="status_ID" labelProperty="description" />
                          </html:select>
                          </div>
                        </td>
                      </tr>
                      <tr>
                      	<td colspan="<%=crapFields?1:2%>">
                          <bean:message key="colname.claim_amount" />
                          <br />
                          <html:text property="claim.amountClaimed" size="13" maxlength="13" styleClass="textfield" />
                        </td>
 
                        <td colspan="<%=crapFields?2:4%>">
                          <bean:message key="colname.currency" />
                          <br />
                          <html:select property="claim.amountClaimedCurrency" styleClass="dropdown">
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
                        <%if (crapFields){%>
	                      	<td colspan="3">
	                          <bean:message key="colname.total_liability" />
	                          <br />
	                          <html:text property="claim.totalLiability" size="13" maxlength="13" styleClass="textfield" />
	                        </td>
                        <%} %>
                      </tr>
                      <tr>
                        <td colspan="<%=crapFields?1:2%>">
                          <bean:message key="colname.amount_paid" />
                          <br />
                          <html:text property="claim.amountPaid" size="13" maxlength="13" styleClass="textfield" />
                        </td>
                        <td colspan="<%=crapFields?2:4%>">
                          <bean:message key="colname.currency" />
                          <br />
                          <html:select property="claim.amountPaidCurrency" styleClass="dropdown">
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
                        
                        <%if (crapFields){%>
	                      	<td colspan="1">
	                          <bean:message key="colname.ix" />:
	                          <html:checkbox property="claim.ix"/>
	                        </td>
	                      	<td colspan="2">
	                          <bean:message key="colname.carry_on" />:
	                          <html:checkbox property="claim.carryon" />
	                        </td>
                        <% } %>
                        
                      <%if (crapFields){%>
	                      <tr>
	                        <td colspan="3">
	                          <bean:message key="colname.excess.value.amount" />
	                          <br />
	                          <html:text property="claim.excessValueAmt" size="13" maxlength="13" styleClass="textfield" />
	                        </td>
	                      	<td colspan="3">
	                          <bean:message key="colname.claim.check" />
	                          <br/>
	                          <html:select name="claimForm" property="claim.claimCheck" styleClass="dropdown" >
				                  <html:option value="">
				                    <bean:message key="select.please_select" />
				                  </html:option>
				                  <html:options collection="claimchecklist" property="value" labelProperty="label"/>
				                </html:select>
	                        </td>
	                      </tr>
                      <%} %>
                      <tr>
                        <td colspan="6">
                          <bean:message key="colname.reason" />
                          &nbsp;(
                          <bean:message key="colname.for_audit" />
                          )
                          <br />
                          <html:textarea property="mod_claim_reason" cols="80" rows="3" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" />
                          <input name='mod_claim_reason2' type="text" id='mod_claim_reason2' value="255" size="4" maxlength="4" disabled />
                        </td>
                      </tr>
                      <tr>
						<td colspan="6">
							<bean:message key="colname.claim_remarks" />
							<br/>
							<html:textarea  name="claimForm" property="claim.claimRemark"  cols="80" rows="10" styleClass="textfield" />
						</td>
					  </tr>
                      
                    </table>
                    <br />
                    <br />
                    <jsp:include page="/pages/includes/claim_contactinfo_incl.jsp" />
                    
                    </logic:notPresent>
                    <logic:present name="edit" scope="request">
                      <br />
                      <br />
                      <a name="editpayout"></a>
                      <h1 class="green">
                        <logic:empty name="index" scope="request">
                          <bean:message key="header.addnew_payout" />
                        </logic:empty>
                        <logic:present name="index" scope="request">
                          <bean:message key="header.edit_payout" />
                        </logic:present>
                        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                      </h1>
                      <table class="form2" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                            <bean:message key="colname.createdate" />
                            <br />
                            <html:text property="createdate" size="15" styleClass="textfield" disabled="true" />
                          </td>
                          <td>
                            <bean:message key="colname.agentusername" />
                            <br />
                            <html:text property="expcreateagent" size="15" styleClass="textfield" disabled="true" />
                          </td>
                          <td>
                            <bean:message key="colname.stationcreated_nobr" />
                            <br />
                            <html:text property="expcreatestation" size="15" styleClass="textfield" disabled="true" />
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.expense_loc" />
                            <br />
                            <logic:present name="editinterim" scope="request">
                              <html:text property="expenselocationdesc" size="15" styleClass="textfield" disabled="true" />
                            </logic:present>
                            <logic:notPresent name="editinterim" scope="request">
                              <logic:present name="index" scope="request">
                                <html:text property="expenselocationdesc" size="15" styleClass="textfield" disabled="true" />
                              </logic:present>
                              <logic:empty name="index" scope="request">
                                <html:select property="expenselocation_ID" styleClass="dropdown">
                                  <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                                </html:select>
                              </logic:empty>
                            </logic:notPresent>
                          </td>
                          <td>
                            <bean:message key="colname.expense_type" />
                            <br />

                              <logic:present name="index" scope="request">
                                <html:text property="expensetypedesc" size="15" styleClass="textfield" disabled="true" />
                              </logic:present>
                              <logic:empty name="index" scope="request">
                                <html:select property="expensetype_ID" styleClass="dropdown">
                                  <html:options collection="expensetypelist" property="expensetype_ID" labelProperty="description" />
                                </html:select>
                              </logic:empty>
                            
                          </td>
                          <td>
                          

                            <bean:message key="colname.paycode" />
                            <br />
                            <html:select property="paycode" styleClass="dropdown">
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
              
                        <tr>
                          <td colspan=3>
                            <bean:message key="colname.comments" />
                            <br />
                            <html:textarea property="comments" cols="80" rows="5" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" />
                            <input name='comments2' type="text" id='comments2' value="255" size="4" maxlength="4" disabled="true" />
                          </td>
                        </tr>
                        <logic:present name="index" scope="request">
                          <tr>
                            <td colspan=3>
                              <bean:message key="colname.reason" />
                              :(
                              <bean:message key="colname.for_audit" />
                              )
                              <br />
                              <html:textarea property="mod_exp_reason" cols="80" rows="3" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" />
                              <input name='mod_exp_reason2' type="text" id='mod_exp_reason2' value="255" size="4" maxlength="4" disabled="true" />
                            </td>
                          </tr>
                        </logic:present>
                        <tr>
                          <td align="center" valign="top" colspan=3>
                            <logic:present name="editinterim" scope="request">
                              <logic:present name="approveinterim" scope="request">
                                <html:hidden property="disclaimamount" value="" />
                                <html:submit property="saveapproveinterim" styleId="button">
                                  <bean:message key="button.approveinterim" />
                                </html:submit>
                                <html:submit property="savedenyinterim" styleId="button">
                                  <bean:message key="button.denyinterim" />
                                </html:submit>
                              </logic:present>
                              <logic:notPresent name="approveinterim" scope="request">
                                <html:hidden property="disclaimamount" value="" />
                                <html:hidden property="expensestatus_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_STATUS_PENDING %>" />
                                <html:submit property="saveinterim" styleId="button">
                                  <bean:message key="button.request_for_approval" />
                                </html:submit>
                              </logic:notPresent>
                            </logic:present>
                            <logic:notPresent name="editinterim" scope="request">
                              <html:hidden property="expensestatus_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED %>" />
                              <html:submit property="save" styleId="button">
                                <bean:message key="button.save" />
                              </html:submit>
                            </logic:notPresent>
                          </td>
                        </tr>
                      </table>
                      
                      <script language=javascript>
                        
	<logic:empty name="index" scope="request">

	document.location.href="#editpayout";
	</logic:empty>
	<logic:present name="index" scope="request">
	document.claimForm.draft.focus();
	</logic:present>

                      </script>
                    </logic:present>
                    <br />
                    <br />
                    <div style="width:100%;" >
                    <a name="an" ></a>
                    <span style="float:left;">
					<h1 class="green" >
						<bean:message key="header.associated.names" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					</h1>
					</span>
					<span style="float:right;" >
						<a id="anshow" href="#an" onClick="show('#names','#anshow','#anhide')" style="display:none;"><bean:message key="link.show" /></a>
						<a id="anhide" href="#an" onClick="hide('#names','#anshow','#anhide')" ><bean:message key="link.hide" /></a>
					</span>
					</div>
					
						<div id="names" style="margin:0;padding:0;">
					<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="p" name="claimForm" property="claim.claimants" type="aero.nettracer.fs.model.Person" >
						<% if (i > 0) { 
								Person person = (Person) p;
						%> 
				          <tr id="<%= TracingConstants.JSP_DELETE_ASSOCIATED_NAME %>_<%=i%>">
				          <td style="margin:0;padding:0;">
				          	<table class="form2" cellspacing="0" cellpadding="0" style="margin:0;">
				          	<tr>
				           	<td colspan=2>
				           		<bean:message key="colname.last_name" /><br />
				           		<input type="text" name="person[<%=i %>].lastName" maxlength="20" size="20" value="<%=person.getLastName() == null ? "" : person.getLastName() %>" class="textfield">
				           	</td>
				           	<td colspan=2>
				           		<bean:message key="colname.first_name" /><br />
				           		<input type="text" name="person[<%=i %>].firstName" maxlength="20" size="20" value="<%=person.getFirstName() == null ? "" : person.getFirstName() %>" class="textfield">
				           	</td>
				           	<td>
				           		<bean:message key="colname.mid_initial" /><br />
				           		<input type="text" name="person[<%=i %>].middleName" maxlength="1" size="1" value="<%=person.getMiddleName() == null ? "" : person.getMiddleName() %>" class="textfield">
				           	</td>
				           	
				          </tr>
				          <!-- MJS START MODIFICATION HERE -->
				          <tr>
				                <td colspan=2>
				                  <bean:message key="colname.street_addr1" />
				                  <br>
				                  <input type="text" name="person[<%=i %>].address.address1" size="45" maxlength="50" value="<%=(person.getAddress()==null || person.getAddress().getAddress1() == null) ? "" : person.getAddress().getAddress1() %>" class="textfield" />
				                </td>
				                <td colspan=3>
				                  <bean:message key="colname.street_addr2" />
				                  <br>
				                  <input type="text" name="person[<%=i %>].address.address1" size="35" maxlength="50" value="<%=(person.getAddress()==null || person.getAddress().getAddress2() == null) ? "" : person.getAddress().getAddress2() %>" class="textfield" />
				                </td>
				              </tr>
				              <tr>
				                <td>
				                  <bean:message key="colname.city" />
				                  <br>
				                  <input type="text" name="person[<%=i %>].address.city" size="15" maxlength="50" value="<%=(person.getAddress()==null || person.getAddress().getCity() == null) ? "" : person.getAddress().getCity() %>" class="textfield" />
				                </td>
				                <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                  <select name="person[<%=i %>].address.state" id="state_<%=i %>" class="dropdown" onchange="fieldChanged('state_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
				                  <% 
				                  		String state =""; 
				                  		if(person.getAddress()!=null && person.getAddress().getState()!=null){
				                  		 	state= person.getAddress().getState();
				                  		}
				                  		for (LabelValueBean bean: stateList) {
				                  %>
				                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(state)) { %>selected<% } %>><%=bean.getLabel() %></option>
				                  <% 
				                  		}
				                  %>
				                  </select>
			                  	</td>
			                  	<td>
				                  <bean:message key="colname.province" />
				                  <br />
				                  <input type="text" name="person[<%=i %>].address.province" id="province_<%=i %>" size="10" maxlength="100" value="<%=(person.getAddress()==null || person.getAddress().getProvince() == null) ? "" : person.getAddress().getProvince() %>" class="textfield" onchange="fieldChanged('province_<%=i %>');" />
				                </td>
				                <td>
				                  <bean:message key="colname.zip" />
				                  <br>
				                  <input type="text" name="person[<%=i %>].address.zip" size="11" maxlength="11" value="<%=(person.getAddress()==null || person.getAddress().getZip() == null) ? "" : person.getAddress().getZip() %>" class="textfield" />
				                </td>
				                <td>
				                  <bean:message key="colname.country" />
				                  <br />
				                  <select name="person[<%=i %>].address.country" class="dropdown" id="country_<%=i %>" onchange="fieldChanged('country_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
				                  <% 
				                  		String country = "";
					                  	if(person.getAddress()!=null && person.getAddress().getCountry()!=null){
				                  		 	country= person.getAddress().getCountry();
				                  		}
				                  		for (LabelValueBean bean: countryList) {
				                  %>
				                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(country)) { %>selected<% } %>><%=bean.getLabel() %></option>
				                  <% 
				                  		}
				                  %>
				                  </select>
			                  	</td>
			               </tr>
				           <tr>
				           		<td>
					            	<bean:message key="colname.home_ph" />
					            	<br/>
				                  	<input type="text" name="person[<%=i %>].homePhone" size="15" maxlength="25" value="<%=person.getHomePhone() == null ? "" : person.getHomePhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.business_ph" />
					            	<br/>
				                  	<input type="text" name="person[<%=i %>].workPhone" size="15" maxlength="25" value="<%=person.getWorkPhone() == null ? "" : person.getWorkPhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.mobile_ph" />
					            	<br/>
				                  	<input type="text" name="person[<%=i %>].mobilePhone" size="15" maxlength="25" value="<%=person.getMobilePhone() == null ? "" : person.getMobilePhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.pager_ph" />
					            	<br/>
				                  	<input type="text" name="person[<%=i %>].pagerPhone" size="15" maxlength="25" value="<%=person.getPagerPhone() == null ? "" : person.getPagerPhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.alt_ph" />
					            	<br/>
				                  	<input type="text" name="person[<%=i %>].alternatePhone" size="15" maxlength="25" value="<%=person.getAlternatePhone() == null ? "" : person.getAlternatePhone() %>" class="textfield" />
				              	</td>
				           </tr>
				           <tr>
								<td colspan="2">
				             		<bean:message key="colname.email" />
				             		<br />
				                  	<input type="text" name="person[<%=i %>].emailAddress" size="35" maxlength="100" value="<%=person.getEmailAddress() == null ? "" : person.getEmailAddress() %>" class="textfield" />
				             	</td>
				             	<td colspan="3">
				             		<bean:message key="colname.dob" />
				             		(<%= a.getDateformat().getFormat() %>)
				             		<br />
				                  	<input type="text" name="person[<%=i %>].disDateOfBirth" size="12" maxlength="11" value="<%=person.getDisDateOfBirth() == null ? "" : person.getDisDateOfBirth() %>" class="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar_<%=i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimForm,'person[<%=i %>].disDateOfBirth','calendar_<%=i %>','<%= a.getDateformat().getFormat() %>'); return false;">
				             	</td>
				           </tr>
				           <tr>
				           		<td colspan="2">
				             		<bean:message key="colname.airline_membership" />
				             		<br />
									<select name="person[<%=i %>].ffAirline" class="dropdown">
				                  		<option value=""><bean:message key="select.none"/></option>
				             		<%
				             			String company = person.getFfAirline();
				             			for (Company c: companyList) {
				             		%>
				                  			<option value="<%=c.getCompanyCode_ID() %>" <% if (c.getCompanyCode_ID().equals(company)) { %>selected<% } %>><%=c.getCompanydesc() %></option>
				             		<%
				             			}
				             		%>
				             		</select>
				             	</td>
				             	<td colspan="3">
				             		<bean:message key="colname.membership_number" />
				              		<br>
				                  	<input type="text" name="person[<%=i %>].ffNumber" size="30" maxlength="20" value="<%=person.getFfAirline() == null ? "" : person.getFfNumber() %>" class="textfield" />
				             	</td>
				           </tr>
				           <tr>
				           		<td colspan=2 >
					            	<bean:message key="oc.label.ssn" />
					            	<br />
				                  	<input type="text" name="person[<%=i %>].redactedSocialSecurity" size="20" maxlength="9" value="<%=person.getRedactedSocialSecurity() == null ? "" : person.getRedactedSocialSecurity() %>" class="textfield" />
					            </td>
					            <td colspan=2 >
					            	<bean:message key="colname.common_num" />
					            	<br />
				                  	<input type="text" name="person[<%=i %>].redactedPassportNumber" size="20" maxlength="20" value="<%=person.getRedactedPassportNumber() == null ? "" : person.getRedactedPassportNumber() %>" class="textfield" />
					            </td>
					            <td>
					            	<bean:message key="colname.country_of_issue" />
					            	<br />
					            	<select name="person[<%=i %>].passportIssuer" class="dropdown" >
				                  		<option value=""><bean:message key="select.none"/></option>
					                  <% 
					                  		country = person.getPassportIssuer();
					                  		for (LabelValueBean bean: countryList) {
					                  %>
					                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(country)) { %>selected<% } %>><%=bean.getLabel() %></option>
					                  <% 
					                  		}
					                  %>
					                  </select>
					            </td>
				           </tr>
				           <tr>
				           		<td>
					            	<bean:message key="colname.drivers" />
					            	<br />
				                  	<input type="text" name="person[<%=i %>].redactedDriversLicenseNumber" size="20" maxlength="20" value="<%=((Person) person).getRedactedDriversLicenseNumber() == null ? "" : ((Person) person).getRedactedDriversLicenseNumber() %>" class="textfield" />
					            </td>
					            <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                    <select name="person[<%=i %>].driversLicenseState" id="dlstate_<%=i %>" class="dropdown" onchange="fieldChanged('dlstate_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
					                  <% 
					                  		state = person.getDriversLicenseState();
					                  		for (LabelValueBean bean: stateList) {
					                  %>
					                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(state)) { %>selected<% } %>><%=bean.getLabel() %></option>
					                  <% 
					                  		}
					                  %>
				                  </select>
				                </td>
				                <td colspan=2 >
				                  <bean:message key="colname.province" />
				                  <br />
				                  	<input type="text" name="person[<%=i %>].driversLicenseProvince" id="dlprovince_<%=i %>" size="15" maxlength="100" value="<%=person.getDriversLicenseProvince() == null ? "" : person.getDriversLicenseProvince() %>" class="textfield" onchange="fieldChanged('dlprovince_<%=i %>');"/>
				                </td>
				                <td colspan=2 >
				                	<bean:message key="colname.country_of_issue" />
				                	<br>
				                	<select name="person[<%=i %>].driversLicenseCountry" id="dlcountry_<%=i %>" class="dropdown" onchange="fieldChanged('dlcountry_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
					                  <% 
					                  		country = ((Person) person).getDriversLicenseCountry();
					                  		for (LabelValueBean bean: countryList) {
					                  %>
					                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(country)) { %>selected<% } %>><%=bean.getLabel() %></option>
					                  <% 
					                  		}
					                  %>
					                  </select>
				                </td>
				           </tr>
				          <tr>
				            <td colspan=5>
				            	<input type="button" value="<bean:message key="button.delete.name" />"
				            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_ASSOCIATED_NAME %>_<%=i %>', 
				                '<bean:message key="header.associated.names" />', 0)"
				            	id="button" >
				           	</td>
				          </tr>
				          <tr>
				          	<td colspan=5>&nbsp;</td>
				          </tr>
				           </table>
				           </td>
				           </tr>
				          <!-- MJS END MODIFICATION HERE -->
				          <% } %>
				          <script>
						  	fieldChanged('state_<%=i %>');
							fieldChanged('country_<%=i %>');
							fieldChanged('dlstate_<%=i %>');
							fieldChanged('dlcountry_<%=i %>');
			              </script>
				          </logic:iterate>
				          <tr>
				          <td colspan="5" align="center">
					          <select name="addNameNum">
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
					
							    <html:submit styleId="button" property="addNames" onclick="setField('addednames');" >
						        	<bean:message key="button.add.name" />
						        </html:submit>
				          </td>
				          </tr>
					</table>
					</div>
					<% 	String showNames = (String) request.getAttribute("showNames");
						if (showNames != null && showNames.equals("true")) { %>
						<script>
				   			jQuery('#names').show();
				   			jQuery('#anshow').hide();
				   			jQuery('#anhide').show();
				   		</script>
					<% } %>
					<br />
                    <br />
                    <div style="width:100%;">
                    <a name="rs" ></a>
                    <span style="float:left;">
					<h1 class="green" >
						<bean:message key="header.associated.receipts" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					</h1>
					</span>
					<span style="float:right;" >
						<a id="rsshow" href="#rs" onClick="show('#receipts','#rsshow','#rshide')" style="display:none;"><bean:message key="link.show" /></a>
						<a id="rshide" href="#rs" onClick="hide('#receipts','#rsshow','#rshide')" ><bean:message key="link.hide" /></a>
					</span>
					</div>
					<div id="receipts"  >
					<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="r" name="claimForm" property="claim.receipts" type="aero.nettracer.fs.model.FsReceipt" >
						  <%
						  	FsReceipt receipt = (FsReceipt) r;
						  %>
				          <tr id="<%= TracingConstants.JSP_DELETE_ASSOCIATED_RECEIPT %>_<%=i%>" >
				            <td style="padding:0px;" >
				            	<table class="form2" cellspacing="0" cellpadding="0" style="padding:0px;margin:0px;" >
								  <tr>
								  	<td colspan="5">
								  		<bean:message key="colname.company.name" />
								  		<br />
								  		<input type="text" name="receipt[<%=i %>].company" maxlength="35" size="35" value="<%=receipt.getCompany() == null ? "" : receipt.getCompany() %>" class="textfield">
								  	</td>
								  </tr>
								  <tr>
					                <td colspan=2>
					                  <bean:message key="colname.street_addr1" />
					                  <br>
					                  <input type="text" name="receipt[<%=i %>].address.address1" size="40" maxlength="50" value="<%=receipt.getAddress().getAddress1() == null ? "" : receipt.getAddress().getAddress1() %>" class="textfield" />
					                </td>
					                <td colspan=3>
					                  <bean:message key="colname.street_addr2" />
					                  <br>
					                  <input type="text" name="receipt[<%=i %>].address.address2" size="35" maxlength="50" value="<%=receipt.getAddress().getAddress2() == null ? "" : receipt.getAddress().getAddress2() %>" class="textfield" />
					                </td>
					              </tr>
					              <tr>
					                <td>
					                  <bean:message key="colname.city" />
					                  <br>
					                  <input type="text" name="receipt[<%=i %>].address.city" size="15" maxlength="50" value="<%=receipt.getAddress().getCity() == null ? "" : receipt.getAddress().getCity() %>" class="textfield" />
					                </td>
					                <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                  <select name="receipt[<%=i %>].address.state" id="arstate_<%=i %>" class="dropdown" onchange="fieldChanged('arstate_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
				                  <% 
				                  		String state = receipt.getAddress().getState();
				                  		for (LabelValueBean bean: stateList) {
				                  %>
				                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(state)) { %>selected<% } %>><%=bean.getLabel() %></option>
				                  <% 
				                  		}
				                  %>
				                  </select>
			                  	</td>
			                  	<td>
				                  <bean:message key="colname.province" />
				                  <br />
				                  <input type="text" name="receipt[<%=i %>].address.province" id="arprovince_<%=i %>" size="10" maxlength="100" value="<%=receipt.getAddress().getProvince() == null ? "" : receipt.getAddress().getProvince() %>" class="textfield" onchange="fieldChanged('arprovince_<%=i %>')" />
				                </td>
				                <td>
				                  <bean:message key="colname.zip" />
				                  <br>
				                  <input type="text" name="receipt[<%=i %>].address.zip" size="11" maxlength="11" value="<%=receipt.getAddress().getZip() == null ? "" : receipt.getAddress().getZip() %>" class="textfield" />
				                </td>
				                <td>
				                  <bean:message key="colname.country" />
				                  <br />
				                  <select name="receipt[<%=i %>].address.country" class="dropdown" id="arcountry_<%=i %>" onchange="fieldChanged('arcountry_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
				                  <% 
				                  		String country = receipt.getAddress().getCountry();
				                  		for (LabelValueBean bean: countryList) {
				                  %>
				                  			<option value="<%=bean.getValue() %>" <% if (bean.getValue().equals(country)) { %>selected<% } %>><%=bean.getLabel() %></option>
				                  <% 
				                  		}
				                  %>
				                  </select>
			                  	</td>
					              </tr>
					              <tr>
              						<td colspan="2">
								  		<bean:message key="colname.company.phone" />
								  		<br />
								  		<input type="text" name="receipt[<%=i %>].phone.phoneNumber" maxlength="20" size="20" value="<%=receipt.getPhone().getPhoneNumber() == null ? "" : receipt.getPhone().getPhoneNumber() %>" class="textfield">
								  	</td>
								  	<td>
								  		<bean:message key="claim.colname.cc_type" />
								  		<br />
										<select name="receipt[<%=i %>].ccType" class="dropdown" >
											<option value=""><bean:message key="claim.cc.please.select" /></option>
											<option value="VI" <% if (receipt.getCcType().equals("VI")) { %>selected<% };%>>Visa</option>	
											<option value="CA" <% if (receipt.getCcType().equals("CA")) { %>selected<% };%>>Mastercard</option>	
											<option value="DS" <% if (receipt.getCcType().equals("DS")) { %>selected<% };%>>Discover</option>
											<option value="AX" <% if (receipt.getCcType().equals("AX")) { %>selected<% };%>>American Express</option>	
											<option value="DC" <% if (receipt.getCcType().equals("DC")) { %>selected<% };%>>Diners Club</option> 
										</select>
								  	</td>
								  	<td >
										<bean:message key="claim.colname.cc_num.last.four" />
										<br/>
								  		<input type="text" name="receipt[<%=i %>].ccLastFour" maxlength="4" size="4" value="<%=((FsReceipt) receipt).getCcLastFour() == null ? "" : ((FsReceipt) receipt).getCcLastFour() %>" class="textfield">
									</td>
									<td >
										<bean:message key="claim.colname.cc_expdate" />
										<br/>
								  		<select name="receipt[<%=i %>].ccExpMonth" class="dropdown" >
								  			<option value="0" <% if (receipt.getCcExpMonth() == 0) { %>selected<% };%>><bean:message key="claim.cc.month" /></option>
								  			<% 
								  				ArrayList<Integer> months = DateUtils.getCcMonths();
								  				for (int j = 0; j < months.size(); ++j) { %>
								  					<option value="<%=months.get(j) %>" <% if (receipt.getCcExpMonth() == months.get(j)) { %>selected<% };%>><%=months.get(j)%></option>								  					
								  			<%	} %>
								  		</select>
										&nbsp;/&nbsp;
								  		<select name="receipt[<%=i %>].ccExpYear" class="dropdown" >
								  			<option value="0" <% if (receipt.getCcExpYear() == 0) { %>selected<% };%>><bean:message key="claim.cc.year" /></option>
								  			<% 
								  				ArrayList<Integer> years = DateUtils.getCcYears();
								  				for (int j = 0; j < years.size(); ++j) { %>
								  					<option value="<%=years.get(j)%>" <% if (receipt.getCcExpYear() == years.get(j)) { %>selected<% };%>><%=years.get(j)%></option>								  					
								  			<%	} %>
								  		</select>
									</td>
              					</tr>
								  <tr>
						            <td colspan="5">
						            	<input type="button" value="<bean:message key="button.delete.receipt" />"
						            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_ASSOCIATED_RECEIPT %>_<%=i %>', 
						                '<bean:message key="header.associated.receipts" />', 0)"
						            	id="button" >
						           	</td>
								  </tr>
				            	</table>
				            </td>
				          </tr>
				          <script>
							fieldChanged('arstate_<%=i %>');
							fieldChanged('arcountry_<%=i %>');
			              </script>
				          </logic:iterate>
				          <tr>
				          <td align="center">
					          <select name="addReceiptNum">
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
							    <input type="submit" name="addReceipts" value='<bean:message key="button.add.receipt" />' id="button" onclick="setField('addedreceipts');" />
				          </td>
				          </tr>
					</table>
					</div>
					<% 	String showReceipts = (String) request.getAttribute("showReceipts");
						if (showReceipts != null && showReceipts.equals("true")) { %>
						<script>
				   			jQuery('#receipts').show();
				   			jQuery('#rsshow').hide();
				   			jQuery('#rshide').show();
				   		</script>
					<% } %>
                    <br />
                    <br />
                    
                    
                    <!-- Start show phones -->
                    <div style="width:100%;">
                    <a name="ph" ></a>
                    <span style="float:left;">
					<h1 class="green" >
						<bean:message key="header.associated.phones" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					</h1>
					</span>
					<span style="float:right;" >
						<a id="phshow" href="#ph" onClick="show('#phonesD','#phshow','#phhide')" style="display:none;"><bean:message key="link.show" /></a>
						<a id="phhide" href="#ph" onClick="hide('#phonesD','#phshow','#phhide')" ><bean:message key="link.hide" /></a>
					</span>
					</div>
					<div id="phonesD"  >
					<table class="form2" cellspacing="0" cellpadding="0">
                    					<logic:iterate indexId="i" id="phones" name="claimForm" property="claim.phones" type="aero.nettracer.fs.model.Phone" >
				          <tr id="<%= TracingConstants.JSP_DELETE_PHONE %>_<%=i%>">
				          <td>
							<bean:message key="claim.colname.phone" />
							<br/>
				                <input type="text" name="phone[<%=i %>].phoneNumber"  size="15" maxlength="25" value="<%=phones.getPhoneNumber() == null ? "" : phones.getPhoneNumber() %>" class="textfield" />
				            </td>
				            <td>
				           	<bean:message key="claim.colname.phoneAssociation" />
							<br/>
								<input type="text" name="phone[<%=i %>].association"  size="15" maxlength="25" value="<%=phones.getAssociation() == null ? "" : phones.getAssociation() %>" class="textfield" />
				            </td>
				        	 <td>
				        	 <br/>
				            	<input type="button" value="<bean:message key="button.delete.phone" />"
				            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_PHONE %>_<%=i %>', 
				                '<bean:message key="claim.colname.phoneAssociation" />', 0)"
				            	id="button" >
				           </td>
				           </tr>
				          
				          </logic:iterate>
				          <tr>
				          <td colspan="5" align="center">
					          <select name="addPhoneNum">
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
					
							    <html:submit styleId="button" property="addPhones" onclick="setField('addedphones');" >
						        	<bean:message key="button.add.phone" />
						        </html:submit>
				          </td>
				          </tr>
                    
                    </table>
                    </div>
                    
                    <% 	String showPhones = (String) request.getAttribute("showPhones");
						if (showPhones != null && showPhones.equals("true")) { %>
						<script>
				   			jQuery('#phonesD').show();
				   			jQuery('#phshow').hide();
				   			jQuery('#phhide').show();
				   		</script>
					<% } %>
                    <br />
                    <br />
                    <!-- end show phones -->
                    
                    
                    <!-- ip addresses -->
                   <div style="width:100%;">
                    <a name="aip" ></a>
                    <span style="float:left;">
					<h1 class="green" >
						<bean:message key="header.associated.ipaddress" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					</h1>
					</span>
					<span style="float:right;" >
						<a id="ipshow" href="#aip" onClick="show('#ipaddressD','#ipshow','#iphide')" style="display:none;"><bean:message key="link.show" /></a>
						<a id="iphide" href="#aip" onClick="hide('#ipaddressD','#ipshow','#iphide')" ><bean:message key="link.hide" /></a>
					</span>
					</div>
					<div id="ipaddressD"  >
	
						<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="ips" name="claimForm" property="claim.ipAddresses" type="aero.nettracer.fs.model.FsIPAddress" >
				          <tr id="<%= TracingConstants.JSP_DELETE_IP_ADDRESS %>_<%=i%>">
				          <td style="margin:0;padding:0;">
							<bean:message key="claim.colname.ipAddress" />
							<br/>
				                <input type="text" name="ipAddress[<%=i %>].ipAddress" size="15" maxlength="15" value="<%=ips.getIpAddress() == null ? "" : ips.getIpAddress() %>" class="textfield" />
				        	</td>
				            <td>
				           	<bean:message key="claim.colname.ipaddressAssociation" />
							<br/>
								<input type="text" name="ipAddress[<%=i %>].association"  size="15" maxlength="25" value="<%=ips.getAssociation() == null ? "" : ips.getAssociation() %>" class="textfield" />
				            </td>
				            <td>
				            <br/>
				            	<input type="button" value="<bean:message key="button.delete.ip" />"
				            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_IP_ADDRESS %>_<%=i %>', 
				                '<bean:message key="header.associated.ipaddress" />', 0)"
				            	id="button" >
				           </td>
				           </tr>
				          
				          </logic:iterate>
				          <tr>
				          <td colspan="3" align="center">
					          <select name="addIPNum">
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
					
							    <html:submit styleId="button" property="addIPs" onclick="setField('addedips');" >
						        	<bean:message key="button.add.ip" />
						        </html:submit>
				          </td>
				          </tr>
					</table>
	
                    </div>
                    
                    <% 	String showIpAddresses = (String) request.getAttribute("showIpAddresses");
						if (showIpAddresses != null && showIpAddresses.equals("true")) { %>
						<script>
				   			jQuery('#aip').show();
				   			jQuery('#ipshow').hide();
				   			jQuery('#iphide').show();
				   		</script>
					<% } %>
                    <br />
                    <br />
                    <!-- end ip addresses -->
                    
                    <% if (ntUser) { %>
                    <!-- Incident Summary -->
                    <h1 class="green">
                   		<bean:message key="header.incident_summary" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
                    <logic:empty name="claimForm" property="claim.ntIncident" >
	                   <table class="form2" cellspacing="0" cellpadding="0">
		                   	<tr>
		                   		<td align=center >
		                   			<!--html:text name="claimForm" property="incidentId" size="20" maxlength="20" styleClass="textfield" /-->&nbsp;&nbsp;
		                   			<input name="incidentId" size="20" maxlength="20" class="textfield" />
		                   			<html:submit styleId="button">
		                   				<bean:message key="button.loadincident" />
		                   			</html:submit>
		                   		</td>
		                   	</tr>
                   		</table>
                    </logic:empty>
                    <logic:notEmpty name="claimForm" property="claim.ntIncident" >
	                   <table class="form2" cellspacing="0" cellpadding="0">
		                   	<tr>
		                   		<td>
		                   			<bean:message key="claim.incident.number" />
		                   			<br/>
		                   			<input name="claimForm" value="<bean:write name="claimForm" property="claim.ntIncident.incident_ID" />" size="20" maxlength="20" type="text" class="textfield" />
		                   		</td>
		                   		<td>
		                   			<bean:message key="claim.incident.type" />
		                   			<br/>
		                   			
		                   			<html:select name="claimForm" property="claim.ntIncident.itemtype_ID" styleClass="dropdown" >
			                            <html:option value="">
			                              <bean:message key="select.please_select" />
			                            </html:option>
			                            <html:option value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>">
			                            	<bean:message key="claim.type.lostdelay" />
			                            </html:option>
			                            <html:option value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>">
			                            	<bean:message key="claim.type.missing" />
			                            </html:option>
			                            <html:option value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>">
			                            	<bean:message key="claim.type.damaged" />
			                            </html:option>
			                        </html:select>
		                   		</td>
		                   		<td colspan="2">
		                   			<bean:message key="claim.incident.date" />
		                   			<br/>
		                   			<html:text name="claimForm" property="claim.ntIncident.displaydate" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar" name="itcalendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, 'theitinerary.disdepartdate','itcalendar','<%= a.getDateformat().getFormat() %>'); return false;">
		                   		</td>
		                   		
		                   	</tr>
		                   	
                   		</table>
	                    </logic:notEmpty> 
                    <% } else { %>
                    	<!-- INCIDENT INFO FOR FRAUD ONLY CUSTOMERS -->
                    	<h1 class="green">
                   			<bean:message key="header.incident_summary" />
							<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    	</h1>
                    	<table class="form2" cellspacing="0" cellpadding="0">
		                   	<tr>
		                   		<td>
		                   			<bean:message key="claim.incident.number" />
		                   			<br/>
		                   			<% String incident_ID = ((ClaimForm)session.getAttribute("claimForm")).getClaim().getIncident().getAirlineIncidentId(); %>
		                   			<input type="text" name="claim.incident.airlineIncidentId" size="20" maxlength="13" value="<%=incident_ID != null ? incident_ID : "" %>" class="textfield" />
		                   		</td>
		                   		<td>
		                   			<bean:message key="claim.incident.type" />
		                   			<br/>
		                   			
		                   			<html:select name="claimForm" property="claim.incident.incidentType" styleClass="dropdown" >
			                            <html:option value="">
			                              <bean:message key="select.please_select" />
			                            </html:option>
			                            <html:option value="<%= String.valueOf(TracingConstants.LOST_DELAY) %>">
			                            	<bean:message key="claim.type.lostdelay" />
			                            </html:option>
			                            <html:option value="<%= String.valueOf(TracingConstants.MISSING_ARTICLES) %>">
			                            	<bean:message key="claim.type.missing" />
			                            </html:option>
			                            <html:option value="<%= String.valueOf(TracingConstants.DAMAGED_BAG) %>">
			                            	<bean:message key="claim.type.damaged" />
			                            </html:option>
			                        </html:select>
		                   		</td>
		                   	</tr>
                   		</table>
                    <% } %>
                    <br />
                    <br/>
                    <!-- Reservation Info -->
                    <h1 class="green">
                   		<bean:message key="header.reservation.details" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                    </h1>
					<table class="form2" cellpadding="0" cellspacing="0">
					<tr>
						<td colspan=2>
							<bean:message key="colname.last_name" />
							<br/>
							<html:text name="claimForm" property="claim.file.incident.reservation.purchaser.lastName" size="20" maxlength="20" styleClass="textfield" />
						</td>
						<td colspan=2>
							<bean:message key="colname.first_name" />
							<br/>
							<html:text name="claimForm" property="claim.file.incident.reservation.purchaser.firstName" size="20" maxlength="20" styleClass="textfield" />
						</td>
						<td>
			              <bean:message key="colname.mid_initial" />
			              <br>
			              <html:text name="claimForm" property="claim.file.incident.reservation.purchaser.middleName" size="1" maxlength="1" styleClass="textfield" />
			            </td>
					</tr>
					<!-- START RESERVATION ADDRESS INFO -->
					<tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.address1" size="40" maxlength="50" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.address2" size="35" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.city" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state.req" />
                  <br />
                  <logic:equal name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="US">
                    <html:select name="claimForm" property="claim.file.incident.reservation.billingAddress.state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'file.incident.reservation.billingAddress.country', 'file.incident.reservation.billingAddress.province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="">
                    <html:select name="claimForm" property="claim.file.incident.reservation.billingAddress.state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'file.incident.reservation.billingAddress.country', 'file.incident.reservation.billingAddress.province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="">
                    <logic:notEqual name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="US">
                      <html:select name="claimForm" property="claim.file.incident.reservation.billingAddress.state" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'file.incident.reservation.billingAddress.country', 'file.incident.reservation.billingAddress.province');" >
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
                      <logic:equal name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="US">
                  		<html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="">
                  <html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.province" size="15" maxlength="100" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="">
                        <logic:notEqual name="claimForm" property="claim.file.incident.reservation.billingAddress.country" value="US">
                  <html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.province" size="15" maxlength="100" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text name="claimForm" property="claim.file.incident.reservation.billingAddress.zip" size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="claimForm" property="claim.file.incident.reservation.billingAddress.country" styleClass="dropdown" onchange="checkstate(this,this.form,'file.incident.reservation.billingAddress.state', 'file.incident.reservation.billingAddress.province');">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
             <tr>
             	<td colspan="5">
             		<bean:message key="colname.email" />
             		<br />
             		<html:text name="claimForm" property="claim.file.incident.reservation.purchaser.emailAddress" size="35" maxlength="100" styleClass="textfield" />
             	</td>
             </tr>
					<!-- END RESERVATION ADDRESS INFO -->
					<tr>
						<td colspan=2>
							<bean:message key="claim.colname.payment.type" />
							<br />
							<html:select property="claim.file.incident.reservation.formOfPayment" styleClass="dropdown" >
								<html:option value=""><bean:message key="payment.type.please.select" /></html:option>
								<html:option value="0"><bean:message key="payment.type.cash" /></html:option>
								<html:option value="1"><bean:message key="payment.type.check" /></html:option>
								<html:option value="2"><bean:message key="payment.type.cc" /></html:option>
								<html:option value="3"><bean:message key="payment.type.inv.reroute" /></html:option>
								<html:option value="4"><bean:message key="payment.type.universal" /></html:option>
								<html:option value="5"><bean:message key="payment.type.misc" /></html:option>
								<html:option value="6"><bean:message key="payment.type.other.cc" /></html:option>
								<html:option value="7"><bean:message key="payment.type.other" /></html:option>
							</html:select>
                        </td>
						<td colspan=2>
                          <bean:message key="claim.colname.amount.paid" />
                          <br />
                          <html:text property="claim.file.incident.reservation.ticketAmount" size="13" maxlength="13" styleClass="textfield" />
                        </td>
                        <td>
                          <bean:message key="colname.currency" />
                          <br />
                          <html:select property="claim.amountClaimedCurrency" styleClass="dropdown">
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
					</tr>
					<tr>
						<td colspan=2>
							<bean:message key="claim.colname.cc_type" />
							<br/>
							<html:select name="claimForm" property="claim.file.incident.reservation.ccType" styleClass="dropdown" >
								<html:option value="">
									<bean:message key="claim.cc.please.select" />
								</html:option>
								<html:option value="VI">Visa</html:option>	
								<html:option value="CA">Mastercard</html:option>	
								<html:option value="DS">Discover</html:option>
								<html:option value="AX">American Express</html:option>	
								<html:option value="DC">Diners Club</html:option>
							</html:select>
						</td>
						<td>
							<bean:message key="claim.colname.cc_num" />
							<br/>
							<html:text name="claimForm" property="claim.file.incident.reservation.redactedCcNumber" size="10" maxlength="6" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="claim.colname.cc_num.last.four" />
							<br/>
							<html:text name="claimForm" property="claim.file.incident.reservation.ccNumLastFour" size="10" maxlength="4" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="claim.colname.cc_expdate" />
							<br/>
							<html:select name="claimForm" property="claim.file.incident.reservation.ccExpMonth" styleClass="dropdown" >
								<html:option value="">
									<bean:message key="claim.cc.month" />
								</html:option>
								<html:options property="ccMonths" />
							</html:select>
							&nbsp;
							<html:select name="claimForm" property="claim.file.incident.reservation.ccExpYear" styleClass="dropdown" >
								<html:option value="">
									<bean:message key="claim.cc.year" />
								</html:option>
								<html:options property="ccYears"/>
							</html:select>
							
						</td>
					</tr>
					
					<tr>
						<td colspan="5">
							<bean:message key="header.reservation.info" />
							<br />
							<html:textarea name="claimForm" property="claim.file.incident.reservation.pnrData.pnrData" cols="80" rows="10" /> 
						</td>
					</tr>
					</table>
					
                    <!-- segments -->
                   <div style="width:100%;">
                    <a name="aseg" ></a>
                    <span style="float:left;">
					<h1 class="green" >
						<bean:message key="header.associated.segment" />
						<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_claim_payment.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					</h1>
					</span>
					<span style="float:right;" >
						<a id="segshow" href="#aseg" onClick="show('#segmentD','#segshow','#seghide')" style="display:none;"><bean:message key="link.show" /></a>
						<a id="seghide" href="#aseg" onClick="hide('#segmentD','#segshow','#seghide')" ><bean:message key="link.hide" /></a>
					</span>
					</div>
					<div id="segmentD"  >
	
						<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="seg" name="claimForm" property="claim.segments" type="aero.nettracer.fs.model.Segment" >
				          <tr id="<%= TracingConstants.JSP_DELETE_SEGMENT %>_<%=i%>">
				          <td style="margin:0;padding:0;">
							<bean:message key="claim.colname.segment.depart" />
							<br/>
				                <input type="text" name="segment[<%=i %>].departure" size="8" maxlength="3" value="<%=seg.getDeparture() == null ? "" : seg.getDeparture() %>" class="textfield" />
				        	</td>
				            <td>
				           	<bean:message key="claim.colname.segment.arrive" />
							<br/>
								<input type="text" name="segment[<%=i %>].arrival"  size="8" maxlength="3" value="<%=seg.getArrival() == null ? "" : seg.getArrival() %>" class="textfield" />
				            </td>
				            <td>
				           	<bean:message key="claim.colname.segment.date" />
				             (<%= a.getDateformat().getFormat() %>)
				             <br />
				                <input type="text" name="segment[<%=i %>].disDate" size="12" maxlength="11" value="<%=seg.getDisDate() == null ? "" : seg.getDisDate() %>" class="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar_<%=i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimForm,'segment[<%=i %>].disDate','calendar_<%=i %>','<%= a.getDateformat().getFormat() %>'); return false;">
				            </td>
				            <td>
				            <br/>
				            	<input type="button" value="<bean:message key="button.delete.segment" />"
				            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_SEGMENT %>_<%=i %>', 
				                '<bean:message key="header.associated.segment" />', 0)"
				            	id="button" >
				           </td>
				           </tr>
				          
				          </logic:iterate>
				          <tr>
				          <td colspan="4" align="center">
					          <select name="addSegNum">
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
					
							    <html:submit styleId="button" property="addSegs" onclick="setField('addedsegs');" >
						        	<bean:message key="button.add.seg" />
						        </html:submit>
				          </td>
				          </tr>
					</table>
	
                    </div>
                    
                    <% 	String showSegments = (String) request.getAttribute("showSegments");
						if (showSegments != null && showSegments.equals("true")) { %>
						<script>
				   			jQuery('#aseg').show();
				   			jQuery('#segshow').hide();
				   			jQuery('#seghide').show();
				   		</script>
					<% } %>
                    <br />
                    <br />
                    <!-- end segments -->
					
					<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SHARED_ATTACHMENTS, a)) { %>
					 <h1 class="green">
                   		<bean:message key="header.shared.files" />
					 </h1>
					<table class="form2" cellpadding="0" cellspacing="0">
				              <tr>
				                <td colspan="3">
				                  <bean:message key="header.attachments" />
				                  :
				                  <br>
				                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
				                  	  <tr align="center">
											
											  <td align="left" width="75%">
					                           	<bean:message key="colname.attachname"/>
					                          </td>
				                          	  <td align="left">
					                            <bean:message key="colname.action"/>
					                          </td>
				                          
				                          </tr>
				                 	  <logic:iterate indexId="i" id="attachment" name="claimForm" property="claim.attachments" type="aero.nettracer.fs.model.Attachment" >
										  
					  				      <tr align="center">
											
											  <td align="left">
					                           	<a href='retrieveAttachment?ID=<bean:write name="attachment" property="attachment_id"/>' target="top"><bean:write name="attachment" property="description"/></a>
					                          </td>
				                          	  <td align="left">
					                            <input type="submit" name="removeAttachment_<%= i %>" id="button" value="<bean:message key="button.delete_attachment"/>">
					                          </td>
				                          
				                          </tr>
										
				                      </logic:iterate>
				                  
				                </table>
				                <br>
				                <center><input type="FILE" name='<%= "attachfile" %>' />
				                &nbsp;
				                <html:submit property="uploadAttachment" styleId="button" onclick="setField('addedattachments');" >
				                  <bean:message key="header.addAttachments" />
				                </html:submit> </center>
				              </td>
				            </tr>
			            </table>
						<%
				          }
						%>
					
					
                    <center>
                    <html:submit property="save" styleId="button">
                      <bean:message key="button.save" />
                    </html:submit>
                    <logic:notEmpty name="back" scope="request" >
                    	&nbsp;&nbsp;
	            		<input id="button" type="button" value='<bean:message key="claim.button.back" />' onClick="history.back()">
                    </logic:notEmpty>
                    </center>
                    <input type="hidden" name="showNames" id="#names" value="<%=request.getAttribute("showNames") %>" />
                    <input type="hidden" name="showReceipts" id="#receipts" value="<%=request.getAttribute("showReceipts") %>" />
                    <input type="hidden" name="showPhones" id="#phonesD" value="<%=request.getAttribute("showPhones") %>" />
                    <input type="hidden" name="showIpAddresses" id="#ipaddressD" value="<%=request.getAttribute("showIpAddresses") %>" />
                    <input type="hidden" name="showSegments" id="#segmentD" value="<%=request.getAttribute("showSegments") %>" />
                    <input type="hidden" id="addednames" value="0" />
                    <input type="hidden" id="addedreceipts" value="0" />
                    <input type="hidden" id="addedips" value="0" />
                    <input type="hidden" id="addedsegs" value="0" />
                    <input type="hidden" id="addedattachments" value="0" />
                    <input type="hidden" id="addedphones" value="0" />
                    <script language="javascript">
						<logic:present name="an" scope="request">
							document.location.href="#an";
						</logic:present>
						
						<logic:present name="rs" scope="request">
							document.location.href="#rs";
						</logic:present>
						
						<logic:present name="aip" scope="request">
							document.location.href="#aip";
						</logic:present>
						
						<logic:present name="ph" scope="request">
							document.location.href="#ph";
						</logic:present>
						
						<logic:present name="aseg" scope="request">
							document.location.href="#aseg";
						</logic:present>
				    </script>
                  </html:form>
					