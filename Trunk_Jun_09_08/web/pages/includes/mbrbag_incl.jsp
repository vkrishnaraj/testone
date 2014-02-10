
<%@page import="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory"%>
<%@page import="com.bagnet.nettracer.tracing.db.issuance.IssuanceItem"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Item" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="java.util.List" %>
<%
	
  Agent a = (Agent)session.getAttribute("user");

  IncidentForm myform = (IncidentForm) session.getAttribute("incidentForm");
  boolean collectPosId = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, a);
  boolean collectExpTagNum = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EXPEDITE_TAG_NUM_COLLECT, a);
  boolean collectAddItemInfo = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADDITIONAL_ITEM_INFORMATION_COLLECT, a);
  
  /*
    Loss Code at Bag Level Permission Check Reference
  	1. I can edit my own incidents that are not closed or delivered and not locked.
 	2. I can edit my own incidents that are closed and delivered and not locked (should depend on 1)
	3. I can edit other station's incidents that are not closed or delivered and not locked (should depend on 1)
	4. I can edit other station's incidents that are closed or delivered and not locked (should depend on 3)
	5. I can edit any loss code, regardless of state or status. (admin level)
  */
  boolean closed=myform.getExistIncStatus()!=null && myform.getExistIncStatus().getStatus_ID()==TracingConstants.MBR_STATUS_CLOSED;
  boolean sameStation=a.getStation().getStation_ID()==myform.getStationcreated().getStation_ID();
  boolean swaLocked=myform.isSwaLocked();
  boolean editSameNonClosedPermission=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_SAME_NON_CLOSED_DELIVERED,a);
  boolean editOtherNonClosedDeliveredPermission=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_OTHER_NON_CLOSED_DELIVERED,a);
  
  boolean editSameNonClosedDeliveredBags=editSameNonClosedPermission && !closed && sameStation && !swaLocked;
  boolean editSameClosedDeliveredBags=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_SAME_CLOSED_DELIVERED,a)  && sameStation && !swaLocked && editSameNonClosedPermission;
  boolean editOtherNonClosedDeliveredBags=editOtherNonClosedDeliveredPermission  && !closed && !sameStation && !swaLocked && editSameNonClosedPermission;
  boolean editOtherClosedDeliveredBags=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_OTHER_CLOSED_DELIVERED,a) && !sameStation && !swaLocked && editOtherNonClosedDeliveredPermission && editSameNonClosedPermission; 
  boolean editAnyBags=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_ANY_BAGS_LOSS_CODES,a);
  
  boolean passengerPickUp=false;
  String inctype="";

	if(request.getAttribute("lostdelay")!=null){
		passengerPickUp=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_LOSTDELAY, a);
		inctype=TracingConstants.LD_REQ;
	} else if (request.getAttribute("missing")!=null){
		passengerPickUp=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_MISSING, a);
		inctype=TracingConstants.MA_REQ;
	} else {
		passengerPickUp=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_PASSENGER_PICK_UP_DAMAGE, a);
		inctype=TracingConstants.DM_REQ;
	}
  
  String cssFormClass;
 
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
	
	boolean canAddContents=false;
	if(request.getAttribute("lostdelay")!=null){
		canAddContents=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_LD, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, a);
	} else if (request.getAttribute("missing")!=null){
		canAddContents=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_MS, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, a);
	} else {
		canAddContents=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_REMARK_UPDATE_DA, a) && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ADD_NEW_CONTENTS, a);
	}
	
	boolean canIssueItems=false;
	if(request.getAttribute("lostdelay")!=null){
		canIssueItems=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_LOSTDELAY, a);
	} else if (request.getAttribute("missing")!=null){
		canIssueItems=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_MISSING, a);
	} else {
		canIssueItems=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_DAMAGE, a);
	}
	
	   
%>

  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
  var lossCodeChange=false;
  var cal1xx = new CalendarPopup();	
  var ppucheck=-1;
  var codeStationMap=new Object();
  <% List losscodes=(List)request.getAttribute("losscodes");
  Company_specific_irregularity_code code=null;
  for(Object obj:losscodes){
	  code=(Company_specific_irregularity_code)obj;%>
	  codeStationMap["<%=code.getLoss_code()%>depart"]=<%=code.isDepartStation()%>;
	  codeStationMap["<%=code.getLoss_code()%>transfer"]=<%=code.isTransferStation()%>;
	  codeStationMap["<%=code.getLoss_code()%>destination"]=<%=code.isDestinationStation()%>;
	  codeStationMap["<%=code.getLoss_code()%>any"]=<%=code.isAnyStation()%>;
  <%}%>
  function textCounter3(field, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } 
  }
  
  function lossCodeChanged(index){
	  <% if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES)){ %>
		  var disValue=document.getElementById("theitem["+index+"].lossCode");
		  if(disValue!=null && disValue.value!="0"){
			  var lastCode=0;
			  <%if(myform.getExistItemlist() !=null && myform.getExistItemlist().size()>0){
	  			int i=0;
	  			for(Item it:myform.getExistItemlist()){%>
	  				if(index==<%=i%>){
	  					lastCode=<%=it.getLossCode()%>
	  				}
		  		<%i++;
				}
			  %>
	
			  var remText=document.getElementById("remark["+(<%=myform.getRemarklist().size()-1%>)+"].remarktext");
	  		  if(disValue.value!=lastCode && (<%=myform.getRemarklist().size()<=myform.getExistRemarkSize()%> 
	  			|| (remText!=null && remText.value!=null && remText.value.replace(/\s*/g, "").length==0 )))
	  				lossCodeChange=true;
			  <%} else {%>
				    var remText=document.getElementById("remark[0].remarktext");
				  	if(remText!=null && remText.value!=null && remText.value.replace(/\s*/g, "").length==0){
				  		lossCodeChange=true;
				  	}
	 		  <%}%>	
		  }
	 <%}%>
  }
  
  function anyLossCodeChanges(){

	  lossCodeChange=false;
	  <% if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES)){
	  	for(int i=0;i<myform.getItemlist().size();i++){ %>
	  	lossCodeChanged(<%=i%>);
	  <% }
	  }%>
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
  
  function checkBagType(pos) {
		var btype=document.getElementById("bagtype"+pos);
		var disableBag=<%=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_DISABLED_BAG, a)%>
		if(btype.value=="71"){
			document.getElementById("childRestraint"+pos).style.display = "block";
			document.getElementById("disableBag"+pos).style.display="none";
			if(disableBag){
				document.getElementById("deviceType"+pos).value="0";
				document.getElementById("deviceCheck"+pos).value="";
			}
		} else if(btype.value=="94" || btype.value=="95"){
			document.getElementById("childRestraint"+pos).style.display = "none";
			document.getElementById("disableBag"+pos).style.display="block";	
		}	else {
			document.getElementById("childRestraint"+pos).style.display = "none";
			document.getElementById("disableBag"+pos).style.display="none";
			if(disableBag){
				document.getElementById("deviceType"+pos).value="0";
				document.getElementById("deviceCheck"+pos).value="";
			}
		}
		
	}
  
  function openPreviewWindow1(fileName) {
	  window.open("issuanceItemAdmin.do?preview_document="+fileName+"&receipt=1", '', 'width=600,height=800,resizable=yes');
	}
	
</SCRIPT>

  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	

	function showmanu(o) {
	
  	var pos = o.name.indexOf("[");
		var pos2 = o.name.indexOf("]");
		pos = o.name.substring(pos+1,pos2);

		if (o.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
			document.getElementById("manu_other" + pos).style.visibility = "visible";
		} else {
			document.getElementById("manu_other" + pos).style.visibility = "hidden";
		}
	}
	

  </SCRIPT>
  
<%
  int report_type = 0;

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
    }
  }
%>
  
  <a name="baginfo"></a>
  <h1 class="green">
<%
    if (report_type == 0) {
%>
      <bean:message key="header.damaged_bag_info" />
      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/work_damaged_bag_information.htm');return false;"><%
      } else if (report_type == 1) {
%>
        <bean:message key="header.bag_info" />
        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_lost_delayed_reports.htm#report info fields');return false;"><%
        } else if (report_type == 2) {
%>
          <bean:message key="header.bag_info" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_baggage_information_(ma).htm#top');return false;"><%
          }
%>
          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" />
      <logic:iterate id="theitem" indexId="i" name="incidentForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
        <div id="<%=TracingConstants.JSP_DELETE_ITEM %>_<%=i%>">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="top">
              <b><a name='additem<%= i %>'></a>
              <bean:message key="colname.bag_number" />
              : &nbsp;&nbsp;
              <%= theitem.getBagnumber() + 1 %></b>
            </td>
<%
            if (report_type != 1) {
%>
              <td <% if (report_type == 2 || !collectExpTagNum) { %>colspan="2"<% } %>>
                <bean:message key="colname.claimnum.req2" />
                <br>
                <html:text name="theitem" property="claimchecknum" size="13" maxlength="12" styleClass="textfield" indexed="true" />
              </td>
              
              <% if (report_type == 0 && collectExpTagNum) { %>
              	<td>
              		<bean:message key="colname.expedite.tagnum" />
              		<br>
              		<html:text name="theitem" property="expediteTagNum" size="13" maxlength="12" styleClass="textfield" indexed="true" />
              	</td>
              <% } %>
              
<%	
            } else {
%>
              <logic:notEqual name="incidentForm" property="incident_ID" value="">
                <td>
                  <bean:message key="colname.matched_claimcheck" />
                  <br>
                  <bean:write name="theitem" property="claimchecknum" />
                  &nbsp;
                </td>
                <td>
                  <bean:message key="colname.matched_ohd" />
                  <br>
                  <logic:notEmpty name="theitem" property="OHD_ID">
                    <a class="matchlink" href='addOnHandBag.do?ohd_ID=<bean:write name="theitem" property="OHD_ID"/>'><bean:write name="theitem" property="OHD_ID" /></a>
                    &nbsp;
                    <logic:notPresent name="cantmatch" scope="request">
                      <input type="submit" name="unmatchbag<%= i %>" value='<bean:message key="button.un_match"/>' id="button">
                    </logic:notPresent>
                  </logic:notEmpty>
                  <logic:empty name="theitem" property="OHD_ID">
                    <logic:notPresent name="cantmatch" scope="request">
                      <html:text name="theitem" property="tempOHD_ID" size="13" maxlength="13" styleClass="textfield" indexed="true" onblur="fillzero(this,13);" />
                      <input type="submit" name="matchbag<%= i %>" value='<bean:message key="button.do_match"/>' id="button" onclick="fillzero(document.incidentForm.tempOHD_ID, 13); return true;">
                    </logic:notPresent>
                  </logic:empty>
                </td>
              </logic:notEqual>
              <logic:equal name="incidentForm" property="incident_ID" value="">
                <td colspan="2">
                  &nbsp;
                </td>
              </logic:equal>
<%
            }
%>
          </tr>
          <tr>
            <td <% if (!collectPosId) { %>colspan=3<% } %>>
            	<% 
            	if (report_type == 0) { 
            	%>
            	<bean:message key="colname.damaged_bag_status" />
            	<% 
            	} else { 
            	%>
              <bean:message key="colname.bag_status" />
              <% 
              } 
              %>
              <br>
		      <input type="text" name = "theitem[<%=i %>].status.description" value="<bean:message name="theitem" property="status.key" />" size="25" maxlength="25" class="textfield" readonly="true"/>

              &nbsp;&nbsp;&nbsp;&nbsp;
              <logic:present name="theitem" property="bdo">
              
              <bean:message key="header.bdo" />: <a href="bdo.do?bdo_id=<bean:write name="theitem" property="bdo.BDO_ID" />"><bean:write name="theitem" property="bdo.BDO_ID_ref" /></a>
              <% if (theitem.isBdoEntryCanceled()) { %>
              	(<bean:message key="bdo.canceled"/>)
              <% } %>
              </logic:present>
              
			  <% if(passengerPickUp) { %>
			      
			  	  <logic:present name="incidentForm" property="incident_ID"> 
			  	  	<logic:notEqual name="incidentForm" property="status.status_ID" value="<%=String.valueOf(TracingConstants.MBR_STATUS_CLOSED) %>">
		          
		              <logic:notEqual name="theitem" property="status.status_ID" value="<%=String.valueOf(TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP) %>">
			              <br/>
			              <% long bdocount=theitem.countBdos(); 
			              	if(bdocount==0){ %>
			              <input type="submit" name="passengerpickedup<%= i %>" value="<bean:message key="passenger.picked.up"/>" id="button" onclick="ppucheck=<%=i %>; if(validatereqFields(this.form, '<%=inctype %>') != false){ window.alert('<bean:message key="info.check.claimcheck.pos.id"/>'); return true;} else { return false;}"> 
			              <% } else if(bdocount>0) {%>
			              <bean:message key="cannot.passenger.picked.up"/>
			              <% } else if(bdocount==-1) {%>
			              <bean:message key="bdo.count.error"/>
			              <% } %>
		              </logic:notEqual>
		              <logic:equal name="theitem" property="status.status_ID" value="<%=String.valueOf(TracingConstants.ITEM_STATUS_PASSENGER_PICKED_UP) %>">
		              	  <br/>
			              <input type="submit" name="passengerpickedup<%= i %>" value="<bean:message key="ppu.reopen"/>" id="button"> 
		              </logic:equal>
		            </logic:notEqual>
	              </logic:present>
              <% } %>
              		 
            </td>
            <% if (collectPosId) { %>
	            <td colspan=2>
	            	<bean:message key="colname.posId" />
	            	<br>
	            	<html:text name="theitem" property="posId" size="8" maxlength="8" styleClass="textfield" indexed="true" />
	            </td>
            <% } %>
          </tr>
          <% if((UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES))){ 
				
        	  boolean editLossCode=((editSameNonClosedDeliveredBags && theitem.isNotDelivered())
	    				|| (editSameClosedDeliveredBags && (closed || !theitem.isNotDelivered()) )
	    				|| (editOtherNonClosedDeliveredBags && theitem.isNotDelivered())
	    				|| (editOtherClosedDeliveredBags && (closed || !theitem.isNotDelivered()))
	    				|| editAnyBags);%>
          <tr>
          	<td >
	            	<bean:message key="colname.loss.code" />
	            	<br>
	            	<% String isLossCodeString="lossCodeChanged("+i+")"; %>
	            	<html:select name="theitem" property="lossCode" styleClass="dropdown" indexed="true"  disabled="<%=!editLossCode %>" onchange="<%=isLossCodeString %>" >      
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
          	<td align="center" style="vertical-align:middle;">&nbsp;<br>
          		<% if(i!=0){ %>
            		<input type="button" name="sameAsPrevious_<%=i%>" value="<bean:message key="button.same_previous"/>" onclick="samePrevious(<%=i %>);" id="button">
            	<% } %>
          	</td>
          </tr>
          <% if(!editLossCode){ %> 
          
	        <html:hidden property="lossCode" value="<%=String.valueOf(theitem.getLossCode()) %>"  name="theitem" indexed="true"/>
	        
	        <html:hidden property="faultStation_id" value="<%=String.valueOf(theitem.getFaultStation_id()) %>" name="theitem"  indexed="true"/>
          <%} %>
          <% } %>
          <tr>
            <td>
              <bean:message key="colname.last_name_onbag" />
              <br>
              <html:text name="theitem" property="lnameonbag" styleId="<%="theitem["+i+"].lnameonbag"%>" size="30" maxlength="50" styleClass="textfield" indexed="true" />
            </td>
            <td>
              <bean:message key="colname.first_name_onbag" />
              <br>
              <html:text name="theitem" property="fnameonbag" styleId="<%="theitem["+i+"].fnameonbag"%>" size="30" maxlength="50" styleClass="textfield" indexed="true" />
            </td>
            <td>
              <bean:message key="colname.mid_initial_onbag" />
              <br>
              <html:text name="theitem" property="mnameonbag" styleId="<%="theitem["+i+"].mnameonbag"%>" size="1" maxlength="1" styleClass="textfield" indexed="true" />
            </td>
          </tr>
          <tr>
            <td valign="top">
              <bean:message key="colname.color.req" />
            
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=3&key=theitem[<%= i %>].color&type=color',800,10,230);return false;"><bean:message key="chart3" /></a>
              <br>
              <html:select name="theitem" property="color" indexed="true" styleClass="dropdown">
                <html:options collection="colorlist" property="value" labelProperty="label" />
              </html:select>
            
              <br>
              <bean:message key="colname.bagtype.req" />
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=1&key=theitem[<%= i %>].bagtype&type=bagtype',800,280,230);return false;"><bean:message key="chart1" /></a>
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=2&key=theitem[<%= i %>].bagtype&type=bagtype',800,370,230);return false;"><bean:message key="chart2" /></a>
              <br>
              <% String bagpos="bagtype"+i;	
              	String funcCall = "checkBagType(" + i + ")"; %> 
			  <html:select name="theitem" styleId="<%=bagpos%>"	property="bagtype" styleClass="dropdown" indexed="true"	onchange="<%=funcCall%>">
						  <html:options collection="typelist" property="value" labelProperty="label" />
              </html:select>
              <div id="childRestraint<%=i %>" style="display:none">
              	<% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_CHILD_RESTRAINT_SYSTEM, a) && theitem.getItemtype_ID()!=2){ %>
              	<bean:message key="colname.child.restraint.system" /><br>
              	 	<html:select name="theitem" property="childRestraint" styleClass="dropdown" indexed="true">
						<html:option value="0"><bean:message key="child.restraint.0"/></html:option>
						<html:option value="1"><bean:message key="child.restraint.1"/></html:option>
						<html:option value="2"><bean:message key="child.restraint.2"/></html:option>
			        </html:select>
			        <% } %>
              </div>
              <div id="disableBag<%=i %>" style="display:none">
              <% if(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_DISABLED_BAG, a)){ %>
              		<% String devType="deviceType"+i;
              			String devTypeCheck="deviceCheck"+i;
              		%>
              		<bean:message key="colname.assist.device.type" /><br>
              	 	<html:select name="theitem" property="assistDeviceType" styleId="<%=devType %>" styleClass="dropdown" indexed="true">
						<html:option value="0">
		                  <bean:message key="select.please_select" />
		                </html:option>
		                <html:options collection="assistDeviceList" property="id" labelProperty="description" />
			        </html:select><br/>
			        <br/>
			        <bean:message key="colname.assist.device.inspection.check" /><br>
              	 	<html:select name="theitem" property="assistDeviceCheck" styleId="<%=devTypeCheck %>"  styleClass="dropdown" indexed="true">
						<html:option value=""><bean:message key="select.please_select"/></html:option>
						<html:option value="Yes"><bean:message key="select.yes"/></html:option>
						<html:option value="No"><bean:message key="select.no"/></html:option>
			        </html:select><br/>
			        <br/>
		      <% } %>
              </div>
            </td>
            <td valign="top">
              <bean:message key="colname.x_desc" />
              <br>
            
              <html:select name="theitem" property="xdescelement_ID_1" styleClass="dropdown" indexed="true">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
              </html:select>
                <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&xdescelement=theitem[<%= i %>].xdescelement_ID_1&type=xdescelement_ID_1',800,30,230);return false;"><bean:message key="chart4" /></a>
              <br>
              <html:select name="theitem" property="xdescelement_ID_2" styleClass="dropdown" indexed="true">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
              </html:select>
                <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&xdescelement=theitem[<%= i %>].xdescelement_ID_2&type=xdescelement_ID_2',800,30,230);return false;"><bean:message key="chart4" /></a>
              <br>
              <html:select name="theitem" property="xdescelement_ID_3" styleClass="dropdown" indexed="true">
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="xdescelementlist" property="XDesc_ID" labelProperty="description" />
              </html:select>
              <a href="#" onclick="openChart2('pages/popups/bagtypechart.jsp?charttype=4&xdescelement=theitem[<%= i %>].xdescelement_ID_3&type=xdescelement_ID_3',800,30,230);return false;"><bean:message key="chart4" /></a>
            </td>
            <td valign="top">
              <bean:message key="colname.manufacturer.req" />
              <br>
              <html:select name="theitem" property="manufacturer_ID" styleClass="dropdown" indexed="true" onchange='showmanu(this);return true;'>
                <html:option value="">
                  <bean:message key="select.please_select" />
                </html:option>
                <html:options collection="manufacturerlist" property="manufacturer_ID" labelProperty="description" />
              </html:select>
              <div id="manu_other<%= i %>">
                <br>
                <html:text name="theitem" property="manufacturer_other" size="25" maxlength="100" styleClass="textfield" indexed="true" />
              </div>
            </td>
          </tr>
          <!-- provide space for bag weight feature - start -->
	      <%
				boolean val = UserPermissions.hasPermission(
							TracingConstants.SYSTEM_COMPONENT_NAME_BAGGAGE_WEIGHT,
							a);
					if (val || theitem.getItemtype_ID() == 1) {
			%>
			<tr id="bag_weight">
			<% if (val){ %>
				<td <% if (theitem.getItemtype_ID() != 1 && !UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SPECIAL_CONDITIONS, a)) { %>colspan="3"<% } %>>
					<bean:message key="colname.bag.weight.and.units" /><br>
					<html:text name="theitem" property="bag_weight" size="8"
						maxlength="10" styleClass="textfield" indexed="true" /> <html:select
						name="theitem" property="bag_weight_unit" styleClass="dropdown"
						indexed="true">
						<html:option value="lbs">lbs</html:option>
						<html:option value="kg">kg</html:option>
					</html:select>
				</td>					
			<% }
			
			   if (report_type == 0 && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SPECIAL_CONDITIONS, a)) { %>
				<td <% if (theitem.getItemtype_ID() != 1) { %>colspan="2"<% } %>>
					<bean:message key="colname.special.conditions" />
					<br>
					<html:select name="theitem" property="specialCondition" styleClass="dropdown" indexed="true" >
						<html:option value="0"><bean:message key="select.please_select" /></html:option> 
						<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_OVERWEIGHT) %>"><bean:message key="option.overweight" /></html:option> 
						<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_OVERSIZED) %>"><bean:message key="option.oversized" /></html:option> 
						<html:option value="<%=String.valueOf(TracingConstants.SPECIAL_CONDITION_BOTH) %>"><bean:message key="option.both" /></html:option> 
					</html:select>
				</td>	   
			<% } 
			
			   if (theitem.getItemtype_ID() == 1) {
			%>
				<td <% if (!UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SPECIAL_CONDITIONS, a)) { %>colspan="2"<% } %>><bean:message key="colname.bag.external.desc" /><br>
					<html:text name="theitem" property="externaldesc" maxlength="50"
						size="75" styleClass="textfield" indexed="true" />
				</td>
			<%
				} else {
			%>
				<td colspan="2"></td>
			<%
				}
			%>
			</tr>
			<%
				}
			%>
          <!-- provide space for bag weight feature - end -->         
          <tr>
            <td colspan="3">

              <bean:message key="colname.key_contents" />
							<a name='<%= "inventory" + i %>'></a>
              <bean:define id="inventories" name="theitem" property="inventorylist" />
              <logic:iterate id="inventorylist" indexId="j" name="inventories" type="com.bagnet.nettracer.tracing.db.Item_Inventory">
	            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" width="100%" id="<%=TracingConstants.JSP_DELETE_INVENTORY %>_<%= i %>_<%= j %>">
	            <% if (report_type == 0 && collectAddItemInfo) { %>
	            	<tr>
	            		<td>
		            		<bean:message key="colname.entered.date" />
		            		<br>
		            		<html:text property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].dispEnteredDate"%>' disabled="true" styleClass="textfield" />
	            		</td> 
	            		<td>
		            		<bean:message key="colname.purchase.date" />
		            		<br>
		            		<html:text property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].dispPurchaseDate"%>' styleClass="textfield" />&nbsp;
		            		<img
								src="deployment/main/images/calendar/calendar_icon.gif"
								id="itcalendar<%='_'+i+'_'+j %>" name="itcalendar<%='_'+i+'_'+j %>" height="15"
								width="20" border="0" onmouseover="this.style.cursor='hand'"
								onClick="cal1xx.select2(document.incidentForm, '<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].dispPurchaseDate"%>','itcalendar<%='_'+i+'_'+j %>','<%= a.getDateformat().getFormat() %>'); return false;">
	            		</td>
	            		<td>
			                <bean:message key="colname.cost" />
			                <br>
			                <html:text property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].dispInvItemCost"%>' size="13" maxlength="12" styleClass="textfield" />
		                </td>
		                <td>
			                <bean:message key="colname.currency" />
			                <br>
			                <html:select property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].invItemCurrency"%>' styleClass="dropdown" >
			                  <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
			                </html:select>
		                </td>
		                <td>
			              	<bean:message key="colname.item.status" />
			              	<br>
			              	<html:select property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].itemStatusId"%>' styleClass="dropdown" >
			              		<html:option value="0">
									<bean:message key="select.please_select" />
								</html:option>
			              		<html:options collection="damagedItemStatusList" property="status_ID" labelProperty="description" />
			              	</html:select>
			            </td> 
			            <td align="center" rowspan="2" style="vertical-align:middle;">&nbsp;<br>
	                      <% 
	                      String check = "true";
	                      if (report_type !=2) {
	                        check = "checkDeleteCount(" + i + ", " + report_type + ")";
	                      }
	                      if (UserPermissions.hasIncidentSavePermission(a, theitem.getIncident())) { %>
							<input type="button" name="deleteinventory_<%=i%>" value="<bean:message key="button.delete_content"/>" onclick="if (<%=check%>) {hideThisElement('<%=TracingConstants.JSP_DELETE_INVENTORY%>_<%=i%>_<%=j%>', '<bean:message key="colname.lc.content" />', 0);}" id="button"> 
						  <%	} %>
		                </td>
	            	</tr>
            	<% } %>
	              <tr>
	                <td>
	                  <% if(request.getAttribute("lostdelay")!=null){ %>
	                  	<bean:message key="colname.category.req" />
	                  <% } else { %>
	                  	<bean:message key="colname.category" />
	                  <% } %>
	                  
	                  <br>
	                  <%	if (UserPermissions.hasIncidentSavePermission(a, theitem.getIncident())	|| (canAddContents && inventorylist.getInventory_ID() == 0)) { %>
							<html:select property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].categorytype_ID"%>'	styleClass="dropdown">
								<html:option value="">
									<bean:message key="select.please_select" />
								</html:option>
								<html:options collection="categorylist"
									property="OHD_CategoryType_ID" labelProperty="description" />
							</html:select> 
						<% 	} else { %> 
							<html:select property='<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].categorytype_ID"%>'	disabled="true" styleClass="dropdown">
								<html:option value="">
									<bean:message key="select.please_select" />
								</html:option>
								<html:options collection="categorylist"
									property="OHD_CategoryType_ID" labelProperty="description" />
							</html:select> <%} %>
	                </td>
	                <td colspan="4">
	                  <% if (report_type == 1) { %>
	                  <bean:message key="colname.ld.description.req" />
	                  <% } else if (report_type == 2) { %>
	                  <bean:message key="colname.pil.description" />
	                  <% } else {%>
	                  <bean:message key="colname.dam.description" />
	                  <% }%>
	                  <br> 
						<% if (UserPermissions.hasIncidentSavePermission(a,theitem.getIncident()) || (canAddContents && inventorylist.getInventory_ID() == 0)) { %>
							<html:text property="<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].description"%>" styleId="<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].description"%>" size="80" maxlength="255" styleClass="textfield" /> 
						<%	} else { %>
							<html:text disabled="true" property="<%="inventorylist[" + (i.intValue() * 20 + j.intValue()) + "].description"%>" styleId="<%="inventorylist["+ (i.intValue() * 20 + j.intValue())+ "].description"%>" size="80" maxlength="255" styleClass="textfield" /> 
						<% } %>
	                </td>
	                <% if (!collectAddItemInfo || report_type != 0) { %>
		            <td align="center">&nbsp;<br>
	                    <% 
	                    String check = "true";
	                    if (report_type !=2) {
	                      check = "checkDeleteCount(" + i + ", " + report_type + ")";
	                    }
	                    if (UserPermissions.hasIncidentSavePermission(a, theitem.getIncident())) { %>
						  <input type="button" name="deleteinventory_<%=i%>" value="<bean:message key="button.delete_content"/>" onclick="if (<%=check%>) {hideThisElement('<%=TracingConstants.JSP_DELETE_INVENTORY%>_<%=i%>_<%=j%>', '<bean:message key="colname.lc.content" />', 0);}" id="button"> 
						<%	} %>
	                </td>
	                <% } %>
	              </tr>
	            </table>
                
	          	</logic:iterate>
	  
		          <center>

                  <select name="addNumInventory[<%=i %>]">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                  </select>
                  <html:submit styleId="button" property="addinventory" indexed="true" >
		            <bean:message key="button.add_content" />
		          </html:submit>
		          
				</center>

            </td>
          </tr>
<%
          if (report_type == 0) {
%>
            <tr>
              <td>
                <bean:message key="colname.damagedesc.req" />
                <br>
                <html:text name="theitem" property="damage" size="30" maxlength="255" styleClass="textfield" indexed="true" />
              </td>
              <td>
                <bean:message key="colname.lvldamage" />
                <br>
                <html:select name="theitem" property="lvlofdamage" styleClass="dropdown" indexed="true">
                  <html:option value="">
                    <bean:message key="select.please_select" />
                  </html:option>
                  <html:option value="<%=String.valueOf(TracingConstants.DAMAGE_MINOR) %>">
                    <bean:message key="select.minor" />
                  </html:option>
                  <html:option value="<%=String.valueOf(TracingConstants.DAMAGE_MAJOR) %>">
                    <bean:message key="select.major" />
                  </html:option>
                  <html:option value="<%=String.valueOf(TracingConstants.DAMAGE_COMPLETE) %>">
                    <bean:message key="select.complete" />
                  </html:option>
                </html:select>
              	<% if (request.getAttribute("damaged") != null && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUE_REPLACEMENT_BAGS, a)) { %>
	          		<br />
         			<bean:message key="replacement.bag.issued" /><br/>
         			<html:select name="theitem" property="replacementBagIssued" styleId="replacementBagIssued" styleClass="dropdown" indexed="true" >
						<html:option value="-1"><bean:message key="select.please_select" /></html:option>
						<html:option value="<%=String.valueOf(TracingConstants.YES) %>"><bean:message key="select.yes" /></html:option>
						<html:option value="<%=String.valueOf(TracingConstants.NO) %>"><bean:message key="select.no" /></html:option>
					</html:select>
         		<% } %>
              </td>
              <td>
                <bean:message key="colname.cost" />
                <br>
                <html:text name="theitem" property="discost" size="13" maxlength="12" styleClass="textfield" indexed="true" />
                <br>
                <bean:message key="colname.currency" />
                <br>
                <html:select name="theitem" property="currency_ID" styleClass="dropdown" indexed="true">
                  <html:options collection="currencylist" property="currency_ID" labelProperty="id_desc" />
                </html:select>
              </td>
            </tr>
            <tr>
              <td colspan="3">
              
              

                <bean:message key="colname.resolutiondesc" />
                <br>
                <html:textarea name="theitem" property="resolutiondesc" cols="80" rows="5" styleClass="textarea_medium" indexed="true"  onkeydown="textCounter3(this,255);" onkeyup="textCounter3(this,255);"/>
              </td>
            </tr>
<%
          }
            if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_DAMAGED_PHOTOS, a)) {
%>
              <tr>
                <td colspan="3">
                  <bean:message key="header.photos" />
                  :
                  <br>
                  <a name='<%= "upload" + i %>'></a>
                  <table width="100%" border="0" cellspacing="0" cellpadding="0">
<%
                    int k = 0;
%>
                    <logic:present name="theitem" property="photolist">
                      <bean:define id="photos" name="theitem" property="photolist" />
                      <logic:iterate id="photolist" indexId="j" name="photos" type="com.bagnet.nettracer.tracing.db.Item_Photo">
<%
                        if (k % 3 == 0) {
%>
                          <tr align="center">
<%
                          }
%>
                          <td align="center">
                            <a href='showImage?ID=<bean:write name="photolist" property="picpath"/>' target="top"><img src='showImage?ID=<bean:write name="photolist" property="thumbpath"/>'></a>
                            <br>
                            <a href='showImage?ID=<bean:write name="photolist" property="picpath"/>' target="top"><bean:write name="photolist" property="fileName"/></a>
                            <br>
                            <input type="submit" name="removePhoto_<%= i %>_<%= j %>" id="button" value="<bean:message key="button.delete_photo"/>">
                          </td>
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
                <center><input type="FILE" name='<%= "imagefile" + i %>' />
                &nbsp;
                <html:submit property="uploadPhoto" indexed="true" styleId="button">
                  <bean:message key="header.addPhotos" />
                </html:submit></center>
              </td>
            </tr>
<%
          }

%>
        <tr>
          <td colspan="3">
            <input type="button" value="<bean:message key="button.delete_item" />" 
            onclick="hideThisDiv('<%=TracingConstants.JSP_DELETE_ITEM %>_<%=i%>', 
            '<bean:message key="header.bag" />')" id="button">
          </td>
        </tr>
      </table>
      </div>
    </logic:iterate>
    <center>
            <select name="additemNum">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
        </select>
    
    <html:submit property="additem" styleId="button">
      <bean:message key="button.add_bag" />
    </html:submit></center>
    <script language="JavaScript">
      


 for (var j=0;j<document.incidentForm.length;j++) {
    currentElement = document.incidentForm.elements[j];
    currentElementName=currentElement.name;
    
    if (currentElementName.indexOf("manufacturer_ID") != -1)
    {
    	var pos = currentElementName.indexOf("[");
			var pos2 = currentElementName.indexOf("]");
  		pos = currentElementName.substring(pos+1,pos2);
			if (currentElement.value == <%= TracingConstants.MANUFACTURER_OTHER_ID %>) {
				document.getElementById("manu_other" + pos).style.visibility = "visible";
  
      } else {
				document.getElementById("manu_other" + pos).style.visibility = "hidden";
			}
    }
    
	}

	

    </script>
<% if (canIssueItems) { %>
    <br>
    <br>
        <script language="JavaScript">

        var issItemType = "0";
        var validQuantity = "0";
        var numPass = "<%=myform.getPassengerlist().size() %>";
        var limitByPass = "false";

    	function populateType() {
    		var catList=document.getElementById("issuance_category");
    		var typeList=document.getElementById("issuance_type");
    		var issueAddTR3=document.getElementById("issuanceAdd3");
    		var issueAddTR4=document.getElementById("issuanceAdd4");
    		var selectedCategory=catList.options[catList.selectedIndex].value;
    		if (selectedCategory=="0") {
        		typeList.options.length=0;
				typeList.options[0]=new Option("No Category","0",false,false);
        		typeList.disabled=true;
	    		issueAddTR3.innerHTML="&nbsp;";
	    		issueAddTR4.innerHTML="&nbsp;";
        		return;
        	}
    		typeList.options.length=0;
    		typeList.disabled=false;
     		<logic:iterate indexId="i" id="c_item" name="item_category_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory" >
    		if("<%=c_item.getId()%>"==selectedCategory)
    			{	
    				<%  boolean isInventoried = c_item.isInventory();
    				    int index = 0;
    					if (isInventoried) { %>
    					    issItemType = "1";
    						typeList.options[0]=new Option("Please Select","0",false,false);
    						<% index++; %>
    						<logic:iterate indexId="j" id="i_item" name="item_inventory_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
    						<% if (c_item.getId() == i_item.getIssuanceItem().getCategory().getId()) { 
    								String customDesc = i_item.getIssuanceItem().getDescription() + " - " + i_item.getDescription() + " (" + i_item.getBarcode() + ")";
    								customDesc = customDesc.replaceAll("\"", "\\\\\""); 
    								customDesc = customDesc.replaceAll("/", "\\\\/"); %>
    							typeList.options[<%=index%>]=new Option("<%=customDesc%>","<%=i_item.getId()%>",false,false);
    						<% index++; } %>
    						</logic:iterate>
				    		issueAddTR3.innerHTML="&nbsp;";
				    		issueAddTR4.innerHTML="&nbsp;";
    				<%  } else { %>
    						issItemType = "0";
							<logic:iterate indexId="j" id="q_item" name="item_quantity_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity" >
							<% if (c_item.getId() == q_item.getIssuanceItem().getCategory().getId()) { 
									String customDesc = q_item.getIssuanceItem().getDescription() + " (" + q_item.getQuantity() + " Available)"; 
    								customDesc = customDesc.replaceAll("\"", "\\\\\"");
    								customDesc = customDesc.replaceAll("/", "\\\\/");
									if (index == 0) { %> 
										validQuantity = <%=q_item.getQuantity() %>;
										limitByPass = <%=q_item.getIssuanceItem().getCategory().isLimitByPassenger() %>;
									<% }%>
								typeList.options[<%=index%>]=new Option("<%=customDesc%>","<%=q_item.getId()%>",false,false);
							<% index++; } %>
							</logic:iterate>
				    		issueAddTR3.innerHTML="<bean:message key='issuance.item.quantity.issued' />" + "<br/><input type='text' name='issuance_quantity' id='issuance_quantity' class='textfield' value='1' />";
				    		issueAddTR4.innerHTML="<br/><input type='submit' name='issueItem' id='button' onclick='return validateIssuanceQuantity();' value='"+"<bean:message key='issuance.item.button.issue' />"+"' >";
    				<%  } %>
    			}
    		</logic:iterate>
    	}

    	function populateInventoryOptions() {
    		var typeList=document.getElementById("issuance_type");
    		var selectedType=typeList.options[typeList.selectedIndex].value;
        	if ("1"==issItemType) {
				var issueAddTR3=document.getElementById("issuanceAdd3");
	    		var issueAddTR4=document.getElementById("issuanceAdd4");
		    	issueAddTR3.innerHTML="&nbsp;";
		    	issueAddTR4.innerHTML="&nbsp;";
				<logic:iterate indexId="i" id="i_item" name="item_inventory_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
	    			if("<%=i_item.getId()%>"==selectedType) {
						<% boolean canLoan = (i_item.getTradeType() == TracingConstants.ISSUANCE_ITEM_INVENTORY_TYPE_BOTH || i_item.getTradeType() == TracingConstants.ISSUANCE_ITEM_INVENTORY_TYPE_LOAN_ONLY); %>
						<% boolean canTradeout = (i_item.getTradeType() == TracingConstants.ISSUANCE_ITEM_INVENTORY_TYPE_BOTH || i_item.getTradeType() == TracingConstants.ISSUANCE_ITEM_INVENTORY_TYPE_TRADEOUT_ONLY); %>
						<% if (canLoan) { %>
				    		issueAddTR3.innerHTML="<br/><input type='submit' name='loanItem' id='button' value='"+"<bean:message key='issuance.item.button.loan' />"+"' >";
						<% }
						   if (canTradeout) { %>
				    		issueAddTR4.innerHTML="<br/><input type='submit' name='tradeItem' id='button' value='"+"<bean:message key='issuance.item.button.tradeout' />"+"' >";
						<% } %>
	    			}
				</logic:iterate>
        	} else {
				<logic:iterate indexId="i" id="q_item" name="item_quantity_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity" >
    				if("<%=q_item.getId()%>"==selectedType) {
    					validQuantity = <%=q_item.getQuantity() %>;
						limitByPass = <%=q_item.getIssuanceItem().getCategory().isLimitByPassenger() %>;
    				}
				</logic:iterate>
            }
    	}

    	function validateIssuanceQuantity() {
			var quantField = document.getElementById("issuance_quantity");
			var testQuantity = validQuantity;
			if (limitByPass && numPass < validQuantity) {
				testQuantity = numPass;
			}
			if (quantField.value > testQuantity) {
				alert("<bean:message key='issuance.item.quantity.issued' />" + " value must be less than or equal to " + testQuantity);
				quantField.focus();
				return false;	
			}
			return true;
        }
    	
        </script>
  <a name="addissuanceitem"></a>
  <h1 class="green">
      <bean:message key="header.issue_items" />
      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;">
          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
      </h1>
      <span class="reqfield">*</span>
      <bean:message key="message.required" />
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
        <html:hidden property="returnissuanceitem" value="" disabled="true"/>
          <tr>
          	<td><b><bean:message key="issuance.item.category" /></b></td>
          	<td><b><bean:message key="issuance.item.type" /></b></td>
          	<td><b><bean:message key="issuance.item.description" /></b></td>
          	<td><b><bean:message key="issuance.item.barcode" /></b></td>
          	<td><b><bean:message key="issuance.item.quantity.issued" /></b></td>
          	<td><b><bean:message key="issuance.item.edit.agent" /></b></td>
          	<td><b><bean:message key="issuance.item.edit.date" /></b></td>
          	<td><b><bean:message key="issuance.item.button.return" /></b></td>
          	<td><b><bean:message key="colname.action" /></b></td>          	
          </tr>
      	<logic:iterate id="issuanceitem" indexId="iiIndex" name="incidentForm" property="issuanceItemIncidents" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemIncident">
      	<% boolean quantified = issuanceitem.getIssuanceItemQuantity() != null; %>
      	<% IssuanceItem iItem; 
      	if (quantified) { 
      		iItem = issuanceitem.getIssuanceItemQuantity().getIssuanceItem();
      	} else {
      		iItem = issuanceitem.getIssuanceItemInventory().getIssuanceItem();
      	}%>
          <tr>
          	<td><%=iItem.getCategory().getDescription() %>&nbsp;</td>
          	<td><%=iItem.getDescription() %>&nbsp;</td>
          	<td><% if (!quantified) { %><%=issuanceitem.getIssuanceItemInventory().getDescription() %><% } %>&nbsp;</td>
          	<td><% if (!quantified) { %><%=issuanceitem.getIssuanceItemInventory().getBarcode() %><% } %>&nbsp;</td>
            <td><% if (quantified) { %><%=issuanceitem.getQuantity() %><% } %>&nbsp;</td>
          	<td><%=issuanceitem.getIssueAgent().getUsername() %>&nbsp;</td>
          	<td><%=TracingConstants.getDisplayDate(issuanceitem.getIssueDate(), a) %>&nbsp;</td>
          		<% if (issuanceitem.isReturned()) { %>
          			<td><bean:message key="issuance.item.returned" /></td>
          	    <% } else { %>
          	    	<td align="center"><input type="submit" name="issuance_edit_<%=iiIndex%>" id="button" onclick="this.form.returnissuanceitem.value = <%=iiIndex%>; this.form.returnissuanceitem.disabled = false;" 
						value="<bean:message key="issuance.item.button.return" />" >
					</input></td>
				<% } %>
						<td style="text-align:left;">
							<logic:notEmpty name="issuanceitem" property="document">
							<a href="#" onclick="openPreviewWindow1('<bean:write name="issuanceitem" property="document.fileName" />')"> 
																<bean:message key="link.preview" /></a>
							</logic:notEmpty>
							<logic:empty name="issuanceitem" property="document">
							&nbsp;
							</logic:empty>
						</td>				
          </tr>
          </logic:iterate>
          <tr>
          	<td colspan="9" align="center"><b><bean:message key="issuance.item.issue" /></b></td>
          </tr>
          <tr>
          	<td colspan="2"><bean:message key="issuance.item.category" /><br/>
	               <select id="issuance_category" name="issuance_category" class="dropdown" onchange="populateType()">
	               	  <option value="0">Please Select</option>
	                  <logic:iterate indexId="i" id="c_item" name="item_category_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory" >
	                     <option value="<%=c_item.getId() %>" >
	                     	<%=c_item.getDescription() %>
	                     </option>
	                  </logic:iterate>
	               </select>
          		</td>
          	<td colspan="2"><bean:message key="issuance.item.type" /><br/>
	               <select id="issuance_type" name="issuance_type" class="dropdown" disabled="true" onchange="populateInventoryOptions()">
	               		<option value="0">No Category</option>
	               </select>
          		</td>
          	<td align="center" colspan="2" id="issuanceAdd3" name="issuanceAdd3">&nbsp;</td>
          	<td align="center" colspan="3" id="issuanceAdd4" name="issuanceAdd4">&nbsp;</td>
           </tr>
        </table>
<% } %>
    <br>
    <br>
    &nbsp;&nbsp;&uarr;
    <a href="#"><bean:message key="link.to_top" /></a>
    <br>
    <br>
    <jsp:include page="/pages/lostdelay/ld_rfl_incl.jsp" />
    <logic:notEqual name="incidentForm" property="incident_ID" value="">
      <a name="interimexpense"></a>
      <h1 class="green">
        <bean:message key="header.interim_expense" />
<%
        if (report_type == 0) {
%>
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/interim_expense.htm');return false;"><%
          } else if (report_type == 1) {
%>
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/interim_expense.htm#top');return false;"><%
            } else if (report_type == 2) {
%>
              <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/interim_expense.htm#top');return false;"><%
              }
%>
              <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <span class="reqfield">*</span>
          <bean:message key="message.required" />
      <jsp:include page="/pages/includes/incident_expense_incl.jsp" >
      	<jsp:param name="formCss" value="<%= cssFormClass %>" />
      </jsp:include>
                <br>
                <br>
                &nbsp;&nbsp;&uarr;
                <a href="#"><bean:message key="link.to_top" /></a>
                <br>
                <br>
     </logic:notEqual>
<script> <logic:iterate id="theitem" indexId="i" name="incidentForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
     		checkBagType(<%=i%>);
		</logic:iterate></script>
	<logic:present name="receiptName" scope="request">
	    <script language=javascript>
	    	var fileName = '<%=(String) request.getAttribute("receiptName") %>';
   			openPreviewWindow1(fileName);
   			anyLossCodeChanges();
	    </script>
    </logic:present>		
