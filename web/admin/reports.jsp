<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="model.DTO.ReportListDTO" %>
<%@ page import="model.notification.Report" %>
<%@ page import="model.notification.ReportType" %>
<%@ page import="model.notification.ReportTargetType" %>
<%@ page import="util.JSPUtils" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Report Management</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/reports-style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="components/admin-header.jsp" %> 
        <div class="admin-container container-fluid">

            <div class="admin-main-content row">
                <%@ include file="components/admin-sidebar.jsp" %>
                <div class="admin-content-area col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <h2>Report Management</h2>

                    <c:if test="${not empty param.message}">
                        <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                    </c:if>

                    <div class="filter-section">
                        <form action="${pageContext.request.contextPath}/admin/reports" method="get" class="form-inline">
                            <div class="form-group">
                                <label for="filterType">Report Type:</label>
                                <select name="filterType" id="filterType" class="form-control">
                                    <option value="">All Types</option>
                                    <c:forEach var="type" items="${reportTypes}">
                                        <option value="${type}" ${selectedType == type ? 'selected' : ''}>${JSPUtils.htmlEncode(type.name())}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="filterStatus">Status:</label>
                                <select name="filterStatus" id="filterStatus" class="form-control">
                                    <option value="">All Statuses</option>
                                    <c:forEach var="status" items="${reportStatuses}">
                                        <option value="${status}" ${selectedStatus == status ? 'selected' : ''}>${JSPUtils.htmlEncode(status.name())}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="filterTargetType">Target Type:</label>
                                <select name="filterTargetType" id="filterTargetType" class="form-control">
                                    <option value="">All Target Types</option>
                                    <c:forEach var="targetType" items="${reportTargetTypes}">
                                        <option value="${targetType}" ${selectedTargetType == targetType ? 'selected' : ''}>${JSPUtils.htmlEncode(targetType.name())}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Apply Filters</button>
                            <a href="${pageContext.request.contextPath}/admin/reports" class="btn btn-secondary">Clear Filters</a>
                        </form>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Reporter</th>
                                    <th>Report Type</th>
                                    <th>Content Preview</th>
                                    <th>Target Type</th>
                                    <th>Target </th>
                                    <th>Status</th>
                                    <th>Report Date</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty reports}">
                                        <c:forEach var="report" items="${reports}">
                                            <tr>
                                                <td>${JSPUtils.htmlEncode(report.id)}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/admin/learners?action=details&id=${JSPUtils.htmlEncode(report.reporterId)}">
                                                        ${JSPUtils.htmlEncode(report.reporterName)}
                                                    </a>
                                                </td>
                                                <td>${JSPUtils.htmlEncode(report.type.name())}</td>
                                                <td>${JSPUtils.htmlEncode(report.contentPreview)}</td>
                                                <td>${JSPUtils.htmlEncode(report.reportedItemType.name())}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${report.reportedItemType.name() == 'COURSE'}">
                                                            <c:set var="targetDetailsUrl" value="${pageContext.request.contextPath}/admin/courses?action=view&id=${report.reportedItemId}" />
                                                        </c:when>
                                                        <c:when test="${report.reportedItemType.name() == 'COMMENT'}">
                                                            <c:set var="targetDetailsUrl" value="${pageContext.request.contextPath}/admin/comments?action=details&id=${report.reportedItemId}" />
                                                        </c:when>
                                                        <c:when test="${report.reportedItemType.name() == 'INSTRUCTOR'}">
                                                            <c:set var="targetDetailsUrl" value="${pageContext.request.contextPath}/admin/instructors?action=details&id=${report.reportedItemId}" />
                                                        </c:when>
                                                        <c:when test="${report.reportedItemType.name() == 'LEARNER'}">
                                                            <c:set var="targetDetailsUrl" value="${pageContext.request.contextPath}/admin/learners?action=details&id=${report.reportedItemId}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="targetDetailsUrl" value="#" />
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <a href="${targetDetailsUrl}">Link</a>
                                                </td>
                                                <td>
                                                    <span class="badge
                                                          <c:choose>
                                                              <c:when test="${report.status == PENDING}">bg-warning</c:when>
                                                              <c:when test="${report.status == SEEN}">bg-info</c:when>
                                                              <c:when test="${report.status == RESOLVED}">bg-success</c:when>
                                                              <c:when test="${report.status == REJECTED}">bg-danger</c:when>
                                                              <c:otherwise>bg-secondary</c:otherwise>
                                                          </c:choose>
                                                          ">${JSPUtils.htmlEncode(report.status.name())}</span>
                                                </td>
                                                <td><fmt:formatDate value="${JSPUtils.toUtilDate(report.createdAt)}" pattern="yyyy-MM-dd HH:mm"/></td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/admin/reports?action=details&id=${JSPUtils.htmlEncode(report.id)}" class="btn btn-sm btn-info">View</a>
                                                    <button type="button" class="btn btn-sm btn-danger" onclick="confirmDelete(${JSPUtils.htmlEncode(report.id)})">Delete</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="10" class="text-center">No reports found.</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <div class="pagination-container">
                        <ul class="pagination">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="${JSPUtils.buildPaginationUrl(request, currentPage - 1)}">Previous</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i" varStatus="status">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${pageUrls[status.index - 1]}">${i}</a>
                                </li>
                            </c:forEach>

                            <c:if test="${currentPage < totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="${JSPUtils.buildPaginationUrl(request, currentPage + 1)}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${pageContext.request.contextPath}/admin/reports" method="post" style="display: none;">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="id" id="deleteReportId">
        </form>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                                        function confirmDelete(reportId) {
                                                            if (confirm('Are you sure you want to delete this report?')) {
                                                                document.getElementById('deleteReportId').value = reportId;
                                                                document.getElementById('deleteForm').submit();
                                                            }
                                                        }
        </script>
    </body>
</html>
