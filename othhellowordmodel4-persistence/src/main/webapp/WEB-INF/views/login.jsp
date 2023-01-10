<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/login" var="loginUrl"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login Seite</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<br><br>
<h4 class="mt-1 mb-5 pb-1">We are The UniPay Team</h4>

<div>
    <h4><a href="/oauth2/authorization/google"> Login with Google</a></h4>
</div>

<body>
<form action="${loginUrl}" method="post" class="h-100 gradient-form" style="background-color: #eee;">

    <div class="mb-3">
        <p>
            <label for="username" class="form-label ">Matrikelnummer</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Matrikelnummer"/>
            <small id="emailHelp" class="form-text text-muted">We'll never share your Matrikelnummer with anyone
                else.</small>
        </p>
    </div>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="*****"/>
    </p>

    <button type="submit" class="btn btn-primary">Log in</button>

    <div class="text-center">
        <p>Not a member? <a href="user/student/add">Sign Up</a></p>
    </div>
</form>

</body>
