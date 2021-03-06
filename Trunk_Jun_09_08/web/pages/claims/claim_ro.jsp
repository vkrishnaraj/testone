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
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Company" %>
<%@ page import="java.util.ArrayList" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  ArrayList<LabelValueBean> stateList = (ArrayList<LabelValueBean>) session.getAttribute("statelist");
  ArrayList<Company> companyList = (ArrayList<Company>) session.getAttribute("companylistByName");
  ArrayList<LabelValueBean> countryList = (ArrayList<LabelValueBean>) session.getAttribute("countrylist");
	
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  int report_type = 0;
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

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
 		
       <logic:iterate name="matchClaims" scope="request" type="aero.nettracer.fs.model.FsClaim" id="matchClaim">
          <html:form action="claim_resolution.do" method="post" onsubmit="return validateFsClaimForm(this);">
          <input type="hidden" disabled="true"  name="delete_these_elements" value="" />
            <html:javascript formName="claimForm" />
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
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>
                        	<bean:message key="colname.claim.id" />
                        	<br />
                        	<html:text name="matchClaim" property="swapId" size="5" styleClass="textfield" disabled="true" />
                        </td>
                        <td>
                        	<bean:message key="colname.claim.date" />
                        	<br />
                        	<input type="text" disabled="true" value="<%=matchClaim.getDisClaimDate(a.getDateformat().getFormat()) %>" class="disabledtextfield"  />
                        </td>
                      	<td>
                      		<bean:message key="header.claim_type"/>
                      		<br />
                      		<html:select name="matchClaim" property="claimType" styleClass="dropdown" disabled="true" >
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
                        
                        <td>
                          <bean:message key="colname.claim_status" />
                          <br />
                          <div id="tohide1">
                          <html:select name="matchClaim" property="statusId" styleClass="dropdown" disabled="true" >
                            <html:options collection="claimstatuslist" property="status_ID" labelProperty="description" />
                          </html:select>
                          </div>
                        </td>
                      </tr>
                      <tr>
                      	<td colspan="2">
                          <bean:message key="colname.claim_amount" />
                          <br />
                          <html:text name="matchClaim" property="amountClaimed" size="13" maxlength="13" styleClass="textfield" disabled="true" />
                        </td>
 
                        <td colspan=2>
                          <bean:message key="colname.currency" />
                          <br />
                          <html:select  name="matchClaim" property="amountClaimedCurrency" styleClass="dropdown" disabled="true" >
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
                      </tr>
                      <tr>
                        <td colspan="2">
                          <bean:message key="colname.amount_paid" />
                          <br />
                          <html:text name="matchClaim" property="amountPaid" size="13" maxlength="13" styleClass="textfield" disabled="true" />
                        </td>
                        <td colspan=2>
                          <bean:message key="colname.currency" />
                          <br />
                          <html:select name="matchClaim" property="amountPaidCurrency" styleClass="dropdown" disabled="true" >
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
                      <tr>
                        <td colspan=4>
                          <bean:message key="colname.reason" />
                          &nbsp;(
                          <bean:message key="colname.for_audit" />
                          )
                          <br />
                          <html:textarea property="mod_claim_reason" cols="80" rows="3" onkeydown="textCounter(this,this,255);" disabled="true" onkeyup="textCounter(this,this,255);" />
                          <input name='mod_claim_reason2' type="text" id='mod_claim_reason2' value="255" size="4" maxlength="4" disabled />
                        </td>
                      </tr>
                      <tr>
							<td colspan=4>
								<bean:message key="colname.claim_remarks" />
								<br/>
								<html:textarea  name="matchClaim" property="claimRemark"  cols="80" rows="6" styleClass="textfield" disabled="true"/>
							</td>
						</tr>
                      
                    </table>
                    <br />
                    <br />
                    <!-- CLAIMANT DETAILS -->
                    <a name="contact"></a>
					  <h1 class="green">
					    <bean:message key="header.claimant.details" />
					<%
					    if (report_type == 0) {
					%>
					      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_passenger_info.htm');return false;"><%
					      } else if (report_type == 1) {
					%>
					        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_passenger_information_(ld).htm');return false;"><%
					        } else if (report_type == 2) {
					%>
					          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_passenger_information_(ma).htm');return false;"><%
					          }
					%>
					          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					      </h1>
					      <span class="reqfield">*</span>
					      <bean:message key="Required" />
					        <table class="form2" cellspacing="0" cellpadding="0">
					          <tr>
					            <td nowrap colspan="2" >
					              <bean:message key="colname.last_name.req" />
					              <br>
					              <html:text name="matchClaim" property="claimant.lastName" size="20" maxlength="20" styleClass="textfield" disabled="true" />
					            </td>
					            <td nowrap colspan="2">
					              <bean:message key="colname.first_name.req" />
					              <br>
					              <html:text name="matchClaim" property="claimant.firstName" size="20" maxlength="20" styleClass="textfield" disabled="true" />
					            </td>
					            <td>
					              <bean:message key="colname.mid_initial" />
					              <br>
					              <html:text name="matchClaim" property="claimant.middleName" size="1" maxlength="1" styleClass="textfield" disabled="true" />
					            </td>
					           
					          </tr>
					         
					          <!-- logic:present name="incident.passengers" property="addresses" -->
					            <logic:iterate indexId="i" id="address" name="matchClaim"  property="claimant.addresses" type="aero.nettracer.fs.model.FsAddress">
					              <tr>
					                <td colspan=2>
					                  <bean:message key="colname.street_addr1.req" />
					                  <br>
					                  <html:text name="address" property="address1" size="45" maxlength="50" styleClass="textfield" disabled="true" />
					                </td>
					                <td colspan=3>
					                  <bean:message key="colname.street_addr2" />
					                  <br>
					                  <html:text name="address" property="address2" size="35" maxlength="50" styleClass="textfield" disabled="true" />
					                </td>
					              </tr>
					              <tr>
					                <td>
					                  <bean:message key="colname.city.req" />
					                  <br>
					                  <html:text name="address" property="city" size="15" maxlength="50" styleClass="textfield" disabled="true" />
					                </td>
					                <td>
					                  <bean:message key="colname.state.req" />
					                  <br />
					                  <logic:equal name="address" property="country" value="US">
					                    <html:select name="address" property="state" styleClass="dropdown" disabled="true"  onchange="updateCountryUS(this, this.form, 'country', 'province');" >
					                      <html:option value="">
					                        <bean:message key="select.none" />
					                      </html:option>
					                      <html:options collection="statelist" property="value" labelProperty="label" />
					                    </html:select>
					                  </logic:equal>
					                  <logic:equal name="address" property="country" value="">
					                    <html:select name="address" property="state" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
					                      <html:option value="">
					                        <bean:message key="select.none" />
					                      </html:option>
					                      <html:options collection="statelist" property="value" labelProperty="label" />
					                    </html:select>
					                  </logic:equal>
					                  <logic:notEqual name="address" property="country" value="">
					                    <logic:notEqual name="address" property="country" value="US">
					                      <html:select name="address" property="state" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
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
					                      <logic:equal name="address" property="country" value="US">
					                  		<html:text name="address" property="province" size="15" maxlength="100" disabled="true" styleClass="disabledtextfield" disabled="true" />
					                      </logic:equal>
					                      <logic:equal name="address" property="country" value="">
					                  <html:text name="address" property="province" size="15" maxlength="100" disabled="true" styleClass="textfield" />
					                      </logic:equal>
					                      <logic:notEqual name="address" property="country" value="">
					                        <logic:notEqual name="address" property="country" value="US">
					                  <html:text name="address" property="province" size="15" maxlength="100" disabled="true" styleClass="textfield" />
					                         </logic:notEqual>
					                      </logic:notEqual>
					                </td>
					                <td>
					                  <bean:message key="colname.zip.req" />
					                  <br>
					                  <html:text name="address" property="zip" size="15" maxlength="11" styleClass="textfield" disabled="true" />
					                </td>
					                <td>
					                  <bean:message key="colname.country" />
					                  <br>
					                  <html:select name="address" property="country" styleClass="dropdown" disabled="true" onchange="checkstate(this,this.form,'state', 'province');">
					                    <html:option value="">
					                      <bean:message key="select.none" />
					                    </html:option>
					                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
					                  </html:select>
					                </td>
					              </tr>
					              </logic:iterate>
					              <tr>
					              	<td>
						            	<bean:message key="colname.home_ph" />
						            	<br/>
							            <html:text name="matchClaim" property="claimant.homePhone" size="15" maxlength="25" styleClass="textfield" disabled="true" />
					              	</td>
					              	<td>
						            	<bean:message key="colname.business_ph" />
						            	<br/>
							            <html:text name="matchClaim" property="claimant.workPhone" size="15" maxlength="25" styleClass="textfield" disabled="true" />
					              	</td>
					              	<td>
						            	<bean:message key="colname.mobile_ph" />
						            	<br/>
							            <html:text name="matchClaim" property="claimant.mobilePhone" size="15" maxlength="25" styleClass="textfield" disabled="true" />
					              	</td>
					              	<td>
						            	<bean:message key="colname.pager_ph" />
						            	<br/>
							            <html:text name="matchClaim" property="claimant.pagerPhone" size="15" maxlength="25" styleClass="textfield" disabled="true" />
					              	</td>
					              	<td>
						            	<bean:message key="colname.alt_ph" />
						            	<br/>
							            <html:text name="matchClaim" property="claimant.alternatePhone" size="15" maxlength="25" styleClass="textfield" disabled="true" />
					              	</td>
					             </tr>
					             <tr>
					             	<td colspan="2">
					             		<bean:message key="colname.email" />
					             		<br />
					             		<html:text name="matchClaim" property="claimant.emailAddress" size="35" maxlength="100" styleClass="textfield" disabled="true" />
					             	</td>
					             	<td colspan="3">
					             		<bean:message key="colname.dob" />
					             		(<%= a.getDateformat().getFormat() %>)
					             		<br />
					             		<html:text name="matchClaim" property="claimant.disDateOfBirth" size="12" maxlength="11" styleClass="textfield" disabled="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.claimForm.claimantDateOfBirth,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
					             		
					             	</td>
					             </tr>
					             <tr>
					             	<td colspan="2">
					             		<bean:message key="colname.airline_membership" />
					             		<br />
					             		<html:select name="matchClaim" property="claimant.ffAirline" styleClass="dropdown" disabled="true" >
					                		<html:option value="">
					                  			<bean:message key="select.none" />
					                		</html:option>
					               			<html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
					              		</html:select>
					             	</td>
					             	<td colspan="3">
					             		<bean:message key="colname.membership_number" />
					              		<br>
					             		<html:text name="matchClaim" property="claimant.ffNumber" size="30" maxlength="20" styleClass="textfield" disabled="true"  />
					             	</td>
					             </tr>
					              <tr>        
					                 <td colspan=2 >
						            	<bean:message key="oc.label.ssn" />
						            	<br />
						            	<html:text name="matchClaim" property="claimant.redactedSocialSecurity" size="20" maxlength="9" styleClass="textfield" disabled="true" />
						            </td>
						            <td colspan=2 >
						            	<bean:message key="colname.common_num" />
						            	<br />
						            	<html:text name="matchClaim" property="claimant.redactedPassportNumber" size="20" maxlength="20" styleClass="textfield" disabled="true" />
						            </td>
						            <td>
						            	<bean:message key="colname.country_of_issue" />
						            	<br />
						            	<html:select name="matchClaim" property="claimant.passportIssuer" styleClass="dropdown" disabled="true" >
					                    <html:option value="">
					                      <bean:message key="select.none" />
					                    </html:option>
					                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
					                  </html:select>
						            </td>
					              </tr>
					              <tr>
					               <td>
						            	<bean:message key="colname.drivers" />
						            	<br />
						            	<html:text name="matchClaim" property="claimant.redactedDriversLicenseNumber" size="20" maxlength="20" styleClass="textfield" disabled="true" />
						            </td>
						            <td>
					                  <bean:message key="colname.state" />
					                  <br />
					                    <html:select name="matchClaim" property="claimant.driversLicenseState" styleClass="dropdown" styleId="dlState" disabled="true" >
					                      <html:option value="">
					                        <bean:message key="select.none" />
					                      </html:option>
					                      <html:options collection="statelist" property="value" labelProperty="label" />
					                    </html:select>
					                </td>
					                <td colspan=2 >
					                  <bean:message key="colname.province" />
					                  <br />
					                  <html:text name="matchClaim" property="claimant.driversLicenseProvince" size="15" maxlength="100" styleClass="textfield" styleId="dlProvince" disabled="true" />
					                </td>
					                <td colspan=2 >
					                	<bean:message key="colname.country_of_issue" />
					                	<br>
					                	<html:select name="matchClaim" property="claimant.driversLicenseCountry" disabled="true" styleId="dlCountry" styleClass="dropdown" >
					                		<html:option value="">
						                        <bean:message key="select.none" />
						                    </html:option>
						                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
					                	</html:select>
					                </td>
					              </tr>
					              <script>	              		
					              </script>
					            <!--/logic:iterate-->
					           	 
					          <!--/logic:present-->
					          <!--tr>
					            <td colspan=5>
					            </td>
					          </tr-->
					        </table>
                    
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
                                <html:select property="expenselocation_ID" styleClass="dropdown" disabled="true" >
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
                                <html:select property="expensetype_ID" styleClass="dropdown" disabled="true" >
                                  <html:options collection="expensetypelist" property="expensetype_ID" labelProperty="description" />
                                </html:select>
                              </logic:empty>
                            
                          </td>
                          <td>
                          

                            <bean:message key="colname.paycode" />
                            <br />
                            <html:select property="paycode" styleClass="dropdown" disabled="true" >
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
                            <html:textarea property="comments" cols="80" rows="5" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" disabled="true" />
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
                              <html:textarea property="mod_exp_reason" cols="80" rows="3" onkeydown="textCounter(this,this,255);" onkeyup="textCounter(this,this,255);" disabled="true" />
                              <input name='mod_exp_reason2' type="text" id='mod_exp_reason2' value="255" size="4" maxlength="4" disabled="true" />
                            </td>
                          </tr>
                        </logic:present>
                        
                        <tr>
                          <td align="center" valign="top" colspan=3>
                            <logic:present name="editinterim" scope="request">
                              <logic:present name="approveinterim" scope="request">
                                <html:hidden property="disclaimamount" value="" />
                                <!--<html:submit property="saveapproveinterim" styleId="button">
                                  <bean:message key="button.approveinterim" />
                                </html:submit>
                                <html:submit property="savedenyinterim" styleId="button">
                                  <bean:message key="button.denyinterim" />
                                </html:submit>-->
                              </logic:present>
                              <logic:notPresent name="approveinterim" scope="request">
                                <html:hidden property="disclaimamount" value="" />
                                <html:hidden property="expensestatus_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_STATUS_PENDING %>" />
                                <!--<html:submit property="saveinterim" styleId="button">
                                  <bean:message key="button.request_for_approval" />
                                </html:submit>-->
                              </logic:notPresent>
                            </logic:present>
                            <logic:notPresent name="editinterim" scope="request">
                              <html:hidden property="expensestatus_ID" value="<%= "" + TracingConstants.EXPENSEPAYOUT_STATUS_APPROVED %>" />
                             <!-- <html:submit property="save" styleId="button">
                                <bean:message key="button.save" />
                              </html:submit>-->
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
					<!--<span style="float:right;" >
						<a id="anshow" href="#an" onClick="show('#names','#anshow','#anhide')"><bean:message key="link.show" /></a>
						<a id="anhide" href="#an" onClick="hide('#names','#anshow','#anhide')" style="display:none;" ><bean:message key="link.hide" /></a>
					</span>-->
					</div>
					
						<div id="names" style="margin:0;padding:0;">
					<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="p" name="matchClaim" property="claimants" type="aero.nettracer.fs.model.Person" >
						<% if (i > 0) { 
								Person person = (Person) p;
						%> 
				          <tr id="<%= TracingConstants.JSP_DELETE_ASSOCIATED_NAME %>_<%=i%>">
				          <td style="margin:0;padding:0;">
				          	<table class="form2" cellspacing="0" cellpadding="0" style="margin:0;">
				          	<tr>
				           	<td colspan=2>
				           		<bean:message key="colname.last_name" /><br />
				           		<input type="text"  disabled="true" name="person[<%=i %>].lastName" maxlength="20" size="20" value="<%=person.getLastName() == null ? "" : person.getLastName() %>" class="textfield">
				           	</td>
				           	<td colspan=2>
				           		<bean:message key="colname.first_name" /><br />
				           		<input type="text" disabled="true" name="person[<%=i %>].firstName" maxlength="20" size="20" value="<%=person.getFirstName() == null ? "" : person.getFirstName() %>" class="textfield">
				           	</td>
				           	<td>
				           		<bean:message key="colname.mid_initial" /><br />
				           		<input type="text" disabled="true" name="person[<%=i %>].middleName" maxlength="1" size="1" value="<%=person.getMiddleName() == null ? "" : person.getMiddleName() %>" class="textfield">
				           	</td>
				           	
				          </tr>
				          <!-- MJS START MODIFICATION HERE -->
				          <tr>
				                <td colspan=2>
				                  <bean:message key="colname.street_addr1" />
				                  <br>
				                  <input type="text" disabled="true" name="person[<%=i %>].address.address1" size="45" maxlength="50" value="<%=person.getAddress().getAddress1() == null ? "" : person.getAddress().getAddress1() %>" class="textfield" />
				                </td>
				                <td colspan=3>
				                  <bean:message key="colname.street_addr2" />
				                  <br>
				                  <input type="text" disabled="true" name="person[<%=i %>].address.address1" size="35" maxlength="50" value="<%=person.getAddress().getAddress2() == null ? "" : person.getAddress().getAddress2() %>" class="textfield" />
				                </td>
				              </tr>
				              <tr>
				                <td>
				                  <bean:message key="colname.city" />
				                  <br>
				                  <input type="text" disabled="true" name="person[<%=i %>].address.city" size="15" maxlength="50" value="<%=person.getAddress().getCity() == null ? "" : person.getAddress().getCity() %>" class="textfield" />
				                </td>
				                <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                  <select name="person[<%=i %>].address.state" id="state_<%=i %>" class="dropdown" disabled="true" onchange="fieldChanged('state_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
				                  <% 
				                  		String state = person.getAddress().getState();
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
				                  <input type="text" disabled="true" name="person[<%=i %>].address.province" id="province_<%=i %>" size="10" maxlength="100" value="<%=person.getAddress().getProvince() == null ? "" : person.getAddress().getProvince() %>" class="textfield" onchange="fieldChanged('province_<%=i %>');" />
				                </td>
				                <td>
				                  <bean:message key="colname.zip" />
				                  <br>
				                  <input type="text" disabled="true" name="person[<%=i %>].address.zip" size="11" maxlength="11" value="<%=person.getAddress().getZip() == null ? "" : person.getAddress().getZip() %>" class="textfield" />
				                </td>
				                <td>
				                  <bean:message key="colname.country" />
				                  <br />
				                  <select name="person[<%=i %>].address.country" class="dropdown" id="country_<%=i %>" onchange="fieldChanged('country_<%=i %>');">
				                  		<option value=""><bean:message key="select.none"/></option>
				                  <% 
				                  		String country = person.getAddress().getCountry();
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
				                  	<input type="text" disabled="true" name="person[<%=i %>].homePhone" size="15" maxlength="25" value="<%=person.getHomePhone() == null ? "" : person.getHomePhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.business_ph" />
					            	<br/>
				                  	<input type="text" disabled="true" name="person[<%=i %>].workPhone" size="15" maxlength="25" value="<%=person.getWorkPhone() == null ? "" : person.getWorkPhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.mobile_ph" />
					            	<br/>
				                  	<input type="text" disabled="true" name="person[<%=i %>].mobilePhone" size="15" maxlength="25" value="<%=person.getMobilePhone() == null ? "" : person.getMobilePhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.pager_ph" />
					            	<br/>
				                  	<input type="text" disabled="true"  name="person[<%=i %>].pagerPhone" size="15" maxlength="25" value="<%=person.getPagerPhone() == null ? "" : person.getPagerPhone() %>" class="textfield" />
				              	</td>
				              	<td>
					            	<bean:message key="colname.alt_ph" />
					            	<br/>
				                  	<input type="text" disabled="true" name="person[<%=i %>].alternatePhone" size="15" maxlength="25" value="<%=person.getAlternatePhone() == null ? "" : person.getAlternatePhone() %>" class="textfield" />
				              	</td>
				           </tr>
				           <tr>
								<td colspan="2">
				             		<bean:message key="colname.email" />
				             		<br />
				                  	<input type="text" disabled="true" name="person[<%=i %>].emailAddress" size="35" maxlength="100" value="<%=person.getEmailAddress() == null ? "" : person.getEmailAddress() %>" class="textfield" />
				             	</td>
				             	<td colspan="3">
				             		<bean:message key="colname.dob" />
				             		(<%= a.getDateformat().getFormat() %>)
				             		<br />
				                  	<input type="text" disabled="true" name="person[<%=i %>].disDateOfBirth" size="12" maxlength="11" value="<%=person.getDisDateOfBirth() == null ? "" : person.getDisDateOfBirth() %>" class="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar_<%=i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.claimForm,'person[<%=i %>].disDateOfBirth','calendar_<%=i %>','<%= a.getDateformat().getFormat() %>'); return false;">
				             	</td>
				           </tr>
				           <tr>
				           		<td colspan="2">
				             		<bean:message key="colname.airline_membership" />
				             		<br />
									<select name="person[<%=i %>].ffAirline" disabled="true" class="dropdown">
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
				                  	<input type="text" disabled="true" name="person[<%=i %>].ffNumber" size="30" maxlength="20" value="<%=person.getFfAirline() == null ? "" : person.getFfNumber() %>" class="textfield" />
				             	</td>
				           </tr>
				           <tr>
				           		<td colspan=2 >
					            	<bean:message key="oc.label.ssn" />
					            	<br />
				                  	<input type="text" disabled="true"  name="person[<%=i %>].redactedSocialSecurity" size="20" maxlength="9" value="<%=person.getRedactedSocialSecurity() == null ? "" : person.getRedactedSocialSecurity() %>" class="textfield" />
					            </td>
					            <td colspan=2 >
					            	<bean:message key="colname.common_num" />
					            	<br />
				                  	<input type="text" disabled="true" name="person[<%=i %>].redactedPassportNumber" size="20" maxlength="20" value="<%=person.getRedactedPassportNumber() == null ? "" : person.getRedactedPassportNumber() %>" class="textfield" />
					            </td>
					            <td>
					            	<bean:message key="colname.country_of_issue" />
					            	<br />
					            	<select name="person[<%=i %>].passportIssuer" disabled="true" class="dropdown" >
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
				                  	<input type="text" disabled="true" name="person[<%=i %>].redactedDriversLicenseNumber" size="20" maxlength="20" value="<%=((Person) person).getRedactedDriversLicenseNumber() == null ? "" : ((Person) person).getRedactedDriversLicenseNumber() %>" class="textfield" />
					            </td>
					            <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                    <select name="person[<%=i %>].driversLicenseState" disabled="true" id="dlstate_<%=i %>" class="dropdown">
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
				                  	<input type="text" disabled="true" name="person[<%=i %>].driversLicenseProvince" id="dlprovince_<%=i %>" size="15" maxlength="100" value="<%=person.getDriversLicenseProvince() == null ? "" : person.getDriversLicenseProvince() %>" class="textfield"/>
				                </td>
				                <td colspan=2 >
				                	<bean:message key="colname.country_of_issue" />
				                	<br>
				                	<select name="person[<%=i %>].driversLicenseCountry" id="dlcountry_<%=i %>" disabled="true" class="dropdown" onchange="fieldChanged('dlcountry_<%=i %>');">
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
				            	<!--<input type="button" disabled="true" value="<bean:message key="button.delete.name" />"
				            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_ASSOCIATED_NAME %>_<%=i %>', 
				                '<bean:message key="header.associated.names" />', 0)"
				            	id="button" >-->
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
			              </script>
				          </logic:iterate>
				          <tr>
				          <td colspan="5" align="center">
					          <select name="addNameNum" disabled="true" >
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
					
							    <!--<html:submit styleId="button" property="addNames" onclick="setField('addednames');" >
						        	<bean:message key="button.add.name" />
						        </html:submit>-->
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
					<SCRIPT>
					show('#receipts','#rsshow','#rshide');
					show('#names','#anshow','#anhide');
					</SCRIPT>
					<!--<span style="float:right;" >
						<a id="rsshow" href="#rs" onClick="show('#receipts','#rsshow','#rshide')"><bean:message key="link.show" /></a>
						<a id="rshide" href="#rs" onClick="hide('#receipts','#rsshow','#rshide')" style="display:none;"><bean:message key="link.hide" /></a>
					</span>-->
					</div>
					<div id="receipts" >
					<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="r" name="matchClaim" property="receipts" type="aero.nettracer.fs.model.FsReceipt" >
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
								  		<input type="text" disabled="true" name="receipt[<%=i %>].company" maxlength="35" size="35" value="<%=receipt.getCompany() == null ? "" : receipt.getCompany() %>" class="textfield">
								  	</td>
								  </tr>
								  <tr>
					                <td colspan=2>
					                  <bean:message key="colname.street_addr1" />
					                  <br>
					                  <input type="text" disabled="true" name="receipt[<%=i %>].address.address1" size="40" maxlength="50" value="<%=receipt.getAddress().getAddress1() == null ? "" : receipt.getAddress().getAddress1() %>" class="textfield" />
					                </td>
					                <td colspan=3>
					                  <bean:message key="colname.street_addr2" />
					                  <br>
					                  <input type="text" disabled="true"  name="receipt[<%=i %>].address.address2" size="35" maxlength="50" value="<%=receipt.getAddress().getAddress2() == null ? "" : receipt.getAddress().getAddress2() %>" class="textfield" />
					                </td>
					              </tr>
					              <tr>
					                <td>
					                  <bean:message key="colname.city" />
					                  <br>
					                  <input type="text" disabled="true" name="receipt[<%=i %>].address.city" size="15" maxlength="50" value="<%=receipt.getAddress().getCity() == null ? "" : receipt.getAddress().getCity() %>" class="textfield" />
					                </td>
					                <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                  <select name="receipt[<%=i %>].address.state" disabled="true" id="arstate_<%=i %>" class="dropdown">
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
				                  <input type="text" disabled="true" name="receipt[<%=i %>].address.province" id="arprovince_<%=i %>" size="10" maxlength="100" value="<%=receipt.getAddress().getProvince() == null ? "" : receipt.getAddress().getProvince() %>" class="textfield"  />
				                </td>
				                <td>
				                  <bean:message key="colname.zip" />
				                  <br>
				                  <input type="text" disabled="true" name="receipt[<%=i %>].address.zip" size="11" maxlength="11" value="<%=receipt.getAddress().getZip() == null ? "" : receipt.getAddress().getZip() %>" class="textfield" />
				                </td>
				                <td>
				                  <bean:message key="colname.country" />
				                  <br />
				                  <select name="receipt[<%=i %>].address.country" disabled="true" class="dropdown" id="arcountry_<%=i %>" onchange="fieldChanged('arcountry_<%=i %>');">
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
								  		<input type="text" disabled="true" name="receipt[<%=i %>].phone.phoneNumber" maxlength="20" size="20" value="<%=receipt.getPhone().getPhoneNumber() == null ? "" : receipt.getPhone().getPhoneNumber() %>" class="textfield">
								  	</td>
								  	<td>
								  		<bean:message key="claim.colname.cc_type" />
								  		<br />
										<select name="receipt[<%=i %>].ccType" disabled="true"  class="dropdown" >
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
								  		<input type="text" disabled="true" name="receipt[<%=i %>].ccLastFour" maxlength="4" size="4" value="<%=((FsReceipt) receipt).getCcLastFour() == null ? "" : ((FsReceipt) receipt).getCcLastFour() %>" class="textfield">
									</td>
									<td >
										<bean:message key="claim.colname.cc_expdate" />
										<br/>
								  		<select name="receipt[<%=i %>].ccExpMonth" disabled="true" class="dropdown" >
								  			<option value="0" <% if (receipt.getCcExpMonth() == 0) { %>selected<% };%>><bean:message key="claim.cc.month" /></option>
								  			<% 
								  				ArrayList<Integer> months = DateUtils.getCcMonths();
								  				for (int j = 0; j < months.size(); ++j) { %>
								  					<option value="<%=months.get(j) %>" <% if (receipt.getCcExpMonth() == months.get(j)) { %>selected<% };%>><%=months.get(j)%></option>								  					
								  			<%	} %>
								  		</select>
										&nbsp;/&nbsp;
								  		<select name="receipt[<%=i %>].ccExpYear" disabled="true" class="dropdown" >
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
						            	<!--<input type="button" disabled="true" value="<bean:message key="button.delete.receipt" />"
						            		onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_ASSOCIATED_RECEIPT %>_<%=i %>', 
						                '<bean:message key="header.associated.receipts" />', 0)"
						            	id="button" >-->
						           	</td>
								  </tr>
				            	</table>
				            </td>
				          </tr>
				          <script>
			              </script>
				          </logic:iterate>
				          <tr>
				          <td align="center">
					          <select name="addReceiptNum" disabled="true" >
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>
							    <!--<input type="submit" disabled="true" name="addReceipts" value='<bean:message key="button.add.receipt" />' id="button" onclick="setField('addedreceipts');" />-->
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
					</div>
					<div id="phonesD"  >
					<table class="form2" cellspacing="0" cellpadding="0">
                    					<logic:iterate indexId="i" id="phones" name="matchClaim" property="phones" type="aero.nettracer.fs.model.Phone" >
				          <tr>
				          <td>
							<bean:message key="claim.colname.phone" />
							<br/>
				                <input disabled="true" type="text" name="phone[<%=i %>].phoneNumber"  size="15" maxlength="25" value="<%=phones.getPhoneNumber() == null ? "" : phones.getPhoneNumber() %>" class="textfield" />
				            </td>
				            <td>
				           	<bean:message key="claim.colname.phoneAssociation" />
							<br/>
								<input disabled="true" type="text" name="phone[<%=i %>].association"  size="15" maxlength="25" value="<%=phones.getAssociation() == null ? "" : phones.getAssociation() %>" class="textfield" />
				            </td>
				           </tr>
                    </logic:iterate>
				          <tr>
				          <td colspan="5" align="center">
					          <select name="addPhoneNum" disabled="true" >
						          <option value="1">1</option>
						          <option value="2">2</option>
						          <option value="3">3</option>
						          <option value="4">4</option>
						          <option value="5">5</option>
						        </select>

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
					</div>
					<div id="ipaddressD"  >
	
						<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="ips" name="matchClaim" property="ipAddresses" type="aero.nettracer.fs.model.FsIPAddress" >
				          <tr>
				          <td style="margin:0;padding:0;">
							<bean:message key="claim.colname.ipAddress" />
							<br/>
				                <input disabled="true"  type="text" name="ipAddress[<%=i %>].ipAddress" size="15" maxlength="15" value="<%=ips.getIpAddress() == null ? "" : ips.getIpAddress() %>" class="textfield" />
				        	</td>
				            <td>
				           	<bean:message key="claim.colname.ipaddressAssociation" />
							<br/>
								<input disabled="true"  type="text" name="ipAddress[<%=i %>].association"  size="15" maxlength="25" value="<%=ips.getAssociation() == null ? "" : ips.getAssociation() %>" class="textfield" />
				            </td>
				           </tr>
				          
				          </logic:iterate>
					</table>
	
                    </div>
                   
                    <br />
                    <br />
                    <!-- end ip addresses -->
                    
                    
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
		                   			<html:text name="matchClaim" disabled="true"  property="incident.airlineIncidentId" size="20" maxlength="13"  styleClass="textfield" /> <!-- value="<=incident_ID != null ? incident_ID : "" %>" -->
		                   		</td>
		                   		<td>
		                   			<bean:message key="claim.incident.type" />
		                   			<br/>
		                   			<html:select name="matchClaim" property="incident.incidentType" styleClass="dropdown" disabled="true" >
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
							<html:text name="matchClaim" property="file.incident.reservation.purchaser.lastName" size="20" maxlength="20" styleClass="textfield" disabled="true" />
						</td>
						<td colspan=2>
							<bean:message key="colname.first_name" />
							<br/>
							<html:text name="matchClaim" property="file.incident.reservation.purchaser.firstName" size="20" maxlength="20" styleClass="textfield" disabled="true" />
						</td>
						<td>
			              <bean:message key="colname.mid_initial" />
			              <br>
			              <html:text name="matchClaim" property="file.incident.reservation.purchaser.middleName" size="1" maxlength="1" styleClass="textfield" disabled="true" />
			            </td>
					</tr>
					<!-- START RESERVATION ADDRESS INFO -->
					<tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="matchClaim" property="file.incident.reservation.billingAddress.address1" size="40" maxlength="50" styleClass="textfield" disabled="true" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="matchClaim" property="file.incident.reservation.billingAddress.address2" size="35" maxlength="50" styleClass="textfield" disabled="true" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="matchClaim" property="file.incident.reservation.billingAddress.city" size="15" maxlength="50" styleClass="textfield" disabled="true" />
                </td>
                <td>
                  <bean:message key="colname.state.req" />
                  <br />
                  <logic:equal name="matchClaim" property="file.incident.reservation.billingAddress.country" value="US">
                    <html:select name="matchClaim" property="file.incident.reservation.billingAddress.state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'file.incident.reservation.billingAddress.country', 'file.incident.reservation.billingAddress.province');" disabled="true" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="matchClaim" property="file.incident.reservation.billingAddress.country" value="">
                    <html:select name="matchClaim" property="file.incident.reservation.billingAddress.state" disabled="true" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'file.incident.reservation.billingAddress.country', 'file.incident.reservation.billingAddress.province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="matchClaim" property="file.incident.reservation.billingAddress.country" value="">
                    <logic:notEqual name="matchClaim" property="file.incident.reservation.billingAddress.country" value="US">
                      <html:select name="matchClaim" property="file.incident.reservation.billingAddress.state" disabled="true" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'file.incident.reservation.billingAddress.country', 'file.incident.reservation.billingAddress.province');" >
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
                      <logic:equal name="matchClaim" property="file.incident.reservation.billingAddress.country" value="US">
                  		<html:text name="matchClaim" property="file.incident.reservation.billingAddress.province" size="15" disabled="true" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="matchClaim" property="file.incident.reservation.billingAddress.country" value="">
                  <html:text name="matchClaim" property="file.incident.reservation.billingAddress.province" size="15" maxlength="100" disabled="true" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="matchClaim" property="file.incident.reservation.billingAddress.country" value="">
                        <logic:notEqual name="matchClaim" property="file.incident.reservation.billingAddress.country" value="US">
                  <html:text name="matchClaim" property="file.incident.reservation.billingAddress.province" size="15" maxlength="100" disabled="true" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text name="matchClaim" property="file.incident.reservation.billingAddress.zip" size="15" maxlength="11" styleClass="textfield" disabled="true" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="matchClaim" property="file.incident.reservation.billingAddress.country" styleClass="dropdown" disabled="true" onchange="checkstate(this,this.form,'file.incident.reservation.billingAddress.state', 'file.incident.reservation.billingAddress.province');">
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
             		<html:text name="matchClaim" property="file.incident.reservation.purchaser.emailAddress" disabled="true" size="35" maxlength="100" styleClass="textfield" />
             	</td>
             </tr>
					<!-- END RESERVATION ADDRESS INFO -->
					<tr>
						<td colspan=2>
							<bean:message key="claim.colname.payment.type" />
							<br />
							<html:select name="matchClaim" property="file.incident.reservation.formOfPayment" styleClass="dropdown" disabled="true"  >
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
                          <html:text  name="matchClaim" property="file.incident.reservation.ticketAmount" size="13" maxlength="13" styleClass="textfield" disabled="true" />
                        </td>
                        <td>
                          <bean:message key="colname.currency" />
                          <br />
                          <html:select name="matchClaim" property="amountClaimedCurrency" styleClass="dropdown" disabled="true" >
                            <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                          </html:select>
                        </td>
					</tr>
					<tr>
						<td colspan=2>
							<bean:message key="claim.colname.cc_type" />
							<br/>
							<html:select name="matchClaim" property="file.incident.reservation.ccType" styleClass="dropdown" disabled="true" >
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
							<html:text name="matchClaim" property="file.incident.reservation.redactedCcNumber" size="10" maxlength="6" disabled="true" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="claim.colname.cc_num.last.four" />
							<br/>
							<html:text name="matchClaim" property="file.incident.reservation.ccNumLastFour" size="10" maxlength="4" disabled="true" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="claim.colname.cc_expdate" />
							<br/>
							<html:select name="matchClaim" property="file.incident.reservation.ccExpMonth" styleClass="dropdown" disabled="true" >
								<html:option value="">
									<bean:message key="claim.cc.month" />
								</html:option>
								<html:options property="ccMonths" />
							</html:select>
							&nbsp;
							<html:select name="matchClaim" property="file.incident.reservation.ccExpYear" styleClass="dropdown" disabled="true" >
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
							<html:textarea name="matchClaim" property="file.incident.reservation.pnrData.pnrData" cols="80" rows="10" disabled="true" /> 
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
					</div>
					<div id="segmentD"  >
	
						<table class="form2" cellspacing="0" cellpadding="0" >
						<logic:iterate indexId="i" id="seg" name="matchClaim" property="segments" type="aero.nettracer.fs.model.Segment" >
				          <tr>
				          <td style="margin:0;padding:0;">
							<bean:message key="claim.colname.segment.depart" />
							<br/>
				                <input disabled="true" type="text" name="segment[<%=i %>].departure" size="8" maxlength="3" value="<%=seg.getDeparture() == null ? "" : seg.getDeparture() %>" class="textfield" />
				        	</td>
				            <td>
				           	<bean:message key="claim.colname.segment.arrive" />
							<br/>
								<input disabled="true" type="text" name="segment[<%=i %>].arrival"  size="8" maxlength="3" value="<%=seg.getArrival() == null ? "" : seg.getArrival() %>" class="textfield" />
				            </td>
				            <td>
				           	<bean:message key="claim.colname.segment.date" />
					        (<%= a.getDateformat().getFormat() %>)
							<br/>
								<input disabled="true" type="text" name="segment[<%=i %>].disDate"  size="12" maxlength="11" value="<%=seg.getDisDate() == null ? "" : seg.getDisDate() %>" class="textfield" />
				            </td>
				           </tr>
				          
				          </logic:iterate>
					</table>
	
                    </div>
                   
                    <br />
                    <br />
                    <!-- segments -->
					
					<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SHARED_ATTACHMENTS, a) && !(a.getCompanycode_ID() != matchClaim.getAirline() && ("US".equals(a.getCompanycode_ID()) || "US".equals(matchClaim.getAirline())))) { %>
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
				                 	 
				                      <logic:iterate indexId="i" id="attachment" name="matchClaim" property="attachments" type="aero.nettracer.fs.model.Attachment" >
										  
					  				      <tr align="center">
											
										  <td align="left">
										  	<logic:notEqual name="attachment" property="attachment_id" value="-1">
				                           	<a href='retrieveAttachment?ID=<bean:write name="attachment" property="attachment_id"/>' target="top"><bean:write name="attachment" property="description"/></a>
				                           	</logic:notEqual>
				                           	
										  	<logic:equal name="attachment" property="attachment_id" value="-1">
										  		<bean:write name="attachment" property="description"/>
										  	</logic:equal>
				                          </td>
				                          
				                          </tr>
										
				                      </logic:iterate>
				                  
				                </table>
				              </td>
				            </tr>
			            </table>
						<%
				          }
						%>
                    <center>
                    <!--<html:submit property="save" styleId="button">
                      <bean:message key="button.save" />
                    </html:submit>-->
                    <logic:notEmpty name="back" scope="request" >
                    	&nbsp;&nbsp;
	            		<!-- <input id="button" disabled="true" type="button" value='<bean:message key="claim.button.back" />' onClick="history.back()">-->
                    </logic:notEmpty>
                    </center>
                    <input type="hidden" disabled="true" name="showNames" id="#names" value="<%=request.getAttribute("showNames") %>" />
                    <input type="hidden" disabled="true" name="showReceipts" id="#receipts" value="<%=request.getAttribute("showReceipts") %>" />
                    <input type="hidden" disabled="true" id="addednames" value="0" />
                    <input type="hidden" disabled="true" id="addedreceipts" value="0" />
                    <script language="javascript">
						<logic:present name="an" scope="request">
							document.location.href="#an";
						</logic:present>
						
						<logic:present name="rs" scope="request">
							document.location.href="#rs";
						</logic:present>
				    </script>
                  </html:form>
                  </logic:iterate>
					