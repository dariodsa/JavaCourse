<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "java.util.*, hr.fer.zemris.java.hw14.model.PollOptions, hr.fer.zemris.java.hw14.model.Poll"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="UTF-8"/>
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
        
            <c:forEach var="option" items="${pollOptions}">
                <tr>
                    <td>${option.optionTitle}</td>
                    <td> ${option.votesCount}</td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
        <h2>Grafički prikaz</h2>
        <img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}" width="800" height="400"/>
        
        <h2>Rezultati u XLS formatu</h2>
        <p>Rezuktati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">Ovdje</a></p>
        
        <h2>Razno</h2>
        <p>Primjeri pjesama pobjedničkih bendova<p>
        <ul>
            <%
            
				try {
				    List<PollOptions> results = (List<PollOptions>)request.getAttribute("pollOptions");
					
					for(int i=0;i<results.size();++i) {
					    if(results.get(i).getVotesCount() != results.get(0).getVotesCount()) break;
					    out.print("<li>");
					    out.print("<a href=" + results.get(i).getOptionLink() + ">" + results.get(i).getOptionTitle() + "</a>");
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