<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

  </SCRIPT>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  
  <html:form action="otherTasks.do" method="post" onsubmit="fillzero(this.file_ref_number, 13); return validateEditTask(this);">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.tasks" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/other_tasks.htm#top');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <logic:present name="task" scope="request">
            <table class="form2" cellspacing="0" cellpadding="0">
              <html:hidden name="task" property="task_id" />
              <tr>
                <td>
                  <bean:message key="header.tsk_created_by" />
                  :
                </td>
                <td>
                  <bean:write name="task" property="assigningAgent.username" />
                </td>
                <td>
                  <bean:message key="header.tsk_station" />
                  :
                </td>
                <td>
                  <bean:write name="task" property="station.stationcode" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.assigned_to" />
                  :
                </td>
                <td colspan="3">
                 
                    <html:select name="taskForm" property="assigned_to_id" styleClass="dropdown">
                      <html:option value="">
                        <bean:message key="select.please_select" />
                      </html:option>
                      <html:options collection="stationAgents" property="agent_ID" labelProperty="username" />
                    </html:select>
                 
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.file" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="task" property="file_ref_number" size="20" maxlength="18" />
                </td>
                <td>
                  <bean:message key="header.tsk_report_type" />
                  :
                </td>
                <td>
                  <html:select name="task" property="file_type" styleClass="dropdown">
                    <html:option value="-1">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="typelist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.tsk_desc" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td colspan="3">
                  <html:text styleClass="textfield" name="task" property="description" size="50" maxlength="255" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.tsk_due_date" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td nowrap>
                  <html:text name="task" styleClass="textfield" property="dispduedate" size="11" maxlength="10" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.taskForm.dispduedate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td>
                  <bean:message key="header.tsk_due_time" />
                  (
                  <%= a.getTimeformat().getFormat() %>):
                </td>
                <td>
                  <html:text name="task" styleClass="textfield" property="dispduetime" size="6" maxlength="8" />
                </td>
              </tr>
              <tr>
                <td nowrap>
                  <bean:message key="header.tsk_reminder_date" />
                  (
                  <%= a.getDateformat().getFormat() %>):
                </td>
                <td nowrap>
                  <html:text name="task" styleClass="textfield" property="dispreminderdate" size="11" maxlength="10" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.taskForm.dispreminderdate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td>
                  <bean:message key="header.tsk_reminder_time" />
                  (
                  <%= a.getTimeformat().getFormat() %>):
                </td>
                <td>
                  <html:text name="task" styleClass="textfield" property="dispremindertime" size="6" maxlength="8" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.tsk_priority" />
                  :
                </td>
                <td>
                  <html:select name="task" property="priority.priority_ID" styleClass="dropdown">
                    <html:options collection="prioritylist" property="priority_ID" labelProperty="description" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="header.tsk_status" />
                  :
                </td>
                <td>
                  <html:select name="task" property="status.status_ID" styleClass="dropdown">
                    <html:options collection="task_status_list" property="status_ID" labelProperty="description" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.tsk_remarks" />
                  :
                </td>
                <td colspan="3">
                  <html:textarea name="task" styleClass="textarea" property="remarks" cols="60" rows="20" onkeydown="textCounter(this.form.remarks,this.form.remLen,1500);" onkeyup="textCounter(this.form.remarks,this.form.remLen,1500);" />
                  <input disabled="true" type="text" name=remLen size=4 maxlength=4 value="1500">
                </td>
              </tr>
              <tr>
                <td colspan="4" align="center">
                  <INPUT Id="button" type="button" value='<bean:message key="Back" />' onClick="history.back()">
                  &nbsp;
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <html:submit styleId="button" property="done">
                        <bean:message key="button.save" />
                      </html:submit>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <html:submit styleId="button" property="done">
                      <bean:message key="button.save" />
                    </html:submit>
                  </logic:notPresent>
                </td>
              </tr>
            </table>
          </logic:present>
        </html:form>
