<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  boolean collectExpTag=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EXPEDITE_TAG_NUM_COLLECT, a);
  boolean wtEnabled=a.getStation().getCompany().getVariable().getWt_enabled()==1;
  
%>
  
  <%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
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


  </script>
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="searchIncident.do" method="post" focus="incident_ID" onsubmit="fillzero(this.incident_ID, 13); return validateSearch(this);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.search_incident" />
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
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td width=33%>
                  <bean:message key="colname.incident_num" />
                  <br>
                  <html:text property="incident_ID" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td width=33%>
                  <bean:message key="colname.report_type" />
                  <br>
                  <html:select property="itemType_ID" styleClass="dropdown" styleId="itemType_ID" >
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="mbrreporttypes" property="itemType_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td width=33%>
                  <bean:message key="colname.status" />
                  <br>
                  <html:select property="status_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="statuslist" property="status_ID" labelProperty="description" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.last_name" />
                  <br>
                  <html:text property="lastname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.first_name" />
                  <br>
                  <html:text property="firstname" size="20" maxlength="20" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.mid_initial" />
                  <br>
                  <html:text property="middlename" size="20" maxlength="1" styleClass="textfield" />
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
                <td>
                  <bean:message key="colname.ticket" />
                  <br>
                  <html:text property="ticketnumber" size="20" maxlength="14" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td nowrap>
                  <bean:message key="colname.date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="e_createtime" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchIncidentForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td>
                  <bean:message key="colname.airline_membership" />
                  <br>
                  <html:select property="companycode_ID" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.membership_number" />
                  <br>
                  <html:text property="membershipnum" size="20" maxlength="20" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.companycreated" />
                  <br>
                  <div id="tohide1">
	                  <html:select property="companycreated_ID" styleClass="dropdown">
	                    <html:option value="">
	                      <bean:message key="select.all" />
	                    </html:option>
	                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
	                  </html:select>
	              </div>
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
                <td>
                  <bean:message key="colname.stationassigned_nobr" />
                  <br>
                  
                  <html:select property="stationassigned_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                  </html:select>
                </td>
                
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.createagent" />
                  <br>
                  <html:text property="agent" size="20" maxlength="20" styleClass="textfield" />
                </td>

                <td>
	                <bean:message key="colname.agentassigned_nobr" />
	                <br>
	                <html:text property="agentassigned" size="20" maxlength="20" styleClass="textfield" onkeypress="document.searchIncidentForm.noAssignedAgent.checked = false;"/>
	                <html:checkbox property="noAssignedAgent" value="1" onclick="if (this.checked == true) {document.searchIncidentForm.agentassigned.value = ''; } else if (this.checked == false){}"><bean:message key="colname.noassignedagent" /></html:checkbox>
                </td>
                <td>
                	<bean:message key="colname.recordlocator" />
                	<br />
                	<html:text property="recordlocator" size="8" maxlength="6" styleClass="textfield" />
                </td>
                
              </tr>
              <tr>
              	<% if(collectExpTag){ %>
              	<td colspan="<%=wtEnabled?"1":"3"%>">
              		<bean:message key="colname.expedite.tagnum" />
              		<br>
              		<html:text property="expediteTagNum" size="13" maxlength="12" styleClass="textfield" styleId="expediteTagNum" />
              	</td>
              	<% } %>
              	
                <% if(wtEnabled){ %>
              	<td colspan="<%=collectExpTag?"2":"3"%>" >
              		<bean:message key="colname.wt.id" />
              		<br>
              		<html:text property="wt_id" size="20" maxlength="13" styleClass="textfield" styleId="wt_id" />
              	</td>
              	<% } %>
              </tr>
              <tr>
                <td colspan="3" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;
                  
    				<logic:equal name="searchIncidentForm" property="itemType_ID" value="1">
    				<input type="button" name="reset" id="button" value="<bean:message key="button.reset" />" onclick="document.location.href='searchIncident.do?ld=1';">
    				</logic:equal>
    				<logic:equal name="searchIncidentForm" property="itemType_ID" value="2">
    				<input type="button" name="reset" id="button" value="<bean:message key="button.reset" />" onclick="document.location.href='searchIncident.do?missing=1';">
    				</logic:equal>
    				<logic:equal name="searchIncidentForm" property="itemType_ID" value="3">
    				<input type="button" name="reset" id="button" value="<bean:message key="button.reset" />" onclick="document.location.href='searchIncident.do?damage=1';">
    				</logic:equal>
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
                <input type="submit" name="generateReport" id="button" value="<bean:message key="button.generateReport" />">
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
                      <bean:message key="colname.incident_num" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.report_type" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.incident_create_date" />
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
      <strong>
        <bean:message key="colname.color" />
      </strong>
    </td>
    <td>
      <strong>
        <bean:message key="colname.bagtype" />
      </strong>
    </td>

                  <logic:notEmpty name="searchIncidentForm" property="flightnum">
                    <td>
                      <b>
                        <bean:message key="colname.flightnum" />
                      </b>
                    </td>
                  </logic:notEmpty>
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
                  <%
                	if(a.getStation().getCompany().getVariable().getWt_enabled()==1){
                 %>
                  <td>
                  	<b>
                  		<bean:message key="colname.worldtracer_id"/>
                  	</b>
                  </td>
                  <%
                	}
                  %>
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
                      <bean:message name="results" property="status.key" />
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
      <td>
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

                    <logic:notEmpty name="searchIncidentForm" property="flightnum">
                      <td>
                        <bean:write name="searchIncidentForm" property="airline" />
                        <bean:write name="searchIncidentForm" property="flightnum" />
                      </td>
                    </logic:notEmpty>
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
                    <%
                	if(a.getStation().getCompany().getVariable().getWt_enabled()==1){
                    %>
                    <td>
                     	<logic:empty name="results" property="wt_id">
                            &nbsp;
                        </logic:empty>
                        <logic:notEmpty name="results" property="wt_id">
                        	 <bean:write name="results" property="wt_id" />
                        </logic:notEmpty>
                    </td>
                    <%
                	}
                    %>
                  </tr>
                </logic:iterate>
                <tr>
                  <td colspan="<logic:notEmpty name="searchIncidentForm" property="flightnum">11</logic:notEmpty> <logic:empty name="searchIncidentForm" property="flightnum">10</logic:empty>">
                    
                    <jsp:include page="/pages/includes/pagination_incl.jsp" />
                    
                  </td>
                </tr>
                
              </table>
              <script language=javascript>
                
  				document.location.href="#result";
              </script>
            </logic:present>
          </html:form>
