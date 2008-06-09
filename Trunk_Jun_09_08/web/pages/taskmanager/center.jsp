<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<jsp:include page="../includes/taskmanager_header.jsp" />
<tr>
  <!-- MIDDLE COLUMN -->
  <td id="middlecolumn">
    <!-- MAIN BODY -->
    <div id="maincontent">
      <html:form action="/logon">
        <input type="hidden" name="taskmanager" value="1">
        <h1 class="green">
          <bean:message key="header.taskhome" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/task_manager_home.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <logic:present name="cbroStationID" scope="session">
          <p align="right">
          	<div id="tohide1" align="right">
            <bean:message key="Station" />
            :
            <select name="cbroStation" onchange="submit()" class="textfield">
              <logic:iterate id="station" name="stationlist" scope="session" type="com.bagnet.nettracer.tracing.db.Station">
                <option value="<bean:write name="station" property="station_ID"/>" <% if (session.getAttribute("cbroStationID").equals("" + station.getStation_ID())) { %> selected <% } %>>
                <bean:write name="station" property="stationcode" />
                </option>
              </logic:iterate>
            </select>
            </div>
          </p>
        </logic:present>
        <br>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <b><bean:message key="tasks" /></b>
            </td>
            <td>
              <b><bean:message key="entries" /></b>
            </td>
          </tr>
          <logic:present name="activityList" scope="session">
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <tr>
                <td>
                  <a href="<bean:write name="activityDTO" property="activityloc"/>"><bean:message key="<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>" /></a>
                </td>
                <td>
                  <bean:write name="activityDTO" property="entries" />
                </td>
              </tr>
            </logic:iterate>
          </logic:present>
        </table>
      </html:form>
