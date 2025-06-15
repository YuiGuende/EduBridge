<%-- 
    Document   : nav-bar
    Created on : May 25, 2025, 12:15:03 PM
    Author     : LEGION
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.13.1/font/bootstrap-icons.min.css">
        <title>Instructor vertical nav bar</title>
        <link rel="stylesheet" href="component/instructorSideBar/style.css">
    </head>

    <body>
        <div class="col-md-2 offset-md-1" style="height: auto;">
            <ul class="nav flex-column">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" aria-haspopup="true"
                       aria-expanded="false" onclick="displayDropdown(this)">
                        <span class="dropdown-icon bi bi-chevron-right " style="margin-right: 5px;"></span>
                        <span class="active">Material</span>

                    </a>
                    <div id="dropdown-material">
                        <c:forEach var="i" begin="1" end="${numOfModule}" step="1">
                            <a  class="dropdown-item nav-link" href="#">
                                <i class="bi bi-caret-right-fill"style="margin-right: 10px;"></i>
                                <span>Module ${i}</span>
                            </a>
                        </c:forEach>
                        <!--                        <a class="dropdown-item nav-link" href="#"><i class="bi bi-caret-right-fill"
                                                                                              style="margin-right: 10px;"></i><span>Module 2</span></a>
                                                <a class="dropdown-item nav-link" href="#"><i class="bi bi-caret-right-fill"
                                                                                              style="margin-right: 10px;"></i><span>Module 3</span></a>
                                                <a class="dropdown-item nav-link" href="#"><i class="bi bi-caret-right-fill"
                                                                                              style="margin-right: 10px;"></i><span>Module 4</span></a>-->
                    </div>
                </li>
                <li id="pricing" class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Pricing</a>
                </li>
                <li id="course-information" class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Information</a>
                </li>
                <li id="course-features" class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Features</a>
                </li>
            </ul>
        </div>


        <script>
            function displayDropdown(link) {
                const dropdown = document.getElementById('dropdown-material');
                const expanded = link.getAttribute("aria-expanded") === "true";

                if (expanded) {
                    dropdown.classList.remove('show');
                    link.setAttribute("aria-expanded", "false");
                } else {
                    dropdown.classList.add('show');
                    link.setAttribute("aria-expanded", "true");
                }
            }
        </script>

        <!-- Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.3/dist/umd/popper.min.js"
                integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/js/bootstrap.min.js"
                integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
    </body>

</html>