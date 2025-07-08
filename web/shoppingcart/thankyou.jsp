<%-- 
    Document   : thankyou
    Created on : 7 thg 7, 2025, 23:30:23
    Author     : GoniperXComputer
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thank You - EduBridge</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f0f4f8;
            padding: 40px;
            margin: 0;
        }

        .thank-you-container {
            max-width: 800px;
            background-color: white;
            padding: 40px 60px;
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .thank-you-container h1 {
            color: #2ecc71;
            margin-bottom: 20px;
        }

        .thank-you-container p {
            font-size: 18px;
            color: #333;
            margin-bottom: 30px;
        }

        .thank-you-container i {
            font-size: 60px;
            color: #2ecc71;
            margin-bottom: 15px;
        }

        .course-list {
            margin-top: 20px;
            text-align: left;
        }

        .course-item {
            border-bottom: 1px solid #ddd;
            padding: 15px 0;
            display: flex;
            justify-content: space-between;
        }

        .course-item h4 {
            margin: 0;
        }

        .price {
            font-weight: bold;
            color: #3498db;
        }

        .thank-you-container a.btn {
            display: inline-block;
            margin-top: 30px;
            text-decoration: none;
            background-color: #3498db;
            color: white;
            padding: 12px 25px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .thank-you-container a.btn:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>

    <div class="thank-you-container">
        <i class="fa-solid fa-circle-check"></i>
        <h1>Cảm ơn bạn đã mua khóa học!</h1>
        <p>Chúng tôi đã nhận được thanh toán của bạn. Dưới đây là thông tin đơn hàng:</p>

        <div class="course-list">
            <c:forEach var="item" items="${purchasedCourses}">
                <div class="course-item">
                    <div>
                        <h4>${item.course.title}</h4>
                        <p><small>Người hướng dẫn: 
                            <c:forEach items="${item.course.instructors}" var="inst" varStatus="loop">
                                ${inst.user.fullname}<c:if test="${!loop.last}">, </c:if>
                            </c:forEach>
                        </small></p>
                    </div>
                    <div class="price">${item.course.price} <i class="fa-solid fa-money-bill-wave"></i></div>
                </div>
            </c:forEach>
        </div>

        <a href="home.jsp" class="btn">Tiếp tục học tập</a>
    </div>

</body>
</html>
