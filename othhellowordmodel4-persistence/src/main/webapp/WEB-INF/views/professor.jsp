
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<title>Home PROFESSOR</title>

</head>
<body>
	
	<hr>
	<h1>Profile PROFESSOR - ${professorSession.name}</h1>
		
	<hr>
	| Home | My Workshops | My Lectures | About |  Welcome,<%=request.getRemoteUser()%> !| <a href="/prelogout"> Logout</a> 
	
	
	<hr> 
	<br>
</body>


</html>