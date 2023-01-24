<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="student_update.title"></spring:message></title>

</head>
<body>

<!-- Main Navigation -->

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
                    <a class="nav-link" href="/home"><spring:message code="navbar.home"></spring:message></a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="http://localhost:8080/mainpage"><spring:message code="navbar.mainpage"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/activity"><spring:message code="navbar.activities"></spring:message></a>
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
            <ul id="weather" class="navbar-nav ms-auto">
                <li>
                    <span class="navbar-text">
                        <div class="row">
                            <div class="col">
                                <div class="row">
                                    <spring:message code="navbar.weather.temp"></spring:message>: ${temp}

                                </div>
                                <div class="row">
                                    <spring:message code="navbar.weather.feel"></spring:message>: ${rf}

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
</header>
<br><br><br><br><br>

<form:form method="POST" modelAttribute="studentForm" action="/user/update/process">
    <form:hidden path="id" cssClass="form-control"/>

    <label for="email"><spring:message code="student_update.form.mail"></spring:message></label>
    <form:input readonly="true" path="email" cssClass="form-control"/>

    <label for="matrikelnummer"><spring:message code="student_update.form.matr"></spring:message></label>
    <form:input readonly="true" path="matrikelnummer" cssClass="form-control"/>

    <label for="nachname"><spring:message code="student_update.form.surname"></spring:message></label>
    <form:input path="nachname" cssClass="form-control"/>

    <label for="name"><spring:message code="student_update.form.name"></spring:message></label>
    <form:input path="name" cssClass="form-control"/>

    <label for="password"><spring:message code="student_update.form.password"></spring:message></label>
    <form:input path="password" cssClass="form-control"/>

    <label for="type"><spring:message code="student_update.form.type"></spring:message></label>
    <form:input path="type" cssClass="form-control"/>

    <label for="authProvider"><spring:message code="student_update.form.provider"></spring:message></label>
    <form:input readonly="true" path="authProvider" cssClass="form-control"/>

    <br>

    <input type="submit" value="Update" class="btn btn-primary">

</form:form>


</body>

</html>