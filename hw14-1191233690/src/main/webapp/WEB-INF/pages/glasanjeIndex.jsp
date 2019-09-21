<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page
	import="java.util.List, hr.fer.zemris.java.p12.utility.Poll, hr.fer.zemris.java.p12.utility.PollOption"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.jsp">
<title>Vote now</title>
</head>
<body>
	<%
		if (request.getAttribute("poll") != null) {
	%><h1>
		<%
			out.print(((Poll) request.getAttribute("poll")).getTitle());
		%>
	</h1>
	<%
		
	%><p>
		<%
			out.print(((Poll) request.getAttribute("poll")).getMessage());
		%>
	</p>
	<%
		} else {
	%>
	<h1>Vote now!</h1>
	<ol>
		<%
			}
			@SuppressWarnings("unchecked")
			List<PollOption> options = (List<PollOption>) request.getAttribute("options");
			for (PollOption o : options) {
		%>
		<li><a href="glasanje-glasaj?id=<%out.print(o.getId());%>"> <%
 	out.write(o.getTitle());
 %>
		</a></li>
		<%
			}
		%>
	</ol>
</body>
</html>