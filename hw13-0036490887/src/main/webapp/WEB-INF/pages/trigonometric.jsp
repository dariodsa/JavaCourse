<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<<<<<<< HEAD
<html>
=======
<!DOCTYPE html>
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
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
<<<<<<< HEAD
			<table border="40">
=======
			<table>
>>>>>>> e491ac7bfaab4b16a4e5916f443f67797f3a7015
			<tbody>
				<c:forEach var="value" items="${values}">
					<tr>
						<td>
							${value.x}
						</td>
						<td>
							${value.sin}
						</td>
						<td>
							${value.cos}
						</td>
					</tr>
				</c:forEach>
			</tbody>
			</table>
	</body>
</html>