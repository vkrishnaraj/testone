<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm"%>
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
        document.worldTracerFWDForm.deleteSegment.value = segNum;
        document.worldTracerFWDForm.submit();
    }
    
  function validateWtFwd(form) {
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
		    else if (currentElementName.indexOf("legfrom") != -1 && currentElementName.indexOf("legfrom_type") == -1)
		    {
		      if (currentElement.value.length < 1 || !checkLegFrom(currentElement.value))
		      {
		        alert("<%= (String)myMessages.getMessage(myLocale, "colname.fromto") %>" + " <%= (String)myMessages.getMessage(myLocale, "error.validation.station") %>");
		        currentElement.focus();
		        return false;
		      }
		    }
		    else if (currentElementName.indexOf("legto") != -1 && currentElementName.indexOf("legto_type") == -1)
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
		 	 if (!validateWTCompanyForward(form)) return false;
		 	 return true;
  }
    

  </SCRIPT>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  <script language="javascript">

function setReason(choose){
  var choosestr = choose.options[choose.selectedIndex].text;
  var reason = choosestr.substring(4);
  document.worldTracerFWDForm.reason_for_loss.value = reason;
}
</script>

  




<html:form action="worldtracerfwd.do" method="post">
	
	<input type="hidden" name="deleteSegment" />
	<input type="hidden" name="reason_for_loss" />

	<tr>
		
		<td id="middlecolumn">
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
				<td colspan="1"><bean:message key="FWD.matching.ahl" /> :</td>
				<td colspan="3"><html:text name="worldTracerFWDForm" size="20"
					property="matchingAhl" maxlength="11"/>
				</td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="colname.expedite_number" />
				<font color=red> * </font> :</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="expeditenum" size="20" maxlength="10" /></td>
			</tr>
			
			<tr>
				<td colspan="4"><bean:message key="Passenger_Name" />
				 :</td>
			</tr>
			<tr>
			    <td colspan="1"><html:text name="worldTracerFWDForm"
					property="passenger1" size="20" maxlength="13" /></td>
				<td colspan="1"><html:text name="worldTracerFWDForm"
					property="passenger2" size="20" maxlength="13" /></td>
				<td colspan="2"><html:text name="worldTracerFWDForm"
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
					<html:text name="worldTracerFWDForm" property="destinationAirline" size="4" maxlength="2" styleClass="textfield" />
                </td>
                <td colspan="1">
                  <bean:message key="colname.stationForwardTo" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td colspan="1">
                	<html:text name="worldTracerFWDForm" property="destinationStation" size="6" maxlength="3" styleClass="textfield" />
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
							key="colname.fromto" /><font color=red> * </font></strong>
						</td>
						<td colspan=1 valign="top"><strong> <bean:message
							key="colname.flightnum" /><font color=red> * </font> </strong>
						</td>
						<td colspan=1 nowrap><strong> <bean:message
							key="colname.departdate" /><font color=red> * </font><br>
						( <%=a.getDateformat().getFormat()%>) </strong>
						</td>
					</tr>
					<c:forEach items="${worldTracerFWDForm.itinerarylist}" var="segment" varStatus="status" >
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
									width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.worldTracerFWDForm, 'itinerary[${status.index}].departdate','calendar${status.index}','<%= a.getDateformat().getFormat() %>'); return false;">
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
				<font color=red> * </font>:</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="bagtag" size="20" maxlength="10" /></td>
			</tr>
            <%String codedes = null; %>
			<tr>
				<td nowrap colspan=4>
				<bean:message key="colname.losscode" /> 
				<html:select property="loss_code" styleClass="dropdown" onclick="setReason(this);">
					<html:option value="0">
						<bean:message key="select.please_select" />
					</html:option>
					<c:forEach items="${losscodes}" var="code">
						<option value="${code.loss_code}" ${code.loss_code == worldTracerFWDForm.loss_code ? 'selected="true"' : ''}>
							<c:out value="${code.loss_code} - ${code.description}"/>
						</option>
					</c:forEach>
				</html:select>
				</td>
			</tr>
			<logic:present name="faultstationlist" scope="request">
			<tr>
				<td colspan="1"><bean:message key="Fault_Station" />:</td>
				<td colspan="3">				
					<html:select name="worldTracerFWDForm" styleClass="dropdown"
						property="fault_station">
						<logic:empty name="worldTracerFWDForm" property="fault_station">
							<html:option value="">
								<bean:message key="select.please_select" />
							</html:option>
						</logic:empty>
						<html:options collection="faultstationlist" property="station_ID"
							labelProperty="stationcode" />
					</html:select>

				</td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="Fault_Terminal" />:</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="fault_terminal" size="20" maxlength="10" /></td>
			</tr>
			</logic:present>

			<tr>
				<td colspan="1"><bean:message key="Supplementary_Information" />:
				</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="supplementary_information" size="57" maxlength="56" /></td>
			</tr>
			<tr>
				<td colspan="4"><bean:message key="Teletype_Address" /><font color=red> * </font>:</td>
			</tr>
			<tr>
				<td><html:text name="worldTracerFWDForm"
					property="teletype_address1" size="20" maxlength="10" /></td>
				<td><html:text name="worldTracerFWDForm"
					property="teletype_address2" size="20" maxlength="10" /></td>
				<td><html:text name="worldTracerFWDForm"
					property="teletype_address3" size="20" maxlength="10" /></td>
				<td><html:text name="worldTracerFWDForm"
					property="teletype_address4" size="20" maxlength="10" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><INPUT type="button" Id="button"
					value="Back" onClick="history.back()"> &nbsp; <html:submit
					styleId="wtbutton" property="save" onclick="return validateWtFwd(this.form);" >
					<bean:message key="button.forward" />
				</html:submit></td>
			</tr>
		</table>
</html:form>
