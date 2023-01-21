<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>ERROR</title>

</head>
<body>

<hr>

<div class="alert alert-danger" role="alert">
    Error, Bitte geben Sie eine gültige OTH Email-Adresse oder mit der Email existiert schon ein Account.
</div>

<br>
<div>
    <a href="/user/student/add" class="btn btn-outline-primary ui-state-active" type="button"
       role="button">Signup</a>
</div>

<br>
<div>
    <a href="/login" class="btn btn-outline-primary ui-state-active" type="button"
       role="button">Login</a>
</div>

</body>


</html>