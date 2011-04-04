<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  boolean ntUser = PropertyBMO.isTrue("nt.user");

%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	
    function changebutton() {
        document.claimForm.saveclaim.disabled = true;
        document.claimForm.saveclaim.value = "<bean:message key="ajax.please_wait" />";
        document.claimForm.save.disabled = false;
      }
      
      function undoChangebutton() {
        document.claimForm.saveclaim.disabled = false;
        document.claimForm.saveclaim.value = "<bean:message key="button.claim.resolution.save" />";
        document.claimForm.save.disabled = true;
      }

  </SCRIPT>
  
    <html:form action="fraud_results.do" method="post" onsubmit="fillzero(this.incident_ID, 13);">
    
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.fraud_results" />
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
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
          <% if (ntUser) { %>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
<%			}
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, a)) {
%>
              <dd>
                <a href="claim_resolution.do"><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
<%
            } if (ntUser) {
%>
					<dd>
                   	<a href='fraud_results.do?incident=<bean:write name="incident" scope="request" />' ><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
		            <dd>
		              <a href='claim_prorate.do'><span class="aa">&nbsp;
		                  <br />
		                  &nbsp;</span>
		                <span class="bb"><bean:message key="menu.claim_prorate" /></span>
		                <span class="cc">&nbsp;
		                  <br />
		                  &nbsp;</span></a>
		            </dd>
            <% } else { %>
					<dd>
                   	<a href='fraud_results.do'><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
            
            <% } %>
          </dl>
        </div>
      </td>
    </tr>
    
    <tr>            
    	<td id="middlecolumn">
        	<div id="maincontent">
                <h1>
                	<bean:message key="claim.fraud.primary_results" />
                </h1>
            	<table class="form2">
            		<tr>
            			<td>
            				<bean:message key="colname.fraudresults.select" />
            			</td>
            			<td>
            				<bean:message key="colname.claim.id" />
            			</td>
            			<td>
            				<bean:message key="colname.fraudresults.company" />
            			</td>
            			<td>
            				<bean:message key="colname.fraudresults.claim_date" />
            			</td>
            		</tr>
            		<!-- ITERATE THROUGH THE LIST OF PRIMARY RESULTS -->
            	</table>
            	<br />
            	<br />
            	<h1>
            		<bean:message key="claim.fraud.secondary_results" />
            	</h1>
            	<table class="form2">
            		<tr>
            			<td>
            				<bean:message key="colname.fraudresults.select" />
            			</td>
            			<td>
            				<bean:message key="colname.claim.id" />
            			</td>
            			<td>
            				<bean:message key="colname.fraudresults.company" />
            			</td>
            			<td>
            				<bean:message key="colname.fraudresults.claim_date" />
            			</td>
            		</tr>
            		<!-- ITERATE THROUGH THE LIST OF SECONDARY RESULTS -->
            	</table>
            	<br />
            	<br />
            	<center>
				<html:submit property="requestInfo" styleId="button">
                      <bean:message key="button.request_info" />
                    </html:submit>
                    </center>        	
            </html:form>
