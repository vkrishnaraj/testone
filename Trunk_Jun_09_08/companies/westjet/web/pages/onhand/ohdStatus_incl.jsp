<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<td>
	<div style="float: left;">
	<bean:message key="colname.ohd_status" /> <br />
	<logic:notEqual name="OnHandForm" property="ohd_id" value="">
		<html:select name="OnHandForm" property="status.status_ID" styleClass="dropdown">
			<html:options collection="ohdStatusList" property="status_ID" labelProperty="description" />
		</html:select>
		</div>
		<div style="float: left;margin-left: 15px;display:table-cell;vertical-align:middle">
		<html:checkbox disabled="true" name="OnHandForm" property="earlyBag" value="1"><bean:message key="label.early.bag"/></html:checkbox>
		</div>
	</logic:notEqual>
	<logic:equal name="OnHandForm" property="ohd_id" value="">
		<input type=text class="textfield" size=4 value='<bean:message key="OnHandForm.new_status"/>' disabled="disabled" >
		</div>
		<div style="float: left;margin-left: 15px;">
		<html:checkbox name="OnHandForm" property="earlyBag" value="1"><bean:message key="label.early.bag"/></html:checkbox>
		</div>
	</logic:equal>
</td>
