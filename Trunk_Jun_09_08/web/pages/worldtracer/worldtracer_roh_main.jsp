<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<html:form action="worldtracerroh.do" method="post">

  <SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
    }

  </SCRIPT>




  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.request_on_hand_title" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#create transfer request');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>

        	
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td><bean:message key="colname.wt_ahl_id" />:</td>
              <td><html:text property="wt_ahl_id" size="20" maxlength="12"/></td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_ohd_id" />:</td>
              <td><html:text property="wt_ohd_id" size="20" maxlength="12"/></td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_fi" />:</td>
              <td><html:text property="fi" size="60" maxlength="55"/></td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_ag" />:</td>
              <td><html:text property="ag" size="20" maxlength="12"/></td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_tx" />:</td>
              <td nowrap>
              <html:text property="teletype_address1" size="10" maxlength="7"/>
              <html:text property="teletype_address2" size="10" maxlength="7"/>
              <html:text property="teletype_address3" size="10" maxlength="7"/>
              <html:text property="teletype_address4" size="10" maxlength="7"/>
              </td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_passname" />:</td>
              <td><html:text property="lname" size="40" readonly="readonly"/></td>
            </tr>


            <tr>
              <td colspan="2" align="center">
                <INPUT id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
                <html:submit styleId="button" property="save" onclick="return validatereqOHDFields(this.form);">
                  <bean:message key="button.request" />
                   
                </html:submit>
              </td>
            </tr>
          </table>
        </html:form>
