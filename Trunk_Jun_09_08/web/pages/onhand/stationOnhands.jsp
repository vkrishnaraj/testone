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
  Agent a = (Agent)session.getAttribute("user");
%>
  <script language="javascript">
    <!--
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
// -->
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
    <!-- END PAGE HEADER/SEARCH -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <logic:present name="onhandlist" scope="request">
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <strong>
                    <bean:message key="colname.ld_report_num" />
                  </strong>
                </td>
                <td>
                  <strong>
                    <bean:message key="colname.on_hand_report_number" />
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
              </tr>
              <logic:iterate id="ohd" name="onhandlist">
                <tr>
                  <td>
                    <!-- Try to obtain the lost delay information for this on-hand if supporting mbr found 
    			with matching -->
                    <a href='searchIncident.do?incident=<%= OHDUtils.getMBRReportNum((OHD)ohd, "" + a.getStation().getStation_ID()) %>'><%=OHDUtils.getMBRReportNum(((OHD)ohd), "" + a.getStation().getStation_ID()) %></a>
                    &nbsp;
                  </td>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
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
                </tr>
              </logic:iterate>
              <input type="hidden" name="search" value="1">
              <tr>
                <td colspan="11">
                  <jsp:include page="/pages/includes/pagination_incl.jsp" />
                </td>
              </tr>
              <!-- end pagination -->
            </table>
          </logic:present>
        </html:form>
