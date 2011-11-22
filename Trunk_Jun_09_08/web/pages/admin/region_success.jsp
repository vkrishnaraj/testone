<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html:form action="region.do" method="post">
     <tr>
  
  <td id="middlecolumn"> 
      <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
        <logic:present name="success"><bean:message key="regions.update.success" /></logic:present>
        <logic:present name="error"><bean:message key="regions.update.failure" /></logic:present>
          </h1>
        </td>
      </tr>
       <tr>
      <td>
      <br/><a href="region.do?view=1"><bean:message key="regions.return.manage" /></a>
      </td>
      </tr>
      </table>
      </td>
      </tr>
      </html:form>
      