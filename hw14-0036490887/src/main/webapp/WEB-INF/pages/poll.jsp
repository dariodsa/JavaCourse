<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import = "java.util.*, hr.fer.zemris.java.hw14.model.PollOptions,hr.fer.zemris.java.hw14.model.Poll" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		
		<title>
		Ankete
		</title>
	</head>
	<body>
	<h1>${poll.title}</h1>
		<h4>${poll.question}</h4>
		<%
		
		List<PollOptions> list = (List<PollOptions>) request.getAttribute("polloptions");
		Poll poll = (Poll)request.getAttribute("poll");
		out.print("<table>");	
		for(int i=1;i<=list.size();++i) {
		    out.print("<tr><td>" + i +". </td>");
		    
		    out.print("<td><a href=vote?optionID=" + 
		    		list.get(i-1).getPollOptionId() + "&pollID=" + poll.getPollId() + ">" +
		    
		            list.get(i-1).getOptionTitle() + "</a></td>");
		    
		    out.print("</tr>");
		}
		out.print("</table>");
		%>
	</body>
</html>