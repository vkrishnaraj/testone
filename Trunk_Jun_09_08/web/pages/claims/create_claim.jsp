<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
    <form action="create_claim.do" method="post" onsubmit="fillzero(this.incident_ID, 13);">
    
    <tr>
      <td colspan="3" id="pageheadercell">
        <div id="pageheaderleft">
          <h1>
            <bean:message key="header.claim_reportnum" />
          </h1>
        </div>
        <div id="pageheaderright">
          <table id="pageheaderright">
            <tr>
              <!--jsp:include page="/pages/includes/mail_incl.jsp" /-->
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
          <br />
          <table class="form2" cellspacing="0" cellpadding="0">
            <tr>
              <td align=center>
                <bean:message key="colname.mbr_report_num" />
                <br />
                <input type="text" name="incidentId" size="13" maxlength="13" class="textfield" value="" onblur="fillzero(this,13);" />
              </td>
              <tr>
                <td align="center" valign="top" colspan="12">
                  <html:submit property="populate" styleId="button">
                    <bean:message key="button.populate" />
                  </html:submit>
                  <html:submit property="skipPrepopulate" styleId="button">
                    <bean:message key="button.skip_prepopulate" />
                  </html:submit>
                </td>
              </tr>
            </table>
          </form>
