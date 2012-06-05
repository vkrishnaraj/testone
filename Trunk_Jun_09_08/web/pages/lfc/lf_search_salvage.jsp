<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%
  Agent a = (Agent)session.getAttribute("user");
%>
  
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
  <SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
  <SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	

	function goprev() {
		  o = document.lfSalvageSearchForm;
		  o.prevpage.value = "1";
		  o.pagination.value="1";
		  o.submit();
		}
		
		function gonext() {
		  o = document.lfSalvageSearchForm;
		  o.nextpage.value="1";
		  o.pagination.value="1";
		  o.submit();
		}
		
		function gopage(i) {
			  o = document.lfSalvageSearchForm;
			  o.currpage.value = i;
			  o.pagination.value="1";
			  o.submit();
		}
		
		function updatePagination() {
		    return true;
		}

  </SCRIPT>
  
  
  <jsp:include page="/pages/includes/validation_search.jsp" />
  <html:form focus="sId" action="lf_search_salvage.do" method="post" >
  	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.lf.search.salvage" />
            </h1>
          </div>
          <div id="pageheaderright">
            <table id="pageheaderright">
              <tr>
                <td>
                  <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><bean:message key="Help" /></a>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
    <tr>
      <td id="middlecolumn">
        <div id="maincontent">
            <logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
          <h1 class="green">
            <bean:message key="header.search_criteria" />
            <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
          </h1>
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
                	<html:select property="salvageStatus" styleClass="dropdown" styleId="salvageStatus" >
	                  <html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>">
	                    <bean:message key="salvage.status_all" />
	                  </html:option>
	                  <html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>">
	                    <bean:message key="salvage.status_open" />
	                  </html:option>
	                  <html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>">
	                    <bean:message key="salvage.status_closed" />
	                  </html:option>
	                </html:select>
                </td>
                <td nowrap>
                  <bean:message key="colname.salvage_date_range" />
                  (
                  <%= a.getDateformat().getFormat() %>)
                  <br>
                  <html:text property="s_createtime" size="12" maxlength="11" styleClass="textfield" styleId="startTime" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.lfSalvageSearchForm.s_createtime,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
                  <html:text property="e_createtime" size="12" maxlength="11" styleClass="textfield" styleId="endTime" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.lfSalvageSearchForm.e_createtime,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
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
                </td>
              </tr>
            </table>
            
            <logic:present name="salvages" scope="request" >
              <h1 class="green">
                <bean:message key="header.search_result" />
                <a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              </h1>
              <a name="result"></a>
              <table class="form2" cellspacing="0" cellpadding="0" width="500">
                <tr>
                  <td>
                    <b>
                      <bean:message key="colname.lf.salvage.id" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.lf.salvage.agent.created" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.lf.salvage.created.date" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.lf.salvage.closeed.date" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.lf.salvage.status" />
                    </b>
                  </td>
                  <td>
                    <b>
                      <bean:message key="colname.lf.salvage.item.count" />
                    </b>
                  </td>
                </tr>
                <logic:iterate indexId="i" id="salvage" name="salvages" type="com.bagnet.nettracer.tracing.dto.SalvageDTO" >
                  <tr>
                    <td>
                      <a href='lf_salvage.do?id=<bean:write name="salvage" property="salvageID" />'>
                      	<bean:write name="salvage" property="salvageID" />
                      </a>
                    </td>
                    <td>
                    	<%=salvage.getAgent() %>
                    </td>
                    <td>
                      <%=salvage.getDisCreatedDate(a.getDateformat().getFormat()) %>
                    </td>
                    <td>
                      <%=salvage.getDisClosedDate(a.getDateformat().getFormat()) %>
                    </td>
                    <td>
                    	<% if (salvage.getStatus() == TracingConstants.LF_STATUS_OPEN) { %>
                      		<bean:message key="salvage.status_open" />
                    	<% } else { %>
                      		<bean:message key="salvage.status_closed" />
                    	<% } %>
                    </td>
                    <td>
                    	<%= salvage.getItemCount() %>
                      <!--  <=salvage.getItems().size() %>-->
                    </td>
                  </tr>
                </logic:iterate> 
                <tr>
				   <td colspan="11">
				   	<jsp:include page="/pages/includes/pagination_incl.jsp" />
				   </td>
			    </tr>
              </table>
              <script language=javascript>
                
  				document.location.href="#result";

              </script>
            </logic:present>
          </html:form>
