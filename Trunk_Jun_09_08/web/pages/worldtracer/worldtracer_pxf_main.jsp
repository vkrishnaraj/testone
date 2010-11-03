<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<html:form action="worldtracerpxf.do" method="post" onsubmit="return validateWorldTracerPXFForm(this);">
	<html:javascript formName="worldTracerPXFForm" />

  <SCRIPT LANGUAGE="JavaScript">
    function textCounter(field, countfield, maxlimit) {
      if (field.value.length > maxlimit) {
        field.value = field.value.substring(0, maxlimit);
      } else {
        countfield.value = maxlimit - field.value.length;
      }
      var hiddenInfo = document.getElementById('furtherInfoHidden');
      hiddenInfo.value = field.value;
    }

  </SCRIPT>




  <tr>
    
    <td id="middlecolumn">
      
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.wt.pxf_title" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#task_manager/view_trace_results.htm#create transfer request');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
         
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>

            	<td>Action Message Address</td>
            	<td>&nbsp;</td>
            	<td>&nbsp;</td>
            </tr>
            
            <tr>
              <th>&nbsp;</th>
              <th><bean:message key="colname.wt_request_station" />:</th>
              <th><bean:message key="colname.wt_request_airline" />:</th>
              <th>Area:</th>
            </tr>
            <!-- 1st Action File -->
            <tr>
              <td>&nbsp;</td>
              <td><html:text property="station_1" size="5" maxlength="3" styleClass="textfield"/></td>
              <td><html:text property="airline_1" size="5" maxlength="2" styleClass="textfield"/></td>
              <td>
              	<html:select name="worldTracerPXFForm"
					property="area_1" styleClass="dropdown">
					<html:option value="AA">AA Area</html:option>
					<html:option value="FW">FW Area</html:option>
					<html:option value="AP">AP Area</html:option>
				</html:select></td>
            </tr>
            <!-- 2nd Action File -->
            <tr>
              <td>&nbsp;</td>
              <td><html:text property="station_2" size="5" maxlength="3" styleClass="textfield"/></td>
              <td><html:text property="airline_2" size="5" maxlength="2" styleClass="textfield"/></td>
              <td>
              	<html:select name="worldTracerPXFForm"
					property="area_2" styleClass="dropdown">
					<html:option value="AA">AA Area</html:option>
					<html:option value="FW">FW Area</html:option>
					<html:option value="AP">AP Area</html:option>
				</html:select></td>
            </tr>
            <!-- 3rd Action File -->
            <tr>
              <td>&nbsp;</td>
              <td><html:text property="station_3" size="5" maxlength="3" styleClass="textfield"/></td>
              <td><html:text property="airline_3" size="5" maxlength="2" styleClass="textfield"/></td>
              <td>
              	<html:select name="worldTracerPXFForm"
					property="area_3" styleClass="dropdown">
					<html:option value="AA">AA Area</html:option>
					<html:option value="FW">FW Area</html:option>
					<html:option value="AP">AP Area</html:option>
				</html:select></td>
            </tr>
            <!-- 4th Action File -->
            <tr>
              <td>&nbsp;</td>
              <td><html:text property="station_4" size="5" maxlength="3" styleClass="textfield"/></td>
              <td><html:text property="airline_4" size="5" maxlength="2" styleClass="textfield"/></td>
              <td>
              	<html:select name="worldTracerPXFForm"
					property="area_4" styleClass="dropdown">
					<html:option value="AA">AA Area</html:option>
					<html:option value="FW">FW Area</html:option>
					<html:option value="AP">AP Area</html:option>
				</html:select></td>
            </tr>
            <!-- 5th Action File -->
            <tr>
              <td>&nbsp;</td>
              <td><html:text property="station_5" size="5" maxlength="3" styleClass="textfield"/></td>
              <td><html:text property="airline_5" size="5" maxlength="2" styleClass="textfield"/></td>
              <td>
              	<html:select name="worldTracerPXFForm"
					property="area_5" styleClass="dropdown">
					<html:option value="AA">AA Area</html:option>
					<html:option value="FW">FW Area</html:option>
					<html:option value="AP">AP Area</html:option>
				</html:select></td>
            </tr>
            <tr><td colspan="4">&nbsp;</td></tr>
            <tr>
			  <th colspan="4"><bean:message key="colname.wt_roh_tx" />:</th>
            </tr>
            <tr>
              <td nowrap="nowrap" colspan="4">
              	<logic:iterate id="teletype" name="worldTracerPXFForm" property="teletypes" indexId="index" type="java.lang.String" offset="0" length="5">
					<html:text property='<%="teletype["+index+"]"%>' size="12" maxlength="10" styleClass="textfield" />
              	</logic:iterate>
              </td>
            </tr>
            <tr><td colspan="4"></td></tr>
            <tr><td colspan="4"></td></tr>
            <tr>
			  <th colspan="4"><bean:message key="colname.wt_pxf_message" />:</th>
            </tr>
            <tr>
<%
            String infoDescription = "furtherInformation";
            String infoText        = "this.form.elements['" + infoDescription + "']";
            String infoText2       = "this.form.elements['" + infoDescription + "2']";
%>
              <td colspan="4">
              <textarea name="<%= infoDescription %>" cols="80" rows="10" onkeydown="textCounter(<%= infoText %>, <%= infoText2 %>,2800);"
              onkeyup="textCounter(<%= infoText %>, <%= infoText2 %>,2800);"></textarea>
              <html:hidden property="furtherInfo" styleId="furtherInfoHidden"/>
              <input name="<%= infoDescription + "2" %>" type="text" value="2800" size="4" maxlength="4" disabled="true" />
              <!--  html:text property="furtherInfo" size="60" maxlength="2800" styleClass="textfield"/ --></td>
            </tr>
            <tr>
              <td colspan="4" align="center">
                <INPUT id="button" type="button" value="Back" onClick="history.back()">
                &nbsp;
                <html:submit styleId="wtbutton" property="save"  onclick="return validatereqPXFFields(this.form);" >
                  <bean:message key="button.send.message" />
                </html:submit>
              </td>
            </tr>
          </table>
          
        </html:form>
