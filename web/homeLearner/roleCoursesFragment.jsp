<%-- 
    Document   : roleCoursesFragment
    Created on : Jul 11, 2025, 4:26:34 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="course" items="${courses}">
    <a href="course-detail?id=${course.id}" class="course-card">
        <div class="course-image-container">
            <img src="${course.thumbnailUrl}" class="course-image" alt="${course.title}">
        </div>
        <div class="course-content">
            <h3 class="course-title">${course.title}</h3>
            <p class="course-instructor">
                ${course.createdBy.user.fullname}
                <c:if test="${not empty course.instructors}">
                    ,
                    <c:forEach var="ins" items="${course.instructors}" varStatus="loop">
                        ${ins.user.fullname}<c:if test="${!loop.last}">, </c:if>
                    </c:forEach>
                </c:if>
            </p>

            <div class="course-rating">
                <span class="rating-score">

                    <c:out value="${course.rate.rate}" default="0.0"/>
                </span>
                <div class="stars">
                    <c:set var="fullStars" value="${course.rate.rate - (course.rate.rate % 1)}" />
                    <c:set var="halfStar" value="${course.rate.rate % 1 >= 0.5}" />
                    <c:set var="emptyStars" value="${5 - fullStars - (halfStar ? 1 : 0)}" />

                    <c:forEach begin="1" end="${fullStars}" var="i">
                        <i class="fas fa-star"></i>
                    </c:forEach>
                    <c:if test="${halfStar}">
                        <i class="fas fa-star-half-alt"></i>
                    </c:if>
                    <c:forEach begin="1" end="${emptyStars}" var="i">
                        <i class="far fa-star"></i>
                    </c:forEach>
                </div>
                <span class="rating-count">
                    (<c:out value="${course.rate.rateQuantity}" default="0"/>)
                </span>
            </div>

            <div class="course-footer">
                <span class="course-price">${course.price} VND</span>
            </div>
        </div>
    </a>
</c:forEach>