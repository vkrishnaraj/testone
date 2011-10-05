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
<html:hidden property="outputtype" value="<%=String.valueOf(TracingConstants.REPORT_OUTPUT_XLS) %>" />
<tr>
	<td>
		<bean:message key="reports.reportType" /> :
	</td>
	<td>
		<html:select property="type" styleClass="dropdown" >
			<html:option value="<%=String.valueOf(TracingConstants.LF_TYPE_LOST) %>"><bean:message key="search.type.lost" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>"><bean:message key="search.type.found" /></html:option>
		</html:select>        
	</td>
</tr>
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
    <bean:message key="colname.lf.report.station" />
    :
  	</td>
  	<td>
    <html:select property="station_ID" styleClass="dropdown" >
      <html:option value="0">
        <bean:message key="select.all" />
      </html:option>
      <html:options collection="airlineallstationlist" property="stationcode" labelProperty="stationcode" />
    </html:select>
  	</td>
</tr>
<tr>
  	<td>
    <bean:message key="colname.lf.report.dropoff" />
    :
  	</td>
  	<td>
    <html:select property="stationCode" styleClass="dropdown" >
      <html:option value="0">
        <bean:message key="select.all" />
      </html:option>
      <html:options collection="airlineallstationlist" property="stationcode" labelProperty="stationcode" />
    </html:select>
  	</td>
</tr>
<tr>
	<td>
		<bean:message key="colname.lf.status" />:
	</td>
	<td>
		<html:select property="status_ID" styleClass="dropdown" >
			<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" /></html:option>
		</html:select>                  
	</td>
</tr>
<tr>
	<td>
		<bean:message key="colname.lf.disposition" />:
	</td>
	<td>
		<html:select property="dispositionId" styleClass="dropdown" >
			<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) %>" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" /></html:option>
			<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED) %>" /></html:option>
		</html:select>                  
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