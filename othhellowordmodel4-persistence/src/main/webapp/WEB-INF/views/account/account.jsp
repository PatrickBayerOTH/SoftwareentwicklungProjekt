<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!doctype html>
<html lang="en">

<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="account.title"></spring:message></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
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
                    <a class="nav-link" href="http://localhost:8080/activity"><spring:message code="navbar.activities"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/friends"><spring:message code="navbar.friends"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/sendMoney"><spring:message code="navbar.send"></spring:message></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="user/accountVerwalten/${studentSession.user.id}"><spring:message code="navbar.account"></spring:message></a>
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
    <script src="https://www.paypal.com/sdk/js?client-id=test&currency=USD"></script>
    <div class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">
        <h1><spring:message code="account.head"></spring:message></h1>
        <div>
            <h3><spring:message code="account.current"></spring:message>: ${credit}</h3>
        </div>
        <form:form method="POST" action="/paypal" modelAttribute="order">
            <h5><spring:message code="account.form.head"></spring:message></h5>
            <label for="amount"><spring:message code="account.form.amount"></spring:message></label>
            <form:input type="number" id="amount" name="amount" path="price" min="1" step=".01"/>
            <form:input type="hidden" path="description" value="UniPay"/>
            <spring:message code="account.form.btn" var="btn"></spring:message>
            <input class="btn btn-outline-primary ui-state-active" type="submit" value="${btn}"/>
        </form:form>
        <div class="table-responsive-md mt-3">
            <table class="table table-striped table-hover display">
                <thead>
                    <tr>
                        <th class="text-center"><spring:message code="account.table.sender"></spring:message></th>
                        <th class="text-center"><spring:message code="account.table.receiver"></spring:message></th>
                        <th class="text-center"><spring:message code="account.table.amount"></spring:message></th>
                        <th class="text-center"><spring:message code="account.table.date"></spring:message></th>
                    </tr>
                </thead>
                <c:forEach var="transaction" items="${transfers}" varStatus="index">
                    <tr>
                        <td>
                        ${sender[index.index]}
                        </td>
                        <td>
                        ${receiver[index.index]}
                        </td>
                        <td>
                        ${transaction.amount}
                        </td>
                        <td>
                        ${date[index.index]}
                        </td>
                    </tr>
                </c:forEach>


            </table>
        </div>
    </div>




    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>

</html>
<style>
    .div-1 {
        height: 400px;
        background-color: #EBEBEB;
    }

    .div-2 {
        height: 400px;
        background-color: #ABBAEA;
    }

    .div-3 {
        height: 400px;
        background-color: #FBD603;
    }

    .img1 {
        position: relative;
        top: 0px;
        right: 0px;
        width: 100px;
        height: 100px;

    }

    .img2 {
        position: relative;
        top: 80px;
        right: 0px;
        width: 100px;
        height: 100px;

    }
    body {
        padding-top: 70px
    }

</style>
