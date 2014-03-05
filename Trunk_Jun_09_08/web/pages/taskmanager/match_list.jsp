<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    
function goprev() {
	o = document.matchform;
	o.prevpage.value = "1";
	o.submit();
}

function gonext() {
	o = document.matchform;
	o.nextpage.value="1";
	o.submit();
}

function gopage(i) {
	o = document.matchform;
	o.currpage.value = i;
	o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  <form name="matchform" action="viewMatches.do" method="post" onsubmit="fillzero(this.incident_ID, 13); fillzero(this.ohd_ID, 13); return true;">

    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#trace filter fields');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td nowrap>
                <bean:message key="colname.incident_file_num" />
                :
                <br>
                <input type="text" name="incident_ID" size="20" maxlength="13" class="textfield" value="<bean:write name="mbr" scope="session"/>" onblur="fillzero(this,13);" />
              </td>
              <td>
                <bean:message key="colname.on_hand_report_file_number" />
                :
                <br>
                <input type="text" name="ohd_ID" size="20" maxlength="13" class="textfield" value="<bean:write name="ohd" scope="session"/>" onblur="fillzero(this,13);" />
              </td>
              <td>
                <b><bean:message key="header.match_status" />
                :</b>
                <br>
                <input type="checkbox" name="status_ID" value="15"
                <bean:write name="status1" scope="session" />
                >
                <bean:message key="match.status.open" />
                &nbsp;&nbsp;&nbsp;
                <input type="checkbox" name="status_ID" value="22"
                <bean:write name="status2" scope="session" />
                >
                <bean:message key="match.status.matched" />
                &nbsp;&nbsp;&nbsp;
                <input type="checkbox" name="status_ID" value="21"
                <bean:write name="status3" scope="session" />
                >
                <bean:message key="match.status.rejected" />
                &nbsp;&nbsp;&nbsp;
                <input type="checkbox" name="status_ID" value="23"
                <bean:write name="status4" scope="session" />
                >
                <bean:message key="match.status.closed" />
              </td>
            </tr>
            <tr>
              <td colspan="3" align="center" valign="top">
                <input type="submit" name="refilter" value="Filter" id="button">
              </td>
            </tr>
          </table>
          <h1 class="green">
            <bean:message key="header.matches" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#sort trace results');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <b><bean:message key="header.select" /></b>
              </td>
              <td>
                <b><a href="viewMatches.do?sort=mbr"><bean:message key="header.matchlist.file" /></a></b>
              </td>
              <td>
                <b><a href="viewMatches.do?sort=ohd"><bean:message key="header.matchlist.ohd" /></a></b>
              </td>
              <td>
                <b><a href="viewMatches.do?sort="><bean:message key="header.match_percent" /></a></b>
              </td>
              <td>
                <b><a href="viewMatches.do?sort=date"><bean:message key="header.match_date" /></b>
              </td>
              <td>
                <b><bean:message key="header.match_details" /></b>
              </td>
              <td>
                <b><bean:message key="header.match_status" /></b>
              </td>
              <logic:present name="cbroStationID" scope="session">
<%
                if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                  <td>
                    <b><bean:message key="header.action" /></b>
                  </td>
<%
                }
%>
              </logic:present>
              <logic:notPresent name="cbroStationID" scope="session">
                <td>
                  <b><bean:message key="header.action" /></b>
                </td>
              </logic:notPresent>
            </tr>
            <logic:present name="matchList" scope="request">
              <logic:iterate id="matches" name="matchList" scope="request" type="com.bagnet.nettracer.tracing.db.Match">
                <tr>
                  <td>
                    <logic:notEqual name="matches" property="status.status_ID" value="22">
                      <input type="checkbox" name="match_ID" value='<bean:write name="matches" property="match_id"/>'>
                    </logic:notEqual>
                  </td>
                  <td>
                    <A HREF='searchIncident.do?incident=<bean:write name="matches" property="mbr.incident_ID"/>'><bean:write name="matches" property="mbr.incident_ID" /></a>
                  </td>
                  <td>
                    <A HREF='addOnHandBag.do?ohd_ID=<bean:write name="matches" property="ohd.OHD_ID"/>'><bean:write name="matches" property="ohd.OHD_ID" /></a>
                  </td>
                  <td>
                    <bean:write name="matches" property="disppercent" />
                  </td>
                  <td nowrap>
                    <bean:write name="matches" property="dispdate" />
                  </td>
                  <td>
                    <a href='viewMatches.do?showMatch=1&match_ID=<bean:write name="matches" property="match_id"/>'><bean:message key="colname.match_details" /></a>
                  </td>
                  <td>
                    <logic:equal name="matches" property="status.status_ID" value="15">
                      <bean:message key="match.status.open" />
                    </logic:equal>
                    <logic:equal name="matches" property="status.status_ID" value="21">
                      <bean:message key="match.status.rejected" />
                    </logic:equal>
                    <logic:equal name="matches" property="status.status_ID" value="22">
                      <bean:message key="match.status.matched" />
                    </logic:equal>
                    <logic:equal name="matches" property="status.status_ID" value="23">
                      <bean:message key="match.status.closed" />
                    </logic:equal>
                  </td>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td nowrap>
<%
                        if (a.getStation().getStation_ID() == matches.getOhd().getHoldingStation().getStation_ID()) {
%>
                          <a href='viewMatches.do?showMatch=1&match_ID=<bean:write name="matches" property="match_id"/>'><bean:message key="colname.forwardOhd_nobr" /></A>
                          
<%
                        } else {
%>
                          <a href='viewMatches.do?showMatch=1&match_ID=<bean:write name="matches" property="match_id"/>'><bean:message key="colname.init_roh" /></A>
<%
                        }
%>
                        <logic:notEqual name="matches" property="status.status_ID" value="<%= "" + TracingConstants.MATCH_STATUS_MATCHED %>">
                          |
                        </logic:notEqual>
                        <logic:equal name="matches" property="status.status_ID" value="<%= "" + TracingConstants.MATCH_STATUS_REJECTED %>">
                          <A HREF='viewMatches.do?match_ID=<bean:write name="matches" property="match_id"/>&unreject=1'><bean:message key="button.unreject" /></a>
                        </logic:equal>
                        <logic:equal name="matches" property="status.status_ID" value="<%= "" + TracingConstants.MATCH_STATUS_OPEN %>">
                          <A HREF='viewMatches.do?match_ID=<bean:write name="matches" property="match_id"/>&reject=1'><bean:message key="button.reject" /></a>
                        </logic:equal>
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td nowrap>
<%
                      if (a.getStation().getStation_ID() == matches.getOhd().getHoldingStation().getStation_ID()) {
%>
                        <a href='viewMatches.do?showMatch=1&match_ID=<bean:write name="matches" property="match_id"/>'><bean:message key="colname.forwardOhd_nobr" /></A>
                        
<%
                      } else {
%>
                        <a href='viewMatches.do?showMatch=1&match_ID=<bean:write name="matches" property="match_id"/>'><bean:message key="colname.init_roh" /></A>
<%
                      }
%>
                      <logic:notEqual name="matches" property="status.status_ID" value="<%= "" + TracingConstants.MATCH_STATUS_MATCHED %>">
                        |
                      </logic:notEqual>
                      <logic:equal name="matches" property="status.status_ID" value="<%= "" + TracingConstants.MATCH_STATUS_REJECTED %>">
                        <A HREF='viewMatches.do?match_ID=<bean:write name="matches" property="match_id"/>&unreject=1'><bean:message key="button.unreject" /></a>
                      </logic:equal>
                      <logic:equal name="matches" property="status.status_ID" value="<%= "" + TracingConstants.MATCH_STATUS_OPEN %>">
                        <A HREF='viewMatches.do?match_ID=<bean:write name="matches" property="match_id"/>&reject=1'><bean:message key="button.reject" /></a>
                      </logic:equal>
                    </td>
                  </logic:notPresent>
                </tr>
              </logic:iterate>
              <tr>
                <td colspan="8">
                  
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
              <tr>
                <td colspan="8">
                  <INPUT type="button" value="Back" onClick="history.back()" Id="button">
                  &nbsp;
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <input type="submit" name="multiunreject" value="Un-reject" id="button">
                      &nbsp;
                      <input type="submit" name="multireject" value="<bean:message key="button.reject" />" Id="button">
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <input type="submit" name="multiunreject" value="Un-reject" id="button">
                    &nbsp;
                    <input type="submit" name="multireject" value="Reject" Id="button">
                  </logic:notPresent>
                </td>
              </tr>
            </logic:present>
          </table>
        </form>
