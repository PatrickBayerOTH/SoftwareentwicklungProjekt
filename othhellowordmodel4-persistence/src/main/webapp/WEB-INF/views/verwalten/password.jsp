<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title><spring:message code="password.title"></spring:message></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
<div class="container text-center">
    <br>

    <div class="alert alert-success" role="alert">
        <spring:message code="password.title"></spring:message>
    </div>

    <form:form method="POST" modelAttribute="neuPassword" action="/user/email-sender-password">
        <form:hidden path="id" cssClass="form-control"/>
        <label for="email"><spring:message code="password.form.mail"></spring:message></label>
        <br><br>
        <form:input path="email" cssClass="form-control"/>

        <br>
        <input type="submit" value="reset" class="btn btn-primary">

    </form:form>

</div>
</body>
</html>