<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<tr>
  
  <td id="middlecolumn">
    
      <table class="form2" cellspacing="0" cellpadding="0">
        <tr>
          <td>
          	<h1>
              <font color=red>
                <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
              </font>
            </h1>
          </td>
        </tr>
	    <tr>
	      <td align=center>
	        <br>
	        <br>
	        <INPUT Id="button" type="button" value="Back" onClick="history.back()">
	        <p />
	      </td>
	    </tr>
      </table>

 