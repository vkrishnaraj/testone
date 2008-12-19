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
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    <!--
function gotoHistoricalReport() {
  o = document.OnHandForm;
	o.historical_report.value = "1";
	o.submit();
	
}
// -->
  </script>
  <html:form action="addOnHandBag.do" method="post">
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.add_on_hand_title" />
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
              <a href="#custitin"><span class="aa">&nbsp;
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
              <dd>
                <a href='viewMatches.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>'><span class="aa">&nbsp;
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
                  <span class="bb"><bean:message key="menu.ohd_history" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
            </logic:notEqual>
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
          <a name="baginfo"></a>
          <h1 class="green">
            <bean:message key="header.bag_info" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="colname.on_hand_report_number" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="ohd_id" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.status" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="status.description" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.found_station" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="found_company" />
                <bean:write name="OnHandForm" property="found_station" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.holding_station" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="holding_company" />
                <bean:write name="OnHandForm" property="holding_station" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.agent_initials" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="agent_initials" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.found_date_time" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="dispFoundTime" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.bag_arrived_date" />
                :
              </td>
              <td>
                <logic:empty name="OnHandForm" property="dispBagArriveDate">
                  &nbsp;
                </logic:empty>
                <logic:notEmpty name="OnHandForm" property="dispBagArriveDate">
                  <bean:write name="OnHandForm" property="dispBagArriveDate" />
                </logic:notEmpty>
              </td>
            </tr>
            <logic:notEmpty name="OnHandForm" property="status">
              <logic:equal name="OnHandForm" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_CLOSED %>">
                <tr>
                  <td>
                    <bean:message key="colname.file_close_date" />
                    :
                  </td>
                  <td>
                    <bean:write name="OnHandForm" property="dispCloseDate" />
                    &nbsp;
                  </td>
                </tr>
              </logic:equal>
            </logic:notEmpty>
            <tr>
              <td>
                <bean:message key="colname.storage_location" />
                :
              </td>
              <td>
                <logic:empty name="OnHandForm" property="storage_location">
                  &nbsp;
                </logic:empty>
                <logic:notEmpty name="OnHandForm" property="storage_location">
                  <bean:write name="OnHandForm" property="storage_location" />
                </logic:notEmpty>
                &nbsp;
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.bag_tag_number" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="bagTagNumber" />
                &nbsp;
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.airline_membership" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="companycode_ID" />
                &nbsp;
                <bean:write name="OnHandForm" property="membershipnum" />
                &nbsp;
                <bean:write name="OnHandForm" property="membershipstatus" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.pnr" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="pnr" />
                &nbsp;
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.color" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="bagColor" />
                &nbsp;
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.bagtype" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="bagType" />
                &nbsp;
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.x_desc" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="XDesc1" />
              </td>
            </tr>
            <tr>
              <td>
                &nbsp;
              </td>
              <td>
                <bean:write name="OnHandForm" property="XDesc2" />
              </td>
            </tr>
            <tr>
              <td>
                &nbsp;
              </td>
              <td>
                <bean:write name="OnHandForm" property="XDesc3" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.manufacturer" />
                :
              </td>
              <td>
                <bean:write name="OnHandForm" property="manufacturer" />
              </td>
            </tr>
          </table>
          <br>
          &nbsp;&nbsp;&uarr;
          <a href="#"><bean:message key="link.to_top" /></a>
          <br>
          <br>
          <a name="passengerinfo"></a>
          <h1 class="green">
            <bean:message key="header.passenger_info" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <table class="form2" cellspacing="0" cellpadding="0">
            <logic:iterate id="passenger" name="OnHandForm" property="passengerList" indexId="i" type="com.bagnet.nettracer.tracing.db.OHD_Passenger">
              <tr>
                <td>
                  <bean:message key="colname.passenger" />
                </td>
                <td>
                  <table class="form2" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                        <bean:message key="colname.pass_name" />
                        :
                      </td>
                      <td>
                        <bean:message key="colname.last_name" />
                        :
                        <br>
                        <bean:write name="passenger" property="lastname" />
                        &nbsp;
                      </td>
                      <td>
                        <bean:message key="colname.first_name" />
                        :
                        <br>
                        <bean:write name="passenger" property="firstname" />
                        &nbsp;
                      </td>
                      <td>
                        <bean:message key="colname.mid_initial" />
                        :
                        <br>
                        <bean:write name="passenger" property="middlename" />
                        &nbsp;
                      </td>
                    </tr>
                    <logic:present name="passenger" property="addresses">
                      <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.OHD_Address">
                        <tr>
                          <td>
                            <bean:message key="colname.street_addr" />
                            :
                          </td>
                          <td>
                            <bean:write name="addresses" property="address1" />
                            &nbsp;
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.street_addr" />
                            :
                          </td>
                          <td>
                            <bean:write name="addresses" property="address2" />
                            &nbsp;
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.city" />
                            :
                          </td>
                          <td>
                            <bean:write name="addresses" property="city" />
                            &nbsp;
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.state" />
                            :
                          </td>
                          <td>

                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.province" />
                            :
                          </td>
                          <td>
                            <html:text property='<%= "addresses[" + (i.intValue() * 20 + k.intValue()) + "].province" %>' size="25" maxlength="100" styleClass="textfield" />
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.zip" />
                            :
                          </td>
                          <td>
                            <bean:write name="addresses" property="zip" />
                            &nbsp;
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.country" />
                              :
                            </td>
                            <td>
                              <html:select name="addresses" property="countrycode_ID" disabled="true" styleClass="dropdown">
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
                              :
                            </td>
                            <td>
                              <bean:write name="addresses" property="homephone" />
                              &nbsp;
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.business_ph" />
                              :
                            </td>
                            <td>
                              <bean:write name="addresses" property="workphone" />
                              &nbsp;
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.mobile_ph" />
                              :
                            </td>
                            <td>
                              <bean:write name="addresses" property="mobile" />
                              &nbsp;
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.pager_ph" />
                              :
                            </td>
                            <td>
                              <bean:write name="addresses" property="pager" />
                              &nbsp;
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.alt_ph" />
                              :
                            </td>
                            <td>
                              <bean:write name="addresses" property="altphone" />
                              &nbsp;
                            </td>
                          </tr>
                          <tr>
                            <td>
                              <bean:message key="colname.email" />
                              :
                            </td>
                            <td>
                              <bean:write name="addresses" property="email" />
                              &nbsp;
                            </td>
                          </tr>
                          <tr>
                            <td colspan="4" bgcolor="white">
                              &nbsp;
                            </td>
                          </tr>
                        </logic:iterate>
                      </logic:present>
                    </table>
                  </td>
                </tr>
              </logic:iterate>
            </table>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
            <br>
            <br>
            <a name="custitin"></a>
            <h1 class="green">
              <bean:message key="header.bag_itinerary" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <span class="reqfield">*</span>
            <bean:message key="Required" />
            <table class="form2" cellspacing="0" cellpadding="0">
              <logic:iterate id="itinerarylist" name="OnHandForm" property="itinerarylist">
                <tr>
                  <td align="left">
                    <bean:message key="colname.fromto" />
                    :
                    <bean:write name="itinerarylist" property="legfrom" />
                    &nbsp;
                  </td>
                  <td align="left">
                    <bean:message key="colname.flightnum" />
                    :
                    <html:select name="itinerarylist" property="airline" styleClass="dropdown" indexed="true">
                      <html:option value="">
                        <bean:message key="select.please_select" />
                      </html:option>
                      <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                    </html:select>
                    &nbsp;
                    <html:text name="itinerarylist" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" />
                  </td>
                  <td align="left">
                    <bean:message key="colname.departdate" />
                    :
                    <bean:write name="itinerarylist" property="disdepartdate" />
                    &nbsp;
                  </td>
                  <td align="left">
                    <bean:message key="colname.arrdate" />
                    :
                    <bean:write name="itinerarylist" property="disarrivedate" />
                    &nbsp;
                  </td>
                </tr>
                <tr>
                  <td align="left">
                    <bean:message key="colname.schdeptime" />
                    :
                    <bean:write name="itinerarylist" property="disschdeparttime" />
                    &nbsp;
                  </td>
                  <td align="left">
                    <bean:message key="colname.scharrtime" />
                    :
                    <bean:write name="itinerarylist" property="disscharrivetime" />
                    &nbsp;
                  </td>
                  <td align="left">
                    <bean:message key="colname.actdeptime" />
                    :
                    <bean:write name="itinerarylist" property="disactdeparttime" />
                    &nbsp;
                  </td>
                  <td align="left">
                    <bean:message key="colname.actarrtime" />
                    :
                    <bean:write name="itinerarylist" property="disactarrivetime" />
                    &nbsp;
                  </td>
                </tr>
              </logic:iterate>
            </table>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
            <br>
            <br>
            <a name="centralbag"></a>
            <h1 class="green">
              <bean:message key="header.central_baggage_inventory" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <span class="reqfield">*</span>
            <bean:message key="Required" />
            <table class="form2" cellspacing="0" cellpadding="0">
              <logic:iterate indexId="i" id="itemlist" name="OnHandForm" property="itemlist">
                <tr>
                  <td>
                    <bean:message key="colname.category" />
                  </td>
                  <td>
                    <html:select name="itemlist" property="OHD_categorytype_ID" indexed="true" styleClass="dropdown" disabled="true">
                      <html:option value="">
                        <bean:message key="select.please_select" />
                      </html:option>
                      <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="categorytype" />
                    </html:select>
                  </td>
                </tr>
                <tr>
                  <td>
                    <bean:message key="colname.description" />
                    :
                  </td>
                  <td>
                    <bean:write name="itemlist" property="description" />
                    &nbsp;
                  </td>
                </tr>
              </logic:iterate>
            </table>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
            <br>
            <br>
            <h1 class="green">
              <bean:message key="header.remarks" />
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#NetTracer.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
            </h1>
            <a name="remarks"></a>
            <table class="form2" cellspacing="0" cellpadding="0">
              <logic:iterate id="remarklist" indexId="i" name="OnHandForm" property="remarklist">
                <tr>
                  <td valign="top">
                    <a name='addremark<%= i %>'></a>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="41%">
                          <bean:message key="colname.date" />
                          :
                          <bean:write name="remarklist" property="dispcreatetime" />
                        </td>
                        <bean:define id="agent" name="remarklist" property="agent" type="com.bagnet.nettracer.tracing.db.Agent" />
                        <td width="60%">
                          <bean:message key="colname.station" />
                          :
                          <bean:write name="agent" property="companycode_ID" />
                          &nbsp;
                          <bean:write name="agent" property="station.stationcode" />
                          &nbsp;&nbsp;&nbsp;&nbsp;
                          <bean:message key="colname.agent" />
                          :
                          <bean:write name="agent" property="username" />
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td valign="top">
                    <bean:write name="remarklist" property="remarktext" />
                    &nbsp;
                  </td>
                </tr>
              </logic:iterate>
            </table>
            <br>
            &nbsp;&nbsp;&uarr;
            <a href="#"><bean:message key="link.to_top" /></a>
<%
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
%>
              <br>
              <br>
              <a name="photos"></a>
              <h1 class="green">
                <bean:message key="header.photos" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#On_Hand_Bag.htm#Found_and_unclaimed');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <span class="reqfield">*</span>
              <bean:message key="Required" />
              <table class="form2" cellspacing="0" cellpadding="0">
                <tr align="center">
                  <logic:iterate id="photo" name="OnHandForm" property="photoList">
                    <td>
                      <a href='showImage?ID=<%= ((OHD_Photo)photo).getPicpath() %>' target="top"><img src="showImage?ID=<%= ((OHD_Photo)photo).getThumbpath() %>"></a>
                    </td>
                  </logic:iterate>
                </tr>
              </table>
              <br>
              &nbsp;&nbsp;&uarr;
              <a href="#"><bean:message key="link.to_top" /></a>
<%
            }
%>
            <br>
            <br>
            <center><INPUT Id="button" type="button" value="Back" onClick="history.back()"></center>
          </html:form>
