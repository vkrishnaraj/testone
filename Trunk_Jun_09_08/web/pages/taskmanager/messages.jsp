<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
request.getAttribute("org.apache.struts.action.MESSAGE");
java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
"org.apache.struts.action.LOCALE");
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
  o = document.composeForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.composeForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.composeForm;
  o.currpage.value = i;
  o.submit();
}
function updatePagination() {
    return true;
}
function sortAgents(sortOrder) {
	o = document.composeForm;
	o.sort.value = sortOrder;
	o.submit();
}

  function batchMessageDelete()
  {
      if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.message.delete") %>?"))
      {  
      var checked = 0;
        var received="";
      
        for (var j=0;j<document.composeForm.length;j++) 
        {
            currentElement = document.composeForm.elements[j];
            if (currentElement.type=="checkbox")
            {
              if (currentElement.checked)
              {
                if (checked > 0) 
                  received += ",";
                checked +=1;
                received +=currentElement.value;
              }
            }
        }
  
        if (checked < 1)
        {
          alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingMessages") %>");
        }
        else
        {
          document.composeForm.delete1.value="1";
        document.composeForm.message_ids.value=received;
            document.composeForm.submit();
        }
    }
  } 


  </script>
  
  <html:form action="message.do" method="post" onsubmit="fillzero(this.search_file_ref, 13); return true;">
    <input type=hidden name="message_ids" value="">
    <input type=hidden name="delete1" value="">
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
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_messages" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/inbox.htm#locate message');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
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
                <bean:message key="header.status" />
                :
              </td>
              <td>
                <html:select property="message_status" styleClass="dropdown" styleId="mess_stat">
                  <html:option value="-1">
                    All
                  </html:option>
                  <html:options collection="message_status_list" property="status_ID" labelProperty="description" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.date_range" />
                (
                <%= a.getDateformat().getFormat() %>):
              </td>
              <td nowrap>
                <html:text styleClass="textfield" property="s_time" size="11" maxlength="10" styleId="s_time" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.composeForm.s_time,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                <html:text styleClass="textfield" property="e_time" size="11" maxlength="10" styleId="e_time" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.composeForm.e_time,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;"></td>
            </tr>
            <tr>
              <td>
                <bean:message key="header.subject" />
                :
              </td>
              <td>
                <html:text styleClass="textfield" property="search_sub" size="20" maxlength="40" styleId="srch_subj" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="header.file" />
                :
              </td>
              <td>
                <html:text styleClass="textfield" property="search_file_ref" size="14" maxlength="13" onblur="fillzero(this,13);" styleId="srch_file" />
              </td>
            </tr>
            <tr>
              <td colspan="3" align="center">
                <br>
                <html:submit styleId="button" property="search">
                  <bean:message key="button.search" />
                </html:submit>
                &nbsp;
                <html:button styleId="button" property="" onclick="eraseForm(this.form);">
                  <bean:message key="button.reset" />
                </html:button>
              </td>
            </tr>
          </table>
          <SCRIPT TYPE="text/javascript">
				function eraseForm(searchForm) {
					searchForm.message_status.value = -1;
					searchForm.s_time.value = '';
					searchForm.e_time.value = '';
					searchForm.search_sub.value = '';
					searchForm.search_file_ref.value = '';
				}
		</SCRIPT>
          
          <br>
          <h1 class="green">
            <bean:message key="header.inbox" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/inbox.htm#top');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <TR>
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
              <TD>
                <b><a href="#" onclick="sortAgents('order_file_number');"><bean:message key="header.files" /></a></b>
              </td>
              <TD>
                <b><bean:message key="header.subject" /></b>
              </TD>
              <TD>
                <b><bean:message key="header.sender" /></b>
              </TD>
              <TD>
                <b><a href="#" onclick="sortAgents('order_date');"><bean:message key="header.date" /></a></b>
              </TD>
              <TD>
                <b><bean:message key="header.status" /></b>
              </TD>
            </TR>
            <logic:present name="messages" scope="request">
              <logic:iterate name="messages" id="message">
                <TR>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <input type="checkbox" name="mcopy" value="<bean:write name="message" property="message_copy_id"/>">
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <input type="checkbox" name="mcopy" value="<bean:write name="message" property="message_copy_id"/>">
                    </td>
                  </logic:notPresent>
                  <logic:notEmpty name="message" property="parent_message.file_ref_number">
                    <td width="12%">
                      <logic:equal name="message" property="parent_message.file_type" value="0">
                        <a href="addOnHandBag.do?ohd_ID=<bean:write name="message" property="parent_message.file_ref_number"/>"><bean:write name="message" property="parent_message.file_ref_number" /></a>
                      </logic:equal>
                      <logic:equal name="message" property="parent_message.file_type" value="1">
                        <a href="searchIncident.do?incident=<bean:write name="message" property="parent_message.file_ref_number"/>"><bean:write name="message" property="parent_message.file_ref_number" /></a>
                      </logic:equal>
                      <logic:equal name="message" property="parent_message.file_type" value="-1">
                        <bean:write name="message" property="parent_message.file_ref_number" />
                      </logic:equal>
                    </td>
                  </logic:notEmpty>
                  <logic:empty name="message" property="parent_message.file_ref_number">
                    <td>
                      &nbsp;
                    </td>
                  </logic:empty>
                  <TD class="testoNero">
                    <A HREF="message.do?message_copy_id=<bean:write name="message" property="message_copy_id"/>"><bean:write name="message" property="subject" /></A>
                  </TD>
                  <TD>
                    <bean:write name="message" property="parent_message.send_station.company.companyCode_ID" />
                    :
                    <SPAN class="bold"><bean:write name="message" property="parent_message.send_station.stationcode" /></SPAN>
                    &nbsp;
                  </TD>
                  <TD>
                    <bean:write name="message" property="disp_send_date" />
                  </TD>
                  <TD>
                    <bean:message name="message" property="status.key" />
                  </TD>
                </TR>
              </logic:iterate>
              <tr>
                <td colspan="11">
			<input name="checkAll" type="button" id="button" onClick="
			  var x = document.getElementsByName('mcopy');
				    for (i=0; i<x.length; i++){
				      x[i].checked = true;
				    }			  
                  " value="Select All" />
                  <input type="hidden" name="inbox" value="1">
                </td>
              </tr>
              
              
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                  
                </td>
              </tr>
              
            </logic:present>
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="11" align="center">
                <INPUT type="button" value="Back" onClick="history.back()" Id="button">
                &nbsp;
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <INPUT type="button" value="Batch Delete" onClick="batchMessageDelete()" Id="button">
                    &nbsp;
                    <html:submit styleId="button" property="new">
                      <bean:message key="button.compose" />
                    </html:submit>
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <INPUT type="button" value="Batch Delete" onClick="batchMessageDelete()" Id="button">
                  &nbsp;
                  <html:submit styleId="button" property="new">
                    <bean:message key="button.compose" />
                  </html:submit>
                </logic:notPresent>
              </td>
            </tr>
          </TABLE>
        </html:form>
