<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/tags/struts-nested" prefix="nested" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.DateUtils" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.TracerDateTime" %>
<%@ page import="com.bagnet.nettracer.tracing.utils.AdminUtils" %>

<%
response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
response.addDateHeader("Expires", -1);
%>

<% LinkedHashMap menu_links = (LinkedHashMap)session.getAttribute("menu_links");
   Agent agent = (Agent)session.getAttribute("user");
   int total_menu = 0;
 
   String forwardURI = (String) request.getAttribute(org.apache.catalina.Globals.FORWARD_REQUEST_URI_ATTR);
   
   if (forwardURI != null && forwardURI.contains("/WebHelp/")) {
  	 response.sendRedirect("/tracer/pages/WebHelp/welcome_to_nettracer.htm");
   } else {

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%
if(!TracerProperties.isTrue(TracerProperties.ALLOW_CACHING)) {
response.addHeader("Pragma", "No-cache");
response.addHeader("Cache-Control", "no-cache");
response.addDateHeader("Expires", -1);
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<title>NetTracer</title>


<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/designScripts.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/date.js"></script>
<logic:present name="user" scope="session">
<style language="text/css">
#menuList, #menuList ul {
	float: left;
	width: 36em;
	list-style: none;
	line-height: 1;
	background: white;
	font-weight: bold;
	padding: 0;
	border: solid #eda;
	border-width: 1px 0;
	margin: 0 0 1em 0;
}
 
#menuList a {
	display: block;
	width: 10em;
	w\idth: 6em;
	color: #7C6240;
	text-decoration: none;
	padding: 0.25em 2em;
}
 
#menuList a.daddy {
	background: url(rightarrow2.gif) center right no-repeat;
}
 
#menuList li {
	float: left;
	padding: 0;
	width: 10em;
}
 
#menuList li ul {
	position: absolute;
	left: -999em;
	height: auto;
	width: 14.4em;
	w\idth: 13.9em;
	font-weight: normal;
	border-width: 0.25em;
	margin: 0;
}
 
#menuList li li {
	padding-right: 1em;
	width: 13em
}
 
#menuList li ul a {
	width: 13em;
	w\idth: 9em;
}
 
#menuList li ul ul {
	margin: -1.75em 0 0 14em;
}
 
#menuList li:hover ul ul, #menuList li:hover ul ul ul, #menuList li.sfhover ul ul, #menuList li.sfhover ul ul ul {
	left: -999em;
}
 
#menuList li:hover ul, #menuList li li:hover ul, #menuList li li li:hover ul, #menuList li.sfhover ul, #menuList li li.sfhover ul, #menuList li li li.sfhover ul {
	left: auto;
}
 
#menuList li:hover, #menuList li.sfhover {
	background: #eda;
}
</style>
<script type="text/javascript">
sfHover = function() {
	var sfEls = document.getElementById("menuList").getElementsByTagName("LI");
	for (var i=0; i<sfEls.length; i++) {
		sfEls[i].onmouseover=function() {
			this.className+=" sfhover";
		}
		sfEls[i].onmouseout=function() {
			this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
		}
	}
}
if (window.attachEvent) window.attachEvent("onload", sfHover);
 </script> 
</logic:present>
<link href="<%=request.getContextPath()%>/deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/deployment/main/css/formstyles.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/deployment/main/css/styles.css" rel="stylesheet" type="text/css">


<logic:present name="user" scope="session">
<jsp:include page="menucss.jsp"/>
</logic:present>

</head>

<%  
if (request.getAttribute("lostdelay") != null || request.getAttribute("missing") != null || request.getAttribute("damaged") != null
			|| request.getAttribute("lost") != null || request.getAttribute("found") != null || request.getAttribute("onhand") != null ) { 
%>
<body onkeydown="if (event.keyCode==13) {event.keyCode=9; return event.keyCode }">
<% } else { %>
<body>
<% } %>

<logic:notPresent name="user" scope="session">


<table cellspacing="0" id="bodytable"> 
  <tr> 
    <td id="topcell">
  	<table cellspacing="0" id="toplayouttable"> 
        <tr> 
          <td id="header" class="cssstandardthreecolumn">
        	<div id="headercontent">      </div>
      		</td> 
        </tr> 

        <tr> 
          <td colspan="3" id="headermenucell">
        
      		</td>
        </tr>
     </table>

 	</td>
  <td id="middlecolumn"> 
	<div id="maincontent">

	<h1 class="green">Welcome to NetTracer&#8482;</h1>
	


  <%
  if (request.getAttribute("errortype") != null) {
	%>
  		<jsp:include page="/pages/customerrpages/error.jsp" />
	<%
	} else {
	%>

<script language="javascript">

    function validateLogonForm(form) {
      var usernameElement = document.getElementsByName("username")[0];                          
      var passwordElement = document.getElementsByName("password")[0];
      if (usernameElement.value.length <= 0) {
        alert('<bean:message key="prompt.username" /> <bean:message key="error.validation.isRequired" />');
        usernameElement.focus();
        return false;
      } else if (passwordElement.value.length <= 0) {
        passwordElement.focus('<bean:message key="prompt.password" /> <bean:message key="error.validation.isRequired" />');
        
        return false;
      } else {
        return true;
      }
    } 

</script>



  <div id="mainlogin">

    <tiles:insert attribute="center"/>
                
              </div>
	<% 
	} 
	%>

        
<p>&nbsp;</p>
<img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/poweredby_net_tracer.jpg"  alt="Powered by Net Tracer" class="imgAlignBottom">

</div>

</logic:notPresent>




<logic:present name="user" scope="session">



<table cellspacing="0" id="bodytable"> 
  <tr> 
    <td id="topcell">
  <table cellspacing="0" id="toplayouttable"> 
        <tr> 
          <td id="header" class="cssstandardthreecolumn">
        <div id="headercontent">
        <table id="headercontent">
          <tr>
            <td><bean:message key="Logged_as"/>:</td>
            <td><bean:write name="user" property="username" scope="session"/></td>
          </tr>
          <tr>
            <td colspan="2"><bean:message key="Company"/>:<bean:write name="user" property="companycode_ID" scope="session"/>
            &nbsp;<bean:message key="station"/>:<bean:write name="user" property="station.stationcode" scope="session"/></td>
          </tr>
          <tr>
            <td colspan="2">
            <%=DateUtils.formatDate(TracerDateTime.getGMTDate(), agent.getDateformat().getFormat() + " " + agent.getTimeformat().getFormat(), null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(agent.getDefaulttimezone())
				.getTimezone()))%>
            
            </td>
          </tr>
          <tr>
            <td colspan="2"><a href="logoff.do">[ <bean:message key="logout"/> ]</a></td>
          </tr>
        </table>
        </div>
      </td> 
        </tr> 


<tr>
  <td colspan="3" id="headermenucell">  


        <table border="0" cellspacing="0" cellpadding="0" id="headermenu"> 
    
          <tr> 
            <td align="left">

<div id="mainMenu">
	<ul id="menuList">
<li><a href="#">Gobioidei</a> 
		<ul> 
			<li><a href="#">Burrowing gobies</a></li> 
			<li><a href="#">Dartfishes</a></li> 
			<li><a href="#">Eellike gobies</a></li> 
			<li><a href="#">Gobies</a></li> 
			<li><a href="#">Loach gobies</a></li> 
			<li><a href="#">Odontobutidae</a></li> 
			<li><a href="#">Sandfishes</a></li> 
			<li><a href="#">Schindleriidae</a></li> 
			<li><a href="#">Sleepers</a></li> 
			<li><a href="#">Xenisthmidae</a></li> 
		</ul> 
	</li> 
	<li><a href="#">Foobar</a> 
		<ul> 
			<li><a href="#">Burrowing gobies</a></li> 
			<li><a href="#">Dartfishes</a></li> 
			<li><a href="#">Eellike gobies</a></li> 
			<li><a href="#">Gobies</a></li> 
			<li><a href="#">Loach gobies</a></li> 
			<li><a href="#">Odontobutidae</a></li> 
			<li><a href="#">Sandfishes</a></li> 
			<li><a href="#">Schindleriidae</a></li> 
			<li><a href="#">Sleepers</a></li> 
			<li><a href="#">Xenisthmidae</a></li> 
		</ul> 
	</li> 

  </ul>
</div>

   
   
        


      </td> 
          </tr> 
       </table>
      </td>
  </tr> 


  <%
  
  if (request.getParameter("errortype") != null) {
  %>
  	<jsp:include page="/pages/customerrpages/error.jsp" />
  <%
  } else {
  %>
	  
  	<tiles:insert attribute="center"/>

  <% 
  } 
  %>

  
</logic:present>
       
      </td> 

        </tr> 
      </table>
  </td> 
  </tr> 
  <tr> 
    <td id="bottomcell">
      <table width="100%" border="0">
      <tr><td><div id="copyright">
      <bean:message key="copyright.line1"/><br/>
    <bean:message key="copyright.line2"/></div></td> 
      <td align="right" width="1">
            <bean:message key="footer.current_version"/>-<%= TracerProperties.getInstanceLabel() %>
      </td>
      </tr>
      </table>
    </td>
  </tr> 
</table>

<logic:present name="user" scope="session">
<DIV ID="calendardiv" STYLE="position:absolute;visibility:hidden;background-color:white;layer-background-color:white; z-index:100"></DIV>

<iframe id="DivShim" src="javascript:false" scrolling="no" frameborder="0" style="position:absolute; z-index:-1; top:0px; left:0px; display:none;"></iframe>

<script language="javascript">
function hasurlfocus(col) {

	
	var r = document.body.createControlRange();
	var x = document.getElementById("menucol_" + col + ".0");
	
	
	x.contentEditable = 'true';
	r.addElement(x);
	x.contentEditable = 'false';
	r.select();
	

}

function nourlfocus(col) {
	var x = document.getElementById("menucol_" + col + ".0");
	x.focus();

}

function goleft() {
	var currentFocus = document.activeElement.id;

	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf("_");
		var index2 = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1,index2);
			num = num - 1;
			
			if (num == 0) {
				var x = document.getElementById("menucol_0.0");
				x.focus();
				return;
			}
			if (num < 0) num = <%=total_menu%> - 1;

			var x = document.getElementById("menucol_" + num + ".0");
	
			x.focus();
			

		}
		
	}
	return false;

}

function goright() {
	var currentFocus = document.activeElement.id;
	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf("_");
		var index2 = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1,index2);
			num++;

			if (num > (<%=total_menu%> - 1)) {
				var x = document.getElementById("menucol_0.0");
				x.focus();
				return;
			}
			
			var x = document.getElementById("menucol_" + num + ".0");
			x.focus();

		}
		
	}
	return false;
}

function goup() {
	var currentFocus = document.activeElement.id;
	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1);
			num=num-1;
			if (num<0) {
				num = 0;
				for (i=20;i>0;i=i-1) {
					if (document.getElementById(currentFocus.substring(0,index+1) + i) != null) {
						num = i;
						break;
					}
				}
			}
			
			var r = document.body.createControlRange();
			var x = document.getElementById(currentFocus.substring(0,index+1) + num);
			x.focus();
	
		}
		
	}
	return false;
}

function godown() {
	var currentFocus = document.activeElement.id;
	if (currentFocus != null && currentFocus.length > 0) {
		
		var index = currentFocus.indexOf(".");

		var num;
		if (index > 0 && currentFocus.indexOf("menucol")>=0) {
			num = currentFocus.substring(index+1);
			num++;
			if (document.getElementById(currentFocus.substring(0,index+1) + num) == null) {
				num = 0;
			}
			
			var r = document.body.createControlRange();
			var x = document.getElementById(currentFocus.substring(0,index+1) + num);
			x.focus();
	
		}
		
	}
	return false;
}


document.onkeydown = function(){
	var currentFocus = document.activeElement.id;

	if (window.event && window.event.keyCode == 117 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/lostDelay.do?express=1";
		return;
	}

	if (window.event && window.event.keyCode == 118 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/damaged.do?express=1";
		return;
	}

	if (window.event && window.event.keyCode == 119 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/missing.do?express=1";
		return;
	}	

	if (window.event && window.event.keyCode == 120 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/expressOnHandBag.do?express=1";
		return;
	}	

	if (window.event && window.event.keyCode == 121 ) {
		window.event.keyCode = 505;
		document.location.href="<%=request.getContextPath()%>/logon.do?taskmanager=1";
		return;
	}		


	if (window.event && window.event.keyCode == 123 ) {
		window.event.keyCode = 505;
		openHelp('pages/WebHelp/nettracerhelp.htm');
		return;
	}		
	

	if (window.event && window.event.keyCode == 77 && window.event.ctrlKey) {
		hasurlfocus(0);
	}
    
    if (window.event && window.event.keyCode == 78 && window.event.ctrlKey) {
      return false;
    }

	if (window.event && window.event.keyCode == 69 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(1);
	}

 	if (window.event && window.event.keyCode == 71 && window.event.ctrlKey) {
 		window.event.keyCode = 505;
		nourlfocus(2);
	}

	if (window.event && window.event.keyCode == 73 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(3);
	}

	if (window.event && window.event.keyCode == 79 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(4);
	}

	if (window.event && window.event.keyCode == 85 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(5);
	}

	if (window.event && window.event.keyCode == 83 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(6);
	}

	if (window.event && window.event.keyCode == 84 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(6);
	}

	if (window.event && window.event.keyCode == 82 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(7);
	}

	if (window.event && window.event.keyCode == 68 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(8);
	}

	if (window.event && window.event.keyCode == 76 && window.event.ctrlKey) {
		window.event.keyCode = 505;
		nourlfocus(9);
	}

	if(window.event && window.event.keyCode == 37 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		goleft();
	}
	

	if(window.event && window.event.keyCode == 39 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		goright();
	}
	

	if(window.event && window.event.keyCode == 38 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		goup();
	}	

	if(window.event && window.event.keyCode == 40 && currentFocus.indexOf("menucol") >= 0) { 
		window.event.keyCode = 505;
		godown();
	}
	

	if (window.event && window.event.keyCode == 27) {
		var root = document.getElementById("menuList");
		hideAllMenus(root,root);
	}



	if(window.event && window.event.keyCode == 505) { 
		return false; 
	}
}

</script>
</logic:present>
<jsp:include page="/pages/includes/analytics.jsp" />
</body>
</html>
<% } %>