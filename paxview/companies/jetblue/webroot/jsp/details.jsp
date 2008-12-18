<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="css/master.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="js/protofx.js"></script>
		<script language="javascript" src="js/nettracer.js"></script>
	<script type="text/javascript">
		function initStaticClaim() 
		{
			var toggleLinks = document.getElementsByClassName('statClaimToggle');
			var contentBlocks = document.getElementsByClassName('statClaimContent');
			
			//accordion effect
			var contentFX = new fx.Accordion(toggleLinks, contentBlocks, { opacity: false });
		}
		function submitform() {

			o = document.searchform;
			o.submit();
		}
	</script>

		<title><spring:message code="pv0" />
		</title>
	</head>
<body onload="initStaticClaim()">

<table cellspacing="0" width="600" border="0"> 
  <tr>
  	<td>
  	<img src="images/pvlogo.jpg"/>
  	</td>
  </tr>
  <tr> 
    <td id="topcell" colspan="2"> <!-- here -->
	<spring:message code="pv35" />: <spring:message code="help.phone" />
		<table border="0">
		<tr>
		<td>
		<div id="content">
		
		<p />								
		
		<div id="wrapper">
		<div id="formbox">
		<table width="550"><tr><td>
		
		<h3><spring:message code="pv8" /></h3>
		<p /><table width="350" class="formtable">
				<tr>
					<td width="125px" class="label"><span class="label"><spring:message code="pv9" />:</span></td>
					<td width="225px" >
						<c:out value="${incident.passengers[0].firstname}"/>
						<c:out value="${incident.passengers[0].middlename}"/>
						<c:out value="${incident.passengers[0].lastname}"/>
					</td>
				</tr>
				<tr>
					<td class="label"><span class="label"><spring:message code="pv10" />:</span></td>
					<td><c:out value="${incident.passengers[0].homephone}" default='<spring:message code="unknown"/>'/></td>
				</tr>
			    <c:if test="${!empty incident.passengers[0].workphone}">
				  <tr>
				    <td class="label"><span><spring:message code="pv11" />:</span></td>
				    <td><c:out value="${incident.passengers[0].workphone}" /></td>
				  </tr>
			    </c:if>
			    <c:if test="${!empty incident.passengers[0].mobile}">
				  <tr>
				    <td class="label"><span><spring:message code="pv12" />:</span></td>
				    <td><c:out value="${incident.passengers[0].mobile}" /></td>
				  </tr>
			    </c:if>
				<tr>
					<td class="label"><span class="label"><spring:message code="pv14" />:</span></td>
					<td><c:out value="${incident.passengers[0].email}" default='<spring:message code="unknown"/>'/></td>
				</tr>
				
				<tr>
					<td colspan="2"><hr size="1px" width="275px"></hr></td>
				</tr>
				
		    
			
				<tr>
					<td class="label"><span><spring:message code="pv15" />:</span></td>
					<td><c:out value="${incident.incident_ID}" default='<spring:message code="unknown"/>'/></td>
				
				</tr>
					
				<tr>
					<td class="label"><span><spring:message code="pv16" />:</span></td>
					<td ><c:out value="${incident.dispcreatetime}" default='<spring:message code="unknown"/>'/></td>	
				</tr>	
					
				<tr>
					<td class="label"><span><spring:message code="pv17" />:</span></td>
					<td align="left"><strong><c:out value="${incident.incident_status}" default='<spring:message code="unknown"/>'/></strong></td>
				</tr>		
					
					
					
					
			</table>
			<br /><br />
				
			<h3><spring:message code="baggage.information" /></h3>
		
				
			<p /><table width="350" class="formtable">
		
		<c:forEach items="${incident.items}" var="item" varStatus="status" >
		    <tr>
					<td width="125px"  class="label"><span><spring:message code="pv18" />:</span></td>
					<td width="225px"  class="field"><c:out value="${item.claimchecknum}" /></td>
				</tr>
		
				<tr>
					<td class="label"><span class="label"><spring:message code="pv19" />:</span></td>
					<td><strong><c:out value="${item.bagstatus}" /></strong></td>
				</tr>
				<tr>
					<td colspan="2"><hr size="1px" width="275px" /></td>
				</tr>
			</c:forEach>

				<tr>

				</tr>
		
			</table><p /></td>
		
			<td>
		
			<div id="right">
				<div align="center"><center><spring:message code="pv22" /></center><p /></div>
				<div class="tools" id="info" style="width: 200px !IMPORTANT;">
		    		<h4><spring:message code="pv23" /></h4>
		    		<ul>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Temporary</strong></a><div class="statClaimContent"><spring:message code="pv24" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Open</strong></a><div class="statClaimContent"><spring:message code="pv25" /></div></li>
		
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Pending</strong></a><div class="statClaimContent"><spring:message code="pv26" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Closed</strong></a><div class="statClaimContent"><spring:message code="pv27" /></div></li>
		    		</ul>
		    		<p>&nbsp;</p>
		    		<h4><spring:message code="pv28" /></h4>
		    		<ul>
		
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Open</strong></a><div class="statClaimContent"><spring:message code="pv29" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Matched</strong></a><div class="statClaimContent"><spring:message code="pv30" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>In Transit</strong></a><div class="statClaimContent"><spring:message code="pv31" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Process for Delivery</strong></a><div class="statClaimContent"><spring:message code="pv32" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>To Be Delivered</strong></a><div class="statClaimContent"><spring:message code="pv33" /></div></li>
		
		    		</ul>
		    	</div>
		    </div>
<img src="images/poweredby_net_tracer.jpg"  alt="<spring:message code="pv34" />" align="right"/>		    
		</td></tr></table>
		<div>
		    
		    </div>
		
		</div>
										
		<p />
		</div>
		</div>
		</td>

		</tr>
		
		</table>
		</td>
		</tr>
		</table>
		        <form action="search.htm" method="get" >
		<input type="submit" value="<spring:message code="pv21" />" class="button" />
        </form>
		</body>
		</html>
