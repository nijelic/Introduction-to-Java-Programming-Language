<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page
	import="java.util.List, hr.fer.zemris.java.p12.utility.Poll, hr.fer.zemris.java.p12.utility.PollOption"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
<title>Rezultati glasovanja</title>
</head>
<body>
	<h1>Rezultati glasovanja</h1>
	<p>Ovo su rezultati glasovanja.</p>
	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Opcija</th>
				<th>Glasovi</th>
			</tr>
		</thead>
		<tbody>
			<%
			@SuppressWarnings("unchecked")
				List<PollOption> list = (List<PollOption>) request.getAttribute("options");
				for (PollOption o : list) {
			%>
			<tr>
				<td>
					<%
						out.print(o.getTitle());
					%>
				</td>
				<td>
					<%
						out.print(o.getVotesCount());
					%>
				</td>
			</tr>
			<%
				}
			%>
		</tbody>
	</table>


	<h2>Grafički prikaz rezultata</h2>
	<img alt="Pie-chart"
		src="glasanje-grafika?pollID=<%=list.get(0).getPollID()%>"
		width="400" height="400" />

	<h2>Rezultati u XLS formatu</h2>
	<p>
		Rezultati u XLS formatu dostupni su <a
			href="glasanje-xls?pollID=<%=list.get(0).getPollID()%>">ovdje</a>
	</p>

	<h2>Razno</h2>
	<p>Primjeri pobjednika:</p>
	<ul>
		<%
			long first = list.get(0).getVotesCount();
			for (PollOption o : list) {
				if (first!=o.getVotesCount()) {
					break;
				}
		%>
		<li><a
			href="<%out.print(o.getLink());%>"
			target="_blank">
				<%
					out.print(o.getTitle());
				%>
		</a></li>
		<%
			}
		%>
	</ul>
</body>
</html>