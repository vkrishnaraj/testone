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
  <html:form action="message.do" method="post">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.message" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/inbox.htm#open message');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <TR>
              <TD colspan="2">
                <bean:message key="colname.date" />
                :
                <bean:write name="composeForm" property="date" />
              </TD>
            </TR>
            <TR>
              <TD width="13%" class="testoGrande">
                <bean:message key="header.from" />
                :
              </TD>
              <TD width="87%">
                <bean:write name="composeForm" property="companyCode" />
                :
                <bean:write name="composeForm" property="stationCode" />
              </TD>
            </TR>
            <TR>
              <TD width="13%">
                <bean:message key="colname.agent" />
                :
              </TD>
              <TD width="87%">
                <bean:write name="composeForm" property="agentName" />
              </TD>
            </TR>
            <tr>
              <TD width="13%">
                <bean:message key="header.to" />
                :
              </TD>
              <TD width="87%">
                <logic:iterate id="recipient" name="composeForm" property="recp_list" type="com.bagnet.nettracer.tracing.db.Recipient">
                  <bean:write name="recipient" property="station.company.companyCode_ID" />
                  :
                  <bean:write name="recipient" property="station.stationcode" />
                  &nbsp;
                </logic:iterate>
              </td>
            </tr>
            <tr>
              <td width="13%">
                <bean:message key="header.file" />
                :
              </td>
              <td width="87%">
                <logic:notEqual name="composeForm" property="file_type" value="-1">
                  <logic:equal name="composeForm" property="file_type" value="0">
                    <a href="addOnHandBag.do?ohd_ID=<bean:write name="composeForm" property="file_ref_number"/>"><bean:write name="composeForm" property="file_ref_number" /></a>
                  </logic:equal>
                  <logic:equal name="composeForm" property="file_type" value="1">
                    <a href="searchIncident.do?incident=<bean:write name="composeForm" property="file_ref_number"/>"><bean:write name="composeForm" property="file_ref_number" /></a>
                  </logic:equal>
                </logic:notEqual>
                <logic:equal name="composeForm" property="file_type" value="-1">
                  &nbsp;
                </logic:equal>
              </td>
            </tr>
            <TR>
              <TD width="13%">
                <bean:message key="header.subject" />
                :
              </TD>
              <TD width="87%">
                <bean:write name="composeForm" property="subject" />
              </TD>
            </TR>
            <TR>
              <TD width="13%">
                <bean:message key="header.message" />
                :
              </TD>
              <TD width="87%">
                <html:textarea rows="20" cols="62" name="composeForm" property="body" readonly="true" />
              </TD>
            </TR>
            <tr>
              <td colspan="2" align="center">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="2" align="center">
                <INPUT type="button" value="Back" onClick="history.back()" Id="button">
                &nbsp;
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <html:submit styleId="button" property="reply">
                      <bean:message key="reply_message" />
                    </html:submit>
                    &nbsp;
                    <html:submit styleId="button" property="delete">
                      <bean:message key="delete_message" />
                    </html:submit>
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <html:submit styleId="button" property="reply">
                    <bean:message key="reply_message" />
                  </html:submit>
                  &nbsp;
                  <html:submit styleId="button" property="delete">
                    <bean:message key="delete_message" />
                  </html:submit>
                </logic:notPresent>
              </td>
            </tr>
          </TABLE>
        </html:form>
