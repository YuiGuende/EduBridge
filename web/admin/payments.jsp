<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="util.JSPUtils" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Payment Management - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
    </head>
    <body>
        <jsp:include page="components/admin-header.jsp"/>

        <div class="container-fluid">
            <div class="row">
                <jsp:include page="components/admin-sidebar.jsp"/>

                <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">
                            <i class="fas fa-credit-card me-2"></i>
                            Payment Management
                        </h1>
                    </div>

                    <c:if test="${not empty param.message}">
                        <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                    </c:if>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">${JSPUtils.htmlEncode(param.error)}</div>
                    </c:if>

                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>User</th>
                                    <th>Method</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>VNP Ref</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty payments}">
                                        <c:forEach var="payment" items="${payments}">
                                            <tr>
                                                <td>${JSPUtils.htmlEncode(payment.id)}</td>
                                                <td>${JSPUtils.htmlEncode(payment.userName)}</td>
                                                <td>${JSPUtils.htmlEncode(payment.paymentMethod)}</td>
                                                <td><fmt:formatDate value="${payment.paymentDate}" pattern="dd/MM/yyyy HH:mm"/></td>
                                                <td><fmt:formatNumber value="${payment.amount}" type="currency" currencySymbol="$" maxFractionDigits="2"/></td>
                                                <td>${JSPUtils.htmlEncode(payment.vnpTxnRef)}</td>
                                                <td>
                                                    <span class="badge bg-${payment.status == 'CONFIRMED' ? 'success' :
                                                                            payment.status == 'PENDING' ? 'warning' : 'danger'}">
                                                              ${JSPUtils.htmlEncode(payment.status)}
                                                          </span>
                                                    </td>
                                                    <td>
                                                        <a href="${pageContext.request.contextPath}/admin/payments?action=view&id=${JSPUtils.htmlEncode(payment.id)}"
                                                           class="btn btn-info btn-sm">
                                                            <i class="fas fa-eye"></i> View
                                                        </a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <tr>
                                                <td colspan="8" class="text-center">No payments found.</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>
                                </tbody>
                            </table>
                        </div>

                        <!-- Pagination -->
                        <nav aria-label="Page navigation">
                            <ul class="pagination justify-content-center">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrls[0]}" aria-label="First">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrls[currentPage - 2]}" aria-label="Previous">
                                            <span aria-hidden="true">&lsaquo;</span>
                                        </a>
                                    </li>
                                </c:if>

                                <c:forEach begin="${(currentPage - 2 > 0) ? (currentPage - 2) : 1}"
                                           end="${(currentPage + 2 <= totalPages) ? (currentPage + 2) : totalPages}" var="i">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="${pageUrls[i - 1]}">${i}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrls[currentPage]}" aria-label="Next">
                                            <span aria-hidden="true">&rsaquo;</span>
                                        </a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="${pageUrls[totalPages - 1]}" aria-label="Last">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                        </nav>
                    </main>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
            <script>
                                                                function updatePaymentStatus(paymentId, status) {
                                                                    if (confirm(`Are you sure you want to change payment status to ${status}?`)) {
                                                                        const formData = new FormData();
                                                                        formData.append('action', 'updateStatus');
                                                                        formData.append('paymentId', paymentId);
                                                                        formData.append('status', status);

                                                                        fetch('${pageContext.request.contextPath}/admin/payments', {
                                                                            method: 'POST',
                                                                            body: formData
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
                                                                                    alert('An error occurred while updating payment status.');
                                                                                });
                                                                    }
                                                                }

                                                                function deletePayment(paymentId) {
                                                                    if (confirm('Are you sure you want to delete this payment? This action cannot be undone.')) {
                                                                        const formData = new FormData();
                                                                        formData.append('action', 'delete');
                                                                        formData.append('paymentId', paymentId);

                                                                        fetch('${pageContext.request.contextPath}/admin/payments', {
                                                                            method: 'POST',
                                                                            body: formData
                                                                        })
                                                                                .then(response => response.json())
                                                                                .then(data => {
                                                                                    if (data.success) {
                                                                                        window.location.href = '${pageContext.request.contextPath}/admin/payments';
                                                                                    } else {
                                                                                        alert('Error: ' + data.message);
                                                                                    }
                                                                                })
                                                                                .catch(error => {
                                                                                    console.error('Error:', error);
                                                                                    alert('An error occurred while deleting payment.');
                                                                                });
                                                                    }
                                                                }
            </script>
        </body>
    </html>
