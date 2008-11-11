<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
 
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
%>
  <!-- Calendar includes -->
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
	

	function getagentassigned() {
		o = document.incidentForm;
		o.changeassignedstation.value="1";
		document.getElementById("agentassigneddiv").innerHTML = "<bean:message key="ajax.please_wait" />";
			postForm("incidentForm", true, function (req) { 
				o.changeassignedstation.value = "0";
				document.getElementById("agentassigneddiv").innerHTML = req.responseText; 

		});

	}
	function submitWtCancel(theform, wtq_id) {
	theform.wtq_pending_cancel.value = wtq_id;
	theform.submit();
	}

// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
<%
  int report_type = 0;

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
    }
  }
%>
  <div id="maincontent">
    <a name="incidentinfo"></a>
    <div id="pageheaderleft">
    <h1 class="green">
      <bean:message key="header.incident_info" />
<%
      if (report_type == 0) {
%>
        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/damaged_bag_reports.htm#report info fields');return false;"><%
        } else if (report_type == 1) {
%>
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_lost_delayed_reports.htm#report info fields');return false;"><%
          } else if (report_type == 2) {
%>
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_missing_articles_reports.htm#report info fields');return false;"><%
            }
%>
            <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <span class="reqfield">*</span>
        <bean:message key="message.required" />
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <br>
    </div>

	<div id="pageheaderright">
	<c:if test="${pendingWtAction == 'WT_PENDING_CREATE'}">
		<bean:message key="wt.pending.ahl.create"/>&nbsp;<a href="javascript: document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message key="update"/></a>&nbsp;<a href="javascript: document.forms[0].wtq_pending_cancel.value = '${wtq_pending_id}'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message key="cancel"/></a>
	</c:if>
	<c:if test="${!empty incidentForm.wt_id }">
		WorldTracer ID: <a href="worldtraceraf.do?ahl_id=<bean:write name="incidentForm" property="wt_id" />">
			<c:out value="${incidentForm.wt_id}" /></a><br/>
		<c:choose>
			<c:when test="${!empty pendingWtAction}">
				<br />
				<c:choose>
					<c:when test="${pendingWtAction == 'WT_PENDING_AMEND'}">
						<bean:message key="wt.pending.ahl.amend"/>
					</c:when>
					<c:when test="${pendingWtAction == 'WT_PENDING_SUSPEND'}">
						<bean:message key="wt.pending.ahl.suspend"/>
					</c:when>
					<c:when test="${pendingWtAction == 'WT_PENDING_REINSTATE'}">
					<bean:message key="wt.pending.ahl.reinstate"/>
					</c:when>
					<c:when test="${pendingWtAction == 'WT_PENDING_CLOSE'}">
						<bean:message key="wt.pending.ahl.close"/>
					</c:when>
				</c:choose>
				&nbsp;<a href="javascript: document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message key="update"/></a>
				&nbsp;<a href="javascript: document.forms[0].wtq_pending_cancel.value = '${wtq_pending_id}'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message key="cancel"/></a>
			</c:when>
			<c:otherwise>
				<br />
				<c:choose>
					<c:when test="${incidentForm.wtFile.wt_status == 'ACTIVE'}">
						<a href="javascript: document.forms[0].wtq_suspend.value = '1'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message key="wt.ahl.suspend"/></a>
					</c:when>
					<c:when test="${incidentForm.wtFile.wt_status == 'SUSPENDED'}">
						<a href="javascript: document.forms[0].wtq_reinstate.value = '1'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message key="wt.ahl.reinstate"/></a>
					</c:when>
					<c:otherwise>
						<bean:message key="wt.ahl.closed"/>
					</c:otherwise>	
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:if>
	</div>
        
        

        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
            <td nowrap=>
              <bean:message key="colname.incident_num" />
              <br>
              <html:text property="incident_ID" size="14" styleClass="textfield" readonly="true" />
              <html:hidden property="assoc_ID"/>
              <html:hidden property="ld_inc_ID"/>
              <html:hidden property="damage_inc_ID" />
              <html:hidden property="ma_inc_ID" />
              <input type="hidden" name="wtq_pending_cancel" value="" />
              <input type="hidden" name="wtq_suspend" value="" />
              <input type="hidden" name="wtq_reinstate" value="" />
              <input type="hidden" name="incident_ID" value="<c:out value='${incident_ID}'/>" />
            </td>
            <td nowrap>
              <bean:message key="colname.incident_create_date" />
              <br>
              <html:text property="dispcreatetime" size="20" styleClass="textfield" disabled="true" />
            </td>
            <td nowrap>
              <bean:message key="colname.incident.createagent" />
              <br>
              <html:text property="agentinit" size="4" styleClass="textfield" disabled="true" />
            </td>
            <td nowrap>
              <bean:message key="colname.stationcreated_nobr" />
              <br>
              <html:text property="stationcreatedcode" size="4" styleClass="textfield" disabled="true" />
            </td>
            <logic:notEmpty name="incidentForm" property="status">
              <logic:equal name="incidentForm" property="status.status_ID" value="<%= "" + TracingConstants.MBR_STATUS_CLOSED %>">
                <td>
                  <bean:message key="colname.file_close_date" />
                  <br>
                  <html:text property="dispclosedate" size="15" styleClass="textfield" disabled="true" />
                </td>
              </logic:equal>
            </logic:notEmpty>
          </tr>
          <logic:notEqual name="incidentForm" property="assoc_ID" value="">
            <tr>
              <td nowrap colspan="6">
                <bean:message key="colname.assoc_report" />
                : &nbsp;&nbsp;
                <logic:notPresent name="lostdelay" scope="request">
                  <logic:notEqual name="incidentForm" property="ld_inc_ID" value="">
                    <a href="lostDelay.do?incident_ID=<bean:write name="incidentForm" property="ld_inc_ID"/>"><bean:message key="header.lostdelay" />
                      (
                      <bean:write name="incidentForm" property="ld_inc_ID" />
                      )</a>
                      
                      
                  </logic:notEqual>
                  <logic:equal name="incidentForm" property="ld_inc_ID" value="">
                    <logic:notEqual name="incidentForm" property="readonly" value="1">
                      <a href="lostDelay.do?add_assoc_report=1"><bean:message key="colname.add_ld" /></a>
                    </logic:notEqual>
                    <logic:equal name="incidentForm" property="readonly" value="1">
                    	<bean:message key="colname.no_assoc_ld" />
                    </logic:equal>
                  </logic:equal>
                  &nbsp;&nbsp;|&nbsp;&nbsp;
                </logic:notPresent>
                <logic:notPresent name="damaged" scope="request">
                  <logic:notEqual name="incidentForm" property="damage_inc_ID" value="">
                    <a href="damaged.do?incident_ID=<bean:write name="incidentForm" property="damage_inc_ID"/>"><bean:message key="header.damaged" />
                      (
                      <bean:write name="incidentForm" property="damage_inc_ID" />
                      )</a>
                      
                  </logic:notEqual>
                  <logic:equal name="incidentForm" property="damage_inc_ID" value="">
                    <logic:notEqual name="incidentForm" property="readonly" value="1">
                      <a href="damaged.do?add_assoc_report=3"><bean:message key="colname.add_damage" /></a>
                    </logic:notEqual>
                    <logic:equal name="incidentForm" property="readonly" value="1">
                    	<bean:message key="colname.no_assoc_damage" />
                    </logic:equal>
                  </logic:equal>
                  <logic:notPresent name="missing" scope="request">
                    &nbsp;&nbsp;|&nbsp;&nbsp;
                  </logic:notPresent>
                </logic:notPresent>
                <logic:notPresent name="missing" scope="request">
                  <logic:notEqual name="incidentForm" property="ma_inc_ID" value="">
                    <a href="missing.do?incident_ID=<bean:write name="incidentForm" property="ma_inc_ID"/>"><bean:message key="header.missing_articles" />
                      (
                      <bean:write name="incidentForm" property="ma_inc_ID" />
                      )</a>
                      
                    </logic:notEqual>
                    <logic:equal name="incidentForm" property="ma_inc_ID" value="">
                      <logic:notEqual name="incidentForm" property="readonly" value="1">
                        <a href="missing.do?add_assoc_report=2"><bean:message key="colname.add_ma" /></a>
                      </logic:notEqual>
                      <logic:equal name="incidentForm" property="readonly" value="1">
                    	<bean:message key="colname.no_assoc_ma" />
                   		</logic:equal>
                    </logic:equal>
                  </logic:notPresent>
                </td>
              </tr>
            </logic:notEqual>
          </table>
          <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
            <tr>
              <td nowrap>
                <bean:message key="colname.status" />
                <br>
                <logic:notEqual name="incidentForm" property="incident_ID" value="">
                  <div id="tohide1">
	                  <html:select property="status_ID" styleClass="dropdown">
	                    <html:options collection="statuslist" property="status_ID" labelProperty="description" />
	                  </html:select>
	              </div>
                </logic:notEqual>
                <logic:equal name="incidentForm" property="incident_ID" value="">
                  <input type=text class="textfield" size=10 value="<bean:message key="incidentForm.new_status"/>" disabled>
                </logic:equal>
              </td>
              
              <td nowrap>
                <bean:message key="colname.non_revenue" /><br>
                <div id="tohide2">
	                <html:select property="nonrevenue" styleClass="dropdown">
	                  <html:option value="0">
	                    <bean:message key="select.no" />
	                  </html:option>
	                  <html:option value="1">
	                    <bean:message key="select.yes" />
	                  </html:option>
	                </html:select>
	            </div>
              </td>
              <td>
                <bean:message key="colname.report_method" />
                <br>
                <html:select property="reportmethod" styleClass="dropdown">
                  <html:option value="0">
                    <bean:message key="select.in_person" />
                  </html:option>
                  <html:option value="1">
                    <bean:message key="select.bsophone" />
                  </html:option>
                  <html:option value="2">
                    <bean:message key="select.callcenter" />
                  </html:option>
                  <html:option value="3">
                    <bean:message key="select.online" />
                  </html:option>
                  <html:option value="4">
                    <bean:message key="select.kiosk" />
                  </html:option>
                </html:select>
              </td>
              <td>
                <bean:message key="colname.recordlocator.req" />
                <br>
                <html:text property="recordlocator" size="18" maxlength="10" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.ticket" />
                <br>
                <html:text property="ticketnumber" size="18" maxlength="14" styleClass="textfield" />
              </td>
              <td nowrap>
                <bean:message key="colname.stationassigned_nobr" />
                <br>
                <input type="hidden" name="changeassignedstation">
                <html:select property="stationassigned_ID" styleClass="dropdown" onchange="getagentassigned();">
                  <html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
                </html:select>
              </td>
              <td nowrap>
              	<div id="agentassigneddiv">
                <bean:message key="colname.agentassigned_nobr" />
                <br>
                <html:select property="agentassigned_ID" styleClass="dropdown">
                	<option value=""><bean:message key="select.please_select" /></option>
                  <html:options collection="agentassignedlist" property="agent_ID" labelProperty="username" />
                </html:select>
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
          <!-- contact info -->
          <jsp:include page="/pages/includes/contactinfo_incl.jsp" />
          <!-- eof contact info -->
          <a name="passit"></a>
          <h1 class="green">
            <bean:message key="header.itinerary" />
<%
            if (report_type == 0) {
%>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_passenger_itineraries.htm#add passenger itinerary');return false;"><%
              } else if (report_type == 1) {
%>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_passenger_itineraries_(ld).htm#add passenger itinerary');return false;"><%
                } else if (report_type == 2) {
%>
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_passenger_itineraries_(ma).htm#add passenger itinerary');return false;"><%
                  }
%>
                  <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <span class="reqfield">*</span>
              <bean:message key="message.required" />
              <logic:iterate id="theitinerary" indexId="k" name="incidentForm" property="itinerarylist">
                <logic:equal name="theitinerary" property="itinerarytype" value="0">
                  <html:hidden name="theitinerary" property="itinerarytype" value="0" indexed="true" />
                  <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
                    <tr>
                      <td>
                        <bean:message key="colname.pax.fromto.req" />
                        <br>
                        <html:text name="theitinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                        /
                        <html:text name="theitinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                        <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                      </td>
                      <td>
                        <bean:message key="colname.pax.flightnum.req" />
                        <br>
                        <html:select name="theitinerary" property="airline" styleClass="dropdown" indexed="true">
			                    <html:option value="">
			                      <bean:message key="select.please_select" />
			                    </html:option>
			                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
			                  </html:select>
                  			&nbsp;
                        <html:text name="theitinerary" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" />
                      </td>
                      <td nowrap>
                        <bean:message key="colname.pax.departdate.req" />
                        (
                        <%= a.getDateformat().getFormat() %>)
                        <br>
                        <html:text name="theitinerary" property="disdepartdate" size="11" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar<%= k %>" name="itcalendar<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disdepartdate" %>','itcalendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                      <td nowrap>
                        <bean:message key="colname.pax.arrdate.req" />
                        (
                        <%= a.getDateformat().getFormat() %>)
                        <br>
                        <html:text name="theitinerary" property="disarrivedate" size="11" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="itcalendar2<%= k %>" name="itcalendar2<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disarrivedate" %>','itcalendar2<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                    </tr>
                    <tr>
                      <td>
                        <bean:message key="colname.schdeptime" />
                        (
                        <%= a.getTimeformat().getFormat() %>)
                        <br>
                        <html:text name="theitinerary" property="disschdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                      </td>
                      <td>
                        <bean:message key="colname.scharrtime" />
                        (
                        <%= a.getTimeformat().getFormat() %>)
                        <br>
                        <html:text name="theitinerary" property="disscharrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                      </td>
                      <td>
                        <bean:message key="colname.actdeptime" />
                        (
                        <%= a.getTimeformat().getFormat() %>)
                        <br>
                        <html:text name="theitinerary" property="disactdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                      </td>
                      <td>
                        <bean:message key="colname.actarrtime" />
                        (
                        <%= a.getTimeformat().getFormat() %>)
                        <br>
                        <html:text name="theitinerary" property="disactarrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                      </td>
                    </tr>
                    <tr>
                      <td colspan=4>
                        <html:submit styleId="button" property="deletePassit" indexed="true">
                          <bean:message key="button.delete_pass_itinerary" />
                        </html:submit>
                      </td>
                    </tr>
                  </table>
                </logic:equal>
              </logic:iterate>
              <center><html:submit property="addpassit" styleId="button">
                <bean:message key="button.add_cust_itinerary" />
              </html:submit></center>
              <p class="blue">
                &nbsp;
              </p>
              <h1 class="green">
                <bean:message key="header.bag_itinerary" />
<%
                if (report_type == 0) {
%>
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_baggage_itinerary.htm#add_baggage_itinerary');return false;"><%
                  } else if (report_type == 1) {
%>
                    <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_itineraries_(ld).htm#add_baggage_itinerary');return false;"><%
                    } else if (report_type == 2) {
%>
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_baggage_itineraries_(ma).htm#add_baggage_itinerary');return false;"><%
                      }
%>
                      <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                  </h1>
                  <span class="reqfield">*</span>
                  <bean:message key="message.required" />
                  <a name="bagit"></a>
                  <logic:iterate id="theitinerary" indexId="k" name="incidentForm" property="itinerarylist">
                    <logic:equal name="theitinerary" property="itinerarytype" value="1">
                      <html:hidden name="theitinerary" property="itinerarytype" value="1" indexed="true" />
                      <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                            <bean:message key="colname.bag.fromto.req" />
                            <br>
                            <html:text name="theitinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                            <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                            /
                            <html:text name="theitinerary" property="legto" size="3" maxlength="3" styleClass="textfield" indexed="true" />
                            <a href="#" onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legto','airportcode',500,600);return false;"><img src="deployment/main/images/nettracer/airport_codes.gif" border=0></a>
                          </td>
                          <td>
                            <bean:message key="colname.bag.flightnum.req" />
                            <br>
                            <html:select name="theitinerary" property="airline" styleClass="dropdown" indexed="true">
					                    <html:option value="">
					                      <bean:message key="select.please_select" />
					                    </html:option>
					                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
					                  </html:select>
					                  &nbsp;
                            <html:text name="theitinerary" property="flightnum" size="4" maxlength="4" styleClass="textfield" indexed="true" />
                          </td>
                          <td nowrap>
                            <bean:message key="colname.bag.departdate.req" />
                            (
                            <%= a.getDateformat().getFormat() %>)
                            <br>
                            <html:text name="theitinerary" property="disdepartdate" size="11" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar3<%= k %>" name="calendar3<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disdepartdate" %>','calendar3<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                          <td nowrap>
                            <bean:message key="colname.bag.arrdate.req" />
                            (
                            <%= a.getDateformat().getFormat() %>)
                            <br>
                            <html:text name="theitinerary" property="disarrivedate" size="11" maxlength="10" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar4<%= k %>" name="calendar4<%= k %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disarrivedate" %>','calendar4<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.schdeptime" />
                            (
                            <%= a.getTimeformat().getFormat() %>)
                            <br>
                            <html:text name="theitinerary" property="disschdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                          </td>
                          <td>
                            <bean:message key="colname.scharrtime" />
                            (
                            <%= a.getTimeformat().getFormat() %>)
                            <br>
                            <html:text name="theitinerary" property="disscharrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                          </td>
                          <td>
                            <bean:message key="colname.actdeptime" />
                            (
                            <%= a.getTimeformat().getFormat() %>)
                            <br>
                            <html:text name="theitinerary" property="disactdeparttime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                          </td>
                          <td>
                            <bean:message key="colname.actarrtime" />
                            (
                            <%= a.getTimeformat().getFormat() %>)
                            <br>
                            <html:text name="theitinerary" property="disactarrivetime" size="5" maxlength="5" styleClass="textfield" indexed="true" />
                          </td>
                        </tr>
                        <tr>
                          <td colspan=4>
                            <html:submit styleId="button" property="deleteBagit" indexed="true">
                              <bean:message key="button.delete_bag_itinerary" />
                            </html:submit>
                          </td>
                        </tr>
                      </table>
                    </logic:equal>
                  </logic:iterate>
                  <center><html:submit property="addbagit" styleId="button">
                    <bean:message key="button.add_bag_itinerary" />
                  </html:submit></center>
                  <br>
                  <br>
                  &nbsp;&nbsp;&uarr;
                  <a href="#"><bean:message key="link.to_top" /></a>
                  <br>
                  <br>
                  <a name="checkedbaggage"></a>
                  <h1 class="green">
                    <bean:message key="header.checked_bag_info" />
<%
                    if (report_type == 0) {
%>
                      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_baggage_check_info.htm');return false;"><%
                      } else if (report_type == 1) {
%>
                        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_check_information_(ld).htm#top');return false;"><%
                        } else if (report_type == 2) {
%>
                          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_baggage_check_information_(ma).htm#top');return false;"><%
                          }
%>
                          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
                      </h1>
                      <span class="reqfield">*</span>
                      <bean:message key="message.required" />
                      <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
                        <tr>
                          <td>
                            <bean:message key="colname.num_pass" />
                            <br>
                            <html:text property="numpassengers" size="8" maxlength="4" styleClass="textfield" />
                          </td>
                          <td>
                            <bean:message key="colname.num_bag_checked" />
                            <br>
                            <html:text property="numbagchecked" size="8" maxlength="4" styleClass="textfield" />
                          </td>
                          <td>
                            <bean:message key="colname.bags_rec" />
                            <br>
                            <html:text property="numbagreceived" size="8" maxlength="4" styleClass="textfield" />
                          </td>
                        </tr>
                        <tr>
                          <td>
                            <bean:message key="colname.bag_loc" />
                            <br>
                            <html:select property="checkedlocation" styleClass="dropdown">
                              <html:option value="0">
                                <bean:message key="select.please_select" />
                              </html:option>
                              <html:option value="1">
                                <bean:message key="select.curb_side" />
                              </html:option>
                              <html:option value="2">
                                <bean:message key="select.ticket_counter" />
                              </html:option>
                              <html:option value="3">
                                <bean:message key="select.gate" />
                              </html:option>
                              <html:option value="4">
                                <bean:message key="select.remote" />
                              </html:option>
                              <html:option value="5">
                                <bean:message key="select.plane_side" />
                              </html:option>
                              <html:option value="6">
                                <bean:message key="select.unchecked" />
                              </html:option>
                              <html:option value="7">
                                <bean:message key="select.kiosk" />
                              </html:option>
                            </html:select>
                          </td>
                          <td>
                            <bean:message key="colname.courtesy_report" />
                            <br>
                            <html:select property="courtesyreport" styleClass="dropdown">
                              <html:option value="">
                                <bean:message key="select.please_select" />
                              </html:option>
                              <html:option value="1">
                                <bean:message key="select.yes" />
                              </html:option>
                              <html:option value="0">
                                <bean:message key="select.no" />
                              </html:option>
                            </html:select>
                          </td>
                          <!--
                          <td>
                            <bean:message key="colname.tsa" />
                            <br>
                            <html:select property="tsachecked" styleClass="dropdown">
                              <html:option value="">
                                <bean:message key="select.please_select" />
                              </html:option>
                              <html:option value="1">
                                <bean:message key="select.yes" />
                              </html:option>
                              <html:option value="0">
                                <bean:message key="select.no" />
                              </html:option>
                            </html:select>
                          </td>
                          -->
                          <td>
                            <bean:message key="colname.custom" />
                            <br>
                            <html:select property="customcleared" styleClass="dropdown">
                              <html:option value="">
                                <bean:message key="select.please_select" />
                              </html:option>
                              <html:option value="1">
                                <bean:message key="select.yes" />
                              </html:option>
                              <html:option value="0">
                                <bean:message key="select.no" />
                              </html:option>
                            </html:select>
                          </td>
                        </tr>
                      </table>
                      <br>
                      <br>
                      &nbsp;&nbsp;&uarr;
                      <a href="#"><bean:message key="link.to_top" /></a>
                      <br>
                      <br>
