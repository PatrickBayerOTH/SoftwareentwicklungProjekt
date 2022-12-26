<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="/login" var="loginUrl"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login Seite</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>

<form action="${loginUrl}" method="post">
    <div class="vh-100 d-flex justify-content-center align-items-center">
        <div class="container">
            <div class="row d-flex justify-content-center">
                <div class="col-12 col-md-8 col-lg-6">
                    <div class="card bg-white">
                        <div class="card-body p-5">
                            <form class="mb-3 mt-md-4">
                                <h2 class="fw-bold mb-2 text-uppercase ">Login</h2>
                                <p class=" mb-5">Please enter your Matrikelnummer and password!</p>
                                <div class="mb-3">
                                    <label for="user.username" class="form-label ">Matrikelnummer</label>
                                    <input type="text" class="form-control" id="user.username" name="user.username"
                                           placeholder="Matrikelnummer">
                                </div>
                                <div class="mb-3">
                                    <label for="user.password" class="form-label ">Password</label>
                                    <input type="password" class="form-control" id="user.password" name="user.password"
                                           placeholder="*******">
                                </div>
                                <p class="small"><a class="text-primary" href="forget-password.html">Forgot
                                    password?</a></p>
                                <div class="d-grid">
                                    <button class="btn btn-outline-dark" type="submit"><a href="/home"></a>Login
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div>
                            <p class="mb-0  text-center">Don't have an account? <a href="user/student/add" class="text-primary fw-bold">Sign Up </a></p>
                        </div>


                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

