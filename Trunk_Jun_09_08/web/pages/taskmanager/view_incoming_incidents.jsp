<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Item" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %> 
<%@ page import="com.bagnet.nettracer.tracing.utils.TracerProperties" %> 
<%
  Agent a = (Agent)session.getAttribute("user");
  boolean collectPosId = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a);
%>
  
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

function sortIncident(sortIncident) {
	o = document.searchIncidentForm;
	o.sort.value = sortIncident;
	o.submit();
}

function resetSort() {
	o = document.getElementById('sort_id');
	if (o && 0 < o.value.length) {
		o.value = '';
	}
	
	return true;
}
  </script>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="incomingReports.do" method="post" focus="incident_ID" onsubmit="resetSort(); fillzero(this.incident_ID, 13); return validateSearch(this);">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
  	<%
	    String sort = StringUtils.trimToNull((String)request.getAttribute("sort"));	
	    if (sort != null) {
	%>
	    <input type="hidden" name="sort" id="sort_id" value="<%= sort %>"/>
	<%} else if(collectPosId){%>
		<input type="hidden" name="sort" id="sort_id" value="<%=TracingConstants.SortParam.OHD_POSITION.getParamString()%>">
	<%} else {%>
	    <input type="hidden" name="sort" id="sort_id" value=""/>
	<% } %>
	 
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_incoming_incidents" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.incident_file_num2" />
                  <br>
                  <html:text property="incident_ID" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td>
                  <bean:message key="colname.report_type" />
                  <br>
                  <html:select property="itemType_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="mbrreporttypes" property="itemType_ID" labelProperty="description" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.claimnum" />
                  <br>
                  <html:text property="claimchecknum" size="20" maxlength="12" styleClass="textfield" />
                </td>
                <td nowrap>
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
                <td nowrap>
                  <bean:message key="colname.date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
                <td>
                  <bean:message key="colname.stationcreated_nobr" />
                  <br>
                  <html:select property="stationcreated_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </td>
              </tr>
              
              <tr>
                <td colspan="2" nowrap>
                  <bean:message key="colname.station.assignment.date.range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_station_assignment_time" size="10" maxlength="10" styleClass="textfield" onkeypress="document.searchIncidentForm.assigned2StationWithin24hrs.checked = false;" onchange="document.searchIncidentForm.assigned2StationWithin24hrs.checked = false;" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="document.searchIncidentForm.assigned2StationWithin24hrs.checked = false;cal1xx.select(document.searchIncidentForm.s_station_assignment_time,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_station_assignment_time" size="10" maxlength="10" styleClass="textfield" onkeypress="document.searchIncidentForm.assigned2StationWithin24hrs.checked = false;" onchange="document.searchIncidentForm.assigned2StationWithin24hrs.checked = false;" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="document.searchIncidentForm.assigned2StationWithin24hrs.checked = false;cal1xx.select(document.searchIncidentForm.e_station_assignment_time,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
                  <br>
                  <html:checkbox property="assigned2StationWithin24hrs" value="1" onclick="if (this.checked == true) {document.searchIncidentForm.s_station_assignment_time.value = '';document.searchIncidentForm.e_station_assignment_time.value = ''; } else if (this.checked == false){}"><bean:message key="colname.assigned.to.station.within.24.hours" /></html:checkbox>
                </td>
              </tr>

              <tr>
                <td colspan="2" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;

                <html:reset styleId="button">
                  <bean:message key="button.reset" />
                </html:reset>
									

                </td>
              </tr>
            </table>
            <logic:present name="resultlist" scope="request">
              <div id="pageheaderleft">
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              </div>
              
              <%
                if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_QUERY_REPORTS, a)) {
              %>
              <div id="pageheaderright">
                <select name="outputtype" class="dropdown">
                  <% if (!TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                    <option value="0" selected="yes"><bean:message key="radio.pdf" /></option>
                  <% } %>
                  <option value="1"><bean:message key="radio.html" /></option>
                </select>
                <html:submit  property="generateReport" styleId="button">
                    <bean:message key="button.generateReport" />
                </html:submit>
                <logic:present name="reportfile" scope="request">
                  <script language=javascript>
                    
                      openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);

                  </script>
                </logic:present>
              </div>
              <%
                }
              %>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.incident_file_num_br" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.report_type" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.incident_create_date_short" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.companycreated" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.stationcreated" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.stationassigned" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="header.status" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.color" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.bagtype" />
                    </b>
                  </td>
                  
                  <td>
                    <b>
                      <bean:message key="colname.itinerary" />
                    </b>
                  </td>
                  <logic:notEmpty name="searchIncidentForm" property="flightnum">
                    <td>
                      <b>
                        <bean:message key="colname.flightnum" />
                      </b>
                    </td>
                  </logic:notEmpty>
                  
                  <logic:equal name="searchIncidentForm" property="itemType_ID" value="1">
	                  <% if (collectPosId) { %> 
	                  	<td>
	                    	<strong>
	                      		<logic:notEqual name="sort" value="<%=TracingConstants.SortParam.OHD_POSITION.getParamString()%>" scope="request">
			               			<a href="#" onclick="sortIncident('<%=TracingConstants.SortParam.OHD_POSITION.getParamString()%>');"><bean:message key="colname.posId" /></a>
			                  </logic:notEqual>
			                  <logic:equal name="sort" value="<%=TracingConstants.SortParam.OHD_POSITION.getParamString()%>" scope="request">
			              			<a href="#" onclick="sortIncident('<%=TracingConstants.SortParam.OHD_POSITIONREV.getParamString()%>');"><bean:message key="colname.posId" /></a>
			              	   </logic:equal>
			               </strong>
	                  	</td>
	           		  <% } %>
	           	  </logic:equal>
	           	  	  
                  <td >
                    <b>
                      <bean:message key="colname.claimnum" />
                    </b>
                  </td>
                  <td >
                    <b>
                      <bean:message key="colname.pass_name" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="results" name="resultlist" type="com.bagnet.nettracer.tracing.db.Incident">
                  <bean:define id="items" name="results" property="itemlist" />
                  <bean:define id="claimchecks" name="results" property="claimcheck_list" />
                  <bean:define id="itinerary" name="results" property="itinerary_list" />
                  <bean:define id="passengers" name="results" property="passenger_list" />
                  <tr>
                    <td>
                      <a href='searchIncident.do?incident=<bean:write name="results" property="incident_ID"/>'><bean:write name="results" property="incident_ID" /></a>
                    </td>
                    <td>
                      <bean:message name="results" property="itemtype.key" />
                    </td>
                    <td>
                      <bean:write name="results" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.company.companyCode_ID" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationcreated.stationcode" />
                    </td>
                    <td>
                      <bean:write name="results" property="stationassigned.stationcode" />
                    </td>
                    <td>
                      <bean:message name="results" property="status.key"/>
                    </td>
                    
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="color">
                          <logic:notEqual name="item_list" property="color" value="">
                            <bean:write name="item_list" property="color" />
                            <br>
                          </logic:notEqual>
                        </logic:present>
                      </logic:iterate>
                      &nbsp;
                    </td>

                    <td >
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="bagtype">
                          <logic:notEqual name="item_list" property="bagtype" value="">
                            <bean:write name="item_list" property="bagtype" />
                            <br>
                          </logic:notEqual>
                        </logic:present>
                      </logic:iterate>
                      &nbsp;
                    </td>


                    <td nowrap>
                      <logic:iterate id="itin_list" name="itinerary" type="com.bagnet.nettracer.tracing.db.Itinerary">
                          <logic:equal name="itin_list" property="itinerarytype" value="0">
                          <bean:write name="itin_list" property="airline" />
                          <bean:write name="itin_list" property="flightnum" />
                          <bean:write name="itin_list" property="departdate" />
                            <br>
                          </logic:equal>
                      </logic:iterate>
                      &nbsp;
                    </td>


                    <logic:notEmpty name="searchIncidentForm" property="flightnum">
                      <td>
                        <bean:write name="searchIncidentForm" property="airline" />
                        <bean:write name="searchIncidentForm" property="flightnum" />
                      </td>
                    </logic:notEmpty>
                  
                  	<logic:equal name="searchIncidentForm" property="itemType_ID" value="1">
	            	  	<% if (collectPosId) {
	            	  		out.print("<td>");
	            	  		
	            	  		@SuppressWarnings("unchecked")
	            	  		List<Item> item_list = (List<Item>)items;
	            	  		if (item_list == null || item_list.isEmpty()) {
            	  				out.print("<br/>");
	            	  		} else {	            	  		
	            	  			for (Item item : item_list) {
	            	  				if (item != null && item.getPosId() != null) {
	            	  					out.print(item.getPosId());
	            	  				}
	            	  				
	            	  				out.print("<br/>");
	            	  			}
	            	  		}

	            	  		out.print("</td>");
	            	  	} %>
	            	</logic:equal>
	            
                    <td>
                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
                        <logic:present name="item_list" property="claimchecknum">
	                        <logic:notEqual name="item_list" property="claimchecknum" value="">
	                          <bean:write name="item_list" property="claimchecknum" />
	                          <br>
	                        </logic:notEqual>
	                      </logic:present>
                      </logic:iterate>
                      <logic:iterate id="claimcheck_list" name="claimchecks" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
                        <logic:notEqual name="claimcheck_list" property="claimchecknum" value="">
                          <bean:write name="claimcheck_list" property="claimchecknum" />
                          <br>
                        </logic:notEqual>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
<%
                      boolean hasp = false;
%>
                      <logic:iterate id="passenger_list" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
<%
                        hasp = false;
%>
                        <logic:notEqual name="passenger_list" property="lastname" value="">
                          <bean:write name="passenger_list" property="lastname" />
                          ,
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <logic:notEqual name="passenger_list" property="firstname" value="">
<%
                          hasp = true;
%>
                        </logic:notEqual>
                        <bean:write name="passenger_list" property="firstname" />
                        <bean:write name="passenger_list" property="middlename" />
<%
                        if (hasp) {
%>
                          <br>
<%
                        }
%>
                      </logic:iterate>
                      &nbsp;
                    </td>
                  </tr>
                </logic:iterate>
                <tr>
                  <td colspan="<logic:notEmpty name="searchIncidentForm" property="flightnum">13</logic:notEmpty> <logic:empty name="searchIncidentForm" property="flightnum">12</logic:empty>">
                    
                    <jsp:include page="/pages/includes/pagination_incl.jsp" />
                    
                  </td>
                </tr>
                
              </table>
              <script language=javascript>
                
  document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
