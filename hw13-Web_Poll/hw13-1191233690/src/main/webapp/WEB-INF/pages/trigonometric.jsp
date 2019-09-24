<%@ page language="java" contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ page import="java.lang.Math, java.lang.Integer"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.jsp">
<title>Trigonometric table</title>
</head>
<body>
	<table>
		<tr>
			<th>Degrees</th>
			<th>Sin</th>
			<th>Cos</th>
		</tr>
		<%
			int a = (Integer) request.getAttribute("a");
			int b = (Integer) request.getAttribute("b");
			while (a <= b) {
		%>
		<tr>
			<td>
				<%
					out.print(a);
				%>
			</td>
			<td>
				<%
					out.print(Math.sin(a / 180.0 * Math.PI));
				%>
			</td>
			<td>
				<%
					out.print(Math.cos(a / 180.0 * Math.PI));
				%>
			</td>
		</tr>

		<%
			a++;
		}
		%>
	</table>
</body>
</html>