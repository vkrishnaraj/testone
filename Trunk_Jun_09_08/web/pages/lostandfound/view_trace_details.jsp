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
		        	<bean:message key="header.match_details" />
		        	<a href="#" onclick="openHelp('pages/WebHelp/nettracerhelp.htm');return false;"><img src="deployment/main/images/nettracer/button_help.gif" width="20" height="21" border="0"></a>
		        </h1>
     			<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
     				<tr>
     					<td class="header" >
         					<bean:message key="colname.lf.lost.id" />
         				</td>
         				<td class="header" >
         					<bean:message key="colname.lf.found.id" />
         				</td>
         				<td class="header" >
         					<bean:message key="colname.lf.score" />
         				</td>
     				</tr>
     				<tr>
     					<td>
       						<a href='create_lost_report.do?lostId=<bean:write name="match" property="lost.id" />' ><bean:write name="match" property="lost.id" /></a>
       					</td>
       					<td>
       						<a href='create_found_item.do?foundId=<bean:write name="match" property="found.id" />' ><bean:write name="match" property="found.barcode" /></a>
       					</td>
       					<td>
       						<bean:write name="match" property="totalScore" />
       					</td>
     				</tr>
     			</table>
     			<br/>
     			<table class="<%=cssFormClass %>" cellspacing="0" cellpadding="0" >
     				<tr>
	     				<td class="header" >
	     					<bean:message key="colname.lf.match.item" />
	     				</td>
	     				<td class="header" >
	     					<bean:message key="colname.lf.score" />
	     				</td>
	     				<td class="header" >
	     					<bean:message key="colname.lf.match.lost" />
	     				</td>
	     				<td class="header" >
	     					<bean:message key="colname.lf.match.found" />
	     				</td>
     				</tr>
     				<logic:iterate indexId="i" id="detail" scope="request" name="match" property="details" type="com.bagnet.nettracer.tracing.db.lf.detection.LFMatchDetail" >
     					<tr>
     						<td>
     							<bean:write name="detail" property="description" />
     						</td>
     						<td>
     							<bean:write name="detail" property="score" />
     						</td>
     						<td>
     							<%=detail.getDecryptedLostValue() == null ? "&nbsp;" : detail.getDecryptedLostValue() %>
     						</td>
     						<td>
     							<%=detail.getDecryptedFoundValue() == null ? "&nbsp;" : detail.getDecryptedFoundValue() %>
     						</td>
     					</tr>
     				</logic:iterate>
     			</table>
     			<br/>
     			<center>
     				<input id="button" type="button" value='<bean:message key="claim.button.back" />' onClick="history.back()">
     			</center>
			    <script language=javascript>
					/*document.location.href="#result";*/
			    </script>
   			</div>
 		</td>
   	</tr>
</html:form>
