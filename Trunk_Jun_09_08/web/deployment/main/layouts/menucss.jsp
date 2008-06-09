
<%@ page import="java.util.Iterator,java.util.LinkedHashMap" %>
<%@ page import="com.bagnet.nettracer.tracing.db.Agent" %>

<% 
	LinkedHashMap menu_links = (LinkedHashMap)session.getAttribute("menu_links");
   int total_menu = 0;

%>


<style type="text/css">

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
	String key = null;
	int j = -1;
  if (menu_links != null && menu_links.size() > 0) {
  	total_menu = menu_links.size();
  	for (Iterator i=menu_links.keySet().iterator();i.hasNext();) {
      key = (String)i.next();
      j++;
      if (key.equals("On-hand Bag") || key.equals("Task Manager")) {
%>

			#menubuilder<%=j%> {
				width: 200px;
				z-index:100;
			}    
<%
			} else {
%>
			#menubuilder<%=j%> {
				width: 170px;
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


iframe {
	position: absolute;
	z-index: -1;
	filter: progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0);
}

</style>

