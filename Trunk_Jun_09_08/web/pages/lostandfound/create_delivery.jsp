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
 	
%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
</SCRIPT>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="delivery.id" action="create_delivery.do" method="post" onsubmit="return validateDeliveryForm(this);">
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.taskmanager" />
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
				<h1 class="green">
		        	<bean:message key="header.create.delivery" />
		        </h1>
    			<span class="reqfield">*</span>
   				<bean:message key="message.required" /> 
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
       				<tr>
						<td>
							<bean:message key="colname.lf.lost.id" />
							<br/>
							<html:text name="createDeliveryForm" property="item.lost.id" disabled="true" styleClass="disabledtextfield" />
						</td>
						<td>
							<bean:message key="colname.lf.tracking.number" />&nbsp;<span class="reqfield">*</span>
							<br/>
							<html:text name="createDeliveryForm" property="item.trackingNumber" size="20" styleClass="textfield" />
						</td>
					</tr>
				</table>
         		<br/>
				<center>
					<input id="button" type="button" value='<bean:message key="claim.button.back" />' onClick="history.back()">&nbsp;&nbsp;
					<html:submit property="save" styleId="button">
						<bean:message key="button.save" />
					</html:submit>
				</center>
   			</div>
   		</td>
   	</tr>
</html:form>
