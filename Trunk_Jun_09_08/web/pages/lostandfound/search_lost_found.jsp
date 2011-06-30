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
 	String cssFormClass = "form2";
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
    
	function goprev() {
	  o = document.searchLostFoundForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.searchLostFoundForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.searchLostFoundForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}

</script>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form focus="id" action="search_lost_found.do" method="post" onsubmit="return validateSearch(this);">
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.search.lost.found" />
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
         		<logic:present name="reportfile" scope="request">
            		<center><a href="#" onclick="openReportWindow('reporting?outputtype=<%= request.getAttribute("outputtype") %>&reportfile=<bean:write name="reportfile" scope="request"/>','report',800,600);return false;"><b><bean:message key="link.view_report" /></b></a></center>
           		</logic:present>
				<h1 class="green">
		        	<bean:message key="header.search_criteria" />
                	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
       				<tr>
             			<td>
               				<bean:message key="colname.reference.id" />
               				<br>
               				<logic:equal name="searchLostFoundForm" property="id" value="0" >
               					<html:text name="searchLostFoundForm" property="id" value="" size="10" styleClass="textfield" />
               				</logic:equal>
               				<logic:greaterThan name="searchLostFoundForm" property="id" value="0" >
               					<html:text name="searchLostFoundForm" property="id" size="10" styleClass="textfield" />
               				</logic:greaterThan>                  
             			</td>
             			<td>
               				<bean:message key="colname.reference.type" />
               				<br>
               				<html:select name="searchLostFoundForm" property="type" styleClass="dropdown" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_TYPE_LOST) %>"><bean:message key="search.type.lost" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>"><bean:message key="search.type.found" /></html:option>
               				</html:select>                  
             			</td>
             			<td>
           					<bean:message key="colname.station_number" />
           					<br>
           					<html:select name="searchLostFoundForm" property="stationId" styleClass="dropdown" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
								<html:options collection="stationlist" property="station_ID" labelProperty="stationcode" />
           					</html:select>
           				</td>
             			<td>
               				<bean:message key="colname.lf.status" />
               				<br>
               				<html:select name="searchLostFoundForm" property="statusId" styleClass="dropdown" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_OPEN) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_OPEN) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_CLOSED) %>" /></html:option>
               				</html:select>                  
             			</td>
             			<td>
               				<bean:message key="colname.lf.disposition" />
               				<br>
               				<html:select name="searchLostFoundForm" property="dispositionId" styleClass="dropdown" >
               					<html:option value="<%=String.valueOf(TracingConstants.LF_STATUS_ALL) %>"><bean:message key="search.option.all" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_OTHER) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP) %>" /></html:option>
               					<html:option value="<%=String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED) %>"><bean:message key="<%="STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED) %>" /></html:option>
               				</html:select>                  
             			</td>
           			</tr>
           			<tr>           				
           				<td nowrap colspan=2>
			                <bean:message key="colname.lf.date.range" />
			                (<%= a.getDateformat().getFormat() %>)
			                <br>
			                <html:text property="startDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchLostFoundForm.startDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
			                <html:text property="endDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.searchLostFoundForm.endDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
		                </td>
		                <td colspan=2>
							<bean:message key="colname.lf.agreement.number" />
							<br>
							<html:text name="searchLostFoundForm" property="agreementNumber" size="15" styleClass="textfield" />
						</td>
		                <td>
		                	<bean:message key="colname.lf.mva.number" />
							<br>
							<html:text name="searchLostFoundForm" property="mvaNumber" size="10" styleClass="textfield" />
		                </td>
           			</tr>
           			<tr>
           				<td>
			              <bean:message key="colname.last_name.req" />
			              <br>
			              <html:text name="searchLostFoundForm" property="lastName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
			              <bean:message key="colname.first_name.req" />
			              <br>
			              <html:text name="searchLostFoundForm" property="firstName" size="20" maxlength="20" styleClass="textfield" />
			            </td>
			            <td>
		              		<bean:message key="colname.lf.phone.number" />
		              		<br/>
		              		<html:text name="searchLostFoundForm" property="phoneNumber" size="15" maxlength="25" styleClass="textfield" />
		              	</td>
		              	<td colspan=2>
		              		<bean:message key="colname.lf.email" />
		              		<br />
		              		<html:text name="searchLostFoundForm" property="email" size="35" maxlength="100" styleClass="textfield" />
		              	</td>
           			</tr>
           			<tr>
           				<td colspan=5>
           					<center>
			              		<html:submit property="search" styleId="button" >
			              			<bean:message key="button.retrieve" />
			              		</html:submit>
			              		&nbsp;&nbsp;
			              		<html:reset styleId="button">
				                  	<bean:message key="button.reset" />
				                </html:reset>
			              	</center>
           				</td>
           			</tr>
				</table>
				<br />
                <h1 class="green">
                	<bean:message key="header.search_result" />
                	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
              	</h1>
              	<table class="<%=cssFormClass %>" cellpadding="0" cellspacing="0" >
              		<tr>
              			<td class="header">
              				<bean:message key="colname.lf.id" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.status" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.station_number" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.date" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.client.name" />
              			</td>
              		</tr>
              		<logic:iterate id="result" name="resultList" type="com.bagnet.nettracer.tracing.db.lf.LFObject">
              			<tr>
              				<td>
              					<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_LOST) %>" >
              						<a href="create_lost_report.do?lostId=<%=result.getId() %>"><bean:write name="result" property="id" /></a>
              					</logic:equal>
              					<logic:equal name="searchLostFoundForm" property="type" value="<%=String.valueOf(TracingConstants.LF_TYPE_FOUND) %>" >
              						<a href="create_found_item.do?foundId=<%=result.getId() %>"><bean:write name="result" property="id" /></a>
              					</logic:equal>
              				</td>
              				<td>
              					<bean:write name="result" property="statusDescription" />
              				</td>
              				<td>
              					<bean:write name="result" property="disStation" />
              				</td>
              				<td>
              					<%=result.getDisplayDate(a.getDateformat().getFormat()) %>
              				</td>
              				<td>
              					<bean:write name="result" property="clientName" />
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
   			</div>
 		</td>
   	</tr>
</html:form>
