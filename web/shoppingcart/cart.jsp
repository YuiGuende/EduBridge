<%-- 
    Document   : cart
    Created on : 7 thg 7, 2025, 16:09:22
    Author     : GoniperXComputer
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Course Cart</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css">
    <link rel="stylesheet" href="shoppingcart/CartStyles.css"/>
    <style>
        body, html{
    margin: 0;
    padding: 0;
}
body {
    font-family: Arial, Helvetica, sans-serif !important;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    background-color: #ffffff;
}

  
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  padding: 12px 30px;
  background-color:white;
  height: 15%;
  box-shadow: 0 5px 8px 5px rgba(0, 0, 0, 0.3);
}

.header i:hover{
color: white;  
}

.header img {
width: 7%;
margin: 10px 20px ;
height: 90%;
 
}
.search-area {
 display: flex;
  align-items: center;
  border: 1px solid #ccc;         
  border-radius: 30px;            
  padding: 10px 16px;
  width: 90%;
  max-width: 800px;
  background-color: white;
  box-sizing: border-box;
  height: 50px;
}


.search-area input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 16px;
  width: 400px;
  color: #333;
}

.search-area i{
  font-size: 18px;
  color: black;
  cursor: pointer;
}
.search-icon{
  background-color: white;
  border-radius: 50%;
  transition: all 0.3s ease;
}
.search-icon i {
  font-size: 16px;
  color: black;
}

.header i.fa-solid {
  font-size: 24px;
  color:#1A2A6C;
  width: 32px;
  height: 32px;
  text-align: center;
  line-height: 32px;
  border-radius: 50%;
  transition: cubic-bezier(0.075, 0.82, 0.165, 1);
}

.header i.fa-solid:hover {
  color: #f9f9f9;
  background-color:#1A2A6C;
  cursor: pointer;
}

/* còn cách đều lề */
.container-total{
  align-items: flex-start;         
  padding: 20px 20%;             
  gap: 20px; 
}

.container-total h2{
    font-family: 'Times New Roman', Times, serif;
    text-align: left;
    text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);;
    font-size: 4rem;
}

.container{
    box-shadow: 0 5px 8px 5px rgba(0, 0, 0, 0.3);
    display: flex;
    gap: 20px;
}
.con-dis1{
    width: 70%;
    border: #333 solid 1px; /* xóa*/
    padding:  5px auto;
}
.iteam1 {
    border-bottom: #333 solid 0.5px;
    padding-left: 0;
    display: flex;
    gap: 10px;
    height: 60px;
}

.iteam1 > i{
    padding: 24px 10px;
}
.iteam1 > h3{
    text-align: center;
    margin:  auto 10px;
}

.iteam2{
    padding-left: 0;
    display: flex;
    gap: 10px;

}
.iteam2 > i{
   margin: 20px 5px;
}
.iteam2 > h3 {
    text-align: center;
    margin: auto 23px;
}
.iteam-chos1{
    display: flex;
    font-size: 22px;
    justify-content: space-evenly;
    align-items: center;
    border-bottom: #1c1d1f solid 0.5px;
    height: 60px;
}

section {
  border:black solid 0.5px;
  padding: 10px 10px;
  width: 100%;
  justify-items: center;
}

.section-header {
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  font-size: 16px;
  color: #2d2f31;
  gap: 10px;
}



.section-body {
  display: none;
  margin-top: 10px;
  padding-left: 15px;
}

.section-body li {
  display: flex;
  justify-content: space-between;
  padding: 5px 0;
  font-size: 14px;
  border: #1c1d1f solid 0.5px;

}

.section-body li a {
  text-decoration: none;
  color: #5624d0;
}
.father-d-1 {

  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  max-width: 1000px;
  margin: auto;
}

.d-1 {
  width: 90%;
  display: flex;
  align-items:center;
  justify-content: space-between;
  padding: 15px;    
  border: 1px solid #ddd;
  border-radius: 10px;
  gap: 15px;
  box-shadow: 0 5px 8px 5px rgba(0, 0, 0, 0.3);
}
.d-1 > label:hover{
    cursor: pointer;
}
.d-1 > img{
    width: 20%;
    display: inline-block;
    height: auto;
    border: #1c1d1f solid 0.3px;
}

.flex-1{
    height: auto;
    display: inline-block;
    
}

.flex-1 > h6{
    font-size: 1.2rem;
    margin: 10px; 
    padding: 5px auto;
}
.flex-1 > span >a {
    text-decoration: none;
}
.flex-2{
    display: flex;
    align-items: center;
}
.flex-2 >p{
    color: gold;

}
.flex-2 >ol{
    display: flex;
    list-style: none;
    color: gold;
    gap: 5px;
    padding: 0;
}
.flex-2 .Student{
    color: #1c1d1f;
}
.flex-1 > ol{
    display: flex;
    list-style: none;
    align-items: center;
    gap: 15px;
    padding: 5px;
    margin: 0;
}
.d-flex{
display: flex;
gap: 3px;
}
.d-flex .d-block{
    display: flex;
    color: green;
}
.d-flex i:hover{
    cursor: pointer;
    color: red;
}

.con-dis2{
    width: 30%;
     padding:5px 20px;
  box-sizing: border-box;
  margin: 0 auto; 

}

.con-dis2 > .payment-info{
  position: sticky;
  top:0;
  left: 0;
  right:0;
}
.payment-info {
  background: blue;
  padding: 10px;
  border-radius: 6px;
  color: #fff;
  font-weight: bold;
  font-size: 18px;
  height: 40%;
}

.cart {
  background: #fff;
}

.p-about {
  font-size: 12px;
}

.table-shadow {
  -webkit-box-shadow: 5px 5px 15px -2px rgba(0,0,0,0.42);
  box-shadow: 5px 5px 15px -2px rgba(0,0,0,0.42);
}

.type {
  font-weight: 400;
  font-size: 16px;
  display:  flex;
  gap: 10px;
  padding: 10px;
  
}

.radio{
  margin-top: 10px;
}

label.radio {
  cursor: pointer;
 
}

label.radio input {
  position: absolute;
  top: 0;
  left: 0;
  visibility: hidden;
  pointer-events: none;
 
}

label.radio span {
  padding: 1px 12px;
  border: 2px solid #ada9a9;
  display: inline-block;
  color: #8f37aa;
  border-radius: 3px;
  text-transform: uppercase;
  font-size: 11px;
  font-weight: 300;
}

label.radio input:checked + span {
  border-color: #fff;
  background-color: blue;
  color: #fff;
}

.credit-inputs {
  background: rgb(102,102,221);
  color: #fff !important;
  border-color: rgb(102,102,221);
}

.credit-inputs::placeholder {
  color: #fff;
  font-size: 13px;
}

.credit-card-label {
  font-size: 9px;
  font-weight: 300;
}

.form-control.credit-inputs:focus {
  background: rgb(102,102,221);
  border: rgb(102,102,221);
}

.line {
  border-bottom: 1px solid rgb(102,102,221);
}

.information span {
  font-size: 12px;
  font-weight: 500;
  margin: 10px auto ;
}

.information {
  margin-bottom: 5px;
}

.items {
  -webkit-box-shadow: 5px 5px 4px -1px rgba(0,0,0,0.25);
  box-shadow: 5px 5px 4px -1px rgba(0, 0, 0, 0.08);
}

.spec {
  font-size: 11px;
}

.payment-info > .d-flex{
  height: 10%;
  justify-content: space-evenly;
  margin: 10px;
}
.payment-info > .d-flex > img{
  border-radius: 20%;
  width: 40px;
}
.payment-info > .d-popi{
  margin: 10px;
  display: flex;
  justify-content: space-between;
}
.payment-info > .d-popi > .d-block{
  color: green;
  width: 50%;
  display: flex;
  align-items: center;
}
/* Discount Form */
.discount-section {
  margin: 15px 0;
  padding: 10px;
  background-color: rgba(255,255,255,0.1);
  border-radius: 5px;
}

.discount-form label {
  display: block;
  margin-bottom: 6px;
  font-size: 14px;
  font-weight: bold;
  color: #fff;
}

.discount-input-wrapper {
  display: flex;
  gap: 10px;
}

.discount-input-wrapper input[type="text"] {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 13px;
}

.discount-input-wrapper .apply-btn {
  padding: 8px 16px;
  background-color: #00cc99;
  color: white;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  transition: 0.3s;
}

.discount-input-wrapper .apply-btn:hover {
  background-color: #009973;
}

.discount-success {
  margin-top: 8px;
  color: #00ff88;
  font-size: 13px;
}

.discount-error {
  margin-top: 8px;
  color: #ff4d4d;
  font-size: 13px;
}

.px-3{
  margin: 10px 50px  80px;
}

.px-3 a {
  display: inline-block;
  color: black;                 
  padding: 10px 20px;         
  border-radius: 20%;           
  font-weight: normal;           
  text-align: center;            
  text-decoration: none;         
  transition: all 0.3s ease;
  margin: 40px auto;
  background-color: white;    
}

.px-3 a:hover {
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);  
  font-weight: bold;                         
  transform: translateY(-2px); 
  color: green;              
}

.course-list {
  flex-wrap: wrap; 
  display: flex;
  gap: 20px;
  justify-content: center;
}

.course1, .course2, .course3 {
  width: 300px;
  border: 1px solid #ccc;
  padding: 15px;
  border-radius: 8px;
  background-color: #f9f9f9;
  text-align: center;
}

.course1 img,
.course2 img,
.corse3 img {
  width: 100%;
  height: auto;
  border-radius: 6px;
  margin-bottom: 10px;
}

.rating {
  list-style: none;
  padding: 0;
  display: flex;
  justify-content: center;
  gap: 6px;
  margin: 5px 0;
  flex-wrap: wrap;
}

.rating h6 {
  margin-right: 8px;
  font-size: 14px;
  font-weight: 600;
}

.rating li {
  color: #f5c518;
}


.course1 img{
   width: 70%;
  height: auto; 
  object-fit: cover;
}
.rating {
  list-style: none;
  padding: 0;
  margin: 10px 0;
  display: flex;
  gap: 6px; 
  flex-wrap: wrap; 
}
.rating h6 {
  margin: 0 10px 0 0;
  font-size: 14px;
  font-weight: 600;
}

.rating li {
  display: inline;
  font-size: 16px;
  color: #f5c518; 
}

.price {
  font-size: 20px;
  font-weight: bold;
  color: green;
}
.course2 img{
   width: 70%;
  height: auto; 
  object-fit: cover;
}
.rating {
  list-style: none;
  padding: 0;
  margin: 10px 0;
  display: flex;
  gap: 6px; 
  flex-wrap: wrap; 
}
.rating h6 {
  margin: 0 10px 0 0;
  font-size: 14px;
  font-weight: 600;
}

.rating li {
  display: inline;
  font-size: 16px;
  color: #f5c518; 
}

.price {
  font-size: 20px;
  font-weight: bold;
  color: green;
}

.course1, .course2, .course3 {
  width: 300px;
  border: 1px solid #ccc;
  padding: 15px;
  border-radius: 8px;
  background-color: #f9f9f9;
  text-align: center;
  transition: all 0.3s ease;
}
.course1:hover,
.course2:hover,
.course3:hover {
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); 
  transform: scale(1.03);
  background-color: #ffffff; 
  z-index: 1;
}
.course3 img{
    width: 70%;
  height: auto; 
  object-fit: cover;
}

.Footer{
  background-color: #1A2A6C;
  display: flex;
  justify-content: center;
  height: 150px;
  color: white;

}

.Footer img{
  width: 10%;
  height: 50%;
  margin: 50px 0px 20px 0px;
  padding: 10px;
}
.Footer h2{
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-weight: 500;
  margin: 55px 0px 20px 0px;
  padding: 10px;
}
    </style>
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
                    <c:when test="${empty cart.cartItems}">
                        <h6>Your cart is currently empty.</h6>
                    </c:when>
                    <c:otherwise>
                        <h6>${cart.cartItems.size()} Course(s) in your cart</h6>
                    </c:otherwise>
                </c:choose>

                <div class="section">
                    <div class="section-header">
                        <strong>▶ Sort By:</strong>
                        <span>price</span>
                    </div>
                    <ul class="section-body">
                        <li><span>date</span></li>
                        <li><span>student</span></li>
                        <li><span>rating</span></li>
                    </ul>
                </div>
            </div>

            <!-- CART ITEMS -->
            <div class="father-d-1">
                <c:forEach items="${cart.cartItems}" var="item">
                    <div class="d-1">
                        <label class="custom-checkbox">
                            <input type="checkbox" class="course-checkbox" checked />
                            <span class="checkmark"></span>
                        </label>
                        <img src="${item.course.thumbnailUrl}" alt="${item.course.title}">
                        <div class="flex-1">
                            <h6>${item.course.title}</h6>
                            <span>By:
                                <c:forEach items="${item.course.instructors}" var="inst" varStatus="loop">
                                    ${inst.user.fullname}<c:if test="${!loop.last}">, </c:if>
                                </c:forEach>
                            </span>
                            <p>Updated: ${item.course.lastUpdate}</p>
                            <div class="flex-2">
                                <p>${item.course.rate.rate} Rating</p>
                                <ol>
                                    <c:forEach begin="1" end="${item.course.rate.rate}" var="i">
                                        <li><i class="fa-solid fa-star"></i></li>
                                    </c:forEach>
                                </ol>
                            </div>
                            <ol>
                                <li><p>${item.course.estimatedTime} HOURS</p></li>
                                <li>${item.course.status}</li>
                                <li>${item.course.primaryLanguage.name}</li>
                            </ol>
                        </div>
                        <div class="d-flex">
                            <span class="d-block">${item.course.price}đ <i class="fa-solid fa-money-bill-wave"></i></span>
                            <form method="post" action="cart/remove">
                                <input type="hidden" name="courseId" value="${item.course.id}" />
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
                <div class="d-flex justify-content-between align-items-center">
                    <span>Card details</span>
                    <img class="rounded" src="image/black-goku-dragon-ball-super-os-2560x1600.jpg" width="30">
                </div>

                <span class="type d-block mt-3 mb-1">Card type</span>
                <label class="radio"><input type="radio" name="card" value="mastercard" checked>
                    <span><img width="30" src="https://img.icons8.com/color/48/000000/mastercard.png"/></span>
                </label>
                <label class="radio"><input type="radio" name="card" value="visa">
                    <span><img width="30" src="https://img.icons8.com/officel/48/000000/visa.png"/></span>
                </label>
                <label class="radio"><input type="radio" name="card" value="amex">
                    <span><img width="30" src="https://img.icons8.com/ultraviolet/48/000000/amex.png"/></span>
                </label>
                <label class="radio"><input type="radio" name="card" value="paypal">
                    <span><img width="30" src="https://img.icons8.com/officel/48/000000/paypal.png"/></span>
                </label>

                <!-- DISCOUNT CODE -->
                
<!-- DISCOUNT CODE SECTION -->
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
                <div class="d-popi"><span>Subtotal:</span><span class="d-block">${cart.subtotal}</span></div>
                <div class="d-popi"><span>VAT + Instructor Bonus:</span><span class="d-block">${cart.tax}</span></div>
                <c:if test="${not empty cart.discount}">
                    <div class="d-popi"><span>Discount (${cart.discount.code}):</span>
                        <span class="d-block">- ${cart.discountAmount}</span>
                    </div>
                </c:if>
                <div class="d-popi"><strong>Total:</strong>
                    <strong class="d-block">${cart.totalWithDiscount}</strong>
                </div>

                <form action="checkout" method="post" id="checkout-form">
                    <input type="hidden" name="action" value="checkout"/>
                    <button type="submit" class="btn-checkout">BUY NOW</button>
                </form>
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

<!-- SCRIPT -->
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

    document.addEventListener("click", function (e) {
        if (!sectionHeader.contains(e.target)) {
            sectionBody.classList.remove("show");
        }
    });
});
</script>
</body>
</html>
