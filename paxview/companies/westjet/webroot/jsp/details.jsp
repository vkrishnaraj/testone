<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="css/master.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/niftyCorners.css" type="text/css" />
<link href="css/sIFR-screen.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/sIFR-print.css" rel="stylesheet" type="text/css" media="print" />
<script type="text/javascript" src="js/niftycube.js"></script>
<script type="text/javascript" src="js/sifr.js"></script>
<script type="text/javascript" src="js/sifr-addons.js"></script>
<script type="text/javascript" src="js/mootools.js"></script>

<script type="text/javascript">
window.addEvent('domready', function() {
	
	var myAccordion = new Accordion($('accordion'), 'p.statClaimToggle', 'div.statClaimContent', {
		opacity: false,
		onActive: function(toggler, element){
		toggler.setStyle('text-decoration', 'none');
	},
	onBackground: function(toggler, element){
		toggler.setStyle('text-decoration', 'underline');
	}
	
	});

});
	
	</script>
	<style type-"text/css">
		p.statClaimToggle {
			cursor: pointer;
		}
	</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WestJet Baggage Tracking Service</title>
</head>
<body onload="initStaticClaim()">
<div id="container">
  <div id="wrapper">
    <div id="header"></div>
    <!-- /header -->
    <div id="content">
      <h1>Baggage Tracking Summary</h1>
      <div id="content_resultsLeftCol">
        <p>
        <table cellspacing="4" cellpadding="0" id="baggageTrackingSummary">
          <tr>
            <td class="rightAlign">Passenger Name:</td>
	    <td><c:out value="${incident.passengers[0].firstname} ${incident.passengers[0].lastname}" /></td>
    </tr>
    <c:if test="${!empty incident.passengers[0].homephone}">
          <tr>
            <td class="rightAlign">Home Phone:</td>
            <td><c:out value="${incident.passengers[0].homephone}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].workphone}">
          <tr>
            <td class="rightAlign">Business Phone:</td>
            <td><c:out value="${incident.passengers[0].workphone}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].mobile}">
          <tr>
            <td class="rightAlign">Mobile Phone:</td>
            <td><c:out value="${incident.passengers[0].mobile}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].hotel}">
          <tr>
            <td class="rightAlign">Hotel:</td>
            <td><c:out value="${incident.passengers[0].hotel}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.passengers[0].email}">
          <tr>
            <td class="rightAlign">Email:</td>
            <td><c:out value="${incident.passengers[0].email}" /></td>
          </tr>
    </c:if>
          <tr>
            <td colspan="2"><div class="hr"></div></td>
          </tr>
    <c:if test="${!empty incident.incident_ID}">
          <tr>
            <td class="rightAlign">Claim Number:</td>
            <td><c:out value="${incident.incident_ID}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.dispcreatetime}">
          <tr>
            <td class="rightAlign">Date Received:</td>
            <td><c:out value="${incident.dispcreatetime}" /></td>
          </tr>
    </c:if>
    <c:if test="${!empty incident.incident_status}">
          <tr>
            <td class="rightAlign">Claim Status:</td>
            <td><c:out value="${incident.incident_status}" /></td>
    </tr>
    </c:if>
        </table>
        </p>
        <p>
        <h1>Baggage Information</h1>
        </p>

        <p>
        <table cellspacing="4" cellpadding="0" id="baggageTrackingSummary">
		<c:forEach items="${incident.items}" var="item" varStatus="status" >
          <tr>
		  <td class="rightAlign">Bag ${status.index + 1}</td>
    </tr>
    <c:if test="${!empty incident.items[status.index].claimchecknum}" >
          <tr>
            <td class="rightAlign">Baggage Number:</td>
            <td><c:out value="${incident.items[status.index].claimchecknum}" /></td>
    	</tr>
    </c:if>
          <tr>
            <td class="rightAlign">Bag Status:</td>
            <td><c:out value="${incident.items[status.index].bagstatus}" /></td>
    </tr>
    <c:if test="${!empty incident.items[status.index].address1}">
          <tr>
            <td class="rightAlign">Deliver to:</td>
	    <td>
		    <c:out value="${incident.items[status.index].address1}" />
    		<c:if test="${!empty incident.items[status.index].address2}">
		<br/><c:out value="${incident.items[status.index].address2}" />
		</c:if>
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

        <p><strong>Special Instructions</strong>:<br />
          <textarea name="specialInstructions" id="specialInstructions" cols="45" rows="5"></textarea>
        </p>
        <p>
        <div align="right" id="newSearchButtonContainer">
        <form action="search.htm" method="get">
          <input type="submit" value="new search" class="button" />
        </form>
        </div>
        </p>
      </div>
      <!-- /content_resultsLeftCol -->
      <div id="content_resultsRightCol">
        <p>&nbsp;&nbsp;Click on a status below for more information.</p>
        <p>
        <table border="0" cellspacing="5" cellpadding="5" id="Definitions">
          <tr>
            <td><h1>Claim Status Definitions:</h1></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Temporary</p><div class="statClaimContent"><p><spring:message code="pv24"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Open</p><div class="statClaimContent"><p><spring:message code="pv25"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Pending</p><div class="statClaimContent"><p><spring:message code="pv26"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Closed</p><div class="statClaimContent"><p><spring:message code="pv27"/></p></div></td>
          </tr>
          <tr>
            <td><div class="hr"></div></td>
          </tr>
          <tr>
            <td><h1>Bag Status Definitions:</h1></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Open</p><div class="statClaimContent"><p><spring:message code="pv29"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Matched</p><div class="statClaimContent"><p><spring:message code="pv30"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >In Transit</p><div class="statClaimContent"><p><spring:message code="pv31"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >Process for Delivery</p><div class="statClaimContent"><p><spring:message code="pv32"/></p></div></td>
          </tr>
          <tr>
			  <td><p class="statClaimToggle" >To Be Delivered</p><div class="statClaimContent"><p><spring:message code="pv33"/></p></div></td>
          </tr>
        </table>
        </p>
      </div>
      <!-- /content_resultsRightCol -->
    </div>
    <!-- /content -->
    <div id="footer">
      <div id="copyright">&copy; WestJet. All rights reserved.</div>
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

if(typeof sIFR == "function"){

// This is the preferred "named argument" syntax

	sIFR.replaceElement("h1", named({sFlashSrc: "flash/din.swf", sColor: "#00285e", sWmode: "transparent"}));
};
</script>
</body>
</html>
