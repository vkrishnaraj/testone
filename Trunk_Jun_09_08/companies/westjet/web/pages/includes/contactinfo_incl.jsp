<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	

	function insertNewLine2(elementId) {
		if (window.event && window.event.keyCode == 13) {
			insertAtCursor(elementId, '\n');
			window.event.keyCode = 505;
		}
  	}
  
  function insertAtCursor(myField, myValue) {
		if (document.selection) {
			myField.focus();
			sel = document.selection.createRange();
			sel.text = myValue;
			sel.collapse(false);
			sel.select();
		} else {
			myField.value += myValue;
		}
	}

	

</SCRIPT>


<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
 
  int report_type = 0;
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
      cssFormClass = "form2_pil";
    }
  }
%>
  <a name="contact"></a>
  <h1 class="green">
    <bean:message key="header.passenger_info" />
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
      <logic:iterate id="passenger" name="incidentForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.Passenger">
      <div id="<%=TracingConstants.JSP_DELETE_PAX %>_<%=i%>">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" id="passid">
          <tr>
            <td colspan=5>
              <a name='addpassenger<%= i %>'></a>
              <b><bean:message key="colname.passenger" /> <%= ((Integer)i).intValue() + 1 %></b>
            </td>

          </tr>
          <tr>
            <td nowrap colspan=2>
              <bean:message key="colname.last_name.req" />
              <br>
              <html:text name="passenger" property="lastname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
            </td>
            <td nowrap colspan=2>
              <bean:message key="colname.first_name.req" />
              <br>
              <html:text name="passenger" property="firstname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
            </td>
            <td>
              <bean:message key="colname.mid_initial" />
              <br>
              <html:text name="passenger" property="middlename" size="1" maxlength="1" indexed="true" styleClass="textfield" />
            </td>
          </tr>
          <tr>
            <td colspan="1">
              <bean:message key="colname.salutation" />
              <br>
              <html:select name="passenger" indexed="true" property="salutation" styleClass="dropdown">
                <html:option value="0">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:option value="1">
                  <bean:message key="select.dr" />
                </html:option>
                <html:option value="2">
                  <bean:message key="select.mr" />
                </html:option>
                <html:option value="3">
                  <bean:message key="select.ms" />
                </html:option>
                <html:option value="4">
                  <bean:message key="select.miss" />
                </html:option>
                <html:option value="5">
                  <bean:message key="select.mrs" />
                </html:option>
                <html:option value="6">
                  <bean:message key="select.other" />
                </html:option>
              </html:select>
            </td>
            <td colspan="1">
            	<bean:message key="spoken.language.label" />
            	<br>
            	 <html:select name="passenger" indexed="true" property="languageKey" styleClass="dropdown" onchange="languagefreeflow(this,this.form,'languageFreeFlow');">
                <html:options collection="spokenLanguageList" property="value" labelProperty="label"/>
              </html:select>
              <% if("other".equals(passenger.getLanguageKey())){ %>
              <html:text name="passenger" property="languageFreeFlow" indexed="true" size="25" maxlength="25" styleClass="textfield" />
          		<% } else { %>
           <html:text name="passenger" property="languageFreeFlow" indexed="true" size="25" maxlength="25" styleClass="textfield" style="display:none;" />
           	<% } %>
            </td>
            <td colspan="2">
              <bean:message key="colname.job_title" />
              <br>
              <html:text name="passenger" indexed="true" property="jobtitle" size="30" maxlength="25" styleClass="textfield" />
            </td>

          </tr>
          <tr>
            <td nowrap>
              <bean:message key="colname.airline_membership" />
              <br>
              <html:select name="passenger" indexed="true" property="membership.companycode_ID" styleClass="dropdown">
                <html:option value="">
                  <bean:message key="select.none" />
                </html:option>
                <html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
              </html:select>
            </td>
            <td nowrap>
           		<bean:message key="colname.membership_number" />
              <br>
              <html:text name="passenger" indexed="true" property="membership.membershipnum" size="30" maxlength="20" styleClass="textfield" />
            </td>
            <td nowrap colspan=3>
              <bean:message key="colname.membership_status" />
              <br>
              <html:text name="passenger" indexed="true" property="membership.membershipstatus" size="20" maxlength="20" styleClass="textfield" />
            </td>
          </tr>

          <logic:present name="passenger" property="addresses">
            <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.Address">
              <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1.req" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].address1" %>' size="45" maxlength="50" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].address2" %>' size="45" maxlength="50" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city.req" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].city" %>' size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state.req" />
                  <br />
                  <logic:equal name="addresses" property="countrycode_ID" value="US">
                    <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].state_ID" %>' styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:equal name="addresses" property="countrycode_ID" value="">
                    <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].state_ID" %>' styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
                      <html:option value="">
                        <bean:message key="select.none" />
                      </html:option>
                      <html:options collection="statelist" property="value" labelProperty="label" />
                    </html:select>
                  </logic:equal>
                  <logic:notEqual name="addresses" property="countrycode_ID" value="">
                    <logic:notEqual name="addresses" property="countrycode_ID" value="US">
                      <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].state_ID" %>' styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');" >
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
                      <logic:equal name="addresses" property="countrycode_ID" value="US">
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].province" %>' size="15" maxlength="100" styleClass="disabledtextfield" disabled="true" />
                      </logic:equal>
                      <logic:equal name="addresses" property="countrycode_ID" value="">
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].province" %>' size="15" maxlength="100" styleClass="textfield" />
                      </logic:equal>
                      <logic:notEqual name="addresses" property="countrycode_ID" value="">
                        <logic:notEqual name="addresses" property="countrycode_ID" value="US">
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].province" %>' size="15" maxlength="100" styleClass="textfield" />
                         </logic:notEqual>
                      </logic:notEqual>
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].zip" %>' size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].countrycode_ID" %>' styleClass="dropdown" onchange="checkstate(this,this.form,'state_ID', 'province');">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.valid_bdate" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].dispvalid_bdate" %>' size="20" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar1<%= i %>" name="calendar1<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].dispvalid_bdate" %>','calendar1<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td colspan=2>
                  <bean:message key="colname.valid_edate" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].dispvalid_edate" %>' size="20" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= i %>" name="calendar2<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].dispvalid_edate" %>','calendar2<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td>
            			<html:checkbox property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].permanent" %>' styleClass="textfield" />  <b><bean:message key="colname.is_permanent" /></b>
            		</td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.home_ph" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].homephone" %>' size="15" maxlength="25" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.business_ph" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].workphone" %>' size="15" maxlength="25" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mobile_ph.req" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].mobile" %>' size="15" maxlength="25" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.pager_ph" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].pager" %>' size="15" maxlength="25" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.alt_ph" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].altphone" %>' size="15" maxlength="25" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td colspan="1" width="25%">
                  <bean:message key="colname.hotel" />
                  <br>
                  <html:text property="<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].hotel" %>" size="20" maxlength="50" styleClass="textfield" />
                </td><td colspan="1" width="25%">
                  <bean:message key="colname.hotel_ph" />
                  <br>
                  <html:text property="<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].hotelphone" %>" size="20" maxlength="50" styleClass="textfield" />
                </td>
                
                
                
                <logic:equal name="incidentForm" property="incident_ID" value="">
                <td colspan="2" width="33%">
                  <bean:message key="colname.email.req" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].email" %>' size="42" maxlength="100" styleClass="textfield" />
                  </logic:equal>
                 <logic:notEqual name="incidentForm" property="incident_ID" value="">
                <td colspan="3" width="50%">
                  <bean:message key="colname.email.req" />
                  <br>
                  <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].email" %>' size="45" maxlength="100" styleClass="textfield" />
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
<%
    if (report_type == 0) {
%>
                      <b><bean:message key="colname.damaged_email_cus" /></b>
<%
    } else if (report_type == 1) {
%>
                      <b><bean:message key="colname.report_email_cus" /></b>
<%
    } else if (report_type == 2) {
%>
                      <b><bean:message key="colname.pilferage_email_cus" /></b>
<%
	}
%>
 </td>
                      <td width="17%">
                      <bean:message key="colname.email.language" />
                      <br />
                      <html:select name="incidentForm" property="language" styleClass="dropdown">
                      	<html:options collection="receiptLocaleList" property="value" labelProperty="label"/>
                    </html:select>
<%
                    }
%>
                  </logic:equal>
                </td>
              </tr>
            </logic:iterate>
          </logic:present>
          <% if (request.getAttribute("lostdelay") != null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUE_RON_KITS, a) && !(i.intValue() > 0)) { %> 
	          <tr>
	          	<td colspan=5>
	          		<bean:message key="number.ron.kits.issued.req" />:&nbsp;
	          		<html:select name="passenger" property="numRonKitsIssued" styleId="numRonKitsIssued" styleClass="dropdown" indexed="true" >
  						<html:option value="-1"><bean:message key="select.please_select" /></html:option>
  						<html:option value="0">0</html:option>
  						<html:option value="1">1</html:option>
  						<html:option value="2">2</html:option>
  						<html:option value="3">3</html:option>
  						<html:option value="4">4</html:option>
  						<html:option value="5">5</html:option>
  						<html:option value="6">6</html:option>
  						<html:option value="7">7</html:option>
  						<html:option value="8">8</html:option>
  						<html:option value="9">9</html:option>
  						<html:option value="10">10</html:option>
  					</html:select>
	          	</td>
	          </tr>
          <% } %>
          
          <% if (request.getAttribute("lostdelay") != null && !(i.intValue() > 0) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_DELIVERY_INSTRUCTIONS)) { %> 
        
	          <tr>
	          	<td colspan=5>
	          		<bean:message key="colname.delivery.instructions" />:<br/>
	          		 <html:textarea styleId="delInstruct" name="incidentForm" property="deliveryInstructions.instructions" cols="80" rows="5" onkeydown="textCounter2(delInstruct, delInstructCounter, 300);insertNewLine2(delInstruct);" onkeyup="textCounter2(delInstruct, delInstructCounter, 300);"/>
	          		 <input name="delInstruct2" id="delInstructCounter" type="text" value="300" size="4" maxlength="4" disabled="true" />
	          	</td>
	          </tr>
          <% } %>
          <tr>
            <td colspan=5>
              <input type="button" value="<bean:message key="button.delete_passenger" />" onclick="hideThisDiv('<%=TracingConstants.JSP_DELETE_PAX %>_<%=i%>', '<bean:message key="colname.passenger" />')" id="button">
            </td>
          </tr>
        </table>
        </div>
      </logic:iterate>
      <center>
      <select name="addPassengerNum">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
      </select>
      <html:submit styleId="button" property="addPassenger">
        <bean:message key="button.add_additional_passenger" />
      </html:submit></center>
      <br>
      <br>
      &nbsp;&nbsp;&uarr;
      <a href="#"><bean:message key="link.to_top" /></a>
      <br>
      <br>
