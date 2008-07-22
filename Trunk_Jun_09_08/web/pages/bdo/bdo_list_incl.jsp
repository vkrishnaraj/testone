<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.BDOForm" %>
<%
  int i = 0;
%>
  <tr>
    <td colspan="3" id="navmenucell">
      <div class="menu">
        <dl>
          <logic:notEqual name="BDOForm" property="OHD_ID" value="">
            <dd>
              <a href='addOnHandBag.do?ohd_ID=<bean:write name="BDOForm" property="OHD_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.ohd_main" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </logic:notEqual>
          <logic:notEqual name="BDOForm" property="incident_ID" value="">
            <dd>
              <a href='lostDelay.do?incident_ID=<bean:write name="BDOForm" property="incident_ID"/>'><span class="aa">&nbsp;
                  <br />
                  &nbsp;</span>
                <span class="bb"><bean:message key="menu.incident_info" /></span>
                <span class="cc">&nbsp;
                  <br />
                  &nbsp;</span></a>
            </dd>
          </logic:notEqual>
          <dd>
            <a href="#"><span class="aab">&nbsp;
                <br />
                &nbsp;</span>
              <span class="bbb"><bean:message key="menu.bdo" /></span>
              <span class="ccb">&nbsp;
                <br />
                &nbsp;</span></a>
          </dd>
        </dl>
      </div>
    </td>
  </tr>
  <!-- END ICONS MENU -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="header.bdo_list" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#bdo/bdo.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <logic:present name="inserted" scope="request">
          <br>
          <center><font color=green>
            <bean:message key="prompt.bdo_insert_success" />
          </font></center>
        </logic:present>

        <br>
        <logic:present name="bdo_list" scope="request">
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <b><bean:message key="colname.bdo_id" /></b>
              </td>
              <td>
                <b><bean:message key="colname.date" /></b>
              </td>
              <td>
                <b><bean:message key="colname.agent" /></b>
              </td>
              <td>
                <b><bean:message key="colname.station" /></b>
              </td>
              <td>
                <b><bean:message key="colname.airline" /></b>
              </td>
              <td>
                <b><bean:message key="colname.delivercompany" /></b>
              </td>
              <td>
                <b><bean:message key="colname.servicelevel" /></b>
              </td>
              <td>
                <b><bean:message key="colname.deliverydate" /></b>
              </td>
              <td>
                <b><bean:message key="colname.bagcount" /></b>
              </td>
            </tr>
            <logic:iterate id="bdos" name="bdo_list" scope="request" type="com.bagnet.nettracer.tracing.db.BDO">
<%
              i = 1;
%>
              <tr>
                <td nowrap>
                  <a href="bdo.do?bdo_id=<bean:write name="bdos" property="BDO_ID"/>"><bean:write name="bdos" property="BDO_ID_ref" /></a>
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="dispcreatetime" />
                  &nbsp;
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="agentinit" />
                  &nbsp;
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="station.stationcode" />
                  &nbsp;
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="companycode_ID" />
                  &nbsp;
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="delivercompany.name" />
                  &nbsp;
                </td>
                <td nowrap>
                  <logic:present name="bdos" property="servicelevel">
                    <bean:write name="bdos" property="servicelevel.description" />
                  </logic:present>
                  &nbsp;
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="dispdeliverydate" />
                  &nbsp;
                </td>
                <td nowrap>
                  <bean:write name="bdos" property="bagcount" />
                  &nbsp;
                </td>
              </tr>
            </logic:iterate>
          </table>
        </logic:present>
        <br>
        <br>
<%
        BDOForm bf = (BDOForm)request.getAttribute("BDOForm");

        if (i == 0 || bf.getIncident_ID() != null) {
%>
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td align="center" valign="top">
                <br>
                <html:submit property="createnewbdo" styleId="button">
                  <bean:message key="button.createnewbdo" />
                </html:submit>
              </td>
            </tr>
          </table>
<%
        }
%>
