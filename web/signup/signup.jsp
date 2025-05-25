<%-- 
    Document   : signup.jsp
    Created on : May 24, 2025, 10:46:45 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="signup/SignupStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Signup</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <div class="signup-form row">
            <div class="col-md-6">
                <img src="resource/images/banner-login.jpg" alt="banner-signup">
            </div>
            <div class="signup-form-right col-md-6">
                <p>${error}</p>
                <form action="signup" method="POST">
                    <h1>Sign up to start your learning journey</h1>
                    <input type="text" name="email" placeholder="Email" required/>
                    <input type="text" name="fullname" placeholder="Full Name" required/>
                    <input type="password" name="password" placeholder="Password" required/>
                    <input type="password" name="confirm" placeholder="Confirm Password" required/>
                    <input type="submit" value="SIGN UP" id="signup"/>
                </form> 
                <div class="separator text-center my-3">
                    <span>Other options</span>
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
                    <span>Already have an account?</span>
                    <a href="${pageContext.request.contextPath}/login">Log In</a>
                </div>
            </div>
        </div>
        <%@ include file="/component/footer.jsp" %>   
    </body>
</html>
