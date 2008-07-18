<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<script language=javascript>
  <!--

function toggledc(o) {
	o.changeservice.value = "1";

	document.getElementById("serviceleveldiv").innerHTML = "<br><bean:message key="ajax.please_wait" />";
			postForm("BDOForm", true, function (req) { 
				o.changeservice.value = "0";
				document.getElementById("serviceleveldiv").innerHTML = req.responseText; 

		});
		
	}

//-->
</script>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <tr>
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
          <logic:notEqual name="BDOForm" property="incident_ID" value="">
            <dd>
              <a href='lostDelay.do?incident_ID=<bean:write name="BDOForm" property="incident_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </logic:notEqual>
          <logic:notEqual name="BDOForm" property="OHD_ID" value="">
            <dd>
              <a href='addOnHandBag.do?ohd_ID=<bean:write name="BDOForm" property="OHD_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.ohd_main" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </logic:notEqual>
          <dd>
            <a href="#"><span class="aab">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bbb"><bean:message key="menu.bdo" /></span>
              <span class="ccb">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
        </dl>
      </div>
    </td>
  </tr>
  <!-- END ICONS MENU -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.bdo_general" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#bdo/bdo.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        
        <logic:present name="wt_id" scope="request">
        <p align="right">
        	WorldTracer ID: <bean:write  name="wt_id" scope="request"/> 
        </p>
        </logic:present>
        
        <span class="reqfield">*</span>
        <bean:message key="message.required" />
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <logic:present name="inserted" scope="request">
          <br>
          <center><font color=green>
            <bean:message key="prompt.bdo_insert_success" />
          </font></center>
        </logic:present>
        <br>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
          	<td nowrap>
              <bean:message key="colname.bdo_id_nobr" />
              <br>
              <html:text property="BDO_ID_ref" size="20" styleClass="textfield" readonly="true" disabled="true"/>
            </td>
          	<td nowrap>
              <bean:message key="colname.date" />
              <br>
              <html:text property="dispcreatetime" size="20" styleClass="textfield" readonly="true" />
            </td>

            <td nowrap>
              <bean:message key="colname.station" />
              <br>
              <html:text property="station.stationcode" size="2" styleClass="textfield" readonly="true" />
            </td>
            <td nowrap>
              <bean:message key="colname.airline" />
              <br>
              <html:text property="companycode_ID" size="2" styleClass="textfield" readonly="true" />
            </td>
          </tr>
          <tr>
          	<td nowrap>
              <bean:message key="colname.agent" />
              <br>
              <html:text property="agentinit" size="10" styleClass="textfield" readonly="true" />
            </td>
            <td nowrap>
              <bean:message key="colname.delivercompany_nobr" />
              <input type="hidden" name="changeservice" value="">
              <br>
              <html:select property="delivercompany_ID" styleClass="dropdown" onchange="toggledc(this.form);">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="delivercompanies" property="delivercompany.delivercompany_ID" labelProperty="delivercompany.name" />
              </html:select>
            </td>
            <td nowrap>
              <bean:message key="colname.servicelevel_nobr" />
              <br>
              <div id="serviceleveldiv">
              <html:select property="servicelevel_ID" styleClass="dropdown">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="servicelevels" property="servicelevel_ID" labelProperty="description" />
              </html:select>
              </div>
            </td>
            <td nowrap>
              <bean:message key="colname.deliverydate" />
              (<%= a.getDateformat().getFormat() %>)
              <br>
              <html:text property="dispdeliverydate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.BDOForm.dispdeliverydate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
          </tr>
        </table>
        <a name="contact"></a>
        <h1 class="green">
          <bean:message key="header.passenger_info" />
        </h1>
        <logic:iterate id="passenger" name="BDOForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan=5>
<%
                if (i.intValue() > 0) {
%>
                  <b><bean:message key="colname.addi_pass_info" />
                  :
<%
                } else {
%>
                  <b><bean:message key="colname.pri_pass_info" />
                  :
<%
                }
%>
              </td>
            </tr>
            <tr>
              <td nowrap colspan=2>
                <bean:message key="colname.last_name" />
                <br>
                <html:text name="passenger" property="lastname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
              </td>
              <td nowrap colspan=2>
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
<%
            if (i.intValue() == 0) {
%>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="passenger" property="address1" size="45" maxlength="50" indexed="true" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="passenger" property="address2" size="45" maxlength="50" indexed="true" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="passenger" property="city" indexed="true" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state" />
                  <br>
                  <html:select name="passenger" property="state_ID" indexed="true" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="statelist" property="value" labelProperty="label" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.province" />
                  <br>
                  <html:text name="passenger" property="province" indexed="true" size="15" maxlength="100" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text name="passenger" property="zip" indexed="true" size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="passenger" property="countrycode_ID" indexed="true" styleClass="dropdown">
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
                  (<%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="passenger" property="dispvalid_bdate" indexed="true" size="20" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar1<%= i %>" name="calendar1<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.BDOForm, '<%= "passenger[" + i + "].dispvalid_bdate" %>','calendar1<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td colspan=3>
                  <bean:message key="colname.valid_edate" />
                  (<%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="passenger" property="dispvalid_edate" indexed="true" size="20" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= i %>" name="calendar2<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.BDOForm, '<%= "passenger[" + i + "].dispvalid_edate" %>','calendar2<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
<%
            }
%>
            <tr>
              <td>
                <bean:message key="colname.home_ph" />
                <br>
                <html:text name="passenger" property="homephone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.business_ph" />
                <br>
                <html:text name="passenger" property="workphone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.mobile_ph" />
                <br>
                <html:text name="passenger" property="mobile" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.pager_ph" />
                <br>
                <html:text name="passenger" property="pager" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.alt_ph" />
                <br>
                <html:text name="passenger" property="altphone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
            </tr>
            <tr>
              <td colspan=2 width=50%>
                <bean:message key="colname.hotel" />
                <br>
                <html:text name="passenger" property="hotel" indexed="true" size="45" maxlength="50" styleClass="textfield" indexed="true" />
              </td>
              <td colspan=3 width=50%>
                <bean:message key="colname.email" />
                <br>
                <html:text name="passenger" property="email" indexed="true" size="45" maxlength="50" styleClass="textfield" />
              </td>
            </tr>
          </table>
        </logic:iterate>
        <br>
        <br>
        &nbsp;&nbsp;&uarr;
        <a href="#"><bean:message key="link.to_top" /></a>
        <br>
        <br>
        <a name="baginfo"></a>
        <h1 class="green">
          <bean:message key="header.bag_info" />
          (
          <bean:message key="colname.bagcount_nobr" />
          :
          <logic:equal name="BDOForm" property="incident_ID" value="">
            <logic:notEqual name="BDOForm" property="OHD_ID" value="">
              1
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="BDOForm" property="incident_ID" value="">
            <logic:equal name="BDOForm" property="choosebags" value="1">
              0
            </logic:equal>
            <logic:notEqual name="BDOForm" property="choosebags" value="1">
              <bean:write name="BDOForm" property="bagcount" />
            </logic:notEqual>
          </logic:notEqual>
          )
        </h1>
        <logic:equal name="BDOForm" property="choosebags" value="1">
          (
          <bean:message key="bdo.choose_bag_desc" />
          )
          <br>
          <br>
        </logic:equal>
        <logic:equal name="BDOForm" property="incident_ID" value="">
          <logic:notEqual name="BDOForm" property="OHD_ID" value="">
          <!-- on hand -->
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td valign=top>
                <b><bean:message key="colname.bag_number" />
                : &nbsp;&nbsp;1
              </td>
              <td valign=top colspan=2>
                <bean:message key="colname.bag_status" />
                <br>
                <html:text property="ohd.status.description" size="25" styleClass="textfield" readonly="true" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.last_name_onbag" />
                <br>
                <html:text property="ohd.lastname" size="30" maxlength="50" styleClass="textfield" readonly="true" />
              </td>
              <td>
                <bean:message key="colname.first_name_onbag" />
                <br>
                <html:text property="ohd.firstname" size="30" maxlength="50" styleClass="textfield" readonly="true" />
              </td>
              <td>
                <bean:message key="colname.mid_initial_onbag" />
                <br>
                <html:text property="ohd.middlename" size="1" maxlength="1" styleClass="textfield" readonly="true" />
              </td>
            </tr>
            <tr>
              <td nowrap colspan="3">
                <bean:message key="colname.color" />
                /
                <bean:message key="colname.bagtype" />
                <br>
                <html:text property="ohd.color" size="2" styleClass="textfield" readonly="true" />
                /
                <html:text property="ohd.type" size="2" styleClass="textfield" readonly="true" />
              </td>
            </tr>
          </table>
          <!-- eof on hand -->
        </logic:notEqual>
      </logic:equal>
      <logic:notEqual name="BDOForm" property="incident_ID" value="">
        <logic:iterate id="theitem" indexId="i" name="BDOForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
          <table class="form2" cellspacing="0" cellpadding="0">
            <logic:notEqual name="theitem" property="OHD_ID" value="">
              <logic:equal name="theitem" property="is_in_station" value="0">
                <tr>
                  <td valign=top colspan=3>
                    <b>(Note: This lost/delayed bag has been matched to on-hand bag
                    <bean:write name="theitem" property="OHD_ID" />
                    . However, this on-hand bag is currently not in the lost/delayed assigned station. Please
                    <A HREF='request_on_hand.do?ohd_ID=<bean:write name="theitem" property="OHD_ID"/>'>click here to request the bag</a>
                    .)
                  </td>
                </tr>
              </logic:equal>
              <logic:equal name="theitem" property="is_in_station" value="1">
                <tr>
                  <td valign=top colspan=3>
                    <b>(Note: This lost/delayed bag has been matched to on-hand bag
                    <bean:write name="theitem" property="OHD_ID" />
                    .)
                  </td>
                </tr>
              </logic:equal>
            </logic:notEqual>
            <tr>
              <td valign=top>
                <b><logic:equal name="BDOForm" property="choosebags" value="1">
                <!-- choose bag for bdo from mbr-->
                <input type="checkbox" name="bagchosen" value="<%= theitem.getBagnumber() %>">
              </logic:equal>
              <bean:message key="colname.bag_number" />
              : &nbsp;&nbsp;
              <%= theitem.getBagnumber() + 1 %>
            </td>
            <td valign=top>
              <bean:message key="colname.bag_status" />
              <br>
              <html:text name="theitem" property="status.description" size="25" styleClass="textfield" readonly="true" />
            </td>
            <td valign=top>
              <bean:message key="colname.claimnum" />
              <br>
              <html:text name="theitem" property="claimchecknum" size="25" styleClass="textfield" indexed="true" />
            </td>
            
            
          </tr>
          <tr>
            <td>
              <bean:message key="colname.last_name_onbag" />
              <br>
              <html:text name="theitem" property="lnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
            </td>
            <td>
              <bean:message key="colname.first_name_onbag" />
              <br>
              <html:text name="theitem" property="fnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
            </td>
            <td>
              <bean:message key="colname.mid_initial_onbag" />
              <br>
              <html:text name="theitem" property="mnameonbag" size="1" maxlength="1" styleClass="textfield" indexed="true" readonly="true" />
            </td>
          </tr>
          <tr>
            <td nowrap colspan="3">
              <bean:message key="colname.color" />
              /
              <bean:message key="colname.bagtype" />
              <br>
              <html:text name="theitem" property="color" indexed="true" size="2" styleClass="textfield" readonly="true" />
              /
              <html:text name="theitem" property="bagtype" indexed="true" size="2" styleClass="textfield" readonly="true" />
            </td>
          </tr>
        </table>
      </logic:iterate>
    </logic:notEqual>
    <br>
    <br>
    &nbsp;&nbsp;&uarr;
    <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center" valign="top">
        <br>
        <html:submit property="save" styleId="button">
          <bean:message key="button.bdo_send" />
        </html:submit>
        <logic:present name="showprint" scope="request">
        &nbsp;&nbsp;
        <input id="button" type="button" name="print" value="<bean:message key="button.bdo_sendprint" />" onclick="openReportWindow('bdo.do?receipt=1&bdo_id=<bean:write name="BDOForm" property="BDO_ID" />','BDOReceipt',800,600);return false;">
        </logic:present>
         <logic:present name="wt_id" scope="request">
             <html:submit styleId="button" property="savetowt" styleId="button" onclick="return validatereqBDOForm(this.form);">
                 <bean:message key="button.savetoWT" />
             </html:submit>
         </logic:present>
      </td>
    </tr>
  </table>
  <!-- ---------end of show bdo------------ -->
