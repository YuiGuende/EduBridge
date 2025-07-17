<%-- 
    Document   : Module
    Created on : Jun 1, 2025, 8:16:11 AM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="courseMaterial/ModuleStyle.css"/>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <div class="container-fluid">
            <div class="row">
                <jsp:include page="/component/instructorSideBar/sidebar.jsp" />

                <form class="right-body col-md-7 " action="" method="POST" enctype="multipart/form-data" style="height: auto;">
                    <ul class="nav flex-column">
                        <li class="nav-item  dropdown-module">
                            <a class="nav-link dropdown-toggle " href="#" role="button" aria-haspopup="true"
                               aria-expanded="false" onclick="displayModule(this)">
                                <span class="dropdown-icon bi bi-chevron-right" style="margin-right: 5px;"></span>

                                <span class="module-name">Module: ${module.title}</span>
                            </a>
                            <div id="dropdown-module" class="row col-md-12">

                                <div class="content-left col-md-12 ">
                                    <div class="left-item col-md-3">
                                        <i class="bi bi-play-btn "></i>
                                        <span class="left-content-text">14 min of videos left</span>
                                    </div>
                                    <div class="left-item col-md-3 ">
                                        <i class="bi bi-book"></i>
                                        <span class="left-content-text">14 min of readings left</span>
                                    </div>
                                    <div class="left-item col-md-3 ">
                                        <i class="bi bi-journal-check"></i>
                                        <span class="left-content-text"> 3 assignment left</span>
                                    </div>




                                </div>
                                <div class="btn-group dropend add-lesson-dropdown">
<<<<<<< HEAD
                                       <a href="lesson?action=add&courseId=${module.course.id}&moduleId=${module.id}" class="btn btn-success">
=======
                                    <a href="lesson?action=add&courseId=${module.course.id}&moduleId=${module.id}" class="btn btn-success">
>>>>>>> origin/main
                                        <i class="bi bi-plus-circle"></i> Add Lesson
                                    </a>

                                </div>
                                <hr class="hr-left col-md-12">

                                <!--                                <a  class=" nav-link dropdown-lesson-item" href="#">
                                                                    <i class="bi bi-check-circle-fill " style="margin-right: 10px; color: #0d7455 !important;"></i>
                                                                    <div class="lesson-item-title">
                                                                        <p>Introduction</p>
                                                                        <p class="lesson-item-type">Reading • 1 minute</p>
                                                                    </div>
                                                                    <div class="btns">
                                                                        <button class="edit-btn">
                                                                            Edit
                                                                        </button>
                                                                        <button class="edit-btn">
                                                                            Delete
                                                                        </button>
                                                                    </div>
                                                                </a>
                                                                <a class="dropdown-lesson-item nav-link" href="#">
                                                                    <i class="bi bi-check-circle-fill"style="margin-right: 10px; color: #0d7455 !important;"></i>
                                                                    <div class="lesson-item-title">
                                                                        <p>How to</p>
                                                                        <p class="lesson-item-type">Video • 10 minute</p>
                                                                    </div>
                                                                    <div class="btns">
                                                                        <button class="edit-btn">
                                                                            Edit
                                                                        </button>
                                                                        <button class="edit-btn">
                                                                            Delete
                                                                        </button>
                                                                    </div>
                                                                </a>
                                                                <a class="dropdown-lesson-item nav-link" href="#">
                                                                    <i class="bi bi-check-circle-fill"style="margin-right: 10px; color: #0d7455 !important;"></i>
                                                                    <div class="lesson-item-title">
                                                                        <p>Programmatic Prompting for Agents</p>
                                                                        <p class="lesson-item-type">Video • 10 minute</p>
                                                                    </div>
                                                                    <div class="btns">
                                                                        <button class="edit-btn">
                                                                            Edit
                                                                        </button>
                                                                        <button class="edit-btn">
                                                                            Delete
                                                                        </button>
                                                                    </div>
                                                                </a>
                                                                <a class="dropdown-lesson-item nav-link" href="#">
                                                                    <i class="bi bi-check-circle-fill"style="margin-right: 10px; color: #0d7455 !important;"></i>
                                                                    <div class="lesson-item-title">
                                                                        <p>Try Out Programmatic Prompting</p>
                                                                        <p class="lesson-item-type">Video • 10 minute</p>
                                                                    </div>
                                                                    <div class="btns">
                                                                        <button class="edit-btn">
                                                                            Edit
                                                                        </button>
                                                                        <button class="edit-btn">
                                                                            Delete
                                                                        </button>
                                                                    </div>
                                                                </a>-->
                                <!-- Nút Add Lesson -->
                                <!-- Add Lesson -->
                                <c:forEach var="lesson" items="${module.lessons}">
                                    <div class="dropdown-lesson col-md-12 mb-2">
                                        <a class="nav-link dropdown-toggle" href="#" role="button" onclick="toggleLesson(this)">
                                            <span class="dropdown-icon bi bi-chevron-right" style="margin-right: 5px;"></span>

                                            <span class="lesson-title"> Lesson: ${lesson.title}</span>
                                        </a>

                                        <div class="lesson-content collapse px-3 py-2" style="background-color: #f9f9f9; border-left: 2px solid #0d6efd; border-radius: 4px;">
                                            <div><strong>Description:</strong> ${lesson.description}</div>
                                            <div><strong>Index:</strong> ${lesson.index}</div>

                                            <!-- Lesson Items -->
                                            <div class="btn-group dropend mt-2">
                                                <button type="button" class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                                                    Add new lesson item
                                                </button>
                                                <ul class="dropdown-menu">

                                                    <li><a class="dropdown-item" href="add-lesson-item?type=reading&lessonId=${lesson.id}">Reading</a></li>
                                                    <li><a class="dropdown-item" href="add-lesson-item?type=video&lessonId=${lesson.id}">Video</a></li>
                                                    <li><a class="dropdown-item" href="#">Quiz</a></li>
                                                    <li><a class="dropdown-item" href="#">Assignment</a></li>
                                                </ul>
                                            </div>

                                            <c:forEach var="lessonItem" items="${lesson.lessonItems}">
                                                <a class="nav-link dropdown-toggle" href="#" role="button" onclick="toggleLesson(this)">
                                                   
                                                    <span class="lesson-title"> ${lessonItem.title}</span>
                                                </a>
                                            </c:forEach>
                                        </div>
                                    </div>

                                </c:forEach>                             
                                <!-- Danh sách Lessons bên trong dropdown-module -->
                            </div>
                        </li>
                    </ul>
                </form>
            </div>
            <script>
                function displayModule(link) {
                    const dropdown = document.getElementById('dropdown-module');
                    const expanded = link.getAttribute("aria-expanded") === "true";

                    if (expanded) {
                        dropdown.classList.remove('show');
                        link.setAttribute("aria-expanded", "false");
                    } else {
                        dropdown.classList.add('show');
                        link.setAttribute("aria-expanded", "true");
                    }
                }

                function displayAdding(link) {
                    const dropdown = document.getElementById('dropdown-adding-item');
                    const expanded = link.getAttribute("aria-expanded") === "true";

                    if (expanded) {
                        dropdown.classList.remove('show');
                        link.setAttribute("aria-expanded", "false");
                    } else {
                        dropdown.classList.add('show');
                        link.setAttribute("aria-expanded", "true");
                    }
                }
                function toggleLesson(link) {
                    const dropdownIcon = link.querySelector(".dropdown-icon");
                    const lessonContent = link.nextElementSibling;
                    if (lessonContent.classList.contains("collapse")) {
                        lessonContent.classList.remove("collapse");
                        dropdownIcon.classList.remove("bi-chevron-right");
                        dropdownIcon.classList.add("bi-chevron-down");
                    } else {
                        lessonContent.classList.add("collapse");
                        dropdownIcon.classList.remove("bi-chevron-down");
                        dropdownIcon.classList.add("bi-chevron-right");
                    }
                }
            </script>

            <!-- Bootstrap JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-XwEi9vHaxmBMGQYFxLjT6TnBtqHnZ4OeMG02H4Om1qTOnMZyg4v9Ma/4J6VXWu+7"
            crossorigin="anonymous"></script>
    </body>
</html>
