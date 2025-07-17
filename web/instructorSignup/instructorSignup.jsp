<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.user.User" scope="request" />

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Instructor Signup</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Main Styles -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructorSignup/InstructorSignupStyles.css" />

        <!-- Bootstrap & Icons -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />

    </head>
    <body>

        <jsp:include page="/component/header.jsp" />

        <div class="container-fluid row">
            <div class="left-side col-md-7">
                <h1>Become an Instructor Today</h1>
                <h4>Change lives — including your own</h4>
            </div>
            <div class="right-side col-md-5">
                <c:if test="${not empty error}">
                    <p class="text-danger text-center fw-bold">${error}</p>
                </c:if>
                <form action="signup-instructor" method="POST" enctype="multipart/form-data" accept-charset="UTF-8" class="needs-validation" novalidate>
                    <input type="hidden" name="step" value="1">
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
                    <jsp:param name="role" value="signup_instructor"/>
                </jsp:include>
                <div class="text-center mt-4">
                    <span>Already have an account?</span>
                    <a href="${pageContext.request.contextPath}/login">Log In</a>
                </div>
            </div>
        </div>
        <jsp:include page="/component/footer.jsp" />
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
