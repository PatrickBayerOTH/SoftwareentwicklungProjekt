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
    <title>Home MANAGER</title>

</head>
<body>

<div class="alert alert-success" role="alert">
    <h4 class="mt-1 mb-2 pb-1">Profile MANAGER - ${managerSession.name}</h4>
</div>
<br>
<h4 class="mt-1 mb-5 pb-1">
    Welcome,<%=request.getRemoteUser()%> ! | <a href="/prelogout"> Logout</a>
</h4>

</body>

</html>