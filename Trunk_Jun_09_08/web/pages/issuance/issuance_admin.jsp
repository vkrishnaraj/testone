<%@page import="com.bagnet.nettracer.tracing.bmo.IssuanceItemBMO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.struts.util.LabelValueBean"%>
<%@page import="com.bagnet.nettracer.tracing.db.issuance.IssuanceItem"%>
<%@page import="com.bagnet.nettracer.tracing.forms.ClaimForm"%>
<%@ page language="java" %>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%
	Agent a = (Agent) session.getAttribute("user");

	boolean ntUser = PropertyBMO.isTrue("nt.user");
	boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
	boolean globalAdmin = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_GLOBAL_ADMIN, a);
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT> 
<SCRIPT LANGUAGE="JavaScript">

	function populateType() {
		var catList=document.getElementById("item_category");
		var typeList=document.getElementById("item_type");
		var selectedCategory=catList.options[catList.selectedIndex].value;
		typeList.options.length=0;
 		<logic:iterate indexId="i" id="c_item" name="item_category_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory" >
		if("<%=c_item.getId()%>"==selectedCategory)
			{	
				<% int index = 0;
					for (IssuanceItem item : c_item.getItems()) { 
						String desc = item.getDescription() != null ? item.getDescription().replaceAll("\"", "\\\\\"") : ""; 
						desc = desc.replaceAll("/", "\\\\/");%>
						typeList.options[<%=index%>]=new Option("<%=desc%>","<%=item.getId()%>",false,false);
				<%   	index++;
				    } %>
			}
		</logic:iterate>
	}

	function collectReason(theButton, theID) {
		var reason = window.prompt("Please enter the reason for discarding this item.","Inactive");
		if (reason !== null) {
			if (reason.replace(/^\s+|\s+$/g, '') != "") {
				theButton.form.discardreason.value = reason;
				theButton.form.discardreason.disabled = false;
				theButton.form.discardinventory.value = theID;
				theButton.form.discardinventory.disabled = false;
				return true;
			} else { /* No valid text in prompt but OK was clicked */
				window.alert("A reason for discarding must be provided.");
				return collectReason(theButton, theID);
			}
		}
		/* Prompt cancelled */
		return false;
	}

	function determineLoanType(incValue, iItemID) {
		var lButton = document.getElementsByName('inventory_loan_' + iItemID)[0];
		var snButton = document.getElementsByName('inventory_snloan_' + iItemID)[0];
		if (incValue.length > 0) {
			lButton.style.display = 'inline';
			snButton.style.display = 'none';
		} else {
			lButton.style.display = 'none';
			snButton.style.display = 'inline';
		}
	}

	function specialNeedLoan(iItemID) {
		var fName = document.getElementById("snFName");
		var lName = document.getElementById("snLName");
		var addr1 = document.getElementById("snAddr1");
		var addr2 = document.getElementById("snAddr2");
		var city = document.getElementById("snCity");
		var state = document.getElementById("snState");
		var prov = document.getElementById("snProv");
		var zip = document.getElementById("snZip");
		var ctry = document.getElementById("snCountry");
		var phone = document.getElementById("snPhone");
		var desc = document.getElementById("snDesc");
		if (fName.value.length == 0) {
			alert("<bean:message key="issuance.item.label.sn.fname" /> <bean:message key="error.validation.isRequired" />");
			fName.focus();
			return false;
		}
		if (lName.value.length == 0) {
			alert("<bean:message key="issuance.item.label.sn.lname" /> <bean:message key="error.validation.isRequired" />");
			lName.focus();
			return false;
		}
		if (phone.value.length == 0) {
			alert("<bean:message key="issuance.item.label.sn.phone" /> <bean:message key="error.validation.isRequired" />");
			phone.focus();
			return false;
		}
		if (desc.value.length == 0) {
			alert("<bean:message key="issuance.item.label.sn.desc" /> <bean:message key="error.validation.isRequired" />");
			desc.focus();
			return false;
		}
		document.location.href="issuanceItemAdmin.do?snloaninventory=" + iItemID + "&snFName=" + escape(fName.value) + "&snLName=" + escape(lName.value) +
		"&snAddr1=" + escape(addr1.value) + "&snAddr2=" + escape(addr2.value) + "&snCity=" + escape(city.value) + "&snState=" + escape(state.value) + "&snZip=" + escape(zip.value) +
		"&snCtry=" + escape(ctry.value) + "&snPhone=" + escape(phone.value) + "&snProv=" + escape(prov.value) + "&snDesc=" + escape(desc.value);
		return false;
	}

	function switchStateProvince(country) {
		var stateField = document.getElementById("snState");
		var provField = document.getElementById("snProv");
		var stateTD = document.getElementById("stateTD");
		var provTD = document.getElementById("provTD");
		if (country.length == 0 || country == 'US') {
			provField.value = '';
			provTD.style.display = 'none';
			stateTD.style.display = 'inline';
		} else {
			stateField.selectedIndex = 0;
			stateTD.style.display = 'none';
			provTD.style.display = 'inline';
		}
	}

	function loadSpecialNeedCollectionModal(iItemID) {
		var theHtml = '' +
		'<form><div>' +
		'	<table class="form2" cellspacing="0" cellpadding="0" width="100%" >' +
		'		<tr>' +
		'   		<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.fname" /> *</strong><br/>' +
		'			<input type="text" id="snFName" class="textfield" value="" maxlength="25" style="width:96%;" /></td>' +
		'			<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.lname" /> *</strong><br/>' +
		'			<input type="text" id="snLName" class="textfield" value="" maxlength="25" style="width:96%;" /></td>' +
		'		</tr>' +
		'		<tr>' +
		'   		<td colspan="6"><strong><bean:message key="issuance.item.label.sn.addr1" /></strong><br/>' +
		'			<input type="text" id="snAddr1" class="textfield" value="" maxlength="100" style="width:98%;" /></td>' +
		'		</tr>' +
		'		<tr>' +
		'   		<td colspan="6"><strong><bean:message key="issuance.item.label.sn.addr2" /></strong><br/>' +
		'			<input type="text" id="snAddr2" class="textfield" value="" maxlength="100" style="width:98%;" /></td>' +
		'		</tr>' +
		'		<tr>' +
		'   		<td colspan="2" width="34%"><strong><bean:message key="issuance.item.label.sn.city" /></strong><br/>' +
		'			<input type="text" id="snCity" class="textfield" value="" maxlength="50" style="width:95%;" /></td>' +
		'   		<td colspan="2" width="33%" id="stateTD"><strong><bean:message key="issuance.item.label.sn.state" /></strong><br/>' +
		'           <select id="snState" class="dropdown" style="width:96%;font-size:9px;border:1px solid #569ECD;margin: 2px 0px 1px 0px" >' +
        '  				<option value="">' +
        '    				<bean:message key="select.none" />' +
        '  				</option>' +
        <% for (LabelValueBean bean : (ArrayList<LabelValueBean>) session.getAttribute("statelist")) { %>
        '	            <option value="<%=bean.getValue()%>" ><%=bean.getLabel().replaceAll("'", "\\\\'").replaceAll("/", "\\\\/")%></option>' +
        <% } %>
        '			</select></td>' +
		'   		<td colspan="2" width="33%" id="provTD" style="display:none;"><strong><bean:message key="issuance.item.label.sn.prov" /></strong><br/>' +
		'			<input type="text" id="snProv" class="textfield" value="" maxlength="20" style="width:95%;" /></td>' +
		'   		<td colspan="2" width="33%"><strong><bean:message key="issuance.item.label.sn.zip" /></strong><br/>' +
		'			<input type="text" id="snZip" class="textfield" value="" maxlength="12" style="width:94%;" /></td>' +
		'		</tr>' +
		'		<tr>' +
		'   		<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.country" /></strong><br/>' +
		'			<select id="snCountry" class="dropdown" style="width:96%;font-size:9px;border:1px solid #569ECD;margin: 2px 0px 1px 0px" ' +
		' 					onchange="switchStateProvince(this.options[this.selectedIndex].value);">' +
        '  				<option value="">' +
        '    				<bean:message key="select.none" />' +
        '  				</option>' +
        <% for (LabelValueBean bean : (ArrayList<LabelValueBean>) session.getAttribute("countrylist")) { %>
        '	            <option value="<%=bean.getValue()%>" <% if (bean.getValue().equals("US")) { %>selected<% } %>><%=bean.getLabel().replaceAll("'", "\\\\'").replaceAll("/", "\\\\/")%></option>' +
        <% } %>
        '			</select></td>' +
		'			<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.phone" /> *</strong><br/>' +
		'			<input type="text" id="snPhone" class="textfield" value="" maxlength="20" style="width:96%;" /></td>' +
		'		</tr>' +
		'		<tr>' +
		'   		<td colspan="6"><strong><bean:message key="issuance.item.label.sn.desc" /> *</strong><br/>' +
		'			<input type="text" id="snDesc" class="textfield" value="" maxlength="255" style="width:98%;" /></td>' +
		'		</tr>' +
		'	</table><br /><br />' +
		'	<div style="width:100%;" align="center">' +
		'		<input type="button" value="<bean:message key="issuance.item.button.loan" />" onclick="return specialNeedLoan(' + iItemID + ');" name="snLoanPopup" id="button">' +
		'	</div>' +
		'	<br /><br />' +
		'</div></form>';
		jQuery("#dialog").dialog({bgiframe : true, autoOpen: false, modal: true, draggable: false, resizable: false, 
						width: 500, height: 350, title: 'Collect Special Need Information', close: function(ev,ui){ jQuery('#dialog-inner-content').empty();} });
		jQuery('#dialog-inner-content').html(theHtml);
		jQuery("#dialog").dialog("open");
		jQuery("#dialog").dialog("option", "title","Collect Special Need Information");
		
		var currentElement = document.getElementById("snFName");
		currentElement.focus();
		
		return false;
	}

	function loadSpecialNeedViewModal(fName, lName, addr1, addr2, city, state, prov, zip, ctry, phone, desc) {
		var stateSection = '' +
		'   		<td colspan="2" width="33%"><strong><bean:message key="issuance.item.label.sn.prov" /></strong> :' +
		'			' + prov + '<br/>'
		if (ctry.length == 0 || ctry == 'US') {
			stateSection = '' +
			'   		<td colspan="2" width="33%"><strong><bean:message key="issuance.item.label.sn.state" /></strong> :' +
			'			' + state + '<br/>'
		}
		var theHtml = '' +
		'<form><div>' +
		'	<table class="form2" cellspacing="0" cellpadding="0" width="100%" >' +
		'		<tr>' +
		'   		<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.fname" /></strong> :' +
		'			' + fName + '<br/>' +
		'			<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.lname" /></strong> :' +
		'			' + lName + '<br/>' +
		'		</tr>' +
		'		<tr><td colspan="6">&nbsp;</td></tr>' +
		'		<tr>' +
		'   		<td colspan="6"><strong><bean:message key="issuance.item.label.sn.addr1" /></strong> :' +
		'			' + addr1 + '<br/>' +
		'		</tr>' +
		'		<tr><td colspan="6">&nbsp;</td></tr>' +
		'		<tr>' +
		'   		<td colspan="6"><strong><bean:message key="issuance.item.label.sn.addr2" /></strong> :' +
		'			' + addr2 + '<br/>' +
		'		</tr>' +
		'		<tr><td colspan="6">&nbsp;</td></tr>' +
		'		<tr>' +
		'   		<td colspan="2" width="34%"><strong><bean:message key="issuance.item.label.sn.city" /></strong> :' +
		'			' + city + '<br/>' + stateSection +
		'   		<td colspan="2" width="33%"><strong><bean:message key="issuance.item.label.sn.zip" /></strong> :' +
		'			' + zip + '<br/>' +
		'		</tr>' +
		'		<tr><td colspan="6">&nbsp;</td></tr>' +
		'		<tr>' +
		'   		<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.country" /></strong> :' +
		'			' + ctry + '<br/>' +
		'			<td colspan="3" width="50%"><strong><bean:message key="issuance.item.label.sn.phone" /></strong> :' +
		'			' + phone + '<br/>' +
		'		</tr>' +
		'		<tr><td colspan="6">&nbsp;</td></tr>' +
		'		<tr>' +
		'   		<td colspan="6"><strong><bean:message key="issuance.item.label.sn.desc" /></strong> :' +
		'			' + desc + '<br/>' +
		'		</tr>' +
		'	</table>' +
		'</div></form>';
		jQuery("#dialog").dialog({bgiframe : true, autoOpen: false, modal: true, draggable: false, resizable: false, 
						width: 500, height: 350, title: 'View Special Need Information', close: function(ev,ui){ jQuery('#dialog-inner-content').empty();} });
		jQuery('#dialog-inner-content').html(theHtml);
		jQuery("#dialog").dialog("open");
		jQuery("#dialog").dialog("option", "title","View Special Need Information");
		
		return false;
	}

	function validateCost(costField) {
		if (!(costField.value && costField.value.match(/^\d*\.?\d+$/))) {
			alert("<bean:message key="issuance.item.cost" /> <bean:message key="error.validation.float" />");
			costField.focus();
			return false;
		}
		return true;
	}

</SCRIPT>

        <html:form action="issuanceItemAdmin.do" method="post" enctype="multipart/form-data" >
            <tr>
              <td colspan="3" id="pageheadercell">
                <div id="pageheaderleft">
                    <h1>
                      <bean:message key="issuance.item.admin" />
                    </h1>
                </div>
                <div id="pageheaderright">
                  <table id="pageheaderright">
                    <tr>
                      <jsp:include page="/pages/includes/mail_incl.jsp" />
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

		          	<font color=red>
		            	<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
					</font>
                	<table cellspacing="0" cellpadding="0" width="100%">
                	<tr>
                		<td>
	                    	<h1 class="green">
	                      		<bean:message key="issuance.item.quantity.header" />
	                    	</h1>
	                    </td>
                        <% if (globalAdmin) { %>
                        <td align="right">
                        	<html:select property="stationsearch_ID" styleClass="dropdown" onchange="this.form.submit()">
								<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
							</html:select>
                        </td>
                        <% } %>
                    </tr>
                    </table>
                    <html:hidden property="editquantity" value="" disabled="true"/>
                    <html:hidden property="issuequantity" value="" disabled="true"/>
                    <html:hidden property="historyquantity" value="" disabled="true"/>
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
	                  <td class="header">
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.issue" />
	                  </td>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.quantity" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.minimum.quantity" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.history" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="q_item" name="item_quantity_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity" >
                	<% boolean itemActive = (q_item.getIssuanceItem().getCategory().isActive() && q_item.getIssuanceItem().isActive()); %>
                  <tr <%if (q_item.getQuantity() < q_item.getMinimuActiveQuantity()) {%> style="background-color: red;" <%}%>>
	                  <% if (itemActive) { %>
	                  <td>
	                     <input type="text" name="item_incid_<%=q_item.getId() %>" value="" size="5" class="textfield" />
	                  </td>
	                  <td>
	                     <input type="submit" name="quantity_issue_<%=q_item.getId()%>" id="button" onclick="this.form.issuequantity.value = <%=q_item.getId()%>; this.form.issuequantity.disabled = false;" 
							value="<bean:message key="issuance.item.button.issue" />" >
						 </input>
	                  </td>
	                  <% } else { %>
	                  <td colspan=2>
	                  		<bean:message key="issuance.item.inactive" />
	                  </td>
	                  <% } %>
                      <td>
              			 <%=q_item.getIssuanceItem().getCategory().getDescription() %>
                      </td>
	                  <td>
	                     <%=q_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  <td>
	                     <input type="text" name="item_quantity_<%=q_item.getId() %>" value="<%=q_item.getQuantity()%>" size="1" class="textfield" />
	                  </td>
	                  <td>
	                  	 <% if (globalAdmin) { %>
	                     	<input type="text" name="item_minquantity_<%=q_item.getId() %>" value="<%=q_item.getMinimuActiveQuantity()%>" size="1" class="textfield" />
	                     <% } else { %>
	                     	<%=q_item.getMinimuActiveQuantity() %>
	                     <% } %>
	                  </td>
	                  <td>
	                     <input type="submit" name="quantity_edit_<%=q_item.getId()%>" id="button" onclick="this.form.editquantity.value = <%=q_item.getId()%>; this.form.editquantity.disabled = false;" 
							value="<bean:message key="issuance.item.button.edit" />" >
						 </input>
	                  </td>
	                  <td>
	                     <input type="submit" name="quantity_history_<%=q_item.getId()%>" id="button" onclick="this.form.historyquantity.value = <%=q_item.getId()%>; this.form.historyquantity.disabled = false;" 
							value="<bean:message key="issuance.item.button.history" />" >
						 </input>
	                  </td>
                  </tr>
                </logic:iterate> 
					  <tr>
		                <td colspan="8" align="center" valign="top">
			                  <html:submit property="quantity_history" styleId="button">
			                    <bean:message key="issuance.item.quantity.button.history" />
			                  </html:submit>
		                </td>
		              </tr>
              </table>
              <br/>
              
                    <h1 class="green">
                      <bean:message key="issuance.item.inventory.header" />
                    </h1>
                    <br />
                    <html:hidden property="editinventory" value="" disabled="true"/>
                    <html:hidden property="issueinventory" value="" disabled="true"/>
                    <html:hidden property="loaninventory" value="" disabled="true"/>
                    <html:hidden property="returninventory" value="" disabled="true"/>
                    <html:hidden property="convertinventory" value="" disabled="true"/>
                    <html:hidden property="discardinventory" value="" disabled="true"/>
                    <html:hidden property="discardreason" value="" disabled="true"/>
                    <html:hidden property="historyinventory" value="" disabled="true"/>
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
	                  <td class="header">
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.tradeout" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.loan" />
	                  </td>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.cost" />
	                  </td>	 	                  
	                  <td class="header">
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.barcode" />
	                  </td>
	                  	 <% if (globalAdmin) { %>
	                  <td class="header">
	                     <bean:message key="issuance.item.tradetype" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit" />
	                  </td>
	                  <% } %>
	                  <td class="header">
	                     <bean:message key="issuance.item.discard" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.history" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="i_item" name="item_inventory_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
                	<% boolean itemActive = (i_item.getIssuanceItem().getCategory().isActive() && i_item.getIssuanceItem().isActive()); %>
                  <tr>
	                  <td>
	                     <input type="text" name="inv_item_incid_<%=i_item.getId() %>" value="" size="5" class="textfield" onkeyup="determineLoanType(this.value, <%=i_item.getId()%>);"/>
	                  </td>
	                  <% if (itemActive) { %>
	                  <td>
	                  	<% if (i_item.getTradeType() != TracingConstants.ISSUANCE_ITEM_INVENTORY_TYPE_LOAN_ONLY) { %>
	                     <input type="submit" name="inventory_issue_<%=i_item.getId()%>" id="button" onclick="this.form.issueinventory.value = <%=i_item.getId()%>; this.form.issueinventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.tradeout" />" >
						 </input>
						<% } %>&nbsp;
	                  </td>
	                  <td>
	                  	 <% if (i_item.getTradeType() != TracingConstants.ISSUANCE_ITEM_INVENTORY_TYPE_TRADEOUT_ONLY) { %>
	                     <input type="submit" name="inventory_loan_<%=i_item.getId()%>" id="button" onclick="this.form.loaninventory.value = <%=i_item.getId()%>; this.form.loaninventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.loan" />" style="display:none;" >
						 </input>
	                     <input type="submit" name="inventory_snloan_<%=i_item.getId()%>" id="button" onclick="return loadSpecialNeedCollectionModal(<%=i_item.getId()%>);" 
							value="<bean:message key="issuance.item.button.snloan" />" >
						 </input>
						<% } %>&nbsp;
	                  </td>
	                  <% } else { %>
	                  <td colspan="2" >
	                  	<bean:message key="issuance.item.inactive" />
	                  </td>
	                  <% } %>
                      <td>
              			 <%=i_item.getIssuanceItem().getCategory().getDescription() %>
                      </td>
	                  <td>
	                     <%=i_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  	 <% if (globalAdmin) { %>
	                  <td>
	                     <input type="text" id="item_cost_<%=i_item.getId() %>" name="item_cost_<%=i_item.getId() %>" value="<%=i_item.getCost()%>" size="6" class="textfield" />
	                  </td>	                  	 
	                  <td>
	                     <input type="text" name="item_desc_<%=i_item.getId() %>" value="<%=i_item.getDescription()%>" size="10" class="textfield" />
	                  </td>
	                  <td>
	                     <input type="text" name="item_barcode_<%=i_item.getId() %>" value="<%=i_item.getBarcode()%>" size="4" maxlength="20" class="textfield" />
	                  </td>
	                  <td>
	                     <select name="item_tradetype_<%=i_item.getId() %>" class="dropdown" >
	                     <%    for (int theType = 0; theType <= 2; theType++) { %>
	                     	<option value="<%=theType %>" <% if (i_item.getTradeType() == theType) { %>selected<% } %>>
	                     		<bean:message key="<%="issuance.item.tradetype." + theType%>" />
	                     	</option>
	                     <%    } %>
	                     </select>
	                  </td>
	                  <td>
	                     <input type="submit" name="inventory_edit_<%=i_item.getId()%>" id="button" onclick="if (validateCost(document.getElementById('item_cost_<%=i_item.getId() %>'))) {this.form.editinventory.value = <%=i_item.getId()%>; this.form.editinventory.disabled = false;} else {return false;}" 
							value="<bean:message key="issuance.item.button.edit" />" >
						 </input>
	                  </td>
	                  	 <% } else { %>
	                  <td>
	                     <%=i_item.getDescription() %>
	                  </td>
	                  <td>
	                  	 <%=i_item.getBarcode() %>
	                  </td>
	                  	<% } %>
	                  <td>
	                     <input type="submit" name="inventory_discard_<%=i_item.getId()%>" id="button" onclick="return collectReason(this, <%=i_item.getId()%>);" 
							value="<bean:message key="issuance.item.button.discard" />" >
						 </input>
	                  </td>
	                  <td>
	                     <input type="submit" name="inventory_history_<%=i_item.getId()%>" id="button" onclick="this.form.historyinventory.value = <%=i_item.getId()%>; this.form.historyinventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.history" />" >
						 </input>
	                  </td>
                  </tr>
                </logic:iterate> 
					  <tr>
	                  	 <% if (globalAdmin) { %>
		                <td colspan="11" align="center" valign="top">
		                <% } else { %>
		                <td colspan="9" align="center" valign="top">
		                <% } %>
			                  <html:submit property="inventory_history" styleId="button">
			                    <bean:message key="issuance.item.inventory.button.history" />
			                  </html:submit>
		                </td>
		              </tr>
              </table>
              <br/>
                    <h1 class="green">
                      <bean:message key="issuance.item.add.inventory.header" />
                    </h1>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
                  <tr>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.cost" />
	                  </td>	 	                  
	                  <td class="header">
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.barcode" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.tradetype" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.add" />
	                  </td>
                  </tr>
                	  <tr>
	                  	<td>
	                        <select id="item_category" name="item_category" class="dropdown" onchange="populateType()">
	                     		<logic:iterate indexId="i" id="c_item" name="item_category_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory" >
	                     			<% if (c_item.isInventory()) { %>
	                     			<option value="<%=c_item.getId() %>" >
	                     				<%=c_item.getDescription() %>
	                     			</option>
	                     			<% } %>
	                     		</logic:iterate>
	                     	</select>
	                  	</td>
	                  	<td>
	                        <select id="item_type" name="item_type" class="dropdown" >
	                        	<% boolean firstAdded = false; %>
	                     		<logic:iterate indexId="i" id="c_item" name="item_category_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory" >
	                     			<% if (c_item.isInventory() && !firstAdded) { 
	                     					for (IssuanceItem item : c_item.getItems()) {%>
	                     				<option value="<%=item.getId() %>" >
	                     					<%=item.getDescription() %>
	                     				</option>
	                     			<% 		}
	                     					firstAdded = true;
	                     			   } %>
	                     		</logic:iterate>
	                     	</select>
	                  	</td>
	                  	<td>
	                     	<input type="text" id="item_cost" name="item_cost" value="0" size="6" maxlength="20" class="textfield" />
	                  	</td>	                  	
	                  	<td>
	                     	<input type="text" name="item_desc" value="" size="10" class="textfield" />
	                  	</td>
	                  	<td>
	                     	<input type="text" name="item_barcode" value="" size="4" maxlength="20" class="textfield" />
	                  	</td>
	                  	<td>
	                     <select name="item_tradetype" class="dropdown" >
	                     <%    for (int theType = 0; theType <= 2; theType++) { %>
	                     	<option value="<%=theType %>" >
	                     		<bean:message key="<%="issuance.item.tradetype." + theType%>" />
	                     	</option>
	                     <%    } %>
	                     </select>
	                  	</td>
						<td>
			                  <html:submit property="addinventory" styleId="button" onclick="return validateCost(document.getElementById('item_cost'))">
			                    <bean:message key="issuance.item.inventory.button.add" />
			                  </html:submit>
			            </td>
                	  </tr>
                    </table>
<% if (request.getAttribute("item_inventory_loan_resultList") != null) { %>
              <br/>              
                    <h1 class="green">
                      <bean:message key="issuance.item.loaned.inventory.header" />
                    </h1>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
	                  <td class="header">
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.return" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.convert" />
	                  </td>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.cost" />
	                  </td>	 	                  
	                  <td class="header">
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.barcode" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.history" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="i_item" name="item_inventory_loan_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
                	<% boolean itemActive = (i_item.getIssuanceItem().getCategory().isActive() && i_item.getIssuanceItem().isActive()); %>
                  <tr>
	                  <td>
	                  	 <% if (i_item.isVerifiedIncident()) { %>
	                  	 	<a href="<%="searchIncident.do?incident=" + i_item.getIncidentID() %>" ><%=i_item.getIncidentID() %></a>
	                     <% } else if (i_item.getIncidentID() != null && i_item.getIncidentID().equals(IssuanceItemBMO.SPECIAL_LOAN_ID)) { 
	                     		String fn = i_item.getFirstName() != null ? i_item.getFirstName().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ln = i_item.getLastName() != null ? i_item.getLastName().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ad1 = i_item.getAddress1() != null ? i_item.getAddress1().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ad2 = i_item.getAddress2() != null ? i_item.getAddress2().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ct = i_item.getCity() != null ? i_item.getCity().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String st = i_item.getState() != null ? i_item.getState().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String pv = i_item.getProvince() != null ? i_item.getProvince().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String zp = i_item.getZip() != null ? i_item.getZip().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ctry = i_item.getCountry() != null ? i_item.getCountry().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String pn = i_item.getPhoneNumber() != null ? i_item.getPhoneNumber().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String desc = i_item.getSpecialNeedDescription() != null ? i_item.getSpecialNeedDescription().replace("'", "\\\\'").replace("/", "\\\\/") : ""; %>
	                     	<a href="###" 
	                     	onclick="loadSpecialNeedViewModal('<%=fn %>', '<%=ln %>', '<%=ad1 %>', '<%=ad2 %>', '<%=ct %>', '<%=st %>', '<%=pv %>', '<%=zp %>', '<%=ctry %>', '<%=pn %>', '<%=desc %>')"
	                     	><bean:message key="issuance.item.button.snloan" /></a>
	                     <% } else { %>
	                     	<%=i_item.getIncidentID() %>
	                     <% } %>
	                  </td>
	                  <% if (itemActive) { %>
	                  <td>
	                     <input type="submit" name="inventory_return_<%=i_item.getId()%>" id="button" onclick="this.form.returninventory.value = <%=i_item.getId()%>; this.form.returninventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.return" />" >
						 </input>
	                  </td>
	                  <td>
	                     <input type="submit" name="inventory_convert_<%=i_item.getId()%>" id="button" onclick="this.form.convertinventory.value = <%=i_item.getId()%>; this.form.convertinventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.convert" />" >
						 </input>
	                  </td>
	                  <% } else { %>
	                  <td colspan="2" >
	                  	<bean:message key="issuance.item.inactive" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     <input type="submit" name="inventory_discard_<%=i_item.getId()%>" id="button" onclick="return collectReason(this, <%=i_item.getId()%>);" 
							value="<bean:message key="issuance.item.button.discard" />" >
						 </input>
	                  </td>
	                  <% } %>
                      <td>
              			 <%=i_item.getIssuanceItem().getCategory().getDescription() %>
                      </td>
	                  <td>
	                     <%=i_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  <td>
	                     <%=i_item.getCost() %>
	                  </td>	                  
	                  <td>
	                     <%=i_item.getDescription() %>
	                  </td>
	                  <td>
	                  	 <%=i_item.getBarcode() %>
	                  </td>
	                  <td>
	                     <input type="submit" name="inventory_history_<%=i_item.getId()%>" id="button" onclick="this.form.historyinventory.value = <%=i_item.getId()%>; this.form.historyinventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.history" />" >
						 </input>
	                  </td>
                  </tr>
                </logic:iterate> 
              </table>
<% } %>
<% if (request.getAttribute("item_inventory_issue_resultList") != null) { %>
              <br/>              
                    <h1 class="green">
                      <bean:message key="issuance.item.issued.inventory.header" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
	                  <td class="header">
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.return" />
	                  </td>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.cost" />
	                  </td>	 	                  
	                  <td class="header">
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.barcode" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.history" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="i_item" name="item_inventory_issue_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
                	<% boolean itemActive = (i_item.getIssuanceItem().getCategory().isActive() && i_item.getIssuanceItem().isActive()); %>
                  <tr>
	                  <td>
	                  	 <% if (i_item.isVerifiedIncident()) { %>
	                  	 	<a href="<%="searchIncident.do?incident=" + i_item.getIncidentID() %>" ><%=i_item.getIncidentID() %></a>
	                     <% } else if (i_item.getIncidentID() != null && i_item.getIncidentID().equals(IssuanceItemBMO.SPECIAL_LOAN_ID)) { 
	                     		String fn = i_item.getFirstName() != null ? i_item.getFirstName().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ln = i_item.getLastName() != null ? i_item.getLastName().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ad1 = i_item.getAddress1() != null ? i_item.getAddress1().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ad2 = i_item.getAddress2() != null ? i_item.getAddress2().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ct = i_item.getCity() != null ? i_item.getCity().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String st = i_item.getState() != null ? i_item.getState().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String pv = i_item.getProvince() != null ? i_item.getProvince().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String zp = i_item.getZip() != null ? i_item.getZip().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String ctry = i_item.getCountry() != null ? i_item.getCountry().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String pn = i_item.getPhoneNumber() != null ? i_item.getPhoneNumber().replace("'", "\\\\'").replace("/", "\\\\/") : ""; 
	                     		String desc = i_item.getSpecialNeedDescription() != null ? i_item.getSpecialNeedDescription().replace("'", "\\\\'").replace("/", "\\\\/") : ""; %>
	                     	<a href="###" 
	                     	onclick="loadSpecialNeedViewModal('<%=fn %>', '<%=ln %>', '<%=ad1 %>', '<%=ad2 %>', '<%=ct %>', '<%=st %>', '<%=pv %>', '<%=zp %>', '<%=ctry %>', '<%=pn %>', '<%=desc %>')"
	                     	><bean:message key="issuance.item.button.snloan" /></a>
	                     <% } else { %>
	                     	<%=i_item.getIncidentID() %>
	                     <% } %>
	                  </td>
	                  <% if (itemActive) { %>
	                  <td>
	                     <input type="submit" name="inventory_return_<%=i_item.getId()%>" id="button" onclick="this.form.returninventory.value = <%=i_item.getId()%>; this.form.returninventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.return" />" >
						 </input>
	                  </td>
	                  <% } else { %>
	                  <td>
	                  	<bean:message key="issuance.item.inactive" />
	                  </td>
	                  <% } %>
                      <td>
              			 <%=i_item.getIssuanceItem().getCategory().getDescription() %>
                      </td>
	                  <td>
	                     <%=i_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  <td>
	                     <%=i_item.getCost() %>
	                  </td>	                  
	                  <td>
	                     <%=i_item.getDescription() %>
	                  </td>
	                  <td>
	                  	 <%=i_item.getBarcode() %>
	                  </td>
	                  <td>
	                     <input type="submit" name="inventory_history_<%=i_item.getId()%>" id="button" onclick="this.form.historyinventory.value = <%=i_item.getId()%>; this.form.historyinventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.history" />" >
						 </input>
	                  </td>
                  </tr>
                </logic:iterate> 
              </table>
<% } %>
<% if (request.getAttribute("item_inventory_discard_resultList") != null) { %>
              <br/>              
                    <h1 class="green">
                      <bean:message key="issuance.item.discarded.inventory.header" />
                    </h1>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
	                  <td class="header">
	                     <bean:message key="issuance.item.return" />
	                  </td>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.cost" />
	                  </td>	 	                  
	                  <td class="header">
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.barcode" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.history" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="i_item" name="item_inventory_discard_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
                	<% boolean itemActive = (i_item.getIssuanceItem().getCategory().isActive() && i_item.getIssuanceItem().isActive()); %>
                  <tr>
	                  <% if (itemActive) { %>
	                  <td>
	                     <input type="submit" name="inventory_return_<%=i_item.getId()%>" id="button" onclick="this.form.returninventory.value = <%=i_item.getId()%>; this.form.returninventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.return" />" >
						 </input>
	                  </td>
	                  <% } else { %>
	                  <td>
	                  	<bean:message key="issuance.item.inactive" />
	                  </td>
	                  <% } %>
                      <td>
              			 <%=i_item.getIssuanceItem().getCategory().getDescription() %>
                      </td>
	                  <td>
	                     <%=i_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  <td>
	                     <%=i_item.getCost() %>
	                  </td>	                  
	                  <td>
	                     <%=i_item.getDescription() %>
	                  </td>
	                  <td>
	                  	 <%=i_item.getBarcode() %>
	                  </td>
	                  <td>
	                     <input type="submit" name="inventory_history_<%=i_item.getId()%>" id="button" onclick="this.form.historyinventory.value = <%=i_item.getId()%>; this.form.historyinventory.disabled = false;" 
							value="<bean:message key="issuance.item.button.history" />" >
						 </input>
	                  </td>
                  </tr>
                </logic:iterate> 
              </table>
<% } %>
					
                  </html:form>
					