<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">

    <meta http-equiv="Refresh" content="20; url=/user/email"/>

    <title>Student Event</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>

<div class="alert alert-success" role="alert">
    Mit der Email: ${email} und dem Name: ${name} sind Sie im System
</div>

<br>
<div class="alert alert-success" role="alert">
    Bitte verifizieren Sie Ihre Email
</div>
<br>

<div class=" d-grid">
    <a href="/login" class="btn btn-outline-dark">Login</a>
</div>

</body>
</html>