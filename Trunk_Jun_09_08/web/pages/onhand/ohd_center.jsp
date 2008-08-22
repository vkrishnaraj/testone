<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.OnHandForm" %>
<%
  Agent         a          = (Agent)session.getAttribute("user");
  OnHandForm onHandForm = (OnHandForm)session.getAttribute("OnHandForm");
  
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
	
		// show manufacturer
	function showmanu(o) {
		if (o.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
			document.getElementById("manu_other").style.visibility = "visible";
		} else {
			document.getElementById("manu_other").style.visibility = "hidden";
		}
	}
	
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <script language="javascript">
    <!--
function gotoHistoricalReport() {
  o = document.OnHandForm;
	o.historical_report.value = "1";
	o.submit();
}
// -->
  </script>
  <SCRIPT LANGUAGE="JavaScript">
    function textCounter2(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }
    // End -->
  </SCRIPT>
  <jsp:include page="../includes/validation_incl.jsp" />
  <html:form action="addOnHandBag.do" method="post" enctype="multipart/form-data" onsubmit="return validateRest(this);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
<%
          if (onHandForm.getOhd_type() == TracingConstants.MASS_OHD_TYPE) {
%>
            <h1>
              <bean:message key="header.mass_add_on_hand_title" />
            </h1>
<%
          } else {
%>
            <h1>
              <bean:message key="header.add_on_hand_title" />
            </h1>
<%
          }
%>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="../includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    <!-- END PAGE HEADER/SEARCH -->
    <!-- ICONS MENU -->
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href="#baginfo"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.bag_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="#passengerinfo"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.passenger_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="#itinerary"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.bag_itinerary" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="#centralbag"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.central_baggage_inventory" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="#remarks"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.remarks" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
<%
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
              <dd>
                <a href="#photos"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.photos" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
<%
            }
%>
            <logic:notEqual name="OnHandForm" property="ohd_id" value="">
<%
              if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BDO, a)) {
                if (a.getCompanycode_ID().equals(onHandForm.getHolding_company())
                        && a.getStation().getStationcode().equals(onHandForm.getHolding_station())) {
%>
                  <dd>
                    <a href='bdo.do?ohd_id=<bean:write name="OnHandForm" property="ohd_id"/>'><span class="aa">&nbsp;
                        <br />
                        &nbsp;</span>
                      <span class="bb"><bean:message key="menu.bdo" /></span>
                      <span class="cc">&nbsp;
                        <br />
                        &nbsp;</span></a>
                  </dd>
<%
                }
              }
%>
              <dd>
                <a href='viewMatches.do?clear=1&ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>'><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.matches" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
              <dd>
                <a href="otherTasks.do?type=0&file=<bean:write name="OnHandForm" property="ohd_id"/>"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.tasks" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
              <dd>
                <a href="#" onclick="gotoHistoricalReport();"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.history" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
            </logic:notEqual>
          </dl>
        </div>
      </td>
    </tr>
    <input type="hidden" name="historical_report" value="">
    <!-- END ICONS MENU -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <a name="baginfo"></a>
          <h1 class="green">
            <bean:message key="header.bag_info" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_on-hand_bag_reports.htm#baggage_info_fields');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <p align="right">
            <logic:notEqual name="OnHandForm" property="ohd_id" value="">
<%
              if (onHandForm.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_OPEN) {
                if (a.getCompanycode_ID().equals(onHandForm.getHolding_company())
                        && a.getStation().getStationcode().equals(onHandForm.getHolding_station())) {
%>
                  <A HREF="forward_on_hand.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>"><b><bean:message key="colname.forwardThisBag" /></b></A>

<%
            		if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD, a)) {
%>
						<logic:notEqual name="OnHandForm" property="wt_id" value="">
						&nbsp;|&nbsp;
						<a href="worldtracerfwd.do?ohd_id=<bean:write name="OnHandForm" property="ohd_id" />">Forward to WT</a>
						</logic:notEqual>
<%
            		}		
%>	

	
<%
                } else {
%>
                  <A HREF='request_on_hand.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>'><b><bean:message key="colname.requestThisBag" /></b></a>

					<% 
					if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH, a)) { 
					%>
						<logic:notEqual name="OnHandForm" property="wt_id" value="">
						&nbsp;|&nbsp;
						<a href="worldtracerroh.do?wt_id=<bean:write name="OnHandForm" property="wt_id" />">Request from WT</a>
						</logic:notEqual>
					<% 
					} 
					%>	

<%
                }
              } else if (onHandForm.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT) {
                if (onHandForm.getForwarded_station() != null && onHandForm.getForwarded_station().length() > 0 
                	&& a.getCompanycode_ID().equals(onHandForm.getHolding_company())
                    && a.getStation().getStationcode().equals(onHandForm.getHolding_station())) {
%>

					<bean:message key="message.forwarded" /> <b><bean:write name="OnHandForm" property="forwarded_station"/></b>
					<bean:message key="message.forwarded_on" /> <b><bean:write name="OnHandForm" property="dispForwarded_date"/></b>
					<bean:message key="message.forwarded_by" /> <b><bean:write name="OnHandForm" property="forwarded_agent"/></b>
<%
                }
              }
%>

              <%
                if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SCANNER_DATA, a)) {
              %>
              
                <logic:notEmpty name="OnHandForm" property="bagTagNumber">
                  <br/><b><a href="scannerData.do?bagTagNumber=<bean:write name="OnHandForm" property="bagTagNumber" />&ohdId=<bean:write name="OnHandForm" property="ohd_id" />"><bean:message key="scanner.link"/></a></b><br/>
                </logic:notEmpty>
              <%
              }
              %>

            </logic:notEqual>
            
  
            <logic:notEqual name="OnHandForm" property="wt_id" value="">
        		<br>WorldTracer ID: <a href="worldtraceraf.do?ohd_id=<bean:write name="OnHandForm" property="wt_id" />"><bean:write name="OnHandForm" property="wt_id" /></a> 
       		</logic:notEqual>
        
          </p>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2_ohd" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.on_hand_report_number" />
                <br>
                <html:text property="ohd_id" size="14" styleClass="textfield" readonly="true" />
              </td>
              <td>
                <bean:message key="colname.found_date_time" />
                <br>
                <html:text property="dispFoundTime" size="14" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.agent_initials" />
                <br>
                <html:text property="agent_initials" size="4" styleClass="textfield" disabled="true" />
              </td>
              <td>
                <bean:message key="colname.found_station_nobr" />
                <br>
                <input type=text size=10 class="textfield" value="<bean:write name="OnHandForm" property="found_company"/> <bean:write name="OnHandForm" property="found_station"/>" disabled>
              </td>
              <td>
                <bean:message key="colname.holding_station_nobr" />
                <br>
                <input type=text size=10 class="textfield" value="<bean:write name="OnHandForm" property="holding_company"/> <bean:write name="OnHandForm" property="holding_station"/>" disabled>
              </td>
              <logic:notEmpty name="OnHandForm" property="status">
                <logic:equal name="OnHandForm" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_CLOSED %>">
                  <td>
                    <bean:message key="colname.file_close_date" />
                    <br>
                    <html:text property="dispCloseDate" size="15" styleClass="textfield" disabled="true" />
                  </td>
                </logic:equal>
              </logic:notEmpty>
            </tr>
          </table>
          <table class="form2_ohd" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.status" />
                <br>
                <logic:notEqual name="OnHandForm" property="ohd_id" value="">
                  <html:select name="OnHandForm" property="status.status_ID" styleClass="dropdown">
                    <html:options collection="oStatusList" property="status_ID" labelProperty="description" />
                  </html:select>
                </logic:notEqual>
                <logic:equal name="OnHandForm" property="ohd_id" value="">
                  <input type=text class="textfield" size=4 value="<bean:message key="OnHandForm.new_status"/>" disabled>
                </logic:equal>
              </td>
              
              <td>
                  <bean:message key="colname.disposal_status" />
                  <br>

                   <html:select name="OnHandForm" property="disposal_status.status_ID" styleClass="dropdown">
                     <OPTION VALUE=""><bean:message key="select.none" /></option>
                     <html:options collection="dStatusList" property="status_ID" labelProperty="description" />
                   </html:select>

                </td>
              
              
              <td>
                <bean:message key="colname.bag_arrived_date.req" />
                (
                <%= a.getDateformat().getFormat() %>)
                <br>
                <html:text property="dispBagArriveDate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.OnHandForm.dispBagArriveDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
            </tr>
            <tr>
              <td colspan="3">
                <bean:message key="colname.name_on_bag" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.last_name" />
                <br>
                <html:text name="OnHandForm" property="lastname" size="20" maxlength="20" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.first_name" />
                <br>
                <html:text name="OnHandForm" property="firstname" size="20" maxlength="20" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.mid_initial" />
                <br>
                <html:text name="OnHandForm" property="middlename" size="4" maxlength="1" styleClass="textfield" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.bag_tag_number.req" />
                <br>
                <html:text property="bagTagNumber" size="18" maxlength="13" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.pnr" />
                <br>
                <html:text property="pnr" size="22" maxlength="20" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.storage_location" />
                <br>
                <html:text property="storage_location" size="22" maxlength="25" styleClass="textfield" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.airline_membership" />
                <br>
                <html:select property="companycode_ID" styleClass="dropdown">
                  <html:option value="">
                    <bean:message key="select.none" />
                  </html:option>
                  <html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
                </html:select>
              </td>
              <td>
                <bean:message key="colname.membership_number" />
                <br>
                <html:text property="membershipnum" size="22" maxlength="20" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.membership_status" />
                <br>
                <html:text property="membershipstatus" size="22" maxlength="20" styleClass="textfield" />
              </td>
            </tr>
            <tr>
              <td valign=top>
                <bean:message key="colname.color.req" />
                <br>
                <html:select property="bagColor" styleClass="dropdown">
                  <html:options collection="colorlist" property="value" labelProperty="label" />
                </html:select>
                <br>
                <br>
                <bean:message key="colname.bagtype.req" />
                <a href="#" onclick="openChart('pages/popups/bagtypechart.jsp?charttype=1');return false;"><bean:message key="chart1" /></a>
                <a href="#" onclick="openChart('pages/popups/bagtypechart.jsp?charttype=2');return false;"><bean:message key="chart2" /></a>
                <br>
                <html:select property="bagType" styleClass="dropdown">
                  <html:options collection="typelist" property="value" labelProperty="label" />
                </html:select>
              </td>
              <td valign=top>
                <bean:message key="colname.x_desc" />
                <br>
                <html:select property="XDesc1" styleClass="dropdown">
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
                </html:select>
                <br>
                <html:select property="XDesc2" styleClass="dropdown">
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
                </html:select>
                <br>
                <html:select property="XDesc3" styleClass="dropdown">
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
                </html:select>
              </td>
              <td valign=top>
                <bean:message key="colname.manufacturer" />
                <br>
                <html:select property="manufacturer_ID" styleClass="dropdown" onchange='showmanu(this);return true;'>
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:options collection="manufacturerlist" property="manufacturer_ID" labelProperty="description" />
                </html:select>
                <div id="manu_other">
                  <br>
                  <html:text property="manufacturer_other" size="25" maxlength="100" styleClass="textfield" />
                </div>
              </td>
            </tr>
          </table>
          <br>
          <br>
          &nbsp;&nbsp;&uarr;
          <a href="#"><bean:message key="link.to_top" /></a>
          <br>
          <br>
          <a name="passengerinfo"></a>
          <h1 class="green">
            <bean:message key="header.passenger_info" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_passenger_information_(oh).htm#add passenger info');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <logic:iterate id="passenger" name="OnHandForm" property="passengerList" indexId="i" type="com.bagnet.nettracer.tracing.db.OHD_Passenger">
            <table class="form2_ohd" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan=5>
<%
                  if (i.intValue() > 0) {
%>
                    <b><bean:message key="colname.addi_pass_info" />
<%
                  } else {
%>
                    <b><bean:message key="colname.pri_pass_info" />
<%
                  }
%>
                </td>
              </tr>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text name="passenger" property="lastname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
                </td>
                <td colspan=2>
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text name="passenger" property="firstname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text name="passenger" property="middlename" size="1" maxlength="1" indexed="true" styleClass="textfield" />
                </td>
              </tr>
              <logic:present name="passenger" property="addresses">
                <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.OHD_Address">
                  <tr>
                    <td colspan=2>
                      <bean:message key="colname.street_addr1" />
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
                      <bean:message key="colname.city" />
                      <br>
                      <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].city" %>' size="15" maxlength="50" styleClass="textfield" />
                    </td>
                    <td>
                      <bean:message key="colname.state" />
                      <br>
                      <logic:equal name="addresses" property="countrycode_ID" value="US">
                        <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].state_ID" %>' styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                          <html:option value="">
                            <bean:message key="select.none" />
                          </html:option>
                          <html:options collection="statelist" property="value" labelProperty="label" />
                        </html:select>
                      </logic:equal>
                      <logic:equal name="addresses" property="countrycode_ID" value="">
                        <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].state_ID" %>' styleClass="dropdown" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                          <html:option value="">
                            <bean:message key="select.none" />
                          </html:option>
                          <html:options collection="statelist" property="value" labelProperty="label" />
                        </html:select>
                      </logic:equal>
                      <logic:notEqual name="addresses" property="countrycode_ID" value="">
                        <logic:notEqual name="addresses" property="countrycode_ID" value="US">
                          <html:select property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].state_ID" %>' styleClass="dropdown" disabled="true" onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
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
                      <br>
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
                      <bean:message key="colname.mobile_ph" />
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
                    <td colspan=5>
                      <bean:message key="colname.email" />
                      <br>
                      <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].email" %>' size="45" maxlength="50" styleClass="textfield" />
                    </td>
                  </tr>
                </logic:iterate>
              </logic:present>
              <tr>
                <td colspan=5>
                  <html:submit styleId="button" property="deletePassenger" indexed="true">
                    <bean:message key="button.delete_passenger" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </logic:iterate>
          <center><html:submit styleId="button" property="addPassenger">
            <bean:message key="button.add_additional_passenger" />
          </html:submit></center>
          <br>
          <br>
          &nbsp;&nbsp;&uarr;
          <a href="#"><bean:message key="link.to_top" /></a>
          <br>
          <br>
          <a name="itinerary"></a>
          <h1 class="green">
            <bean:message key="header.bag_itinerary" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_baggage_itineraries_(oh).htm#add_baggage_itinerary');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <logic:iterate id="itinerarylist" indexId="k" name="OnHandForm" property="itinerarylist">
            <table class="form2_ohd" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.fromto" />
                  <br>
                  <html:hidden name="itinerarylist" property="itinerarytype" value="1" indexed="true" />
                  <html:text name="itinerarylist" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                  <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                  /
                  <html:text name="itinerarylist" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                  <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                </td>
                <td>
                  <bean:message key="colname.flightnum" />
                  <br>
                  <html:select name="itinerarylist" property="airline" styleClass="dropdown" indexed="true">
                    <html:option value="">
                      <bean:message key="select.please_select" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                  &nbsp;
                  <html:text name="itinerarylist" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" />
                </td>
                <td>
                  <bean:message key="colname.departdate" />
                  (
                  <%= a.getDateformat().getFormat() %>):
                  <br>
                  <html:text name="itinerarylist" property="disdepartdate" size="11" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= k %>" name="calendar2<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.OnHandForm, '<%= "itinerarylist[" + k + "].disdepartdate" %>','calendar2<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td>
                  <bean:message key="colname.arrdate" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="itinerarylist" property="disarrivedate" size="11" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3<%= k %>" name="calendar3<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.OnHandForm, '<%= "itinerarylist[" + k + "].disarrivedate" %>','calendar3<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.schdeptime" />
                  (
                  <%= a.getTimeformat().getFormat() %>)
                  <br>
                  <html:text name="itinerarylist" property="disschdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                </td>
                <td>
                  <bean:message key="colname.scharrtime" />
                  (
                  <%= a.getTimeformat().getFormat() %>)
                  <br>
                  <html:text name="itinerarylist" property="disscharrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                </td>
                <td>
                  <bean:message key="colname.actdeptime" />
                  (
                  <%= a.getTimeformat().getFormat() %>)
                  <br>
                  <html:text name="itinerarylist" property="disactdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                </td>
                <td>
                  <bean:message key="colname.actarrtime" />
                  (
                  <%= a.getTimeformat().getFormat() %>)
                  <br>
                  <html:text name="itinerarylist" property="disactarrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                </td>
              </tr>
              <tr>
                <td colspan=4>
                  <html:submit styleId="button" property="deleteBag" indexed="true">
                    <bean:message key="button.delete_bag_itinerary" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </logic:iterate>
          <center><html:submit property="additinerary" styleId="button">
            <bean:message key="button.add_bag_itinerary" />
          </html:submit></center>
          <br>
          <br>
          &nbsp;&nbsp;&uarr;
          <a href="#"><bean:message key="link.to_top" /></a>
          <br>
          <br>
          <a name="centralbag"></a>
          <h1 class="green">
            <bean:message key="header.central_baggage_inventory" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_baggage_inventory_(oh).htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <logic:iterate indexId="i" id="itemlist" name="OnHandForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.OHD_Inventory">
            <table class="form2_ohd" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                <td>
                  <bean:message key="colname.category" /><br>
                  <html:select name="itemlist" property="OHD_categorytype_ID" indexed="true" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.please_select" />
                    </html:option>
                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="categorytype" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.description" />
                  <br>
                  <html:text name="itemlist" property="description" size="70" maxlength="255" styleClass="textfield" indexed="true" />
                </td>
                <td align="center">&nbsp;<br>
                  <html:submit styleId="button" property="deleteItem" indexed="true">
                    <bean:message key="button.delete_content" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </logic:iterate>
          <center><html:submit styleId="button" property="additem">
            <bean:message key="button.add_content" />
          </html:submit></center>
          <br>
          <br>
          &nbsp;&nbsp;&uarr;
          <a href="#"><bean:message key="link.to_top" /></a>
          <br>
          <br>
<%
          if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
            <a name="photos"></a>
            <h1 class="green">
              <bean:message key="header.photos" />
              <a href="#" onclick="openHelp('pages/WebHelp/on-hand_reports/work_with_baggage_inventory_(oh).htm#upload photos');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <span class="reqfield">*</span>
            <bean:message key="Required" />
            <table class="form2_ohd" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
                    int k = 0;
%>
                    <logic:present name="OnHandForm" property="photoList">
                      <logic:iterate id="photo" name="OnHandForm" property="photoList" type="com.bagnet.nettracer.tracing.db.OHD_Photo">
<%
                        if (k % 3 == 0) {
%>
                          <tr align="center">
<%
                          }
%>
                          <td align=center>
                            <a href='showImage?ID=<bean:write name="photo" property="picpath"/>' target="top"><img src='showImage?ID=<bean:write name="photo" property="thumbpath"/>'></a>
                            <br>
                            <html:submit styleId="button" property="deletePhoto" indexed="true">
                              <bean:message key="button.delete_photo" />
                            </html:submit>
                          </td>
<%
                          if (k % 3 == 2) {
%>
                          </tr>
<%
                        }
                        k++;
%>
                      </logic:iterate>
                    </logic:present>
                  </tr>
                </table>
                <br>
                <center><input type="FILE" name="theFile1" />
                &nbsp;
                <html:submit property="uploadPhoto" styleId="button">
                  <bean:message key="header.addPhotos" />
                </html:submit>
              </td>
            </tr>
          </table>
          <br>
          <br>
          &nbsp;&nbsp;&uarr;
          <a href="#"><bean:message key="link.to_top" /></a>
          <br>
          <br>
<%
        }
%>
        <h1 class="green">
          <bean:message key="header.remarks" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_remarks_(oh).htm#add remarks');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <a name="remarks"></a>
        <table class="form2_ohd" cellspacing="0" cellpadding="0">
          <logic:iterate id="remark" indexId="i" name="OnHandForm" property="remarklist" type="com.bagnet.nettracer.tracing.db.Remark">
            <logic:equal name="remark" property="remarktype" value="<%= "" + TracingConstants.REMARK_REGULAR %>">
            	<logic:present name="remark" property="agent">
              		<bean:define id="agent" name="remark" property="agent" type="com.bagnet.nettracer.tracing.db.Agent" />
              	</logic:present>
              <tr>
                <td valign="top">
                  <a name='addremark<%= i %>'></a>
                  <bean:message key="colname.date" />
                  :
                  <bean:write name="remark" property="dispcreatetime" />
                </td>
                <td>
                  <bean:message key="colname.station" />
                  :
                  <logic:present name="remark" property="agent">
                  	<bean:write name="agent" property="companycode_ID" />
                  	&nbsp;
                  	<bean:write name="agent" property="station.stationcode" />
                  </logic:present>
                </td>
                <td>
                  <bean:message key="colname.agent" />
                  :
                  <logic:present name="remark" property="agent">
                  	<bean:write name="agent" property="username" />
                  </logic:present>
                </td>
              </tr>
              <tr>
                <td valign="top" colspan=3>
<%
                  
                  if (a.getGroup().getDescription().equalsIgnoreCase("Admin") || remark.getRemark_ID() == 0) {
%>
<%
                    String remarkDescription = "remark[" + i + "].remarktext";
                    String remarkText        = "this.form.elements['" + remarkDescription + "']";
                    String remarkText2       = "this.form.elements['" + remarkDescription + "2']";
%>
                    <textarea name="<%= remarkDescription %>" cols="80" rows="10" onkeydown="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);" onkeyup="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);"
                    <logic:equal name="OnHandForm" property="readonly" value="1"><% if (remark.getRemark_ID() > 0) {%> readonly="readonly"<% } %></logic:equal>
                    ><%= remark.getRemarktext() %> </textarea>
                    
                    <input name="<%= remarkDescription + "2" %>" type="text" value="1500" size="4" maxlength="4" disabled="true" />
                    <br>
                    <logic:notEqual name="OnHandForm" property="readonly" value="1">
                    <html:submit styleId="button" property="deleteRemark" indexed="true">
                      <bean:message key="button.delete_remark" />
                    </html:submit>
                    </logic:notEqual>
<%
                  } else {
%>
                    <bean:write name="remark" property="remarktext" />

<%
                  }
%>
                </td>
              </tr>
            </logic:equal>
          </logic:iterate>
        </table>
        <center><html:submit property="addremark" styleId="button">
          <bean:message key="button.add_remark" />
        </html:submit></center>
        <br>
        <br>
        &nbsp;&nbsp;&uarr;
        <a href="#"><bean:message key="link.to_top" /></a>
        <br>
        <br>
      </div>
      <logic:notEqual name="OnHandForm" property="readonly" value="1">
        <table width="100%" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center">
              <br>
              <logic:notEmpty name="OnHandForm" property="status">
<% if (a.getStation().getCompany().getVariable().getWt_enabled() == 1){ %>
                <html:submit styleId="button" property="savetracing" styleId="button" onclick="return validatereqWtOHDForm(this.form);">
                  <bean:message key="button.saveohd" />
                </html:submit>
<% } else { %>
 				<html:submit styleId="button" property="savetracing" styleId="button" onclick="return validatereqOHDForm(this.form);">
                  <bean:message key="button.saveohd" />
                </html:submit>
<% } %>

<%
				if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER, a)) {
					if (a.getStation().getCompany().getVariable().getWt_enabled() == 1){
						if (a.getStation().getCompany().getVariable().getWt_write_enabled() == 1){
							if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_OHD, a)){
%>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <logic:notEmpty name="OnHandForm" property="wt_id">
                 <logic:notEqual name="OnHandForm" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_CLOSED %>">
                  <html:submit styleId="button" property="closetowt" styleId="button" onclick="return validatereqOHDForm(this.form);">
                   <bean:message key="button.closetoWT" />
                  </html:submit>
                 </logic:notEqual>
                </logic:notEmpty>
                <logic:equal name="OnHandForm" property="wt_id" value="">
                 <logic:notEqual name="OnHandForm" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_CLOSED %>">
                  <html:submit styleId="button" property="savetowt" styleId="button" onclick="return validatereqWtOHDForm(this.form);">
                   <bean:message key="button.savetoWT" />
                  </html:submit>
                 </logic:notEqual>
                </logic:equal>
<%
							}
						}
					}
				}
%>
              </logic:notEmpty>
              <logic:empty name="OnHandForm" property="status">
                <input type="hidden" name="savetemp" value="">
                <input type="button" name="s" value="Save as Temporary" onclick="if (validatereqOHDForm(this.form)){ saveOHDTemporary(this.form)};" id="button">
                &nbsp;&nbsp;&nbsp;
                <html:submit styleId="button" property="savetracing" onclick="return validatereqOHDForm(this.form);">
                  <bean:message key="button.savetracingohd" />
                </html:submit>
              </logic:empty>
            </td>
          </tr>
        </table>
      </logic:notEqual>
      
      <logic:equal name="OnHandForm" property="readonly" value="1">
      <logic:equal name="OnHandForm" property="allow_remark_update" value="1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" valign="top">
              <br>
              <logic:notEmpty name="OnHandForm" property="status">
     <% if (a.getStation().getCompany().getVariable().getWt_enabled() == 1){ %>
                <html:submit styleId="button" property="savetracing" styleId="button" onclick="return validatereqWtOHDForm(this.form);">
                  <bean:message key="button.saveremark" />
                </html:submit>
     <% } else { %>
                <html:submit styleId="button" property="savetracing" styleId="button" onclick="return validatereqOHDForm(this.form);">
                  <bean:message key="button.saveremark" />
                </html:submit>
     <% } %>
              </logic:notEmpty>
            </td>
          </tr>
        </table>
      </logic:equal>
      </logic:equal>
    </html:form>
    <SCRIPT LANGUAGE="JavaScript">
      <!--

	// happens after load

	if (document.OnHandForm.manufacturer_ID.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
		document.getElementById("manu_other").style.visibility = "visible";

	} else {
		document.getElementById("manu_other").style.visibility = "hidden";
	}




<logic:present name="item" scope="request">
  document.location.href="#centralbag";
</logic:present>

<logic:present name="itinerary" scope="request">
  document.location.href="#itinerary";
</logic:present>

<logic:present name="passenger" scope="request">
  document.location.href="#passengerinfo";
</logic:present>

<logic:present name="remark" scope="request">
  document.location.href="#remarks";
</logic:present>

<logic:present name="upload" scope="request">
  document.location.href="#photos";
</logic:present>

//-->
    </SCRIPT>
