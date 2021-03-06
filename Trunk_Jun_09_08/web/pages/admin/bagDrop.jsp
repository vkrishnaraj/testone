<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.forms.BagDropForm"%>

<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>

<%
  Agent a = (Agent)session.getAttribute("user");
%>

  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
    
	  </SCRIPT>
	  <SCRIPT LANGUAGE="JavaScript">
	function goprev() {
	  o = document.bagDropForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.bagDropForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.bagDropForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
  </script>

<html:form action="bagDrop.do" method="post">

  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
			<bean:message key='header.bagdrop.title'/>
        </h1>
      </div>
      
    </td>
  </tr>


 <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
      <div class="headerleft">
        <h1 class="green">
          <bean:message key='header.bagdrop.search.title'/>
        </h1>
        </div>

        <div id="pageheaderright">
			<logic:present name="cbroStationID" scope="session">
	            <bean:message key="Station" /> : 
	            <select name="cbroStation" onchange="submit()" class="textfield">
	              <logic:iterate id="station" name="stationlist" scope="session" type="com.bagnet.nettracer.tracing.db.Station">
	                <option value="<bean:write name="station" property="station_ID"/>" <% if (session.getAttribute("cbroStationID").equals("" + station.getStation_ID())) { %> selected <% } %>>
	                <bean:write name="station" property="stationcode" />
	                </option>
	              </logic:iterate>
	            </select>
	        </logic:present>
		</div>
		
		<br/>
		<br/>
		
        <strong>
          <bean:message key="wildcard" />
        </strong>
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
               <bean:message key='itin_airline'/>
              <br/>
                <html:text name="bagDropForm" property="dto.airlineCode" size="4" maxlength="4" styleClass="textfield" />
            </td>
            <td>
               <bean:message key='itin_flightnum'/>
              <br/>
                <html:text name="bagDropForm" property="dto.flightNumber" size="12" maxlength="15" styleClass="textfield" />
            </td>
            <td colspan="2">
            	<bean:message key='colname.bagdrop.search.scharrival'/>
            	(<%= a.getDateformat().getFormat() %>)	
		                <br>
		                <html:text property="searchStartDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.bagDropForm.searchStartDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
		                <html:text property="searchEndDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.bagDropForm.searchEndDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">           
            </td>
          </tr>
          <tr>
            <td colspan="5" align="center">
              <br>
              <html:submit styleId="button" property="search">
                <bean:message key="button.search" />
              </html:submit>
              &nbsp;
              <html:reset styleId="button">
                <bean:message key="button.reset" />
              </html:reset>
            </td>
          </tr>
        </table>
        <br>
        <h1 class="green">
          <bean:message key='header.search_result'/>
        </h1>
        
		<br/>
       	<bean:message key='bagdrop.avgtime'/> &nbsp; <bean:write name="bagDropForm" property="dispAvgTimeToCarousel"/>
		<br/>
		<br/>
		
	    <display:table requestURI="/bagDrop.do" name="bagDropForm.bagDropList" sort="external" class="form2" cellspacing="0" cellpadding="0" id="bagDrop" defaultsort="1" excludedParams="*" >
	       	<display:setProperty name="basic.empty.showtable" value="true"/>
	       	<display:column style="width:10%;" titleKey="colname.bagdrop.origin" value="${bagDrop.originStationCode}&nbsp;" sortable="true" sortName="origin"/>
	        <display:column style="width:10%;" titleKey="colname.bagdrop.dest"   value="${bagDrop.arrivalStationCode}&nbsp;" sortable="true" sortName="dest"/>
	        <display:column style="width:10%;" titleKey="itin_airline"     		 value="${bagDrop.airline}&nbsp;" sortable="true" sortName="airline"/>
	        <display:column style="width:10%;" titleKey="itin_flightnum"         value="${bagDrop.flight}&nbsp;" sortable="true" sortName="flight"/>
	        <display:column style="width:20%;" titleKey="colname.scharrtime"     value="${bagDrop.dispSchArrivalDateTime}&nbsp;" sortable="true" sortName="scharr" />
	        <display:column style="width:20%;" titleKey="colname.actarrtime"     value="${bagDrop.dispActArrivalDateTime}&nbsp;" sortable="true" sortName="actarr" />
	        <display:column style="width:20%;" titleKey="colname.bagdrop.bagdroptime"  value="${bagDrop.dispBagDropDateTime}&nbsp;" sortable="true" sortName="bagdrop" />
	        <display:column style="width:20%;" titleKey="colname.bagdrop.carouseltime" value="${bagDrop.dispTimeToCarousel}&nbsp;" paramId="carousel" paramProperty="carousel" sortable="false" />
	        <display:column style="width:20%;" titleKey="colname.action" value="Edit" href="bagDrop.do" paramId="editId" paramProperty="id" sortable="false"/>
	 		<display:footer>
			    <tr>
					<td colspan="9">
				       	<jsp:include page="/pages/includes/pagination_incl.jsp" />
					</td>
			    </tr>
			    <tr>
			    	<logic:equal name="bagDropForm" property="displayGetFlightInfoButton" value="true">
            			<td colspan="9" align="center">
            			<html:hidden property="getFlightData" value="1" disabled="true" />
              				<html:submit styleId="button" property="getFlightDataButton" onclick="this.disabled=true; this.form.getFlightData.disabled = false; this.value='Please wait'; this.form.submit();">
                				<bean:message key="button.bagdrop.refreshflightinfo" />
              				</html:submit>
            			</td>
            		</logic:equal>
          		</tr>
		 	</display:footer>
		</display:table>
	          		


</html:form>