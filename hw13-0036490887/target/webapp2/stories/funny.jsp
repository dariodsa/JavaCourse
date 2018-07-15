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
	</head>
	<body>
		 <font color=<%
		         List<Pair> colors = new ArrayList<>();
		         colors.add(new Pair("White","FFFFFF"));
		         colors.add(new Pair("Red","FF0000"));
		         colors.add(new Pair("Green","00FF00"));
		         colors.add(new Pair("Cyan", "00FFFF"));
		         int r = new Random().nextInt(colors.size());
		         out.write(colors.get(r).getValue());
		 %>>
			A little girl climbed up onto her grandfather’s lap and asked, “Did God make me?”<br>
	<br>
			“Yes,” the grandpa replied.<br>
			“Did he make you, too?”<br>
			“Yes.”<br>
			“Well,” the girl said, looking  at his wrinkles and thinning hair, “he sure is doing a better job nowadays!”<br>
		</font>
	</body>
</html>