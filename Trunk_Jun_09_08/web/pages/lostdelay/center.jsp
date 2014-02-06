<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Locale" %>
<%
	Agent a = (Agent) session.getAttribute("user");
	String cssFormClass = "form2_ld";
	IncidentForm myform = (IncidentForm) session.getAttribute("incidentForm");
	Station stationAss = StationBMO.getStation(myform.getStationassigned_ID());
	boolean submitOnSave = PropertyBMO.isTrue("ntfs.submit.lostdelay");
	boolean val2=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, a);
	
	ResourceBundle bundle = ResourceBundle.getBundle(
			"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
%>

<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Passenger"%>
<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>
<jsp:include page="/pages/includes/incident_js_incl.jsp" />
<script language="javascript">
    function confirmCarrierWt(){
    	 <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_WT_OTHER_CARRIER, a) && request.getAttribute("lostdelay") != null){ %>
  		
    	 var wtAirline= document.getElementById("wtAirlineCode"); 
    	 var wtStation= document.getElementById("wtStationCode");
    	 wtStation.value=wtStation.value.toUpperCase();
 		 var hasWTID=document.getElementById("wtid");
 		 if(wtAirline!=null && wtStation!=null){
	 		  if (wtAirline.value!=""  && wtAirline.value !="<%=a.getCompanycode_ID() %>" && wtStation.value.length != 3)
	 	      {     alert("<%= (String)bundle.getString( "colname.wt.station") %>" + " <%= (String)bundle.getString( "error.validation.isRequired.and.wt.length") %>");
		 	        wtStation.focus();
		 	        return false;
	 	      } else if (hasWTID==null && wtAirline.value != "" && wtAirline.value !="<%=a.getCompanycode_ID() %>")
	 	      {
		 	        if(!(confirm("You are creating a WorldTracer File on behalf of another airline. Is this correct?"))){
		 		        return false;
		 	        }
	 	      }
 		 }
	 <% } %>
     }
     
     function confirmWt() {
    	 confirmCarrierWt();
         <% if (!PropertyBMO.isTrue("confirm.wt.inc.action")) { %>
         		return true;
         <% } else { %>
	         	if (confirm("<bean:message key='confirm.wt.inc.action' />")) {
	             	return true;
	         	}
	         	return false;
         <% } %>
     }
    
     function deleteBag(bagtag_pos, isNew) {
    	if(isNew=='true'){
	    	var del=window.confirm("Delete the corresponding bag for this tag?");
	    	if(del==true){
	    		hideThisDiv(bagtag_pos);
	    	}
    	} else {
	    	var del=window.confirm("A claim check has been deleted. Do you wish to edit and delete a bag as well?");
	    	if(del==true){
	    		window.location.hash="#"+bagtag_pos;
	    	}
    	} 
    }
    
  </script>
  
<logic:present name="prepopulate" scope="request">

  <script language="javascript">
    
    var buttonSelected = null;
    function validateThis(form) {
      if (buttonSelected == null) {
        return true;
      } else {
        if (validateRest(form) == true) {
            
            form.doprepopulate.value = 11;
            disableButton(form.doprepopulate1);
      
            return true;
        } else {
        	return false;
        } 
        
      } 
      return true;
    }

  </script>
  
  <html:form action="lostDelay.do" method="post" enctype="multipart/form-data" onsubmit="return validateThis(this);">
    

    <html:hidden property="doprepopulate" value="" />
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
      <h1><bean:message key="header.prepopulate_ld" /></h1>
      </div>
      <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <jsp:include page="/pages/includes/mail_incl.jsp" />
          <td><a href="#"
            onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
            key="Help" /></a></td>
        </tr>
      </table>
      </div>
      </td>
    </tr>
    <tr>
      <td id="middlecolumn">
      <div id="maincontent" align="center"><font color=red>
      <logic:messagesPresent message="true">
        <html:messages id="msg" message="true">
          <br />
          <bean:write name="msg" />
          <br />
        </html:messages>
      </logic:messagesPresent> </font> <br>
      <jsp:include page="/pages/includes/incident_population.jsp" />
      
      <logic:present name="prepopIncList" scope="request">
        <h1 class="green">
          <bean:message key="prepop.search.incresults" />
        </h1>
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
            <td><strong><bean:message key="prepop.search.inc" /></strong></td>
            <td><strong><bean:message key="prepop.search.inc_create_date" /></strong></td>
            <td><strong><bean:message key="prepop.search.inc_status" /></strong></td>
            <td><strong><bean:message key="prepop.search.inc_pax" /></strong></td>
            <td><strong><bean:message key="prepop.search.inc.create_station" /></strong></td>
            <td><strong><bean:message key="prepop.search.inc.assigned_station" /></strong></td>
          </tr>
          <logic:iterate id="prepopInc" name="prepopIncList" type="com.bagnet.nettracer.tracing.db.Incident">
            <tr>
              <td><a href='searchIncident.do?incident=<bean:write name="prepopInc" property="incident_ID"/>'>
              <bean:write name="prepopInc" property="incident_ID" />
              </a></td>
              <td><bean:write name="prepopInc" property="displaydate" /></td>
              <td><bean:message name="prepopInc" property="status.key" /></td>
              <td>
<%
                boolean hasp = false;
%>              <bean:define id="passengers" name="prepopInc" property="passenger_list" />
                <logic:iterate id="passenger" name="passengers" type="com.bagnet.nettracer.tracing.db.Passenger">
<%
                  hasp = false;
%>
                  <logic:notEqual name="passenger" property="lastname" value="">
                    <bean:write name="passenger" property="lastname" />
                    ,
<%
                    hasp = true;
%>
                  </logic:notEqual>
                  <logic:notEqual name="passenger" property="firstname" value="">
<%
                    hasp = true;
%>
                  </logic:notEqual>
                  <bean:write name="passenger" property="firstname" />
                  <bean:write name="passenger" property="middlename" />
<%
                  if (hasp) {
%>
                    <br>
<%
                  }
%>
                </logic:iterate>
                &nbsp;
              </td>
              <td><bean:write name="prepopInc" property="stationcreated.stationcode" /></td>
              <td><bean:write name="prepopInc" property="stationassigned.stationcode" /></td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>

      <logic:present name="prepopOhdList" scope="request">
        <h1 class="green">
          <bean:message key="prepop.search.ohdresults" />
        </h1>
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
            <td><strong><bean:message key="prepop.search.ohd_id" /></strong></td>
            <td><strong><bean:message key="prepop.search.ohd_create_date" /></strong></td>
            <td><strong><bean:message key="prepop.search.ohd_name_on_bag" /></strong></td>
            <td><strong><bean:message key="prepop.search.ohd_color" /></strong></td>
            <td><strong><bean:message key="prepop.search.ohd_type" /></strong></td>
            <td><strong><bean:message key="prepop.search.ohd_found" /></strong></td>
            <td><strong><bean:message key="prepop.search.ohd_holding" /></strong></td>
          </tr>
          <logic:iterate id="prepopOhd" name="prepopOhdList" type="com.bagnet.nettracer.tracing.db.OHD">
            <tr>
              <td><a href="addOnHandBag.do?ohd_ID=<bean:write name="prepopOhd" property="OHD_ID"/>"><bean:write name="prepopOhd" property="OHD_ID" /></a></td>
              <td><bean:write name="prepopOhd" property="displaydate"/></td>
              <td>
                <logic:notEmpty name="prepopOhd" property="passenger">
                  <logic:notEmpty name="prepopOhd" property="passenger.lastname">
                    <bean:write name="prepopOhd" property="passenger.lastname" />
                    ,
                    <bean:write name="prepopOhd" property="passenger.firstname" />
                    &nbsp;
                    <bean:write name="prepopOhd" property="passenger.middlename" />
                    &nbsp;
                  </logic:notEmpty>
                  <logic:empty name="prepopOhd" property="passenger.lastname">
                    &nbsp;
                  </logic:empty>
                </logic:notEmpty>
                <logic:empty name="prepopOhd" property="passenger">
                &nbsp;
                </logic:empty>
              </td>
              <td><bean:write name="prepopOhd" property="color"/>&nbsp;</td>
              <td><bean:write name="prepopOhd" property="type"/>&nbsp;</td>
              <td><bean:write name="prepopOhd" property="foundAtStation.stationcode"/>&nbsp;</td>
              <td><bean:write name="prepopOhd" property="holdingStation.stationcode"/>&nbsp;</td>
            </tr>
          </logic:iterate>
        </table>
      </logic:present>

  </html:form>
  
  
  
  
</logic:present>

<logic:notPresent name="prepopulate" scope="request">




  <html:form  styleId="dirtyCheck-form" action="lostDelay.do" method="post" enctype="multipart/form-data"
    onsubmit="return validateRest(this);">
    <input type="hidden" name="delete_these_elements" value="" />
    <html:hidden property="otherSystemInformation" />
    <html:hidden property="pcn_id" />
    <html:hidden property="notifiedOfRequirements"/>
    <html:hidden property="remarkEnteredWhenNotifiedOfRequirements"/>
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft"><logic:present name="express"
        scope="request">
        <h1><bean:message key="header.express_lostdelay" /></h1>
      </logic:present> <logic:notPresent name="express" scope="request">
        <h1><bean:message key="header.lostdelay" /></h1>
      </logic:notPresent></div>
      <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <%
            if(!PropertyBMO.isTrue(PropertyBMO.DISABLE_INC_REPORT)) {
            %>
            <td><a href='javascript:window.print();'><img
              src="deployment/main/images/nettracer/icon_printrpt.gif"
              width="12" height="12"></a></td>
            <td><a href="javascript:window.print();"><bean:message
              key="print_report" /></a> &nbsp;</td>
           <%
            }
           %>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.LOST_RECEIPT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','LostReceipt',800,600);return false;"><img
              src="deployment/main/images/nettracer/icon_printrecpt.gif"
              width="12" height="12"></a></td>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.LOST_RECEIPT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','LostReceipt',800,600);return false;"><bean:message
              key="link.lost_delay_receipt" /></a> &nbsp;</td>
          </logic:notEqual>
          <jsp:include page="/pages/includes/mail_incl.jsp" />
          <td><a href="#"
            onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
            key="Help" /></a></td>
        </tr>
      </table>
      </div>
      </td>
    </tr>
    
    
    <logic:present name="express" scope="request">
      
      <jsp:include page="/pages/includes/mbrexpress_incl.jsp" />
      
    </logic:present>
    <logic:notPresent name="express" scope="request">
      <tr>
        <td colspan="3" id="navmenucell">
        <div class="menu">
        <dl>
          <%
          	if (TracerProperties
          						.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_INC_INFORMATION)) {
          %>
          <dd><a href="#incidentinfo"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.incident_info" /></span> <span class="cc">&nbsp;
          <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties
          						.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_PASSENGER)) {
          %>
          <dd><a href="#contact"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.contact_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties
          						.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_ITINERARY)) {
          %>
          <dd><a href="#passit"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.itinerary" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_BAGGAGE)) {
          %>
          <dd><a href="#checkedbaggage"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.bag_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <%
          	if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_INTERIM)) {
          %>

          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <dd><a href="#interimexpense"><span class="aa">&nbsp;
            <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.interim_expense_payout" /></span> <span class="cc">&nbsp;
            <br />
            &nbsp;</span></a></dd>
          </logic:notEqual>
          <%
          	}
          %>
          <%
          	if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_REMARKS)) {
          %>

          <dd><a href="#remarks"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.remarks" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
          	}
          %>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            
          <%
            boolean val = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OSI_LD, a);
            if (val && TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_OSI)) {
          %>

          <dd><a href="osi.do?incident_id=<bean:write name="incidentForm" property="incident_ID" />"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.osi" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
            }
          %>
          <%
            boolean bPaxCommunication = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION, a);
            boolean bPaxCommunicationReadOnly = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PAX_COMMUNICATION_READ_ONLY, a);
            if (bPaxCommunication || bPaxCommunicationReadOnly) {
          %>
          <dd><a href='paxCommunication.do?incident_id=<bean:write name="incidentForm" property="incident_ID" />'><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.pax_communication" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
            }
          %>
          
          <%
            val = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUSTOMER_COMMENTS, a);
            if (val && TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.INCIDENT_TAB_CUSTOMER_COMMENTS)) {
          %>

          <dd><a href="customerComments.do?incident_id=<bean:write name="incidentForm" property="incident_ID" />"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.customercomments" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <%
            }
          %>
            <dd><a
              href='viewMatches.do?clear=1&incident_ID=<bean:write name="incidentForm" property="incident_ID"/>'><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.matches" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <dd><a
              href="otherTasks.do?type=1&file=<bean:write name="incidentForm" property="incident_ID"/>"><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.tasks" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <%
            	if (UserPermissions.hasPermission(
            							TracingConstants.SYSTEM_COMPONENT_NAME_CLAIMS, a)) {
            %>
            <logic:notEqual name="incidentForm" property="readonly"
              value="1">
              <%
              	if (UserPermissions.hasPermission(
              									TracingConstants.SYSTEM_COMPONENT_NAME_MODIFY_CLAIM,
              									a)) {
              %>
              <dd><a href="select_claim.do?incidentId=<bean:write name="incidentForm" property="incident_ID" />"><span
                class="aa">&nbsp; <br />
              &nbsp;</span> <span class="bb"><bean:message
                key="menu.claims" /></span> <span class="cc">&nbsp; <br />
              &nbsp;</span></a></dd>
              <% if (submitOnSave && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_VIEW_FRAUD_RESULTS, a)) { %>              
              <dd><a href="select_claim.do?incidentId=<bean:write name="incidentForm" property="incident_ID" />&fraud_results=1"><span
                class="aa">&nbsp; <br />
              &nbsp;</span> <span class="bb"><bean:message
                key="menu.fraud.checks" /></span> <span class="cc">&nbsp; <br />
              &nbsp;</span></a></dd>
              <% }
              	} else if (UserPermissions.hasPermission(
              									TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
              %>
              <dd><a href="claim_prorate.do?incidentId=<bean:write name="incidentForm" property="incident_ID" />"><span class="aa">&nbsp;
              <br />
              &nbsp;</span> <span class="bb"><bean:message
                key="menu.claims" /></span> <span class="cc">&nbsp; <br />
              &nbsp;</span></a></dd>
              <%
              	}
              %>
            </logic:notEqual>
            <%
            	}
            %>
            <%
            	if (UserPermissions.hasPermission(
            							TracingConstants.SYSTEM_COMPONENT_NAME_BDO, a)) {
            %>
            <dd><a
              href='bdo.do?mbr_id=<bean:write name="incidentForm" property="incident_ID"/>&type=<%=TracingConstants.LOST_DELAY%>'><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message key="menu.bdo" /></span>
            <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <%
            	}
            %>
            <dd><a href="lostDelay.do?close=1"><span class="aa">&nbsp;
            <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.close" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <dd><a href="#" onclick="gotoHistoricalReport();"><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.history" /></span> <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
          </logic:notEqual>
        </dl>
        </div>
        </td>
      </tr>
      
      <tr>
        
        <td id="middlecolumn">
        <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SAVE_BUTTON_TOP_OF_PAGE, a)) { %> 
        <logic:notEqual name="incidentForm" property="readonly"
          value="1">
          <logic:notEqual name="incidentForm" property="incident_ID"
                value="">
			<div style="width:100%;text-align:center;" >
				<input type="button" id="topSave" class="button" onClick="document.getElementById('saveButton').click();" value="<bean:message key="button.save" />" >
				<br /><br />
			</div>
		  </logic:notEqual>  
		</logic:notEqual>
		<% } %>
        <jsp:include page="/pages/includes/reportinfo_addr_iti_bagcheck_incl.jsp" />
        
        <a name="claimcheck"></a>
        <a name='addclaimcheck'></a>
        <h1 class="green"><bean:message key="colname.claimnum" />
        <a href="#"
          onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_baggage_check_information_(ld).htm#add claim check');return false;"><img
          src="deployment/main/images/nettracer/button_help.gif"
          width="20" height="21" border="0"></a>
        </h1>
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" id="test">
          <logic:iterate id="claimcheck" indexId="i" name="incidentForm" property="claimchecklist">
            <tr id="claimcheck_<%=i %>">
              <td width="30%" nowrap="nowrap"><bean:message
                key="colname.claimnum.req" /> :</td>
              <td><html:text name="claimcheck"
                property="claimchecknum" size="13" maxlength="12"
                styleClass="textfield" indexed="true" /> <%
                  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SCANNER_DATA, a)) {
                %> <logic:notEmpty name="claimcheck"
                property="claimchecknum">
                <a
                  href="scannerData.do?bagTagNumber=<bean:write name="claimcheck" property="claimchecknum" />&incidentId=<bean:write name="incidentForm" property="incident_ID" />"><br />
                <bean:message key="scanner.link" /></a>
              </logic:notEmpty> <%
 	}
 %>
              </td>
              <td><logic:notEqual name="incidentForm"
                property="incident_ID" value="">
                <logic:notEmpty name="claimcheck" property="OHD_ID">
                  <a class="matchlink" 
                    href='addOnHandBag.do?ohd_ID=<bean:write name="claimcheck" property="OHD_ID"/>'><bean:write
                    name="claimcheck" property="OHD_ID" /></a>
                    &nbsp;
                    <logic:notPresent name="cantmatch" scope="request">

                    <input type="submit" name="unmatchclaim<%= i %>"
                      value='<bean:message key="button.un_match"/>'
                      id="button">
                  </logic:notPresent>
                </logic:notEmpty>
                <logic:notPresent name="cantmatch" scope="request">
                  <logic:empty name="claimcheck" property="OHD_ID">
                      &nbsp;&nbsp;
                      <bean:message key="colname.matched_ohd" />
                      :
                      <html:text name="claimcheck" property="tempOHD_ID"
                      size="13" maxlength="12" styleClass="textfield"
                      indexed="true" onblur="fillzero(this,13);" />
                    <input type="submit" name="matchclaim<%= i %>"
                      value='<bean:message key="button.do_match"/>'
                      id="button" onclick="fillzero(document.incidentForm.tempOHD_ID, 13); return true;">
                  </logic:empty>
                </logic:notPresent>
              </logic:notEqual>&nbsp;</td>
            </tr>
            <tr id="claimcheck_<%=i %>_1">
              <td colspan="3">
              <logic:notEqual name="incidentForm"
                property="incident_ID" value="">
	              <input type="button" value="<bean:message key="button.delete_claim" />" onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_CLAIMCHECK %>_<%=i%>', 
	              '<bean:message key="colname.claimnum.req" />', 1); <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DELETE_BAGTAG_BAGS, a)) {%> deleteBag('<%=TracingConstants.JSP_DELETE_ITEM%>_<%=i%>', 'false') <%} %>" id="button">
              </logic:notEqual>
              <logic:equal name="incidentForm"
                property="incident_ID" value="">
                <input type="button" value="<bean:message key="button.delete_claim" />" onclick="hideThisElement('<%=TracingConstants.JSP_DELETE_CLAIMCHECK %>_<%=i%>', 
	              '<bean:message key="colname.claimnum.req" />', 1); <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DELETE_BAGTAG_BAGS, a)) {%> deleteBag('<%=TracingConstants.JSP_DELETE_ITEM%>_<%=i%>', 'true') <%} %>" id="button">
              </logic:equal>
              </td>
            </tr>
          </logic:iterate>
           <!-- provide space for bag weight feature - start -->
           <!-- 
            <tr id="bag_weight">
              <td>Total Baggage Weight/Unit<br>
					<input type="text" name="total_baggage_weight" maxlength="10" size="8" value="" class="textfield">
					<select name="total_baggage_weight_unit">
			          <option value="kilogram">kg</option>
			          <option value="pound">lbs</option>
			        </select>
              </td>
              <td colspan="2"></td>
            </tr>
             -->
           <!-- provide space for bag weight feature - end -->
        </table>
        <center>
        <select name="addclaimcheckNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
        <html:submit property="addclaimcheck"
          styleId="button">
          <bean:message key="button.add_claimcheck" />
        </html:submit></center>
        <jsp:include page="/pages/includes/mbrbag_incl.jsp" />
        <logic:notEmpty name="incidentForm" property="incident_ID" >
	        <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_CREATE, a)) { %>
	        	<jsp:include page="/pages/communications/customer_communications.jsp" />
	        <% } %>
        </logic:notEmpty>
        <jsp:include page="/pages/includes/remark_incl.jsp" />
        </div>
        <logic:notEqual name="incidentForm" property="readonly"
          value="1">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" valign="top"><br>
              <logic:notEqual name="incidentForm" property="incident_ID"
                value="">
		<html:hidden property="save" value="" disabled="true" />
                <html:button property="saveButton" styleId="button"
			onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'lostdelay') != false && validateRest(this.form) != false && confirmCarrierWt() != false) {this.form.save.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.save.disabled = true; return false;}">
                  <bean:message key="button.save" />
                </html:button>

                <%
                	if (UserPermissions.hasPermission(
                								TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER, a)) {
                							if (a.getStation().getCompany().getVariable().getWt_enabled() == 1) {
                								if (a.getStation().getCompany().getVariable()
                										.getWt_write_enabled() == 1) {
                									if (UserPermissions
                											.hasPermission(
                													TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT,
                													a)) {
                %>
				&nbsp;&nbsp;&nbsp;&nbsp;

				<c:if test="${empty pendingWtAction}">
					<c:if test="${(incidentForm.wt_id == '') || (incidentForm.wt_id == null)}" > 
					<% if (stationAss != null && stationAss.getWt_stationcode() != null && stationAss.getWt_stationcode().trim().length() > 0) { %>
			<html:hidden property="savetowt" value="" disabled="true" />
						<html:button styleId="wtbutton" property="savetowtButton"
							onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'lostdelay') != false && validateRest(this.form) != false && confirmWt() != false) {this.form.savetowt.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.savetowt.disabled = true; return false;}">
                        <bean:message key="button.savetoWT" />
                      	</html:button>
                      	<% } %>
					</c:if>
 
                <%
                	if (!a.getStation().getCompany().getVariable().isAuto_wt_amend()) {
                %>
                    <c:if test="${incidentForm.wtFile.wt_status == 'ACTIVE'}">
			<html:hidden property="amendWT" value="" disabled="true" />
                      <% if (PropertyBMO.isTrue(PropertyBMO.TRACING_STATUS_BLOCK_WT) && myform.getTracingStatus() == TracingConstants.INCIDENT_TRACING_STATUS_TRACING) { %>
                      <html:button styleId="wtbutton" property="amendWTButton" disabled="true" >
                        <bean:message key="button.amendWT" />
                      </html:button>
                      <% } else { %>
                      <html:button styleId="wtbutton" property="amendWTButton"
			      onclick="disableButtons(); if( validatereqFields(this.form, 'lostdelay') != false && validateRest(this.form) != false) {this.form.amendWT.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.amendWT.disabled = true; return false;}">
                        <bean:message key="button.amendWT" />
                      </html:button>
                      <% } %>
                    </c:if>
                <%
                	}
                %>
                </c:if>

                <%
                	}
                								}
                							}
                						}
                %>
              </logic:notEqual>
              <logic:equal name="incidentForm" property="incident_ID" value="">
                <input type="hidden" name="savetemp" value="">
                <%
                	if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_TEMP_INCIDENTS, a)) {
                	%>
                <input type="button" name="s" value="Save as Temporary"
                  onclick="if(validatereqFields(this.form) && validateRest(this.form) != false && confirmCarrierWt() != false){ enableStateProvince(); saveIncidentTemporary(this.form, 'lostdelay')};"
                  id="button">
                &nbsp;&nbsp;&nbsp;
                <% } %>

		<%-- <html:submit property="savetracing" styleId="button" onclick="return validatereqFields(this.form, 'lostdelay');"> --%>
				<logic:present scope="request" name="importAhl">
					<html:hidden property="amendWT" value="" disabled="true" />
					<html:button styleId="wtbutton" property="amendWTButton" onclick="disableButtons(); if( validatereqFields(this.form, 'lostdelay') != false && validateRest(this.form) != false) {this.form.amendWT.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.amendWT.disabled = true; return false;}">
						<bean:message key="button.amendWT" />
					</html:button>
				</logic:present>
				<logic:notPresent scope="request" name="importAhl">
					<html:hidden property="savetracing" value="" disabled="true" />
		                <html:button property="savetracingButton" styleClass="button" styleId="saveButton" onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'lostdelay') != false && validateRest(this.form) != false && confirmCarrierWt() != false) {this.form.savetracing.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.savetracing.disabled = true; return false;}">
		                  <bean:message key="button.savetracing" />
		                </html:button>
                </logic:notPresent>

              </logic:equal></td>
            </tr>
          </table>
        </logic:notEqual>
        <logic:equal name="incidentForm" property="readonly" value="1">
          <logic:equal name="incidentForm"
            property="allow_remark_update" value="1">
            <table width="100%" border="0" cellpadding="0"
              cellspacing="0">
              <tr>
                <td align="center" valign="top"><br>
                <logic:notEqual name="incidentForm"
                  property="incident_ID" value="">
		<html:hidden property="save" value="" disabled="true" />
                <html:button property="saveremarkButton" styleId="button"
			onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'lostdelay') != false  && validateRest(this.form) != false && confirmCarrierWt() != false) {this.form.save.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.save.disabled = true; return false;}">
                  <bean:message key="button.saveremark" />
                </html:button>
                 <% if (!UserPermissions.hasIncidentSavePermission(a,myform.getIncident_ID()) && val2 && myform.getStatus_ID()==TracingConstants.MBR_STATUS_OPEN) { %>
					
					<html:hidden property="saveadditions" value="" disabled="true" />
					<html:button property="saveadditionsbutton" styleId="button"
						onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'lostdelay') != false && validateRest(this.form) != false && confirmCarrierWt() != false) {this.form.saveadditions.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.saveadditions.disabled = true; return false;}">
						<bean:message key="button.save" />
					</html:button>
				<%}%>
				
                </logic:notEqual></td>
                
                
         
              </tr>
            </table>
          </logic:equal>
          
        </logic:equal>
    </logic:notPresent>
    
    <script language="javascript">
      


<logic:present name="passit" scope="request">
	document.location.href="#passit";
</logic:present>

<logic:present name="bagit" scope="request">
	document.location.href="#bagit";
</logic:present>

<logic:present name="claimcheck" scope="request">
	document.location.href="#addclaimcheck";
</logic:present>

<logic:present name="passenger" scope="request">
	document.location.href='#addpassenger<bean:write name="passenger" scope="request" />';
</logic:present>

<logic:present name="item" scope="request">
	document.location.href='#additem<bean:write name="item" scope="request" />';
</logic:present>

<logic:present name="inventory" scope="request">
	document.location.href='#inventory<bean:write name="inventory" scope="request" />';
</logic:present>


<logic:present name="remark" scope="request">
	document.location.href="#addremark<bean:write name="remark" scope="request" />";
</logic:present>

<logic:present name="issuanceitem" scope="request">
	document.location.href="#addissuanceitem";
</logic:present>


    </script>
  </html:form>


</logic:notPresent>
