<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.jsp">
<title>Hi!</title>
</head>
<body >
<a href="colors.jsp">Background color chooser</a><br>
<a href="trigonometric?a=0&b=90">Trigonometric table: [0,90]</a><br>
<form action="trigonometric" method="GET">
 Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
 Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
 <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form><br>
<a href="stories/funny.jsp">Almost funny story.</a><br>
<a href="powers?a=1&b=100&n=3">Table of powers.</a><br>
<a href="appinfo.jsp">Time passed.</a>
</body>
</html>