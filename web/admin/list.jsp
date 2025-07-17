<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Danh sách báo cáo</title>
</head>
<body>
    <h2>Danh sách các báo cáo đã gửi</h2>
    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th>
            <th>Người gửi</th>
            <th>Loại</th>
            <th>Nội dung</th>
            <th>Ngày tạo</th>
        </tr>
        <c:forEach var="report" items="${reports}">
            <tr>
                <td>${report.id}</td>
                <td>${report.user.email}</td>
                <td>${report.type}</td>
                <td>${report.content}</td>
                <td>${report.createdAt}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
