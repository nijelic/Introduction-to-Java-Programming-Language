<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, hr.fer.zemris.java.p12.utility.Poll"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pick poll</title>
</head>
<body>
<%
@SuppressWarnings("unchecked")
List<Poll> polls = (List<Poll>)request.getAttribute("polls"); 
%>

<h1>Pick poll</h1><br>
<p>
<%
if(request.getAttribute("error")!=null){
	out.print(request.getAttribute("error"));
	}
%>
</p><br>
<ol>
<%
for(Poll p:polls) {
%>
<li><a href="glasanje?pollID=
<%
out.print(p.getPollID());
%>
">
<%
out.write(p.getMessage());
%>
</a></li>
<%
}
%>
</ol>
</body>
</html>