<%@page import="com.bagnet.nettracer.tracing.db.Station"%>
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
 	ArrayList stationList = (ArrayList) request.getSession().getAttribute("stationlist");
 	
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
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
	
	function setFoundId(foundId, matchItem, itemId) {
		document.getElementById('foundId').value = foundId;
		document.getElementById('matchItem').value = matchItem;
		document.getElementById('itemId').value = itemId;
	}
	
	function textCounter2(field, maxlimit) {
	    if (field.value.length > maxlimit) {
	      field.value = field.value.substring(0, maxlimit);
	    }
	}
	
</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="lost.id" action="create_lost_report.do" method="post" >
<input type="hidden" name="delete_these_elements" value="" />
<html:hidden property="matchItem" styleId="matchItem" value="" />
<html:hidden property="itemId" styleId="itemId" value="" />
<html:hidden property="foundId" styleId="foundId" value="" />
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.lost.report" />
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
   				<center><font color="red">
         			<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/><br/></html:messages></logic:messagesPresent>
         		</font></center>
				<h1 class="green">
					<bean:message key="header.report.information" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
					<tr>
						<td>
							<bean:message key="colname.lf.report.id" />
							<br/>
							<html:text name="lostReportForm" property="lost.id" disabled="true" styleClass="disabledtextfield" size="15"/>
						</td>
						<td>
							<bean:message key="colname.lf.created.date" />
							<br/>
							<html:text name="lostReportForm" property="disOpenDate" disabled="true" styleClass="disabledtextfield" size="15"/>
						</td>
						<td>
							<bean:message key="colname.lf.created.agent" />
							<br/>
							<html:text name="lostReportForm" property="lost.agent.username" disabled="true" styleClass="disabledtextfield" size="15"/>
						</td>
						<td>
							<bean:message key="colname.lf.datelost"/>
							<br>
							<html:text name="lostReportForm" property="disLossdate" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.lostReportForm.disLossdate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
						</td>
					</tr>
					<tr>
						
						<td>
							<bean:message key="colname.lf.company" />&nbsp;<span class="reqfield">*</span>
							<br/>
							
							<% if(a.getSubcompany()!=null){ %>
         					<html:select name="lostReportForm" property="lost.companyId" styleId="company" disabled="true" styleClass="dropdown" styleId="lostCompanyId">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="name" />
         					</html:select>
         					<% } else { %>
         					<html:select name="lostReportForm" property="lost.companyId" styleId="company" styleClass="dropdown" styleId="lostCompanyId">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="subComplist" property="subcompanyCode" labelProperty="name" />
         					</html:select>
         					<% } %>
						</td>
						<td>
         					<bean:message key="colname.lf.status" />&nbsp;<span class="reqfield">*</span>
         					<br>
         					<html:select name="lostReportForm" property="lost.statusId" styleClass="dropdown" >
         						<html:option value="-1"><bean:message key="option.lf.please.select" /></html:option>
         						<html:options collection="lfstatuslist" property="status_ID" labelProperty="description" />
         					</html:select>
         				</td>
						<td>
							<bean:message key="colname.lf.closed.date" />
							<br/>
							<html:text name="lostReportForm" property="disClosedDate" disabled="true" styleClass="disabledtextfield" size="15"/>
						</td>
						<td>
							<bean:message key="colname.lf.closed.agent" />
							<br/>
							<html:text name="lostReportForm" property="closedAgentUsername" disabled="true" styleClass="disabledtextfield" size="15"/>
						</td>
					</tr>
				</table>
				<br/>
				<h1 class="green">
		        	<bean:message key="header.contact.information" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" /> 
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
			            <td colspan=2>
			              <bean:message key="colname.last_name.req" />&nbsp;<span class="reqfield">*</span>
			              <br>
			              <html:text name="lostReportForm" property="lost.client.lastName" styleId="lastName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td nowrap colspan="2">
			              <bean:message key="colname.first_name.req" />&nbsp;<span class="reqfield">*</span>
			              <br>
			              <html:text name="lostReportForm" property="lost.client.firstName" styleId="firstName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
			              <bean:message key="colname.mid_initial" />
			              <br>
			              <html:text name="lostReportForm" property="lost.client.middleName" styleId="middleName" size="1" maxlength="1" styleClass="textfield" />
			            </td>
			        </tr>
	            	<tr>
		                <td colspan=2>
		                  <bean:message key="colname.street_addr1" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedAddress1" styleId="address1" size="45" maxlength="50" styleClass="textfield" />
		                </td>
		                <td colspan=3>
		                  <bean:message key="colname.street_addr2" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedAddress2" styleId="address2" size="35" maxlength="50" styleClass="textfield" />
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <bean:message key="colname.city" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedCity" styleId="city" size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select name="lostReportForm" property="lost.client.address.decryptedState" styleId="state"  styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedProvince" styleId="province" size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedZip" styleId="zip" size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:select name="lostReportForm" property="lost.client.address.country" styleId="country" styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
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
		              		<html:text name="lostReportForm" property="primaryInternationalNumber" styleId="priInterNum" size="2" maxlength="10" styleClass="textfield" />
		              		<html:text name="lostReportForm" property="primaryAreaNumber" styleId="priAreaNum" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="lostReportForm" property="primaryExchangeNumber" styleId="priExchaNum" size="3" maxlength="10" styleClass="textfield" />
		              		<html:text name="lostReportForm" property="primaryLineNumber" styleId="priLineNum" size="4" maxlength="10" styleClass="textfield" /><br/>
		              		<html:select name="lostReportForm" property="primaryNumberType" styleId="priPhoneType" styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              		<bean:message key="colname.lf.phone.extension" />: <html:text name="lostReportForm" property="primaryExtension" size="3" maxlength="4" styleClass="textfield" />
		              		
		              	</td>
		              	<td colspan="3">
		              		<bean:message key="colname.lf.secondary.phone" /><br/>
		              		<bean:message key="colname.lf.phone.logic" />
		              		<br/>
		              		<html:text name="lostReportForm" property="secondaryInternationalNumber" size="2" maxlength="10" styleId="secInterNum" styleClass="textfield" />
		              		<html:text name="lostReportForm" property="secondaryAreaNumber" size="3" maxlength="10" styleId="secAreaNum"  styleClass="textfield" />
		              		<html:text name="lostReportForm" property="secondaryExchangeNumber" size="3" maxlength="10" styleId="secExchaNum"  styleClass="textfield" />
		              		<html:text name="lostReportForm" property="secondaryLineNumber" size="4" maxlength="10" styleId="secLineNum"  styleClass="textfield" /><br/>
		              		<html:select name="lostReportForm" property="secondaryNumberType" styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              		<bean:message key="colname.lf.phone.extension" />: <html:text name="lostReportForm" property="secondaryExtension" size="3" maxlength="4" styleClass="textfield" />
		              	</td>
		              </tr>
		              <tr>
		              	<td colspan="2" >
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="lostReportForm" property="lost.client.decryptedEmail" styleId="email" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              	<td colspan="3" >
		              		<bean:message key="colname.lf.confirm.email" />
		              		<br />
		              		<html:text name="lostReportForm" property="lost.client.confirmEmail" styleId="confirmemail" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              </tr>
		              <tr>
		              	<td colspan="5" >
		              		<bean:message key="colname.lf.feedback" />
		              		<br />
		              		<html:textarea name="lostReportForm" property="lost.feedback" styleId="feedback" disabled="true" cols="70" styleClass="textfield" />
		              	</td>
		              </tr>
				</table>
				<br/>
				<logic:notEmpty  name="lostReportForm" property="lost.shipment">
					<h1 class="green">
			        	<bean:message key="header.shipping.information" />
			        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
			        </h1>
    				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
				        <tr>
				            <td colspan=5>
				              <bean:message key="colname.shipping_name" />&nbsp;<span class="reqfield">*</span>
				              <br>
				              <html:text name="lostReportForm" property="lost.shipment.shippingName" styleId="shipName" size="20" maxlength="20" styleClass="textfield" />
				            
				        </tr>
				        
	            	<tr>
		                <td colspan=2>
		                  <bean:message key="colname.shipping_address" /><br/>
		                  <bean:message key="colname.street_addr1" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" styleId="shipAddress1" property="lost.shipment.shippingAddress.decryptedAddress1" size="45" maxlength="50" styleClass="textfield" />
		                </td>
		                <td colspan=3>
		                  <bean:message key="colname.street_addr2" />
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" styleId="shipAddress2" property="lost.shipment.shippingAddress.decryptedAddress2" size="35" maxlength="50" styleClass="textfield" />
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <bean:message key="colname.city" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" styleId="shipCity" property="lost.shipment.shippingAddress.decryptedCity" size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select disabled="true" name="lostReportForm" styleId="shipState" property="lost.shipment.shippingAddress.decryptedState" styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text disabled="true" name="lostReportForm" styleId="shipProvince" property="lost.shipment.shippingAddress.decryptedProvince" size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip" />
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" styleId="shipZip"  property="lost.shipment.shippingAddress.decryptedZip" size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:select disabled="true" name="lostReportForm" styleId="shipCountry"  property="lost.shipment.shippingAddress.country" styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
		                    <html:option value="">
		                      <bean:message key="select.none" />
		                    </html:option>
		                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		              </tr>
		              <logic:notEmpty  name="lostReportForm" property="lost.shipment.billingAddress">
		              <tr>
		                <td colspan=2>
		                  <bean:message key="colname.billing_address" /><br/>
		                  <bean:message key="colname.street_addr1" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.decryptedAddress1" styleId="billAddress1" size="45" maxlength="50" styleClass="textfield" />
		                </td>
		                <td colspan=3>
		                  <bean:message key="colname.street_addr2" />
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.decryptedAddress2" styleId="billAddress2"  size="35" maxlength="50" styleClass="textfield" />
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <bean:message key="colname.city" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.decryptedCity"  styleId="billCity"  size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.decryptedState"  styleId="billState" styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.decryptedProvince"  styleId="billProvince"  size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip" />
		                  <br>
		                  <html:text disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.decryptedZip" styleId="billZip" size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />&nbsp;<span class="reqfield">*</span>
		                  <br>
		                  <html:select disabled="true" name="lostReportForm" property="lost.shipment.billingAddress.country" styleId="billCountry" styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
		                    <html:option value="">
		                      <bean:message key="select.none" />
		                    </html:option>
		                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		              </tr>
		              </logic:notEmpty>
		              <tr>
		              	<td colspan=5>
		              		<bean:message key="colname.shipping_option" /><br/>
		              		<html:text disabled="true" name="lostReportForm" property="lost.shipment.shippingOption" styleId="shipOption"  styleClass="textfield" />
		              	</td>
		              </tr>
			        </table>
			    </logic:notEmpty>
				<br/>
				<h1 class="green">
		        	<bean:message key="header.itinerary.information" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" /> 
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
         			<logic:iterate indexId="i" id="segment" name="lostReportForm" property="lost.segments" type="com.bagnet.nettracer.tracing.db.lf.LFSegment" >
         				<tr>
	         		<!-- 		<td>
								<bean:message key="colname.lf.segment.origin" />
								<br>
	         					<select name="segment[<%=i %>].originId" class="dropdown" id="origin_<%=i %>" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							Station origStation;
	         							for (int j = 0; j < stationList.size(); ++j) {
	         								origStation = (Station) stationList.get(j);
	         						%>
	         								<option value="<%=origStation.getStation_ID() %>" <% if (origStation.getStation_ID() == segment.getOriginId()) { %>selected<% } %>><%=origStation.getStationcode() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
							</td>
	         				<td>
								<bean:message key="colname.lf.segment.destination" />
								<br>
	         					<select name="segment[<%=i %>].destinationId" class="dropdown" id="destination_<%=i %>" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							Station destStation;
	         							for (int j = 0; j < stationList.size(); ++j) {
	         								destStation = (Station) stationList.get(j);
	         						%>
	         								<option value="<%=destStation.getStation_ID() %>" <% if (destStation.getStation_ID() == segment.getDestinationId()) { %>selected<% } %>><%=destStation.getStationcode() %></option>
	         						<%	
	         							}
         							%>
	         					</select>
							</td>  -->
						<td>
							<bean:message key="colname.lf.segment.origin" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="segment" property="originId" styleClass="dropdown" indexed="true">
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
						<td>
							<bean:message key="colname.lf.segment.destination" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="segment" property="destinationId" styleClass="dropdown" indexed="true" >
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
			              	<td>
			              		<bean:message key="colname.lf.segment.flight" />
			              		<br />
		              			<html:text name="segment" property="flightNumber" size="20" maxlength="20" styleClass="textfield" indexed="true" />
			              	</td>
			              	<td>
			                  <input id="button" type="submit" value="<bean:message key="button.delete_segment" />" name="deleteSegment[<%=i %>]" />
			              	</td>
         				</tr>
         			</logic:iterate>
				</table>
				<center>
					<select name="addsegmentnum">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
					<html:submit property="addsegment" styleId="button">
						<bean:message key="button.add_cust_itinerary" />
					</html:submit>
				</center>
				<br/>
				<br/>
				<h1 class="green">
					<bean:message key="header.item.information" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<logic:iterate indexId="i" id="item" name="lostReportForm" property="lost.items" type="com.bagnet.nettracer.tracing.db.lf.LFItem" >
         				<% if (item.getType() == TracingConstants.LF_TYPE_LOST) { %> 
         				<tr>
         				<td style="width:45%;" class="header" >
       						<% 
       						int dispositionId = item.getDispositionId();
       						if (item.getFound() != null) { %>
       								<bean:message key="lf.match.found" />:&nbsp;<a style="color:#fff;" href='create_found_item.do?foundId=<%=item.getFound().getId() %>'><%=item.getFound().getBarcode() %></a>&nbsp;
	      							<% if (dispositionId == TracingConstants.LF_DISPOSITION_OTHER || dispositionId == TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) { %>
										[<a style="color:#fff;" href='create_lost_report.do?unmatchItem=1&itemId=<%=item.getId() %>'><bean:message key="button.un_match" /></a>]
									<% } %>
       						<% } else { %>
       								<bean:message key="lf.match.found" />:&nbsp;
   									<input type="text" size="10" class="textfield" id="foundInput" onchange="setFoundId(this.value,1,<%=item.getId() %>)" />&nbsp;
									<!--[<a style="color:#fff;" href="javascript:document.lostReportForm.submit();" ><bean:message key="button.do_match" /></a>]-->
									[<a style="color:#fff;" id="confirmInput" href="javascript:document.lostReportForm.submit();" onclick="return validateId('foundInput');" ><bean:message key="button.do_match" /></a>]
       						<% } %>
       					</td>
       					<td colspan=2 style="width:55%;" class="header" >
       						<% 
         						if (dispositionId == TracingConstants.LF_DISPOSITION_DELIVERED || dispositionId == TracingConstants.LF_DISPOSITION_PICKED_UP || item.getDeliveryRejected()) { %>
	         						<% if (item.getDispositionId() == TracingConstants.LF_DISPOSITION_DELIVERED) { %>
			         						<bean:message key="colname.lf.tracking.number" />:&nbsp;<html:text name="item" property="trackingNumber" size="20" styleClass="textfield" />&nbsp;
	         						<% } else if (item.getDispositionId() == TracingConstants.LF_DISPOSITION_PICKED_UP) { %>
			         						<bean:message key="lf.picked.up" />&nbsp;
	         						<% } else if (item.getDeliveryRejected()) {%>
	         								<bean:message key="lf.delivery.rejected" />
	         						<% } %>
			         						[<a style="color:#fff;" href='create_lost_report.do?undo=1&itemId=<%=item.getId() %>'><bean:message key="lf.undo" /></a>]
        					<%	} else { %>
        							&nbsp;
        					<%  } %>
        					</td>
       							
    	   					</tr>
   	   					<tr>
   	   					
	         				<td>
	         					<bean:message key="colname.lf.last.name.item" />
	         					<br>
	         					<html:text name="lostReportForm" property="lost.lastName" styleId="lastnameBag" maxlength="50" styleClass="textfield" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.first.name.item" />
	         					<br>
	         					<html:text name="lostReportForm" property="lost.firstName" styleId="firstnameBag"  maxlength="50" styleClass="textfield" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.middle.name.item" />
	         					<br>
	         					<html:text name="lostReportForm" property="lost.middleName" styleId="middlenameBag" maxlength="50" styleClass="textfield" />
	         				</td>
	         			</tr>
         				<tr>
	         				<td>
	         					<bean:message key="colname.lf.brand" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].brand" class="textfield"  id="itembrand_<%=i %>" value="<%=item.getBrand() == null ? "" : item.getBrand() %>" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.serial" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].serialNumber" class="textfield" id="itemserial_<%=i %>" value="<%=item.getSerialNumber() == null ? "" : item.getSerialNumber() %>" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.model" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].model" class="textfield"  id="itemmodel_<%=i %>" value="<%=item.getModel() == null ? "" : item.getModel() %>" />
	         				</td>
	         			</tr>
	         			<tr>
	         				<td>
	         					<bean:message key="colname.lf.category" />
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
	         					<select name="item[<%=i %>].color" class="dropdown" id="itemcolor_<%=i %>" >
	         						<option value=""><bean:message key="option.lf.please.select" /></option>
	         						<%
	         							ArrayList colorList = (ArrayList) request.getSession().getAttribute("lfcolorlist");
	         							LabelValueBean color = new LabelValueBean();
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
	         					<input type="text"  id="itemsize_<%=i %>" name="item[<%=i %>].size" class="textfield" value="<%=item.getSize() == null ? "" : item.getSize() %>" />
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.lostPhoneNumber" /><br/><bean:message key="colname.lf.phone.logic"/>
	         					<br>
	         					<input type="text" name="item[<%=i %>].dispCountry" size="2"  id="iteminter_<%=i %>" class="textfield" value="<%=item.getDispCountry()%>" />
	         					 <input type="text" name="item[<%=i %>].dispArea" size="3"  id="itemarea_<%=i %>" class="textfield" value="<%=item.getDispArea()%>" />
	         					 <input type="text" name="item[<%=i %>].dispExchange" size="3"  id="itemexchange_<%=i %>"  class="textfield" value="<%=item.getDispExchange()%>" />
	         					 <input type="text" name="item[<%=i %>].dispLine" size="4"  id="itemline_<%=i %>" class="textfield" value="<%=item.getDispLine()%>" /><br/>
	         					 <bean:message key="colname.lf.phone.extension" />: <input  id="itemext_<%=i %>" type="text" name="item[<%=i %>].dispExtension" size="4"  class="textfield" value="<%=item.getDispExtension()%>" />
	         					
	         				</td>
	         				<td>
	         					<bean:message key="colname.lf.caseColor" />
	         					<br>
	         					<select name="item[<%=i %>].caseColor" class="dropdown" id="itemcasecolor_<%=i %>" >
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
	         				<td colspan=3>
	         					<bean:message key="colname.lf.description" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].description"  id="itemdesc_<%=i %>" size="250" class="textfield" style="width: 95%;" value="<%=item.getDescription() == null ? "" : item.getDescription() %>" />
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
					<bean:message key="header.remarks" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" />
				
			<jsp:include page="/pages/lostandfound/remark_lost.jsp" />
			
      <center><html:submit property="addremark" styleId="button">
        <bean:message key="button.add_remark" />
      </html:submit></center>
      <br>
      <br>
				<center>
					<html:hidden property="save" value="" disabled="true" />
					<html:button property="saveButton" styleId="button" onclick="if (validateLfReportForm(this.form)) {this.form.save.disabled = false; this.form.submit();} else { this.form.save.disabled = true; return false; }">
						<bean:message key="button.save" />
					</html:button>
					
				</center>
				<script>
					fieldChanged('state');
					fieldChanged('country');
             	</script>
   			</div>
   		</td>
   	</tr>
</html:form>
