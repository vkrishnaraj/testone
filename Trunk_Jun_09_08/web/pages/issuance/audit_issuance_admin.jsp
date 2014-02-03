<%@page import="java.text.DecimalFormat"%>
<%@page import="com.bagnet.nettracer.tracing.bmo.IssuanceItemBMO"%>
<%@page import="com.bagnet.nettracer.tracing.forms.issuance.AuditIssuanceItemAdminForm"%>
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

	AuditIssuanceItemAdminForm form = (AuditIssuanceItemAdminForm) request.getAttribute("auditIssuanceItemAdminForm");
	boolean ntUser = PropertyBMO.isTrue("nt.user");
	boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
	boolean globalAdmin = UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_ISSUANCE_ITEMS_GLOBAL_ADMIN, a);
	DecimalFormat df = new DecimalFormat("#0.00");
    long currentID = -1;
 	if (request.getAttribute("id") != null) {
 		currentID = (Long) request.getAttribute("id");
 	}
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">

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

</SCRIPT>

        <html:form action="auditIssuanceItemAdmin.do" method="post" enctype="multipart/form-data" >
            <tr>
              <td colspan="3" id="pageheadercell">
                <div id="pageheaderleft">
                    <h1>
                      <bean:message key="audit.issuance.item.admin" />
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
                <% if (request.getAttribute("audit_item_quantity_resultList") != null) { %>
                    <h1 class="green">
                      <bean:message key="audit.issuance.item.quantity.header" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td>
	                     <bean:message key="issuance.item.type" /><br/>
	                     <select name="quantity_id" class="dropdown" >
	                     	<option value="-1" <% if (-1 == currentID) { %>selected<% } %>>
	                     		<bean:message key="issuance.item.allitems" />
	                     	</option>
                <logic:iterate indexId="i" id="q_item" name="item_quantity_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemQuantity" >
	                     	<option value="<%=q_item.getId() %>" <% if (q_item.getId() == currentID) { %>selected<% } %>>
	                     		<%=q_item.getIssuanceItem().getDescription() %>
	                     	</option>
				</logic:iterate>
	                     </select>
                        </td>
                        <% if (globalAdmin) { %>
                        <td>
	                     <bean:message key="issuance.item.station" /><br/>
                        	<html:select property="stationsearch_ID" styleClass="dropdown" >
								<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
							</html:select>
                        </td>
                        <% } %>
                      </tr>
                    </table>
              <div align="center">
	                     <input type="submit" name="view_quantity" id="button"  
							value="<bean:message key="issuance.item.button.view" />" >
						 </input>
                    </div>
                    <br/>
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
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
	                     <bean:message key="issuance.item.quantity.change" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.minimum.quantity" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit.agent" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit.date" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="q_item" name="audit_item_quantity_resultList" type="com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemQuantity" >
                  <tr>
                      <td>
              			 <%=q_item.getIssuanceItem().getCategory().getDescription()%>
                      </td>
	                  <td>
	                     <%=q_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  <td>
	                     <%=q_item.getQuantity()%>
	                  </td>
	                  <td>
	                     <%=q_item.getQuantityChange()%>
	                  </td>
	                  <td>
	                     <%=q_item.getMinimuActiveQuantity()%>
	                  </td>
	                  <td>
	                  	 <% String incID = (q_item.getIncidentID() != null ? q_item.getIncidentID() : ""); %>
	                  	 <% if (q_item.isVerifiedIncident()) { %>
	                  	 	<a href="<%="searchIncident.do?incident=" + incID %>" ><%=incID %></a>
	                     <% } else { %>
	                     	<%=incID %>
	                     <% } %>&nbsp;
	                  </td>
	                  <td>
	                     <%=q_item.getEditAgent().getUsername() %>
	                  </td>
	                  <td>
	                     <%=form.getDisplayDate(q_item.getEditDate()) %>
	                  </td>
                  </tr>
                </logic:iterate> 
              </table>
              <% } %>
                <% if (request.getAttribute("audit_item_inventory_resultList") != null) { %>
                    <h1 class="green">
                      <bean:message key="audit.issuance.item.inventory.header" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                        <td>
	                     <bean:message key="issuance.item.type" /><br/>
	                     <select name="inventory_id" class="dropdown" >
	                     	<option value="-1" <% if (-1 == currentID) { %>selected<% } %>>
	                     		<bean:message key="issuance.item.allitems" />
	                     	</option>
                <logic:iterate indexId="i" id="i_item" name="item_inventory_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItemInventory" >
	                     	<option value="<%=i_item.getId() %>" <% if (i_item.getId() == currentID) { %>selected<% } %>>
	                     		<%=i_item.getIssuanceItem().getDescription() + " : " + i_item.getDescription() %>
	                     	</option>
				</logic:iterate>
	                     </select>
                        </td>
                        <% if (globalAdmin) { %>
                        <td>
	                     <bean:message key="issuance.item.station" /><br/>
                        	<html:select property="stationsearch_ID" styleClass="dropdown" >
								<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
							</html:select>
                        </td>
                        <% } %>
                      </tr>
                    </table>
              <div align="center">
	                     <input type="submit" name="view_inventory" id="button"  
							value="<bean:message key="issuance.item.button.view" />" >
						 </input>
                    </div>
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
	                     <bean:message key="issuance.item.status" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit.agent" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit.date" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="i_item" name="audit_item_inventory_resultList" type="com.bagnet.nettracer.tracing.db.issuance.AuditIssuanceItemInventory" >
                  <tr>
                      <td>
              			 <%=i_item.getIssuanceItem().getCategory().getDescription()%>
                      </td>
	                  <td>
	                     <%=i_item.getIssuanceItem().getDescription()%>
	                  </td>
	                  <td>
	                     <%=df.format(i_item.getCost())%>
	                  </td>	                  
	                  <td>
	                     <%=i_item.getDescription()%>
	                  </td>
	                  <td>
	                     <%=i_item.getBarcode()%>&nbsp;
	                  </td>
	                  <td>
	                     <bean:message key="<%="issuance.item.tradetype." + i_item.getTradeType()%>" />
	                  </td>
	                  <td>
	                     <bean:message key="<%=i_item.getInventoryStatus().getKey()%>" />
	                  </td>
	                  <td>
	                  	 <% String incID = (i_item.getIncidentID() != null ? i_item.getIncidentID() : ""); %>
	                  	 <% if (i_item.isVerifiedIncident()) { %>
	                  	 	<a href="<%="searchIncident.do?incident=" + incID %>" ><%=incID %></a>
	                     <% } else if (i_item.getIncidentID() != null && i_item.getIncidentID().equals(IssuanceItemBMO.SPECIAL_LOAN_ID)) { %>
	                     	<a href="###" 
	                     	onclick="loadSpecialNeedViewModal('<%=i_item.getFirstNameJavascript() %>', '<%=i_item.getLastNameJavascript() %>', '<%=i_item.getAddress1Javascript() %>', 
	                     			'<%=i_item.getAddress2Javascript() %>', '<%=i_item.getCityJavascript() %>', '<%=i_item.getStateJavascript() %>', '<%=i_item.getProvinceJavascript() %>', 
	                     			'<%=i_item.getZipJavascript() %>', '<%=i_item.getCountryJavascript() %>', '<%=i_item.getPhoneNumberJavascript() %>', '<%=i_item.getSpecialNeedDescriptionJavascript() %>')"
	                     	><bean:message key="issuance.item.button.snloan" /></a>
	                     <% } else { %>
	                     	<%=incID %>
	                     <% } %>&nbsp;
	                  </td>
	                  <td>
	                     <%=i_item.getEditAgent().getUsername() %>
	                  </td>
	                  <td>
	                     <%=form.getDisplayDate(i_item.getEditDate()) %>
	                  </td>
                  </tr>
                </logic:iterate> 
              </table>
              <% } %>
              <div align="center">
			                  <html:submit property="back" styleId="button">
			                    <bean:message key="Back" />
			                  </html:submit>
                    </div>
					
                  </html:form>
					