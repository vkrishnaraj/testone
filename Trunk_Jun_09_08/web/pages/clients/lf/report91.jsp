<%@ page language="java"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>

<%@ taglib uri="/tags/struts-nested" prefix="nested"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%
	Agent agent = (Agent) session.getAttribute("user");
%>

<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="aero.nettracer.lf.services.LFLogUtil" %>
<html:hidden property="outputtype" value="<%=String.valueOf(TracingConstants.REPORT_OUTPUT_XLS) %>" />
<tr>
	<td>
		<bean:message key="colname.lf.report.created.date.range" />
		(
		<%=agent.getDateformat().getFormat()%>)
		<font color=red> * </font> :
	</td>
	<td>
		<html:text property="starttime" size="11" maxlength="10" styleClass="textfield" />
		<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.starttime,'calendar','<%=agent.getDateformat().getFormat()%>'); return false;">
		-
		<html:text property="endtime" size="11" maxlength="10" styleClass="textfield" />
		<img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.statReportForm.endtime,'calendar2','<%=agent.getDateformat().getFormat()%>'); return false;">
	</td>
</tr>
<tr>
  	<td>
    <bean:message key="colname.lf.report.subcompany" />
    :
  	</td>
  	<td>
  	<% if(agent.getSubcompany()==null){ %>
    <html:select property="subcompCode" styleClass="dropdown" >
      <html:option value="0">
        <bean:message key="select.all" />
      </html:option>
      <html:options collection="airlineallsubcomplist" property="subcompanyCode" labelProperty="subcompanyCode"/>
    </html:select>
    <% } else { %>
    <html:select property="subcompCode" styleClass="dropdown" disabled="true">
      <html:option value="<%=String.valueOf(agent.getSubcompany().getId()) %>" ><%=agent.getSubcompany().getSubcompanyCode() %> </html:option>
    </html:select>
    <% } %>
  	</td>
</tr>
<tr>
	<td colspan="2" align="center" valign="top" bgcolor=white>
		<br>
		<INPUT type="button" value="Back" id="button" onClick="history.back()">
		&nbsp;
		<html:button property="createbutton" styleId="button"
			onclick="changebutton();">
			<bean:message key="button.createreport" />
		</html:button>
	</td>
</tr>
<logic:present name="reportfile" scope="request">
	
	<tr>
		<td colspan=2 align=center bgcolor=white>
			<br>
			<br>
			<a href="#"
				onclick="openReportWindow('reporting?outputtype=<%=request.getAttribute("outputtype")%>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message
						key="link.view_report" />
			</a>
			<p>
				<a target="reportwin"
					href="reporting?outputtype=<%=request.getAttribute("outputtype")%>&reportfile=<bean:write name="reportfile" scope="request"/>"><b><bean:message
							key="link.save_report" />
				</a>
		</td>
	</tr>
</logic:present>