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
 	
 	String type = (String) request.getAttribute("type");
 	request.removeAttribute("type");
%>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
    
	function goprev() {
	  o = document.handleItemsForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.handleItemsForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.handleItemsForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}

</script>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="view_items_salvage.do" method="post" onsubmit="return validateSearch(this);">
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.taskmanager" />
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
     			<h1 class="green">
		        	<bean:message key="header.search_criteria" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
		        <table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
     				<tr>
     					<td style="width:70%;">
			                <bean:message key="colname.lf.date.range" />
			                (<%= a.getDateformat().getFormat() %>)
			                <br>
			                <html:text property="startDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar" name="calendar" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.handleItemsForm.startDate,'calendar','<%= a.getDateformat().getFormat() %>'); return false;">&nbsp;-
			                <html:text property="endDate" size="12" maxlength="11" styleClass="textfield" /><img src="deployment/main/images/calendar/calendar_icon.gif" id="calendar2" name="calendar2" height="15" width="20" border="0" onmouseover="this.style.cursor='hand'" onClick="cal1xx.select(document.handleItemsForm.endDate,'calendar2','<%= a.getDateformat().getFormat() %>'); return false;">
		                </td>
		                <td style="width:30%;">
		                	<bean:message key="colname.lfc.value" />:&nbsp;
		                	<br>
           					<html:select name="handleItemsForm" property="value" styleClass="dropdown" >
      							<html:option value="<%=String.valueOf(TracingConstants.LFC_ITEM_LOW_VALUE) %>" ><bean:message key="lfc.low.value" /></html:option>
      							<html:option value="<%=String.valueOf(TracingConstants.LFC_ITEM_HIGH_VALUE) %>" ><bean:message key="lfc.high.value" /></html:option>
           					</html:select>
		                </td>
     				</tr>
     				<tr>
     					<td colspan=3 >
     						<center>
								<html:submit property="filterItems" styleId="button">
									<bean:message key="button.filter" />
								</html:submit>
							</center>
     					</td>
     				</tr>
     			</table>
		        <br/>
				<h1 class="green">
					<bean:message key="header.salvage.items" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
         				<td class="header" style="width:20%;">
         					<bean:message key="colname.lf.id" />
         				</td>
         				<td class="header" style="width:20%;">
         					<bean:message key="colname.lf.received.date" />
         				</td>
         				<td class="header" style="width:20%;">
         					<bean:message key="colname.lf.item.location" />
         				</td>
         				<td class="header" style="width:40%;">
         					<bean:message key="colname.lf.item.description" />
         				</td>
         			</tr>
         			<logic:iterate indexId="i" id="item" name="handleItemsForm" property="foundItems" type="com.bagnet.nettracer.tracing.db.lf.LFItem" >
         				<tr>
         					<td>
         						<a href='create_found_item.do?foundId=<%=item.getFound().getId() %>'><%=item.getFound().getBarcode() %></a>
         					</td>
         					<td>
         						<%=item.getFound().getDisReceivedDate() %>
         					</td>
         					<td>
         						<% 
         							int location = item.getFound().getItemLocation();
         							switch (location) {
         							case TracingConstants.LF_LOCATION_SHELF: %>
         								<bean:message key="lf.location.shelf" />
         							<%	
         								break;
         							case TracingConstants.LF_LOCATION_VERIFICATION: %>
         								<bean:message key="lf.location.verification" />
         							<%
         								break;
         							case TracingConstants.LF_LOCATION_WAITING: %>
         								<bean:message key="lf.location.waiting" />
         							<% 
         								break;
 									case TracingConstants.LF_LOCATION_DELIVERY: %>
         								<bean:message key="lf.location.delivery" />
       								<%
       									break;
       								case TracingConstants.LF_LOCATION_SALVAGED: %>
         								<bean:message key="lf.location.salvaged" />
         							<%
         								break;
         							default: %>
         								<bean:message key="lf.location.unknown" />
         						<%	
         								break;
         							} 
         						%>
         					</td>
         					<td>
         						<%=item.getDescription() == null || item.getDescription().isEmpty() ? "&nbsp;" : item.getDescription() %>
         					</td>
         				</tr>
         			</logic:iterate>
         			<tr>
					   <td colspan="11">
					   	<jsp:include page="/pages/includes/pagination_incl.jsp" />
					   </td>
				    </tr>
			    </table>
			    <br/>
			    <script language=javascript>
					document.location.href="#result";
			    </script>
   			</div>
 		</td>
   	</tr>
</html:form>
