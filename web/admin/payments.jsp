<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.dto.PaymentListDTO" %>
<%@ page import="com.example.util.JSPUtils" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Payment Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
</head>
<body>
    <div class="admin-container">
        <%@ include file="components/admin-sidebar.jsp" %>
        <div class="admin-main-content">
            <%@ include file="components/admin-header.jsp" %>
            <div class="admin-content-area">
                <h2>Payment Management</h2>

                <div class="table-responsive">
                    <table class="data-table">
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
                            <c:forEach var="payment" items="${payments}">
                                <tr>
                                    <td>${JSPUtils.htmlEncode(payment.id)}</td>
                                    <td>${JSPUtils.htmlEncode(payment.userName)}</td>
                                    <td>${JSPUtils.htmlEncode(payment.paymentMethod)}</td>
                                    <td><fmt:formatDate value="${payment.paymentDate}" pattern="yyyy-MM-dd"/></td>
                                    <td><fmt:formatNumber value="${payment.amount}" type="currency" currencySymbol="$"/></td>
                                    <td>${JSPUtils.htmlEncode(payment.vnp)}</td>
                                    <td>${JSPUtils.htmlEncode(payment.paymentStatus)}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/payments?action=details&id=${JSPUtils.htmlEncode(payment.id)}" class="btn btn-sm btn-info">View Details</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty payments}">
                                <tr>
                                    <td colspan="8">No payments found.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="${JSPUtils.buildPaginationUrl(pageContext.request, currentPage - 1)}" class="btn btn-sm btn-secondary">Previous</a>
                    </c:if>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="${JSPUtils.buildPaginationUrl(pageContext.request, i)}" class="btn btn-sm ${currentPage == i ? 'btn-primary' : 'btn-secondary'}">${i}</a>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <a href="${JSPUtils.buildPaginationUrl(pageContext.request, currentPage + 1)}" class="btn btn-sm btn-secondary">Next</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
