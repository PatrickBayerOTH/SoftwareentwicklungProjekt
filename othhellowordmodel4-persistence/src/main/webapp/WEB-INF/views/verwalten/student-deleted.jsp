<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
    <%--
        <meta http-equiv="Refresh" content="5; url=/studentProfessor/add"/>
    --%>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="student_deleted.title"></spring:message></title>
</head>
<body>


<br>
<h4>${msgs}</h4>
<br>

<div>
    <a class="btn btn-outline-primary ui-state-active" type="button" href="/login"
       role="button"><spring:message code="student_deleted.login"></spring:message></a>
</div>
<br>
<div>
    <a class="btn btn-outline-primary ui-state-active" type="button" href="/user/student/add"
       role="button"><spring:message code="student_deleted.singup"></spring:message></a>
</div>


</body>
</html>