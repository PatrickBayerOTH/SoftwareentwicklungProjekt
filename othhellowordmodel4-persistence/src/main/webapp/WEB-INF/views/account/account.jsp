<!doctype html>
<html lang="en">

<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Accountdetails</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>

    <header>
        <!-- Fixed navbar -->
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
                        <a class="nav-link" href="http://localhost:8080/sendMoney">Senden</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/prelogout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>

    <div class="overflow-hidden p-3 p-md-5 m-md-3 bg-light">
        <div class="table-responsive-md mt-3">
            <table class="table table-striped table-hover display">
                <thead>
                    <tr>
                        <th class="text-center">Id</th>
                    </tr>
                </thead>
                <c:forEach var="transaction" items="${transfers}" varStatus="status">
                    <tr>
                        <td>
                        ${sender[status.index]}
                        </td>
                        <td>
                        ${receiver[status.index]}
                        </td>
                        <td>
                        ${transaction.amount}
                        </td>
                        <td>
                        ${transaction.date}
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

</style>