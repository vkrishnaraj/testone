<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	


  </SCRIPT>
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form action="css_calls.do">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
  
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
               <bean:message key="TASK_LABEL_CSS_DAILY" />
          </h1>
          <br>

          <font color=red>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
			</font>
            
            <!-- BEGIN -->
            
            <logic:notPresent name="generaltaskForm" property="stationList">
            <center>
               <h1 class="green">No new tasks</h1>
              </center>
              </logic:notPresent>
            <logic:present name="generaltaskForm" property="stationList">
            <br/>
              <input type="checkbox" name="select_all" id="select_all" /><bean:message key="css.select.all" />
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td class="header">
               		<bean:message key="css_sl_select" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_station" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_calls" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_select" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_station" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_calls" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_select" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_station" />
                  </td>
                  <td class="header">
               		<bean:message key="css_sl_calls" />
                  </td>
                </tr>
                <logic:iterate id="cssStation" name="generaltaskForm" property="stationList" type="com.bagnet.nettracer.tracing.dto.CSSStationsDTO">
                  <tr>
                    <td style="width:8%;" align="center">
                    	<html:checkbox name="cssStation" property="station1Checked" indexed="true" />
                    </td>
                    <td style="width:10%;">
                        <html:hidden name="cssStation" property="station1Desc" indexed="true" />
                    	&nbsp;<bean:write name="cssStation" property="station1Desc" />
                    </td>
                    <td style="width:15%;">
                        <html:hidden name="cssStation" property="station1Tasks" indexed="true" />
                    	&nbsp;<bean:write name="cssStation" property="station1Tasks" />
                    </td>
                    <td style="width:8%;" align="center">
                    	<% if (cssStation.getStation2Desc() != null) { %>
                    	<html:checkbox name="cssStation" property="station2Checked" indexed="true" />
                    	<% } %>
                    	&nbsp;
                    </td>
                    <td style="width:10%;">
                        <html:hidden name="cssStation" property="station2Desc" indexed="true" />
                    	&nbsp;<bean:write name="cssStation" property="station2Desc" />
                    </td>
                    <td style="width:15%;">
                        <html:hidden name="cssStation" property="station2Tasks" indexed="true" />
                    	&nbsp;<bean:write name="cssStation" property="station2Tasks" />
                    </td>
                    <td style="width:8%;" align="center">
                    	<% if (cssStation.getStation3Desc() != null) { %>
                    	<html:checkbox name="cssStation" property="station3Checked" indexed="true" />
                    	<% } %>
                    	&nbsp;
                    </td>
                    <td style="width:10%;">
                        <html:hidden name="cssStation" property="station3Desc" indexed="true" />
                    	&nbsp;<bean:write name="cssStation" property="station3Desc" />
                    </td>
                    <td style="width:16%;">
                        <html:hidden name="cssStation" property="station3Tasks" indexed="true" />
                    	&nbsp;<bean:write name="cssStation" property="station3Tasks" />
                    </td>
                  </tr>
                </logic:iterate>
              </table>
              <br/>
            <center>
            	<html:submit property="tasklist" styleId="button"/>
            </center>
              <SCRIPT language="JAVASCRIPT" >
              	jQuery("#select_all").click(function(source) { 
            	    checkboxes = jQuery('input[type="checkbox"]');
            	    for(var i in checkboxes){
            	        checkboxes[i].checked = source.target.checked;
            	    }
            	});
              </SCRIPT>
            </logic:present>
          </html:form>
