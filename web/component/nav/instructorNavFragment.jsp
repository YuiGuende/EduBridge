<%-- 
    Document   : instructorNavFragment
    Created on : Jul 18, 2025, 3:39:09 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="uri" value="${pageContext.request.requestURI}" />
<div class="nav-container col-md-8">
    <a href="home-instructor" class="nav-link ${fn:contains(uri, 'homeInstructor') ? 'active' : ''}">Dashboard</a>
    <a href="total-courses" class="nav-link ${fn:contains(uri, 'totalCourses') ? 'active' : ''}">Courses</a>  
    <a href="total-learners" class="nav-link ${fn:contains(uri, 'totalLearners') ? 'active' : ''}">Learners</a>
</div>
<div class="icon-nav col-md-4 d-flex justify-content-end align-items-center">
    <a href="#" class="position-relative">
        <i class="fa-solid fa-message fs-5"></i>
        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
            3
            <span class="visually-hidden">unread messages</span>
        </span>
    </a>

    <div class="dropdown">
        <a class="position-relative text-decoration-none" href="#" id="notiInsDropdown"
           role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="fa-solid fa-bell fs-5"></i>
            <c:if test="${newNotifications > 0}">
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                    ${newNotifications}
                </span>
            </c:if>
        </a>

        <div class="dropdown-menu dropdown-menu-end mt-2 p-2 shadow"
             aria-labelledby="notiInsDropdown"
             style="width: 400px; max-height: 400px; overflow-y: auto;">
            <h6 class="dropdown-header">Notifications</h6>

            <c:choose>
                <c:when test="${not empty notifications}">
                    <c:forEach var="notification" items="${notifications}">
                        <a href="${notification.link}" class="dropdown-item small d-flex align-items-start">
                            <i class="fa-solid fa-circle-info text-primary me-2"></i>
                            <div>
                                <div class="fw-semibold">${notification.title}</div>
                                <div class="text-muted small">${notification.time}</div>
                            </div>
                        </a>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="dropdown-item text-muted text-center small">
                        No notifications yet.
                    </div>
                </c:otherwise>
            </c:choose>

            <div class="dropdown-divider"></div>
            <a href="all-notifications" class="dropdown-item text-center small">
                View All
            </a>
        </div>
    </div>


    <div class="dropdown">
        <!-- Button trigger dropdown -->
        <button class="btn p-0 border-0 bg-transparent" type="button" id="dropdownIns"
                data-bs-toggle="dropdown" aria-expanded="false">
            <c:choose>
                <c:when test="${not empty user.instructor.avatarUrl}">
                    <img src="${user.instructor.avatarUrl}" alt="${user.fullname}" class="rounded-circle" style="width: 40px; height: 40px; object-fit: cover;">
                </c:when>
                <c:otherwise>
                    <div class="avatar-initials">
                        <c:set var="initials" value="" />
                        <c:forEach var="word" items="${fn:split(user.fullname, ' ')}" begin="0" end="1">
                            <c:set var="initials" value="${initials}${fn:substring(word, 0, 1)}" />
                        </c:forEach>
                        ${fn:toUpperCase(initials)}
                    </div>
                </c:otherwise>
            </c:choose>
        </button>

        <!-- Dropdown menu -->
        <ul class="dropdown-menu custom-dropdown dropdown-menu-end mt-2 p-3 shadow" aria-labelledby="dropdownIns" style="min-width: 220px;">
            <li class="d-flex align-items-center mb-2">
                <div class="me-2">
                    <c:choose>
                        <c:when test="${not empty user.instructor.avatarUrl}">
                            <img src="${user.instructor.avatarUrl}" alt="${user.fullname}" class="rounded-circle" style="width: 40px; height: 40px; object-fit: cover;">
                        </c:when>
                        <c:otherwise>
                            <div class="avatar-initials small">
                                ${fn:toUpperCase(initials)}
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="flex-grow-1">
                    <div class="fw-semibold">${user.fullname}</div>
                    <div class="text-muted small">${user.email}</div>
                </div>
            </li>
            <li><hr class="dropdown-divider"></li>

            <li>
                <a class="dropdown-item d-flex align-items-center gap-2" href="profile">
                    <i class="fa-solid fa-user"></i> Profile
                </a>
            </li>

            <li>
                <a class="dropdown-item d-flex align-items-center gap-2" href="logout">
                    <i class="fa-solid fa-right-from-bracket"></i> Logout
                </a>
            </li>
        </ul>
    </div>
</div>
