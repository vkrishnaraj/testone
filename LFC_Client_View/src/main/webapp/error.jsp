<html>
<body>
	<% String referer = request.getHeader("Referer"); 
	int index = -1;
	if (referer == null || !referer.matches("^.*/client/.*\\.do$")) {
		index = referer.indexOf("/client/");
		referer = "NO";
	} else {
		index = referer.lastIndexOf("/client/");
		referer = referer.substring((referer.lastIndexOf("/client/")));
	}
	session.setAttribute("prev", referer);
	response.sendRedirect("error.do"); 
	%>
</body>
</html>
