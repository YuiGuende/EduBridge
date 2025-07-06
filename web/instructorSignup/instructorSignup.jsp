<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="user" class="model.user.User" scope="request" />
<jsp:useBean id="instructor" class="model.user.Instructor" scope="request" />

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

        <div class="signup-container">
            <h2>Become an Instructor</h2>
            <c:if test="${not empty error}">
                <p style="text-align: center;
                   color: red;
                   font-weight: 600;">
                    ${error}
                </p>
            </c:if>
            <form action="signup-instructor" method="POST" enctype="multipart/form-data" accept-charset="UTF-8">
                <div class="mb-3">
                    <input type="email" name="email" class="form-control" placeholder="Email" value="${user.email}" required />
                </div>
                <div class="mb-3">
                    <input type="text" name="fullname" class="form-control" placeholder="Full Name" value="${user.fullname}" required />
                </div>
                <div class="mb-3">
                    <input type="password" name="password" class="form-control" placeholder="Password" required />
                </div>
                <div class="mb-3">
                    <input type="password" name="confirm" class="form-control" placeholder="Confirm Password" required />
                </div>
                <div class="mb-3">
                    <label class="form-label">Upload Avatar</label>
                    <input type="file" name="avatarFile" class="form-control" accept="image/*" />
                </div>
                <div class="mb-3">
                    <textarea name="bio" class="form-control" placeholder="Bio" rows="4">${instructor.bio}</textarea>
                </div>
                <div class="mb-3">
                    <input type="number" min="0" name="experience" class="form-control" placeholder="Years of Experience" value="${instructor.experienceYears}" required/>
                </div>
                <div class="mb-3">
                    <input type="text" name="specialization" class="form-control" placeholder="Specialization" value="${specialization}" required/>
                </div>
                <div class="mb-3">
                    <select name="education_level" class="form-select" required>
                        <option value="">Select Education Level</option>
                        <option value="Associate">Associate</option>
                        <option value="Bachelor">Bachelor</option>
                        <option value="Master">Master</option>
                        <option value="PhD">PhD</option>
                    </select>
                </div>
                <div class="mb-3">
                    <input type="text" name="linkedin" class="form-control" placeholder="LinkedIn Profile (optional)" />
                </div>
                <div class="d-grid">
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </div>
            </form>

            <div class="separator text-center my-3"><span>Other options</span></div>
            <div class="other-options">
                <button class="btn btn-outline-danger"><i class="fab fa-google"></i></button>
                <button class="btn btn-outline-primary"><i class="fab fa-facebook-f"></i></button>
                <button class="btn btn-outline-linkedln"><i class="fab fa-linkedin-in"></i></button>
            </div>

            <div class="text-center mt-4">
                <span>Already have an account?</span>
                <a href="${pageContext.request.contextPath}/login">Log In</a>
            </div>
        </div>

        <jsp:include page="/component/footer.jsp" />
    </body>
</html>
