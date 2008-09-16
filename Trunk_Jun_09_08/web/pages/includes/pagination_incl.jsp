<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<logic:present name="pages" scope="request">
  <logic:equal name="currpage" scope="request" value="0">
    &lt;
    <bean:message key="Back" />
  </logic:equal>
  <logic:notEqual name="currpage" scope="request" value="0">
    <a href="#" onclick="goprev();return false;">&lt;
      <bean:message key="Back" /></a>
    &nbsp;
  </logic:notEqual>
  <logic:iterate id="pages" indexId="i" name="pages" scope="request">
<%
int p = i.intValue() + 1;
int curp = Integer.parseInt((String)request.getAttribute("currpage"));
if ((curp <= (i.intValue() + 15)) && (curp >=(i.intValue() - 15))) {
	if (request.getAttribute("currpage").equals(i.toString())) {
		out.println("<b>" + p + "</b>");
	} else {
	  out.println("<a href='#' onclick='gopage(" + i.intValue() + ");return false;'>" + p + "</a>&nbsp;");
	}
}
%>
  </logic:iterate>
  <logic:present name="end" scope="request">
    <bean:message key="Next" />
    &gt;
  </logic:present>
  <logic:notPresent name="end" scope="request">
    <a href="#" onclick="gonext();return false;"><bean:message key="Next" />
      &gt;</a>
  </logic:notPresent>
  <br>
</logic:present>
<!-- end pagination -->
<input type="text" name="rowsperpage" value='<bean:write name="rowsperpage" scope="request"/>' size=3 maxlength=2 class="textfield">
&nbsp;
<bean:message key="RowsPerPage" />
&nbsp;&nbsp;
<input type="submit" name="update" value="update" Id="button" onclick="return updatePagination();">
<!-- pagination -->
<input type="hidden" name="currpage" value='<bean:write name="currpage" scope="request"/>'>
<input type="hidden" name="nextpage"/>
<input type="hidden" name="prevpage"/>
<input type="hidden" name="pagination"/>
<input type="hidden" name="update_pagination"/>
<!-- eof pagination -->

