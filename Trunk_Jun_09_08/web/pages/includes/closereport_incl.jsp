<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
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
  	String incident_ID = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getIncident_ID();
    int itemType = IncidentBMO.getIncidentByID(incident_ID, null).getItemtype().getItemType_ID();
    lc = LossCodeBMO.getLossCode(lossCodeInt, itemType, a.getCurrentlocale(), a.getStation().getCompany());
  } else {
    lc = null;
  }
  Station faultStation = ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getFaultstation();
  
%>


<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.IncidentBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Incident"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<script langugage="javascript">
  <!--
	function getstations() {
		o = document.incidentForm;
		o.getstation.value="1";
		document.getElementById("faultstationdiv").innerHTML = "<td nowrap><bean:message key="ajax.please_wait" /></td>";
			postForm("incidentForm", true, function (req) { 
				o.getstation.value = "0";
				document.getElementById("faultstationdiv").innerHTML = req.responseText; 
		});
	}

//-->
</script>
<tr>
  <td nowrap width=20%>
    <input type="hidden" name="close" value="1">
    <a name="fault"></a>
    <bean:message key="colname.faultcompany" />
    <br>
    <input type="hidden" name="getstation">
    <html:select property="faultcompany_id" styleClass="dropdown" onchange="getstations();">
      <html:option value="">
        <bean:message key="select.please_select" />
      </html:option>
      <%
 		 		Agent faultagent = (Agent)session.getAttribute("user");

        if (TracingConstants.LIMIT_FAULT_AIRLINE) {
        	
      %>
      <html:option value="<%=faultagent.getCompanycode_ID()%>">
				<%=faultagent.getStation().getCompany().getCompanydesc()%>
      </html:option>

      <%
      	} else {
      %>
      <html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
      <% } %>
      
    </html:select>
  </td>
  
  
 
    <td nowrap>
    	<div id="faultstationdiv">
    	 <logic:present name="faultstationlist" scope="request">
      <bean:message key="colname.faultstation" />
      <br>
      <html:select property="faultstation_id" styleClass="dropdown">
        <logic:equal name="incidentForm" property="readonly" value="1">
          <% if (faultStation != null) {
          	String faultStationCode = StationBMO.getStation(faultStation.getStation_ID()).getStationcode();
            int faultStationId = faultStation.getStation_ID();
          %>
            <OPTION VALUE="<%= faultStationId %>">
              <%=faultStationCode %>
            </OPTION>
          <% } else {%>
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <% } %>
        </logic:equal>
        <logic:notEqual name="incidentForm" property="readonly" value="1">
        
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultstationlist" property="station_ID" labelProperty="stationcode" />

        </logic:notEqual>
      </html:select>
      </logic:present>
      </div>
    </td>
  
  
  
</tr>
<tr>
  <td nowrap colspan=2>
    <bean:message key="colname.losscode" />
    <br>
      <html:select property="loss_code" styleClass="dropdown">
        <logic:equal name="incidentForm" property="readonly" value="1">
          <% if (lc != null) { %>
            <OPTION VALUE="<%= "" + lc.getLoss_code() %>">
              <%= "" + lc.getLoss_code() %>-
              <%= "" + lc.getDescription() %>
              </OPTION>
          <% } else {%>
          <html:option value="0">
            <bean:message key="select.please_select" />
          </html:option>
          <% } %>
        </logic:equal>
        <logic:notEqual name="incidentForm" property="readonly" value="1">
        
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
        </logic:notEqual>
      </html:select>
      
    
  </td>
</tr>
