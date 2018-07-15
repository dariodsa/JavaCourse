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
			<h4>${entry.title}</h4>
			${entry.text}
		</center>
		Kreirano: ${entry.createdAt}<br>
		Modificirano: ${entry.lastModifiedAt}<br>
		<%
			if(request.getSession().getAttribute("current.user.nick") != null) {
			    if(request.getSession().getAttribute("current.user.nick").toString().compareTo(request.getAttribute("user").toString()) == 0) {
			        %><a href="new">Dodaj novi članak</a><br><br><%	        
			    }
			}
		
		 %>
		<h5>Komentari:</h5><br>
		
		<c:forEach var="comment" items="${entry.comments}">
		${comment.message}<br>
		${comment.usersEMail} ${comment.postedOn}<br>
		<hr>
		</c:forEach>
		
					<h4>Dodavanje novog komentara</h4>
					<table>
						<tr>
							<%
							if(request.getParameter("error") != null && !request.getParameter("error").isEmpty()) {
							    out.print("<td style=\"background:red\">" + request.getParameter("error") + "</td>");
							}
							%>
						</tr>
						<tr>
							<td>
							<form action="" method="post">
								<textarea name="comment" cols="35" rows="10" placeholder="Komentar"></textarea><br>
								Email: <input type="email" name="email" placeholder="fifa2018@fifa.ru"><br>
								<input type="submit" value="Pošalji"/>
							</form>
							</td>
						</tr>
					</table>
		
		
	<hr>
	<a href="../../main">Početna stranica</a>
	</body>
</html>