
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="pt-br">
  <head>    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.no-icons.min.css" rel="stylesheet">
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css" rel="stylesheet">    
    
  </head>  
<body>

	<div class="container">

		<div  class="spacesup"></div>		
		<div class="text-center text-uppercase">
			<h4>Search the Academic Event</h4>
		</div>
		
				
		<spring:url value="/selectFriend" var="selectUrl" />
				
		<form:form  class="form-row"  modelAttribute="friendForm" action="${selectUrl}">	
		
			<div  class="form-group col-sm-1"></div>
			<spring:bind path="name">
				  <div class="form-group col-sm-8  ${status.error ? 'has-error' : ''}">		
						<form:input path="name" type="text" class="form-control" id="name" required="required"/>                                
						<form:errors path="name" class="control-label" />								
				  </div>
			</spring:bind>	
						
			<div class="form-group col-sm-3 d-flex justify-content-center justify-content-md-start">
				<button type="submit" class="btn btn-outline-secondary"><i class="fas fa-search"></i> <span class="esconder"> Search</span></button>
			</div>	
		</form:form>	
	

	<c:if test="${not empty students}">

		<div class="text-center mt-5">
			<h5>Results of the Search:</h5>		
		</div>

		<div class="table-responsive-md mt-3" >		
		<table class="table table-striped table-hover display" >
			<thead>
				<tr>
					<th class="text-center">Id</th>
					<th class="text-center">Name</th>
					<th class="text-center">Email</th>
				</tr>
			</thead>

			<c:forEach var="student" items="${students}">
			    <tr>
			   
			   
					<td>${student.id}</td>
					
					<td>${student.name}</td>
								
					<td  class="text-center">
					 
					  <button class="btn btn-sm" data-toggle="tooltip" data-placement="botton" title="Selecione este academicevent" onclick="location.href='http://localhost:8080/addFriend/${student.id}'" ><i class="fas fa-check-circle"></i></button>		
					  
	                </td>
			    </tr>
			</c:forEach>
		</table>
		</div>
		</c:if>	
	
	
</div>	
	
</body>
</html>