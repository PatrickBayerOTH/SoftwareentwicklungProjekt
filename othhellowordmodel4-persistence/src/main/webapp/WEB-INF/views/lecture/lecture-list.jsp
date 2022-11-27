<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Lectures</title>

</head>
<body>
<h1>Listing all Lectures</h1>

	<h2>${msgs}</h2>
	
	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Id</th>
		            <th>Title</th>
		            <th>Update</th>
		            <th>Delete</th>
		           		            
		</tr>
	        
	
		 <c:forEach var="lecture" items="${lectures}">
		            <tr>
		                <td>${lecture.id}</td>
		                <td>${lecture.title}</td>
		                	                                
		                <td><a href="/lecture/update/${lecture.id}">Update</a></td>
		                <td><a href="/lecture/delete/${lecture.id}">Delete</a></td>
		                
		                
		            </tr>
		</c:forEach>

	
	</table>
	
	<h2>${errors}</h2>		
	
	<a href="/lecture/add/">Add Academic Lecture</a>
		
	
</body>


</html>