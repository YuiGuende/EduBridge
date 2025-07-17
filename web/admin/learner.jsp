<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Learner Management - Admin</title>
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
                        <i class="fas fa-users me-2"></i>
                        Learner Management
                    </h1>
                </div>

                <!-- Search and Filter -->
                <div class="card mb-4">
                    <div class="card-body">
                        <form method="GET" action="${pageContext.request.contextPath}/admin/learners">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label for="searchName" class="form-label">Search by Name</label>
                                    <input type="text" class="form-control" id="searchName" name="name" 
                                           value="${param.name}" placeholder="Enter learner name...">
                                </div>
                                <div class="col-md-4">
                                    <label for="searchEmail" class="form-label">Search by Email</label>
                                    <input type="email" class="form-control" id="searchEmail" name="email" 
                                           value="${param.email}" placeholder="Enter email...">
                                </div>
                                <div class="col-md-4">
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

                <!-- Learners Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list me-2"></i>
                            Learners (${totalLearners} total)
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Education Level</th>
                                        <th>Enrolled Courses</th>
                                        <th>Status</th>
                                        <th>Joined</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="learner" items="${learners}">
                                        <tr>
                                            <td>${learner.id}</td>
                                            <td>
                                                <div class="fw-bold">${learner.user.fullname}</div>
                                            </td>
                                            <td>${learner.user.email}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty learner.educationLevel}">
                                                        <span class="badge bg-info">${learner.educationLevel}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">Not specified</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <span class="badge bg-primary">${learner.courseLearnerList.size()} courses</span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${learner.user.role == 'LEARNER'}">
                                                        <span class="badge bg-success">Active</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Inactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${learner.user.createdAt}" pattern="dd/MM/yyyy"/>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <button type="button" class="btn btn-sm btn-outline-primary" 
                                                            onclick="viewLearner(${learner.id})" title="View Details">
                                                        <i class="fas fa-eye"></i>
                                                    </button>
                                                    <c:choose>
                                                        <c:when test="${learner.user.role == 'LEARNER'}">
                                                            <button type="button" class="btn btn-sm btn-outline-danger" 
                                                                    onclick="disableLearner(${learner.id})" title="Disable">
                                                                <i class="fas fa-ban"></i>
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="button" class="btn btn-sm btn-outline-success" 
                                                                    onclick="enableLearner(${learner.id})" title="Enable">
                                                                <i class="fas fa-check-circle"></i>
                                                            </button>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <c:if test="${totalPages > 1}">
                            <nav aria-label="Learner pagination">
                                <ul class="pagination justify-content-center">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage - 1}&name=${param.name}&email=${param.email}">
                                                Previous
                                            </a>
                                        </li>
                                    </c:if>
                                    
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&name=${param.name}&email=${param.email}">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    
                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage + 1}&name=${param.name}&email=${param.email}">
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

    <!-- View Learner Modal -->
    <div class="modal fade" id="viewLearnerModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Learner Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="learnerDetails">
                    <!-- Content will be loaded dynamically -->
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function viewLearner(learnerId) {
            fetch(`${pageContext.request.contextPath}/admin/learners?action=view&id=${learnerId}`)
                .then(response => response.text())
                .then(html => {
                    document.getElementById('learnerDetails').innerHTML = html;
                    new bootstrap.Modal(document.getElementById('viewLearnerModal')).show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error loading learner details');
                });
        }

        function disableLearner(learnerId) {
            if (confirm('Are you sure you want to disable this learner?')) {
                updateLearnerStatus(learnerId, 'DISABLED');
            }
        }

        function enableLearner(learnerId) {
            if (confirm('Are you sure you want to enable this learner?')) {
                updateLearnerStatus(learnerId, 'LEARNER');
            }
        }

        function updateLearnerStatus(learnerId, status) {
            fetch('${pageContext.request.contextPath}/admin/learners', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=updateStatus&learnerId=${learnerId}&status=${status}`
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('Error: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while updating learner status.');
            });
        }
    </script>
</body>
</html>
