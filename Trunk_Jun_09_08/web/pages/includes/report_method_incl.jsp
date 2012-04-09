<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<bean:message key="colname.report_method" /> 
<br> 
<html:select property="reportmethod" styleClass="dropdown">
	<html:option value="0">
		<bean:message key="select.in_person" />
	</html:option>
	<html:option value="1">
		<bean:message key="select.bsophone" />
	</html:option>
	<html:option value="2">
		<bean:message key="select.callcenter" />
	</html:option>
	<html:option value="3">
		<bean:message key="select.online" />
	</html:option>
	<html:option value="4">
		<bean:message key="select.kiosk" />
	</html:option>
</html:select>