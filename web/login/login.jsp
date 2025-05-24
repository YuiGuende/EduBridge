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
        <link rel="stylesheet" href="login/LoginStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Login</title>
    </head>
    <body>
        <div class="login-form row">
            <div class="col-md-6">
                <img src="resource/images/banner-login.jpg" alt="banner-login">
            </div>
            <div class="login-form-right col-md-6">
                ${error}
                <form action="login" method="POST">
                    <h1>Log in to continue your learning journey</h1>
                    <input type="text" name="username" placeholder="Username" />
                    <input type="password" name="password" placeholder="Password" />
                    <input type="submit" value="SIGN IN" id="signinBtn"/>
                </form> 
                <div class="separator text-center my-3">
                    <span>Other login options</span>
                </div>
                <div class="other-options">
                    <button class="btn btn-outline-danger">
                        <i class="fab fa-google"></i>
                    </button>
                    <button class="btn btn-outline-primary">
                        <i class="fab fa-facebook-f"></i>
                    </button>
                    <button class="btn btn-outline-linkedln">
                        <i class="fab fa-linkedin-in"></i>
                    </button>
                </div>
                <div class="text-center mt-4">
                    <span>Don't have an account?</span>
                    <a href="signup.jsp">Sign Up</a>
                </div>
            </div>
        </div>
    </body>
</html>
