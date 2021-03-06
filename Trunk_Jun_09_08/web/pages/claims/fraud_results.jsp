<%@ page language="java" %>
<%@page import="com.bagnet.nettracer.tracing.forms.FraudResultsForm"%>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="aero.nettracer.fs.model.detection.AccessRequest" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  String company = a.getCompanycode_ID();
  FraudResultsForm myform = (FraudResultsForm) session.getAttribute("fraudResultsForm");
  boolean ntUser = PropertyBMO.isTrue("nt.user");
  boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");

  String incident = "";
  incident = request.getParameter(incident);
  if(incident != null && incident.length() > 0){
	  request.setAttribute("incident", incident);
  }
  
%>
  <Script language="javascript">
  jQuery(document).ready(function() {
		
		jQuery("#refresh").hide(); 

	});
  
  var count=<%=myform.getTraceResponse()!=null?myform.getTraceResponse().getSecondsUntilReload():0 %>;

  var counter=setInterval(timer, 1000); /*1000 will  run it every 1 second*/
  
  function timer()
  {
    count=count-1;
    if (count <= 0)
    {
    	jQuery("#timer").hide();
    	jQuery("#refresh").show();
        clearInterval(counter);
        return;
    }

    document.getElementById("timer").innerHTML=count+" seconds";
  }
  </Script>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  
    <html:form action="fraud_results.do" method="post" >
    
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
          <% 
          String incidentId = (String) request.getAttribute("incident");
          if (ntUser && incidentId != null && !incidentId.equals("null")) { %>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request" />'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
<%			}
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM, a)) {
            	String claimIdString = request.getParameter("claimId");
            	if (claimIdString == null || claimIdString.equals("null") || Integer.parseInt(claimIdString) <= 0) {
            		String incId = request.getParameter("incident");
					if (incId != null && !incId.equals("null")) {%>
					
              <dd>
                <a href='select_claim.do?incidentId=<%=incId %>' ><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
					<dd>
                   	<a href='fraud_results.do?incidentId=<%=incId %>'><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
					<% } else { %>
              <dd>
                <a href='claim_resolution.do' ><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
					<dd>
                   	<a href='fraud_results.do'><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
              <%   } 
              } else { %>
              <dd>
                <a href='claim_resolution.do?claimId=<bean:write name="claimId" scope="request" />' ><span class="aa">&nbsp;
                    <br />
                    &nbsp;</span>
                  <span class="bb"><bean:message key="menu.claim_payout" /></span>
                  <span class="cc">&nbsp;
                    <br />
                    &nbsp;</span></a>
              </dd>
                   	<dd>
                   	<a href='fraud_results.do?claimId=<bean:write name="claimId" scope="request" />' ><span class="aab">&nbsp;<br />&nbsp;</span>
                   	<span class="bbb"><bean:message key="menu.fraud.checks" /></span>
                        <span class="ccb">&nbsp;
                          <br />
                          &nbsp;</span></a>
                   </dd>
                   <% } %>
                   
<%
            } 
            if (ntUser && incidentId != null && !incidentId.equals("null")) {
%>
		            <dd>
		              <a href='claim_prorate.do?incident=<%=request.getAttribute("incident") %>'><span class="aa">&nbsp;
		                  <br />
		                  &nbsp;</span>
		                <span class="bb"><bean:message key="menu.claim_prorate" /></span>
		                <span class="cc">&nbsp;
		                  <br />
		                  &nbsp;</span></a>
		            </dd>
            <% } %>
                    <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_DEPREC_CALCULATOR, a) && (ntUser || ntfsUser)) { %>
                      <dd>
                      <a href='claim_deprec_calc.do?claim_id=<bean:write name="claimId" scope="request" />'><span class="aa">&nbsp;
                          <br />
                          &nbsp;</span>
                        <span class="bb"><bean:message key="menu.claim_deprec_calc" /></span>
                        <span class="cc">&nbsp;
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
                	<bean:message key="claim.meta.summary" />&nbsp;<bean:write name="displayId" scope="request"/>
                </h1>
                <font color=red>
                  <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                </font>
                <logic:empty name="fraudResultsForm" property="traceResponse" >
                	<br />
                </logic:empty>
					<logic:notEmpty name="fraudResultsForm" property="traceResponse">
					<logic:equal value="false" name="fraudResultsForm" property="traceResponse.traceComplete">
					<br/>
					<% String claimid = request.getParameter("claimId"); %>
					<font color=red> 
						<bean:message key="fraud.still.searching"/>: <span id="timer"> </span> <span id="refresh" style="display:none"><a href='fraud_results.do?claimId=<%=claimid %>' ><bean:message key="fraud.still.refresh"/></a></span>
					 </font>		
					<br/>
					</logic:equal>
       		<table class="form2" cellspacing="0" cellpadding="0">
				<tr>
						<td <%=myform.getTraceResponse().getDisplayClass() %>>
							<bean:write name="fraudResultsForm" property="traceResponse.metaSummary"/>
							<logic:notEmpty name="fraudResultsForm" property="traceResponse.metaWarning">
								<ul>
									<logic:iterate id="warning" name="fraudResultsForm" property="traceResponse.metaWarning" type="aero.nettracer.fs.model.detection.MetaWarning">
										<li><bean:write name="warning" property="description" filter="false"/></li>
									</logic:iterate>
								</ul>
							</logic:notEmpty>
												
						</td>							
					
				</tr>
			</table>	
					</logic:notEmpty>
	
	


                <h1>
                	<bean:message key="claim.fraud.primary_results" />
                </h1>
            	<table class="form2" cellspacing="0" cellpadding="0" >
            		<jsp:include page="/pages/claims/results.jsp" >
					    	<jsp:param name="beanName" value="fraudResultsForm" />
					    	<jsp:param name="beanProperty" value="primaryResults" />
					    	<jsp:param name="company" value="<%=company %>" />
					    	<jsp:param name="incident" value="<%=incident %>" />

				    </jsp:include>
            	</table>
            	<br />
            	<br />
            	<h1>
            		<bean:message key="claim.fraud.secondary_results" />
            	</h1>
            	<table class="form2" cellspacing="0" cellpadding="0" >
            		<jsp:include page="/pages/claims/results.jsp" >
					    	<jsp:param name="beanName" value="fraudResultsForm" />
					    	<jsp:param name="beanProperty" value="secondaryResults" />
					    	<jsp:param name="company" value="<%=company %>" />
							<jsp:param name="incident" value="<%=incident %>" />

				    </jsp:include>
            	</table>
            	<br />
            	<center>
					<html:submit property="requestInfo" styleId="button">
                      <bean:message key="button.request_info" />
                    </html:submit>
                    &nbsp;&nbsp;
					<html:submit property="delete" styleId="button">
                      <bean:message key="delete" />
                    </html:submit>
                </center>        	
                <br />
                <input type="hidden" name="incident" value="<%=(String) request.getAttribute("incident") %>" />
                <input type="hidden" name="claimId" value="<%=(String) request.getAttribute("claimId") %>" />
                <input type="hidden" name="displayId" value="<%=(String) request.getAttribute("displayId") %>" />
            </html:form>
