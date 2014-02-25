<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<div id="reportOptionsDiv" style="display:none;" >
	<table style="width:100%;">
		<tr>
			<td style="width:50%;text-align:right;">Output Type:</td>
			<td style="width:50%;text-align:left;">
				<select id="outputSelect" style="font-size:9px;border:1px solid #569ECD;margin:2px 0px 1px 0px;display:inline;" >
					<option value="<%=String.valueOf(TracingConstants.REPORT_OUTPUT_PDF) %>"><bean:message key="radio.pdf" /></option>
					<option value="<%=String.valueOf(TracingConstants.REPORT_OUTPUT_HTML) %>"><bean:message key="radio.html" /></option>
				</select>
			</td>
		</tr>
	</table>
</div>