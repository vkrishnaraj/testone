<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
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
	String cssFormClass = "form2_pil";
	
	int lossCodeInt = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code();
	String incident_ID = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getIncident_ID();
	Incident inc = IncidentBMO.getIncidentByID(incident_ID, null);
	java.util.List LimitedLossCodes=PropertyBMO.getSplitList(PropertyBMO.LIMITED_CODES_MISSING);

	Company_specific_irregularity_code lc = null;
	if (lossCodeInt != 0 && inc != null) { 
	  int itemType = inc.getItemtype().getItemType_ID();
	  lc = LossCodeBMO.getLossCode(lossCodeInt, itemType, a.getStation().getCompany());
	} else {
	  lc = null;
  	}

	boolean bagLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES));
	
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
	  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)
			  && myDispute != null
			  && myDispute.getStatus().getStatus_ID() == TracingConstants.DISPUTE_RESOLUTION_STATUS_OPEN) { 
			disputeActionType = "viewToResolve"; 
	  }
	  boolean stationLock=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LIMITED_LOSS_CODES, a) && inc.getStatus().getStatus_ID()==13 && a.getStation().getStationcode().equals(inc.getFaultstationcode()) );
%>
  <html:form action="missing.do" method="post" enctype="multipart/form-data">
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
              <a href="missing.do?close=1"><span class="aab">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bbb"><bean:message key="menu.close" /></span>
                <span class="ccb">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
            <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a) 
            		|| UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a)){ %>
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
            <% } %>
          </dl>
        </div>
      </td>
    </tr>
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <span class="reqfield">*</span>
          <bean:message key="message.required" />
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <% if(bagLossCodes){ %>
          <% java.util.List lossCodes=(java.util.List)request.getAttribute("losscodes");%>
          <logic:iterate name="incidentForm" property="itemlist" id="theitem" type="com.bagnet.nettracer.tracing.db.Item">
            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
				  <tr>
					<td colspan=3>
				<a name="additem${status.index}"></a>
					  <b><bean:message key="colname.bag_number" />
					  :
					  <c:out value="${theitem.bagnumber + 1}"/></b>
					</td>
				  </tr>
          		
				  <tr>
					<td colspan="2" style="width:60%">
						<b><bean:message key="colname.loss.code" /></b>
			  <br>
			  <%  	for (java.util.Iterator i = lossCodes.iterator(); i.hasNext(); ) {
			            com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (
			                                                                                      com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code)i.next();
			            
		            if(code.getLoss_code()==theitem.getLossCode())
		            { %>
		         
			  			<%=code.getLoss_code() %>-<%=code.getDescription()%> 
		            <% break;
		            }
		            }%>
					</td>
					<td>
						<b><bean:message key="colname.fault.station" /></b>
					  <br>
					  <% if(theitem.getFaultStation()!=null){ %>
			  			<bean:write name="theitem" property="faultStation.stationcode"/>
			  		  <% } %>
					</td>
				  </tr>
			  </table>
		  
          </logic:iterate>
		<% } else if(!bagLossCodes){ %>
          <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
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
			    		<html:hidden name="close" property="close" value="1"/>
			    	<html:submit property="unlock_fault" styleId="button">
                      <bean:message key="button.unlock.fault.information" />
                    </html:submit>
			    	<%    } 
			    	   } %>
			    </logic:equal>
			  </td>
            </tr>
            <tr>
            <% if(stationLock){ %>
            <td nowrap colspan="3">
			    <b><bean:message key="colname.closereport.losscode" /></b>
			    <br>
			      <html:select property="loss_code" styleClass="dropdown">      
			          <html:option value="0">
			            <bean:message key="select.please_select" />
			          </html:option>
			    <%
			          java.util.List codes = LossCodeBMO.getCompanyCodes(a.getStation().getCompany().getCompanyCode_ID(), TracingConstants.MISSING_ARTICLES, true, a, inc.getStatus().getStatus_ID()==13);
			    	 
			          for (java.util.Iterator i = codes.iterator(); i.hasNext(); ) {
			            com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (
			                                                                                      com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code)i.next();
			            
			            if(code.getLoss_code()!=lossCodeInt && LimitedLossCodes!=null && LimitedLossCodes.contains(String.valueOf(code.getLoss_code())))
			            {
			            	continue;
			            }
			    %>
			            <OPTION VALUE="<%= "" + code.getLoss_code() %>" <% String lost_code = "" + ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code();  if (lost_code.equals("" + code.getLoss_code())) { %> SELECTED <% } %>>
			            <%= "" + code.getLoss_code() %>-
			            <%= "" + code.getDescription() %>
			            </OPTION>
			    <%
			          }
			    %>
			      </html:select>
			  </td>
            <% } else { %>
              <td nowrap colspan=3>
		      <b><bean:message key="colname.losscode" /></b>
                <br>
                <c:out value="${lossCode.loss_code}-${lossCode.description}" default="Not Set" />
              </td>
              <% } %>
            </tr>
          </table>
          <% } %>
                    <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
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
                       <% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_ALLOW_OPEN_INCIDENT_DISPUTE)) { %>
                      <logic:equal name="disputeProcess" scope="request" value="false">
		                  <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a) && (bagLossCodes)){ 
		                   		String incidentId = "" + request.getAttribute("incident");
    		  					if (! DisputeResolutionUtils.isIncidentLocked(incidentId)) { %>
		                    <input type="submit" id="button" value='<bean:message key="button.dispute.fault" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=start";return false;'>
		                     <% } 
		                    } %>
	                  </logic:equal>
                       <% } else { %>
                                 <logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                      <logic:equal name="disputeProcess" scope="request" value="false">
		                  <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DISPUTE_FAULT_CODE, a) && (bagLossCodes)){ 
		                   		String incidentId = "" + request.getAttribute("incident");
    		  					if (! DisputeResolutionUtils.isIncidentLocked(incidentId)) { %>
		                    <input type="submit" id="button" value='<bean:message key="button.dispute.fault" />' onclick='document.location.href="disputeResolution.do?id=<bean:write name="incident" scope="request"/>&actionType=start";return false;'>
		                     <% } 
		                    } %>
	                  </logic:equal>
	                  </logic:equal>
	                  <% } %>
                  <INPUT id="button" type="button" value="Back" onClick="history.back()">
                  <% if (stationLock) { %>
	                  <html:submit property="save" styleId="button">
	                  <bean:message key="button.save" />
	                </html:submit>
	              <% } %>
                  </td>
                  </tr>
                  </table>
    </html:form>
