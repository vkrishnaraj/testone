<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<td><bean:message key="colname.ohd_status" /> <br>
<logic:notEqual name="OnHandForm" property="ohd_id" value="">
	<html:select name="OnHandForm" property="status.status_ID"
		styleClass="dropdown">
		<html:options collection="ohdStatusList" property="status_ID"
			labelProperty="description" />
	</html:select>
</logic:notEqual> <logic:equal name="OnHandForm" property="ohd_id" value="">
	<input type=text class="textfield" size=4
		value="<bean:message key="OnHandForm.new_status"/>" disabled>
</logic:equal></td>