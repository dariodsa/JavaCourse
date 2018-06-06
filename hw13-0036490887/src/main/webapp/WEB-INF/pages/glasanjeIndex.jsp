<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
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
		<title>
		Glasanje za omiljeni bend
		</title>
	</head>
	<body>
		<h1>Glasanje za omiljeni bend</h1>
		<p>Od sljedećih bendova, koji Vam je najdraži? <a>
		<p>Kliknite na link kako biste glasali!</p>
		<ol>
			<c:forEach var="bend" items="${bends}">
			<li><a href="glasanje-glasaj?id=${bend.id}">${bend.name}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>