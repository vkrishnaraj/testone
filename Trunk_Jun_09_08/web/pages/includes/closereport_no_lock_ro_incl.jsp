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
<%
	Agent a = (Agent)session.getAttribute("user");
	String cssFormClass = "form2_ld";

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
%>

<%
/**  
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
  */
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
			    	<% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MANAGE_FAULT_DISPUTE, a)){ 
			    		  String incidentId = "" + request.getAttribute("incident");
			    		  if (DisputeResolutionUtils.isIncidentLocked(incidentId)) {
			    	%>

    	              <html:submit property="unlock_fault" styleId="button">
                      <bean:message key="button.unlock.fault.information" />
                    </html:submit>

			    	<%    } 
			    	   } %>
			  </td>             
	</tr>
    <tr>
    <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_FAULT_STATION_LOCK, a) && inc.getStatus().getStatus_ID()==13 && inc.isLocked()) 
    {%>
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
            //if((code.getCode_id()==75 || code.getCode_id()==76 || code.getCode_id()==83 || code.getCode_id()==93 || code.getCode_id()==66) && (lossCodeInt!=75 || lossCodeInt!=76 || lossCodeInt!=83  || lossCodeInt!=93 || lossCodeInt!=66))
             System.out.println("Get LossCode Check "+ code.getLoss_code()+" and "+(code.getLoss_code()==75 || code.getLoss_code()==76 || code.getLoss_code()==83 || code.getLoss_code()==93 || code.getLoss_code()==66));
            System.out.println("LossCode Int check "+lossCodeInt + " and "+(lossCodeInt!=75 || lossCodeInt!=76 || lossCodeInt!=83  || lossCodeInt!=93 || lossCodeInt!=66));
            if(((code.getLoss_code()==75 && lossCodeInt!=75) || (code.getLoss_code()==76 && lossCodeInt!=76) || (code.getLoss_code()==83 && lossCodeInt!=83)  || (code.getLoss_code()==93 && lossCodeInt!=93) || (code.getLoss_code()==66 && lossCodeInt!=66)))
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
        <td nowrap colspan=2>
	      <b><bean:message key="colname.losscode" /></b>
               <br>
               <c:out value="${lossCode.loss_code}-${lossCode.description}" default="Not Set" />
        </td>
        <% } %>
    </tr>
