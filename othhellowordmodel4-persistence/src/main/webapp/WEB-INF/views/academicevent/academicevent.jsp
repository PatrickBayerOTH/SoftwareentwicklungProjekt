<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Creating an Academic Event......</title>
<link rel="stylesheet"   href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
</head>
<body>

 <div class="jumbotron">
         <div class="container">
            <h1>Academic Event</h1>
            
         </div>
 </div>
<section class="container">

	 <form:form  method="POST" modelAttribute="academiceventForm" class="form-horizontal" action="/academicevent/add/process">
         <fieldset>
            <legend>Add new Academic Event </legend>

			<div class="form-group">
               <label class="control-label col-lg-2" for="name">Title</label>
               
               <form:input id="title" path="title"/>
               <form:errors path="title" cssClass="error"/> 
                
               
            </div>
            
            <div class="form-group">
               <label class="control-label col-lg-2" for="date">Date</label>
               
               <form:input id="date" path="date" type="date" />
    		   <form:errors path="date" cssClass="error"/>                                     
            </div>
                  
                     
            
            
            <div class="form-group">
               <div class="col-lg-offset-2 col-lg-10">
                <input type="submit" id="btnAdd" class="btn btn-primary" value ="Add"/>
                <input type="reset" id="btnReset" class="btn btn-secondary" value ="Reset"/>
               </div>
            </div>
            
		
		</fieldset>
		
		
	</form:form>
	
		
</section>

</body>
</html>