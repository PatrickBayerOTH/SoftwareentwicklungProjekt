<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">

    <meta http-equiv="Refresh" content="5; url=/user/all/${studentSession.user.id}"/>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>Student Event</title>
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
                <a class="nav-link" href="http://localhost:8080/mainpage">&Uuml;bersicht </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/activity">Aktivit&auml;ten</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/friends">Freunde</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/prelogout">Logout</a>
            </li>
        </ul>
    </div>
</nav>
<br><br><br><br>

<div class="alert alert-success" role="alert">
    <h4 class="mt-1 mb-2 pb-1">Ihre gewünschte Änderung sind vorgenommen</h4>
</div>
<br>
<br>
<div class="alert alert-info" role="alert">
    <h4 class="mt-1 mb-2 pb-1">Sie werden weitergeleitet</h4>
</div>

</body>
</html>