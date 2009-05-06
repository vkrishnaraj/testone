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

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
  var cal1xx = new CalendarPopup(); 


  </SCRIPT>
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="claimsettlement.header.baggageInformation" />
          </h1>
        </div>
      </td>
    </tr>

<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd><a href='searchIncident.do?incident=<bean:write name="claimSettlementForm" property="incident_ID"/>'><span class="aa">&nbsp;
    <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.incident_info" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
            
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
    

  </dl>



  
  <tr>
    
    <td id="middlecolumn">
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
      key="claimsettlement.bagNumber" />: <%=i.intValue() + 1%></h1>

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
              <td width=66%><html:text name="theBag"
                indexed="true" property="manufacturer" size="15" maxlength="100" /></td>
            </tr>
            <tr>
              <td colspan="2" class="header"><strong><bean:message key="claimsettlement.contents" /></strong>
              </td>
            </tr>
            <tr>
              <td colspan="2">
              <table width="100%">
          <logic:iterate id="theContent" name="claimSettlementForm" property="contentsList" indexId="j"
                    type="com.bagnet.nettracer.tracing.db.claims.SettlementBagInventory">
                
    <% 
    
    if (theContent.getClaimSettlementBag().getBagId() == theBag.getBagId() && !theContent.isFlaggedForRemoval()) {

    %>
    
                <tr>
                  <td><bean:message key="claimsettlement.category" /><br>
                    <html:select indexed="true" name="theContent" property="categoryType_ID" styleClass="dropdown">
                      <html:option value="">
                        <bean:message key="select.please_select" />
                      </html:option>
                      <html:options collection="categorylist" property="OHD_CategoryType_ID" labelProperty="description" />
                    </html:select>

</td>
                  <td><bean:message key="claimsettlement.contentDescription" /><br>
                  
                  <html:text indexed="true" name="theContent" property="description" size="60" maxlength="255" styleId="textfield"/>

                  </td>
                  <td align="center">&nbsp;<br>
                    <input type="submit" name="deleteBag[<%=i %>].content[<%=j %>][0]" value='<bean:message key="claimsettlement.deleteContent" />' id="button">
      </tr>
      <%
    }
    %>
                
                </logic:iterate>
              </table>


              <center>
              <input type="submit" name="addContents[<%=i%>][0]" value='<bean:message key="claimsettlement.addContents" />' id="button">
              </center>

              </td>
            </tr>


          </table>

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



