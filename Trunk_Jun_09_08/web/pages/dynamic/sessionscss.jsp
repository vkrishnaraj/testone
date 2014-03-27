
<%@ page contentType="text/css" %>
<%@page import="com.bagnet.nettracer.tracing.utils.TracerProperties"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>

<% 
	LinkedHashMap menu_links = (LinkedHashMap)session.getAttribute("menu_links");
	Agent user = (Agent) session.getAttribute("user");
   int total_menu = 0;
   
   long expireTime = 0;
   if (menu_links != null && menu_links.size() > 0) {
		expireTime = new Date().getTime() + (90 * 60 * 1000);
   }
		SimpleDateFormat sdf = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String expireStr = sdf.format(new Date(expireTime));
		response.setHeader("Expires", expireStr);

%>



#menuList {
	position:relative;
	font-size:1em;
	z-index:100;
	margin: 0px;
	padding: 0px;
}

#mainMenu {
	position:relative;
	background: <%=session.getAttribute("first_row_bg")%>;
}

#menuList ul {
	z-index:100;
	margin: 0px;
	padding: 0px;
}

#menuList li {
	z-index:100;
	display:inline;
	list-style: none;
	margin: 0px;
	padding: 0px;
}

a.starter {
	z-index:100;
	margin: 0px;
	padding: 0px;
	color: <%=session.getAttribute("first_row_menu_text")%>;
}

a.starter:hover, a.starter:active, a.starter:focus {
	background-color: <%=session.getAttribute("drop_down_bg_hilite")%>;
	color: <%=session.getAttribute("drop_down_text_hilite")%>;
}



<%

	java.util.Locale myLocale = (java.util.Locale) session.getAttribute("org.apache.struts.action.LOCALE"); /*spa*/
	boolean spanish = "spa".equalsIgnoreCase(myLocale.getISO3Language());
	
	String key = null;
	int j = -1;
  if (menu_links != null && menu_links.size() > 0) {
  	total_menu = menu_links.size();
  	for (Iterator i=menu_links.keySet().iterator();i.hasNext();) {
      key = (String)i.next();
      j++;
      if (key.equals("On-hand Bag") || key.equals("Task Manager")
    		  || (spanish && (key.equals("Mishandled Bag") || key.equals("Damaged Bag") || key.equals("Unchecked property")))) {
%>

				#menubuilder<%=j%> {
					width: <%=TracerProperties.get(user.getCompanycode_ID(),"menu.width") %>px;
					z-index:100;
				}    
<%
			} else {
%>
				#menubuilder<%=j%> {
					width: 225px;
					z-index:100;
				}
<%
			}
		}
	}
%>

.navimenu {
	text-align: top;
	position: absolute;
	visibility: hidden;
	z-index: 100;
	line-height: 20px;
}
.navimenu li {
	width:100%;

}
.navimenu li a {
	background: <%=session.getAttribute("drop_down_bg")%>;
	z-index:100;
	color: <%=session.getAttribute("drop_down_text")%>;
	display: block;
	width:100%;
	font-family:Arial, Helvetica,sans-serif;
	font-size: 1em;
	padding-left: 5px;
	padding-right: 5px;
	text-decoration: none;
	margin-top:-2px;
}

.navimenu li a:hover, .navimenu li a:active, .navimenu li a:focus {
	background-color: <%=session.getAttribute("drop_down_bg_hilite")%>;
	color: <%=session.getAttribute("drop_down_text_hilite")%>;
}


.naviframe iframe {
	position: absolute;
	z-index: -1;
	filter: progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);
	}


<%
String ua = request.getHeader( "User-Agent" );
boolean isMSIE6 = ( ua != null && ua.indexOf( "MSIE 6" ) != -1 );
boolean isMSIE = ( ua != null && ua.indexOf( "MSIE" ) != -1 );
if (!isMSIE6 && isMSIE) {
%>



 body{
  margin:0;
  <%
  	if (session.getAttribute("bodyFontSize") == null) {
  		session.setAttribute("bodyFontSize", ".7em");
  	}
  %>
  font-size: <%=session.getAttribute("bodyFontSize") %>;
 }

 div#ntcontent{
  margin:0;
  padding:0 0 30px 0;
  }

  #footerL{
  float: right;
background-position:left top;
background-repeat:no-repeat;
background-image:url('../../deployment/main/images/left.png');
width: 8px;
height: 100%;
  
 }
 
 #footerC{
 float: right;
background-position:left top;
background-repeat:repeat-x;
background-image:url('../../deployment/main/images/center.png');
width: 734px;
height: 100%;
color: white;
text-align: right;
 }
 
 #footerC a:link{
 color: #FFF;
 text-decoration: none;
 }
 #footerC a:visited{
 color: #FFF;
 text-decoration: none;
 }
 #footerC a:active{
 color: #FFF;
 text-decoration: none;
 }

 #footerC a:hover {
 text-decoration: underline;
 }
 
 #footerR{
 float:right;
background-position:right top;
background-repeat:no-repeat;
background-image:url('../../deployment/main/images/right.png');
width: 8px;
 height: 100%;
 
 }




 div#footer{
  position:absolute;
  bottom:0;
  width:750px;
  margin-left: -375px;
  left: 50%;
  height:30px;
  background-color:gray;
  color:white;
 }
 @media screen{
  body>div#footer{
   position: fixed;
  }
 }
 * html body{
  overflow:hidden;
 } 
 * html div#ntcontent{
  height:100%;
  overflow:auto;
 }
 <% } else { %>


 body{
  margin:0;
  
  <%
  	if (session.getAttribute("bodyFontSize") == null) {
  		session.setAttribute("bodyFontSize", ".7em");
  	}
  %>
  font-size: <%=session.getAttribute("bodyFontSize") %>;
 }

 div#ntcontent{
  height: 100%;
  overflow:auto;
  position:relative;
 }
 <% /* 
 */ %>

 div#footer{
  position:absolute;
  bottom:0;
  width:750px;
  margin-left: -382px;
  left: 50%;
  height:30px;
  background-color:gray;
  color:white;
  }



 <% } %>

input.textfield,
input.disabledtextfield {
	height:15px;
	border:1px solid #569ECD;
	margin:2px 0px 1px 0px;
	padding: 0px;
	display:inline;
}




div#dialog-inner-content h1{
	margin-bottom: 5px;
	font-size: 1.3em;
	color:#004990;
}



.modaltable {
	white-space: nowrap;
	font-size: .9em;
	vertical-align: top;
	width: 97%;
}

tr.mh {
	vertical-align: top;
	background-color:#004990;
	color:#fff;
	font-color:#fff;
}


td.mh {
	vertical-align: top;
	background-color:#004990;
	color:#fff;
	font-color:#fff;
}

.modaltdG {
	vertical-align: top;
	background-color:#D1E4F1;
}
.modaltdW {
	vertical-align: top;
	background-color:#FFF;
}



.button {
		background-color: #1F43AE;
	border: 0px none;
	height: 20px;
	margin: 0px;
	display: inline;

	font-size:11px;
	font-weight:bold;
	color: #FFFFFF;
	cursor: hand;
	cursor: pointer;
}




#slideUpContainer {
left: 20px;
position: fixed;
width: 94% ;
}
 

#sliderInner {
background-color: #F0F0F0;
border: 1px solid #666666;
border-bottom-width: 1px;
padding: 20px 20px 20px 20px ;
height: 300px;
overflow: auto;
}

p.warning {
	color: #ff0000;
	text-align: center;	
}

td#printreceipt a {
	background-position:top;
	color: #2758AF;
	font-size:1.0em;
	font-weight:bold;
	text-align:center;
	text-decoration:none;
	padding:0px 2px;
}

td#printreceipt a:hover,
td#printreceipt a:active {
	text-decoration:underline;
	color:#166AAE;
}

a img.printreceipt {
	text-decoration:none;
}

.no_fraud {
	margin: 0px;
	padding: 0px;
	background-color: #4ba047;
}

.suspected_fraud {
	margin:	0px;
	padding: 0px;
	background-color: #FEFF82;
}

.likely_fraud {
	margin:	0px;
	padding: 0px;
	background-color: #e79d63;
}


.known_fraud {
	margin:	0px;
	padding: 0px;
	background-color: #FE8080;
}

td#printreceipt {
	margin:10px;
	padding:0px;	
}

h2#fraudlink {
	margin: 10px;
	padding: 0px;
	font-size: 1.2em;	
}
