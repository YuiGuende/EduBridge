<%-- 
    Document   : header
    Created on : May 24, 2025, 9:44:52 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="component/ComponentStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Header</title>
    </head>
    <body>
        <header class="header row">
            <div class="logo col-md-2">
                <img src="resource/images/logo.png" alt="logo"/>
            </div>
            <div class="search-container col-md-6">
                <input type="text" placeholder="Search">
                <button class="search-button">
                    <i class="fa fa-search"></i>
                </button>                
            </div>
            <div class="startBtn col-md-4 row">
                <button id="loginBtn">Log In</button>
                <button id="signupBtn">Sign Up</button>
            </div>
        </header>    
    </body>
    <script>
        document.getElementById("loginBtn").onclick = function () {
            window.location.href = "${pageContext.request.contextPath}/login";
        };
        document.getElementById("signupBtn").onclick = function () {
            window.location.href = "${pageContext.request.contextPath}/signup";
        };
    </script>
</html>
