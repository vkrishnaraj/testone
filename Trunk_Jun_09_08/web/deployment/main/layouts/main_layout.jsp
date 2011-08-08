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

<% LinkedHashMap menu_links = (LinkedHashMap)session.getAttribute("menu_links");
   Agent agent = (Agent)session.getAttribute("user");
   int total_menu = 0;
   String ua = request.getHeader( "User-Agent" );
   boolean isMSIE6 = ( ua != null && ua.indexOf( "MSIE 6" ) != -1 );
   boolean isMSIE = ( ua != null && ua.indexOf( "MSIE" ) != -1 );
 
   java.util.Locale                                myLocale   = (java.util.Locale)session.getAttribute(
   "org.apache.struts.action.LOCALE");
   
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


<%@page import="com.bagnet.nettracer.tracing.utils.UserPermissions"%>
<%@page import="com.bagnet.nettracer.tracing.constant.TracingConstants"%><html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>NetTracer</title>

<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/designScripts.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/date.js"></script>


<logic:present name="user" scope="session">

<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer_menu.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/jquery-1.3.2.min.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/jquery.form.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/jquery.bgiframe.min.js"></script>
<script language="javascript" SRC="<%=request.getContextPath()%>/deployment/main/js/date.js"></SCRIPT>
<script language="javascript" SRC="<%=request.getContextPath()%>/deployment/main/js/field_validation.js"></SCRIPT>


<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/AnchorPosition.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/PopupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="deployment/main/js/popcalendar.js"></SCRIPT>

<script language="javascript" src="<%=request.getContextPath()%>/pages/dynamic/sessionjs.jsp?<%=session.getId() %><%=agent.getUsername() %><%=myLocale.getDisplayLanguage() %>"></script>
<link href="<%=request.getContextPath()%>/deployment/main/css/jquery-ui-1.7.2.custom.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/pages/dynamic/sessionscss.jsp?<%=session.getId() %><%=agent.getUsername() %><%=myLocale.getDisplayLanguage() %>" rel="stylesheet" type="text/css" />


<!--[if lt IE 7]>
<script language="javascript" src="<%=request.getContextPath()%>/deployment/main/js/nettracer_menu2.js"></script>
</script>
<![endif]-->
</logic:present>
<style type="text/css">
 body{
  <%
  	if (session.getAttribute("bodyFontSize") == null) {
  		session.setAttribute("bodyFontSize", ".7em");
  	}
  %>
  font-size: <%=session.getAttribute("bodyFontSize") %>;
 }
</style>
<link href="<%=request.getContextPath()%>/deployment/main/css/nettracerstyles1.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/deployment/main/css/formstyles.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/deployment/main/css/styles.css" rel="stylesheet" type="text/css" />
  


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
<!-- <img src="<%=request.getContextPath()%>/deployment/main/images/nettracer/poweredby_net_tracer.jpg"  alt="Powered by Net Tracer" class="imgAlignBottom">
 -->
 <div class="imgAlignBottom">&#160;</div>


</logic:notPresent>

<logic:present name="user" scope="session">


<table cellspacing="0" id="bodytable"> 
  <tr> 
    <td id="topcell">
  <table cellspacing="0" id="toplayouttable"> 
        <tr> 
		<td id="header" class="cssstandardthreecolumn">
		  <div id="headercontent" style="float:right">
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
<%
	
  int index = -1;
	int index2 = -1;
	String key = null;
	String key2 = null;
  if (menu_links != null && menu_links.size() > 0) {
  	total_menu = menu_links.size();
    for (Iterator i=menu_links.keySet().iterator();i.hasNext();) {
      key = (String)i.next();
			key2 = key.replaceAll(" ", "_");
			index++;
      
      LinkedHashMap nextMenu = (LinkedHashMap)menu_links.get(key);					

%>
<li class="menubar">
<%
	if (key.indexOf("Task Manager") != -1) {
%>
	<a id="menucol_<%=index%>.0" href="logon.do?taskmanager=1" class="starter"><bean:message key="<%=key2%>" arg0="<u>" arg1="</u>"/></a>
<%
	} else {
%>
	<a id="menucol_<%=index%>.0" href="#" class="starter"><bean:message key="<%=key2%>" arg0="<u>" arg1="</u>"/></a>
  
<%
  }    
			if (nextMenu != null && nextMenu.size() > 0) {
				index2 = 0;
%>
				<ul id="menubuilder<%=index%>" class="navimenu" >
<%
				for (Iterator j=nextMenu.keySet().iterator();j.hasNext();) {
					key2 = (String)j.next();
          String val2 = (String)nextMenu.get(key2);
          
        	key2 = key2.replaceAll(" ", "_");
        	index2++;

%>
					<li><a id="menucol_<%=index%>.<%=index2%>" href="<%=val2%>"><bean:message key="<%=key2%>"/></a></li>
<%

				}
%>
				</ul> 
					
<%
			}
%>
</li> 

<%
			if (i.hasNext()) {
%> 
&nbsp;|&nbsp; 
<%
			}
		}
	}
%>


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
    <bean:message key="copyright.line2"/></div><div id="dialog"><div id="dialog-inner-content"></div></div></td> 
      <td align="right" width="1">
            <bean:message key="footer.current_version"/>-<%= TracerProperties.getInstanceLabel() %>
      </td>
      </tr>
      </table>
    </td>
  </tr> 
</table>
<div id="calendardiv" STYLE="position:absolute;visibility:hidden;background-color:white;layer-background-color:white; z-index:100"></div>

<logic:present name="user" scope="session">

<script language="javascript">

jQuery(document).ready(function () {
	jQuery("#closeLink").click(function(event) {handleEvent(event)});
	jQuery("#openLink").click(function(event) {handleEvent(event)});
	jQuery("#switchLink").click(function(event) {switchLocation(event)});
	});

</script>

<div id="slideUpContainer">
	<div id="sliderInner">
	<div style="float: right; margin-right: 10px;">
	<!-- 	<a id="switchLink" href="#">Switch Location</a>&nbsp;&nbsp; -->
		<a id="closeLink" href="#">Close</a>
	</div><br/>
	<div id="sliderContentFrame" style="float: none"></div>
</div>
</div>

<html:form action="quickSearch.do" method="post"><input id="quickSearchQuery2" type="hidden" name="quickSearch" value=""/></html:form>
<iframe id="DivShim" src="javascript:false" scrolling="no" frameborder="0" style="position:absolute; z-index:-1; top:0px; left:0px; display:none;"></iframe>
</logic:present>
</body>
</html>
<% } %>


