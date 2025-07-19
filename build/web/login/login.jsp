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
            <div class="col-md-7">
                <img src="resource/images/banner-login.jpg" alt="banner-login">
            </div>
            <div class="login-form-right col-md-5">
                <form action="login" method="POST" class="needs-validation" novalidate>
                    <h1>Log in to continue your learning journey</h1>
                    <c:if test="${not empty successMessage}">
                        <p id="successMsg">
                            ${successMessage}
                        </p>
                    </c:if>
                    <c:if test="${not empty error}">
                        <p id="errorMsg">
                            ${error}
                        </p>
                    </c:if>
                    <div class="mb-2">
                        <input type="email" id="email" name="email"
                               class="form-control ${not empty emailError ? 'is-invalid' : ''}"
                               value="${user.email}" placeholder="Email" required />
                        <div class="invalid-feedback">
                            Please enter an email address.
                        </div>
                    </div>                    
                    <div class="mb-2 position-relative">
                        <input type="password" id="password" name="password"
                               class="form-control ${not empty passwordError ? 'is-invalid' : ''}"
                               placeholder="Password"
                               value="${user.password}"
                               required style="padding-right: 2.5rem;" />
                        <i class="fa fa-eye"
                           onclick="togglePassword('password', this)"
                           style="position: absolute; top: 0.75rem; right: 1.3rem; cursor: pointer;"></i>
                        <div class="invalid-feedback">
                            Please enter a password.
                        </div>
                    </div>
                    <label>
                        <input type="checkbox" name="remember" value="true"
                               ${rememberMeChecked ? "checked" : ""} id="remember"/>
                        Remember me
                    </label>
                    <div class="d-grid mb-2">
                        <button type="submit" class="btn btn-primary">SIGN IN</button>
                    </div>                </form> 
                    <jsp:include page="/component/socialLogin/socialLogin.jsp">
                        <jsp:param name="role" value="login"/>
                    </jsp:include>
                <div class="text-center mt-4">
                    <span>Don't have an account?</span>
                    <a href="${pageContext.request.contextPath}/signup">Sign Up</a>
                </div>
            </div>
        </div>
        <%@ include file="/component/footer.jsp" %>
        <script>
            (function () {
                'use strict';
                const forms = document.querySelectorAll('.needs-validation');
                Array.from(forms).forEach(function (form) {
                    form.addEventListener('submit', function (event) {
                        if (!form.checkValidity()) {
                            event.preventDefault();
                            event.stopPropagation();
                        }

                        form.classList.add('was-validated');
                    }, false);
                });
            })();

            function togglePassword(fieldId, icon) {
                const input = document.getElementById(fieldId);
                const isPassword = input.type === "password";
                input.type = isPassword ? "text" : "password";
                icon.classList.toggle("fa-eye");
                icon.classList.toggle("fa-eye-slash");
            }
        </script>
    </body>
</html>
