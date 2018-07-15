<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<<<<<<< HEAD
<html>
=======
<!DOCTYPE html>
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
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
<<<<<<< HEAD
		<p>Od sljedećih bendova, koji Vam je najdraži?</p>
=======
		<p>Od sljedećih bendova, koji Vam je najdraži? <a>
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
		<p>Kliknite na link kako biste glasali!</p>
		<ol>
			<c:forEach var="bend" items="${bends}">
			<li><a href="glasanje-glasaj?id=${bend.id}">${bend.name}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>