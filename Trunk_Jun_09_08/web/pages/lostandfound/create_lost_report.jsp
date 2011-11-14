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
	
</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="lost.id" action="create_lost_report.do" method="post" onsubmit="return validateLfReportForm(this);">
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
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
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
					<tr>
						<td>
							<bean:message key="colname.lf.company" />&nbsp;<span class="reqfield">*</span>
							<br/>
         					<html:select name="lostReportForm" property="lost.companyId" styleClass="dropdown" >
         						<html:option value="<%=TracingConstants.LF_ABG_COMPANY_ID %>"><bean:message key="option.lf.please.select" /></html:option>
         						<html:option value="<%=TracingConstants.LF_AVIS_COMPANY_ID %>"><bean:message key="option.lf.avis" /></html:option>
         						<html:option value="<%=TracingConstants.LF_BUDGET_COMPANY_ID %>"><bean:message key="option.lf.budget" /></html:option>
         					</html:select>
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
         					<bean:message key="colname.lf.vantive.number" />
         					<br>
         					<html:text name="lostReportForm" property="lost.vantiveNumber" size="10" styleClass="textfield" />
         				</td>
         				<td colspan="2">
         					&nbsp;
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
			              <html:text name="lostReportForm" property="lost.client.lastName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td nowrap colspan="2">
			              <bean:message key="colname.first_name.req" />&nbsp;<span class="reqfield">*</span>
			              <br>
			              <html:text name="lostReportForm" property="lost.client.firstName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
			              <bean:message key="colname.mid_initial" />
			              <br>
			              <html:text name="lostReportForm" property="lost.client.middleName" size="1" maxlength="1" styleClass="textfield" />
			            </td>
			        </tr>
	            	<tr>
		                <td colspan=2>
		                  <bean:message key="colname.street_addr1" />&nbsp;<span class="reqfield"></span>
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedAddress1" size="45" maxlength="50" styleClass="textfield" />
		                </td>
		                <td colspan=3>
		                  <bean:message key="colname.street_addr2" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedAddress2" size="35" maxlength="50" styleClass="textfield" />
		                </td>
		              </tr>
		              <tr>
		                <td>
		                  <bean:message key="colname.city" />&nbsp;<span class="reqfield"></span>
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedCity" size="10" maxlength="50" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.state" />
		                  <br />
		                  <html:select name="lostReportForm" property="lost.client.address.decryptedState" styleId="state" styleClass="dropdown" onchange="fieldChanged('state');" >
		                  	<html:option value="">
			                    <bean:message key="select.none" />
		                    </html:option>
			                <html:options collection="statelist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		                <td>
		                  <bean:message key="colname.province" />
		                  <br />
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedProvince" size="10" maxlength="100" styleId="province" styleClass="textfield" onchange="fieldChanged('province');" />
		                </td>
		                <td>
		                  <bean:message key="colname.zip" />
		                  <br>
		                  <html:text name="lostReportForm" property="lost.client.address.decryptedZip" size="11" maxlength="11" styleClass="textfield" />
		                </td>
		                <td>
		                  <bean:message key="colname.country" />&nbsp;<span class="reqfield"></span>
		                  <br>
		                  <html:select name="lostReportForm" property="lost.client.address.country" styleId="country" styleClass="dropdown" onchange="fieldChanged('country');">
		                    <html:option value="">
		                      <bean:message key="select.none" />
		                    </html:option>
		                    <html:options name="OnHandForm" collection="countrylist" property="value" labelProperty="label" />
		                  </html:select>
		                </td>
		              </tr>
		              <tr>
		              	<td colspan="2">
		              		<bean:message key="colname.lf.primary.phone" />
		              		<br/>
		              		<html:text name="lostReportForm" property="primaryPhoneNumber" size="15" maxlength="25" styleClass="textfield" />
		              		<html:select name="lostReportForm" property="primaryNumberType" styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              	</td>
		              	<td colspan="3">
		              		<bean:message key="colname.lf.secondary.phone" />
		              		<br/>
		              		<html:text name="lostReportForm" property="secondaryPhoneNumber" size="15" maxlength="25" styleClass="textfield" />
		              		<html:select name="lostReportForm" property="secondaryNumberType" styleClass="dropdown" >
		              			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_HOME) %>"><bean:message key="option.lf.home" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_MOBILE) %>"><bean:message key="option.lf.mobile" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_WORK) %>"><bean:message key="option.lf.work" /></html:option>
		              			<html:option value="<%=String.valueOf(TracingConstants.LF_PHONE_TYPE_OTHER) %>"><bean:message key="option.lf.other" /></html:option>
		              		</html:select>
		              	</td>
		              </tr>
		              <tr>
		              	<td colspan="2" >
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="lostReportForm" property="lost.client.decryptedEmail" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
		              	<td colspan="3" >
		              		<bean:message key="colname.lf.confirm.email" />
		              		<br />
		              		<html:text name="lostReportForm" property="lost.client.confirmEmail" size="35" maxlength="100" styleClass="textfield" />
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
         			<logic:iterate indexId="i" id="item" name="lostReportForm" property="lost.items" type="com.bagnet.nettracer.tracing.db.lf.LFItem" >
         				<% 
         					if (item.getType() == TracingConstants.LF_TYPE_LOST) { %>
	         				<tr>
         					<%  int dispositionId = item.getDispositionId();
         						if (dispositionId == TracingConstants.LF_DISPOSITION_DELIVERED || dispositionId == TracingConstants.LF_DISPOSITION_PICKED_UP) { %>
			         					<td class="header" >
	         						<% if (item.getDispositionId() == TracingConstants.LF_DISPOSITION_DELIVERED) { %>
			         						<bean:message key="colname.lf.tracking.number" />:&nbsp;<html:text name="item" property="trackingNumber" size="20" styleClass="textfield" />&nbsp;
	         						<% } else if (item.getDispositionId() == TracingConstants.LF_DISPOSITION_PICKED_UP) { %>
			         						<bean:message key="lf.picked.up" />&nbsp;
	         						<% } %>
			         						[<a style="color:#fff;" href='create_lost_report.do?undo=1&itemId=<%=item.getId() %>'><bean:message key="lf.undo" /></a>]
			         					</td>
        					<%	} else { %>
        							<td class="header" >&nbsp;</td>
        					<%  } %>
       							<td class="header" colspan=2>
       								<% if (item.getFound() != null) { %>
       									<bean:message key="lf.match.found" />:&nbsp;<a style="color:#fff;" href='create_found_item.do?foundId=<%=item.getFound().getId() %>'><%=item.getFound().getId() %></a>&nbsp;
       									<% if (dispositionId == TracingConstants.LF_DISPOSITION_OTHER || dispositionId == TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) { %>
											[<a style="color:#fff;" href='create_lost_report.do?unmatchItem=1&itemId=<%=item.getId() %>'><bean:message key="button.un_match" /></a>]
										<% } %>
       								<% } else { %>
       									<bean:message key="lf.match.found" />:&nbsp;
   										<input type="text" size="10" class="textfield" id="foundInput" onchange="setFoundId(this.value,1,<%=item.getId() %>)" />&nbsp;
										[<a style="color:#fff;" href="javascript:document.lostReportForm.submit();" ><bean:message key="button.do_match" /></a>]
       								<% } %>
       							</td>
    	   					</tr>
         				<tr>
	         				<td>
	         					<bean:message key="colname.lf.brand" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].brand" class="textfield" value="<%=item.getBrand() == null ? "" : item.getBrand() %>" />
	         				</td>
	         				<td colspan=2>
	         					<bean:message key="colname.lf.serial" />
	         					<br>
	         					<input type="text" name="item[<%=i %>].serialNumber" class="textfield" value="<%=item.getSerialNumber() == null ? "" : item.getSerialNumber() %>" />
	         				</td>
	         			</tr>
	         			<tr>
	         				<td>
	         					<bean:message key="colname.lf.category" />
	         					<br>
	         					<select name="item[<%=i %>].category" class="dropdown" onchange="updateSubCategories('category_<%=i %>', <%=i %>);" id="category_<%=i %>" >
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
	         					<select name="item[<%=i %>].subCategory" class="dropdown" id="subcategories_<%=i %>" onchange="" >
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
	         					<select name="item[<%=i %>].color" class="dropdown" >
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
	         				<td colspan=3>
	         					<bean:message key="colname.lf.description" />
	         					<br>
	         					<textarea name="item[<%=i %>].description" cols="80" rows="3" class="textfield" ><%=item.getDescription() == null ? "" : item.getDescription() %></textarea>
	         				</td>
	         			</tr>
	         			<% } %>
         			</logic:iterate>
         		</table>
         		<br/>
				<h1 class="green">
					<bean:message key="header.trip.information" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
				</h1>
				<span class="reqfield">*</span>
				<bean:message key="message.required" /> 
				<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
					<tr>
						<td>
							<bean:message key="colname.lf.agreement.number" />
							<br>
							<html:text name="lostReportForm" property="lost.reservation.agreementNumber" size="10" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.lf.mva.number" />
							<br>
							<html:text name="lostReportForm" property="lost.reservation.mvaNumber" size="10" styleClass="textfield" />
						</td>
						<td>
							<bean:message key="colname.lf.rental.location" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="lostReportForm" property="lost.reservation.pickupLocationId" styleClass="dropdown" >
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
						<td>
							<bean:message key="colname.lf.dropoff.location" />&nbsp;<span class="reqfield">*</span>
							<br>
		            		<html:select name="lostReportForm" property="lost.reservation.dropoffLocationId" styleClass="dropdown" >
		            			<html:option value=""><bean:message key="option.lf.please.select" /></html:option>
		            			<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
		            		</html:select>
						</td>
					</tr>
					<tr>
						<td colspan=4>
							<bean:message key="colname.lf.where.did.you.leave.it" />
							<br>
							<html:text name="lostReportForm" property="lost.remarks" size="80" styleClass="textfield" />
						</td>
					</tr>
				</table>
				<br/>
				<center>
					<html:submit property="save" styleId="button">
						<bean:message key="button.save" />
					</html:submit>
				</center>
				<script>
					fieldChanged('state');
					fieldChanged('country');
             	</script>
   			</div>
   		</td>
   	</tr>
</html:form>
