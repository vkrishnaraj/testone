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
 	String cssFormClass = "form2_dam";
 	
 	String type = (String) request.getAttribute("type");
 	request.removeAttribute("type");
%>


<%@page import="com.bagnet.nettracer.tracing.db.salvage.SalvageItem"%><SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/date.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
    
	function goprev() {
	  o = document.lostFoundManagerForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.lostFoundManagerForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.lostFoundManagerForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}

</script>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="lost_found_manager.do" method="post" onsubmit="return validateSearch(this);">
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
					<% if (type.equals("lost")) { %>
		        		<bean:message key="header.open.lost" />	
		        	<% } else { %>
			        	<bean:message key="header.open.found" />
		        	<% } %>
                	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm#Retrieve.htm#Retrieve_Reports');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
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
              					<% if (type.equals("lost")) { %>
              						<a href="create_lost_report.do?lostId=<%=result.getId() %>"><bean:write name="result" property="id" /></a>
              					<% } else { %>
              						<a href="create_found_item.do?foundId=<%=result.getId() %>"><bean:write name="result" property="id" /></a>
              					<% } %>
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
			    <% if (type.equals("lost")) { %>
			    	<input type="hidden" name="openLost" value="1" />
			    <% } else { %>
			    	<input type="hidden" name="openFound" value="1" />
			    <% } %>
   			</div>
 		</td>
   	</tr>
</html:form>
