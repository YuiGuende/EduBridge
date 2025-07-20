us<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="util.JSPUtils" %>
<%@ page import="model.user.User" %>
<%@ page import="model.user.Instructor" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><c:choose><c:when test="${not empty instructor.id}">Edit Instructor</c:when><c:otherwise>Add New Instructor</c:otherwise></c:choose> - Admin</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/instructors-style.css">
    </head>
    <body>
        <jsp:include page="components/admin-header.jsp" />

        <div class="container-fluid ">
            <div class="row">
                <jsp:include page="components/admin-sidebar.jsp" />

                <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">
                            <i class="fas fa-chalkboard-teacher me-2"></i>
                            <c:choose>
                                <c:when test="${not empty instructor.id}">Edit Instructor</c:when>
                                <c:otherwise>Add New Instructor</c:otherwise>
                            </c:choose>
                        </h1>
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <a href="${pageContext.request.contextPath}/admin/instructors" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-2"></i>Back to List
                            </a>
                        </div>
                    </div>

                    <c:if test="${not empty requestScope.error}">
                        <div class="alert alert-danger">${JSPUtils.htmlEncode(requestScope.error)}</div>
                    </c:if>

                    <div class="card">
                        <div class="card-header">
                            Instructor Details
                        </div>
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/admin/instructors" method="post">
                                <c:if test="${not empty instructor.id}">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${JSPUtils.htmlEncode(instructor.id)}">
                                </c:if>
                                <c:if test="${empty instructor.id}">
                                    <input type="hidden" name="action" value="create">
                                </c:if>

                                <div class="mb-3">
                                    <label for="fullname" class="form-label">Full Name <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="fullname" name="fullname" value="${JSPUtils.htmlEncode(instructor.fullname)}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                                    <input type="email" class="form-control" id="email" name="email" value="${JSPUtils.htmlEncode(instructor.email)}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="specialization" class="form-label">Specialization <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="specialization" name="specialization" value="${JSPUtils.htmlEncode(instructor.specialization)}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="experienceYears" class="form-label">Experience (Years) <span class="text-danger">*</span></label>
                                    <input type="number" class="form-control" id="experienceYears" name="experienceYears" value="${JSPUtils.htmlEncode(instructor.experienceYears)}" required min="0">
                                </div>
                                <div class="mb-3">
                                    <label for="educationLevel" class="form-label">Education Level <span class="text-danger">*</span></label>
                                    <input type="text" class="form-control" id="educationLevel" name="educationLevel" value="${JSPUtils.htmlEncode(instructor.educationLevel)}" required>
                                </div>
                                <div class="mb-3">
                                    <label for="bio" class="form-label">Bio</label>
                                    <textarea class="form-control" id="bio" name="bio" rows="3">${JSPUtils.htmlEncode(instructor.bio)}</textarea>
                                </div>
                                <div class="mb-3">
                                    <label for="linkedinProfile" class="form-label">LinkedIn Profile URL</label>
                                    <input type="url" class="form-control" id="linkedinProfile" name="linkedinProfile" value="${JSPUtils.htmlEncode(instructor.linkedinProfile)}">
                                </div>
                                <div class="mb-3">
                                    <label for="avatarUrl" class="form-label">Avatar URL</label>
                                    <input type="url" class="form-control" id="avatarUrl" name="avatarUrl" value="${JSPUtils.htmlEncode(instructor.avatarUrl)}">
                                    <c:if test="${not empty instructor.avatarUrl}">
                                        <img src="${JSPUtils.htmlEncode(instructor.avatarUrl)}" alt="Current Avatar" class="img-thumbnail mt-2" style="max-width: 100px; height: auto;">
                                    </c:if>
                                </div>

                                <button type="submit" class="btn btn-primary">
                                    <c:choose>
                                        <c:when test="${not empty instructor.id}">
                                            <i class="fas fa-save me-2"></i>Update Instructor
                                        </c:when>
                                        <c:otherwise>
                                            <i class="fas fa-plus-circle me-2"></i>Add Instructor
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                            </form>
                        </div>
                    </div>
                </main>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
