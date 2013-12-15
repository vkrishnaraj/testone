<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@page import="com.bagnet.nettracer.tracing.forms.BagDropForm"%>

<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>

<%
  Agent a = (Agent)session.getAttribute("user");
%>

 <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
	
	</SCRIPT>

<html:form action="bagDrop.do" method="post">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
			<bean:message key='header.bagdrop.edit.title'/>
        </h1>
      </div>
    </td>
  </tr>

 <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <table class="form2" cellspacing="0" cellpadding="0">
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          </font>
          <tr>
            <td>
               <bean:message key='itin_flightnum'/>
            </td>
            <td>
            <bean:write name="bagDropForm" property="editBagDrop.airline" />
            &nbsp;
            <bean:write name="bagDropForm" property="editBagDrop.flight" />
            </td>
          </tr>
          
          <tr>
            <td>
              <bean:message key='colname.bagdrop.origin'/>
            </td>
            <td>
            <bean:write name="bagDropForm" property="editBagDrop.originStationCode" />
            </td>
          </tr>
          
          <tr>
            <td>
              <bean:message key='colname.bagdrop.dest'/>
            </td>
            <td>
            <bean:write name="bagDropForm" property="editBagDrop.arrivalStationCode" />
            </td>
          </tr>
          
         <tr>
            <td>
              <bean:message key='colname.scharrtime'/>
            </td>
            <td>
            <bean:write name="bagDropForm" property="editBagDrop.dispSchArrivalDateTimeCell" />
            </td>
          </tr>
          
         <tr>
            <td>
              <bean:message key='colname.actarrtime'/>
            </td>
            <td>
            <bean:write name="bagDropForm" property="editBagDrop.dispActArrivalDateTime" />
            </td>
          </tr>
          
          
          <tr>
            <td>
              <bean:message key='colname.bagdrop.bagdropdate'/>
              (<%= a.getDateformat().getFormat() %>)	
            </td>
            <td>
              <html:text name="bagDropForm" property="editBagDrop.dispBagDropDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.bagDropForm.editBagDrop.dispBagDropDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">
            </td>
          </tr>
          <tr>
            <td>
              <bean:message key='colname.bagdrop.bagdroptime'/>
              (<%= a.getTimeformat().getFormat() %>)	 
            </td>
            <td>
              <html:text name="bagDropForm" property="editBagDrop.dispBagDropTime" size="12" maxlength="11" styleClass="textfield" />
            </td>
          </tr>
       
          <tr>
          <td align="center" colspan="2">
            <INPUT type="button" Id="button" value="Back" onClick="history.back()">
            &nbsp;
              <html:submit styleId="button" property="save">
                <bean:message key="button.save" />
              </html:submit>
            </td>
          </tr>
        </table>
       <logic:present name="bagDropForm" property="auditList" scope="request">
       <br>
       <h1 class="green">
          <bean:message key='header.bagdrop.audit.title'/>
        </h1>
       <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td width="20%">
              <b><bean:message key='colname.bagdrop.entrydate'/></b>
            </td>
            <td width="15%">
              <b><bean:message key='agent'/></b>
            </td>
            <td width="20%">
              <b><bean:message key='colname.scharrtime'/></b>
            </td>
           <td width="20%">
              <b><bean:message key='colname.actarrtime'/></b>
            </td>
           <td width="20%">
              <b><bean:message key='colname.bagdrop.bagdroptime'/></b>
            </td>
            <td width="15%">
              <b><bean:message key='colname.bagdrop.entrymethod'/></b>
            </td>
          </tr>

            <logic:iterate id="audit" name="bagDropForm" property="auditList" >
              <tr>
                <td>
             		<bean:write name="audit" property="dispEntryDate"/>&nbsp;
                </td>
                <td>
             		<bean:write name="audit" property="modifyAgent.username"/>&nbsp;
                </td>
                <td >
              		<bean:write name="audit" property="dispSchArrivalDate"/>&nbsp;
                </td>
                <td >
            	    <bean:write name="audit" property="dispActArrivalDate"/>&nbsp;
                </td>
                <td >
                	<bean:write name="audit" property="dispBagDropTime"/>&nbsp;
                </td>
                <td >
              <bean:write name="audit" property="dispEntryMethod"/>&nbsp;
                </td>
              </tr>
            </logic:iterate>
        </table>
       </logic:present>

</html:form>