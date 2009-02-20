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
<title><bean:message key="pv0" /></title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	
	<script type="text/javascript" src="<%=request.getContextPath()%>/deployment/main/js/protofx.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>
	
	
	<style type="text/css">
		body, td {
			font: 9pt Arial, Helvetica, sans-serif;
			color: #000000;
		}
		
		a {
			color: #006699;
			text-decoration: none;
			font-weight: bold;
			font: bold 10px/10px Arial, Helvetica, sans-serif;
		}
		
		a:hover {
			text-decoration: underline;
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
	padding: 22px 0 20px 20px;
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
			
			//accordion effect
			var contentFX = new fx.Accordion(toggleLinks, contentBlocks, { opacity: false });
		}
		function submitform() {

			o = document.searchform;
			o.submit();
		}
	</script>
	
</head>

<body onload="initStaticClaim()">
<form method="post" action="passengerviewonly.do" name="searchform">
<input type="hidden" name="locale" value="<%= session.getAttribute("currentLanguage")%>" />


<table cellspacing="0" width="600" border="0"> 
  <tr>
  	<td>
  	<img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/pvlogo.gif"/>
  	</td>
  	<td valign="top" align="right">
	<% if (session.getAttribute("currentLanguage").equals("es")) { %>
	<a href="#" onclick="document.forms[0].locale.value='en'; document.forms[0].submit();"><bean:message key="pven" /></a>
	<% } else {%>
	<a href="#" onclick="document.forms[0].locale.value='es'; document.forms[0].submit();"><bean:message key="pves" /></a>
	<% } %>
  	
  	</td>
  </tr>
  <tr> 
    <td id="topcell" colspan="2"> <!-- here -->
    
<bean:define id="theform" name="IncidentForm" scope="request" />

<logic:notPresent name="incident" scope="request">
		
		<div id="content" style="width 580px">
		
		<p /><h3><bean:message key="pv1" /></h3>
		<p><bean:message key="pv2" /></p>
		<logic:messagesPresent message="true"><p><font color=red><html:messages id="msg" message="true"><br/><bean:write name="msg"/><br/></html:messages></font></p></logic:messagesPresent>

		<div id="formbox" style="margin-top: 1em;  width: 575px;">
		
		<h3><bean:message key="pv3" /></h3>
			<table width="100%">
				<tr>
					<td class="label" width="15%"><span><bean:message key="pv4" />:</span></td>
					<td width="25%">
					<input type="text" name="name" size="20" maxlength="50" class="textfield" /></td><td width="2%"></td>
					<td width="48%" rowspan="3">
					
					<p><br /><font style= "font-size: 11px;"><bean:message key="pv5" /></font><br /><font style= "font-size: 11px; line-height: 25px;">ATLNK00000486	&nbsp;becomes&nbsp; 	ATLFL486</font></p>
		
					</td>
				</tr>	
				<tr>
					<td width="15%" class="label"><span><bean:message key="pv6" />:</span></td>
					<td width="25%">
					<input type="text" name="incident_ID" size="20" maxlength="50" onblur="fillzero(this,13);" />
					
					</td><td width="2%"></td>
				</tr>
								<tr>
		
					<td>&nbsp;</td><td><span class="button" style="width:100px; "><input type="submit" name="search" value="<bean:message key="pv6" />" align="absmiddle" /></span></td><td width="2%"></td>
				</tr>				
			</table>
		</div>
		<p />								
		 
		</div>
		
</logic:notPresent>

<logic:present name="incident" scope="request">
		<input type="hidden" name="name" value="<%=session.getAttribute("name") %>" />
		<input type="hidden" name="incident_ID" value="<%=session.getAttribute("incident") %>" />
		<input type="hidden" name="search" value="1" />
		<bean:message key="pv35" />: <bean:write name="theform" property="stationassigned.company.phone" />
		<table border = 0>
		<tr>
		<td>
		<div id="content">
		
		<p />								
													
		<style type="text/css">
			.borderOff { border-bottom:0px solid; border-left:0px solid; border-right:0px solid; border-top:0px solid; margin-top: 1px; background: none; background-color : transparent; 
		background-image: none; 
		}	
		</style>
		
		<div id="wrapper">
		<div id="formbox">
		<table width="550"><tr><td>
		
		<h3><bean:message key="pv8" /></h3>
		
			<p></p><table width="350" class="formtable">
			<% int z = 0; %>
			<logic:iterate id="passenger" name="theform" property="passengerlist" indexId="i" type="com.bagnet.nettracer.tracing.db.Passenger">
			<% if (z == 0) { %>
				<tr>
		
					<td width="125px" class="label"><span class="label"><bean:message key="pv9" />:</span></td>
					<td width="225px" >
					<bean:write name="passenger" property="firstname" /> 
					<bean:write name="passenger" property="middlename" /> 
					<bean:write name="passenger" property="lastname" />
					</td>
				
				</tr>
				<logic:present name="passenger" property="addresses">
		    <logic:iterate indexId="k" name="passenger" id="addresses" property="addresses" type="com.bagnet.nettracer.tracing.db.Address">
		                    
				<tr>
					<td class="label"><span class="label"><bean:message key="pv10" />:</span></td>
					<td><bean:write name="addresses" property="homephone"/></td>
					</tr>
				<tr>
		
					<td class="label"><span><bean:message key="pv11" />:</span></td>
					<td><bean:write name="addresses" property="workphone"/></td>
					
				</tr>
				<tr>
					<td class="label"><span><bean:message key="pv12" />:</span></td>
					<td><bean:write name="addresses" property="mobile"/></td>
					
				</tr>
				<tr>
		
					<td class="label"><span><bean:message key="pv13" />:</span></td>
					<td><bean:write name="addresses" property="hotel"/></td>
					
				</tr>
				<tr>	
					<td class="label"><span><bean:message key="pv14" />: </span></td>
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
					<td class="label"><span><bean:message key="pv15" />:</span></td>
					<td><bean:write name="theform" property="incident_ID" /></td>
				
				</tr>
					
				<tr>
					<td class="label"><span><bean:message key="pv16" />:</span></td>
					<td ><bean:write name="theform" property="dispcreatetime" /></td>	
				</tr>	
					
				<tr>
					<td class="label"><span><bean:message key="pv17" />:</span></td>
					<td align="left"><strong><bean:write name="theform" property="status.description" /></strong></td>
				</tr>		
					
					
					
					
			</table>
			<br /><br />
				
			<h3>Baggage Information</h3>
		
				
			<p /><table width="350" class="formtable">
		
		    <logic:iterate id="theitem" indexId="i" name="theform" property="itemlist" type="com.bagnet.nettracer.tracing.db.Item">
		
		    <tr>
					<td width="125px"  class="label"><span><bean:message key="pv18" />:</span></td>
					<td width="225px"  class="field"><bean:write name="theitem" property="claimchecknum" /></td>
				</tr>
		
				<tr>
					<td class="label"><span class="label"><bean:message key="pv19" />:</span></td>
					<td><strong><bean:write name="theitem" property="status.description" /></strong></td>
				</tr>
				<tr>
					<td colspan="2"><hr size="1px" width="275px" /></td>
				</tr>
				</logic:iterate>
				<tr>
					<td class="label"><span><bean:message key="pv20" />:</span></td>
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
					<td>&nbsp;</td><td><p><a href="#" onclick="document.forms[0].search.value=''; document.forms[0].submit();"><bean:message key="pv21" /></a></p></td>
				</tr>
		
			</table><p /></td>
		
			<td>
		
			<div id="right">
				<div align="center"><center><bean:message key="pv22" /></center><p /></div>
				<div class="tools" id="info" style="width: 200px !IMPORTANT;">
		    		<h4><bean:message key="pv23" /></h4>
		    		<ul>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Temporary</strong></a><div class="statClaimContent"><bean:message key="pv24" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Open</strong></a><div class="statClaimContent"><bean:message key="pv25" /></div></li>
		
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Pending</strong></a><div class="statClaimContent"><bean:message key="pv26" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Closed</strong></a><div class="statClaimContent"><bean:message key="pv27" /></div></li>
		    		</ul>
		    		<p>&nbsp;</p>
		    		<h4><bean:message key="pv28" /></h4>
		    		<ul>
		
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Open</strong></a><div class="statClaimContent"><bean:message key="pv29" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Matched</strong></a><div class="statClaimContent"><bean:message key="pv30" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>In Transit</strong></a><div class="statClaimContent"><bean:message key="pv31" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>Process for Delivery</strong></a><div class="statClaimContent"><bean:message key="pv32" /></div></li>
		    			<li><a href="javascript:void(0);" class="statClaimToggle"><strong>To Be Delivered</strong></a><div class="statClaimContent"><bean:message key="pv33" /></div></li>
		
		    		</ul>
		    	</div>
		    </div>
<img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/poweredby_net_tracer.jpg"  alt="<bean:message key="pv34" />" align="right"/>		    
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
		
		
		

</logic:present>
</td>
</tr>
</table>
</form>
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