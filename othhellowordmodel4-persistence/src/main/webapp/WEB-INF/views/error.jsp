<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="error.title"></spring:message></title>

</head>
<body>

<hr>

<div class="alert alert-danger" role="alert">
    <spring:message code="error.head"></spring:message>
</div>

<div>
    <a href="/login" class="btn btn-outline-primary ui-state-active" type="button"
       role="button"><spring:message code="error.login"></spring:message></a>
</div>

</body>


</html>