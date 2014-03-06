<%@page import="com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim"%>
<%@page import="com.bagnet.nettracer.tracing.db.Agent"%>
<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>

<%
  Agent a = (Agent)session.getAttribute("user");
  OnlineClaim c = (OnlineClaim)request.getAttribute("claim");
%>

<style type="text/css">
 
 .ocTable {
  cellspacing:0;
  cellpadding:0;
 }

 .ocTable td {
  background-color:#D1E4F1;
  color:#000000;
  font-family:Arial,Helvetica,sans-serif;
  border:1px solid #FFFFFF;
  padding:2px;
  vertical-align:top;
  margin:10px 0;
 } 
 
 .boldCell {
  font-weight:bold;
 }
 
 td.ocHeader {
  background-color: #004990;
  color: #FFFFFF;
  font-weight:bold;
 }
</style>

<div align="left" style="text-align:left;">

<!-- OC Personal Information -->

<h1><bean:message key="oc.label.info.title" /></h1>
<h3><bean:message key="oc.label.name.email" /></h3>
<table class="ocTable" width="95%"><tr>
<td class="boldCell">
<bean:message key="oc.label.salutation" />
</td><td class="boldCell">
<bean:message key="oc.label.firstname" />
</td><td class="boldCell">
<bean:message key="oc.label.middleinitial" />
</td><td class="boldCell">
<bean:message key="oc.label.lastname" />
</td></tr>
<logic:present name="claim" property="passenger" scope="request">
<logic:notEmpty name="claim" property="passenger" scope="request">
<logic:iterate id="paxes" name="claim" property="passenger" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCPassenger" indexId="passIndex">
<tr><td><bean:write name="paxes" property="salutation"/></td>
<td><bean:write name="paxes" property="firstName"/></td>
<td><bean:write name="paxes" property="middleInitial"/></td>
<td><bean:write name="paxes" property="lastName"/></td></tr>
</logic:iterate>
</logic:notEmpty>
</logic:present>
</table>

<!-- Addresses -->
<h3><bean:message key="oc.label.permanent.title" /></h3>

<table class="ocTable" width="95%"><tr>
<logic:present name="claim" property="permanentAddress" scope="request">
<td class="boldCell"><bean:message key="oc.label.permanent.address1" /></td><td colspan="7"><bean:write name="claim" property="permanentAddress.address1" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.permanent.address2" /></td><td colspan="7"><bean:write name="claim" property="permanentAddress.address2" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.permanent.city" /></td><td><bean:write name="claim" property="permanentAddress.city" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.permanent.state" /></td><td><bean:write name="claim" property="permanentAddress.stateProvince" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.permanent.zip" /></td><td><bean:write name="claim" property="permanentAddress.postalCode" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.permanent.country" /></td><td><bean:write name="claim" property="permanentAddress.country" scope="request"/></td>
</logic:present>
<logic:notPresent name="claim" property="permanentAddress" scope="request">
<td class="boldCell"><bean:message key="oc.label.permanent.address1" /></td><td colspan="7"></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.permanent.address2" /></td><td colspan="7"></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.permanent.city" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.permanent.state" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.permanent.zip" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.permanent.country" /></td><td></td>
</logic:notPresent>
</tr></table>
<h3><bean:message key="oc.label.mailing.title" /></h3>
<table class="ocTable" width="95%"><tr>
<logic:present name="claim" property="mailingAddress" scope="request">
<td class="boldCell"><bean:message key="oc.label.mailing.address1" /></td><td colspan="7"><bean:write name="claim" property="mailingAddress.address1" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.mailing.address2" /></td><td colspan="7"><bean:write name="claim" property="mailingAddress.address2" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.mailing.city" /></td><td><bean:write name="claim" property="mailingAddress.city" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.mailing.state" /></td><td><bean:write name="claim" property="mailingAddress.stateProvince" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.mailing.zip" /></td><td><bean:write name="claim" property="mailingAddress.postalCode" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.mailing.country" /></td><td><bean:write name="claim" property="mailingAddress.country" scope="request"/></td>
</logic:present>
<logic:notPresent name="claim" property="mailingAddress" scope="request">
<td class="boldCell"><bean:message key="oc.label.mailing.address1" /></td><td colspan="7"></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.mailing.address2" /></td><td colspan="7"></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.mailing.city" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.mailing.state" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.mailing.zip" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.mailing.country" /></td><td></td>
</logic:notPresent>
</tr></table>

<!-- End Addresses -->

<!-- Phone Numbers -->
<% int phIndex = 0; %>
<h3><bean:message key="oc.label.phone.title" /></h3>
<table class="ocTable" width="95%"><tr>
<logic:present name="claim" property="phone" scope="request">
<logic:notEmpty name="claim" property="phone" scope="request">
<logic:iterate id="phoneNums" name="claim" property="phone" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCPhone" indexId="phoneIndex">
<% if (phIndex % 2 == 0) { %></tr><tr><% } %>
<td class="boldCell">
<logic:match value="Home" name="phoneNums" property="phoneType">
<bean:message key="oc.label.phone.home" />
</logic:match>
<logic:match value="Business" name="phoneNums" property="phoneType">
<bean:message key="oc.label.phone.work" />
</logic:match>
<logic:match value="Fax" name="phoneNums" property="phoneType">
<bean:message key="oc.label.phone.fax" />
</logic:match>
<logic:match value="Mobile" name="phoneNums" property="phoneType">
<bean:message key="oc.label.phone.cell" />
</logic:match>
</td>
<% if (phIndex % 2 == 0) { %><td><% }
else { %><td colspan="5"><% }
phIndex++; %>
<bean:write name="phoneNums" property="phoneNumber"/></td>
</logic:iterate>
</logic:notEmpty>
<logic:empty name="claim" property="phone" scope="request">
<td class="boldCell"><bean:message key="oc.label.phone.home" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.phone.work" /></td><td colspan="5"></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.phone.fax" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.phone.cell" /></td><td colspan="5"></td>
</logic:empty>
</logic:present>
<logic:notPresent name="claim" property="phone" scope="request">
<td class="boldCell"><bean:message key="oc.label.phone.home" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.phone.work" /></td><td colspan="5"></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.phone.fax" /></td><td></td>
<td class="boldCell"><bean:message key="oc.label.phone.cell" /></td><td colspan="5"></td>
</logic:notPresent>
</tr></table>

<!-- END Phone Numbers -->

<h3><bean:message key="oc.label.addinfo" /></h3>
<table class="ocTable" width="95%"><tr>
<td class="boldCell"><bean:message key="oc.label.businessname" /></td><td><bean:write name="claim" property="businessName" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.occupation" /></td><td><bean:write name="claim" property="occupation" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.freqflyer" /></td><td><bean:write name="claim" property="frequentFlierNumber" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.ssn" /></td><td><bean:write name="claim" property="socialSecurity" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.email" /></td><td colspan="3"><bean:write name="claim" property="emailAddress" scope="request"/></td>
</tr></table><br/>

<!-- OC Flight Information -->
<h1><bean:message key="oc.label.flight.title" /></h1>
<table class="ocTable" width="95%"><tr>
<td class="boldCell"><bean:message key="oc.label.bags.travel" /></td><td><bean:write name="claim" property="bagsTravelWith" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.bags.lost" /></td><td><bean:write name="claim" property="bagsDelayed" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.where.checked" /></td><td><bean:write name="claim" property="checkedLocation" scope="request"/>
<logic:equal value="Other" name="claim" property="checkedLocation" scope="request" >
:&#160;<bean:write name="claim" property="checkedLocationDescription" scope="request"/>
</logic:equal>
</td>
<td class="boldCell"><bean:message key="oc.label.num.passengers" /></td><td><bean:write name="claim" property="passengersTravelingWithYou" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.reason.travel" /></td><td><bean:write name="claim" property="reasonForTravel" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.length.stay" /></td><td><bean:write name="claim" property="lengthOfStay" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.excess.charge" /></td>
<td><logic:match value="1" name="claim" property="chargedForExcessBaggage" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="chargedForExcessBaggage" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.est.weight" /></td><td><bean:write name="claim" property="bagWeight" scope="request"/></td>                                                                                     <!-- Not Persisted -->
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.excess.value" /></td>
<td><logic:match value="1" name="claim" property="declaredExcessValue" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="declaredExcessValue" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.value.declared" /></td>
<td><bean:write name="claim" property="declaredValue" scope="request"/>&#160;<bean:write name="claim" property="declaredCurrency" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bag.clear.customs" /></td>
<td><logic:match value="1" name="claim" property="bagClearCustoms" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="bagClearCustoms" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.bag.rerouted" /></td>
<td><logic:match value="1" name="claim" property="baggageReroutedEnRoute" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="baggageReroutedEnRoute" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.diff.claim.check" /></td>
<td><logic:match value="1" name="claim" property="differentClaimCheck" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="differentClaimCheck" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.who.rerouted" /></td><td><bean:write name="claim" property="reroutedAirlineCity" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.reroute.reason" /></td><td><bean:write name="claim" property="reroutedReason" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.claim.immediately" /></td>
<td><logic:match value="1" name="claim" property="attemptToClaimAtArrival" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="attemptToClaimAtArrival" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.last.seen" /></td><td><bean:write name="claim" property="lastSawBaggage" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.city.filed" /></td><td><bean:write name="claim" property="whereDidYouFileReport" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.another.airline" /></td>
<td><logic:match value="1" name="claim" property="reportedToAnotherAirline" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="reportedToAnotherAirline" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.another.airline.name" /></td><td><bean:write name="claim" property="reportedAirline" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.another.airline.city" /></td><td><bean:write name="claim" property="reportedCity" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.another.airline.file" /></td><td><bean:write name="claim" property="reportedFileNumber" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.ticket.airline" /></td>
<td><logic:match value="1" name="claim" property="ticketWithAnotherAirline" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="ticketWithAnotherAirline" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>                                                                                 <!-- Not Persisted -->
<td class="boldCell"><bean:message key="oc.label.ticket.number" /></td><td><bean:write name="claim" property="ticketNumber" scope="request"/></td>                <!-- Not Persisted -->
</tr></table><br/>

<h1><bean:message key="oc.label.itinerary.title" /></h1>
<logic:present name="claim" property="itinerary" scope="request">
<logic:notEmpty name="claim" property="itinerary" scope="request">
<table class="ocTable" width="95%">
<tr>
<td class="ocHeader"><bean:message key="oc.label.itinerary.from" /></td>
<td class="ocHeader"><bean:message key="oc.label.itinerary.to" /></td>
<td class="ocHeader"><bean:message key="oc.label.itinerary.airline" /></td>
<td class="ocHeader"><bean:message key="oc.label.itinerary.flight" /></td>
<td class="ocHeader"><bean:message key="oc.label.itinerary.date" /></td>
</tr>
<logic:iterate id="itins" name="claim" property="itinerary" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCItinerary">
<tr>
<td><bean:write name="itins" property="departureCity"/></td>
<td><bean:write name="itins" property="arrivalCity"/></td>
<td><bean:write name="itins" property="airline"/></td>
<td><bean:write name="itins" property="flightNum"/></td>
<td><bean:write name="itins" property="date"/></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:empty name="claim" property="itinerary" scope="request">
<table class="ocTable" width="95%"><tr><td class="boldCell"><bean:message key="oc.label.itinerary.none" /></td></tr></table>
</logic:empty>
</logic:present>
<logic:notPresent name="claim" property="itinerary" scope="request">
<table class="ocTable" width="95%"><tr><td class="boldCell"><bean:message key="oc.label.itinerary.none" /></td></tr></table>
</logic:notPresent>
<br/>

<!-- OC Bag Information -->

<h1><bean:message key="oc.label.bag.title" /></h1>
<logic:present name="claim" property="bag" scope="request">
<logic:notEmpty name="claim" property="bag" scope="request">
<logic:iterate id="bags" name="claim" property="bag" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCBag" indexId="bagsIndex">
<% if (bags.isBagArrive() && !(c.isDamaged() || c.isMissing()) && !bags.getTag().equals("INTERIM")) { %>
<h3><bean:message key="oc.label.bag.number" />&nbsp;<%= bagsIndex.intValue() + 1 %></h3>
<table class="ocTable" width="95%"><tr>
<td class="boldCell"><bean:message key="oc.label.bag.tag" /></td><td><bean:write name="bags" property="tag"/></td>
</tr></table>
<% } else if (bags.isBagArrive() && c.isInterim() && bags.getTag().equals("INTERIM")) { %>
<h3><bean:message key="oc.title.delayed.expenses" /></h3>
<table class="ocTable" width="95%">
<logic:present name="bags" property="contents">
<logic:notEmpty name="bags" property="contents">
<tr>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.quantity" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.gender" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.article" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.color" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.size" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.brand" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.store" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.date" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.price" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.currency" /></td>
</tr>
<logic:iterate id="conts" name="bags" property="contents" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCContents">
<tr>
<td><bean:write name="conts" property="quantity"/></td>
<td>
<logic:equal value="0" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.na"/>
</logic:equal>
<logic:equal value="1" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.male"/>
</logic:equal>
<logic:equal value="2" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.female"/>
</logic:equal>
<logic:equal value="3" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.child"/>
</logic:equal>
<logic:equal value="4" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.infant"/>
</logic:equal>
<logic:equal value="5" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.fchild"/>
</logic:equal>
</td>
<td><bean:write name="conts" property="article"/></td>
<td><bean:write name="conts" property="color"/></td>
<td><bean:write name="conts" property="size"/></td>
<td><bean:write name="conts" property="brand"/></td>
<td><bean:write name="conts" property="purchasedAt"/></td>
<td><bean:write name="conts" property="purchasedDate"/></td>
<td><bean:write name="conts" property="price"/>&#160;<bean:write name="conts" property="currency"/></td>
<td><bean:write name="conts" property="contentOwner"/></td>
</tr>
</logic:iterate>
<tr><td colspan="10">Total Cost: <bean:write name="bags" property="grandTotal"/></td></tr>
</logic:notEmpty>
<logic:empty name="bags" property="contents">
<tr><td colspan="10"><bean:message key="oc.label.bag.contents.none" /></td></tr>
</logic:empty>
</logic:present>
<logic:notPresent name="bags" property="contents">
<tr><td colspan="10"><bean:message key="oc.label.bag.contents.none" /></td></tr>
</logic:notPresent>
</table>
<% } else { %>
<h3><bean:message key="oc.label.bag.number" />&nbsp;<%= bagsIndex.intValue() + 1 %></h3>
<table class="ocTable" width="95%"><tr>
<%     if (bags.isBagArrive()) { 
	       if (!c.isDamaged()) {%>
<td class="boldCell" colspan="10"><bean:message key="oc.title.missing.claim" /></td>
<%         } else if (!c.isMissing()) { %>
<td class="boldCell" colspan="10"><bean:message key="oc.title.damaged.claim" /></td>
<%         } else { %>
<td class="boldCell" colspan="10"><bean:message key="oc.title.miss.damage.claim" /></td>
<%         } 
       } else { %>
<td class="boldCell" colspan="10"><bean:message key="oc.title.delayed.claim" /></td>
<%     } %>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.bag.tag" /></td><td colspan="3"><bean:write name="bags" property="tag"/></td>
<td class="boldCell" colspan="2"><bean:message key="oc.label.bag.brand" /></td><td colspan="2"><bean:write name="bags" property="brand"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.bag.name" /></td><td colspan="7"><bean:write name="bags" property="nameOnBag"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.bag.purchase" /></td><td colspan="3"><bean:write name="bags" property="purchaseDate"/></td>
<td class="boldCell" colspan="2"><bean:message key="oc.label.bag.markings" /></td><td colspan="2"><bean:write name="bags" property="externalMarkings"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.bag.color" /></td><td colspan="3"><bean:write name="bags" property="bagColor"/></td>
<td class="boldCell" colspan="2"><bean:message key="oc.label.bag.type" /></td><td colspan="2"><bean:write name="bags" property="bagType"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.bag.value" /></td>
<td colspan="3"><bean:write name="bags" property="bagValue"/>&#160;<bean:write name="bags" property="bagCurrency"/></td>
<td class="boldCell" colspan="2"><bean:message key="oc.label.bag.owner" /></td><td colspan="2"><bean:write name="bags" property="bagOwner"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.bag.selected" /></td><td colspan="7">
<logic:match value="true" name="bags" property="hardSided">
<bean:message key="oc.label.bag.elem.hard"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="softSided">
<bean:message key="oc.label.bag.elem.soft"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="locks">
<bean:message key="oc.label.bag.elem.lock"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="wheels">
<bean:message key="oc.label.bag.elem.wheel"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="zippers">
<bean:message key="oc.label.bag.elem.zip"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="pullStrap">
<bean:message key="oc.label.bag.elem.pull"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="feet">
<bean:message key="oc.label.bag.elem.feet"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="retractibleHandle">
<bean:message key="oc.label.bag.elem.handle"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="nameTag">
<bean:message key="oc.label.bag.elem.name"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="trim">
<bean:message key="oc.label.bag.elem.trim"/>&#160;(<bean:write name="bags" property="trimDescription"/>),&#160;
</logic:match>
<logic:match value="true" name="bags" property="pockets">
<bean:message key="oc.label.bag.elem.pocket"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="leather">
<bean:message key="oc.label.bag.elem.leather"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="metal">
<bean:message key="oc.label.bag.elem.metal"/>,&#160;
</logic:match>
<logic:match value="true" name="bags" property="ribbonsOrMarkings">
<bean:message key="oc.label.bag.elem.ribbon"/>,&#160;
</logic:match>
</td>
</tr><tr><td style="background-color: #FFFFFF;">&#160;</td></tr><tr>
<td class="boldCell" colspan="10"><bean:message key="oc.label.bag.contents.title" /></td></tr>
<logic:present name="bags" property="contents">
<logic:notEmpty name="bags" property="contents">
<tr>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.quantity" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.gender" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.article" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.color" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.size" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.brand" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.store" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.date" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.price" /></td>
<td class="ocHeader"><bean:message key="oc.label.bag.contents.currency" /></td>
</tr>
<logic:iterate id="conts" name="bags" property="contents" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCContents">
<tr>
<td><bean:write name="conts" property="quantity"/></td>
<td>
<logic:equal value="0" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.na"/>
</logic:equal>
<logic:equal value="1" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.male"/>
</logic:equal>
<logic:equal value="2" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.female"/>
</logic:equal>
<logic:equal value="3" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.child"/>
</logic:equal>
<logic:equal value="4" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.infant"/>
</logic:equal>
<logic:equal value="5" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.fchild"/>
</logic:equal>
</td>
<td><bean:write name="conts" property="article"/></td>
<td><bean:write name="conts" property="color"/></td>
<td><bean:write name="conts" property="size"/></td>
<td><bean:write name="conts" property="brand"/></td>
<td><bean:write name="conts" property="purchasedAt"/></td>
<td><bean:write name="conts" property="purchasedDate"/></td>
<td><bean:write name="conts" property="price"/>&#160;<bean:write name="conts" property="currency"/></td>
<td><bean:write name="conts" property="contentOwner"/></td>
</tr>
</logic:iterate>
<tr><td colspan="10">Total Cost: <bean:write name="bags" property="grandTotal"/></td></tr>
</logic:notEmpty>
<logic:empty name="bags" property="contents">
<tr><td colspan="10"><bean:message key="oc.label.bag.contents.none" /></td></tr>
</logic:empty>
</logic:present>
<logic:notPresent name="bags" property="contents">
<tr><td colspan="10"><bean:message key="oc.label.bag.contents.none" /></td></tr>
</logic:notPresent>
</table>
<% } %>
</logic:iterate>
</logic:notEmpty>
<logic:empty name="claim" property="bag" scope="request">
<table class="ocTable" width="95%"><tr><td class="boldCell" colspan="9"><bean:message key="oc.label.bag.none" /></td></tr></table>
</logic:empty>
</logic:present>
<logic:notPresent name="claim" property="bag" scope="request">
<table class="ocTable" width="95%"><tr><td class="boldCell" colspan="9"><bean:message key="oc.label.bag.none" /></td></tr></table>
</logic:notPresent>
<br/>

<!-- OC Uploaded Receipts -->

<h1><bean:message key="oc.label.files.title" /></h1>
<logic:present name="claim" property="file" scope="request">
<logic:notEmpty name="claim" property="file" scope="request">
<table class="ocTable" width="95%">
<tr><td><bean:message key="oc.label.files.filename" />
</td><td><bean:message key="oc.label.files.interim" />
</td></tr>
<logic:iterate id="files" name="claim" property="file" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCFile">
<tr><td>
<a href="showImage?ID=<bean:write name='files' property='path'/>/<bean:write name='files' property='filename'/>&useOCPath=1" target="_blank">
<bean:write name="files" property="filename"/></a>
</td><td><logic:match value="true" name="files" property="interim">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="false" name="files" property="interim">
<bean:message key="oc.label.no"/>
</logic:match>
</td></tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:empty name="claim" property="file" scope="request">
<table class="ocTable" width="95%"><tr><td class="boldCell"><bean:message key="oc.label.files.none" /></td></tr></table>
</logic:empty>
</logic:present>
<logic:notPresent name="claim" property="file" scope="request">
<table class="ocTable" width="95%"><tr><td class="boldCell"><bean:message key="oc.label.files.none" /></td></tr></table>
</logic:notPresent>
<br/>

<!-- OC Previous Claims Information -->
<h1><bean:message key="oc.label.prev.claim.title" /></h1>
<table class="ocTable" width="95%"><tr>
<td class="boldCell"><bean:message key="oc.label.claim.type" /></td>
<td><logic:match value="true" name="claim" property="delayed" scope="request">
<bean:message key="oc.label.type.ld"/>,&#160;
</logic:match>
<logic:match value="true" name="claim" property="missing" scope="request">
<bean:message key="oc.label.type.miss"/>,&#160;
</logic:match>
<logic:match value="true" name="claim" property="damaged" scope="request">
<bean:message key="oc.label.type.dam"/>,&#160;
</logic:match>
<logic:match value="true" name="claim" property="interim" scope="request">
<bean:message key="oc.label.type.inter"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.bag.received.date" /></td><td><bean:write name="claim" property="bagReceivedDate" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.paid.foreign.curr" /></td>
<td><logic:match value="1" name="claim" property="requestForeignCurrency" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="requestForeignCurrency" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.foreign.curr.email" /></td><td><bean:write name="claim" property="foreignCurrencyEmail" scope="request"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.priv.insur" /></td>
<td><logic:match value="1" name="claim" property="privateInsurance" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="privateInsurance" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.priv.insur.name" /></td><td><bean:write name="claim" property="privateInsuranceName" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.priv.insur.addr" /></td><td><bean:write name="claim" property="privateInsuranceAddr" scope="request"/></td>
</tr><tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.prev.claim" /></td>
<td><logic:match value="1" name="claim" property="filedPreviousClaim" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="filedPreviousClaim" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.which" /></td><td><bean:write name="claim" property="filedPreviousAirline" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.prev.claim.date" /></td><td><bean:write name="claim" property="filedPrevoiusDate" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.name" /></td><td colspan="3"><bean:write name="claim" property="filedPreviousClaimant" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.tsa.inspect" /></td>
<td><logic:match value="1" name="claim" property="tsaInspected" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="tsaInspected" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
<td class="boldCell"><bean:message key="oc.label.prev.claim.tsa.note" /></td>
<td><logic:match value="1" name="claim" property="tsaNotePresent" scope="request">
<bean:message key="oc.label.yes"/>
</logic:match>
<logic:match value="2" name="claim" property="tsaNotePresent" scope="request">
<bean:message key="oc.label.no"/>
</logic:match></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.tsa.where" /></td><td colspan="3"><bean:write name="claim" property="tsaInspectionLocation" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.comment" /></td><td colspan="3"><bean:write name="claim" property="comments" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.claim.amount" /></td><td><bean:write name="claim" property="paxClaimAmount" scope="request"/>
&#160;<bean:write name="claim" property="paxClaimAmountCurrency" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.date.incident" /></td><td><bean:write name="claim" property="paxClaimDate" scope="request"/></td>
</tr></table>

</div>