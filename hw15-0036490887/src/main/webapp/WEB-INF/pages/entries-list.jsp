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
			<h4>Lista blog članaka</h4>
			<ol>
			<c:forEach var="article" items="${entries}">
			   <li><a href="${user}/${article.id}">${article.title}</a> </li>
			   
			</c:forEach>
			</ol>
		</center>
		<%
		   if(request.getSession().getAttribute("current.user.nick") != null) {
		       String logUser = request.getSession().getAttribute("current.user.nick").toString();
		       if(logUser.compareTo(request.getAttribute("user").toString()) == 0) {
		           %>
		   		<a href="${user}/new">Dodaj novi članak</a><br>
		   		<a href="${user}/edit">Editiraj članke</a>
		   		 <%           
		       }
		   }
		      
		 %>
	<hr>
	<a href="../main">Početna stranica</a>
	</body>
</html>