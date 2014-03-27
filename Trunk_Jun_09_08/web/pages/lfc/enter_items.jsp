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
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryContainer" %>
<%@ page import="com.bagnet.nettracer.tracing.history.FoundHistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Locale" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
 	 
 	ArrayList categoryList = (ArrayList) request.getSession().getAttribute("lfcategorylist");
 	ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(a.getCurrentlocale()));
 	
 	int itemCount = PropertyBMO.getValueAsInt("lfc.item.entry.display.count");
 	ArrayList<FoundHistoryObject> history = ((HistoryContainer) request.getSession().getAttribute("historyContainer")).getNewestFoundHistoryItems(itemCount);
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();
	
	
	function getSubCategory() {
		var catList=document.getElementById("category");
		var subCatList=document.getElementById("subCategory");
		var selectedCategory=catList.options[catList.selectedIndex].value;
		subCatList.options.length=1;
		
		subCatList.options[0]=new Option("<bean:message key="option.lf.please.select" />","0",true,false);
		<logic:iterate indexId="i" id="cList" name="lfcategorylist"  type="com.bagnet.nettracer.tracing.db.lf.LFCategory" >
		if(<%=cList.getId()%>==selectedCategory)
			{	
				<% String source="lfsubcategorylist"+cList.getId();%>
				<logic:iterate indexId="j" id="scList" name="<%=source%>"  type="com.bagnet.nettracer.tracing.db.lf.LFSubCategory" >
					subCatList.options[<%=j+1%>]=new Option("<%=scList.getDescription()%>","<%=scList.getId()%>",false,false);
				</logic:iterate>
			}
		</logic:iterate>
		
		/*document.getElementById("subCategory"+selectedCategory).style.display="inline";*/
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
	
	function displayContactDiv() {
		jQuery('#contactInfoDiv').show();
		document.getElementById('button').disabled = true;
		document.getElementById('lastName').focus();
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
	
	function validateItemEntry(form) {
		for (var i = 0; i < form.length; ++i) {
		
			currentElement = form.elements[i];
			elementName = currentElement.name;

			if (elementName == "disReceivedDate") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lfc.item.received.date") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.companyId") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lf.found.company") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.locationId") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lfc.found.station") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.barcode") {
				if (currentElement.value.length == 0) {
					alert("<%= (String)bundle.getString("colname.lfc.item.id") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				} else if (isNaN(currentElement.value) || currentElement.value.indexOf('-') != -1 || currentElement.value.indexOf('.') != -1) {
					alert("<%= (String)bundle.getString("colname.lfc.item.id") %>" + " <%= (String)bundle.getString("error.validation.validNumber") %>");
			        currentElement.focus();
			        return false;
				}
			} else if (elementName == "found.item.category") {
				if (currentElement.value == 0) {
					alert("<%= (String)bundle.getString("colname.lfc.category") %>" + " <%= (String)bundle.getString("error.validation.isRequired") %>");
			        currentElement.focus();
			        return false;
				}
			}
			
		}
		return true;
	}
	
	function updateItemLocation(moveId, divId, historyId) {
		o = document.enterItemsForm;
		o.fhoId.value = historyId;
		o.divId.value = divId;
		document.getElementById(moveId).innerHTML = "<bean:message key="ajax.please_wait" />";
		postForm("enterItemsForm", true, function (req) {
			o.fhoId.value = "";
			o.divId.value = "";
			document.getElementById(moveId).innerHTML = req.responseText; 
			updateSaveButton();
		});
	}
	
	function updateSaveButton() {
		document.getElementById("saveButton").disabled = false;
		for (var j = 0; j < <%=itemCount %>; ++j) {
			var divId = "moveDiv_" + j;
			var div = document.getElementById(divId);
			if (div != null) {
				document.getElementById("saveButton").disabled = true;
			} else {
				var sumDivId = "summaryItem_" + j;
				var sumDiv = document.getElementById(sumDivId);
				if (sumDiv != null) {
					sumDiv.className = "summaryItem";
				}
			}
		}
	}
	
</SCRIPT>
<html:form focus="found.barcode" action="enter_items.do" method="post" onsubmit="return validateItemEntry(this);" >
<input type="hidden" name="divId">
<input type="hidden" name="fhoId">
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.item.entry" />
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
     			<div id="itementryleft">
     				<center>
	     				<font color=red>
		                  <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/><br/></html:messages></logic:messagesPresent>
		                </font>
	                </center>
     				<h1 class="green">
			        	<bean:message key="header.entry.information" />
			        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
			        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" />
				<table class="<%=cssFormClass %>" cellpadding=0 cellspacing=0 >
					<tr>
   						<td style="width:33%;"  colspan=2>
							<bean:message key="colname.lfc.item.received.date" />
							(<%= a.getDateformat().getFormat() %>)&nbsp;<span class="reqfield">*</span>
							<html:text property="disReceivedDate" size="12" maxlength="11" styleClass="textfield" styleId="disReceivedDate" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select2(document.enterItemsForm,'disReceivedDate','calendar','<%= a.getDateformat().getFormat() %>'); return false;">
						</td>
						<!-- Put in a Company drop down list that determines the available stations, changes with JS -->
						<td style="width:34%%;" colspan=2>
							<bean:message key="colname.lf.found.company" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<% if(a.getSubcompany()!=null){ %>
         					<html:select name="enterItemsForm" property="found.companyId" disabled="true" styleClass="dropdown" styleId="foundCompanyId" onchange="getStations();">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="subcompanyCode" />
		            		</html:select>
         					<% } else { %>
         					<html:select name="enterItemsForm" property="found.companyId" styleClass="dropdown" styleId="foundCompanyId" onchange="getStations();">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="subcompanyCode" />
		            		</html:select>
         					<% } %>
           				</td>
						<td style="width:33%;" colspan=2>
							<bean:message key="colname.lf.found.location" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="enterItemsForm" property="found.locationId" styleClass="dropdown" styleId="foundLocationId" >
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationList" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
           				</td>
           			</tr>
           			<tr>
					    <td style="width:50%;" colspan=3>
					        <bean:message key="colname.lf.segment.flight" />
					        <br>
					        <html:text name="enterItemsForm" property="found.flightNumber" size="10" maxlength="20" styleClass="textfield" styleId="flightNumber"/>
					    </td>
           				<td style="width:50%;" colspan=3>
           					<bean:message key="colname.lfc.value" />
           					<br>
           					<html:select name="enterItemsForm" property="found.item.value" styleId="value" styleClass="dropdown" >
      							<html:option value="<%=String.valueOf(TracingConstants.LFC_ITEM_LOW_VALUE) %>" ><bean:message key="lfc.low.value" /></html:option>
      							<html:option value="<%=String.valueOf(TracingConstants.LFC_ITEM_HIGH_VALUE) %>" ><bean:message key="lfc.high.value" /></html:option>
           					</html:select>
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
     				<table class="<%=cssFormClass %>" cellpadding=0 cellspacing=0 >
     					<tr>
     						<td style="width:25%;">
								<bean:message key="colname.lfc.item.id" />&nbsp;<span class="reqfield">*</span>
								<br>
								<html:text property="found.barcode" size="12" styleId="barcode" styleClass="textfield" />
							</td>
							<td style="width:33%;">
	           					<bean:message key="colname.lfc.category" />&nbsp;<span class="reqfield">*</span>
	           					<br/>
	           					<input type="hidden" name="changesubcategory">
	           					<html:select name="enterItemsForm" property="found.item.category" styleClass="dropdown" styleId="category" onchange="getSubCategory();" >
	           						<html:option value="0"><bean:message key="option.lf.please.select" /></html:option>
	           						<html:options collection="lfcategorylist" property="id" labelProperty="description" />
	           					</html:select>
	           				</td>
	           				<td style="width:41%;">
	           					<div id="subcategorydiv">
	           					<bean:message key="colname.lfc.subcategory" />
	           					<br/>
	           					<html:select name="enterItemsForm" property="found.item.subCategory" styleClass="dropdown" styleId="subCategory">
	           						<option value="0"><bean:message key="option.lf.please.select" /></option>
	           						<html:options collection="lfsubcategorylist" property="id" labelProperty="description" style="display:none"/>
	           					</html:select>
	           					</div>
	           				</td>
     					</tr>
     					<tr>
     						<td style="width:25%;">
     							<bean:message key="colname.lfc.brand" />
     							<br>
     							<html:text name="enterItemsForm" property="found.item.brand" size="12" styleClass="textfield" styleId="brand" />
     						</td>
     						<td style="width:33%;">
     							<bean:message key="colname.lfc.model" />
     							<br>
     							<html:text name="enterItemsForm" property="found.item.model" size="12" styleClass="textfield" styleId="model" />
     						</td style="width:41%;">
     						<td>
    							<bean:message key="colname.lfc.serial.number" />
     							<br>
     							<html:text name="enterItemsForm" property="found.item.serialNumber" size="12" styleClass="textfield" styleId="serialNumber" />
     						</td>
   						</tr>
   						<tr>
     						<td style="width:25%;">
	         					<bean:message key="colname.lfc.color" />
	         					<br>
	         					<html:select name="enterItemsForm" property="found.item.color" styleClass="dropdown" styleId="color" >
	           						<option value=""><bean:message key="option.lf.please.select" /></option>
	      							<html:options collection="lfcolorlist" property="value" labelProperty="label" />
	           					</html:select>
	         				</td>
     						<td style="width:33%;">
	         					<bean:message key="colname.lfc.case.color" />
	         					<br>
	         					<html:select name="enterItemsForm" property="found.item.caseColor" styleClass="dropdown" styleId="caseColor" >
	           						<option value=""><bean:message key="option.lf.please.select" /></option>
	      							<html:options collection="lfcolorlist" property="value" labelProperty="label" />
	           					</html:select>
	         				</td>
     						<td style="width:41%;">
    							<bean:message key="colname.lf.lostPhoneNumber" /><br/><bean:message key="colname.lf.phone.logic"/>
     							<br>
		              		<html:text name="enterItemsForm" property="disFoundInternationalNumber" size="1" maxlength="10" styleClass="textfield" />
		              		<html:text name="enterItemsForm" property="disFoundAreaNumber" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="enterItemsForm" property="disFoundExchangeNumber" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="enterItemsForm" property="disFoundLineNumber" size="4" maxlength="10" styleClass="textfield" /><br/>
		              		<bean:message key="colname.lf.phone.extension" />: <html:text name="enterItemsForm" property="disFoundExtension" size="3" maxlength="4" styleClass="textfield" />
     						</td>
     					</tr>
     					<tr>
     						<td colspan=2>
     							<bean:message key="colname.lfc.description" />
     							<br>
     							<html:text name="enterItemsForm" property="found.item.description" size="40" maxlength="30" styleClass="textfield" styleId="description" />
     						</td>
     						<td>
     							<bean:message key="colname.lfc.weight" />
     							<br>
     							<html:text name="enterItemsForm" property="found.item.weight" size="15" maxlength="30" styleClass="textfield" styleId="description" value="1.0"/>
     						</td>
     					</tr>
     				</table>
     				<br/>
     				<div id="contactInfoDiv" style="margin:0;padding:0;display:none;" >
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
					              <html:text name="enterItemsForm" property="found.client.lastName" styleId="lastName" size="20" maxlength="20" styleClass="textfield" styleId="lastName" />
					            </td>
					            <td nowrap colspan="2">
					              <bean:message key="colname.first_name.req" />
					              <br>
					              <html:text name="enterItemsForm" property="found.client.firstName" size="20" maxlength="20" styleClass="textfield" styleId="firstName" />
					            </td>
					            <td>
					              <bean:message key="colname.mid_initial" />
					              <br>
					              <html:text name="enterItemsForm" property="found.client.middleName" size="1" maxlength="1" styleClass="textfield" styleId="middleName" />
					            </td>
					        </tr>
			            	<tr>
				                <td colspan=2>
				                  <bean:message key="colname.street_addr1" />
				                  <br>
				                  <html:text name="enterItemsForm" property="found.client.address.decryptedAddress1" size="30" maxlength="50" styleClass="textfield" styleId="addr1" />
				                </td>
				                <td colspan=3>
				                  <bean:message key="colname.street_addr2" />
				                  <br>
				                  <html:text name="enterItemsForm" property="found.client.address.decryptedAddress2" size="30" maxlength="50" styleClass="textfield" styleId="addr2" />
				                </td>
				              </tr>
				              <tr>
				                <td>
				                  <bean:message key="colname.city" />
				                  <br>
				                  <html:text name="enterItemsForm" property="found.client.address.decryptedCity" size="10" maxlength="50" styleClass="textfield" styleId="city" />
				                </td>
				                <td>
				                  <bean:message key="colname.state" />
				                  <br />
				                  <html:select name="enterItemsForm" property="found.client.address.decryptedState" styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" styleId="state">
				                  	<html:option value="">
					                    <bean:message key="select.none" />
				                    </html:option>
					                <html:options collection="statelist" property="value" labelProperty="label" />
				                  </html:select>
				                </td>
				                <td>
				                  <bean:message key="colname.province" />
				                  <br />
				                  <html:text name="enterItemsForm" property="found.client.address.decryptedProvince" size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
				                </td>
				                <td>
				                  <bean:message key="colname.zip" />
				                  <br>
				                  <html:text name="enterItemsForm" property="found.client.address.decryptedZip" size="11" maxlength="11" styleClass="textfield" styleId="zip"/>
				                </td>
				                <td>
				                  <bean:message key="colname.country" />
				                  <br>
				                  <html:select name="enterItemsForm" property="found.client.address.country" styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
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
						              		
				              		<html:text name="enterItemsForm" property="primaryInternationalNumber" size="2" maxlength="10" styleClass="textfield" />
				              		<html:text name="enterItemsForm" property="primaryAreaNumber" size="3" maxlength="10" styleClass="textfield" />
				              		<html:text name="enterItemsForm" property="primaryExchangeNumber" size="3" maxlength="10" styleClass="textfield" />
				              		<html:text name="enterItemsForm" property="primaryLineNumber" size="4" maxlength="10" styleClass="textfield" /><br/>
				              		<html:select name="enterItemsForm" property="primaryNumberType" styleClass="dropdown" >
				              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
				              		</html:select>
				              		<bean:message key="colname.lf.phone.extension" />: <html:text name="enterItemsForm" property="primaryExtension" size="3" maxlength="4" styleClass="textfield" />
				              	</td>
				              	<td colspan="3">
				              		<bean:message key="colname.lf.secondary.phone" /><br/>
		              				<bean:message key="colname.lf.phone.logic" />
				              		<br/>
				              		<html:text name="enterItemsForm" property="secondaryInternationalNumber" size="2" maxlength="10" styleClass="textfield" />
				              		<html:text name="enterItemsForm" property="secondaryAreaNumber" size="3" maxlength="10" styleClass="textfield" />
				              		<html:text name="enterItemsForm" property="secondaryExchangeNumber" size="3" maxlength="10" styleClass="textfield" />
				              		<html:text name="enterItemsForm" property="secondaryLineNumber" size="4" maxlength="10" styleClass="textfield" /><br/>
				              		<html:select name="enterItemsForm" property="secondaryNumberType" styleClass="dropdown" >
				              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
				              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
				              		</html:select>
				              		<bean:message key="colname.lf.phone.extension" />: <html:text name="enterItemsForm" property="secondaryExtension" size="3" maxlength="4" styleClass="textfield" />
				              	</td>
				              </tr>
				              <tr>
				              	<td colspan="5" >
				              		<bean:message key="colname.lf.email" />
				              		<br />
				              		<html:text name="enterItemsForm" property="found.client.decryptedEmail" size="30" maxlength="100" styleClass="textfield" styleId="email" />
				              	</td>
				              </tr>
						</table>
     				</div>
				<center>
					<input id="button" type="button" value='<bean:message key="lfc.button.contact.info" />' onClick="displayContactDiv();">
					&nbsp;&nbsp;
					<html:submit property="save" styleId="saveButton" styleClass="button" >
						<bean:message key="button.save" />
					</html:submit>
				</center>
     			</div>
     			<div id="itementryright" >
     				<h1 class="green" >
			        	<bean:message key="header.item.entry.summary" />
			        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
			        </h1>
   					<br/>
     				<% 
	     				for (int i = history.size() - 1; i >= 0; --i) { 
	 						FoundHistoryObject fho = history.get(i);
	 						int status = fho.getFound().getEntryStatus();
							boolean needsVerification = status == TracingConstants.LF_STATUS_VERIFICATION_NEEDED || (fho.isHasTraceResults() && status != TracingConstants.LF_STATUS_MOVED);
						  	String cssClass = needsVerification ? "summaryActionItem" : "summaryItem";
	 						
     					%>
						<div id="summaryItem_<%=i %>" class="<%=cssClass %>">
							<span style="font-weight: bold;" ><bean:message key="colname.lfc.item.id" />:&nbsp;</span><a href="create_found_item.do?barcode=<%=fho.getFound().getBarcode() %>"><%=fho.getFound().getBarcode() %></a>
							<!-- br-->
							<span style="font-weight: bold;" ><bean:message key="lfc.summary.desc" />:&nbsp;</span><%=fho.getFound().getSummaryDesc() %>
							<!-- br-->
							<span style="font-weight: bold;" ><bean:message key="lfc.item.entry.status" />:&nbsp;</span>
							<%
								if (status == TracingConstants.LF_STATUS_ENTERED) {
							%>
									<bean:message key="lf.status.entered" />
							<%
								} else if (status == TracingConstants.LF_STATUS_VERIFICATION_NEEDED) {
							%>
									<bean:message key="lf.status.verification.needed" />
							<%
								} else if (status == TracingConstants.LF_STATUS_MOVED) {
							%>
									<bean:message key="lf.status.moved" />
							<%
								}
							%>
							<% 	if (needsVerification) { %>
								<div id="moveDiv_<%=i %>" >
									<span style="font-weight: bold;" ><bean:message key="lfc.bin.id" />:&nbsp;</span>
									<html:text name="enterItemsForm" property="found.binId" size="5" maxlength="50" styleClass="textfield" styleId="binId" />
									<center>
										<input class="button" type="button" value='<bean:message key="lfc.button.item.moved" />' onClick="updateItemLocation('summaryItem_<%=i %>','<%=i %>','<%=fho.getUniqueId() %>')">
									</center>
									<script>
										document.getElementById('saveButton').disabled = true;
									</script>
								</div>
							<%	} %>
						</div>
     				<% 
     				} %>
     			</div>
     			<script>
					fieldChanged('state');
					fieldChanged('country');
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
