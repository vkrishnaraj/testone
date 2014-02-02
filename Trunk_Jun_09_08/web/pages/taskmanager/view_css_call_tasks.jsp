<%@page import="com.bagnet.nettracer.tracing.db.taskmanager.FourDayTask"%>
<%@page import="com.bagnet.nettracer.tracing.db.taskmanager.FiveDayTask"%>
<%@page import="com.bagnet.nettracer.tracing.db.taskmanager.ThreeDayTask"%>
<%@page import="com.bagnet.nettracer.tracing.db.taskmanager.TwoDayTask"%>
<%@page import="com.bagnet.nettracer.tracing.db.taskmanager.OneDayTask"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
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
  o = document.generaltaskForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.generaltaskForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
	  o = document.generaltaskForm;
	  o.currpage.value = i;
	  o.pagination.value="1";
	  o.submit();
}
function updatePagination() {
    return true;
}


  </script>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="css_calls.do?tasklist=NO">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
               <bean:message key="TASK_LABEL_CSS_DAILY" />
          </h1>
          <br>

          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
            </table>
                        
            <logic:notPresent name="resultlist" scope="request">
            <center>
               <h1 class="green">No new tasks</h1>
              </center>
              </logic:notPresent>
            <logic:present name="resultlist" scope="request">
            <center>
            	<input type="button" value="Start Working!" onclick='this.disabled;document.location.href="css_calls.do?gettask=1";return true;' id="button">
            </center>
            <br/>
              <h1 class="green">
                <bean:message key="header.morningduties.incidentlist" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td class="header">
                      <bean:message key="colname.stationassigned" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.stationcreated" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.incident_file_num_br" />
                  </td>
                  <td class="header">
                      <bean:message key="generaltask.time.to.call" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.pass_name" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.css.call.type" />
                  </td>
                  <td class="header">
                      <bean:message key="colname.css.working.agent" />
                  </td>
                </tr>
                <logic:iterate indexId="i" id="results" name="resultlist" type="com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask">
                  <bean:define id="incident" name="results" property="incident" />
                  <bean:define id="passengers" name="incident" property="passenger_list" />
                  <tr>
                    <td>
                      <bean:write name="incident" property="stationcreated.stationcode" />
                    </td>
                    <td>
                      <bean:write name="incident" property="stationassigned.stationcode" />
                    </td>
                    <td>
                      <% if (TracingConstants.TASK_MANAGER_WORKING == results.getStatus().getStatus_ID()) { %>
                      	<bean:write name="incident" property="incident_ID" />
                      <% } else { %>
                      	<a id="<%="link" + i %>" href='css_calls.do?gettask=1&incident=<bean:write name="incident" property="incident_ID"/>'><bean:write name="incident" property="incident_ID" /></a>
                      <% } %>
                    </td>
                    <td>
                      <%=TracingConstants.getDisplayDate(results.getOpened_timestamp(), a) %>&nbsp;
                    </td>
                    <td>
					  <%  boolean hasp = false; %>
                      <logic:iterate id="passenger_list" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
						<% hasp = false; %>
                        <logic:notEqual name="passenger_list" property="lastname" value="">
                          <bean:write name="passenger_list" property="lastname" />,
						  <% hasp = true; %>
                        </logic:notEqual>
                        <logic:notEqual name="passenger_list" property="firstname" value="">
						  <% hasp = true; %>
                        </logic:notEqual>
                        <bean:write name="passenger_list" property="firstname" />
                        <bean:write name="passenger_list" property="middlename" />
						<% if (hasp) { %>
                          <br>
						<% } %>
                      </logic:iterate>
                      &nbsp;
                    </td>
                    <td>
                      <% if (results instanceof OneDayTask) { %>
                      	<bean:message key="TASK_LABEL_1DAY" />
                      <% } else if (results instanceof TwoDayTask) { %>
                      	<bean:message key="TASK_LABEL_2DAY" />
                      <% } else if (results instanceof ThreeDayTask) { %>
                      	<bean:message key="TASK_LABEL_3DAY" />
                      <% } else if (results instanceof FourDayTask) { %>
                      	<bean:message key="TASK_LABEL_4DAY" />
                      <% } else if (results instanceof FiveDayTask) { %>
                      	<bean:message key="TASK_LABEL_5DAY" />
                      <% } %>
                    </td>
                    <td>
                      <% if (TracingConstants.TASK_MANAGER_WORKING == results.getStatus().getStatus_ID()) { %>
                      	<bean:write name="results" property="assigned_agent.username" />
                      <% } %>&nbsp;
                    </td>
                  </tr>
                </logic:iterate>
                <tr>
					<td colspan="12"> <jsp:include
						page="/pages/includes/pagination_incl.jsp" /> 
					</td>
				</tr>
              </table>
            </logic:present>
          </html:form>
