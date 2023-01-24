<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/login" var="loginUrl"/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title><spring:message code="login.title" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>
<br><br>
<h4 class="mt-1 mb-5 pb-1 d-flex justify-content-center"><spring:message code="navbar.head" /></h4>

<div class="text-center">
    <div class="col-md-12">
        <a href="/oauth2/authorization/google"
           class="btn btn btn-lg btn-google btn-block text-uppercase btn-outline "><img
                src="https://img.icons8.com/color/16/000000/google-logo.png"><spring:message code="login.google"/></a>
    </div>
</div>
<br>

<p class="text-center"><spring:message code="login.content"/></p>

<form action="${loginUrl}" method="post" class="h-100 gradient-form" style="background-color: #eee;">

    <div class="mb-3">
        <p>
            <label for="username" class="form-label "><spring:message code="login.form.mail"/></label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Email"/>
            <small id="emailHelp" class="form-text text-muted">We'll never share your Data with anyone
                else.</small>
        </p>
    </div>
    <div class="mb-3">
        <p>
            <label for="password" class="form-label "><spring:message code="login.form.password"/></label>
            <input type="password" id="password" name="password" class="form-control" placeholder="*****"/>
        </p>
    </div>

    <div class="text-start">
        <p><a href="user/emailForPassword" target="_blank"
              class="text-decoration-none"><spring:message code="login.form.forgot"/></a></p>
    </div>

    <button type="submit" class="btn btn-primary"><spring:message code="login.form.btn"/></button>


    <div class="text-center">
        <p><spring:message code="login.form.register.1"/> <a href="user/student/add" class="text-decoration-none"><spring:message code="login.form.register.2"/></a></p>
    </div>


</form>

</body>
