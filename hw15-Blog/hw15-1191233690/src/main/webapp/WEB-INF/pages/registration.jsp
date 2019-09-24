<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>

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
	<span><b><%= (String)session.getAttribute("current.user.fn")%> <%= (String)session.getAttribute("current.user.ln")%>
		</b>><a href="<%=request.getContextPath() %>/servleti/logout">Logout</a></span>
	<br>
<br>
<% } else {%>
	<span><b> Not loged in!</b></span>
<br>
<br>
	<form action="save" method="post">

		<div>
			<div>
				<span class="formLabel">First name</span> <input type="text"
					name="fn" <%if(session.getAttribute("current.user.fn")!=null){ %>
					value='<%out.print((String)session.getAttribute("current.user.fn"));%>'
					<%}%> size="30">
			</div>
<br>
			<%if(session.getAttribute("err.fn")!=null){%>
			<div class="greska">
				<% out.print((String)session.getAttribute("err.fn"));
				session.setAttribute("err.fn", null);%>
			</div>
			<%}%>
		</div>
<br>
		<div>
			<div>
				<span class="formLabel">Last name</span><input type="text" name="ln"
					<%if(session.getAttribute("current.user.ln")!=null){ %>
					value='<%out.print((String)session.getAttribute("current.user.ln"));%>'
					<%}%> size="30">
			</div>
			<%if(session.getAttribute("err.ln")!=null){%>
			<div class="greska">
				<% out.print((String)session.getAttribute("err.ln"));
				session.setAttribute("err.ln", null);%>
			</div>
			<%}%>
		</div>
<br>
		<div>
			<div>
				<span class="formLabel">EMail</span><input type="text" name="email"
					<%if(session.getAttribute("current.user.email")!=null){ %>
					value='<%out.print((String)session.getAttribute("current.user.email"));%>'
					<%}%> size="50">
			</div>
			<%if(session.getAttribute("err.email")!=null){%>
			<div class="greska">
				<% out.print((String)session.getAttribute("err.email"));
				session.setAttribute("err.email", null);%>
			</div>
			<%}%>
		</div>
<br>
		<div>
			<div>
				<span class="formLabel">Nick</span><input type="text" name="nick"
					<%if(session.getAttribute("current.user.nick")!=null){ %>
					value='<%out.print((String)session.getAttribute("current.user.nick"));%>'
					<%}%> size="30">
			</div>
			<%if(session.getAttribute("err.nick")!=null){%>
			<div class="greska">
				<% out.print((String)session.getAttribute("err.nick"));
				session.setAttribute("err.nick", null);%>
			</div>
			<%}%>
		</div>
<br>
		<div>
			<div>
				<span class="formLabel">Password</span><input type="password"
					name="pass" size="30">
			</div>
			<%if(session.getAttribute("err.pass")!=null){%>
			<div class="greska">
				<% out.print((String)session.getAttribute("err.pass"));
				session.setAttribute("err.pass", null);%>
			</div>
			<%}%>
		</div>
<br>
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="method" value="Submit"> 
		</div>

	</form>
	<%}%>
	<br>
	<form action="main">
    	<input type="submit" value="Main" />
	</form>
</body>
</html>