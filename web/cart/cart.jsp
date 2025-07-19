<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Course Cart</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link rel="stylesheet" href="cart/CartStyles.css"/>
    </head>
    <body>
        <jsp:include page="/component/header.jsp" />

        <div class="container-total">
            <div class="container">
                <div class="con-dis1">
                    <div>
                        <a href="${pageContext.request.contextPath}/home-learner">
                            <i class="fa-solid fa-arrow-left"></i> Continue to choose
                        </a>
                    </div>
                    <div class="iteam-chos1">
                        <c:choose>
                            <c:when test="${empty cartDTO.cartItems}">
                                <h6>Your cart is currently empty.</h6>
                            </c:when>
                            <c:otherwise>
                                <h6>${cartDTO.cartItems.size()} Course(s) in your cart</h6>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${not empty cartDTO.cartItems}">
                        <!-- CART TABLE -->
                        <table class="cart-table">
                            <thead>
                                <tr>
                                    <th>Course</th>
                                    <th class="text-right">Price</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cartDTO.cartItems}" var="item">
                                    <tr>
                                        <td class="course-info">
                                            <input type="checkbox" class="course-checkbox" checked data-price="${item.discountedPrice}" />
                                            <img src="${item.thumbnailUrl}" alt="${item.title}">
                                            <div>
                                                <h6>${item.title}</h6>
                                                <p>Original: <del>${item.originalPrice}đ</del></p>
                                            </div>
                                        </td>
                                        <td class="price-cell" style="text-align: right">${item.discountedPrice}đ</td>
                                        <td>
                                            <form method="post" action="${pageContext.request.contextPath}/cart">
                                                <input type="hidden" name="id" value="${item.courseId}" />
                                                <input type="hidden" name="action" value="remove" />
                                                <button type="submit" class="btn btn-danger">
                                                    <i class="fa-solid fa-trash delete-item"></i>
                                                </button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr class="total-row">
                                    <td><strong>Total</strong></td>
                                    <td class="price-cell" style="text-align: right"><strong id="cart-total">${cartDTO.subtotal}</strong></td>
                                    <td></td>
                                </tr>
                            </tfoot>
                        </table>

                        <!-- BUY NOW BUTTON CENTERED -->
                        <div class="checkout-container">
                            <form action="checkout" method="get" id="cart-checkout-form">
                                <!-- Lặp từng course đã tick -->
                                <c:forEach items="${cartDTO.cartItems}" var="item">
                                    <input type="hidden" name="selectedCourseIds" value="${item.courseId}">
                                </c:forEach>

                                <button type="submit" class="btn-checkout">CHECK OUT</button>
                            </form>
                            <h5 style="color:red;">${error}</h5>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <%@ include file="/component/footer.jsp" %>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const checkboxes = document.querySelectorAll(".course-checkbox");
                const totalElement = document.getElementById("cart-total");

                function formatCurrency(value) {
                    return value.toLocaleString("vi-VN") + "đ";
                }

                function updateTotal() {
                    let total = 0;
                    checkboxes.forEach(cb => {
                        if (cb.checked) {
                            total += parseFloat(cb.dataset.price);
                        }
                    });
                    totalElement.textContent = formatCurrency(total);
                }

                checkboxes.forEach(cb => {
                    cb.addEventListener("change", updateTotal);
                });

                updateTotal();
            });
        </script>

    </body>
</html>
