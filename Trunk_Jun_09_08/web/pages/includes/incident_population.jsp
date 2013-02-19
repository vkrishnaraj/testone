<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass = "form2";
%>

      <%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
      <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
        <% if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.RESERVATION_BY_BAGTAG)) { %>
          <tr>
            <td colspan="2"><strong><bean:message key="please_enter_one" /></strong><br />&nbsp;</td>
          </tr>
          <tr>
            <td width="25%"><bean:message key="colname.bag_tag_number" />:</td>
            <td><html:text property="bagTagNumber" size="15" maxlength="10" styleClass="textfield"/>
              <% if (TracerProperties.isTrue(a.getCompanycode_ID(),TracerProperties.RESERVATION_POPULATION_SEARCH) && request.getAttribute("lostdelay") != null) { %>
                &nbsp;<html:submit property="prepopSearch" styleId="button" onclick="buttonSelected = 'prepopSearch'">
                  <bean:message key="button.prepopulate.search" />
                </html:submit>
              <% } %>
            
            </td>
          </tr>
        <% } %>
          
        <tr>
          <td><bean:message key="colname.recordlocator" />:</td>
          <td><html:text property="recordlocator" size="15" styleClass="textfield" value="" maxlength="6"/></td>
        </tr>
        <tr>
          <td align="center" valign="top" colspan="12"><html:submit
            property="doprepopulate1" styleId="button" onclick="buttonSelected = 'prepopulate'">
            <bean:message key="button.populate" />
          </html:submit> <html:submit property="skip_prepopulate" styleId="button"  onclick="buttonSelected = null">
            <bean:message key="button.skip_populate"/>
          </html:submit></td>
        </tr>
      </table>