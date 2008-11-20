<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<jsp:include page="/pages/includes/taskmanager_header.jsp" />
<tr>
  <!-- MIDDLE COLUMN -->
  <td id="middlecolumn">
    <!-- MAIN BODY -->
    <div id="maincontent">
      <html:form action="/logon">
        <input type="hidden" name="taskmanager" value="1">
        
          	<div class="headerleft">
          		<h1 class="green">
	            	<bean:message key="header.taskhome" />
    	        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/task_manager_home.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
    	        </h1>
        	</div>
        	
        	<div class="headerright">
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
		<p>&nbsp;</p>
		<p>&nbsp;</p>
        <table class="form2" cellspacing="0" cellpadding="0">
          <logic:present name="activityList" scope="session">
          
            <tr>
              <td class="header">
                <b><bean:message key="tasks.incidents" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="1">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>

			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>

            <tr>
              <td class="header">
                <b><bean:message key="tasks.ohds" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="2">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>

			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>

            <tr>
              <td class="header">
                <b><bean:message key="tasks.claims" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="3">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>

			<tr>
			  <td colspan="2" class="white">&nbsp;</td>
			</tr>

            <tr>
              <td class="header">
                <b><bean:message key="tasks.other" /></b>
              </td>
              <td class="header">
                <b><bean:message key="entries" /></b>
              </td>
            </tr>
            <logic:iterate id="activityDTO" name="activityList" type="com.bagnet.nettracer.tracing.dto.ActivityDTO">
              <logic:equal name="activityDTO" property="group" value="4">
                <tr>
                  <td>
                    <a href='<bean:write name="activityDTO" property="activityloc"/>'><bean:message key='<%= activityDTO.getActivityinfo().replaceAll(" ", "_") %>' /></a>
                  </td>
                  <td>
                    <bean:write name="activityDTO" property="entries" />
                  </td>
                </tr>
              </logic:equal>
            </logic:iterate>

          </logic:present>
        </table>
      </html:form>
