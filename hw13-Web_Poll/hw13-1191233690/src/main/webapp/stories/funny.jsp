<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Random"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="../style.jsp">
<title>Almost funny stroy</title>
</head>
<body>
	<%
		Random rand = new Random();
	%>
	<p>
		<font color="rgb(<%=rand.nextInt(255)%>,<%=rand.nextInt(255)%>,<%=rand.nextInt(255)%>)">
		A journalist asked a programmer: - What makes code bad? - No comment.
		</font>
	</p>
</body>
</html>