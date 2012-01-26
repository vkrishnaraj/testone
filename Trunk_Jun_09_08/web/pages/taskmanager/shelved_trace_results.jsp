<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Status" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFCategory" %>
<%@ page import="com.bagnet.nettracer.tracing.db.lf.LFSubCategory" %>
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryContainer" %>
<%@ page import="com.bagnet.nettracer.tracing.history.HistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.history.FoundHistoryObject" %>
<%@ page import="com.bagnet.nettracer.tracing.bmo.PropertyBMO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@page import="java.util.ResourceBundle" %>
<%@page import="java.util.Locale" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
%>
<SCRIPT LANGUAGE="JavaScript">
    
	function goprev() {
	  o = document.shelvedTraceResultsForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.shelvedTraceResultsForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.shelvedTraceResultsForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
</script>
<html:form action="shelved_trace_results.do" method="post" >
	<tr>
        <td colspan="3" id="pageheadercell">
          <div id="pageheaderleft">
            <h1>
            	<bean:message key="header.shelved.trace.results" />
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
   	<tr>
   		<td id="middlecolumn">        
     		<div id="maincontent">
   				<center>
    				<font color=red>
                  		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/><br/></html:messages></logic:messagesPresent>
                	</font>
               	</center>
                <h1 class="green">
					<bean:message key="header.process.trace.results" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
               	<table class="<%=cssFormClass %>" cellspacing=0 cellpadding=0 >
               		<tr>
               			<td>
               				<center>
               				<bean:message key="colname.lfc.value" />:&nbsp;
           					<html:select name="shelvedTraceResultsForm" property="value" styleClass="dropdown" >
      							<html:option value="<%=String.valueOf(TracingConstants.LFC_ITEM_LOW_VALUE) %>" ><bean:message key="lfc.low.value" /></html:option>
      							<html:option value="<%=String.valueOf(TracingConstants.LFC_ITEM_HIGH_VALUE) %>" ><bean:message key="lfc.high.value" /></html:option>
           					</html:select>
           					&nbsp;&nbsp;
               				<html:submit property="getNextItem" styleId="button">
        						<bean:message key="button.get.next.item" />
      						</html:submit>
      						</center>
               			</td>
               		</tr>
               	</table>
               	<br />
                <h1 class="green">
					<bean:message key="header.found.item.information" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
                <table class="<%=cssFormClass %>" cellpadding=0 cellspacing=0 >
                	<tr>
                		<td class="header" style="width:25%;" >
                			<b><bean:message key="colname.lf.found.id" /></b>
                		</td>
                		<td class="header" style="width:50%;" >
                			<b><bean:message key="colname.lfc.found.description" /></b>
                		</td>
                		<td class="header" style="width:25%;" >
                			<b><bean:message key="colname.lfc.found.date" /></b>
                		</td>
                	</tr>
                	<logic:iterate id="found" name="shelvedTraceResultsForm" property="foundList" type="com.bagnet.nettracer.tracing.db.lf.LFFound" >
                		<tr>
	                		<td style="width:25%;" >
								<a href='create_found_item.do?foundId=<bean:write name="found" property="id" />' ><bean:write name="found" property="barcode" /></a>
	                		</td>
	                		<td style="width:50%;" >
	                			<bean:write name="found" property="extendedSummaryDesc" />
	                		</td>
	                		<td style="width:25%;" >
								<%=found.getDisFoundDate(a.getDateformat().getFormat()) %>
	                		</td>
                		</tr>
                	</logic:iterate>
                	<tr>
					   	<td colspan=3 >
					   		<jsp:include page="/pages/includes/pagination_incl.jsp" />
					   	</td>
                	</tr>
                </table>
  			</div>
   		</td>
   	</tr>
</html:form>
