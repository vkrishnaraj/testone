<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<bean:message key="colname.report_method" /> 
<br> 
<html:select property="reportmethod" styleClass="dropdown">
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_IN_PERSON) %>">
		<bean:message key="select.in_person" />
	</html:option>
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_BSO_PHONE) %>">
		<bean:message key="select.bsophone" />
	</html:option>
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_CALL_CENTER) %>">
		<bean:message key="select.callcenter" />
	</html:option>
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_KIOSK) %>">
		<bean:message key="select.kiosk" />
	</html:option>
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_CBRO_CLAIM) %>">
		<bean:message key="select.cbro.claim" />
	</html:option>
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_ONLINE) %>">
		<bean:message key="select.online" />
	</html:option>
	<html:option value="<%=String.valueOf(TracingConstants.REPORT_METHOD_CBRO_PHONE) %>">
		<bean:message key="select.cbro.phone" />
	</html:option>
</html:select>