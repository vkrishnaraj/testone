<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.DisputeResolutionUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.db.dr.Dispute" %>
<%@ page import="com.bagnet.nettracer.tracing.db.dr.DisputeUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Incident" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Item" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.IncidentBMO" %>
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
	%>
	
  <SCRIPT LANGUAGE="JavaScript">
  <%
	boolean lossCodeChange=false;
	Incident inc=null;
	if(myform.getIncident_ID()!=null && myform.getIncident_ID().length()>0){
		inc=IncidentBMO.getIncidentByID(myform.getIncident_ID(), null);
		List<Item> ilist=myform.getItemlist();
		int i=0;
		for(Item it:ilist){
			if(it.getLossCode()!=0 && it.getLossCode()!=inc.getItemlist().get(i).getLossCode()){
				if(myform.getRemarklist()!=null && (myform.getRemarklist().size()<=inc.getRemarks().size())){
					lossCodeChange=true;
					break;
				}
			}
			i++;
		}
	}
	%>
	var lossCodeChange=<%=lossCodeChange%>;
	
	function lossCodeChanged(index){
		  lossCodeChange=false;

		  <% if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_LEVEL_LOSS_CODES)){ %>
		  var disValue=document.getElementById("theitem["+index+"].lossCode");
		  if(disValue!=null && disValue.value!="0"){
			  var lastCode=0;
				  <% if(inc!=null ) {
				  		if(inc.getItemlist() !=null && inc.getStatus()!=null && inc.getStatus().getStatus_ID()==TracingConstants.MBR_STATUS_CLOSED){
				  			int i=0;
				  			for(Item it:inc.getItemlist()){%>
				  				if(index==<%=i%>){
				  					lastCode=<%=it.getLossCode()%>
				  				}
				  		<%	i++;
				  			} 
				  		} %>
						var remText=document.getElementById("remark["+(<%=myform.getRemarklist().size()-1%>)+"].remarktext");
						
						if(disValue.value!=lastCode && (<%=myform.getRemarklist().size()<=inc.getRemarks().size()%> 
				  			|| (remText!=null && remText.value!=null && remText.value.replace(/\s*/g, "").length==0 )))
				  			lossCodeChange=true;
				  <% } %>	
		  }
		  <%}%>
		  
	  }
	  
	  function anyLossCodeChanges(){
		  
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
                <td>
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
                <td>
                  <bean:message key="colname.ldclose.arr_flight_num" />
                  <br>
                  <html:text name="theitem" property="arrivedonflightnum" size="10" maxlength="5" styleClass="textfield" indexed="true" />
                </td>
                <td>
                  <bean:message key="colname.ldclose.arr_date" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text name="theitem" property="disarrivedondate" size="13" maxlength="13" styleClass="textfield" indexed="true" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2<%= i %>" name="calendar2<%= i %>" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.incidentForm, '<%= "theitem[" + i + "].disarrivedondate" %>','calendar2<%= i %>','<%= a.getDateformat().getFormat() %>'); return false;"></td>
                  <% if (i == 0) { %>
                  <td><br>
                  	<center>
                  		<input type="button" id="button" onclick="populateBagInfo(this);" value="<bean:message key="apply.to.all"/>" />
                  	</center>
                  </td>
                  <% } %>
              </tr>
              
              <% }
              if(bagLossCodes){
            	boolean notClosed=myform.getStatus().getStatus_ID()!=TracingConstants.MBR_STATUS_CLOSED;
            	boolean closed=myform.getStatus().getStatus_ID()!=TracingConstants.MBR_STATUS_CLOSED;
            	boolean notDelivered=theitem.getStatus().getStatus_ID()!=TracingConstants.ITEM_STATUS_TOBEDELIVERED;
            	boolean delivered=theitem.getStatus().getStatus_ID()==TracingConstants.ITEM_STATUS_TOBEDELIVERED;
            	boolean sameStation=a.getStation().getStation_ID()==myform.getStationcreated().getStation_ID();
            	boolean diffStation=a.getStation().getStation_ID()!=myform.getStationcreated().getStation_ID();
                boolean editLossCode=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_NON_CLOSED_DELIVERED_BAGS,a) && notClosed && notDelivered && sameStation)
        				|| (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_DELIVERED_BAGS_SAME_STATION,a) && sameStation && delivered )
        				|| (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_CLOSED_INCIDENT,a) && sameStation && closed )
        				|| (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_EDIT_ANY_CLOSED_DELIVERED,a) && (closed || delivered)); %>
	          <% if(!editLossCode){ %> 
	          
		        <html:hidden property="lossCode" value="<%=String.valueOf(theitem.getLossCode()) %>"  name="theitem" indexed="true"/>
		        
		        <html:hidden property="faultStation_id" value="<%=String.valueOf(theitem.getFaultStation_id()) %>" name="theitem"  indexed="true"/>
	          <%} %>
              <tr>
              	<td colspan="3">
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
              
	          	<td align="center" style="vertical-align:middle;">&nbsp;<br>
	          		<% if(i!=0){ %>
	            		<input type="button" name="sameAsPrevious_<%=i%>" value="<bean:message key="button.same_previous"/>" onclick="samePrevious(<%=i %>);" id="button">
	            	<% } %>
	          	</td>
	          	
              	<td colspan="3">&nbsp;<br></td>
              </tr>
              <% } %>
            </table>
          </logic:iterate>