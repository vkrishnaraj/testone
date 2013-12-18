<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
	request.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
	"org.apache.struts.action.LOCALE");
  if (null == request.getAttribute("outputtype")) {
	  request.setAttribute("outputtype", "0");
  }

  boolean canTeletype = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_TELETYPE_PRINT, a);
%>
  
  <%@page import="com.bagnet.nettracer.tracing.bmo.RequestOhdBMO"%>
<script language="javascript">
    
function goprev() {
  o = document.viewIncomingRequestForm;
  o.prevpage.value = "1";
  o.submit();
}

function gonext() {
  o = document.viewIncomingRequestForm;
  o.nextpage.value="1";
  o.submit();
}

function gopage(i) {
  o = document.viewIncomingRequestForm;
  o.currpage.value = i;
  o.submit();
}
function updatePagination() {
    return true;
}
function sortIncomingBags(sortOrder) {
	o = document.viewIncomingRequestForm;
	o.sort.value = sortOrder;
	o.submit();
}


  function batchReceive()
  {
    if (confirm("<%= (String)myMessages.getMessage(myLocale, "check.receive") %>?"))
    {  
      var checked = 0;
      var received="";
    
      for (var j=0;j<document.viewIncomingRequestForm.length;j++) 
      {
        currentElement = document.viewIncomingRequestForm.elements[j];
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
        alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingBags") %>");
      }
      else
      {
        document.viewIncomingRequestForm.close1.value="1";
        document.viewIncomingRequestForm.ohd_ID.value=received;
        document.viewIncomingRequestForm.submit();
      }
    }
  } 


  </script>
  <html:form action="incomingBags.do" method="post" onsubmit="fillzero(this.ohd_num, 13); return true;">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  <html:hidden property="reportnum" value="30" />
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
    <input type=hidden name=close1 value="">
    <input type=hidden name=ohd_ID value="">
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_incoming_bags" />
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
                <bean:message key="colname.bag_tag_number" />
                :
              </td>
              <td>
                <html:text property="bag_tag" size="14" maxlength="12" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="header.expedite" />
                :
              </td>
              <td>
                <html:text property="expedite" size="12" maxlength="12" styleClass="textfield" />
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
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/incoming_bags.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <!-- removed prior to testing -->
          <!--
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          -->
          <!-- removed prior to testing -->
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="11" width="100%" align="right">
                <strong>
                  <bean:message key="colname.report_output_type" />
                </strong>
              </td>
            </tr> 
            <logic:notPresent name="noInboundExpediteBags" scope="request">
            <tr>
              <td colspan="11" align="right">
                <% if (!TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %>
                  <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("0")) { %> checked <% } %> value="0">
                  <bean:message key="radio.pdf" />
                <% } %>
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("1") || TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) { %> checked <% } %> value="1">
                <bean:message key="radio.html" />
                <% 
				if (canTeletype) {
				%>
                <input type="radio" name="outputtype" <% if (request.getAttribute("outputtype").equals("5")) { %> checked <% } %> value="5">
                <bean:message key="radio.teletype" />
                <html:text property="teletypeAddress" size="10" maxlength="13" styleClass="textfield" />
				<% 
				}
				%>                
                &nbsp;    
                <html:submit styleId="button" property="teletype">
                  <bean:message key="button.createreport" />
                </html:submit>
              </td>
            </tr>  
            </logic:notPresent>     
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>                  
            <tr>
              <logic:present name="cbroStationID" scope="session">
<%
                if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                  <td>
                    <b><bean:message key="header.batch_receive" /></b>
                  </td>
<%
                }
%>
              </logic:present>
              <logic:notPresent name="cbroStationID" scope="session">
                <td>
                  <b><bean:message key="header.batch_receive" /></b>
                </td>
              </logic:notPresent>
              <td>
                <b><a href="#" onclick="sortIncomingBags('ohd');"><bean:message key="header.ohd" /></a></b>
              </td>
              <td>
                <b><bean:message key="colname.ld_report_num" /></b>
              </td>
              <td>
                <b><a href="#" onclick="sortIncomingBags('expedite');"><bean:message key="header.expedite" /></a></b>
              </td>
              <td>
                <b><a href="#" onclick="sortIncomingBags('bagtag');"><bean:message key="colname.bag_tag_number" /></a></b>
              </td>
             <td>
                <b><bean:message key="colname.airline" /></b>
              </td>
              <td>
                <b><bean:message key="colname.flight.num" /></b>
              </td> 
              <td>
                <b><bean:message key="colname.forwarddate" /></b>
              </td>                            
              <td>
                <b><bean:message key="header.forward_details" /></b>
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
            <logic:present name="requestList" scope="request">
              <logic:iterate id="forwardLog" name="requestList" type="com.bagnet.nettracer.tracing.db.OHD_Log">
                <tr>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <input type="checkbox" name="code" value="<bean:write name="forwardLog" property="ohd.OHD_ID"/>">
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <input type="checkbox" name="code" value="<bean:write name="forwardLog" property="ohd.OHD_ID"/>">
                    </td>
                  </logic:notPresent>
                  <td>
                    <a href="addOnHandBag.do?ohd_ID=<bean:write name="forwardLog" property="ohd.OHD_ID"/>"><bean:write name="forwardLog" property="ohd.OHD_ID" /></a>
                  </td>
                  <td>
                    <%
                    String incidentId = RequestOhdBMO.getOhdRequestIncidentId(forwardLog.ohd_request_id);
                    if (incidentId == null && forwardLog.getOhd().getMatched_incident() != null && forwardLog.getOhd().getMatched_incident().trim().length() > 0) {
                      incidentId = forwardLog.getOhd().getMatched_incident();
                    }
                    
                    if (incidentId != null) {
                    %>
                      <a href="searchIncident.do?incident=<%=incidentId %>"><%=incidentId %></a>
                    <%
                    }
                    %>&nbsp;
                  </td>
                  <td>
                    <logic:empty name="forwardLog" property="expeditenum">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="forwardLog" property="expeditenum" />
                  </td>
                  <td>
                    <logic:empty name="forwardLog" property="ohd.claimnum">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="forwardLog" property="ohd.claimnum" />
                  </td>
                  <td>
                    <logic:empty name="forwardLog" property="dispDestinationAirline">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="forwardLog" property="dispDestinationAirline" />
                  </td>
                  <td>
                    <logic:empty name="forwardLog" property="dispDestinationFlightnum">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="forwardLog" property="dispDestinationFlightnum" />
                  </td> 
                  <td>
                    <logic:empty name="forwardLog" property="dispForwardTime">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="forwardLog" property="dispForwardTime" />
                  </td>                                       
                  <td>
                    <A HREF="forward_on_hand.do?showForward=1&forward_id=<bean:write name="forwardLog" property="OHDLog_ID"/>"><bean:message key="details" /></a>
                  </td>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <a href="incomingBags.do?close=1&ohd_ID=<bean:write name="forwardLog" property="ohd.OHD_ID"/>"><bean:message key="received" /></a>
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <a href="incomingBags.do?close=1&ohd_ID=<bean:write name="forwardLog" property="ohd.OHD_ID"/>"><bean:message key="received" /></a>
                    </td>
                  </logic:notPresent>
                </tr>
              </logic:iterate>
              
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
                    <INPUT type="button" value="Batch Receive" onClick="batchReceive()" Id="button">
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <INPUT type="button" value="Batch Receive" onClick="batchReceive()" Id="button">
                </logic:notPresent>
              </td>
            </tr>
          <!-- removed prior to testing -->
          <!--
            <tr>
              <td colspan="11" align="center">
                &nbsp;
              </td>
            </tr>
          -->
          <!-- removed prior to testing -->
          </table>
          
          <logic:present name="reportfile" scope="request">
              <script language="javascript">
				openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);
              </script>
          </logic:present>
        </html:form>
