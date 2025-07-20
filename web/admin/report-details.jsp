<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="model.DTO.ReportDetailDTO" %>
<%@ page import="model.notification.Report" %>
<%@ page import="util.JSPUtils" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin - Report Details</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/css/report-details-style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <%@ include file="components/admin-header.jsp" %>
        <div class="admin-container container-fluid">

            <div class="admin-main-content row ">
                <%@ include file="components/admin-sidebar.jsp" %>
                <div class="admin-content-area col-md-9 ms-sm-auto col-lg-10 px-md-4">
                    <h2>Report Details - ID: ${JSPUtils.htmlEncode(report.id)}</h2>

                    <c:if test="${not empty param.message}">
                        <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                    </c:if>

                    <div class="details-card">
                        <h3>Report Information</h3>
                        <p><strong>Report ID:</strong> ${JSPUtils.htmlEncode(report.id)}</p>
                        <p>
                            <strong>Reporter:</strong>
                            <a href="${pageContext.request.contextPath}/admin/learners?action=details&id=${JSPUtils.htmlEncode(report.reporterId)}">
                                ${JSPUtils.htmlEncode(report.reporterName)}
                            </a>
                        </p>
                        <p><strong>Report Type:</strong> ${JSPUtils.htmlEncode(report.reportType.name())}</p>
                        <p><strong>Reason:</strong> ${JSPUtils.htmlEncode(report.reason)}</p>
                        <p><strong>Report Date:</strong> ${JSPUtils.htmlEncode(report.reportDateFormatted)}</p>
                        <p>
                            <strong>Status:</strong>
                            <span class="badge
                                  <c:choose>
                                      <c:when test="${report.status == PENDING}">bg-warning</c:when>
                                      <c:when test="${report.status == SEEN}">bg-info</c:when>
                                      <c:when test="${report.status == RESOLVED}">bg-success</c:when>
                                      <c:when test="${report.status == REJECTED}">bg-danger</c:when>
                                      <c:otherwise>bg-secondary</c:otherwise>
                                  </c:choose>
                                  ">${JSPUtils.htmlEncode(report.status.name())}</span>
                        </p>
                     

                        <h3>Reported Item Details</h3>
                        <p><strong>Item Type:</strong> ${JSPUtils.htmlEncode(report.reportedItemType.name())}</p>

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

                        <p>
                            <strong>Item :</strong>
                            <a href="${targetDetailsUrl}">Link</a>
                        </p>



                        <h3>Update Report Status</h3>
                        <form action="${pageContext.request.contextPath}/admin/reports" method="post" class="form-inline">
                            <input type="hidden" name="action" value="updateStatus">
                            <input type="hidden" name="id" value="${JSPUtils.htmlEncode(report.id)}">
                            <div class="form-group">
                                <label for="status">New Status:</label>
                                <select name="status" id="status" class="form-control">
                                    <c:forEach var="statusOption" items="${reportStatusList}">
                                        <option value="${statusOption}" ${report.status == statusOption ? 'selected' : ''}>
                                            ${JSPUtils.htmlEncode(statusOption.name())}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Update Status</button>
                        </form>

                        <h3>Actions</h3>
                        <div class="action-buttons">
                            <button type="button" class="btn btn-danger" onclick="confirmDelete(${JSPUtils.htmlEncode(report.id)})">Delete Report</button>
                        </div>
                    </div>

                    <div class="back-link">
                        <a href="${pageContext.request.contextPath}/admin/reports" class="btn btn-secondary">Back to Report List</a>
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
                                    if (confirm('Are you sure you want to delete this report? This action cannot be undone.')) {
                                        document.getElementById('deleteReportId').value = reportId;
                                        document.getElementById('deleteForm').submit();
                                    }
                                }
        </script>
    </body>
</html>
