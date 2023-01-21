<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
<div class="container text-center">
    <br>

    <div class="alert alert-success" role="alert">
        Bitte geben Sie ihre Email ein.
    </div>

    <form:form method="POST" modelAttribute="neuPassword" action="/user/email-sender-password">
        <form:hidden path="id" cssClass="form-control"/>
        <label for="email">Email</label>
        <br><br>
        <form:input path="email" cssClass="form-control"/>

        <br>
        <input type="submit" value="reset" class="btn btn-primary">

    </form:form>

</div>
</body>
</html>