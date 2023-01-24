<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title><spring:message code="matr.title"></spring:message></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
<div class="container text-center">
    <br>

    <div class="alert alert-success" role="alert">
        <spring:message code="matr.head"></spring:message>
    </div>

    <form:form method="POST" modelAttribute="neuMatrikelnummer"
               action="user/matrikelNummer/process/${studentSession.user.id}">
        <form:hidden path="id" cssClass="form-control"/>

        <label for="matrikelnummer"><spring:message code="matr.form.nr"></spring:message></label>
        <form:input path="matrikelnummer" cssClass="form-control"/>

        <br>

        <label for="type"><spring:message code="matr.form.type"></spring:message></label>
        <form:input path="type" cssClass="form-control"/>
        <br>

        <input type="submit" value="add" class="btn btn-primary">

    </form:form>

</div>
</body>
</html>