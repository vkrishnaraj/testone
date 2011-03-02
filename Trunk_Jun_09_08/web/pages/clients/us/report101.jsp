<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%
  Agent agent = (Agent)session.getAttribute("user");
%>

                     <tr>
			                <td>
			                  <bean:message key="colname.report_output_type" />
			                  :
			                </td>
			                <td>
    			                  <html:radio property="outputtype" value="2" />
    			                  <bean:message key="radio.xls" />
			                </td>
			              </tr>
			              <tr>
			                <td colspan="2" align="center" valign="top" bgcolor=white>
			                  <br>
			                  <INPUT type="button" value="Back" id="button" onClick="history.back()">
			                  &nbsp;
			                  <html:button property="createbutton" styleId="button" onclick="changebutton();">
			                    <bean:message key="button.createreport" />
			                  </html:button>
			                </td>
			              </tr>
		              	<logic:present name="reportfile" scope="request">
			              
			              <tr>
			                <td colspan=2 align=center bgcolor=white>
			                  <br>
			                  <br>
			                  <a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></a>
			                  <p>
			                    <a target="reportwin" href="reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>"><b><bean:message key="link.save_report" /></a>
		                  </td>
		                </tr>
		             	 	</logic:present>
                
              