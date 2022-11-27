<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <meta name="viewport" content="width=device-width, initial-scale=1">
   
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<title>List of Students</title>

</head>
<body>
<header>
      <!-- Fixed navbar -->
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">UniPay</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="http://localhost:8080/mainpage">&Uuml;bersicht </a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="http://localhost:8080/activity">Aktivit&auml;ten</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="http://localhost:8080/friends">Freunde</a>
            </li>
              <li class="nav-item">
              <a class="nav-link" href="http://localhost:8080/login">Logout</a>
            </li>
          </ul>
         
        </div>
      </nav>
    </header>
<h1>Listing all Students</h1>

	
	
	<table class="table table-dark"border="2" width="70%" cellpadding="2">

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