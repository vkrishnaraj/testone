<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>

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
<tr>
  <td nowrap width=20%>
    <a name="fault"></a>
    <bean:message key="colname.faultcompany" />
    <br>
    <input type="hidden" name="getstation">
      <%
 		 		Agent faultagent = (Agent)session.getAttribute("user");
        	
      %>
        <html:select property="faultcompany_id" styleClass="dropdown" onchange="getstations();">
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="faultCompanyList" property="companyCode_ID" labelProperty="companydesc" />
        </html:select>     
  </td>
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
</tr>
<tr>
  <td nowrap colspan="2">
    <bean:message key="colname.closereport.losscode" />
    <br>
      <html:select property="loss_code" styleClass="dropdown">      
          <html:option value="0">
            <bean:message key="select.please_select" />
          </html:option>
    <%
          java.util.List codes = (java.util.List)request.getAttribute("losscodes");
    
	      for (java.util.Iterator i = codes.iterator(); i.hasNext(); ) {
	          com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code code = (com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code)i.next();
	          String lost_code = "" + ((com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm")).getLoss_code(); // loss code of current incident
	        
	          if (code.isActive() || lost_code.equals("" + code.getLoss_code())) {  
	 %>
	          <OPTION VALUE="<%= "" + code.getLoss_code() %>" <% if (lost_code.equals("" + code.getLoss_code())) { %> SELECTED <% } %>>
	          <%= "" + code.getLoss_code() %>-
	          <%= "" + code.getDescription() %>
	          </OPTION>
	 <%
	          }
	      }
	 %>
      </html:select>
  </td>
</tr>
