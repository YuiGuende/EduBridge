<%-- 
    Document   : totalCourse
    Created on : Jun 1, 2025, 8:44:43 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="totalCourses/TotalCoursesStyles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <title>Total Courses</title>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />
        <input type="hidden" name="id" value="<c:out
                   value='${instructor.id}' />"/>
        <div class="container-fluid">
            <div class="container-header row">
                <div class="nav col-md-5">
                    <a href="total-courses?action=all" class="filter-btn ${action == 'all' ? 'active' : ''}">All</a>
                    <a href="total-courses?action=public" class="filter-btn ${action == 'public' ? 'active' : ''}">Public</a>
                    <a href="total-courses?action=draft" class="filter-btn ${action == 'draft' ? 'active' : ''}">Draft</a>
                    <a href="total-courses?action=archived" class="filter-btn ${action == 'archived' ? 'active' : ''}">Archived</a>
                </div>
                <div class="col-md-4">
                    <form method="get" action="total-courses" class="search-container">
                        <input type="text" name="keyword" value="${param.keyword}" placeholder="Search" />
                        <button class="search-button" type="submit">
                            <i class="fa fa-search"></i>
                        </button>
                        <input type="hidden" name="action" value="${action}" />
                    </form>
                </div>
                <a href="add-course" class="col-md-1 createBtn"><i class="fas fa-plus" style="color: #fcfcfc;"></i>Create</a>
                <form method="get" action="total-courses" class="col-md-2 row">
                    <input type="hidden" name="action" value="${action}" />
                    <input type="hidden" name="keyword" value="${keyword}" />
                    <label for="sort" class='col-md-6'>Sort by:</label>
                    <select name="sort" id="sort" onchange="this.form.submit()" class="col-md-6">
                        <option value="" ${empty sort ? 'selected' : ''}>Auto</option>
                        <option value="az" ${sort == 'az' ? 'selected' : ''}>A – Z</option>
                        <option value="za" ${sort == 'za' ? 'selected' : ''}>Z – A</option>
                        <option value="newest" ${sort == 'newest' ? 'selected' : ''}>Newest</option>
                        <option value="oldest" ${sort == 'oldest' ? 'selected' : ''}>Oldest</option>
                        <option value="popular" ${sort == 'popular' ? 'selected' : ''}>Most Enrolled</option>
                        <option value="rating" ${sort == 'rating' ? 'selected' : ''}>Top Rated</option>
                    </select>
                </form>
            </div>
            <c:if test="${not empty listCourse}">
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Published Time</th>
                        <th>Last Update</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    <c:forEach var="course" items="${listCourse}">
                        <tr>
                            <td><c:out value="${course.id}"/></td>
                            <td><c:out value="${course.title}"/></td>
                            <td><c:out value="${course.publishedTimeFormatted}"/></td>
                            <td><c:out value="${course.lastUpdateFormatted}"/></td>
                            <td><c:out value="${course.status}"/></td>
                            <td>
                                <a href="view-course?id=${course.id}" class="btn btn-outline-primary btn-sm">
                                    <i class="fas fa-eye"></i> View
                                </a>
                                <a href="course?id=${course.id}" class="btn btn-outline-secondary btn-sm">
                                    <i class="fas fa-edit"></i> Edit
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            <c:if test="${empty listCourse}">
                <h5 id="emptyList">No courses found in this category.</h5>
            </c:if>
            <div class="paging">
                <c:if test="${currentPage > 1}">
                    <a href="total-courses?page=${currentPage - 1}&action=${action}">Previous</a>
                </c:if>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="total-courses?page=${i}&action=${action}" class="${i == currentPage ? 'active-page' : ''}">${i}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="total-courses?page=${currentPage + 1}&action=${action}">Next</a>
                </c:if>
            </div>
        </div>
        <jsp:include page="/component/footer.jsp" />
    </body>
</html>
