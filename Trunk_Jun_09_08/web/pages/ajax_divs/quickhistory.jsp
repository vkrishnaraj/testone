<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%
	Agent a = (Agent) session.getAttribute("user");
    boolean createDelayed = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISHANDLED_BAG, a);
    boolean createDamaged = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_DAMAGED_BAG, a);
    boolean createMissing = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_MISSING_ARTICLES, a);
    boolean createOnHand = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_ON_HAND_BAG, a);
%>


<%@ page import="java.util.ArrayList"%>

<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.db.OHD"%>
<!--<script type="text/javascript"
src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
</script>-->

<head>
<link href="<%=request.getContextPath()%>/deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/deployment/main/css/formstyles.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer_load.js"></script>
</head>
<script type="text/javascript">

jQuery(document).ready(function() {
	
	jQuery(".tab_content").hide();
	jQuery(".tab_content:first").show(); 

});

</script> 

<div id="header">
	<ul class="tabs">
		<span onclick="switchView(this)" id="searchShow"><li  rel="search" >Search
		</li></span>
		<span onclick="switchView(this)" id="historyShow"><li class="active" rel="history" >History
		</li></span>
	</ul>
</div>
<div style="text-align: center; padding: 0 0 0 0; border-bottom: 2px blue solid;">
<div class="tab_container"> 
<div id="history" class="tab_content">
	<span style="float:left">
		<h1>Matching User History</h1>
	</span>

	<c:if test="${empty quickSearchForm.histCon}">
		<br/><br/><div style="text-align:center; color:red;"><bean:message key="result.nohistory"/></div>
	</c:if>

	<c:if test="${!empty quickSearchForm.histCon}">

		<table class="modaltable" cellspacing="2" cellpadding="2">
			<tr class="mh">
				<td class="mh"><b> <bean:message
					key="colname.history_objectId" /></b></td>
				<td class="mh"><b> <bean:message
					key="colname.history_objectType" /></b></td>
				<td class="mh"><b> <bean:message
					key="colname.history_description" /></b></td>
				<td class="mh"><b> <bean:message
					key="colname.history_date" /> </b></td>
			</tr>

			<logic:iterate id="results" name="quickSearchForm"
				property="histCon"
				type="com.bagnet.nettracer.tracing.history.HistoryObject">
				
				<tr class="modaltdG">
					<!--<td><=session.getAttribute("user")%></td>-->
					<td style="text-align:left;"><a href="<bean:write name="results" property="linkURL"/>"><bean:write name="results" property="objectID" /></a></td>
					<td style="text-align:left;"><bean:write name="results" property="objectType" /></td>
					
					<td style="text-align:left;"><bean:write name="results" property="statusDesc" /></td>
					<td style="text-align:left;"><bean:write name="results" property="date" /></td>
					
				</tr>
			</logic:iterate>
		</table>
	</c:if>
</div>

<div id="search" class="tab_content">
<div style="text-align: center; padding: 0 5 0 5; border-bottom: 2px blue solid;">      <strong>Please enter your search criteria</strong><br/><p/><input type="text" name="search" class="textfield" id="quickSearchQuery3" onKeyDown="quickSearchKey3();"/>&nbsp; <button type="button" id="button" onclick="quickSearchKey4();">Search</button><br /><br /></div>
	<!--<html:text styleId="quickSearchQuery3" name="quickSearchForm" property="search" onkeydown="quickSearchKey3()" styleClass="textfield" size="20" maxlength="15"></html:text>
&nbsp;
<button type="button" id="button" onclick="this.disabled = true; this.value='<bean:message key="ajax.please_wait" />';  quickSearchKey4();">Search</button>-->


<c:if test="${quickSearchForm.dto.prepop == true}">
<% if (createOnHand || createMissing || createDelayed || createDamaged) { %>
	 <div style="text-align: center; padding: 5 5 5 5; border-bottom: 2px blue solid;">

	<h1>Pre-Population</h1>
	The input you provided, <strong><%=request.getParameter("search")%></strong>,
	was identified as a potentially valid tag number or record locator. If
	you would like to pre-populate a form, please select the appropriate
	options below: <br />
	<br /><center>
	<% if (createMissing || createDelayed || createDamaged) { %>
	<select id="qPrepopulateType">
	<%   if (createDelayed) { %>
		<option value="1">Delayed</option>
	<%   }
	     if (createDamaged) { %>
		<option value="3">Damaged</option>
	<%   }
	     if (createMissing) { %>
		<option value="2">Pilfered</option>
	<%   } %>
	</select>
	<button type="button" id="button"
		onclick="this.disabled = true; this.value='<bean:message key="ajax.please_wait" />';  qPrepopulateIncident(document.getElementById('qPrepopulateType').value, '<%=request.getParameter("search")%>', <c:out  value="${quickSearchForm.dto.prepopType}"/>);">Create
	Incident</button>
	&nbsp; &nbsp; 
	<%   if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.RESERVATION_POPULATE_OHD_ON)) { %>
	or &nbsp; &nbsp;
	<%   }
	   } 
	   if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.RESERVATION_POPULATE_OHD_ON) && createOnHand) { %>
	<button type="button" id="button"
		onclick="this.disabled = true; this.value='<bean:message key="ajax.please_wait" />';  qPrepopulateIncident(4, '<%=request.getParameter("search")%>', <c:out  value="${quickSearchForm.dto.prepopType}" />);">Create
	On-Hand</button>
	<% } %>
	</center>
	
	<br />
<% } %>
</c:if>



	<c:if test="${!empty quickSearchForm.dto && quickSearchForm.dto.displayIncList == true}">
		<span style="float:left"><h1>Matching Incidents</h1></span>
		<c:if test="${!empty quickSearchForm.dto && quickSearchForm.dto.limitedStartDate != null}">
			<span style="float:right; vertical-align: top; font-size: .8em">
				Automated Search Dates: <c:out  value="${quickSearchForm.dto.limitedStartDate}"/> - <c:out  value="${quickSearchForm.dto.limitedEndDate}"/>
			</span>
		</c:if>
		<c:if test="${empty quickSearchForm.dto.IList}">
		<br/><br/><div style="text-align:center; color:red; border-bottom:1px blue dashed;"><bean:message key="result.nomatches"/></div>
		</c:if>
		<c:if test="${!empty quickSearchForm.dto.IList}">

			<table class="modaltable" cellspacing="2" cellpadding="2">
				<tr class="mh">
					<td class="mh"><b> <bean:message
						key="colname.incident_num" /> </b></td>
					<td class="mh"><b> <bean:message key="colname.report_type" />
					</b></td>
					<td class="mh"><b> <bean:message
						key="colname.incident_create_date" /></b></td>
					<td class="mh"><b> <bean:message
						key="colname.stationassigned" /> </b></td>
					<td class="mh"><b> <bean:message key="header.status" /> </b></td>
					<td class="mh"><strong> <bean:message
						key="colname.color" />/<bean:message key="colname.bagtype" /></strong></td>
					<td class="mh"><b> <bean:message key="colname.claimnum" />
					</b></td>
					<td class="mh"><b> <bean:message key="colname.pass_name" />
					</b></td>
					<%
						if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
					%>
					<td class="mh"><b> <bean:message
						key="colname.worldtracer_id" /> </b></td>
					<%
						}
					%>
				</tr>

				<logic:iterate id="results" name="quickSearchForm"
					property="dto.IList"
					type="com.bagnet.nettracer.tracing.db.Incident">
					<bean:define id="items" name="results" property="itemlist" />
					<bean:define id="claimchecks" name="results"
						property="claimcheck_list" />
					<bean:define id="itinerary" name="results"
						property="itinerary_list" />
					<bean:define id="passengers" name="results"
						property="passenger_list" />


					<tr class="modaltdG">
						<td><a
							href='searchIncident.do?incident=<bean:write name="results" property="incident_ID"/>'><bean:write
							name="results" property="incident_ID" /></a></td>
						<td><bean:message name="results" property="itemtype.key" /></td>
						<td><bean:write name="results" property="displaydate" /></td>
						<td><bean:write name="results"
							property="stationassigned.stationcode" /></td>
						<td><bean:message name="results" property="status.key" /></td>
						<td>
						
						<logic:iterate id="item_list" name="items"
							type="com.bagnet.nettracer.tracing.db.Item">
							<logic:present name="item_list" property="color">
								<logic:notEqual name="item_list" property="color" value="">
									<bean:write name="item_list" property="color" />

								</logic:notEqual> &nbsp;
							</logic:present>
							<logic:present name="item_list" property="bagtype">
								<logic:notEqual name="item_list" property="bagtype" value="">
									<bean:write name="item_list" property="bagtype" />
								</logic:notEqual>
							</logic:present>
							<br />

						</logic:iterate>
						
						</td>
						<td>                      <logic:iterate id="item_list" name="items" type="com.bagnet.nettracer.tracing.db.Item">
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
                      </logic:iterate></td>
						<td><%
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
                      &nbsp;</td>
                      
                         <%
                                               	if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
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
			</table>

		<c:if test="${quickSearchForm.dto.IMore == true}">
			<div style="text-align:center;"><a href="searchIncident.do"><bean:message key="result.toomanymatches"/></a></div>
		</c:if>
		</c:if>
	</c:if>


	

<br />
	<c:if test="${!empty quickSearchForm.dto && quickSearchForm.dto.displayOhdList == true}">
	<span style="float:left"><h1>Matching On-Hands</h1></span>
		<c:if test="${!empty quickSearchForm.dto && quickSearchForm.dto.limitedStartDate != null}">
			<span style="float:right; vertical-align: top; font-size: .8em">
				Automated Search Dates: <c:out  value="${quickSearchForm.dto.limitedStartDate}"/> - <c:out  value="${quickSearchForm.dto.limitedEndDate}"/>
			</span>
		</c:if>
		<c:if test="${empty quickSearchForm.dto.OList}">
		<br/><br/><div style="text-align:center; color:red; border-bottom:1px blue dashed;"><bean:message key="result.nomatches"/></div>
		</c:if>
		<c:if test="${!empty quickSearchForm.dto.OList}">
		
<table class="modaltable" cellspacing="2" cellpadding="2">

              <tr class="mh">
                <td class="mh">
                  <strong>
                    <bean:message key="colname.on_hand_report_number" />
                  </strong>
                </td>
                <td class="mh">
                  <strong>
                    <bean:message key="colname.ohd_create_date" />
                  </strong>
                </td>
                <td class="mh">
                  <strong>
                    <bean:message key="colname.bag_tag_number" />
                  </strong>
                </td>
                <td class="mh">
                  <strong>
                    <bean:message key="header.status" />
                  </strong>
                </td>
                <td class="mh">
                  <strong>
                    <bean:message key="colname.color" />/<bean:message key="colname.bagtype" /> 
                  </strong>
                </td>
                <td class="mh">
                  <strong>
                    <bean:message key="colname.holding_station" />
                  </strong>
                </td>
                <td class="mh">
                  <strong>
                    <bean:message key="colname.name" />
                  </strong>
                </td>
                <%
                	if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
                %>
                <td class="mh">
                	<strong>
                		<bean:message key="colname.worldtracer_id"/>
                	</strong>
                </td>
                <%
                	}
                %>
                <td class="mh">
                  <strong>
                    <bean:message key="header.action" />
                  </strong>
                </td>
              </tr>
              
				<logic:iterate id="ohd" name="quickSearchForm"
					property="dto.OList"
					type="com.bagnet.nettracer.tracing.db.OHD">


                <tr class="modaltdG">
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
                    <bean:write name="ohd" property="color" />&nbsp;
                    <logic:empty name="ohd" property="type">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="ohd" property="type" />
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
                   	if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
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
	if (a.getStation().getStation_ID() != ((OHD) ohd).getHoldingStation().getStation_ID()) {
%>
                        <A HREF="request_on_hand.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="request" /></a><br/>
<%
	} else {
%>
                        &nbsp;
<%
	}

					if (a.getStation().getStation_ID() == ((OHD) ohd).getHoldingStation().getStation_ID()) {
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
                  </logic:notEqual>
                </tr>
              </logic:iterate>
              
              
              
</table>

		<c:if test="${quickSearchForm.dto.OMore == true}">
		<div style="text-align:center;"><a href="searchOnHand.do"><bean:message key="result.toomanymatches"/></a></div>
		</c:if>
		</c:if>
	</c:if>

<br />


	<c:if test="${!empty quickSearchForm.dto && quickSearchForm.dto.displayScanList == true}">
		<span style="float: left"><h1>Scan Data</h1></span>
<span style="float:right; vertical-align: top; font-size: .8em">
Automated Search Dates: <c:out  value="${quickSearchForm.dto.startDate}"/> - <c:out  value="${quickSearchForm.dto.endDate}"/>
</span>
		<c:if test="${empty quickSearchForm.dto.SList}">
		<br/><br/><div style="text-align:center; color:red; border-bottom:1px blue dashed;"><bean:message key="result.nomatches"/></div>
		</c:if>
		<c:if test="${!empty quickSearchForm.dto.SList}">
		<script LANGUAGE="javascript">
			var cal1xx = new CalendarPopup();
		</script>
		
<table class="modaltable" cellspacing="2" cellpadding="2">

	<tr class="mh">
              <td width="24%" class="mh"><b><bean:message key="scanner.string1" /></b></td>
              <td width="12%" class="mh"><b><bean:message key="scanner.string2" /></b></td>
              <td width="12%" class="mh"><b><bean:message key="scanner.string3" /></b></td>
              <td width="40%" class="mh"><b><bean:message key="scanner.string4" /></b></td>
              <td width="12%" class="mh"><b><bean:message key="scanner.ohd.id" /></b></td>
	</tr>
	            <c:forEach var="scannerDTO" items="${quickSearchForm.dto.SList}">
				<tr class="modaltdG">
                <td id="str1">
                  <c:if test='${scannerDTO.string1 != null && scannerDTO.string1 != ""}'>
                    <c:out value="${scannerDTO.string1}" />
                  </c:if>
                  <c:if test='${scannerDTO.string1 == null && scannerDTO.string1 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str2">
                  <c:if test='${scannerDTO.string2 != null && scannerDTO.string2 != ""}'>
                    <c:out value="${scannerDTO.string2}" />
                  </c:if>
                  <c:if test='${scannerDTO.string2 == null || scannerDTO.string2 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str3">
                  <c:if test='${scannerDTO.string3 != null && scannerDTO.string3 != ""}'>
                    <c:out value="${scannerDTO.string3}" />
                  </c:if>
                  <c:if test='${scannerDTO.string3 == null || scannerDTO.string3 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str4"> 
                  <c:if test='${scannerDTO.string4 != null && scannerDTO.string4 != ""}'>
                    <c:out value="${scannerDTO.string4}" escapeXml="false"/>
                  </c:if>
                   <c:if test='${scannerDTO.string4 == null || scannerDTO.string4 == ""}'>
                    &nbsp;
                  </c:if>
                </td>
                <td id="str5">
                  <c:if test='${scannerDTO.ohdId != null && scannerDTO.ohdId != ""}'>
                    <a href="addOnHandBag.do?ohd_ID=<c:out value="${scannerDTO.ohdId}" />">
                      <c:out value="${scannerDTO.ohdId}" />
                    </a>
                  </c:if>
                  <c:if test='${scannerDTO.ohdId == null || scannerDTO.ohdId == ""}'>
                    &nbsp;
                  </c:if>
                </td>
				</tr>
				</c:forEach>
			

		</table>
	</c:if>
</c:if>


<br />
</div>


</div>
