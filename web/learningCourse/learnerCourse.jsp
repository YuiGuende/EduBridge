<%-- 
    Document   : viewCourse
    Created on : Jun 1, 2025, 4:28:41 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="learningCourse/leanercourseStyle.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>View Course</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />

        <c:if test="${course != null}">
            <input type="hidden" name="courseId" value="<c:out value='${course.id}' />"/>
        </c:if>

        <!-- Course Header Section -->
        <div class="course-header">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8">
                        <div class="course-header-content">
                            <nav aria-label="breadcrumb" class="mb-3">
                                <ol class="breadcrumb">
                                    <li class="breadcrumb-item"><a href="home-instructor">Dashboard</a></li>
                                    <li class="breadcrumb-item"><a href="total-courses">Courses</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">${course.title}</li>
                                </ol>
                            </nav>

                            <h1 class="course-title">${course.title}</h1>
                            <p class="course-headline">${course.headline}</p>

                            <div class="course-meta">
                                <div class="meta-item">
                                    <span class="rating">
                                        <c:out value="${course.rate.rate}" default="0.0"/>
                                    </span>
                                    <div class="rating-stars">
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

                                <div class="meta-item">
                                    <i class="fas fa-users"></i>
                                    <span>${studentNum} students enrolled</span>
                                </div>

                                <div class="meta-item">
                                    <i class="fas fa-clock"></i>
                                    <span class="total-duration">${course.estimatedTime}</span>
                                </div>
                            </div>

                            <div class="instructor-info">
                                <h6 class="instructor-label">Created by:</h6>
                                <div class="instructor-main">
                                    <div class="instructor-details">
                                        <c:if test="${not empty course.createdBy.avatarUrl}">
                                            <img src="${course.createdBy.avatarUrl}" alt="${course.createdBy.user.fullname}" class="instructor-avatar">
                                        </c:if>
                                        <span class="instructor-name">${course.createdBy.user.fullname}</span>
                                    </div>
                                </div>

                                <c:if test="${not empty course.instructors}">
                                    <div class="collaborators mt-2">
                                        <span class="collab-label">Collaborators:</span>
                                        <div class="collab-list">
                                            <c:forEach var="ins" items="${course.instructors}" varStatus="loop">
                                                <span class="collaborator">
                                                    <c:if test="${not empty ins.avatarUrl}">
                                                        <img src="${ins.avatarUrl}" alt="${ins.user.fullname}" class="collab-avatar">
                                                    </c:if>
                                                    ${ins.user.fullname}<c:if test="${!loop.last}">, </c:if>
                                                    </span>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                            <div class="course-info-footer">
                                <div class="info-item">
                                    <i class="fas fa-calendar-alt"></i>
                                    <span>Last updated: ${course.lastUpdateFormatted}</span>
                                </div>

                                <div class="info-item">
                                    <i class="fas fa-globe"></i>
                                    <span class="primary-language">${course.primaryLanguage.name}</span>
                                    <c:if test="${not empty course.languages}">
                                        <span class="subtitle-languages">
                                            + <c:forEach var="lang" items="${course.languages}" varStatus="loop">
                                                ${lang.name}<c:if test="${!loop.last}">, </c:if>
                                            </c:forEach> subtitles
                                        </span>
                                    </c:if>
                                </div>

                                <div class="info-item">
                                    <i class="fas fa-layer-group"></i>
                                    <span>${fn:length(course.modules)} modules • ${course.totalLessons} lessons</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4">
                        <div class="course-preview-card">
                            <div class="preview-image">
                                <img src="${course.thumbnailUrl}" alt="${course.title}" class="img-fluid">
                            </div>

                            <div class="course-status">
                                <div class="status-info">
                                    <div class="status-item">
                                        <span class="status-label">Status:</span>
                                        <span>${course.status}</span>
                                    </div>
                                    <div class="status-item">
                                        <span class="status-label">Created:</span>
                                        <span>${course.createdDateFormatted}</span>
                                    </div>
                                    <div class="status-item">
                                        <span class="status-label">Price</span>
                                        <span>${course.price} VND</span>
                                    </div>
                                </div>
                            </div>
                            <c:choose>
                                <c:when test="${isEnrolled}">
                                    <a href="${pageContext.request.contextPath}/learn-course?id=${course.id}" class="btn btn-success btn-go-to-course">
                                        <i class="fas fa-play-circle"></i> Go to Course
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.request.contextPath}/cart" method="post" class="add-to-cart-form">
                                        <input type="hidden" name="id" value="${course.id}" />
                                        <input type="hidden" name="action" value="add" />
                                        <button type="submit" class="btn btn-primary btn-add-to-cart">
                                            <i class="fas fa-shopping-cart"></i> Add to Cart
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Course Content Section -->
        <div class="course-content-section">
            <div class="container">
                <!-- Learning Outcomes -->
                <div class="content-block">
                    <h3 class="section-title">
                        <i class="fas fa-bullseye"></i>
                        Learning Outcomes
                    </h3>
                    <div class="learning-outcomes">
                        <ul class="outcomes-list">
                            <c:forEach var="outcome" items="${course.learningOutcomes}">
                                <li>
                                    <i class="fas fa-check-circle"></i>
                                    ${outcome}
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <!-- Requirements -->
                <div class="content-block">
                    <h3 class="section-title">
                        <i class="fas fa-list-check"></i>
                        Requirements
                    </h3>
                    <div class="requirements">
                        <ul class="requirements-list">
                            <c:forEach var="requirement" items="${course.requirements}">
                                <li>
                                    <i class="fas fa-arrow-right"></i>
                                    ${requirement}
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <!-- Description -->
                <div class="content-block">
                    <h3 class="section-title">
                        <i class="fas fa-info-circle"></i>
                        Course Description
                    </h3>
                    <div class="description">
                        <div id="description-text" class="description-content collapsed">
                            ${course.description}
                        </div>
                        <button id="toggle-description" class="btn-toggle">
                            <span class="toggle-text">Show more</span>
                            <i class="fas fa-chevron-down toggle-icon"></i>
                        </button>
                    </div>
                </div>
                <div class="content-block">
                    <h3 class="section-title">
                        <i class="fas fa-bullseye"></i>
                        Target audience
                    </h3>
                    <div class="learning-outcomes">
                        <ul class="outcomes-list">
                            <c:forEach var="outcome" items="${course.courseFor}">
                                <li>
                                    <i class="fas fa-check-circle"></i>
                                    ${outcome}
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <!-- Course Curriculum -->
                <div class="content-block">
                    <h3 class="section-title">
                        <i class="fas fa-book-open"></i>
                        Course Curriculum
                    </h3>

                    <div class="curriculum-summary">
                        <span class="summary-text">
                            ${fn:length(course.modules)} modules • 
                            ${course.totalLessons} lessons • 
                            ${course.estimatedTime} total length
                        </span>
                    </div>

                    <div class="curriculum-content">
                        <div class="accordion" id="curriculumAccordion">
                            <c:forEach var="module" items="${course.modules}" varStatus="moduleStatus">
                                <div class="accordion-item module-item">
                                    <h2 class="accordion-header" id="heading${moduleStatus.index}">
                                        <button class="accordion-button collapsed" type="button" 
                                                data-bs-toggle="collapse" 
                                                data-bs-target="#collapse${moduleStatus.index}" 
                                                aria-expanded="false" 
                                                aria-controls="collapse${moduleStatus.index}">
                                            <div class="module-header">
                                                <div class="module-info">
                                                    <i class="fas fa-chevron-right toggle-icon"></i>
                                                    <span class="module-title">Module ${moduleStatus.index + 1}: ${module.title}</span>
                                                </div>
                                                <div class="module-meta">
                                                    <span class="lesson-count">${fn:length(module.lessons)} lessons</span>
                                                    <span class="module-duration">${module.estimatedDuration}</span>
                                                </div>
                                            </div>
                                        </button>
                                    </h2>
                                    <div id="collapse${moduleStatus.index}" 
                                         class="accordion-collapse collapse" 
                                         aria-labelledby="heading${moduleStatus.index}"
                                         data-bs-parent="#curriculumAccordion">
                                        <div class="accordion-body">
                                            <c:if test="${not empty module.description}">
                                                <p class="module-description">${module.description}</p>
                                            </c:if>

                                            <div class="lessons-list">
                                                <c:forEach var="lesson" items="${module.lessons}" varStatus="lessonStatus">
                                                    <div class="lesson-item">
                                                        <div class="lesson-header">
                                                            <div class="lesson-info">
                                                                <i class="fas fa-play-circle lesson-icon"></i>
                                                                <span class="lesson-title">Lesson ${lessonStatus.index + 1}: ${lesson.title}</span>
                                                            </div>
                                                            <span class="lesson-duration">${lesson.estimatedDuration}</span>
                                                        </div>

                                                        <c:if test="${not empty lesson.lessonItems}">
                                                            <div class="lesson-items">
                                                                <c:forEach var="item" items="${lesson.lessonItems}">
                                                                    <div class="lesson-item-row">
                                                                        <div class="item-info">
                                                                            <c:choose>
                                                                                <c:when test="${item.type == 'VIDEO'}">
                                                                                    <i class="fas fa-video item-icon video-icon"></i>
                                                                                </c:when>
                                                                                <c:when test="${item.type == 'ARTICLE'}">
                                                                                    <i class="fas fa-file-alt item-icon article-icon"></i>
                                                                                </c:when>
                                                                                <c:when test="${item.type == 'QUIZ'}">
                                                                                    <i class="fas fa-question-circle item-icon quiz-icon"></i>
                                                                                </c:when>
                                                                            </c:choose>
                                                                            <span class="item-title">${item.title}</span>
                                                                        </div>
                                                                        <div class="item-meta">
                                                                            <c:if test="${item.type == 'QUIZ'}">
                                                                                <span class="quiz-questions">${item.questionCount} questions</span>
                                                                            </c:if>
                                                                            <span class="item-duration">${item.estimatedDuration}</span>
                                                                        </div>
                                                                    </div>
                                                                </c:forEach>
                                                            </div>
                                                        </c:if>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/report.jsp" />
        <%@ include file="/component/footer.jsp" %>   
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Custom JS -->
        <script>
            // Course Viewer JavaScript
            document.addEventListener("DOMContentLoaded", () => {
                // Description toggle functionality
                const descriptionText = document.getElementById("description-text");
                const toggleButton = document.getElementById("toggle-description");

                if (descriptionText && toggleButton) {
                    // Check if content is actually truncated
                    const isContentTruncated = descriptionText.scrollHeight > descriptionText.offsetHeight + 5;

                    if (!isContentTruncated) {
                        toggleButton.style.display = "none";
                    }

                    toggleButton.addEventListener("click", () => {
                        const isCollapsed = descriptionText.classList.contains("collapsed");
                        const toggleText = toggleButton.querySelector(".toggle-text");
                        const toggleIcon = toggleButton.querySelector(".toggle-icon");

                        if (isCollapsed) {
                            descriptionText.classList.remove("collapsed");
                            descriptionText.classList.add("expanded");
                            toggleText.textContent = "Show less";
                            toggleIcon.classList.remove("fa-chevron-down");
                            toggleIcon.classList.add("fa-chevron-up");
                            toggleButton.classList.add("expanded");
                        } else {
                            descriptionText.classList.remove("expanded");
                            descriptionText.classList.add("collapsed");
                            toggleText.textContent = "Show more";
                            toggleIcon.classList.remove("fa-chevron-up");
                            toggleIcon.classList.add("fa-chevron-down");
                            toggleButton.classList.remove("expanded");
                        }
                    });
                }

                // Accordion toggle icons
                const accordionButtons = document.querySelectorAll(".accordion-button");
                accordionButtons.forEach((button) => {
                    button.addEventListener("click", function () {
                        const toggleIcon = this.querySelector(".toggle-icon");
                        if (toggleIcon) {
                            // Bootstrap handles the collapsed class, we just need to update our icon
                            setTimeout(() => {
                                if (this.classList.contains("collapsed")) {
                                    toggleIcon.style.transform = "rotate(0deg)";
                                } else {
                                    toggleIcon.style.transform = "rotate(90deg)";
                                }
                            }, 10);
                        }
                    });
                });

                // Lesson item click handlers
                const lessonItems = document.querySelectorAll(".lesson-item-row");
                lessonItems.forEach((item) => {
                    item.addEventListener("click", function () {
                        // Add click feedback
                        this.style.backgroundColor = "#e3f2fd";
                        setTimeout(() => {
                            this.style.backgroundColor = "";
                        }, 200);

                        // Here you can add navigation logic
                        console.log("Lesson item clicked:", this.querySelector(".item-title").textContent);
                    });
                });
            });
        </script>
    </body>
</html>
