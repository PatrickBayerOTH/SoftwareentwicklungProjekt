<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</head>

<body>
    <header>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a class="navbar-brand" href="/home">UniPay</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
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
    </header>





    <div id="main" class="container">
        <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
            <div class="spacesup"></div>
            <div class="text-center text-uppercase">
                <h4>Suche Freunde</h4>

            </div>
            <!-- Info Alert -->
            <c:if test="${success eq 'Erfolgreich'}">
                <div class="alert alert-success alert-dismissible fade show" th:if=${success}>
                    <h2>${success}</h2>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>



            <c:if test="${success eq 'Bereits vorhanden'}">
                <div class="alert alert-danger alert-dismissible fade show" th:if=${success}>
                    <h2>${success}</h2>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>





            <spring:url value="/selectFriend" var="selectUrl" />

            <form:form class="form-row" modelAttribute="friendForm" action="${selectUrl}">

                <div class="form-group col-sm-1"></div>
                <spring:bind path="name">
                    <div class="form-group col-sm-8  ${status.error ? 'has-error' : ''}">
                        <form:input path="name" type="text" class="form-control" id="name" required="required" />
                        <form:errors path="name" class="control-label" />
                    </div>
                </spring:bind>

                <div class="form-group col-sm-3 d-flex justify-content-center justify-content-md-start">
                    <button type="submit" class="btn btn-outline-secondary"><i class="fas fa-search"></i> <span class="esconder"> Search</span></button>
                </div>
            </form:form>

            <div class="product-device shadow-sm d-none d-md-block"></div>
            <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
        </div>




        <c:if test="${not empty students}">

            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="text-center mt-5">
                    <h5>Ergebnisse deiner Suche:</h5>
                </div>

                <div class="table-responsive-md mt-3">
                    <table class="table table-striped table-hover display">
                        <thead>
                            <tr>
                                <th style="display:none;" class="text-center">Id</th>
                                <th class="text-center">Name</th>
                                <th class="text-center">Nachname</th>
                            </tr>
                        </thead>

                        <c:forEach var="student" items="${students}">
                            <tr>


                                <td style="display:none;">${student.id}</td>

                                <td>${student.name}</td>
                                <td>${student.nachname}</td>

                                <td class="text-center">

                                    <button onclick="location.href='http://localhost:8080/addFriend/${student.id}'" class="btn btn-success"><svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-person-fill-add" viewBox="0 0 16 16">
                                            <path d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0Zm-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                            <path d="M2 13c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Z" />
                                        </svg></button>


                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                <div class="product-device shadow-sm d-none d-md-block"></div>
                <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
            </div>



        </c:if>
        <c:if test="${empty students}">

            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="text-center mt-5">
                    <h5>Gib etwas in die Suchleiste ein um nach Freunden zu suchen:) </h5>
                </div>
                <div class="product-device shadow-sm d-none d-md-block"></div>
                <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
            </div>


        </c:if>
        <c:if test="${deleted eq 'Freund entfernt'}">
            <div class="alert alert-success alert-dismissible fade show" th:if=${deleted}>
                <center>
                    <h2>${deleted}</h2>
                </center>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty friends}">
            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="text-center mt-5">
                    <h5>Deine Freunde:</h5>
                </div>

                <div class="table-responsive-md mt-3">
                    <table class="table table-striped table-hover display">
                        <thead>
                            <tr>
                                <th style="display:none;" class="text-center">Id</th>
                                <th class="text-center">Name</th>
                                <th class="text-center">Nachname</th>

                            </tr>
                        </thead>


                        <c:forEach var="friend" items="${friends}" varStatus="status">
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
        <c:if test="${empty friends}">
            <div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
                <div class="text-center mt-5">
                    <h5>Du hast noch keine Freunde </h5>
                </div>
                <div class="product-device shadow-sm d-none d-md-block"></div>
                <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
            </div>


        </c:if>


    </div>

</body>

</html>
<style>
    #main {
        margin-top: 100px;

    }

    #mess {
        margin-top: 100px;

    }

</style>
