<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>

	<title>Updating a Lecture</title>
    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

 

</head>
<body>

<h1>Updating a Lecture</h1>
     
       
        <br>
        <br>
    
  <div>      	
    <form:form class="mt-4" method="POST" action="/lecture/update/process" modelAttribute="lectureForm">
		
		<div>

		<spring:url value="/lecture/selectacademicevent" var="selectacademiceventUrl" />

		<form:hidden path="academicevent.id" value="${academicevent.id}"/>
		<form:hidden path="id" value="${id}"/>
			    	
			<div class="form-row">			 
							
				<div class="form-group col-sm-2 ${status.error ? 'has-error' : ''}">
					<label for="academicevent.id">Academic Event</label>			
							
					<form:input path="academicevent.title" type="text"  id="academicevent.title" 
					placeholder="Click here to select a Academic Event" onclick="location.href='${selectacademiceventUrl}'"  readonly="true" />
					<form:errors path="academicevent.id" class="control-label" />		
			  
	  			</div>
			
			</div>				
			
			<br>
	
	
		<div>
			<label for="title">Title of the Lecture</label>

			
	        <form:input path="title" cssClass="form-control"/>
	        <form:errors path="title"></form:errors>
	        		      
	        <input type="submit" value="Add Lecture" class="btn">
		
		</div>

	</div>			
		</form:form>

  </div>
 
  
</body>
</html>