<%-- 
    Document   : checkout
    Created on : Jul 19, 2025, 12:09:01 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Checkout Confirmation</title>
        <link rel="stylesheet" href="checkout/CheckoutStyles.css"/>
    </head>
    <body>
        <jsp:include page="/component/header.jsp"/>

        <div class="checkout-container">
            <h2>Confirm Your Order</h2>

            <!-- Thông tin Learner -->
            <div class="learner-info">
                <h3><strong>Your Information</strong></h3>
                <p><strong>Name:</strong> ${learner.fullname}</p>
                <p><strong>Email:</strong> ${learner.email}</p>
            </div>

            <form action="checkout" method="post" id="checkout-form">
                <c:forEach items="${selectedCourses}" var="course">
                    <input type="hidden" name="selectedCourseIds" value="${course.id}" />
                </c:forEach>

                <!-- Bảng thông tin -->
                <table>
                    <thead>
                        <tr>
                            <th>Course</th>
                            <th>Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${selectedCourses}" var="course">
                            <tr>
                                <td>${course.title}</td>
                                <td style="text-align:right;">${course.discountPrice}đ</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td><strong>Total:</strong></td>
                            <td style="text-align:right;"><strong>${total}đ</strong></td>
                        </tr>
                    </tbody>
                </table>

                <button type="submit" class="btn-checkout">BUY NOW</button>
            </form>
        </div>
        <%@ include file="/component/footer.jsp"%>
    </body>
</html>

