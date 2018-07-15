<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ page import = "java.util.List, hr.fer.zemris.java.web.ColorChooser.Pair, java.util.Random, java.util.ArrayList"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<<<<<<< HEAD
<html>
	<head>
		<meta charset="UTF-8"/>
		<link type="text/css" rel="stylesheet"  href="/webapp2/style.jsp"/>
=======
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
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
		<title>OS usage</title>
	</head>
	<body>
		<h1>OS usage</h1>
		
		<p>Here are the results of OS usage in survey that we completed.</p>
		
		<img src="reportImage">
		
	</body>
</html>