<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style.jsp">
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
<title>Rezultati glasovanja</title>
</head>
<body>
	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Izvođač</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody><%
		Integer length = (Integer)request.getAttribute("length");
		for(Integer i = 0; i < length; i++) {
		%>
			<tr>
				<td><%out.print(request.getAttribute(request.getAttribute(i.toString()+"ID")+"Author"));%></td>
				<td><%out.print(request.getAttribute(i.toString()));%></td>
			</tr>
		<%} %>
		</tbody>
	</table>
		

	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pjesama pobjedničkih bendova:</p>
	<ul>
	<%
		String first = (String)request.getAttribute("0");
		for(Integer i=0; i < length; i++) {
			if(!first.equals(request.getAttribute(i.toString()))) {
				break;
			}
			%>
			<li><a href="<% out.print(request.getAttribute(request.getAttribute(i.toString()+"ID")+"url"));%>"
			target="_blank"><%out.print(request.getAttribute(request.getAttribute(i.toString()+"ID")+"Author")); %></a></li>
			<%
		}
	%>
	</ul>
</body>
</html>