
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="pt-br">
  <head>    
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

  </head>  
<body>
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
    

    
                         

	<div id="main" class="container">
	   

		<div  class="spacesup"></div>		
		<div class="text-center text-uppercase">
			<h4>Search the Academic Event</h4>
			
		</div>
		<!-- Info Alert -->
<c:if test="${success eq 'Erfolgreich'}">
<div class="alert alert-success alert-dismissible fade show"  th:if=${success}>
  	<h2 >${success}</h2>
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>
</c:if>



	<c:if test="${success eq 'Bereits vorhanden'}">
<div class="alert alert-danger alert-dismissible fade show"  th:if=${success}>
  	<h2 >${success}</h2>
    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
</div>
</c:if>



		
				
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
			<h5>Ergebnisse deiner Suche:</h5>		
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
					 
					  <button onclick="location.href='http://localhost:8080/addFriend/${student.id}'" ><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill-add" viewBox="0 0 16 16">
  <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0Zm-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z"/>
  <path d="M2 13c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Z"/>
</svg></button>		
					   
</button>
	                </td>
			    </tr>
			</c:forEach>
		</table>
		</div>
		</c:if>	
		<c:if test="${not empty friends}">

		<div class="text-center mt-5">
			<h5>Deine Freunde:</h5>		
		</div>

		<div class="table-responsive-md mt-3" >		
		<table class="table table-striped table-hover display" >
			<thead>
				<tr>
					<th class="text-center">Id</th>
				
				</tr>
			</thead>

			<c:forEach var="friend" items="${friends}">
			    <tr>
			   
			         <td>${friend.userId}</td>
			         <td>${friend.friendId}</td>
					
					
				
					
			    </tr>
			</c:forEach>
		</table>
		</div>
		</c:if>	
	
	
</div>	
	
</body>
</html>
<style>
 
    
    #main {
    margin-top:100px;
   
}

 #mess {
    margin-top:100px;
   
}
</style>