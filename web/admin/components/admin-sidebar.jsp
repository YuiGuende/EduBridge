<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Sidebar</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/components/admin-sidebar.css">
    <!-- You might also need to include other general CSS files here if they apply to the sidebar -->
</head>
<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3">
        <div class="admin-sidebar">
            <div class="sidebar-header">
                <h3>Admin Panel</h3>
            </div>
            <ul class="sidebar-menu">
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/dashboard') ? 'active' : ''}" aria-current="page" href="${pageContext.request.contextPath}/admin/dashboard">
                        <i class="fas fa-tachometer-alt me-2"></i>
                        Dashboard
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/courses') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/courses">
                        <i class="fas fa-book me-2"></i>
                        Course Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/instructors') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/instructors">
                        <i class="fas fa-chalkboard-teacher me-2"></i>
                        Instructor Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/learners') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/learners">
                        <i class="fas fa-users me-2"></i>
                        Learner Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/comments') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/comments">
                        <i class="fas fa-comments me-2"></i>
                        Comment Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/reports') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/reports">
                        <i class="fas fa-flag me-2"></i>
                        Report Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/orders') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/orders">
                        <i class="fas fa-shopping-cart me-2"></i>
                        Order Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link ${request.getRequestURI().endsWith('/admin/payments') ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/payments">
                        <i class="fas fa-credit-card me-2"></i>
                        Payment Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/logout">
                        <i class="fas fa-sign-out-alt me-2"></i>
                        Logout
                    </a>
                </li>
                <!-- Add more navigation items as needed -->
            </ul>

            <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                <span>Saved reports</span>
                <a class="link-secondary" href="#" aria-label="Add a new report">
                    <i class="fas fa-plus-circle"></i>
                </a>
            </h6>
            <ul class="nav flex-column mb-2">
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-file-alt me-2"></i>
                        Current month
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-file-alt me-2"></i>
                        Last quarter
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-file-alt me-2"></i>
                        Year-end sale
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
