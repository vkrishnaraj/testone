<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
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
  o = document.searchDisputeForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.searchDisputeForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.searchDisputeForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="searchDispute.do" method="post" focus="incident_ID" onsubmit="fillzero(this.incident_ID, 13); return validateSearch(this);">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_disputes" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <br>
          <strong>
            <bean:message key="wildcard" />
          </strong>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.incident_file_num2" />
                  <br>
                  <html:text property="incident_ID" size="20" maxlength="13" styleClass="textfield" onblur="fillzero(this,13);" />
                </td>
                <td>
                </td>
              </tr>

              <tr>
                <td colspan="2" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;

                <html:reset styleId="button">
                  <bean:message key="button.reset" />
                </html:reset>
									

                </td>
              </tr>
            </table>
            <logic:present name="resultlist" scope="request">
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.incident_file_num_br" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.incident_create_date_short" />
                    </b>
                  </td>                  
                  <td>
                    <b>
                      <bean:message key="colname.fault.station" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.loss.code" />
                    </b>
                  </td>                   
                  <td>
                    <b>
                      <bean:message key="colname.disputing.agent" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.suggested.fault.station" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.suggested.loss.code" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="dispute" name="resultlist" type="com.bagnet.nettracer.tracing.db.dr.Dispute">
                  	<bean:define id="station" name="dispute" property="suggestedFaultStation" />
					<bean:define id="disputeAgent" name="dispute" property="disputeAgent" />
					<bean:define id="incident" name="dispute" property="incident" />
               		<bean:define id="status" name="dispute" property="status" />
               		<bean:define id="beforeDisputeFaultStation" name="dispute" property="beforeDisputeFaultStation" />
                  <tr>
                    <td>
                    <logic:equal name="status" property="status_ID" value='<%= "" + TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN %>'>
                      <a href='disputeResolution.do?id=<bean:write name="incident" property="incident_ID"/>&actionType=viewToResolve'><bean:write name="incident" property="incident_ID" /></a>
                    </logic:equal>
                    <logic:notEqual name="status" property="status_ID" value='<%= "" + TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN %>'>
                      <bean:write name="incident" property="incident_ID" />
                    </logic:notEqual>
                    </td>
                    <td>
                      <bean:write name="dispute" property="dispTimestampCreated" />
                    </td>                    
                    <td>
                      <bean:write name="beforeDisputeFaultStation" property="stationcode" />
                    </td>
                    <td>
                      <bean:write name="dispute" property="beforeDisputeLossCode" />
                    </td>                      
                    <td>
                      <bean:write name="disputeAgent" property="username" />
                    </td>
                    <td>
                      <bean:write name="station" property="stationcode" />
                    </td>
                    <td>
                      <bean:write name="dispute" property="suggestedLossCode" />
                    </td>
                  </tr>
                </logic:iterate>
                
              </table>
              <script language=javascript>
                
  document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
