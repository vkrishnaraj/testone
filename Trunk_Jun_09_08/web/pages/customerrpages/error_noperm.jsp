<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<tr>
  
  <td id="middlecolumn">
    
    <h1 class="green">
      <bean:message key="error.error_noperm" />
    </h1>
    <p>
      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td>
            <bean:message key="error.error_noperm.desc" />
          </td>
        </tr>
        <tr>
          <td align=center>
            <br>
            <br>
            <INPUT Id="button" type="button" value="Back" onClick="history.back()">
          </td>
        </tr>
      </table>
