<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code"%>
<%
  Agent a = (Agent)session.getAttribute("user");
%>

<%

  int lossCodeInt = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code();
  
  
  
  Company_specific_irregularity_code lc = null;
  if (lossCodeInt != 0) {
	int itemType = 3;
  	if (request.getAttribute("lostdelay") != null) {
    	itemType = 1;
  	} else if (request.getAttribute("missing") != null) {
     		 itemType = 2;
    }	
    
    lc = LossCodeBMO.getLossCode(lossCodeInt, itemType, a.getStation().getCompany());
  } else {
    lc = null;
  }
  Station faultStation = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getFaultstation();
  String faultAirline = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getFaultcompany_id();
  
%>


<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.IncidentBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Incident"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<script langugage="javascript">
  
	function getstations() {
		o = document.incidentForm;
		o.getstation.value="1";
		document.getElementById("faultstationdiv").innerHTML = "<td nowrap><bean:message key="ajax.please_wait" /></td>";
			postForm("incidentForm", true, function (req) { 
				o.getstation.value = "0";
				document.getElementById("faultstationdiv").innerHTML = req.responseText; 
		});
	}


</script>
		<bean:define id="station" name="dispute" property="suggestedFaultStation" scope="request" />
		<bean:define id="disputeAgent" name="dispute" property="disputeAgent" scope="request" />
		<bean:define id="resolutionAgent" name="dispute" property="resolutionAgent" scope="request" />
		<bean:define id="determinedFaultStation" name="dispute" property="determinedFaultStation" scope="request" />
		<bean:define id="status" name="dispute" property="status" scope="request" />
            	<tr>
				  <td nowrap>Dispute Date: <bean:write name="dispute" property="dispTimestampCreated" /></td>
				  <td nowrap>Dispute Agent: <bean:write name="disputeAgent" property="username" /></td>
				</tr>
				<tr>
				  <td colspan="2" nowrap>Dispute Explanation:<br />
				    <bean:write name="dispute" property="disputeExplanation" scope="request" />
				  </td>
				</tr>
				<tr>
				  <td nowrap>Suggested Fault Station: <bean:write name="station" property="stationcode" /></td>
				  <td nowrap>Suggested Fault Code: <bean:write name="dispute" property="suggestedLossCode" scope="request" /></td>
				</tr> 
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>  
				
				<logic:notEqual name="status" property="status_ID" value='<%="" + TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN%>'>
            	<tr>
            	<td nowrap>Resolved Date: <bean:write name="dispute" property="dispTimestampResolved" /></td>
            	</tr>
            	<tr>
				  <td nowrap>Dispute Resolution: <%="" + request.getAttribute("statusDesc") %></td>
				  <td nowrap>Resolution Agent: <bean:write name="resolutionAgent" property="username" /></td>
				</tr>
				<tr>
				  <td colspan="2" nowrap>Resolution Remarks:<br />
				    <bean:write name="dispute" property="resolutionRemarks" scope="request" />
				  </td>
				</tr>
				<tr>
				  <td nowrap>Determined Fault Station: <bean:write name="determinedFaultStation" property="stationcode" /></td>
				  <td nowrap>Determined Fault Code: <bean:write name="dispute" property="determinedLossCode" scope="request" /></td>
				</tr> 
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr> 
				</logic:notEqual>