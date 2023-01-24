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
    <title><spring:message code="send_friend.title"></spring:message></title>
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

    <div id="main">
        <div id="senden" class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">
            <div class="col-md-12">
                <div class="row ">
                    <c:if test="${targetfriend eq 'targetfriend'}">
                        <spring:url value="/sendMoney/${id}" var="selectUrl" />
                        <div class="row">
                            <div class="col">
                                <form:form class="form-row" modelAttribute="sendForm" action="${selectUrl}">
                                    <div class="form-group col-sm-1"></div>
                                    <spring:bind path="amount">
                                        <div class="form-group col-sm-8  ${status.error ? 'has-error' : ''}">
                                            <spring:message code="send_friend.form.amount" var = "amount"></spring:message>
                                            <form:input path="amount" type="number" step="0.01" min="1" max="${kontostand}" class="form-control" id="amount" required="required" placeholder="${amount}" />
                                            <form:errors path="amount" class="control-label" />

                                        </div>
                                    </spring:bind>
                                    <spring:bind path="message">
                                        <div class="form-group col-sm-8  ${status.error ? 'has-error' : ''}">
                                            <spring:message code="send_friend.form.message" var ="message"></spring:message>
                                            <form:input path="message" type="text" class="form-control" id="amount" required="required" placeholder="${message}" />
                                            <form:errors path="message" class="control-label" />

                                        </div>
                                    </spring:bind>

                                    <div class="form-group col-sm-3 d-flex justify-content-center justify-content-md-start">
                                        <button type="submit" class="btn btn-success"><svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-cash-coin" viewBox="0 0 16 16">
                                                <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z" />
                                                <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z" />
                                                <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z" />
                                                <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z" />
                                            </svg></button>
                                    </div>


                                </form:form>
                            </div>
                            <div class="col">
                                <h3><spring:message code="send_friend.form.available"></spring:message>: ${kontostand}</h3>


                            </div>
                        </div>
                    </c:if>

                    <h1 class=" text-start display-4 fw-normal col-md-6"><spring:message code="send_friend.form.head"></spring:message></h1>

                </div>
            </div>





            <p class="lead fw-normal"><spring:message code="send_friend.form.content"></spring:message></p>


            <div class="product-device shadow-sm d-none d-md-block"></div>
            <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
        </div>


        <c:if test="${targetfriend eq 'targetfriend'}">


            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <c:if test="${not empty transactions}">
                    <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                        <div class="text-center mt-5">
                            <h5><spring:message code="send_friend.table.head"></spring:message></h5>
                        </div>

                        <div class="table-responsive-md mt-3">
                            <table class="table table-striped table-hover display">
                                <thead>
                                    <tr>
                                        <th class="text-center"><spring:message code="send_friend.table.sender"></spring:message></th>
                                        <th class="text-center"><spring:message code="send_friend.table.receiver"></spring:message></th>
                                        <th class="text-center"><spring:message code="send_friend.table.amount"></spring:message></th>
                                        <th class="text-center"><spring:message code="send_friend.table.message"></spring:message></th>
                                        <th class="text-center"><spring:message code="send_friend.table.date"></spring:message></th>
                                    </tr>
                                </thead>
                                <c:forEach var="transaction" items="${transactions}" varStatus="index">
                                    <tr>
                                        <td>
                                            <c:if test="${directions[index.index] eq '0'}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill=#00ff00 class="bi bi-arrow-down-left" viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd" d="M2 13.5a.5.5 0 0 0 .5.5h6a.5.5 0 0 0 0-1H3.707L13.854 2.854a.5.5 0 0 0-.708-.708L3 12.293V7.5a.5.5 0 0 0-1 0v6z" />
                                                </svg>

                                            </c:if>
                                            <c:if test="${directions[index.index] ne '0'}">
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill=#ff0000 class="bi bi-arrow-down-right" viewBox="0 0 16 16">
                                                    <path fill-rule="evenodd" d="M14 13.5a.5.5 0 0 1-.5.5h-6a.5.5 0 0 1 0-1h4.793L2.146 2.854a.5.5 0 1 1 .708-.708L13 12.293V7.5a.5.5 0 0 1 1 0v6z" />
                                                </svg>

                                            </c:if>
                                        </td>


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
                                            ${transaction.message}
                                        </td>
                                        <td>
                                            ${date[index.index]}
                                        </td>
                                    </tr>
                                </c:forEach>


                            </table>
                        </div>
                        <div class="product-device shadow-sm d-none d-md-block"></div>
                        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
                    </div>




                </c:if>
                <c:if test="${empty transactions}">
                    <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                        <div class="text-center mt-5">
                            <h5><spring:message code="send_friend.notable.head"></spring:message> </h5>
                        </div>
                        <div class="product-device shadow-sm d-none d-md-block"></div>
                        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
                    </div>


                </c:if>
            </div>



        </c:if>

        <c:if test="${targetfriend eq 'notargetfriend'}">
            <c:if test="${targetfriend eq 'notargetfriend'}">


                <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                    <c:if test="${not empty transactions}">
                        <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                            <div class="text-center mt-5">
                                <h5><spring:message code="send_friend.table.head"></spring:message></h5>
                            </div>

                            <div class="table-responsive-md mt-3">
                                <table class="table table-striped table-hover display">
                                    <thead>
                                        <tr>
                                            <th class="text-center"><spring:message code="send_friend.table.direction"></spring:message></th>
                                            <th class="text-center"><spring:message code="send_friend.table.sender"></spring:message></th>
                                            <th class="text-center"><spring:message code="send_friend.table.receiver"></spring:message></th>
                                            <th class="text-center"><spring:message code="send_friend.table.amount"></spring:message></th>
                                            <th class="text-center"><spring:message code="send_friend.table.message"></spring:message></th>
                                            <th class="text-center"><spring:message code="send_friend.table.date"></spring:message></th>
                                        </tr>
                                    </thead>
                                    <c:forEach var="transaction" items="${transactions}" varStatus="index">
                                        <tr>
                                            <td>
                                                <c:if test="${directions[index.index] eq '0'}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill=#00ff00 class="bi bi-arrow-down-left" viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd" d="M2 13.5a.5.5 0 0 0 .5.5h6a.5.5 0 0 0 0-1H3.707L13.854 2.854a.5.5 0 0 0-.708-.708L3 12.293V7.5a.5.5 0 0 0-1 0v6z" />
                                                    </svg>

                                                </c:if>
                                                <c:if test="${directions[index.index] ne '0'}">
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill=#ff0000 class="bi bi-arrow-down-right" viewBox="0 0 16 16">
                                                        <path fill-rule="evenodd" d="M14 13.5a.5.5 0 0 1-.5.5h-6a.5.5 0 0 1 0-1h4.793L2.146 2.854a.5.5 0 1 1 .708-.708L13 12.293V7.5a.5.5 0 0 1 1 0v6z" />
                                                    </svg>

                                                </c:if>
                                            </td>
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
                                                ${transaction.message}
                                            </td>
                                            <td>
                                                ${date[index.index]}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                            <div class="product-device shadow-sm d-none d-md-block"></div>
                            <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
                        </div>
                    </c:if>
                    <c:if test="${empty transactions}">
                        <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                            <div class="text-center mt-5">
                                <h5><spring:message code="send_friend.notable.head"></spring:message> </h5>
                            </div>
                            <div class="product-device shadow-sm d-none d-md-block"></div>
                            <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
                        </div>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${not empty currfriends}">

                <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                    <div class="table-responsive-md mt-3">

                        <table class="table table-striped table-hover display">
                            <thead>
                                <tr>
                                    <th style="display:none;" class="text-center"><spring:message code="send_friend.table.id"></spring:message></th>
                                    <th class="text-center"><spring:message code="send_friend.table.name"></spring:message></th>
                                    <th class="text-center"><spring:message code="send_friend.table.surname"></spring:message></th>

                                    <h5><spring:message code="send_friend.table.choose"></spring:message></h5>
                                </tr>
                            </thead>

                            <c:forEach var="friend" items="${currfriends}" varStatus="status">
                                <tr>
                                    <td>${studfriends[status.index].name}
                                    </td>
                                    <td>${studfriends[status.index].nachname}
                                    </td>
                                    <td style="display:none;">${friend.friendId}
                                    </td>
                                    <td class="text-center ">
                                        <button onclick="location.href='http://localhost:8080/sendMoney/${friend.friendId}'" type="button" class="btn btn-primary"><svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-cash-coin" viewBox="0 0 16 16">
                                                <path fill-rule="evenodd" d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z" />
                                                <path d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z" />
                                                <path d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z" />
                                                <path d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z" />
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
        </c:if>
        <c:if test="${empty currfriends}">
            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="table-responsive-md mt-3">
                    <h5><spring:message code="send_friend.notable.nofriends"></spring:message></h5>
                </div>
                <button onclick="location.href='http://localhost:8080/selectFriend'" type="button" class="btn btn-primary"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill-add" viewBox="0 0 16 16">
                        <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0Zm-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                        <path d="M2 13c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Z" />
                    </svg>
                </button>
                <div class="product-device shadow-sm d-none d-md-block"></div>
                <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
            </div>
        </c:if>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>

</html>
<style>
    .div-1 {
        margin-top: 200px;
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

    #main {
        top: 300px;
    }

    body {
        padding-top: 70px;
    }

</style>
