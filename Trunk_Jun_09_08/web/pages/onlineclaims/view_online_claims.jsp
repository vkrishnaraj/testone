<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
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

	function testFrame() {
		alert('hi');
		alert('hi2');
	}
    
function goprev() {
  o = document.displayClaimsListForm;
  o.prevpage.value = "1";
  o.pagination.value="1";
  o.submit();
}

function gonext() {
  o = document.displayClaimsListForm;
  o.nextpage.value="1";
  o.pagination.value="1";
  o.submit();
}

function gopage(i) {
  o = document.displayClaimsListForm;
  o.currpage.value = i;
  o.pagination.value="1";
  o.submit();

}
function updatePagination() {
    return true;
}

  </script>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="displayClaimsList.do" method="post">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.view_newly_submitted_claims" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
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
				<td><bean:message key="colname.claim_status" /> <br>
				<html:select property="claimStatus" styleClass="dropdown">
					<html:option value="SUBMITTED">
						<bean:message key="select.oc.submitted" />
					</html:option>
					<html:option value="REVIEW">
						<bean:message key="select.oc.review" />
					</html:option>
					<html:option value="ONRECORD">
						<bean:message key="select.oc.onrecord" />
					</html:option>
				</html:select></td>
			</tr>
              <tr>
                <td nowrap>
                  <bean:message key="colname.oc.date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="startDate" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.displayClaimsListForm.startDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="endDate" size="10" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.displayClaimsListForm.endDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
              </tr>
              

              <tr>
                <td align="center" valign="top">
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
                      <bean:message key="colname.oc.dateSubmitted" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.claim_status" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.oc.incidentId" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.oc.action" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="results" name="resultlist" type="com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim">
                  <tr>
                    <td>
                      <bean:write name="results" property="submitDate" />&nbsp;
                    </td>
                    <td>
                      <bean:write name="results" property="status" />&nbsp;
                    </td>
                    
                    <td>
						<a href='searchIncident.do?incident=<bean:write name="results" property="incident.incident_ID"/>'>
						<bean:write name="results" property="incident.incident_ID" />&nbsp;</a>
                    </td>
                    <td>
                      <a href='displayClaim.do?claimId=<bean:write name="results" property="claimId" />' >
                      <!--a href='#' onClick='testFrame(<bean:write name="results" property="claimId" />);' -->
                        <bean:message key="action.oc.viewClaim" /></a>
                      <br/>
                      <logic:equal name="results" property="status" value="SUBMITTED">
	                      <a href='changeClaimStatus.do?claimId=<bean:write name="results" property="claimId" />&newStatus=REVIEW'>
	                        <bean:message key="action.oc.send_to_review" /></a>
                      </logic:equal>
					  <logic:equal name="results" property="status" value="REVIEW">
	                      <a href='changeClaimStatus.do?claimId=<bean:write name="results" property="claimId" />&newStatus=ONRECORD'>
	                        <bean:message key="action.oc.send_to_onrecord" /></a>
                      </logic:equal>
                    </td>
                  </tr>
                </logic:iterate>
                <tr>
                  <td colspan="4">
                    
                    <jsp:include page="/pages/includes/pagination_incl.jsp" />
                    
                  </td>
                </tr>
                
              </table>
              <script language="javascript">
                
  document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
