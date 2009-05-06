<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<SCRIPT LANGUAGE="JavaScript">
  function textCounter(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } else {
      countfield.value = maxlimit - field.value.length;
    }
  }

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

</script>
<html:form action="message.do" method="post" onsubmit="fillzero(this.file_ref_number, 13); return true;">
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.compose" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/inbox.htm#send new message');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <span class="reqfield">*</span>
        <bean:message key="Required" />
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <br>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <TD width="13%">
              <bean:message key="colname.date" />
              :
            </TD>
            <TD width="87%">
              <bean:write name="composeForm" property="date" />
            </TD>
          </TR>
          <TR>
            <TD width="13%">
              <bean:message key="colname.agent" />
              :
            </TD>
            <TD width="47%">
              <bean:write name="composeForm" property="agentName" />
            </TD>
          </TR>
          <tr>
            <td width="13%">
              <bean:message key="header.file" />
              :
            </td>
            <td width="87%">
              <html:text styleClass="textfield" name="composeForm" property="file_ref_number" size="20" maxlength="18"  onblur="fillzero(this,13);"/>
              &nbsp;&nbsp;&nbsp;
              <bean:message key="header.tsk_report_type" />
              :
              &nbsp;
              <html:select name="composeForm" property="file_type" styleClass="dropdown">
                <html:option value="-1">
                  <bean:message key="select.none" />
                </html:option>
                <html:options collection="typelist" property="value" labelProperty="label" />
              </html:select>
            </td>
          </tr>
          <TR>
            <TD width="13%">
              <bean:message key="header.to" />
              <font color=red>
                *
              </font>
              :
            </TD>
            <TD width="87%">
              <logic:iterate id="recp_list" indexId="k" name="composeForm" property="recp_list" type="com.bagnet.nettracer.tracing.db.Recipient">
                <logic:notEqual name="recp_list" property="station_id" value="0">
                  <html:text name="recp_list" property="company_code" size="3" maxlength="3" styleClass="textfield" indexed="true" onblur="submit()" />
                  <a href="#" onclick="openWindow('pages/popups/airlines.jsp?key=recp_list[<%= k %>].company_code&submitform=1','airlines',500,600);return false;"><img src="deployment/main/images/nettracer/airline_codes.gif" border=0></a>
                  &nbsp;
                  <html:select name="recp_list" styleClass="dropdown" property="station_id" indexed="true">
<%
                    if (recp_list.getStation_id() == -1) {
%>
                      <option value="-1" selected>
                      <bean:message key="select.all" />
                      </option>
<%
                    } else {
%>
                      <option value="-1" selected>
                      <bean:message key="select.all" />
                      </option>
<%
                    }
%>
                    <logic:iterate id="station" name="recp_list" property="stationList" type="com.bagnet.nettracer.tracing.db.Station">
                      <option value="<bean:write name="station" property="station_ID"/>" <% if (recp_list.getStation_id() == station.getStation_ID()) { %> selected <% } %>>
                      <bean:write name="station" property="stationcode" />
                      </option>
                    </logic:iterate>
                  </html:select>
                </logic:notEqual>
                <logic:equal name="recp_list" property="station_id" value="0">
                  <html:text name="recp_list" property="company_code" size="3" maxlength="3" styleClass="textfield" indexed="true" onblur="submit()" />
                  <a href="#" onclick="openWindow('pages/popups/airlines.jsp?key=recp_list[<%= k %>].company_code&submitform=1','airlines',500,600);return false;"><img src="deployment/main/images/nettracer/airline_codes.gif" border=0></a>
                </logic:equal>
                |&nbsp;
              </logic:iterate>
              <html:submit styleId="button" property="add">
                <bean:message key="add" />
              </html:submit>
            </TD>
          </TR>
          <TR>
            <TD width="13%">
              <bean:message key="header.subject" />
              <font color=red>
                *
              </font>
              :
            </TD>
            <TD width="87%">
              <html:text name="composeForm" styleClass="textfield" property="subject" size="80" />
            </TD>
          </TR>
          <TR>
            <TD width="13%">
              <bean:message key="header.message" />
              <font color=red>
                *
              </font>
              :
            </TD>
            <TD width="87%">
              <html:textarea rows="20" cols="62" name="composeForm" property="body" onkeydown="textCounter(this.form.body,this.form.remLen,1500);" onkeyup="textCounter(this.form.body,this.form.remLen,1500);" />
              <input disabled="true" type="text" name=remLen size=4 maxlength=4 value="1500">
            </TD>
          </TR>
          <tr>
            <td>
              &nbsp;
            </td>
            <td>
              &nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2" align="center">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit styleId="button" property="send">
                <bean:message key="send_message" />
              </html:submit>
            </td>
          </tr>
        </TABLE>
      </html:form>
