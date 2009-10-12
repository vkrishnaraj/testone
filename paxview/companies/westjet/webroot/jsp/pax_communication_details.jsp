<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication" %>
<%@ page import="com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident" %>
<% 
String myNextAction = "" + (String) request.getAttribute("prepareto");

StringBuffer sbPriorCommunication = new StringBuffer("");
WS_PVIncident advancedIncident = (WS_PVIncident) request.getSession().getAttribute("FORM_DATA");
if (advancedIncident != null) {
	WS_PVPaxCommunication[] arr = advancedIncident.getPaxCommunication();
	if (arr != null) {
		if (arr.length > 0){
			for(int i=0; i<arr.length; i++) {
				WS_PVPaxCommunication myWS_PVPaxCommunication = arr[i];
				if(myWS_PVPaxCommunication != null) {
					String enteredBy = "";
					String myAgent = "" + myWS_PVPaxCommunication.getAgent();
					if(myAgent==null || myAgent.equals("") || myAgent.equals("null")) {  //comment by pax 
						enteredBy = " - Your Comment";
					} else {
						enteredBy = " - US Airways";
					} 
					
					sbPriorCommunication.append("<H2>" + myWS_PVPaxCommunication.getCreate_timestamp() + enteredBy + "</H2><BR />");
					
					String strAcknowledgedBy = "";
					String myAcknowledgedAgent = "" + myWS_PVPaxCommunication.getAcknowledged_agent();
					String myAcknowledgedTimestamp = "" + myWS_PVPaxCommunication.getAcknowledged_timestamp();
					if(myAcknowledgedAgent != null && myAcknowledgedTimestamp != null) {
						if(!myAcknowledgedAgent.equals("") && (!myAcknowledgedTimestamp.equals(""))) {

							strAcknowledgedBy = "<I>Comment acknowledged by US Airways on " + myAcknowledgedTimestamp + "</I>";
						}
					}
					sbPriorCommunication.append(strAcknowledgedBy + "<BR />");
					
					sbPriorCommunication.append("<H4>" + myWS_PVPaxCommunication.getComment() + "</H4><BR />");
					sbPriorCommunication.append("<HR />");
				}
			}
		}
	}	
	
}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="css/master.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/niftyCorners.css" type="text/css" />
<script type="text/javascript" src="js/niftycube.js"></script>
<script type="text/javascript" src="js/mootools.js"></script>

<script>
var highlightcolor="#EDEDED"
var ns6=document.getElementById&&!document.all
var previous=''
var eventobj
var intended=/INPUT|TEXTAREA|SELECT|OPTION/
function checkel(which){
if (which.style&&intended.test(which.tagName)){
if (ns6&&eventobj.nodeType==3)
eventobj=eventobj.parentNode.parentNode
return true
}
else
return false
}
function highlight(e){
eventobj=ns6? e.target : event.srcElement
if (previous!=''){
if (checkel(previous))
previous.style.backgroundColor=''
previous=eventobj
if (checkel(eventobj))
eventobj.style.backgroundColor=highlightcolor
}
else{
if (checkel(eventobj))
eventobj.style.backgroundColor=highlightcolor
previous=eventobj
}
}
</script>

<script type="text/javascript">
window.addEvent('domready', function() {

	var myAccordion = new Accordion($('accordion'), 'p.statClaimToggle', 'div.statClaimContent', {
		opacity: false,
		onActive: function(toggler, element){

			var browser=navigator.appName;
			var version = parseFloat(navigator.appVersion.split('MSIE')[1]);
        
        	if (browser=="Microsoft Internet Explorer" && (version<7))
	        {
        		toggler.setStyle('text-decoration', 'underline');
		    } else {   
				toggler.setStyle('text-decoration', 'none');
			}
		},
		onBackground: function(toggler, element){
			toggler.setStyle('text-decoration', 'underline');
		},
		display: <c:choose>
			<c:when test="${incident.incident_status == 'Temporary'}">
			0
			</c:when>
			<c:when test="${incident.incident_status == 'Open'}">
				1
			</c:when>
				<c:when test="${incident.incident_status == 'Pending'}">
				2
			</c:when>
			<c:when test="${incident.incident_status == 'Open'}">
				3
			</c:when>
			<c:otherwise>
				0
			</c:otherwise>
		</c:choose>
	
	
	});

});

</script>

<style type="text/css">
  p.statClaimToggle {
    cursor: pointer;
  }
</style>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="page.title" /> - Pax Communication Details</title>
</head>
<body>
<div id="container">
  <div id="wrapper">
   				<c:choose>
						<c:when test="${siteLanguage == 'fr'}">
							<div id="header_fr"></div>
						</c:when>
						<c:when		test="${(empty siteLanguage) and cookie.userLanguage.value == 'fr'}">
							<div id="header_fr"></div>
						</c:when>
						<c:otherwise>
							<div id="header"></div>
						</c:otherwise>
					</c:choose>	
    <!-- /header -->
    <div id="content_resultsCenterCol">
    <!--<div style="overflow:auto;height:auto;">--><center>
      	<table border="0" cellspacing="5" cellpadding="5" id="Definitions">
      	    <tr>
      	    	<td><strong><spring:message code="pax.communication.priorcomments" /></strong>:<br /></td>
      	    </tr>
      		<tr>
				<td>
			  			<c:out value="${incident.comments }"/>
						<%=sbPriorCommunication.toString() %>
				</td>
			</tr>
		</table></center>
	</div> 
    <div id="newPaxComment"><center>
	    <form method="POST" action="paxCommunication.htm" name="frmNewPaxComment" id="frmNewPaxComment">
<%
//if(myNextAction.equalsIgnoreCase("sendnew")) {
%>
			<table cellspacing="1" cellpadding="1">
				<tr>
					<td colspan="2">
						<c:if test="${!empty noNewComment }">
									<spring:message code="no.new.comment" />
						</c:if></td>
				</tr>
				<tr>		
					<td>&nbsp;</td>
					<td>
			  		<TEXTAREA name="newPaxComment" id="newPaxComment" ROWS="5" COLS="73" onKeyUp="highlight(event)" onClick="highlight(event)"></TEXTAREA>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="right">
						<div id="searchButtonContainer">
							<input type="submit" class="button" tabindex="3" value='Add Comment' />
						</div>
					</td>
				</tr>
			</table>
<%
//}
%>
		</form></center>
    </div>
    <!-- /content -->
    <div id="footer">
      <div id="copyright"><spring:message code="rights.reserved" /></div>
    </div>
    <!-- /footer -->
  </div>
  <!-- /wrapper -->
</div>
<!-- /container -->
<script type="text/javascript">

window.onload=function(){
Nifty("#content_resultsRightCol","big");
}
</script>
</body>
</html>
