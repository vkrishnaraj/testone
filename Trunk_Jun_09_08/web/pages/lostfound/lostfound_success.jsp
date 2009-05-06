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
<%
            String found = (String)request.getAttribute("found");
%>
<%
            if (found == null) {
%>
              <bean:message key="message.success.lostreport" />
              <p>
                <bean:message key="message.lost.number" />
                <a href='addLost.do?file_ref_number=<bean:write name="file_ref_number" scope="request"/>'><bean:write name="file_ref_number" scope="request" /></a>
              </td>
<%
            } else {
%>
              <bean:message key="message.success.foundreport" />
              <p>
                <bean:message key="message.found.number" />
                <a href='addFound.do?file_ref_number=<bean:write name="file_ref_number" scope="request"/>'><bean:write name="file_ref_number" scope="request" /></a>
              </td>
<%
            }
%>
          </tr>
        </table>
