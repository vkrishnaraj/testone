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
             <table>
              <%
              String ohd_id = (String)request.getAttribute("closedOhd_ID");
              if (ohd_id != null && ohd_id.length() >= 13) {
				%>
             
             <tr>
             <td> 
            <bean:message key="message.onhand.number.success" />
            </td>
            </tr>
            <tr>
            <td>
            <%
                StringTokenizer st = new StringTokenizer(ohd_id, ",");
                while (st.hasMoreTokens()) {
                  String token = (String)st.nextToken();
%>
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
                <a href='addOnHandBag.do?ohd_ID=${ohd_id}'>${ohd_id}</a>
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
			 <%
              String notClosedOhd_ID = (String)request.getAttribute("notClosedOhd_ID");

              if (notClosedOhd_ID != null && notClosedOhd_ID.length() >= 13) {
            	  %>
             <tr>
             <td>
            <bean:message key="message.onhand.number.failed" />
            </td>
            </tr>
            <tr>
            <td>
            	  <%
                StringTokenizer st = new StringTokenizer(notClosedOhd_ID, ",");

                while (st.hasMoreTokens()) {
                  String token = (String)st.nextToken();
%>
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
                <a href='addOnHandBag.do?ohd_ID=${ohd_id}'>${ohd_id}</a>
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
            </td>
          </tr>
        </table>
