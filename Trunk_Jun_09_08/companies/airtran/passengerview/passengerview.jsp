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
<title>NetTracerSearch</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	
	
		<link href="http://www.airtran.com/Common/scripts/at_low.css" rel="stylesheet" media="screen" type="text/css" />
		<style type="text/css" media="screen,print">@import url(http://www.airtran.com/Common/scripts/at_main.css);</style>
		
		<link href="http://www.airtran.com/Common/scripts/at_textregular.css" rel="stylesheet" media="screen,projection" type="text/css" />
		<link href="http://www.airtran.com/Common/scripts/at_textlarge.css" title="Larger text" rel="alternate stylesheet" media="screen,projection" type="text/css" />

		<link href="http://www.airtran.com/Common/scripts/at_print.css" rel="stylesheet" media="print" type="text/css" />
	
	
	<script type="text/javascript" src="http://www.airtran.com/Common/scripts/at_global.js"></script>
	<script type="text/javascript" src="http://tickets.airtran.com/Common/scripts/protofx.js"></script>
	
	<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer_load.js"></script>
	
	
	<style type="text/css">
		.statClaimContent 
		{
			font-size: 11px;
			height: 0;
			overflow: hidden;
			margin-top: 4px;
			margin-bottom: -4px;
			width: 180px;
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

<BODY onload="initStaticClaim()" style="BORDER-RIGHT: #f5f5f5 4px solid; BORDER-TOP: #f5f5f5 4px solid; BACKGROUND: url(http://www.airtran.com/Common/images/content_back.gif) repeat-y; BORDER-LEFT: #f5f5f5 4px solid; BORDER-BOTTOM: #f5f5f5 4px solid; HEIGHT: 3000px; TEXT-ALIGN: left" leftMargin=0 topMargin=0>



<img src="http://www.airtran.com/Common/images/logo_print.gif" width="215" height="64" id="plogo" />


<table cellspacing="0" id="bodytable"> 
  <tr> 
    <td id="topcell">
    
<bean:define id="theform" name="IncidentForm" scope="request" />


<logic:notPresent name="incident" scope="request">
		
		<form method="post" action="passengerviewonly.do" name="searchform">
		
		<div id="content" style="WIDTH: 525px">
		
		
		
		
		<p><h3>Delayed Baggage</h3>
		<p>If you have notified an AirTran Airways Crew Member of your delayed baggage, enter your claim number below to check the current status of your baggage.</p>
		
		
		<logic:messagesPresent message="true"><p><font color=red><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></font></p></logic:messagesPresent>
		
		
		
		<div id="formbox" style="margin-top: 1em;  width: 525px;">
		
		<H3>Check Baggage Status</H3>
			<table width="100%">
				<tr>
					<td class="label" width="15%"><span>Last Name:</span></td>
					<td width="25%">
					<input type="text" name="name" size="20" maxlength="50" class="textfield"></td><td width="2%"></td>
					<td width="48%" rowspan="3">
					
					<p><br /><font style= "font-size: 11px;"><b>Claim number</b> &#150; You may omit leading zeros when entering your claim number. For example:</font><br /><font style= "font-size: 11px; line-height: 25px;">ATLFL00000486	&nbsp;becomes&nbsp; 	ATLFL486</font></p>
		
					</td>
				</tr>	
				<tr>
					<td width="15%" class="label"><span>Claim Number:</span></td>
					<td width="25%">
					<input type="text" name="incident_ID" size="20" maxlength="50" onblur="fillzero(this,13);">
					
					</td><td width="2%"></td>
				</tr>
				
				
				
				
				<tr>
		
					<td>&nbsp;</td><td><span class="button" style="width:100px; "><span><input type="submit" name="search" value="&nbsp;&nbsp;&nbsp;Search" align="absmiddle"></span></span><td width="2%"></td>
				</tr>				
			</table>
		</div>
		<p class="clear">If you have not already notified us of your delayed baggage, please contact our System Baggage Service office Monday through Friday from 7:30am to 7:30pm.<br /><br />
		
		System Baggage Service 1-800-965-2107 x8900
		</p>								
		 </p>
		</div>
		</form>
</logic:notPresent>

<logic:present name="incident" scope="request">
		
		
		
		
		
		
		
		<table summary="">
		<tr>
		<td>
		<div id="content" style="WIDTH: 525px">
		
		
		
		
		
		
		
		<p>								
													
		<style type="text/css">
			.borderOff { border-bottom:0px solid; border-left:0px solid; border-right:0px solid; border-top:0px solid; margin-top: 1px; background: none; background-color : transparent; 
		background-image: none; 
		}	
		</style>
		 
											
		 	
		
		<div id="wrapper">
		<table width="520"><tr><td>
		
		<h3>Baggage Tracking Summary</h3>
			<p><table width="340" class="formtable">
			<% int z = 0; %>
			<logic:iterate id="passenger" name="theform" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.Passenger">
			<% if (z == 0) { %>
				<tr>
		
					<td width="115px" class="label"><span>Passenger Name:</span></td>
					<td width="225px"  class="field">
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
					<td class="field"><bean:write name="addresses" property="hotel"/></td>
					
				</tr>
				<tr>	
					<td class="label"><span>Email: </span></td>
					<td><html:text name="addresses" property="email" size="25" maxlength="100" styleClass="borderOff" indexed="true" readonly="true" /></td>
					
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
					<td class="field"><html:text name="theform" property="incident_ID" size="20" styleClass="borderOff" readonly="true" /></td>
				
				</tr>
					
				<tr>
					<td class="label"><span>Date Received:</span></td>
					<td class="field"><html:text name="theform" property="dispcreatetime" size="20" styleClass="borderOff" readonly="true" /></td>	
				</tr>	
					
				<tr>
					<td class="label"><span>Claim Status:</span></td>
					<td class="field"><html:text name="theform" property="status.description" size="20" styleClass="borderOff" readonly="true" /></td>
					
				</tr>		
					
					
					
					
			</table>
			<br /><br />
				
			<h3>Baggage Information</h3>
		
				
			<p><table width="340" class="formtable">
		
		    <logic:iterate id="theitem" indexId="i" name="theform" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
		
		    <tr>
					<td width="115px"  class="label"><span>Bag Number:</span></td>
					<td width="225px"  class="field"><html:text name="theitem" property="claimchecknum" size="20" maxlength="20" styleClass="borderOff" indexed="true" readonly="true" /></td>
				</tr>
		
				<tr>
					<td class="label"><span class="label">Bag Status:</span></td>
					<td><html:text name="theitem" property="status.description" size="25" maxlength="25" styleClass="borderOff" indexed="true" readonly="true" /></td>
		
				</tr>
				</logic:iterate>
				<tr>
					<td class="label"><span>Deliver To:</span></td>
					<td>
					<logic:iterate id="bdo_passenger" name="theform" property="bdo_passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.BDO_Passenger">
					
		    			<html:text name="bdo_passenger" property="address1" size="17" maxlength="50" styleClass="borderOff" indexed="true" readonly="true" />
		    			<br/>
		    			<html:text name="bdo_passenger" property="address2" size="17" maxlength="50" styleClass="borderOff" indexed="true" readonly="true" />
						<br/>
		    			<bean:write name="bdo_passenger" property="city"/>
						<bean:write name="bdo_passenger" property="state_ID"/>
						<bean:write name="bdo_passenger" property="zip"/>
						<p>
					</logic:iterate>

					</td>
					
				</tr>	
				
				
				
										
				<tr>
					<td>&nbsp;</td><td><p><span class="button" style="width:100px; "><span><a href="passengerviewonly.do" target="_self">new search</a></span></span></span></p></td>
				</tr>
		
			</table></p></td>
		
			<td>
		
			<div id="right">
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

</logic:present>


<script LANGUAGE="javascript" SRC="http://www.airtran.com/Common/HitBox/environment.js"></script>
<script LANGUAGE="javascript" SRC="http://www.airtran.com/Common/HitBox/hbx_page_code_redesign.js"></script>
<script LANGUAGE="javascript" defer="defer" SRC="http://www.airtran.com/Common/HitBox/hbx.js"></script>

</body>
</html>