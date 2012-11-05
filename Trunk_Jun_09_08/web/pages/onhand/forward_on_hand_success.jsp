<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.*;" %>
<tr>
  
  <td id="middlecolumn">
    
    <table class="form2" cellspacing="0" cellpadding="0">
      <tr>
        <td>
          <h1 class="green">
            <bean:message key="message.onhand.forward" />
            <logic:present name="ohdidlist" scope="request">
              <br>
              
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
            </logic:present>
            <logic:present  name="ohd_ID" scope="request">
              <bean:message key="message.onhand.report" />
              &nbsp;
              <a href='addOnHandBag.do?ohd_ID=<bean:write name="ohd_ID" scope="request"/>'><bean:write name="ohd_ID" scope="request" /></a>
              &nbsp;
              <bean:message key="message.onhand.created" />
            </logic:present>
          </td>
        </tr>
      </table>
