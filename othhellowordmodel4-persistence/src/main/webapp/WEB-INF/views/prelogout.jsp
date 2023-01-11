<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>Logout</title>

</head>
<body>
<br>
<br>
<div class="alert alert-dark" role="alert">
    Are you sure you want to leave?
</div>
<br>
<div>
    <a href="/login" class="btn btn-outline-warning ui-state-active" type="button"
       role="button">logout</a>
</div>
<br>

<div>
    <a href="/home" class="btn btn-outline-primary ui-state-active" type="button"
       role="button">Home</a>
</div>

</body>
</html>