<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogEntry, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blog Entries</title>
</head>
<body>
<%if(session.getAttribute("current.user.id") != null) { %>
<span><b> <%=(String)session.getAttribute("current.user.fn")%> <%= (String)session.getAttribute("current.user.ln")%></b> <a href="<%=request.getContextPath() %>/servleti/logout">Logout</a></span>
<br>
<br>
<% } else {%>
<span> <b>Not loged in!</b> </span><br>
<br>
<%
}
@SuppressWarnings("unchecked")
List<BlogEntry> entries = (List<BlogEntry>)request.getAttribute("entries");
if(entries.isEmpty()) {
	%>
	<h1>There is no blog entries.</h1>
	<%
} else
for(BlogEntry be:entries) {
%>
<a href="<%=session.getAttribute("current.picked.nick") %>/<%=be.getId()%>"><%= be.getTitle()%></a><br>
<%} %>
<%if((Boolean)request.getAttribute("editor")){ %>
<a href="<%=(String)session.getAttribute("current.user.nick") %>/new">create new</a><br>
<a href="<%=(String)session.getAttribute("current.user.nick") %>/edit">edit</a><br>
<%} %>
</body>
</html>