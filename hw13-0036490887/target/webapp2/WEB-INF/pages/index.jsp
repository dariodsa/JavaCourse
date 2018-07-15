<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
	<head>
		<meta charset="UTF-8"/>
		<style>
			body {background-color: #<%
				String color = session.getAttribute("pickedBgCol").toString();
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
			<a href="color.jsp">Background color chooser</a>
	</body>
</html>