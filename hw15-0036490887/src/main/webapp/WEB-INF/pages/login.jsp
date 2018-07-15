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
		<%
		if(request.getSession().getAttribute("current.user.nick") == null) {
		%>
			<form action="" method="POST">
				<table>
					<tr>
						<td colspan="2"><h4 align="center">Prijava</h4></td>
					</tr>
					
					<c:if test="${not empty param.error}">
						<tr>
							<td colspan="2" style="background:red" align="center">
								${param.error}
							</td>
						</tr>
					</c:if>
					<tr>
						<td>Koriničko ime: </td>
						<td><input type="text" name="username" value="${username}"/><br></td>
					</tr>
					<tr>
						<td>Lozinka:</td>
						<td><input type="password" name="password" value=""/><br></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Pošalji"/></td>
					</tr>
				</table>
		</form>
		<%
		} else {
		%> <h3>Dobro došao  
			<% out.print(request.getSession().getAttribute("current.user.fn") + " " +
				        request.getSession().getAttribute("current.user.ln"));
			%>
			</h3>
			Kliknite na korisnika kako bi vidjeli njegove članke i kako bi komentirali.<br>
			<br>
		<%} %>
		<a href="register"> Registracija</a><hr>
		<h4>Lista korisnika:</h4>
		<c:forEach var="user" items="${blogUsers}">
		   <a href="author/${user.nick}">${user.nick}</a>
		</c:forEach>
		
		</center>
	</body>
</html>