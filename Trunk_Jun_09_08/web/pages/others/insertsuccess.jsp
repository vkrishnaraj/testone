<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.reporting.ReportingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%
	int reportType = -1;
	String receiptType = "";
	boolean ntfsUser = PropertyBMO.isTrue("ntfs.user");
%>

<tr>
  
  <td id="middlecolumn">
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
	    <h1 class="green">
            <logic:present name="lostdelay" scope="request">
              <bean:message key="message.lostdelay.success" />
              <p>
                <bean:message key="colname.incident_num" />
                :
                <a href='lostDelay.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                <%
                	reportType = ReportingConstants.LOST_RECEIPT_RPT;
                	receiptType = "LostReceipt";
                %>
              </logic:present>
              <logic:present name="missingarticles" scope="request">
                <bean:message key="message.missingarticles.success" />
                <p>
                  <bean:message key="colname.incident_num" />
                  :
                  <a href='missing.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                <%
                	reportType = ReportingConstants.MISSING_RECEPIT_RPT;
                	receiptType = "MissingRecepit";
               	%>
                </logic:present>
                <logic:present name="damaged" scope="request">
                  <bean:message key="message.damaged.success" />
                  <p>
                    <bean:message key="colname.incident_num" />
                    :
                    <a href='damaged.do?incident_ID=<bean:write name="Incident_ID" scope="request"/>'><bean:write name="Incident_ID" scope="request" /></a>
                <%
                	reportType = ReportingConstants.DAMAGE_RECEPIT_RPT;
                	receiptType = "DamagedReceipt";
               	%>
                  </logic:present>
                </h1></td>
              </tr>
              <tr>
                <td id="printreceipt">
                	<br>
	                <a href='#'
		              	onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%= reportType %>&incident=<bean:write name="Incident_ID" scope="request" />&new=1','<%= receiptType %>',800,600);return false;">
		              		<img src="deployment/main/images/nettracer/icon_printrecpt.gif" width="12" height="12">
		            </a>
           			<a href='#'
          				onclick="openReportWindow('searchIncident.do?receipt=1&toprint=<%= reportType %>&incident=<bean:write name="Incident_ID" scope="request" />&new=1','<%= receiptType %>',800,600);return false;">
          				Print Receipt
          			</a>
          			<br>
          			<br>
      			</td>
              </tr>
              <% if (ntfsUser) { %>
              <tr class="<%=request.getAttribute("fraudStatus") %>">
              	<td>
              		<h2 id="fraudlink">
           	  		<a href='fraud_results.do?incident=<bean:write name="Incident_ID" scope="request"/>'><bean:message key="insert.success.match.results"/></a>
              		</h2>
              	</td>
              </tr>
              <% } %>
            </table>
