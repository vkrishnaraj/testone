<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils" %>
<%@ page import="java.util.List" %>


<%@page import="com.bagnet.nettracer.tracing.db.audit.Audit_Incident"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	


</SCRIPT>

<script language="javascript">
  
function goprev() {
  o = document.auditMBRForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditMBRForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditMBRForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

</script>
<%
	Agent a = (Agent)session.getAttribute("user");
	boolean hasCollectDlPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DRIVERS_LICENSE_COLLECT, a);
 	boolean hasViewEditDlPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DRIVERS_LICENSE_VIEW_EDIT, a);
 	
	boolean hasCollectPassportPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSPORT_COLLECT, a);
 	boolean hasViewEditPassportPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSPORT_VIEW_EDIT, a);
 	
%>
<html:form action="audit_mbr.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.audit_mbr" />
          (
          <bean:write name="incident_ID" scope="request" />
          )
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
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
          <dd>
            <a href="audit_ohd.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_On_hand" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_unchecked.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Unchecked_Property" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_mbr.do"><span class="aab">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bbb"><bean:message key="Audit_Mishandled_Report" />
                <br>
                &nbsp;</span>
              <span class="ccb">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_claims.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Claims" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_agent.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Agent" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_station.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Station" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_shift.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Shift" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_airport.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Airport" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_group.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Group" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_company.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Company" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
          <dd>
            <a href="audit_losscode.do"><span class="aa">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bb"><bean:message key="Audit_Loss_Code" />
                <br>
                &nbsp;</span>
              <span class="cc">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
        </dl>
      </div>
    </td>
  </tr>
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <strong>
                <bean:message key="colname.field" />
              </strong>
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <strong>
                  <bean:message key="colname.modifiedOn" />
                  :
                  <bean:write name="audit_incident" property="dispmodify_time" />
                  (
<% if (((Audit_Incident)audit_incident).getModify_agent() != null) { out.println(((Audit_Incident)audit_incident).getModify_agent().getUsername()); } else {out.println("Customer");}%>
                  
                  )
                </strong>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.status" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <bean:message name="audit_incident" property="status.key" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.file_close_date" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <bean:write name="audit_incident" property="dispclosedate" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.stationassigned" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <bean:write name="audit_incident" property="stationassigned.stationcode" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
          <td>
              <bean:message key="colname.agentassigned_nobr" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
              <c:choose>
              	<c:when test="${!empty audit_incident.agentassigned.username}" >
              		<bean:write name="audit_incident" property="agentassigned.username" />
              	</c:when>
              	<c:otherwise>&nbsp;</c:otherwise>
              </c:choose>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.non_revenue" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:match name="audit_incident" property="nonrevenue" value="1">
                	<bean:message key="select.yes" /> - <bean:write name="audit_incident" property="revenueCode" />
                </logic:match>
                <logic:match name="audit_incident" property="nonrevenue" value="0">
                	<bean:message key="select.no" />
                </logic:match>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.report_method" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:match name="audit_incident" property="reportmethod" value="0">
                	<bean:message key="select.in_person" />
                </logic:match>
                <logic:match name="audit_incident" property="reportmethod" value="1">
                	<bean:message key="select.bsophone" />
                </logic:match>
                <logic:match name="audit_incident" property="reportmethod" value="2">
                	<bean:message key="select.callcenter" />
                </logic:match>
                <logic:match name="audit_incident" property="reportmethod" value="3">
                	<bean:message key="select.online" />
                </logic:match>
                <logic:match name="audit_incident" property="reportmethod" value="4">
                	<bean:message key="select.kiosk" />
                </logic:match>
                <logic:greaterThan name="audit_incident" property="reportmethod" value="4">
                	<bean:write name="audit_incident" property="reportmethod" />
                </logic:greaterThan>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.recordlocator" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <bean:write name="audit_incident" property="recordlocator" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.ticket" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <bean:write name="audit_incident" property="ticketnumber" />
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="header.passenger_info" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:iterate id="passenger" name="audit_incident" property="passenger_list" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_Passenger">
                  <bean:message key="colname.last_name" />
                  :
                  <bean:write name="passenger" property="lastname" />
                  &nbsp;
                  <bean:message key="colname.first_name" />
                  :
                  <bean:write name="passenger" property="firstname" />
                  &nbsp;
                  <bean:message key="colname.mid_initial" />
                  :
                  <bean:write name="passenger" property="middlename" />
                  <br>
                  <logic:present name="passenger" property="address_list">
                    <logic:iterate indexId="k" name="passenger" id="address" property="address_list" type="com.bagnet.nettracer.tracing.db.audit.Audit_Address">
                      <bean:message key="colname.street_addr1" />
                      :
                      <bean:write name="address" property="address1" />
                      <br>
                      <bean:message key="colname.street_addr2" />
                      :
                      <bean:write name="address" property="address2" />
                      <br>
                      <bean:message key="colname.city" />
                      :
                      <bean:write name="address" property="city" />
                      <br>
                      <bean:message key="colname.state" />
                      :
                      <bean:write name="address" property="state" />
                      &nbsp;&nbsp;
                      <bean:message key="colname.province" />
                      :
                      <bean:write name="address" property="province" />
                      <br>
                      <bean:message key="colname.zip" />
                      :
                      <bean:write name="address" property="zip" />
                      <bean:message key="colname.country" />
                      :
                      <bean:write name="address" property="country" />
                      <br>
                      <bean:message key="colname.home_ph" />
                      :
                      <bean:write name="address" property="homephone" />
                      <bean:message key="colname.business_ph" />
                      :
                      <bean:write name="address" property="workphone" />
                      <br>
                      <bean:message key="colname.mobile_ph" />
                      :
                      <bean:write name="address" property="mobile" />
                      <bean:message key="colname.pager_ph" />
                      :
                      <bean:write name="address" property="pager" />
                      <br>
                      <bean:message key="colname.alt_ph" />
                      :
                      <bean:write name="address" property="altphone" />
                      <br>
                      <bean:message key="colname.email" />
                      :
                      <bean:write name="address" property="email" />
                      <% if (hasCollectDlPermission) { %>
	                      <br>
	                      <bean:message key="colname.drivers" />
	                      :
	                      <% if (hasViewEditDlPermission) { %>
	                      	<bean:write name="passenger" property="decriptedDriversLicense" />
	                      <% } else { %>
	                      	<bean:write name="passenger" property="redactedDriversLicense" />
	                      <% } %>
	                      <br>
	                      <bean:message key="colname.state" />
	                      :
	                      <bean:write name="passenger" property="dlstate" />
	                      <br>
	                      <bean:message key="colname.province" />
	                      :
	                      <bean:write name="passenger" property="driversLicenseProvince" />
	                      <br>
	                      <bean:message key="colname.country_of_issue" />
	                      :
	                      <bean:write name="passenger" property="driversLicenseCountry" />
                      <% } %>
                      <% if (hasCollectPassportPermission) { %>
                      	<br>
                      	<bean:message key="colname.common_num" />
                      	:
                      	<% if (hasViewEditPassportPermission) { %>
                      		<bean:write name="passenger" property="decryptedPassportNumber" />
                      	<% } else { %>
                      		<bean:write name="passenger" property="redactedPassportNumber" />
                      	<% } %>
                      	<br>
                      	<bean:message key="colname.country" />
                      	:
                      	<bean:write name="passenger" property="passportIssuer" />
                      <% } %>
                    </logic:iterate>
                  </logic:present>
                  <br>
                  <br>
                </logic:iterate>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="header.checked_bag_info" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <bean:message key="colname.num_pass" />
                :
                <bean:write name="audit_incident" property="numpassengers" />
                <br>
                <bean:message key="colname.num_bag_checked" />
                :
                <bean:write name="audit_incident" property="numbagchecked" />
                <br>
                <bean:message key="colname.bags_rec" />
                :
                <bean:write name="audit_incident" property="numbagreceived" />
                <br>
                <bean:message key="colname.bag_loc" />
                :
                <logic:equal name="audit_incident" property="checkedlocation" value="1">
                  <bean:message key="select.curb_side" />
                </logic:equal>
                <logic:equal name="audit_incident" property="checkedlocation" value="2">
                  <bean:message key="select.ticket_counter" />
                </logic:equal>
                <logic:equal name="audit_incident" property="checkedlocation" value="3">
                  <bean:message key="select.gate" />
                </logic:equal>
                <logic:equal name="audit_incident" property="checkedlocation" value="4">
                  <bean:message key="select.remote" />
                </logic:equal>
                <logic:equal name="audit_incident" property="checkedlocation" value="5">
                  <bean:message key="select.plane_side" />
                </logic:equal>
                <logic:equal name="audit_incident" property="checkedlocation" value="6">
                  <bean:message key="select.unchecked" />
                </logic:equal>
                <logic:equal name="audit_incident" property="checkedlocation" value="7">
                  <bean:message key="select.kiosk" />
                </logic:equal>
                <br>
                <bean:message key="colname.courtesy_report" />
                :
                <logic:equal name="audit_incident" property="courtesyreport" value="0">
                  <bean:message key="select.no" />
                </logic:equal>
                <logic:equal name="audit_incident" property="courtesyreport" value="1">
                  <bean:message key="select.yes" />
                </logic:equal>
                <br>
                <bean:message key="colname.tsa" />
                :
                <logic:equal name="audit_incident" property="tsachecked" value="0">
                  <bean:message key="select.no" />
                </logic:equal>
                <logic:equal name="audit_incident" property="tsachecked" value="1">
                  <bean:message key="select.yes" />
                </logic:equal>
                <br>
                <bean:message key="colname.custom" />
                :
                <logic:equal name="audit_incident" property="customcleared" value="0">
                  <bean:message key="select.no" />
                </logic:equal>
                <logic:equal name="audit_incident" property="customcleared" value="1">
                  <bean:message key="select.yes" />
                </logic:equal>
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="header.itinerary" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:iterate id="itinerary" name="audit_incident" property="itinerary_list" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_Itinerary">
                  <logic:equal name="itinerary" property="itinerarytype" value="0">
                    <bean:message key="colname.fromto" />
                    :
                    <bean:write name="itinerary" property="legfrom" />
                    /
                    <bean:write name="itinerary" property="legto" />
                    &nbsp;
                    <bean:message key="colname.flightnum" />
                    :
                    <bean:write name="itinerary" property="airline" />
                    /
                    <bean:write name="itinerary" property="flightnum" />
                    <br>
                    <bean:message key="colname.departdate" />
                    :
                    <bean:write name="itinerary" property="disdepartdate" />
                    <br>
                    <bean:message key="colname.arrdate" />
                    :
                    <bean:write name="itinerary" property="disarrivedate" />
                    <br>
                    <bean:message key="colname.schdeptime" />
                    :
                    <bean:write name="itinerary" property="disschdeparttime" />
                    <br>
                    <bean:message key="colname.scharrtime" />
                    :
                    <bean:write name="itinerary" property="disscharrivetime" />
                    <br>
                    <bean:message key="colname.actdeptime" />
                    :
                    <bean:write name="itinerary" property="disactdeparttime" />
                    <br>
                    <bean:message key="colname.actarrtime" />
                    :
                    <bean:write name="itinerary" property="disactarrivetime" />
                    <br>
                    <br>
                  </logic:equal>
                </logic:iterate>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
          <% if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_DELIVERY_INSTRUCTIONS)) {%>
          <tr>
          	<td>
              <bean:message key="header.delivery_instructions" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
              	<bean:write name="audit_incident" property="instructions" />
              </td>
            </logic:iterate>
          </tr>
          <% } %>
          <tr>
            <td>
              <bean:message key="header.bag_itinerary" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:iterate id="itinerary" name="audit_incident" property="itinerary_list" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_Itinerary">
                  <logic:equal name="itinerary" property="itinerarytype" value="1">
                    <bean:message key="colname.fromto" />
                    :
                    <bean:write name="itinerary" property="legfrom" />
                    /
                    <bean:write name="itinerary" property="legto" />
                    &nbsp;
                    <bean:message key="colname.flightnum" />
                    :
                    <bean:write name="itinerary" property="airline" />
                    /
                    <bean:write name="itinerary" property="flightnum" />
                    <br>
                    <bean:message key="colname.departdate" />
                    :
                    <bean:write name="itinerary" property="disdepartdate" />
                    <br>
                    <bean:message key="colname.arrdate" />
                    :
                    <bean:write name="itinerary" property="disarrivedate" />
                    <br>
                    <bean:message key="colname.schdeptime" />
                    :
                    <bean:write name="itinerary" property="disschdeparttime" />
                    <br>
                    <bean:message key="colname.scharrtime" />
                    :
                    <bean:write name="itinerary" property="disscharrivetime" />
                    <br>
                    <bean:message key="colname.actdeptime" />
                    :
                    <bean:write name="itinerary" property="disactdeparttime" />
                    <br>
                    <bean:message key="colname.actarrtime" />
                    :
                    <bean:write name="itinerary" property="disactarrivetime" />
                    <br>
                    <br>
                  </logic:equal>
                </logic:iterate>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
          <logic:present name="ld" scope="request">
            <tr>
              <td>
                <bean:message key="colname.claimnum" />
              </td>
              <logic:iterate id="audit_incident" name="compareList" scope="request">
                <td>
                  <logic:iterate id="claimcheck" name="audit_incident" property="claimcheck_list" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_Incident_Claimcheck">
                    <bean:message key="colname.claimnum" />
                    :
                    <bean:write name="claimcheck" property="claimchecknum" />
                    <br>
                    <bean:message key="colname.matched_ohd" />
                    :
                    <bean:write name="claimcheck" property="OHD_ID" />
                    <br>
                    <br>
                  </logic:iterate>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
          </logic:present>
          
          <tr>
            <td>
              <bean:message key="header.bag_info" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:iterate id="theitem" name="audit_incident" property="itemlist" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_Item">
                  <logic:notEmpty name="theitem">
                    <logic:notPresent name="ld" scope="request">
                      <bean:message key="colname.claimnum" />
                      :
                      <bean:write name="theitem" property="claimchecknum" />
                      <br>
                    </logic:notPresent>
                    <logic:present name="ld" scope="request">
                      <bean:message key="colname.matched_ohd" />
                      :
                      <bean:write name="theitem" property="OHD_ID" />
                      <br>
                    </logic:present>
                    <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EXPEDITE_TAG_NUM_COLLECT, a)) { %>
	                    <bean:message key="colname.expedite.tagnum" />
	                    :
	                    <bean:write name="theitem" property="expediteTagNum" />
	                    <br>
                    <% } %>
                    <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a)) { %>
	                    <bean:message key="colname.posId" />
	                    :
	                    <bean:write name="theitem" property="posId" />
	                    <br>
                    <% } %>
                    <bean:message key="colname.name_on_bag" />
                    :
                    <bean:write name="theitem" property="lnameonbag" />
                    ,
                    <bean:write name="theitem" property="fnameonbag" />
                    <bean:write name="theitem" property="mnameonbag" />
                    <br>
                    <bean:message key="colname.color" />
                    :
                    <bean:write name="theitem" property="color" />
                    <br>
                    <bean:message key="colname.bagtype" />
                    :
                    <bean:write name="theitem" property="bagtype" />
                    <br>
                    <bean:message key="colname.x_desc" />
                    :
                    <br>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message name="theitem" property="xdescelement1Key" />
                    <br>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message name="theitem" property="xdescelement2Key" />
                    <br>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <bean:message name="theitem" property="xdescelement3Key" />
                    <br>
                    <bean:message key="colname.manufacturer" />
                    :
                    <bean:write name="theitem" property="manufacturer" />
                    <br>
                    <bean:message key="colname.key_contents" />:
                    <br>
                    <logic:iterate id="inventories" name="theitem" property="inventorylist" type="com.bagnet.nettracer.tracing.db.audit.Audit_Item_Inventory">
                    &nbsp;&nbsp;<bean:write name="inventories"  property="description" /><br>
                    </logic:iterate>
                    <br>
                    <bean:message key="colname.bag_status" />
                    :
                    <bean:message name="theitem" property="status.key" />
                    <logic:present name="damaged" scope="request">
                      <br>
                      <bean:message key="colname.damagedesc" />
                      :
                      <bean:write name="theitem" property="damage" />
                      <br>
                      <bean:message key="colname.lvldamage" />
                      :
                      <bean:write name="theitem" property="lvlofdamage" />
                      <br>
                      <bean:message key="colname.cost" />
                      :
                      <bean:write name="theitem" property="discost" />
                      <bean:write name="theitem" property="currency_ID" />
                      <br>
                      <bean:message key="colname.resolutiondesc" />
                      :
                      <bean:write name="theitem" property="resolutiondesc" />
                      <br>
                      <bean:message key="header.photos" />
                      :
                      <br>
                      <logic:iterate indexId="j" id="photo" name="theitem" property="photolist" type="com.bagnet.nettracer.tracing.db.audit.Audit_Item_Photo"><img src='showImage?ID=<bean:write name="photo" property="thumbpath"/>'><br>
                      </logic:iterate>
                    </logic:present>
                    <br>
                    <br>
                    </logic:notEmpty>
                </logic:iterate>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
          <logic:present name="missing" scope="request">
            <tr>
              <td>
                <bean:message key="header.ma" />
              </td>
              <logic:iterate id="audit_incident" name="compareList" scope="request">
                <td>
                  <logic:iterate id="article" indexId="i" name="audit_incident" property="article_list" type="com.bagnet.nettracer.tracing.db.audit.Audit_Articles">
                    <bean:message key="colname.article" />
                    :
                    <bean:write name="article" property="article" />
                    <br>
                    <bean:message key="colname.purchase_date" />
                    :
                    <bean:write name="article" property="dispurchasedate" />
                    <bean:message key="colname.cost" />
                    :
                    <bean:write name="article" property="discost" />
                    <bean:write name="article" property="currency_ID" />
                    <br>
                    <bean:message key="colname.desc" />
                    :
                    <bean:write name="article" property="description" />
                    <br>
                    <br>
                  </logic:iterate>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
          </logic:present>
          <tr>
            <td>
              <bean:message key="header.remarks" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:iterate indexId="i" id="remark" name="audit_incident" property="remark_list" type="com.bagnet.nettracer.tracing.db.audit.Audit_Remark">
                  <logic:equal name="remark" property="remarktype" value="<%= "" + TracingConstants.REMARK_REGULAR %>">
                    <logic:notEqual name="remark" property="remarktext" value="">
                      <bean:message key="colname.agent" />
                      :
                      <bean:write name="remark" property="agentUsername" />
                      <br>
                      <bean:write name="remark" property="readonlyremarktext" filter="false"/>
                      <br>
                      <br>
                    </logic:notEqual>
                  </logic:equal>
                </logic:iterate>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
          <tr>
            <td>
              <bean:message key="header.close" />
            </td>
            <logic:iterate id="audit_incident" name="compareList" scope="request">
              <td>
                <logic:equal name="audit_incident" property="status.status_ID" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                  <logic:present name="ld" scope="request">
                    <logic:iterate id="theitem" indexId="i" name="audit_incident" property="itemlist" type="com.bagnet.nettracer.tracing.db.audit.Audit_Item">
                      <bean:message key="colname.bag_number" />
                      :
                      <%= theitem.getBagnumber() + 1 %>
                      <br>
                      <bean:message key="colname.arr_airline_id" />
                      :
                      <bean:write name="theitem" property="arrivedonairline_ID" />
                      <br>
                      <bean:message key="colname.arr_airline_id" />
                      :
                      <bean:write name="theitem" property="arrivedonairline_ID" />
                      <br>
                      <bean:message key="colname.arr_date" />
                      :
                      <bean:write name="theitem" property="disarrivedondate" />
                    </logic:iterate>
                  </logic:present>
                  <bean:message key="colname.faultcompany" />
                  :
                  <bean:write name="audit_incident" property="faultstation.company.companyCode_ID" />
                  <br>
                  <bean:message key="colname.faultstation" />
                  :
                  <bean:write name="audit_incident" property="faultstation.stationcode" />
                  <br>
                  <bean:message key="colname.losscode" />
                  :
                  <bean:write name="audit_incident" property="loss_code" />
                  <br>
                  <br>
                  <bean:message key="header.remarks" />
                  :
                  <br>
                  <logic:iterate id="remark" indexId="i" name="audit_incident" property="remark_list" type="com.bagnet.nettracer.tracing.db.audit.Audit_Remark">
                    <logic:equal name="remark" property="remarktype" value="<%= "" + TracingConstants.REMARK_CLOSING %>">
                      <logic:notEqual name="remark" property="remarktext" value="">
                        <bean:message key="colname.agent" />
                        :
                        <bean:write name="remark" property="agentUsername" />
                        <br>
                        <bean:write name="remark" property="readonlyremarktext" filter="false"/>
                        <br>
                        <br>
                      </logic:notEqual>
                    </logic:equal>
                  </logic:iterate>
                </logic:equal>
                &nbsp;
              </td>
            </logic:iterate>
          </tr>
        </table>
      </html:form>
