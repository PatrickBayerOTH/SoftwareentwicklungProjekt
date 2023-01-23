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
    <title>Home STUDENT</title>

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
                <a class="nav-link" href="http://localhost:8080/sendMoney">Senden</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/user/accountVerwalten/${studentSession.user.id}">Account Verwalten</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/prelogout">Logout</a>
            </li>
        </ul>
        <ul id="weather" class="navbar-nav ms-auto">
            <li>
                        <span class="navbar-text">
                            <div class="row">
                                <div class="col">
                                    <div class="row">
                                        Temperatur: ${temp}

                                    </div>
                                    <div class="row">
                                        ReelFeal: ${rf}

                                    </div>
                                </div>
                                <div class="col">
                                    <img src="${wet}" alt="SVG mit img laden" width="64" height="64">

                                </div>
                            </div>
                        </span>
            </li>
        </ul>
    </div>
</nav>

<br><br><br><br>

<div class="alert alert-success" role="alert">
    <h4 class="mt-1 mb-2 pb-1">Profile STUDENT - ${studentSession.user.name} ${studentSession.user.nachname} </h4>
</div>


<div>
    <a href="/user/all/${studentSession.user.id}" class="btn btn-outline-primary ui-state-active" type="button"
       role="button">Daten Aktualisieren</a>
</div>
<br>
<div>
    <a href="/user/student/delete" class="btn btn-outline-danger ui-state-active" type="button" role="button">Account
        Löschen</a>
</div>


</body>
</html>