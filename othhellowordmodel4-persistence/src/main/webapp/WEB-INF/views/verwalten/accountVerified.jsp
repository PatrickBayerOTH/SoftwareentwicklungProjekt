<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="account_verified.title"></spring:message></title>

</head>
<body>
<div class="alert alert-success" role="alert">
    <h3><spring:message code="account_verified.head"></spring:message></h3>
</div>
<br>
<div class=" d-grid">
    <a href="/login" class="btn btn-outline-dark"><spring:message code="account_verified.login"></spring:message></a>
</div>
</body>
</html>