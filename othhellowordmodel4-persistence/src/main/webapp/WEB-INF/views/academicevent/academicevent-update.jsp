<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<title>Update Academic Event</title>

</head>
<body>

			
	 <form:form method="POST" modelAttribute="academiceventForm" action="/academicevent/update/process">
		
			
        <form:hidden path="id" cssClass="form-control"/>
        
        
		<label for="titulo">Title</label>
        <form:input path="title" cssClass="form-control"/>
        		       
        <label for="data">Date</label>
        <form:input path="date" type="date" cssClass="form-control"/>
        
        <input type="submit" value="Update" class="btn">
         
		
	</form:form>
	
</body>

</html>