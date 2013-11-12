<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="aero.nettracer.serviceprovider.wt_1_0.common.Ahl" %>
<%@ page import="aero.nettracer.serviceprovider.wt_1_0.common.Passenger" %>
<%@ page import="aero.nettracer.serviceprovider.wt_1_0.common.Itinerary" %>
<%@ page import="aero.nettracer.serviceprovider.wt_1_0.common.Address" %>




<%@page import="java.text.DateFormat"%><logic:present name="file_not_found" scope="request">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
          
			<bean:message key="header.file_detail" />
        	&nbsp;
        	(
        	<logic:present name="wt_raw_incident" scope="request">
        		<bean:write name="wt_raw_incident" scope="request"/>
      		  </logic:present>
        	<logic:present name="wt_raw_ahl" scope="request">
        		<bean:write name="wt_raw_ahl" scope="request"/>
       	 </logic:present>
        )
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
	<table>

<tr>
	<td border="1">
		<tr>
			<td class="label">File Not Found in World Tracer</td>
		</tr>
	</td>
</tr>
</table>
</div>
</td>
</logic:present>

<<logic:present name="wt_raw" scope="request">
<bean:define id="wtr" name="wt_raw" scope="request"/>
<bean:define id="paxlist" name="wt_raw" property="pax"/>
<bean:define id="paxitin" name="wt_raw" property="paxItinerary"/>
<bean:define id="item" name="wt_raw" property="item"/>



<%Ahl res = (Ahl)request.getAttribute("wt_raw"); %>

    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
          
			<bean:message key="header.file_detail" />
        	&nbsp;
        	(
        	<logic:present name="wt_raw_incident" scope="request">
        		<bean:write name="wt_raw_incident" scope="request"/>
      		  </logic:present>
        	<logic:present name="wt_raw_ohd" scope="request">
        		<bean:write name="wt_raw_ohd" scope="request"/>
       	 </logic:present>
        )
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
	<table>
	<td>

		<tr>
			<td class="label"><bean:message key="wt.view.ahl.date" /></td>
			<td>
				<%
				if (res.getCreateDate() != null){
					out.println(res.getCreateDate().getTime().toGMTString());
				}
				%>
			</td>
		</tr>
	
		<tr>
			<td class="label"><bean:message key="wt.view.ahl.station" /></td>
			<td>
				<%
				if(res.getFaultStation() != null && res.getFaultStation().length() > 0){
					out.println(res.getFaultStation());
				}
				%>
			</td>
		</tr>
		
		<tr>
			<td class="label"><bean:message key="wt.view.ahl.reason" /></td>
			<td>
				<%
				if(res.getFaultReason() != 0){
					out.println(res.getFaultReason());
				}
				%>
			</td>
		</tr>

		<tr>
			<td class="label"><bean:message key="wt.view.ahl.description" /></td>
			<td>
			<% 
			if(res.getFaultReasonDescription() != null && res.getFaultReasonDescription().length() > 0){
				out.println(res.getFaultReasonDescription());
			}
			%>
			</td>
		</tr>

       	<tr>
			<td class="label"><span class="label"><bean:message key="wt.view.ahl.pnr" /></span></td>
			<td><bean:write name="wtr" property="pnrLocator"/></td>
			</tr>
		<tr>
       	
       	<logic:iterate id="passenger" name="paxlist" indexId="i" type="aero.nettracer.serviceprovider.wt_1_0.common.Passenger">
				<tr>
					<%if (i.intValue() == 0){ %>
       			    	<td width="115px" class="label"><bean:message key="wt.view.ahl.passenger" /></td>
       				<%}else{ %>
       					<td width="115px" class="label"></td>
       				<%} %>
					<td align="char" char=":"  class="field">
					<bean:write name="passenger" property="firstname" />
					<bean:write name="passenger" property="middlename" />
					<bean:write name="passenger" property="lastname" />
					<br/>
					<logic:notEmpty name="passenger" property="address">
				<bean:write name="passenger" property="address.address1" />
<!--				<bean:write name="passenger" property="address.address2" />-->
<!--				<br/>-->
<!--				<bean:write name="passenger" property="address.city" />-->
<!--				<bean:write name="passenger" property="address.state" />-->
<!--				<bean:write name="passenger" property="address.zip" />-->
				<%
				Address addr = res.getPax()[i.intValue()].getAddress();
				String a = "";
				if (addr.getAddress1() != null && addr.getAddress1().length() > 0){
					a += addr.getAddress1() + "<br/>";
				}
				if (addr.getAddress2() != null && addr.getAddress2().length() > 0){
					a += addr.getAddress2() + "<br/>";
				}
				
				
				%>
				<br/>
				
					<bean:write name="passenger" property="address.emailAddress" />
				<br/>
					<logic:notEmpty name="passenger" property="address.homePhone">
						<bean:message key="wt.view.ahl.passenger.homephone"/> 
						<bean:write name="passenger" property="address.homePhone" />
				<br/>
					</logic:notEmpty>
					<logic:notEmpty name="passenger" property="address.workPhone">
						<bean:message key="wt.view.ahl.passenger.workphone"/>
						<bean:write name="passenger" property="address.workPhone" />
				<br/>
					</logic:notEmpty>
					<logic:notEmpty name="passenger" property="address.mobilePhone">
						<bean:message key="wt.view.ahl.passenger.mobilephone"/>
						<bean:write name="passenger" property="address.mobilePhone" />
				<br/>
					</logic:notEmpty>
					<logic:notEmpty name="passenger" property="address.altPhone">
						<bean:message key="wt.view.ahl.passenger.altphone"/>
						<bean:write name="passenger" property="address.altPhone" />
				<br/>
					</logic:notEmpty>
				</logic:notEmpty>
				<bean:write name="passenger" property="languageFreeFlow"/>
				<br/>
				</td>
				</tr>
		</logic:iterate>
		<br/>
       	<br/>
       	<td>
       	<logic:iterate id="pitin" name="paxitin" indexId="i" type="aero.nettracer.serviceprovider.wt_1_0.common.Itinerary" >
       			<tr>
       			<%if (i.intValue() == 0){ %>
       			    <td width="115px" class="label"><span class="label"><bean:message key="wt.view.ahl.itinerary" /></span></td>
       			<%}else{ %>
       				<td width="115px" class="label"></td>
       			<%} %>
       			<td align="char" char=":"  class="field">
<!--       			<bean:write name="pitin" property="airline" />-->
<!--       			<bean:write name="pitin" property="flightNumber" />-->
<!--				<bean:write name="pitin" property="departureCity" />-->
<!--				<bean:write name="pitin" property="arrivalCity" />-->
				<%	
				  	Itinerary itin = res.getPaxItinerary()[i.intValue()];
					String itinString = itin.getAirline() + " " +
										itin.getFlightNumber() + " " +
										itin.getDepartureCity() + " " +
										itin.getArrivalCity() + " ";
					if(itin.getFlightDate() != null && itin.getFlightDate().getTime() != null){
					DateFormat ITIN_DATE_FORMAT = new java.text.SimpleDateFormat("MMM dd, yyyy");
					itinString += ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
					}
					out.println(itinString);
				%>
				<br/>
				</td>
				</tr>
       	</logic:iterate>
       	<td/>
       	
       	<logic:iterate id="claim" name="wt_raw" property="claimCheck" indexId="i"
       	type="aero.nettracer.serviceprovider.wt_1_0.common.ClaimCheck">
       		<tr>
     		<%if (i.intValue() == 0){ %>
       			<td width="115px" class="label"><span class="label"><bean:message key="wt.view.ahl.tag" /></span></td>
       		<%}else{ %>
       			<td width="115px" class="label"></td>
       		<%} %>
       		<td align="char" char=":"   class="field">
       			<bean:write name="claim" property="airlineCode" />
       			<bean:write name="claim" property="tagNumber" />
			</td>
       		</tr>
       	</logic:iterate>
       	
       	<logic:iterate id="bag" name="item" indexId="i"
			type="aero.nettracer.serviceprovider.wt_1_0.common.Item">
			<tr>
				<%if (i.intValue() == 0){ %>
				<td width="115px" valign="top" class="label"><span class="label"><bean:message key="wt.view.ahl.bag" /></span></td>
				<%}else{ %>
				<td width="115px" class="label"></td>
				<%} %>

				<td class="field">
				<%if (i.intValue() != 0){ %>
				<hr align="left" width="175px"/>
				<%} %>
				<span class="label"><bean:message key="wt.view.ahl.bag.color" /></span>
				<bean:write name="bag" property="color" /> <br />
				<span class="label"><bean:message key="wt.view.ahl.bag.type" /></span> <bean:write name="bag"
					property="type" /> <br />
				<span class="label"><bean:message key="wt.view.ahl.bag.description" /></span> <bean:write name="bag"
					property="desc1" /> <br />
				<span class="label"><bean:message key="wt.view.ahl.bag.manufacturer" /></span>
				<bean:write name="bag" property="manufacturer" /> <br />
				<dt>Contents:</dt>
				<ul>
				<logic:iterate id="content" name="bag" property="content"
					indexId="j"
					type="aero.nettracer.serviceprovider.wt_1_0.common.Content">
					<li>
					<bean:write name="content" property="category" />:
					<bean:write name="content" property="description" />
					</li>
				</logic:iterate>
				</ul>
				</td>
			</tr>
		</logic:iterate>
       	
       	<tr>
			<td class="label"><span class="label"><bean:message key="wt.view.ahl.further.information" /></span></td>
			<td><bean:write name="wtr" property="furtherInfo"/></td>
			</tr>
		<tr>
       	
       	</td>
	</td>
</tr>

</table>
</div>
</td>
</tr>
            <tr>
              <td colspan="4" align="center">
                <INPUT id="button" type="button" value="Back" onClick="history.back()">
                </br>
                &nbsp;
              </td>
            </tr>
</logic:present>


