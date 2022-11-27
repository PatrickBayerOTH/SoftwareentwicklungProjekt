<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add a Workshop</title>
</head>
<body>
<h1>Adding a Workshop</h1>

<h2>Step 1: Finding an Academic Event:<br></h2>
<div>
	 <form:form method="POST" action="/workshop/findacademicevent">
			        
		<label for="titulo">Title of the Academic Event</label>
        
        <input type="text" name="titleacademicevent">
        
        <br>
                
        <input type="submit" value="Search Academic Event" class="btn">
        
	</form:form>
</div>

      
  <c:if test="${not empty academicevents}">
      
        <br>
        <br>
      	Academic Events found: <br>
	    <form:form method="POST" action="/workshop/add/process" 
	    	modelAttribute="workshopForm">
	    	
			 <c:forEach var="event" items="${academicevents}">
				            <tr>
				                <td><form:radiobutton path="academicevent.id" value="${event.id}"/></td>
				                <td>${event.title}</td>
				            </tr>
			</c:forEach>
			
			<br>
			
	<div>
	<h2>Step 2: Informing the data about the Workshop: <br></h2> 
			<label for="title">Title of the Workshop</label>

			
	        <form:input path="title" cssClass="form-control"/>
	    
	    	<br>    
	      
	        <input type="submit" value="Add Workshop" class="btn">
			
		</form:form>
	</div>

  </c:if>
  
  
</body>
</html>