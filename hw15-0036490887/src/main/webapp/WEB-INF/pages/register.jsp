<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF8"> 
	</head>
	<body>
		<center>
			<form action="" method="POST">
				<table>
					<tr>
						<td colspan="2"><h4 align="center">Registracija</h4></td>
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
						<td>Ponovljena ozinka:</td>
						<td><input type="password" name="password2" value=""/><br></td>
					</tr>
					<tr>
						<td>E-mail:</td>
						<td><input type="email" name="email" value=""/><br></td>
					</tr>
					<tr>
						<td>Ime:</td>
						<td><input type="text" name="firstname" value=""/><br></td>
					</tr>
					<tr>
						<td>Prezime:</td>
						<td><input type="text" name="lastname" value=""/><br></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Pošalji"/></td>
					</tr>
				</table>
			</form>
		</center>
	</body>
</html>