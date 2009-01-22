<%@ page language="java"%>
<%@ page import="com.bagnet.nettracer.tracing.db.ExpensePayout"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%
  Agent a = (Agent) session.getAttribute("user");
%>
<!-- Calendar includes -->
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    <!--
  var cal1xx = new CalendarPopup(); 

    // -->
  </SCRIPT>
<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd><a href='searchIncident.do?incident='><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.incident_info" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_resolution.do"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message key="menu.claim_payout" /></span>
    <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    <%
      if (UserPermissions.hasPermission(
          TracingConstants.SYSTEM_COMPONENT_NAME_CLAIM_PRORATE, a)) {
    %>
    <dd><a href="claim_prorate.do"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_prorate" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    <%
      }
    %>

    <dd><a href="claim_settlement.do?screen=1"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_process" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=2"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_customer" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="#"><span class="aab">&nbsp; <br />
    &nbsp;</span> <span class="bbb"><bean:message
      key="menu.claim_baggage" /></span> <span class="ccb">&nbsp; <br />
    &nbsp;</span></a></dd>

    <dd><a href="claim_settlement.do?screen=4"><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.claim_summary" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>

  </dl>



  <!-- END ICONS MENU -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn"><!-- MAIN BODY -->
    <div id="maincontent"><html:form action="claim_settlement.do"
      method="post">


      <h1 class="green"><bean:message
        key="claimsettlement.baggageInfo" /></h1>


      <logic:present name="claimSettlementForm" property="bagList">
        <logic:iterate id="theBag" indexId="i"
          name="claimSettlementForm" property="bagList"
          type="com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag">
          <p />&nbsp;
          <h1 class="green"><bean:message
      key="claimsettlement.bagNumber" />: <%=i%></h1>

          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" class="header"><strong><bean:message
                key="claimsettlement.bagDesc" /></strong></td>
            </tr>
            <tr>
              <td width=33%><bean:message
                key="claimsettlement.color" /></td>
              <td width=66%><html:select indexed="true" name="theBag"
                property="color" styleClass="dropdown">
                <html:options collection="colorlist" property="value"
                  labelProperty="label" />
              </html:select></td>
            </tr>

            <tr>
              <td width=33%><bean:message
                key="claimsettlement.type" /></td>
              <td width=66%>
  <html:select name="theBag" property="type" styleClass="dropdown" indexed="true">
                <html:options collection="typelist" property="value" labelProperty="label" />
              </html:select>
            
</td>
            </tr>
            <tr>
              <td width=33%><bean:message
                key="claimsettlement.manufacturer" /></td>
              <td width=66%><html:text name="settlementBag"
                property="manufacturer" size="15" maxlength="100" /></td>
            </tr>
            <tr>
              <td colspan="2" class="header"><strong><bean:message key="claimsettlement.contents" /></strong>
              </td>
            </tr>
            <tr>
              <td colspan="2">
              <table width="100%">
                <logic:iterate id="contents" name="theBag" property="inventory" indexId="j">
                  
                
                <tr>
                  <td><bean:message key="claimsettlement.category" /><br>
                    <html:select indexed="true" name="contents" property="categoryType_ID" styleClass="dropdown">
                      <html:option value="">
                        <bean:message key="select.please_select" />
                      </html:option>
                      <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="categorytype" />
                    </html:select>

</td>
                  <td><bean:message key="claimsettlement.contentDescription" /><br>
                  
                  <html:text indexed="true" name="contents" property="description" size="60" maxlength="255"/>

                  </td>
                  <td align="center">&nbsp;<br>
                    <html:submit indexed="true" styleId="button" property="deleteBag[<%=i %>].content[<%=j %>]">
                      <bean:message key="claimsettlement.deleteContent" />
                    </html:submit>
                </tr>
                </logic:iterate>
              </table>


              <center>
          <html:submit indexed="true" styleId="button" property="theBag[<%=i%>].addContents">
                  <bean:message key="claimsettlement.addContents" />
                </html:submit>
              </center>

              </td>
            </tr>


          </table>

          <center><input type="submit" name="addinventory[]"
            value="Add Additional Content" id="button"></center>


    </logic:iterate>
   





  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td align="center" valign="top"><br>
      <html:submit property="save3" styleId="button">
        <bean:message key="button.save" />
      </html:submit></td>
    </tr>
  </table>
   </logic:present>

  <p /></html:form>



