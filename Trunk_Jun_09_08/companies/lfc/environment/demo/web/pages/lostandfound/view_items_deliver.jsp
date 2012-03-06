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
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
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
<html:form action="view_items_deliver.do" method="post" onsubmit="return validateSearch(this);">
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
					<bean:message key="header.deliver.items" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
         				<td class="header">
         					<bean:message key="colname.lf.id" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.disposition" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.date" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.item.description" />
         				</td>
         			</tr>
         			<logic:iterate indexId="i" id="item" name="handleItemsForm" property="foundItems" type="com.bagnet.nettracer.tracing.db.lf.LFItem" >
         				<% if (item.getType() == TracingConstants.LF_TYPE_FOUND) { %>
         				<tr>
         					<td style="width:15%;">
         						<a href='create_found_item.do?foundId=<%=item.getFound().getId() %>'><%=item.getFound().getId() %></a>
         					</td>
         					<td style="width:15%;">
         						<%=item.getDisposition().getDescription() %>
         					</td>
         					<td style="width:10%;">
         						<%=item.getFound().getDisplayDate(a.getDateformat().getFormat()) %>
         					</td>
         					<td style="width:30%;">
         						<%=item.getDescription() == null || item.getDescription().isEmpty() ? "&nbsp;" : item.getDescription() %>
         					</td>
         				</tr>
         				<% } %>
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
