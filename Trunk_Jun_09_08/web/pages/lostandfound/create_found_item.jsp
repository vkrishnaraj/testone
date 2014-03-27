<%@page import="com.bagnet.nettracer.tracing.forms.lf.FoundItemForm"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFCategory" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFSubCategory" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
 	
 	ArrayList categoryList = (ArrayList) request.getSession().getAttribute("lfcategorylist");
 	
 	boolean enableIdField = false;
 	if (request.getAttribute("enableIdField") != null) {
		enableIdField = ((String) request.getAttribute("enableIdField")).equals("true");
		request.removeAttribute("enableIdField");
 	}
 	
 	int dispositionId = 0;
 	boolean haveDeliveryInformation = false;
 	boolean deliveryRejected = false;
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();
	
	var subCategoryArray = null;
	
	function getSubCategories() {
		if (subCategoryArray != null) {
			return;
		}
		var i;
	   	var j;
	   	subCategoryArray = new Array(<%=categoryList.size()%>);
	   	<%
	   	   LFCategory currCategory;
	   	   LFSubCategory subCategory;
	   	   for (int i = 0; i < categoryList.size(); ++i) {
	   		   currCategory = (LFCategory) categoryList.get(i);
  		%>
  			   i = <%=i %>;
	   		   subCategoryArray[i] = new Array(<%=currCategory.getSubcategories().size() %>);
	   		   j = 0;
	   		   <%
	   		       Iterator iterator = currCategory.getSubcategories().iterator();
	   		       while (iterator.hasNext()) {
	   		           subCategory = (LFSubCategory) iterator.next();
	   		   %>
	   		           subCategoryArray[i][j] = <%="{\"value\": \""+subCategory.getId()+"\", \"label\": \""+subCategory.getDescription()+"\"}" %>;
	   		           
	   		           ++j;
	   		   <%
	   		       }
	   		   %>
	   	<% } %>
	}
	
	function validateWeight(matchId, form) {
	   var validWeight=prompt("Please confirm this is the correct weight:",document.getElementById("weight").value)
	   if(validWeight != "" && validWeight !== null) {
		    /*document.getElementById("confirm").value="1";
		    document.getElementById("matchId").value=matchId;
	   		document.getElementById("weight").value=validWeight;
	   		form.submit();*/
	   		location.replace("create_found_item.do?email=1&matchId="+matchId+"&validWeight="+validWeight);
	   } else {
		   return false;
	   }
   }
	

	function getStations() {
		var compList=document.getElementById("foundCompanyId");
		var stationList=document.getElementById("foundLocationId");
		var selectedCompany=compList.options[compList.selectedIndex].value;
		stationList.options.length=1;
		
		stationList.options[0]=new Option("<bean:message key="option.lf.please.select" />","",true,false);
		<logic:iterate indexId="i" id="cList" name="subComplist"  type="com.bagnet.nettracer.tracing.db.lf.Subcompany" >
		if("<%=cList.getSubcompanyCode()%>"==selectedCompany)
			{	
				<% String source="subComplist"+cList.getSubcompanyCode();%>
				<logic:iterate indexId="j" id="scList" name="<%=source%>"  type="com.bagnet.nettracer.tracing.db.Station" >
					stationList.options[<%=j+1%>]=new Option("<%=scList.getStationcode()%>","<%=scList.getStation_ID()%>",false,false);
				</logic:iterate>
			}
		</logic:iterate>
		
		/*document.getElementById("subCategory"+selectedCategory).style.display="inline";*/
	}
	
	function fieldChanged(field) {
		var state = document.getElementById('state');
		var province = document.getElementById('province');
		var country = document.getElementById('country');
	
		if (field == "state") {
			stateChanged(state, province, country);
		} else if (field == "province") {
			provinceChanged(state, province, country);
		} else if (field == "country") {
			countryChanged(state, province, country);
		}
	}
	
	function stateChanged(state, province, country) {
		if (state.value == "") {
			province.disabled = false;	
			province.className = "textfield";
		} else {
			province.value = "";
			province.disabled = true;
			province.className = "disabledtextfield";
			country.value = "US";
		}
	}
	
	function provinceChanged(state, province, country) {
		if (province.value == "") {
			state.disabled = false;
		} else {
			state.value = "";
			state.disabled = true;
		}
	}
	
	function countryChanged(state, province, country) {
		if (country.value == "") {
			state.disabled = false;
			province.disabled = false;
			province.className = "textfield";
		} else if (country.value == "US") {
			state.disabled = false;
			province.value = "";
			province.disabled = true;
			province.className = "disabledtextfield";
		} else {
			state.value = "";
			state.disabled = true;
			province.disabled = false;
			province.className = "textfield";
		}
	}
	
	function updateSubCategories(categoryId, itemIndex) {
		getSubCategories();
		var categories = document.getElementById(categoryId);
		var selectedIdx = categories.selectedIndex;
		
		var subCategories = document.getElementById('subcategories_' + itemIndex);
		subCategories.options[0].selected = true;
		if (selectedIdx == 0) {
			for (var i = subCategories.length; i > 0; --i) {
				subCategories.remove(i);
			}
			return;
		}

		if (selectedIdx > 0) {
			selectedIdx = selectedIdx - 1;
		}
		var newSubCategories = subCategoryArray[selectedIdx];
		
		for (var i = subCategories.length; i > 0; --i) {
			subCategories.remove(i);	
		}

		for (var i = 0; i < newSubCategories.length; ++i) {
			subCategories.add(new Option(newSubCategories[i].label, newSubCategories[i].value), subCategories.length);
		}
	}
	
	function setSelectedSubCategory(categoryIdx, subCategoryIdx, elementId) {
		if (categoryIdx > 0) {
			document.getElementById(elementId).disabled = false;
		} else {
			return;
		}
		getSubCategories();
		var subCategories = subCategoryArray[categoryIdx - 1];
		var subCategoryElement = document.getElementById(elementId);
		for (var i = 0; i < subCategories.length; ++i) {
			if (subCategories[i].value == subCategoryIdx) {
				subCategoryElement.options.selectedIndex = i+1;
				break;
			}
		}
	}
	
	function setLostId(lostId, matchItem, itemId) {
		document.getElementById('lostId').value = lostId;
		document.getElementById('matchItem').value = matchItem;
		document.getElementById('itemId').value = itemId;
	}
	
    function show(name,link1,link2) {
  	  jQuery(name).show();
  	  toggleLinks(link1,link2);
  	  document.getElementById(name).value = "true";
    }
    
    function hide(name,link1,link2) {
  	  jQuery(name).hide();
  	  toggleLinks(link1,link2);
  	  document.getElementById(name).value = "false";
    }
    
    function toggleLinks(link1,link2) {
	  	  if (jQuery(link1).is(':visible')) {
	  		  jQuery(link1).hide();
	  		  jQuery(link2).show();
	  	  } else {
	  		  jQuery(link1).show();
	  		  jQuery(link2).hide();
	  	  }
	  }
    
	function textCounter2(field, maxlimit) {
	    if (field.value.length > maxlimit) {
	      field.value = field.value.substring(0, maxlimit);
	    }
	}
	
</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="found.barcode" action="create_found_item.do" method="post" onsubmit="return validateFoundItemForm(this);" >
<input type="hidden" name="delete_these_elements" value="" />
<html:hidden property="matchItem" styleId="matchItem" value="" />
<html:hidden property="itemId" styleId="itemId" value="" />
<html:hidden property="lostId" styleId="lostId" value="" />
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.found.item" />
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
   	<tr>
   		<td id="middlecolumn">        
     		<div id="maincontent">
         		<logic:present name="reportfile" scope="request">
            		<center><a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a></center>
           		</logic:present>
   				<center>
   					<font color="red">
         				<logic:messagesPresent message="true">
         					<html:messages id="msg" message="true">
         						<br/>
         						<bean:write name="msg"/>
         						<br/>
         						<br/>
       						</html:messages>
   						</logic:messagesPresent>
         			</font>
       			</center>
       			<logic:equal name="foundItemForm" property="displaySummary" value="true">
					<h1 class="green">
			        	<bean:message key="header.report.summary" />
			        	<!-- a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a-->
			        </h1><br>
			        	<logic:equal name="foundItemForm" property="hasContactInfo" value="true" >
					        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
			        			<tr>
			        				<td class="summaryActionItem" >
			        					<center><a href="#contactInfo" ><b><bean:message key="message.contact.info.present" /></b></a></center>
			        				</td>
			        			</tr>
					        </table>
			        	</logic:equal>
			        	<logic:notEmpty name="foundItemForm" property="traceResults" >
			        	<h1 class="green">
					        	<bean:message key="header.potential.matches" />
					        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					        </h1>
			        		<logic:iterate indexId="i" id="match" name="foundItemForm" property="traceResults" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory" >
					        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
			        			<tr>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="colname.description" />
			        				</td>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="header.found.item" />:&nbsp;
			        					<bean:write name="match" property="found.barcode" />
			        				</td>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="colname.score" />:&nbsp;
			        					
				        					<bean:write name="match" property="score" />
			        				</td>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="header.lost.report" />:&nbsp;
			        					<a style="color:#fff;" href="create_lost_report.do?lostId=<bean:write name="match" property="lost.id" />" >
			        						<bean:write name="match" property="lost.id" />
			        					</a>
			        				</td>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="colname.lf.action" />
			        				</td>
			        			</tr>
			        			<logic:iterate indexId="j" id="detail" name="match" property="details" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail" >
			        				<bean:size id="numDetails" name="match" property="details" />
				        			<tr>
				        				<td>
				        					<bean:write name="detail" property="description" />
				        				</td>
				        				<td>
				        					<bean:write name="detail" property="decryptedFoundValue" />
				        				</td>
				        				<td>
				        					<bean:write name="detail" property="score" />
				        				</td>
				        				<td>
				        					<bean:write name="detail" property="decryptedLostValue" />
				        				</td>
				        				<% if (j == 0) { %>
				        					<td rowspan=<bean:write name="numDetails" /> >
			         						<% 
			         						   int statusId = match.getStatus().getStatus_ID();
			         						   if (statusId == TracingConstants.LF_TRACING_CONFIRMED) { %>
			         							<a href='create_found_item.do?unconfirm=1&matchId=<%=match.getId() %>'><bean:message key="button.un_match" /></a>
				         							<% 
				         							String email = match.getLost().getClient().getDecryptedEmail();
				         							if (email != null && !email.isEmpty()) {
				         							   if (match.getLost().isFoundEmail()) { %>
				         								,<br/><bean:message key="message.customer.notified" />
				         							<% } else { %>
				         								,<br/><a href='#' onclick='validateWeight(<%=match.getId() %>)'><bean:message key="button.email.customer" /></a>
				         							<% } 
			         								}%>
			         						<% } else { %>
			         							<a id='confirm<%=match.getLost().getId()%>' href='create_found_item.do?confirm=1&matchId=<%=match.getId() %>'><bean:message key="button.do_match" /></a>,&nbsp;
			         							<a href='create_found_item.do?reject=1&matchId=<%=match.getId() %>'><bean:message key="button.reject" /></a>
			         						<% } %>
				        					</td>
				        				<% } %>
				        			</tr> 
			        			</logic:iterate>
					        </table>
					        </logic:iterate>
			        	</logic:notEmpty>
			        	
			        	<!-- REJECTED RESULTS START HERE -->
			        	<logic:notEmpty name="foundItemForm" property="rejectedResults" >
			        		<div style="width:100%;" >
                    		<a name="rm" ></a>
                    		<span style="float:left;">
                    		<br/>
							<h1 class="green">
					        	<bean:message key="header.rejected.matches" />
					        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
					        </h1>
					        </span>
					        </div>
					        <span style="float:right;" >
								<a id="showMatches" href="#rm" onClick="show('#rejectedMatches','#showMatches','#hideMatches')"><bean:message key="link.show" /></a>
								<a id="hideMatches" href="#rm" onClick="hide('#rejectedMatches','#showMatches','#hideMatches')" style="display:none;" ><bean:message key="link.hide" /></a>
							</span>
			        	<div id="rejectedMatches" style="margin:0;padding:0;display:none;" >
			        		<logic:iterate indexId="i" id="match" name="foundItemForm" property="rejectedResults" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory" >
			        		
					        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
			        			<tr>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="colname.description" />
			        				</td>
			        				<td class="header" style="width:30%;" >
			        					<bean:message key="header.found.item" />:&nbsp;
			        					<bean:write name="match" property="found.barcode" />
			        				</td>
			        				<td class="header" style="width:30%;" >
			        					<bean:message key="header.lost.report" />:&nbsp;
			        					<a style="color:#fff;" href="create_lost_report.do?lostId=<bean:write name="match" property="lost.id" />" >
			        						<bean:write name="match" property="lost.id" />
			        					</a>
			        				</td>
			        				<td class="header" style="width:20%;" >
			        					<bean:message key="colname.lf.action" />
			        				</td>
			        			</tr>
			        			<logic:iterate indexId="j" id="detail" name="match" property="details" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail" >
			        				<bean:size id="numDetails" name="match" property="details" />
				        			<tr>
				        				<td>
				        					<bean:write name="detail" property="description" />
				        				</td>
				        				<td>
				        					<bean:write name="detail" property="decryptedFoundValue" />
				        				</td>
				        				<td>
				        					<bean:write name="detail" property="decryptedLostValue" />
				        				</td>
				        				<% if (j == 0) { %>
				        					<td rowspan=<bean:write name="numDetails" /> >
			         							<a href='create_found_item.do?unreject=1&matchId=<%=match.getId() %>'><bean:message key="button.unreject" /></a>
				        					</td>
				        				<% } %>
				        			</tr> 
			        			</logic:iterate>
					        </table>
					        </logic:iterate>
					        </div>
					        <br/>
			        	</logic:notEmpty>
			        	
       			</logic:equal>
       			
				<h1 class="green">
		        	<bean:message key="header.report.information" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" />
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
       				<tr>

            		<logic:notEmpty name="foundItemForm" property="found.barcode">
						<td colspan="2">
							<bean:message key="colname.lf.report.id" />&nbsp;<span class="reqfield">*</span>
         					<br>
         					<% if (enableIdField) { %>
								<html:text name="foundItemForm" property="found.barcode" styleClass="textfield" />
         					<% } else { %>
								<html:text name="foundItemForm" property="found.barcode" disabled="true" styleClass="disabledtextfield" />
							<% } %>
         				</td>
					</logic:notEmpty>
					<logic:empty name="foundItemForm" property="found.barcode">
						<td colspan="2">
							<bean:message key="colname.lf.report.id" />&nbsp;<span class="reqfield">*</span>
         					<br>
							<html:text name="foundItemForm" property="found.barcode" styleClass="textfield" />
         				</td>
         			</logic:empty>
						<td>
							<bean:message key="colname.lf.received.date" />&nbsp;<span class="reqfield">*</span>
							<br/>
							<logic:empty name="foundItemForm" property="found.receivedDate" >
								<html:text name="foundItemForm" property="disReceivedDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.foundItemForm.disReceivedDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
							</logic:empty>
							<logic:notEmpty name="foundItemForm" property="found.receivedDate">
								<html:text name="foundItemForm" property="disReceivedDate" disabled="true" styleClass="disabledtextfield" />
							</logic:notEmpty>
						</td>
						<td>
							<bean:message key="colname.lf.created.date" />
							<br/>
							<html:text name="foundItemForm" property="disFoundDate" disabled="true" styleClass="disabledtextfield" />
						</td>
						<td>
							<bean:message key="colname.lf.created.agent" />
							<br/>
							<html:text name="foundItemForm" property="found.agent.username" disabled="true" styleClass="disabledtextfield" />
						</td>
					</tr>
					<tr>
						<td>
							<bean:message key="colname.lf.company" />&nbsp;<span class="reqfield">*</span>
							<br/>
							<% if(a.getSubcompany()!=null){ %>
         					<html:select name="foundItemForm" property="found.companyId" styleClass="dropdown" disabled="true" styleId="foundCompanyId" onchange="getStations();">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="name" />
         					</html:select>
         					<% } else { %>
         					<html:select name="foundItemForm" property="found.companyId" styleClass="dropdown" styleId="foundCompanyId" onchange="getStations();">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="name" />
         					</html:select>
         					<% } %>
						</td>
						<td>
							<bean:message key="colname.lf.found.location" />
							<br>
		            		<html:select name="foundItemForm" property="found.locationId" styleClass="dropdown" styleId="foundLocationId">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
						<td>
							<bean:message key="colname.lf.segment.flight" />
							<br/>
							<html:text name="foundItemForm" property="found.flightNumber" size="20" maxlength="20" styleClass="textfield" />
						</td>
						<td>
         					<bean:message key="colname.lf.status" />&nbsp;<span class="reqfield">*</span>
         					<br>
         					<html:select name="foundItemForm" property="found.statusId" styleClass="dropdown" >
         						<html:option value="-1"><bean:message key="option.lf.please.select" /></html:option>
         						<html:options collection="lfstatuslist" property="status_ID" labelProperty="description" />
         					</html:select>
         				</td>
						<td>
         					<bean:message key="colname.lf.item.location" />
         					<br>
         					<html:select name="foundItemForm" property="found.itemLocation" styleClass="dropdown" >
         						<html:option value="<%=String.valueOf(TracingConstants.LF_LOCATION_SHELF) %>"><bean:message key="lf.location.shelf" /></html:option>
         						<html:option value="<%=String.valueOf(TracingConstants.LF_LOCATION_VERIFICATION) %>"><bean:message key="lf.location.verification" /></html:option>
         						<html:option value="<%=String.valueOf(TracingConstants.LF_LOCATION_WAITING) %>"><bean:message key="lf.location.waiting" /></html:option>
         						<html:option value="<%=String.valueOf(TracingConstants.LF_LOCATION_DELIVERY) %>"><bean:message key="lf.location.delivery" /></html:option>
         						<html:option value="<%=String.valueOf(TracingConstants.LF_LOCATION_SALVAGED) %>"><bean:message key="lf.location.salvaged" /></html:option>
         					</html:select>
         				</td>
					</tr>
				</table>
				<br/>
				<a name="contactInfo" ></a>
				<h1 class="green">
		        	<bean:message key="header.contact.information" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" /> 
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
			            <td colspan=2>
			              <bean:message key="colname.last_name.req" />
			              <br>
			              <html:text name="foundItemForm" property="found.client.lastName" styleId="lastname" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td nowrap colspan="2">
			              <bean:message key="colname.first_name.req" />
			              <br>
			              <html:text name="foundItemForm" property="found.client.firstName" styleId="firstname"  size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
			              <bean:message key="colname.mid_initial" />
			              <br>
			              <html:text name="foundItemForm" property="found.client.middleName" styleId="middlename"  size="1" maxlength="1" styleClass="textfield" />
			            </td>
			        </tr>
	            	<tr>
		                <td colspan=2>
		                  <bean:message key="colname.street_addr1" />
		                  <br>
		                  <html:text name="foundItemForm" property="found.client.address.decryptedAddress1" styleId="address1"  size="45" maxlength="50" styleClass="textfield" />
		                </td>
		                <td colspan=3>
		                  <bean:message key="colname.street_addr2" />
		                  <br>
		                  <html:text name="foundItemForm" property="found.client.address.decryptedAddress2" styleId="address2"  size="35" maxlength="50" styleClass="textfield" />
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <bean:message key="colname.city" />
		                  <br>
		                  <html:text name="foundItemForm" property="found.client.address.decryptedCity" styleId="city"  size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select name="foundItemForm" property="found.client.address.decryptedState" styleId="state"  styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text name="foundItemForm" property="found.client.address.decryptedProvince" styleId="province"  size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip" />
		                  <br>
		                  <html:text name="foundItemForm" property="found.client.address.decryptedZip" styleId="zip"  size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />
		                  <br>
		                  <html:select name="foundItemForm" property="found.client.address.country" styleId="country"  styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
		                    <html:option value="">
		                      <bean:message key="select.none" />
		                    </html:option>
		                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		              </tr>
		              <tr>
		              	<td colspan="2">
		              		<bean:message key="colname.lf.primary.phone" /><br/>
		              		<bean:message key="colname.lf.phone.logic" />
		              		<br/>
		              		<html:text name="foundItemForm" property="primaryInternationalNumber"  styleId="priInterNum" size="2" maxlength="10" styleClass="textfield" />
		              		<html:text name="foundItemForm" property="primaryAreaNumber" styleId="priAreaNum"  size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="foundItemForm" property="primaryExchangeNumber" styleId="priExchaNum"  size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="foundItemForm" property="primaryLineNumber" styleId="priLineNum"  size="4" maxlength="10" styleClass="textfield" /><br/>
		              		<html:select name="foundItemForm" property="primaryNumberType" styleId="priPhoneType"  styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              		<bean:message key="colname.lf.phone.extension" />: <html:text name="foundItemForm"  styleId="priExt" property="primaryExtension" size="3" maxlength="4" styleClass="textfield" />
		              	</td>
		              	<td colspan="3">
		              		<bean:message key="colname.lf.secondary.phone" /> <br/>
		              		<bean:message key="colname.lf.phone.logic" />
		              		<br/>
		              		<html:text name="foundItemForm" property="secondaryInternationalNumber" styleId="secInterNum" size="2" maxlength="10" styleClass="textfield" />
		              		<html:text name="foundItemForm" property="secondaryAreaNumber" styleId="secAreaNum" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="foundItemForm" property="secondaryExchangeNumber" styleId="secExchaNum" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="foundItemForm" property="secondaryLineNumber" styleId="secLineNum" size="4" maxlength="10" styleClass="textfield" /><br/>
		              		<html:select name="foundItemForm" property="secondaryNumberType" styleId="secPhoneType"  styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME)%>"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              		<bean:message key="colname.lf.phone.extension" />: <html:text name="foundItemForm" styleId="secExt"  property="secondaryExtension" size="4" maxlength="4" styleClass="textfield" /> 
		              	</td>
		              </tr>
		              <tr>
		              	<td colspan="2" >
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="foundItemForm" property="found.client.decryptedEmail" styleId="email"  size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              	<td colspan="3" >
		              		<bean:message key="colname.lf.confirm.email" />
		              		<br />
		              		<html:text name="foundItemForm" property="found.client.confirmEmail" styleId="confirmemail" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              </tr>
				</table>
				<br/>
				<h1 class="green">
					<bean:message key="header.item.information" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<logic:iterate indexId="i" id="item" name="foundItemForm" property="found.items" type="com.bagnet.nettracer.tracing.db.lf.LFItem" >
         			<% 
       					if (item.getType() == TracingConstants.LF_TYPE_FOUND) { %>
	         				<tr>
       					<%	dispositionId = item.getDispositionId();
       						deliveryRejected = item.getDeliveryRejected();
       						haveDeliveryInformation = (dispositionId == TracingConstants.LF_DISPOSITION_DELIVERED || dispositionId == TracingConstants.LF_DISPOSITION_PICKED_UP) || item.getDeliveryRejected();
       						%>
       						<td colspan=3 class="header">
      								<% if (item.getLost() != null) { %>
      									<bean:message key="lf.match.lost" />:&nbsp;<a style="color:#fff;" href='create_lost_report.do?lostId=<%=item.getLost().getId() %>'><%=item.getLost().getId() %></a>&nbsp;
      									<% if (dispositionId == TracingConstants.LF_DISPOSITION_OTHER || dispositionId == TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) { %>
										[<a style="color:#fff;" href='create_found_item.do?unmatchItem=1&itemId=<%=item.getId() %>'><bean:message key="button.un_match" /></a>]
										<% } %>
      								<% } else { %>
      									<bean:message key="lf.match.lost" />:&nbsp;
  										<input type="text" size="10" class="textfield" id="foundInput" onchange="setLostId(this.value,1,<%=item.getId() %>)" />&nbsp;
										<!-- [<a style="color:#fff;" href="javascript:document.foundItemForm.submit();" ><bean:message key="button.do_match" /></a>]-->
										[<a style="color:#fff;" id="confirmInput" href="javascript:document.foundItemForm.submit();" onclick="return validateId('foundInput');" ><bean:message key="button.do_match" /></a>]
      								<% } %>
      							</td>
	       					</tr>
         				<tr>
	         				<td>
	         					<bean:message key="colname.lfc.value" />
	         					<br>
		         				<select name="item[<%=i %>].value"  id="itemvalue_<%=i %>" class="dropdown"  >
			              			<option value="0"><bean:message key="option.lf.please.select" /></option>
			              			<option value="<%=TracingConstants.LFC_ITEM_HIGH_VALUE %>" <% if (item.getValue() == TracingConstants.LFC_ITEM_HIGH_VALUE) { %>selected<% } %> ><bean:message key="lfc.high.value" /></option>
			              			<option value="<%=TracingConstants.LFC_ITEM_LOW_VALUE %>" <% if (item.getValue() == TracingConstants.LFC_ITEM_LOW_VALUE) { %>selected<% } %> ><bean:message key="lfc.low.value" /></option>
			              		</select>
	         				</td>
	         				<td colspan=2>
	         					<bean:message key="colname.lfc.condition" />
	         					<br>
		              			<html:select name="item" styleId="itemcondition_<%=i %>"  property="itemCondition" styleClass="dropdown" >
			              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
			              			<html:option value="<%=TracingConstants.LFC_CONDITION_NEW%>"><bean:message key="option.lfc.condition.new" /></html:option>
			              			<html:option value="<%=TracingConstants.LFC_CONDITION_GOOD %>"><bean:message key="option.lfc.condition.good" /></html:option>
			              			<html:option value="<%=TracingConstants.LFC_CONDITION_AVERAGE %>"><bean:message key="option.lfc.condition.average" /></html:option>
			              			<html:option value="<%=TracingConstants.LFC_CONDITION_POOR %>"><bean:message key="option.lfc.condition.poor" /></html:option>
		              			</html:select>
	         				</td>
	         			</tr>
         				<tr>
	         				<td>
	         					<bean:message key="colname.lf.brand" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].brand" id="itembrand_<%=i %>" class="textfield" value="<%=item.getBrand() == null ? "" : item.getBrand() %>" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.serial" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].serialNumber"  id="itemserial_<%=i %>" class="textfield" value="<%=item.getSerialNumber() == null ? "" : item.getSerialNumber() %>" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.model" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].model" id="itemmodel_<%=i %>"  class="textfield" value="<%=item.getModel() == null ? "" : item.getModel() %>" />
	         				</td>
	         			</tr>
	         			<tr>
	         				<td>
	         					<bean:message key="colname.lf.category" />&nbsp;<span class="reqfield">*</span>
	         					<br>
	         					<select name="item[<%=i %>].category" class="dropdown"  id="category_<%=i %>" onchange="updateSubCategories('category_<%=i %>', <%=i %>);" id="category_<%=i %>" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							LFCategory category;
	         							for (int j = 0; j < categoryList.size(); ++j) {
	         								category = (LFCategory) categoryList.get(j);
	         						%>
	         								<option value="<%=category.getId() %>" <% if (category.getId() == item.getCategory()) { %>selected<% } %>><%=category.getDescription() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.subcategory" />
	         					<br>
	         					<select name="item[<%=i %>].subCategory"  class="dropdown" id="subcategories_<%=i %>" onchange="" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList subCategories = new ArrayList();
	         							for (int j = 0; j < categoryList.size(); ++j) {
	         								category = (LFCategory) categoryList.get(j);
	         								if (category.getId() == item.getCategory()) {
	         									subCategories = new ArrayList(category.getSubcategories());
	         									break;
	         								}
	         							}

	         							for (int j = 0; j < subCategories.size(); ++j) {
	         								subCategory = (LFSubCategory) subCategories.get(j);
	         						%>
	         								<option value="<%=subCategory.getId() %>" <% if (subCategory.getId() == item.getSubCategory()) { %>selected<% } %>><%=subCategory.getDescription() %></option>
	         						<%
	         							}
	         						%>
	         					</select>
	         					<script>
	         						setSelectedSubCategory(<%=item.getCategory()%>, <%=item.getSubCategory()%>, 'subcategories_<%=i %>');
	         					</script>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.color" />
	         					<br>
	         					<select name="item[<%=i %>].color" class="dropdown"  id="itemcolor_<%=i %>"  >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList colorList = (ArrayList) request.getSession().getAttribute("lfcolorlist");
	         							LabelValueBean color;
	         							for (int j = 0; j < colorList.size(); ++j) {
	         								color = (LabelValueBean) colorList.get(j);
	         						%>
	         								<option value="<%=color.getValue() %>" <% if (item.getColor() != null && item.getColor().equals(color.getValue())) { %>selected<% } %> ><%=color.getLabel() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         			</tr>
         				<tr>
	         				<td>
	         					<bean:message key="colname.lf.size" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].size"  id="itemsize_<%=i %>"  class="textfield" value="<%=item.getSize() == null ? "" : item.getSize() %>" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.lostPhoneNumber" /><br/><bean:message key="colname.lf.phone.logic"/>
	         					<br>
	         					 <input type="text" name="item[<%=i %>].dispCountry"   id="iteminter_<%=i %>" size="2" class="textfield" value="<%=item.getDispCountry()%>" />
	         					 <input type="text" name="item[<%=i %>].dispArea" size="3"  id="itemarea_<%=i %>"   class="textfield" value="<%=item.getDispArea()%>" />
	         					 <input type="text" name="item[<%=i %>].dispExchange" size="3"  id="itemexchange_<%=i %>"  class="textfield" value="<%=item.getDispExchange()%>" />
	         					 <input type="text" name="item[<%=i %>].dispLine" size="4"  id="itemline_<%=i %>"  class="textfield" value="<%=item.getDispLine()%>" /><br/>
	         					 <bean:message key="colname.lf.phone.extension" />: <input type="text"  id="itemext_<%=i %>" name="item[<%=i %>].dispExtension" size="4" class="textfield" value="<%=item.getDispExtension()%>" />
	         					<!-- <input type="text" name="item[<%=i %>].dispPhone" class="textfield" value="<%=item.getDispPhone()%>" />-->
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.caseColor" />
	         					<br>
	         					<select name="item[<%=i %>].caseColor" class="dropdown"  id="itemcasecolor_<%=i %>"  >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList caseColorList = (ArrayList) request.getSession().getAttribute("lfcolorlist");
	         							LabelValueBean caseColor = new LabelValueBean();
	         							for (int j = 0; j < caseColorList.size(); ++j) {
	         								caseColor = (LabelValueBean) caseColorList.get(j);
	         						%>
	         								<option value="<%=caseColor.getValue() %>" <% if (item.getCaseColor() != null && item.getCaseColor().equals(caseColor.getValue())) { %>selected<% } %> ><%=caseColor.getLabel() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
	         				</td>
	         			</tr>
	         			<tr>
	         				<td colspan=2>
	         					<bean:message key="colname.lf.description" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].description"   id="itemdesc_<%=i %>" class="textfield" size="250" style="width: 95%;" value="<%=item.getDescription() == null ? "" : item.getDescription() %>" />
	         				</td>
	         				<td colspan=1>
	         					<bean:message key="colname.lfc.weight" />
	         					<br>
	         					<input type="text" id="weight" name="item[<%=i %>].weight"  id="itemweight_<%=i %>"  class="textfield" value="<%=item.getWeight()%>" />
	         				</td>
	         			</tr>
	         			<tr>
	         				<td colspan=3>
	         					<bean:message key="colname.lf.long.description" />
	         					<br>
	         					<textarea name="item[<%=i %>].longDescription" cols="80" rows="3" class="textfield"   id="itemlongdesc_<%=i %>" 
	         					onblur="textCounter2(this.form.elements['item[<%=i %>].description'],2000);" 
	         					onkeydown="textCounter2(this.form.elements['item[<%=i %>].description'],2000);" 
	         					onkeyup="textCounter2(this.form.elements['item[<%=i %>].description'],2000);"
	         					><%=item.getLongDescription() == null ? "" : item.getLongDescription() %></textarea>
	         				</td>
	         			</tr>
	         		<% } %>
         			</logic:iterate>
         		</table>
         		<br/>
         		<h1 class="green">
					<bean:message key="header.delivery.information" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<table class="<%=cssFormClass %>" cellpadding=0 cellspacing=0 >
				<% if (haveDeliveryInformation) { %>
					<tr >
						<td class="header" style="width:33%;" >
							<bean:message key="lf.colname.delivery.summary" />
						</td>
						<td class="header" style="width:33%;" >
							<bean:message key="colname.lf.delivered.date" />
						</td>
						<td class="header" style="width:33%;" >
							<bean:message key="colname.lf.action" />
						</td>
					</tr>
					<tr>
	         		<logic:equal name="foundItemForm" property="foundItem.disposition.status_ID" value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>" >
						<td>
							<bean:message key="colname.lf.tracking.number" />:&nbsp;
							<bean:write name="foundItemForm" property="foundItem.trackingNumber" />
						</td>
					</logic:equal>
	         		<logic:equal name="foundItemForm" property="foundItem.deliveryRejected" value="true" >
						<td>
							<bean:message key="lf.delivery.rejected" />
						</td>
					</logic:equal>
	         		<logic:equal name="foundItemForm" property="foundItem.disposition.status_ID" value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" >
						<td>
							<bean:message key="lf.picked.up" />
						</td>
					</logic:equal>
					<td>
						<bean:write name="foundItemForm" property="disDeliveredDate" />
					</td>
					<td>
						<a href='create_found_item.do?undo=1&itemId=<bean:write name="foundItemForm" property="foundItem.id" />'><bean:message key="lf.undo" /></a>
					</td>
					</tr>
				<% } else { %>
					<tr>
						<td colspan=3 class="header" >
							<bean:message key="lf.colname.enter.delivery.information" />
						</td>
					</tr>
					<tr>
						<td style="width:50%;" >
							<bean:message key="colname.lf.tracking.number" />:&nbsp;
							<html:text name="foundItemForm" property="foundItem.trackingNumber" size="20" styleClass="textfield" styleId="trackingNumber" />
						</td>
						<td  style="width:25%;" >
							<a href='create_found_item.do?deliveryRejected=1&itemId=<bean:write name="foundItemForm" property="foundItem.id" />'><bean:message key="lf.delivery.rejected"/></a>
						</td>
						<td style="width:25%;" >
							<a href='create_found_item.do?pickup=1&itemId=<bean:write name="foundItemForm" property="foundItem.id" />'><bean:message key="lf.picked.up"/></a>
						</td>
					</tr>
				<% } %>
					<tr>
						<td>
							<bean:message key="lf.colname.check.number" />:&nbsp;
							<% if (dispositionId == TracingConstants.LF_DISPOSITION_PICKED_UP || deliveryRejected) { %>
								<html:text name="foundItemForm" property="found.checkNumber" size="10" styleClass="disabledtextfield" styleId="checkNumber" />
							<% } else { %>
								<html:text name="foundItemForm" property="found.checkNumber" size="10" styleClass="textfield" styleId="checkNumber" />
							<% } %>
						</td>
						<td colspan=2 >
							<bean:message key="lf.colname.check.amount" />:&nbsp;
							<% if (dispositionId == TracingConstants.LF_DISPOSITION_PICKED_UP || deliveryRejected) { %>
								<html:text name="foundItemForm" property="dispCheckAmount" size="10" styleClass="disabledtextfield" styleId="checkAmount" />
							<% } else { %>
								<html:text name="foundItemForm" property="dispCheckAmount" size="10" styleClass="textfield" styleId="checkAmount" />
							<% } %>
						</td>
					</tr>
				</table>
				<br/>
         		<h1 class="green">
					<bean:message key="header.remarks" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" />
			<a name="remarks" ></a>
			<jsp:include page="/pages/lostandfound/remark_found.jsp" />
			
      <center><html:submit property="addremark" styleId="button">
        <bean:message key="button.add_remark" />
      </html:submit></center>
      <br>
      <br>
				<center>
					<html:hidden property="save" value="" disabled="true" />
					<html:button property="saveButton" styleId="button" onclick="if (validateFoundItemForm(this.form)) {this.form.save.disabled = false; this.form.submit();} else { this.form.save.disabled = true; return false; }">
						<bean:message key="button.save" />
					</html:button>
				</center>
				<script>
					fieldChanged('state');
					fieldChanged('country');
					<% if (request.getAttribute("remark") != null) { %>
						document.location.href="#remarks";
					<% } %>
					getStations();

					var stationList=document.getElementById("foundLocationId");
					var matchid=<%=request.getAttribute("stationID")%>
					if(matchid!=null){
						stationList.value=matchid;
					}
             	</script>
   			</div>
   		</td>
   	</tr>
</html:form>
