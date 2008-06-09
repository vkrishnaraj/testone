<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
	Agent a = (Agent) session.getAttribute("user");
    String ohd_id = request.getParameter("ohd_id");
%>
<SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
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

function getreason(choose){
  var choosestr = choose.options[choose.selectedIndex].text;
  var reason = choosestr.substring(4);
  document.all.reason_for_loss.value = reason;
}
</script>

  
<!-- calendar stuff ends here -->

<html:form action="worldtracerfwd.do" method="post"
	onsubmit="return (validateForwardOHD(this) && setExpediteNum(this)); ">

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
				<td colspan="1"><html:text name="worldTracerFWDForm"
					property="ohd_ID" size="20" maxlength="13" onblur="submit()" /></td>
				<td colspan="1">

				    <bean:message
					key="wt.id" />:</td>
				<td colspan="1"><html:text name="worldTracerFWDForm"
					property="wt_id" size="20" maxlength="13" /></td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="FWD.placed.in.File" /> :</td>
				<td colspan="3"><html:select name="worldTracerFWDForm"
					property="placed_in_file" styleClass="dropdown">
					<html:option value=""></html:option>
					<html:option value="AHL">AHL</html:option>
					<html:option value="DPR">DPR</html:option>
				</html:select></td>
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
                  <bean:message key="colname.stationForwardTo" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td colspan="3">
                	<html:select name="worldTracerFWDForm" property="companyCode" styleClass="dropdown" onchange="submit()" >
                    	<html:options collection="companylist"  property="companyCode_ID" labelProperty="companyCode_ID" />
                  	</html:select>&nbsp;
                    <logic:present name="stationList" scope="request">
                      <html:select name="worldTracerFWDForm" styleClass="dropdown" property="destStation">
                        <logic:empty name="worldTracerFWDForm" property="destStation">
                          <html:option value="">
                            <bean:message key="select.please_select" />
                          </html:option>
                        </logic:empty>
                        <html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
                      </html:select>
                    </logic:present>

                </td>
              </tr>
			<tr>
				<td colspan="4">
				<table>
					<tr>
						<td colspan="3"><strong> <bean:message
							key="header.bag_itinerary" /> </strong></td>
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
					<logic:iterate indexId="k" id="itinerary"
						name="worldTracerFWDForm" property="itinerarylist">
						<tr>
							<td colspan=1 nowrap>
							<html:hidden name="itinerary" property="itinerarytype" value="1" indexed="true" />
							<html:text name="itinerary" property="legfrom" size="3" maxlength="3" styleClass="textfield" indexed="true" />
							<a href="#" 
							    onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerary[<%= k %>].legfrom','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border=0></a> 
								/ 
							<html:text name="itinerary" property="legto" size="3" maxlength="3" styleClass="textfield"   indexed="true" /> 
							<a href="#"
								onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerary[<%= k %>].legto','airportcode',500,600);return false;"><img
								src="deployment/main/images/nettracer/airport_codes.gif"
								border=0></a>
							</td>
							<td colspan=1><html:select name="itinerary" property="airline"
								styleClass="dropdown" indexed="true">
								<html:option value="">
									<bean:message key="select.please_select" />
								</html:option>
								<html:options collection="companylist" property="companyCode_ID"
									labelProperty="companyCode_ID" />
							</html:select> &nbsp; 
							<html:text styleClass="textfield" name="itinerary"
								property="flightnum" size="4" maxlength="4" indexed="true" />
							</td>
							<td colspan="1" nowrap><html:text styleClass="textfield" name="itinerary"
								property="disdepartdate" size="11" maxlength="10" indexed="true" /><img
								src="deployment/main/images/calendar/calendar_icon.gif"
								id="calendar<%= k %>" name="calendar<%= k %>" height="15"
								width="20" border="0" onmouseover="this.style.cursor='hand'"
								onClick="cal1xx.select2(document.worldTracerFWDForm, '<%= "itinerary[" + k + "].disdepartdate" %>','calendar<%= k %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>

						</tr>
						<tr>
							<td colspan=3>
							<html:submit styleId="button"
								property="deleteBag" indexed="true">
								<bean:message key="button.delete_bag_itinerary" />
							</html:submit>
							</td>
						</tr>
					</logic:iterate>
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
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="bagtag" size="20" maxlength="10" /></td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="Expedite_Tag_Number" />
				 :</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="ebagtag" size="20" maxlength="10" /></td>
			</tr>
            <%String codedes = null; %>
			<tr>
				<td nowrap colspan=4>
				<bean:message key="colname.losscode" /> 
				<html:select property="loss_code" styleClass="dropdown" onclick="getreason(this)">
					<html:option value="0">
						<bean:message key="select.please_select" />
					</html:option>
					<%
						java.util.List codes = (java.util.List) request
										.getAttribute("losscodes");

								for (java.util.Iterator i = codes.iterator(); i.hasNext();) {
									com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code) i
											.next();
					%>
					<OPTION VALUE="<%= "" + code.getLoss_code() %>"
						<% String lost_code = "" + ((com.bagnet.nettracer.tracing.forms.WorldTracerFWDForm)session.getAttribute("worldTracerFWDForm")).getLoss_code();  
					    if (lost_code.equals("" + code.getLoss_code())) { %>
						SELECTED <% } %>>        
						<%= "" + code.getLoss_code() %>-
                        <%= "" + code.getDescription() %>
					<%
					     codedes = code.getDescription();
						}
					%>
					
				</html:select>
				</td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="Fault_Station" />:</td>
				<td colspan="3">				
				<logic:present name="stationList" scope="request">
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
				</logic:present>
				</td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="Fault_Terminal" />:</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="fault_terminal" size="20" maxlength="10" /></td>
			</tr>
			<tr>
				<td colspan="1"><bean:message key="Reason_For_Loss_Comments" />:
				</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="reason_for_loss" size="20" maxlength="10" /></td>
			</tr>

			<tr>
				<td colspan="1"><bean:message key="Supplementary_Information" />:
				</td>
				<td colspan="3"><html:text name="worldTracerFWDForm"
					property="supplementary_information" size="20" maxlength="10" /></td>
			</tr>
			<tr>
				<td colspan="4"><bean:message key="Teletype_Address" />:</td>
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
					styleId="button" property="save">
					<bean:message key="button.forward" />
				</html:submit></td>
			</tr>
		</table>
</html:form>
