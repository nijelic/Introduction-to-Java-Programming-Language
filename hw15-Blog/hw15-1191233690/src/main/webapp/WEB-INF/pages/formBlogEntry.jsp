<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update entry</title>
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
if(session.getAttribute("msg")!=null){
	out.print(session.getAttribute("msg"));
	session.setAttribute("msg", null);
}
%>
<form action="<%=request.getContextPath() %>/servleti/updateBlog" method="post" id="usrform">
  title: <input type="text" name="title" value="<% if(session.getAttribute("title")!=null){
	out.print(session.getAttribute("title"));
	session.setAttribute("title", null);
}%>" >
<br>
  <textarea  name="text" rows="20" cols="200"  form="usrform"><%
if(session.getAttribute("text")!=null){
	out.print(session.getAttribute("text"));
	session.setAttribute("text", null);
}
%></textarea>
  <input type="submit">
</form>

<br>

</body>
</html>