<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.IncidentForm" %>



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
  <h1 class="green">
    <bean:message key="header.incident.checklist" />
  </h1>
      
      <div id="pax_0">
        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
          <tr>
          <script type="text/javascript" src="../jquery.bgiframe.js"></script>
          <script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/jquery.bgiframe.min.js"></script>
            <td align="center"> 
              &nbsp;<br />
              <script type="text/javascript">
          		function checklistModalBox() {
              		jQuery.ui.dialog.defaults.bgiframe = true;
              		jQuery("#dialog").dialog({bgiframe : true,
        				autoOpen: false, modal: true, draggable: false, resizable: false, 
        				width: 620, height: 300, title: 'Incident Checklist' 
        			});
					jQuery('#dialog-inner-content').html(getLoadingContent());	
					jQuery("#dialog").dialog("open");	
					jQuery('#dialog-inner-content').load("incidentChecklist.do?incident_id=<bean:write name='incidentForm' property='incident_ID'/>", {}, function() {checklistModalCallback();});
          		}
          	 </script>
			<input type="button" value="View Incident Checklist" onclick="checklistModalBox();" id="button" />

              <br />&nbsp;
            </td>
          </tr>
        </table>
        </div>

      <br>
      <br>
      &nbsp;&nbsp;&uarr;
      <a href="#"><bean:message key="link.to_top" /></a>
      <br>
      <br>

