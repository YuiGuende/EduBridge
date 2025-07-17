<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Comment Management - Admin</title>
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
                        <i class="fas fa-comments me-2"></i>
                        Comment Management
                    </h1>
                </div>

                <!-- Search and Filter -->
                <div class="card mb-4">
                    <div class="card-body">
                        <form method="GET" action="${pageContext.request.contextPath}/admin/comments">
                            <div class="row g-3">
                                <div class="col-md-4">
                                    <label for="searchCourse" class="form-label">Search by Course</label>
                                    <input type="text" class="form-control" id="searchCourse" name="course" 
                                           value="${param.course}" placeholder="Enter course title...">
                                </div>
                                <div class="col-md-4">
                                    <label for="searchUser" class="form-label">Search by User</label>
                                    <input type="text" class="form-control" id="searchUser" name="user" 
                                           value="${param.user}" placeholder="Enter user name...">
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

                <!-- Comments Table -->
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">
                            <i class="fas fa-list me-2"></i>
                            Comments (${totalComments} total)
                        </h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="table-dark">
                                    <tr>
                                        <th>ID</th>
                                        <th>User</th>
                                        <th>Course</th>
                                        <th>Comment</th>
                                        <th>Date</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="comment" items="${comments}">
                                        <tr>
                                            <td>${comment.id}</td>
                                            <td>
                                                <div class="fw-bold">${comment.user.fullname}</div>
                                                <div class="text-muted small">${comment.user.email}</div>
                                            </td>
                                            <td>
                                                <div class="fw-bold">${comment.course.title}</div>
                                                <div class="text-muted small">ID: ${comment.course.id}</div>
                                            </td>
                                            <td>
                                                <div class="comment-content" style="max-width: 300px;">
                                                    <c:choose>
                                                        <c:when test="${comment.content.length() > 100}">
                                                            <span class="comment-preview">${comment.content.substring(0, 100)}...</span>
                                                            <span class="comment-full d-none">${comment.content}</span>
                                                            <br>
                                                            <button type="button" class="btn btn-link btn-sm p-0" onclick="toggleComment(this)">
                                                                Show more
                                                            </button>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${comment.content}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </td>
                                            <td>
                                                <fmt:formatDate value="${comment.createdAt}" pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td>
                                                <div class="btn-group" role="group">
                                                    <button type="button" class="btn btn-sm btn-outline-primary" 
                                                            onclick="viewComment(${comment.id})" title="View Full Comment">
                                                        <i class="fas fa-eye"></i>
                                                    </button>
                                                    <button type="button" class="btn btn-sm btn-outline-danger" 
                                                            onclick="deleteComment(${comment.id})" title="Delete Comment">
                                                        <i class="fas fa-trash"></i>
                                                    </button>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <c:if test="${totalPages > 1}">
                            <nav aria-label="Comment pagination">
                                <ul class="pagination justify-content-center">
                                    <c:if test="${currentPage > 1}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage - 1}&course=${param.course}&user=${param.user}">
                                                Previous
                                            </a>
                                        </li>
                                    </c:if>
                                    
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                                            <a class="page-link" href="?page=${i}&course=${param.course}&user=${param.user}">
                                                ${i}
                                            </a>
                                        </li>
                                    </c:forEach>
                                    
                                    <c:if test="${currentPage < totalPages}">
                                        <li class="page-item">
                                            <a class="page-link" href="?page=${currentPage + 1}&course=${param.course}&user=${param.user}">
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

    <!-- View Comment Modal -->
    <div class="modal fade" id="viewCommentModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Comment Details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="commentDetails">
                    <!-- Content will be loaded dynamically -->
                </div>
            </div>
        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteCommentModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Delete Comment</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete this comment? This action cannot be undone.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="button" class="btn btn-danger" id="confirmDelete">Delete</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        let currentCommentId = null;

        function toggleComment(button) {
            const commentContent = button.closest('.comment-content');
            const preview = commentContent.querySelector('.comment-preview');
            const full = commentContent.querySelector('.comment-full');
            
            if (preview.classList.contains('d-none')) {
                preview.classList.remove('d-none');
                full.classList.add('d-none');
                button.textContent = 'Show more';
            } else {
                preview.classList.add('d-none');
                full.classList.remove('d-none');
                button.textContent = 'Show less';
            }
        }

        function viewComment(commentId) {
            fetch(`${pageContext.request.contextPath}/admin/comments?action=view&id=${commentId}`)
                .then(response => response.text())
                .then(html => {
                    document.getElementById('commentDetails').innerHTML = html;
                    new bootstrap.Modal(document.getElementById('viewCommentModal')).show();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error loading comment details');
                });
        }

        function deleteComment(commentId) {
            currentCommentId = commentId;
            new bootstrap.Modal(document.getElementById('deleteCommentModal')).show();
        }

        document.getElementById('confirmDelete').addEventListener('click', function() {
            fetch('${pageContext.request.contextPath}/admin/comments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=delete&commentId=${currentCommentId}`
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    bootstrap.Modal.getInstance(document.getElementById('deleteCommentModal')).hide();
                    location.reload();
                } else {
                    alert('Error: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while deleting comment.');
            });
        });
    </script>
</body>
</html>
