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
<%@ page import="com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils" %>
<%@ page import="java.util.List" %>


<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.audit.Audit_OHD"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	


</SCRIPT>

<script language="javascript">
  
function goprev() {
  o = document.auditOHDForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.auditOHDForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.auditOHDForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

</script>
<%
  List compareList = (List)request.getAttribute("compareList");
  Agent a = (Agent) session.getAttribute("user");
%>
  <html:form action="audit_ohd.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.audit_ohd" />
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
              <a href="audit_ohd.do"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="Audit_On_hand" />
                  <br>
                  &nbsp;</span>
                <span class="ccb">&nbsp;
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
              <a href="audit_mbr.do"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="Audit_Mishandled_Report" />
                  <br>
                  &nbsp;</span>
                <span class="cc">&nbsp;
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
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <strong>
                    <bean:message key="colname.modifiedOn" />
                    :
                    <bean:write name="audit_ohd" property="displaytime_modified" />
                    (
                    <bean:write name="audit_ohd" property="modifying_agent.username" />
                    )
                  </strong>
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.holding_station" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="holdingStation.company.companyCode_ID" />
                  <bean:write name="audit_ohd" property="holdingStation.stationcode" />
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.file_close_date" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="dispCloseDate" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_TO_BE_INVENTORIED, a)) { %>
	            <tr>
	              <td>
	                <bean:message key="colname.inventory_date" />
	              </td>
	              <logic:iterate id="audit_ohd" name="compareList" scope="request">
	                <td>
	                  <bean:write name="audit_ohd" property="dispInventoryDate" />
	                  &nbsp;
	                </td>
	              </logic:iterate>
	            </tr>
            <% } %>
            <tr>
              <td>
                <bean:message key="colname.status" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:message name="audit_ohd" property="status.key" />
                </td>
              </logic:iterate>
            </tr>
            <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a)) { %>
            <tr>
              <td>
                <bean:message key="colname.posId" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="dispPosId" />
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.latecheck" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="dispLateCheckInd" />
                </td>
              </logic:iterate>
            </tr>
            <% } %>
            <tr>
              <td>
                <bean:message key="colname.matchedincident" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="matched_incident" />
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.name_on_bag" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:message key="colname.last_name" />
                  :
                  <bean:write name="audit_ohd" property="lastname" />
                  <br>
                  <bean:message key="colname.first_name" />
                  :
                  <bean:write name="audit_ohd" property="firstname" />
                  <br>
                  <bean:message key="colname.mid_initial" />
                  :
                  <bean:write name="audit_ohd" property="middlename" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.bag_tag_number" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="claimnum" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.pnr" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="record_locator" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.storage_location" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="storage_location" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.airline_membership" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <logic:notEmpty name="audit_ohd" property="membership">
                    <bean:message key="colname.company" />
                    :
                    <bean:write name="audit_ohd" property="membership.companycode_ID" />
                    <br>
                    <bean:message key="colname.membership_number" />
                    :
                    <bean:write name="audit_ohd" property="membership.membershipnum" />
                    <br>
                    <bean:message key="colname.membership_status" />
                    :
                    <bean:write name="audit_ohd" property="membership.membershipstatus" />
                    <br>
                  </logic:notEmpty>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.color" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="color" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.bagtype" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="type" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.x_desc" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:message name="audit_ohd" property="desc1Key" />
                  <br>
                  <bean:message name="audit_ohd" property="desc2Key" />
                  <br>
                  <bean:message name="audit_ohd" property="desc3Key" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.manufacturer" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <bean:write name="audit_ohd" property="manufacturer" />
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.passenger_info" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <logic:iterate id="passenger" name="audit_ohd" property="passengerList" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Passenger">
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
                    <logic:present name="passenger" property="addressList">
                      <logic:iterate indexId="k" name="passenger" id="address" property="addressList" type="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Address">
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
                        <bean:message key="colname.email" />
                        :
                        <bean:write name="address" property="email" />
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
                <bean:message key="header.bag_itinerary" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <logic:iterate id="itinerary" name="audit_ohd" property="itineraryList" indexId="i" type="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Itinerary">
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
                  </logic:iterate>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.central_baggage_inventory" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <logic:iterate indexId="i" id="item" name="audit_ohd" property="itemList" type="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Inventory">
                    <bean:message key="colname.category" />
                    :
                    <bean:write name="item" property="category" />
                    <br>
                    <bean:message key="colname.description" />
                    :
                    <bean:write name="item" property="description" />
                    <br>
                    <br>
                  </logic:iterate>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.photos" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <logic:iterate indexId="i" id="photo" name="audit_ohd" property="photoList" type="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Photo"><img src='showImage?ID=<bean:write name="photo" property="thumbpath"/>'><br>
                  </logic:iterate>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
            <tr>
            	<td>
                <bean:message key="header.ohd.fault" />
              </td>
                            <logic:iterate id="audit_ohd" name="compareList" scope="request">
                            <td>
                           
                <bean:message key="colname.faultstation" />
                    :
                     <%
                            if(((Audit_OHD)audit_ohd).getFaultstation_ID() != 0) {  	
                     %>
                    <%= StationBMO.getStation(((Audit_OHD)audit_ohd).getFaultstation_ID()).getStationcode() %>
                    <% } 
                        else {
                        	%>
					<bean:message key="none"/>
					<% } %>
                    <br />
                    <bean:message key="colname.closereport.losscode" />
                    :
                    <%
                            if(((Audit_OHD)audit_ohd).getLoss_code() != 0) {  	
                     %>
                    	<%= LossCodeBMO.getCode(((Audit_OHD)audit_ohd).getLoss_code()).getDescription() %>
                      <% } 
                        else {
                        	%>
                        	<bean:message key="none"/>
					<% } %>
                    </td>
              </logic:iterate>
            </tr>
            <tr>
              <td>
                <bean:message key="header.remarks" />
              </td>
              <logic:iterate id="audit_ohd" name="compareList" scope="request">
                <td>
                  <logic:iterate indexId="i" id="remark" name="audit_ohd" property="remarkList" type="com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Remark">
                    <% if(!remark.isSecure() ||  (remark.isSecure() && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a))){ %>
                  
                    <bean:write name="remark" property="readonlyremarktext" filter="false"/>
                    <br>
                    <br>
                    <% } %>
                  </logic:iterate>
                  &nbsp;
                </td>
              </logic:iterate>
            </tr>
          </table>
          <br>
          <center><INPUT type="button" Id="button" value="Back" onClick="history.back()"></center>
        </html:form>
