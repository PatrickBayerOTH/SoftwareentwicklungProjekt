<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>


<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title>ERROR</title>

</head>
<body>

<hr>

<div class="alert alert-danger" role="alert">
    Error, bitte melden sie sich nochmal an, oder geben Sie eine gültige Email an
</div>


<div>
    ${errors}
</div>


<br>
<div>
    <button type="button" class="alert alert-primary"><a href="/home">Home</a></button>
</div>
<br>
<div>
    <button type="button" class="alert alert-primary"><a href="/login">Login</a></button>
</div>

</body>


</html>