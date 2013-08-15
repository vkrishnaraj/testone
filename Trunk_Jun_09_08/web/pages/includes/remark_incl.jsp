<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@page import="com.bagnet.nettracer.tracing.forms.IncidentForm"%>

<SCRIPT LANGUAGE="JavaScript">
  function textCounter2(field, countfield, maxlimit) {
    if (field.value.length > maxlimit) {
      field.value = field.value.substring(0, maxlimit);
    } else {
      countfield.value = maxlimit - field.value.length;
    }
  }
  
	function insertNewLine(elementId) {
		if (elementId.indexOf("remark") != -1 && window.event && window.event.keyCode == 13) {
			insertAtCursor(document.getElementById(elementId), '\n');
			window.event.keyCode = 505;
		}
  	}
	
	function insertAtCursor(myField, myValue) {
		if (document.selection) {
			myField.focus();
			sel = document.selection.createRange();
			sel.text = myValue;
			sel.collapse(false);
			sel.select();
		} else {
			myField.value += myValue;
		}
	}
</SCRIPT>
<%
  Agent a = (Agent)session.getAttribute("user");
  String cssFormClass;
 
  int report_type = 0;
  cssFormClass = "form2_dam";

  if (request.getAttribute("lostdelay") != null) {
    report_type = 1;
    cssFormClass = "form2_ld";
  } else {
    if (request.getAttribute("missing") != null) {
      report_type = 2;
      cssFormClass = "form2_pil";
    }
  }
%>
  <a name="remarks"></a>
  <h1 class="green">
    <bean:message key="header.remarks" />
<%
    if (report_type == 0) {
%>
      <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#damaged_bag_reports/remarks.htm');return false;"><%
      } else if (report_type == 1) {
%>
        <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#lost_delayed_bag_reports/work_with_remarks_(ld).htm');return false;"><%
        } else if (report_type == 2) {
%>
          <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#missing_articles_reports/work_with_remarks_(ma).htm');return false;"><%
          }
%>
          <img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
      </h1>
      <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
        <logic:iterate id="remark" indexId="i" name="incidentForm" property="remarklist" type="com.bagnet.nettracer.tracing.db.Remark">
          <% if(!remark.isSecure() ||  (remark.isSecure() && UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a))){ %>
          <logic:equal name="remark" property="remarktype" value="<%= "" + TracingConstants.REMARK_REGULAR %>">
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
              <%  if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a) && remark.getRemark_ID()==0) { %>
              
              <td>
                <bean:message key="colname.secure" />
                :
                <input type="checkbox" name="remark[<%=i %>].secure" 
                      <logic:equal name="remark" property="secure" value="true">
                        checked="checked"
                      </logic:equal> />
              </td><% } else if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a)) { %>
              <td>
              	<logic:equal name="remark" property="secure" value="true">
              		<span style="color:red"><bean:message key="secure.remark" /></span>
              	</logic:equal>
              	<logic:notEqual name="remark" property="secure" value="true">
              		<bean:message key="general.remark" />
              	</logic:notEqual>
              </td>
              <% } %>
            </tr>
            <tr>
<%
			String remarkId = "remark[" + i + "]";
            String remarkDescription = "remark[" + i + "].remarktext";
            String remarkText        = "this.form.elements['" + remarkDescription + "']";
            String remarkText2       = "this.form.elements['" + remarkDescription + "2']";
%>
<%  		if(remark.getRemark_ID() != 0) {
%>
	
            <td valign="top" colspan=<%=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a))?4:3%>>
<%
                if (UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_UPDATE_REMARKS, a)) {
%>

                  <textarea name="<%= remarkDescription %>" id="<%=remarkId %>" cols="80" rows="10" onkeydown="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);insertNewLine('<%=remarkId %>');" onkeyup="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);"
                  <logic:equal name="incidentForm" property="readonly" value="1"><% if (remark.getRemark_ID() > 0) {%> readonly="readonly"<% } %></logic:equal>
                  ><%= remark.getRemarktext() %></textarea>
                  <input name="<%= remarkDescription + "2" %>" id="<%=remarkId + ".counter" %>" type="text" value="1500" size="4" maxlength="4" disabled="true" />
                  <br>
                  <logic:notEqual name="incidentForm" property="readonly" value="1">
                  <html:submit styleId="button" property="deleteRemark" indexed="true">
                    <bean:message key="button.delete_remark" />
                  </html:submit>
                  </logic:notEqual>
<%
                }
                else {
%>				<bean:write name="remark" property="readonlyremarktext" filter="false"/>	
<%
                }
%>
            	</td>
<%
			} else  {
%>
				<td valign="top" colspan=<%=(UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_SECURE_REMARKS, a))?4:3%>>
                  <textarea name="<%= remarkDescription %>" id="<%=remarkId %>" cols="80" rows="10" onkeydown="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);insertNewLine('<%=remarkId %>');" 
                  	onkeyup="textCounter2(<%= remarkText %>, <%= remarkText2 %>,1500);"><%= remark.getRemarktext() %></textarea>
                  <input name="<%= remarkDescription + "2" %>" id="<%=remarkId + ".counter" %>" type="text" value="1500" size="4" maxlength="4" disabled="true" />
                  <br>
                  <html:submit styleId="button" property="deleteRemark" indexed="true">
                    <bean:message key="button.delete_remark" />
                  </html:submit>
                  </td>
<%
			}
%>
            </tr>
          </logic:equal>
          <% } %>
          
		<input type="hidden" name="remark[<%=i %>].secure" value="<bean:write name="remark" property="secure"/>" />

        </logic:iterate>
      </table>
      <center><html:submit property="addremark" styleId="button">
        <bean:message key="button.add_remark" />
      </html:submit></center>
      <br>
      <br>
      &nbsp;&nbsp;&uarr;
      <a href="#"><bean:message key="link.to_top" /></a>
      <br>
      <br>
