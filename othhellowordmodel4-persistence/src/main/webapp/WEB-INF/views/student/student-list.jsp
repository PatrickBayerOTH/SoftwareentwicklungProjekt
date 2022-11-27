<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Students</title>

</head>
<body>
	<h2>${msgs}</h2>
	
	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Id</th>
		            <th>Name</th>
		            <th>Email</th>
		            <th>Update</th>
		            <th>Delete</th>
		            
		</tr>
	        
	
		 <c:forEach var="student" items="${students}">
		            <tr>
		                <td>${student.id}</td>
		                <td>${student.name}</td>
		                <td>${student.email}</td>
		                                
		                <td><a href="/student/update/${student.id}">Update</a></td>
		                <td><a href="/student/delete/${student.id}">Delete</a></td>
		                
		            </tr>
		</c:forEach>

	
	</table>
	
	<h2>${errors}</h2>		
	
	<a href="/student/add/">Add Student</a>
		
	
</body>


</html>