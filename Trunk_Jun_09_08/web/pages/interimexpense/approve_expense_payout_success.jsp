<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.*" %>
<tr>
  <!-- MIDDLE COLUMN -->
  <td id="middlecolumn">
    <!-- MAIN BODY -->
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <bean:message key="message.success.expense.approve" />
            <p>
<%
              String inc_id = (String)request.getAttribute("incident_id");
              String pay_id = (String)request.getAttribute("payout_id");

              if (inc_id != null && inc_id.indexOf(",") != -1) {
                StringTokenizer st  = new StringTokenizer(inc_id, ",");
                StringTokenizer st2 = new StringTokenizer(pay_id, ",");

                while (st.hasMoreTokens()) {
                  String incident_id = (String)st.nextToken();
                  String payout_id   = (String)st2.nextToken();
%>
									<a href='searchIncident.do?incident=<%= incident_id %>'><%= incident_id %></a>
                 <!-- <a href='claim_resolution.do?approveinterim=1&incident_id=<%= incident_id %>&exp_id=<%= payout_id %>'><%= incident_id %></a> -->
                  <br>
<%
                }
              } else {
%>
								<a href='searchIncident.do?incident=<%= inc_id %>'><%= inc_id %></a>
                <!--<a href='claim_resolution.do?approveinterim=1&incident_id=<%= inc_id %>&exp_id=<%= pay_id %>'><%= inc_id %></a>-->
<%
              }
%>
            </td>
          </tr>
        </table>
