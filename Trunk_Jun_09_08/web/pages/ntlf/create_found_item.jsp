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

	function displayPPUReceipt(fileName, foundId) {
		window.open("ntlf_create_found_item.do?displayReceipt="+fileName+"&foundId="+foundId, '', 'width=600,height=800,resizable=yes');
	}
	
</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="found.barcode" action="ntlf_create_found_item.do" method="post" onsubmit="return validateFoundItemForm(this);" >
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
     			<logic:messagesPresent message="true">
				<%
					Object success = request.getAttribute("success");
					String color = (success != null && ((Boolean) success)) ? "green" : "red";						
				%>
				<span>
					<font color="<%=color %>" >
						<html:messages id="msg" message="true">
							<bean:write name="msg"/>
							<br><br>
						</html:messages>
					</font>
				</span>
			</logic:messagesPresent>
         		<logic:present name="reportfile" scope="request">
            		<center><a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a></center>
           		</logic:present>
				<h1 class="green">
		        	<bean:message key="header.report.information" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" />
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
       				<tr>

						<td>
							<bean:message key="colname.lf.report.id" />&nbsp;<span class="reqfield">*</span>
         					<br>
							<html:text name="foundItemForm" property="found.id" disabled="true" styleClass="disabledtextfield" />
         				</td>
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
         					<html:select name="foundItemForm" property="found.statusId" styleClass="dropdown" disabled="true">
         						<html:option value="-1"><bean:message key="option.lf.please.select" /></html:option>
         						<html:options collection="lfstatuslist" property="status_ID" labelProperty="description" />
         					</html:select>
         				</td>
						<td>
               				<bean:message key="colname.lf.disposition" />
               				<br>
               				<html:select name="foundItemForm" property="foundItem.disposition.status_ID" styleClass="dropdown" disabled="true" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>"><bean:message key="none" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_SENT_TO_LFC) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_SENT_TO_LFC) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_REMOVED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_REMOVED) %>" /></html:option>
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
			              <bean:message key="colname.last_name.found.req" />
			              <br>
			              <html:text name="foundItemForm" property="found.client.lastName" styleId="lastname" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td nowrap colspan="2">
			              <bean:message key="colname.first_name.found.req" />
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
		              	<td colspan="5" >
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="foundItemForm" property="found.client.decryptedEmail" styleId="email"  size="35" maxlength="100" styleClass="textfield" />
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
       					if (item.getType() == TracingConstants.LF_TYPE_FOUND) { 
							dispositionId = item.getDispositionId();
       						haveDeliveryInformation = (dispositionId != TracingConstants.LF_DISPOSITION_OTHER);
       						%>
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
	         				<td>
	         					<bean:message key="colname.lfc.condition" />
	         					<br>
		              			<select name="item[<%=i %>].itemCondition" id="itemcondition_<%=i %>" class="dropdown" >
			              			<option value=""><bean:message key="option.lf.please.select" /></option>
			              			<option value="<%=TracingConstants.LFC_CONDITION_NEW%>" <% if (TracingConstants.LFC_CONDITION_NEW.equals(item.getItemCondition())) { %>selected<% } %> ><bean:message key="option.lfc.condition.new" /></option>
			              			<option value="<%=TracingConstants.LFC_CONDITION_GOOD %>" <% if (TracingConstants.LFC_CONDITION_GOOD.equals(item.getItemCondition())) { %>selected<% } %> ><bean:message key="option.lfc.condition.good" /></option>
			              			<option value="<%=TracingConstants.LFC_CONDITION_AVERAGE %>" <% if (TracingConstants.LFC_CONDITION_AVERAGE.equals(item.getItemCondition())) { %>selected<% } %> ><bean:message key="option.lfc.condition.average" /></option>
			              			<option value="<%=TracingConstants.LFC_CONDITION_POOR %>" <% if (TracingConstants.LFC_CONDITION_POOR.equals(item.getItemCondition())) { %>selected<% } %> ><bean:message key="option.lfc.condition.poor" /></option>
		              			</select>
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.size" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].size"  id="itemsize_<%=i %>"  class="textfield" value="<%=item.getSize() == null ? "" : item.getSize() %>" />
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
	         				<td colspan=2>
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
	         		<logic:equal name="foundItemForm" property="foundItem.disposition.status_ID" value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_SENT_TO_LFC) %>" >
						<td>
							<bean:message key="colname.lf.tracking.number" />:&nbsp;
							<bean:write name="foundItemForm" property="foundItem.trackingNumber" />
						</td>
					</logic:equal>
	         		<logic:equal name="foundItemForm" property="foundItem.disposition.status_ID" value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" >
						<td>
							<bean:message key="lf.picked.up" />
						</td>
					</logic:equal>
	         		<logic:equal name="foundItemForm" property="foundItem.disposition.status_ID" value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_REMOVED) %>" >
						<td>
							<bean:message key="lf.removed" />:&nbsp;
							<bean:write name="foundItemForm" property="foundItem.removalReason" />
						</td>
					</logic:equal>
					<td>
						<bean:write name="foundItemForm" property="disDeliveredDate" />
					</td>
					<td>
						<a href='ntlf_create_found_item.do?undo=1&itemId=<bean:write name="foundItemForm" property="foundItem.id" />'><bean:message key="lf.undo" /></a>
						<logic:equal name="foundItemForm" property="foundItem.disposition.status_ID" value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" >
							<logic:notEmpty name="foundItemForm" property="found.receiptFileName" >
								,&nbsp;
								<a href="#" onclick="displayPPUReceipt('<bean:write name="foundItemForm" property="found.receiptFileName" />', <bean:write name="foundItemForm" property="found.id" />)"><bean:message key="print.ppu" /></a>
							</logic:notEmpty>
						</logic:equal>
					</td>
					</tr>
				<% } else { %>
					<tr>
						<td colspan=3 class="header" >
							<bean:message key="lf.colname.change.item.disposition" />
						</td>
					</tr>
					<tr>
						<td style="width:50%;" >
							<bean:message key="colname.lf.tracking.number" />:&nbsp;
							<html:text name="foundItemForm" property="foundItem.trackingNumber" size="20" styleClass="textfield" styleId="trackingNumber" />
						</td>
						<td style="width:25%;" >
							<html:hidden property="deliver" value="" disabled="true" />
							<a href="###" onclick="if (document.getElementById('trackingNumber').value.length > 0) { document.foundItemForm.deliver.disabled = false; document.foundItemForm.submit(); } 
								else {window.alert('<bean:message key="colname.lf.tracking.number" /> <bean:message key="error.validation.isRequired" />')}"><bean:message key="lf.deliver.to.customer"/></a>
						</td>
						<td style="width:25%;" >
							<html:hidden property="sendToLFC" value="" disabled="true" />
							<a href="###" onclick="if (document.getElementById('trackingNumber').value.length > 0) { document.foundItemForm.sendToLFC.disabled = false; document.foundItemForm.submit(); } 
								else {window.alert('<bean:message key="colname.lf.tracking.number" /> <bean:message key="error.validation.isRequired" />')}"><bean:message key="lf.send.to.lfc"/></a>
						</td>
					</tr>
					<tr>
						<td style="width:50%;" >
							<bean:message key="colname.lf.removal.reason" />:&nbsp;
							<html:text name="foundItemForm" property="foundItem.removalReason" size="20" maxlength="200" styleClass="textfield" styleId="removalReason" />
						</td>
						<td style="width:25%;" >
							<html:hidden property="remove" value="" disabled="true" />
							<a href="###" onclick="if (document.getElementById('removalReason').value.length > 0) { document.foundItemForm.remove.disabled = false; document.foundItemForm.submit(); } 
							else {window.alert('<bean:message key="colname.lf.removal.reason" /> <bean:message key="error.validation.isRequired" />')}"><bean:message key="lf.remove.item"/></a>
						</td>
						<td style="width:25%;" >
							<html:hidden property="pickup" value="" disabled="true" />
							<a href="###" onclick="document.foundItemForm.pickup.disabled = false; document.foundItemForm.submit();"><bean:message key="lf.picked.up"/></a>
						</td>
					</tr>
				<% } %>
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

					var stationList=document.getElementById("foundLocationId");
					var matchid=<%=request.getAttribute("stationID")%>
					if(matchid!=null){
						stationList.value=matchid;
					}
             	</script>
   			</div>
   		</td>
   	</tr>
   	<logic:present name="receiptName" scope="request">
	    <script language=javascript>
	    	var fileName = '<%=(String) request.getAttribute("receiptName") %>';
   			var foundId = document.getElementById('found.id').value;
   			displayPPUReceipt(fileName, foundId);
	    </script>
    </logic:present>
</html:form>
