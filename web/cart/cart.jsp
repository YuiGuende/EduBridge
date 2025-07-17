<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Course Cart</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
        <link rel="stylesheet" href="cart/CartStyles.css"/>
    </head>
    <body>
        <div class="header">
            <img src="image/logocap.jpg" alt="logo">
            <div class="search-area">
                <input type="text" placeholder="Tìm kiếm...">
                <div class="search-icon"><i class="fa-solid fa-magnifying-glass"></i></div>
            </div>
            <i class="fa-solid fa-cart-plus"></i>
            <i class="fa-solid fa-message"></i>
            <i class="fa-solid fa-bell"></i>
            <i class="fa-solid fa-user"></i>
        </div>

        <div class="container-total">
            <div class="display-1"><h2>Course Cart</h2></div>

            <div class="container">
                <!-- LEFT: CART ITEM -->
                <div class="con-dis1">
                    <div class="iteam1">
                        <i class="fa-solid fa-arrow-left"></i><h3>Continue Choose Course</h3>
                    </div>
                    <div class="iteam2">
                        <i class="fa-solid fa-cart-flatbed"></i><h3>Course in Cart</h3>
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

                    <!-- CART ITEMS -->
                    <div class="father-d-1">
                        <c:forEach items="${cartDTO.cartItems}" var="item">
                            <div class="d-1">
                                <label class="custom-checkbox">
                                    <input type="checkbox" class="course-checkbox" checked />
                                    <span class="checkmark"></span>
                                </label>
                                <img src="${item.thumbnailUrl}" alt="${item.title}">
                                <div class="flex-1">
                                    <h6>${item.title}</h6>
                                    <p>Original Price: <del>${item.originalPrice}đ</del></p>
                                    <p>Discounted Price: ${item.discountedPrice}đ</p>
                                </div>
                                <div class="d-flex">
                                    <form method="post" action="${pageContext.request.contextPath}/cart">
                                        <input type="hidden" name="id" value="${item.courseId}" />
                                        <input type="hidden" name="action" value="remove" />
                                        <button type="submit" class="btn btn-danger">
                                            <i class="fa-solid fa-trash delete-item"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- RIGHT: PAYMENT -->
                <div class="con-dis2">
                    <div class="payment-info">

<!--                        <div class="d-flex justify-content-between align-items-center">
                            <span>Card details</span>
                        </div>-->

                        <!-- DISCOUNT CODE -->
                        <div class="discount-section">
                            <form action="${pageContext.request.contextPath}/cart/apply-discount" method="post" class="discount-form">
                                <label for="discountCode">Discount Code:</label>
                                <div class="discount-input-wrapper">
                                    <input type="text" id="discountCode" name="discountCode" placeholder="Enter code..." />
                                    <button type="submit" class="apply-btn">Apply</button>
                                </div>
                                <c:if test="${not empty discountSuccess}">
                                    <p class="discount-success">${discountSuccess}</p>
                                </c:if>
                                <c:if test="${not empty discountError}">
                                    <p class="discount-error">${discountError}</p>
                                </c:if>
                            </form>
                        </div>

                        <!-- PRICE SUMMARY -->
                        <hr class="line">

                        <div class="d-popi"><span><strong>Total:</strong>  </span><span class="d-block">${cartDTO.subtotal}</span></div>
                            <c:if test="${not empty cartDTO.discount}">
                            <div class="d-popi"><span>Discount (${cartDTO.discount.code}):</span>
                                <span class="d-block">- ${cartDTO.discountAmount}</span>
                            </div>
                        </c:if>

           

                        <!-- ✅ FORM: Gửi selectedCourseIds tới /checkout -->
                        <form action="checkout" method="post" id="checkout-form">
                            <input type="hidden" name="action" value="checkout"/>
                            <c:forEach items="${cartDTO.cartItems}" var="item">
                                <input type="hidden" name="selectedCourseIds" value="${item.courseId}" />
                            </c:forEach>
                            <button type="submit" class="btn-checkout">BUY NOW</button>
                        </form>

                        <h5 style="color:red;">${error}</h5>
                    </div>
                </div>
            </div>

            <!-- RECENTLY ADDED -->
            <div class="display-2">
                <h4>Recently in Your Cart</h4>
                <c:forEach items="${recentItems}" var="recentItem">
                    <div class="d-1">
                        <img src="image/${recentItem.image}" alt="${recentItem.name}">
                        <div class="flex-1">
                            <h6>${recentItem.name}</h6>
                            <span>By: ${recentItem.instructor.name}</span>
                            <p>Update: ${recentItem.updateDate}</p>
                            <p><i class="fa-solid fa-person"></i> ${recentItem.studentCount} students</p>
                        </div>
                        <div class="d-flex">
                            <span class="d-block">${recentItem.price}đ</span>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- RECOMMENDED COURSES -->
            <div class="display-3">
                <h4>Your Course You May Like</h4>
                <div class="course-list">
                    <c:forEach items="${recommendedCourses}" var="course">
                        <div class="course1">
                            <img src="image/${course.image}" alt="${course.name}">
                            <h4>${course.name}</h4>
                            <p>${course.instructor.name}</p>
                            <p><i class="fa-solid fa-star"></i> ${course.rating} rating</p>
                            <p><i class="fa-solid fa-person"></i> ${course.studentCount} students</p>
                            <p><i class="fa-brands fa-youtube"></i> ${course.videoCount} videos</p>
                            <span class="price">${course.price}đ</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <!-- FOOTER -->
        <div class="Footer">
            <img src="image/logo.png" alt="logo">
            <h2><i class="fa-solid fa-at"></i>EduBridge: Since 2025</h2>
        </div>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const sectionHeader = document.querySelector(".section-header");
                const selectedSpan = sectionHeader?.querySelector("span");
                const sectionBody = document.querySelector(".section-body");
                const options = sectionBody?.querySelectorAll("li span");

                sectionHeader?.addEventListener("click", function (e) {
                    e.stopPropagation();
                    sectionBody.classList.toggle("show");
                });

                options?.forEach(option => {
                    option.addEventListener("click", function (e) {
                        e.stopPropagation();
                        const newValue = this.textContent.trim();
                        selectedSpan.textContent = newValue;
                        sectionBody.classList.remove("show");
                    });
                });

                document.addEventListener("click", function (e) {
                    if (!sectionHeader.contains(e.target)) {
                        sectionBody.classList.remove("show");
                    }
                });
            });
        </script>
    </body>
</html>
