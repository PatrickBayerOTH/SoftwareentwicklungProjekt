<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="student_list.title"></spring:message></title>

</head>
<body>
<header>
    <!-- Fixed navbar -->
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="/home"><spring:message code="navbar.head"></spring:message></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="http://localhost:8080/home"><spring:message code="navbar.home"></spring:message></a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="http://localhost:8080/mainpage"><spring:message code="navbar.mainpage"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/friends"><spring:message code="navbar.friends"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/sendMoney"><spring:message code="navbar.send"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/accountVerwalten/${studentSession.user.id}"><spring:message code="navbar.account"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/prelogout"><spring:message code="navbar.logout"></spring:message></a>
                </li>
            </ul>
            <ul class="navbar-nav ms-auto me-4">
                <li>
                    <a class="nav-link" href="?lang=en_US"><spring:message code="navbar.en"></spring:message></a>
                </li>
                <li>
                    <a class="nav-link" href="?lang=de_DE"><spring:message code="navbar.de"></spring:message></a>
                </li>
            </ul>
            <ul id="weather" class="navbar-nav">
                <li>
                    <span class="navbar-text">
                        <div class="row">
                            <div class="col-5">
                                <div class="row">
                                    <spring:message code="navbar.weather.temp"></spring:message>: ${temp}

                                </div>
                                <div class="row">
                                    <spring:message code="navbar.weather.feel"></spring:message>: ${rf}

                                </div>
                            </div>
                            <div class="col-5">
                                <img src="${wet}" alt="SVG mit img laden" width="64" height="64">
                            </div>
                        </div>
                    </span>
                </li>
            </ul>

        </div>
    </nav>
</header>
<br><br>
<br><br><br>
<div class="alert alert-success" role="alert">
    <h4 class="mt-1 mb-2 pb-1"><spring:message code="student_list.head"></spring:message> - ${studentSession.user.name}</h4>
</div>
<br>
<br><br>
<table class="table" border="2" width="70%" cellpadding="2">

    <tr>
        <th scope="col"><spring:message code="student_list.table.mail"></spring:message></th>
        <th scope="col"><spring:message code="student_list.table.matr"></spring:message></th>
        <th scope="col"><spring:message code="student_list.table.surname"></spring:message></th>
        <th scope="col"><spring:message code="student_list.table.name"></spring:message></th>
        <th scope="col"><spring:message code="student_list.table.type"></spring:message></th>
        <th scope="col"><spring:message code="student_list.table.provider"></spring:message></th>
    </tr>

    <tr>
        <td>${studentSession.user.email}</td>
        <td>${studentSession.user.matrikelnummer}</td>
        <td>${studentSession.user.nachname}</td>
        <td>${studentSession.user.name}</td>
        <td>${studentSession.user.type}</td>
        <td>${studentSession.user.authProvider}</td>
    </tr>

</table>
<br>

<div>
    <a href="/user/update/${studentSession.user.id}" class="btn btn-outline-primary ui-state-active" type="button"
       role="button"><spring:message code="student_list.table.btn"></spring:message></a>
</div>


</body>


</html>