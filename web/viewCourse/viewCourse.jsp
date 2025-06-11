<%-- 
    Document   : viewCourse
    Created on : Jun 1, 2025, 4:28:41 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="viewCourse/ViewCourseStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>View Course</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <c:if test="${course != null}">
            <input type="hidden" name="id" value="<c:out value='${course.id}' />"/>
        </c:if>
        <div class="container-fluid">
            <div class="container-header row">
                <h1><strong>${course.title}</strong></h1>
                <h4>${course.headLine}</h4>
                <span class="rating-stars">4.8 ★★★★★</span>
                <h6>Created by <strong>Instructor Name</strong></h6>
                <p><i class="fas fa-edit"></i>Last update: ${course.lastUpdate}</p>
                <p><i class="fas fa-globe"></i>${course.language}</p>
            </div>
            <!-- Course preview video -->
            <div class="ratio ratio-16x9 mb-4">
                <iframe src="https://www.youtube.com/embed/sample" title="Course Preview"></iframe>
            </div>

            <!-- What you'll learn -->
            <h4>What you'll learn</h4>
            <ul>
                <li>Understand the basics of X</li>
                <li>Build Y applications from scratch</li>
                <li>Deploy Z systems</li>
            </ul>

            <!-- Course content -->
            <h4 class="mt-4">Course content</h4>
            <div class="accordion" id="courseContent">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="headingOne">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne">
                            Section 1: Introduction
                        </button>
                    </h2>
                    <div id="collapseOne" class="accordion-collapse collapse show">
                        <div class="accordion-body">
                            <ul>
                                <li>Lesson 1: Welcome</li>
                                <li>Lesson 2: Course Overview</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- More sections -->
            </div>

            <!-- RIGHT COLUMN -->
            <div class="col-md-4">
                <div class="card shadow-sm">
                    <img src="course-thumbnail.jpg" class="card-img-top" alt="Course Image">
                    <div class="card-body">
                        <h3>$19.99</h3>
                        <a href="/enroll?courseId=123" class="btn btn-primary w-100 mb-2">Enroll Now</a>
                        <p class="text-muted">30-Day Money-Back Guarantee</p>

                        <h5>This course includes:</h5>
                        <ul class="list-unstyled">
                            <li><i class="fa fa-play-circle"></i> 10 hours on-demand video</li>
                            <li><i class="fa fa-file"></i> 5 downloadable resources</li>
                            <li><i class="fa fa-mobile"></i> Access on mobile and TV</li>
                            <li><i class="fa fa-certificate"></i> Certificate of completion</li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
