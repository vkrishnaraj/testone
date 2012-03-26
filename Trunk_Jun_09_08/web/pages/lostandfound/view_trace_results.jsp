<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>
<%@ page import="com.bagnet.nettracer.tracing.forms.lf.TraceResultsFilter" %>
<%
	Agent a = (Agent)session.getAttribute("user");
 	String cssFormClass = "form2";
 	
%>

<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
    
	var cal1xx = new CalendarPopup();	
    
	function goprev() {
	  o = document.traceResultsForm;
	  o.prevpage.value = "1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gonext() {
	  o = document.traceResultsForm;
	  o.nextpage.value="1";
	  o.pagination.value="1";
	  o.submit();
	}
	
	function gopage(i) {
		  o = document.traceResultsForm;
		  o.currpage.value = i;
		  o.pagination.value="1";
		  o.submit();
	}
	
	function updatePagination() {
	    return true;
	}
	
	function toggleState(elementId,cbId) {
		var element = document.getElementById(elementId);
		element.value = document.getElementById(cbId).checked;
	}

</script>
<jsp:include page="/pages/includes/validation_search.jsp" />
<html:form action="view_trace_results.do" method="post" onsubmit="return validateSearch(this);">
<html:hidden property="open" styleId="open" value="false" />
<html:hidden property="closed" styleId="closed" value="false" />
<html:hidden property="confirmed" styleId="confirmed" value="false" />
<html:hidden property="rejected" styleId="rejected" value="false" />
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
     					<td>
     						<bean:message key="colname.lf.lost.id" />
     						<br/>
     						<logic:equal name="traceResultsForm" property="filter.lostId" value="0" >
	     						<html:text name="traceResultsForm" property="filter.lostId" size="10" styleClass="textfield" value="" />
     						</logic:equal>
     						<logic:greaterThan name="traceResultsForm" property="filter.lostId" value="0" >
	     						<html:text name="traceResultsForm" property="filter.lostId" size="10" styleClass="textfield" />
     						</logic:greaterThan>
     					</td>
     					<td>
     						<bean:message key="colname.lf.found.id" />
     						<br/>
     						<html:text name="traceResultsForm" property="filter.barcode" size="10" styleClass="textfield" />
     					</td>
     					<td>
     						<bean:message key="colname.lf.match.status" />:
     						<br/>
     						<% TraceResultsFilter filter = (TraceResultsFilter) session.getAttribute("filter"); %>
       						<input id="openCb" type="checkbox" name="filter.open" <% if (filter.getOpen()) { %> checked <% } %> onClick="toggleState('open','openCb');" /><bean:message key="lf.match.open" />&nbsp;
       						<input id="closedCb" type="checkbox" name="filter.closed" <% if (filter.getClosed()) { %> checked <% } %> onClick="toggleState('closed','closedCb');" /><bean:message key="lf.match.closed" />&nbsp;
       						<input id="confirmedCb" type="checkbox" name="filter.confirmed" <% if (filter.getConfirmed()) { %> checked <% } %> onClick="toggleState('confirmed','confirmedCb');" /><bean:message key="lf.match.confirmed" />&nbsp;
       						<input id="rejectedCb" type="checkbox" name="filter.rejected" <% if (filter.getRejected()) { %> checked <% } %> onClick="toggleState('rejected','rejectedCb');" /><bean:message key="lf.match.rejected" />&nbsp;
     					</td>
     				</tr>
     				<tr>
     					<td colspan=3 >
     						<center>
								<html:submit property="filterMatches" styleId="button">
									<bean:message key="button.filter" />
								</html:submit>
							</center>
     					</td>
     				</tr>
     			</table>
     			<br/>
				<h1 class="green">
					<bean:message key="header.matches" />
					<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
         		<logic:messagesPresent message="true"><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></logic:messagesPresent>
         		<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0">
         			<tr>
         				<td class="header" >
         					<bean:message key="colname.lf.select" />
         				</td>
         				<td class="header" >
         					<bean:message key="colname.lf.lost.id" />
         				</td>
         				<td class="header" >
         					<bean:message key="colname.lf.found.id" />
         				</td>
         				<td class="header" >
         					<bean:message key="colname.lf.score" />
         				</td>
         				<td class="header" >
         					<bean:message key="header.match_details" />
         				</td>
         				<td class="header" >
         					<bean:message key="colname.lf.status" />
         				</td>		
         				<td class="header" >
         					<bean:message key="colname.lf.action" />
         				</td>		
         			</tr>
         			<logic:iterate indexId="i" id="match" name="traceResultsForm" property="matches" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory" >
         				<tr>
         					<td>
         						<input type="checkbox" name="match[<%=i %>].selected" />
         					</td>
         					<td>
         						<a href='create_lost_report.do?lostId=<bean:write name="match" property="lost.id" />' ><bean:write name="match" property="lost.id" /></a>
         					</td>
         					<td>
         						<a href='create_found_item.do?foundId=<bean:write name="match" property="found.id" />' ><bean:write name="match" property="found.barcode" /></a>
         					</td>
         					<td>
         						<bean:write name="match" property="totalScore" />
         					</td>
         					<td>
         						<a href='view_trace_results.do?details=1&matchId=<%=match.getId() %>'><bean:message key="details" /></a>
         					</td>
         					<td>
         						<bean:write name="match" property="statusDescription" />
         					</td>
         					<td>
         						<% 
         						   int statusId = match.getStatus().getStatus_ID();
         						   if (statusId == TracingConstants.LF_TRACING_REJECTED) { %>
         							<a href='view_trace_results.do?unreject=1&matchId=<%=match.getId() %>'><bean:message key="button.unreject" /></a>
         						<% } else if (statusId == TracingConstants.LF_TRACING_CONFIRMED) { %>
         							<a href='view_trace_results.do?unconfirm=1&matchId=<%=match.getId() %>'><bean:message key="button.un_match" /></a>
         						<% } else if (statusId == TracingConstants.LF_TRACING_OPEN){ %>
         							<a href='view_trace_results.do?reject=1&matchId=<%=match.getId() %>'><bean:message key="button.reject" /></a>,&nbsp;
         							<a href='view_trace_results.do?confirm=1&matchId=<%=match.getId() %>'><bean:message key="button.do_match" /></a>
         						<% } else if (statusId == TracingConstants.LF_TRACING_CLOSED) {%>
         							<a href='view_trace_results.do?reject=1&matchId=<%=match.getId() %>'><bean:message key="button.reject" /></a>
         						<% } %>
         					</td>
         				</tr>
         			</logic:iterate>
         			<tr>
					   	<td colspan="7">
					   		<jsp:include page="/pages/includes/pagination_incl.jsp" />
					   	</td>
				    </tr>
				    <tr>
				    	<td colspan="7" >
				    		<center>
						    	<html:submit property="unreject" styleId="button" >
						    		<bean:message key="button.unreject" />
						    	</html:submit>
						    	&nbsp;&nbsp;
						    	<html:submit property="reject" styleId="button" >
						    		<bean:message key="button.reject" />
						    	</html:submit>
				    		</center>
				    	</td>
				    </tr>
			    </table>
			    <script language=javascript>
					/*document.location.href="#result";*/
					toggleState('open','openCb');
					toggleState('closed','closedCb');
					toggleState('confirmed','confirmedCb');
					toggleState('rejected','rejectedCb');
			    </script>
   			</div>
 		</td>
   	</tr>
</html:form>
