<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <title>Update Student Event</title>

</head>
<body>

<br>
<form:form method="POST" modelAttribute="studentForm" action="/studentProfessor/update/process">
    <br>
    <form:hidden path="id" cssClass="form-control"/>
    <br>
    <label for="user.password">Password</label>
    <form:input path="user.password" id="user.password" cssClass="form-control"/>

    <br>
    <label for="email">email</label>
    <form:input path="email" cssClass="form-control"/>
    <br>

    <input type="submit" value="Update" class="btn">


</form:form>

</body>

</html>