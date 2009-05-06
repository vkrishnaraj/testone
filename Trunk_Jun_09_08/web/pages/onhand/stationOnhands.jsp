<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.OHDUtils" %>
<%
	Agent a = (Agent) session.getAttribute("user");
	org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources) request
			.getAttribute("org.apache.struts.action.MESSAGE");
	java.util.Locale myLocale = (java.util.Locale) session.getAttribute("org.apache.struts.action.LOCALE");
%>

<script language="javascript">

function goprev() {
  o = document.deliverForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.deliverForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.deliverForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

function submitForwardForm()
{
  var checked = 0;
  var ohd_id="";
  
  for (var j=0;j<document.deliverForm.length;j++) {
    
    currentElement = document.deliverForm.elements[j];
     if (currentElement.type=="checkbox")
     {
      if (currentElement.checked)
       {
        if (checked > 0) ohd_id += ",";
        checked +=1;
        ohd_id +=currentElement.value;
       }
     }
   }

   if (checked < 1)
   {
     alert("<%= (String)myMessages.getMessage(myLocale, "error.validation.missingForward") %>");
   }
   else
   {
    document.forwardOnHandForm.batch_id.value=ohd_id;
    document.forwardOnHandForm.submit();
  }
}
  </script>
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
  <html:form action="onhands.do" method="post">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.On-hand*Bags" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="/pages/includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <logic:present name="onhandlist" scope="request">
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <strong>
                    <bean:message key="colname.forwardOhd" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.on_hand_report_number" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.ld_report_num" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.incident_create_date" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.bag_tag_number" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="header.status" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.color" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.bagtype" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="header.companyCode" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.found_station" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.name" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="header.action" />
                  </strong>
                </td>
              </tr>
              <logic:iterate id="ohd" name="onhandlist" type="com.bagnet.nettracer.tracing.db.OHD">
                <tr>
                  <td>
                    <%
                        
                    	if (ohd != null && 
                          ohd.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_IN_TRANSIT &&
                          ohd.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT
                        ) {
                    %>
                      <input type="checkbox" name="ohd" value="<bean:write name="ohd" property="OHD_ID"/>">
                    <%
                    	} else {
                    %>
                      &nbsp;
                    <%
                    	}
                    %>
                  </td>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
                  </td>
                  <td>
                    <a href='searchIncident.do?incident=<%= OHDUtils.getMBRReportNum((OHD)ohd, "" + a.getStation().getStation_ID()) %>'><%=OHDUtils.getMBRReportNum(((OHD) ohd), "" + a.getStation().getStation_ID())%></a>
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="ohd" property="displaydate" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="claimnum" />
                    &nbsp;
                  </td>
                  <td>
                    <bean:write name="ohd" property="status.description" />
                  </td>
                  <td>
                    <logic:empty name="ohd" property="color">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="ohd" property="color" />
                  </td>
                  <td>
                    <logic:empty name="ohd" property="type">
                      &nbsp;
                    </logic:empty>
                    <bean:write name="ohd" property="type" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="foundAtStation.company.companyCode_ID" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="foundAtStation.stationcode" />
                  </td>
                  <td>
                    <logic:notEmpty name="ohd" property="passenger">
                      <logic:notEmpty name="ohd" property="passenger.lastname">
                        <bean:write name="ohd" property="passenger.lastname" />
                        ,
                        <bean:write name="ohd" property="passenger.firstname" />
                        &nbsp;
                        <bean:write name="ohd" property="passenger.middlename" />
                        &nbsp;
                      </logic:notEmpty>
                      <logic:empty name="ohd" property="passenger.lastname">
                        &nbsp;
                      </logic:empty>
                    </logic:notEmpty>
                    <logic:empty name="ohd" property="passenger">
                      &nbsp;
                    </logic:empty>
                  </td>
                  <td>
                    <%
                        
                      if (ohd != null && 
                          ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT ||
                          ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT
                        ) {
                    %>
                      <a href="onhands.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>&cancelFwd=1" ><bean:message key="link.cancelForward" /></a>
                    <% } else {%>
                      &nbsp;
                    <% } %> 
                  </td>
                </tr>
              </logic:iterate>
              <input type="hidden" name="search" value="1">
              <tr>
                <td colspan="12">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
               <tr>
                        <td colspan="12">
                          &nbsp;
                        </td>
                      </tr>
                      <tr>
                        <td colspan="12" align="center">
                          <logic:present name="cbroStationID" scope="session">
          <%
                            if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
          %>
                              <input type="button" name="batch" value="<bean:message key="button.batchForward" />" Id="button" onClick="submitForwardForm()">
          <%
                            }
          %>
                          </logic:present>
                          <logic:notPresent name="cbroStationID" scope="session">
                            <input type="button" name="batch" value="<bean:message key="button.batchForward" />" Id="button" onClick="submitForwardForm()">
                          </logic:notPresent>
                        </td>
                      </tr>
            </table>
          </logic:present>
        </html:form>
        <html:form action="forward_on_hand.do" method="post">
          <input type="hidden" name="batch" value="1">
          <input type="hidden" name="batch_id" value="">
        </html:form>