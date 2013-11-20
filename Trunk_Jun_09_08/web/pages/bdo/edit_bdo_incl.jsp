<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.DeliverCompany" %>
<%@ page import="com.bagnet.nettracer.tracing.db.DeliveryIntegrationType" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<%@page import="com.bagnet.nettracer.tracing.forms.BDOForm"%>
<%@page import="com.bagnet.nettracer.tracing.utils.BDOUtils"%>
<%@page import="com.bagnet.nettracer.tracing.web.StatusListDisp" %>



<%@page import="com.bagnet.nettracer.tracing.utils.StringTemplateProcessor"%>
<%@page import="org.apache.struts.util.MessageResources"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.List"%>

<%@page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO"%>
<%@page import="com.bagnet.nettracer.tracing.db.Passenger"%>
<%@page import="com.bagnet.nettracer.tracing.db.Item"%>
<%@page import="com.bagnet.nettracer.tracing.db.Incident_Claimcheck"%>

<%
Agent a = (Agent)session.getAttribute("user");
BDOForm myform = (BDOForm) session.getAttribute("BDOForm");
String incident_ID = myform.getIncident().getIncident_ID();
boolean isServ=(myform.getDelivercompany_ID()!=0 && (((DeliverCompany)(BDOUtils.getDeliverCompany(myform.getDelivercompany_ID()))).getDelivery_integration_type()==DeliveryIntegrationType.SERV));
boolean bagLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES));
/*
Loss Code at Bag Level Permission Check Reference
	1. I can edit my own incidents that are not closed or delivered and not locked.
	2. I can edit my own incidents that are closed and delivered and not locked (should depend on 1)
	3. I can edit other station's incidents that are not closed or delivered and not locked (should depend on 1)
	4. I can edit other station's incidents that are closed or delivered and not locked (should depend on 3)
	5. I can edit any loss code, regardless of state or status. (admin level)
*/
boolean closed=(myform.getIncident_ID()!=null && !myform.getIncident_ID().isEmpty() && myform.getIncident()!=null && myform.getIncident().getStatus().getStatus_ID()==TracingConstants.MBR_STATUS_CLOSED);
boolean sameStation=(myform.getIncident_ID()!=null && !myform.getIncident_ID().isEmpty() && myform.getIncident()!=null && a.getStation().getStation_ID()==myform.getIncident().getStationcreated().getStation_ID());
boolean swaLocked=(myform.getIncident_ID()!=null && !myform.getIncident_ID().isEmpty() && myform.getIncident()!=null && myform.getIncident().isSwaLocked());
boolean editSameNonClosedPermission=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_SAME_NON_CLOSED_DELIVERED,a);
boolean editOtherNonClosedDeliveredPermission=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_OTHER_NON_CLOSED_DELIVERED,a);

boolean editSameNonClosedDeliveredBags=editSameNonClosedPermission && !closed && sameStation && !swaLocked;
boolean editSameClosedDeliveredBags=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_SAME_CLOSED_DELIVERED,a)  && sameStation && !swaLocked && editSameNonClosedPermission;
boolean editOtherNonClosedDeliveredBags=editOtherNonClosedDeliveredPermission  && !closed && !sameStation && !swaLocked && editSameNonClosedPermission;
boolean editOtherClosedDeliveredBags=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_OTHER_CLOSED_DELIVERED,a) && !sameStation && !swaLocked && editOtherNonClosedDeliveredPermission && editSameNonClosedPermission; 
boolean editAnyBags=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_ANY_BAGS_LOSS_CODES,a);
%>
<%@page import="com.bagnet.nettracer.tracing.db.Address"%><script language="javascript">
<%
boolean lossCodeChange=false;
if(bagLossCodes){
	if(myform.getExistItemList()!=null  && myform.getExistItemList().size()>0 ){
		List<Item> eIlist=myform.getExistItemList();
		List<Item> ilist=myform.getItemlist();
		int i=0;
		for(Item it:ilist){
			if(eIlist.size()>=(i+1)){
				Item incI=eIlist.get(i);
				if(it.getItem_ID()==incI.getItem_ID() && it.getLossCode()!=0 && it.getLossCode()!=incI.getLossCode()){
					lossCodeChange=true;
				}
			}
			i++;
		}
	}
}
%>
var lossCodeChange=<%=lossCodeChange%>;

function toggledc(o) {
	o.changeservice.value = "1";

	document.getElementById("serviceleveldiv").innerHTML = "<br><bean:message key="ajax.please_wait" />";
			postForm("BDOForm", true, function (req) { 
				o.changeservice.value = "0";
				document.getElementById("serviceleveldiv").innerHTML = req.responseText; 
				isServ();
		});
		
	}
  
  	function isServ(){

		var selDC=document.getElementById("delivercompany_ID").value;
  		document.getElementById("deliveryCostDiv").style.display = 'none';
		<% List<DeliverCompany> servlist=BDOUtils.getSERVDeliverCompanies();
		if(servlist!=null){
		for(DeliverCompany dc:servlist){ %>
			if(selDC==<%=dc.getDelivercompany_ID()%>){
				document.getElementById("deliveryCostDiv").style.display = 'inline'; 
			}
		<% }
		} %>
	}
	
  	function textCounter3(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

    function cancelBdo() {
    	var answer = confirm('<bean:message key="bdo.cancel.confirmation" />');
    	if (answer) {
			emailAlertNotice();
        	window.location = 'cancelBdo.do?bdo_id=<bean:write name="BDOForm" property="BDO_ID" />';
    	}
    }
    
    function populateClaimNum(second,first){
    	second.value = first.value;
    }

    function cancelItem(item) {
    	var answer = confirm('<bean:message key="bdo.cancel.item.confirmation" />');
    	if (answer) {
    		emailAlertNotice();
        	window.location = 'cancelBdo.do?bdo_id=<bean:write name="BDOForm" property="BDO_ID" />&item_id=' + item;
    	}
    }
  	
    function samePrevious(index){
  	  if(index>0){
  		  var preLossCode=document.getElementById("theitem["+(index-1)+"].lossCode");
  		  var preFaultStation=document.getElementById("theitem["+(index-1)+"].faultStation_id");
  		  var lossCode=document.getElementById("theitem["+index+"].lossCode");
  		  var faultStation=document.getElementById("theitem["+index+"].faultStation_id");
  		  lossCode.selectedIndex=preLossCode.selectedIndex;
  		  faultStation.selectedIndex=preFaultStation.selectedIndex;
  	  }
    }
    
    function showBDORemark(){
    	if(<%=bagLossCodes%>){
	    	lossCodeChange=true;
	    	document.getElementById("bdoRemark").style.display="block";
    	}
    }

</script>

  <bean:define id="ohd" name="BDOForm" property="ohd" type="com.bagnet.nettracer.tracing.db.OHD"/>
  <tr>
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
          <logic:notEqual name="BDOForm" property="incident_ID" value="">
            <dd>
            <% if((request.getAttribute("mbrtype")!=null && request.getAttribute("mbrtype").equals(TracingConstants.LOST_DELAY+"")) ||
            	(myform.getIncident()!=null && myform.getIncident().getItemtype()!=null && myform.getIncident().getItemtype_ID()==TracingConstants.LOST_DELAY)) {%>
              <a href='lostDelay.do?incident_ID=<bean:write name="BDOForm" property="incident_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
             <% } %>
            <% if((request.getAttribute("mbrtype")!=null && request.getAttribute("mbrtype").equals(TracingConstants.MISSING_ARTICLES+"")) ||
            		(myform.getIncident()!=null && myform.getIncident().getItemtype()!=null && myform.getIncident().getItemtype_ID()==TracingConstants.MISSING_ARTICLES)) { %>
              <a href='missing.do?incident_ID=<bean:write name="BDOForm" property="incident_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
             <% } %>
            <% if((request.getAttribute("mbrtype")!=null && request.getAttribute("mbrtype").equals(TracingConstants.DAMAGED_BAG+"")) ||
            		(myform.getIncident()!=null && myform.getIncident().getItemtype()!=null && myform.getIncident().getItemtype_ID()==TracingConstants.DAMAGED_BAG)) {%>
              <a href='damaged.do?incident_ID=<bean:write name="BDOForm" property="incident_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
             <% } %>
            </dd>
          </logic:notEqual>
          <logic:notEqual name="BDOForm" property="OHD_ID" value="">
            <dd>
              <a href='addOnHandBag.do?ohd_ID=<bean:write name="BDOForm" property="OHD_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.ohd_main" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </logic:notEqual>
          <dd>
            <a href="#"><span class="aab">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bbb"><bean:message key="menu.bdo" /></span>
              <span class="ccb">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
        </dl>
      </div>
    </td>
  </tr>
  
  <tr>
    
    <td id="middlecolumn">
      
	    <div id="maincontent">
		    <span style="float: left">
		    <% if(PropertyBMO.getValue(PropertyBMO.BDSI_ADDRESS_ENDPOINT)!=null) {%>
		    
        
        	<logic:present name="webserviceError" scope="request">
			  	<h1 class="green">
		          <bean:message key="header.bdo_tracking" />
		 		</h1>
	 			<table>
			   		<tr>
			   			<td><bean:message key="no.status.list.webservice.error"/></td>
			   		</tr>
		    	</table>  
        	</logic:present>
		  	<logic:present name="statusList" scope="request">
		  	<h1 class="green">
	          <bean:message key="header.bdo_tracking" />
	 		</h1>
		  		<logic:notEmpty name="statusList" scope="request">
				  	<table class="form2" cellspacing="0" cellpadding="0">
				  		<tr>
				    			<td class="header"><bean:message key="status.bag.list.date"/></td>
				    			<td class="header"><bean:message key="status.bag.list.description"/></td>
				    		</tr>
				    	<logic:iterate id="status" indexId="i" name="statusList" scope="request" type="com.bagnet.nettracer.tracing.web.StatusListDisp">
				    		<tr>
				    			<td><bean:write property="createDateDisp" name="status"/></td>
				    			<td><bean:write property="delStatusDesc2" name="status"/></td>
				    		</tr>
				    	</logic:iterate>
			    	</table>
		    	</logic:notEmpty>
		    	
		  		<logic:empty name="statusList" scope="request">
				  	<table>
				   		<tr>
				   			<td><bean:message key="no.status.list.error"/></td>
				   		</tr>
			    	</table>  
		    	</logic:empty>
		    </logic:present>
		    <span style="float: left">
	    <% } %>
        <h1 class="green">
          <bean:message key="header.bdo_general" />
  </h1>
        <span class="reqfield">*</span>
        <bean:message key="message.required" />

  </span>
        
        <span style="align:right; float:right;">
       
        <logic:present name="wt_id" scope="request">
         
        <% if(request.getAttribute("wt_id")!= null && !request.getAttribute("wt_id").equals("") && !request.getAttribute("wt_id").equals("null"))
        	out.println("WorldTracer ID:");
        	out.println(request.getAttribute("wt_id") + "<br/>");
        %>
        
        
        </logic:present>
		<logic:equal name="BDOForm" property="canceled" value="false">
			<a href="#" onclick="cancelBdo()"><bean:message key="button.cancel.bdo"/></a>
		</logic:equal>        
        </span>
        <font color="red">
          <logic:messagesPresent message="true"><div style="display:block"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></div></logic:messagesPresent>
        </font>
        
        <logic:present name="integrationResponse" scope="request">
          <table border="1" width = "400" align="center">
            <tr>
              <td align="center">
                <bean:write name="integrationResponse" scope="request" filter="false"/>
              </td>
            </tr>
          </table>
        </logic:present>
         
        
        <logic:present name="inserted" scope="request">
          <br>
          <center><font color=green>
            <bean:message key="prompt.bdo_insert_success" />
          </font></center>
        </logic:present>
        <br>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
          	<td nowrap>
              <bean:message key="colname.bdo_id_nobr" />
              <br>
              <html:text property="BDO_ID_ref" size="20" styleClass="textfield" readonly="true" disabled="true"/>
            </td>
          	<td nowrap>
              <bean:message key="colname.date" />
              <br>
              <html:text property="dispcreatetime" size="20" styleClass="textfield" readonly="true" />
            </td>

            <td nowrap>
              <bean:message key="colname.station" />
              <br>
              <html:text property="station.stationcode" size="2" styleClass="textfield" readonly="true" />
            </td>
            <td nowrap>
              <bean:message key="colname.airline" />
              <br>
              <html:text property="companycode_ID" size="2" styleClass="textfield" readonly="true" />
            </td>
            <td nowrap>
              <bean:message key="colname.agent" />
              <br>
              <html:text property="agentinit" size="10" styleClass="textfield" readonly="true" />
            </td>
          </tr>

          <logic:notEmpty property="deliveryStatus" name="BDOForm"> 
          <tr>
          	<td colspan="1">
          		<bean:message key="label.bdo.currentDeliveryStatus" />: 
          		<br />
          		<input type="text" class="textfield" readonly="readonly" size="20" value='<bean:message name="BDOForm" property="deliveryStatusKey" />' />
          		
          	</td>
          	<td colspan="1">
          		<bean:message key="label.bdo.lastDeliveryUpdateDate" />: 
          		<br />
          		<html:text property="dispLastDeliveryUpdate" size="20" styleClass="textfield" readonly="true" />
          	</td>
          	<td colspan="3">
          		&nbsp;
          	</td>
          </tr>
          </logic:notEmpty>
          
          <tr>
            <td nowrap>
              <bean:message key="colname.delivercompany_nobr" />
              <input type="hidden" name="changeservice" value="">
              <br>
              <html:select property="delivercompany_ID" styleClass="dropdown" onchange="toggledc(this.form);">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="delivercompanies" property="delivercompany.delivercompany_ID" labelProperty="delivercompany.name" />
              </html:select>
            </td>
            <td nowrap>
              <bean:message key="colname.servicelevel_nobr" />
              <br>
              <div id="serviceleveldiv">
              <html:select property="servicelevel_ID" styleClass="dropdown">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="servicelevels" property="servicelevel_ID" labelProperty="description" />
              </html:select>
              </div>
            </td>
            <td nowrap>
              <bean:message key="colname.deliverydate.req" />
              (<%= a.getDateformat().getFormat() %>)
              <br />
              <html:text property="dispdeliverydate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.BDOForm.dispdeliverydate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
            </td>
            <td nowrap colspan="2">
              <bean:message key="colname.pickupdatetime" />
              (<%= a.getDateformat().getFormat() %>) &nbsp;( <%=a.getTimeformat().getFormat()%>)
              <br />
              <html:text property="disppickupdate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.BDOForm.disppickupdate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
              &nbsp;
              <html:text property="disppickuptime" size="6" maxlength="10" styleClass="textfield" />
             </td>
          </tr>
          
          <tr>
            <td colspan="5">
              <bean:message key="colname.bdo.cost" />
              <br>
                <html:select property="currency" styleClass="dropdown">
                  <html:option value=""><bean:message key="select.please_select" /></html:option>
                  <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                </html:select>&nbsp;
                <html:text property="cost" size="6" maxlength="10" styleClass="textfield" />
                <br>
                
	              <bean:message key="original.delivery.cost"/>:
	              <bean:write name="BDOForm" property="dispOrigDelivCost" />
            </td>          
          </tr>
          
          <tr>
            <td colspan="5">
              <bean:message key="colname.delivery.remarks" />
              <br />
              <html:textarea rows="7" cols="80" property="delivery_comments" styleClass="textarea_medium" onkeydown="textCounter3(this,textCounter2,300);" onkeyup="textCounter3(this,textCounter2, 300);"/>
              <input name="textCounter2" type="text" value="300" size="4" maxlength="4" disabled="true" />
            </td>
          </tr>
         </table>
        <a name="contact"></a>
        <h1 class="green">
          <bean:message key="header.passenger_info" />
  	</h1>
	<br/>

<script type="text/javascript">
	var passengerArr = ["passenger[0].lastname", "passenger[0].firstname", "passenger[0].middlename"];
	var addressArr = ["passenger[0].address1", "passenger[0].address2", "passenger[0].city", "passenger[0].state_ID", "passenger[0].province","passenger[0].zip","passenger[0].countrycode_ID","passenger[0].dispvalid_bdate", "passenger[0].dispvalid_edate","passenger[0].","passenger[0].email"];
	var phoneArr = ["passenger[0].homephone", "passenger[0].workphone", "passenger[0].altphone", "passenger[0].mobile", "passenger[0].pager", "passenger[0].hotel", "passenger[0].hotelphone"];
	var itemArr = ["item[0].claimchecknum"];
	
    var passengers = [<%=myform.getPassengerlist().size() %>];
    var addresses = [<%=myform.getPassengerlist().size() %>];
    var phonenumbers = [<%=myform.getPassengerlist().size() %>];
    <% if(myform.getIncident()!=null && myform.getIncident().getClaimchecks()!=null){ %>
    	var items = [<%=myform.getIncident().getClaimchecks().size() %>];
    <% } else { %>
   		var items = [0];
    <% } %>

    function emailAlertNotice() {
        <% if (PropertyBMO.isTrue(PropertyBMO.PROPERTY_BDO_CANCEL_EMAIL_ALERT)) {
            if (myform != null && myform.getIncident() != null && myform.getIncident().getPassenger_list() != null ) { 
            	if (myform.getIncident().getPassenger_list().size() > 0 && myform.getIncident().getPassenger_list().get(0) != null) {
            		Passenger p = (Passenger) myform.getIncident().getPassenger_list().get(0);
            		if (p.getAddresses() != null && p.getAddresses().size() > 0) {
            			Address address = p.getAddress(0);
            			if (address != null && address.getEmail() != null && address.getEmail().length() > 0) {
            				%>
            				alert('<bean:message key="bdo.cancel.email.notice" />');			
            				<%
            			}
            		}
            	}
            }	
        }
        %>

    }

    function mapSimpleData(index, dataSet, arr) {
	    if (index == "") { return; }
	    var data = dataSet[index-1].data;
	    for (var i in arr) {
		var element = document.getElementsByName(arr[i])[0];
		if (element != null && element.type != null) {
			if (element.type == 'text') {
				value = data[arr[i]];
				if (value == null) { element.value = '';} else {
					element.value = data[arr[i]]; 
				}
			} else if (element.type == 'select-one') {
				for (var x=0; x< element.length; x++) {
					if(element[x].value == data[arr[i]]) {
						element[x].selected = true;
					}
				}
			}
		}
	    }
    }
	
	function mapData(item, arr) {
	    var data = item.data("source").data;
	    for (var i in arr) {
		var element = document.getElementsByName(arr[i])[0];
		if (element != null && element.type != null) {
			if (element.type == 'text') {
				value = data[arr[i]];
				if (value == null) { element.value = '';} else {
					element.value = data[arr[i]]; 
				}
			} else if (element.type == 'select-one') {
				for (var x=0; x< element.length; x++) {
					if(element[x].value == data[arr[i]]) {
						element[x].selected = true;
					}
				}
			}
		}
	    }
    }


    jQuery(document).ready(function() {

        function DropdownData() {
            this.shortd = "";
	    this.longd = "";
	    this.data = {};
	    }


	<logic:iterate id="passenger" name="BDOForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">

		<%
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		Locale locale = new Locale(a.getCurrentlocale());
		
		StringTemplateProcessor p = new StringTemplateProcessor();
		p.addClass(passenger);
		String paxNames = p.fillValues("{firstname} {lastname}");
		String paxData = p.fillValues("'passenger[0].firstname': \"{firstname}\", 'passenger[0].lastname': \"{lastname}\"");
		
		
		String addShort = messages.getMessage(locale, "gen.passenger") + " " + i + " " + messages.getMessage(locale, "gen.address");
		String addLong = p.fillValues("{firstname} {lastname} <br />{address1} {address2}<br/>{city}, {state_ID}{province} {zip}<br />{countrycode_ID}");
		String addData = p.fillValues("'passenger[0].address1': \"{address1}\", 'passenger[0].address2': \"{address2}\", 'passenger[0].city': \"{city}\", 'passenger[0].state_ID': '{state_ID}', 'passenger[0].province': \"{province}\",'passenger[0].zip': \"{zip}\",'passenger[0].countrycode_ID': '{countrycode_ID}','passenger[0].dispvalid_bdate': '{dispvalid_bdate}', 'passenger[0].dispvalid_edate': '{dispvalid_edate}','passenger[0].email':'{email}'");
		
		String phoneShort = messages.getMessage(locale, "gen.passenger") + " " + i + " " + messages.getMessage(locale, "gen.phone");
		String phoneLong = p.fillValues("Name: {firstname} {lastname}<br />Home: {homephone}<br/> Work: {workphone}<br/>Others: {altphone}{mobile} {pager}<br/> Hotel: {hotel} {hotelphone} ");
		String phoneData = p.fillValues("'passenger[0].homephone': \"{homephone}\", 'passenger[0].workphone': \"{workphone}\", 'passenger[0].altphone': \"{altphone}\", 'passenger[0].mobile': \"{mobile}\", 'passenger[0].pager': \"{pager}\", 'passenger[0].hotel':\"{hotel}\", 'passenger[0].hotelphone':\"{hotelphone}\"");
		%>

		var pax = new DropdownData();
		pax.shortd = "<%=paxNames %>";
		pax.longd = "<%=paxNames %>";
		pax.data={<%=paxData%>};
		passengers[<%=i%>] = pax;

        var address = new DropdownData();
	    address.shortd = "<%=addShort%>";
		address.longd = "<%=addLong%>";
		address.data={<%=addData%>};
		addresses[<%=i%>] = address;

        var phone = new DropdownData();
	    phone.shortd = "<%=phoneShort%>";
		phone.longd = "<%=phoneLong%>";
		phone.data={<%=phoneData%>};
	    phonenumbers[<%=i%>] = phone;
	</logic:iterate>
	
	<logic:notEmpty name="BDOForm" property="incident.claimchecks">

	<logic:iterate id="item" name="BDOForm" property="incident.claimchecks" indexId="i" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">

		<%
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		Locale locale = new Locale(a.getCurrentlocale());
		
		StringTemplateProcessor p = new StringTemplateProcessor();
		p.addClass(item);
		String bagNums= p.fillValues("{claimchecknum}");
		String bagData = p.fillValues("'item[0].claimchecknum': '{claimchecknum}'");
		
		%>
	
		var bag = new DropdownData();
		bag.shortd = "<%=bagNums%>";
		bag.longd = "<%=bagNums%>";
		bag.data={<%=bagData%>};
		items[<%=i%>] = bag;

	</logic:iterate>
	</logic:notEmpty>




    });
</script>

<select style="margin-right: 10px" onChange="mapSimpleData(this.options.selectedIndex, passengers, passengerArr); "  class="dropdown">
	<option value=""><bean:message key="pick.a.passenger" /></option>
	<logic:iterate id="passenger" name="BDOForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
	<%
		StringTemplateProcessor p = new StringTemplateProcessor();
		p.addClass(passenger);
		String paxNames = p.fillValues("{firstname} {lastname}");
		%>
		<option value="<%=i%>"><%=paxNames%></option>
	</logic:iterate>
</select>
<select style="margin-right: 10px" onChange="mapSimpleData(this.options.selectedIndex, addresses, addressArr); "  class="dropdown">
	<option value=""><bean:message key="pick.a.address" /></option>
	<logic:iterate id="passenger" name="BDOForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
	<%
		StringTemplateProcessor p = new StringTemplateProcessor();
		p.addClass(passenger);
		String addLong = p.fillValues("{address1} {address2}, {city}, {state_ID}{province} {zip}  {countrycode_ID}");
	%>
		<option value="<%=i%>"><%=addLong%></option>
	</logic:iterate>
</select>
<select style="margin-right: 10px" onChange="mapSimpleData(this.options.selectedIndex, phonenumbers, phoneArr); "  class="dropdown">
	<option value=""><bean:message key="pick.a.phonenumber" /></option>
	<logic:iterate id="passenger" name="BDOForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
	<%
		StringTemplateProcessor p = new StringTemplateProcessor();
		p.addClass(passenger);
		String phoneLong = p.fillValues("Home: {homephone}, Work: {workphone}, Others: {altphone} {mobile} {pager}, Hotel: {hotel} {hotelphone}");
	%>
		<option value="<%=i%>"><%=phoneLong%></option>
	</logic:iterate>
</select>


        <logic:iterate id="passenger" name="BDOForm" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan=5>
<%
                if (i.intValue() > 0) {
%>
                  <b><bean:message key="colname.addi_pass_info" />
                  :
<%
                } else {
%>
                  <b><bean:message key="colname.pri_pass_info" />
                  :
<%
                }
%>
              </td>
            </tr>
            <tr>
              <td nowrap colspan=2>
                <bean:message key="colname.last_name" />
                <br>
                <html:text name="passenger" property="lastname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
              </td>
              <td nowrap colspan=2>
                <bean:message key="colname.first_name" />
                <br>
                <html:text name="passenger" property="firstname" size="20" maxlength="20" indexed="true" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.mid_initial" />
                <br>
                <html:text name="passenger" property="middlename" size="1" maxlength="1" indexed="true" styleClass="textfield" />
              </td>
            </tr>
<%
if (i.intValue() == 0) {

%>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.street_addr1" />
                  <br>
                  <html:text name="passenger" property="address1" size="45" maxlength="50" indexed="true" styleClass="textfield" />
                </td>
                <td colspan=3>
                  <bean:message key="colname.street_addr2" />
                  <br>
                  <html:text name="passenger" property="address2" size="45" maxlength="50" indexed="true" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  <br>
                  <html:text name="passenger" property="city" indexed="true" size="15" maxlength="50" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.state" />
                  <br>
                  <html:select name="passenger" property="state_ID" indexed="true" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="statelist" property="value" labelProperty="label" />
                  </html:select>
                </td>
                <td>
                  <bean:message key="colname.province" />
                  <br>
                  <html:text name="passenger" property="province" indexed="true" size="15" maxlength="100" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.zip" />
                  <br>
                  <html:text name="passenger" property="zip" indexed="true" size="15" maxlength="11" styleClass="textfield" />
                </td>
                <td>
                  <bean:message key="colname.country" />
                  <br>
                  <html:select name="passenger" property="countrycode_ID" indexed="true" styleClass="dropdown">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td colspan=2>
                  <bean:message key="colname.valid_bdate" />
                  (<%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="passenger" property="dispvalid_bdate" indexed="true" size="20" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar1<%= i %>" name="calendar1<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.BDOForm, '<%= "passenger[" + i + "].dispvalid_bdate" %>','calendar1<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                <td colspan=3>
                  <bean:message key="colname.valid_edate" />
                  (<%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="passenger" property="dispvalid_edate" indexed="true" size="20" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= i %>" name="calendar2<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.BDOForm, '<%= "passenger[" + i + "].dispvalid_edate" %>','calendar2<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
<%
            }
%>
            <tr>
              <td>
                <bean:message key="colname.home_ph" />
                <br>
                <html:text name="passenger" property="homephone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.business_ph" />
                <br>
                <html:text name="passenger" property="workphone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.mobile_ph" />
                <br>
                <html:text name="passenger" property="mobile" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.pager_ph" />
                <br>
                <html:text name="passenger" property="pager" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td>
                <bean:message key="colname.alt_ph" />
                <br>
                <html:text name="passenger" property="altphone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
            </tr>
            <tr>
              <td colspan=2>
                <bean:message key="colname.hotel" />
                <br>
                <html:text name="passenger" property="hotel" indexed="true" size="45" maxlength="50" styleClass="textfield" indexed="true" />
              </td> 
              <td >
                <bean:message key="colname.hotel_ph" />
                <br>
                <html:text name="passenger" property="hotelphone" indexed="true" size="15" maxlength="25" styleClass="textfield" />
              </td>
              <td colspan=2>
                <bean:message key="colname.email" />
                <br>
                <html:text name="passenger" property="email" indexed="true" size="45" maxlength="50" styleClass="textfield" />
              </td>
            </tr>
          </table>
        </logic:iterate>
        <br>
        <br>
        &nbsp;&nbsp;&uarr;
        <a href="#"><bean:message key="link.to_top" /></a>
        <br>
        <br>
        <a name="baginfo"></a>
        <h1 class="green">
          <bean:message key="header.bag_info" />
          (
          <bean:message key="colname.bagcount_nobr" />
          :
          <logic:equal name="BDOForm" property="incident_ID" value="">
            <logic:notEqual name="BDOForm" property="OHD_ID" value="">
              1
            </logic:notEqual>
          </logic:equal>
          <logic:notEqual name="BDOForm" property="incident_ID" value="">
            <logic:equal name="BDOForm" property="choosebags" value="1">
              0
            </logic:equal>
            <logic:notEqual name="BDOForm" property="choosebags" value="1">
              <bean:write name="BDOForm" property="bagcount" />
            </logic:notEqual>
          </logic:notEqual>
          )
        </h1>
        <logic:equal name="BDOForm" property="choosebags" value="1">
          (
          <bean:message key="bdo.choose_bag_desc" />
          )
          <br>
          <br>
        </logic:equal>
        <logic:equal name="BDOForm" property="incident_ID" value="">
          <logic:notEqual name="BDOForm" property="OHD_ID" value="">
          
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td valign=top>
                <b><bean:message key="colname.bag_number" />
                : &nbsp;&nbsp;1
              </td>
              <td valign=top colspan=1>
                <bean:message key="colname.bag_status" />
                <br>
                <input type="text" name = "ohd.status.description" value="<bean:message name="ohd" property="status.key" />" size="25" maxlength="25" styleClass="textfield" readonly="true"/>
              </td>
              <td valign=top colspan=1>
                <bean:message key="colname.claimnum" />
                <br>
                <html:text property = "ohd.claimnum" size="25" maxlength="25" styleClass="textfield" readonly="true"/>
              </td>
            </tr>
            <tr>
              <td>
                <bean:message key="colname.last_name_onbag" />
                <br>
                <html:text property="ohd.lastname" size="30" maxlength="50" styleClass="textfield" readonly="true" />
              </td>
              <td>
                <bean:message key="colname.first_name_onbag" />
                <br>
                <html:text property="ohd.firstname" size="30" maxlength="50" styleClass="textfield" readonly="true" />
              </td>
              <td>
                <bean:message key="colname.mid_initial_onbag" />
                <br>
                <html:text property="ohd.middlename" size="1" maxlength="1" styleClass="textfield" readonly="true" />
              </td>
            </tr>
            <tr>
              <td nowrap>
                <bean:message key="colname.color" />
                /
                <bean:message key="colname.bagtype" />
                <br>
                <html:text property="ohd.color" size="2" styleClass="textfield" readonly="true" />
                /
                <html:text property="ohd.type" size="2" styleClass="textfield" readonly="true" />
              </td>
              <td nowrap>
                <bean:message key="colname.noAddFees" /><br/>
                <input type="checkbox" name="ohd.noAddFees" <% if(ohd.isNoAddFees()) {%> checked="true" <%} %>/>
              </td>
              <td>
                <bean:message key="colname.other" />
                <br>
			  <html:select property="ohd.other" styleClass="dropdown" >
                <html:option value="0">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="bdoCategoryList" property="id" labelProperty="description" />
              </html:select>

              </td>
            </tr>
            
            <tr>
              <td colspan=3>
             	<bean:message key="colname.special.conditions" />
				<br>
				<html:select property="ohd.specialCondition" styleClass="dropdown"  >
					<html:option value="0"><bean:message key="select.please_select" /></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_OVERWEIGHT) %>"><bean:message key="option.overweight" /></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_OVERSIZED) %>"><bean:message key="option.oversized" /></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_BOTH) %>"><bean:message key="option.both" /></html:option> 
				</html:select>
              </td>
            </tr>
          </table>
          
        </logic:notEqual>
      </logic:equal>
      <logic:notEqual name="BDOForm" property="incident_ID" value="">
      
		<logic:iterate id="theitem" indexId="i" name="BDOForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
          <table class="form2" cellspacing="0" cellpadding="0">
            <logic:notEqual name="theitem" property="OHD_ID" value="">
              <logic:equal name="theitem" property="is_in_station" value="0">
                <tr>
                  <td valign=top colspan=3>
                    <b>(Note: This lost/delayed bag has been matched to on-hand bag
                    <bean:write name="theitem" property="OHD_ID" />
                    . However, this on-hand bag is currently not in the lost/delayed assigned station. Please
                    <A HREF='request_on_hand.do?ohd_ID=<bean:write name="theitem" property="OHD_ID"/>'>click here to request the bag</a>
                    .)
                  </td>
                </tr>
              </logic:equal>
              <logic:equal name="theitem" property="is_in_station" value="1">
                <tr>
                  <td valign=top colspan=3>
                    <b>(Note: This lost/delayed bag has been matched to on-hand bag
                    <bean:write name="theitem" property="OHD_ID" />
                    .)
                  </td>
                </tr>
              </logic:equal>
            </logic:notEqual>
            <tr>
              <td valign=top>
                <b>
                <logic:equal name="BDOForm" property="choosebags" value="1">
                
                <input type="checkbox" name="bagchosen" id="bagchosen<%= theitem.getBagnumber() %>" value="<%= theitem.getBagnumber() %>">
              </logic:equal>
              <bean:message key="colname.bag_number" />
              : &nbsp;&nbsp;
              <%= theitem.getBagnumber() + 1 %>
              <logic:notEqual name="BDOForm" property="choosebags" value="1">
              	<logic:equal name="BDOForm" property="canceled" value="false">
              		<% if (theitem != null && theitem.getBdo() != null &&  theitem.getBdo().getBDO_ID() == myform.getBDO_ID() && (theitem.getStatus().getStatus_ID() == TracingConstants.ITEM_STATUS_PROCESSFORDELIVERY || theitem.getStatus().getStatus_ID() == TracingConstants.ITEM_STATUS_TOBEDELIVERED)) { %>
              			<br /><a href="#" onclick="cancelItem(<%=theitem.getItem_ID() %>)"><bean:message key="button.cancel.item"/></a>
              		<% } %>
              	</logic:equal>
              </logic:notEqual>
            </td>
            <td valign=top>
              <bean:message key="colname.bag_status" />
              <br>
              <input type="text" name = "theitem[<%=i %>].status.description" value="<bean:message name="theitem" property="status.key" />" size="25" maxlength="25" class="textfield" readonly="true"/>
            </td>
            <td valign=top>
              <bean:message key="colname.claimnum" />
              <br>
              <html:text name="theitem" property="claimchecknum" size="25" styleId="<%="bagtagId"+i %>" styleClass="textfield" indexed="true"/>
              <select class="dropdown" id="bagtagSelect<%=i %>" onchange="populateClaimNum(bagtagId<%=i %>,bagtagSelect<%=i %>); mapSimpleData(this.options.selectedIndex, items, itemArr); ">
				<option value=""><bean:message key="pick.a.bag" /></option>
				<logic:iterate id="item" name="BDOForm"  property="incident.claimchecks" indexId="i" type="com.bagnet.nettracer.tracing.db.Incident_Claimcheck">
					<%
					StringTemplateProcessor p = new StringTemplateProcessor();
					p.addClass(item);
					String bagNums = p.fillValues("{claimchecknum}");
					%>
					<option value="<%=bagNums.toString()%>"><%=bagNums%></option>
				</logic:iterate>
			</html>
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key="colname.last_name_onbag" />
              <br>
              <html:text name="theitem" property="lnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
            </td>
            <td>
              <bean:message key="colname.first_name_onbag" />
              <br>
              <html:text name="theitem" property="fnameonbag" size="30" maxlength="50" styleClass="textfield" indexed="true" readonly="true" />
            </td>
            <td>
              <bean:message key="colname.mid_initial_onbag" />
              <br>
              <html:text name="theitem" property="mnameonbag" size="1" maxlength="1" styleClass="textfield" indexed="true" readonly="true" />
            </td>
          </tr>
          <tr>
              <td nowrap>
              <bean:message key="colname.color" />
              /
              <bean:message key="colname.bagtype" />
              <br>
              <html:text name="theitem" property="color" indexed="true" size="2" styleClass="textfield" readonly="true" />
              /
              <html:text name="theitem" property="bagtype" indexed="true" size="2" styleClass="textfield" readonly="true" />
            </td>
              <td nowrap>
                <bean:message key="colname.noAddFees" /><br/>
                <input type="checkbox" name="theitem[<%=i %>].noAddFees" <% if(theitem.isNoAddFees()) {%> checked="true" <%} %>/>
              </td>
              <td>
                <bean:message key="colname.other" />
                <br>
				  <html:select name="theitem" property="other" styleClass="dropdown" indexed="true" >
	                <html:option value="0">
	                  <bean:message key="select.please_select" />
	                </html:option>
	                <html:options collection="bdoCategoryList" property="id" labelProperty="description" />
	              </html:select>
              </td>
            </tr>
            
            <tr>
              <td colspan=3>
             	<bean:message key="colname.special.conditions" />
				<br>
				<html:select name="theitem" property="specialCondition" styleClass="dropdown" indexed="true" >
					<html:option value="0"><bean:message key="select.please_select" /></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_OVERWEIGHT) %>"><bean:message key="option.overweight" /></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_OVERSIZED) %>"><bean:message key="option.oversized" /></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_BOTH) %>"><bean:message key="option.both" /></html:option> 
				</html:select>
              </td>
            </tr>
            
            <% if(bagLossCodes){

          	  boolean editLossCode=((editSameNonClosedDeliveredBags && theitem.isNotDelivered())
  	    				|| (editSameClosedDeliveredBags && (closed || !theitem.isNotDelivered()) )
  	    				|| (editOtherNonClosedDeliveredBags && theitem.isNotDelivered())
  	    				|| (editOtherClosedDeliveredBags && (closed || !theitem.isNotDelivered()))
  	    				|| editAnyBags);%>
          <% if(!editLossCode){ %> 
	          
		        <html:hidden property="lossCode" value="<%=String.valueOf(theitem.getLossCode()) %>"  name="theitem" indexed="true"/>
		        
		        <html:hidden property="faultStation_id" value="<%=String.valueOf(theitem.getFaultStation_id()) %>" name="theitem"  indexed="true"/>
	          <%} %>
              <tr>
              	<td colspan="2">
            		<bean:message key="colname.loss.code" />
	            	<br>
	            	<html:select name="theitem" property="lossCode" styleClass="dropdown" indexed="true" onchange="showBDORemark();" disabled="<%=!editLossCode %>" >      
			          <html:option value="0">
			            <bean:message key="select.please_select" />
			          </html:option>
			           <html:options collection="losscodes" property="loss_code" labelProperty="codeDescription" />
		            </html:select>
	          	</td>
	          	<td>
	            	<bean:message key="colname.fault.station" />
	            	<br>
			        <html:select name="theitem" property="faultStation_id" styleClass="dropdown" indexed="true" disabled="<%=!editLossCode %>">  
			          <html:option value="">
			            <bean:message key="select.please_select" />
			          </html:option>
			          <html:options collection="faultstationlist" property="station_ID" labelProperty="stationcode" />
			        </html:select>
	          	</td>
              </tr>
              <tr>
              <% if(i!=0){ %>
              	<td align="center" style="vertical-align:middle;">&nbsp;<br>
	          		
	            		<input type="button" name="sameAsPrevious_<%=i%>" value="<bean:message key="button.same_previous"/>" onclick="samePrevious(<%=i %>);" id="button">
	            	
	          	</td>
	          	<td colspan="2" style="vertical-align:middle;">&nbsp;<br></td>
	          	<% } %>
              </tr>
              
              <% } %>
        </table>
      </logic:iterate>
      <% if(bagLossCodes){ %>
      <div id="bdoremark" style="display:none">
	  <h1 class="green">
	    <bean:message key="header.bdo.remarks" />
	      </h1>
		<table  class="form2" cellspacing="0" cellpadding="0">
        	<tr >
              <td>
                <bean:message key="colname.station" />
                :
                <logic:present name="BDOForm" property="agent">
   
	                <bean:write name="BDOForm" property="agent.companycode_ID" />
	                &nbsp;
	                <bean:write name="BDOForm" property="agent.station.stationcode" />
	              </logic:present>
              </td>
              <td>
                <bean:message key="colname.agent" />
                :
                <logic:present name="BDOForm" property="agent">
                	<bean:write name="BDOForm" property="agent.username" />
                </logic:present>
              </td>
              <% boolean securePermission=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a);  
              if (securePermission) { %>
              <td>
                <bean:message key="colname.secure" />
                :
                <input type="checkbox" name="secure" 
                      <logic:equal name="BDOForm" property="secure" value="true">
                        checked="checked"
                      </logic:equal> />
              </td><% } %>
            </tr>
            <tr >
            <td valign="top" colspan="<%=securePermission?3:2%>">

				<!-- onkeydown="textCounter2(<= remarkText %>, <= remarkText2 %>,1500);insertNewLine('<=remarkId %>');" onkeyup="textCounter2(<= remarkText %>, <= remarkText2 %>,1500);" -->
                  <html:textarea name="BDOForm" property="remark" cols="80" rows="10" />
                  <!-- <input name="<= remarkDescription + "2" %>" id="<=remarkId + ".counter" %>" type="text" value="1500" size="4" maxlength="4" disabled="true" />-->
                  <br>
            	</td>
            </tr>
         </table>
         </div>
      <% } %>
    </logic:notEqual>
    <br>
    <br>
    &nbsp;&nbsp;&uarr;
    <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center" valign="top">
        <br>
        <logic:equal name="BDOForm" property="canceled" value="true">
        	<bean:message key="bdo.cannot.save.due.to.cancel" />
        </logic:equal>
        <logic:equal name="BDOForm" property="canceled" value="false">
        <html:hidden property="save" value = ""/>
        <html:button property="saveButton" styleId="button" onclick="changebutton(); if(validateReqBDO(document.BDOForm)) {clearBeforeUnload(); document.BDOForm.submit();} else {undoChangebutton();}">
          <bean:message key="button.bdo_send" />
        </html:button>
        </logic:equal>
        <logic:present name="showprint" scope="request">
        &nbsp;&nbsp;
        <input id="button" type="button" name="print" value="<bean:message key="button.bdo_sendprint" />" onclick="openReportWindow('bdo.do?receipt=1&toprint=<%=ReportingConstants.BDO_RECEIPT_RPT%>&bdo_id=<bean:write name="BDOForm" property="BDO_ID" />','BDOReceipt',800,600);return false;">
        </logic:present>
        <div id="deliveryCostDiv" style="display:none">
        <html:hidden property="requestDelivCost" value="" disabled="true" />
        	<input type="submit" name="requestDelivCost" value='<bean:message key="button.requestDelivCost"/>' id="button">
        </div>
      </td>
    </tr>
  </table>
	<script>
	<% if(lossCodeChange){ %>
		showBDORemark();
	<% }%>
		isServ();
	</script>

