<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <title><spring:message code="manager.title"></spring:message></title>

</head>
<body>

<div class="alert alert-success" role="alert">
    <h4 class="mt-1 mb-2 pb-1"><spring:message code="manager.head"></spring:message> - ${managerSession.name}</h4>
</div>
<br>
<h4 class="mt-1 mb-5 pb-1">
    <spring:message code="manager.welcome"></spring:message>,<%=request.getRemoteUser()%> ! | <a href="/prelogout"> <spring:message code="manager.logout"></spring:message></a>
</h4>

</body>

</html>