<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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

    
    <div class="container-fluid">
        <div class="row">
            
            
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">
                        <i class="fas fa-chalkboard-teacher me-2"></i>
                        Instructor Management
                    </h1>
                </div>

                <!-- Search and Filter -->
                <div class="card mb-4">
                    <div class="card-body">
                        <form method="GET" action="${pageContext.request.contextPath}/admin/instructors">
                            <div class="row g-3">
                                <div class="col-md-3">
                                    <label for="searchName" class="form-label">Search by Name</label>
                                    <input type="text" class="form-control" id="searchName" name="name" 
                                           value="${param.name}" placeholder="Enter instructor name...">
                                </div>
                                <div class="col-md-3">
                                    <label for="searchEmail" class="form-label">Search by Email</label>
                                    <input type="email" class="form-control" id="searchEmail" name="email" 
                                           value="${param.email}" placeholder="Enter email...">
                                </div>
                                <div class="col-md-3">
                                    <label for="specializationFilter" class="form-label">Specialization</label>
                                    <input type="text" class="form-control" id="specializationFilter" name="specialization" 
                                           value="${param.specialization}" placeholder="Specialization...">
                                </div>
                                <div class="col-md-3">
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

                <!-- Instructors Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list me-2"></i>
                            Instructors (${totalInstructors} total)
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th>Avatar</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Specialization</th>
                                        <th>Experience</th>
                                        <th>Courses</th>
                                        <th>Status</th>
                                        <th>Joined</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="instructor" items="${instructors}">
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty instructor.avatarUrl}">
                                                        <img src="${instructor.avatarUrl}" alt="Avatar" 
                                                             class="rounded-circle" style="width: 40px; height: 40px; object-fit: cover;">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="bg-secondary rounded-circle d-flex align-items-center justify-content-center text-white" 
                                                             style="width: 40px; height: 40px;">
                                                            <i class="fas fa-user"></i>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <div class="fw-bold">${instructor.user.fullname}</div>
                                                <div class="text-muted small">ID: ${instructor.id}</div>
                                            </td>
                                            <td>${instructor.user.email}</td>
                                            <td>
                                                <span class="badge bg-info">${instructor.specialization}</span>
                                            </td>
                                            <td>${instructor.experienceYears} years</td>
                                            <td>
                                                <span class="badge bg-primary">${instructor.coursesCreated.size()} courses</span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${instructor.user.role == 'instructor'}">
                                                        <span class="badge bg-success">Active</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">Inactive</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${instructor.user.createdAt}" pattern="dd/MM/yyyy"/>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <button type="button" class="btn btn-sm btn-outline-primary" 
                                                            onclick="viewInstructor(${instructor.id})" title="View Details">
                                                        <i class="fas fa-eye"></i>
                                                    </button>
                                                    <button type="button" class="btn btn-sm btn-outline-warning" 
                                                            onclick="editInstructor(${instructor.id})" title="Edit">
                                                        <i class="fas fa-edit"></i>
                                                    </button>
                                                    <c:choose>
                                                        <c:when test="${instructor.user.role == 'INSTRUCTOR'}">
                                                            <button type="button" class="btn btn-sm btn-outline-danger" 
                                                                    onclick="disableInstructor(${instructor.id})" title="Disable">
                                                                <i class="fas fa-ban"></i>
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <button type="button" class="btn btn-sm btn-outline-success" 
                                                                    onclick="enableInstructor(${instructor.id})" title="Enable">
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
                            <nav aria-label="Instructor pagination">
                                <ul class="pagination justify-content-center">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage - 1}&name=${param.name}&email=${param.email}&specialization=${param.specialization}">
                                                Previous
                                            </a>
                                        </li>
                                    </c:if>
                                    
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&name=${param.name}&email=${param.email}&specialization=${param.specialization}">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    
                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage + 1}&name=${param.name}&email=${param.email}&specialization=${param.specialization}">
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

    <!-- View Instructor Modal -->
    <div class="modal fade" id="viewInstructorModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Instructor Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="instructorDetails">
                    <!-- Content will be loaded dynamically -->
                </div>
            </div>
        </div>
    </div>

    <!-- Edit Instructor Modal -->
    <div class="modal fade" id="editInstructorModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Edit Instructor</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <form id="editInstructorForm">
                    <div class="modal-body">
                        <input type="hidden" id="editInstructorId" name="instructorId">
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="editFullname" class="form-label">Full Name</label>
                                    <input type="text" class="form-control" id="editFullname" name="fullname" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="editEmail" class="form-label">Email</label>
                                    <input type="email" class="form-control" id="editEmail" name="email" required>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="editSpecialization" class="form-label">Specialization</label>
                                    <input type="text" class="form-control" id="editSpecialization" name="specialization" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="mb-3">
                                    <label for="editExperience" class="form-label">Experience (Years)</label>
                                    <input type="number" class="form-control" id="editExperience" name="experienceYears" min="0" required>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mb-3">
                            <label for="editEducationLevel" class="form-label">Education Level</label>
                            <input type="text" class="form-control" id="editEducationLevel" name="educationLevel" required>
                        </div>
                        
                        <div class="mb-3">
                            <label for="editLinkedin" class="form-label">LinkedIn Profile</label>
                            <input type="url" class="form-control" id="editLinkedin" name="linkedinProfile">
                        </div>
                        
                        <div class="mb-3">
                            <label for="editBio" class="form-label">Bio</label>
                            <textarea class="form-control" id="editBio" name="bio" rows="4"></textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function viewInstructor(instructorId) {
            fetch(`${pageContext.request.contextPath}/admin/instructors?action=view&id=${instructorId}`)
                .then(response => response.text())
                .then(html => {
                    document.getElementById('instructorDetails').innerHTML = html;
                    new bootstrap.Modal(document.getElementById('viewInstructorModal')).show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error loading instructor details');
                });
        }

        function editInstructor(instructorId) {
            fetch(`${pageContext.request.contextPath}/admin/instructors?action=edit&id=${instructorId}`)
                .then(response => response.json())
                .then(instructor => {
                    document.getElementById('editInstructorId').value = instructor.id;
                    document.getElementById('editFullname').value = instructor.user.fullname;
                    document.getElementById('editEmail').value = instructor.user.email;
                    document.getElementById('editSpecialization').value = instructor.specialization;
                    document.getElementById('editExperience').value = instructor.experienceYears;
                    document.getElementById('editEducationLevel').value = instructor.educationLevel;
                    document.getElementById('editLinkedin').value = instructor.linkedinProfile || '';
                    document.getElementById('editBio').value = instructor.bio || '';
                    
                    new bootstrap.Modal(document.getElementById('editInstructorModal')).show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error loading instructor data');
                });
        }

        function disableInstructor(instructorId) {
            if (confirm('Are you sure you want to disable this instructor?')) {
                updateInstructorStatus(instructorId, 'DISABLED');
            }
        }

        function enableInstructor(instructorId) {
            if (confirm('Are you sure you want to enable this instructor?')) {
                updateInstructorStatus(instructorId, 'INSTRUCTOR');
            }
        }

        function updateInstructorStatus(instructorId, status) {
            fetch('${pageContext.request.contextPath}/admin/instructors', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=updateStatus&instructorId=${instructorId}&status=${status}`
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
                alert('An error occurred while updating instructor status.');
            });
        }

        // Handle edit form submission
        document.getElementById('editInstructorForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const formData = new FormData(this);
            formData.append('action', 'update');
            
            fetch('${pageContext.request.contextPath}/admin/instructors', {
                method: 'POST',
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    bootstrap.Modal.getInstance(document.getElementById('editInstructorModal')).hide();
                    location.reload();
                } else {
                    alert('Error: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while updating instructor.');
            });
        });
    </script>
</body>
</html>
