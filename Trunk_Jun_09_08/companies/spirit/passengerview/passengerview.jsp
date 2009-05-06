<%@ page language="java" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="com.bagnet.nettracer.tracing.db.OHD_Photo" %>
<%@ page import="com.bagnet.nettracer.tracing.constant.TracingConstants" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>Spirit Airlines - NetTracer Search</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	
	
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/deployment/main/js/protofx.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>
	
	
	<style type="text/css">
		body, td {
			font: 9pt Arial, Helvetica, sans-serif;
			color: #000000;
		}
	
		.statClaimContent 
		{
			font-size: 11px;
			height: 0;
			overflow: hidden;
			margin-top: 4px;
			margin-bottom: -4px;
			width: 180px;
		}
		
		td.label
		{
			font-weight: bold;
			text-align: right;
			padding-top: 5px;
		}
		
		#formbox {
			border: 1px solid #006699;
			padding: 10px 10px 10px 10px;
			background-color: #F4F4F4;
			position: relative;
		}
		
		#wrapper {
			position: relative;
			margin: 0 auto;
			text-align: left;
			width: 765px;
			z-index: 0;
		}
		
		#wrapper table.formtable td {
			padding: 3px 8px 3px 0;
		}
		
		span.button {
	height: 30px;
	display: block;
	margin-right: 5px;
	float: left;
	width: 80px;
}

#right {
	padding: 22px 0 0 20px;
}

#right .tools {
	margin: 0 0 33px 10px;
	width: 120px;
}

#right .tools h4 {
	color: #000000;
	font-weight: bold;
	border-bottom: 1px solid #000000;
	margin-bottom: 6px;
	padding: 4px 0 7px 0;
}

#right #tools h4 {
}

#right #info h4 {
}

#right .tools ul {
	margin: 0; padding: 0;
}

#right .tools li {
	display: block;
	border-bottom: 1px solid #000000;
	list-style: none;
	margin: 0 0 8px;
	padding: 0 0 9px 6px;
}

#right .tools a {
	color: #006699;
	text-decoration: none;
	font-weight: bold;
	font: bold 10px/10px Arial, Helvetica, sans-serif;
}

#right .tools a:hover {
	text-decoration: underline;
}

#right .tools li a {
	font-size: 11px;
	font-weight: normal;
	
}

#right img {
	display: block;
}
	</style>
	<script type="text/javascript">
		function initStaticClaim() 
		{
			var toggleLinks = document.getElementsByClassName('statClaimToggle');
			var contentBlocks = document.getElementsByClassName('statClaimContent');
			

			var contentFX = new fx.Accordion(toggleLinks, contentBlocks, { opacity: false });
		}
		function submitform() {

			o = document.searchform;
			o.submit();
		}
	</script>
	
</head>

<BODY onload="initStaticClaim()">

<img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/logo.jpg"/>

<table cellspacing="0" id="bodytable"> 
  <tr> 
    <td id="topcell"> 
    
<bean:define id="theform" name="IncidentForm" scope="request" />

<logic:notPresent name="incident" scope="request">
		
		<form method="post" action="passengerviewonly.do" name="searchform">
		
		<div id="content" style="WIDTH: 800px">
		
		<p /><h3>Delayed Baggage</h3>
		<p>If you have notified Spirit Airlines of your delayed baggage, enter your claim number below to check the current status of your baggage.</p>		
		<logic:messagesPresent message="true"><p><font color=red><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></font></p></logic:messagesPresent>

		<div id="formbox" style="margin-top: 1em;  width: 525px;">
		
		<h3>Check Baggage Status</h3>
			<table width="100%">
				<tr>
					<td class="label" width="15%"><span>Last Name:</span></td>
					<td width="25%">
					<input type="text" name="name" size="20" maxlength="50" class="textfield" /></td><td width="2%"></td>
					<td width="48%" rowspan="3">
					
					<p><br /><font style= "font-size: 11px;"><b>Claim number</b> &#150; You may omit leading zeros when entering your claim number. For example:</font><br /><font style= "font-size: 11px; line-height: 25px;">ATLNK00000486	&nbsp;becomes&nbsp; 	ATLFL486</font></p>
		
					</td>
				</tr>	
				<tr>
					<td width="15%" class="label"><span>Claim Number:</span></td>
					<td width="25%">
					<input type="text" name="incident_ID" size="20" maxlength="50" onblur="fillzero(this,13);" />
					
					</td><td width="2%"></td>
				</tr>
								<tr>
		
					<td>&nbsp;</td><td><span class="button" style="width:100px; "><input type="submit" name="search" value="&nbsp;&nbsp;&nbsp;Search" align="absmiddle" /></span></td><td width="2%"></td>
				</tr>				
			</table>
		</div>
		<p />								
		 
		</div>
		</form>
</logic:notPresent>

<logic:present name="incident" scope="request">

		<table border = 0>
		<tr>
		<td>
		<div id="content" style="WIDTH: 600px">
		
		<p>								
													
		<style type="text/css">
			.borderOff { border-bottom:0px solid; border-left:0px solid; border-right:0px solid; border-top:0px solid; margin-top: 1px; background: none; background-color : transparent; 
		background-image: none; 
		}	
		</style>
		
		<div id="wrapper">
		<div id="formbox" style="margin-top: 1em;  width: 575px;">
		<table width="550"><tr><td>
		
		<h3>Baggage Tracking Summary</h3>
			<p></p><table width="350" class="formtable">
			<% int z = 0; %>
			<logic:iterate id="passenger" name="theform" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.Passenger">
			<% if (z == 0) { %>
				<tr>
		
					<td width="125px" class="label"><span class="label">Passenger Name:</span></td>
					<td width="225px" >
					<bean:write name="passenger" property="firstname" /> 
					<bean:write name="passenger" property="middlename" /> 
					<bean:write name="passenger" property="lastname" />
					</td>
				
				</tr>
				<logic:present name="passenger" property="addresses">
		    <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.Address">
		                    
				<tr>
					<td class="label"><span class="label">Home Phone:</span></td>
					<td><bean:write name="addresses" property="homephone"/></td>
					</tr>
				<tr>
		
					<td class="label"><span>Business Phone:</span></td>
					<td><bean:write name="addresses" property="workphone"/></td>
					
				</tr>
				<tr>
					<td class="label"><span>Mobile Phone:</span></td>
					<td><bean:write name="addresses" property="mobile"/></td>
					
				</tr>
				<tr>
		
					<td class="label"><span>Hotel:</span></td>
					<td><bean:write name="addresses" property="hotel"/></td>
					
				</tr>
				<tr>	
					<td class="label"><span>Email: </span></td>
					<td><bean:write name="addresses" property="email"/></td>			
				</tr>
				
				<tr>
					<td colspan="2"><hr size="1px" width="275px"></hr></td>
				</tr>
				
				</logic:iterate>
		    </logic:present>
		    
		    <%
		    } 
			z++;
			%>
			</logic:iterate>
		    
			
				<tr>
					<td class="label"><span>Claim Number:</span></td>
					<td><bean:write name="theform" property="incident_ID" /></td>
				
				</tr>
					
				<tr>
					<td class="label"><span>Date Received:</span></td>
					<td ><bean:write name="theform" property="dispcreatetime" /></td>	
				</tr>	
					
				<tr>
					<td class="label"><span>Claim Status:</span></td>
					<td align="left"><strong><bean:write name="theform" property="status.description" /></strong></td>
				</tr>		
					
					
					
					
			</table>
			<br /><br />
				
			<h3>Baggage Information</h3>
		
				
			<p /><table width="350" class="formtable">
		
		    <logic:iterate id="theitem" indexId="i" name="theform" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
		
		    <tr>
					<td width="125px"  class="label"><span>Bag Number:</span></td>
					<td width="225px"  class="field"><bean:write name="theitem" property="claimchecknum" /></td>
				</tr>
		
				<tr>
					<td class="label"><span class="label">Bag Status:</span></td>
					<td><strong><bean:write name="theitem" property="status.description" /></strong></td>
				</tr>
				<tr>
					<td colspan="2"><hr size="1px" width="275px"></td>
				</tr>
				</logic:iterate>
				<tr>
					<td class="label"><span>Deliver To:</span></td>
					<td>
					<logic:iterate id="bdo_passenger" name="theform" property="bdo_passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
					
		    			<bean:write name="bdo_passenger" property="address1"/>
		    			<br/>
		    			<bean:write name="bdo_passenger" property="address2"/>
						<br/>
		    			<bean:write name="bdo_passenger" property="city"/>
						<bean:write name="bdo_passenger" property="state_ID"/>
						<bean:write name="bdo_passenger" property="zip"/>
						<p />
					</logic:iterate>

					</td>
					
				</tr>	
										
				<tr>
					<td>&nbsp;</td><td><p><a href="passengerviewonly.do" target="_self">New Search</a></p></td>
				</tr>
		
			</table></p></td>
		
			<td>
		
			<div id="right">
				<div align="center"><center>Click on a status below<br>below for more information.</br></center><p /></div>
				<div class="tools" id="info" style="width: 200px !IMPORTANT;">
		    		<h4>Claim Status Definitions</h4>
		    		<ul>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Temporary</strong></a><div class="statClaimContent">The claim has been received but it has not yet been entered into the system for baggage searching.</div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Open</strong></a><div class="statClaimContent">The claim has been entered and has been submitted for baggage searching.</div></li>
		
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Pending</strong></a><div class="statClaimContent">The claim is awaiting approval for either delivery scheduling or expense reimbursement.</div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Closed</strong></a><div class="statClaimContent">The baggage has been located, delivery has been scheduled, and all outstanding items have been resolved.</div></li>
		    		</ul>
		    		<p>&nbsp;</p>
		    		<h4>Bag Status Definitions</h4>
		    		<ul>
		
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Open</strong></a><div class="statClaimContent">The claim is in process but the baggage has not yet been located.</div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Matched</strong></a><div class="statClaimContent">The baggage has been located.</div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>In Transit</strong></a><div class="statClaimContent">The baggage is currently en route to the specified destination/delivery city.</div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Process for Delivery</strong></a><div class="statClaimContent">The baggage has been received in the destination/delivery city and delivery will be scheduled.</div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>To Be Delivered</strong></a><div class="statClaimContent">The baggage has been scheduled for delivery and will arrive within approximately 4 hours.</div></li>
		
		    		</ul>
		    	</div>
		    </div>
		    
		</td></tr></table>
		<div>
		    <img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/poweredby_net_tracer.jpg"  alt="Powered by NetTracer" align="right"/>
		    </div>
		
		</div>
										
		</p>
		</div>
		</div>
		</td>
		
		<td>

		    
		</div>
		</td>
		
		</tr>
		
		</table>
		</div>
		
		</div>

</logic:present>
</td>
</tr>
</table>
<script type="text/javascript">

var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");

document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));

</script>

<script type="text/javascript">

var pageTracker = _gat._getTracker("UA-2715451-2");

pageTracker._initData();

pageTracker._trackPageview();

</script>
</body>
</html>