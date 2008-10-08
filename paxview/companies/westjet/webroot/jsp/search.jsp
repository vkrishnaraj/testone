<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="css/master.css" rel="stylesheet" type="text/css" />
<link href="css/sIFR-screen.css" rel="stylesheet" type="text/css" media="screen" />
<link href="css/sIFR-print.css" rel="stylesheet" type="text/css" media="print" />
<script type="text/javascript" src="js/sifr.js"></script>
<script type="text/javascript" src="js/sifr-addons.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WestJet Baggage Tracking Service</title>
</head>
<body>
  <div id="wrapper">
    <div id="header"></div>
    <!-- /header -->
    <div id="content">
      <h1>Check baggage status</h1>
      <div id="francais">&gt;&gt; <a href="#">Fran&ccedil;ais</a></div>
      <!-- /francais -->
      <p><strong>Delayed Baggage</strong>: If you have notified a WestJet Customer Service Agent of your delayed baggage, please enter your claim number below to check the current status of your baggage.</p>

      <form:form method="POST" action="search.htm" >
      <table cellspacing="0" cellpadding="0" id="inputDelayedBags">
         <c:if test="${!empty noneFound }">
      		<tr>
          		<td>&nbsp;</td>
         		 <td class="formError" colspan="2"><spring:message code="no.incident.found"/></td>
        	</tr>
        </c:if>
        <tr>
          <td><label for="last name">Last Name:</label></td>
          <td class="formField"><form:input path="lastname" tabindex="1" cssClass="formField" /></td>
          <td class="formError"><form:errors path="lastname" cssClass="errors" /></td>
        </tr>
        <tr>
          <td><label for="claim number">Claim Number:</label></td>
          <td class="formField"><form:input path="claimnumber" tabindex="2" cssClass="formField" /></td>
          <td class="formError"><form:errors path="claimnumber" /></td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td class="smallFormText" colspan="2"><strong>Claim number</strong> - You may omit leading zeros when entering your claim number. For example:
            WSLFL486 becomes WSLFL00000486. </td>
        </tr>
        <tr>
          <td>&nbsp;</td>
          <td><div id="searchButtonContainer">
                <input type="submit" class="button" tabindex="3"/>
            </div></td>
        </tr>
      </table>
      </form:form>
      <div class="hr"></div>
      <p><img src="images/icon_Advisory.png" alt="Important Note" width="20" height="20" style="float: left; margin: 0 10px 100px 0;" />If you have not already notified us of your delayed baggage, please contact our System Baggage Service office Monday through Friday from 7:30am to 7:30pm.</p>
      <p><strong>System Baggage Service</strong>: 1-800-965-2107 x8900.</p>
    </div>
    <!-- /content -->
    <div id="footer">
      <div id="copyright">&copy; WestJet. All rights reserved.</div>
    </div>
    <!-- /footer -->
  </div>
  <!-- /wrapper -->
<script type="text/javascript">
if(typeof sIFR == "function"){
// This is the preferred "named argument" syntax

	sIFR.replaceElement("h1", named({sFlashSrc: "flash/din.swf", sColor: "#00285e", sWmode: "transparent"}));
};
</script>
</body>
</html>
