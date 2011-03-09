<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
          
          
          <logic:present name="errorMsg" scope="request">
          <bean:message name="errorMsg" scope="request"/>
          <br/>
          <br/>
          	<logic:present name="Incident_ID" scope="request">
          	<a href='GeneralTask.do?loadIncident=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
          	</logic:present>
          	<logic:present name="taskmanagerbutton" scope="request">
  			<input type="button" value="Return to Task Manager" onclick='document.location.href="logon.do?taskmanager=1";' id="button">
  			</logic:present>   
          </logic:present>
          
          
          
          <logic:notPresent name="errorMsg" scope="request">
          <bean:message key="generaltask.taskupdatedsuccessfully" />
          	<br/>
          	<logic:present name="gettaskbutton">
          	<input type="button" value="<bean:message key="generaltask.getnexttask" />" onclick='document.location.href="GeneralTask.do?gettask=<bean:write name='day' scope='request'/>";' id="button">
  			</logic:present>   
  			<logic:present name="taskmanagerbutton" scope="request">
  			<input type="button" value="Return to Task Manager" onclick='document.location.href="logon.do?taskmanager=1";' id="button">
  			</logic:present>   
         </logic:notPresent>
          </h1>
                </td>
              </tr>
            </table>
