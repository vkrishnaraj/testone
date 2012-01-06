<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ page import="java.util.ArrayList" %>

<% if (((String) request.getAttribute("formName")).equals("enterItemsForm")) { %>
<br/>
<% } %>
<bean:message key="colname.lf.subcategory" />
<br/>
<html:select name='<%=(String) request.getAttribute("formName") %>' property="subCategory" styleClass="dropdown" >
	<html:option value="0"><bean:message key="option.lf.please.select" /></html:option>
	<html:options collection="lfsubcategorylist" property="id" labelProperty="description" />
</html:select>
