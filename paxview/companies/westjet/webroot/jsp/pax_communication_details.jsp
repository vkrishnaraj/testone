<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication" %>
<%@ page import="com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<% 
String myNextAction = "" + (String) request.getAttribute("prepareto");

boolean isThereAnyMessage = false;

String strLocale = "" + request.getAttribute("siteLanguage");

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
						if (strLocale.equalsIgnoreCase("fr")) {
							enteredBy = " - Votre message";
						} else {
							enteredBy = " - Your Comment";
						}
					} else {
						enteredBy = " - WestJet";
					} 
					String createdDateTime = myWS_PVPaxCommunication.getCreate_timestamp();

					SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss");
					Locale localeFR = Locale.FRANCE;
			        DateFormat fullDateFormatFR =
			            DateFormat.getDateTimeInstance(
			            DateFormat.FULL,
			            DateFormat.MEDIUM,
			            localeFR);

					if (strLocale.equalsIgnoreCase("fr")) {
				        try {
				        	Date myFrenchDate = sdf.parse(createdDateTime); 
				        	createdDateTime = fullDateFormatFR.format(myFrenchDate);
				        } catch(ParseException e) {
				        	createdDateTime += e.toString();
				        }
				        
					}
					
					sbPriorCommunication.append("<p class='small'>");
					sbPriorCommunication.append("<H2>" + createdDateTime + " GMT" + enteredBy + "</H2>");
					
					String strAcknowledgedBy = "";
					String myAcknowledgedAgent = "" + myWS_PVPaxCommunication.getAcknowledged_agent();
					String myAcknowledgedTimestamp = "" + myWS_PVPaxCommunication.getAcknowledged_timestamp();
					if(myAcknowledgedAgent != null && myAcknowledgedTimestamp != null) {
						if(!myAcknowledgedAgent.equals("") && (!myAcknowledgedTimestamp.equals(""))) {
							if(!myAcknowledgedTimestamp.equals("null")) {
								String myStatus = myWS_PVPaxCommunication.getStatus();
								if(myStatus.equalsIgnoreCase("RESPONDED")) {
									strAcknowledgedBy = "<I>Comment responded by WestJet on " + myAcknowledgedTimestamp + "</I>";
									if (strLocale.equalsIgnoreCase("fr")) {
								        try {
								        	Date myFrenchAcknowledgedDate = sdf.parse(myAcknowledgedTimestamp); 
								        	myAcknowledgedTimestamp = fullDateFormatFR.format(myFrenchAcknowledgedDate);
								        } catch(ParseException e) {
								        	myAcknowledgedTimestamp += e.toString();
								        }
										strAcknowledgedBy = "<I>Message répondu par WestJet le " + myAcknowledgedTimestamp + "</I>";
									}
								} else {
									strAcknowledgedBy = "<I>Comment acknowledged by WestJet on " + myAcknowledgedTimestamp + "</I>";
									if (strLocale.equalsIgnoreCase("fr")) {
								        try {
								        	Date myFrenchAcknowledgedDate = sdf.parse(myAcknowledgedTimestamp); 
								        	myAcknowledgedTimestamp = fullDateFormatFR.format(myFrenchAcknowledgedDate);
								        } catch(ParseException e) {
								        	myAcknowledgedTimestamp += e.toString();
								        }
										strAcknowledgedBy = "<I>Message reconnu par WestJet le " + myAcknowledgedTimestamp + "</I>";
									}
								}	
							}
						}
					}
					sbPriorCommunication.append(strAcknowledgedBy + "</p><BR />");
					String myComment = "" + myWS_PVPaxCommunication.getComment();
					myComment = myComment.replaceAll("\n", "<BR />");
					sbPriorCommunication.append("<H4>" + myComment + "</H4>");
					sbPriorCommunication.append("<HR />");
				}
			}
			isThereAnyMessage = true;
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

<script type="text/javascript">
function redirect2ClaimDetail() {
	document.location.href='search.htm';
}
</script>

<script language="javascript" type="text/javascript">
function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
</script>


<style type="text/css">
p.statClaimToggle {
    cursor: pointer;
}
p.small
{
	line-height: 3px
}
p.big
{
	line-height: 30px
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

    <div id="content_commentsCenterCol">
    
      	<table border="0" cellspacing="5" cellpadding="5" id="Definitions">
      	    <tr>
   				<td align="left">
					<div id="searchButtonContainer">
							<form action="claimDetail.htm" method="POST">
          						<input type="submit" value="<spring:message code='view.claim.detail'/>" class="button" />
        					</form>
					</div>
				</td>   	    	
      	    </tr>
<% if(isThereAnyMessage) { %>
      	    <tr>
      	    	<td><center><strong><spring:message code="pax.communication.priorcomments" /></strong>:</center></td>
      	    </tr>
<% } %>
      		<tr>
				<td>
			  			<!--<c:out value="${incident.comments }"/>-->
						<%=sbPriorCommunication.toString() %>
				</td>
			</tr>
		</table><!--</center>-->
	</div> 
    <div id="newPaxComment">
	    <form method="post" action="paxCommunication.htm" name="frmNewPaxComment" id="frmNewPaxComment">
			<table cellspacing="0" cellpadding="5" border="0">
				<tr>
					<td>
						<c:if test="${!empty noNewComment }">
									<spring:message code="no.new.comment" />
						</c:if></td>
				</tr>
				<tr>
					<td><center>
						<spring:message code="please.type.new.message.below" /></center>
					</td>
				</tr>
				<tr>		
					<td>
						<table cellspacing="0" cellpadding="0" border="0">
							<tr><td>
								<TEXTAREA name="newPaxComment" id="newPaxComment" ROWS="5" COLS="82" onKeyDown="limitText(this.form.newPaxComment,this.form.countdown,1500);" onKeyUp="highlight(event);limitText(this.form.newPaxComment,this.form.countdown,1500);"></TEXTAREA>
							</td></tr>
							<tr><td align="center">
								<font size="1">(<spring:message code="maximum.characters" />: 1500)<br />
								<spring:message code="you.have" /> <input readonly type="text" name="countdown" size="3" value="1500"> <spring:message code="characters.left" /></font>
							</td></tr>
							<tr><td align="right">
								<div id="searchButtonContainer">
									<input type="submit" class="button" tabindex="3" value='<spring:message code="button.submit.message" />' />
								</div>
							</td></tr></table>
					</td>
				</tr>
			</table>

		</form>
    </div>
    
    <div id="footer">
      <div id="copyright"><spring:message code="rights.reserved" /></div>
    </div>
    
  </div>
  
</div>

<script type="text/javascript">

window.onload=function(){
Nifty("#content_resultsRightCol","big");
}
</script>
</body>
</html>
