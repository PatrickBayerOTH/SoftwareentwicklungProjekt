<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="en">

<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="main.title"></spring:message></title>
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

    <div class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">

        <h1 class=" text-start display-4 fw-normal"><spring:message code="main.account.head.1"></spring:message></h1>
        <h3 class=" text-start display-6 fw-normal"><spring:message code="main.account.head.2"></spring:message></h3>
        <p class="lead fw-normal"><spring:message code="main.account.p"></spring:message></p>
        <a class="btn btn-outline-secondary" href="/account"><spring:message code="main.account.btn"></spring:message></a>
        <div class="product-device shadow-sm d-none d-md-block"></div>
        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
    </div>
    <div class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">
        <div class="col-md-12">
            <div class="row ">

                <h1 class=" text-start display-4 fw-normal col-md-6"><spring:message code="main.friends.head"></spring:message></h1>
                <a class="btn btn-primary col-md-3 " href="#" style="padding: 20px;"><spring:message code="main.friends.search"></spring:message></a>
            </div>
        </div>
        <p class="lead fw-normal"><spring:message code="main.friends.p"></spring:message></p>
        <a class="btn btn-outline-secondary" href="#"><spring:message code="main.friends.btn"></spring:message></a>

        <div class="product-device shadow-sm d-none d-md-block"></div>
        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
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
        padding-top: 70px;
    }

</style>
