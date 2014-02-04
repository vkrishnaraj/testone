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
  <script language="javascript">
    
function goprev() {
  o = document.taskForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.taskForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.taskForm;
  o.currpage.value = i;
  o.submit();

}
function updatePagination() {
    return true;
}
function sortAgents(sortOrder) {
	o = document.taskForm;
	o.sort.value = sortOrder;
	o.submit();
}


  </script>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  
  <html:form action="otherTasks.do" method="post" onsubmit="fillzero(this.file_ref_number, 13); return validateRest(this);">
    <logic:present name="file_type" scope="request">
      <html:hidden property="file_type" value="<%= (String)request.getAttribute("file_type") %>" />
    </logic:present>
    <input type=hidden name="task_ids" value="">
    <input type=hidden name="delete1" value="">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
<%
          String sort = (String)request.getAttribute("sort");

          if (sort != null && sort.length() > 0) {
%>
            <input type=hidden name=sort value='<%= sort %>'>
<%
          } else {
%>
            <input type=hidden name=sort value="">
<%
          }
%>
          <h1 class="green">
            <bean:message key="header.search_tasks" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/other_tasks.htm#search other tasks');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <bean:message key="header.tsk_status" />
                :
              </td>
              <td>
                <html:select property="task_status" styleClass="dropdown">
                  <html:option value="-2">
                    <bean:message key="select.all_active" />
                  </html:option>
                  <html:option value="-1">
                    <bean:message key="select.all" />
                  </html:option>
                  <html:options collection="task_status_list" property="status_ID" labelProperty="description" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.task_date_range" />
                (
                <%= a.getDateformat().getFormat() %>):
              </td>
              <td nowrap>
                <html:text styleClass="textfield" property="s_time" size="11" maxlength="10" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.taskForm.s_time,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                <html:text styleClass="textfield" property="e_time" size="11" maxlength="10" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.taskForm.e_time,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
            </tr>
            <tr>
              <td>
                <bean:message key="header.file" />
                :
              </td>
              <td>
                <html:text styleClass="textfield" property="file_ref_number" size="14" maxlength="13" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.assigned_to" />
                :
              </td>
              <td>
                <html:select name="taskForm" property="assigned_to_id" styleClass="dropdown">
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:options collection="stationAgents" property="agent_ID" labelProperty="username" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td colspan="3" align="center">
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
            <bean:message key="header.tasks" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/other_tasks.htm#top');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <logic:present name="cbroStationID" scope="session">
<%
                if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                  <td>
                    <b><bean:message key="colname.batch.delete" /></b>
                  </td>
<%
                }
%>
              </logic:present>
              <logic:notPresent name="cbroStationID" scope="session">
                <td>
                  <b><bean:message key="colname.batch.delete" /></b>
                </td>
              </logic:notPresent>
              <td>
                <b><bean:message key="header.tsk_desc" /></b>
              </td>
              <td>
                <b><a href="#" onclick="sortAgents('due_date');"><bean:message key="header.tsk_due_date" /></a></b>
              </td>
              <td>
                <b><a href="#" onclick="sortAgents('reminder_date');"><bean:message key="header.tsk_reminder_date" /></a></b>
              </td>
              <td>
                <b><a href="#" onclick="sortAgents('file_ref_number');"><bean:message key="header.file" /></a></b>
              </td>
              <td>
                <b><a href="#" onclick="sortAgents('priority');"><bean:message key="header.tsk_priority" /></a></b>
              </td>
              <td>
                <b><bean:message key="colname.assigned_to" /></b>
              </td>
              <td>
                <b><a href="#" onclick="sortAgents('status');"><bean:message key="header.tsk_status" /></a></b>
              </td>
              <td>
                <b><bean:message key="header.tsk_edit_view" /></b>
              </td>
            </tr>
            <logic:present name="taskList" scope="request">
              <logic:iterate id="taskDTO" name="taskList">
                <tr>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <input type="checkbox" name="task" value="<bean:write name="taskDTO" property="task_id"/>">
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <input type="checkbox" name="task" value="<bean:write name="taskDTO" property="task_id"/>">
                    </td>
                  </logic:notPresent>
                  <td>
                    <logic:empty name="taskDTO" property="description">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="taskDTO" property="description" />
                  </td>
                  <td>
                    <bean:write name="taskDTO" property="dispduedate" />
                    &nbsp;
                    <bean:write name="taskDTO" property="dispduetime" />
                  </td>
                  <td>
                    <bean:write name="taskDTO" property="dispreminderdate" />
                    &nbsp;
                    <bean:write name="taskDTO" property="dispremindertime" />
                  </td>
                  <logic:notEmpty name="taskDTO" property="file_ref_number">
                    <td>
                      <logic:equal name="taskDTO" property="file_type" value="0">
                        <a href="addOnHandBag.do?ohd_ID=<bean:write name="taskDTO" property="file_ref_number"/>"><bean:write name="taskDTO" property="file_ref_number" /></a>
                      </logic:equal>
                      <logic:equal name="taskDTO" property="file_type" value="1">
                        <a href="searchIncident.do?incident=<bean:write name="taskDTO" property="file_ref_number"/>"><bean:write name="taskDTO" property="file_ref_number" /></a>
                      </logic:equal>
                      <logic:equal name="taskDTO" property="file_type" value="-1">
                        <bean:write name="taskDTO" property="file_ref_number" />
                      </logic:equal>
                    </td>
                  </logic:notEmpty>
                  <logic:empty name="taskDTO" property="file_ref_number">
                    <td>
                      &nbsp;
                    </td>
                  </logic:empty>
                  <td>
                    <bean:write name="taskDTO" property="priority.description" />
                  </td>
                  <td>
                    <logic:notEmpty name="taskDTO" property="assignedTo">
                      <bean:write name="taskDTO" property="assignedTo.username" />
                    </logic:notEmpty>
                    <logic:empty name="taskDTO" property="assignedTo">
                      &nbsp;
                    </logic:empty>
                  </td>
                  <td>
                    <bean:message name="taskDTO" property="status.key" />
                  </td>
                  <td>
                    <a href="otherTasks.do?edit_task_id=<bean:write name="taskDTO" property="task_id"/>"><bean:message key="edit" /></a>
                  </td>
                </tr>
              </logic:iterate>
              
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  
                </td>
              </tr>
            </logic:present>
            <tr>
              <td colspan="8">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="9" align="center">
                <INPUT Id="button" type="button" value='<bean:message key="Back" />' onClick="history.back()">
                &nbsp;
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <INPUT type="button" value='<bean:message key="Batch.Delete" />' onClick="batchTaskDelete()" Id="button">
                    &nbsp;
                    <html:submit styleId="button" property="create">
                      <bean:message key="button.createTask" />
                    </html:submit>
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <INPUT type="button" value="Batch Delete" onClick="batchTaskDelete()" Id="button">
                  &nbsp;
                  <html:submit styleId="button" property="create">
                    <bean:message key="button.createTask" />
                  </html:submit>
                </logic:notPresent>
              </td>
            </tr>
          </table>
        </html:form>
