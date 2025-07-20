<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.example.dto.CommentDetailDTO" %>
<%@ page import="com.example.util.JSPUtils" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Comment Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-style.css">
</head>
<body>
    <div class="admin-container">
        <%@ include file="components/admin-sidebar.jsp" %>
        <div class="admin-main-content">
            <%@ include file="components/admin-header.jsp" %>
            <div class="admin-content-area">
                <h2>Comment Details - ID: ${JSPUtils.htmlEncode(comment.id)}</h2>

                <c:if test="${not empty param.message}">
                    <div class="alert alert-success">${JSPUtils.htmlEncode(param.message)}</div>
                </c:if>

                <div class="details-card">
                    <h3>Comment Information</h3>
                    <p><strong>Comment ID:</strong> ${JSPUtils.htmlEncode(comment.id)}</p>
                    <p><strong>User:</strong> ${JSPUtils.htmlEncode(comment.userName)}</p>
                    <p><strong>Course:</strong> ${JSPUtils.htmlEncode(comment.courseTitle)}</p>
                    <p><strong>Date:</strong> <fmt:formatDate value="${comment.commentDate}" pattern="yyyy-MM-dd HH:mm"/></p>
                    <p><strong>Status:</strong> ${JSPUtils.htmlEncode(comment.status)}</p>
                    <p><strong>Content:</strong></p>
                    <div class="comment-content-box">
                        <p>${JSPUtils.htmlEncode(comment.content)}</p>
                    </div>

                    <h3>Actions</h3>
                    <div class="action-buttons">
                        <a href="${pageContext.request.contextPath}/admin/comments?action=toggleStatus&id=${JSPUtils.htmlEncode(comment.id)}" class="btn btn-warning">Toggle Status</a>
                        <a href="${pageContext.request.contextPath}/admin/comments?action=delete&id=${JSPUtils.htmlEncode(comment.id)}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this comment?');">Delete Comment</a>
                    </div>
                </div>

                <div class="back-link">
                    <a href="${pageContext.request.contextPath}/admin/comments" class="btn btn-secondary">Back to Comment List</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
