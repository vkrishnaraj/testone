<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
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
  <html:form focus="sId" action="salvageSearch.do" method="post" onsubmit="fillzero(this.salvageId, 13); return validateSearch(this);">
  
  <jsp:include page="/pages/includes/taskmanager_header.jsp" />
    
    <tr>
      
      <td id="middlecolumn">
        
        <div id="maincontent">
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
            <table class="form2" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <bean:message key="colname.salvage_id" />
                  <br>
                  <html:text property="salvageId" value="" size="20" maxlength="13" styleClass="textfield" styleId="sId" onblur="fillzero(this,13);" />
                </td>
                <td>
                	<bean:message key="colname.salvage_status" />
                	<br>
                	<html:select property="salvageStatus" styleClass="dropdown" >
	                  <html:option value="0">
	                    <bean:message key="salvage.status_open" />
	                  </html:option>
	                  <html:option value="1">
	                    <bean:message key="salvage.status_closed" />
	                  </html:option>
	                  <html:option value="2">
	                    <bean:message key="salvage.status_all" />
	                  </html:option>
	                </html:select>
                </td>
                <td nowrap>
                  <bean:message key="colname.salvage_date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.salvageSearchForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
                  <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.salvageSearchForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
                </td>
              </tr>
              
              <tr>
                <td colspan="3" align="center" valign="top">
                  <html:submit property="search" styleId="button">
                    <bean:message key="button.retrieve" />
                  </html:submit>
                  &nbsp;

                <html:reset styleId="button">
                  <bean:message key="button.reset" />
                </html:reset>
				  &nbsp;				
				<html:button property="salvageId" styleId="button" onclick="document.location.href='salvageEdit.do?create_new=1'">
          			<bean:message key="salvage.create_new" />
          		</html:button>
                </td>
              </tr>
            </table>
            
            <logic:present name="resultList" scope="request">
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.salvage_id" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.salvage_date" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.salvage_status" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.salvage_pickedupby" />
                    </b>
                  </td>
                </tr>
                <logic:iterate id="results" name="resultList" type="com.bagnet.nettracer.tracing.db.salvage.Salvage">
                  <tr>
                    <td>
                      <a href='salvageEdit.do?salvageId=<bean:write name="results" property="salvageId" />'>
                      <bean:write name="results" property="salvageId" />
                      </a>
                    </td>
                    <td>
                      <a href='salvageEdit.do?salvageId=<bean:write name="results" property="salvageId" />'>
                      <%=results.getDisSalvageDate(a.getDateformat().getFormat()) %>
                      </a>
                    </td>
                    <td>
                    	<logic:equal name="results" property="status" value="0">
                      		<bean:message key="salvage.status_open" />
                    	</logic:equal>
                    	<logic:equal name="results" property="status" value="1">
                      		<bean:message key="salvage.status_closed" />
                    	</logic:equal>
                    </td>
                    
                    <td>
                      <bean:write name="results" property="pickedUpByLName" />
                    </td>
                  </tr>
                </logic:iterate> 
              </table>
              <script language=javascript>
                
  document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
