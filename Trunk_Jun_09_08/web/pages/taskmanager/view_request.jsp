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
  o = document.viewRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.viewRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewRequestForm;
  o.currpage.value = i;
  o.submit();

}
function updatePagination() {
    return true;
}
function sortIncomingRequests(sortOrder) {
	o = document.viewRequestForm;
	o.sort.value = sortOrder;
	o.submit();
}



  </script>
  <html:form action="viewROH.do" method="post" onsubmit="fillzero(this.ohd_num, 13); fillzero(this.incident_num, 13); return true;">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
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
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_incoming_requests" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_created_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="Required" />
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="colname.on_hand_report_file_number" />
                :
              </td>
              <td colspan="3">
                <html:text property="ohd_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.incident_file_num" />
                :
              </td>
              <td colspan="3">
                <html:text property="incident_num" size="14" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
              </td>
            </tr>
            <tr>
              <td colspan="4" align="center">
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
            <bean:message key="header.search_result" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_incoming_requests.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <b><a href="#" onclick="sortIncomingRequests('ohd_num');"><bean:message key="header.ohd" /></a></b>
              </td>
              <td>
                <b><a href="#" onclick="sortIncomingRequests('incident_num');"><bean:message key="header.file" /></a></b>
              </td>
              <td>
                <b><a href="#" onclick="sortIncomingRequests('bagtag');"><bean:message key="colname.bag_tag_number" /></a></b>
              </td>              
              <td>
                <b><bean:message key="header.match" /></b>
              </td>
              <td>
                <b><bean:message key="header.request" /></b>
              </td>
              <logic:present name="cbroStationID" scope="session">
<%
                if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                  <td>
                    <b><bean:message key="deny" /></b>
                  </td>
                  <td>
                    <b><bean:message key="forward" /></b>
                  </td>
<%
                }
%>
              </logic:present>
              <logic:notPresent name="cbroStationID" scope="session">
                <td>
                  <b><bean:message key="deny" /></b>
                </td>
                <td>
                  <b><bean:message key="forward" /></b>
                </td>
              </logic:notPresent>
            </tr>
            <logic:present name="requestList" scope="request">
              <logic:iterate id="req" name="requestList">
                <tr>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="req" property="ohd.OHD_ID"/>"><bean:write name="req" property="ohd.OHD_ID" /></a>
                  </td>
                  <td>
                    <logic:present name="req" property="incident_ID">
                      <A HREF="searchIncident.do?incident=<bean:write name="req" property="incident_ID"/>"><bean:write name="req" property="incident_ID" /></a>
                    </logic:present>
                    <logic:notPresent name="req" property="incident_ID">
                      &nbsp;
                    </logic:notPresent>
                  </td>
                  <td>
                    <logic:notEmpty name="req" property="ohd.claimnum">
                      <bean:write name="req" property="ohd.claimnum" />
                    </logic:notEmpty>
                    <logic:empty name="req" property="ohd.claimnum">
                      &nbsp;
                    </logic:empty>                  
					
                  </td>                  
                  <logic:notEqual name="req" property="match_id" value="0">
                    <td>
                      <A HREF="viewMatches.do?showMatch=1&match_ID=<bean:write name="req" property="match_id"/>"><bean:message key="details" /></a>
                    </td>
                  </logic:notEqual>
                  <logic:equal name="req" property="match_id" value="0">
                    <td>
                      &nbsp;
                    </td>
                  </logic:equal>
                  <td>
                    <A HREF="request_on_hand.do?showRequest=1&request_ID=<bean:write name="req" property="ohd_request_id"/>"><bean:message key="details" /></a>
                  </td>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <A HREF="request_on_hand.do?deny=1&request_ID=<bean:write name="req" property="ohd_request_id"/>"><bean:message key="deny" /></a>
                      </td>
                      <td>
                        <A HREF="forward_on_hand.do?ohd_ID=<bean:write name="req" property="ohd.OHD_ID"/>&request_ID=<bean:write name="req" property="ohd_request_id"/>"><bean:message key="forward" /></a>
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <A HREF="request_on_hand.do?deny=1&request_ID=<bean:write name="req" property="ohd_request_id"/>"><bean:message key="deny" /></a>
                    </td>
                    <td>
                      <A HREF="forward_on_hand.do?ohd_ID=<bean:write name="req" property="ohd.OHD_ID"/>&request_ID=<bean:write name="req" property="ohd_request_id"/>"><bean:message key="forward" /></a>
                    </td>
                  </logic:notPresent>
                </tr>
              </logic:iterate>
            </logic:present>
            
            <tr>
              <td colspan="7">
                <jsp:include page="/pages/includes/pagination_incl.jsp" />
                
              </td>
            </tr>
            
            <tr>
              <td colspan="7" align="center">
                <INPUT type="button" value="Back" onClick="history.back()" Id="button">
                &nbsp;
              </td>
            </tr>
          </table>
        </html:form>
