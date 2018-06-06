<%@page import="java.text.SimpleDateFormat, java.util.Date, java.lang.*"%>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<meta charset="UTF-8"/>
		<link type="text/css" rel="stylesheet"  href="/webapp2/style.jsp"/>
	</head>
	<body>
	<%
	
	ServletContext ctx = getServletContext();
	long time = System.currentTimeMillis() - (long)ctx.getAttribute("time");
	StringBuilder result = new StringBuilder();
	if(time / (24*60*60 * 1000) > 0) {
	    result.append(String.format("%02d days ",(time/(24*60*60*1000))));
	}
	if( time / (60 * 60 * 1000)  % 24 > 0) {
	    result.append(String.format("%02d hours ",time/(60*60 *1000) % 24));
	}
	if(time / (60 * 1000) %60 > 0) {
	    result.append(String.format("%02d minutes ", time / (60 * 1000) % 60));
	}
	result.append(String.format("%02d seconds ", (time / 1000)%60));
	result.append(String.format("%02d miliseconds", time % 1000));
	        
	out.print(result.toString());
	%>
	
	</body>
</html>