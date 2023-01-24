<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html lang="en">

<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="friends.title"></spring:message></title>
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


    <div class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">
        <div class="col-md-12">
            <div class="row ">

                <h1 class=" text-start display-4 fw-normal col-md-6"><spring:message code="friends.head"></spring:message></h1>
                <a class="btn btn-primary col-md-3 " href="http://localhost:8080/selectFriend" style="padding: 20px;"><spring:message code="friends.search"></spring:message></a>
            </div>
        </div>





        <p class="lead fw-normal"><spring:message code="friends.content"></spring:message></p>


        <div class="product-device shadow-sm d-none d-md-block"></div>
        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
    </div>

    <div class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">
        <c:if test="${not empty currfriends}">
            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="text-center mt-5">
                    <h5><spring:message code="friends.table.head"></spring:message></h5>
                </div>

                <div class="table-responsive-md mt-3">
                    <table class="table table-striped table-hover display">
                        <thead>
                            <tr>
                                <th class="text-center"><spring:message code="friends.table.name"></spring:message></th>
                                <th class="text-center"><spring:message code="friends.table.surname"></spring:message></th>

                            </tr>
                        </thead>

                        <c:forEach var="friend" items="${currfriends}" varStatus="status">
                            <tr>

                                <td style="display:none;"> ${friend.friendId}</td>

                                <td>${studfriends[status.index].name}


                                </td>
                                <td>${studfriends[status.index].nachname}


                                </td>


                                <td class="text-center ">


                                    <button onclick="location.href='http://localhost:8080/sendMoney/${friend.friendId}'" type="button" class="btn btn-primary"><svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-cash-coin" viewBox="0 0 16 16">
                                            <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z" />
                                            <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z" />
                                            <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z" />
                                            <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z" />
                                        </svg>
                                    </button>

                                    <button onclick="location.href='http://localhost:8080/removeFriend/${friend.friendId}'" type="button" class="btn btn-danger"><svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-x-square-fill" viewBox="0 0 16 16">
                                            <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm3.354 4.646L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 1 1 .708-.708z" />
                                        </svg>
                                    </button>


                                </td>

                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="product-device shadow-sm d-none d-md-block"></div>
                <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
            </div>




        </c:if>
        <c:if test="${empty currfriends}">
            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="text-center mt-5">
                    <h5><spring:message code="friends.notable.head"></spring:message></h5>
                </div>
                <div class="product-device shadow-sm d-none d-md-block"></div>
                <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
            </div>

        </c:if>






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
