<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="org.apache.struts.action.DynaActionForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<script language="javascript">
  
  function goprev() {
    o = document.stationForm;
    o.prevpage.value = "1";
    o.pagination.value="1";
    o.edit.value = "1";
    o.stationId.value = "<bean:write name="stationForm" property="stationId"/>";
    o.submit();
  }

  function gonext() {
    o = document.stationForm;
    o.nextpage.value="1";
    o.pagination.value="1";
    o.edit.value = "1";
    o.stationId.value = "<bean:write name="stationForm" property="stationId"/>";
    o.submit();
  }

  function gopage(i) {
    o = document.stationForm;
    o.currpage.value = i;
    o.pagination.value="1";
    o.edit.value = "1";
    o.stationId.value = "<bean:write name="stationForm" property="stationId"/>";
    o.submit();

  }
  function updatePagination() {
	    return true;
	}

</script>

<%
  Agent a = (Agent)session.getAttribute("user");
%>
  <html:form action="stationAdmin.do" method="post" onsubmit="return validateStationForm(this);">
    <html:javascript formName="stationForm" />
    <html:hidden name="stationForm" property="stationId" />
    <input type="hidden" name="edit" value="">
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.editStation" />
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
            <bean:message key="station" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#administration/work_with_stations.htm#edit station');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
          <table class="form2" cellspacing="0" cellpadding="0">
            <font color=red>
              <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            </font>
            <tr>
              <td>
                <bean:message key="header.stationCode" />
                <font color=red>
                  *:
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="stationCode" size="12" maxlength="12" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.stationDesc" />
                  <font color=red>
                    *
                  </font>
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="stationDesc" size="20" maxlength="25" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="header.companyCode" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="companyCode" size="20" maxlength="25" readonly="true" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.street_addr" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="addr1" size="20" maxlength="25" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.street_addr" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="addr2" size="20" maxlength="25" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.city" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="city" size="20" maxlength="25" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.state" />
                  :
                </td>
                <td>
                  <html:select styleClass="dropdown" name="stationForm" property="state_ID">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="statelist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.country" />
                  :
                </td>
                <td>
                  <html:select styleClass="dropdown" name="stationForm" property="countrycode_ID">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <html:options collection="countrylist" property="value" labelProperty="label" />
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.zip" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="zip" size="12" maxlength="11" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.phone" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="phone" size="20" maxlength="50" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.associated_airport" />
                  :
                </td>
                <td>
                  <html:select styleClass="dropdown" name="stationForm" property="associated_airport">
                    <html:option value="">
                      <bean:message key="select.none" />
                    </html:option>
                    <logic:notEmpty name="airportlist">
                      <html:options collection="airportlist" property="airport_code" labelProperty="airport_code" />
                    </logic:notEmpty>
                  </html:select>
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.airport_location" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="airport_location" size="50" maxlength="100" />
                </td>
              </tr>
<%
              if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_COMPANIES, a)) {
%>
			  <logic:present name="lzStations" scope="request">
	              <tr>
	                <td>
	                  <bean:message key="movetolz.rowname.incidentlz" />
	                  :
	                </td>
	                <td>
	                  <html:select styleClass="dropdown" name="stationForm" property="lz_id">
	                    <html:options collection="lzStations" property="value" labelProperty="label" />
	                  </html:select>
	                </td>
	              </tr>
	          </logic:present>
<%
              }
%>

              <tr>
                <td>
                  <bean:message key="colname.operation_hours" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="operation_hours" size="20" maxlength="25" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.station_region" />
                  :
                </td>
                <td>
                  <jsp:include page="/pages/admin/station_regions.jsp" />

                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.station_region_mgr" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="station_region_mgr" size="50" maxlength="100" />
                </td>
              </tr>
              <tr>
                <td>
                  <bean:message key="colname.goal" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="goal" size="10" maxlength="10" />
                </td>
              </tr>
              <tr>
	            <td>
	              <bean:message key="active" />
	              <font color=red>
	                *
	              </font>
	              :
	            </td>
	            <td>
	              <html:select name="stationForm" property="active" styleClass="dropdown">
	                <html:option value="true">
	                  <bean:message key="select.yes" />
	                </html:option>
	                <html:option value="false">
	                  <bean:message key="select.no" />
	                </html:option>
	              </html:select>
	            </td>
	          </tr>
	          <tr>
                <td>
                  <bean:message key="colname.wt_station" />
                  :
                </td>
                <td>
                  <html:text styleClass="textfield" name="stationForm" property="wt_stationcode" size="10" maxlength="10" />
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
                  <html:submit styleId="button" property="save">
                    <bean:message key="button.save" />
                  </html:submit>
                </td>
              </tr>
            </table>
            <br>
            <logic:present name="stationForm" property="stationId">
              <h1 class="green">
                <bean:message key="station_users" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Administration.htm#Maintain_Stations');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <table class="form2" cellspacing="0" cellpadding="0">
                <logic:present name="agentList" scope="request">
                  <tr>
                    <td>
                      <b><bean:message key="header.agentUsername" /></b>
                    </td>
                    <td>
                      <b><bean:message key="header.agentStation" /></b>
                    </td>
                    <td>
                      <b><bean:message key="header.agentGroup" /></b>
                    </td>
                    <td>
                      <b><bean:message key="header.agentLname" /></b>
                    </td>
                    <td>
                      <b><bean:message key="header.agentFname" /></b>
                    </td>
                    <td>
                      <b><bean:message key="header.agentMname" /></b>
                    </td>
                    <td>
                      <b><bean:message key="header.agentActive" /></b>
                    </td>
                  </tr>
                  <logic:iterate id="agent" name="agentList">
                    <tr>
<%
                      if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_MAINTAIN_AGENTS, a)) {
%>
                        <td>
                          <A HREF="agentAdmin.do?edit=1&stationId=<bean:write name="agent" property="station.station_ID"/>&agentId=<bean:write name="agent" property="agent_ID"/>"><bean:write name="agent" property="username" /></A>
                        </td>
<%
                      } else {
%>
                        <td>
                          <bean:write name="agent" property="username" />
                        </td>
<%
                      }
%>
                      <td>
                        <bean:write name="agent" property="station.stationcode" />
                      </td>
                      <td>
                        <bean:write name="agent" property="group.description" />
                      </td>
                      <td>
                        <bean:write name="agent" property="lastname" />
                        &nbsp;
                      </td>
                      <td>
                        <bean:write name="agent" property="firstname" />
                        &nbsp;
                      </td>
                      <td>
                        <bean:write name="agent" property="mname" />
                        &nbsp;
                      </td>
                      <td>
                        <logic:equal name="agent" property="active" value="true">
                          <bean:message key="select.yes" />
                        </logic:equal>
                        <logic:notEqual name="agent" property="active" value="true">
                          <bean:message key="select.no" />
                        </logic:notEqual>
                      </td>
                    </tr>
                  </logic:iterate>
	              
	              <tr>
	                <td colspan="8">
  	                <jsp:include page="/pages/includes/pagination_incl.jsp" />
	                </td>
	              </tr>
	              
                </logic:present>
                <tr>
                  <td colspan="8" align="center">
                    <html:submit styleId="button" property="addNewAgent">
                      <bean:message key="button.add.station.agent" />
                    </html:submit>
                  </td>
                </tr>
              </table>
            </logic:present>
          </html:form>
