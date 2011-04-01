<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@page import="com.bagnet.nettracer.tracing.forms.PrivacyPermissionsForm"%>

<%@ page import="java.util.Iterator,
				java.util.List"%>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
    
  
  <html:form action="privacypermissions.do?save=1">
  
   <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    <tr>
      <td id="middlecolumn">
        <div id="maincontent">
        <h1 class="green">
          <bean:message key="privacypermissions.header" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        	<table class="form2_dam">
        		<logic:present name="success" scope="request">
        			<logic:equal name="success" scope="request" value="1">
        			<center>Update Successful</center>
        			</logic:equal>
        			<logic:equal name="success" scope="request" value="0">
        			<center>Update Failed</center>
        			</logic:equal>
        		</logic:present>
        	
        		<tr>
        			<td>
        			</td>
        			<td>
        				Default Access
        			</td>
        			<td>
        				Approved Access
        			</td>
        		</tr>
        		
        		<%
        		List componentList = (List)request.getAttribute("components");
        		for (Iterator i = componentList.iterator(); i.hasNext(); ) {
        			String label = (String)i.next();
        			String defProperty = "def." + label;
        			String reqProperty = "req." + label;
        			String messageKey = "privacypermission.label." + label;
        		%>
        		<tr>
        			<td>
        				<bean:message key="<%=messageKey%>"/>
        			</td>
        			<td>
        			<html:checkbox  styleId='<%=defProperty %>' name='privacyPermissionsForm' property="<%=defProperty %>" />
        			</td>
        			<td>
        			<html:checkbox  styleId='<%=reqProperty %>' name='privacyPermissionsForm' property="<%=reqProperty %>" />
        			</td>
        		</tr>
        		<%}%>
        		
        		            	          <tr>
            <td colspan="3" align="center">
              <INPUT Id="button" type="button" value="Back" onClick="history.back()">
              &nbsp;
              <html:submit property="save" styleId="button">
                <bean:message key="button.save" />
              </html:submit>
            </td>
            
        	</table>
        	
        </div>
      </td>
    </tr>

            
   
  </html:form>
