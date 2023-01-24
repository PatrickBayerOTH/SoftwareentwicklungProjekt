<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <title><spring:message code="student_added.title"></spring:message></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>

<div class="alert alert-success" role="alert">
    ${name} <br>
     <spring:message code="student_added.head"></spring:message>
    ${email}
</div>

<br>
<div class="alert alert-success" role="alert">
    <spring:message code="student_added.message"></spring:message>
</div>
<br>

<div class=" d-grid">
    <a href="/login" class="btn btn-outline-dark"><spring:message code="student_added.login"></spring:message></a>
</div>

</body>
</html>