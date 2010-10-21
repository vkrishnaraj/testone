<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<%@page import="com.bagnet.nettracer.reporting.ReportingConstants"%>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/ajax_forall.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
  
	var cal1xx = new CalendarPopup();	
  
    function changebutton() {
      document.WorldTracerSearchForm.saveButton.disabled = true;
      document.WorldTracerSearchForm.saveButton.value = "<bean:message key="ajax.please_wait" />";
      document.WorldTracerSearchForm.save.disabled = false;
    }
    
    function undoChangebutton() {
      document.WorldTracerSearchForm.saveButton.disabled = false;
      document.WorldTracerSearchForm.saveButton.value = "<bean:message key="button.bdo_send" />";
      document.WorldTracerSearchForm.save.disabled = true;
    }


</SCRIPT>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  <html:form action="worldtraceraf.do">
  <html:hidden property="rawtext" value="1"/>
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
          
            <logic:present parameter="ahl" scope="request">
              <bean:message key="header.worldtracersearchahl" />
            </logic:present>
            <logic:present parameter="ohd" scope="request">
              <bean:message key="header.worldtracersearchohd" />
            </logic:present>
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
          <br>
        
              
              <logic:present parameter="ahl" scope="request">
                
                  <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td align=center>
              <bean:message key="label.worldtracersearchahl" />
                    <br>
                <html:text property="ahl_id" size="13" maxlength="13" styleClass="textfield" value="" />
              </td>
              <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit styleId="button">
                    <bean:message key="button.search" />
                  </html:submit>
                </td>
              </tr>
            </table>
                
              </logic:present>
             
             
              <logic:present parameter="ohd" scope="request">
               
                
                  <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td align=center>
               <bean:message key="label.worldtracersearchohd" />
                    <br>
                <html:text property="ohd_id" size="13" maxlength="13" styleClass="textfield" value="" />
              </td>
              <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit styleId="button">
                    <bean:message key="button.search" />
                  </html:submit>
                </td>
              </tr>
            </table>
              </logic:present>
        
            
            
          </html:form>
