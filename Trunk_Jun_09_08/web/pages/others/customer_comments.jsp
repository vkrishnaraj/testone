<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>


<tr>
  <td colspan="3" id="pageheadercell">
  <div id="pageheaderleft">
  <h1><bean:message key="header.customerComments" /> ( <bean:write
    name="CustomerCommentsForm" property="incident_id" scope="request" /> )</h1>
  </div>
  <div id="pageheaderright">
  <table id="pageheaderright">
    <tr>
      <td><a href="#"
        onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message
        key="Help" /></a></td>
    </tr>
  </table>
  </div>
  </td>
</tr>

<tr>
  <td colspan="3" id="navmenucell">
  <div class="menu">
  <dl>
    <dd><a href="searchIncident.do?incident=<bean:write name="CustomerCommentsForm" property="incident_id" />"> <span class="aa">&nbsp; <br />
    &nbsp;</span> <span class="bb"><bean:message
      key="menu.incident_info" /></span> <span class="cc">&nbsp; <br />
    &nbsp;</span></a></dd>
    <dd><a href=""> <span class="aab">&nbsp; <br />
    &nbsp;</span> <span class="bbb"><bean:message key="menu.customercomments" /></span> <span
      class="ccb">&nbsp; <br />
    &nbsp;</span></a></dd>
  </dl>
  </div>
  </td>
</tr>
<tr>
  <td id="middlecolumn"><html:form action="customerComments.do" method="post">
    <html:hidden property="incident_id"/>
    <html:hidden property="id"/>

    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td align="center"><html:textarea property="text"
          name="CustomerCommentsForm" cols="80" rows="15"></html:textarea> <br />
          <logic:notEqual name="CustomerCommentsForm" property="readOnly" value="true">
            <html:submit property="save" styleId="button">
              <bean:message key="button.save" />
            </html:submit>
          </logic:notEqual>
        </td>
      </tr>
    </table>
  </html:form>