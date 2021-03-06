<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "java.util.*, hr.fer.zemris.java.web.objects.BendResult"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF-8"/>
		<style>
			body {background-color: #<%
				String color = (String)session.getAttribute("pickedBgCol");
				if(color == null) {
				    out.print("FFFFFF");
				} else {
				    out.print(color);
				}
			%>    
			}
		</style>
		<style 
			type="text/css">
				table.rez td{text-align: center;}
		</style>
		<title>
		Rezultati glasanja
		</title>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezulati glasnaja. </p>
		<table border="1" class="rez">
		<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
		<tbody>
		
			<c:forEach var="result" items="${results}">
				<tr>
					<td>${result.bend.name}</td>
					<td> ${result.numOfVotes}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		<h2>Grafički prikaz</h2>
		<img alt="Pie-chart" src="glasanje-grafika" width="800" height="400"/>
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezuktati u XLS formatu dostupni su <a href="glasanje-xls">Ovdje</a></p>
		
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova<p>
		<ul>
			<%
			try {
			    List<BendResult> results = (List<BendResult>)request.getAttribute("results");
				Collections.sort(results);
				
				for(int i=0;i<results.size();++i) {
				    if(results.get(i).getNumOfVotes() != results.get(0).getNumOfVotes()) break;
				    out.print("<li>");
				    out.print("<a href=" + results.get(i).getBend().getLink() + ">" + results.get(i).getBend().getName() + "</a>");
				    out.print("</li>");
				}
			} catch(ClassCastException ex) {
			    response.setStatus(500);
			    out.print("Error in given data.");
			}
			%>
		</ul>
	</body>
</html>