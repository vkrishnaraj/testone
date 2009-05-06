<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent agent = (Agent)session.getAttribute("user");
%>
  <html:form action="billing.do" method="post" onsubmit="return validateBillingForm(this);">
    <html:javascript formName="billingForm" />
    
    <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
    <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
    <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
    <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
    <SCRIPT LANGUAGE="JavaScript">
      
	var cal1xx = new CalendarPopup();	


    </SCRIPT>
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.billing_report" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/forward_to_lz.htm#view bag details');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <br>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan=2>
                  <strong>
                    <bean:message key="header.report_options" />
                  </strong>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="Company" />
                  :
                </td>
                <td>
                  <html:select property="companycode_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="companylistById" property="companyCode_ID" labelProperty="companyCode_ID" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.report_type" />
                  :
                </td>
                <td>
                  <html:select property="itemType_ID" styleClass="dropdown">
                    <html:option value="0">
                      <bean:message key="select.all" />
                    </html:option>
                    <html:options collection="mbrreporttypes" property="itemType_ID" labelProperty="description" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.tran_charge" />
                  :
                </td>
                <td>
                  <html:text property="tran_value" size="14" styleClass="textfield" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.report_date_range" />
                  (
                  <%= agent.getDateformat().getFormat() %>)
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td>
                  <html:text property="starttime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.billingForm.starttime,'calendar','<%= agent.getDateformat().getFormat() %>'); return false;">-
                  <html:text property="endtime" size="11" maxlength="10" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.billingForm.endtime,'calendar2','<%= agent.getDateformat().getFormat() %>'); return false;"></td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.report_output_type" />
                  :
                </td>
                <td>
                  <html:radio property="outputtype" value="0" />
                  <bean:message key="radio.pdf" />
                  <html:radio property="outputtype" value="1" />
                  <bean:message key="radio.html" />
                  <html:radio property="outputtype" value="2" />
                  <bean:message key="radio.xls" />
                  <html:radio property="outputtype" value="3" />
                  <bean:message key="radio.csv" />
                  <html:radio property="outputtype" value="4" />
                  <bean:message key="radio.xml" />
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center" valign="top" bgcolor=white>
                  <br>
                  <INPUT type="button" value="Back" id="button" onClick="history.back()">
                  &nbsp;
                  <html:submit property="create" styleId="button">
                    <bean:message key="button.createreport" />
                  </html:submit>
                </td>
              </tr>
              <logic:present name="reportfile" scope="request">
              
              <tr>
                <td colspan=2 align=center bgcolor=white>
                  <br>
                  <br>
                  <a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></a>
                  <p>
                    <a target="reportwin" href="reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>"><b><bean:message key="link.save_report" /></a>
                  </td>
                </tr>
              </logic:present>
            </table>
            <logic:present name="reportfile" scope="request">
              <script language=javascript>
                

openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request" />','report',800,600);


              </script>
            </logic:present>
          </html:form>
