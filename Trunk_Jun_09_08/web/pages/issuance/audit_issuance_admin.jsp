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
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>

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
	                     <bean:message key="issuance.item.category" />
                      </td>
	                  <td>
	                     <bean:message key="issuance.item.description" />
	                  </td>
	                  <td>
	                     <bean:message key="issuance.item.quantity" />
	                  </td>
	                  <td>
	                     <bean:message key="issuance.item.quantity.change" />
	                  </td>
	                  <td>
	                     <bean:message key="issuance.item.minimum.quantity" />
	                  </td>
	                  <td>
	                     <bean:message key="issuance.item.incident" />
	                  </td>
	                  <td>
	                     <bean:message key="issuance.item.edit.agent" />
	                  </td>
	                  <td>
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
	                     <%=incID %>
	                  </td>
	                  <td>
	                     <%=q_item.getEditAgent().getUsername() %>
	                  </td>
	                  <td>
	                     <%=q_item.getEditDate() %>
	                  </td>
                  </tr>
                </logic:iterate> 
					  <tr>
		                <td colspan="6" align="center" valign="top">
			                  <html:submit property="back" styleId="button">
			                    <bean:message key="Back" />
			                  </html:submit>
		                </td>
		              </tr>
              </table>
                    
					
                  </html:form>
					