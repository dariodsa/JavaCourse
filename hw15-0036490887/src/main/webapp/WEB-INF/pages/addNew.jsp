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
			<h4>Dodavanje novog članka</h4>
			
			<form action="" method="post">
				<table>
					<tr>
					<%
						if(request.getParameter("error") != null && !request.getParameter("error").isEmpty()) {
							    out.print("<td style=\"background:red\">" + request.getParameter("error") + "</td>");
						}
					%>
					</tr>
					<tr>
						<td>Naslov:</td>
						<td><input type="text" name="heading" placeholder="Naslov"/></td>
					</tr>
					<tr>
						<td colspan="2">
							<textarea rows="10" cols="60" name="text"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="right">
						<input type="submit" value="Pošalji"/>
						</td>
					</tr>
				</table>
			</form>
		</center>
		
	</body>
</html>