<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Course Management - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
    </head>
    <body>


        <div class="container-fluid">
            <div class="row">


                <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">
                            <i class="fas fa-book me-2"></i>
                            Course Management
                        </h1>
                    </div>

                    <!-- Search and Filter -->
                    <div class="card mb-4">
                        <div class="card-body">
                            <form method="GET" action="${pageContext.request.contextPath}/admin/courses">
                                <div class="row g-3">
                                    <div class="col-md-4">
                                        <label for="searchTitle" class="form-label">Search by Title</label>
                                        <input type="text" class="form-control" id="searchTitle" name="title" 
                                               value="${param.title}" placeholder="Enter course title...">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="statusFilter" class="form-label">Status</label>
                                        <select class="form-select" id="statusFilter" name="status">
                                            <option value="">All Status</option>
                                            <option value="DRAFT" ${param.status == 'DRAFT' ? 'selected' : ''}>Draft</option>
                                            <option value="REQUESTING" ${param.status == 'REQUESTING' ? 'selected' : ''}>Pending</option>
                                            <option value="PUBLIC" ${param.status == 'PUBLIC' ? 'selected' : ''}>Approved</option>
                                            <option value="REJECTED" ${param.status == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                                            <option value="ARCHIVE" ${param.status == 'ARCHIVE' ? 'selected' : ''}>Disabled</option>
                                        </select>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="instructorFilter" class="form-label">Instructor</label>
                                        <input type="text" class="form-control" id="instructorFilter" name="instructor" 
                                               value="${param.instructor}" placeholder="Instructor name...">
                                    </div>
                                    <div class="col-md-2">
                                        <label class="form-label">&nbsp;</label>
                                        <div class="d-grid">
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-search me-1"></i>Search
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Courses Table -->
                    <div class="card">
                        <div class="card-header">
                            <h5 class="mb-0">
                                <i class="fas fa-list me-2"></i>
                                Courses (${totalCourses} total)
                            </h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>ID</th>
                                            <th>Thumbnail</th>
                                            <th>Title</th>
                                            <th>Instructor</th>
                                            <th>Rating</th>
                                            <th>Status</th>
                                            <th>Created</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="course" items="${courses}">
                                            <tr>
                                                <td>${course.id}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty course.thumbnailUrl}">
                                                            <img src="${course.thumbnailUrl}" alt="Thumbnail" 
                                                                 class="img-thumbnail" style="width: 60px; height: 40px; object-fit: cover;">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="bg-light d-flex align-items-center justify-content-center" 
                                                                 style="width: 60px; height: 40px;">
                                                                <i class="fas fa-image text-muted"></i>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <div class="fw-bold">${course.title}</div>
                                                    <div class="text-muted small">${course.headline}</div>
                                                </td>
                                                <td>
                                                    
                                                         ${course.createdBy}
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${course.rate != null && course.rate.rate > 0}">
                                                            <div class="d-flex align-items-center">
                                                                <span class="me-1">${course.rate.rate}</span>
                                                                <div class="text-warning">
                                                                    <c:forEach begin="1" end="5" var="i">
                                                                        <i class="fas fa-star${i <= course.rate.rate ? '' : '-o'}"></i>
                                                                    </c:forEach>
                                                                </div>
                                                                
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="text-muted">No ratings</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <span class="badge bg-${course.status == 'PUBLIC' ? 'success' :
                                                                            course.status == 'REQUESTING' ? 'warning' :
                                                                            course.status == 'REJECTED' ? 'danger' :
                                                                            course.status == 'ARCHIVE' ? 'secondary' : 'info'}">
                                                              ${course.status}
                                                          </span>

                                                    </td>
                                                    <td>
                                                        <fmt:formatDate value="${course.createdDate}" pattern="dd/MM/yyyy"/>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group" role="group">
                                                            <a href="${pageContext.request.contextPath}/admin/courses?action=view&id=${course.id}" 
                                                               class="btn btn-sm btn-outline-primary" title="View Details">
                                                                <i class="fas fa-eye"></i>
                                                            </a>
                                                            <c:if test="${course.status == 'REQUESTING'}">
                                                                <button type="button" class="btn btn-sm btn-outline-success" 
                                                                        onclick="approveCourse(${course.id})" title="Approve">
                                                                    <i class="fas fa-check"></i>
                                                                </button>
                                                                <button type="button" class="btn btn-sm btn-outline-danger" 
                                                                        onclick="rejectCourse(${course.id})" title="Reject">
                                                                    <i class="fas fa-times"></i>
                                                                </button>
                                                            </c:if>
                                                            <c:if test="${course.status != 'ARCHIVE'}">
                                                                <button type="button" class="btn btn-sm btn-outline-warning" 
                                                                        onclick="disableCourse(${course.id})" title="Disable">
                                                                    <i class="fas fa-ban"></i>
                                                                </button>
                                                            </c:if>
                                                            <c:if test="${course.status == 'ARCHIVE'}">
                                                                <button type="button" class="btn btn-sm btn-outline-success" 
                                                                        onclick="enableCourse(${course.id})" title="Enable">
                                                                    <i class="fas fa-check-circle"></i>
                                                                </button>
                                                            </c:if>

                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>

                                <!-- Pagination -->
                                <c:if test="${totalPages > 1}">
                                    <nav aria-label="Course pagination">
                                        <ul class="pagination justify-content-center">
                                            <c:if test="${currentPage > 1}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?page=${currentPage - 1}&title=${param.title}&status=${param.status}&instructor=${param.instructor}">
                                                        Previous
                                                    </a>
                                                </li>
                                            </c:if>

                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                    <a class="page-link" href="?page=${i}&title=${param.title}&status=${param.status}&instructor=${param.instructor}">
                                                        ${i}
                                                    </a>
                                                </li>
                                            </c:forEach>

                                            <c:if test="${currentPage < totalPages}">
                                                <li class="page-item">
                                                    <a class="page-link" href="?page=${currentPage + 1}&title=${param.title}&status=${param.status}&instructor=${param.instructor}">
                                                        Next
                                                    </a>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </nav>
                                </c:if>
                            </div>
                        </div>
                    </main>
                </div>
            </div>

            <!-- Modals -->
            <!-- Approve Course Modal -->
            <div class="modal fade" id="approveCourseModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Approve Course</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Are you sure you want to approve this course?</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-success" id="confirmApprove">Approve</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Reject Course Modal -->
            <div class="modal fade" id="rejectCourseModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Reject Course</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Please provide a reason for rejecting this course:</p>
                            <textarea class="form-control" id="rejectReason" rows="3" placeholder="Enter rejection reason..."></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-danger" id="confirmReject">Reject</button>
                        </div>
                    </div>
                </div>
            </div>
            <form id="statusUpdateForm" method="POST" action="${pageContext.request.contextPath}/admin/courses" style="display: none;">
                <input type="hidden" name="action" value="updateStatus" />
                <input type="hidden" name="courseId" id="formCourseId" />
                <input type="hidden" name="status" id="formStatus" />
                <input type="hidden" name="reason" id="formReason" />
            </form>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                                                                            let currentCourseId = null;

                                                                            function approveCourse(courseId) {
                                                                                currentCourseId = courseId;
                                                                                new bootstrap.Modal(document.getElementById('approveCourseModal')).show();
                                                                            }

                                                                            function rejectCourse(courseId) {
                                                                                currentCourseId = courseId;
                                                                                new bootstrap.Modal(document.getElementById('rejectCourseModal')).show();
                                                                            }

                                                                            function disableCourse(courseId) {
                                                                                if (confirm('Are you sure you want to disable this course?')) {
                                                                                    updateCourseStatus(courseId, 'ARCHIVE');
                                                                                }
                                                                            }

                                                                            function enableCourse(courseId) {
                                                                                if (confirm('Are you sure you want to enable this course?')) {
                                                                                    updateCourseStatus(courseId, 'PUBLIC');
                                                                                }
                                                                            }

                                                                            document.getElementById('confirmApprove').addEventListener('click', function () {
                                                                                submitStatusUpdate(currentCourseId, 'PUBLIC');
                                                                                bootstrap.Modal.getInstance(document.getElementById('approveCourseModal')).hide();
                                                                            });

                                                                            document.getElementById('confirmReject').addEventListener('click', function () {
                                                                                const reason = document.getElementById('rejectReason').value;
                                                                                if (!reason.trim()) {
                                                                                    alert('Please provide a reason for rejection.');
                                                                                    return;
                                                                                }
                                                                                submitStatusUpdate(currentCourseId, 'REJECTED', reason);
                                                                                bootstrap.Modal.getInstance(document.getElementById('rejectCourseModal')).hide();
                                                                            });


                                                                            function submitStatusUpdate(courseId, status, reason = '') {
                                                                                document.getElementById('formCourseId').value = courseId;
                                                                                document.getElementById('formStatus').value = status;
                                                                                document.getElementById('formReason').value = reason;
                                                                                document.getElementById('statusUpdateForm').submit();
                                                                            }
            </script>
        </body>
    </html>
