<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	
	function showmanu(o) {
		if (o.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
			document.getElementById("manu_other").style.visibility = "visible";
		} else {
			document.getElementById("manu_other").style.visibility = "hidden";
		}
	}
	

  </SCRIPT>
  
  <SCRIPT LANGUAGE="JavaScript">
    function textCounter2(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

  </SCRIPT>
  <html:form action="addOnHandBag.do" method="post" enctype="multipart/form-data" onsubmit="return validateRest(this);">
    <input type="hidden" name="express" value="1">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.express_add_on_hand_title" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="/pages/includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    
    
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <a name="baginfo"></a>
          <h1 class="green">
            <bean:message key="header.bag_info" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/creating_express_on-hand_bag_reports.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2_ohd" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.bag_arrived_date" />
                (
                <%= a.getDateformat().getFormat() %>)
                <br>
                <html:text property="dispBagArriveDate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar5" name="calendar5" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.OnHandForm.dispBagArriveDate,'calendar5','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              <td>
                <bean:message key="colname.found_date_time" />
                <br>
                <html:text property="dispFoundTime" size="14" styleClass="textfield" disabled="true" />
              </td>
              <td>
                <bean:message key="colname.found_station" />
                <br>
                <input type=text size=10 class="textfield" value="<bean:write name="OnHandForm" property="found_company"/> <bean:write name="OnHandForm" property="found_station"/>" disabled>
              </td>
            </tr>
            <tr>
            	
            	<td valign="top">
                <bean:message key="colname.bag_tag_number" />
                <br>
                <html:text property="bagTagNumber" size="14" maxlength="13" styleClass="textfield" />
              </td>
              
              <td valign=top>
                <bean:message key="colname.color" />
                 <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=3&type=bagColor',800,30,230);return false;"><bean:message key="chart3" /></a>
                <br>
                <html:select property="bagColor" styleClass="dropdown">
                  <html:options collection="colorlist" property="value" labelProperty="label" />
                </html:select>
                <br>
                <br>
                <bean:message key="colname.bagtype" />
                <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=1&type=bagType',800,280);return false;"><bean:message key="chart1" /></a>
                <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=2&type=bagType',800,370);return false;"><bean:message key="chart2" /></a>
                <br>
                <html:select property="bagType" styleClass="dropdown">
                  <html:options collection="typelist" property="value" labelProperty="label" />
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
          <a name="passengerinfo"></a>
          <h1 class="green">
            <bean:message key="header.passenger_info" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_passenger_information_(oh).htm#add passenger info');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <table class="form2_ohd" cellspacing="0" cellpadding="0">
            <logic:iterate id="passenger" name="OnHandForm" property="passengerList" indexId="i" type="com.bagnet.nettracer.tracing.db.OHD_Passenger">
              <tr>
                <td colspan=2>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text name="passenger" property="lastname" size="30" maxlength="30" indexed="true" styleClass="textfield" />
                </td>
                <td colspan=2>
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text name="passenger" property="firstname" size="30" maxlength="30" indexed="true" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text name="passenger" property="middlename" size="4" maxlength="1" indexed="true" styleClass="textfield" />
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
                    <td colspan=2>
                      <bean:message key="colname.pager_ph" />
                      <br>
                      <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].pager" %>' size="15" maxlength="25" styleClass="textfield" />
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
            </logic:iterate>
          </table>
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
                  <html:select name="itinerarylist" property="legfrom" styleClass="dropdown" indexed="true">
                    <html:option value=""></html:option>
                    <html:options collection="allstationlist" property="stationcode" labelProperty="stationcode" />
                  </html:select>
                  /
                  <html:select name="itinerarylist" property="legto" styleClass="dropdown" indexed="true">
                    <html:option value=""></html:option>
                    <html:options collection="allstationlist" property="stationcode" labelProperty="stationcode" />
                  </html:select>
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
                  <%= a.getDateformat().getFormat() %>)
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
                    <bean:message key="button.delete_ohd_itinerary" />
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
                    <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.description" />
                  <br>
                  <html:text name="itemlist" property="description" size="70" maxlength="255" styleClass="textfield" indexed="true" />
                </td>
                <td align="center">&nbsp;<br>
                  <html:submit styleId="button" property="deleteItem" indexed="true" onclick="return checkOhdDeleteCount();">
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
        </div>
        <table class="form2_ohd" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center">
              <br>
              <input type="hidden" name="savetemp" value="">
              <input type="button" name="s" value="Save as Temporary" onclick="saveOHDTemporary(this.form);" id="button">
              &nbsp;&nbsp;&nbsp;
              <html:submit styleId="button" property="savetracing">
                <bean:message key="button.savetracingohd" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
      <script language="javascript">
        



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


      </script>
