<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <bean:message key="message.success.ohd" />
            <p>
              <bean:message key="message.onhand.number" />
              <a href='addOnHandBag.do?ohd_ID=<bean:write name="ohd_ID" scope="request"/>'><bean:write name="ohd_ID" scope="request" /></a>
            </td>
          </tr>
        </table>
