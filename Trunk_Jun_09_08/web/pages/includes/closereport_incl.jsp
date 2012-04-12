<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>

<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code"%>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%
  Agent a = (Agent)session.getAttribute("user");

  int lossCodeInt = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code();
	String incident_ID = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getIncident_ID();
	Incident inc = IncidentBMO.getIncidentByID(incident_ID, null);
  
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
  
	String LockedStation=inc.getFaultstation().getStationcode();
	String LockedCode="";
	if(lc!=null){
		LockedCode=lc.getLoss_code()+"-"+lc.getDescription();
	}
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
<tr>
  <td nowrap width=20%>
    <a name="fault"></a>
    <bean:message key="colname.faultcompany" />
    <br>
    <input type="hidden" name="getstation">
      <%
 		 		Agent faultagent = (Agent)session.getAttribute("user");

      boolean stationLock=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FAULT_STATION_LOCK, a);
        	
      %>
        <html:select property="faultcompany_id" styleClass="dropdown" onchange="getstations();">
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultCompanyList" property="companyCode_ID" labelProperty="companydesc" />
        </html:select>     
  </td>
  <% if(DisputeResolutionUtils.isIncidentLocked(request.getAttribute("incident").toString()) || DisputeResolutionUtils.isIncidentStationLocked(request.getAttribute("incident").toString()) || (stationLock  && (inc.isLocked() || inc.isCodeLocked() || inc.isStationLocked()) && inc.getStatus().getStatus_ID()==13)) { %>
    <td nowrap>
               <div id="faultstationdiv">
		<b><bean:message key="colname.faultstation" /></b>
                 <br>
                 <%=(LockedStation!=null)?LockedStation:"Not Set"%>
               </div>
        </td>
        <%}
  else {%>
    <td nowrap>
    	<div id="faultstationdiv">
    <logic:present name="faultstationlist" scope="request">
      <bean:message key="colname.faultstation" />
      <br>
      <html:select property="faultstation_id" styleClass="dropdown">  
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultstationlist" property="station_ID" labelProperty="stationcode" />
      </html:select>
      </logic:present>
      </div>
    </td>
    <%} %>
    
    <% boolean splitLock=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SPLIT_LOCK, a);
    if(splitLock)
    { %>
		<logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
			<% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_ALLOW_OPEN_INCIDENT_DISPUTE)) { %>
         		<html:submit property="lock_fault" styleId="button">
      		<bean:message key="button.lock.fault.information" />
      		</html:submit>
      	<% } %>
		</logic:notEqual>

		<td align="center" valign="baseline">
		<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)){ 
		String incidentId = "" + request.getAttribute("incident");
		if (DisputeResolutionUtils.isIncidentStationLocked(incidentId)) {
		%>
		      <html:submit property="unlock_faultstation" styleId="button">
		      <bean:message key="button.unlock.fault.station" />
		    </html:submit>
		
		<%		  
		} else {
		%>
				<logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
		      <html:submit property="lock_faultstation" styleId="button">
		      <bean:message key="button.lock.fault.station" />
		      </html:submit>
				</logic:equal>
		<% 	  }
		if (DisputeResolutionUtils.isIncidentCodeLocked(incidentId)) {
		%>
		      <html:submit property="unlock_faultcode" styleId="button">
		      <bean:message key="button.unlock.fault.code" />
		    </html:submit>
		
		<%		  
		} else {
		%>
				<logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
		      <html:submit property="lock_faultcode" styleId="button">
		      <bean:message key="button.lock.fault.code" />
		      </html:submit>
				</logic:equal>
		<% 	  }
		}  %>
		</td>
		<% }
    else if(stationLock) 
    { %>
    
    <td align="center" valign="baseline">
    	<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)){ 
    		  String incidentId = "" + request.getAttribute("incident");
    		  if (DisputeResolutionUtils.isIncidentLocked(incidentId)) {
    	%>
    	              <html:submit property="unlock_fault" styleId="button">
                      <bean:message key="button.unlock.fault.information" />
                    </html:submit>

    	<%		  
    		  } else {
    	%>
      				<logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
    	              <html:submit property="lock_fault" styleId="button">
                      <bean:message key="button.lock.fault.information" />
                      </html:submit>
      				</logic:equal>
      				<logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
      					<% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_ALLOW_OPEN_INCIDENT_DISPUTE)) { %>
	   	              		<html:submit property="lock_fault" styleId="button">
                      		<bean:message key="button.lock.fault.information" />
                      		</html:submit>
                      	<% } %>
      				</logic:notEqual>
    	<% 	  } 
    	   }  %>
    </td>
    
    <% 
    } else {
    %>
    <td align="center" valign="baseline">
    	<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)){ 
    		  String incidentId = "" + request.getAttribute("incident");
    		  if (DisputeResolutionUtils.isIncidentLocked(incidentId)) {
    	%>
    	              <html:submit property="unlock_fault" styleId="button">
                      <bean:message key="button.unlock.fault.information" />
                    </html:submit>

    	<%		  
    		  } else {
    	%>
      				<logic:equal name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
    	              <html:submit property="lock_fault" styleId="button">
                      <bean:message key="button.lock.fault.information" />
                      </html:submit>
      				</logic:equal>
      				<logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
      					<% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_ALLOW_OPEN_INCIDENT_DISPUTE)) { %>
	   	              		<html:submit property="lock_fault" styleId="button">
                      		<bean:message key="button.lock.fault.information" />
                      		</html:submit>
                      	<% } %>
      				</logic:notEqual>
    	<% 	  } 
    	   }  %>
    </td>
    <% } %>
</tr>
<tr>
 <% if(DisputeResolutionUtils.isIncidentLocked(request.getAttribute("incident").toString()) || DisputeResolutionUtils.isIncidentCodeLocked(request.getAttribute("incident").toString())) { %>
   
        <td nowrap colspan=3>
	      <b><bean:message key="colname.losscode" /></b>
               <br>
               <%=(LockedCode!="")?LockedCode:"Not Set"%>
        </td>
        <%}
 else {%>
  <td nowrap colspan="3">
    <bean:message key="colname.closereport.losscode" />
    <br>
      <html:select property="loss_code" styleClass="dropdown">      
          <html:option value="0">
            <bean:message key="select.please_select" />
          </html:option>
    <%
          java.util.List codes = (java.util.List)request.getAttribute("losscodes");
    
          for (java.util.Iterator i = codes.iterator(); i.hasNext(); ) {
            com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (
                                                                                      com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code)i.next();
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
  <% }%>
</tr>
