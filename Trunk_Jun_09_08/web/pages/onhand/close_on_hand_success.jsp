<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.*" %>
<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <bean:message key="message.success.ohd.receive" />
            <p />
<%
              String ohd_id = (String)request.getAttribute("ohd_ID");

              if (ohd_id != null && ohd_id.length() > 13) {
                StringTokenizer st = new StringTokenizer(ohd_id, ",");

                while (st.hasMoreTokens()) {
                  String token = (String)st.nextToken();
%>
                  <bean:message key="message.onhand.number" />
                  <a href='addOnHandBag.do?ohd_ID=<%= token %>'><%= token %></a>
                <p />
                <logic:present name="deliveredFlag" scope="request">
                  <hr width="50%" />
                  <bean:message key="message.pleasedeliverbags" /><p />
                  <a href="deliver.do"><bean:message key="message.pleasedeliver" /></a>
                </logic:present>

<%
                }
              } else {
%>
                <bean:message key="message.onhand.number" />
                <a href='addOnHandBag.do?ohd_ID=<bean:write name="ohd_ID" scope="request"/>'><bean:write name="ohd_ID" scope="request" /></a>
                <p />
                <logic:present name="deliveredFlag" scope="request">
                  <hr width="50%" />
                  <bean:message key="message.pleasedeliverbag" /><p />
                  <a href="deliver.do"><bean:message key="message.pleasedeliver" /></a>
                </logic:present>

<%
              }
              
%>

            </td>
          </tr>
        </table>
