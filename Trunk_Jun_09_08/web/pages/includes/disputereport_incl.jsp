<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.UserGroup" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.AdminUtils" %>

<%@page import="com.bagnet.nettracer.tracing.bmo.LossCodeBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code"%>
<%
  Agent a = (Agent)session.getAttribute("user");
%>

<%

  int lossCodeInt = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code();
  String incident_ID = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getIncident_ID();
  Incident inc = IncidentBMO.getIncidentByID(incident_ID, null);
  java.util.List LimitedLossCodes=PropertyBMO.getSplitList(PropertyBMO.LIMITED_CODES_DISPUTES);
  boolean updateLossCodes=false;
  /*boolean limitedCodes=(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_GROUPS, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LIMITED_LOSS_CODES, a) && inc.getStatus().getStatus_ID()==13 && UserPermissions.hasIncidentSavePermission(a, inc));*/
  
  
  Company_specific_irregularity_code lc = null;
  if (lossCodeInt != 0) {
	int itemType = 3;
	updateLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_DAMAGE_LOSS_CODES, a));
  	if (request.getAttribute("lostdelay") != null) {
    	itemType = 1;
    	updateLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_LOSS_CODES, a));
  	} else if (request.getAttribute("missing") != null) {
     		 itemType = 2;
     		updateLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_MISSING_LOSS_CODES, a));
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
<% boolean val = PropertyBMO.isTrue(PropertyBMO.PROPERTY_INCIDENT_DISPUTE_SELECT); %>

 
<tr>
  <td nowrap colspan="3" width=20% >
    <a name="fault"></a>
    <bean:message key="colname.faultcompany" />
    <br>
    <input type="hidden" name="getstation">
      <%
 		 		Agent faultagent = (Agent)session.getAttribute("user");
        	
      %>
        <html:select property="faultcompany_id" styleClass="dropdown" disabled="true" onchange="getstations();">
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultCompanyList" property="companyCode_ID" labelProperty="companydesc" />
        </html:select>     
  </td>
    <td colspan="3" nowrap>
    	<div id="faultstationdiv">
    <logic:present name="faultstationlist" scope="request">
      <bean:message key="colname.faultstation" />
      <br>
      <% if(val && request.getParameter("actionType").equals("start")) {%>
      <html:select property="faultstation_id" styleClass="dropdown" value="">  
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultstationlist" property="station_ID" labelProperty="stationcode"  />
      </html:select>
      <% }
      else { %>
       <html:select property="faultstation_id" styleClass="dropdown" >  
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultstationlist" property="station_ID" labelProperty="stationcode"  />
      </html:select>
      <% } %>
      </logic:present>
      </div>
    </td> 
</tr>
<tr>
  <td nowrap colspan="6">
    <bean:message key="colname.closereport.losscode" />
    <br>
     
     <% if(val && request.getParameter("actionType").equals("start")) {%>
      <html:select property="loss_code" styleClass="dropdown" value="0">      
          <html:option value="0">
            <bean:message key="select.please_select" />
          </html:option>
    <%
          java.util.List codes = (java.util.List)request.getAttribute("losscodes");
    
          for (java.util.Iterator i = codes.iterator(); i.hasNext(); ) {
            com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (
                                                                                      com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code)i.next();
            
            if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_GROUPS, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LIMITED_LOSS_CODES, a) && code.getLoss_code()!=lossCodeInt && LimitedLossCodes!=null && LimitedLossCodes.contains(String.valueOf(code.getLoss_code())))
            {
            	continue;
            }
    %>
            <OPTION VALUE="<%= "" + code.getLoss_code() %>" <% String lost_code = "" + ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code(); %>>
            <%= "" + code.getLoss_code() %>-
            <%= "" + code.getDescription() %>
            </OPTION>
    <%
          }
    %>
      </html:select>
       <% } else { %>
     <html:select property="loss_code" styleClass="dropdown">      
          <html:option value="0">
            <bean:message key="select.please_select" />
          </html:option>
    <%
          java.util.List codes = (java.util.List)request.getAttribute("losscodes");
    
          for (java.util.Iterator i = codes.iterator(); i.hasNext(); ) {
            com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (
                                                                                      com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code)i.next();
            if(!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_GROUPS, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LIMITED_LOSS_CODES, a) && code.getLoss_code()!=lossCodeInt && LimitedLossCodes!=null && LimitedLossCodes.contains(String.valueOf(code.getLoss_code())))
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
      <% } %>
  </td>
</tr>