<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="password_done.title"></spring:message></title>
</head>
<body>
<br><br>

<br>
<div class="alert alert-info" role="alert">
    <h4 class="mt-1 mb-2 pb-1"><spring:message code="password_done.head"></spring:message></h4>
</div>

<div>
    <a href="/login" class="btn btn-outline-primary ui-state-active" type="button"
       role="button"><spring:message code="password_done.login"></spring:message></a>
</div>

</body>
</html>