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
    o = document.viewOHDLZedForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.submit();
  }

  function gonext() {
    o = document.viewOHDLZedForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.submit();
  }

  function gopage(i) {
    o = document.viewOHDLZedForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}

  </script>
  
  <html:form action="forwardBag.do" method="post">
    <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.forward_ohd" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/forward_to_lz.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <br>
          <table class="form2" cellspacing="0" cellpadding="0">
            <logic:present name="onhandlist" scope="request">
              <tr>
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <td>
                      <b><bean:message key="colname.batch_forward" /></b>
                    </td>
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <td>
                    <b><bean:message key="colname.batch_forward" /></b>
                  </td>
                </logic:notPresent>
                <td>
                  <b><bean:message key="colname.on_hand_report_file_number" /></b>
                </td>
                <td>
                  <b><bean:message key="colname.bag_tag_number" /></b>
                </td>
                <td>
                  <b><bean:message key="colname.location" /></b>
                </td>
                <td>
                  <b><bean:message key="colname.color" /></b>
                </td>
                <td>
                  <b><bean:message key="colname.bagtype" /></b>
                </td>
                <td>
                  <b><bean:message key="colname.found_station" /></b>
                </td>
                <td>
                  <b><bean:message key="colname.name" /></b>
                </td>
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <td>
                      <b><bean:message key="colname.forwardOhd" /></b>
                    </td>
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <td>
                    <b><bean:message key="colname.forwardOhd" /></b>
                  </td>
                </logic:notPresent>
              </tr>
              <logic:iterate id="ohd" name="onhandlist">
                <tr>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <input type="checkbox" name="forwardBag" value="<bean:write name="ohd" property="OHD_ID"/>">
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <input type="checkbox" name="forwardBag" value="<bean:write name="ohd" property="OHD_ID"/>">
                    </td>
                  </logic:notPresent>
                  <td>
                    <A HREF="addOnHandBag.do?ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:write name="ohd" property="OHD_ID" /></a>
                  </td>
                  <td>
                    <logic:empty name="ohd" property="claimnum">
                      &nbsp;
                    </logic:empty>
                    <logic:notEmpty name="ohd" property="claimnum">
                      <bean:write name="ohd" property="claimnum" />
                    </logic:notEmpty>
                  </td>
                  <td style="width: 20%;">
                    <logic:empty name="ohd" property="storage_location">
                      &nbsp;
                    </logic:empty>
                    <logic:notEmpty name="ohd" property="storage_location">
                      <bean:write name="ohd" property="storage_location" />
                    </logic:notEmpty>
                  </td>
                  <td>
                    <logic:empty name="ohd" property="color">
                      &nbsp;
                    </logic:empty>
                    <logic:notEmpty name="ohd" property="color">
                      <bean:write name="ohd" property="color" />
                    </logic:notEmpty>
                  </td>
                  <td>
                    <logic:empty name="ohd" property="type">
                      &nbsp;
                    </logic:empty>
                    <logic:notEmpty name="ohd" property="type">
                      <bean:write name="ohd" property="type" />
                    </logic:notEmpty>
                  </td>
                  <td>
                    <bean:write name="ohd" property="foundAtStation.stationcode" />
                  </td>
                  <td>
                    <bean:write name="ohd" property="passenger.lastname" />
                    <logic:notEmpty name="ohd" property="passenger.lastname">
                      ,
                    </logic:notEmpty>
                    &nbsp;
                    <bean:write name="ohd" property="passenger.firstname" />
                    &nbsp;
                    <bean:write name="ohd" property="passenger.middlename" />
                  </td>
                  <logic:present name="cbroStationID" scope="session">
<%
                    if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                      <td>
                        <A HREF="forward_on_hand.do?lz=1&ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="forward" /></a>
                      </td>
<%
                    }
%>
                  </logic:present>
                  <logic:notPresent name="cbroStationID" scope="session">
                    <td>
                      <A HREF="forward_on_hand.do?lz=1&ohd_ID=<bean:write name="ohd" property="OHD_ID"/>"><bean:message key="forward" /></a>
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
              <td colspan="11">
                &nbsp;
              </td>
            </tr>
            <tr>
              <td colspan="11" align="center">
                <INPUT type="button" Id="button" value="Back" onClick="history.back()">
                &nbsp;
                <logic:present name="cbroStationID" scope="session">
<%
                  if (session.getAttribute("cbroStationID").equals("" + a.getStation().getStation_ID())) {
%>
                    <input type="button" name="batch" value="<bean:message key="button.batchForward" />" Id="button" onClick="submitLzedform()">
<%
                  }
%>
                </logic:present>
                <logic:notPresent name="cbroStationID" scope="session">
                  <input type="button" name="batch" value="<bean:message key="button.batchForward" />" Id="button" onClick="submitLzedform()">
                </logic:notPresent>
              </td>
            </tr>
          </table>
        </html:form>
        <html:form action="forward_on_hand.do" method="post">
          <input type="hidden" name="batch" value="1">
          <input type="hidden" name="batch_id" value="">
          <input type="hidden" name="lz" value="1">
        </html:form>
