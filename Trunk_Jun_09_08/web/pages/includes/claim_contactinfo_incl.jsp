<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="aero.nettracer.fs.model.Phone" %>

<%@page import="org.apache.struts.util.LabelValueBean"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	
	
	function dlFieldChanged(field) {
		var dlState = document.getElementById('dlState');
		var dlProvince = document.getElementById('dlProvince');
		var dlCountry = document.getElementById('dlCountry');
	
		if (field == "dlState") {
			stateChanged(dlState, dlProvince, dlCountry);
		} else if (field == "dlProvince") {
			provinceChanged(dlState, dlProvince, dlCountry);
		} else if (field == "dlCountry") {
			countryChanged(dlState, dlProvince, dlCountry);
		}
	}
	
	function stateChanged(dlState, dlProvince, dlCountry) {
		if (dlState.value == "") {
			dlProvince.disabled = false;	
			dlProvince.className = "textbox";
			dlCountry.value = "";
		} else {
			dlProvince.value = "";
			dlProvince.disabled = true;
			dlProvince.className = "disabledtextbox";
			dlCountry.value = "US";
		}
	}
	
	function provinceChanged(dlState, dlProvince, dlCountry) {
		if (dlProvince.value == "") {
			dlState.disabled = false;
		} else {
			dlState.value = "";
			dlState.disabled = true;
		}
	}
	
	function countryChanged(dlState, dlProvince, dlCountry) {
		if (dlCountry.value == "") {
			dlState.disabled = false;
			dlProvince.disabled = false;
			dlProvince.className = "textbox";
		} else if (dlCountry.value == "US") {
			dlState.disabled = false;
			dlProvince.value = "";
			dlProvince.disabled = true;
			dlProvince.className = "disabledtextbox";
		} else {
			dlState.value = "";
			dlState.disabled = true;
			dlProvince.disabled = false;
			dlProvince.className = "textbox";
		}
	}

</SCRIPT>


<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
 
  int report_type = 0;
  cssFormClass = "form2";

  
%>
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
              <html:text name="claimForm" property="claimant.lastName" size="20" maxlength="20" styleClass="textfield" />
            </td>
            <td nowrap colspan="2">
              <bean:message key="colname.first_name.req" />
              <br>
              <html:text name="claimForm" property="claimant.firstName" size="20" maxlength="20" styleClass="textfield" />
            </td>
            <td>
              <bean:message key="colname.mid_initial" />
              <br>
              <html:text name="claimForm" property="claimant.middleName" size="1" maxlength="1" styleClass="textfield" />
            </td>
           
          </tr>
         
          <!-- logic:present name="incident.passengers" property="addresses" -->
            <!--logic:iterate indexId="i" id="address" name="claimForm"  property="claimant.addresses" type="aero.nettracer.fs.model.FsAddress"-->
              <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1.req" />
                  <br>
                  <html:text name="claimForm" property="address1" size="45" maxlength="50" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="claimForm" property="address2" size="35" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city.req" />
                  <br>
                  <html:text name="claimForm" property="city" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state.req" />
                  <br />
                  <logic:equal name="claimForm" property="country" value="US">
                    <html:select name="claimForm" property="state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="claimForm" property="country" value="">
                    <html:select name="claimForm" property="state" styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="claimForm" property="country" value="">
                    <logic:notEqual name="claimForm" property="country" value="US">
                      <html:select name="claimForm" property="state" styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'country', 'province');" >
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
                      <logic:equal name="claimForm" property="country" value="US">
                  		<html:text name="claimForm" property="province" size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="claimForm" property="country" value="">
                  <html:text name="claimForm" property="province" size="15" maxlength="100" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="claimForm" property="country" value="">
                        <logic:notEqual name="claimForm" property="country" value="US">
                  <html:text name="claimForm" property="province" size="15" maxlength="100" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip.req" />
                  <br>
                  <html:text name="claimForm" property="zip" size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="claimForm" property="country" styleClass="dropdown" onchange="checkstate(this,this.form,'state', 'province');">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
              <tr>
              	<td>
	            	<bean:message key="colname.home_ph" />
	            	<br/>
		            <html:text name="claimForm" property="homePhone" size="15" maxlength="25" styleClass="textfield" />
              	</td>
              	<td>
	            	<bean:message key="colname.business_ph" />
	            	<br/>
		            <html:text name="claimForm" property="workPhone" size="15" maxlength="25" styleClass="textfield" />
              	</td>
              	<td>
	            	<bean:message key="colname.mobile_ph" />
	            	<br/>
		            <html:text name="claimForm" property="mobilePhone" size="15" maxlength="25" styleClass="textfield" />
              	</td>
              	<td>
	            	<bean:message key="colname.pager_ph" />
	            	<br/>
		            <html:text name="claimForm" property="pagerPhone" size="15" maxlength="25" styleClass="textfield" />
              	</td>
              	<td>
	            	<bean:message key="colname.alt_ph" />
	            	<br/>
		            <html:text name="claimForm" property="alternatePhone" size="15" maxlength="25" styleClass="textfield" />
              	</td>
             </tr>
             <tr>
             	<td colspan="2">
             		<bean:message key="colname.email" />
             		<br />
             		<html:text name="claimForm" property="claimant.emailAddress" size="35" maxlength="100" styleClass="textfield" />
             	</td>
             	<td colspan="3">
             		<bean:message key="colname.dob" />
             		(<%= a.getDateformat().getFormat() %>)
             		<br />
             		<html:text name="claimForm" property="claimantDateOfBirth" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.claimForm.claimantDateOfBirth,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
             		
             	</td>
             </tr>
             <tr>
             	<td colspan="2">
             		<bean:message key="colname.airline_membership" />
             		<br />
             		<html:select name="claimForm" property="claimant.ffAirline" styleClass="dropdown">
                		<html:option value="">
                  			<bean:message key="select.none" />
                		</html:option>
               			<html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
              		</html:select>
             	</td>
             	<td colspan="3">
             		<bean:message key="colname.membership_number" />
              		<br>
             		<html:text name="claimForm" property="claimant.ffNumber" size="30" maxlength="20" styleClass="textfield" />
             	</td>
             </tr>
              <tr>        
                 <td colspan=2 >
	            	<bean:message key="oc.label.ssn" />
	            	<br />
	            	<html:text name="claimForm" property="claimant.redactedSocialSecurity" size="20" maxlength="9" styleClass="textfield" />
	            </td>
	            <td colspan=2 >
	            	<bean:message key="colname.common_num" />
	            	<br />
	            	<html:text name="claimForm" property="claimant.redactedPassportNumber" size="20" maxlength="20" styleClass="textfield" />
	            </td>
	            <td>
	            	<bean:message key="colname.country_of_issue" />
	            	<br />
	            	<html:select name="claimForm" property="claimant.passportIssuer" styleClass="dropdown" >
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
	            	<html:text name="claimForm" property="claimant.redactedDriversLicenseNumber" size="20" maxlength="20" styleClass="textfield" />
	            </td>
	            <td>
                  <bean:message key="colname.state" />
                  <br />
                    <html:select name="claimForm" property="claimant.driversLicenseIssuer" styleClass="dropdown" styleId="dlState" onchange="dlFieldChanged('dlState');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                </td>
                <td colspan=2 >
                  <bean:message key="colname.province" />
                  <br />
                  <html:text name="claimForm" property="claimant.driversLicenseIssuer" size="15" maxlength="100" styleClass="textfield" styleId="dlProvince" onchange="dlFieldChanged('dlProvince');" />
                </td>
                <td colspan=2 >
                	<bean:message key="colname.country_of_issue" />
                	<br>
                	<html:select name="claimForm" property="claimant.driversLicenseCountry" styleId="dlCountry" styleClass="dropdown" onchange="dlFieldChanged('dlCountry');">
                		<html:option value="">
	                        <bean:message key="select.none" />
	                    </html:option>
	                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                	</html:select>
                </td>
              </tr>
              <script>
					dlFieldChanged('dlCountry');		              		
              </script>
            <!--/logic:iterate-->
           	 
          <!--/logic:present-->
          <!--tr>
            <td colspan=5>
            </td>
          </tr-->
        </table>
        <!-- /div-->
      <!--center>
      <select name="addPassengerNum">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
      </select>
      <html:submit styleId="button" property="addPassenger">
        <bean:message key="button.add_additional_passenger" />
      </html:submit></center-->
      <!--br>
      <br>
      &nbsp;&nbsp;&uarr;
      <a href="#"><bean:message key="link.to_top" /></a>
      <br>
      <br-->
