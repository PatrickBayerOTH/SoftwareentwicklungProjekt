<%@page import="de.othr.im.model.Contact"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Contacts</title>

</head>
<body>


	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Id</th>
		            <th>Name</th>
		            <th>Email</th>
		            <th>Age</th>
		            <th>Message</th>
		            <th>Update</th>
		            <th>Delete</th>
		            
		</tr>
	        
	
		 <c:forEach var="contact" items="${contacts}">
		            <tr>
		                <td>${contact.id}</td>
		                <td>${contact.name}</td>
		                <td>${contact.email}</td>
		                <td>${contact.email}</td>
		                <td>${contact.message}</td>
		                
		                <td><a href="/contacts/update/${contacto.id}">Update</a></td>
		                <td><a href="/contacts/delete/${contacto.id}">Delete</a></td>
		                
		            </tr>
		</c:forEach>

	
	</table>
		
	
	<a href="/contact/addadv/">Add Contact Request</a>
		
	
</body>


</html>