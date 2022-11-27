<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Workshops of an Event</title>

</head>
<body>

	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Academic Event</th>
		            <th><h2>${academicevent.title}</h2></th>
		            
		</tr>
		
		
		<tr>
		            <th>Id Workshop</th>
		            <th>Title of the Workshop</th>
		            	            
		            
		</tr>
	        	
		 <c:forEach var="workshop" items="${workshops}">
		            <tr>
		                <td>${workshop.id}</td>
		                <td>${workshop.title}</td>
		            </tr>
		</c:forEach>

	</table>
<a href="/academicevent/add/">Add Academic Event</a> | <a href="/workshop/add/">Add an Workshop </a>


</body>


</html>