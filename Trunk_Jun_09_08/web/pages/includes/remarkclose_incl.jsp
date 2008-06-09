<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<SCRIPT LANGUAGE="JavaScript">
  function textCounter2(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } else {
      countfield.value = maxlimit - field.value.length;
    }
  }
  // End -->
</SCRIPT>
<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
 
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      cssFormClass = "form2_pil";
    }
  }
%>
  <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
    <logic:iterate id="remark" indexId="i" name="incidentForm" property="remarklist" type="com.bagnet.nettracer.tracing.db.Remark">
      <logic:equal name="remark" property="remarktype" value="<%= "" + TracingConstants.REMARK_CLOSING %>">
        <logic:present name="remark" property="agent">
        	<bean:define id="agent" name="remark" property="agent" type="com.bagnet.nettracer.tracing.db.Agent" />
        </logic:present>
        <tr>
          <td valign="top">
            <a name='addremark<%= i %>'></a>
            <bean:message key="colname.date" />
            :
            <bean:write name="remark" property="dispcreatetime" />
          </td>
          <td>
            <bean:message key="colname.station" />
            :
                <logic:present name="remark" property="agent">
   
	                <bean:write name="agent" property="companycode_ID" />
	                &nbsp;
	                <bean:write name="agent" property="station.stationcode" />
	              </logic:present>
          </td>
          <td>
            <bean:message key="colname.agent" />
            :
                <logic:present name="remark" property="agent">
                <bean:write name="agent" property="username" />
                </logic:present>
          </td>
        </tr>
        <tr>
          <td valign="top" colspan=3>
<%
            if (a.getGroup().getDescription().equalsIgnoreCase("Admin") || remark.getRemarktext() == null
                    || remark.getRemarktext().trim().length() <= 0) {
%>
<%
              String remarkDescription = "remark[" + i + "].remarktext";
              String remarkText        = "this.form.elements['" + remarkDescription + "']";
              String remarkText2       = "this.form.elements['" + remarkDescription + "2']";
%>
              <textarea name="<%= remarkDescription %>" cols="80" rows="10" onkeydown="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);" onkeyup="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);"><%= remark.getRemarktext() %></textarea>
              <input name="<%= remarkDescription + "2" %>" type="text" value="1500" size="4" maxlength="4" disabled="true" />
              <br>
              <logic:notEqual name="currentstatus" scope="request" value='<%= "" + TracingConstants.MBR_STATUS_CLOSED %>'>
                <html:submit styleId="button" property="deleteCloseRemark" indexed="true">
                  <bean:message key="button.delete_remark" />
                </html:submit>
              </logic:notEqual>
<%
            } else {
%>
              <bean:write name="remark" property="remarktext" />
<%
            }
%>
          </td>
        </tr>
      </logic:equal>
    </logic:iterate>
  </table>

    <center><html:submit property="addcloseremark" styleId="button">
      <bean:message key="button.add_remark" />
    </html:submit></center>

