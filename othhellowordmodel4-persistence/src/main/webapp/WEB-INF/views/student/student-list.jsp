<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>List of studentProfessors</title>

</head>
<body>


<table border="2" width="70%" cellpadding="2">

    <tr>
        <th>matrikelnummer</th>
        <th>universität</th>
        <th>name</th>
        <th>nachname</th>
        <th>email</th>
        <th>kontostand</th>
    </tr>


    <c:forEach var="studentProfessors" items="${studentProfessors}">
        <tr>
            <td>${studentProfessors.matrikelnummer}</td>
            <td>${studentProfessors.universität}</td>
            <td>${studentProfessors.name}</td>
            <td>${studentProfessors.nachname}</td>
            <td>${studentProfessors.email}</td>
            <td>${studentProfessors.kontostand}</td>

<%--            <td><a href="/studentProfessor/update/${studentProfessors.id}">Update</a></td>
            <td><a href="/studentProfessor/delete/${studentProfessors.id}">Delete</a></td>--%>

        </tr>
    </c:forEach>


</table>

<p><a href="/studentProfessor/add">Add Student </a></p>



</body>


</html>