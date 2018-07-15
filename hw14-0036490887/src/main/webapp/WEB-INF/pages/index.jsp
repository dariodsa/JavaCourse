<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		
		<title>
		Ankete
		</title>
	</head>
	<body>
		<h1>Ankete</h1>
		<table border="10">
		<c:forEach var="poll" items="${polls}">
			<tr><td><a href="glasanje?pollID=${poll.pollId}">${poll.title}</a></td></tr>
		</c:forEach>
		</table>
	</body>
</html>