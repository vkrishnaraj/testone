<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<html:select property="checkedlocation" styleClass="dropdown">
	<html:option value="0">
		<bean:message key="select.please_select" />
	</html:option>
	<html:option value="1">
		<bean:message key="select.curb_side" />
	</html:option>
	<html:option value="2" >
		<bean:message key="select.ticket_counter" />
	</html:option>
	<html:option value="3">
		<bean:message key="select.gate" />
	</html:option>
	<html:option value="4">
		<bean:message key="select.remote" />
	</html:option>
	<html:option value="5">
		<bean:message key="select.plane_side" />
	</html:option>
	<html:option value="6">
		<bean:message key="select.online" />
	</html:option>
	<html:option value="7">
		<bean:message key="select.kiosk" />
	</html:option>
		<html:option value="8">
		<bean:message key="select.dme" />
	</html:option>
</html:select>