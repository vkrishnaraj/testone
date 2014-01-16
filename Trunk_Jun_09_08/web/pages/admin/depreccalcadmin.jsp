<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.DeprecCalcAdminForm" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
  DeprecCalcAdminForm myform = (DeprecCalcAdminForm) session.getAttribute("deprecCalcAdminForm");
%>
  
  
  <html:form action="deprecCalcAdmin.do" method="post" enctype="multipart/form-data" >
    
  <td id="middlecolumn">
    
	  <div id="maincontent">


			<logic:present name="saved" scope="request">
				<font color=green> <bean:message
						key="deprec.calc.rules.saved" />
				</font>
				<br />
			</logic:present>
			<logic:present name="catDeleted" scope="request">
				<font color=green> <bean:message
						key="deprec.category.deleted" />
				</font>
				<br />
			</logic:present>
			<font color=red> 
				<logic:messagesPresent message="true">
					<html:messages id="msg" message="true">
						<bean:write name="msg" />
						<br />
					</html:messages>
				</logic:messagesPresent>
			</font> <br>

<!--  POC0 -->


<h1 class="green"><bean:message key="header.deprec.calc.admin" /> </h1>

<h2><bean:message key="header.general.deprec.rules" /></h2>
<table class='form2_ld' cellspacing="0" cellpadding="0">
	<tr>
		<td class="header" width="20%"><bean:message key="deprec.calc.20.wo.receipt"/></td>
		<td><html:text name="deprecCalcAdminForm" property="lessTwentyDeprec" size="5" styleClass="textfield" /><bean:message key="deprec.calc.per.depreciation"/></td>
	</tr>
	<tr>
		<td class="header" width="20%"><bean:message key="deprec.calc.20.150.wo.receipt"/></td>
		<td><html:text name="deprecCalcAdminForm" property="twentyOnefiftyDeprec" size="5" styleClass="textfield" /><bean:message key="deprec.calc.per.depreciation"/></td>
	</tr>
	<tr>
		<td class="header" width="20%"><bean:message key="deprec.calc.150.wo.receipt"/></td>
		<td><html:text name="deprecCalcAdminForm" property="onefiftyDeprec" size="5" styleClass="textfield" /><bean:message key="deprec.calc.per.depreciation"/></td>
	</tr>
	<tr>
		<td class="header" width="20%"><bean:message key="deprec.calc.no.purchase.date"/></td>
		<td><html:select name="deprecCalcAdminForm" property="noDates" styleClass="dropdown" >
			<html:option value="<%=String.valueOf(TracingConstants.NODATE_NO_IMPACT) %>"><bean:message key="deprec.calc.no.impact"/></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.NODATE_SAME_NO_RECEIPT) %>"><bean:message key="deprec.calc.same.no.receipt"/></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.NODATE_SAME_MAX_DEPREC) %>"><bean:message key="deprec.calc.same.max.depreciate"/></html:option>
		</html:select></td>
	</tr>
</table>
<h2><bean:message key="header.category.deprec.rules" /></h2>
<table class='form2_ld' cellspacing="0" cellpadding="0">
	<tr>
		<td class="header" rowspan="2"><strong><bean:message key="deprec.calc.category"/></strong></td>
		<td class="header" rowspan="2"><strong><bean:message key="deprec.calc.calculation.method"/></strong></td>
		<td class="header" rowspan="2"><strong><bean:message key="deprec.covered.coc.general"/></strong></td>
		<td class="header" rowspan="2"><strong><bean:message key="deprec.calc.flat.rate"/></strong></td>

		<td class="header" colspan="5"><strong><bean:message key="deprec.calc.defined"/></strong></td>
		<td class="header" rowspan="2"><strong><bean:message key="deprec.calc.delete"/></strong></td>  
	</tr>
	<tr>
		<td class="header"><strong><bean:message key="deprec.calc.1.year"/></strong></td>
		<td class="header"><strong><bean:message key="deprec.calc.2.year"/></strong></td>
		<td class="header"><strong><bean:message key="deprec.calc.3.year"/></strong></td>
		<td class="header"><strong><bean:message key="deprec.calc.each.year"/></strong></td>
		<td class="header"><strong><bean:message key="deprec.calc.max.allowed"/></strong></td>
	</tr>

		
		<!-- TODO: add Logic:Iterate for Depreciation_Category -->
		<logic:iterate id="category" indexId="i" name="deprecCalcAdminForm" property="categories" type="com.bagnet.nettracer.tracing.db.Depreciation_Category">
		<tr>
			<td>
				
				<input type="hidden" name="category[<%=i %>].id" value="<bean:write name="category" property="id"/>" />
				<logic:equal value="0" property="id" name="category">
					<html:text  name="category" property="name" maxlength="30"  styleClass="textfield" size="10" indexed="true"/>
				</logic:equal>
				<logic:notEqual value="0" property="id" name="category">
					<logic:empty name="category" property="name">
						<html:text  name="category" property="name" maxlength="30"  styleClass="textfield" size="10" indexed="true"/>
					</logic:empty>
					<logic:notEmpty  name="category" property="name">
						<bean:write name="category" property="name"/>
						<input type="hidden" name="category[<%=i %>].name" value="<bean:write name="category" property="name"/>" />
					</logic:notEmpty>
				</logic:notEqual>
			</td>
			<td>
				<html:select name="category" property="calcMethod" styleClass="dropdown" indexed="true" >
					<html:option value="<%=String.valueOf(TracingConstants.DEFINED_RATE) %>"><bean:message key="deprec.defined.rate"/></html:option> 
					<html:option value="<%=String.valueOf(TracingConstants.FLAT_RATE) %>"><bean:message key="deprec.flat.rate" /></html:option> 
				</html:select>
				
			</td>
			<td><input type="checkbox" name="category[<%=i %>].notCoveredCoc" <logic:equal name="category" property="notCoveredCoc" value="true">checked="checked"</logic:equal> /></td>
			<td><html:text  name="category" property="flatRate" size="5" styleClass="textfield" indexed="true"/></td>
			<td><html:text  name="category" property="firstYear" size="5" styleClass="textfield" indexed="true" /></td>
			<td><html:text  name="category" property="secondYear" size="5" styleClass="textfield" indexed="true" /></td>
			<td><html:text  name="category" property="thirdYear" size="5" styleClass="textfield" indexed="true" /></td>
			<td><html:text  name="category" property="eachYear" size="5" styleClass="textfield" indexed="true" /></td>
			<td><html:text  name="category" property="maxDeprec" size="5" styleClass="textfield" indexed="true" /></td>
			<td>
				  <html:submit styleId="button" property="deleteCategory" indexed="true">
                    <bean:message key="deprec.calc.delete" />
                  </html:submit>
			</td>
		</tr>
		</logic:iterate>
		
		
	<tr>
		<td colspan="10">
			<html:submit property="addcategory" styleId="button">
				<bean:message key="deprec.add.new.category" />
			</html:submit>
		</td>
	</tr>
</table>
<center>	<html:submit property="save" styleId="button">
				<bean:message key="deprec.save.rules"/>
			</html:submit>
</center>


          </html:form>
