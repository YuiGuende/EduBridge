<%-- Document : learnercourse Created on : 11 thg 7, 2025, 00:46:58 Author : GoniperXComputer --%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

    <head>
        <meta charset="UTF-8">
        <title>EdurBridge - Learner View About Course</title>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
              integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="learnercourse/leanercourseStyle.css" />
    </head>

    <body>
        <div class="header">
            <img src="image/logocap.jpg" alt="logo">
            <div class="search-area">
                <input type="text" placeholder="Tìm kiếm...">
                <div class="search-icon">
                    <i class="fa-solid fa-magnifying-glass"></i>
                </div>
            </div>
            <a href="cart/cart.jsp"><i class="fa-solid fa-cart-plus"></i> </a>
            <a href="report.jsp"><i class="fa-solid fa-message"></i> </a>
            <a href="notification.jsp"><i class="fa-solid fa-bell"></i></a>
            <a href="#"><i class="fa-solid fa-user"></i></a>
        </div>

        <div class="container">
            <div
                style="background-color: #0B0742; height: 450px; position: absolute; left: 0; right: 0; top: 0; z-index: -1;">
            </div>

            <div class="sub-container">
                <div class="information-course">

                    <div class="breadcrumb">
                        <c:forEach items="${course.tags}" var="tag" varStatus="loop" end="2">
                            <a class="breadcrumb-link" href="/topic/${tag.name}">${tag.name}</a>
                            <c:if test="${!loop.last}">
                                <span class="breadcrumb-separator">›</span>
                            </c:if>
                        </c:forEach>
                    </div>

                    <div class="display-3">

                        <h1 class="display-4">
                            ${course.title}
                        </h1>

                        <h6 class="display-5">
                            ${course.headline}
                        </h6>

                        <div class="display-6">
                            <!-- Hiển thị rating -->
                            <c:choose>
                                <c:when test="${ratingCount > 0}">
                                    <p>
                                        <fmt:formatNumber value="${averageRating}" pattern="0.0" /> Rating
                                        (${ratingCount})
                                    </p>
                                </c:when>
                                <c:otherwise>
                                    <p>No ratings yet</p>
                                </c:otherwise>
                            </c:choose>

                            <!-- Hiển thị sao -->
                            <ol>
                                <c:forEach begin="1" end="5" var="i">
                                    <c:choose>
                                        <c:when test="${i <= averageRating}">
                                            <li><i class="fa-solid fa-star"></i></li>
                                            </c:when>
                                            <c:when test="${(i - 0.5) <= averageRating}">
                                            <li><i class="fa-solid fa-star-half-alt"></i></li>
                                            </c:when>
                                            <c:otherwise>
                                            <li><i class="fa-regular fa-star"></i></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                            </ol>
                            <p class="Student">${course.studentCount}</p>
                        </div>
                        <p>Created by <a href="#">${primaryInstructor.user.fullname}</a></p>
                    </div>

                    <div class="display-7">
                        <h3> Language : ${language.name} (${language.nativename})</h3>

                        <h3> Last Update :
                            <fmt:formatDate value="${course.lastUpdate}" pattern="dd-MM-yyyy HH:mm:ss" />
                        </h3>
                    </div>

                    <div class="last-infor">
                        <div class="last-infor2">
                        </div>
                        <h3>What you will learn</h3>
                        <div class="infor">

                            <ol class="rol-1">

                                <c:forEach items="${learningoutcomes}" var="outcome" end="2">
                                    <li><i class="fa-solid fa-check"></i>${outcome}</li>
                                    </c:forEach>
                            </ol>

                            <ol class="rol-2">
                                <c:forEach items="${learningoutcomes}" var="outcome" begin="3">
                                    <li><i class="fa-solid fa-check"></i>${outcome}</li>
                                    </c:forEach>
                            </ol>

                        </div>


                        <h3>Rquirements</h3>
                        <ul>
                            <c:forEach items="${course.requirements}" var="requirement">
                                <li>${requirement}</li>
                                </c:forEach>

                        </ul>

                        <h3>Description</h3>
                        <c:forTokens items="${course.description}" delims="\n" var="paragraph">
                            <p>${paragraph}</p>
                        </c:forTokens>

                        <h3> Course For : ${course.courseFor}</h3>

                        <% //<a href="#" class="see-more"><i class="fa-solid fa-down-long"></i>Show more</a>
                        %>

                        <div class="last-infor2">
                            <div class="mentor">
                                <c:choose>
                                    <c:when test="${not empty avatarmentor}">
                                        <img src="${avatarmentor}" alt="avatarmentor">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pageContext.request.contextPath}/resource/image/avamentor.jpg"
                                             alt="avatarmentor">
                                    </c:otherwise>
                                </c:choose>

                                <div class="mentor-content">
                                    <h2>Mentor</h2>
                                    <h3>
                                        <c:choose>
                                            <c:when test="${not empty linkedin_profile}">
                                                <a href="${linkedin_profile}"
                                                   target="_blank">${mentorname}</a>
                                            </c:when>
                                            <c:otherwise>
                                                ${mentorname}
                                            </c:otherwise>
                                        </c:choose>
                                    </h3>
                                    <p class="subtitle">
                                        <c:if test="${not empty special}">
                                            ${special} -
                                        </c:if>
                                        <c:if test="${not empty education_level}">
                                            ${education_level}
                                        </c:if>
                                    </p>

                                    <ul>
                                        <li><i class="fa-solid fa-face-smile"></i>
                                            <fmt:formatNumber value="${averageRating}" pattern="0.0" />
                                            Rating
                                        </li>
                                        <% /* <li><i class="fa-solid fa-expand"></i>
                                            <c:choose>
                                                <c:when
                                                    test="${not empty primaryInstructor.reviewCount}">
                                                    ${primaryInstructor.reviewCount} Reviews
                                                </c:when>
                                            </c:choose>
                                            </li>
                                            <li><i class="fa-solid fa-graduation-cap"></i>
                                                <c:choose>
                                                    <c:when
                                                        test="${not empty primaryInstructor.studentCount}">
                                                        ${primaryInstructor.studentCount} Students
                                                    </c:when>
                                                </c:choose>
                                            </li>
                                            <li><i class="fa-solid fa-book"></i>
                                                <c:choose>
                                                    <c:when
                                                        test="${not empty primaryInstructor.courseCount}">
                                                        ${primaryInstructor.courseCount} Courses
                                                    </c:when>
                                                </c:choose>
                                            </li>
                                            */%>
                                    </ul>

                                    <div class="description">
                                        <c:choose>
                                            <c:when test="${not empty descriptionInstructor}">
                                                <p>${descriptionInstructor}</p>
                                            </c:when>
                                            <c:otherwise>
                                                <p>${mentorname} is an experienced instructor with
                                                    ${experienced} years of teaching experience.</p>
                                                <p>Specialized in ${special} with a ${education_level}
                                                    degree.</p>
                                                </c:otherwise>
                                            </c:choose>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="paycourse">
                    <div>
                        <c:if test="${not empty imagescourse}">
                            <img src="${imagescourse}" alt="Course thumbnail">
                        </c:if>
                        <c:if test=" ${empty course.thumbnailUrl}">
                            <img src="resourse/images/avatarcourse.jpg" alt="Default thumbnail">
                        </c:if>

                        <div class="bg-primary mb-5 py-3">
                            <h3 class="text-white py-3 px-4 m-0">Course Features</h3>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Instructor</h6>
                                <h6 class="text-white my-3">
                                    ${mentorname}
                                </h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Rating</h6>
                                <h6 class="text-white my-3">
                                    <c:choose>
                                        <c:when test="${ratingCount > 0}">
                                            <fmt:formatNumber value="${averageRating}" pattern="0.0" />
                                            <small>(${ratingCount})</small>
                                        </c:when>
                                        <c:otherwise>
                                            Not rated
                                        </c:otherwise>
                                    </c:choose>
                                </h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Lectures</h6>
                                <h6 class="text-white my-3">
                                    ${lesson}
                                </h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Duration</h6>
                                <h6 class="text-white my-3">
                                    ${totaltime} hours
                                </h6>
                            </div>
                            <div class="d-flex justify-content-between border-bottom px-4">
                                <h6 class="text-white my-3">Skill level</h6>
                                <h6 class="text-white my-3">All Level</h6>
                            </div>
                            <div class="d-flex justify-content-between px-4">
                                <h6 class="text-white my-3">Language</h6>
                                <h6 class="text-white my-3">
                                    ${language}
                                </h6>
                            </div>
                            <h5 class="text-white py-3 px-4 m-0">Course Price: ${price}<i
                                    class="fa-solid fa-money-bill-wave"></i></h5>
                            <!-- Coupon Section -->
                            <div class="coupon-section">
                                <c:if test="${not empty coupon}">
                                    <div class="coupon-code">
                                        ${coupon} is applied -
                                        <fmt:formatNumber value="${percentageofcounpon * 100}"
                                                          pattern="0.##" />% off
                                    </div>
                                </c:if>

                                <div class="coupon-input">
                                    <input type="text" placeholder="Enter Coupon">
                                    <button class="apply-btn">Apply</button>
                                </div>
                            </div>
                            <div class="py-3 px-4">
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="course_id" value="${course.id}">
                                    <button type="submit" class="btn btn-block btn-secondary py-3 px-5">ADD
                                        TO CART</button>
                                </form>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>


        <div class="video-content">
            <h2>PREVIEW COURSE</h2>
            <div class="course-cotent">
                <h3>Course Content</h3>
                <p>${totalmodule} Module In Course - ${lesson} lesson - ${totaltime} time</p>

                <div class="section">
                    <c:forEach items="${modules}" var="module">
                        <div class="section-header">
                            <strong>▶ ${module.title}</strong>
                            <span> ${module.lessons.size()} lectures • ${module.estimatedDuration}
                                min</span>
                        </div>
                        <ul class="section-body">
                            <c:forEach var="lesson" items="${module.lessons}">
                                <li>
                                    <a href="#">${lessontitle}</a>
                                    <span>
                                        <fmt:formatNumber value="${lessontime/ 60}" pattern="00" />:
                                        <fmt:formatNumber value="${lessontime % 60}" pattern="00" />
                                    </span>
                                </li>
                            </c:forEach>


                        </ul>


                        <div class="section">
                            <c:forEach items="${modules}" var="module">
                                <div class="section-header">
                                    <strong>▶ ${module.title}</strong>
                                    <span> ${module.lessons.size()} lectures • ${module.estimatedDuration}
                                        min</span>
                                </div>
                                <ul class="section-body">
                                    <c:forEach var="lesson" items="${module.lessons}">
                                        <li>
                                            <a href="#">${lessontitle}</a>
                                            <span>
                                                <fmt:formatNumber value="${lessontime/ 60}" pattern="00" />:
                                                <fmt:formatNumber value="${lessontime % 60}" pattern="00" />
                                            </span>
                                        </li>
                                    </c:forEach>


                                </ul>
                            </div>
                        </div>
                    </div>

                    <div class="container2">
                        <div class="another-course">
                            <h2>MORE COURSE FOR YOU</h2>
                            <div class="course-list">

                                <div class="course1">
                                    <img src="image/React_Native_Tutorial.avif" alt="react">
                                    <h4> React Native</h4>
                                    <p> Nguyen Tran Thanh Duy <a href="https://www.facebook.com/yui.guende.3"></a> </p>
                                    <ul class="rating">
                                        <h6>4.6 rating</h6>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                    </ul>

                                    <p><i class="fa-solid fa-person"></i> 360 Student</p>
                                    <p><i class="fa-brands fa-youtube"></i>400 video</p>

                                    <span class="price">1.600.000 <i class="fa-solid fa-money-bill-wave"></i></span>
                                </div>

                                <div class="course2">
                                    <img src="image/JavaScript-Logo.jpg" alt="JavaScript-Logo">
                                    <h4>JavaScript</h4>
                                    <p> Nguyen Tran Thanh Duy <a href="https://www.facebook.com/yui.guende.3"></a> </p>
                                    <ul class="rating">
                                        <h6>4.6 rating</h6>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                    </ul>

                                    <p><i class="fa-solid fa-person"></i> 900 Student</p>
                                    <p><i class="fa-brands fa-youtube"></i>1000 video</p>

                                    <span class="price">2.000.000<i class="fa-solid fa-money-bill-wave"></i></span>
                                </div>

                                <div class="course3">
                                    <img src="image/github.jpg" alt="github">
                                    <h4>Github</h4>
                                    <p> Nguyen Tran Thanh Duy <a href="https://www.facebook.com/yui.guende.3"></a> </p>
                                    <ul class="rating">
                                        <h6>4.7 rating</h6>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                        <li><i class="fa-solid fa-star"></i></li>
                                    </ul>

                                    <p><i class="fa-solid fa-person"></i> 80 Student</p>
                                    <p><i class="fa-brands fa-youtube"></i>5 video</p>

                                    <span class="price">70.000<i class="fa-solid fa-money-bill-wave"></i></span>
                                </div>

                                <c:forEach items="${relatedcourse}" var="relatedCourse">
                                    <div class="course">
                                        <a
                                            href="${pageContext.request.contextPath}/course-detail?course_id=${relatedCourse.id}">
                                            <img src="${relatedCourse.thumbnail}" alt="${relatedCourse.title}">
                                        </a>
                                        <h4><a
                                                href="${pageContext.request.contextPath}/course-detail?course_id=${relatedCourse.id}">${relatedCourse.title}</a>
                                        </h4>
                                        <p>${relatedCourse.instructorName}</p>
                                        ... (các phần khác)
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>


                    <div class="review-container">
                        <div class="rating-summary">
                            <h2>Learner reviews</h2>
                            <div class="average-rating">
                                <i class="fa-solid fa-star"></i>
                                <span class="score">${averageRating}</span>
                                <span class="total">(${ratingCount} reviews)</span>
                            </div>

                            <div class="rating-bars">
                                <div class="bar"><span>5 stars</span>
                                    <div class="progress">
                                        <div style="width: 76.76%"></div>
                                    </div> <span>76.76%</span>
                                </div>
                                <div class="bar"><span>4 stars</span>
                                    <div class="progress">
                                        <div style="width: 17.52%"></div>
                                    </div> <span>17.52%</span>
                                </div>
                                <div class="bar"><span>3 stars</span>
                                    <div class="progress">
                                        <div style="width: 3.53%"></div>
                                    </div> <span>3.53%</span>
                                </div>
                                <div class="bar"><span>2 stars</span>
                                    <div class="progress">
                                        <div style="width: 0.95%"></div>
                                    </div> <span>0.95%</span>
                                </div>
                                <div class="bar"><span>1 star</span>
                                    <div class="progress">
                                        <div style="width: 1.22%"></div>
                                    </div> <span>1.22%</span>
                                </div>
                            </div>
                        </div>
                        <div class="user-reviews">
                            <p class="review-count">Showing Review</p>

                            <div class="review-box">
                                <div class="avatar">HT</div>
                                <div class="content">
                                    <p><i class="fa-solid fa-star"></i> 5 ・ Reviewed on Aug 10, 2021</p>
                                    <p>Well structured course. Concepts are explained clearly with hands on exercises.
                                    </p>
                                </div>
                            </div>

                            <div class="review-box">
                                <div class="avatar">TS</div>
                                <div class="content">
                                    <p><i class="fa-solid fa-star"></i> 5 ・ Reviewed on Nov 6, 2020</p>
                                    <p>Great course and very well structured. I'm really impressed with the instructor
                                        who give thorough
                                        walkthrough to the code.</p>
                                </div>
                            </div>

                            <div class="review-box">
                                <div class="avatar">TD</div>
                                <div class="content">
                                    <p><i class="fa-solid fa-star"></i> 5 ・ Reviewed on Apr 12, 2021</p>
                                    <p>I recommend this course to everyone who wants to excel in Machine Learning. This
                                        is a Great Course!</p>
                                </div>
                            </div>

                            <a href="#" class="view-more">View more reviews</a>
                        </div>
                    </div>

                    <div class="Footer">
                        <img src="image/logo.png" alt="logo">
                        <h2><i class="fa-solid fa-at"></i>EduBridge:Since 2025</h2>

                    </div>
                </div>
            </div>
        </div>
    </body>

</html>