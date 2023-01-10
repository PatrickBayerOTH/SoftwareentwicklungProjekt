<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <title>Sign Up</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<body>
<div class="container text-center">
    <br>

    <div class="alert alert-success" role="alert">
        Bitte geben Sie ihre Matrikelnummer, um die Registrierung zu erfolgen.
    </div>

    <form:form method="POST" modelAttribute="neuMatrikelnummer" action="user/matrikelNummer/process/${studentSession.user.id}">
        <form:hidden path="id" cssClass="form-control"/>

        <label for="matrikelnummer">Marikelnummer</label>
        <form:input path="matrikelnummer" cssClass="form-control"/>

        <br>

        <input type="submit" value="Update" class="btn btn-primary">

    </form:form>
<%--
  <form:form method="POST" modelAttribute="neuMatrikelnummer" style="max-width: 600px; margin: 0 auto;"
               action="user/matrikelNummer/process/${studentSession.user.id}">
        <br><br>
        <div class="m-3">

            <div class="form-group row">
                <label class="col-4 col-form-label" for="user.matrikelnummer" >Matrikelnummer: </label>
                <div class="col-8">
                    <form:input path="user.matrikelnummer" type="text" id="user.matrikelnummer"
                                class="form-control"></form:input>
                </div>
            </div>
            <br>

            <button type="submit" class="btn btn-primary">Martikelnummer hinzufügen</button>

            <button type="reset" class="btn btn-primary">Reset</button>
            <br>
            <br>
        </div>
    </form:form>
--%>


</div>
</body>
</html>