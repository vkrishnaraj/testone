<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<tr>
	<!-- MIDDLE COLUMN -->
	<td id="middlecolumn">
		<!-- MAIN BODY -->
		<table class="form2" cellspacing="0" cellpadding="0">
			<tr>
				<td width="500px">
					<h1 class="green">
						<bean:message key="message.invalid_token" /></h1>
						<br />
							<a href='logon.do?taskmanager=1'><bean:message
									key="continue_cap" />
							</a>
				</td>
			</tr>
		</table>