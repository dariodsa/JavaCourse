<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF8"> 
	</head>
	<body>
		<header style="text-align:right">
		<blink>
			<%
				if(request.getSession().getAttribute("current.user.nick") != null) {
				    out.print(request.getSession().getAttribute("current.user.fn") + " " +
				              request.getSession().getAttribute("current.user.ln") + " " +
				              "<a href=\"/blog/servleti/main/logout\">Logout</a>"
				    );
				} else {
				    out.print("Nisi prijavljen.");
				}
			%>
			</blink>
		</header>
	
		<center>
			<h3>${title}</h3>
			<form action="" method="POST">
				<input type="hidden" name="entryID" value="${entryID}"/>
				<textarea rows="10" cols="45" name="text">${text}</textarea><br>
				<input type="submit" value="Promijeni"/>
			</form>
		</center>
	</body>
</html>