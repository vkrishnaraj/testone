<%@ page language="java" %>
<%@ taglib prefix="nt" uri="http://nettracerTags"%> 
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>

<%
Agent a = (Agent)session.getAttribute("user");
boolean hasPermission = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_MANAGE_TASKS, a);
boolean isCss = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_MANAGE_CSS_DAILY_CALLS, a);
if (hasPermission) {
%>
<c:if test="${!empty sessionTaskContainer and !empty nt:instanceOf(sessionTaskContainer,'com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask')}">

<%
	  
      

  
  String cssFormClass;
 
  int report_type = 0;
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
      cssFormClass = "form2_pil";
    }
  }
%>

<SCRIPT LANGUAGE="JavaScript">

  function textCounterTask(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } else {
      countfield.value = maxlimit - field.value.length;
    }
  }
  
	function insertNewLineTask(elementId) {
		if (window.event && window.event.keyCode == 13) {
			insertAtCursorTask(document.getElementById(elementId), '\n');
			window.event.keyCode = 505;
		}
  	}
	
	function insertAtCursorTask(myField, myValue) {
		if (document.selection) {
			myField.focus();
			sel = document.selection.createRange();
			sel.text = myValue;
			sel.collapse(false);
			sel.select();
		} else {
			myField.value += myValue;
		}
	}
</SCRIPT>


  <h1 class="green">
    <bean:message key="header.nettracer.currentTask" />
  </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" />
      <div id="netTracerGeneralTask">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
				<tr><td width ="20%"><bean:message key="generaltask.tasktype" />:</td><td><strong id="taskType"><bean:message name="sessionTaskContainer" property="label"/></strong></td></tr>
				<tr><td width ="20%"><bean:message key="colname.agentassigned_nobr" />:</td><td><bean:write name="sessionTaskContainer" property="assigned_agent.username"/></td></tr>
		<% if (isCss) { %>
		        <SCRIPT LANGUAGE="JavaScript">

		        var todayDate = new Date();
		        var dateString = formatDate(todayDate,"<%=a.getDateformat().getFormat() %>");

				function closeTask(type) {
					var remarkElement=document.getElementById("taskRemark");
					var taskRemark = remarkElement.value.replace(/^\s+|\s+$/g, '');
					if (taskRemark.length > 0) {

						remarkElement.value="";
						jQueryform = jQuery("#dirtyCheck-form");
					    var formData = jQueryform.serialize();
					    var startData = jQueryform.data('ays-form');
					    var checkSave=true;
					    if (startData!=undefined && formData != startData) {
					    	checkSave=confirm("You have made changes to the Incident. Do you want to continue?");
					    }
					    remarkElement.value=taskRemark;
						if (checkSave) {
							if ((type == 'abort' || type == 'complete')) {
								clearBeforeUnload();
								document.location.href="css_calls.do?" + type + "=1&taskRemark=" + escape(taskRemark);
							} else {
								return loadTaskDeferModal();
							}	
						}
					} else {
						alert("Call Remarks is required.");
					}
					return false;
				}

				function deferTask() {
					var startDate = document.getElementById("taskNewStartDate").value.replace(/^\s+|\s+$/g, '');
					var expireDate = document.getElementById("taskExpireDate").value.replace(/^\s+|\s+$/g, '');
					var startTime = document.getElementById("taskNewStart").value.replace(/^\s+|\s+$/g, '');
					var expireTime = document.getElementById("taskExpire").value.replace(/^\s+|\s+$/g, '');
					var taskRemark = document.getElementById("taskRemark").value.replace(/^\s+|\s+$/g, '');
					if (startDate.length == 0) {
						alert("New Start Date is required.")
						return false;
					}
					if (startTime.length == 0) {
						alert("New Start Time (HH:mm) is required.")
						return false;
					}
					if (!startTime.match(/^\d\d:\d\d$/g)) {
						alert("New Start Time (HH:mm) is improperly formatted. Please input time in 24 hour format with a ':' separating hours and minutes. Ex: 21:35");
						return false;
					}
					if (expireTime.length > 0 && !expireTime.match(/^\d\d:\d\d$/g)) {
						alert("Expire Time (HH:mm) is improperly formatted. Please input time in 24 hour format with a ':' separating hours and minutes. Ex: 21:35");
						return false;
					}
					var testSTime = startTime.replace(/:/g, '');
					if (formatDate(new Date(),"<%= a.getDateformat().getFormat() %>") == startDate && formatDate(new Date(),"HHmm") > testSTime) {
						alert("New Start Time (HH:mm) must be a time in the future");
						return false;
					}
					if (expireDate.length > 0 && expireTime.length > 0) {
						var testETime = expireTime.replace(/:/g, '');
						var sDate = Date.parse(startDate);
						var eDate = Date.parse(expireDate);
						if (eDate < sDate) {
							alert("Expire Date cannot be before New Start Date.");
							return false;
						} else if (eDate == sDate && testETime <= testSTime) {
							alert("Expire Time (HH:mm) cannot be before New Start Time (HH:mm).");
							return false;
						}
					}
					clearBeforeUnload();
					document.location.href="css_calls.do?defer=1&taskStart=" + escape(startTime) + "&taskExpire=" + escape(expireTime) +
											"&taskStartDate=" + escape(startDate) + "&taskExpireDate=" + escape(expireDate) +
											"&taskRemark=" + escape(taskRemark);
					return false;
					
				}

				function loadTaskDeferModal() {
					var theHtml = '' +
					'<form><div>' +
					'	<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" width="100%" >' +
					'		<tr>' +
					'   		<td width="50%"><strong><bean:message key="generaltask.new.start" /></strong><br/>' +
					'			<input type="hidden" id="taskNewStartDate" value="' + dateString + '" />' +
					'			<input type="text" id="taskNewStart" class="textfield" value="" maxlength="5" /></td>' +
					'			<td width="50%"><strong><bean:message key="generaltask.expire" /></strong><br/>' +
					'			<input type="hidden" id="taskExpireDate" value="' + dateString + '" />' +
					'			<input type="text" id="taskExpire" class="textfield" value="" maxlength="5" /></td>' +
					'		</tr>' +
					'	</table><br /><br />' +
					'	<div style="width:100%;" align="center">' +
					'		<input type="button" value="<bean:message key="button.task.defer" />" onclick="return deferTask();" name="deferTaskPopup" id="button">' +
					'	</div>' +
					'	<br /><br />' +
					'</div></form>';
					jQuery("#dialog").dialog({bgiframe : true, autoOpen: false, modal: true, draggable: false, resizable: false, 
									width: 400, height: 160, title: 'Defer Task', close: function(ev,ui){ jQuery('#dialog-inner-content').empty();} });
					jQuery('#dialog-inner-content').html(theHtml);
					jQuery("#dialog").dialog("open");
					jQuery("#dialog").dialog("option", "title","Defer Task");
					
					var currentElement = document.getElementById("taskNewStartDate");
					currentElement.focus();
					
					return false;
				}

		        </SCRIPT>
                <bean:define id="mdt" name="sessionTaskContainer" type="com.bagnet.nettracer.tracing.db.taskmanager.MorningDutiesTask"/>
				<tr><td width ="20%"><bean:message key="generaltask.time.to.call" />:</td><td><%=TracingConstants.getDisplayDate(mdt.getOpened_timestamp(), a) %></td></tr>
				<tr><td width ="20%"><bean:message key="generaltask.remark" />:</td><td>
                  <textarea name="taskRemark" id="taskRemark" cols="80" rows="5" onkeydown="textCounterTask(document.getElementById('taskRemark'), document.getElementById('taskRemark2'),1000);insertNewLineTask('taskRemark');" 
                  	onkeyup="textCounterTask(document.getElementById('taskRemark'), document.getElementById('taskRemark2'),1000);"></textarea>
                  <input name="taskRemark2" id="taskRemark2" type="text" value="1000" size="4" maxlength="4" disabled="true" />
                </td></tr>
				<tr><td colspan="2">
          			<span style="float:left">
          				<input type="button" value="<bean:message key="button.task.complete" />" onclick="return closeTask('complete');" name="completeTask" id="button">
          			</span>
          			<span style="float:right">
          	    		<input type="button" value="<bean:message key="button.task.defer" />" onclick="return closeTask('defer')" name="deferTask" id="button">&nbsp;&nbsp;&nbsp;
          				<input type="button" value="<bean:message key="button.task.abort" />" onclick="return closeTask('abort');" name="abortTask" id="button">
          			</span>
		  		</td></tr>
		<% } else { %>
				<tr><td width ="20%"><bean:message key="generaltask.timestarted" />:</td><td><bean:write name="sessionTaskContainer" property="opened_timestamp"/>&nbsp;&nbsp;<input type="button" value="<bean:message key="button.task.pause"/>" onclick='document.location.href="GeneralTask.do?pause=1";return true;' id="button"></td></tr>
				<tr><td colspan="2">
          <span style="float:left">
          	<input type="button" value="<bean:message key="button.task.complete" />" onclick='if(confirm("Have you saved all of your changes?")){clearBeforeUnload();document.location.href="GeneralTask.do?complete=1";}' id="button">
          </span>
          <span style="float:right">
          	<select id="task.defer.time" name="defer_time" class="dropdown">
          		<option value="0" selected="selected"><bean:message key="select.please_select" /></option>
          		<option value="30"><bean:message key="generaltask.defer.30min" /></option>
          		<option value="120"><bean:message key="generaltask.defer.2hr" /></option>
          		<option value="240"><bean:message key="generaltask.defer.4hr" /></option>
          	</select>
          	<input type="button" value="<bean:message key="button.task.defer" />" onclick='var time=document.forms[0].defer_time.value;clearBeforeUnload();document.location.href="GeneralTask.do?defer=1&defer_time="+time;' id="button">&nbsp;&nbsp;
          	<input type="button" value="<bean:message key="button.task.abort" />" onclick='clearBeforeUnload();document.location.href="GeneralTask.do?abort=1";return true;' id="button">
          </span>
		  </td>
          </tr>
        <% } %>
        </table>
        </div>

      <br>
      <br>

</c:if>
<% } %>
