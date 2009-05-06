<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<jsp:include page="/pages/includes/validation_incl.jsp" />
<html:form action="worldtracerqoh.do" method="post" onsubmit="return validateWorldTracerReqQOHForm(this);">
	<html:javascript formName="worldTracerReqQOHForm" />

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
          <bean:message key="header.wt.request_qoh_title" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#create transfer request');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>

        	
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td><bean:message key="colname.request_ahl.req" />:</td>
              <td colspan="3"><html:text property="matchingAhl" size="20" maxlength="12" styleClass="textfield"/></td>
            </tr>
            <tr>
            	<td><bean:message key="colname.bag.tag.req" />:</td>
              <td colspan="3"><html:text property="bagTag" size="20" maxlength="10" styleClass="textfield"/></td>
            <tr>
              <td><bean:message key="colname.wt_request_station.req" />:</td>
              <td><html:text property="fromStation" size="5" maxlength="3" styleClass="textfield"/></td>
              <td><bean:message key="colname.wt_request_airline.req" />:</td>
              <td><html:text property="fromAirline" size="5" maxlength="2" styleClass="textfield"/></td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_fi" />:</td>
              <td colspan="3"><html:text property="furtherInfo" size="60" maxlength="55" styleClass="textfield"/></td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_tx" />:</td>
              <td nowrap="nowrap" colspan="3">
              	<logic:iterate id="teletype" name="worldTracerReqQOHForm" property="teletypes" indexId="index" type="java.lang.String" offset="0" length="4">
					<html:text property='<%="teletype["+index+"]"%>' size="12" maxlength="10" styleClass="textfield" />
              	</logic:iterate>
              </td>
            </tr>
            <tr>
              <td><bean:message key="colname.wt_roh_passname" />:</td>
              <td colspan="3"><html:text property="paxName" size="40" maxlength="38" styleClass="textfield"/></td>
            </tr>


            <tr>
              <td colspan="4" align="center">
                <INPUT id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
                <html:submit styleId="button" property="save"  onclick="return validatereqOHDFields(this.form);" >
                  <bean:message key="button.request" />
                </html:submit>
              </td>
            </tr>
          </table>
        </html:form>
