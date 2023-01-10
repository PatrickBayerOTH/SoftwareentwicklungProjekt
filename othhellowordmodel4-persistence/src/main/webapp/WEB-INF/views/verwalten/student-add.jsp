<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
<div class="container text-center">
    <br>
    <div>
        <h2 class="fw-bold mb-2 text-uppercase ">User Registration - Sign Up</h2>
    </div>

    <form:form method="POST" modelAttribute="neuStudent" style="max-width: 600px; margin: 0 auto;"
               action="add/process">
        <br><br>
        <div class="m-3">

            <div class="form-group row">
                <label class="col-4 col-form-label" for="user.matrikelnummer">Matrikelnummer: </label>
                <div class="col-8">
                    <form:input path="user.matrikelnummer" type="text" id="user.matrikelnummer"
                                class="form-control"></form:input>
                    <form:errors path="user.matrikelnummer"></form:errors>
                </div>
            </div>
            <br>

            <div class="form-group row">
                <label class="col-4 col-form-label" for="user.email">E-mail: </label>
                <div class="col-8">
                    <form:input path="user.email" type="email" id="user.email" class="form-control"></form:input>
                    <form:errors path="user.email"></form:errors>
                </div>
            </div>

            <br>
            <div class="form-group row">
                <label class="col-4 col-form-label" for="user.nachname">Nachname: </label>
                <div class="col-8">
                    <form:input path="user.nachname" type="text" id="user.nachname" class="form-control"></form:input>
                    <form:errors path="user.nachname"></form:errors>
                </div>
            </div>

            <br>
            <div class="form-group row">
                <label class="col-4 col-form-label" for="user.name">Name: </label>
                <div class="col-8">
                    <form:input path="user.name" type="text" id="user.name" class="form-control"></form:input>
                    <form:errors path="user.name"></form:errors>
                </div>
            </div>
            <br>
            <div class="form-group row">
                <label class="col-4 col-form-label" for="user.password">Password: </label>
                <div class="col-8">
                    <form:input path="user.password" id="user.password" class="form-control"
                                type="password"></form:input>
                    <form:errors path="user.password"></form:errors>
                </div>
            </div>
            <br>
          <div class="form-group row">
                <label class="col-4 col-form-label" for="user.type">Type: </label>
                <div class="col-8">
                    <form:input path="user.type" type="text" id="user.type" class="form-control"></form:input>
                    <form:errors path="user.type"></form:errors>
                </div>
            </div>

            <br>
            <br>

            <button type="submit" class="btn btn-primary">Create user</button>

            <button type="reset" class="btn btn-primary">Reset</button>
            <br>
            <br>
        </div>
    </form:form>
    <div class="center-block">
        <a href="/login" class="btn btn-primary">Login</a>
    </div>

</div>
</body>
</html>