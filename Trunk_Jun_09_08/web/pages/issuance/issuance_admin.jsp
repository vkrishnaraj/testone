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
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><bean:write name="msg"/><br/><br/></html:messages></logic:messagesPresent>
                    </font>
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
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
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
	                     <input type="text" name="inv_item_incid_<%=i_item.getId() %>" value="" size="5" class="textfield" />
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
							value="<bean:message key="issuance.item.button.loan" />" >
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
	                     <input type="submit" name="inventory_edit_<%=i_item.getId()%>" id="button" onclick="this.form.editinventory.value = <%=i_item.getId()%>; this.form.editinventory.disabled = false;" 
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
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
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
			                  <html:submit property="addinventory" styleId="button">
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
	                     <bean:message key="issuance.item.convert" />
	                  </td>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.type" />
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
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
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
					