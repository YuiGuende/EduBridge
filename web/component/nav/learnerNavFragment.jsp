<%-- 
    Document   : learnerNavFragment
    Created on : Jul 18, 2025, 3:39:27 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form class="search-container col-md-6">
    <input type="text" placeholder="Search">
    <button class="search-button">
        <i class="fa fa-search"></i>
    </button>                
</form>
<div class="icon-nav col-md-6 d-flex justify-content-end align-items-center">
    <a href="cart" class="position-relative">
        <i class="fa-solid fa-cart-shopping"></i>
    </a>
    <a href="#" class="position-relative">
        <i class="fa-solid fa-message"></i>
        <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
            3
            <span class="visually-hidden">unread messages</span>
        </span>
    </a>

    <div class="dropdown">
        <a class="position-relative text-decoration-none" href="#" id="notiLearnerDropdown"
           role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <i class="fa-solid fa-bell"></i>
            <c:if test="${newNotifications > 0}">
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                    ${newNotifications}
                </span>
            </c:if>
        </a>

        <div class="dropdown-menu dropdown-menu-end p-2 shadow"
             aria-labelledby="notiLearnerDropdown"
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
        <button class="btn p-0 border-0 bg-transparent" type="button" id="dropdownLearner"
                data-bs-toggle="dropdown" aria-expanded="false">
            <div class="avatar-initials">
                <c:set var="initials" value="" />
                <c:forEach var="word" items="${fn:split(user.fullname, ' ')}" begin="0" end="1">
                    <c:set var="initials" value="${initials}${fn:substring(word, 0, 1)}" />
                </c:forEach>
                ${fn:toUpperCase(initials)}
            </div>
        </button>

        <!-- Dropdown menu -->
        <ul class="dropdown-menu custom-dropdown dropdown-menu-end mt-2 p-3 shadow" aria-labelledby="dropdownLearner" style="min-width: 220px;">
            <li class="d-flex align-items-center mb-2">
                <div class="me-2">
                    <div class="avatar-initials small">
                        ${fn:toUpperCase(initials)}
                    </div>
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
                <a class="dropdown-item d-flex align-items-center gap-2" href="mycourse">
                    <i class="fa-solid fa-book-open-reader"></i>My Course
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