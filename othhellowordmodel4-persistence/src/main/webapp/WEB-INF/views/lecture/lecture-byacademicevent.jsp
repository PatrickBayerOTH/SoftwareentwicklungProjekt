<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Lectures of one Event</title>

</head>
<body>

	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Academic Event</th>
		            <th><h2>${academicevent.title}</h2></th>
		            
		</tr>
		
		
		<tr>
		            <th>Id Lecture</th>
		            <th>Title of the Lecture</th>
		            	            
		            
		</tr>
	        	
		 <c:forEach var="lecture" items="${lectures}">
		            <tr>
		                <td>${lecture.id}</td>
		                <td>${lecture.title}</td>
		            </tr>
		</c:forEach>

	</table>
<a href="/academicevent/add/">Add Academic Event</a> | <a href="/lecture/add/">Add an Lecture </a>


</body>


</html>