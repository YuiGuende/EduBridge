<%-- 
    Document   : signup.jsp
    Created on : May 24, 2025, 10:46:45 AM
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
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
            <link rel="stylesheet" href="signup/SignupStyles.css">
            <title>Signup</title>
        </head>
        <body>
        <jsp:include page="/component/header.jsp" />
        <div class="signup-form row">
            <div class="col-md-7">
                <img src="resource/images/banner-login.jpg" alt="banner-signup">
            </div>
            <div class="signup-form-right col-md-5">
                <form action="signup" method="POST" class="needs-validation" novalidate>
                    <h1>Sign up to start your learning journey</h1>
                    <c:if test="${not empty error}">
                        <p>${error}</p>
                    </c:if>
                    <div class="mb-2">
                        <input type="email" id="email" name="email"
                               class="form-control ${not empty emailError ? 'is-invalid' : ''}"
                               value="${user.email}" placeholder="Email" required />
                        <div class="invalid-feedback">
                            <c:out value="${emailError}" default="Please enter a valid email address." />
                        </div>
                    </div>                    
                    <div class="mb-2">
                        <input type="text" id="fullname" name="fullname"
                               class="form-control ${not empty fullnameError ? 'is-invalid' : ''}"
                               value="${user.fullname}" placeholder="Full name" required />
                        <div class="invalid-feedback">
                            <c:out value="${fullnameError}" default="Please enter your full name." />
                        </div>
                    </div>
                    <div class="mb-2 position-relative">
                        <input type="password" id="password" name="password"
                               class="form-control ${not empty passwordError ? 'is-invalid' : ''}"
                               placeholder="Password"
                               required style="padding-right: 2.5rem;" />
                        <i class="fa fa-eye"
                           onclick="togglePassword('password', this)"
                           style="position: absolute; top: 0.75rem; right: 1.3rem; cursor: pointer;"></i>
                        <div class="invalid-feedback">
                            <c:out value="${passwordError}" default="Please enter a password." />
                        </div>
                    </div>
                    <div class="mb-2 position-relative">
                        <input type="password" id="confirm" name="confirm"
                               class="form-control ${not empty confirmError ? 'is-invalid' : ''}"
                               placeholder="Confirm password"
                               required style="padding-right: 2.5rem;" />
                        <i class="fa fa-eye"
                           onclick="togglePassword('confirm', this)"
                           style="position: absolute; top: 0.75rem; right: 1.3rem; cursor: pointer;"></i>
                        <div class="invalid-feedback">
                            <c:out value="${confirmError}" default="Please confirm your password." />
                        </div>
                    </div>
                    <div class="d-grid mb-2">
                        <button type="submit" class="btn btn-primary">Sign Up</button>
                    </div>
                </form> 
                <jsp:include page="/component/socialLogin/socialLogin.jsp">
                    <jsp:param name="role" value="signup_learner" />
                </jsp:include>
                <div class="text-center mt-4">
                    <span>Already have an account?</span>
                    <a href="${pageContext.request.contextPath}/login">Log In</a>
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
