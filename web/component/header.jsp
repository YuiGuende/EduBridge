<%-- 
    Document   : header
    Created on : May 24, 2025, 9:44:52 AM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/component/ComponentStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Header</title>
    </head>
    <body>
        <header class="header row">
            <div class="logo col-md-2">
                <a href="<c:choose>
                       <c:when test="${sessionScope.user.role eq 'admin'}">${pageContext.request.contextPath}/admin/dashboard</c:when>
                       <c:when test="${sessionScope.user.role eq 'instructor'}">${pageContext.request.contextPath}/home-instructor</c:when>
                       <c:when test="${sessionScope.user.role eq 'learner'}">${pageContext.request.contextPath}/home-learner</c:when>
                       <c:otherwise>${pageContext.request.contextPath}/</c:otherwise>
                   </c:choose>">
                    <img src="${pageContext.request.contextPath}/resource/images/logo.png" alt="logo"/>
                </a>
            </div>
            <div class="col-md-10 row">
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <%@ include file="nav/guestNavFragment.jsp" %>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${sessionScope.user.role eq 'admin'}">
                                <%@ include file="nav/adminNavFragment.jsp" %>
                            </c:when>
                            <c:when test="${sessionScope.user.role eq 'instructor'}">
                                <%@ include file="nav/instructorNavFragment.jsp" %>
                            </c:when>
                            <c:otherwise>
                                <%@ include file="nav/learnerNavFragment.jsp" %>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </header>  
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
