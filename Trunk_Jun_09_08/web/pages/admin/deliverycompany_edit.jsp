<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.MaintainDeliveryCompanyForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <jsp:include page="../includes/validation_incl.jsp" />
  <html:form action="bdoAdmin.do" method="post" onsubmit="return validateMaintainDeliveryCompanyForm(this);">
    <html:javascript formName="MaintainDeliveryCompanyForm" />
    <html:hidden name="MaintainDeliveryCompanyForm" property="delivercompany_ID" />
    <input type="hidden" name="delete1" value="" />
    <input type="hidden" name="deleteServiceLevelID" value="" />
    <input type="hidden" name="deleteDelivCoStation" value="" />
	<input type="hidden" name="description" value="n/a" />
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.editDeliveryCompany" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <jsp:include page="../includes/mail_incl.jsp" />
              <td>
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
    <!-- END PAGE HEADER/SEARCH -->
    <tr>
      <!-- MIDDLE COLUMN -->
      <td id="middlecolumn">
        <!-- MAIN BODY -->
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="deliveryCompany" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="header.deliverycompany_name" />
                <font color=red>
                  *:
                </td>
                <td>
                  <html:text styleClass="textfield" name="MaintainDeliveryCompanyForm" property="name" size="30" maxlength="50" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.deliverycompany_address" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="MaintainDeliveryCompanyForm" property="address" size="30" maxlength="50" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.phone" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="MaintainDeliveryCompanyForm" property="phone" size="30" maxlength="50" />
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  &nbsp;
                </td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  <INPUT Id="button" type="button" value="Back" onClick="history.back()">
                  &nbsp;
                  <html:submit styleId="button" property="saveDeliveryCompany">
                    <bean:message key="button.save" />
                  </html:submit>
                </td>
              </tr>
            </table>
            <br>
            
            <!-- Service Levels -->
            <logic:greaterThan name="MaintainDeliveryCompanyForm" property="delivercompany_ID" value="0">
              <h1 class="green">
                <bean:message key="header.deliverycompany_servicelevels" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0">
                <logic:present name="serviceLevelList" scope="request">
                  <tr>
                    <td width="90%">
                      <b><bean:message key="header.deliverycompany_servicelevel" /></b>
                    </td>
                    <td width="10%">
                      <b><bean:message key="delete" /></b>
                    </td>
                  </tr>
                  <logic:iterate id="serviceLevel" name="serviceLevelList">
                    <tr>
                      <td width="90%">
                        <A HREF="bdoAdmin.do?editServiceLevel=1&delivercompany_ID=<bean:write name="MaintainDeliveryCompanyForm" property="delivercompany_ID"/>&serviceLevelId=<bean:write name="serviceLevel" property="servicelevel_ID"/>"><bean:write name="serviceLevel" property="description" /></A>
                      </td>
                      <td width="10%">
                      <input type="button" name="a" value="<bean:message key="delete"/>" onclick="deleteDeliveryCoServiceLevel('<bean:write name="serviceLevel" property="servicelevel_ID"/>','<bean:write name="serviceLevel" property="description"/>');" id="button">
                      </td>
                    </tr>
                  </logic:iterate>
                  <tr>
                    <td colspan="2">
                      &nbsp;
                    </td>
                  </tr>
                </logic:present>
                <tr>
                  <td colspan="2" align="center">
                    <html:submit styleId="button" property="addNewServiceLevel">
                      <bean:message key="button.deliverycompany_addnewservicelevel" />
                    </html:submit>
                  </td>
                </tr>
              </table>
            </logic:greaterThan>
            <br>
            
            <!-- Stations -->
            <logic:greaterThan name="MaintainDeliveryCompanyForm" property="delivercompany_ID" value="0">
              <h1 class="green">
                <bean:message key="header.deliverycompany_stations" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0">
                <logic:present name="stationList" scope="request">
                  <tr>
                    <td width="90%">
                      <b><bean:message key="header.deliverycompany_station" /></b>
                    </td>
                    <td width="10%">
                      <b><bean:message key="delete" /></b>
                    </td>
                  </tr>
                  <logic:iterate id="station" name="stationList">
                    <tr>
                      <td width="90%">
                        <bean:write name="station" property="stationcode" />
                      </td>
                      <td width="10%">
                      <input type="button" name="deleteStation" value="<bean:message key="delete"/>" onclick="deleteDeliveryCompanyStation('<bean:write name="station" property="stationcode"/>','<bean:write name="station" property="station_ID"/>');" id="button">
                      </td>
                    </tr>
                  </logic:iterate>
                  <tr>
                    <td colspan="2">
                      &nbsp;
                    </td>
                  </tr>
                </logic:present>
                <tr>
                  <td colspan="2" align="center">
                    <logic:present name="entireStationList">
                      <html:select styleClass="dropdown" name="MaintainDeliveryCompanyForm" property="station_ID">
                        <html:options collection="entireStationList" property="station_ID" labelProperty="stationcode" />
                      </html:select>
                      <html:submit styleId="button" property="addNewStation">
                        <bean:message key="button.deliverycompany_addNewStation" />
                      </html:submit>
                    </logic:present>
                  </td>
                </tr>
              </table>
            </logic:greaterThan>
          </html:form>
