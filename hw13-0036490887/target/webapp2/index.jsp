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
	</head>
	<body>
			<a href="setcolor">Background color chooser</a><br><hr>
			<form action="trigonometric" method="GET">
			  Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
			  Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			  <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
			</form>
			<hr>
			<a href="stories/funny.jsp">Funny stories</a><br><hr>
			
			<a href="powers?a=1&b=100&n=3">Powers actions</a><br><hr>
	</body>
</html>