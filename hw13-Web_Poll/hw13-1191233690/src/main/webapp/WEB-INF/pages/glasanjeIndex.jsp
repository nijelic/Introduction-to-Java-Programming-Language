<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.jsp">
<title>Glasovanje</title>
</head>
<body>
	<h1>Glasanje za omiljenog autora:</h1>
	<p>Od sljedećih autora, koji Vam je autor najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<%
			Integer length = (Integer) request.getAttribute("length");
			for (Integer i = 0; i < length; i++) {
		%>
		<li>
			<a href="glasanje-glasaj?id=<%=(String) request.getAttribute(i.toString() + "ID")%>">
				<%=(String) request.getAttribute(i.toString() + "Author")%>
			</a>
		</li>
		<%
			}
		%>
	</ol>
</body>
</html>