<%-- 
    Document   : order-history
    Created on : 8 thg 7, 2025, 01:51:28
    Author     : GoniperXComputer
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Lịch sử đơn hàng</title>
    <style>
        table { width: 80%; margin: auto; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
        h2 { text-align: center; }
    </style>
</head>
<body>
    <h2>Lịch sử đơn hàng</h2>
    <table>
        <thead>
            <tr>
                <th>ID Đơn</th>
                <th>Ngày đặt</th>
                <th>Khóa học</th>
                <th>Tổng tiền</th>
                <th>Giảm giá</th>
                <th>Thanh toán</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orderList}">
            <tr>
                <td>${order.id}</td>
                <td>${order.orderDate}</td>
                <td>
                    <ul>
                        <c:forEach var="item" items="${order.items}">
                            <li>${item.course.title} (${item.quantity}x)</li>
                        </c:forEach>
                    </ul>
                </td>
                <td>${order.subtotal}</td>
                <td>${order.discountAmount}</td>
                <td>${order.total}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>

