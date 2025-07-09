<%-- 
    Document   : socialLogin
    Created on : Jul 5, 2025, 4:48:03 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="component/socialLogin/socialLoginStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Social Login</title>
    </head>
    <body>
        <%
            String role = request.getParameter("role");
        %>
        <div class="separator text-center my-3"><span>Other options</span></div>
        <div class="d-flex justify-content-center social-buttons mb-3">
            <a href="https://accounts.google.com/o/oauth2/v2/auth?client_id=975399677929-35j8qjkastop507e5d4gc6r3bielu4ur.apps.googleusercontent.com&redirect_uri=http://localhost:9999/EduBridge/login-google&response_type=code&scope=openid%20email%20profile&access_type=online&state=<%=role%>" 
               class="btn btn-outline-danger">
                <i class="fab fa-google"></i>
            </a>
            <a href="https://www.facebook.com/v12.0/dialog/oauth?client_id=YOUR_APP_ID&redirect_uri=http://localhost:9999/EduBridge/login-facebook&state=<%=role%>"
               class="btn btn-outline-primary">
                <i class="fab fa-facebook-f"></i>
            </a>            
            <a href="https://www.linkedin.com/oauth/v2/authorization?response_type=code&client_id=867yqxfh8u77hs&redirect_uri=http://localhost:9999/EduBridge/login-linkedin&scope=r_liteprofile%20r_emailaddress&state=<%=role%>" 
               class="btn btn-outline-linkedin">
                <i class="fab fa-linkedin"></i>
            </a>
        </div>
    </body>
</html>
