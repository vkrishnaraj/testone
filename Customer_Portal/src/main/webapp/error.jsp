<html>
<body>
	<% String referer = request.getHeader("Referer"); 
	int index = -1;
	if (referer == null || !referer.matches("^.*/customer-portal/.*\\.do$")) {
		index = referer.indexOf("/customer-portal/");
		referer = "NO";
	} else {
		index = referer.lastIndexOf("/customer-portal/");
		referer = referer.substring((referer.lastIndexOf("/customer-portal/")));
	}
	session.setAttribute("prev", referer);
	response.sendRedirect("error.do"); 
	%>
</body>
</html>
