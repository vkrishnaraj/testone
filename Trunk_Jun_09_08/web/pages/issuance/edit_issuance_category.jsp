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

        <html:form action="editIssuanceCategory.do" method="post" enctype="multipart/form-data" >
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
<% if (request.getAttribute("item_category_resultList") != null) { %>
                    <h1 class="green">
                      <bean:message key="issuance.item.category.header" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <html:hidden property="editcategory" value="" disabled="true"/>
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
              <tr>
                      <td class="header">
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.lostdelay" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.damage" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.missing" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.inventoried" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.active" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.edit.category" />
	                  </td>
                  </tr>
                <logic:iterate indexId="i" id="c_item" name="item_category_resultList" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceCategory" >
                <tr>
                      <td>
              			 <%=c_item.getDescription() %>
                      </td>
                      <td>
              			 <% if (c_item.isLostdelay()) { %><bean:message key="issuance.item.yes" /><% } else { %><bean:message key="issuance.item.no" /><% } %>
                      </td>
                      <td>
              			 <% if (c_item.isDamage()) { %><bean:message key="issuance.item.yes" /><% } else { %><bean:message key="issuance.item.no" /><% } %>
                      </td>
                      <td>
              			 <% if (c_item.isMissing()) { %><bean:message key="issuance.item.yes" /><% } else { %><bean:message key="issuance.item.no" /><% } %>
                      </td>
                      <td>
              			 <% if (c_item.isInventory()) { %><bean:message key="issuance.item.yes" /><% } else { %><bean:message key="issuance.item.no" /><% } %>
                      </td>
                      <td>
              			 <% if (c_item.isActive()) { %><bean:message key="issuance.item.yes" /><% } else { %><bean:message key="issuance.item.no" /><% } %>
                      </td>
	                  <td>
	                     <input type="submit" name="category_edit_<%=c_item.getId()%>" id="button" onclick="this.form.editcategory.value = <%=c_item.getId()%>; this.form.editcategory.disabled = false;" 
							value="<bean:message key="issuance.item.button.edit.category" />" >
						 </input>
	                  </td>
                </tr>
                </logic:iterate>
					  <tr>
		                <td colspan="7" align="center" valign="top">
			                  <html:submit property="addcategory" styleId="button">
			                    <bean:message key="issuance.item.add.category" />
			                  </html:submit>
		                </td>
		              </tr>
                </table>
                <% } else { /* END if (globalAdmin) */ %>
                    <h1 class="green">
                      <bean:message key="edit.issuance.category" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
                      	<td>
                      		<bean:message key="edit.issuance.category.description" /> <br/>
                      		<html:text name="editIssuanceCategoryForm" property="category.description" size="10" styleClass="textfield" />
                      	</td>
                      	<td>
                      		<bean:message key="edit.issuance.category.lostdelay" /> <br/>
                			<html:checkbox name="editIssuanceCategoryForm" property="category.lostdelay" />
                      	</td>
                      	<td>
                      		<bean:message key="edit.issuance.category.missing" /> <br/>
                			<html:checkbox name="editIssuanceCategoryForm" property="category.missing" />
                      	</td>
                      	<td>
                      		<bean:message key="edit.issuance.category.damage" /> <br/>
                			<html:checkbox name="editIssuanceCategoryForm" property="category.damage" />
                      	</td>
                      	<td>
                      		<bean:message key="edit.issuance.category.active" /> <br/>
                			<html:checkbox name="editIssuanceCategoryForm" property="category.active" />
                      	</td>
                      	<td>
                      		<bean:message key="edit.issuance.category.inventory" /> <br/>
                      		<logic:equal name="editIssuanceCategoryForm" property="category.id" value="0">
                				<html:checkbox name="editIssuanceCategoryForm" property="category.inventory" />
                      		</logic:equal>
                      		<logic:notEqual name="editIssuanceCategoryForm" property="category.id" value="0">
                      			<bean:write name="editIssuanceCategoryForm" property="category.inventory" />
                      		</logic:notEqual>
                      	</td>
                      </tr>
              </table>
              <br/>
                    <h1 class="green">
                      <bean:message key="edit.issuance.category.items" />
                    </h1>
                    <font color=red>
                      <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
                    </font>
                    <br />
                    <table class="form2" cellspacing="0" cellpadding="0" width="100%">
                      <tr>
	                  <td class="header">
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td class="header">
	                     <bean:message key="issuance.item.active" />
	                  </td>
                      </tr>
                <logic:iterate indexId="i" id="item" name="editIssuanceCategoryForm" property="items" type="com.bagnet.nettracer.tracing.db.issuance.IssuanceItem" >
                		<tr>
                			<td>
                      		<html:text name="item" property="description" size="10" styleClass="textfield" indexed="true" />
                			</td>
                			<td>
                			<html:checkbox name="item" property="active" indexed="true" />
                			</td>
                		</tr>
                </logic:iterate>
		                <td colspan="3" align="center" valign="top">
			                  <html:submit property="add_category_item" styleId="button">
			                    <bean:message key="edit.issuance.category.add.item" />
			                  </html:submit>
		                </td>
                    </table><br/>
		                <div align="center" >
			                  <html:submit property="save_category" styleId="button">
			                    <bean:message key="edit.issuance.category.save" />
			                  </html:submit>
		                </div>
                    <% } %>
					
                  </html:form>
					