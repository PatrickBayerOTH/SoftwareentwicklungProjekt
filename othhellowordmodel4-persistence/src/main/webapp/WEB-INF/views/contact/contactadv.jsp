<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sending a Contact Message......</title>
<link rel="stylesheet"   href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
</head>
<body>

 <div class="jumbotron">
         <div class="container">
            <h1>Contact Message</h1>
            
         </div>
 </div>
<section class="container">

	 <form:form  method="POST" modelAttribute="contactForm" class="form-horizontal" action="/contact/addadv/process">
         <fieldset>
            <legend>Add new contact message</legend>

			<div class="form-group">
               <label class="control-label col-lg-2" for="name">Name</label>
               
               <form:input id="name" path="name"/>
               <form:errors path="name" cssClass="error"/> 
                   
               
            </div>
            
            <div class="form-group">
               <label class="control-label col-lg-2" for="email">Email</label>
               
               <form:input id="email" path="email"/>
    		   <form:errors path="email" cssClass="error"/>                                     
            </div>
                  
            <div class="form-group">
               <label class="control-label col-lg-2" for="age">Age</label>
               
               <form:input id="age" path="age"/>
                                         
            </div>        
            
             <div class="form-group">
               <label class="control-label col-lg-2 for="message" >Message</label>
               
               <form:textarea id="message" path="message" rows="4"/>
                                         
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