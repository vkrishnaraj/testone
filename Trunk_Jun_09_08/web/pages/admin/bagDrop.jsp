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
            <td colspan="4" align="center">
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
        
	    <display:table requestURI="/bagDrop.do" name="bagDropForm.bagDropList" sort="external" class="form2" cellspacing="0" cellpadding="0" id="<%=TracingConstants.TABLE_ID_BAG_DROP %>" defaultsort="1"  >
	       	<display:setProperty name="basic.empty.showtable" value="true"/>
	       	<display:column style="width:10%;" titleKey="colname.bagdrop.origin" property="originStationCode" sortable="true" sortName="origin"/>
	        <display:column style="width:10%;" titleKey="colname.bagdrop.dest" property="arrivalStationCode" sortable="true" sortName="dest"/>
	        <display:column style="width:10%;" titleKey="itin_flightnum" property="flight" sortable="true" sortName="flight"/>
	        <display:column style="width:20%;" titleKey="colname.scharrtime" property="dispSchArrivalDateTimeCell" sortable="true" sortName="scharr" />
	        <display:column style="width:20%;" titleKey="colname.actarrtime" property="dispActArrivalDateTimeCell" sortable="true" sortName="actarr" />
	        <display:column style="width:20%;" titleKey="colname.bagdrop.bagdroptime" property="dispBagDropDateTimeCell" sortable="true" sortName="bagdrop" />
	        <display:column style="width:20%;" titleKey="colname.bagdrop.carouseltime" property="dispTimeToCarousel" paramId="carousel" paramProperty="carousel" sortable="false" />
	        <display:column style="width:20%;" titleKey="colname.action" value="Edit" href="bagDrop.do" paramId="editId" paramProperty="id" sortable="false"/>
	 		<display:footer>
			    <tr>
					<td colspan="9">
				       	<jsp:include page="/pages/includes/pagination_incl.jsp" />
					</td>
			    </tr>
			    <tr>
            		<td colspan="9" align="center">
              			<html:submit styleId="button" property="getFlightData">
                			<bean:message key="button.bagdrop.refreshflightinfo" />
              			</html:submit>
            		</td>
          		</tr>
		 	</display:footer>
		</display:table>
	          		


</html:form>