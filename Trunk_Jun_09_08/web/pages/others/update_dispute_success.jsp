<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>


<%
  	Agent a = (Agent)session.getAttribute("user");
  	String cssFormClass = "form2_ld";
  
	String incident = "" + request.getAttribute("incident");
  	int incidentType = DisputeResolutionUtils.getIncidentType(incident);
  	
	String targetAction = "lostDelay.do";
	if (incidentType == TracingConstants.MISSING_ARTICLES) {
		targetAction = "missing.do";
	} else if (incidentType == TracingConstants.DAMAGED_BAG) {
		targetAction = "damaged.do";
	}
%>

   <input type='hidden' name='id' value='<bean:write name="incident" scope="request"/>'>
    <tr>
      <td colspan="3" id="navmenucell">
        <div class="menu">
          <dl>
            <dd>
              <a href='searchIncident.do?incident=<bean:write name="incident" scope="request"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href="<%=targetAction %>?close=1"><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.close" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <dd>
              <a href='disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=view'><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.dispute.resolution" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </dl>
        </div>
      </td>
    </tr>
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      	<tr>
        	<td>
        	<logic:present name="success" scope="request">
          		<h1 class="green">
					Dispute Updated Successfully
					</h1>
			</logic:present>
			<logic:notPresent name="success" scope="request">
			         <h1 class="green">
					Dispute Updated Failed
					</h1>
			</logic:notPresent>
            </td>
    	</tr>
    	<tr>
    	<td>
    	<br/>
    	<input type="submit" id="button" value='Return to Dispute Manager' onclick='document.location.href="searchDispute.do?actionType=manage";return false;'>
    	&nbsp;
    	</td>
    	</tr>
    </table>
    
    
