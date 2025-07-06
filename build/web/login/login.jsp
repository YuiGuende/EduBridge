<%-- 
    Document   : Login
    Created on : May 21, 2025, 9:00:48 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="user" class="model.user.User" scope="request"></jsp:useBean>

    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <link rel="stylesheet" href="login/LoginStyles.css">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
            <title>Login</title>
        </head>
        <body>
        <jsp:include page="/component/header.jsp" />
        <div class="login-form row">
            <div class="col-md-6">
                <img src="resource/images/banner-login.jpg" alt="banner-login">
            </div>
            <div class="login-form-right col-md-6">
                <form action="login" method="POST">
                    <h1>Log in to continue your learning journey</h1>
                    <c:choose>
                        <c:when test="${not empty successMessage}">
                            <p id="successMsg">
                                ${successMessage}
                            </p>
                        </c:when>
                        <c:when test="${not empty error}">
                            <p id="errorMsg">
                                ${error}
                            </p>
                        </c:when>
                    </c:choose>
                    <input type="text" name="email" placeholder="Email" value="${user.email}" required/>
                    <input type="password" name="password" placeholder="Password" value="${user.password}" required/>
                    <label>
                        <input type="checkbox" name="remember" value="on"
                               ${rememberMeChecked ? "checked" : ""} id="remember"/>
                        Remember me
                    </label>
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
                    <a href="${pageContext.request.contextPath}/signup">Sign Up</a>
                </div>
            </div>
        </div>
        <%@ include file="/component/footer.jsp" %>
    </body>
</html>
