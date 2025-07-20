<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="util.JSPUtils" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Payment Details - Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-details.css">
    </head>
    <body>
        <jsp:include page="components/admin-header.jsp" />

        <div class="container-fluid">
            <div class="row">
                <jsp:include page="components/admin-sidebar.jsp" />

                <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">
                            <i class="fas fa-info-circle me-2"></i>
                            Payment Details
                        </h1>
                        <div class="btn-toolbar mb-2 mb-md-0">
                            <a href="${pageContext.request.contextPath}/admin/payments" class="btn btn-secondary btn-sm">
                                <i class="fas fa-arrow-left"></i> Back to Payments
                            </a>
                        </div>
                    </div>

                    <c:if test="${not empty param.message}">
                        <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                    </c:if>
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">${JSPUtils.htmlEncode(param.error)}</div>
                    </c:if>

                    <c:if test="${not empty payment}">
                        <div class="card mb-4">
                            <div class="card-header">
                                Payment ID: ${JSPUtils.htmlEncode(payment.id)}
                            </div>
                            <div class="card-body">
                                <div class="row mb-2">
                                    <div class="col-md-4"><strong>User:</strong></div>
                                    <div class="col-md-8">
                                        <a href="${pageContext.request.contextPath}/admin/learners?action=details&id=${JSPUtils.htmlEncode(payment.userId)}">
                                            ${JSPUtils.htmlEncode(payment.userName)}
                                        </a>
                                    </div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-4"><strong>Payment Method:</strong></div>
                                    <div class="col-md-8">${JSPUtils.htmlEncode(payment.paymentMethod)}</div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-4"><strong>Payment Date:</strong></div>
                                    <div class="col-md-8"><fmt:formatDate value="${payment.paymentDate}" pattern="dd/MM/yyyy HH:mm"/></div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-4"><strong>Amount:</strong></div>
                                    <div class="col-md-8"><fmt:formatNumber value="${payment.totalAmount}" type="currency" currencySymbol="VND" maxFractionDigits="2"/></div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col-md-4"><strong>VNP Transaction Ref:</strong></div>
                                    <div class="col-md-8">${JSPUtils.htmlEncode(payment.vnpTxnRef)}</div>
                                </div>

                                <div class="row mb-2">

                                    <div class="col-md-4"><strong>Status:</strong></div>
                                    <div class="col-md-8">
                                        <span class="badge bg-${payment.paymentStatus == 'CONFIRMED' ? 'success' :
                                                                payment.paymentStatus == 'PENDING' ? 'warning' : 'danger'}">${JSPUtils.htmlEncode(payment.paymentStatus)}</span>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <c:if test="${not empty payment.courseDetails}">
                            <h3>Order Items</h3>
                            <div class="table-responsive">
                                <table class="table table-striped table-sm">
                                    <thead>
                                        <tr>
                                            <th>Course Name</th>
                                            <th>Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="item" items="${payment.courseDetails}">
                                            <tr>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/admin/courses?action=view&id=${JSPUtils.htmlEncode(item.courseId)}">
                                                        ${JSPUtils.htmlEncode(item.courseTitle)}
                                                    </a>
                                                </td>
                                                <td><fmt:formatNumber value="${item.amount}" type="currency" currencySymbol="$" maxFractionDigits="2"/></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>

                    </c:if>
                    <c:if test="${empty payment}">
                        <div class="alert alert-warning text-center">Payment details not found.</div>
                    </c:if>
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
                                                            window.location.href = '${pageContext.request.contextPath}/admin/payments'; // Redirect to list
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
