<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.*;" %>
<tr>
  <!-- MIDDLE COLUMN -->
  <td id="middlecolumn">
    <!-- MAIN BODY -->
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <bean:message key="message.success.ohd" />
            <p>
<%
              List ohdidlist = (List)request.getAttribute("ohdidlist");

              for (Iterator i = ohdidlist.iterator(); i.hasNext(); ) {
                String ohd_ID = (String)i.next();
%>
                <bean:message key="message.onhand.number" />
                <a href='addOnHandBag.do?ohd_ID=<%= ohd_ID %>'><%= ohd_ID %></a>
                <br>
<%
              }
%>
            </td>
          </tr>
        </table>
