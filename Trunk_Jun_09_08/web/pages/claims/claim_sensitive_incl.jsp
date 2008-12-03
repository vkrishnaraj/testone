
<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>

<td colspan="2"><bean:message key="colname.ssn" /> <br>
<html:text property="ssn" size="15" maxlength="9" styleClass="textfield" />
</td>
</tr>
<tr>
	<td nowrap><bean:message key="colname.dlstate" /> <br>
	<div id="tohide2"><html:select property="dlstate"
		styleClass="dropdown">
		<html:option value="">
			<bean:message key="select.none" />
		</html:option>
		<html:options collection="statelist" property="value"
			labelProperty="label" />
	</html:select></div>
	</td>
	<td><bean:message key="colname.drivers" /> <br>
	<html:text property="driverslicense" size="20" maxlength="15"
		styleClass="textfield" /></td>

	<td nowrap><bean:message key="colname.country_of_issue" /> <br>
	<html:select property="countryofissue" styleClass="dropdown">
		<html:option value="">
			<bean:message key="select.none" />
		</html:option>
		<html:options collection="countrylist" property="value"
			labelProperty="label" />
	</html:select></td>
	<td nowrap><bean:message key="colname.common_num" /> <br>
	<html:text property="commonnum" size="22" maxlength="20"
		styleClass="textfield" /></td>