<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<jsp:include page="../includes/validation_incl.jsp" />
<html:form action="addOnHandBag.do" method="post" enctype="multipart/form-data" onsubmit="return submitMassform(this);">
  <tr>
    <td colspan="3" id="pageheadercell">
      <div id="pageheaderleft">
        <h1>
          <bean:message key="header.mass_add_on_hand_title" />
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
  <!-- ICONS MENU -->
  <!-- END ICONS MENU -->
  <tr>
    <!-- MIDDLE COLUMN -->
    <td id="middlecolumn">
      <!-- MAIN BODY -->
      <div id="maincontent">
        <h1 class="green">
          <bean:message key="colname.bag_tag_number" />
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#on-hand_reports/mass_on-hand_bag.htm#add_mass_on_hand');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
        </h1>
        <span class="reqfield">*</span>
        <bean:message key="Required" />
        <font color=red>
          <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
        </font>
        <br>
        <table class="form2" cellspacing="0" cellpadding="0">
          <tr>
            <td>
              <input type="text" name="bagTagNumber1" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber2" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber3" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber4" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber5" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber6" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber7" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber8" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber9" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber10" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber11" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber12" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber13" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber14" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber15" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber16" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber17" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber18" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber19" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber20" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber21" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber22" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber23" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber24" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber25" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber26" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber27" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber28" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber29" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber30" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber31" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber32" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber33" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber34" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber35" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
          <tr>
            <td>
              <input type="text" name="bagTagNumber36" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber37" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber38" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber39" size="14" maxlength="13" style="textfield" />
            </td>
            <td>
              <input type="text" name="bagTagNumber40" size="14" maxlength="13" style="textfield" />
            </td>
          </tr>
        </table>
      </div>
      <input type="hidden" name="mass">
      <html:hidden property="bagTagNumber" value="" />
      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td colspan="5">
            &nbsp;
          </td>
          <tr>
            <td colspan="5" align="center">
              <html:submit styleId="button" property="savetracing">
                <bean:message key="button.savetracingohd" />
              </html:submit>
            </td>
          </tr>
        </table>
      </html:form>
