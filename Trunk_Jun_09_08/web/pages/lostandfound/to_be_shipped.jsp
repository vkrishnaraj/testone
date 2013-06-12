<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFCategory" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="aero.nettracer.lf.services.LFTracingUtil" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
 	
 	String type = (String) request.getAttribute("type");
 	request.removeAttribute("type");
 	
 	HashMap<String,String> catMap=(HashMap<String,String>)session.getAttribute("catmap");
 	HashMap<String,String> subcatMap=(HashMap<String,String>)session.getAttribute("subcatmap");
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
<html:form action="view_to_be_shipped.do" method="post" onsubmit="return validateSearch(this);">
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
					<bean:message key="header.to.be.delivered" />	
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
              		<tr>
              			<td class="header">
              				<bean:message key="colname.lf.paid.timestamp" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.matched.timestamp" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.lost.report.number" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.found.report.number" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.location" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.category" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.subcategory" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.short.item.description" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.feedback" />
              			</td>
              			<td class="header">
              				<bean:message key="colname.lf.service.type" />
              			</td>
              		</tr>
              		<logic:iterate id="result" name="resultList" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory">
              			<logic:notEmpty name="result" property="lost.shipment.transaction"> 
              			<tr>
              				<td>
              					<%=DateUtils.formatDate(result.getLost().getShipment().getTransaction().getTransactionDate(), TracingConstants.DISPLAY_DATETIMEFORMAT,null,null)%>&nbsp;
              				</td>
              				<td>
              					<%=DateUtils.formatDate(result.getMatchTimeStamp(), TracingConstants.DISPLAY_DATETIMEFORMAT,null,null)%>&nbsp;
              				</td>
              				<td>
              				<% if(result.getLost()!=null){ %>
              					<a href="create_lost_report.do?lostId=<%=result.getLost().getId() %>"><bean:write name="result" property="lost.id" /></a>&nbsp;
              					<% } %>
              				</td>
              				<td>
              				<% if(result.getFound()!=null){ %>
              					<a href="create_found_item.do?foundId=<%=result.getFound().getId() %>"><bean:write name="result" property="found.barcode" /></a>&nbsp;
              					
              					<% } %>
              				</td>
              				<td>
              					<bean:write name="result" property="lost.location.stationcode" />&nbsp;
              				</td>
              				<td>
              					<%=catMap.get(String.valueOf(result.getLost().getItem().getCategory()))!=null?catMap.get(String.valueOf(result.getLost().getItem().getCategory())):"" %>&nbsp;
              				</td>
              				<td>
              					<%=subcatMap.get(String.valueOf(result.getLost().getItem().getSubCategory()))!=null?subcatMap.get(String.valueOf(result.getLost().getItem().getSubCategory())):"" %>&nbsp;
              				</td>
              				<td>
              					<bean:write name="result" property="found.item.description" />&nbsp;
              				</td>
              				<td>
              					<bean:write name="result" property="lost.feedback" />&nbsp;
              				</td>
              				<td>
              					<bean:write name="result" property="lost.shipment.shippingOption" />&nbsp;
              				</td>
              			</tr>
              			</logic:notEmpty>
              		</logic:iterate>
				   	<tr>
						   <td colspan="11">
						   	<jsp:include page="/pages/includes/pagination_incl.jsp" />
						   </td>
				    </tr>
			    </table>
   			</div>
 		</td>
   	</tr>
</html:form>
