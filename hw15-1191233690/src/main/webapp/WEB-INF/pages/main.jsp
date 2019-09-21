<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
    <%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>

<style type="text/css">
		
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
</style>

</head>
<body>
<%if(session.getAttribute("current.user.id") != null) { %>
<span><b> <%=(String)session.getAttribute("current.user.fn")%> <%= (String)session.getAttribute("current.user.ln")%></b> <a href="<%=request.getContextPath() %>/servleti/logout">Logout</a></span>
<br>
<br>
<% } else {%>
<span> <b>Not loged in!</b> </span><br>
<br>
<%if(session.getAttribute("err") != null) { 
	out.write((String)session.getAttribute("err"));
	session.setAttribute("err", null);}%>
<br>
<form action="login" method="post">
		
		<div>
			<span class="formLabel">Nick</span><input type="text" name="nick" size="30"
			 <%if(session.getAttribute("current.user.nick")!=null){ %>value='<%out.print((String)session.getAttribute("current.user.nick"));%>'<%}%> >
		</div>
		<br>
		<div>
			<span class="formLabel">Password</span><input type="password" name="pass" size="30">
		</div>
		<br>
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Submit">
		</div>
	</form>
	<br>
	<a href="register">Registration</a>
	<br>
	<%} 
	@SuppressWarnings("unchecked")
	List<String> nicks = (List<String>)request.getAttribute("nicks");	
	for(String nick: nicks) {
	%>
	<a href="author/<%=nick%>"><%=nick%></a><br>
	<%} %>
</body>
</html>