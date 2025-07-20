<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="util.JSPUtils" %>
<%@ page import="model.user.UserStatus" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Instructor Management - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
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
                        Instructor Management
                    </h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <a href="${pageContext.request.contextPath}/admin/instructors?action=createForm" class="btn btn-sm btn-primary">
                            <i class="fas fa-plus me-1"></i>Add New Instructor
                        </a>
                    </div>
                </div>

                <c:if test="${not empty param.message}">
                    <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                </c:if>
                <c:if test="${not empty param.error}">
                    <div class="alert alert-danger">${JSPUtils.htmlEncode(param.error)}</div>
                </c:if>

                <div class="mb-3">
                    <label for="statusFilter" class="form-label">Filter by Status:</label>
                    <select id="statusFilter" class="form-select w-auto d-inline-block" onchange="applyStatusFilter()">
                        <option value="<%= UserStatus.ACTIVE.name() %>" ${statusFilter == UserStatus.ACTIVE.name() ? 'selected' : ''}>Active</option>
                        <option value="<%= UserStatus.INACTIVE.name() %>" ${statusFilter == UserStatus.INACTIVE.name() ? 'selected' : ''}>Inactive</option>
                        <option value="ALL" ${statusFilter == "ALL" ? 'selected' : ''}>All</option>
                    </select>
                </div>

                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Full Name</th>
                                <th>Email</th>
                                <th>Created At</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty instructors}">
                                    <c:forEach var="instructor" items="${instructors}">
                                        <tr>
                                            <td>${JSPUtils.htmlEncode(instructor.id)}</td>
                                            <td>${JSPUtils.htmlEncode(instructor.fullname)}</td>
                                            <td>${JSPUtils.htmlEncode(instructor.email)}</td>
                                            <td><fmt:formatDate value="${instructor.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                            <td>
                                                <span class="badge 
                                                    <c:choose>
                                                        <c:when test="${instructor.status eq UserStatus.ACTIVE}">bg-success</c:when>
                                                        <c:when test="${instructor.status eq UserStatus.INACTIVE}">bg-danger</c:when>
                                                        <c:otherwise>bg-secondary</c:otherwise>
                                                    </c:choose>
                                                ">
                                                    ${JSPUtils.htmlEncode(instructor.status.name())}
                                                </span>
                                            </td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/instructors?action=view&id=${JSPUtils.htmlEncode(instructor.id)}" class="btn btn-info btn-sm">
                                                    <i class="fas fa-eye"></i> View
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/instructors?action=editForm&id=${JSPUtils.htmlEncode(instructor.id)}" class="btn btn-warning btn-sm">
                                                    <i class="fas fa-edit"></i> Edit
                                                </a>
                                                <c:if test="${instructor.status eq UserStatus.ACTIVE}">
                                                    <button type="button" class="btn btn-danger btn-sm" onclick="confirmUpdateStatus(${JSPUtils.htmlEncode(instructor.id)}, '<%= UserStatus.INACTIVE.name() %>')">
                                                        <i class="fas fa-user-slash"></i> Deactivate
                                                    </button>
                                                </c:if>
                                                <c:if test="${instructor.status eq UserStatus.INACTIVE}">
                                                    <button type="button" class="btn btn-success btn-sm" onclick="confirmUpdateStatus(${JSPUtils.htmlEncode(instructor.id)}, '<%= UserStatus.ACTIVE.name() %>')">
                                                        <i class="fas fa-user-check"></i> Activate
                                                    </button>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" class="text-center">No instructors found.</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>

                <nav aria-label="Page navigation">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageUrls[0]}" aria-label="First">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="${pageUrls[currentPage - 2]}" aria-label="Previous">
                                <span aria-hidden="true">&lsaquo;</span>
                            </a>
                        </li>
                        <c:forEach begin="${(currentPage - 2 > 0) ? (currentPage - 2) : 1}" end="${(currentPage + 2 <= totalPages) ? (currentPage + 2) : totalPages}" var="i">
                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                <a class="page-link" href="${pageUrls[i - 1]}">${i}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageUrls[currentPage]}" aria-label="Next">
                                <span aria-hidden="true">&rsaquo;</span>
                            </a>
                        </li>
                        <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                            <a class="page-link" href="${pageUrls[totalPages - 1]}" aria-label="Last">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
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

        function applyStatusFilter() {
            const status = document.getElementById('statusFilter').value;
            window.location.href = '${pageContext.request.contextPath}/admin/instructors?statusFilter=' + status;
        }
    </script>
</body>
</html>
