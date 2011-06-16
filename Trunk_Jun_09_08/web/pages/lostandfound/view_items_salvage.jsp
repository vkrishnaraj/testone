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
	  o = document.salvageItemsForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.salvageItemsForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.salvageItemsForm;
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
					<bean:message key="header.salvage.items" />
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
         				<td class="header">
         					<bean:message key="colname.lf.select" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.id" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.status" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.date" />
         				</td>
         				<td class="header">
         					<bean:message key="colname.lf.item.description" />
         				</td>
         			</tr>
         			<logic:iterate indexId="i" id="item" name="salvageItemsForm" property="foundItems" type="com.bagnet.nettracer.tracing.db.lf.LFFound" >
         				<tr>
         					<td>
         						<input type="checkbox" name="item[<%=i %>].selected" />
         					</td>
         					<td>
         						<a href='create_found_item.do?foundId=<%=item.getId() %>'><%=item.getId() %></a>
         					</td>
         					<td>
         						<%=item.getItem().getStatus().getDescription() %>
         					</td>
         					<td>
         						<%=item.getDisplayDate(a.getDateformat().getFormat()) %>
         					</td>
         					<td>
         						<%=item.getItem().getDescription() == null || item.getItem().getDescription().isEmpty() ? "&nbsp;" : item.getItem().getDescription() %>
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
				<center>
					<html:submit property="salvageItems" styleId="button">
						<bean:message key="button.salvage.items" />
					</html:submit>
				</center>
			    <script language=javascript>
					document.location.href="#result";
			    </script>
   			</div>
 		</td>
   	</tr>
</html:form>
