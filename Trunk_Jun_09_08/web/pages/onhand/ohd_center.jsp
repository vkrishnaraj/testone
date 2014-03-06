<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.forms.OnHandForm"%>
<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.StationBMO"%>

<%@page import="java.util.List"%>
<%@page import="com.bagnet.nettracer.tracing.db.OHD_Itinerary"%>
<%@page import="com.bagnet.nettracer.tracing.db.Item"%>
<%
  org.apache.struts.util.PropertyMessageResources myMessages = (org.apache.struts.util.PropertyMessageResources)
                                                               request.getAttribute("org.apache.struts.action.MESSAGE");
  java.util.Locale myLocale = (java.util.Locale)session.getAttribute(
                                                               "org.apache.struts.action.LOCALE");

   Agent a = (Agent) session.getAttribute("user");
   OnHandForm onHandForm = (OnHandForm) session.getAttribute("OnHandForm");
   Station holdingStation = null;
   if(onHandForm != null ) {
	   holdingStation = StationBMO.getStationByCode(onHandForm.getHolding_station(), onHandForm.getHolding_company());
   }
   String cssFormClass = "form2_ohd";
   
   boolean ppuLD=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_LOSTDELAY, a);
   boolean ppuMA=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_MISSING, a);
   boolean ppuDM=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_DAMAGE, a);
%>



<logic:present name="prepopulate" scope="request">
   <script>

	  function disableButton(aButton) {
	   	aButton.disabled = true;
	   	aButton.value= "<bean:message key='ajax.please_wait' />";
	  }
      
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
  
  <html:form action="addOnHandBag.do" method="post" onsubmit="return validateThis(this);">
    
    <html:hidden property="doprepopulate" value="" />
    <tr>
      <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
      <h1><bean:message key="header.prepopulate_ohd" /></h1>
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
      <div id="maincontent" align="center"><font color="red">
      <logic:messagesPresent message="true">
        <html:messages id="msg" message="true">
          <br />
          <bean:write name="msg" />
          <br />
        </html:messages>
      </logic:messagesPresent> </font> <br>
      </div>
      <jsp:include page="/pages/includes/incident_population.jsp" />
  </html:form>
</logic:present>

<logic:notPresent name="prepopulate" scope="request">

<SCRIPT SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT>
  
    var cal1xx = new CalendarPopup();    

    

    function showmanu(o) {
        if (o.value == <%=TracingConstants.MANUFACTURER_OTHER_ID%>) {
            document.getElementById("manu_other").style.visibility = "visible";
        } else {
            document.getElementById("manu_other").style.visibility = "hidden";
        }
    }
    

  </SCRIPT>

<script>
	var disposeLocal=false;


		<%String Initial_OHD_DisStatusVal=(String)session.getAttribute("Initial_OHD_DisStatusVal");%>
		  
  function ppuCheck(){
	  <% if(ppuLD || ppuMA || ppuDM) { %>
	    var initialVal = <%=Initial_OHD_DisStatusVal%>;
		var passPickUpVal = <%=TracingConstants.OHD_STATUS_OWNER_PICKED_UP%>;
		var x = document.getElementById("disposal_status.status_ID");
		var selectedVal = x.value;
		if(selectedVal==passPickUpVal){
			<%if(onHandForm.getExistMatchedItemlist()!=null){
				  for(Object obj:onHandForm.getExistMatchedItemlist()){
					  Item item=(Item)obj;
					  if(item.getOHD_ID()!=null && item.getOHD_ID().equals(onHandForm.getOhd_id())){ 
						  if(((item.getItemtype_ID()==TracingConstants.LOST_DELAY && ppuLD) || 
								  (item.getItemtype_ID()==TracingConstants.MISSING_ARTICLES && ppuMA) || 
								  (item.getItemtype_ID()==TracingConstants.DAMAGED_BAG && ppuDM)) &&
								  item.getStatus().getStatus_ID()!=TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP){%>
						  	alert('<%= (String) myMessages.getMessage(myLocale, "error.update.incident.ppu")%>');
						  	if(initialVal!=0)
						  		x.value=initialVal;
						  	else
						  		x.value="";
						  	return false;
						  <% 
						  }
					  }
				  }  
				}
			%>
			alert('<%= (String) myMessages.getMessage(myLocale, "info.check.claimcheck.pos.id")%>');
		  
	  	}
	<%}%>
  }
  
  function validateStatus(form)
  {	
	<%String Intial_OHD_StatusVal =  (String)session.getAttribute("Intial_OHD_StatusVal");%>
	returnStatus = true;    
    var initialVal = <%=Intial_OHD_StatusVal%>;
	var x = document.getElementsByName("status.status_ID");
	var inTransitVal = <%=TracingConstants.OHD_STATUS_IN_TRANSIT%>;
	var matchInTransitVal = <%=TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT%>;
	var selectedVal = x[0].value;

	if(initialVal!=inTransitVal && initialVal!=matchInTransitVal)
		{			
		 if(selectedVal==inTransitVal)
			{	
				alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.status.change.otherStatus")%>'+' '+'<%= (String) myMessages.getMessage(myLocale, "STATUS_KEY_6")%>');
				returnStatus = false; 
			}

		 if(selectedVal==matchInTransitVal)
			{							
				alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.status.change.otherStatus")%>'+' '+'<%= (String) myMessages.getMessage(myLocale, "STATUS_KEY_5")%>');
				returnStatus = false;
			}
		}
	
		if((initialVal == inTransitVal && selectedVal!= inTransitVal ) || 
				(initialVal == matchInTransitVal && selectedVal != matchInTransitVal))
			{								
				alert('<%= (String) myMessages.getMessage(myLocale, "error.validation.status.change")%>');
				returnStatus = false;
			}
		return returnStatus;
  }
  
  
function gotoHistoricalReport() {
  o = document.OnHandForm;
    o.historical_report.value = "1";
    clearBeforeUnload(); 
    o.submit();
}

  </script>
<SCRIPT>
  function textCounter2(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
    field.value = field.value.substring(0, maxlimit);
    } else {
    countfield.value = maxlimit - field.value.length;
    }
  }

  function updateLateCheckValue() {
	var lateCheck = document.getElementById("lateCheck");
	if (lateCheck.value.indexOf("true") != -1) {
		lateCheck.value = "false";
	} else {
		lateCheck.value = "true";
	}
  }


  function isDisposedCheck(){
	  disposeLocal=false;
	  var disValue=document.getElementById("disposal_status.status_ID");
	  if(disValue.value==<%=TracingConstants.ITEM_STATUS_DISPOSED_LOCALLY%>){
		  <% if(onHandForm.getExistDisposalStatus()!=null) {
		  		int lastStatus=0;
		  		if(onHandForm.getExistDisposalStatus()!=null)
		  			lastStatus=onHandForm.getExistDisposalStatus().getStatus_ID();%>

				    var remText=document.getElementById("remark["+(<%=onHandForm.getRemarklist().size()-1%>)+"].remarktext");
		  		if(disValue.value!=<%=lastStatus%> && (<%= onHandForm.getRemarklist().size()<=onHandForm.getExistRemarkSize() %> 
		  			|| (remText!=null && remText.value!=null && remText.value.replace(/\s*/g, "").length==0 )))
		  			disposeLocal=true;
		  <% } else { %>
		    var remText=document.getElementById("remark[0].remarktext");
		  	if(remText!=null && remText.value!=null && remText.value.replace(/\s*/g, "").length==0){
				disposeLocal=true;
		  	}
		  <% } %>
	  }
	  
  }
  </SCRIPT>

<html:form styleId="dirtyCheck-form" action="addOnHandBag.do" method="post"
  enctype="multipart/form-data" onsubmit="return validateRest(this);">
  <html:hidden property="lateCheck" />
  <tr>
    <td colspan="3" id="pageheadercell">
    <div id="pageheaderleft">
    <%
       if (onHandForm.getOhd_type() == TracingConstants.MASS_OHD_TYPE) {
    %>
    <h1><bean:message key="header.mass_add_on_hand_title" /></h1>
    <%
       } else {
    %>
    <h1><bean:message key="header.add_on_hand_title" /></h1>
    <%
       }
    %>
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
    <td colspan="3" id="navmenucell">
    <div class="menu">
    <dl>
      <dd><a href="#baginfo"><span class="aa">&nbsp; <br />
      &nbsp;</span> <span class="bb"><bean:message key="menu.bag_info" /></span>
      <span class="cc">&nbsp; <br />
      &nbsp;</span></a></dd>
      <dd><a href="#passengerinfo"><span class="aa">&nbsp;
      <br />
      &nbsp;</span> <span class="bb"><bean:message
        key="menu.passenger_info" /></span> <span class="cc">&nbsp; <br />
      &nbsp;</span></a></dd>
      <dd><a href="#itinerary"><span class="aa">&nbsp; <br />
      &nbsp;</span> <span class="bb"><bean:message
        key="menu.bag_itinerary" /></span> <span class="cc">&nbsp; <br />
      &nbsp;</span></a></dd>
      <dd><a href="#centralbag"><span class="aa">&nbsp;
      <br />
      &nbsp;</span> <span class="bb"><bean:message
        key="menu.central_baggage_inventory" /></span> <span class="cc">&nbsp;
      <br />
      &nbsp;</span></a></dd>
      <dd><a href="#remarks"><span class="aa">&nbsp; <br />
      &nbsp;</span> <span class="bb"><bean:message key="menu.remarks" /></span>
      <span class="cc">&nbsp; <br />
      &nbsp;</span></a></dd>
      <%
         if (UserPermissions.hasPermission(
                  TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
      %>
      <dd><a href="#photos"><span class="aa">&nbsp; <br />
      &nbsp;</span> <span class="bb"><bean:message key="menu.photos" /></span>
      <span class="cc">&nbsp; <br />
      &nbsp;</span></a></dd>
      <%
         }
      %>
      <logic:notEqual name="OnHandForm" property="ohd_id" value="">
        <%
           if (UserPermissions.hasPermission(
                       TracingConstants.SYSTEM_COMPONENT_NAME_BDO, a)) {
                    if (a.getCompanycode_ID().equals(onHandForm.getHolding_company())
                          && a.getStation().getStationcode().equals(
                                onHandForm.getHolding_station())) {
        %>
        <dd><a
          href='bdo.do?ohd_id=<bean:write name="OnHandForm" property="ohd_id"/>'><span
          class="aa">&nbsp; <br />
        &nbsp;</span> <span class="bb"><bean:message key="menu.bdo" /></span>
        <span class="cc">&nbsp; <br />
        &nbsp;</span></a></dd>
        <%
           }
                 }
        %>
        <dd><a
          href='viewMatches.do?clear=1&ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>'><span
          class="aa">&nbsp; <br />
        &nbsp;</span> <span class="bb"><bean:message key="menu.matches" /></span>
        <span class="cc">&nbsp; <br />
        &nbsp;</span></a></dd>
        <dd><a
          href="otherTasks.do?type=0&file=<bean:write name="OnHandForm" property="ohd_id"/>"><span
          class="aa">&nbsp; <br />
        &nbsp;</span> <span class="bb"><bean:message key="menu.tasks" /></span>
        <span class="cc">&nbsp; <br />
        &nbsp;</span></a></dd>
        <dd><a href="#" onclick="gotoHistoricalReport();"><span
          class="aa">&nbsp; <br />
        &nbsp;</span> <span class="bb"><bean:message key="menu.ohd_history" /></span>
        <span class="cc">&nbsp; <br />
        &nbsp;</span></a></dd>
      </logic:notEqual>
    </dl>
    </div>
    </td>
  </tr>
  <input type="hidden" name="wtq_pending_cancel" value="" />
  <input type="hidden" name="wtq_suspend" value="" />
  <input type="hidden" name="wtq_reinstate" value="" />
  <input type="hidden" name="historical_report" value="" />
  <input type="hidden" name="hidden_ohd_id" value="" />
  
  <tr>
    
    <td id="middlecolumn">
    <div id="maincontent"><a name="baginfo"></a>
    <div id="pageheaderleft">
    <h1 class="green"><bean:message key="header.bag_info" /> <a
      href="#"
      onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_on-hand_bag_reports.htm#baggage_info_fields');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a></h1>
          <span class="reqfield">*</span> <bean:message key="Required" /> <font
      color="red"> <logic:messagesPresent message="true">
      <html:messages id="msg" message="true">
        <br />
        <bean:write name="msg" />
        <br />
      </html:messages>
    </logic:messagesPresent> </font> <br>
      </div>
      <div id="pageheaderright">
    <logic:notEqual name="OnHandForm" property="ohd_id" value="">
      <c:if test="${!empty OnHandForm.matched_incident}" >
        <b><bean:message key="message.matched_to" /> <a href='searchIncident.do?incident=<c:out value="${OnHandForm.matched_incident}" />'><c:out value="${OnHandForm.matched_incident}" /></a></b><br />
      </c:if>
      <%
         if (onHandForm.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED &&
        		 onHandForm.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_MATCH_IN_TRANSIT &&
        		 onHandForm.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_IN_TRANSIT) {
                  if (a.getCompanycode_ID().equals(onHandForm.getHolding_company())
                        && a.getStation().getStationcode().equals(
                              onHandForm.getHolding_station())) {
      %>
      <A
        HREF="forward_on_hand.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>"><b><bean:message key="colname.forwardThisBag" /></b></A>
      <c:if test="${!empty OnHandForm.wt_id and empty pendingWtAction }">
        <%
           if (UserPermissions.hasPermission(
                                TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD, a)) {
        %>
        <logic:notEqual name="OnHandForm" property="wt_id" value="">
                        &nbsp;|&nbsp;
                        <a
            href="worldtracerfoh.do?ohd_id=${OnHandForm.ohd_id}&clear=1">Forward
          to WT</a>
        </logic:notEqual>
        <%
           }
        %>
      </c:if>
      <%
         } else if(!a.getStation().getStationcode().equals(onHandForm.getHolding_station())) {
      %>
      <a href='request_on_hand.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>'><b><bean:message
        key="colname.requestThisBag" /></b></a>
              <c:if test="${!empty OnHandForm.wt_id }">
        <%
           if (UserPermissions.hasPermission(
                                TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_ROH, a)) {
        %>
        <logic:notEqual name="OnHandForm" property="wt_id" value="">
                        &nbsp;|&nbsp;
                        
            <c:if test="${!empty OnHandForm.matched_incident}" >
            <a href="worldtracerroh.do?wt_ohd_id=${OnHandForm.wt_id}&incident=${OnHandForm.matched_incident}"><bean:message key="wt.request.ohd"/></a>
        	</c:if>
        	<c:if test="${empty OnHandForm.matched_incident}" >
        	   <a href="worldtracerroh.do?wt_ohd_id=${OnHandForm.wt_id}"><bean:message key="wt.request.ohd"/></a>
        	</c:if>
        </logic:notEqual>
        <%
           }
        %>
      </c:if>

      <%
         }
      %>


      <c:if test="${pendingWtAction == 'WT_PENDING_CREATE'}">
      <br />
        <bean:message key="wt.pending.ohd.create" />
      &nbsp;<a href="javascript: document.forms[0].hidden_ohd_id.value = '${OnHandForm.ohd_id}'; document.forms[0].submit();"><bean:message key="update" /></a>
      &nbsp;<a href="javascript: document.forms[0].wtq_pending_cancel.value = '${wtq_pending_id}'; document.forms[0].hidden_ohd_id.value = '${OnHandForm.ohd_id}'; document.forms[0].submit();">
         <bean:message key="cancel" /></a>
      </c:if>
      <c:if test="${!empty OnHandForm.wt_id }">
            <%
      	if(a.getCompanycode_ID().equals(onHandForm.getHolding_company())) {
      		%>
      <br />
        WorldTracer ID: <a href="worldtraceraf.do?rawtext=1&ohd_id=${OnHandForm.wt_id}">
        <c:out value="${OnHandForm.wt_id}" /></a>


        <c:choose>
          <c:when test="${!empty pendingWtAction}">
         <br />
         <% boolean action = false; %>
            <c:choose>
            
              <c:when test="${pendingWtAction == 'WT_PENDING_AMEND'}">
              <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT, a)) { %>
                <bean:message key="wt.pending.ohd.amend" />
                <% action = true;} %>
              </c:when>
              <c:when test="${pendingWtAction == 'WT_PENDING_SUSPEND'}">
              <% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
                <bean:message key="wt.pending.ohd.suspend" />
                <% action = true;} %>
              </c:when>
              <c:when
                test="${pendingWtAction == 'WT_PENDING_REINSTATE'}">
                <% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
                <bean:message key="wt.pending.ohd.reinstate" />
                <% action = true;} %>
              </c:when>
              <c:when test="${pendingWtAction == 'WT_PENDING_CLOSE'}">
              <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_INCIDENT, a)) { %>
                <bean:message key="wt.pending.ohd.close" />
                <% action = true;} %>
              </c:when>
              <c:when test="${pendingWtAction == 'WT_PENDING_FOH'}">
              <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_FWD, a)) { %> 
                <bean:message key="wt.pending.ohd.fwd" />
                <% action = true;} %>
              </c:when>
            </c:choose> &nbsp;
            <% if(action){ %>
            <a
              href="javascript: ; document.forms[0].hidden_ohd_id.value = '${OnHandForm.ohd_id}'; document.forms[0].submit();"><bean:message
              key="update" /></a>&nbsp;<a
              href="javascript: document.forms[0].wtq_pending_cancel.value = '${wtq_pending_id}'; document.forms[0].hidden_ohd_id.value = '${OnHandForm.ohd_id}'; document.forms[0].submit();"><bean:message
              key="cancel" /></a>
              <% } %> 
          </c:when>
          <c:otherwise>
         <br />
            <c:choose>
              <c:when
                test="${OnHandForm.wtFile.wt_status == 'ACTIVE'}">
               <% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
                <a
                  href="javascript: document.forms[0].wtq_suspend.value = '1'; document.forms[0].hidden_ohd_id.value = '${OnHandForm.ohd_id}'; document.forms[0].submit();"><bean:message
                  key="wt.ohd.suspend" /></a>
                  <% } %>
              </c:when>
              <c:when
                test="${OnHandForm.wtFile.wt_status == 'SUSPENDED'}">
           <% if (UserPermissions.hasPermission("WorldTracer SUS/RIT", a)) { %>
                <a
                  href="javascript: document.forms[0].wtq_reinstate.value = '1'; document.forms[0].hidden_ohd_id.value = '${OnHandForm.ohd_id}'; document.forms[0].submit();"><bean:message
                  key="wt.ohd.reinstate" /></a>
                   <% } %>
              </c:when>
              <c:otherwise>
                <bean:message key="wt.ohd.closed" />
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
              <% } %>
      </c:if>


      <%
         } else if (onHandForm.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_IN_TRANSIT) {
                  if (onHandForm.getForwarded_station() != null
                        && onHandForm.getForwarded_station().length() > 0
                        && a.getCompanycode_ID()
                              .equals(onHandForm.getHolding_company())
                        && a.getStation().getStationcode().equals(
                              onHandForm.getHolding_station())) {
      %>

      <bean:message key="message.forwarded" />
      <b><bean:write name="OnHandForm" property="forwarded_station" /></b>
      <bean:message key="message.forwarded_on" />
      <b><bean:write name="OnHandForm" property="dispForwarded_date" /></b>
      <bean:message key="message.forwarded_by" />
      <b><bean:write name="OnHandForm" property="forwarded_agent" /></b>&nbsp;

      <a href="addOnHandBag.do?ohd_ID=<bean:write name="OnHandForm" property="ohd_id"/>&cancelFwd=1" ><bean:message key="link.cancelForward" /></a>       
      
      
      <%
         }
               }
         else {
      %>
   
<c:if test="${!empty OnHandForm.wt_id }">
        WorldTracer ID: <a href="worldtraceraf.do?rawtext=1&ohd_id=${OnHandForm.wt_id}">
        <c:out value="${OnHandForm.wt_id}" /></a>
        <c:if test="${OnHandForm.wtFile.wt_status != 'SUSPENDED' && OnHandForm.wtFile.wt_status != 'ACTIVE'}">
		<br/>
		<bean:message key="wt.ohd.closed" />
		</c:if>
</c:if>

<%} %>
<% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CLONE_OHD, a)){ %>

         
<c:if test="${!empty OnHandForm.ohd_id}">
	<br /><a href="addOnHandBag.do?cloneOnHand=${OnHandForm.ohd_id}&skip_prepopulate=1"><bean:message key="ohd.clone"/></a>
</c:if>

      <% }
         if (UserPermissions.hasPermission(
                     TracingConstants.SYSTEM_COMPONENT_NAME_SCANNER_DATA, a)) {
      %>

      <logic:notEmpty name="OnHandForm" property="bagTagNumber">
        <br />
        <b><a
          href="scannerData.do?bagTagNumber=<bean:write name="OnHandForm" property="bagTagNumber" />&ohdId=<bean:write name="OnHandForm" property="ohd_id" />"><bean:message
          key="scanner.link" /></a></b>
        <br />
      </logic:notEmpty>
      <%
         }
      %>

    </logic:notEqual></div>

    <table class="form2_ohd" cellspacing="0" cellpadding="0">
      <tr>
        <td>
	        <bean:message key="colname.on_hand_create_report_number" /> <br>
	        <html:text property="ohd_id" size="14" styleClass="textfield" readonly="true" />
		</td>
		
        <td>
	        <bean:message key="colname.found_date_time" /> <br>
	        <html:text property="dispFoundTime" size="14" styleClass="textfield" />
        </td>
        <td>
        	<bean:message key="colname.ohd_create_agent" /> <br>
         	<html:text property="agent_initials" size="10" readonly="true" styleClass="textfield" disabled="true" />
        </td>
        <td>
        	<bean:message key="colname.found_station_nobr" /> <br>
        	<input type="text" size="10" class="textfield" disabled="disabled"
        			value="<bean:write name="OnHandForm" property="found_company"/> <bean:write name="OnHandForm" property="found_station"/>"/>
        </td>
        <td>
        	<bean:message key="colname.holding_station_nobr" /> <br>
        	<input type="text" size="10" class="textfield" disabled="disabled"
        		value="<bean:write name="OnHandForm" property="holding_company"/> <bean:write name="OnHandForm" property="holding_station"/>"/>
        </td>
        
        <c:set var="form2_ohd_colspan" value="3" scope="request"/>
        <logic:notEmpty name="OnHandForm" property="status">
          <logic:equal name="OnHandForm" property="status.status_ID" value="<%="" + TracingConstants.OHD_STATUS_CLOSED%>">
            <td>
            	<c:set var="form2_ohd_colspan" value="4" scope="request"/>
            	<bean:message key="colname.file_close_date" /> <br>
            	<html:text property="dispCloseDate" size="15" styleClass="textfield" disabled="true" />            	
            </td>
          </logic:equal>
        </logic:notEmpty>      
      </tr>
      
      <tr>
        <td>
        	<bean:message key="colname.ohd_modified_date" /> <br> 
        	<html:text property="dispModifiedDate" size="14" readonly="true" styleClass="textfield" />
        </td>
        <td>
        	<bean:message key="colname.ohd_modified_agent"/> <br>
          	<html:text property="modifiedAgent" size="10" styleClass="textfield" disabled="true" />
         </td>
		 <td colspan="${form2_ohd_colspan}">
        	<bean:message key="colname.ohd_inventory_date" /> <br> 
        	<html:text property="dispInventoryDate" size="14" readonly="true" styleClass="textfield" />
      	</td>
      </tr>
    </table>
    <table class="form2_ohd" cellspacing="0" cellpadding="0">
      <tr>
		<jsp:include page="/pages/onhand/ohdStatus_incl.jsp" />
        <td><bean:message key="colname.disposal_status" /> <br>

        <html:select name="OnHandForm"
          property="disposal_status.status_ID" styleClass="dropdown" onchange="ppuCheck(); isDisposedCheck();">
          <OPTION VALUE=""><bean:message key="select.none" /></option>
          <html:options collection="dStatusList" property="status_ID"
            labelProperty="description"/>
        </html:select></td>


        <td><bean:message key="colname.bag_arrived_date.req" /> (
        <%=a.getDateformat().getFormat()%>) <br>
        <html:text property="dispBagArriveDate" size="11" maxlength="10"
          styleClass="textfield" /><img
          src="deployment/main/images/calendar/calendar_icon.gif"
          id="calendar" name="calendar" height="15" width="20"
          border="0" onmouseover="this.style.cursor='hand'"
          onClick="cal1xx.select(document.OnHandForm.dispBagArriveDate,'calendar','<%=a.getDateformat().getFormat()%>'); return false;"></td>
      </tr>
      <% if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a)) { %>
      <tr>
      	<td>
      		<bean:message key="colname.posId" /><br>
      		<html:text property="posId" size="8" maxlength="8" styleClass="textfield" />
      	</td>
      	<td colspan="2">
      		<bean:message key="colname.latecheck" /><br>
      		<html:checkbox property="lateCheckValue" onclick="updateLateCheckValue();" />
      	</td>
      </tr>
      <% } %>
      <tr>
        <td><bean:message key="colname.last_name_onbag" /> <br>
        <html:text name="OnHandForm" property="lastname" size="30"
          maxlength="30" styleClass="textfield" /></td>
        <td><bean:message key="colname.first_name_onbag" /> <br>
        <html:text name="OnHandForm" property="firstname" size="30"
          maxlength="30" styleClass="textfield" /></td>
        <td><bean:message key="colname.mid_initial_onbag" /> <br>
        <html:text name="OnHandForm" property="middlename" size="4"
          maxlength="1" styleClass="textfield" /></td>
      </tr>
      <tr>
        <td><bean:message key="colname.bag_tag_number.req" /> <br>
        <html:text property="bagTagNumber" size="18" maxlength="12"
          styleClass="textfield" /></td>
        <td><bean:message key="colname.pnr" /> <br>
        <html:text property="pnr" size="10" maxlength="6"
          styleClass="textfield" /></td>
        <td><bean:message key="colname.storage_location" /> <br>
        <html:textarea name="OnHandForm" styleId="commentBox" property="storage_location" rows="3" cols="40" 
          onkeydown="textCounter2(commentBox, commentCount, 125);" 
          onkeyup="textCounter2(commentBox, commentCount, 125);" styleClass="textfield" />  
          <input name="comment2" id="commentCount" type="text" value="125" size="4" maxlength="4" disabled="true" />  </td>
      </tr>
      <tr>
        <td><bean:message key="colname.airline_membership" /> <br>
        <html:select property="companycode_ID" styleClass="dropdown">
          <html:option value="">
            <bean:message key="select.none" />
          </html:option>
          <html:options collection="companylistByName"
            property="companyCode_ID" labelProperty="companydesc" />
        </html:select></td>
        <td><bean:message key="colname.membership_number" /> <br>
        <html:text property="membershipnum" size="22" maxlength="20"
          styleClass="textfield" /></td>
        <td><bean:message key="colname.membership_status" /> <br>
        <html:text property="membershipstatus" size="22" maxlength="20"
          styleClass="textfield" /></td>
      </tr>
      <tr>
        <td valign="top"><bean:message key="colname.color.req" /> 
        <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=3&type=bagColor',800,30,230);return false;"><bean:message key="chart3" /></a><br />
        <html:select property="bagColor" styleClass="dropdown">
          <html:options collection="colorlist" property="value"
            labelProperty="label" />
        </html:select> <br>
        <br>
        <bean:message key="colname.bagtype.req" /> 
        <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=1&key=bagType&type=bagType',800,280,230);return false;"><bean:message key="chart1" /></a>
        <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=2&key=bagType&type=bagType',800,370,230);return false;"><bean:message key="chart2" /></a>
        <br>
        <html:select property="bagType" styleClass="dropdown">
          <html:options collection="typelist"  property="value" labelProperty="label"  />
        </html:select></td>
        <td valign="top"><bean:message key="colname.x_desc" /> <br>
        <html:select property="XDesc1" styleClass="dropdown">
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="xdescelementlist"
            property="XDesc_ID" labelProperty="description" />
        </html:select>
        <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&type=XDesc1',800,30,230);return false;"><bean:message key="chart4" /></a>
        <br>
        <html:select property="XDesc2" styleClass="dropdown">
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="xdescelementlist"
            property="XDesc_ID" labelProperty="description" />
        </html:select>
        <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&type=XDesc2',800,30,230);return false;"><bean:message key="chart4" /></a>
        <br>
        <html:select property="XDesc3" styleClass="dropdown">
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="xdescelementlist"
            property="XDesc_ID" labelProperty="description" />
        </html:select>
        <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&type=XDesc3',800,30,230);return false;"><bean:message key="chart4" /></a>
        </td>
        <td valign="top"><bean:message key="colname.manufacturer" />
        <br>
        <html:select property="manufacturer_ID" styleClass="dropdown"
          onchange='showmanu(this);return true;'>
          <html:option value="">
            <bean:message key="select.please_select" />
          </html:option>
          <html:options collection="manufacturerlist"
            property="manufacturer_ID" labelProperty="description" />
        </html:select>
        <div id="manu_other"><br>
        <html:text property="manufacturer_other" size="25"
          maxlength="100" styleClass="textfield" /></div>
        </td>
      </tr>
      <tr>
      <td colspan=3>
      			<bean:message key="colname.bag.external.desc" /><br>
              	<html:text property="externaldesc" maxlength="50" size="75" styleClass="textfield"/>
              
      </td>
      </tr>
      <% if ( UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ASSIGN_WAREHOUSE_DATES, a) ) { %> 
	  
       <tr>
	          <td>
	          		<bean:message key="colname.warehouse.received.date" /> (
        <%=a.getDateformat().getFormat()%>) <br>
        <html:text property="dispWarehouseReceivedDate" size="11" maxlength="10"
          styleClass="textfield" /><img
          src="deployment/main/images/calendar/calendar_icon.gif"
          id="calendar2" name="calendar2" height="15" width="20"
          border="0" onmouseover="this.style.cursor='hand'"
          onClick="cal1xx.select(document.OnHandForm.dispWarehouseReceivedDate,'calendar2','<%=a.getDateformat().getFormat()%>'); return false;">
	          	</td>
	          	<td colspan=2>
	          		<bean:message key="colname.warehouse.sent.date" /> (
        <%=a.getDateformat().getFormat()%>) <br>
        <html:text property="dispWarehouseSentDate" size="11" maxlength="10"
          styleClass="textfield" /><img
          src="deployment/main/images/calendar/calendar_icon.gif"
          id="calendar3" name="calendar3" height="15" width="20"
          border="0" onmouseover="this.style.cursor='hand'"
          onClick="cal1xx.select(document.OnHandForm.dispWarehouseSentDate,'calendar3','<%=a.getDateformat().getFormat()%>'); return false;">
	          	</td>
	          	
	          </tr>
	          <% } %>
    </table>
    <br>
    <br>
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <a name="passengerinfo"></a>
    <h1 class="green"><bean:message key="header.passenger_info" />
    <a href="#"
      onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_passenger_information_(oh).htm#add passenger info');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a></h1>
    <span class="reqfield">*</span> <bean:message key="Required" /> <logic:iterate
      id="passenger" name="OnHandForm" property="passengerList"
      indexId="i" type="com.bagnet.nettracer.tracing.db.OHD_Passenger">
      <table class="form2_ohd" cellspacing="0" cellpadding="0">
        <tr>
			<jsp:include page="/pages/includes/paxHeader.jsp" >
				<jsp:param name="header_index" value="<%= i %>"/>
			</jsp:include>
        </tr>
        <tr>
          <td colspan="2"><bean:message key="colname.last_name" /> <br>
          <html:text name="passenger" property="lastname" size="30"
            maxlength="30" indexed="true" styleClass="textfield" /></td>
          <td colspan="2"><bean:message key="colname.first_name" />
          <br>
          <html:text name="passenger" property="firstname" size="30"
            maxlength="30" indexed="true" styleClass="textfield" /></td>
          <td><bean:message key="colname.mid_initial" /> <br>
          <html:text name="passenger" property="middlename" size="1"
            maxlength="1" indexed="true" styleClass="textfield" /></td>
        </tr>
        <logic:present name="passenger" property="addresses">
          <logic:iterate indexId="k" name="passenger" id="addresses"
            property="addresses"
            type="com.bagnet.nettracer.tracing.db.OHD_Address">
            <tr>
              <td colspan="2"><bean:message
                key="colname.street_addr1" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].address1"%>'
                size="45" maxlength="50" styleClass="textfield" /></td>
              <td colspan="3"><bean:message
                key="colname.street_addr2" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].address2"%>'
                size="45" maxlength="50" styleClass="textfield" /></td>
            </tr>
            <tr>
              <td><bean:message key="colname.city" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].city"%>'
                size="15" maxlength="50" styleClass="textfield" /></td>
              <td><bean:message key="colname.state" /> <br>
              <logic:equal name="addresses" property="countrycode_ID"
                value="US">
                <html:select
                  property='<%="addresses["
                                                   + (i
                                                         .intValue() * 20 + k
                                                         .intValue())
                                                   + "].state_ID"%>'
                  styleClass="dropdown"
                  onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                  <html:option value="">
                    <bean:message key="select.none" />
                  </html:option>
                  <html:options collection="statelist" property="value"
                    labelProperty="label" />
                </html:select>
              </logic:equal> <logic:equal name="addresses" property="countrycode_ID"
                value="">
                <html:select
                  property='<%="addresses["
                                                   + (i
                                                         .intValue() * 20 + k
                                                         .intValue())
                                                   + "].state_ID"%>'
                  styleClass="dropdown"
                  onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                  <html:option value="">
                    <bean:message key="select.none" />
                  </html:option>
                  <html:options collection="statelist" property="value"
                    labelProperty="label" />
                </html:select>
              </logic:equal> <logic:notEqual name="addresses"
                property="countrycode_ID" value="">
                <logic:notEqual name="addresses"
                  property="countrycode_ID" value="US">
                  <html:select
                    property='<%="addresses["
                                                      + (i
                                                            .intValue() * 20 + k
                                                            .intValue())
                                                      + "].state_ID"%>'
                    styleClass="dropdown" disabled="true"
                    onchange="updateCountryUS(this, this.form, 'countrycode_ID', 'province');">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="statelist"
                      property="value" labelProperty="label" />
                  </html:select>
                </logic:notEqual>
              </logic:notEqual></td>
              <td><bean:message key="colname.province" /> <br>
              <logic:equal name="addresses" property="countrycode_ID"
                value="US">
                <html:text
                  property='<%="addresses["
                                                + (i.intValue() * 20 + k
                                                      .intValue())
                                                + "].province"%>'
                  size="15" maxlength="100"
                  styleClass="disabledtextfield" disabled="true" />
              </logic:equal> <logic:equal name="addresses" property="countrycode_ID"
                value="">
                <html:text
                  property='<%="addresses["
                                                + (i.intValue() * 20 + k
                                                      .intValue())
                                                + "].province"%>'
                  size="15" maxlength="100" styleClass="textfield" />
              </logic:equal> <logic:notEqual name="addresses"
                property="countrycode_ID" value="">
                <logic:notEqual name="addresses"
                  property="countrycode_ID" value="US">
                  <html:text
                    property='<%="addresses["
                                                   + (i
                                                         .intValue() * 20 + k
                                                         .intValue())
                                                   + "].province"%>'
                    size="15" maxlength="100" styleClass="textfield" />
                </logic:notEqual>
              </logic:notEqual></td>
              <td><bean:message key="colname.zip" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].zip"%>'
                size="15" maxlength="9" styleClass="textfield" /></td>
              <td><bean:message key="colname.country" /> <br>
              <html:select
                property='<%="addresses["
                                                + (i.intValue() * 20 + k
                                                      .intValue())
                                                + "].countrycode_ID"%>'
                styleClass="dropdown"
                onchange="checkstate(this,this.form,'state_ID', 'province');">
                <html:option value="">
                  <bean:message key="select.none" />
                </html:option>
                <html:options name="OnHandForm" collection="countrylist"
                  property="value" labelProperty="label" />
              </html:select></td>
            </tr>
            <tr>
              <td><bean:message key="colname.home_ph" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].homephone"%>'
                size="15" maxlength="25" styleClass="textfield" /></td>
              <td><bean:message key="colname.business_ph" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].workphone"%>'
                size="15" maxlength="25" styleClass="textfield" /></td>
              <td><bean:message key="colname.mobile_ph" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].mobile"%>'
                size="15" maxlength="25" styleClass="textfield" /></td>
              <td><bean:message key="colname.pager_ph" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].pager"%>'
                size="15" maxlength="25" styleClass="textfield" /></td>
              <td><bean:message key="colname.alt_ph" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].altphone"%>'
                size="15" maxlength="25" styleClass="textfield" /></td>
            </tr>
            <tr>
              <td colspan="5"><bean:message key="colname.email" /> <br>
              <html:text
                property='<%="addresses["
                                       + (i.intValue() * 20 + k
                                             .intValue())
                                       + "].email"%>'
                size="45" maxlength="50" styleClass="textfield" /></td>
            </tr>
          </logic:iterate>
        </logic:present>
        <tr>
          <td colspan="5"><html:submit styleId="button"
            property="deletePassenger" indexed="true">
            <bean:message key="button.delete_passenger" />
          </html:submit></td>
        </tr>
      </table>
    </logic:iterate>
    <center><html:submit styleId="button"
      property="addPassenger">
      <bean:message key="button.add_additional_passenger" />
    </html:submit></center>
    <br>
    <br>
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <a name="itinerary"></a>
		  <script>
			  function swap(field, i, j) {

				  var swap1 = document.getElementsByName('itinerarylist[' + i + '].' + field)[0];
				var swap2 = document.getElementsByName('itinerarylist[' + j + '].' + field)[0];
				var tmp = swap1.value;
				swap1.value = swap2.value;
				swap2.value = tmp;
				}

			function swapItin(moveUp, moveDown) {
				var k = 0;
				swap('legfrom', moveUp, moveDown);	
				swap('legto', moveUp, moveDown);	
				swap('airline', moveUp, moveDown);	
				swap('flightnum', moveUp, moveDown);	
				swap('disdepartdate', moveUp, moveDown);	
				swap('disarrivedate', moveUp, moveDown);	
				swap('disschdeparttime', moveUp, moveDown);	
				swap('disscharrivetime', moveUp, moveDown);	
				swap('disactdeparttime', moveUp, moveDown);	
				swap('disactarrivetime', moveUp, moveDown);	
			}

			function swapUpItin(elem, indexes) {
				var idx = 0;

				for (var i = 0; i < indexes.length; ++i) {
					if (indexes[i] == elem) {
						idx = i;
						break;					
					}
				}

				if (idx < 1) { return; }
				var moveUp = indexes[idx];
				var moveDown = indexes[idx - 1];
				swapItin(moveUp, moveDown);
			}	


			function swapDownItin(elem, indexes) {
				var idx = 0;

				for (var i = 0; i < indexes.length; ++i) {
					if (indexes[i] == elem) {
						idx = i;
						break;					
					}
				}

				if (idx > indexes.length - 2) {	return;	}
				var moveUp = indexes[idx+1];
				var moveDown = indexes[idx];
				swapItin(moveUp, moveDown);
			}



				 <% 
				 	int itinSize = 0;
				 	List list = onHandForm.getItinerarylist();
				 	
					String bItinIndexes = "";
					
					int firstBIndex = -1;
					
					int lastBIndex = 0;
				 	for (int i = 0; i < list.size(); ++i) {
				 		OHD_Itinerary itin = (OHD_Itinerary) list.get(i);
				 		
						bItinIndexes += i + ",";
						if (firstBIndex == -1) {
							firstBIndex = i;
						}
						lastBIndex = i;
				 		
				 	}

				 	if (bItinIndexes.length() > 0) {
				 		bItinIndexes = bItinIndexes.substring(0, bItinIndexes.length() - 1);
					}
					if (firstBIndex == -1) {
						firstBIndex = 0;
					}


				 	%>

				 var bItinIndexes = [<%=bItinIndexes%>];
		  </script>
    <h1 class="green"><bean:message key="header.ohd_bag_itinerary" />
    <a href="#"
      onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_baggage_itineraries_(oh).htm#add_baggage_itinerary');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a></h1>
    <span class="reqfield">*</span> <bean:message key="Required" />

      	      <%
	      int t = 0;
	      %>
    <logic:iterate
      id="itinerarylist" indexId="k" name="OnHandForm"
      property="itinerarylist">
	  
	      <span id="placeHolder<%=t%>" style="float:right">
	      <%
	      if (firstBIndex != t) {	
		%>
		<input type="button" class="button" id="moveUp<%=t%>" value="Move Up" onclick="swapUpItin(<%=t%>, bItinIndexes);"/>
		<%
		}
		if (lastBIndex != t) {
		%>

		<input type="button" class="button"  id="moveDown<%=t%>" value="Move Down" onclick="swapDownItin(<%=t%>, bItinIndexes);"/>
		<%
		}
		%>
		</span>
	  
      <table class="form2_ohd" cellspacing="0" cellpadding="0">
        <tr>
          <td><bean:message key="colname.ohd.fromto.req" /> <br>
          <html:hidden name="itinerarylist" property="itinerarytype"
            value="1" indexed="true" /> <html:text name="itinerarylist"
            property="legfrom" size="3" maxlength="3"
            styleClass="textfield" indexed="true" /> <a href="#"
            onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%=k%>].legfrom','airportcode',500,600);return false;"><img
            src="deployment/main/images/nettracer/airport_codes.gif"
            border="0"></a> / <html:text name="itinerarylist"
            property="legto" size="3" maxlength="3"
            styleClass="textfield" indexed="true" /> <a href="#"
            onclick="openWindow('pages/popups/airportcodes.jsp?key=itinerarylist[<%=k%>].legto','airportcode',500,600);return false;"><img
            src="deployment/main/images/nettracer/airport_codes.gif"
            border="0"></a></td>
          <td><bean:message key="colname.ohd.flightnum.req" /> <br>
          
          <logic:empty name="itinerarylist" property="airline">
            <jsp:setProperty name="itinerarylist" property="airline" value="<%= a.getCompanycode_ID() %>"/>
          </logic:empty>
          
          <html:select name="itinerarylist" property="airline"
            styleClass="dropdown" indexed="true">
            <html:option value="">
              <bean:message key="select.please_select" />
            </html:option>
            <html:options collection="companylistById"
              property="companyCode_ID" labelProperty="companyCode_ID" />
          </html:select> &nbsp; <html:text name="itinerarylist" property="flightnum"
            size="4" maxlength="4" styleClass="textfield" indexed="true" /></td>
          <td><bean:message key="colname.ohd.departdate.req" /> ( <%=a.getDateformat().getFormat()%>):
          <br>
          <html:text name="itinerarylist" property="disdepartdate"
            size="11" maxlength="10" styleClass="textfield"
            indexed="true" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="calendar2<%=k%>" name="calendar2<%=k%>" height="15"
            width="20" border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.OnHandForm, '<%="itinerarylist[" + k
                           + "].disdepartdate"%>','calendar2<%=k%>','<%=a.getDateformat().getFormat()%>'); return false;"></td>
          <td><bean:message key="colname.ohd.arrdate.req" /> ( <%=a.getDateformat().getFormat()%>)
          <br>
          <html:text name="itinerarylist" property="disarrivedate"
            size="11" maxlength="10" styleClass="textfield"
            indexed="true" /><img
            src="deployment/main/images/calendar/calendar_icon.gif"
            id="calendar3<%=k%>" name="calendar3<%=k%>" height="15"
            width="20" border="0" onmouseover="this.style.cursor='hand'"
            onClick="cal1xx.select2(document.OnHandForm, '<%="itinerarylist[" + k
                           + "].disarrivedate"%>','calendar3<%=k%>','<%=a.getDateformat().getFormat()%>'); return false;"></td>
        </tr>
        <tr>
          <td><bean:message key="colname.schdeptime" /> ( <%=a.getTimeformat().getFormat()%>)
          <br>
          <html:text name="itinerarylist" property="disschdeparttime"
            size="5" maxlength="5" styleClass="textfield" indexed="true" /></td>
          <td><bean:message key="colname.scharrtime" /> ( <%=a.getTimeformat().getFormat()%>)
          <br>
          <html:text name="itinerarylist" property="disscharrivetime"
            size="5" maxlength="5" styleClass="textfield" indexed="true" /></td>
          <td><bean:message key="colname.actdeptime" /> ( <%=a.getTimeformat().getFormat()%>)
          <br>
          <html:text name="itinerarylist" property="disactdeparttime"
            size="5" maxlength="5" styleClass="textfield" indexed="true" /></td>
          <td><bean:message key="colname.actarrtime" /> ( <%=a.getTimeformat().getFormat()%>)
          <br>
          <html:text name="itinerarylist" property="disactarrivetime"
            size="5" maxlength="5" styleClass="textfield" indexed="true" /></td>
        </tr>
        <tr>
          <td colspan="4"><html:submit styleId="button"
            property="deleteBag" indexed="true">
            <bean:message key="button.delete_ohd_itinerary" />
          </html:submit></td>
        </tr>
      </table>
    <% ++t; %>
    </logic:iterate>
    
    <center><html:submit property="additinerary"
      styleId="button">
      <bean:message key="button.add_bag_itinerary" />
    </html:submit></center>
    <br>
    <br>
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <a name="centralbag"></a>
    <h1 class="green"><bean:message
      key="header.central_baggage_inventory" /> <a href="#"
      onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_baggage_inventory_(oh).htm');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a></h1>
    <span class="reqfield">*</span> <bean:message key="Required" /> <logic:iterate
      indexId="i" id="itemlist" name="OnHandForm" property="itemlist"
      type="com.bagnet.nettracer.tracing.db.OHD_Inventory">
      <table class="form2_ohd" cellspacing="0" cellpadding="0"
        width="100%">
        <tr>
          <td><bean:message key="colname.category" /><br>
          <html:select name="itemlist" property="OHD_categorytype_ID"
            indexed="true" styleClass="dropdown">
            <html:option value="">
              <bean:message key="select.please_select" />
            </html:option>
            <html:options collection="categorylist"
              property="OHD_CategoryType_ID"
              labelProperty="description" />
          </html:select></td>
          <td><bean:message key="colname.ohd.description.req" /> <br>
          <html:text name="itemlist" property="description" size="70"
            maxlength="255" styleClass="textfield" indexed="true" /></td>
          <td align="center">&nbsp;<br>
          <html:submit styleId="button" property="deleteItem"
            indexed="true"  onclick="return checkOhdDeleteCount();">
            <bean:message key="button.delete_content" />
          </html:submit></td>
        </tr>
      </table>
    </logic:iterate>
    <center><html:submit styleId="button" property="additem">
      <bean:message key="button.add_content" />
    </html:submit></center>
    <br>
    <br>
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <%
       if (UserPermissions.hasPermission(
                TracingConstants.SYSTEM_COMPONENT_NAME_OHD_PHOTOS, a)) {
    %> <a name="photos"></a>
    <h1 class="green"><bean:message key="header.photos" /> <a
      href="#"
      onclick="openHelp('pages/WebHelp/on-hand_reports/work_with_baggage_inventory_(oh).htm#upload photos');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a></h1>
    <span class="reqfield">*</span> <bean:message key="Required" />
    <table class="form2_ohd" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <%
             int k = 0;
          %>
          <logic:present name="OnHandForm" property="photoList">
            <logic:iterate id="photo" name="OnHandForm"
              property="photoList"
              type="com.bagnet.nettracer.tracing.db.OHD_Photo">
              <%
                 if (k % 3 == 0) {
              %>
              <tr align="center">
                <%
                   }
                %>
                <td align="center"><a
                  href='showImage?ID=<bean:write name="photo" property="picpath"/>'
                  target="top"><img
                  src='showImage?ID=<bean:write name="photo" property="thumbpath"/>'></a>
                <br>
                <a href='showImage?ID=<bean:write name="photo" property="picpath"/>' target="top">
                  <bean:write name="photo" property="fileName"/></a>
                            <br>
                <html:submit styleId="button" property="deletePhoto"
                  indexed="true">
                  <bean:message key="button.delete_photo" />
                </html:submit></td>
                <%
                   if (k % 3 == 2) {
                %>
              </tr>
              <%
                 }
                                k++;
              %>
            </logic:iterate>
          </logic:present>
        </table>
        <br>
        <center><input type="FILE" name="theFile1" /> &nbsp; <html:submit
          property="uploadPhoto" styleId="button">
          <bean:message key="header.addPhotos" />
        </html:submit></center>
        </td>
      </tr>
    </table>
    <br>
    <br>
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <%
       }
    %>
    <h1 class="green"><bean:message key="header.ohd.fault" />
    <a href="#"
      onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a>
    </h1>
    <% 
    if ((a.getCompanycode_ID().equals(onHandForm.getHolding_company()) && (UserPermissions.hasPermission(
            TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_OHD_LOSS_CODES, a)) || onHandForm.getOhd_id() == null || onHandForm.getOhd_id().trim().length() < 1
            || (onHandForm.getStatus().getStatus_ID() != TracingConstants.OHD_STATUS_CLOSED && onHandForm.getReadonly() != 1))) { %>
    	<jsp:include page="/pages/includes/ohd_fault_incl.jsp">
    		<jsp:param value="form2_ohd" name="tableClass"/>
    	</jsp:include>
    <%
    }
    else {
    %>
    	<jsp:include page="/pages/includes/ohd_fault_incl_ro.jsp" >
    		<jsp:param value="form2_ohd" name="tableClass"/>
    	</jsp:include>
    <%
    }
    %>
    <br />
    <br />
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br />
    <br />
    <h1 class="green"><bean:message key="header.remarks" /> <a
      href="#"
      onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/work_with_remarks_(oh).htm#add remarks');return false;"><img
      src="deployment/main/images/nettracer/button_help.gif" width="20"
      height="21" border="0"></a></h1>
    <a name="remarks"></a>
    <table class="form2_ohd" cellspacing="0" cellpadding="0">
      <logic:iterate id="remark" indexId="i" name="OnHandForm"
        property="remarklist"
        type="com.bagnet.nettracer.tracing.db.Remark">
        <% if(!remark.isSecure() ||  (remark.isSecure() && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a))){ %>
          
        <logic:equal name="remark" property="remarktype"
          value="<%=""
                              + TracingConstants.REMARK_REGULAR%>">
          <logic:present name="remark" property="agent">
            <bean:define id="agent" name="remark" property="agent"
              type="com.bagnet.nettracer.tracing.db.Agent" />
          </logic:present>
          <tr class="<%=remark.isSecure()?"secureRemark":""%>">
            <td style="<%=remark.isSecure()?"color:darkRed":""%>" valign="top"><a name='addremark<%=i%>'></a> <bean:message
              key="colname.date" /> : <bean:write name="remark"
              property="dispcreatetime" /></td>
            <td style="<%=remark.isSecure()?"color:darkRed":""%>"><bean:message key="colname.station" /> : <logic:present
              name="remark" property="agent">
              <bean:write name="agent" property="companycode_ID" />
              &nbsp;
              <bean:write name="agent" property="station.stationcode" />
            </logic:present></td>
            <td style="<%=remark.isSecure()?"color:darkRed":""%>"><bean:message key="colname.agent" /> : <logic:present
              name="remark" property="agent">
              <bean:write name="agent" property="username" />
            </logic:present></td>
             <%  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a) && remark.getRemark_ID()==0) { %>
              
              <td style="<%=remark.isSecure()?"color:darkRed":""%>">
                <bean:message key="colname.secure" />
                :
                <input type="checkbox" name="remark[<%=i %>].secure" 
                      <logic:equal name="remark" property="secure" value="true">
                        checked="checked"
                      </logic:equal> />
              </td><% } else if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a)) { %>
              <td style="<%=remark.isSecure()?"color:darkRed":""%>">
              	<logic:equal name="remark" property="secure" value="true">
              		<bean:message key="secure.remark" />
              	</logic:equal>
              	<logic:notEqual name="remark" property="secure" value="true">
              		<bean:message key="general.remark" />
              	</logic:notEqual>
              </td>
              <% } %>
          </tr>
          <tr class="<%=remark.isSecure()?"secureRemark":""%>">
            <td style="<%=remark.isSecure()?"color:darkRed":""%>" valign="top" colspan="<%=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a))?4:3%>">
            <%
               if (a.getGroup().getDescription().equalsIgnoreCase("Admin")
                                    || remark.getRemark_ID() == 0) {
            %> <%
    String remarkDescription = "remark[" + i + "].remarktext";
                      String remarkText = "this.form.elements['"
                            + remarkDescription + "']";
                      String remarkText2 = "this.form.elements['"
                            + remarkDescription + "2']";
 %> <textarea name="<%=remarkDescription%>" cols="80" rows="10"
              onkeydown="textCounter2(<%=remarkText%>, <%=remarkText2%>,1500); "
              onkeyup="textCounter2(<%=remarkText%>, <%=remarkText2%>,1500);"
              <logic:equal name="OnHandForm" property="readonly" value="1"><%if (remark.getRemark_ID() > 0) {%> readonly="readonly"<%}%></logic:equal>><%=remark.getRemarktext()%> </textarea>

            <input name="<%=remarkDescription + "2"%>" type="text"
              value="1500" size="4" maxlength="4" disabled="true" /> <br>
            <logic:notEqual name="OnHandForm" property="readonly"
              value="1">
              <html:submit styleId="button" property="deleteRemark"
                indexed="true">
                <bean:message key="button.delete_remark" />
              </html:submit>
            </logic:notEqual> <%
    } else {
 %> <bean:write name="remark" property="readonlyremarktext" filter="false"/> <%
    }
 %>
            </td>
          </tr>
        </logic:equal>
        <% }  %>
	  	<input type="hidden" name="remark[<%=i %>].secure" value="<bean:write name="remark" property="secure"/>" />
        
      </logic:iterate>
    </table>
    <center><html:submit property="addremark" styleId="button">
      <bean:message key="button.add_remark" />
    </html:submit></center>
    <br>
    <br>
    &nbsp;&nbsp;&uarr; <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    </div>
    <logic:notEqual name="OnHandForm" property="readonly" value="1">
    <table width="100%" cellspacing="0" cellpadding="0">
      <tr><td align="center"><br />
    <c:if test="${empty OnHandForm.status}">
        <input type="hidden" name="savetemp" value="">
        <%
           if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_CREATE_TEMP_INCIDENTS, a)) {
        %>
        <input type="button" name="s" value="Save as Temporary"
        	onclick="isDisposedCheck();  if (validatereqOHDForm(this.form) && validateRest(this.form)){ saveOHDTemporary(this.form)};" id="button">
        &nbsp;&nbsp;&nbsp;
        <% } %>
        <html:submit styleId="button" property="savetracing" onclick="isDisposedCheck();  return validatereqOHDForm(this.form);">
           <bean:message key="button.savetracingohd" />
        </html:submit>
    </c:if>

     <c:if test="${!empty OnHandForm.status}">
         <html:submit styleId="button" property="savetracing" onclick="isDisposedCheck();  return (validateStatus(this.form) && validatereqOHDForm(this.form));">
          <bean:message key="button.saveohd" />
        </html:submit>
	     <%
	      if ((a.getStation().getCompany().getVariable().getWt_enabled() == 1) &&
	          (a.getStation().getCompany().getVariable().getWt_write_enabled() == 1) &&
	          (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER, a)) &&
		  (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_WORLD_TRACER_OHD, a))) {
	    %>
		<c:if test="${empty pendingWtAction and empty OnHandForm.wt_id}">
		<%
		if (holdingStation != null && holdingStation.getWt_stationcode() != null && holdingStation.getWt_stationcode().trim().length() > 0) { 
		%>
			&nbsp;&nbsp;&nbsp;&nbsp;
	                <logic:notEqual name="OnHandForm" property="status.status_ID" value="<%="" + TracingConstants.OHD_STATUS_CLOSED%>">
	                  <html:submit property="savetowt" styleId="wtbutton" onclick="isDisposedCheck();  return (validateStatus(this.form) && validatereqOHDForm(this.form));">
	                    <bean:message key="button.savetoWT" />
	                  </html:submit>
	                </logic:notEqual>
	                <% } %>
		</c:if>
		<c:if test="${empty pendingWtAction and !empty OnHandForm.wt_id}">
	            <%
	               if (!a.getStation().getCompany().getVariable().isAuto_wt_amend()) {
	            %>
			&nbsp;&nbsp;&nbsp;&nbsp;
	                <logic:notEqual name="OnHandForm" property="status.status_ID" value="<%="" + TracingConstants.OHD_STATUS_CLOSED%>">
	                  <html:submit property="amendtowt" styleId="wtbutton" onclick="isDisposedCheck();  return (validateStatus(this.form) && validatereqOHDForm(this.form));">
	                    <bean:message key="button.amendWT" />
	                  </html:submit>
	                </logic:notEqual>
			<% } %>
		</c:if>
		<% } %>
    </c:if>    
  </td></tr></table>
    </logic:notEqual>
    <logic:equal name="OnHandForm" property="readonly" value="1">
      <logic:equal name="OnHandForm" property="allow_remark_update"
        value="1">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" valign="top"><br>
            <logic:notEmpty name="OnHandForm" property="status">
              <html:submit property="savetracing" styleId="button"
                onclick="isDisposedCheck();  return (validateStatus(this.form) && validatereqOHDForm(this.form));">
                <bean:message key="button.saveremark" />
              </html:submit>
            </logic:notEmpty></td>
          </tr>
        </table>
      </logic:equal>
    </logic:equal>
</html:form>
<SCRIPT>
    



    if (document.OnHandForm.manufacturer_ID.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
        document.getElementById("manu_other").style.visibility = "visible";

    } else {
        document.getElementById("manu_other").style.visibility = "hidden";
    }




<logic:present name="item" scope="request">
  document.location.href="#centralbag";
</logic:present>

<logic:present name="itinerary" scope="request">
  document.location.href="#itinerary";
</logic:present>

<logic:present name="passenger" scope="request">
  document.location.href="#passengerinfo";
</logic:present>

<logic:present name="remark" scope="request">
  document.location.href="#remarks";
</logic:present>

<logic:present name="upload" scope="request">
  document.location.href="#photos";
</logic:present>

isDisposedCheck();
  </SCRIPT>
</logic:notPresent>

