<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();
	
	function getSubCategory() {
		o = document.searchLostFoundForm;
		o.changesubcategory.value = "1";
		document.getElementById("subcategorydiv").innerHTML = "<bean:message key="ajax.please_wait" />";
		postForm("searchLostFoundForm", true, function (req) { 
			o.changesubcategory.value = "0";
			document.getElementById("subcategorydiv").innerHTML = req.responseText; 

		});
	}

	function getStations() {
		var compList=document.getElementById("companyId");
		var stationList=document.getElementById("locationId");
		var selectedCompany=compList.options[compList.selectedIndex].value;
		stationList.options.length=1;
		
		stationList.options[0]=new Option("<bean:message key="search.option.all" />","<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>",true,false);
		var selectedStation;
		<logic:iterate indexId="i" id="cList" name="subComplist"  type="com.bagnet.nettracer.tracing.db.lf.Subcompany" >
		if("<%=cList.getSubcompanyCode()%>"==selectedCompany)
			{	
				<% String source="subComplist"+cList.getSubcompanyCode();%>
				<logic:iterate indexId="j" id="scList" name="<%=source%>"  type="com.bagnet.nettracer.tracing.db.Station" >
					stationList.options[<%=j+1%>]=new Option("<%=scList.getStationcode()%>","<%=scList.getStation_ID()%>",false,false);
					if(<%=request.getAttribute("selectStation")%>==<%=scList.getStation_ID()%>){
						selectedStation=<%=request.getAttribute("selectStation")%>;
					}
				</logic:iterate>
			}
		</logic:iterate>
		if(selectedStation!=null){
			stationList.value=selectedStation;
		} else {
			stationList.value=<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>;
		}
		/*document.getElementById("subCategory"+selectedCategory).style.display="inline";*/
	}
    
	function goprev() {
	  o = document.searchLostFoundForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.searchLostFoundForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.searchLostFoundForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}

</script>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="<%=(request.getParameter("found")!=null?"barcode":"id") %>" action="search_lost_found.do" method="post" onsubmit="return validateSearch(this);">
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_LOST) %>" >
            		<bean:message key="header.search.lost.reports" />
            		<input type="hidden" name="lost" value="1" />
            	</logic:equal>
            	<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>" >
            		<bean:message key="header.search.found.items" />
            		<input type="hidden" name="found" value="1" />
            	</logic:equal>
            </h1>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
                <td>
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
   	<tr>
   		<td id="middlecolumn">        
     		<div id="maincontent">
         		<logic:present name="reportfile" scope="request">
            		<center><a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a></center>
           		</logic:present>
				<h1 class="green">
		        	<bean:message key="header.search_criteria" />
                	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
       				<tr>
             			
             			<td>
               				<bean:message key="colname.reference.id" />
               				<br>
             				<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_LOST) %>" > 
	               				<logic:equal name="searchLostFoundForm" property="id" value="0" >
	               					<html:text name="searchLostFoundForm" property="id" value="" size="10" styleClass="textfield" />
	               				</logic:equal>
	               				<logic:greaterThan name="searchLostFoundForm" property="id" value="0" >
	               					<html:text name="searchLostFoundForm" property="id" size="10" styleClass="textfield" />
	               				</logic:greaterThan>
               				</logic:equal>
               				<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>" >
               					<logic:empty name="searchLostFoundForm" property="barcode" >
	               					<html:text name="searchLostFoundForm" property="barcode" value="" size="10" styleClass="textfield" />
               					</logic:empty>
               					<logic:notEmpty name="searchLostFoundForm" property="barcode" >
	               					<html:text name="searchLostFoundForm" property="barcode" size="10" styleClass="textfield" />
               					</logic:notEmpty>
               				</logic:equal>                 
             			</td>
             			
						<td>
							<bean:message key="colname.lf.company" />
							<br/>
         					<html:select name="searchLostFoundForm" property="companyId" styleClass="dropdown" styleId="companyId" onchange="getStations();">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="name" />
         					</html:select>
						</td>
						<td>
							<bean:message key="colname.station_number" />
							<br>
		            		<html:select name="searchLostFoundForm" property="stationId" styleClass="dropdown" styleId="locationId">
		            			<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
								<html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
             			<td>
               				<bean:message key="colname.lf.status" />
               				<br>
               				<html:select name="searchLostFoundForm" property="statusId" styleClass="dropdown" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" /></html:option>
               				</html:select>                  
             			</td>
           			</tr>
           			<tr>           				
           				<td nowrap colspan=<%=(request.getParameter("found")!=null? 2:4) %>>
			                <bean:message key="colname.lf.date.range" />
			                (<%= a.getDateformat().getFormat() %>)
			                <br>
			                <html:text property="startDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchLostFoundForm.startDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
			                <html:text property="endDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchLostFoundForm.endDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
		                </td>
		                <logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>" >
		                <% if(a.getCompanycode_ID().equals(TracingConstants.LF_LF_COMPANY_ID)&&(a.getSubcompany()==null)){ %>
		                <td nowrap colspan=1>
			                <bean:message key="colname.agent.name" /><br/>
			                <html:text name="searchLostFoundForm" property="agentName" size="15" maxlength="20" styleClass="textfield" />
			               
		                </td>
		                <%} %>
             			<td>
               				<bean:message key="colname.lf.disposition" />
               				<br>
               				<html:select name="searchLostFoundForm" property="dispositionId" styleClass="dropdown" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STAGED_FOR_SHIPPING) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STAGED_FOR_SHIPPING) %>" /></html:option>
               				</html:select>                  
             			</td>
		                </logic:equal>
           			</tr>
           			<tr>
           				<td>
			              <bean:message key="colname.last_name.req" />
			              <br>
			              <html:text name="searchLostFoundForm" property="lastName" size="15" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
			              <bean:message key="colname.first_name.req" />
			              <br>
			              <html:text name="searchLostFoundForm" property="firstName" size="15" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
		              		<bean:message key="colname.lf.phone.number" />
		              		<br/><bean:message key="colname.lf.phone.logic"/>
		              		<br/>
		              		
		              		<html:text name="searchLostFoundForm" property="intNumber" styleId="priInterNum" size="1" maxlength="10" styleClass="textfield" />
		              		<html:text name="searchLostFoundForm" property="areaNumber" styleId="priAreaNum" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="searchLostFoundForm" property="exchangeNumber" styleId="priExchaNum" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="searchLostFoundForm" property="lineNumber" styleId="priLineNum" size="3" maxlength="10" styleClass="textfield" /><br/>
		              		<bean:message key="colname.lf.phone.extension" />: <html:text name="searchLostFoundForm" property="extension" size="3" maxlength="4" styleClass="textfield" />
		              		
		              		<!--<html:text name="searchLostFoundForm" property="phoneNumber" size="15" maxlength="25" styleClass="textfield" />-->
		              	</td>
		              	<td>
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="searchLostFoundForm" property="email" size="20" maxlength="100" styleClass="textfield" />
		              	</td>
           			</tr>
           			<tr>
           				<td>
           					<bean:message key="colname.lf.category" />
           					<br/>
           					<input type="hidden" name="changesubcategory">
           					<html:select name="searchLostFoundForm" property="category" styleClass="dropdown" onchange="getSubCategory();" >
           						<html:option value="0"><bean:message key="option.lf.please.select" /></html:option>
           						<html:options collection="lfcategorylist" property="id" labelProperty="description" />
           					</html:select>
           				</td>
           				<td>
           					<div id="subcategorydiv">
           					<bean:message key="colname.lf.subcategory" />
           					<br/>
           					<html:select name="searchLostFoundForm" property="subCategory" styleClass="dropdown" >
           						<option value="0"><bean:message key="option.lf.please.select" /></option>
      							<html:options collection="lfsubcategorylist" property="id" labelProperty="description" />
           					</html:select>
           					</div>
           				</td>
           				<td>
           					<bean:message key="colname.lf.brand" />
           					<br>
           					<html:text name="searchLostFoundForm" property="brand" size="15" maxlength="30" styleClass="textfield" />
           				</td>
           				<td >
           					<bean:message key="colname.lf.description" />
         					<br>
		              		<html:text name="searchLostFoundForm" property="itemDescription" size="25" maxlength="100" styleClass="textfield" />
           				</td>
           			</tr>
		            <tr>
           				<td>
			              <bean:message key="colname.serial.number" />
			              <br>
			              <html:text name="searchLostFoundForm" property="serialNumber" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td colspan=3>
			            </td>
			        </tr>
           			<tr>
           				<td colspan=4>
           					<center>
			              		<html:submit property="search" styleId="button" >
			              			<bean:message key="button.retrieve" />
			              		</html:submit>
			              		&nbsp;&nbsp;
			              		<html:reset styleId="button">
				                  	<bean:message key="button.reset" />
				                </html:reset>
			              	</center>
           				</td>
           			</tr>
				</table>
				<br />
                <h1 class="green">
                	<bean:message key="header.search_result" />
                	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              	</h1>
              	<table class="<%=cssFormClass %>" cellpadding="0" cellspacing="0" >
              		<tr>
              			<td class="header">
              				<bean:message key="colname.lf.id" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.status" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.station_number" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.date" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.client.name" />
              			</td>
              		</tr>
              		<logic:iterate id="result" name="resultList" type="com.bagnet.nettracer.tracing.db.lf.LFObject">
              			<tr>
              				<td>
              					<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_LOST) %>" >
              						<a href="create_lost_report.do?lostId=<%=result.getId() %>"><bean:write name="result" property="id" /></a>
              					</logic:equal>
              					<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>" >
              						<a href="create_found_item.do?foundId=<%=result.getId() %>"><bean:write name="result" property="barcode" /></a>
              					</logic:equal>
              				</td>
              				<td>
              					<bean:write name="result" property="statusDescription" />
              				</td>
              				<td>
              					<bean:write name="result" property="disStation" />
              				</td>
              				<td>
              					<%=result.getDisplayDate(a.getDateformat().getFormat()) %>
              				</td>
              				<td>
              					<bean:write name="result" property="clientName" />
              				</td>
              			</tr>
              		</logic:iterate>
				   	<tr>
						   <td colspan="11">
						   	<jsp:include page="/pages/includes/pagination_incl.jsp" />
						   </td>
				    </tr>
			    </table>
			    <script language=javascript>
					document.location.href="#result";
					getStations();

					var stationList=document.getElementById("locationId");
					var matchid=<%=request.getAttribute("stationID")%>
					if(matchid!=null){
						stationList.value=matchid;
					}
					
			    </script>
   			</div>
 		</td>
   	</tr>
</html:form>
