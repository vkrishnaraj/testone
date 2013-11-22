<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>

<%
	Agent a = (Agent) session.getAttribute("user");
    String cssFormClass = "form2_dam";
    boolean submitOnSave = PropertyBMO.isTrue("ntfs.submit.damaged");
    IncidentForm myform = (IncidentForm) session.getAttribute("incidentForm");
	boolean val2=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, a);
%>

<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>

<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<jsp:include page="/pages/includes/incident_js_incl.jsp" />
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
  
  <html:form action="damaged.do" method="post" onsubmit="return validateThis(this);">
    
    <html:hidden property="doprepopulate" value="" />
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
      <h1><bean:message key="header.prepopulate_dam" /></h1>
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
      
      
  </html:form>
</logic:present>

<logic:notPresent name="prepopulate" scope="request">


  
  <html:form styleId="dirtyCheck-form" action="damaged.do" method="post"
    enctype="multipart/form-data" onsubmit="return validateRest(this);">
    <input type="hidden" name="delete_these_elements" value="" />
    <html:hidden property="otherSystemInformation" />
    <html:hidden property="notifiedOfRequirements"/>
    <html:hidden property="remarkEnteredWhenNotifiedOfRequirements"/>
    <input type="hidden" name="historical_report" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft"><logic:present name="express"
        scope="request">
        <h1><bean:message key="header.express_damaged" /></h1>
      </logic:present> <logic:notPresent name="express" scope="request">
        <h1><bean:message key="header.damaged" /></h1>
      </logic:notPresent></div>
      <div id="pageheaderright">
      <table id="pageheaderright">
        <tr>
          <td></td>
          <td></td>
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
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.DAMAGE_RECEPIT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','DamageReceipt',800,600);return false;"><img
              src="deployment/main/images/nettracer/icon_printrecpt.gif"
              width="12" height="12"></a></td>
            <td><a href='#'
              onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%=ReportingConstants.DAMAGE_RECEPIT_RPT%>&incident=<bean:write name="incidentForm" property="incident_ID" />','DamageReceipt',800,600);return false;"><bean:message
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
          <dd><a href="#contact"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.contact_info" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <dd><a href="#passit"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.itinerary" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          
          <dd><a href="#checkedbaggage"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.damaged_bag_info" /></span> <span class="cc">&nbsp;
          <br />
          &nbsp;</span></a></dd>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            <dd><a href="#interimexpense"><span class="aa">&nbsp;
            <br />
            &nbsp;</span> <span class="bb"><bean:message
              key="menu.interim_expense_payout" /></span> <span class="cc">&nbsp;
            <br />
            &nbsp;</span></a></dd>
          </logic:notEqual>
          <dd><a href="#remarks"><span class="aa">&nbsp;
          <br />
          &nbsp;</span> <span class="bb"><bean:message
            key="menu.remarks" /></span> <span class="cc">&nbsp; <br />
          &nbsp;</span></a></dd>
          <logic:notEqual name="incidentForm" property="incident_ID"
            value="">
            
          <%
            boolean val = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_OSI_DAM, a);
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
              href='bdo.do?mbr_id=<bean:write name="incidentForm" property="incident_ID"/>&type=<%=TracingConstants.DAMAGED_BAG%>'><span
              class="aa">&nbsp; <br />
            &nbsp;</span> <span class="bb"><bean:message key="menu.bdo" /></span>
            <span class="cc">&nbsp; <br />
            &nbsp;</span></a></dd>
            <%
            	}
            %>
            <dd><a href="damaged.do?close=1"><span class="aa">&nbsp;
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
        <jsp:include page="/pages/includes/mbrbag_incl.jsp" />
        <logic:notEmpty name="incidentForm" property="incident_ID" >
	        <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CUST_COMM_CREATE, a)) { %>
	        	<jsp:include page="/pages/communications/customer_communications.jsp" />
	        <% } %> 
        </logic:notEmpty>
        <jsp:include page="/pages/includes/remark_incl.jsp" />
        <logic:notEqual name="incidentForm" property="readonly" value="1">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" valign="top"><br>
		<html:hidden property="save" value="" disabled="true" />
              <html:button property="saveButton" styleId="button"
		      onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'damaged') != false && validateRest(this.form) != false) {this.form.save.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.save.disabled = true; return false;}">
                <logic:notEqual name="incidentForm" property="incident_ID" value="">
              	  <bean:message key="button.save" />
                </logic:notEqual>
                <logic:equal name="incidentForm" property="incident_ID" value="">
              	  <bean:message key="button.saveincident" />
                </logic:equal>
              </html:button></td>
            </tr>
          </table>
        </logic:notEqual> <logic:equal name="incidentForm" property="readonly" value="1">
          <logic:equal name="incidentForm"
            property="allow_remark_update" value="1">
            <table width="100%" border="0" cellpadding="0"
              cellspacing="0">
              <tr>
                <td align="center" valign="top"><br>
                <logic:notEqual name="incidentForm"
                  property="incident_ID" value="">
		<html:hidden property="save" value="" disabled="true" />
                  <html:button property="saveremarkButton" styleId="button" onclick="disableButtons(); anyLossCodeChanges(); this.form.save.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();">
                    <bean:message key="button.saveremark" />
                  </html:button>
                  
                 <% if (!UserPermissions.hasIncidentSavePermission(a,myform.getIncident_ID()) && val2 && myform.getStatus_ID()==TracingConstants.MBR_STATUS_OPEN) { %>
					
					<html:hidden property="saveadditions" value="" disabled="true" />
					<html:button property="saveadditionsbutton" styleId="button"
						onclick="disableButtons(); anyLossCodeChanges(); if(validatereqFields(this.form, 'damaged') != false && validateRest(this.form) != false) {this.form.saveadditions.disabled = false; clearBeforeUnload(); enableStateProvince(); this.form.submit();} else {enableButtons(); this.form.saveadditions.disabled = true; return false;}">
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

<logic:present name="upload" scope="request">
	document.location.href='#upload<bean:write name="upload" scope="request" />';
</logic:present>


    </script>
  </html:form>

</logic:notPresent>