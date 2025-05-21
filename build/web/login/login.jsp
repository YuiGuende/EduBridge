<%-- 
    Document   : Login
    Created on : May 21, 2025, 9:00:48 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="login/LoginStyles.css">
        <title>Login</title>
    </head>
    <body>
        <header>
            <div class="logo">
                <img src="resource/images/logo.png" alt="logo"/>
            </div>
            <div class="search">
                <form>
                    <input type="text" placeholder="Search">
                    <button><i class="fa fa-search"></i></button>
                </form>

            </div>
            <div class="startBtn">
                <button id="loginBtn">Log In</button>
                <button id="signupBtn">Sign Up</button>
            </div>
        </header>
        <div class="login form">
            ${error}
            <div>
                <img src="resource/images/banner-login.jpg" alt="banner-login">
            </div>
            <form action="login" method="POST">
                <input type="text" name="username" placeholder="Username" />
                <input type="text" name="password" placeholder="Password" />
                <input type="submit" value="SIGN IN" />
            </form>             
        </div>
    </body>
</html>