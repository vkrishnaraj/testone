<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%
  Agent a = (Agent)session.getAttribute("user");
  if (request.getAttribute("rowsperpage") == null) {
	  request.setAttribute("rowsperpage", "0");
  }
  if (request.getAttribute("currpage") == null) {
	  request.setAttribute("currpage", "0");
  }
  
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  <script language="javascript">
    
function goprev() {
  o = document.searchDisputeForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchDisputeForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchDisputeForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="searchDispute.do" method="post" focus="incident_ID" onsubmit="fillzero(this.incident_ID, 13); return validateSearch(this);">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <td></td>
              <td></td>
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
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='logon.do?taskmanager=1'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.taskmanagerhome" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href='searchDispute.do?actionType=manage'><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.manage.dispute" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
<!-- test it -->   
<!--
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
-->
<!-- test it -->     
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_disputes" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.incident_file_num2" />
                  <br>
                  <html:text property="incident_ID" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td>
                	<bean:message key="colname.dispute.type"/>
                	<html:select property="dispute_type" styleClass="dropdown">      
				        <html:option value="0">
				          <bean:message key="select.please_select" />
				        </html:option>
				        <html:option value="1">
				          <bean:message key="select.code_dispute" />
				        </html:option>
				        <html:option value="2">
				          <bean:message key="select.station_dispute" />
				        </html:option>
				        <html:option value="3">
				          <bean:message key="select.code_station_dispute" />
				        </html:option>
				    </html:select>
				        
                </td>
              </tr>

              <tr>
                <td colspan="2" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;

                <html:reset styleId="button">
                  <bean:message key="button.reset" />
                </html:reset>
									
                <% boolean showGetNext = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_GET_NEXT, a);
                
		    	if(showGetNext)
		    	{%>
		    	
		    	    <html:submit property="getnext" styleId="button">
                    	<bean:message key="button.getnext" />
                    </html:submit>
                
		    	<% } %>

                </td>
              </tr>
            </table>
            <logic:present name="resultlist" scope="request">
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.incident_file_num_br" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="dispute.incident_create_date_short" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="dispute.dispute_create_date_short" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.fault.station" />
                    </b>
                  </td>   
                  <td>
                    <b>
                      <bean:message key="colname.loss.code" />
                    </b>
                  </td>                                 
                  <td>
                    <b>
                      <bean:message key="colname.disputing.agent" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.suggested.fault.station" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.suggested.loss.code" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.travel.date" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.dispute.type" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="dispute" name="resultlist" type="com.bagnet.nettracer.tracing.db.dr.Dispute">
                  	<bean:define id="station" name="dispute" property="suggestedFaultStation" />
					<bean:define id="disputeAgent" name="dispute" property="disputeAgent" />
					<bean:define id="incident" name="dispute" property="incident" />
					<%	String deptDate= dispute.getIncident().getItinerary_list().get(0).getDisdepartdate();
						String bfs=dispute.getBeforeDisputeFaultStation().getStationcode();
						String sfs=dispute.getSuggestedFaultStation().getStationcode();
						int blc=dispute.getBeforeDisputeLossCode();
						int slc=dispute.getSuggestedLossCode();
					%>
					
					<bean:define id="itinerary" name="dispute" property="incident.itinerary" />
					<bean:define id="beforeDisputeFaultStation" name="dispute" property="beforeDisputeFaultStation" />
                  <tr>
                    <td>
                      <a href='disputeResolution.do?id=<bean:write name="incident" property="incident_ID"/>&actionType=viewToResolve'><bean:write name="incident" property="incident_ID" /></a>
                    </td>
                    <td>
                      <bean:write name="incident" property="displaydate" />
                    </td>
                    <td>
                      <bean:write name="dispute" property="dispTimestampCreated" />
                    </td>
                    <td>                
                       <bean:write name="beforeDisputeFaultStation" property="stationcode" />             
                    </td>  
                    <td>
                      <bean:write name="dispute" property="beforeDisputeLossCode" />
                    </td>                                      
                    <td>
                      <bean:write name="disputeAgent" property="username" />
                    </td>
                    <td>
                      <bean:write name="station" property="stationcode" />
                    </td>
                    <td>
                      <bean:write name="dispute" property="suggestedLossCode" />
                    </td>
                    <td>
                      <%= deptDate %>
                    </td>
                    <td>
                    <%=(bfs==sfs&&blc!=slc)?"Code Dispute":"" %>
                    <%=(bfs!=sfs&&blc==slc)?"Station Dispute":"" %>
                    <%=(bfs!=sfs&&blc!=slc)?"Code and Station Dispute":"" %>
                    </td>
                  </tr>
                </logic:iterate>
                <tr>
                  <td colspan="<logic:notEmpty name="searchDisputeForm" property="flightnum">13</logic:notEmpty> <logic:empty name="searchDisputeForm" property="flightnum">12</logic:empty>">
                    
                    <jsp:include page="/pages/includes/pagination_incl.jsp" />
                    
                  </td>
                </tr>               
              </table>
              <script language=javascript>
                
  document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
