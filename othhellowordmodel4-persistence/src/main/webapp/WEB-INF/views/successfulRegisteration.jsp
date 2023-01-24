<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">

    <meta http-equiv="Refresh" content="20; url=/user/email"/>

    <title><spring:message code="registration_success.title"></spring:message></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>


<div class="alert alert-success" role="alert">
    <span><spring:message code="registration_success.head"></spring:message> ${emailId}</span>
</div>


</body>
</html>