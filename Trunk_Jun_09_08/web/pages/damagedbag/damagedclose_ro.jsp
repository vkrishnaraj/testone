<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.IncidentBMO"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.db.dr.Dispute" %>
<%@ page import="com.bagnet.nettracer.tracing.db.dr.DisputeUtils" %>
<%
  	Agent a = (Agent)session.getAttribute("user");
	int lossCodeInt = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code();
	String incident_ID = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getIncident_ID();
	Incident inc = IncidentBMO.getIncidentByID(incident_ID, null);

	Company_specific_irregularity_code lc = null;
	if (lossCodeInt != 0 && inc != null) { 
	  int itemType = inc.getItemtype().getItemType_ID();
	  lc = LossCodeBMO.getLossCode(lossCodeInt, itemType, a.getStation().getCompany());
	} else {
	  lc = null;
	}

	if(inc != null) {
		request.setAttribute("faultStationCode", inc.getFaultstation().getStationcode());
	}
	request.setAttribute("lossCode", lc);

	Dispute myDispute = DisputeUtils.getDisputeByIncidentId(incident_ID);
	String disputeProcess = "false";
	if (myDispute != null) {
		disputeProcess = "true";
	} 
	request.setAttribute("disputeProcess", disputeProcess);
	
	String disputeActionType = "view";
	if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)) { 
		disputeActionType = "viewToResolve"; 
	}
%>
  <html:form action="damaged.do" method="post" enctype="multipart/form-data">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.close" />
            (
            <bean:write name="incident" scope="request" />
            )
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <td></td>
              <td></td>
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
              <a href="damaged.do?close=1"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.close" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <logic:equal name="disputeProcess" scope="request" value="true">
            <dd>
              <a href='disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=<%=disputeActionType %>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.dispute.resolution" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            </logic:equal>              
          </dl>
        </div>
      </td>
    </tr>
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <table class="form2" cellspacing="0" cellpadding="0">
           <tr>
              <td nowrap width=20%>
		      <b><bean:message key="colname.faultcompany" /></b>
                <br>
                <c:out value="${incidentForm.faultcompany_id}" default="Not Set" />
              </td>
              <td nowrap>
                <div id="faultstationdiv">
			<b><bean:message key="colname.faultstation" /></b>
                  <br>
                  <c:out value="${faultStationCode}" default="Not Set" />
                </div>
              </td>
			  <td align="center" valign="bottom">
			    <logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
			    	<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)){ 
			    		  String incidentId = "" + request.getAttribute("incident");
			    		  if (DisputeResolutionUtils.isIncidentLocked(incidentId)) {
			    	%>
			    		<input type="submit" id="button" value='<bean:message key="button.unlock.fault.information" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=unlock";return false;'>
			    	<%    } 
			    	   } %>
			    </logic:equal>
			  </td>               
            </tr>
            <tr>
              <td nowrap colspan=3>
		      <b><bean:message key="colname.losscode" /></b>
                <br>
                <c:out value="${lossCode.loss_code}-${lossCode.description}" default="Not Set" />
              </td>
            </tr>
          </table>
	<table class="form2" cellspacing="0" cellpadding="0">
		<c:if test="${!empty incidentForm.remarklist}">
			<tr>
				<td valign="top" colspan=3><b><bean:message key="colname.remarks"/></b></td>
			</tr>
		</c:if>
	        <logic:iterate id="remark" indexId="i" name="incidentForm" property="remarklist" type="com.bagnet.nettracer.tracing.db.Remark">
<%
            if(remark.getRemark_ID() != 0) {
%>
		        <logic:equal name="remark" property="remarktype" value="<%= "" + TracingConstants.REMARK_CLOSING %>">
			        <logic:present name="remark" property="agent">
				        <bean:define id="agent" name="remark" property="agent" type="com.bagnet.nettracer.tracing.db.Agent" />
			        </logic:present>
			        <tr>
                        <td valign="top">
				<b><bean:message key="colname.date" /> :</b> <bean:write name="remark" property="dispcreatetime" />
                        </td>
                        <td>
				<b><bean:message key="colname.station" /> :</b> 
                            <logic:present name="remark" property="agent">
					            <bean:write name="agent" property="companycode_ID" />
	                            &nbsp;
	                            <bean:write name="agent" property="station.stationcode" />
                            </logic:present>
                        </td>
			<td><b><bean:message key="colname.agent" /> : </b>
                            <logic:present name="remark" property="agent">
					        <bean:write name="agent" property="username" />
				        </logic:present></td>
			        </tr>
			        <tr>
					<td valign="top" colspan=3>
						<c:choose>
							<c:when test="${empty remark.remarktext}">&nbsp;</c:when>
							<c:otherwise>${remark.remarktext}</c:otherwise>
						</c:choose>
                        </td>
			</tr>
		</logic:equal>
<%
		}
%>
	</logic:iterate>
        </table>
        </div>
 <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td align="center" valign="top">
                  <br>
                                 <logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                      <logic:equal name="disputeProcess" scope="request" value="false">
		                  <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a)){ 
		                   		String incidentId = "" + request.getAttribute("incident");
    		  					if (! DisputeResolutionUtils.isIncidentLocked(incidentId)) { %>
		                    <input type="submit" id="button" value='<bean:message key="button.dispute.fault" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=start";return false;'>
		                     <% } 
		                    } %>
	                  </logic:equal>
	                  </logic:equal>
	                  
                  <INPUT id="button" type="button" value="Back" onClick="history.back()">
                  </td>
                  </tr>
                  </table>
    </html:form>
