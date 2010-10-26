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
</style>

<div align="center">

<!-- OC Personal Information -->

<table class="ocTable" width="95%"><tr>
<td class="boldCell" colspan="8"><bean:message key="oc.label.info.title" /></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.firstname" /></td><td><bean:write name="claim" property="firstName" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.lastname" /></td><td><bean:write name="claim" property="lastName" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.middleinitial" /></td><td colspan="3"><bean:write name="claim" property="middleInitial" scope="request"/></td>
</tr>

<!-- Addresses -->
<tr>
<td class="boldCell" colspan="8"><bean:message key="oc.label.permanent.title" /></td>
</tr><tr>
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
</tr><tr>
<td class="boldCell" colspan="8"><bean:message key="oc.label.mailing.title" /></td>
</tr><tr>
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
</tr>

<!-- End Addresses -->

<tr>
<td class="boldCell"><bean:message key="oc.label.email" /></td><td colspan="7"><bean:write name="claim" property="emailAddress" scope="request"/></td>
</tr>

<!-- Phone Numbers -->

<tr>
<td class="boldCell" colspan="8"><bean:message key="oc.label.phone.title" /></td>
</tr><tr>
<logic:present name="claim" property="phone" scope="request">
<logic:notEmpty name="claim" property="phone" scope="request">
<logic:iterate id="phoneNums" name="claim" property="phone" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCPhone" indexId="phoneIndex">
<logic:equal value="2" name="phoneIndex"></tr><tr></logic:equal>
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
<logic:equal value="0" name="phoneIndex"><td></logic:equal>
<logic:equal value="1" name="phoneIndex"><td colspan="5"></logic:equal>
<logic:equal value="2" name="phoneIndex"><td></logic:equal>
<logic:equal value="3" name="phoneIndex"><td colspan="5"></logic:equal>
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
</tr>

<!-- END Phone Numbers -->

<tr>
<td class="boldCell"><bean:message key="oc.label.businessname" /></td><td><bean:write name="claim" property="businessName" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.occupation" /></td><td colspan="5"><bean:write name="claim" property="occupation" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.freqflyer" /></td><td><bean:write name="claim" property="frequentFlierNumber" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.ssn" /></td><td colspan="5"><bean:write name="claim" property="socialSecurity" scope="request"/></td>
</tr></table><br/>

<!-- OC Flight Information -->
<table class="ocTable" width="95%"><tr>
<td class="boldCell" colspan="4"><bean:message key="oc.label.flight.title" /></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bags.travel" /></td><td><bean:write name="claim" property="bagsTravelWith" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.bags.lost" /></td><td><bean:write name="claim" property="bagsDelayed" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.where.checked" /></td><td><bean:write name="claim" property="checkedLocation" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.num.passengers" /></td><td><bean:write name="claim" property="passengersTravelingWithYou" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.recheck.bag" /></td><td><bean:write name="claim" property="haveToRecheck" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.bag.inspected" /></td><td><bean:write name="claim" property="wasBagInspected" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.excess.charge" /></td><td><bean:write name="claim" property="chargedForExcessBaggage" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.bags.checked" /></td><td></td>                                                                                   <!-- Not Persisted -->
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bags.missing" /></td><td></td>                                                                                   <!-- Not Persisted -->
<td class="boldCell"><bean:message key="oc.label.claim.check" /></td><td></td>                                                                                    <!-- Not Persisted -->
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.excess.value" /></td><td><bean:write name="claim" property="declaredExcessValue" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.value.declared" /></td><td><bean:write name="claim" property="declaredValue" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bag.clear.customs" /></td><td><bean:write name="claim" property="bagClearCustoms" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.est.weight" /></td><td></td>                                                                                     <!-- Not Persisted -->
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bag.rerouted" /></td><td><bean:write name="claim" property="baggageReroutedEnRoute" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.diff.claim.check" /></td><td><bean:write name="claim" property="differentClaimCheck" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.who.rerouted" /></td><td><bean:write name="claim" property="reroutedAirlineCity" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.reroute.reason" /></td><td><bean:write name="claim" property="reroutedReason" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.claim.immediately" /></td><td><bean:write name="claim" property="attemptToClaimAtArrival" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.last.seen" /></td><td><bean:write name="claim" property="lastSawBaggage" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.city.filed" /></td><td><bean:write name="claim" property="whereDidYouFileReport" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.another.airline" /></td><td><bean:write name="claim" property="reportedToAnotherAirline" scope="request"/></td>
</tr><tr>
<td class="boldCell" colspan="4"><bean:message key="oc.label.ticket.title" /></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.ticket.airline" /></td><td></td>                                                                                 <!-- Not Persisted -->
<td class="boldCell"><bean:message key="oc.label.ticket.number" /></td><td><bean:write name="claim" property="ticketNumber" scope="request"/></td>                <!-- Not Persisted -->
</tr></table><br/>

<logic:present name="claim" property="itinerary" scope="request">
<logic:notEmpty name="claim" property="itinerary" scope="request">
<table class="ocTable" width="95%">
<tr>
<td class="boldCell" colspan="5"><bean:message key="oc.label.itinerary.title" /></td>
</tr>
<tr>
<td class="boldCell"><bean:message key="oc.label.itinerary.from" /></td>
<td class="boldCell"><bean:message key="oc.label.itinerary.to" /></td>
<td class="boldCell"><bean:message key="oc.label.itinerary.airline" /></td>
<td class="boldCell"><bean:message key="oc.label.itinerary.flight" /></td>
<td class="boldCell"><bean:message key="oc.label.itinerary.date" /></td>
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
<bean:message key="oc.label.itinerary.none" /><br/>
</logic:empty>
</logic:present>
<logic:notPresent name="claim" property="itinerary" scope="request">
<bean:message key="oc.label.itinerary.none" /><br/>
</logic:notPresent>
<br/>

<!-- OC Bag Information -->

<logic:present name="claim" property="bag" scope="request">
<logic:notEmpty name="claim" property="bag" scope="request">
<logic:iterate id="bags" name="claim" property="bag" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCBag" indexId="bagsIndex">
<table class="ocTable" width="95%">
<logic:equal value="0" name="bagsIndex">
<tr><td colspan="4" class="boldCell"><bean:message key="oc.label.bag.title" /></td></tr>
</logic:equal>
<tr><td colspan="4" class="boldCell"><bean:message key="oc.label.bag.number" />&nbsp;<%= bagsIndex.intValue() + 1 %></td></tr>
<tr>
<td class="boldCell"><bean:message key="oc.label.bag.tag" /></td><td><bean:write name="bags" property="tag"/></td>
<td class="boldCell"><bean:message key="oc.label.bag.brand" /></td><td><bean:write name="bags" property="brand"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bag.name" /></td><td colspan="3"><bean:write name="bags" property="nameOnBag"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bag.purchase" /></td><td><bean:write name="bags" property="purchaseDate"/></td>
<td class="boldCell"><bean:message key="oc.label.bag.markings" /></td><td><bean:write name="bags" property="externalMarkings"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.bag.color" /></td><td><bean:write name="bags" property="bagColor"/></td>
<td class="boldCell"><bean:message key="oc.label.bag.type" /></td><td><bean:write name="bags" property="bagType"/></td>
</tr></table><br/>
<table class="ocTable" width="95%"><tr><td class="boldCell" colspan="9"><bean:message key="oc.label.bag.contents.title" /></td></tr>
<logic:present name="bags" property="contents">
<logic:notEmpty name="bags" property="contents">
<tr>
<td class="boldCell"><bean:message key="oc.label.bag.contents.gender" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.article" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.color" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.size" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.brand" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.store" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.date" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.price" /></td>
<td class="boldCell"><bean:message key="oc.label.bag.contents.currency" /></td>
</tr>
<logic:iterate id="conts" name="bags" property="contents" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCContents">
<tr>
<td>
<logic:match value="true" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.male"/>
</logic:match>
<logic:match value="false" name="conts" property="male">
<bean:message key="oc.label.bag.contents.gender.female"/>
</logic:match>
</td>
<td><bean:write name="conts" property="article"/></td>
<td><bean:write name="conts" property="color"/></td>
<td><bean:write name="conts" property="size"/></td>
<td><bean:write name="conts" property="brand"/></td>
<td><bean:write name="conts" property="purchasedAt"/></td>
<td><bean:write name="conts" property="purchasedDate"/></td>
<td><bean:write name="conts" property="price"/></td>
<td><bean:write name="conts" property="currency"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>
<logic:empty name="bags" property="contents">
<tr><td colspan="9"><bean:message key="oc.label.bag.contents.none" /></td></tr>
</logic:empty>
</logic:present>
<logic:notPresent name="bags" property="contents">
<tr><td colspan="9"><bean:message key="oc.label.bag.contents.none" /></td></tr>
</logic:notPresent>
</table>
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

<logic:present name="claim" property="file" scope="request">
<logic:notEmpty name="claim" property="file" scope="request">
<table class="ocTable" width="95%">
<tr><td class="boldCell">
<bean:message key="oc.label.files.title" />
</td></tr>
<logic:iterate id="files" name="claim" property="file" type="com.bagnet.nettracer.tracing.db.onlineclaims.OCFile">
<tr><td>
<a href="/tracer/showImage?ID=<bean:write name='files' property='path'/>/<bean:write name='files' property='filename'/>&useOCPath=1" target="_blank">
<bean:write name="files" property="filename"/></a>
</td></tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:empty name="claim" property="file" scope="request">
<bean:message key="oc.label.files.none" /><br/>
</logic:empty>
</logic:present>
<logic:notPresent name="claim" property="file" scope="request">
<bean:message key="oc.label.files.none" /><br/>
</logic:notPresent>
<br/>

<!-- OC Previous Claims Information -->

<table class="ocTable" width="95%"><tr><td colspan="4" class="boldCell"><bean:message key="oc.label.prev.claim.title" /></td></tr>
<td class="boldCell" colspan="3"><bean:message key="oc.label.prev.claim" /></td><td><bean:write name="claim" property="filedPreviousClaim" scope="request"/></td>
</tr><tr>
<td class="boldCell" colspan="4"><bean:message key="oc.label.prev.claim.yes" /></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.which" /></td><td><bean:write name="claim" property="filedPreviousAirline" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.prev.claim.date" /></td><td><bean:write name="claim" property="filedPrevoiusDate" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.name" /></td><td colspan="3"><bean:write name="claim" property="filedPreviousClaimant" scope="request"/></td>
</tr><tr>
<td class="boldCell" colspan="4"><bean:message key="oc.label.prev.claim.tsa" /></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.tsa.inspect" /></td><td><bean:write name="claim" property="tsaInspected" scope="request"/></td>
<td class="boldCell"><bean:message key="oc.label.prev.claim.tsa.note" /></td><td><bean:write name="claim" property="tsaNotePresent" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.tsa.where" /></td><td colspan="3"><bean:write name="claim" property="tsaInspectionLocation" scope="request"/></td>
</tr><tr>
<td class="boldCell"><bean:message key="oc.label.prev.claim.comment" /></td><td colspan="3"><bean:write name="claim" property="comments" scope="request"/></td>
</tr></table>

</div>