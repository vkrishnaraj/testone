<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  boolean collectPosId = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a);
  boolean invDate=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_TO_BE_INVENTORIED, a);
%>
  
  <%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

  </SCRIPT>
  
  <script language="javascript">
    
function goprev() {
  o = document.searchIncidentForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchIncidentForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchIncidentForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

function sortSearchOhd(sortOrder) {
	o = document.searchIncidentForm;
	o.sort.value = sortOrder;
	o.submit();
}
  </script>
  <html:form action="searchOnHand.do" method="post" onsubmit="fillzero(this.incident_ID, 13); return true;">
  <%
    String sort = (String)request.getAttribute("sort");

    if (sort != null && sort.length() > 0) {
%>
      <input type=hidden name=sort value='<%= sort %>'>
<%
    } else {
%>
      <input type=hidden name=sort value="<%=TracingConstants.SortParam.OHD_NUM.getParamString()%>">
<%
    }
%>
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.search_on_hand" />
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
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color="red">
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.on_hand_report_number" />
                  <br>
                  <html:text property="incident_ID" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td>
                  <bean:message key="colname.ohd_status" />
                  <br>
                  <html:select property="status_ID" styleClass="dropdown">
                    <html:option value="<%= Integer.toString(TracingConstants.OHD_STATUS_ALL) %>">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:option value="<%= Integer.toString(TracingConstants.OHD_STATUS_ACTIVE) %>">
                      <bean:message key="select.all_active" />
                    </html:option>
                    <html:options collection="ohdStatusList" property="status_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.bag_tag_number" />
                  <br>
                  <html:text property="ticketnumber" size="14" maxlength="13" styleClass="textfield" />
                </td>
                
                <td nowrap="nowrap">
                  <bean:message key="colname.flightnum" />
                  <br>
                  <html:select property="airline" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                  &nbsp;
                  <html:text property="flightnum" size="20" maxlength="4" styleClass="textfield" />
                </td>

              </tr>
              <tr>
                <td>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text property="lastname" size="25" maxlength="25" styleClass="textfield" />
                </td>
                <td colspan="2">
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text property="firstname" size="25" maxlength="25" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text property="middlename" size="25" maxlength="1" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.found_company" />
                  <br>
                  <html:select property="companycreated_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.found_station_nobr" />
                  <br>
                  <html:select property="stationcreated_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.holding_company" />
                  <br>
                  <html:select property="companycode_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.holding_station_nobr" />
                  <br>
                  <html:select property="stationassigned_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td <% if (!collectPosId) { %>colspan="2"<% } %>>
                  <bean:message key="colname.date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td colspan="2">
                  <bean:message key="colname.ohd_create_agent" />
                  <br>
                  <html:text property="agent" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <% if (collectPosId) { %>
                	<td>
                		<bean:message key="colname.posId" />
                		<br>
                		<html:text property="posId" size="8" maxlength="8" styleClass="textfield" />
                	</td>
                <% } %>
              </tr>
              
              <tr>
              	<% System.out.println("Inv Date permission: "+invDate);
              	if(invDate) {%>
		              <td>
		              	  <bean:message key="colname.inventory.date"/>
		                  (
		                  <%= a.getDateformat().getFormat() %>)
		                  <br/>
		              	  <html:text property="s_inventorydate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3" name="calendar3" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.s_inventorydate,'calendar3','<%= a.getDateformat().getFormat() %>'); return false;">-
	                      <html:text property="e_inventorydate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4" name="calendar4" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.e_inventorydate,'calendar4','<%= a.getDateformat().getFormat() %>'); return false;">
	                
		              </td>
	              <% } %>
	              <td colspan="2">
	              	  <bean:message key="colname.routing.date"/>
	                  (
	                  <%= a.getDateformat().getFormat() %>)
	                  <br/>
	              	  <html:text property="routingdate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar5" name="calendar5" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.routingdate,'calendar5','<%= a.getDateformat().getFormat() %>'); return false;">
                  	  
	              </td>
	              <td colspan="<%=invDate?"1":"2"%>">
	              	  <bean:message key="colname.routing.station"/>
	                  <br>
	                  <html:text property="routingstation" size="3" maxlength="3" styleClass="textfield" />
	              </td>
              </tr>
              <tr>
                <td colspan="4" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;
                  <html:button styleId="button" property="reset" onclick="document.location.href='searchOnHand.do';">
                    <bean:message key="button.reset" />
                  </html:button>
                </td>
              </tr>
            </table>
            <logic:present name="onhandlist" scope="request">
            
            <div id="pageheaderleft" style="width:400px;">
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
            </div>
           <%
              if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_QUERY_REPORTS, a)) {
            %>
            <div id="pageheaderright">
              <select class="dropdown" name="outputtype">
                <% if (!TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                  <option value="0" selected="selected"><bean:message key="radio.pdf" /></option>
                <% } %>
                <option value="1"><bean:message key="radio.html" /></option>
              </select>
              <input type="submit" name="generateReport" id="button" value="<bean:message key="button.generateReport" />">
              <logic:present name="reportfile" scope="request">
                <script language="javascript">
                    openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);
                </script>
              </logic:present>
            </div>
            <%
              }
            %>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_NUM.getParamString()%>" scope="request">
                <td>
                  <strong>
                     <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_NUM.getParamString()%>');"><bean:message key="colname.on_hand_report_number" /></a>
                  </strong>
                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_NUM.getParamString()%>" scope="request">
                <td>
                  <strong>
                     <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_NUMREV.getParamString()%>');"><bean:message key="colname.on_hand_report_number" /></a>
                  </strong>
                </td>
                </logic:equal>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_DATE.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_DATE.getParamString()%>');"><bean:message key="colname.ohd_create_date" /></a>
                  </strong>
                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_DATE.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_DATEREV.getParamString()%>');"><bean:message key="colname.ohd_create_date" /></a>
                  </strong>
                </td>
                </logic:equal>
                <logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_BAGTAG.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_BAGTAG.getParamString()%>');"><bean:message key="colname.bag_tag_number" /></a>
                  </strong>
                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_BAGTAG.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_BAGTAGREV.getParamString()%>');"><bean:message key="colname.bag_tag_number" /></a>
                  </strong>
                </td>
                </logic:equal>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_STATUS.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_STATUS.getParamString()%>');"><bean:message key="header.status" /></a>
                  </strong>
                </td>
                </logic:notEqual>
              	<logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_STATUS.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_STATUSREV.getParamString()%>');"><bean:message key="header.status" /></a>
                  </strong>
                </td>
                </logic:equal>
                <logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_COLOR.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_COLOR.getParamString()%>');"><bean:message key="colname.color" /></a>
                  </strong>
                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_COLOR.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_COLORREV.getParamString()%>');"><bean:message key="colname.color" /></a>
                  </strong>
                </td>
                </logic:equal>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_TYPE.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_TYPE.getParamString()%>');"><bean:message key="colname.bagtype" /></a>
                  </strong>
                </td>
                </logic:notEqual>
              	<logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_TYPE.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_TYPEREV.getParamString()%>');"><bean:message key="colname.bagtype" /></a>
                  </strong>
                </td>
                </logic:equal>
                <logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_AIRLINEFOUND.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_AIRLINEFOUND.getParamString()%>');"><bean:message key="colname.found_company" /></a>
                  </strong>
                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_AIRLINEFOUND.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_AIRLINEFOUNDREV.getParamString()%>');"><bean:message key="colname.found_company" /></a>
                  </strong>
                </td>
                </logic:equal>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_STATIONFOUND.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_STATIONFOUND.getParamString()%>');" id="foundStation"><bean:message key="colname.found_station" /></a>
                  </strong>
                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_STATIONFOUND.getParamString()%>" scope="request">
                <td>
                  <strong>
                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_STATIONFOUNDREV.getParamString()%>');" id="foundStation"><bean:message key="colname.found_station" /></a>
                  </strong>
                </td>
                </logic:equal>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_HOLDSTATION.getParamString()%>" scope="request">
	                <td>
	                  <strong>
	                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_HOLDSTATION.getParamString()%>');" id="holdStation"><bean:message key="colname.holding_station" /></a>
	                  </strong>
	                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_HOLDSTATION.getParamString()%>" scope="request">
	                <td>
	                  <strong>
	                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_HOLDSTATIONREV.getParamString()%>');" id="holdStation"><bean:message key="colname.holding_station" /></a>
	                  </strong>
	                </td>
                </logic:equal>
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_NAME.getParamString()%>" scope="request">
	                <td>
	                  <strong>
	                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_NAME.getParamString()%>');"><bean:message key="colname.name" /></a>
	                  </strong>
	                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_NAME.getParamString()%>" scope="request">
	                <td>
	                  <strong>
	                    <a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_NAMEREV.getParamString()%>');"><bean:message key="colname.name" /></a>
	                  </strong>
	                </td>
                </logic:equal>
                <%
                	if(a.getStation().getCompany().getVariable().getWt_enabled()==1){
                %>
                
              	<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_WTID.getParamString()%>" scope="request">
	                <td>
	                	<strong>
	                		<a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_WTID.getParamString()%>');"><bean:message key="colname.worldtracer_id"/></a>
	                	</strong>
	                </td>
                </logic:notEqual>
                <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_WTID.getParamString()%>" scope="request">
	                <td>
	                	<strong>
	                		<a href="#" onclick="sortSearchOhd('<%=TracingConstants.SortParam.OHD_WTIDREV.getParamString()%>');"><bean:message key="colname.worldtracer_id"/></a>
	                	</strong>
	                </td>
                </logic:equal>
                <%
                	}
                %>
                <td>
                  <strong>
                    <bean:message key="colname.requestOhd" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.forwardOhd" />
                  </strong>
                </td>
              </tr>
              <logic:iterate id="ohd" name="onhandlist">
                <tr>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
                  </td>
                  <td>
                    <bean:write name="ohd" property="displaydate" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="claimnum" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:message name="ohd" property="status.key" />
                  </td>
                  <td>
                    <logic:empty name="ohd" property="color">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="ohd" property="color" />
                  </td>
                  <td>
                    <logic:empty name="ohd" property="type">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="ohd" property="type" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="foundAtStation.company.companyCode_ID" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="foundAtStation.stationcode" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="holdingStation.stationcode" />
                  </td>
                  <td>
                    <logic:notEmpty name="ohd" property="passenger">
                      <logic:notEmpty name="ohd" property="passenger.lastname">
                        <bean:write name="ohd" property="passenger.lastname" />
                        ,
                        <bean:write name="ohd" property="passenger.firstname" />
                        &nbsp;
                        <bean:write name="ohd" property="passenger.middlename" />
                        &nbsp;
                      </logic:notEmpty>
                      <logic:empty name="ohd" property="passenger.lastname">
                        &nbsp;
                      </logic:empty>
                    </logic:notEmpty>
                    <logic:empty name="ohd" property="passenger">
                      &nbsp;
                    </logic:empty>
                  </td>
                   <%
                	if(a.getStation().getCompany().getVariable().getWt_enabled()==1){
                   %>
                  <td>
                  	<logic:empty name="ohd" property="wt_id">
                  		&nbsp;
                  	</logic:empty>
                  	<logic:notEmpty name="ohd" property="wt_id">
                  		<bean:write name="ohd" property="wt_id"/>
                  	</logic:notEmpty>
                  </td>
                  <%
                	}
                  %>
                  <logic:equal name="ohd" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_OPEN %>">
                    <td>
<%
                      if (a.getStation().getStation_ID() != ((OHD)ohd).getHoldingStation().getStation_ID()) {
%>
                        <A HREF="request_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="request" /></a>
<%
                      } else {
%>
                        &nbsp;
<%
                      }
%>
                    </td>
                    <td>
<%
                      if (a.getStation().getStation_ID() == ((OHD)ohd).getHoldingStation().getStation_ID()) {
%>
                        <A HREF="forward_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="forward" /></a>
<%
                      } else {
%>
                        &nbsp;
<%
                      }
%>
                    </td>
                  </logic:equal>
                  <logic:notEqual name="ohd" property="status.status_ID" value="<%= "" + TracingConstants.OHD_STATUS_OPEN %>">
                    <td>
                      &nbsp;
                    </td>
                    <td>
                      &nbsp;
                    </td>
                  </logic:notEqual>
                </tr>
              </logic:iterate>
              <input type="hidden" name="search" value="1">
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
              
            </table>
            <script language="javascript">
  document.location.href="#result";
            </script>
          </logic:present>
        </html:form>
