<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.WorldTracerFOHForm"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
	Agent a = (Agent) session.getAttribute("user");
    String ohd_id = request.getParameter("ohd_id");
%>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");
%>
<SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

    function deleteFwdSegment(segNum) {
        document.worldTracerFOHForm.deleteSegment.value = segNum;
        document.worldTracerFOHForm.submit();
    }
    
  function validateWtFoh(form) {
	  for (var j=0;j<form.length;j++) {
		  	currentElement = form.elements[j];
	    	currentElementName=currentElement.name;
	    	
			if (currentElementName.indexOf("expeditenum") != -1)
		    {
		      if (currentElement.value.length < 1)
		      {
		         alert("<%= (String)myMessages.getMessage(myLocale, "colname.expedite_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
		         currentElement.focus();
		         return false;
		      }
		        
		      else if (!checkExpedite(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.expedite_number") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.expedite") %>");
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("matchingAhl") != -1)
		    {
		      if (currentElement.value.length < 1)
		      {
		         alert("<%= (String)myMessages.getMessage(myLocale, "FWD.matching.ahl") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
		         currentElement.focus();
		         return false;
		      }
		    }
		    else if (currentElementName.indexOf("destinationAirline") != -1)
		    {
		      if (currentElement.value.length < 1)
		      {
		         alert("<%= (String)myMessages.getMessage(myLocale, "colname.airlineForwardTo") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
		         currentElement.focus();
		         return false;
		      }
		    }
		    else if (currentElementName.indexOf("destinationStation") != -1)
		    {
		      if (currentElement.value.length < 1)
		      {
		         alert("<%= (String)myMessages.getMessage(myLocale, "colname.stationForwardTo") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.isRequired") %>");
		         currentElement.focus();
		         return false;
		      }
		    }
		    else if (currentElementName.indexOf("legfrom") != -1)
		    {
		      if (currentElement.value.length < 1 || !checkLegFrom(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("legto") != -1)
		    {
		      if (currentElement.value.length < 1 || !checkLegFrom(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>"); 
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("airline") != -1)
		    {
		      if (currentElement.value.length < 1 || !checkFlightNum(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.flightNum") %>");
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("flightnum") != -1)
		    {
		      if (currentElement.value.length < 1 || !checkFlightNum(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.flightnum") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.flightNum") %>");
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("departdate") != -1)
		    {
		      if (currentElement.value.length < 1 || !checkDate(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.departdate") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.date") %>");
		        currentElement.focus();
		        return false;
		      }
		    }
		 	}
		 	 return true;
  }
    
    // End -->
  </SCRIPT>
<!-- Calendar includes -->
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    <!--
	var cal1xx = new CalendarPopup();	
	//cal1xx.showNavigationDropdowns();
// -->
  </SCRIPT>
  <!-- calendar stuff ends here -->
  <script language="javascript">
</script>

  
<!-- calendar stuff ends here -->

<jsp:include page="/pages/includes/validation_incl.jsp" />

<html:form action="worldtracerfoh.do" method="post">
	
	<input type="hidden" name="deleteSegment" />
	<input type="hidden" name="reason_for_loss" />

	<tr>
		<!-- MIDDLE COLUMN -->
		<td id="middlecolumn"><!-- MAIN BODY -->
		<div id="maincontent">
		<h1 class="green"><bean:message
			key="header.forward_to_worldtracer_title" /> </h1>
		<font color=red> 
		 <logic:messagesPresent message="true">
			<html:messages id="msg" message="true">		
				<bean:write name="msg" />
			</html:messages>
		 </logic:messagesPresent>
		 <br>
		 </font>
		<table class="form2" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="1"><bean:message
					key="colname.on_hand_report_number" />:</td>
				<td colspan="1"><html:text name="worldTracerFOHForm"
					property="ohd_ID" size="20" maxlength="13" onblur="submit()" readonly="true"/></td>
				<td colspan="1">

				    <bean:message
					key="wt.id" />:</td>
				<td colspan="1"><html:text name="worldTracerFOHForm" property="wt_id" size="20" maxlength="13" readonly="true"/></td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="FWD.matching.ahl" /> :</td>
				<td colspan="3"><html:text name="worldTracerFOHForm" size="20"
					property="matchingAhl" maxlength="11"/>
				</td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="colname.expedite_number" />
				<font color=red> * </font> :</td>
				<td colspan="3"><html:text name="worldTracerFOHForm"
					property="expeditenum" size="20" maxlength="10" /></td>
			</tr>
			
			<tr>
				<td colspan="4"><bean:message key="Passenger_Name" />
				 :</td>
			</tr>
			<tr>
			    <td colspan="1"><html:text name="worldTracerFOHForm"
					property="passenger1" size="20" maxlength="13" /></td>
				<td colspan="1"><html:text name="worldTracerFOHForm"
					property="passenger2" size="20" maxlength="13" /></td>
				<td colspan="2"><html:text name="worldTracerFOHForm"
					property="passenger3" size="20" maxlength="13" /></td>
			</tr>

            <tr>	
            	<td colspan="1">
                  <bean:message key="colname.airlineForwardTo" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td colspan="1">
					<html:text name="worldTracerFOHForm" property="destinationAirline" size="4" maxlength="2" styleClass="textfield" />
                </td>
                <td colspan="1">
                  <bean:message key="colname.stationForwardTo" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td colspan="1">
                	<html:text name="worldTracerFOHForm" property="destinationStation" size="6" maxlength="3" styleClass="textfield" />
                </td>
              </tr>
			<tr>
				<td colspan="4">
				<table>
					<tr>
						<td colspan="3"><strong> <bean:message
							key="header.fwd_bag_itinerary" /> </strong></td>
					</tr>
					<tr>
						<td colspan=1 valign="top"><strong> <bean:message
							key="colname.fromto" /> </strong>
						</td>
						<td colspan=1 valign="top"><strong> <bean:message
							key="colname.flightnum" /> </strong>
						</td>
						<td colspan=1 nowrap><strong> <bean:message
							key="colname.departdate" /><br>
						( <%=a.getDateformat().getFormat()%>) </strong>
						</td>
					</tr>
					<c:forEach items="${worldTracerFOHForm.itinerarylist}" var="segment" varStatus="status" >
						<tr>
							<td>
							<input type="text" size="3" maxlength="3" value="${segment.legfrom}" name="itinerary[${status.index}].legfrom" />
								<a href="#" 
							    onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerary[${status.index}].legfrom','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border=0></a>
								<c:out value="/" /> 
								<input type="text" value="${segment.legto}" size="3" maxlength="3" name="itinerary[${status.index}].legto" />
																<a href="#" 
							    onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerary[${status.index}].legto','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border=0></a>
							</td>
							<td>
								<input type="text" value="${segment.airline}" size="3" maxlength="3" name="itinerary[${status.index}].airline" />
								&nbsp;
								<input type="text" value="${segment.flightnum}" size="6" maxlength="5" name="itinerary[${status.index}].flightnum" />
							</td>
							<td colspan="1" nowrap="nowrap" >
								<input type="text" value="${segment.departdate}" size="11" maxlength="10" name="itinerary[${status.index}].departdate" />
								<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar${status.index}" name="calendar${status.index}" height="15"
									width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.worldTracerFOHForm, 'itinerary[${status.index}].departdate','calendar${status.index}','<%= a.getDateformat().getFormat() %>'); return false;">
							</td>
						</tr>
						<tr>
							<td colspan=3>
								<button type="button" value="${status.index}" id="button" onclick="deleteFwdSegment(${status.index});">
									<bean:message key="button.delete_bag_itinerary" />
								</button>
							</td>
						</tr>
					</c:forEach>
				</table>
				<center><html:submit styleId="button"
					property="additinerary">
					<bean:message key="button.add_bag_itinerary" />
				</html:submit></center>
				</td>
			</tr>

            <tr>
				<td colspan="1"><bean:message key="Tag_Number" />
				:</td>
				<td colspan="3"><html:text name="worldTracerFOHForm"
					property="bagtag" size="20" maxlength="10" /></td>
			</tr>

			<tr>
				<td colspan="1"><bean:message key="Supplementary_Information" />:
				</td>
				<td colspan="3"><html:text name="worldTracerFOHForm"
					property="supplementary_information" size="57" maxlength="56" /></td>
			</tr>
			<tr>
				<td colspan="4"><bean:message key="Teletype_Address" />:</td>
			</tr>
			<tr>
				<td><html:text name="worldTracerFOHForm"
					property="teletype_address1" size="20" maxlength="10" /></td>
				<td><html:text name="worldTracerFOHForm"
					property="teletype_address2" size="20" maxlength="10" /></td>
				<td><html:text name="worldTracerFOHForm"
					property="teletype_address3" size="20" maxlength="10" /></td>
				<td><html:text name="worldTracerFOHForm"
					property="teletype_address4" size="20" maxlength="10" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><INPUT type="button" Id="button"
					value="Back" onClick="history.back()"> &nbsp; <html:submit
					styleId="button" property="save" onclick="return validateWtFoh(this.form);" >
					<bean:message key="button.forward" />
				</html:submit></td>
			</tr>
		</table>
</html:form>
