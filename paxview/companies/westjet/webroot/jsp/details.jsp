<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVPaxCommunication" %>
<%@ page import="com.bagnet.nettracer.ws.v1_1.paxview.xsd.WS_PVIncident" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="css/master.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/niftyCorners.css" type="text/css" />
<script type="text/javascript" src="js/niftycube.js"></script>
<script type="text/javascript" src="js/mootools.js"></script>

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
<title><spring:message code="page.title" /> - Details</title>
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
    <div id="content">
      <h1><spring:message code="bag.track.summary" /></h1>
      <div id="content_resultsLeftCol">
        <p>
        <table cellspacing="4" cellpadding="0" id="baggageTrackingSummary">
          <tr>
            <td class="rightAlign"><spring:message code="guest.name" />:</td>
            <td><c:out value="${incident.passengers[0].firstname} ${incident.passengers[0].lastname}" /></td>
    </tr>
    <c:if test="${!empty incident.passengers[0].homephone}">
          <tr>
            <td class="rightAlign"><spring:message code="home.phone" />:</td>
            <td><c:out value="${incident.passengers[0].homephone}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].workphone}">
          <tr>
            <td class="rightAlign"><spring:message code="work.phone" />:</td>
            <td><c:out value="${incident.passengers[0].workphone}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].mobile}">
          <tr>
            <td class="rightAlign"><spring:message code="cell.phone" />:</td>
            <td><c:out value="${incident.passengers[0].mobile}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].hotel}">
          <tr>
            <td class="rightAlign"><spring:message code="hotel" />:</td>
            <td><c:out value="${incident.passengers[0].hotel}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].email}">
          <tr>
            <td class="rightAlign"><spring:message code="email" />:</td>
            <td><c:out value="${incident.passengers[0].email}" /></td>
          </tr>
    </c:if>
          <tr>
            <td colspan="2"><div class="hr"></div></td>
          </tr>
    <c:if test="${!empty incident.incident_ID}">
          <tr>
            <td class="rightAlign"><spring:message code="field.claimnumber" />:</td>
            <td><c:out value="${incident.incident_ID}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.dispcreatetime}">
          <tr>
            <td class="rightAlign"><spring:message code="date.received" />:</td>
            <td><c:out value="${incident.dispcreatetime}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.incident_status}">
          <tr>
            <td class="rightAlign"><spring:message code="claim.status" />:</td>
            <td>
              <c:choose>
                <c:when test="${incident.incident_status == 'Open' }">
                  <spring:message code="claim.status.open"/>
                </c:when>
                <c:when test="${incident.incident_status == 'Pending' }">
                  <spring:message code="claim.status.pending"/>
                </c:when>
                <c:when test="${incident.incident_status == 'Closed' }">
                  <spring:message code="claim.status.closed"/>
                </c:when>
                <c:when test="${incident.incident_status == 'Temporary' }">
                  <spring:message code="claim.status.temporary"/>
                </c:when>
                <c:otherwise>
                  &nbsp;
                </c:otherwise>
              </c:choose>
            </td>
    </tr>
    </c:if>
        </table>
        </p>
        <p>
        <h1><spring:message code="bag.info" /></h1>
        </p>

        <p>
        <table cellspacing="4" cellpadding="0" id="baggageTrackingSummary">
          <c:forEach items="${incident.items}" var="item" varStatus="status" >
          <tr>
            <td class="rightAlign"><spring:message code="bag" /> ${status.index + 1}</td>
          </tr>
    <c:if test="${!empty incident.items[status.index].claimchecknum}" >
          <tr>
            <td class="rightAlign"><spring:message code="bag.tag.number" />:</td>
            <td><c:out value="${incident.items[status.index].claimchecknum}" /></td>
    	</tr>
    </c:if>
          <tr>
            <td class="rightAlign"><spring:message code="bag.status" />:</td>
            <td>						
            <c:choose>
							<c:when test="${item.bagstatus == 'Open' }">
								<spring:message code="bag.status.open"/>
							</c:when>
							<c:when test="${item.bagstatus == 'Matched' }">
									<spring:message code="bag.status.matched"/>
							</c:when>
							<c:when test="${item.bagstatus == 'In Transit' }">
									<spring:message code="bag.status.transit"/>
							</c:when>
							<c:when test="${item.bagstatus == 'To Be Delivered' }">
									<spring:message code="bag.status.tobe.delivered"/>
							</c:when>
							<c:when test="${item.bagstatus == 'Process For Delivery' }">
									<spring:message code="bag.status.proc.delivery"/>
							</c:when>
							<c:otherwise>
								<spring:message code="unknown"/>
							</c:otherwise>
						</c:choose>
						</td>
    </tr>
    <c:if test="${!empty incident.items[status.index].deliveryAddress}">
          <tr>
            <td class="rightAlign"><spring:message code="deliver.to" />:</td>
	    <td>
		    <c:out value="${incident.items[status.index].deliveryAddress}" />

	    </td>
    </tr>
    </c:if>
		<c:if test="${!status.last}">
			<tr>
            	<td colspan="2"><div class="hr"></div></td>
           </tr>
		</c:if>
		</c:forEach>
        </table>
        </p>

		<c:if test="${!empty incident.comments }">
        <p><strong><spring:message code="special.instructions" /></strong>:<br />
          <textarea name="specialInstructions" id="specialInstructions" cols="45" rows="5" readonly="readonly"><c:out value="${incident.comments }"/></textarea>
        </p>
        </c:if>
        <!-- 
        <p>
        <div align="right" id="newSearchButtonContainer">
        <form action="search.htm" method="get">
          <input type="submit" value="<spring:message code='new.search'/>" class="button" />
        </form>
        </div>
        </p>
        -->
        <div class="hr"></div>       
<%

WS_PVIncident advancedIncident = (WS_PVIncident) request.getSession().getAttribute("FORM_DATA");
if (advancedIncident != null) {
  if (advancedIncident.getPaxCommunication() != null) {
	WS_PVPaxCommunication[] arr = advancedIncident.getPaxCommunication();
	
	if (arr == null || arr.length == 0) {
		//option 1 (pax sends a message)
%>
		<p><strong><spring:message code="pax.want.to.send.new.commnent" /><a href="paxCommunication.htm?prepareto=sendnew">&nbsp; here</a></strong></p>
<%
		
	} else if (arr.length > 0){
		//loop through to find out is there is any new comment from airline
		boolean isThereAnyNewCommentByAirline = false;
		for(int i=0; i<arr.length; i++) {
			WS_PVPaxCommunication myWS_PVPaxCommunication = arr[i];
			if(myWS_PVPaxCommunication != null) {
				String myStatus = "" + myWS_PVPaxCommunication.getStatus();
				if(myStatus.equalsIgnoreCase("NEW") 
						&& myWS_PVPaxCommunication.getAgent() != null) {
					isThereAnyNewCommentByAirline = true;
				}				
			}

		}
		if(isThereAnyNewCommentByAirline) {
			//view new message by airline option
%>
		<p><strong><spring:message code="pax.want.to.view.new.commnent" /><a href="paxCommunication.htm?prepareto=viewnew">&nbsp;click here</a></strong></p>
<%
		} else {
			//view prior communication option
%>
		<p><strong><spring:message code="pax.want.to.view.prior.commnent" /><a href="paxCommunication.htm?prepareto=viewprior">&nbsp;click here</a></strong></p>
<%
		}
	}
  } else {
%>
<p><strong><spring:message code="pax.want.to.send.new.commnent" /><a href="paxCommunication.htm?prepareto=sendnew">&nbsp;click here</a></strong></p>
<%
  }
}
%>
        <!-- new button to start pax communication begins -->
        <div class="hr"></div>
        <p>
        <div align="right" id="newSearchButtonContainer">
        <form action="search.htm" method="get">
          <input type="submit" value="<spring:message code='new.search'/>" class="button" />
        </form>
        </div>
        </p>
        <!-- new button to start pax communication ends -->
      </div>
      <!-- /content_resultsLeftCol -->
      <div id="content_resultsRightCol">
        <p>&nbsp;&nbsp;<spring:message code="click.for.info" /></p>
        <p>
        <table border="0" cellspacing="5" cellpadding="5" id="Definitions">
          <tr>
            <td><h1><spring:message code="claim.definitions" />:</h1></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="claim.status.temporary" /></p><div class="statClaimContent"><p><spring:message code="pv24"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="claim.status.open" /></p><div class="statClaimContent"><p><spring:message code="pv25"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="claim.status.pending" /></p><div class="statClaimContent"><p><spring:message code="pv26"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="claim.status.closed" /></p><div class="statClaimContent"><p><spring:message code="pv27"/></p></div></td>
          </tr>
          <tr>
            <td><div class="hr"></div></td>
          </tr>
          <tr>
            <td><h1><spring:message code="pv28" /></h1></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="bag.status.open" /></p><div class="statClaimContent"><p><spring:message code="pv29"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="bag.status.matched" /></p><div class="statClaimContent"><p><spring:message code="pv30"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="bag.status.transit" /></p><div class="statClaimContent"><p><spring:message code="pv31"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="bag.status.proc.delivery" /></p><div class="statClaimContent"><p><spring:message code="pv32"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" ><spring:message code="bag.status.tobe.delivered" /></p><div class="statClaimContent"><p><spring:message code="pv33"/></p></div></td>
          </tr>
        </table>
        </p>
      </div>
      <!-- /content_resultsRightCol -->
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