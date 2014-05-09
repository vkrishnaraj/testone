<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.db.taskmanager.GeneralTask"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
  String bagItinCss="form2_bag";
  String paxItinCss="form2_pax";
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
  IncidentForm myform = (IncidentForm) session.getAttribute("incidentForm");
  
  boolean displayNonRevenueCodes = PropertyBMO.isTrue(PropertyBMO.DISPLAY_NON_REVENUE_CODES);
  
%>

<%@page import="com.bagnet.nettracer.tracing.utils.IncidentUtils"%>
<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>
<%@page import="com.bagnet.nettracer.tracing.db.Incident"%>

<%@page import="java.util.List"%>
<%@page import="com.bagnet.nettracer.tracing.db.Itinerary"%><SCRIPT
	LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

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
	
	function toggleRevenueCodesVisible() {
		<% if (displayNonRevenueCodes) { %> 
			var revSelected = document.getElementById("isNonRevenue").value;
			if (revSelected == "1") {
				document.getElementById("nonRevCodes").style.display = 'inline';
			} else {
				document.getElementById("nonRevCodes").style.display = 'none';
			}
		<% } %>
	}
	
	function updateCourtesyDescription() {
		var courtesyReasonId = document.getElementById("courtesyReasonId");
		if (!courtesyReasonId) return;

		var courtesyDescription = document.getElementById("courtesyDescription");
		if (courtesyReasonId.options[courtesyReasonId.selectedIndex].value == 900) {
			courtesyDescription.disabled = false;
		} else {
			courtesyDescription.disabled = true;
			courtesyDescription.value = "";
		}
	}
	
	function checkSameAirline(){
		var wtAirline= document.getElementById("wtAirlineCode"); 
   	 	var wtStation= document.getElementById("wtStationCode"); 
   		<% if(myform.getIncident_ID()==null || myform.getIncident_ID().isEmpty()){ %>
		 if(wtAirline!=null && wtStation!=null){
	 		  if (wtAirline.value!=""  && wtAirline.value !="<%=a.getCompanycode_ID() %>"){
	 			 wtStation.disabled=false;
	 		  } else {
	 			 wtStation.disabled=true;
	 			 wtStation.value="";
	 		  }
		 }
		 <% } %>
	}

  </SCRIPT>

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


	<c:if
		test="${!empty incidentForm.incident_ID and !empty incidentObj and incidentForm.incident_ID == incidentObj.incident_ID}">
		<% 
     Incident incidentObj = (Incident) session.getAttribute("incidentObj");
     if (IncidentUtils.promptToCloseFile(null, incidentObj, null)) { %>
		<table border="1" align="center">
			<tr>
				<td align="center"><bean:message key="no.outstanding.items" />
					<p /> <input type="button"
					value="<bean:message key="button.close_file" />" id="button"
					onclick="window.location = 'lostDelay.do?incident_ID=<bean:write name="incidentForm" property="incident_ID"/>&close=1'
              " />

				</td>
			</tr>
		</table>
		<% } %>
	</c:if>

	<a name="incidentinfo"></a>
	
	<div id="pageheaderleft">
		<h1 class="green">
			<%
      if (report_type == 0) {
%>
			<bean:message key="header.damaged_info" />
			<a href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/damaged_bag_reports.htm#report info fields');return false;">
				<%
        } else if (report_type == 1) {
%> <bean:message key="header.incident_info" /> <a href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_lost_delayed_reports.htm#report info fields');return false;">
					<%
          } else if (report_type == 2) {
%> <bean:message key="header.pilferage_info" /> <a href="#"
					onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_missing_articles_reports.htm#report info fields');return false;">
						<%
            }
%> <img src="deployment/main/images/nettracer/button_help.gif"
						width="20" height="21" border="0">
				</a>
		</h1>
		<span class="reqfield">*</span>
		<bean:message key="message.required" />
		<font color="red"> <logic:messagesPresent message="true">
				<html:messages id="msg" message="true">
					<br />
					<bean:write name="msg" filter="false"/>
					<br />
				</html:messages>
			</logic:messagesPresent>
		</font> <br>
	</div>

	<div id="pageheaderright">
		<c:if test="${pendingWtAction == 'WT_PENDING_CREATE'}">
			<bean:message key="wt.pending.ahl.create" />&nbsp;<a
				href="javascript: document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message
					key="update" /></a>&nbsp;<a
				href="javascript: document.forms[0].wtq_pending_cancel.value = '${wtq_pending_id}'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message
					key="cancel" /></a>
		</c:if>
		<c:if test="${!empty incidentForm.wt_id }">
			<bean:message key="wt.ahl.id" />
			<a
				href="worldtraceraf.do?rawtext=1&ahl_id=<bean:write name="incidentForm" property="wt_id" />&incident_id=${incident}">
				<c:out value="${incidentForm.wt_id}" />
			</a>
			<input type="hidden" value="<%=myform.getWt_id() %>" id="wtid"/>
			<br />
			<c:choose>
				<c:when test="${!empty pendingWtAction}">
					<% boolean action = false; %>
					<br />
					<c:choose>
						<c:when test="${pendingWtAction == 'WT_PENDING_AMEND'}">
							<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT, a)) { %>
							<bean:message key="wt.pending.ahl.amend" />
							<% action = true;} %>
						</c:when>
						<c:when test="${pendingWtAction == 'WT_PENDING_SUSPEND'}">
							<% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
							<bean:message key="wt.pending.ahl.suspend" />
							<% action = true;} %>
						</c:when>
						<c:when test="${pendingWtAction == 'WT_PENDING_REINSTATE'}">
							<% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
							<bean:message key="wt.pending.ahl.reinstate" />
							<% action = true;} %>
						</c:when>
						<c:when test="${pendingWtAction == 'WT_PENDING_CLOSE'}">
							<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT, a)) { %>
							<bean:message key="wt.pending.ahl.close" />
							<% action = true;} %>
						</c:when>
					</c:choose>
					<% if(action){ %>
				&nbsp;<a
						href="javascript: document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message
							key="update" /></a>
				&nbsp;<a
						href="javascript: document.forms[0].wtq_pending_cancel.value = '${wtq_pending_id}'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message
							key="cancel" /></a>
					<% } %>
				</c:when>
				<c:otherwise>
					<br />
					<c:choose>
						<c:when test="${incidentForm.wtFile.wt_status == 'ACTIVE'}">
							<% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a) && !(PropertyBMO.isTrue(PropertyBMO.TRACING_STATUS_BLOCK_WT) && myform.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING)) { %>
							<a
								href="javascript: document.forms[0].wtq_suspend.value = '1'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message
									key="wt.ahl.suspend" /></a>
							<% } %>
						</c:when>
						<c:when test="${incidentForm.wtFile.wt_status == 'SUSPENDED'}">
							<% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
							<a
								href="javascript: document.forms[0].wtq_reinstate.value = '1'; document.forms[0].incident_ID.value = '${incident}'; document.forms[0].submit();"><bean:message
									key="wt.ahl.reinstate" /></a>
							<% } %>
						</c:when>
						<c:otherwise>
							<bean:message key="wt.ahl.closed" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:if>
		
	</div>



	<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		<tr>
			<td nowrap="nowrap">
				<%
      if (report_type == 0) {
%> <bean:message key="colname.damaged_incident_num" /> <%
        } else if (report_type == 1) {
%> <bean:message key="colname.incident_num" /> <%
          } else if (report_type == 2) {
%> <bean:message key="colname.pilferage_incident_num" /> <%
            }
%> <br> <html:text property="incident_ID" size="14"
					styleClass="textfield" readonly="true" /> <html:hidden
					property="assoc_ID" /> <html:hidden property="ld_inc_ID" /> <html:hidden
					property="damage_inc_ID" /> <html:hidden property="ma_inc_ID" />

				<input type="hidden" name="wtq_pending_cancel" value="" /> <input
				type="hidden" name="wtq_suspend" value="" /> <input type="hidden"
				name="wtq_reinstate" value="" /> <input type="hidden"
				name="incident_ID" value="<c:out value='${incident_ID}'/>" />
			</td>
			<td nowrap="nowrap"><bean:message
					key="colname.incident_create_date" /> <br> <html:text
					property="dispcreatetime" size="20" styleClass="textfield"
					disabled="true" /></td>
			<td nowrap="nowrap">
				<%
      if (report_type == 0) {
%> <bean:message key="colname.damage.createagent" /> <%
        } else if (report_type == 1) {
%> <bean:message key="colname.incident.createagent" /> <%
          } else if (report_type == 2) {
%> <bean:message key="colname.pilferage.createagent" /> <%
            }
%> <br> <html:text property="agentinit" size="10"
					styleClass="textfield" disabled="true" />
			</td>
			<td nowrap="nowrap"><bean:message
					key="colname.stationcreated_nobr" /> <br> <html:text
					property="stationcreatedcode" size="4" styleClass="textfield"
					disabled="true" /></td>
<% 			if (report_type == 1 && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_TRACING_STATUS, a)) {
%>
			<td nowrap="nowrap"><bean:message
					key="colname.tracing_status" /> <br> 
					<html:select property="tracingStatus" styleClass="dropdown" >
						<html:option value="<%=String.valueOf(TracingConstants.INCIDENT_TRACING_STATUS_DEFAULT) %>">
							<bean:message key="option.tracing_status.default" />
						</html:option>
						<html:option value="<%=String.valueOf(TracingConstants.INCIDENT_TRACING_STATUS_TRACING) %>">
							<bean:message key="option.tracing_status.tracing" />
						</html:option>
						<html:option value="<%=String.valueOf(TracingConstants.INCIDENT_TRACING_STATUS_FINAL) %>">
							<bean:message key="option.tracing_status.final" />
						</html:option>
					</html:select>
			</td>
<%          } 
%>
			<logic:notEmpty name="incidentForm" property="status">
				<logic:equal name="incidentForm" property="status.status_ID"
					value='<%= String.valueOf(TracingConstants.MBR_STATUS_CLOSED) %>'>
					<td><bean:message key="colname.file_close_date" /> <br>
						<html:text property="dispclosedate" size="15"
							styleClass="textfield" disabled="true" /></td>
				</logic:equal>
			</logic:notEmpty>
		</tr>
		<logic:notEqual name="incidentForm" property="assoc_ID" value="">
			<tr>
				<td nowrap="nowrap" colspan="6"><bean:message
						key="colname.assoc_report" /> : &nbsp;&nbsp; <logic:notPresent
						name="lostdelay" scope="request">
						<logic:notEqual name="incidentForm" property="ld_inc_ID" value="">
							<a
								href="lostDelay.do?incident_ID=<bean:write name="incidentForm" property="ld_inc_ID"/>"><bean:message
									key="header.lostdelay" /> ( <bean:write name="incidentForm"
									property="ld_inc_ID" /> )</a>


						</logic:notEqual>
						<logic:equal name="incidentForm" property="ld_inc_ID" value="">
							<logic:notEqual name="incidentForm" property="readonly" value="1">
								<a href="lostDelay.do?add_assoc_report=1"><bean:message
										key="colname.add_ld" /></a>
							</logic:notEqual>
							<logic:equal name="incidentForm" property="readonly" value="1">
								<bean:message key="colname.no_assoc_ld" />
							</logic:equal>
						</logic:equal>
                  &nbsp;&nbsp;|&nbsp;&nbsp;
                </logic:notPresent> <logic:notPresent name="damaged" scope="request">
						<logic:notEqual name="incidentForm" property="damage_inc_ID"
							value="">
							<a
								href="damaged.do?incident_ID=<bean:write name="incidentForm" property="damage_inc_ID"/>"><bean:message
									key="header.damaged" /> ( <bean:write name="incidentForm"
									property="damage_inc_ID" /> )</a>

						</logic:notEqual>
						<logic:equal name="incidentForm" property="damage_inc_ID" value="">
							<logic:notEqual name="incidentForm" property="readonly" value="1">
								<a href="damaged.do?add_assoc_report=3"><bean:message
										key="colname.add_damage" /></a>
							</logic:notEqual>
							<logic:equal name="incidentForm" property="readonly" value="1">
								<bean:message key="colname.no_assoc_damage" />
							</logic:equal>
						</logic:equal>
						<logic:notPresent name="missing" scope="request">
                    &nbsp;&nbsp;|&nbsp;&nbsp;
                  </logic:notPresent>
					</logic:notPresent> <logic:notPresent name="missing" scope="request">
						<logic:notEqual name="incidentForm" property="ma_inc_ID" value="">
							<a
								href="missing.do?incident_ID=<bean:write name="incidentForm" property="ma_inc_ID"/>"><bean:message
									key="header.missing_articles" /> ( <bean:write
									name="incidentForm" property="ma_inc_ID" /> )</a>

						</logic:notEqual>
						<logic:equal name="incidentForm" property="ma_inc_ID" value="">
							<logic:notEqual name="incidentForm" property="readonly" value="1">
								<a href="missing.do?add_assoc_report=2"><bean:message
										key="colname.add_ma" /></a>
							</logic:notEqual>
							<logic:equal name="incidentForm" property="readonly" value="1">
								<bean:message key="colname.no_assoc_ma" />
							</logic:equal>
						</logic:equal>
					</logic:notPresent></td>
			</tr>
		</logic:notEqual>
	</table>
	<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		<tr>
			<td nowrap="nowrap"><bean:message key="colname.status" /> <br>
				<logic:notEqual name="incidentForm" property="incident_ID" value="">

					<logic:equal name="incidentForm" property="status_ID"
						value='<%= String.valueOf(TracingConstants.MBR_STATUS_CLOSED) %>'>
						<% if ((report_type == 1 &&  UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REOPEN_LOSTDELAY, a))
                  				 || (report_type == 0 &&  UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REOPEN_DAMAGED_BAG, a))
                  				|| (report_type == 2 &&  UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REOPEN_MISSING_ARTICLES, a)))
                  				{ %>
						<div id="tohide1">
							<html:select property="status_ID" styleClass="dropdown">
								<html:options collection="statuslist" property="status_ID"
									labelProperty="description" />
							</html:select>
						</div>
						<% } else { %>
						<input type="text" class="textfield" size="10"
							value="<bean:message key="incidentForm.closed_status"/>"
							disabled="disabled">
						<% } %>
					</logic:equal>
					<logic:notEqual name="incidentForm" property="status_ID"
						value='<%= String.valueOf(TracingConstants.MBR_STATUS_CLOSED) %>'>
						<div id="tohide1">
							<html:select property="status_ID" styleClass="dropdown">
								<html:options collection="statuslist" property="status_ID"
									labelProperty="description" />
							</html:select>
						</div>
					</logic:notEqual>
				</logic:notEqual> <logic:equal name="incidentForm" property="incident_ID" value="">
					<input type="text" class="textfield" size="10"
						value="<bean:message key="incidentForm.new_status"/>"
						disabled="disabled">
				</logic:equal></td>

			<td nowrap="nowrap"><bean:message key="colname.non_revenue" /><br>
				<div id="tohide2" <% if (displayNonRevenueCodes) { %>
					style="width: 105px;" <% } %>>
					<html:select property="nonrevenue" styleClass="dropdown"
						onchange="toggleRevenueCodesVisible();" styleId="isNonRevenue">
						<html:option value="0">
							<bean:message key="select.no" />
						</html:option>
						<html:option value="1">
							<bean:message key="select.yes" />
						</html:option>
					</html:select>
					<% if (displayNonRevenueCodes) { %>
					<html:select property="revenueCode" styleClass="dropdown"
						styleId="nonRevCodes">
						<html:option value="" />
						<html:options collection="nonRevenueCodesList"
							property="revenueCode" labelProperty="revenueCode" />
					</html:select>
					<% } %>
				</div></td>
			<td>
				<bean:message key="colname.report_method" /> 
				<br> 
				<html:select property="reportmethod" styleClass="dropdown">
					<html:options collection="reportmethodslist" property="value" labelProperty="label" />
				</html:select>
			</td>
			<td><bean:message key="colname.recordlocator.req" /> <br>
				<html:text property="recordlocator" size="18" maxlength="6"
					styleClass="textfield" /></td>
			<td><bean:message key="colname.ticket" /> <br> <html:text
					property="ticketnumber" size="18" maxlength="14"
					styleClass="textfield" /></td>
			<td nowrap="nowrap"><bean:message
					key="colname.stationassigned_nobr" /> <br> <input
				type="hidden" name="changeassignedstation"> <html:select
					property="stationassigned_ID" styleClass="dropdown"
					onchange="getagentassigned();">
					<html:options collection="stationlist" property="station_ID"
						labelProperty="stationcode" />
				</html:select></td>
			<td nowrap="nowrap">
				<div id="agentassigneddiv">
					<bean:message key="colname.agentassigned_nobr" />
					<br>
					<html:select property="agentassigned_ID" styleClass="dropdown">
						<option value="">
							<bean:message key="select.please_select" />
						</option>
						<html:options collection="agentassignedlist" property="agent_ID"
							labelProperty="username" />
					</html:select>
				</div>
			</td>
		</tr>
		<% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_WT_OTHER_CARRIER, a) && request.getAttribute("lostdelay") != null){ %>
		<logic:empty  name="incidentForm" property="incident_ID">
		<tr>
			<td colspan="3">
				<bean:message key="colname.wt.airline" />
				<br/>
				<html:select name="incidentForm"  property="wtCompanyCode" styleClass="dropdown" styleId="WtAirlineCode" onchange="checkSameAirline()" >
           			<html:option value=""><bean:message key="option.none" /></html:option>
           			<html:options collection="wtCompList" property="wtCompanyCode" labelProperty="wtCompanyCode" />
				</html:select>
      			
			</td>
			<td colspan="4">
				<bean:message key="colname.wt.station" />
				<br>
           		<html:text name="incidentForm"  property="wtStationCode" styleClass="textfield" styleId="wtStationCode" maxlength="3"  />
			</td>
		</tr>
		</logic:empty>
		<logic:notEmpty  name="incidentForm" property="incident_ID">
		<tr>
			<td colspan="3">
				<bean:message key="colname.wt.airline" />
				<br/>
				<html:select disabled="true" name="incidentForm"  property="wtCompanyCode" styleClass="dropdown" styleId="WtAirlineCode">
           			<html:option value=""><bean:message key="option.none" /></html:option>
           			<html:options collection="wtCompList" property="wtCompanyCode" labelProperty="wtCompanyCode" />
				</html:select>
      			<input type="hidden" name="wtCompanyCode" value="<bean:write name="incidentForm" property="wtCompanyCode"/>"/>
			</td>
			<td colspan="4">
				<bean:message key="colname.wt.station" />
				<br>
           		<html:text disabled="true" name="incidentForm"  property="wtStationCode" styleClass="textfield" styleId="wtStationCode" maxlength="3"/>
      			<input type="hidden" name="wtStationCode" value="<bean:write name="incidentForm" property="wtStationCode"/>"/>
			</td>
		</tr>
		</logic:notEmpty>
		<%} %>
	</table>
	<br> <br> &nbsp;&nbsp;&uarr; <a href="#"><bean:message
			key="link.to_top" /></a> <br> <br>

	<%
            	boolean hasPermission = UserPermissions.hasPermission(
            			TracingConstants.SYSTEM_COMPONENT_MANAGE_TASKS, a);
            	GeneralTask task = (GeneralTask) session
            			.getAttribute("sessionTaskContainer");
            	String incident_id = myform.getIncident_ID();
            	boolean display = false;
            	if (hasPermission
            			&& task != null
            			&& task instanceof com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask) {
            		com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask mtask = (com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask) task;
            		if (mtask.getIncident() != null
            				&& mtask.getIncident().getIncident_ID() != null
            				&& mtask.getIncident().getIncident_ID()
            						.equals(incident_id)) {
            			display = true;
            		}
            	}

            	if (display) {
            %>
	<jsp:include page="/pages/includes/general_task_incl.jsp" />
	<%} %>

	<jsp:include page="/pages/includes/auto_checklist_incl.jsp" />

	<jsp:include page="/pages/includes/contactinfo_incl.jsp" />

	<a name="passit"></a>
	<h1 class="green">

		<script language="javaScript">
		  		toggleRevenueCodesVisible();
		  
			  function swap(field, i, j) {

				  var swap1 = document.getElementsByName('theitinerary[' + i + '].' + field)[0];
				var swap2 = document.getElementsByName('theitinerary[' + j + '].' + field)[0];
				var tmp = swap1.value;
				swap1.value = swap2.value;
				swap2.value = tmp;
				}

			function swapItin(moveUp, moveDown) {
				swap('legfrom', moveUp, moveDown);	
				swap('legto', moveUp, moveDown);	
				swap('airline', moveUp, moveDown);	
				swap('flightnum', moveUp, moveDown);	
				swap('disdepartdate', moveUp, moveDown);	
				swap('disarrivedate', moveUp, moveDown);	
				swap('disschdeparttime', moveUp, moveDown);	
				swap('disscharrivetime', moveUp, moveDown);	
				swap('disactdeparttime', moveUp, moveDown);	
				swap('disactarrivetime', moveUp, moveDown);	
			}

			function swapUpItin(elem, indexes) {
				var idx = 0;

				for (var i = 0; i < indexes.length; ++i) {
					if (indexes[i] == elem) {
						idx = i;
						break;					
					}
				}

				if (idx < 1) { return; }
				var moveUp = indexes[idx];
				var moveDown = indexes[idx - 1];
				swapItin(moveUp, moveDown);
			}	


			function swapDownItin(elem, indexes) {
				var idx = 0;

				for (var i = 0; i < indexes.length; ++i) {
					if (indexes[i] == elem) {
						idx = i;
						break;					
					}
				}

				if (idx > indexes.length - 2) {	return;	}
				var moveUp = indexes[idx+1];
				var moveDown = indexes[idx];
				swapItin(moveUp, moveDown);
			}

			function hideThisItinerary(deleteThis, indexes) {
				var newArray = [];

				for (x = 0; x < indexes.length; ++x) {
					if (indexes[x] == deleteThis) {
						document.getElementById('placeHolder' + deleteThis).innerHTML = "";	
						if (indexes.length == 1){
							continue;
						}
						if (x == 0) {
							var remove = document.getElementById("moveUp" + (indexes[x + 1]));
							removeElement(remove);
						} else if (x == indexes.length-1) {
							var remove = document.getElementById("moveDown" + (indexes[x - 1]));
							removeElement(remove);
						}
					} else {
						newArray.push(indexes[x]);
					}
				}
				indexes = newArray;
				return indexes;
			}


				 <% 
				 	int itinSize = 0;
				 	List list = myform.getItinerarylist();
				 	String pItinIndexes = "";
					String bItinIndexes = "";
					int firstPIndex = -1;
					int firstBIndex = -1;
					int lastPIndex = 0;
					int lastBIndex = 0;
				 	for (int i = 0; i < list.size(); ++i) {
				 		Itinerary itin = (Itinerary) list.get(i);
				 		if (itin.getItinerarytype() == 0) {
							pItinIndexes += i + ",";
							if (firstPIndex == -1) {
								firstPIndex = i;
							}
							lastPIndex = i;	
				 		} else {
							bItinIndexes += i + ",";
							if (firstBIndex == -1) {
								firstBIndex = i;
							}
							lastBIndex = i;
				 		}
				 	}
				 	if (pItinIndexes.length() > 0) {
				 		pItinIndexes = pItinIndexes.substring(0, pItinIndexes.length() - 1);
				 	}
				 	if (bItinIndexes.length() > 0) {
				 		bItinIndexes = bItinIndexes.substring(0, bItinIndexes.length() - 1);
					}
					if (firstBIndex == -1) {
						firstBIndex = 0;
					}
					if (firstPIndex == -1) {
						firstPIndex = 0;
					}

				 	%>

				 var pItinIndexes = [<%=pItinIndexes%>];
				 var bItinIndexes = [<%=bItinIndexes%>];
		  </script>
		<bean:message key="header.itinerary" />
		<%
            if (report_type == 0) {
%>
		<a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_passenger_itineraries.htm#add passenger itinerary');return false;">
			<%
              } else if (report_type == 1) {
%> <a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_passenger_itineraries_(ld).htm#add passenger itinerary');return false;">
				<%
                } else if (report_type == 2) {
%> <a href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_passenger_itineraries_(ma).htm#add passenger itinerary');return false;">
					<%
                  }
%> <img src="deployment/main/images/nettracer/button_help.gif"
					width="20" height="21" border="0">
			</a>
	</h1>
	<span class="reqfield">*</span>
	<bean:message key="message.required" />
	<%
	      int t = 0;
	      int pCount = 0;
		  boolean hasPassItin = false;
	      %>
	<div id="<%=TracingConstants.JSP_PAX_ITIN %>">
	<logic:iterate id="theitinerary" indexId="k" name="incidentForm"
		property="itinerarylist">
		<logic:equal name="theitinerary" property="itinerarytype" value="0">
			<% hasPassItin = true; 
			   pCount++; %>


			<span id="placeHolder<%=t%>" style="float: right"> <%
	      if (firstPIndex != t) {	
		%> <input type="button" class="button" id="moveUp<%=t%>"
				value="Move Up" onclick="swapUpItin(<%=t%>, pItinIndexes);" /> <%
		}
		if (lastPIndex != t) {
		%> <input type="button" class="button" id="moveDown<%=t%>"
				value="Move Down" onclick="swapDownItin(<%=t%>, pItinIndexes);" /> <%
		}
		%>
			</span>

			<div id="<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k%>">
				<html:hidden name="theitinerary" property="itinerarytype" value="0"
					indexed="true" />
				<table class="<%=paxItinCss  %> <%=cssFormClass %>" cellspacing="0" cellpadding="0" name="hidexItinerary<%=k %>"
					id="hidexItinerary">
					<tr>
						<td>
							<html:hidden property="legfrom_type" name="theitinerary"/>
							<html:hidden property="legto_type" name="theitinerary"/>
						<bean:message key="colname.pax.fromto.req" /> <br>
							<html:text name="theitinerary" property="legfrom" size="3"
								maxlength="3" styleClass="textfield" indexed="true" /> <a
							href="#"
							onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border="0"></a> / <html:text name="theitinerary"
								property="legto" size="3" maxlength="3" styleClass="textfield"
								indexed="true" /> <a href="#"
							onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legto','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border="0"></a></td>
						<td><logic:empty name="theitinerary" property="airline">
								<jsp:setProperty name="theitinerary" property="airline"
									value="<%= a.getCompanycode_ID() %>" />
							</logic:empty> <bean:message key="colname.pax.flightnum.req" /> <br> <html:select
								name="theitinerary" property="airline" styleClass="dropdown"
								indexed="true">
								<html:option value="">
									<bean:message key="select.please_select" />
								</html:option>
								<html:options collection="companylistById"
									property="companyCode_ID" labelProperty="companyCode_ID" />
							</html:select> &nbsp; <html:text name="theitinerary" property="flightnum"
								size="4" maxlength="4" styleClass="textfield" indexed="true" />
						</td>
						<td nowrap="nowrap"><bean:message
								key="colname.pax.departdate.req" /> ( <%= a.getDateformat().getFormat() %>)
							<br> <html:text name="theitinerary" property="disdepartdate"
								size="11" maxlength="10" styleClass="textfield" indexed="true" /><img
							src="deployment/main/images/calendar/calendar_icon.gif"
							id="itcalendar<%= k %>" name="itcalendar<%= k %>" height="15"
							width="20" border="0" onmouseover="this.style.cursor='hand'"
							onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disdepartdate" %>','itcalendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
						<td nowrap="nowrap"><bean:message
								key="colname.pax.arrdate.req" /> ( <%= a.getDateformat().getFormat() %>)
							<br> <html:text name="theitinerary" property="disarrivedate"
								size="11" maxlength="10" styleClass="textfield" indexed="true" /><img
							src="deployment/main/images/calendar/calendar_icon.gif"
							id="itcalendar2<%= k %>" name="itcalendar2<%= k %>" height="15"
							width="20" border="0" onmouseover="this.style.cursor='hand'"
							onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disarrivedate" %>','itcalendar2<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
					</tr>
					<tr>
						<td><bean:message key="colname.schdeptime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disschdeparttime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
						<td><bean:message key="colname.scharrtime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disscharrivetime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
						<td><bean:message key="colname.actdeptime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disactdeparttime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
						<td><bean:message key="colname.actarrtime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disactarrivetime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
					</tr>
					<tr>
						<td colspan="4"><input type="button"
							value="<bean:message key="button.delete_pass_itinerary" />"
							onclick="pItinIndexes = hideThisItinerary(<%=k%>, pItinIndexes); hideThisDiv('<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k%>', '<bean:message key="colname.itinerary" />'); hideCloneButton()"
							id="button"></td>
					</tr>
				</table>
			</div>
		</logic:equal>
		<% ++t; %>
	</logic:iterate>
	</div>
	<input type="hidden" id="passItinTotal" name="passItinTotal" value="<%=pCount %>" />
	<script language="javascript" >

		function hideCloneButton() {
			var pNum = document.getElementById('passItinTotal');
			pNum.value = pNum.value - 1;
			if (pNum.value < 1) {
				document.getElementById('cloneButtonLoc').style.display = 'none';
			}
		}
	
	</script>
	<center>
		<select name="addpassitNum" id="addpassitNum">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
		</select>
		<html:submit property="addpassit" styleId="button">
			<bean:message key="button.add_cust_itinerary" />
		</html:submit>
	</center>
	<p class="blue">&nbsp;</p>
	<table style="width:100%;"><tr><td>
	<h1 class="green">
		<bean:message key="header.bag_itinerary" />
		<%
                if (report_type == 0) {
%>
		<a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_baggage_itinerary.htm#add_baggage_itinerary');return false;">
			<%
                  } else if (report_type == 1) {
%> <a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_itineraries_(ld).htm#add_baggage_itinerary');return false;">
				<%
                    } else if (report_type == 2) {
%> <a href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_baggage_itineraries_(ma).htm#add_baggage_itinerary');return false;">
					<%
                      }
%> <img src="deployment/main/images/nettracer/button_help.gif"
					width="20" height="21" border="0">
			</a>
	</h1>
	<span class="reqfield">*</span>
	<bean:message key="message.required" />
		<% if (hasPassItin) { %>
	</td><td id="cloneButtonLoc" style="padding-top: 12px;">
		<span style="float:right;" >
		<html:submit property="clonepassit" styleId="button">
			<bean:message key="button.clone_cust_itinerary" />
		</html:submit>
		</span>
		<% } %>
	</td></tr></table>
	<a name="bagit"></a>
	<% t = 0; %>
	<div id="<%=TracingConstants.JSP_BAG_ITIN %>">
	<logic:iterate id="theitinerary" indexId="k" name="incidentForm"
		property="itinerarylist">
		<logic:equal name="theitinerary" property="itinerarytype" value="1">


			<span id="placeHolder<%=t%>" style="float: right"> <%
	      if (firstBIndex != t) {	
		%> <input type="button" class="button" id="moveUp<%=t%>"
				value="Move Up" onclick="swapUpItin(<%=t%>, bItinIndexes);" /> <%
		}
		if (lastBIndex != t) {
		%> <input type="button" class="button" id="moveDown<%=t%>"
				value="Move Down" onclick="swapDownItin(<%=t%>, bItinIndexes);" /> <%
		}
		%>
			</span>


			<div id="<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k%>">
			
				<html:hidden name="theitinerary" property="itinerarytype" value="1"
					indexed="true" />
				<table class="<%=bagItinCss %> <%=cssFormClass %>" cellspacing="0" cellpadding="0" id="bagItin">
					<tr>
						<td><bean:message key="colname.bag.fromto.req" /> <br>
							<html:text name="theitinerary" property="legfrom" size="3"
								maxlength="3" styleClass="textfield" indexed="true" /> <a
							href="#"
							onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border="0"></a> / <html:text name="theitinerary"
								property="legto" size="3" maxlength="3" styleClass="textfield"
								indexed="true" /> <a href="#"
							onclick="openWindow('pages/popups/airportcodes.jsp?key=theitinerary[<%= k %>].legto','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border="0"></a></td>
						<td><bean:message key="colname.bag.flightnum.req" /> <br>

							<logic:empty name="theitinerary" property="airline">
								<jsp:setProperty name="theitinerary" property="airline"
									value="<%= a.getCompanycode_ID() %>" />
							</logic:empty> <html:select name="theitinerary" property="airline"
								styleClass="dropdown" indexed="true">
								<html:option value="">
									<bean:message key="select.please_select" />
								</html:option>
								<html:options collection="companylistById"
									property="companyCode_ID" labelProperty="companyCode_ID" />
							</html:select> &nbsp; <html:text name="theitinerary" property="flightnum"
								size="4" maxlength="4" styleClass="textfield" indexed="true" />
						</td>
						<td nowrap="nowrap"><bean:message
								key="colname.bag.departdate.req" /> ( <%= a.getDateformat().getFormat() %>)
							<br> <html:text name="theitinerary" property="disdepartdate"
								size="11" maxlength="10" styleClass="textfield" indexed="true" /><img
							src="deployment/main/images/calendar/calendar_icon.gif"
							id="calendar3<%= k %>" name="calendar3<%= k %>" height="15"
							width="20" border="0" onmouseover="this.style.cursor='hand'"
							onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disdepartdate" %>','calendar3<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
						<td nowrap="nowrap"><bean:message
								key="colname.bag.arrdate.req" /> ( <%= a.getDateformat().getFormat() %>)
							<br> <html:text name="theitinerary" property="disarrivedate"
								size="11" maxlength="10" styleClass="textfield" indexed="true" /><img
							src="deployment/main/images/calendar/calendar_icon.gif"
							id="calendar4<%= k %>" name="calendar4<%= k %>" height="15"
							width="20" border="0" onmouseover="this.style.cursor='hand'"
							onClick="cal1xx.select2(document.incidentForm, '<%= "theitinerary[" + k + "].disarrivedate" %>','calendar4<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
					</tr>
					<tr>
						<td><bean:message key="colname.schdeptime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disschdeparttime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
						<td><bean:message key="colname.scharrtime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disscharrivetime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
						<td><bean:message key="colname.actdeptime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disactdeparttime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
						<td><bean:message key="colname.actarrtime" /> ( <%= a.getTimeformat().getFormat() %>)
							<br> <html:text name="theitinerary"
								property="disactarrivetime" size="5" maxlength="5"
								styleClass="textfield" indexed="true" /></td>
					</tr>
					<tr>
						<td colspan="4"><input type="button"
							value="<bean:message key="button.delete_bag_itinerary" />"
							onclick="bItinIndexes = hideThisItinerary(<%=k%>, bItinIndexes); hideThisDiv('<%=TracingConstants.JSP_DELETE_ITINERARY %>_<%=k%>', '<bean:message key="colname.itinerary" />')"
							id="button"></td>
					</tr>
				</table>
			</div>
		</logic:equal>
		<% ++t; %>
	</logic:iterate>
	
	</div>
	<center>
		<select name="addbagitNum" id="addbagitNum">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
		</select>
		<html:submit property="addbagit" styleId="button">
			<bean:message key="button.add_bag_itinerary" />
		</html:submit>
	</center>
	<br> <br> &nbsp;&nbsp;&uarr; <a href="#"><bean:message
			key="link.to_top" /></a> <br> <br> <a name="checkedbaggage"></a>
	<h1 class="green">
		<bean:message key="header.checked_bag_info" />
		<%
                    if (report_type == 0) {
%>
		<a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_baggage_check_info.htm');return false;">
			<%
                      } else if (report_type == 1) {
%> <a href="#"
			onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_check_information_(ld).htm#top');return false;">
				<%
                        } else if (report_type == 2) {
%> <a href="#"
				onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_baggage_check_information_(ma).htm#top');return false;">
					<%
                          }
%> <img src="deployment/main/images/nettracer/button_help.gif"
					width="20" height="21" border="0">
			</a>
	</h1>
	<span class="reqfield">*</span>
	<bean:message key="message.required" />
	<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
		<tr>
			<td><bean:message key="colname.num_pass.req" /> <br> <html:text
					property="numpassengers" size="8" maxlength="4"
					styleClass="textfield" /></td>
			<td><bean:message key="colname.num_bag_checked.req" /> <br> <html:text
					property="numbagchecked" size="8" maxlength="4"
					styleClass="textfield" /></td>
			<td><bean:message key="colname.bags_rec.req" /> <br> <html:text
					property="numbagreceived" size="8" maxlength="4"
					styleClass="textfield" /></td>
		</tr>
		<tr>
			<td><bean:message key="colname.bag_loc.req" /> <br>
			
				<html:select property="checkedlocation" styleClass="dropdown">
					<html:option value="0">
						<bean:message key="select.please_select" />
					</html:option>
					<html:options collection="bagchecklist" property="value" labelProperty="label"/>
				</html:select>
			</td>
			


			<% 
				boolean val2 = PropertyBMO.isTrue(PropertyBMO.PROPERTY_INCIDENT_CUSTOMCLEARED_SELECT); 
			    boolean displayWeight = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_BAGGAGE_WEIGHT, a);
			    
			%>

			<td <% if (!displayWeight) { %>colspan="2"<% } %>>
			<bean:message key="colname.custom" /> <br> 
				<% if(!val2 || (myform.getCustomcleared()==1 || myform.getCustomcleared()==0)){ %>
				<html:select name="incidentForm" property="customcleared" styleClass="dropdown" >
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
				<% } else { %> 
				 <html:select name="incidentForm" property="customcleared" styleClass="dropdown" value="">
					<html:option value="">
						<bean:message key="select.please_select" />
					</html:option>
					<html:option value="1">
						<bean:message key="select.yes" />
					</html:option>
					<html:option value="0">
						<bean:message key="select.no" />
					</html:option>
				</html:select> <%  } %>
				</td>
			<% if (displayWeight) { %>
				<td id="bag_weight"><bean:message key="colname.total.weight.and.units" /><br>
				<html:text property="overall_weight" size="8" maxlength="10"
					styleClass="textfield" /> <html:select
					property="overall_weight_unit" styleClass="dropdown">
					<html:option value="lbs">lbs</html:option>
					<html:option value="kg">kg</html:option>
				</html:select></td>
			<% 
			    }
			%>
		</tr>
		<tr >
			<% 
				boolean courtesyReasonCollect = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_INCIDENT_COURTESY_REASON_COLLECT, a);
			%>
			<td <% if (!courtesyReasonCollect) { %>colspan="3"<% } %>>
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
			<% if (courtesyReasonCollect) { %>
				<td>
					<bean:message key="colname.courtesy.reason" />
					<br>
					<html:select property="courtesyReasonId" styleId="courtesyReasonId" styleClass="dropdown" onchange="updateCourtesyDescription();">
						<html:option value="0">
							<bean:message key="select.please_select" />
						</html:option>
						<% if (report_type == 0) { %>
							<html:options collection="damagedCourtesyReasonList" property="status_ID" labelProperty="description" />
						<% } else if (report_type == 1) { %>
							<html:options collection="lostDelayedCourtesyReasonList" property="status_ID" labelProperty="description" />
						<% } else { %>
							<html:options collection="courtesyReasonList" property="status_ID" labelProperty="description" />
						<% } %>			
					</html:select>
				</td>
				<td>
					<bean:message key="colname.courtesy.description" />
					<br>
					<html:textarea property="courtesyDescription" styleId="courtesyDescription" rows="3" cols="40" 
          				onkeydown="textCounter2(courtesyDescription, courtesyDescriptionCount, 100);" 
          				onkeyup="textCounter2(courtesyDescription, courtesyDescriptionCount, 100);" styleClass="textfield" />  
          			<input name="courtesyDescriptionCount" id="courtesyDescriptionCount" type="text" value="100" size="4" maxlength="4" disabled="true" />
				</td>
				<script type="text/javascript">
					updateCourtesyDescription();
				</script>				
			<% } %>
		</tr>
	</table>
	<br> <br> &nbsp;&nbsp;&uarr; <a href="#"><bean:message
			key="link.to_top" /></a> <br> <br>
	<script>
		checkSameAirline();
	</script>
			