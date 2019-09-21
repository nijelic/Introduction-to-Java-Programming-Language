<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.jsp">

<title>App info</title>
</head>
<body>
<%
Long time = System.currentTimeMillis()- Long.valueOf((String)getServletContext().getAttribute("time"));
out.print((time/86400000)+" days  "+(time/3600000)%24+" h  "+(time/60000)%60+" min  "+(time/1000)%60+" s  "+time%1000+" ms"); %>
</body>
</html>