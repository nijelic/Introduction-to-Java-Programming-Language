<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List, hr.fer.zemris.java.tecaj_13.model.BlogEntry, hr.fer.zemris.java.tecaj_13.model.BlogComment" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Blog</title>
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

BlogEntry entry = (BlogEntry)request.getAttribute("entry");
@SuppressWarnings("unchecked")
List<BlogComment> comments = (List<BlogComment>)request.getAttribute("comments");
%>
<h1><%= entry.getTitle() %></h1><br>
<p><%= entry.getText() %></p><br>
<span>Last modified: <%=entry.getLastModifiedAt() %> Created at: <%=entry.getCreatedAt()%></span><br>
<br>
<%if((Boolean)entry.getCreator().getNick().equals(session.getAttribute("current.user.nick"))){ %>
<a href="<%=request.getContextPath() %>/servleti/editBlog/<%=entry.getId()%>">Edit</a>
<%} %>
<br>
<br>
<h2>Comments:</h2>
<br>
<%for(BlogComment c:comments) {%>
<span>
<%=c.getPostedOn()%><br>
<%=c.getMessage()%>
</span>
<br><br>
<%} %>
<br>
<form action="addComment/<%=entry.getId() %>" method="post" id="usrform">
  <h3>Comment if you want:</h3>
  <textarea  name="comment" rows="10" cols="50"  form="usrform"></textarea>
  <h3>Email:</h3> <input type="text" name="email">
  <input type="submit">
</form>
</body>
</html>