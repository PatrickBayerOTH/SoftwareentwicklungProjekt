<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Events</title>

</head>
<body>
<h1>Listing all Academic Events</h1>

	<h2>${msgs}</h2>
	
	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Id</th>
		            <th>Title</th>
		            <th>Date</th>
		            <th>Update</th>
		            <th>Delete</th>
		            <th>Workshops</th>
		            <th>Lectures</th>
		            
		</tr>
	        
	
		 <c:forEach var="academicevent" items="${academicevents}">
		            <tr>
		                <td>${academicevent.id}</td>
		                <td>${academicevent.title}</td>
		                <td>${academicevent.date}</td>
		                                
		                <td><a href="/academicevent/update/${academicevent.id}">Update</a></td>
		                <td><a href="/academicevent/delete/${academicevent.id}">Delete</a></td>
		                <td><a href="/workshop/findworkshopsbyacademicevent/${academicevent.id}">View Workshops</a></td>
		                 <td><a href="/lecture/findlecturesbyacademicevent/${academicevent.id}">View Lectures</a></td>
		                
		            </tr>
		</c:forEach>

	
	</table>
	
	<h2>${errors}</h2>		
	
	<a href="/academicevent/add/">Add Academic Event</a>
		
	
</body>


</html>