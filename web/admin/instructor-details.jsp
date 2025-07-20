<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="util.JSPUtils" %>
<%@ page import="model.user.UserStatus" %>
<%@ page import="model.course.CourseStatus" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Instructor Details - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/instructors-style.css">
    </head>
    <body>
        <jsp:include page="components/admin-header.jsp" />

        <div class="container-fluid">
            <div class="row">
                <jsp:include page="components/admin-sidebar.jsp" />

                <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">
                            <i class="fas fa-chalkboard-teacher me-2"></i>
                            Instructor Details
                        </h1>
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <a href="${pageContext.request.contextPath}/admin/instructors?action=editForm&id=${JSPUtils.htmlEncode(instructor.id)}" class="btn btn-sm btn-warning">
                                <i class="fas fa-edit me-1"></i>Edit Instructor
                            </a>
                            <c:if test="${instructor.status eq UserStatus.ACTIVE}">
                                <button type="button" class="btn btn-sm btn-danger ms-2" onclick="confirmUpdateStatus(${JSPUtils.htmlEncode(instructor.id)}, '<%= UserStatus.INACTIVE.name() %>')">
                                    <i class="fas fa-user-slash me-1"></i>Deactivate Instructor
                                </button>
                            </c:if>
                            <c:if test="${instructor.status eq UserStatus.INACTIVE}">
                                <button type="button" class="btn btn-sm btn-success ms-2" onclick="confirmUpdateStatus(${JSPUtils.htmlEncode(instructor.id)}, '<%= UserStatus.ACTIVE.name() %>')">
                                    <i class="fas fa-user-check me-1"></i>Activate Instructor
                                </button>
                            </c:if>
                        </div>
                    </div>

                    <c:if test="${not empty param.message}">
                        <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                    </c:if>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">${JSPUtils.htmlEncode(param.error)}</div>
                    </c:if>

                    <div class="card mb-4">
                        <div class="card-header">
                            Instructor Information
                        </div>
                        <div class="card-body">

                            <div class="row mb-2">
                                <div class="col-md-3"><strong>ID:</strong></div>
                                <div class="col-md-5">${JSPUtils.htmlEncode(instructor.id)}</div>

                                <div class="col-md-4">
                                    <c:if test="${not empty instructor.avatarUrl}">
                                        <img src="${JSPUtils.htmlEncode(instructor.avatarUrl)}" alt="Instructor Avatar" class="img-thumbnail mb-3" style="width: 150px; height: 150px; object-fit: cover;">
                                    </c:if>
                                    <c:if test="${empty instructor.avatarUrl}">
                                        <img src="/placeholder.svg?height=150&width=150" alt="Default Avatar" class="img-thumbnail mb-3" style="width: 150px; height: 150px; object-fit: cover;">
                                    </c:if></div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-md-3"><strong>Full Name:</strong></div>
                                <div class="col-md-9">${JSPUtils.htmlEncode(instructor.fullname)}</div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-md-3"><strong>Email:</strong></div>
                                <div class="col-md-9">${JSPUtils.htmlEncode(instructor.email)}</div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-md-3"><strong>Bio:</strong></div>
                                <div class="col-md-9">${JSPUtils.htmlEncode(instructor.bio)}</div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-md-3"><strong>Created At:</strong></div>
                                <div class="col-md-9"><fmt:formatDate value="${instructor.createdAt}" pattern="dd/MM/yyyy HH:mm"/></div>
                            </div>
                            <div class="row mb-2">
                                <div class="col-md-3"><strong>Status:</strong></div>
                                <div class="col-md-9">
                                    <span class="badge
                                          <c:choose>
                                              <c:when test="${instructor.status eq UserStatus.ACTIVE}">bg-success</c:when>
                                              <c:when test="${instructor.status eq UserStatus.INACTIVE}">bg-danger</c:when>
                                              <c:otherwise>bg-secondary</c:otherwise>
                                          </c:choose>
                                          ">
                                        ${JSPUtils.htmlEncode(instructor.status.name())}
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="card mb-4">
                        <div class="card-header">
                            Courses Taught by Instructor
                        </div>
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${not empty instructor.courses}">
                                    <ul class="list-group">
                                        <c:forEach var="course" items="${instructor.courses}">
                                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                                <a href="${pageContext.request.contextPath}/admin/courses?action=view&id=${JSPUtils.htmlEncode(course.id)}">
                                                    ${JSPUtils.htmlEncode(course.title)}
                                                </a>
                                                <span class="badge
                                                      <c:choose>
                                                          <c:when test="${course.status eq CourseStatus.PUBLIC}">bg-primary</c:when>
                                                          <c:when test="${course.status eq CourseStatus.DRAFT}">bg-secondary</c:when>
                                                          <c:when test="${course.status eq CourseStatus.ARCHIVE}">bg-info</c:when>
                                                          <c:otherwise>bg-dark</c:otherwise>
                                                      </c:choose>
                                                      ">
                                                    ${JSPUtils.htmlEncode(course.status.name())}
                                                </span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:when>
                                <c:otherwise>
                                    <p class="text-muted">No courses found for this instructor.</p>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-secondary">
                        <i class="fas fa-arrow-left me-1"></i>Back to List
                    </a>
                </main>
            </div>
        </div>

        <form id="updateStatusForm" action="${pageContext.request.contextPath}/admin/instructors" method="post" style="display: none;">
            <input type="hidden" name="action" value="updateStatus">
            <input type="hidden" name="id" id="instructorIdForStatusUpdate">
            <input type="hidden" name="status" id="newStatusValue">
        </form>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                    function confirmUpdateStatus(instructorId, newStatus) {
                                        let message = '';
                                        if (newStatus === '<%= UserStatus.INACTIVE.name() %>') {
                                            message = 'Are you sure you want to deactivate this instructor? They will no longer be able to create or manage courses.';
                                        } else if (newStatus === '<%= UserStatus.ACTIVE.name() %>') {
                                            message = 'Are you sure you want to activate this instructor? They will regain access to create and manage courses.';
                                        }

                                        if (confirm(message)) {
                                            document.getElementById('instructorIdForStatusUpdate').value = instructorId;
                                            document.getElementById('newStatusValue').value = newStatus;
                                            document.getElementById('updateStatusForm').submit();
                                        }
                                    }
        </script>
    </body>
</html>
