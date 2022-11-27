<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of Students</title>

</head>
<body>
<h1>Listing all Students</h1>

	
	
	<table border="2" width="70%" cellpadding="2">

		<tr>
		            <th>Id</th>
		            <th>name</th>
		            <th>email</th>
		         
		            
		</tr>
	        
	
		 <c:forEach var="student" items="${students}">
		            <tr>
		                <td>${student.id}</td>
		                <td>${student.name}</td>
		                <td>${student.email}</td>
		                                
		             <!--   <td><a href="/academicevent/update/${academicevent.id}">Update</a></td>
		                <td><a href="/academicevent/delete/${academicevent.id}">Delete</a></td>
		                <td><a href="/workshop/findworkshopsbyacademicevent/${academicevent.id}">View Workshops</a></td>
		                 <td><a href="/lecture/findlecturesbyacademicevent/${academicevent.id}">View Lectures</a></td>
		               --> 
		            </tr>
		</c:forEach>

	
	</table>
	
	<h2>${errors}</h2>		
	
	<a href="/academicevent/add/">Add Academic Event</a>
		
	
</body>


</html>