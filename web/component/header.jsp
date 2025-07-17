<%-- 
    Document   : header
    Created on : May 24, 2025, 9:44:52 AM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="model.user.User" scope="session" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/component/ComponentStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Header</title>
    </head>
    <body>
        <header class="header row">
            <div class="logo col-md-2">

                <img src="${pageContext.request.contextPath}/resource/images/logo.png" alt="logo"/>
            </div>
            <div class="search-container col-md-6">
                <input type="text" placeholder="Search">
                <button class="search-button">
                    <i class="fa fa-search"></i>
                </button>                
            </div>

            <c:if test="${empty user}">
                <div class="startBtn col-md-4 row">
                    <button id="loginBtn">Log In</button>
                    <button id="signupBtn">Sign Up</button>
                </div>
            </c:if>
            <c:if test="${not empty user && user.role == 'learner'}">
                <i class="fa-solid fa-cart-shopping"></i>
            </c:if >
            <c:if test="${not empty user}">
                <i class="far fa-envelope"></i>
                <i class="fa-regular fa-bell"></i>
                <i class="fa-solid fa-circle-user"></i>
            </c:if >
        </header>    
    </body>
    <script>
        document.getElementById("loginBtn").onclick = function () {
            window.location.href = "${pageContext.request.contextPath}/login";
        };
        document.getElementById("signupBtn").onclick = function () {
            window.location.href = "${pageContext.request.contextPath}/signup";
        };
        document.getElementById("signupInsBtn").onclick = function () {
            window.location.href = "${pageContext.request.contextPath}/signup-instructor";
        };

    </script>
</html>
