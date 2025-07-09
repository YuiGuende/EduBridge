<%-- 
    Document   : homeInstructor
    Created on : Jul 9, 2025, 8:42:07 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link rel="stylesheet" href="homeInstructor/homeInstructorStyles.css">
        <title>Instructor Dashboard</title>
    </head>
    <body>
        <h2>Instructor Dashboard</h2>

        <!-- Tổng doanh thu -->
        <div class="stat-box">
            <div class="card">
                <h3>Tổng Doanh Thu</h3>
                <p><b>${totalRevenue.total} đ</b></p>
            </div>
            <div class="card">
                <h3>Tháng Này</h3>
                <p><b>${totalRevenue.monthly} đ</b></p>
            </div>
            <div class="card">
                <h3>Năm Nay</h3>
                <p><b>${totalRevenue.yearly} đ</b></p>
            </div>
        </div>

        <!-- Biểu đồ doanh thu -->
        <div class="chart-container">
            <h3>Doanh thu theo tháng</h3>
            <canvas id="monthlyChart" height="100"></canvas>
        </div>

        <script>
            const monthLabels = [
            <c:forEach var="m" items="${monthlyRevenue}">
                "<c:out value='${m.month}'/>",
            </c:forEach>
            ];
            const monthData = [
            <c:forEach var="m" items="${monthlyRevenue}">
                <c:out value='${m.total}'/>,
            </c:forEach>
            ];

            new Chart(document.getElementById("monthlyChart"), {
                type: "line",
                data: {
                    labels: monthLabels,
                    datasets: [{
                            label: "Doanh thu (VNĐ)",
                            data: monthData,
                            borderColor: "#1A2A6C",
                            backgroundColor: "rgba(26,42,108,0.1)",
                            fill: true,
                            tension: 0.3
                        }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {beginAtZero: true}
                    }
                }
            });
        </script>

        <!-- Top khóa học -->
        <h3>Top Khóa Học Bán Chạy</h3>
        <table>
            <thead>
                <tr>
                    <th>Tên Khóa Học</th>
                    <th>Số Lượng Học Viên</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="course" items="${bestSellers}">
                    <tr>
                        <td>${course.name}</td>
                        <td>${course.total}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Top học viên -->
        <h3>Top Học Viên Chi Tiêu Cao</h3>
        <table>
            <thead>
                <tr>
                    <th>Họ Tên</th>
                    <th>Email</th>
                    <th>Tổng Tiền Đã Mua</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="buyer" items="${topBuyers}">
                    <tr>
                        <td>${buyer.fullname}</td>
                        <td>${buyer.email}</td>
                        <td>${buyer.total} đ</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>   
    </body>
</html>
