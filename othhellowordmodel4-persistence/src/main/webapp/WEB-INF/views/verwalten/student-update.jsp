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
    <title>Update Student Event</title>

</head>
<body>

<!-- Main Navigation -->

<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="/home">UniPay</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse"
            aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/home">Home </a>
            </li>
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
                <a class="nav-link" href="/user/accountVerwalten/${studentSession.user.id}">Account Verwalten</a>
            </li>
            <li class="nav-item ">
                <a class="nav-link" href="/prelogout">Logout</a>
            </li>
        </ul>
    </div>
</nav>
<br><br><br><br>

<form:form method="POST" modelAttribute="studentForm" action="/user/update/process">
    <form:hidden path="id" cssClass="form-control"/>

    <label for="email">Email</label>
    <form:input readonly="true" path="email" cssClass="form-control"/>

    <label for="matrikelnummer">Marikelnummer</label>
    <form:input readonly="true" path="matrikelnummer" cssClass="form-control"/>

    <label for="nachname">Nachname</label>
    <form:input path="nachname" cssClass="form-control"/>

    <label for="name">Name</label>
    <form:input path="name" cssClass="form-control"/>

    <label for="password">password</label>
    <form:input path="password" cssClass="form-control"/>

    <label for="type">Type</label>
    <form:input path="type" cssClass="form-control"/>

    <label for="authProvider">Provider</label>
    <form:input readonly="true" path="authProvider" cssClass="form-control"/>

    <br>

    <input type="submit" value="Update" class="btn btn-primary">

</form:form>


</body>

</html>