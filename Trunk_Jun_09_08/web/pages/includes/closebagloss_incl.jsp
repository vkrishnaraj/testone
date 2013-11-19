<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Item" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>
<%@ page import="java.util.List" %>
<%
  	Agent a = (Agent)session.getAttribute("user");

	String cssFormClass;
	cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
	IncidentForm myform=(com.bagnet.nettracer.tracing.forms.IncidentForm)session.getAttribute("incidentForm");
	String incident_ID = myform.getIncident_ID();
	boolean bagLossCodes=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_LOSS_CODES_BAG_LEVEL, a) && PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES));

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
   %>
	
  <SCRIPT LANGUAGE="JavaScript">
  
	var lossCodeChange=false;
	
	function lossCodeChanged(index){
		  <% if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES)){ %>
			  var disValue=document.getElementById("theitem["+index+"].lossCode");
			  if(disValue!=null && disValue.value!="0"){
				  var lastCode=0;
				  <% com.bagnet.nettracer.tracing.db.Status existStatus=myform.getExistIncStatus();
			  		if(myform.getExistItemlist() !=null  && myform.getExistItemlist().size()>0  && existStatus!=null && existStatus.getStatus_ID()!=TracingConstants.MBR_STATUS_CLOSED){
			  			int i=0;
			  			for(Item it:myform.getExistItemlist()){%>
			  				if(index==<%=i%>){
			  					lastCode=<%=it.getLossCode()%>
			  				}
			  		<%	i++;
			  			}
			  		} %>
					var remText=document.getElementById("remark["+(<%=myform.getRemarklist().size()-1%>)+"].remarktext");
					
					if(disValue.value!=lastCode && (<%=myform.getRemarklist().size()<=myform.getExistRemarkSize()%> 
			  			|| (remText!=null && remText.value!=null && remText.value.replace(/\s*/g, "").length==0 )))
			  			lossCodeChange=true;
					  	
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
  </SCRIPT>
		<logic:iterate id="theitem" indexId="i" name="incidentForm" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
            <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
            
              <% if (request.getAttribute("lostdelay") != null || bagLossCodes) { %>
              <tr>
                <td colspan=4>
                  <a name='additem<%= i %>'></a>
                  <b><bean:message key="colname.bag_number" />
                  :
                  <%= theitem.getBagnumber() + 1 %>
                </td>
              </tr>
              <% }
              if (request.getAttribute("lostdelay") != null) { %>
              <tr>
                <td  style="width:25%">
                  <bean:message key="colname.ldclose.arr_airline_id" />
                  <br>
                  
                  <logic:empty name="theitem" property="arrivedonairline_ID">
                    <jsp:setProperty name="theitem" property="arrivedonairline_ID" value="<%= a.getCompanycode_ID() %>"/>
                  </logic:empty>
                  <html:select name="theitem" property="arrivedonairline_ID" styleClass="dropdown" indexed="true">
                    <html:option value="">
                      <bean:message key="select.please_select" />
                    </html:option>
                    <html:options collection="companylistByName" property="companyCode_ID" labelProperty="companydesc" />
                  </html:select>
                </td>
                <td style="width:25%" >
	                  <bean:message key="colname.ldclose.arr_flight_num" />
	                  <br>
	                  <html:text name="theitem" property="arrivedonflightnum" size="10" maxlength="5" styleClass="textfield" indexed="true" />
                </td>
                <td colspan="<%=i==0?1:2 %>">
	                  <bean:message key="colname.ldclose.arr_date" />
	                  (
	                  <%= a.getDateformat().getFormat() %>)
	                  <br>
	                  <html:text name="theitem" property="disarrivedondate" size="13" maxlength="13" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= i %>" name="calendar2<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitem[" + i + "].disarrivedondate" %>','calendar2<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
                  <% if (i == 0) { %>
                  <td style="width:25%" ><br>
                  	<center>
                  		<input type="button" id="button" onclick="populateBagInfo(this);" value="<bean:message key="apply.to.all"/>" />
                  	</center>
                  </td>
                  <% } %>
              </tr>
              
              <% }
              if(bagLossCodes){

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
	              	<td colspan="3" style="width:80%">
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
	              </tr>
	              
	              <tr>
	              	<% if(i!=0){ %>
		          	<td align="center" style="vertical-align:middle;">&nbsp;<br>
		          	
		            		<input type="button" name="sameAsPrevious_<%=i%>" value="<bean:message key="button.same_previous"/>" onclick="samePrevious(<%=i %>);" id="button">
		            	
		          	</td>
		          	
	              	<td colspan="3">&nbsp;<br></td>
	              	<% } %>
	              </tr>
	              
              <% } %>
            </table>
          </logic:iterate>
          <script>
          	anyLossCodeChanges();
          </script>