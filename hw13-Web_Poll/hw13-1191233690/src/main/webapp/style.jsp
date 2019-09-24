<%@ page language="java" contentType="text/css; charset=UTF-8"
    pageEncoding="UTF-8"%>

body {
background-color: <%
if(session.getAttribute("pickedBgCol") == null) {%>
	white
<%}else { %>
	${sessionScope.pickedBgCol }
<%}%> ;
font-size:20px;
}
