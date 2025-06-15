<%-- 
    Document   : viewCourse
    Created on : Jun 1, 2025, 4:28:41 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="viewCourse/ViewCourseStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <title>View Course</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <c:if test="${course != null}">
            <input type="hidden" name="id" value="<c:out value='${course.id}' />"/>
        </c:if>
        <div class="container-fluid row">
            <div class="container-header row">
                <div class="container-header-content col-md-7 offset-md-1">
                    <h1><strong>${course.title}</strong></h1>
                    <h5>${course.headLine}</h5>
                    <span class="rating-stars">4.8 ★★★★★</span>
                    <h6>Created by <strong>Instructor Name</strong></h6>
                    <p><i class="fas fa-edit"></i>Last update: ${course.lastUpdate}</p>
                    <p><i class="fas fa-globe"></i>${course.language.language}</p>
                </div>
            </div>   
            <div class="container-content col-md-7 offset-md-1">
                <div class="learning-outcome">
                    <h4><strong>Learning Outcomes</strong></h4>
                    <ul>
                        <c:forEach var="item" items="${course.learningOutcomes}">
                            <li>${item}</li>
                            </c:forEach>
                    </ul>
                </div>
                <div class="requirement">
                    <h4><strong>Requirements</strong></h4>
                    <ul>
                        <c:forEach var="item" items="${course.requirement}">
                            <li>${item}</li>
                            </c:forEach>
                    </ul>
                </div>
                <div class="description">
                    <h4><strong>Descriptions</strong></h4>
                    <p id="description-text" class="collapsed">${course.description}</p>
                    <button id="toggle-button"  onclick="toggleDescription()">Show more</button>
                </div>
                <div class="course-content my-4">
                    <h4><strong>Course Content</strong></h4>
                    <div class="accordion mt-3" id="courseAccordion">

                        <!-- Section 1 -->
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="heading1">
                                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                        data-bs-target="#collapse1" aria-expanded="false" aria-controls="collapse1">
                                    <i class="fas fa-chevron-right toggle-icon"></i>
                                    Section 1: Introduction
                                    <span class="section-duration">3 lectures • 10min</span>
                                </button>
                            </h2>
                            <div id="collapse1" class="accordion-collapse collapse" aria-labelledby="heading1"
                                 data-bs-parent="#courseAccordion">
                                <div class="accordion-body">
                                    <div class="content-item">
                                        <div class="content-left">
                                            <i class="fas fa-play-circle text-danger"></i>
                                            <span>Welcome</span>
                                        </div>
                                        <span class="duration">02:00</span>
                                    </div>
                                    <div class="content-item">
                                        <div class="content-left">
                                            <i class="fas fa-file-alt text-primary"></i>
                                            <span>How to Use the Course</span>
                                        </div>
                                        <span class="duration">04:00</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-5 offset-md-7">
                    <div class="card shadow-sm">
                        <img src="${course.thumbnailUrl}" class="card-img-top" alt="Course Image">

                        <div class="card-body">
                            <h3>$19999</h3>
                            <a href="/enroll?courseId=${course.id}" class="btn btn-primary w-100 mb-2">Enroll Now</a>
                            <p class="text-muted">30-Day Money-Back Guarantee</p>

                            <h5>This course includes:</h5>

                        </div>
                    </div>
                </div>

            </div>
            <script>
                window.addEventListener("DOMContentLoaded", function () {
                    const desc = document.getElementById('description-text');
                    const btn = document.getElementById('toggle-button');

                    // Check if the text is actually being truncated
                    if (desc.scrollHeight > desc.offsetHeight + 5) { // thêm 5 cho an toàn
                        btn.style.display = 'inline'; // show button
                    }

                    btn.addEventListener("click", function () {
                        if (desc.classList.contains('collapsed')) {
                            desc.classList.remove('collapsed');
                            desc.classList.add('expanded');
                            btn.textContent = 'Show less';
                        } else {
                            desc.classList.remove('expanded');
                            desc.classList.add('collapsed');
                            btn.textContent = 'Show more';
                        }
                    });
                });
            </script>
    </body>
</html>
